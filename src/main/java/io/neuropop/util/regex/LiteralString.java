package io.neuropop.util.regex;

import java.util.Objects;

public class LiteralString implements Expression {
	private final String str;
	
	public LiteralString(String str) {
		if (str == null || str.isEmpty())
			throw new IllegalArgumentException();

		this.str = str;
	}

	public LiteralString(char... characters) {
		Objects.requireNonNull(characters);
		if (characters.length == 0)
			throw new IllegalArgumentException();

		this.str = new String(characters);
	}

	@Override
	public String regex() {
		return Literals.escape(str);
	}
}
