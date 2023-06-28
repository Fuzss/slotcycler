package fuzs.slotcycler.config;

import net.minecraft.client.gui.screens.Screen;

import java.util.function.BooleanSupplier;

public enum ModifierKey {
    DISABLED(() -> false), NONE(() -> true), CONTROL(Screen::hasControlDown), SHIFT(Screen::hasShiftDown), ALT(Screen::hasAltDown);

    private final BooleanSupplier active;

    ModifierKey(BooleanSupplier active) {
        this.active = active;
    }

    public boolean isActive() {
        return this.active.getAsBoolean();
    }

    public boolean isKey() {
        return this == CONTROL || this == SHIFT || this == ALT;
    }
}
