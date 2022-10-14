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
        return new TranslatableText("Custom Time (todo) translate");
    }

    @Override
    protected @NotNull Text getDescription() {
        return TextUtils.getEnabledText(Config.CustomTimeEnabled);
    }

    @Override
    protected Color getDescriptionColor() {
        return TextUtils.getEnabledColor(Config.CustomTimeEnabled);
    }

    @Override
    protected @NotNull ItemStack getItem() {
        return new ItemStack(Items.CLOCK);
    }
}
