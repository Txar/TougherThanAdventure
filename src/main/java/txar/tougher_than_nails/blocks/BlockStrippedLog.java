package txar.tougher_than_nails.blocks;

import net.minecraft.core.achievement.stat.StatList;
import net.minecraft.core.block.BlockLog;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

public class BlockStrippedLog extends BlockLog {
	public BlockStrippedLog(String key, int id) {
		super(key, id);
	}

	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta, TileEntity tileEntity) {
		entityplayer.addStat(StatList.mineBlockStatArray[this.id], 1);
		ItemStack heldItemStack = entityplayer.inventory.getCurrentItem();
		Item heldItem = heldItemStack != null ? Item.itemsList[heldItemStack.itemID] : null;

		if (heldItem == null) {
			this.dropBlockWithCause(world, EnumDropCause.IMPROPER_TOOL, x, y, z, meta, tileEntity);
			entityplayer.hurt(entityplayer, 2, DamageType.COMBAT);
		} else if (heldItem.canHarvestBlock(this)) {
			this.dropBlockWithCause(world, EnumDropCause.PROPER_TOOL, x, y, z, meta, tileEntity);
		} else {
			this.dropBlockWithCause(world, EnumDropCause.IMPROPER_TOOL, x, y, z, meta, tileEntity);
			entityplayer.hurt(entityplayer, 2, DamageType.COMBAT);
		}
	}

	public void dropBlockWithCause(World world, EnumDropCause cause, int x, int y, int z, int meta, TileEntity tileEntity) {
		if (!world.isClientSide) {
			if (cause == EnumDropCause.PROPER_TOOL || cause == EnumDropCause.PICK_BLOCK) {
				ItemStack[] drops = this.getBreakResult(world, cause, x, y, z, meta, tileEntity);
				if (drops != null && drops.length > 0) {
					ItemStack[] var9 = drops;
					int var10 = drops.length;

					for (int var11 = 0; var11 < var10; ++var11) {
						ItemStack drop = var9[var11];
						if (drop != null) {
							if (EntityItem.enableItemClumping) {
								world.dropItem(x, y, z, drop.copy());
							} else {
								for (int i = 0; i < drop.stackSize; ++i) {
									ItemStack drop1 = drop.copy();
									drop1.stackSize = 1;
									world.dropItem(x, y, z, drop1);
								}
							}
						}
					}
				}
			} else if (cause == EnumDropCause.IMPROPER_TOOL) {
				world.setBlock(x, y, z, this.id);
			}
		}
	}
}
