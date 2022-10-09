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

    //TODO Translate
    public static final KeyBinding TOGGLE_CUSTOM_TIME = KeyBindingHelper.registerKeyBinding(new KeyBinding("Toggle custom time", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6, "category.falsereality"));
    public static final KeyBinding TOGGLE_SPED_UP_TIME = KeyBindingHelper.registerKeyBinding(new KeyBinding("Toggle sped-up time", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F7, "category.falsereality"));
    public static final KeyBinding SCROLL_THROUGH_TIME = KeyBindingHelper.registerKeyBinding(new KeyBinding("Scroll through time", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "category.falsereality"));

    /* TIME PRESETS */
    public static final KeyBinding TIME_DAY = KeyBindingHelper.registerKeyBinding(new KeyBinding("Day (preset)", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "category.falsereality"));
    public static final KeyBinding TIME_MIDNIGHT = KeyBindingHelper.registerKeyBinding(new KeyBinding("Midnight (preset)", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "category.falsereality"));
    public static final KeyBinding TIME_NIGHT = KeyBindingHelper.registerKeyBinding(new KeyBinding("Night (preset)", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "category.falsereality"));
    public static final KeyBinding TIME_NOON = KeyBindingHelper.registerKeyBinding(new KeyBinding("Noon (preset)", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "category.falsereality"));

    @Override
    public void onInitialize() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (TOGGLE_CUSTOM_TIME.wasPressed()) {
                Config.CustomTimeEnabled = !Config.CustomTimeEnabled;
                client.player.sendMessage(new LiteralText(String.format("Custom Time %s", Config.CustomTimeEnabled ? "Enabled" : "Disabled")), true);
            }
            if (TOGGLE_SPED_UP_TIME.wasPressed()) {
                Config.TimeSpedUpEnabled = !Config.TimeSpedUpEnabled;
                client.player.sendMessage(new LiteralText(String.format("Time Sped Up %s", Config.TimeSpedUpEnabled ? "Enabled" : "Disabled")), true);
            }

            if (TIME_DAY.wasPressed()) Config.CustomTime = 1000;
            if (TIME_MIDNIGHT.wasPressed()) Config.CustomTime = 18000;
            if (TIME_NIGHT.wasPressed()) Config.CustomTime = 13000;
            if (TIME_NOON.wasPressed()) Config.CustomTime = 6000;
        });
    }
}
