package nl.devpieter.falsereality.setting;

import nl.devpieter.falsereality.models.TimeConfig;
import nl.devpieter.utilize.setting.interfaces.ISetting;

import java.lang.reflect.Type;

public interface ITimeConfigSetting extends ISetting<TimeConfig> {

    @Override
    default Type getType() {
        return TimeConfig.class;
    }
}
