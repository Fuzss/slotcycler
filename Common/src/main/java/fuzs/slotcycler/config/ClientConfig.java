package fuzs.slotcycler.config;

import fuzs.puzzleslib.config.ConfigCore;
import fuzs.puzzleslib.config.annotation.Config;
import fuzs.slotcycler.SlotCycler;
import net.minecraft.client.gui.screens.Screen;

import java.util.function.BooleanSupplier;

public class ClientConfig implements ConfigCore {
    @Config(description = {"Modifier key required to be held to allow for scrolling through cycle slots.", "Setting to \"NONE\" will overwrite vanilla hotbar scrolling, making the hotbar only usable with number keys."})
    public ModifierKey scrollingModifierKey = ModifierKey.ALT;
    @Config(description = {"Offset of cycle slots display from screen bottom.", "Allows for compatibility with mods that move the hotbar upwards such as the Raised mod (Raised has built-in support on Fabric)."})
    public int slotsOffset = 0;
    @Config(description = "Choose when the cycle slots display is shown next to the hotbar: Always, never, or only when the modifier key for scrolling is held. Doesn't disable cycling itself though.")
    public SlotsDisplayState slotsDisplayState = SlotsDisplayState.ALWAYS;

    public enum ModifierKey {
        DISABLED(() -> false),
        NONE(() -> true),
        CONTROL(Screen::hasControlDown),
        SHIFT(Screen::hasShiftDown),
        ALT(Screen::hasAltDown);

        private final BooleanSupplier active;

        ModifierKey(BooleanSupplier active) {
            this.active = active;
        }

        public boolean active() {
            return this.active.getAsBoolean();
        }
    }

    public enum SlotsDisplayState {
        ALWAYS(() -> true),
        KEY(() -> SlotCycler.CONFIG.get(ClientConfig.class).scrollingModifierKey.active()),
        NEVER(() -> false);

        private final BooleanSupplier show;

        SlotsDisplayState(BooleanSupplier show) {
            this.show = show;
        }

        public boolean show() {
            return this.show.getAsBoolean();
        }
    }
}
