package fuzs.slotcycler.client.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class ClientModRegistry {
    public static final KeyMapping CYCLE_LEFT_KEY_MAPPING = new KeyMapping("key.cycleLeft", InputConstants.KEY_G, "key.categories.inventory");
    public static final KeyMapping CYCLE_RIGHT_KEY_MAPPING = new KeyMapping("key.cycleRight", InputConstants.KEY_H, "key.categories.inventory");
}
