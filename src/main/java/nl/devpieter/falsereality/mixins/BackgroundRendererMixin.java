package nl.devpieter.falsereality.mixins;

import net.minecraft.client.world.ClientWorld;
import nl.devpieter.falsereality.TimeManager;
import nl.devpieter.falsereality.modifiers.abstraction.IWeatherModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

//#if MC>=12106
import net.minecraft.client.render.fog.StandardFogModifier;
//#else
//$$ import net.minecraft.client.render.BackgroundRenderer;
//#endif

//#if MC>=12106
@Mixin(StandardFogModifier.class)
//#else
//$$ @Mixin(BackgroundRenderer.class)
//#endif
public class BackgroundRendererMixin {

    @Unique
    private static final TimeManager timeManager = TimeManager.getInstance();

    //#if MC>=12102
    @Redirect(
            method = "getFogColor",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/world/ClientWorld;getRainGradient(F)F"
            )
    )
    //#else
    //$$ @Redirect(
    //$$         method = "render",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/client/world/ClientWorld;getRainGradient(F)F"
    //$$         )
    //$$ )
    //#endif

    // TODO - Prevent code duplication, move to a util class
    private static float redirectGetRainGradient(ClientWorld instance, float delta) {
        if (!timeManager.getCurrentWorldConfig().isEnabled()) return instance.getRainGradient(delta);

        IWeatherModifier weatherModifier = timeManager.getWeatherModifier();
        if (weatherModifier == null || !weatherModifier.isEnabled()) return instance.getRainGradient(delta);

        float original = instance.getRainGradient(delta);
        return weatherModifier.getModifiedRainGradient(delta, original);
    }
}
