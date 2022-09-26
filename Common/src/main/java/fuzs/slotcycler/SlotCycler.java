package fuzs.slotcycler;

import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.core.ModConstructor;
import fuzs.slotcycler.config.ClientConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SlotCycler implements ModConstructor {
    public static final String MOD_ID = "slotcycler";
    public static final String MOD_NAME = "Slot Cycler";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder CONFIG = CoreServices.FACTORIES.clientConfig(ClientConfig.class, () -> new ClientConfig());

    @Override
    public void onConstructMod() {
        CONFIG.bakeConfigs(MOD_ID);
    }
}
