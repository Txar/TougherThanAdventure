package txar.tougher_than_nails;

public class EnergyBar {
	public int maxEnergy;

	public double thirst;
	public double hunger;
	public double temperature;
	public double rest;
	public int minBadLevel;
	public int minOkayLevel;
	public int minGoodLevel;

	public enum EnergyLevel {
		GOOD,
		OKAY,
		BAD,
		TERRIBLE
	}

	public EnergyBar(int maxEnergy, int minBadLevel, int minOkayLevel, int minGoodLevel) {
		this.maxEnergy = maxEnergy;
		this.minBadLevel = minBadLevel;
		this.minOkayLevel = minOkayLevel;
		this.minGoodLevel = minGoodLevel;
		thirst = hunger = temperature = rest = (int) maxSegmentEnergy();
	}

	public float maxSegmentEnergy() {
		return maxEnergy / 4.0f;
	}

	public double getCurrentEnergy() {
		return thirst + hunger + temperature + rest;
	}

	public float getCurrentEnergyPercentage(){
		return (float) getCurrentEnergy() / maxEnergy;
	}

	public void consumeEnergy(double amount) {
		double actualAmount = amount / 6.0f;
		thirst -= actualAmount * 2.0f;
		hunger -= actualAmount;
		rest -= actualAmount * 3.0f;
	}

	public EnergyLevel getEnergyLevel() {
		double energy = getCurrentEnergy();
		if (energy >= this.minGoodLevel) {
			return EnergyLevel.GOOD;
		} else if (energy >= this.minOkayLevel) {
			return EnergyLevel.OKAY;
		} else if (energy >= this.minBadLevel) {
			return EnergyLevel.BAD;
		} else {
			return EnergyLevel.TERRIBLE;
		}
	}

	private static byte[] intToByteArray(int value) {
		return new byte[] {
			(byte)(value >>> 24),
			(byte)(value >>> 16),
			(byte)(value >>> 8),
			(byte)value
		};
	}

	int intFromByteArray(byte[] bytes) {
		return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
	}

	public byte[] getBytes() {
		byte[] bytes = new byte[8 * 4];

		byte[] tempByteArray;

		tempByteArray = intToByteArray(maxEnergy);
		int start = 0;
		for (int i = 0; i < 4; i++) bytes[start + i] = tempByteArray[i];

		tempByteArray = intToByteArray(minBadLevel);
		start = 4;
		for (int i = 0; i < 4; i++) bytes[start + i] = tempByteArray[i];

		tempByteArray = intToByteArray(minOkayLevel);
		start = 8;
		for (int i = 0; i < 4; i++) bytes[start + i] = tempByteArray[i];

		tempByteArray = intToByteArray(minGoodLevel);
		start = 12;
		for (int i = 0; i < 4; i++) bytes[start + i] = tempByteArray[i];

		tempByteArray = intToByteArray((int) thirst);
		start = 16;
		for (int i = 0; i < 4; i++) bytes[start + i] = tempByteArray[i];

		tempByteArray = intToByteArray((int) hunger);
		start = 20;
		for (int i = 0; i < 4; i++) bytes[start + i] = tempByteArray[i];

		tempByteArray = intToByteArray((int) temperature);
		start = 24;
		for (int i = 0; i < 4; i++) bytes[start + i] = tempByteArray[i];

		tempByteArray = intToByteArray((int) rest);
		start = 28;
		for (int i = 0; i < 4; i++) bytes[start + i] = tempByteArray[i];

		return bytes;
	}

	public EnergyBar(byte[] bytes) {
		byte[] tempByteArray = new byte[4];

		int start = 0;
		for (int i = 0; i < 4; i++) tempByteArray[i] = bytes[start + i];
		maxEnergy = intFromByteArray(tempByteArray);

		start = 4;
		for (int i = 0; i < 4; i++) tempByteArray[i] = bytes[start + i];
		minBadLevel = intFromByteArray(tempByteArray);

		start = 8;
		for (int i = 0; i < 4; i++) tempByteArray[i] = bytes[start + i];
		minOkayLevel = intFromByteArray(tempByteArray);

		start = 12;
		for (int i = 0; i < 4; i++) tempByteArray[i] = bytes[start + i];
		minGoodLevel = intFromByteArray(tempByteArray);

		start = 16;
		for (int i = 0; i < 4; i++) tempByteArray[i] = bytes[start + i];
		thirst = intFromByteArray(tempByteArray);

		start = 20;
		for (int i = 0; i < 4; i++) tempByteArray[i] = bytes[start + i];
		hunger = intFromByteArray(tempByteArray);

		start = 24;
		for (int i = 0; i < 4; i++) tempByteArray[i] = bytes[start + i];
		temperature = intFromByteArray(tempByteArray);

		start = 28;
		for (int i = 0; i < 4; i++) tempByteArray[i] = bytes[start + i];
		rest = intFromByteArray(tempByteArray);

		System.out.println(maxEnergy);
	}
}
