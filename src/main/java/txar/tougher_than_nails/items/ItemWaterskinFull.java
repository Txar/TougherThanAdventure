package txar.tougher_than_nails.items;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import txar.tougher_than_nails.EnergyBar;
import txar.tougher_than_nails.EntityPlayerEnergyBar;
import txar.tougher_than_nails.TougherThanAdventure;

public class ItemWaterskinFull extends ItemWaterskinEmpty {
	public int capacity;
	public int currentCapacity;
	public int healAmount;
	public ItemWaterskinFull(String name, int id, int healAmount, int capacity) {
		super(name, id);
		this.capacity = capacity;
		this.currentCapacity = capacity;
		this.healAmount = healAmount;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		ItemStack s = super.onItemRightClick(itemstack, world, entityplayer);
		if (s == null) {
			currentCapacity = capacity;
		} else {
			EnergyBar energyBar = ((EntityPlayerEnergyBar) entityplayer).getEnergyBar();
			if (energyBar.thirst < energyBar.maxSegmentEnergy() && itemstack.consumeItem(entityplayer)) {
				((EntityPlayerEnergyBar) entityplayer).setThirst(energyBar.thirst + healAmount);
			}
			currentCapacity--;
		}

		if (currentCapacity <= 0) {
			return new ItemStack(TougherThanAdventure.itemSmallWaterskinEmpty);
		} else {
			return new ItemStack(this);
		}
	}
}
