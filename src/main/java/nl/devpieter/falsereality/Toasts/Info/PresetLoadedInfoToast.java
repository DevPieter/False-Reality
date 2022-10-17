package nl.devpieter.falsereality.Toasts.Info;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import nl.devpieter.falsereality.Preset;
import nl.devpieter.falsereality.Toasts.ItemToast;
import nl.devpieter.falsereality.Utils.TextUtils;
import org.jetbrains.annotations.NotNull;

public class PresetLoadedInfoToast extends ItemToast {

    private final Preset preset;

    public PresetLoadedInfoToast(Preset preset) {
        this.preset = preset;
    }

    @Override
    protected @NotNull Text getTitle() {
        return new TranslatableText("string.falsereality.loaded_a_preset");
    }

    @Override
    protected @NotNull Text getDescription() {
        return TextUtils.appendText(this.preset.getName(), String.format(" (%s)", this.preset.time));
    }

    @Override
    protected @NotNull ItemStack getItem() {
        return new ItemStack(Items.WRITABLE_BOOK);
    }
}
