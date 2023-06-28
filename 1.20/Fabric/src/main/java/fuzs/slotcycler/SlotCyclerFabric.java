package fuzs.slotcycler;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class SlotCyclerFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(SlotCycler.MOD_ID, SlotCycler::new);
    }
}
