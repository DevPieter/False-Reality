package nl.devpieter.falsereality.mixins;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import nl.devpieter.falsereality.TimeManager;
import nl.devpieter.falsereality.modifiers.abstraction.IWeatherModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Unique
    private final TimeManager timeManager = TimeManager.getInstance();

    //#if MC>=12102
    //    @Redirect(
    //            method = "renderSky",
    //            at = @At(
    //                    value = "INVOKE",
    //                    target = "Lnet/minecraft/client/world/ClientWorld;getRainGradient(F)F"
    //            )
    //    )
    //    private float redirectGetRainGradient(ClientWorld instance, float delta) {
    //        return getOverrideRainGradient(instance, delta);
    //    }
    //#else
    //$$ @Redirect(
    //$$         method = "renderWeather",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/client/world/ClientWorld;getRainGradient(F)F"
    //$$         )
    //$$ )
    //$$ private float renderWeatherRedirectGetRainGradient(ClientWorld instance, float delta) {
    //$$     return getOverrideRainGradient(instance, delta);
    //$$ }
    //$$
    //$$ @Redirect(
    //$$         method = "tickRainSplashing",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/client/world/ClientWorld;getRainGradient(F)F"
    //$$         )
    //$$ )
    //$$ private float tickRainSplashingRedirectGetRainGradient(ClientWorld instance, float delta) {
    //$$     return getOverrideRainGradient(instance, delta);
    //$$ }
    //$$
    //$$ @Redirect(
    //$$         method = "renderSky",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/client/world/ClientWorld;getRainGradient(F)F"
    //$$         )
    //$$ )
    //$$ private float renderSkyRedirectGetRainGradient(ClientWorld instance, float delta) {
    //$$     return getOverrideRainGradient(instance, delta);
    //$$ }
    //$$
    //#endif

    @Unique
    private float getOverrideRainGradient(ClientWorld instance, float delta) {
        IWeatherModifier modifier = timeManager.getWeatherModifier();
        if (modifier == null || !modifier.isEnabled()) return instance.getRainGradient(delta);

        float original = instance.getRainGradient(delta);
        return modifier.getModifiedRainGradient(delta, original);
    }
}
