package fuzs.slotcycler.fabric;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.slotcycler.SlotCycler;
import net.fabricmc.api.ModInitializer;

public class SlotCyclerFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(SlotCycler.MOD_ID, SlotCycler::new);
    }
}
