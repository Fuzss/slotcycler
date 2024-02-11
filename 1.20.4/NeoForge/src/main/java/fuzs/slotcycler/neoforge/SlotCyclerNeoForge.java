package fuzs.slotcycler.neoforge;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.slotcycler.SlotCycler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod(SlotCycler.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SlotCyclerNeoForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(SlotCycler.MOD_ID, SlotCycler::new);
    }
}
