package io.neuropop.util.regex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import io.neuropop.text.Ascii;
import io.neuropop.text.Hex;

// package-private
class Literals {
	private static final Set<Character> METACHARACTERS = newMetacharacters();
	private static final Map<Character, String> ALIASES = newAliases(); 
	
	private static Set<Character> newMetacharacters() {
		String str = "()[]{}.,-\\|+*?$^&:!<>=#";
		Set<Character> metacharacters = new HashSet<>(str.length());
		for (int i = 0; i < str.length(); ++i)
			metacharacters.add(str.charAt(i));
		return metacharacters;
	}
	
	private static Map<Character, String> newAliases() {
		Map<Character, String> aliases = new HashMap<>();
		aliases.put((char) 0x09, "\\t");
		aliases.put((char) 0x0a, "\\n");
		aliases.put((char) 0x0d, "\\r");
		aliases.put((char) 0x0c, "\\f");
		aliases.put((char) 0x07, "\\a");
		aliases.put((char) 0x1b, "\\e");
		return aliases;
	}
	
	private Literals() {
	}
	
	private static void appendEscaped(StringBuilder builder, char c) {
		Objects.requireNonNull(builder);

		String alias = ALIASES.get(c);
		if (alias != null) {
			builder.append(alias);
			return;
		}
		if (Ascii.isAsciiCharacter(c)
				&& !Ascii.isControlCharacter(c)) {
			if (METACHARACTERS.contains(c))
				builder.append('\\');
			builder.append(c);
		} else {
			byte b0 = (byte) (c >>> 8);
			if (b0 != 0) {
				builder.append("\\u").append(Hex.toHexString(b0));
			} else {
				builder.append("\\x");
			}
			byte b1 = (byte) (c & 0xff);
			builder.append(Hex.toHexString(b1));
		}
	}
	
	public static String escape(char c) {
		StringBuilder builder = new StringBuilder();
		appendEscaped(builder, c);
		return builder.toString();
	}
	
	public static String escape(String str) {
		if (str == null)
			return null;

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < str.length(); ++i)
			appendEscaped(builder, str.charAt(i));
		return builder.toString();
	}
}
