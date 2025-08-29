package nl.devpieter.falsereality.setting;

import nl.devpieter.falsereality.models.PolymorphicValue;
import nl.devpieter.falsereality.modifiers.abstraction.ITimeModifier;
import nl.devpieter.utilize.setting.base.SettingBase;
import org.jetbrains.annotations.NotNull;

public class TimeModifierSetting extends SettingBase<PolymorphicValue<ITimeModifier>> implements IPolymorphicSetting<ITimeModifier> {

    public TimeModifierSetting(@NotNull String identifier, PolymorphicValue<ITimeModifier> defaultValue) {
        super(identifier, defaultValue);
    }
}
