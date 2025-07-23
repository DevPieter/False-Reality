package nl.devpieter.falsereality.listeners;

import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import nl.devpieter.falsereality.TimeManager;
import nl.devpieter.falsereality.models.TimeConfig;
import nl.devpieter.falsereality.models.WorldConfig;
import nl.devpieter.utilize.listeners.packet.IPacketListener;
import nl.devpieter.utilize.managers.TaskManager;
import nl.devpieter.utilize.utils.PlayerUtils;
import nl.devpieter.utilize.utils.TextUtils;

import java.lang.reflect.Type;

public class GameJoinPacketListener implements IPacketListener<GameJoinS2CPacket> {

    @Override
    public Type getPacketType() {
        return GameJoinS2CPacket.class;
    }

    @Override
    public boolean onPacket(GameJoinS2CPacket gameJoinS2CPacket) {
        TaskManager.getInstance().addTask(new TaskManager.RunOnceTask(() -> {
            TimeManager timeManager = TimeManager.getInstance();

            WorldConfig worldConfig = timeManager.getCurrentWorldConfig();
            if (worldConfig == null || !worldConfig.isEnabled()) return;

            TimeConfig timeConfig = timeManager.getCurrentTimeConfig();
            if (timeConfig == null) return;

            Text title = Text.translatable("falsereality.text.false_reality_is", Text.translatable("falsereality.text.enabled"));
            Text description = worldConfig.useGlobalConfig() ? Text.translatable("falsereality.text.using_global_config") : Text.translatable("falsereality.text.using_world_config");

            Style titleStyle = Style.EMPTY.withColor(0x3264a8);
            Style descriptionStyle = Style.EMPTY.withColor(0x394352).withItalic(true);

            PlayerUtils.sendMessage(TextUtils.withStyle(title, titleStyle), false);
            PlayerUtils.sendMessage(TextUtils.withStyle(description, descriptionStyle), false);
        }, 10));

        return false;
    }
}
