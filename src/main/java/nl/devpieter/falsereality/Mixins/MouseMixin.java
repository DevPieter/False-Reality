package nl.devpieter.falsereality.Mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import nl.devpieter.falsereality.FalseReality;
import nl.devpieter.falsereality.Settings.Config;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "onMouseScroll", cancellable = true)
    public void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo callbackInfo) {
        if (!FalseReality.SCROLL_THROUGH_TIME.isPressed()) return;

        //TODO: Testing
        boolean ultra = Screen.hasAltDown();
        boolean slow = Screen.hasShiftDown();
        boolean fast = Screen.hasControlDown();

        if (Config.SpedUpTimeEnabled) {
            //TODO: Config.SpedUpBy += this.getSpedUpBy((long) vertical, slow, fast, ultra);
            this.client.inGameHud.setOverlayMessage(new TranslatableText("string.falsereality.sped_up_time_set_to", Config.addSpedUpBy(this.getSpedUpBy((long) vertical, slow, fast, ultra))), false);
        } else {
            //TODO: Config.CustomTime += this.getCustomTime((long) vertical, slow, fast, ultra);
            this.client.inGameHud.setOverlayMessage(new TranslatableText("string.falsereality.custom_time_set_to", Config.addCustomTime(this.getCustomTime((long) vertical, slow, fast, ultra))), false);
        }

        callbackInfo.cancel();
    }

    private long getSpedUpBy(long times, boolean slow, boolean fast, boolean ultra) {
        if (!slow && !fast) return times;
        if (slow) return (long) (times * (ultra ? 0.1 : 0.5));
        return (times * (ultra ? 20 : 10));
    }

    private long getCustomTime(long times, boolean slow, boolean fast, boolean ultra) {
        if (!slow && !fast) return times * 10;
        if (slow) return (times * (ultra ? 1 : 5));
        return (times * (ultra ? 200 : 100));
    }
}
