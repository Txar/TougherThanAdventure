package txar.tougher_than_adventure;

public interface EntityPlayerEnergyBar {
	EnergyBar getEnergyBar();

	double getThirst();
	double getHunger();
	double getTemperature();
	double getRest();

	void setThirst(double value);
	void setHunger(double value);
	void setTemperature(double value);
	void setRest(double value);

	void consumeEnergy(double amount);
}
