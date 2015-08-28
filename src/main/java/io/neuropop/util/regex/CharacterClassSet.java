package io.neuropop.util.regex;

import io.neuropop.util.Preconditions;

import java.util.LinkedHashSet;
import java.util.Set;

public class CharacterClassSet implements CharacterClass {
	private final Set<CharacterClass> classes;
	
	public CharacterClassSet(char... characters) {
		Preconditions.checkNotNull(characters);
		Preconditions.checkArgument(characters.length > 0);

		Set<Character> set = new LinkedHashSet<>();
		for (char c : characters)
			set.add(c);
		this.classes = new LinkedHashSet<>();
		for (char c : set)
			this.classes.add(new LiteralCharacter(c));
	}
	
	public CharacterClassSet(CharacterClass... classes) {
		Preconditions.checkNotNull(classes);
		Preconditions.checkArgument(classes.length > 0);
		
		this.classes = new LinkedHashSet<>();
		for (CharacterClass c : classes) {
			if (c == null)
				throw new IllegalArgumentException("classes");
			if (c instanceof LogicalCharacterClass)
				throw new IllegalArgumentException("classes");
			if (c instanceof CharacterClassSet) {
				this.classes.addAll(((CharacterClassSet) c).classes);
			} else {
				this.classes.add(c);
			}
		}
	}
	
	@Override
	public String regex() {
		return new StringBuilder()
				.append("[")
				.append(decl())
				.append("]")
				.toString();
	}

	@Override
	public String decl() {
		StringBuilder builder = new StringBuilder();
		for (CharacterClass c : classes)
			builder.append(c.decl());
		return builder.toString();
	}
}
