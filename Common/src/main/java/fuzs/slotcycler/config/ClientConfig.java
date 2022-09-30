package fuzs.slotcycler.config;

import fuzs.puzzleslib.config.ConfigCore;
import fuzs.puzzleslib.config.annotation.Config;

public class ClientConfig implements ConfigCore {
    @Config(description = {"Modifier key required to be held to allow for cycling slots by scrolling.", "Setting to \"NONE\" will overwrite hotbar scrolling, making the hotbar only usable with number keys."})
    public ModifierKey scrollingModifierKey = ModifierKey.ALT;
    @Config(description = "Offset of cycle slots display from screen bottom. Allows for compatibility with mods that move the hotbar upwards such as Raised.")
    public int slotsOffset = 0;
    @Config(description = "Hide the display of the next and previous item you can cycle the current hotbar slot to.")
    public boolean hideSlotsDisplay = false;

    public enum ModifierKey {
        NONE, CONTROL, SHIFT, ALT
    }
}
