package nl.devpieter.falsereality.Toasts.Info;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import nl.devpieter.falsereality.Settings.Config;
import nl.devpieter.falsereality.Toasts.ItemToast;
import nl.devpieter.falsereality.Utils.TextUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TimeSyncedInfoToast extends ItemToast {
    private final boolean successful;

    public TimeSyncedInfoToast(boolean successful) {
        this.successful = successful;
    }

    @Override
    protected @NotNull Text getTitle() {
        return new TranslatableText("string.falsereality.sync");
    }

    @Override
    protected @NotNull Text getDescription() {
        Text description = new TranslatableText("string.falsereality.sync.unsuccessful");
        if (this.successful) description = new TranslatableText("string.falsereality.sync.successful", Config.CustomTime);
        return description;
    }

    @Override
    protected Color getDescriptionColor() {
        return TextUtils.getEnabledColor(this.successful);
    }

    @Override
    protected @NotNull ItemStack getItem() {
        return new ItemStack(this.successful ? Items.CLOCK : Items.BARRIER);
    }
}
