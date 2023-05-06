package fuzs.slotcycler.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.KeyMappingsContext;
import fuzs.puzzleslib.api.client.event.v1.ClientTickEvents;
import fuzs.puzzleslib.api.client.event.v1.InputEvents;
import fuzs.puzzleslib.api.client.event.v1.RenderGuiCallback;
import fuzs.slotcycler.client.handler.KeyBindingHandler;
import fuzs.slotcycler.client.handler.MouseScrollHandler;
import fuzs.slotcycler.client.handler.SlotRendererHandler;

public class SlotCyclerClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerHandlers();
    }

    private static void registerHandlers() {
        ClientTickEvents.START.register(KeyBindingHandler::onClientTick$Start);
        RenderGuiCallback.EVENT.register(SlotRendererHandler::onHudRender);
        InputEvents.BEFORE_MOUSE_SCROLL.register(MouseScrollHandler::onMouseScroll);
    }

    @Override
    public void onRegisterKeyMappings(KeyMappingsContext context) {
        context.registerKeyMappings(KeyBindingHandler.CYCLE_LEFT_KEY_MAPPING, KeyBindingHandler.CYCLE_RIGHT_KEY_MAPPING);
    }
}
