package txar.tougher_than_adventure.items;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;

public class ToolFlint extends Item {
	private final int damageVsEntity;
	private int damaged;

	public ToolFlint withDamage(int damage) {
		ToolFlint t = this;
		t.damaged = damage;
		return t;
	}

	public ToolFlint(String name, int id, int durability, int damageDealt) {
		super(name, id);
		this.setMaxDamage(durability);
		this.maxStackSize = 1;
		this.damageVsEntity = damageDealt + 1;
		this.damaged = 0;
		setContainerItem(this);
	}

	public ToolFlint(String name, int id, int durability, int damageDealt, int damaged) {
		super(name, id);
		this.setMaxDamage(durability);
		this.maxStackSize = 1;
		this.damageVsEntity = damageDealt + 1;
		this.damaged = damaged;
		setContainerItem(this.withDamage(1));
	}

	public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
		if (damaged != 0) {
			itemstack.damageItem(damaged, entityliving);
			damaged = 0;
		}
		itemstack.damageItem(1, entityliving1);
		setContainerItem(this.withDamage(itemstack.getMetadata() + 1));
		return true;
	}

	public int getDamageVsEntity(Entity entity) {
		return this.damageVsEntity;
	}

	public boolean onBlockDestroyed(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
		Block block = Block.blocksList[i];
		if (block != null && (block.getHardness() > 0.0F || this.isSilkTouch())) {
			if (damaged != 0) {
				itemstack.damageItem(damaged, entityliving);
				damaged = 0;
			}
			itemstack.damageItem(5, entityliving);
			setContainerItem(this.withDamage(itemstack.getMetadata() + 1));
		}

		return true;
	}

	public boolean canHarvestBlock(Block block) {
		return false;
	}

	public boolean isFull3D() {
		return true;
	}
}
