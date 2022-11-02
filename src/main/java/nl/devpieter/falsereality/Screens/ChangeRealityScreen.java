package nl.devpieter.falsereality.Screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import nl.devpieter.falsereality.Enums.MoonPhase;
import nl.devpieter.falsereality.Screens.Widgets.ItemToggleWidget;
import nl.devpieter.falsereality.Screens.Widgets.SliderWidget;
import nl.devpieter.falsereality.Settings.Config;

import java.awt.*;

public class ChangeRealityScreen extends Screen {

    private final Identifier texture = new Identifier("falsereality", "textures/gui/config.png");
    private final int textureWidth = 147, textureHeight = 166;
    private int top, left;

    private ItemToggleWidget customTimeEnabledWidget, spedUpTimeEnabledWidget;
    private CyclingButtonWidget<MoonPhase> moonPhaseWidget;

    public ChangeRealityScreen() {
        super(new TranslatableText("screen.falsereality.change_reality"));
    }

    @Override
    protected void init() {
        this.top = (this.height - this.textureHeight) / 2;
        this.left = (this.width - this.textureWidth) / 2;

        int center = this.width / 2;
        int bottom = this.textureHeight + top;

        //TODO: Debug
        SliderWidget sliderWidget = new SliderWidget(10, 10, 128, 20, "TODO", 0, 5, 0);

        this.customTimeEnabledWidget = new ItemToggleWidget(center - ItemToggleWidget.textureWidth - 10, this.top + 35, Config.customTimeEnabled(), Config::customTimeEnabled);
        this.customTimeEnabledWidget.setItem(new ItemStack(Items.CLOCK));
        this.addDrawableChild(this.customTimeEnabledWidget);

        this.spedUpTimeEnabledWidget = new ItemToggleWidget(center + 10, this.top + 35, Config.spedUpTimeEnabled(), Config::spedUpTimeEnabled);
        this.spedUpTimeEnabledWidget.setItems(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.SWIFTNESS), new ItemStack(Items.GLASS_BOTTLE));
        this.addDrawableChild(this.spedUpTimeEnabledWidget);

        this.moonPhaseWidget = CyclingButtonWidget.builder(MoonPhase::getName).values(MoonPhase.values()).initially(Config.moonPhase()).omitKeyText().build(center - 64, bottom - 28, 128, 20, LiteralText.EMPTY, (button, moonPhase) -> Config.moonPhase(moonPhase));
        this.addDrawableChild(this.moonPhaseWidget);
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

        this.drawTexture(matrices, this.left, this.top, 0, 0, this.textureWidth, this.textureHeight);
    }

    @Override//TODO: Remove (DEBUG)
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
