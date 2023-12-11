package txar.tougher_than_nails.blocks;

import net.minecraft.core.block.BlockFlower;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.world.World;
import txar.tougher_than_nails.TougherThanAdventure;

import java.util.Random;

public class BlockBlueberryBush extends BlockFlower {
	public int initialGrowthTime;
	public int growthTimeWithFluctuation;
	public int fluctuation;
	public int growthProgress;

	public BlockBlueberryBush(String key, int id, int initialGrowthTime, int fluctuation) {
		super(key, id);
		this.initialGrowthTime = initialGrowthTime;
		this.fluctuation = fluctuation;
		this.growthProgress = 0;
		this.growthTimeWithFluctuation = 0;
	}

	public boolean growthCompleted() { return growthProgress >= growthTimeWithFluctuation;}

	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (world.seasonManager.getCurrentSeason().getId() != "overworld.summer") {
			growthProgress = 0;
		} else {
			if (growthProgress < growthTimeWithFluctuation) growthProgress++;
		}
	}

	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		if (growthCompleted()) {
			world.dropItem(x, y, z, new ItemStack(TougherThanAdventure.itemBlueberry, 2));
			world.playSoundEffect(SoundType.WORLD_SOUNDS, x, y, z, "sound/step/grass1.ogg", 1.0f, 1.0f);
		}
		return false;
	}
}
