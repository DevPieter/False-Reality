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

    @Override
    public void onInitialize() {

        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("test_1", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_N, "category.examplemod.test"));
        KeyBinding keyBinding2 = KeyBindingHelper.registerKeyBinding(new KeyBinding("test_2", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_M, "category.examplemod.test"));

        KeyBinding keyBindingPlus = KeyBindingHelper.registerKeyBinding(new KeyBinding("test_3", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UP, "category.examplemod.test"));
        KeyBinding keyBindingMinus = KeyBindingHelper.registerKeyBinding(new KeyBinding("test_4", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, "category.examplemod.test"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed()) {
                Config.CustomTimeEnabled = !Config.CustomTimeEnabled;
                client.player.sendMessage(new LiteralText("Custom Time Enabled: " + Config.CustomTimeEnabled), true);
            }
            if (keyBinding2.wasPressed()) {
                Config.TimeSpedUpEnabled = !Config.TimeSpedUpEnabled;
                client.player.sendMessage(new LiteralText("Time Sped Up Enabled: " + Config.TimeSpedUpEnabled), true);
            }

            if (keyBindingPlus.wasPressed()) {
                Config.SpedUpBy++;
                client.player.sendMessage(new LiteralText("Time Sped Up By: " + Config.SpedUpBy), true);
            }
            if (keyBindingMinus.isPressed()) {
                Config.SpedUpBy--;
                client.player.sendMessage(new LiteralText("Time Sped Up By: " + Config.SpedUpBy), true);
            }
        });
    }
}
