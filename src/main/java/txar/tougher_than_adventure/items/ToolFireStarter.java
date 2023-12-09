package txar.tougher_than_adventure.items;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

import java.util.Random;

public class ToolFireStarter extends Item {
	private float chanceOfFire;

	public ToolFireStarter(String name, int id, int durability, float chanceOfFire) {
		super(name, id);
		this.maxStackSize = 1;
		this.chanceOfFire = chanceOfFire;
		this.setMaxDamage(durability);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
		blockX += side.getOffsetX();
		blockY += side.getOffsetY();
		blockZ += side.getOffsetZ();

		int i1 = world.getBlockId(blockX, blockY, blockZ);
		if (i1 == 0) {
			boolean success = world.rand.nextFloat() <= chanceOfFire;

			world.playSoundEffect(SoundType.WORLD_SOUNDS, (double) blockX + 0.5, (double) blockY + 0.5, (double) blockZ + 0.5, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
			itemstack.damageItem(1, entityplayer);

			if (success) {
				if (world.setBlockWithNotify(blockX, blockY, blockZ, Block.fire.id)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
