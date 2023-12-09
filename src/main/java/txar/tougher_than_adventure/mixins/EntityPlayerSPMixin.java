package txar.tougher_than_adventure.mixins;


import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.GuiFurnace;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import txar.tougher_than_adventure.entities.TileEntityWaterFilter;
import txar.tougher_than_adventure.gui.GuiWaterFilter;

@Mixin (
	value = {EntityPlayerSP.class},
	remap = false
)
public abstract class EntityPlayerSPMixin extends EntityPlayerMixin {
	@Shadow
	protected Minecraft mc;
	@Shadow
	public InventoryPlayer inventory;

	public EntityPlayerSPMixin(World world) {
		super(world);
	}

	public void displayGUIWaterFilter(TileEntityWaterFilter tileEntityWaterFilter) {
		this.mc.displayGuiScreen(new GuiWaterFilter(this.inventory, tileEntityWaterFilter));
	}
}
