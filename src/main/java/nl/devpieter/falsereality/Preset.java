package nl.devpieter.falsereality;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import nl.devpieter.falsereality.Settings.Config;
import nl.devpieter.falsereality.Toasts.Info.PresetLoadedInfoToast;

public enum Preset {

    DAY("day", 1000), MIDNIGHT("midnight", 18000), NIGHT("night", 13000), NOON("noon", 6000);

    public final String translationKey;
    public final long time;

    Preset(String translationKey, long time) {
        this.translationKey = translationKey;
        this.time = time;
    }

    public void load() {
        Config.CustomTime = this.time;
        MinecraftClient.getInstance().getToastManager().add(new PresetLoadedInfoToast(this));
    }

    public Text getName() {
        return new TranslatableText("key.falsereality.time." + this.translationKey);
    }
}
