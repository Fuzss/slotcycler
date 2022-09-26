package fuzs.slotcycler.client;

import fuzs.puzzleslib.client.core.ClientModConstructor;
import fuzs.slotcycler.client.init.ClientModRegistry;

public class SlotCyclerClient implements ClientModConstructor {

    @Override
    public void onRegisterKeyMappings(KeyMappingsContext context) {
        context.registerKeyMappings(ClientModRegistry.CYCLE_LEFT_KEY_MAPPING);
        context.registerKeyMappings(ClientModRegistry.CYCLE_RIGHT_KEY_MAPPING);
    }
}
