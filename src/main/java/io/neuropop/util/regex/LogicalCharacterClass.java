package io.neuropop.util.regex;

import java.util.Objects;

// package-private
abstract class LogicalCharacterClass implements CharacterClass {
	
	/* Negation */
	
	static class Negation extends LogicalCharacterClass {
		private final CharacterClass x;
		
		public Negation(CharacterClass x) {
			Objects.requireNonNull(x);
			if (x instanceof LogicalCharacterClass)
				throw new IllegalArgumentException("Cannot negate a logical character class");

			this.x = x;
		}

		@Override
		public String decl() {
			return new StringBuilder()
					.append("^")
					.append(x.decl())
					.toString();
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
			Objects.requireNonNull(x);
			Objects.requireNonNull(y);

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
			Objects.requireNonNull(x);
			Objects.requireNonNull(y);

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
