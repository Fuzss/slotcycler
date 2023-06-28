package fuzs.slotcycler.client.handler;

import com.mojang.blaze3d.platform.InputConstants;
import fuzs.slotcycler.SlotCycler;
import fuzs.slotcycler.config.ClientConfig;
import fuzs.slotcycler.config.ModifierKey;
import fuzs.slotcycler.util.SlotUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiPredicate;

public class KeyBindingHandler {
    public static final KeyMapping CYCLE_LEFT_KEY_MAPPING = new KeyMapping("key.cycleLeft", InputConstants.KEY_G, "key.categories.inventory");
    public static final KeyMapping CYCLE_RIGHT_KEY_MAPPING = new KeyMapping("key.cycleRight", InputConstants.KEY_H, "key.categories.inventory");
    private static final int DEFAULT_SLOTS_DISPLAY_TICKS = 15;

    private static int slotsDisplayTicks;

    public static void onClientTick$Start(Minecraft minecraft) {
        if (slotsDisplayTicks > 0) slotsDisplayTicks--;
        if (minecraft.getOverlay() == null && minecraft.screen == null && !minecraft.player.isSpectator()) {
            handleModKeybinds(minecraft.player);
            handleHotbarKeybinds(minecraft.player, minecraft.options);
        }
    }

    private static void handleModKeybinds(Player player) {
        while (CYCLE_LEFT_KEY_MAPPING.consumeClick()) {
            cycleSlots(player, SlotUtil::cycleSlotsLeft);
        }
        while (CYCLE_RIGHT_KEY_MAPPING.consumeClick()) {
            cycleSlots(player, SlotUtil::cycleSlotsRight);
        }
        if (SlotCycler.CONFIG.get(ClientConfig.class).scrollingModifierKey.isActive()) {
            slotsDisplayTicks = DEFAULT_SLOTS_DISPLAY_TICKS;
        }
    }

    private static void handleHotbarKeybinds(Player player, Options options) {
        if (!SlotCycler.CONFIG.get(ClientConfig.class).doublePressHotbarKey) return;
        boolean saveHotbarActivatorDown = options.keySaveHotbarActivator.isDown();
        boolean loadHotbarActivatorDown = options.keyLoadHotbarActivator.isDown();
        if (!player.isCreative() || !loadHotbarActivatorDown && !saveHotbarActivatorDown) {
            ModifierKey scrollingModifierKey = SlotCycler.CONFIG.get(ClientConfig.class).scrollingModifierKey;
            boolean invert = scrollingModifierKey.isKey() && scrollingModifierKey.isActive();
            for (int i = 0; i < options.keyHotbarSlots.length; i++) {
                while (i == player.getInventory().selected && options.keyHotbarSlots[i].consumeClick()) {
                    cycleSlots(player, invert ? SlotUtil::cycleSlotsLeft : SlotUtil::cycleSlotsRight);
                }
            }
        }
    }

    private static void cycleSlots(Player player, BiPredicate<Player, SlotUtil.SlotSwapper> predicate) {
        if (predicate.test(player, KeyBindingHandler::swapSlots)) {
            // not sure if this actually does something client-side
            player.stopUsingItem();
            setPopTimeColumn(player);
            slotsDisplayTicks = DEFAULT_SLOTS_DISPLAY_TICKS;
        }
    }

    public static void setPopTimeColumn(Player player) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < 4; i++) {
            ItemStack itemStack = inventory.items.get(inventory.selected + 9 * i);
            if (!itemStack.isEmpty()) itemStack.setPopTime(5);
        }
    }

    public static void swapSlots(Player player, int currentSlot, int nextSlot) {
        final Minecraft minecraft = Minecraft.getInstance();
        minecraft.gameMode.handleInventoryMouseClick(player.containerMenu.containerId, currentSlot, nextSlot, ClickType.SWAP, player);
    }

    public static int getSlotsDisplayTicks() {
        return slotsDisplayTicks;
    }
}
