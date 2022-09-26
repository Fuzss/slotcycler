package fuzs.slotcycler.api.client.event;

import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screens.Screen;

/**
 * basically just like {@link ScreenMouseEvents}, but fires when no screen is open to handle mouse scrolling in the {@link net.minecraft.client.gui.Gui}
 * <p><code>screen</code> is therefore always <code>null</code>
 */
public final class MouseInputEvents {
    public static final Event<ScreenMouseEvents.AllowMouseScroll> ALLOW_MOUSE_SCROLL = EventFactory.createArrayBacked(ScreenMouseEvents.AllowMouseScroll.class, listeners -> (Screen screen, double mouseX, double mouseY, double horizontalAmount, double verticalAmount) -> {
        for (ScreenMouseEvents.AllowMouseScroll event : listeners) {
            if (!event.allowMouseScroll(screen, mouseX, mouseY, horizontalAmount, verticalAmount)) return false;
        }
        return true;
    });
    public static final Event<ScreenMouseEvents.BeforeMouseScroll> BEFORE_MOUSE_SCROLL = EventFactory.createArrayBacked(ScreenMouseEvents.BeforeMouseScroll.class, listeners -> (Screen screen, double mouseX, double mouseY, double horizontalAmount, double verticalAmount) -> {
        for (ScreenMouseEvents.BeforeMouseScroll event : listeners) {
            event.beforeMouseScroll(screen, mouseX, mouseY, horizontalAmount, verticalAmount);
        }
    });
    public static final Event<ScreenMouseEvents.AfterMouseScroll> AFTER_MOUSE_SCROLL = EventFactory.createArrayBacked(ScreenMouseEvents.AfterMouseScroll.class, listeners -> (Screen screen, double mouseX, double mouseY, double horizontalAmount, double verticalAmount) -> {
        for (ScreenMouseEvents.AfterMouseScroll event : listeners) {
            event.afterMouseScroll(screen, mouseX, mouseY, horizontalAmount, verticalAmount);
        }
    });

    private MouseInputEvents() {

    }
}
