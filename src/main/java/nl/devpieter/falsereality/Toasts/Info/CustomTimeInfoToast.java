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

public class CustomTimeInfoToast extends ItemToast {

    @Override
    protected @NotNull Text getTitle() {
        return new TranslatableText("string.falsereality.custom_time");
    }

    @Override
    protected @NotNull Text getDescription() {
        return TextUtils.appendText(TextUtils.getEnabledText(Config.customTimeEnabled()), Config.customTimeEnabled() ? String.format(" (%s)", Config.customTime()) : "");
    }

    @Override
    protected Color getDescriptionColor() {
        return TextUtils.getEnabledColor(Config.customTimeEnabled());
    }

    @Override
    protected @NotNull ItemStack getItem() {
        return new ItemStack(Items.CLOCK);
    }
}
