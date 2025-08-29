package nl.devpieter.falsereality.setting;

import com.google.common.reflect.TypeToken;
import nl.devpieter.falsereality.models.PolymorphicValue;
import nl.devpieter.utilize.setting.interfaces.ISetting;

import java.lang.reflect.Type;

public interface IPolymorphicSetting<T> extends ISetting<PolymorphicValue<T>> {

    @Override
    default Type getType() {
        return new TypeToken<PolymorphicValue<T>>() {
        }.getType();
    }
}
