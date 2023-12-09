package txar.tougher_than_adventure.gui;

import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.block.entity.TileEntityBlastFurnace;
import net.minecraft.core.block.entity.TileEntityFurnace;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.ContainerFurnace;
import net.minecraft.core.player.inventory.InventoryPlayer;
import txar.tougher_than_adventure.containers.ContainerWaterFilter;
import txar.tougher_than_adventure.entities.TileEntityWaterFilter;

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
