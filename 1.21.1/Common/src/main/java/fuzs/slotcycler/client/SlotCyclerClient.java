package fuzs.slotcycler.client;

import fuzs.hotbarslotcycling.api.v1.client.HotbarCyclingProvider;
import fuzs.hotbarslotcycling.api.v1.client.SlotCyclingProvider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.minecraft.world.entity.player.Player;

public class SlotCyclerClient implements ClientModConstructor {

    @Override
    public void onClientSetup() {
        SlotCyclingProvider.registerProvider((Player player) -> {
            return new HotbarCyclingProvider(player.getInventory());
        });
    }
}
