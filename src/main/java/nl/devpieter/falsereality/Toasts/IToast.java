package nl.devpieter.falsereality.Toasts;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;

public abstract class IToast implements Toast {

    @Override
    public Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        manager.drawTexture(matrices, 0, 0, 0, 0, this.getWidth(), this.getHeight());

        return drawToast(matrices, manager, startTime);
    }

    protected abstract Visibility drawToast(MatrixStack matrices, ToastManager manager, long startTime);

    protected Visibility getDefaultShowProgress(long startTime) {
        return startTime >= 2000L ? Visibility.HIDE : Visibility.SHOW;
    }

    public static void send(Toast toast) {
        MinecraftClient.getInstance().getToastManager().add(toast);
    }
}
