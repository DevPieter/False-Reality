package nl.devpieter.falsereality.Toasts;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.awt.*;

public class TestToast implements Toast {

    @Override
    public Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        manager.drawTexture(matrices, 0, 0, 0, 0, this.getWidth(), this.getHeight());

        manager.getGame().textRenderer.draw(matrices, new LiteralText("Hello World!"), 30.0F, 7.0F, new Color(254, 254, 63).getRGB());
        manager.getGame().textRenderer.draw(matrices, new LiteralText("Bottom Text?"), 30.0F, 18.0F, -1);

        return startTime >= 5000L ? Visibility.HIDE : Visibility.SHOW;
    }
}
