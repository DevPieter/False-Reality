package nl.devpieter.falsereality.models;

public class WorldConfig {

    private boolean isEnabled;
    private boolean useGlobalConfig;

    private TimeConfig timeConfig;

    public WorldConfig(boolean isEnabled, boolean useGlobalConfig, TimeConfig timeConfig) {
        this.isEnabled = isEnabled;
        this.useGlobalConfig = useGlobalConfig;
        this.timeConfig = timeConfig;
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

    public TimeConfig timeConfig() {
        return timeConfig;
    }

    public void setTimeConfig(TimeConfig timeConfig) {
        this.timeConfig = timeConfig;
    }
}
