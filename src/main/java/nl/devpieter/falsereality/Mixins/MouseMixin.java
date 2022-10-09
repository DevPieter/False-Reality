package nl.devpieter.falsereality.Mixins;

import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.MathHelper;
import nl.devpieter.falsereality.FalseReality;
import nl.devpieter.falsereality.Settings.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(at = @At("HEAD"), method = "onMouseScroll", cancellable = true)
    public void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo callbackInfo) {
        if (!FalseReality.SCROLL_THROUGH_TIME.isPressed()) return;

        boolean fast = Screen.hasControlDown();

        if (Config.TimeSpedUpEnabled) Config.SpedUpBy += MathHelper.lfloor(vertical * (fast ? 10 : 1));
        else Config.CustomTime += MathHelper.lfloor(vertical * (fast ? 100 : 10));

        callbackInfo.cancel();
    }
}
