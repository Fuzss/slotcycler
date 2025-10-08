package fuzs.slotcycler;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SlotCycler implements ModConstructor {
    public static final String MOD_ID = "slotcycler";
    public static final String MOD_NAME = "Slot Cycler";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public static ResourceLocation id(String path) {
        return ResourceLocationHelper.fromNamespaceAndPath(MOD_ID, path);
    }
}
