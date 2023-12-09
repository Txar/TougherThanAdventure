package txar.tougher_than_adventure.mixins;

import net.minecraft.core.block.BlockTallGrass;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import txar.tougher_than_adventure.TougherThanAdventure;

@Mixin (
	value = {BlockTallGrass.class},
	remap = false
)
public class BlockTallGrassMixin {
	@Inject(
		at = @At("HEAD"),
		method = "getBreakResult",
		cancellable = true
	)
	private void mixin(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfoReturnable<ItemStack[]> cir) {
		if (world.rand.nextFloat() < 0.1f) {
			cir.setReturnValue(new ItemStack[]{new ItemStack(TougherThanAdventure.itemGrassFiber)});
		}
	}
}
