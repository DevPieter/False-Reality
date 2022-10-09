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

        if (Config.TimeSpedUpEnabled) this.changeSpedUpBy(MathHelper.lfloor(vertical * (fast ? 10 : 1)));
        else this.changeCustomTime(MathHelper.floor(vertical * (fast ? 100 : 10)));

        callbackInfo.cancel();
    }

    private void changeSpedUpBy(long by) {
        Config.SpedUpBy += by;

        if (Config.SpedUpBy > 250) Config.SpedUpBy = -250;
        if (Config.SpedUpBy < -250) Config.SpedUpBy = 250;
    }

    private void changeCustomTime(int time) {
        Config.CustomTime += time;

        if (Config.CustomTime > 24000) Config.CustomTime = 0;
        if (Config.CustomTime < 0) Config.CustomTime = 24000;
    }
}
