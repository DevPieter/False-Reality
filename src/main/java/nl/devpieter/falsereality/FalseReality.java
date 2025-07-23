package nl.devpieter.falsereality;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import nl.devpieter.falsereality.zz_nums.MoonPhase;
import nl.devpieter.falsereality.listeners.GameJoinPacketListener;
import nl.devpieter.falsereality.models.TimeConfig;
import nl.devpieter.falsereality.models.WorldConfig;
import nl.devpieter.falsereality.statics.KeyBindings;
import nl.devpieter.falsereality.statics.Settings;
import nl.devpieter.utilize.managers.PacketManager;

public class FalseReality implements ClientModInitializer {

    private static FalseReality INSTANCE;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
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
                TimeConfig timeConfig = timeManager.getCurrentTimeConfig();
                if (timeConfig == null) return;

                int nextOrdinal = (timeConfig.moonPhase().ordinal() + 1) % MoonPhase.values().length;
                timeConfig.setMoonPhase(MoonPhase.values()[nextOrdinal]);
                timeManager.save();

                WorldConfig worldConfig = timeManager.getCurrentWorldConfig();
                if (worldConfig == null) return;

                Text message = worldConfig.useGlobalConfig() ?
                        Text.translatable("falsereality.text.global_param", timeConfig.moonPhase().getTranslatedName()) :
                        Text.translatable("falsereality.text.world_param", timeConfig.moonPhase().getTranslatedName());

                client.inGameHud.setOverlayMessage(message, false);
            }
        });
    }

    public static FalseReality getInstance() {
        return INSTANCE;
    }
}
