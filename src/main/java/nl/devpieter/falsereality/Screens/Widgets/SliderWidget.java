package nl.devpieter.falsereality.Screens.Widgets;

import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;

//TODO: Under construction (TEST)
public class SliderWidget extends net.minecraft.client.gui.widget.SliderWidget {

    private final String translationKey;
    private final double min, max;

    public SliderWidget(int x, int y, int width, int height, String translationKey, double min, double max, double value) {
        super(x, y, width, height, new TranslatableText(translationKey, value), value);
        this.translationKey = translationKey;
        this.min = min;
        this.max = max;
    }

    @Override
    protected void updateMessage() {

    }

    @Override
    protected void applyValue() {
        System.out.println("Test: " + this.value());
    }

    public double value() {
        return MathHelper.clamp(this.value * max, this.min, this.max);
    }
}
