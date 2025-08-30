package nl.devpieter.falsereality.models;

import nl.devpieter.falsereality.modifiers.StaticTimeModifier;
import nl.devpieter.falsereality.modifiers.StaticWeatherModifier;
import nl.devpieter.falsereality.modifiers.abstraction.ITimeModifier;
import nl.devpieter.falsereality.modifiers.abstraction.IWeatherModifier;

public class WorldConfig {

    private boolean isEnabled;
    private boolean useGlobalConfig;

    private PolymorphicValue<ITimeModifier> timeModifier;
    private PolymorphicValue<IWeatherModifier> weatherModifier;

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

    public PolymorphicValue<ITimeModifier> getTimeModifier() {
        return timeModifier;
    }

    public PolymorphicValue<IWeatherModifier> getWeatherModifier() {
        return weatherModifier;
    }

    public void sync() {
        if (timeModifier != null) timeModifier.sync();
        else timeModifier = new PolymorphicValue<>(new StaticTimeModifier());

        if (weatherModifier != null) weatherModifier.sync();
        else weatherModifier = new PolymorphicValue<>(new StaticWeatherModifier());
    }
}
