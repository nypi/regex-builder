package io.neuropop.util.regex;

import io.neuropop.util.Preconditions;

// protected
abstract class LogicalCharacterClass implements CharacterClass {
	
	@Override
	public String regex() {
		return new StringBuilder()
				.append("[")
				.append(decl())
				.append("]")
				.toString();
	}
	
	/* Negation */
	
	static class Negation extends LogicalCharacterClass {
		private final CharacterClass x;
		
		public Negation(CharacterClass x) {
			Preconditions.checkNotNull(x);
			Preconditions.checkArgument(!(x instanceof LogicalCharacterClass));
			
			this.x = x;
		}

		@Override
		public String decl() {
			StringBuilder builder = new StringBuilder();
			builder.append("^");
			if (x instanceof LogicalCharacterClass
					&& !(x instanceof Union))
				builder.append("[").append(x.decl()).append("]");
			else
				builder.append(x.decl());
			return builder.toString();
		}

		@Override
		public CharacterClass not() {
			// not(not(x)) = x
			return x;
		}
	}
	
	/* Union */
	
	static class Union extends LogicalCharacterClass {
		private final CharacterClass x;
		private final CharacterClass y;
		
		public Union(CharacterClass x, CharacterClass y) {
			Preconditions.checkNotNull(x);
			Preconditions.checkNotNull(y);
			
			this.x = x;
			this.y = y;
		}
		
		@Override
		public String decl() {
			StringBuilder builder = new StringBuilder();
			if (x instanceof LogicalCharacterClass
					&& !(x instanceof Union))
				builder.append("[").append(x.decl()).append("]");
			else
				builder.append(x.decl());
			if (y instanceof LogicalCharacterClass
					&& !(y instanceof Union))
				builder.append("[").append(y.decl()).append("]");
			else
				builder.append(y.decl());
			return builder.toString();
		}
		
		@Override
		public CharacterClass not() {
			// not(x or y) = not(x) and not(y)
			return x.not().intersection(y.not());
		}
	}
	
	/* Intersection */
	
	static class Intersection extends LogicalCharacterClass {
		private final CharacterClass x;
		private final CharacterClass y;
		
		public Intersection(CharacterClass x, CharacterClass y) {
			Preconditions.checkNotNull(x);
			Preconditions.checkNotNull(y);
			
			this.x = x;
			this.y = y;
		}
		
		@Override
		public String decl() {
			StringBuilder builder = new StringBuilder();
			// TODO brackets on the left-hand negation 
			// can be omitted, if it's a first statement
			// in a character class clause
			if (x instanceof Negation)
				builder.append("[").append(x.decl()).append("]");
			else
				builder.append(x.decl());
			builder.append("&&");
			// right-hand negation should be always put into brackets
			if (y instanceof Negation)
				builder.append("[").append(y.decl()).append("]");
			else
				builder.append(y.decl());
			return builder.toString();
		}
		
		@Override
		public CharacterClass not() {
			// not(x and y) = not(x) or not(y)
			return x.not().union(y.not());
		}
	}
}
