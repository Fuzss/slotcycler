package fuzs.slotcycler.client.handler;

import fuzs.slotcycler.client.init.ClientModRegistry;
import fuzs.slotcycler.util.SlotUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;

public class KeyBindingHandler {

    public static void onClientTick$Start(Minecraft minecraft) {
        if (minecraft.getOverlay() == null && (minecraft.screen == null || minecraft.screen.passEvents)) {
            handleKeybinds(minecraft.player);
        }
    }

    private static void handleKeybinds(Player player) {
        while (ClientModRegistry.CYCLE_LEFT_KEY_MAPPING.consumeClick()) {
            if (!player.isSpectator()) {
                if (SlotUtil.cycleSlotsLeft(player, KeyBindingHandler::swapSlots)) {
                    // not sure if this actually does something client-side
                    player.stopUsingItem();
                    setPopTimeColumn(player);
                }
            }
        }
        while (ClientModRegistry.CYCLE_RIGHT_KEY_MAPPING.consumeClick()) {
            if (!player.isSpectator()) {
                if (SlotUtil.cycleSlotsRight(player, KeyBindingHandler::swapSlots)) {
                    // not sure if this actually does something client-side
                    player.stopUsingItem();
                    setPopTimeColumn(player);
                }
            }
        }
    }

    public static void setPopTimeColumn(Player player) {
        final int selected = player.getInventory().selected;
        for (int i = 0; i < 4; i++) {
            final ItemStack itemStack = player.getInventory().items.get(selected + 9 * i);
            if (!itemStack.isEmpty()) itemStack.setPopTime(5);
        }
    }

    public static void swapSlots(Player player, int currentSlot, int nextSlot) {
        final Minecraft minecraft = Minecraft.getInstance();
        minecraft.gameMode.handleInventoryMouseClick(player.containerMenu.containerId, currentSlot, nextSlot, ClickType.SWAP, player);
    }
}
