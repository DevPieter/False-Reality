package nl.devpieter.falsereality.zz_ixins;

import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import nl.devpieter.falsereality.TimeManager;
import nl.devpieter.falsereality.models.TimeConfig;
import nl.devpieter.falsereality.models.WorldConfig;
import nl.devpieter.falsereality.statics.KeyBindings;
import nl.devpieter.utilize.utils.ClientUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

    @Unique
    private final TimeManager timeManager = TimeManager.getInstance();

    @Inject(at = @At("HEAD"), method = "onMouseScroll", cancellable = true)
    public void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (!KeyBindings.SCROLL_THROUGH_TIME_KEY.isPressed()) return;

        TimeConfig timeConfig = this.timeManager.getCurrentTimeConfig();
        if (timeConfig == null) return;

        boolean ultra = Screen.hasAltDown();
        boolean slow = Screen.hasShiftDown();
        boolean fast = Screen.hasControlDown();

        timeConfig.addTime(this.getCustomTime((long) vertical, slow, fast, ultra));
        timeManager.save();

        WorldConfig worldConfig = timeManager.getCurrentWorldConfig();
        if (worldConfig == null) return;

        Text message = worldConfig.useGlobalConfig() ?
                Text.translatable("falsereality.text.global_param", timeConfig.time()) :
                Text.translatable("falsereality.text.world_param", timeConfig.time());

        ClientUtils.getClient().inGameHud.setOverlayMessage(message, false);
        ci.cancel();
    }

    // Normal = 50
    // Normal Slow = 10
    // Normal Fast = 100

    // Ultra Slow = 1
    // Ultra Fast = 200

    // Scroll = Normal (50)
    // Scroll + Shift = Slow (10)
    // Scroll + Control = Fast (100)
    // Scroll + Shift + Alt = Ultra Slow (1)
    // Scroll + Control + Alt = Ultra Fast (200)

    @Unique
    private long getCustomTime(long scroll, boolean slow, boolean fast, boolean ultra) {

        // Normal
        if (!slow && !fast) return scroll * 50;

        // Slow
        if (slow) return (scroll * (ultra ? 1 : 10));

        // Fast
        return (scroll * (ultra ? 200 : 100));
    }
}
