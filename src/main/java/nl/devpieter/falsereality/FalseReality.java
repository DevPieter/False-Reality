package nl.devpieter.falsereality;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import nl.devpieter.falsereality.Enums.MoonPhase;
import nl.devpieter.falsereality.Settings.Config;
import nl.devpieter.falsereality.Toasts.IToast;
import nl.devpieter.falsereality.Toasts.Info.CustomTimeInfoToast;
import nl.devpieter.falsereality.Toasts.Info.SpedUpTimeInfoToast;
import nl.devpieter.falsereality.Enums.Preset;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

public class FalseReality implements ModInitializer {

    public static final KeyBinding TOGGLE_CUSTOM_TIME = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.toggle_custom_time", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6, "category.falsereality"));
    public static final KeyBinding TOGGLE_SPED_UP_TIME = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.toggle_sped_up_time", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F7, "category.falsereality"));
    public static final KeyBinding SCROLL_THROUGH_TIME = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.scroll_through_time", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "category.falsereality"));

    public static final KeyBinding SYNC_TIME = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.sync_time", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F8, "category.falsereality"));

    @Override
    public void onInitialize() {
        Preset.loadKeybindings();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            Config.moonPhase(MoonPhase.NewMoon);

            if (TOGGLE_CUSTOM_TIME.wasPressed()) {
                Config.customTimeEnabled(!Config.customTimeEnabled());
                IToast.send(new CustomTimeInfoToast());
            }
            if (TOGGLE_SPED_UP_TIME.wasPressed()) {
                Config.spedUpTimeEnabled(!Config.spedUpTimeEnabled());
                IToast.send(new SpedUpTimeInfoToast());
            }

            for (Preset preset : Arrays.stream(Preset.values()).filter(preset -> preset.keyBinding.wasPressed()).toList()) preset.load();
        });
    }
}
