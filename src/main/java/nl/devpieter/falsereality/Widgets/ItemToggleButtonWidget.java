package nl.devpieter.falsereality.Widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ItemToggleButtonWidget extends ClickableWidget {

    protected boolean toggled;
    protected Identifier texture;
    protected int u, v, pressedUOffset, hoverVOffset;
    protected ItemStack item, toggledItem;

    public ItemToggleButtonWidget(@Nullable Text message, boolean toggled, int x, int y, int width, int height) {
        super(x, y, width, height, message == null ? LiteralText.EMPTY : message);
        this.toggled = toggled;
    }

    public void setTexture(Identifier texture, int u, int v, int pressedUOffset, int hoverVOffset) {
        this.u = u;
        this.v = v;
        this.pressedUOffset = pressedUOffset;
        this.hoverVOffset = hoverVOffset;
        this.texture = texture;
    }

    public void setItems(ItemStack item, ItemStack toggledItem) {
        this.item = item;
        this.toggledItem = toggledItem;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public boolean isToggled() {
        return this.toggled;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
        this.appendDefaultNarrations(builder);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.texture);
        RenderSystem.disableDepthTest();

        ItemStack item = this.toggled ? this.toggledItem : this.item;
        int textureU = this.toggled ? this.u + this.pressedUOffset : this.u;
        int textureV = this.isHovered() ? this.v + this.hoverVOffset : this.v;

        this.drawTexture(matrices, this.x, this.y, textureU, textureV, this.width, this.height);

        if (!this.getMessage().equals(LiteralText.EMPTY)) {//TODO: Test
            client.getItemRenderer().renderInGui(item, this.x, this.y + 8);
            textRenderer.drawWithShadow(matrices, this.getMessage(), this.x + 12, this.y, -1);
        } else client.getItemRenderer().renderInGui(item, (this.x + this.width) / 2 - 8, this.y + 8);

        RenderSystem.enableDepthTest();
    }
}
