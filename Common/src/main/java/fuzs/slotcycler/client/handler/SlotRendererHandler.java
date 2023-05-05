package fuzs.slotcycler.client.handler;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.slotcycler.SlotCycler;
import fuzs.slotcycler.client.core.ClientModServices;
import fuzs.slotcycler.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;

public class SlotRendererHandler {
    private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");

    public static void onHudRender(Gui gui, PoseStack matrixStack, float tickDelta, int screenWidth, int screenHeight) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!minecraft.options.hideGui) {
            if (minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR) {
                if (SlotCycler.CONFIG.get(ClientConfig.class).slotsDisplayState != ClientConfig.SlotsDisplayState.NEVER) {
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.disableDepthTest();
                    renderAdditionalHotbar(minecraft, gui, matrixStack, tickDelta, screenWidth, screenHeight);
                }
            }
        }
    }

    private static void renderAdditionalHotbar(Minecraft minecraft, Gui gui, PoseStack poseStack, float partialTicks, final int screenWidth, final int screenHeight) {

        if (KeyBindingHandler.getSlotsDisplayTicks() == 0 && SlotCycler.CONFIG.get(ClientConfig.class).slotsDisplayState == ClientConfig.SlotsDisplayState.KEY) {
            return;
        }

        Player player = getCameraPlayer(minecraft);
        if (player == null) return;

        int posY = screenHeight;
        posY -= SlotCycler.CONFIG.get(ClientConfig.class).slotsYOffset;
        // support Raised mod natively on Fabric
        posY -= ClientModServices.ABSTRACTIONS.getRaisedDistance();
        if (SlotCycler.CONFIG.get(ClientConfig.class).slotsDisplayState == ClientConfig.SlotsDisplayState.KEY) {
            posY += (screenHeight - posY + 23) * (1.0F - Math.min(1.0F, (KeyBindingHandler.getSlotsDisplayTicks() - partialTicks) / 5.0F));
        }

        NonNullList<ItemStack> items = player.getInventory().items;
        int selected = player.getInventory().selected;
        int leftSlot = getRightSlot(items, selected);
        int rightSlot = getLeftSlot(items, selected);
        boolean renderToRight = player.getMainArm() == HumanoidArm.RIGHT;
        if (renderToRight) {
            if (leftSlot == -1) {
                return;
            }
        } else if (rightSlot == -1) {
            return;
        }

        ItemStack mainStack = items.get(selected);
        ItemStack leftStack = items.get(leftSlot);
        ItemStack rightStack = items.get(rightSlot);
        if (renderToRight) {
            if (leftSlot <= rightSlot) {
                rightStack = ItemStack.EMPTY;
            }
        } else {
            if (leftSlot <= rightSlot) {
                leftStack = ItemStack.EMPTY;
            }
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        
        int posX = screenWidth / 2;
        posX += (91 + SlotCycler.CONFIG.get(ClientConfig.class).slotsXOffset) * (renderToRight ? 1 : -1);
        if (renderToRight) {
            gui.blit(poseStack, posX, posY - 23, 53, 22, 29, 24);
            if (!rightStack.isEmpty()) {
                gui.blit(poseStack, posX + 40, posY - 23, 53, 22, 29, 24);
            }
            gui.blit(poseStack, posX + 28, posY - 22, 21, 0, 20, 22);
            gui.blit(poseStack, posX + 26, posY - 22 - 1, 0, 22, 24, 24);
        } else {
            if (!leftStack.isEmpty()) {
                gui.blit(poseStack, posX - 29 - 40, posY - 23, 24, 22, 29, 24);
            }
            gui.blit(poseStack, posX - 29, posY - 23, 24, 22, 29, 24);
            gui.blit(poseStack, posX - 29 - 19, posY - 22, 21, 0, 20, 22);
            gui.blit(poseStack, posX - 29 - 21, posY - 22 - 1, 0, 22, 24, 24);
        }

        posY -= 16 + 3;
        if (renderToRight) {
            renderItemInSlot(minecraft, posX + 10, posY, partialTicks, player, leftStack);
            renderItemInSlot(minecraft, posX + 10 + 20, posY, partialTicks, player, mainStack);
            renderItemInSlot(minecraft, posX + 10 + 20 + 20, posY, partialTicks, player, rightStack);
        } else {
            renderItemInSlot(minecraft, posX - 26, posY, partialTicks, player, rightStack);
            renderItemInSlot(minecraft, posX - 26 - 20, posY, partialTicks, player, mainStack);
            renderItemInSlot(minecraft, posX - 26 - 20 - 20, posY, partialTicks, player, leftStack);
        }
    }

    private static int getLeftSlot(NonNullList<ItemStack> items, int selected) {
        int leftSlot = -1;
        for (int i = 1; i < 4; i++) {
            int slotIndex = i * 9 + selected;
            if (!items.get(slotIndex).isEmpty()) {
                leftSlot = slotIndex;
                break;
            }
        }
        return leftSlot;
    }

    private static int getRightSlot(NonNullList<ItemStack> items, int selected) {
        int rightSlot = -1;
        for (int i = 3; i > 0; i--) {
            int slotIndex = i * 9 + selected;
            if (!items.get(slotIndex).isEmpty()) {
                rightSlot = slotIndex;
                break;
            }
        }
        return rightSlot;
    }

    private static Player getCameraPlayer(Minecraft minecraft) {
        return !(minecraft.getCameraEntity() instanceof Player) ? null : (Player) minecraft.getCameraEntity();
    }

    private static void renderItemInSlot(Minecraft minecraft, int posX, int posY, float partialTicks, Player player, ItemStack stack) {
        if (stack.isEmpty()) return;
        PoseStack posestack = RenderSystem.getModelViewStack();
        float f = (float) stack.getPopTime() - partialTicks;
        if (f > 0.0F) {
            float f1 = 1.0F + f / 5.0F;
            posestack.pushPose();
            posestack.translate(posX + 8, posY + 12, 0.0D);
            posestack.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
            posestack.translate(-(posX + 8), -(posY + 12), 0.0D);
            RenderSystem.applyModelViewMatrix();
        }
        minecraft.getItemRenderer().renderAndDecorateItem(player, stack, posX, posY, 0);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        if (f > 0.0F) {
            posestack.popPose();
            RenderSystem.applyModelViewMatrix();
        }
        minecraft.getItemRenderer().renderGuiItemDecorations(minecraft.font, stack, posX, posY);
    }
}
