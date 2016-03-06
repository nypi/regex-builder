package io.neuropop.util.regex;

// package-private
class CharacterClassAlias implements CharacterClass {
	private final String alias;
	
	protected CharacterClassAlias(String alias) {
		if (alias == null || alias.isEmpty())
			throw new IllegalArgumentException();

		this.alias = alias;
	}
	
	@Override
	public String decl() {
		return alias;
	}
	
	@Override
	public String regex() {
		return decl();
	}
}
