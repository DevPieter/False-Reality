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

    public ChangeRealityScreen() {
        super(new TranslatableText("screen.falsereality.change_reality"));
    }

    @Override
    protected void init() {
        this.top = (this.height - this.textureHeight) / 2;
        this.left = (this.width - this.textureWidth) / 2;

        int center = this.width / 2;
        int bottom = this.textureHeight + top;

        ItemToggleWidget customTimeEnabledWidget = new ItemToggleWidget(center - ItemToggleWidget.textureWidth - 10, this.top + 35, Config.customTimeEnabled(), Config::customTimeEnabled);
        customTimeEnabledWidget.setItem(new ItemStack(Items.CLOCK));
        this.addDrawableChild(customTimeEnabledWidget);

        ItemToggleWidget spedUpTimeEnabledWidget = new ItemToggleWidget(center + 10, this.top + 35, Config.spedUpTimeEnabled(), Config::spedUpTimeEnabled);
        spedUpTimeEnabledWidget.setItems(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.SWIFTNESS), new ItemStack(Items.GLASS_BOTTLE));
        this.addDrawableChild(spedUpTimeEnabledWidget);

        SliderWidget customTimeSliderWidget = new SliderWidget(center - 64, bottom - 68, 128, "string.falsereality.custom_time_value", 0, 24000, value -> Config.customTime(Math.round(value)));
        customTimeSliderWidget.value(Config.customTime());
        this.addDrawableChild(customTimeSliderWidget);

        SliderWidget spedUpTimeSliderWidget = new SliderWidget(center - 64, bottom - 48, 128, "string.falsereality.sped_up_value", -250, 250, value -> Config.spedUpBy(Math.round(value)));
        spedUpTimeSliderWidget.value(Config.spedUpBy());
        this.addDrawableChild(spedUpTimeSliderWidget);

        CyclingButtonWidget<MoonPhase> moonPhaseWidget = CyclingButtonWidget.builder(MoonPhase::getName).values(MoonPhase.values()).initially(Config.moonPhase()).omitKeyText().build(center - 64, bottom - 28, 128, 20, LiteralText.EMPTY, (button, moonPhase) -> Config.moonPhase(moonPhase));
        this.addDrawableChild(moonPhaseWidget);
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

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
