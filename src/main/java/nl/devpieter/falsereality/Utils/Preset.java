package nl.devpieter.falsereality.Utils;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import nl.devpieter.falsereality.Settings.Config;
import nl.devpieter.falsereality.Toasts.IToast;
import nl.devpieter.falsereality.Toasts.Info.PresetLoadedInfoToast;

public enum Preset {

    DAY("day", 1000), NOON("noon", 6000), NIGHT("night", 13000), MIDNIGHT("midnight", 18000);

    public final KeyBinding keyBinding;
    public final String translationKey;
    public final long time;

    Preset(String translationKey, long time) {
        this.keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.time." + translationKey, InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "category.falsereality"));
        this.translationKey = translationKey;
        this.time = time;
    }

    public void load() {
        Config.CustomTime = this.time;
        IToast.send(new PresetLoadedInfoToast(this));
    }

    public Text getName() {
        return new TranslatableText("key.falsereality.time." + this.translationKey);
    }

    public static void loadKeybindings() {
    }
}
