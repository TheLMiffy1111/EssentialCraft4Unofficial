package essentialcraft.api;

import net.minecraft.util.IStringSerializable;

public enum EnumMatrixType implements IStringSerializable {

	EMPTY("empty"),
	CHAOS("chaos"),
	FROZEN("frozen"),
	MAGIC("magic"),
	SHADE("shade");

	private int index;
	private String name;

	private EnumMatrixType(String s) {
		index = ordinal();
		name = s;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public int getIndex() {
		return index;
	}

	public static EnumMatrixType fromIndex(int i) {
		return values()[i];
	}
}
