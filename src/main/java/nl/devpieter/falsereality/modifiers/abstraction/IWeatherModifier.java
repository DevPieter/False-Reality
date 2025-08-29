package nl.devpieter.falsereality.modifiers.abstraction;

public interface IWeatherModifier extends IModifier {

    float getModifiedRainGradient(float delta, float original);

    boolean isRaining();

    void setRaining(boolean raining);
}
