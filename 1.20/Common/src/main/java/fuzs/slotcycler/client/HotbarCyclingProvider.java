package fuzs.slotcycler.client;

import fuzs.puzzlesapi.api.client.slotcycling.v1.SlotCyclingProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;

public record HotbarCyclingProvider(Inventory inventory) implements SlotCyclingProvider {

    private static int getFilledSlot(Inventory inventory, boolean forward) {
        return getFilledSlot(inventory, inventory.selected, forward);
    }

    private static int getFilledSlot(Inventory inventory, int selected, boolean forward) {
        int inventoryRows = inventory.items.size() / Inventory.getSelectionSize();
        for (int i = 1; i < inventoryRows; i++) {
            int slot = ((i * (forward ? -1 : 1) + inventoryRows) % inventoryRows * Inventory.getSelectionSize() + selected) % inventory.items.size();
            if (Inventory.isHotbarSlot(slot) || !inventory.getItem(slot).isEmpty()) {
                return slot;
            }
        }
        return -1;
    }

    private static boolean performSlotCycling(Inventory inventory, int slot, boolean forward) {
        if (slot != -1) {
            while (!Inventory.isHotbarSlot(slot)) {
                int otherSlot = getFilledSlot(inventory, slot, forward);
                swapSlots(inventory.player, slot, otherSlot);
                slot = otherSlot;
            }
            ItemStack itemInHand = inventory.getItem(slot);
            if (!itemInHand.isEmpty()) itemInHand.setPopTime(5);
            return true;
        }
        return false;
    }

    private static void swapSlots(Player player, int slot, int otherSlot) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.gameMode.handleInventoryMouseClick(player.containerMenu.containerId, slot, otherSlot, ClickType.SWAP, player);
    }

    @Override
    public ItemStack getSelectedStack() {
        return this.inventory.getSelected();
    }

    @Override
    public ItemStack getForwardStack() {
        int slot = this.getForwardSlot();
        return slot != -1 ? this.inventory.getItem(slot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack getBackwardStack() {
        int slot = this.getBackwardSlot();
        return slot != -1 ? this.inventory.getItem(slot) : ItemStack.EMPTY;
    }

    @Override
    public int getForwardSlot() {
        return getFilledSlot(this.inventory, true);
    }

    @Override
    public int getBackwardSlot() {
        return getFilledSlot(this.inventory, false);
    }

    @Override
    public boolean cycleSlotForward() {
        return performSlotCycling(this.inventory, this.getForwardSlot(), true);
    }

    @Override
    public boolean cycleSlotBackward() {
        return performSlotCycling(this.inventory, this.getBackwardSlot(), false);
    }
}
