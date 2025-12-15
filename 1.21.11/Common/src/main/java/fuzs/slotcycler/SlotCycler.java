package fuzs.slotcycler;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SlotCycler implements ModConstructor {
    public static final String MOD_ID = "slotcycler";
    public static final String MOD_NAME = "Slot Cycler";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
