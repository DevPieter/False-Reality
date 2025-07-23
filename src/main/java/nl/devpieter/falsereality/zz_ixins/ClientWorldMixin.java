package nl.devpieter.falsereality.zz_ixins;

import net.minecraft.client.world.ClientWorld;
import nl.devpieter.falsereality.TimeManager;
import nl.devpieter.falsereality.models.TimeConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.Properties.class)
public class ClientWorldMixin {

    @Unique
    private final TimeManager timeManager = TimeManager.getInstance();

    @Inject(at = @At("HEAD"), method = "getTimeOfDay", cancellable = true)
    public void setTimeOfDay(CallbackInfoReturnable<Long> cir) {
        TimeConfig timeConfig = this.timeManager.getCurrentTimeConfig();
        if (timeConfig != null) cir.setReturnValue(timeConfig.getExtraTime());
    }
}
