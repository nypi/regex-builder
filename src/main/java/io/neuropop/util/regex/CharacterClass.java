package io.neuropop.util.regex;

public interface CharacterClass extends Expression {
	String decl();
	
	/* logical operators */
	
	default CharacterClass not() {
		return new LogicalCharacterClass.Negation(this);
	}
	
	default CharacterClass union(CharacterClass y) {
		if (!(this instanceof LogicalCharacterClass)
				&& !(y instanceof LogicalCharacterClass))
			return new CharacterClassSet(this, y);
		else
			return new LogicalCharacterClass.Union(this, y);
	}
	
	default CharacterClass intersection(CharacterClass y) {
		return new LogicalCharacterClass.Intersection(this, y);
	}
}
