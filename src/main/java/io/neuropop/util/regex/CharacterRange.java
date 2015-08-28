package io.neuropop.util.regex;

import io.neuropop.util.Preconditions;

public class CharacterRange implements CharacterClass {
	private final char min;
	private final char max;
	
	public CharacterRange(char min, char max) {
		Preconditions.checkArgument(min <= max);
		
		this.min = min;
		this.max = max;
	}

	@Override
	public String decl() {
		StringBuilder builder = new StringBuilder();
		Literals.appendEscaped(builder, min);
		builder.append("-");
		Literals.appendEscaped(builder, max);
		return builder.toString();
	}

	@Override
	public String regex() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		Literals.appendEscaped(builder, min);
		builder.append("-");
		Literals.appendEscaped(builder, max);
		builder.append("]");
		return builder.toString();
	}
}
