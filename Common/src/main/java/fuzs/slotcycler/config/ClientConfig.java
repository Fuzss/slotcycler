package fuzs.slotcycler.config;

import fuzs.puzzleslib.config.ConfigCore;
import fuzs.puzzleslib.config.annotation.Config;

public class ClientConfig implements ConfigCore {
    @Config(description = {"Modifier key required to be held to allow for scrolling through cycle slots.", "Setting to \"NONE\" will overwrite vanilla hotbar scrolling, making the hotbar only usable with keys."})
    public ModifierKey scrollingModifierKey = ModifierKey.ALT;
    @Config(description = {"Horizontal offset of cycle slots display from default position.", "Allows for compatibility with mods that also add their own displays next to the hotbar."})
    public int slotsXOffset = 0;
    @Config(description = {"Vertical offset of cycle slots display from screen bottom.", "Allows for compatibility with mods that move the hotbar upwards such as the Raised mod (Raised has built-in support on Fabric)."})
    public int slotsYOffset = 0;
    @Config(description = "Choose when the cycle slots display is shown next to the hotbar: Always, never, or only when the modifier key for scrolling is held. Doesn't disable cycling itself though.")
    public SlotsDisplayState slotsDisplayState = SlotsDisplayState.ALWAYS;
    @Config(description = "Cycle slots by pressing a hotbar key when the corresponding slot is already selected.")
    public boolean doublePressHotbarKey = true;

    public enum SlotsDisplayState {
        ALWAYS, KEY, NEVER
    }
}
