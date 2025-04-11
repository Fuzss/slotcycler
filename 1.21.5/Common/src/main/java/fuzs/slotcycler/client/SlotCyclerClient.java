package fuzs.slotcycler.client;

import fuzs.hotbarslotcycling.api.v1.client.HotbarCyclingProvider;
import fuzs.hotbarslotcycling.api.v1.client.SlotCyclingProvider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.slotcycler.SlotCycler;
import net.minecraft.world.entity.player.Player;

public class SlotCyclerClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        ConfigHolder.registerConfigurationScreen(SlotCycler.MOD_ID, "hotbarslotcycling");
    }

    @Override
    public void onClientSetup() {
        SlotCyclingProvider.registerProvider((Player player) -> {
            return new HotbarCyclingProvider(player.getInventory());
        });
    }
}
