package txar.tougher_than_adventure.mixins;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import txar.tougher_than_adventure.EnergyBar;
import txar.tougher_than_adventure.EntityPlayerEnergyBar;

@Mixin (
	value = {ItemFood.class},
	remap = false
)
public class ItemFoodMixin extends Item {
	@Shadow
	protected int healAmount;

	public ItemFoodMixin(String name, int id) {
		super(name, id);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		EnergyBar energyBar = ((EntityPlayerEnergyBar) entityplayer).getEnergyBar();
		double hunger = energyBar.hunger;
		if (hunger < energyBar.maxSegmentEnergy() && itemstack.consumeItem(entityplayer)) {
			((EntityPlayerEnergyBar) entityplayer).setHunger(hunger + healAmount);
			((EntityPlayerEnergyBar) entityplayer).setThirst(energyBar.thirst + (healAmount * 0.4f));
		}

		return itemstack;
	}
}
