package io.neuropop.util.regex;

import io.neuropop.util.Preconditions;

public class LiteralString implements Expression {
	private final String str;
	
	public LiteralString(String str) {
		Preconditions.checkNotEmpty(str);

		this.str = str;
	}

	public LiteralString(char... characters) {
		Preconditions.checkNotNull(characters);
		Preconditions.checkArgument(characters.length > 0);

		this.str = new String(characters);
	}

	@Override
	public String regex() {
		return Literals.escape(str);
	}
}
