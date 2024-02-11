package fuzs.slotcycler.client;

import fuzs.hotbarslotcycling.api.v1.client.HotbarCyclingProvider;
import fuzs.hotbarslotcycling.api.v1.client.SlotCyclingProvider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;

public class SlotCyclerClient implements ClientModConstructor {

    @Override
    public void onClientSetup() {
        SlotCyclingProvider.registerProvider(player -> new HotbarCyclingProvider(player.getInventory()));
    }
}
