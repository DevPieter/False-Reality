package nl.devpieter.falsereality.Toasts;

import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;

public class InfoToast extends IToast {

    private final Text title;
    private final Color titleColor;

    private final Text description;
    private final Color descriptionColor;


    public InfoToast(Text title, Text description) {
        this.title = title;
        this.titleColor = new Color(254, 254, 63);
        this.description = description;
        this.descriptionColor = new Color(190, 190, 190);
    }

    public InfoToast(Text title, Color titleColor, Text description, Color descriptionColor) {
        this.title = title;
        this.titleColor = titleColor;
        this.description = description;
        this.descriptionColor = descriptionColor;
    }

    @Override
    protected Visibility drawToast(MatrixStack matrices, ToastManager manager, long startTime) {
        manager.getGame().textRenderer.draw(matrices, this.title, 30.0F, 7.0F, this.titleColor.getRGB());
        manager.getGame().textRenderer.draw(matrices, this.description, 30.0F, 18.0F, this.descriptionColor.getRGB());
        return this.getDefaultShowProgress(startTime);
    }
}
