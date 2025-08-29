package nl.devpieter.falsereality.mixins;

import net.minecraft.client.world.ClientWorld;
import nl.devpieter.falsereality.TimeManager;
import nl.devpieter.falsereality.modifiers.abstraction.ITimeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.Properties.class)
public class ClientWorldPropertiesMixin {

    @Shadow
    private long timeOfDay;

    @Unique
    private final TimeManager timeManager = TimeManager.getInstance();

    @Inject(at = @At("HEAD"), method = "getTimeOfDay", cancellable = true)
    public void getTimeOfDay(CallbackInfoReturnable<Long> cir) {
        ITimeModifier modifier = timeManager.getTimeModifier();
        if (modifier == null || !modifier.isEnabled()) return;

        cir.setReturnValue(modifier.getModifiedTime(this.timeOfDay));
    }
}
