package nl.devpieter.falsereality;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import nl.devpieter.falsereality.enums.MoonPhase;
import nl.devpieter.falsereality.listeners.GameJoinPacketListener;
import nl.devpieter.falsereality.models.WorldConfig;
import nl.devpieter.falsereality.modifiers.abstraction.ITimeModifier;
import nl.devpieter.falsereality.modifiers.abstraction.IWeatherModifier;
import nl.devpieter.falsereality.statics.KeyBindings;
import nl.devpieter.falsereality.statics.Settings;
import nl.devpieter.utilize.managers.PacketManager;

public class FalseReality implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Settings.load();
        KeyBindings.init();

        PacketManager.getInstance().subscribe(new GameJoinPacketListener());

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KeyBindings.TOGGLE_KEY.wasPressed()) {
                TimeManager timeManager = TimeManager.getInstance();
                WorldConfig worldConfig = timeManager.getCurrentWorldConfig();
                if (worldConfig == null) return;

                worldConfig.setEnabled(!worldConfig.isEnabled());
                timeManager.save();

                Text message = worldConfig.isEnabled() ?
                        Text.translatable("falsereality.text.false_reality_is", Text.translatable("falsereality.text.enabled")) :
                        Text.translatable("falsereality.text.false_reality_is", Text.translatable("falsereality.text.disabled"));

                client.inGameHud.setOverlayMessage(message, false);
            }
            if (KeyBindings.TOGGLE_USE_GLOBAL_CONFIG_KEY.wasPressed()) {
                TimeManager timeManager = TimeManager.getInstance();
                WorldConfig worldConfig = timeManager.getCurrentWorldConfig();
                if (worldConfig == null) return;

                worldConfig.setUseGlobalConfig(!worldConfig.useGlobalConfig());
                timeManager.save();

                Text message = worldConfig.useGlobalConfig() ?
                        Text.translatable("falsereality.text.using_global_config") :
                        Text.translatable("falsereality.text.using_world_config");

                client.inGameHud.setOverlayMessage(message, false);
            }
            if (KeyBindings.CYCLE_MOON_PHASE_KEY.wasPressed()) {
                TimeManager timeManager = TimeManager.getInstance();

                ITimeModifier modifier = timeManager.getTimeModifier();
                if (modifier == null) return;

                int nextOrdinal = (modifier.getMoonPhase().ordinal() + 1) % MoonPhase.values().length;
                modifier.setMoonPhase(MoonPhase.values()[nextOrdinal]);
                timeManager.save();

                WorldConfig worldConfig = timeManager.getCurrentWorldConfig();
                if (worldConfig == null) return;

                Text message = worldConfig.useGlobalConfig() ?
                        Text.translatable("falsereality.text.global_param", modifier.getMoonPhase().getTranslatedName()) :
                        Text.translatable("falsereality.text.world_param", modifier.getMoonPhase().getTranslatedName());

                client.inGameHud.setOverlayMessage(message, false);
            }

            if (KeyBindings.TOGGLE_WEATHER_KEY.wasPressed()) {
                TimeManager timeManager = TimeManager.getInstance();

                IWeatherModifier modifier = timeManager.getWeatherModifier();
                if (modifier == null) return;

                modifier.setRaining(!modifier.isRaining());
                timeManager.save();

                WorldConfig worldConfig = timeManager.getCurrentWorldConfig();
                if (worldConfig == null) return;

                Text rainingText = modifier.isRaining() ? Text.translatable("falsereality.text.raining") : Text.translatable("falsereality.text.clear");
                Text message = worldConfig.useGlobalConfig() ? Text.translatable("falsereality.text.global_param", rainingText) : Text.translatable("falsereality.text.world_param", rainingText);

                client.inGameHud.setOverlayMessage(message, false);
            }
        });
    }
}
