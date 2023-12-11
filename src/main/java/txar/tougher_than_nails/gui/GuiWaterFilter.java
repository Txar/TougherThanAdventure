package txar.tougher_than_nails.gui;

import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.player.inventory.InventoryPlayer;
import txar.tougher_than_nails.containers.ContainerWaterFilter;
import txar.tougher_than_nails.entities.TileEntityWaterFilter;

public class GuiWaterFilter extends GuiContainer {
	private TileEntityWaterFilter waterFilterInventory;
	public GuiWaterFilter(InventoryPlayer inventoryplayer, TileEntityWaterFilter tileEntityWaterFilter) {
		super(new ContainerWaterFilter(inventoryplayer, tileEntityWaterFilter));
		this.waterFilterInventory = tileEntityWaterFilter;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		this.fontRenderer.drawString("Water Filter", 60, 6, 4210752);
		this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
	}
}
