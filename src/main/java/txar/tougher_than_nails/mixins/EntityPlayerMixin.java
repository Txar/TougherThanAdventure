package txar.tougher_than_nails.mixins;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import txar.tougher_than_nails.EnergyBar;
import txar.tougher_than_nails.EntityPlayerEnergyBar;
import txar.tougher_than_nails.entities.TileEntityWaterFilter;

@Mixin(
	value = {EntityPlayer.class},
	remap = false
)
public abstract class EntityPlayerMixin extends EntityLiving implements EntityPlayerEnergyBar {
	@Shadow
	protected abstract void damageEntity(int damage, DamageType damageType);

	@Shadow
	public abstract boolean hurt(Entity entity, int damage, DamageType type);

	@Shadow
	protected abstract void joinEntityItemWithWorld(EntityItem entityitem);

	@Unique
	public EnergyBar energyBar;

	@Unique
	public int ticksSinceEnergyHeal = 0;

	public EnergyBar getEnergyBar() {
		if (this.energyBar == null) {
			energyBar = new EnergyBar(180, 60, 121, 144);
		}
		return this.energyBar;
	}

	double capAt(double value, double cap) {
		return (value > cap) ? cap : value;
	}

	public double getThirst() { return energyBar.thirst; }
	public double getHunger() { return energyBar.hunger; }
	public double getTemperature() { return energyBar.temperature; }
	public double getRest() { return energyBar.rest; }

	public void setThirst(double value) { energyBar.thirst = capAt(value, energyBar.maxSegmentEnergy()); }
	public void setHunger(double value) { energyBar.hunger = capAt(value, energyBar.maxSegmentEnergy()); }
	public void setTemperature(double value) { energyBar.temperature = capAt(value, energyBar.maxSegmentEnergy()); }
	public void setRest(double value) { energyBar.rest = capAt(value, energyBar.maxSegmentEnergy()); }

	public void consumeEnergy(double amount) { energyBar.consumeEnergy(amount); }

	public EntityPlayerMixin(World world) {
		super(world);
		energyBar = new EnergyBar(180, 59, 121, 143);
	}

	public void displayGUIWaterFilter(TileEntityWaterFilter tileEntityWaterFilter) {}

	private void temperatureTick() {
		switch (this.world.seasonManager.getCurrentSeason().getId()) {
			case ("overworld.spring"):
				setTemperature(energyBar.maxSegmentEnergy() * 0.8f);
				break;
			case ("overworld.summer"):
				setTemperature(energyBar.maxSegmentEnergy() * 1.0f);
				break;
			case ("overworld.fall"):
				setTemperature(energyBar.maxSegmentEnergy() * 0.6f);
				break;
			case ("overworld.winter"):
				setTemperature(energyBar.maxSegmentEnergy() * 0.4f);
				break;
			default:
				setTemperature(energyBar.maxSegmentEnergy() * 0.78f);
				break;
		}

		if (this.world.getBlockBiome((int) this.x, (int) this.y, (int) this.z).hasSurfaceSnow()) {
			if (this.world.isDaytime()) {
				setTemperature(getTemperature());
			} else {
				setTemperature(getTemperature() * 0.75f);
			}

			setTemperature(getTemperature() * 0.5f);

		} else if (this.world.getBlockBiome((int) this.x, (int) this.y, (int) this.z).topBlock == Block.sand.id) {
			if (this.world.isDaytime()) {
				setTemperature(getTemperature() * 1.4f);
			} else {
				setTemperature(getTemperature() * 0.9f);
			}
		} else {
			if (this.world.isDaytime()) {
				setTemperature(getTemperature());
			} else {
				setTemperature(getTemperature() * 0.75f);
			}
		}
	}

	@Inject (
		method = "tick()V",
		at = @At("HEAD")
	)
	private void tick(CallbackInfo c) {
		if (!world.isClientSide) {
			if (energyBar == null) {
				energyBar = new EnergyBar(180, 60, 121, 144);
			}

			ticksSinceEnergyHeal++;
			if (ticksSinceEnergyHeal >= 40) {
				ticksSinceEnergyHeal = 0;
				switch (energyBar.getEnergyLevel()) {
					case GOOD:
						if (health < 20) {
							heal(1);
							consumeEnergy(6);
						}
						break;
					case OKAY:
						break;
					case BAD:
						hurt(this, 1, DamageType.GENERIC);
						break;
					case TERRIBLE:
						hurt(this, 2, DamageType.GENERIC);
						break;
				}
			}

			temperatureTick();
		}
	}

	@Inject (
		method = "addMovementStat",
		at = @At("HEAD")
	)
	private void addMovementStat(double d, double d1, double d2, CallbackInfo ci) {
		if (!world.isClientSide) {
			if (energyBar == null) energyBar = new EnergyBar(180, 60, 121, 144);
			if (this.vehicle == null) {
				int k = Math.round(MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2) * 100.0F);
				if (k > 0) {
					consumeEnergy(0.004);
				} else {
					consumeEnergy(0.002);
					setRest(getRest() + 0.025);
				}
			}
		}
	}

	@Inject (
		method = "addAdditionalSaveData",
		at = @At("HEAD")
	)
	private void addAdditionalSaveDataMixin(CompoundTag tag, CallbackInfo ci) {
		if (energyBar == null) {
			energyBar = new EnergyBar(180, 60, 121, 144);
		}
		tag.putByteArray("EnergyBar", energyBar.getBytes());
	}

	@Inject (
		method = "readAdditionalSaveData",
		at = @At("HEAD")
	)
	private void readAdditionalSaveDataMixin(CompoundTag tag, CallbackInfo ci) {
		this.energyBar = new EnergyBar(tag.getByteArray("EnergyBar"));
	}
}
