package nl.devpieter.falsereality;

import net.minecraft.client.MinecraftClient;
import nl.devpieter.falsereality.enums.MoonPhase;
import nl.devpieter.falsereality.models.TimeConfig;
import nl.devpieter.falsereality.models.WorldConfig;
import nl.devpieter.falsereality.statics.Settings;
import nl.devpieter.utilize.utils.ClientUtils;
import org.jetbrains.annotations.Nullable;

public class TimeManager {

    private static TimeManager INSTANCE;

    private TimeManager() {
    }

    public static TimeManager getInstance() {
        if (INSTANCE == null) INSTANCE = new TimeManager();
        return INSTANCE;
    }

    public void save() {
        Settings.save(Settings.GLOBAL_TIME_CONFIG);
        Settings.save(Settings.WORLD_CONFIGS);
    }

    public @Nullable TimeConfig getCurrentTimeConfig() {
        WorldConfig worldConfig = this.getCurrentWorldConfig();
        if (worldConfig == null || !worldConfig.isEnabled()) return null;

        if (worldConfig.useGlobalConfig()) return Settings.GLOBAL_TIME_CONFIG.getValue();
        return worldConfig.timeConfig();
    }

    public WorldConfig getCurrentWorldConfig() {
        String worldKey = this.getWorldKey();
        if (worldKey == null) return null;

        WorldConfig config = Settings.WORLD_CONFIGS.get(worldKey);
        if (config == null) {
            config = this.getDefaultWorldConfig();
            Settings.WORLD_CONFIGS.put(worldKey, config);
            Settings.save(Settings.WORLD_CONFIGS);
        }

        return config;
    }

    private WorldConfig getDefaultWorldConfig() {
        return new WorldConfig(false, true, new TimeConfig(0L, MoonPhase.FullMoon));
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
