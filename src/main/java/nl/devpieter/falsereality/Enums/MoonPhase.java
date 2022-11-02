package nl.devpieter.falsereality.Enums;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public enum MoonPhase {

    FullMoon("full_moon", 0), WaningGibbous("waning_gibbous", 1), LastQuarter("last_quarter", 2), WaningCrescent("waning_crescent", 3), NewMoon("new_moon", 4), WaxingCrescent("waxing_crescent", 5), FirstQuarter("first_quarter", 6), WaxingGibbous("waxing_gibbous", 7);

    public final String translationKey;
    public final int day;

    MoonPhase(String translationKey, int day) {
        this.translationKey = translationKey;
        this.day = day;
    }

    public Text getName() {
        return new TranslatableText("key.falsereality.moon_phase." + this.translationKey);
    }

    public long getExtraTime() {
        return 24000L * day;
    }
}
