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

	/* equality */

	@Override
	public int hashCode() {
		return (int) c;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof LiteralCharacter))
			return false;

		LiteralCharacter literalCharacter = (LiteralCharacter) other;
		return c == literalCharacter.c;
	}
}
