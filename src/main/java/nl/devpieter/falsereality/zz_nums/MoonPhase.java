package nl.devpieter.falsereality.zz_nums;

import net.minecraft.text.Text;

public enum MoonPhase {

    FullMoon("full_moon", 0),
    WaningGibbous("waning_gibbous", 1),
    LastQuarter("last_quarter", 2),
    WaningCrescent("waning_crescent", 3),
    NewMoon("new_moon", 4),
    WaxingCrescent("waxing_crescent", 5),
    FirstQuarter("first_quarter", 6),
    WaxingGibbous("waxing_gibbous", 7);

    private final String name;
    private final int dayOffset;

    MoonPhase(String name, int dayOffset) {
        this.name = name;
        this.dayOffset = dayOffset;
    }

    public Text getTranslatedName() {
        return Text.translatable("falsereality.text.moon_phase." + this.name);
    }

    public long getExtraTime() {
        return this.dayOffset * 24000L;
    }
}
