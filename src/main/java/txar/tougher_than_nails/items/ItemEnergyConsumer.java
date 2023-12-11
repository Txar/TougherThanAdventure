package txar.tougher_than_nails.items;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import txar.tougher_than_nails.EntityPlayerEnergyBar;

public class ItemEnergyConsumer extends ItemFood {
	public ItemEnergyConsumer(String name, int id, int healAmount, boolean favouriteWolfMeat) {
		super(name, id, healAmount, favouriteWolfMeat);
		setMaxStackSize(64);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (itemstack.consumeItem(entityplayer)) {
			((EntityPlayerEnergyBar) entityplayer).consumeEnergy(healAmount);
		}

		return itemstack;
	}
}
