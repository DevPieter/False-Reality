package nl.devpieter.falsereality.statics;

import nl.devpieter.falsereality.zz_nums.MoonPhase;
import nl.devpieter.falsereality.models.TimeConfig;
import nl.devpieter.falsereality.setting.TimeConfigSetting;
import nl.devpieter.falsereality.setting.WorldConfigMapSetting;
import nl.devpieter.utilize.setting.SettingManager;
import nl.devpieter.utilize.setting.interfaces.ISetting;
import nl.devpieter.utilize.utils.common.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Settings {

    private static final File CONFIG_FOLDER = new File("config/falsereality");
    private static final File SETTINGS_FILE = new File(CONFIG_FOLDER, "Settings.json");

    public static final TimeConfigSetting GLOBAL_TIME_CONFIG = new TimeConfigSetting(
            "falsereality.global_time_config",
            new TimeConfig(0L, MoonPhase.FullMoon)
    );

    public static final WorldConfigMapSetting WORLD_CONFIGS = new WorldConfigMapSetting(
            "falsereality.world_configs",
            new HashMap<>()
    );

    public static void load() {
        if (!FileUtils.doesFileExist(SETTINGS_FILE)) return;

        SettingManager settingManager = SettingManager.getInstance();
        settingManager.loadSettings(SETTINGS_FILE, List.of(
                GLOBAL_TIME_CONFIG,
                WORLD_CONFIGS
        ));
    }

    public static void save(ISetting<?> setting) {
        if (!FileUtils.tryCreateFile(SETTINGS_FILE)) return;

        SettingManager settingManager = SettingManager.getInstance();
        settingManager.queueSave(SETTINGS_FILE, setting);
    }
}
