package fuzs.slotcycler.data;

import fuzs.puzzleslib.api.data.v1.AbstractLanguageProvider;
import fuzs.slotcycler.client.handler.KeyBindingHandler;
import net.minecraft.data.PackOutput;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(PackOutput packOutput, String modId) {
        super(packOutput, modId);
    }

    @Override
    protected void addTranslations() {
        this.add(KeyBindingHandler.CYCLE_LEFT_KEY_MAPPING, "Cycle Hotbar Slot Left");
        this.add(KeyBindingHandler.CYCLE_RIGHT_KEY_MAPPING, "Cycle Hotbar Slot Right");
    }
}
