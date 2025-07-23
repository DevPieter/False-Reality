package nl.devpieter.falsereality.statics;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static final KeyBinding TOGGLE_KEY = new KeyBinding(
            "falsereality.key.toggle",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_BRACKET,
            "falsereality.category"
    );

    public static final KeyBinding TOGGLE_USE_GLOBAL_CONFIG_KEY = new KeyBinding(
            "falsereality.key.toggle_use_global_config",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_BRACKET,
            "falsereality.category"
    );

    public static final KeyBinding CYCLE_MOON_PHASE_KEY = new KeyBinding(
            "falsereality.key.cycle_moon_phase",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_M,
            "falsereality.category"
    );

    public static final KeyBinding SCROLL_THROUGH_TIME_KEY = new KeyBinding(
            "falsereality.key.scroll_through_time",
            InputUtil.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_5,
            "falsereality.category"
    );

    public static void init() {
        KeyBindingHelper.registerKeyBinding(TOGGLE_KEY);
        KeyBindingHelper.registerKeyBinding(TOGGLE_USE_GLOBAL_CONFIG_KEY);
        KeyBindingHelper.registerKeyBinding(CYCLE_MOON_PHASE_KEY);
        KeyBindingHelper.registerKeyBinding(SCROLL_THROUGH_TIME_KEY);
    }
}
