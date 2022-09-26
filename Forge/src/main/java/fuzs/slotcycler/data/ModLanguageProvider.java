package fuzs.slotcycler.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(DataGenerator gen, String modId) {
        super(gen, modId, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("key.cycleLeft", "Cycle Hotbar Slot Left");
        this.add("key.cycleRight", "Cycle Hotbar Slot Right");
    }
}
