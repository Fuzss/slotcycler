package fuzs.slotcycler.neoforge.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.slotcycler.SlotCycler;
import fuzs.slotcycler.client.SlotCyclerClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = SlotCycler.MOD_ID, dist = Dist.CLIENT)
public class SlotCyclerNeoForgeClient {

    public SlotCyclerNeoForgeClient() {
        ClientModConstructor.construct(SlotCycler.MOD_ID, SlotCyclerClient::new);
    }
}
