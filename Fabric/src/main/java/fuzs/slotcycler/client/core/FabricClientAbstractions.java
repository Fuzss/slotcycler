package fuzs.slotcycler.client.core;

import net.fabricmc.loader.api.FabricLoader;

public class FabricClientAbstractions implements ClientAbstractions {


    @Override
    public int getRaisedDistance() {
        return FabricLoader.getInstance().getObjectShare().get("raised:distance") instanceof Integer distance ? distance : 0;
    }
}
