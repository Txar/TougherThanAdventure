package txar.tougher_than_adventure.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFurnace;
import net.minecraft.core.block.entity.TileEntityFurnace;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;

import java.util.Random;

public class BlockWaterFilter extends BlockFurnace {

	public BlockWaterFilter(String key, int id, boolean flag) {
		super(key, id, flag);
	}

	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		if (this.isActive) {
			int l = world.getBlockMetadata(x, y, z);
			float f = (float)x + 0.5F;
			float f1 = (float)y + 0.0F + rand.nextFloat() * 6.0F / 16.0F;
			float f2 = (float)z + 0.5F;
			float f3 = 0.52F;
			float f4 = rand.nextFloat() * 0.6F - 0.3F;
			if (l == 4) {
				world.spawnParticle("water", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0, 0.0, 0.0);
			} else if (l == 5) {
				world.spawnParticle("water", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0, 0.0, 0.0);
			} else if (l == 2) {
				world.spawnParticle("water", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0, 0.0, 0.0);
			} else if (l == 3) {
				world.spawnParticle("water", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0, 0.0, 0.0);
			}

		}
	}

	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		if (!world.isClientSide) {
			TileEntityFurnace tileentityfurnace = (TileEntityFurnace)world.getBlockTileEntity(x, y, z);
			player.displayGUIFurnace(tileentityfurnace);
		}

		return true;
	}
}
