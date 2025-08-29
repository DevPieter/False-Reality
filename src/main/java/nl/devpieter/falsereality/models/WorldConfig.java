package nl.devpieter.falsereality.models;

import nl.devpieter.falsereality.modifiers.StaticTimeModifier;
import nl.devpieter.falsereality.modifiers.StaticWeatherModifier;
import nl.devpieter.falsereality.modifiers.abstraction.ITimeModifier;
import nl.devpieter.falsereality.modifiers.abstraction.IWeatherModifier;

public class WorldConfig {

    private boolean isEnabled;
    private boolean useGlobalConfig;

    private final PolymorphicValue<ITimeModifier> timeModifier;
    private final PolymorphicValue<IWeatherModifier> weatherModifier;

    public WorldConfig(boolean isEnabled, boolean useGlobalConfig) {
        this.isEnabled = isEnabled;
        this.useGlobalConfig = useGlobalConfig;

        timeModifier = new PolymorphicValue<>(new StaticTimeModifier());
        weatherModifier = new PolymorphicValue<>(new StaticWeatherModifier());
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean useGlobalConfig() {
        return useGlobalConfig;
    }

    public void setUseGlobalConfig(boolean useGlobalConfig) {
        this.useGlobalConfig = useGlobalConfig;
    }

//    public ITimeModifier getTimeModifier() {
//        return timeModifier.getValue(ITimeModifier.class);
//    }
//
//    public IWeatherModifier getWeatherModifier() {
//        return weatherModifier.getValue(IWeatherModifier.class);
//    }

    public PolymorphicValue<ITimeModifier> getTimeModifier() {
        return timeModifier;
    }

    public PolymorphicValue<IWeatherModifier> getWeatherModifier() {
        return weatherModifier;
    }

    public void sync() {
        timeModifier.sync();
        weatherModifier.sync();
    }
}
