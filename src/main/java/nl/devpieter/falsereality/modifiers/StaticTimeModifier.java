package nl.devpieter.falsereality.modifiers;

import nl.devpieter.falsereality.enums.MoonPhase;
import nl.devpieter.falsereality.modifiers.abstraction.ITimeModifier;

public class StaticTimeModifier implements ITimeModifier {

    private boolean enabled;

    private long time;
    private MoonPhase moonPhase = MoonPhase.FullMoon;

    @Override
    public String getName() {
        return "Time Modifier (Static)";
    }

    @Override
    public boolean isEnabled() {
//        return enabled;
        return true; // TODO - Make configurable by the user, preferably in a GUI
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public long getModifiedTime(long original) {
        return time + moonPhase.getExtraTime();
    }

    @Override
    public void setTime(long time) {
        time = time % 24000L;
        if (time < 0) time = 24000;

        this.time = time;
    }

    @Override
    public void addTime(long time) {
        setTime(this.time + time);
    }

    @Override
    public MoonPhase getMoonPhase() {
        return moonPhase;
    }

    @Override
    public void setMoonPhase(MoonPhase moonPhase) {
        this.moonPhase = moonPhase;
    }
}
