package nl.devpieter.falsereality.Screens.Widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class ItemToggleWidget extends ClickableWidget {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private final Identifier texture = new Identifier("falsereality", "textures/gui/item_toggle_widget.png");

    public static final int textureWidth = 24, textureHeight = 24;

    private boolean toggled;
    private final ToggleCallback callback;

    private ItemStack enabledItem, disabledItem;

    public ItemToggleWidget(int x, int y, boolean toggled, ToggleCallback callback) {
        super(x, y, 24, 24, LiteralText.EMPTY);
        this.toggled = toggled;
        this.callback = callback;
    }

    public boolean toggled() {
        return this.toggled;
    }

    public void toggled(boolean toggled) {
        this.toggled = toggled;
        this.callback.onToggle(toggled);
    }

    public void setItem(ItemStack item) {
        this.enabledItem = item;
        this.disabledItem = item;
    }

    public void setItems(ItemStack enabledItem, ItemStack disabledItem) {
        this.enabledItem = enabledItem;
        this.disabledItem = disabledItem;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.texture);
        this.drawTexture(matrices, this.x, this.y, this.toggled ? 27 : 1, this.isHovered() ? 27 : 1, this.width, this.height);

        ItemStack item = this.toggled ? this.enabledItem : this.disabledItem;
        if (item == null) return;
        this.client.getItemRenderer().renderInGuiWithOverrides(item, this.x + 4, this.y + 4);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.toggled(!this.toggled());
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
        this.appendDefaultNarrations(builder);
    }
}
