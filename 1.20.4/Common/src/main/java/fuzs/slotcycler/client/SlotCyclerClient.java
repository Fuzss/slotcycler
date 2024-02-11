package fuzs.slotcycler.client;

import fuzs.puzzlesapi.api.client.slotcycling.v1.SlotCyclingProvider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;

public class SlotCyclerClient implements ClientModConstructor {

    @Override
    public void onClientSetup() {
        SlotCyclingProvider.registerProvider(player -> new HotbarCyclingProvider(player.getInventory()));
    }
}
