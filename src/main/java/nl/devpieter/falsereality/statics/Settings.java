package nl.devpieter.falsereality.statics;

import nl.devpieter.falsereality.models.PolymorphicValue;
import nl.devpieter.falsereality.modifiers.StaticTimeModifier;
import nl.devpieter.falsereality.modifiers.StaticWeatherModifier;
import nl.devpieter.falsereality.modifiers.abstraction.ITimeModifier;
import nl.devpieter.falsereality.modifiers.abstraction.IWeatherModifier;
import nl.devpieter.falsereality.setting.TimeModifierSetting;
import nl.devpieter.falsereality.setting.WeatherModifierSetting;
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

    public static final WorldConfigMapSetting WORLD_CONFIGS = new WorldConfigMapSetting(
            "falsereality.world_configs",
            new HashMap<>()
    );

    public static final TimeModifierSetting GLOBAL_TIME_MODIFIER = new TimeModifierSetting(
            "falsereality.global_time_modifier",
            new PolymorphicValue<>(new StaticTimeModifier())
    );

    public static final WeatherModifierSetting GLOBAL_WEATHER_MODIFIER = new WeatherModifierSetting(
            "falsereality.global_weather_modifier",
            new PolymorphicValue<>(new StaticWeatherModifier())
    );

    public static void load() {
        if (!FileUtils.doesFileExist(SETTINGS_FILE)) return;

        SettingManager settingManager = SettingManager.getInstance();
        settingManager.loadSettings(SETTINGS_FILE, List.of(
                WORLD_CONFIGS,
                GLOBAL_TIME_MODIFIER,
                GLOBAL_WEATHER_MODIFIER
        ));

        GLOBAL_TIME_MODIFIER.getValue().getValue(ITimeModifier.class);
        GLOBAL_WEATHER_MODIFIER.getValue().getValue(IWeatherModifier.class);
    }

    public static void save(ISetting<?> setting) {
        if (!FileUtils.tryCreateFile(SETTINGS_FILE)) return;

        SettingManager settingManager = SettingManager.getInstance();
        settingManager.queueSave(SETTINGS_FILE, setting);
    }
}
