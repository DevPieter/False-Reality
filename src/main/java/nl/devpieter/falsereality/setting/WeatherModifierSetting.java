package nl.devpieter.falsereality.setting;

import nl.devpieter.falsereality.models.PolymorphicValue;
import nl.devpieter.falsereality.modifiers.abstraction.IWeatherModifier;
import nl.devpieter.utilize.setting.base.SettingBase;
import org.jetbrains.annotations.NotNull;

public class WeatherModifierSetting extends SettingBase<PolymorphicValue<IWeatherModifier>> implements IPolymorphicSetting<IWeatherModifier> {

    public WeatherModifierSetting(@NotNull String identifier, PolymorphicValue<IWeatherModifier> defaultValue) {
        super(identifier, defaultValue);
    }
}
