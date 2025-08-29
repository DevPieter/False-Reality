package nl.devpieter.falsereality.modifiers.abstraction;

import nl.devpieter.falsereality.enums.MoonPhase;

public interface ITimeModifier extends IModifier {

    long getTime();

    long getModifiedTime(long original);

    void setTime(long time);

    void addTime(long time);

    MoonPhase getMoonPhase();

    void setMoonPhase(MoonPhase moonPhase);
}
