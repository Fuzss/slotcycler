package fuzs.slotcycler.client.handler;

import fuzs.slotcycler.SlotCycler;
import fuzs.slotcycler.config.ClientConfig;
import fuzs.slotcycler.mixin.client.accessor.MouseHandlerAccessor;
import fuzs.slotcycler.util.SlotUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class MouseScrollHandler {

    public static Optional<Unit> onMouseScroll(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (!player.isSpectator()) {
            if (isKeyDown(SlotCycler.CONFIG.get(ClientConfig.class).scrollingModifierKey)) {
                double totalScroll = verticalAmount + ((MouseHandlerAccessor) minecraft.mouseHandler).getAccumulatedScroll();
                if (totalScroll > 0.0) {
                    if (SlotUtil.cycleSlotsLeft(player, KeyBindingHandler::swapSlots)) {
                        KeyBindingHandler.setPopTimeColumn(player);
                    }
                } else if (totalScroll < 0.0) {
                    if (SlotUtil.cycleSlotsRight(player, KeyBindingHandler::swapSlots)) {
                        KeyBindingHandler.setPopTimeColumn(player);
                    }
                }
                return Optional.of(Unit.INSTANCE);
            }
        }
        return Optional.empty();
    }

    private static boolean isKeyDown(ClientConfig.ModifierKey modifierKey) {
        return switch (modifierKey) {
            case NONE -> true;
            case CONTROL -> Screen.hasControlDown();
            case SHIFT -> Screen.hasShiftDown();
            case ALT -> Screen.hasAltDown();
        };
    }
}