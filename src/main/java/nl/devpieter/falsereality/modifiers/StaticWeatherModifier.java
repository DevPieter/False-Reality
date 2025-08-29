package nl.devpieter.falsereality.modifiers;

import net.minecraft.util.math.MathHelper;
import nl.devpieter.falsereality.modifiers.abstraction.IWeatherModifier;

public class StaticWeatherModifier implements IWeatherModifier {

    private boolean enabled;

    private boolean raining;

    @Override
    public String getName() {
        return "Weather Modifier (Static)";
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
    public float getModifiedRainGradient(float delta, float original) {
        return MathHelper.clamp(raining ? 1.0f : 0.0f, 0.0f, 1.0f);
    }

    @Override
    public boolean isRaining() {
        return raining;
    }

    @Override
    public void setRaining(boolean raining) {
        this.raining = raining;
    }
}
