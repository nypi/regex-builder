package io.neuropop.util.regex;

public class CharacterRange implements CharacterClass {
	private final char min;
	private final char max;
	
	public CharacterRange(char min, char max) {
		if (max < min)
			throw new IllegalArgumentException();

		this.min = min;
		this.max = max;
	}

	@Override
	public String decl() {
		return new StringBuilder()
				.append(Literals.escape(min))
				.append("-")
				.append(Literals.escape(max))
				.toString();
	}
}
