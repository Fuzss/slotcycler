package fuzs.slotcycler.client;

import fuzs.puzzleslib.client.core.ClientCoreServices;
import fuzs.slotcycler.SlotCycler;
import fuzs.slotcycler.api.client.event.MouseInputEvents;
import fuzs.slotcycler.client.handler.KeyBindingHandler;
import fuzs.slotcycler.client.handler.MouseScrollHandler;
import fuzs.slotcycler.client.handler.SlotRendererHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class SlotCyclerFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCoreServices.FACTORIES.clientModConstructor(SlotCycler.MOD_ID).accept(new SlotCyclerClient());
        registerHandlers();
    }

    private static void registerHandlers() {
        ClientTickEvents.START_CLIENT_TICK.register(KeyBindingHandler::onClientTick$Start);
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            Minecraft minecraft = Minecraft.getInstance();
            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            SlotRendererHandler.onHudRender(minecraft.gui, matrixStack, tickDelta, screenWidth, screenHeight);
        });
        MouseInputEvents.ALLOW_MOUSE_SCROLL.register((Screen screen, double mouseX, double mouseY, double horizontalAmount, double verticalAmount) -> {
            return MouseScrollHandler.onMouseScroll(mouseX, mouseY, horizontalAmount, verticalAmount).isEmpty();
        });
    }
}
