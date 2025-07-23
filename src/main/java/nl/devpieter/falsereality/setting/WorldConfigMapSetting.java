package nl.devpieter.falsereality.setting;

import nl.devpieter.falsereality.models.WorldConfig;
import nl.devpieter.utilize.setting.base.SettingBase;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class WorldConfigMapSetting extends SettingBase<HashMap<String, WorldConfig>> implements IWorldConfigMapSetting {

    public WorldConfigMapSetting(@NotNull String identifier, HashMap<String, WorldConfig> defaultValue) {
        super(identifier, defaultValue);
    }

    @Override
    public void put(String key, WorldConfig value) {
        if (value == null) throw new IllegalArgumentException("WorldConfig value cannot be null");
        this.getValue().put(key, value);
    }

    @Override
    public WorldConfig get(String key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        return this.getValue().get(key);
    }
}