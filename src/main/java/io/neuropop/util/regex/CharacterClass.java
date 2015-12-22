package io.neuropop.util.regex;

public interface CharacterClass extends Expression {
	String decl();

	default String regex() {
		return new StringBuilder()
				.append("[")
				.append(decl())
				.append("]")
				.toString();
	}

	/* logical operators */
	
	default CharacterClass not() {
		return new LogicalCharacterClass.Negation(this);
	}
	
	default CharacterClass union(CharacterClass y) {
		// optimization: using sets for the non-logical
		// character classes
		if (!(this instanceof LogicalCharacterClass)
				&& !(y instanceof LogicalCharacterClass))
			return new CharacterClassSet(this, y);
		// default
		return new LogicalCharacterClass.Union(this, y);
	}
	
	default CharacterClass intersection(CharacterClass y) {
		// default
		return new LogicalCharacterClass.Intersection(this, y);
	}
}
