package me.blackfur.ferociousforgery.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FormingAnvilScreen extends HandledScreen<FormingAnvilScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("minecraft", "textures/gui/container/dispenser.png");

    public FormingAnvilScreen(FormingAnvilScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);

        int x = (width - backgroundWidth) / 2 + 64;
        int y = (height - backgroundHeight) / 2 + 16;

        if (handler.propertyDelegate.size() == 258) {
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    fill(matrices, i * 3 + x, j * 3 + y, (i + 1) * 3 + x, (j + 1) * 3 + y,
                         getColor(handler.propertyDelegate.get(0), handler.propertyDelegate.get(1),
                                  handler.propertyDelegate.get(i * 16 + j + 2)));
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (int) ((mouseX - ((width - backgroundWidth) / 2 + 64)) / 3);
        int y = (int) ((mouseY - ((height - backgroundHeight) / 2 + 16)) / 3);
        if (x > -1 && x < 16 && y > -1 && y < 16) {
            this.client.interactionManager.clickButton(this.handler.syncId, x * 16 + y);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public int getColor(int material, int temperature, int height) {
        return material * height;
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}
