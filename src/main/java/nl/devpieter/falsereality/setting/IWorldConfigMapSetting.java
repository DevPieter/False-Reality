package nl.devpieter.falsereality.setting;

import com.google.common.reflect.TypeToken;
import nl.devpieter.falsereality.models.WorldConfig;
import nl.devpieter.utilize.setting.interfaces.ISetting;

import java.lang.reflect.Type;
import java.util.HashMap;

public interface IWorldConfigMapSetting extends ISetting<HashMap<String, WorldConfig>> {

    void put(String key, WorldConfig value);

    WorldConfig get(String key);

    @Override
    default Type getType() {
        return new TypeToken<HashMap<String, WorldConfig>>() {
        }.getType();
    }
}
