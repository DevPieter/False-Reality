package nl.devpieter.falsereality.Mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;
import nl.devpieter.falsereality.FalseReality;
import nl.devpieter.falsereality.Settings.Config;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(Mouse.class)
public class MouseMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "onMouseScroll", cancellable = true)
    public void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo callbackInfo) {
        if (!FalseReality.SCROLL_THROUGH_TIME.isPressed()) return;

        boolean fast = Screen.hasControlDown();

        if (Config.SpedUpTimeEnabled) {
            Config.SpedUpBy += MathHelper.lfloor(vertical * (fast ? 10 : 1));
            this.client.inGameHud.setOverlayMessage(new TranslatableText("string.falsereality.sped_up_time_set_to", Config.SpedUpBy), false);
        } else {
            Config.CustomTime += MathHelper.lfloor(vertical * (fast ? 100 : 10));
            this.client.inGameHud.setOverlayMessage(new TranslatableText("string.falsereality.custom_time_set_to", Config.CustomTime), false);
        }

        callbackInfo.cancel();
    }
}
