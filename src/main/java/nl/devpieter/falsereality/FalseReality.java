package nl.devpieter.falsereality;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class FalseReality implements ModInitializer {

    public static boolean TEST_1;
    public static boolean TEST_2;
    public static boolean TEST_3;

    @Override
    public void onInitialize() {

        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("test_1", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_N, "category.examplemod.test"));
        KeyBinding keyBinding2 = KeyBindingHelper.registerKeyBinding(new KeyBinding("test_2", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_M, "category.examplemod.test"));
        KeyBinding keyBinding3 = KeyBindingHelper.registerKeyBinding(new KeyBinding("test_3", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_COMMA, "category.examplemod.test"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed()) TEST_1 = !TEST_1;
            if (keyBinding2.wasPressed()) TEST_2 = !TEST_2;
            if (keyBinding3.wasPressed()) TEST_3 = !TEST_3;
        });
    }
}
