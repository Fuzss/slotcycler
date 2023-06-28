package fuzs.slotcycler.client.handler;

import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.slotcycler.SlotCycler;
import fuzs.slotcycler.config.ClientConfig;
import fuzs.slotcycler.mixin.client.accessor.MouseHandlerAccessor;
import fuzs.slotcycler.util.SlotUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class MouseScrollHandler {

    public static EventResult onMouseScroll(boolean leftDown, boolean middleDown, boolean rightDown, double horizontalAmount, double verticalAmount) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (!player.isSpectator() && SlotCycler.CONFIG.get(ClientConfig.class).scrollingModifierKey.isActive()) {
            double totalScroll = verticalAmount + ((MouseHandlerAccessor) minecraft.mouseHandler).slotcycler$getAccumulatedScroll();
            if (totalScroll > 0.0) {
                if (SlotUtil.cycleSlotsLeft(player, KeyBindingHandler::swapSlots)) {
                    KeyBindingHandler.setPopTimeColumn(player);
                }
            } else if (totalScroll < 0.0) {
                if (SlotUtil.cycleSlotsRight(player, KeyBindingHandler::swapSlots)) {
                    KeyBindingHandler.setPopTimeColumn(player);
                }
            }
            return EventResult.INTERRUPT;
        }
        return EventResult.PASS;
    }
}
