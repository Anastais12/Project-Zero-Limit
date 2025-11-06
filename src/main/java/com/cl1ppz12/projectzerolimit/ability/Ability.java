package com.cl1ppz12.projectzerolimit.ability;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.cl1ppz12.projectzerolimit.server.aura.AuraManager;
import com.cl1ppz12.projectzerolimit.server.cooldown.CooldownManager;
import com.cl1ppz12.projectzerolimit.util.DescriptionBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.List;

import static net.minecraft.client.MinecraftClient.getInstance;

public abstract class Ability {

    public final String id;
    private Text name;
    private List<Text> description;
    private final Identifier texture;
    private final AbilityType type;
    private final float damage;
    private final float auraCost;
    private final float cooldownTicks;

    private boolean isPassive;

    protected Ability(String id, Text name, Text description, Identifier texture, AbilityType type, float damage, int auraCost, int cooldownTicks) {
        this.id = id;
        this.name = name;
        this.description = Collections.singletonList(description);
        this.texture = texture;
        this.type = type;
        this.damage = damage;
        this.auraCost = auraCost;
        this.cooldownTicks = cooldownTicks;
        this.isPassive = false;
    }

    public void initialize() {
        setDisplayName(getTranslationTitle());
        setDescription(new DescriptionBuilder()
                .addTitle(getTranslationTitle(), getType().getFormatting(), true)
                .addDamage(getDamage())
                .addEmptyLine()
                .addDescription(getTranslationDescription())
                .addEmptyLine()
                .addCost(getCost())
                .addCooldown(getCooldown())
                .addEmptyLine()
                .build());
    }

    public abstract void use(ServerPlayerEntity player);

    public final boolean canUse(ServerPlayerEntity p) {
        return !CooldownManager.isOnCooldown(p, this)
                && AuraManager.canAfford(p, (int) auraCost);
    }

    public final void onUsed(ServerPlayerEntity p) {
        AuraManager.consume(p, (int) auraCost);
        CooldownManager.put(p, this, (int) cooldownTicks);
    }

    public Identifier getIconTexture() {
        Identifier iconId = Identifier.of(ProjectZeroLimit.MOD_ID, "textures/ability/" + id + ".png");

        if (net.fabricmc.api.EnvType.CLIENT == net.fabricmc.loader.api.FabricLoader.getInstance().getEnvironmentType()) {
            MinecraftClient mc = getInstance();
            if (mc.getResourceManager().getResource(iconId).isEmpty()) {
                return Identifier.of(ProjectZeroLimit.MOD_ID, "textures/ability/missing.png");
            }
        }
        return iconId;
    }

    public final Identifier id() {
        int colon = id.indexOf(':');
        if (colon != -1) {
            return Identifier.of(id.substring(0, colon), id.substring(colon + 1));
        }
        return Identifier.of(ProjectZeroLimit.MOD_ID, id);
    }

    public abstract void playSound(LivingEntity entity, BlockPos pos, ServerWorld world);

    public String getID() {
        return id;
    }

    public Identifier getAbilityImage() {
        return texture;
    }

    public Text getDisplayName() {
        return name;
    }

    public float getDamage() {
        return damage;
    }

    public float getCooldown() {
        return cooldownTicks;
    }

    public float getCost() {
        return auraCost;
    }

    public List<Text> getDescription() {
        return description;
    }

    public AbilityType getType() {
        return type;
    }

    public boolean isPassive() {
        return isPassive;
    }

    public void setDescription(List<Text> text) {
        this.description = text;
    }

    public void setDisplayName(Text name) {
        this.name = name;
    }

    public void setPassive(boolean passive) {
        this.isPassive = passive;
    }

    //The Translations with the id
    protected MutableText getTranslationTitle() {
        String key = "project_zero_limit.ability." + id;
        return Text.translatable(key + ".name");
    }

    protected MutableText getTranslationDescription() {
        String key = "project_zero_limit.ability." + id;
        return Text.translatable(key + ".description");
    }

    public enum AbilityType {
        PASSIVE,
        INNATE,
        ONI_ART,
        ONI_PROTOCOL,
        NULL;

        public Formatting getFormatting() {
            return switch (this) {
                case PASSIVE        -> Formatting.GREEN;
                case INNATE         -> Formatting.YELLOW;
                case ONI_ART        -> Formatting.LIGHT_PURPLE;
                case ONI_PROTOCOL   -> Formatting.DARK_PURPLE;
                default             -> Formatting.GRAY;
            };
        }

        public static int getOrdinal(AbilityType type) {
            return type.ordinal();
        }

        public static AbilityType getType(int ordinal) {
            return values()[ordinal];
        }
    }
}