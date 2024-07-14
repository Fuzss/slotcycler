package fuzs.slotcycler.neoforge;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.slotcycler.SlotCycler;
import net.neoforged.fml.common.Mod;

@Mod(SlotCycler.MOD_ID)
public class SlotCyclerNeoForge {

    public SlotCyclerNeoForge() {
        ModConstructor.construct(SlotCycler.MOD_ID, SlotCycler::new);
    }
}
