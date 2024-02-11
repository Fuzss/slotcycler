package fuzs.slotcycler.forge.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.slotcycler.SlotCycler;
import fuzs.slotcycler.client.SlotCyclerClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = SlotCycler.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SlotCyclerForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(SlotCycler.MOD_ID, SlotCyclerClient::new);
    }
}
