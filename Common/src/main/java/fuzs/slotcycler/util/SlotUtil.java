package fuzs.slotcycler.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SlotUtil {
    public static int cycleHotbarSlotLeft(Player player) {
        return cycleSlotLeft(player, player.getInventory().selected);
    }

    public static int cycleSlotLeft(Player player, int slot) {
        int rightSlot = -1;
        final Inventory inventory = player.getInventory();
        for (int i = 3; i > 0; i--) {
            int slotIndex = (i * 9 + slot) % 36;
            if (Inventory.isHotbarSlot(slotIndex) || !inventory.items.get(slotIndex).isEmpty()) {
                rightSlot = slotIndex;
                break;
            }
        }
        return rightSlot;
    }

    public static int cycleHotbarSlotRight(Player player) {
        return cycleSlotRight(player, player.getInventory().selected);
    }

    public static int cycleSlotRight(Player player, int slot) {
        int leftSlot = -1;
        final Inventory inventory = player.getInventory();
        for (int i = 1; i < 4; i++) {
            int slotIndex = (i * 9 + slot) % 36;
            if (Inventory.isHotbarSlot(slotIndex) || !inventory.items.get(slotIndex).isEmpty()) {
                leftSlot = slotIndex;
                break;
            }
        }
        return leftSlot;
    }

    public static boolean cycleSlotsRight(Player player) {
        return cycleSlotsRight(player, SlotUtil::swapSlots);
    }

    public static boolean cycleSlotsRight(Player player, SlotSwapper slotSwapper) {
        if (SlotUtil.cycleHotbarSlotRight(player) != -1) {
            int currentSlot = SlotUtil.cycleSlotRight(player, player.getInventory().selected);
            while (!Inventory.isHotbarSlot(currentSlot)) {
                int nextSlot = SlotUtil.cycleSlotRight(player, currentSlot);
                slotSwapper.swapSlots(player, currentSlot, nextSlot);
                currentSlot = nextSlot;
            }
            return true;
        }
        return false;
    }

    public static boolean cycleSlotsLeft(Player player) {
        return cycleSlotsLeft(player, SlotUtil::swapSlots);
    }

    public static boolean cycleSlotsLeft(Player player, SlotSwapper slotSwapper) {
        if (SlotUtil.cycleHotbarSlotLeft(player) != -1) {
            int currentSlot = SlotUtil.cycleSlotLeft(player, player.getInventory().selected);
            while (!Inventory.isHotbarSlot(currentSlot)) {
                int nextSlot = SlotUtil.cycleSlotLeft(player, currentSlot);
                slotSwapper.swapSlots(player, currentSlot, nextSlot);
                currentSlot = nextSlot;
            }
            return true;
        }
        return false;
    }

    private static void swapSlots(Player player, int currentSlot, int nextSlot) {
        final NonNullList<ItemStack> items = player.getInventory().items;
        ItemStack itemstack = items.get(nextSlot);
        items.set(nextSlot, items.get(currentSlot));
        items.set(currentSlot, itemstack);
    }

    public interface SlotSwapper {
        void swapSlots(Player player, int currentSlot, int nextSlot);
    }
}
