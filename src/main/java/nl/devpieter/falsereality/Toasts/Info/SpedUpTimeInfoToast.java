package nl.devpieter.falsereality.Toasts.Info;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.*;
import nl.devpieter.falsereality.Settings.Config;
import nl.devpieter.falsereality.Toasts.ItemToast;
import nl.devpieter.falsereality.Utils.TextUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SpedUpTimeInfoToast extends ItemToast {

    @Override
    protected @NotNull Text getTitle() {
        return new TranslatableText("string.falsereality.sped_up_time");
    }

    @Override
    protected @NotNull Text getDescription() {
        return TextUtils.appendText(TextUtils.getEnabledText(Config.SpedUpTimeEnabled), Config.SpedUpTimeEnabled ? String.format(" (%s)", Config.SpedUpBy) : "");
    }

    @Override
    protected Color getDescriptionColor() {
        return TextUtils.getEnabledColor(Config.SpedUpTimeEnabled);
    }

    @Override
    protected @NotNull ItemStack getItem() {
        return new ItemStack(Items.CLOCK);
    }
}
