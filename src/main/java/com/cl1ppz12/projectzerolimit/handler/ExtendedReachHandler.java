package com.cl1ppz12.projectzerolimit.handler;

import com.cl1ppz12.projectzerolimit.item.ModItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ExtendedReachHandler {
    private static final double PICKUP_RADIUS = 12.0;

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register((ServerWorld world) -> {
            if (world.isClient) return;

            for (ServerPlayerEntity player : world.getPlayers()) {

                // Only activate when ONI_MASK is in main hand
                if (!player.getMainHandStack().isOf(ModItems.BLACK_HOLE)) continue;

                Box box = player.getBoundingBox().expand(PICKUP_RADIUS);

                List<ItemEntity> items =
                        world.getEntitiesByClass(ItemEntity.class, box, item -> !item.cannotPickup());

                for (ItemEntity item : items) {
                    double dist = player.squaredDistanceTo(item);

                    if (dist < 2.0) {
                        item.onPlayerCollision(player); // Pickup
                    } else {
                        // Magnet pull effect
                        Vec3d pull = player.getPos()
                                .subtract(item.getPos())
                                .normalize()
                                .multiply(0.1);

                        item.addVelocity(pull.x, pull.y, pull.z);
                    }
                }
            }
        });
    }
}
