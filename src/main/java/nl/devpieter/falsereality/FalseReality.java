package nl.devpieter.falsereality;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import nl.devpieter.falsereality.Settings.Config;
import nl.devpieter.falsereality.Toasts.IToast;
import nl.devpieter.falsereality.Toasts.Info.CustomTimeInfoToast;
import nl.devpieter.falsereality.Toasts.Info.SpedUpTimeInfoToast;
import nl.devpieter.falsereality.Utils.Preset;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

public class FalseReality implements ModInitializer {

    public static final KeyBinding TOGGLE_CUSTOM_TIME = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.toggle_custom_time", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6, "category.falsereality"));
    public static final KeyBinding TOGGLE_SPED_UP_TIME = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.toggle_sped_up_time", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F7, "category.falsereality"));
    public static final KeyBinding SCROLL_THROUGH_TIME = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.scroll_through_time", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "category.falsereality"));

    public static final KeyBinding SYNC_TIME = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.sync_time", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F8, "category.falsereality"));

    /* TIME PRESETS */
//    public static final KeyBinding TIME_DAY = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.time.day", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "category.falsereality"));
//    public static final KeyBinding TIME_MIDNIGHT = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.time.midnight", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "category.falsereality"));
//    public static final KeyBinding TIME_NIGHT = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.time.night", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "category.falsereality"));
//    public static final KeyBinding TIME_NOON = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.time.noon", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "category.falsereality"));

    @Override
    public void onInitialize() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (TOGGLE_CUSTOM_TIME.wasPressed()) {
                Config.CustomTimeEnabled = !Config.CustomTimeEnabled;
                IToast.send(new CustomTimeInfoToast());
            }
            if (TOGGLE_SPED_UP_TIME.wasPressed()) {
                Config.SpedUpTimeEnabled = !Config.SpedUpTimeEnabled;
                IToast.send(new SpedUpTimeInfoToast());
            }

            for (Preset preset : Arrays.stream(Preset.values()).filter(preset -> preset.keyBinding.wasPressed()).toList()) preset.load();

//            if (TIME_DAY.wasPressed()) Preset.DAY.load();
//            if (TIME_MIDNIGHT.wasPressed()) Preset.MIDNIGHT.load();
//            if (TIME_NIGHT.wasPressed()) Preset.NIGHT.load();
//            if (TIME_NOON.wasPressed()) Preset.NOON.load();
        });
    }
}
