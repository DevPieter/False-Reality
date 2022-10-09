package nl.devpieter.falsereality;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import nl.devpieter.falsereality.Settings.Config;
import org.lwjgl.glfw.GLFW;

public class FalseReality implements ModInitializer {

    public static final KeyBinding TOGGLE_CUSTOM_TIME = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.toggle_custom_time", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6, "category.falsereality"));
    public static final KeyBinding TOGGLE_SPED_UP_TIME = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.toggle_sped_up_time", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F7, "category.falsereality"));
    public static final KeyBinding SCROLL_THROUGH_TIME = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.scroll_through_time", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "category.falsereality"));

    public static final KeyBinding SYNC_TIME = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.sync_time", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F8, "category.falsereality"));

    /* TIME PRESETS */
    public static final KeyBinding TIME_DAY = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.time.day", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "category.falsereality"));
    public static final KeyBinding TIME_MIDNIGHT = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.time_midnight", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "category.falsereality"));
    public static final KeyBinding TIME_NIGHT = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.time_night", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "category.falsereality"));
    public static final KeyBinding TIME_NOON = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.falsereality.time_noon", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "category.falsereality"));

    @Override
    public void onInitialize() {//TODO: Add toast messages
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (TOGGLE_CUSTOM_TIME.wasPressed()) {
                Config.CustomTimeEnabled = !Config.CustomTimeEnabled;
                client.player.sendMessage(new LiteralText(String.format("DEBUG: Custom Time %s", Config.CustomTimeEnabled ? "Enabled" : "Disabled")), true);
            }
            if (TOGGLE_SPED_UP_TIME.wasPressed()) {
                Config.TimeSpedUpEnabled = !Config.TimeSpedUpEnabled;
                client.player.sendMessage(new LiteralText(String.format("DEBUG: Time Sped Up %s", Config.TimeSpedUpEnabled ? "Enabled" : "Disabled")), true);
            }

            if (TIME_DAY.wasPressed()) Config.CustomTime = 1000;
            if (TIME_MIDNIGHT.wasPressed()) Config.CustomTime = 18000;
            if (TIME_NIGHT.wasPressed()) Config.CustomTime = 13000;
            if (TIME_NOON.wasPressed()) Config.CustomTime = 6000;
        });
    }
}
