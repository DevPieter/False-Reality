package nl.devpieter.falsereality.Settings;

import nl.devpieter.falsereality.Enums.MoonPhase;

public class Config {

    public static boolean CustomTimeEnabled;
    public static long CustomTime = 13000;

    public static MoonPhase MoonPhase;

    public static boolean SpedUpTimeEnabled;
    public static long SpedUpBy = 15;

    public static long setCustomTime(long time) {
        time = time % 24000L;
        if (time < 0) time = 24000;
        CustomTime = time;
        return CustomTime;
    }

    public static long addCustomTime(long time) {
        return setCustomTime(CustomTime + time);
    }

    public static long setSpedUpBy(long spedUpBy) {
        if (spedUpBy > 250) spedUpBy = -250;
        if (spedUpBy < -250) spedUpBy = 250;
        SpedUpBy = spedUpBy;
        return SpedUpBy;
    }

    public static long addSpedUpBy(long spedUpBy) {
        return setSpedUpBy(SpedUpBy + spedUpBy);
    }
}
