package txar.tougher_than_adventure.mixins;

import net.minecraft.core.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import txar.tougher_than_adventure.ItemAccessor;

@Mixin (
	value = {Item.class},
	remap = false
)
public class ItemMixin implements ItemAccessor {
	@Unique
	public boolean isAFlintTool() {
		return false;
	}
}
