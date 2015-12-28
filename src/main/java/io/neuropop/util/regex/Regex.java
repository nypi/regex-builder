package io.neuropop.util.regex;

import io.neuropop.util.Preconditions;

public final class Regex {
	private Regex() {
	}
	
	/* logical operators */

	public static Expression concat(Expression... expressions) {
		return and(expressions);
	}
	
	public static Expression and(Expression... expressions) {
		Preconditions.checkNotNull(expressions);
		Preconditions.checkArgument(expressions.length > 0);
		
		Expression x = expressions[0];
		for (int i = 1; i < expressions.length; ++i)
			x = x.and(expressions[i]);
		return x;
	}

	public static Expression any(Expression... expressions) {
		return or(expressions);
	}
	
	public static Expression or(Expression... expressions) {
		Preconditions.checkNotNull(expressions);
		Preconditions.checkArgument(expressions.length > 0);
		
		Expression x = expressions[0];
		for (int i = 1; i < expressions.length; ++i)
			x = x.or(expressions[i]);
		return x;
	}
	
	/* literals */
	
	public static Expression ignoreCase(String str) {
		Preconditions.checkNotEmpty(str);
		
		Expression x = null;
		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			Expression y = ch(Character.toUpperCase(c))
					.or(ch(Character.toLowerCase(c)));
			x = x == null
					? y
					: x.and(y);
		}
		return x;
	}
	
	public static LiteralString str(String str) {
		return new LiteralString(str);
	}

	public static LiteralString str(char... characters) {
		return new LiteralString(characters);
	}

	/* character classes */

	public static LiteralCharacter ch(char c) {
		return new LiteralCharacter(c);
	}

	public static LiteralCharacter tab() {
		return ch((char) 0x09);
	}

	public static LiteralCharacter newLine() {
		return ch((char) 0x0a);
	}

	public static LiteralCharacter carriageReturn() {
		return ch((char) 0x0d);
	}

	public static LiteralCharacter formFeed() {
		return ch((char) 0x0c);
	}

	public static LiteralCharacter alert() {
		return ch((char) 0x07);
	}

	public static LiteralCharacter escape() {
		return ch((char) 0x1b);
	}

	public static CharacterClass oneOf(CharacterClass... classes) {
		return new CharacterClassSet(classes);
	}

	public static CharacterClass oneOf(char... characters) {
		return new CharacterClassSet(characters);
	}

	public static CharacterClass oneOf(String str) {
		Preconditions.checkNotNull(str);
		
		return new CharacterClassSet(str.toCharArray());
	}

	public static CharacterRange range(char min, char max) {
		return new CharacterRange(min, max);
	}

	/* pre-defined character classes */
	
	public static CharacterClass anyCharacter() {
		return new CharacterClassAlias(".");
	}
	
	public static CharacterClass digit() {
		return new CharacterClassAlias("\\d");
	}
	
	public static CharacterClass nonDigit() {
		return new CharacterClassAlias("\\D");
	}
	
	public static CharacterClass whitespace() {
		return new CharacterClassAlias("\\s");
	}
	
	public static CharacterClass nonWhitespace() {
		return new CharacterClassAlias("\\S");
	}
	
	public static CharacterClass word() {
		return new CharacterClassAlias("\\w");
	}
	
	public static CharacterClass nonWord() {
		return new CharacterClassAlias("\\W");
	}
	
	/* POSIX character classes (US-ASCII only) */

	public static final class PosixClasses {
		private PosixClasses() {
		}
		
		public static CharacterClass posixLowerCase() {
			return new CharacterClassAlias("\\p{Lower}");
		}
		
		public static CharacterClass posixUpperCase() {
			return new CharacterClassAlias("\\p{Upper}");
		}
		
		public static CharacterClass posixAscii() {
			return new CharacterClassAlias("\\p{ASCII}");
		}
		
		public static CharacterClass posixAlpha() {
			return new CharacterClassAlias("\\p{Alpha}");
		}
		
		public static CharacterClass posixDigit() {
			return new CharacterClassAlias("\\p{Digit}");
		}
		
		public static CharacterClass posixAlphanumeric() {
			return new CharacterClassAlias("\\p{Alnum}");
		}
		
		public static CharacterClass posixPunctuation() {
			return new CharacterClassAlias("\\p{Punct}");
		}
		
		public static CharacterClass posixVisible() {
			return new CharacterClassAlias("\\p{Graph}");
		}
		
		public static CharacterClass posixPrintable() {
			return new CharacterClassAlias("\\p{Print}");
		}
		
		public static CharacterClass posixSpaceOrTab() {
			return new CharacterClassAlias("\\p{Blank}");
		}
		
		public static CharacterClass posixControl() {
			return new CharacterClassAlias("\\p{Cntrl}");
		}
		
		public static CharacterClass posixHexDigit() {
			return new CharacterClassAlias("\\p{XDigit}");
		}
		
		public static CharacterClass posixWhitespace() {
			return new CharacterClassAlias("\\p{Space}");
		}
	}
	
	/* java.lang.Character classes (simple java character type) */

	public static final class JavaClasses {
		private JavaClasses() {
		}
		
		public static CharacterClass javaLowerCase() {
			return new CharacterClassAlias("\\p{javaLowerCase}");
		}
	
		public static CharacterClass javaUpperCase() {
			return new CharacterClassAlias("\\p{javaUpperCase}");
		}
	
		public static CharacterClass javaWhitespace() {
			return new CharacterClassAlias("\\p{javaWhitespace}");
		}
	
		public static CharacterClass javaMirrored() {
			return new CharacterClassAlias("\\p{javaMirrored}");
		}
	}

	/* classes for Unicode scripts, blocks, categories and binary properties */

	public static final class UnicodeClasses {
		private UnicodeClasses() {
		}
	
		public static CharacterClass unicodeLatin() {
			return new CharacterClassAlias("\\p{IsLatin}");
		}
		
		public static CharacterClass unicodeGreek() {
			return new CharacterClassAlias("\\p{InGreek}");
		}
		
		public static CharacterClass unicodeUpperCase() {
			return new CharacterClassAlias("\\p{Lu}");
		}
		
		public static CharacterClass unicodeAlphabetic() {
			return new CharacterClassAlias("\\p{IsAlphabetic}");
		}
		
		public static CharacterClass unicodeCurrency() {
			return new CharacterClassAlias("\\p{Sc}");
		}
		
		public static CharacterClass unicodeNonGreek() {
			return new CharacterClassAlias("\\P{InGreek}");
		}
		
		public static CharacterClass unicodeNonUppercase() {
			return new CharacterClassAlias("\\p{L}").intersection(unicodeUpperCase().not());
		}
	}
}
