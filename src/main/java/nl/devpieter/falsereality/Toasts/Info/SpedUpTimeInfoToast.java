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
        return new TranslatableText("Sped Up Time (todo) translate");
    }

    @Override
    protected @NotNull Text getDescription() {
        Text title = TextUtils.getEnabledText(Config.TimeSpedUpEnabled);
        title = TextUtils.appendText(title, new LiteralText(String.format(" (%s)", Config.SpedUpBy)));
        return title;
    }

    @Override
    protected Color getDescriptionColor() {
        return TextUtils.getEnabledColor(Config.TimeSpedUpEnabled);
    }

    @Override
    protected @NotNull ItemStack getItem() {
        return new ItemStack(Items.CLOCK);
    }
}
