package fuzs.slotcycler.client.handler;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.slotcycler.SlotCycler;
import fuzs.slotcycler.client.core.ClientModServices;
import fuzs.slotcycler.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;

public class SlotRendererHandler {
    private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");

    public static void onHudRender(Gui gui, PoseStack matrixStack, float tickDelta, int screenWidth, int screenHeight) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!minecraft.options.hideGui) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();
            if (minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR) {
                if (SlotCycler.CONFIG.get(ClientConfig.class).slotsDisplayState == ClientConfig.SlotsDisplayState.NEVER) return;
                renderAdditionalHotbar(minecraft, gui, matrixStack, tickDelta, screenWidth, screenHeight);
            }
        }
    }

    private static void renderAdditionalHotbar(Minecraft minecraft, Gui gui, PoseStack poseStack, float partialTicks, int screenWidth, int screenHeight) {
        if (SlotCycler.CONFIG.get(ClientConfig.class).slotsDisplayState == ClientConfig.SlotsDisplayState.KEY && KeyBindingHandler.cycleSlotsDisplay == 0) return;
        Player player = getCameraPlayer(minecraft);
        if (player != null) {
            final int originalScreenHeight = screenHeight;
            screenHeight -= SlotCycler.CONFIG.get(ClientConfig.class).slotsOffset;
            // support Raised mod natively on Fabric
            screenHeight -= ClientModServices.ABSTRACTIONS.getRaisedDistance();
            if (SlotCycler.CONFIG.get(ClientConfig.class).slotsDisplayState == ClientConfig.SlotsDisplayState.KEY) {
                screenHeight += (originalScreenHeight - screenHeight + 23) * (1.0F - Math.min(1.0F, (KeyBindingHandler.cycleSlotsDisplay - partialTicks) / 5.0F));
            }
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
            final Inventory inventory = player.getInventory();
            final int selected = inventory.selected;
            ItemStack mainStack = inventory.items.get(selected);
            HumanoidArm humanoidarm = player.getMainArm().getOpposite();
            int leftSlot = getRightSlot(inventory, selected);
            int rightSlot = getLeftSlot(inventory, selected);
            if (humanoidarm == HumanoidArm.LEFT) {
                if (leftSlot == -1) {
                    return;
                }
            } else if (rightSlot == -1) {
                return;
            }
            ItemStack leftStack = inventory.items.get(leftSlot);
            ItemStack rightStack = inventory.items.get(rightSlot);
            if (humanoidarm == HumanoidArm.LEFT) {
                if (leftSlot <= rightSlot) {
                    rightStack = ItemStack.EMPTY;
                }
            } else {
                if (leftSlot <= rightSlot) {
                    leftStack = ItemStack.EMPTY;
                }
            }
            final int screenCenter = screenWidth / 2;
            if (humanoidarm == HumanoidArm.LEFT) {
                gui.blit(poseStack, screenCenter + 91, screenHeight - 23, 53, 22, 29, 24);
                if (!rightStack.isEmpty()) {
                    gui.blit(poseStack, screenCenter + 91 + 40, screenHeight - 23, 53, 22, 29, 24);
                }
                gui.blit(poseStack, screenCenter + 91 + 28, screenHeight - 22, 21, 0, 20, 22);
                gui.blit(poseStack, screenCenter + 91 + 26, screenHeight - 22 - 1, 0, 22, 24, 24);
            } else {
                if (!leftStack.isEmpty()) {
                    gui.blit(poseStack, screenCenter - 91 - 29 - 40, screenHeight - 23, 24, 22, 29, 24);
                }
                gui.blit(poseStack, screenCenter - 91 - 29, screenHeight - 23, 24, 22, 29, 24);
                gui.blit(poseStack, screenCenter - 91 - 29 - 19, screenHeight - 22, 21, 0, 20, 22);
                gui.blit(poseStack, screenCenter - 91 - 29 - 21, screenHeight - 22 - 1, 0, 22, 24, 24);
            }
            int j2 = screenHeight - 16 - 3;
            if (humanoidarm == HumanoidArm.LEFT) {
                renderSlot(minecraft, screenCenter + 91 + 10, j2, partialTicks, player, leftStack);
                renderSlot(minecraft, screenCenter + 91 + 10 + 20, j2, partialTicks, player, mainStack);
                renderSlot(minecraft, screenCenter + 91 + 10 + 20 + 20, j2, partialTicks, player, rightStack);
            } else {
                renderSlot(minecraft, screenCenter - 91 - 26, j2, partialTicks, player, rightStack);
                renderSlot(minecraft, screenCenter - 91 - 26 - 20, j2, partialTicks, player, mainStack);
                renderSlot(minecraft, screenCenter - 91 - 26 - 20 - 20, j2, partialTicks, player, leftStack);
            }
        }
    }

    private static int getLeftSlot(Inventory inventory, int selected) {
        int leftSlot = -1;
        for (int i = 1; i < 4; i++) {
            int slotIndex = i * 9 + selected;
            if (!inventory.items.get(slotIndex).isEmpty()) {
                leftSlot = slotIndex;
                break;
            }
        }
        return leftSlot;
    }

    private static int getRightSlot(Inventory inventory, int selected) {
        int rightSlot = -1;
        for (int i = 3; i > 0; i--) {
            int slotIndex = i * 9 + selected;
            if (!inventory.items.get(slotIndex).isEmpty()) {
                rightSlot = slotIndex;
                break;
            }
        }
        return rightSlot;
    }

    private static Player getCameraPlayer(Minecraft minecraft) {
        return !(minecraft.getCameraEntity() instanceof Player) ? null : (Player) minecraft.getCameraEntity();
    }

    private static void renderSlot(Minecraft minecraft, int p_168678_, int p_168679_, float p_168680_, Player p_168681_, ItemStack p_168682_) {
        if (!p_168682_.isEmpty()) {
            PoseStack posestack = RenderSystem.getModelViewStack();
            float f = (float)p_168682_.getPopTime() - p_168680_;
            if (f > 0.0F) {
                float f1 = 1.0F + f / 5.0F;
                posestack.pushPose();
                posestack.translate((double)(p_168678_ + 8), (double)(p_168679_ + 12), 0.0D);
                posestack.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                posestack.translate((double)(-(p_168678_ + 8)), (double)(-(p_168679_ + 12)), 0.0D);
                RenderSystem.applyModelViewMatrix();
            }
            minecraft.getItemRenderer().renderAndDecorateItem(p_168681_, p_168682_, p_168678_, p_168679_, 0);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            if (f > 0.0F) {
                posestack.popPose();
                RenderSystem.applyModelViewMatrix();
            }
            minecraft.getItemRenderer().renderGuiItemDecorations(minecraft.font, p_168682_, p_168678_, p_168679_);
        }
    }
}
