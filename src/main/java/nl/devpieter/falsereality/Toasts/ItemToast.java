package nl.devpieter.falsereality.Toasts;

import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class ItemToast extends IToast {

    @NotNull
    protected abstract Text getTitle();

    @NotNull
    protected abstract Text getDescription();

    @NotNull
    protected abstract ItemStack getItem();

    protected Color getTitleColor() {
        return new Color(254, 254, 63);
    }

    protected Color getDescriptionColor() {
        return new Color(190, 190, 190);
    }

    @Override
    protected Visibility drawToast(MatrixStack matrices, ToastManager manager, long startTime) {
        manager.getGame().textRenderer.draw(matrices, this.getTitle(), 30.0F, 7.0F, this.getTitleColor().getRGB());
        manager.getGame().textRenderer.draw(matrices, this.getDescription(), 30.0F, 18.0F, this.getDescriptionColor().getRGB());

        //TODO: Render item

        return this.getDefaultShowProgress(startTime);
    }

}
