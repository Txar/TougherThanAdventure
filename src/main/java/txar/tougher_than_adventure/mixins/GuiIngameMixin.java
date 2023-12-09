package txar.tougher_than_adventure.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import txar.tougher_than_adventure.EnergyBar;
import txar.tougher_than_adventure.EntityPlayerEnergyBar;

@Mixin (
	value = {GuiIngame.class},
	remap = false
)
public class GuiIngameMixin extends Gui {
	@Shadow
	protected Minecraft mc;

	@Inject (
		at = @At("TAIL"),
		method = "renderGameOverlay"
	)
	public void renderEnergyBar(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci) {


		int width = this.mc.resolution.scaledWidth;
		int height = this.mc.resolution.scaledHeight;
		int sp = (int)(this.mc.gameSettings.screenPadding.value * (float)height / 8.0F);

		if (this.mc.gameSettings.immersiveMode.drawHotbar()) {
			GL11.glEnable(3042);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glBindTexture(3553, this.mc.renderEngine.getTexture("/assets/tougher_than_adventure/gui/energy_bar.png"));

			int energyBarX = (width / 2) - 91;
			int energyBarY = height - 22 - sp - 20;

			this.drawTexturedModalRect(energyBarX, energyBarY, 0, 0, 182, 8);

			try {
				EnergyBar energyBar = ((EntityPlayerEnergyBar) this.mc.thePlayer).getEnergyBar();

				int segmentWidth = (int) Math.floor(energyBar.thirst);
				int segmentPosition = energyBarX + 1;
				this.drawTexturedModalRect(segmentPosition, energyBarY, 0, 16, segmentWidth, 8);

				segmentPosition += segmentWidth;
				segmentWidth = (int) Math.floor(energyBar.hunger);
				this.drawTexturedModalRect(segmentPosition, energyBarY, 0, 23, segmentWidth, 8);

				segmentPosition += segmentWidth;
				segmentWidth = (int) Math.floor(energyBar.temperature);
				this.drawTexturedModalRect(segmentPosition, energyBarY, 0, 30, segmentWidth, 8);

				segmentPosition += segmentWidth;
				segmentWidth = (int) Math.floor(energyBar.rest);
				this.drawTexturedModalRect(segmentPosition, energyBarY, 0, 37, segmentWidth, 8);
			} catch (Exception e) {
				System.out.println(e);
			}

			this.drawTexturedModalRect(energyBarX, energyBarY, 0, 8, 182, 8);
			GL11.glDisable(3042);
		}
	}
}
