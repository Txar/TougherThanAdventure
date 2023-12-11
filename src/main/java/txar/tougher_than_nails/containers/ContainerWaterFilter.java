package txar.tougher_than_nails.containers;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import txar.tougher_than_nails.entities.TileEntityWaterFilter;

import java.util.List;

public class ContainerWaterFilter extends Container {
	TileEntityWaterFilter tileEntity;
	public ContainerWaterFilter(IInventory inventory, TileEntityWaterFilter tileEntityWaterFilter) {
		this.tileEntity = tileEntityWaterFilter;

		this.addSlot(new Slot(tileEntityWaterFilter, 0, 79, 12));
		this.addSlot(new Slot(tileEntityWaterFilter, 1, 79, 69));

		int j;
		for(j = 0; j < 3; ++j) {
			for(int k = 0; k < 9; ++k) {
				this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 100 + j * 18));
			}
		}

		for(j = 0; j < 9; ++j) {
			this.addSlot(new Slot(inventory, j, 8 + j * 18, 158));
		}
	}

	@Override
	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return null;
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction action, Slot slot, int target, EntityPlayer entityPlayer) {
		if (slot.id >= 2 && slot.id < 39) {
			if (action != InventoryAction.MOVE_ALL) {
				if (target == 1) {
					return this.getSlots(0, 1, false);
				}

				if (target == 2) {
					return this.getSlots(1, 1, false);
				}
			}

			if (slot.id >= 3 && slot.id <= 29) {
				return this.getSlots(30, 9, false);
			}

			if (slot.id >= 31 && slot.id <= 38) {
				return this.getSlots(3, 27, false);
			}
		}

		if (slot.id >= 0 && slot.id < 2) {
			return slot.id == 2 ? this.getSlots(3, 36, true) : this.getSlots(3, 36, false);
		} else {
			return null;
		}
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		return false;
	}
}
