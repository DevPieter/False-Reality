package nl.devpieter.falsereality.models;

import nl.devpieter.falsereality.zz_nums.MoonPhase;

public class TimeConfig {

    private long time;
    private MoonPhase moonPhase;

    public TimeConfig(long time, MoonPhase moonPhase) {
        this.time = time;
        this.moonPhase = moonPhase;
    }

    public long getExtraTime() {
        return this.time + this.moonPhase.getExtraTime();
    }

    public long time() {
        return this.time;
    }

    public void setTime(long time) {
        time = time % 24000L;
        if (time < 0) time = 24000;

        this.time = time;
    }

    public void addTime(long time) {
        this.setTime(this.time + time);
    }

    public MoonPhase moonPhase() {
        return this.moonPhase;
    }

    public void setMoonPhase(MoonPhase moonPhase) {
        this.moonPhase = moonPhase;
    }
}
