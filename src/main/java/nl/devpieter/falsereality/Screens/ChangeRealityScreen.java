package nl.devpieter.falsereality.Screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import nl.devpieter.falsereality.Screens.Widgets.ItemToggleWidget;
import nl.devpieter.falsereality.Settings.Config;

import java.awt.*;

public class ChangeRealityScreen extends Screen {

    private final Identifier texture = new Identifier("falsereality", "textures/gui/config.png");
    private final int backgroundWidth = 147, backgroundHeight = 166;
    private int top, left;

    private ItemToggleWidget customTimeEnabledWidget, spedUpTimeEnabledWidget;

    public ChangeRealityScreen() {
        super(new TranslatableText("screen.falsereality.change_reality"));
    }

    @Override
    protected void init() {
        this.top = (this.height - this.backgroundHeight) / 2;
        this.left = (this.width - this.backgroundWidth) / 2;

        this.customTimeEnabledWidget = new ItemToggleWidget(this.left, this.top, Config.customTimeEnabled());
        this.customTimeEnabledWidget.setItem(new ItemStack(Items.CLOCK));
        this.addDrawableChild(this.customTimeEnabledWidget);

        this.spedUpTimeEnabledWidget = new ItemToggleWidget(this.left, this.top, Config.spedUpTimeEnabled());
        this.spedUpTimeEnabledWidget.setItems(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.SWIFTNESS), new ItemStack(Items.GLASS_BOTTLE));
        this.addDrawableChild(this.spedUpTimeEnabledWidget);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.renderBackgroundImage(matrices);
        super.render(matrices, mouseX, mouseY, delta);

        drawCenteredTextWithShadow(matrices, this.textRenderer, this.title.asOrderedText(), this.width / 2, this.top + 12, new Color(255, 85, 85).getRGB());
    }

    public void renderBackgroundImage(MatrixStack matrices) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.texture);

        this.drawTexture(matrices, this.left, this.top, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
