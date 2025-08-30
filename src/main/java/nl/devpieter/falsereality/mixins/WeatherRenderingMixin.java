package nl.devpieter.falsereality.mixins;

import nl.devpieter.falsereality.TimeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

//#if MC>=12102

import net.minecraft.client.render.WeatherRendering;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.World;
import nl.devpieter.falsereality.modifiers.abstraction.IWeatherModifier;

@Mixin(WeatherRendering.class)
public class WeatherRenderingMixin {

    @Unique
    private final TimeManager timeManager = TimeManager.getInstance();

    //#if MC>=12106
    @Redirect(
            method = "renderPrecipitation(Lnet/minecraft/world/World;Lnet/minecraft/client/render/VertexConsumerProvider;IFLnet/minecraft/util/math/Vec3d;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getRainGradient(F)F"
            )
    )
    //#elseif MC>=12104
    //$$ @Redirect(
    //$$         method = "method_62316",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/world/World;getRainGradient(F)F"
    //$$         )
    //$$ )
    //#else
    //$$ @Redirect(
    //$$         method = "renderPrecipitation(Lnet/minecraft/world/World;Lnet/minecraft/client/render/LightmapTextureManager;IFLnet/minecraft/util/math/Vec3d;)V",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/world/World;getRainGradient(F)F"
    //$$         )
    //$$ )
    //#endif
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

    // TODO - Prevent code duplication, move to a util class
    @Unique
    private float getOverrideRainGradient(World instance, float delta) {
        if (!timeManager.getCurrentWorldConfig().isEnabled()) return instance.getRainGradient(delta);

        IWeatherModifier modifier = timeManager.getWeatherModifier();
        if (modifier == null || !modifier.isEnabled()) return instance.getRainGradient(delta);

        float original = instance.getRainGradient(delta);
        return modifier.getModifiedRainGradient(delta, original);
    }
}
//#else
//$$ @Mixin(TimeManager.class)
//$$ public class WeatherRenderingMixin {
//$$
//$$ }
//#endif