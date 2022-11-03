package nl.devpieter.falsereality.Settings;

import nl.devpieter.falsereality.Enums.MoonPhase;

public class Config {

    private static boolean customTimeEnabled;
    private static long customTime = 13000;

    private static MoonPhase moonPhase;

    private static boolean spedUpTimeEnabled;
    private static long spedUpBy = 15;

    public static boolean customTimeEnabled() {
        return customTimeEnabled;
    }

    public static void customTimeEnabled(boolean enabled) {
        customTimeEnabled = enabled;
    }

    public static long customTime() {
        return customTime;
    }
    
    public static long customTime(long time) {
        time = time % 24000L;
        if (time < 0) time = 24000;
        customTime = time;
        return customTime;
    }

    public static long addCustomTime(long time) {
        return customTime(customTime + time);
    }

    public static MoonPhase moonPhase() {
        return moonPhase == null ? MoonPhase.FullMoon : moonPhase;
    }

    public static void moonPhase(MoonPhase moonPhase) {
        Config.moonPhase = moonPhase;
    }

    public static boolean spedUpTimeEnabled() {
        return spedUpTimeEnabled;
    }

    public static void spedUpTimeEnabled(boolean enabled) {
        spedUpTimeEnabled = enabled;
    }

    public static long spedUpBy() {
        return spedUpBy;
    }

    public static long spedUpBy(long spedUpBy) {
        if (spedUpBy > 250) spedUpBy = -250;
        if (spedUpBy < -250) spedUpBy = 250;
        Config.spedUpBy = spedUpBy;
        return Config.spedUpBy;
    }

    public static long addSpedUpBy(long spedUpBy) {
        return spedUpBy(Config.spedUpBy + spedUpBy);
    }
}
