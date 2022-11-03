package nl.devpieter.falsereality.Screens.Widgets;

import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;
import nl.devpieter.falsereality.Screens.Widgets.Callbacks.SliderCallback;

public class SliderWidget extends net.minecraft.client.gui.widget.SliderWidget {

    private final String translationKey;
    private final double min, max;
    private final SliderCallback callback;

    public SliderWidget(int x, int y, int width, String translationKey, double min, double max, SliderCallback callback) {
        super(x, y, width, 20, LiteralText.EMPTY, 0);
        this.translationKey = translationKey;
        this.min = min;
        this.max = max;
        this.callback = callback;
    }

    @Override
    protected void updateMessage() {
        this.setMessage(new TranslatableText(this.translationKey, this.value()));
    }

    @Override
    protected void applyValue() {
        this.callback.onSlide(this.value());
    }

    public double value() {
        return MathHelper.lerp(MathHelper.clamp(this.value, 0.0, 1.0), this.min, this.max);
    }

    public void value(double value) {
        this.value = MathHelper.clamp((value - this.min) / (this.max - this.min), 0.0, 1.0);
        this.updateMessage();
    }
}
