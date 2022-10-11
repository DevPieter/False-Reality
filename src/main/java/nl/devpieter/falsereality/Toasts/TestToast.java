package nl.devpieter.falsereality.Toasts;

import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.awt.*;

public class TestToast extends IToast {

    @Override
    protected Visibility drawToast(MatrixStack matrices, ToastManager manager, long startTime) {
        manager.getGame().textRenderer.draw(matrices, new LiteralText("Hello World!"), 30.0F, 7.0F, new Color(254, 254, 63).getRGB());
        manager.getGame().textRenderer.draw(matrices, new LiteralText("Bottom Text?"), 30.0F, 18.0F, new Color(190, 190, 190).getRGB());
        return this.getDefaultShowProgress(startTime);
    }
}
