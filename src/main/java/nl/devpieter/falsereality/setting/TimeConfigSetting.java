package nl.devpieter.falsereality.setting;

import nl.devpieter.falsereality.models.TimeConfig;
import nl.devpieter.utilize.setting.base.SettingBase;
import org.jetbrains.annotations.NotNull;

public class TimeConfigSetting extends SettingBase<TimeConfig> implements ITimeConfigSetting {

    public TimeConfigSetting(@NotNull String identifier, TimeConfig defaultValue) {
        super(identifier, defaultValue);
    }
}
