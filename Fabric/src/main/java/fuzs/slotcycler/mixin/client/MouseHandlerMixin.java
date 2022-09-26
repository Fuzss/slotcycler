package fuzs.slotcycler.mixin.client;

import fuzs.slotcycler.api.client.event.MouseInputEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
abstract class MouseHandlerMixin {
	@Shadow
	@Final
	private Minecraft minecraft;
	@Shadow
	private double xpos;
	@Shadow
	private double ypos;
	@Unique
	private Double slotcycler_verticalScrollAmount;
	@Unique
	private Double slotcycler_horizontalScrollAmount;
	@Unique
	private boolean slotcycler_beforeMouseScroll;

	@Inject(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z"), cancellable = true)
	private void slotcycler_onScroll$inject$invoke$isSpectator(long windowPointer, double xOffset, double yOffset, CallbackInfo callback) {
		// Adapted to fire for the gui instead of a screen
		if (this.minecraft.screen != null) return;

		// just recalculate this instead of capturing local, shouldn't be able to change in the meantime
		this.slotcycler_verticalScrollAmount = this.minecraft.options.discreteMouseScroll().get() ? Math.signum(yOffset) : yOffset * this.minecraft.options.mouseWheelSensitivity().get();
		// Apply same calculations to horizontal scroll as vertical scroll amount has
		this.slotcycler_horizontalScrollAmount = this.minecraft.options.discreteMouseScroll().get() ? Math.signum(xOffset) : xOffset * this.minecraft.options.mouseWheelSensitivity().get();

		// xpos and ypos are used raw here instead of being scaled, that is only done for when a screen is active
		if (!MouseInputEvents.ALLOW_MOUSE_SCROLL.invoker().allowMouseScroll(null, this.xpos, this.ypos, this.slotcycler_horizontalScrollAmount, this.slotcycler_verticalScrollAmount)) {
			this.slotcycler_verticalScrollAmount = null;
			this.slotcycler_horizontalScrollAmount = null;
			callback.cancel();
			return;
		}

		MouseInputEvents.BEFORE_MOUSE_SCROLL.invoker().beforeMouseScroll(null, this.xpos, this.ypos, this.slotcycler_horizontalScrollAmount, this.slotcycler_verticalScrollAmount);
		// injection for after callback is easier just at tail, so store here we were actually in the correct if branch
		this.slotcycler_beforeMouseScroll = true;
	}

	@Inject(method = "onScroll", at = @At("TAIL"))
	private void slotcycler_onScroll$inject$tail(long windowPointer, double xOffset, double yOffset, CallbackInfo callback) {
		// Adapted to fire for the gui instead of a screen
		if (this.minecraft.screen != null || !this.slotcycler_beforeMouseScroll) return;

		MouseInputEvents.AFTER_MOUSE_SCROLL.invoker().afterMouseScroll(null, this.xpos, this.ypos, this.slotcycler_horizontalScrollAmount, this.slotcycler_verticalScrollAmount);
		this.slotcycler_verticalScrollAmount = null;
		this.slotcycler_horizontalScrollAmount = null;
		this.slotcycler_beforeMouseScroll = false;
	}
}
