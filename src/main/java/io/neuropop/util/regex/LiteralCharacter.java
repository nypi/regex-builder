package io.neuropop.util.regex;

public class LiteralCharacter implements CharacterClass {
	private final char c;
	
	public LiteralCharacter(char c) {
		this.c = c;
	}
	
	@Override
	public String decl() {
		return Literals.escape(c);
	}
	
	@Override
	public String regex() {
		return decl();
	}
}
