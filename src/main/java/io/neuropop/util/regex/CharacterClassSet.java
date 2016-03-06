package io.neuropop.util.regex;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class CharacterClassSet implements CharacterClass {
	private final Set<CharacterClass> classes;
	
	public CharacterClassSet(char... characters) {
		Objects.requireNonNull(characters);
		if (characters.length == 0)
			throw new IllegalArgumentException();

		this.classes = new LinkedHashSet<>();
		for (char c : characters)
			this.classes.add(new LiteralCharacter(c));
	}
	
	public CharacterClassSet(CharacterClass... classes) {
		Objects.requireNonNull(classes);
		if (classes.length == 0)
			throw new IllegalArgumentException();

		this.classes = new LinkedHashSet<>();
		for (CharacterClass c : classes) {
			if (c == null)
				continue;
			if (c instanceof LogicalCharacterClass)
				throw new IllegalArgumentException("Illegal use of a logical character class");
			if (c instanceof CharacterClassSet) {
				this.classes.addAll(((CharacterClassSet) c).classes);
			} else {
				this.classes.add(c);
			}
		}
		if (this.classes.isEmpty())
			throw new IllegalStateException("Empty set");
	}

	@Override
	public String decl() {
		StringBuilder builder = new StringBuilder();
		for (CharacterClass c : classes)
			builder.append(c.decl());
		return builder.toString();
	}
}
