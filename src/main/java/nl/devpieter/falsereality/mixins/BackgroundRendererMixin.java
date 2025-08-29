package nl.devpieter.falsereality.mixins;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.world.ClientWorld;
import nl.devpieter.falsereality.TimeManager;
import nl.devpieter.falsereality.modifiers.abstraction.IWeatherModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @Unique
    private static final TimeManager timeManager = TimeManager.getInstance();

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/world/ClientWorld;getRainGradient(F)F"
            )
    )
    private static float renderRedirectGetRainGradient(ClientWorld instance, float delta) {
        IWeatherModifier weatherModifier = timeManager.getWeatherModifier();
        if (weatherModifier == null || !weatherModifier.isEnabled()) return instance.getRainGradient(delta);

        float original = instance.getRainGradient(delta);
        return weatherModifier.getModifiedRainGradient(delta, original);
    }
}
