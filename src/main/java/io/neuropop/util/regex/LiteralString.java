package io.neuropop.util.regex;

import io.neuropop.util.Preconditions;

public class LiteralString implements Expression {
	private final String str;
	
	public LiteralString(String str) {
		Preconditions.checkNotEmpty(str);
		
		this.str = str;
	}
	
	@Override
	public String regex() {
		return Literals.escape(str);
	}
}
