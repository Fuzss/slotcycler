package fuzs.slotcycler.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.slotcycler.SlotCycler;
import net.fabricmc.api.ClientModInitializer;

public class SlotCyclerFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(SlotCycler.MOD_ID, SlotCyclerClient::new);
    }
}
