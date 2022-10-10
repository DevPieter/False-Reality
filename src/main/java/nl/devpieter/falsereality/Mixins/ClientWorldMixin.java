package nl.devpieter.falsereality.Mixins;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import nl.devpieter.falsereality.FalseReality;
import nl.devpieter.falsereality.Settings.Config;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends World {

    @Shadow
    @Final
    private ClientWorld.Properties clientWorldProperties;

    protected ClientWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Inject(at = @At("HEAD"), method = "setTimeOfDay", cancellable = true)
    public void onSetTimeOfDay(long timeOfDay, CallbackInfo callbackInfo) {
        if (FalseReality.SYNC_TIME.wasPressed()) Config.CustomTime = timeOfDay;
        if (!Config.CustomTimeEnabled) return;

        if (Config.SpedUpBy > 250) Config.SpedUpBy = -250;
        if (Config.SpedUpBy < -250) Config.SpedUpBy = 250;
        if (Config.TimeSpedUpEnabled) Config.CustomTime += Config.SpedUpBy;

        if (Config.CustomTime > 24000) Config.CustomTime = 0;
        if (Config.CustomTime < 0) Config.CustomTime = 24000;
        this.clientWorldProperties.setTimeOfDay(Config.CustomTime);

        callbackInfo.cancel();
    }
}
