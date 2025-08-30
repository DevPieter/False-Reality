package nl.devpieter.falsereality.mixins;

import net.minecraft.client.render.WeatherRendering;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.World;
import nl.devpieter.falsereality.TimeManager;
import nl.devpieter.falsereality.modifiers.abstraction.IWeatherModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WeatherRendering.class)
public class WeatherRenderingMixin {

    @Unique
    private final TimeManager timeManager = TimeManager.getInstance();

    @Redirect(
            method = "renderPrecipitation(Lnet/minecraft/world/World;Lnet/minecraft/client/render/LightmapTextureManager;IFLnet/minecraft/util/math/Vec3d;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getRainGradient(F)F"
            )
    )
    private float renderPrecipitationRedirectGetRainGradient(World instance, float delta) {
        return getOverrideRainGradient(instance, delta);
    }

    @Redirect(
            method = "addParticlesAndSound",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/world/ClientWorld;getRainGradient(F)F"
            )
    )
    private float addParticlesAndSoundRedirectGetRainGradient(ClientWorld instance, float delta) {
        return getOverrideRainGradient(instance, delta);
    }

    @Unique
    private float getOverrideRainGradient(World instance, float delta) {
        IWeatherModifier modifier = timeManager.getWeatherModifier();
        if (modifier == null || !modifier.isEnabled()) return instance.getRainGradient(delta);

        float original = instance.getRainGradient(delta);
        return modifier.getModifiedRainGradient(delta, original);
    }
}
