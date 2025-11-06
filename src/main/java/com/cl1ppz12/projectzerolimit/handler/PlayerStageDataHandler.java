package com.cl1ppz12.projectzerolimit.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.WorldSavePath;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerStageDataHandler {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String DATA_FOLDER_NAME = "zero_limit_data";
    private static final String DATA_FILE_NAME = "oni_stages.json";

    // Keep track of processed advancements in memory to prevent double increments
    private static final Set<String> processedAdvancements = new HashSet<>();

    public static void onWorldLoad(MinecraftServer server, ServerWorld world) {
        Path worldPath = server.getSavePath(WorldSavePath.ROOT);
        File dataFolder = worldPath.resolve(DATA_FOLDER_NAME).toFile();
        if (!dataFolder.exists()) dataFolder.mkdirs();

        File dataFile = worldPath.resolve(DATA_FOLDER_NAME).resolve(DATA_FILE_NAME).toFile();
        if (!dataFile.exists()) {
            try (FileWriter writer = new FileWriter(dataFile)) {
                GSON.toJson(new JsonObject(), writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addPlayer(MinecraftServer server, UUID playerUuid) {
        Path worldPath = server.getSavePath(WorldSavePath.ROOT);
        File dataFile = worldPath.resolve(DATA_FOLDER_NAME).resolve(DATA_FILE_NAME).toFile();

        try (FileReader reader = new FileReader(dataFile)) {
            JsonObject data = GSON.fromJson(reader, JsonObject.class);
            if (data == null) data = new JsonObject();

            if (!data.has(playerUuid.toString())) {
                JsonObject playerData = new JsonObject();
                playerData.addProperty("stageLevel", 1);
                data.add(playerUuid.toString(), playerData);
            }

            try (FileWriter writer = new FileWriter(dataFile)) {
                GSON.toJson(data, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void incrementPlayerStage(MinecraftServer server, UUID playerUuid, String advancementId) {
        String processedKey = playerUuid + ":" + advancementId;
        if (processedAdvancements.contains(processedKey)) return; // already incremented for this advancement

        processedAdvancements.add(processedKey);

        Path worldPath = server.getSavePath(WorldSavePath.ROOT);
        File dataFile = worldPath.resolve(DATA_FOLDER_NAME).resolve(DATA_FILE_NAME).toFile();

        try (FileReader reader = new FileReader(dataFile)) {
            JsonObject data = GSON.fromJson(reader, JsonObject.class);
            if (data == null) data = new JsonObject();

            JsonObject playerData;
            if (data.has(playerUuid.toString())) {
                playerData = data.getAsJsonObject(playerUuid.toString());
                int currentStage = playerData.get("stageLevel").getAsInt();
                playerData.addProperty("stageLevel", currentStage + 1);
            } else {
                playerData = new JsonObject();
                playerData.addProperty("stageLevel", 1);
            }

            data.add(playerUuid.toString(), playerData);

            try (FileWriter writer = new FileWriter(dataFile)) {
                GSON.toJson(data, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
