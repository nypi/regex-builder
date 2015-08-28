package io.neuropop.util.regex;

import io.neuropop.util.Preconditions;

// protected
class CharacterClassAlias implements CharacterClass {
	private final String alias;
	
	protected CharacterClassAlias(String alias) {
		Preconditions.checkNotEmpty(alias);
		
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
