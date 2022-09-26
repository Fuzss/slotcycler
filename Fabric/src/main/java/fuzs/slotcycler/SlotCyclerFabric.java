package fuzs.slotcycler;

import fuzs.puzzleslib.core.CoreServices;
import net.fabricmc.api.ModInitializer;

public class SlotCyclerFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CoreServices.FACTORIES.modConstructor(SlotCycler.MOD_ID).accept(new SlotCycler());
    }
}
