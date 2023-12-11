package txar.tougher_than_nails.mixins;

import net.minecraft.core.crafting.CraftingManager;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin (
	value = CraftingManager.class,
	remap = false
)
public interface TougherCraftingManagerAccessor {
	@Invoker("addRecipe")
	void callAddRecipeWithArgs(ItemStack itemstack, boolean useAlternatives, boolean countForMetadata, boolean consumeContainerItem, Object[] aobj);
}
