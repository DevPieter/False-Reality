package nl.devpieter.falsereality.mixins;

import net.minecraft.client.world.ClientWorld;
import nl.devpieter.falsereality.TimeManager;
import nl.devpieter.falsereality.modifiers.abstraction.IWeatherModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    @Unique
    private final TimeManager timeManager = TimeManager.getInstance();

    @Redirect(
            method = "getSkyBrightness",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/world/ClientWorld;getRainGradient(F)F"
            )
    )
    private float getSkyBrightnessRedirectGetRainGradient(ClientWorld instance, float delta) {
        return getOverrideRainGradient(instance, delta);
    }

    @Redirect(
            method = "getSkyColor",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/world/ClientWorld;getRainGradient(F)F"
            )
    )
    private float getSkyColorRedirectGetRainGradient(ClientWorld instance, float delta) {
        return getOverrideRainGradient(instance, delta);
    }

    @Redirect(
            method = "getCloudsColor",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/world/ClientWorld;getRainGradient(F)F"
            )
    )
    private float getCloudsColorRedirectGetRainGradient(ClientWorld instance, float delta) {
        return getOverrideRainGradient(instance, delta);
    }

    // TODO - Prevent code duplication, move to a util class
    @Unique
    private float getOverrideRainGradient(ClientWorld instance, float delta) {
        if (!timeManager.getCurrentWorldConfig().isEnabled()) return instance.getRainGradient(delta);

        IWeatherModifier modifier = timeManager.getWeatherModifier();
        if (modifier == null || !modifier.isEnabled()) return instance.getRainGradient(delta);

        float original = instance.getRainGradient(delta);
        return modifier.getModifiedRainGradient(delta, original);
    }
}
