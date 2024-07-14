package fuzs.slotcycler.neoforge.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.slotcycler.SlotCycler;
import fuzs.slotcycler.client.SlotCyclerClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = SlotCycler.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SlotCyclerNeoForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(SlotCycler.MOD_ID, SlotCyclerClient::new);
    }
}
