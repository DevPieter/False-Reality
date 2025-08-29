package nl.devpieter.falsereality;

import net.minecraft.client.MinecraftClient;
import nl.devpieter.falsereality.models.WorldConfig;
import nl.devpieter.falsereality.modifiers.abstraction.ITimeModifier;
import nl.devpieter.falsereality.modifiers.abstraction.IWeatherModifier;
import nl.devpieter.falsereality.statics.Settings;
import nl.devpieter.utilize.utils.ClientUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class TimeManager {

    private static TimeManager INSTANCE;

    private final Logger logger = LoggerFactory.getLogger(TimeManager.class);

    private TimeManager() {
    }

    public static TimeManager getInstance() {
        if (INSTANCE == null) INSTANCE = new TimeManager();
        return INSTANCE;
    }

    public void save() {
        syncConfigs();
        validateConfigs();

        Settings.save(Settings.WORLD_CONFIGS);
        Settings.save(Settings.GLOBAL_TIME_MODIFIER);
        Settings.save(Settings.GLOBAL_WEATHER_MODIFIER);
    }

    private void syncConfigs() {
        HashMap<String, WorldConfig> worldConfigs = Settings.WORLD_CONFIGS.getValue();
        worldConfigs.forEach((key, config) -> config.sync());
        Settings.WORLD_CONFIGS.setValue(worldConfigs);

        Settings.GLOBAL_TIME_MODIFIER.getValue().sync();
        Settings.GLOBAL_WEATHER_MODIFIER.getValue().sync();
    }

    private void validateConfigs() {
        HashMap<String, WorldConfig> worldConfigs = Settings.WORLD_CONFIGS.getValue();

        worldConfigs.entrySet().removeIf(entry -> {
            if (entry.getValue() == null) {
                logger.warn("Removing null WorldConfig for world key: {}", entry.getKey());
                return true;
            }

            if (entry.getValue().getTimeModifier().getValue(ITimeModifier.class) == null) {
                logger.warn("Removing WorldConfig for world key: {} due to null TimeModifier", entry.getKey());
                return true;
            }

            if (entry.getValue().getWeatherModifier().getValue(IWeatherModifier.class) == null) {
                logger.warn("Removing WorldConfig for world key: {} due to null WeatherModifier", entry.getKey());
                return true;
            }

            return false;
        });

        Settings.WORLD_CONFIGS.setValue(worldConfigs);

        if (Settings.GLOBAL_TIME_MODIFIER.getValue().getValue(ITimeModifier.class) == null) {
            logger.warn("Global TimeModifier is null, resetting to default.");
            Settings.GLOBAL_TIME_MODIFIER.reset();
        }

        if (Settings.GLOBAL_WEATHER_MODIFIER.getValue().getValue(IWeatherModifier.class) == null) {
            logger.warn("Global WeatherModifier is null, resetting to default.");
            Settings.GLOBAL_WEATHER_MODIFIER.reset();
        }
    }

    public @Nullable ITimeModifier getTimeModifier() {
        WorldConfig worldConfig = this.getCurrentWorldConfig();
        if (worldConfig == null || !worldConfig.isEnabled()) return null;

        if (worldConfig.useGlobalConfig()) {
            return Settings.GLOBAL_TIME_MODIFIER.getValue().getValue(ITimeModifier.class);
        }

        return worldConfig.getTimeModifier().getValue(ITimeModifier.class);
    }

    public @Nullable IWeatherModifier getWeatherModifier() {
        WorldConfig worldConfig = this.getCurrentWorldConfig();
        if (worldConfig == null || !worldConfig.isEnabled()) return null;

        if (worldConfig.useGlobalConfig()) {
            return Settings.GLOBAL_WEATHER_MODIFIER.getValue().getValue(IWeatherModifier.class);
        }

        return worldConfig.getWeatherModifier().getValue(IWeatherModifier.class);
    }

    public WorldConfig getCurrentWorldConfig() {
        String worldKey = this.getWorldKey();
        if (worldKey == null) return null;

        WorldConfig config = Settings.WORLD_CONFIGS.get(worldKey);
        if (config == null) {
            config = this.getDefaultWorldConfig();

            config.sync();
            Settings.WORLD_CONFIGS.put(worldKey, config);
            Settings.save(Settings.WORLD_CONFIGS);
        }

        return config;
    }

    private WorldConfig getDefaultWorldConfig() {
        return new WorldConfig(false, true);
    }

    private @Nullable String getWorldKey() {
        MinecraftClient client = ClientUtils.getClient();
        if (client.world == null) return null;

        boolean isSingleplayer = client.isInSingleplayer();

        if (isSingleplayer) return this.getSingleplayerWorldKey();
        else return this.getMultiplayerWorldKey();
    }

    private @Nullable String getSingleplayerWorldKey() {
        MinecraftClient client = ClientUtils.getClient();
        if (client.world == null || !client.isInSingleplayer()) return null;
        if (client.getServer() == null || client.getServer().getSaveProperties() == null) return null;

        // TODO - This will work for now, but there is a change for duplicate world names.
        return client.getServer().getSaveProperties().getLevelName();
    }

    private @Nullable String getMultiplayerWorldKey() {
        MinecraftClient client = ClientUtils.getClient();
        if (client.world == null || client.getCurrentServerEntry() == null) return null;

        return client.getCurrentServerEntry().address;
    }
}
