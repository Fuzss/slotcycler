package fuzs.slotcycler.client;

import fuzs.puzzleslib.client.core.ClientCoreServices;
import fuzs.slotcycler.SlotCycler;
import fuzs.slotcycler.client.handler.KeyBindingHandler;
import fuzs.slotcycler.client.handler.MouseScrollHandler;
import fuzs.slotcycler.client.handler.SlotRendererHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = SlotCycler.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SlotCyclerForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientCoreServices.FACTORIES.clientModConstructor(SlotCycler.MOD_ID).accept(new SlotCyclerClient());
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final TickEvent.ClientTickEvent evt) -> {
            if (evt.phase == TickEvent.Phase.START) {
                KeyBindingHandler.onClientTick$Start(Minecraft.getInstance());
            }
        });
        MinecraftForge.EVENT_BUS.addListener((final InputEvent.MouseScrollingEvent evt) -> {
            MouseScrollHandler.onMouseScroll(evt.getMouseX(), evt.getMouseY(), evt.getScrollDelta(), evt.getScrollDelta()).ifPresent(unit -> evt.setCanceled(true));
        });
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(final RegisterGuiOverlaysEvent evt) {
        evt.registerBelowAll("cycleable_slots", SlotRendererHandler::onHudRender);
    }
}
