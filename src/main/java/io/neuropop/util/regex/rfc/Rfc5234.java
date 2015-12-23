package io.neuropop.util.regex.rfc;

import io.neuropop.util.regex.CharacterClass;
import io.neuropop.util.regex.Expression;
import io.neuropop.util.regex.Regex;

public final class Rfc5234 {

	/* character classes */

	// %x41-5A / %x61-7A   ; A-Z / a-z
	public static CharacterClass ALPHA = Regex.range((char) 0x41, (char) 0x5a)
			.union(Regex.range((char) 0x61, (char) 0x7a));

	// "0" / "1"
	public static CharacterClass BIT = Regex.oneOf('0', '1');

	// %x01-7F
	//      ; any 7-bit US-ASCII character,
	//      ; excluding NUL
	public static CharacterClass CHAR = Regex.range((char) 0x01, (char) 0x7f);

	// %x0D
	//      ; carriage return
	public static CharacterClass CR = Regex.ch((char) 0x0d);

	// %x00-1F / %x7F
	//      ; controls
	public static CharacterClass CTL = Regex.range((char) 0x00, (char) 0x1f)
			.union(Regex.ch((char) 0x7f));

	// %x30-39
	//      ; 0-9
	public static CharacterClass DIGIT = Regex.range((char) 0x30, (char) 0x39);

	// %x22
	//      ; " (Double Quote)
	public static CharacterClass DQUOTE = Regex.ch((char) 0x22);

	// DIGIT / "A" / "B" / "C" / "D" / "E" / "F"
	public static CharacterClass HEXDIG = DIGIT.union(Regex.range('A', 'F'));

	// %x09
	//      ; horizontal tab
	public static CharacterClass HTAB = Regex.ch((char) 0x09);

	// %x0A
	//      ; linefeed
	public static CharacterClass LF = Regex.ch((char) 0x0a);

	// %x00-FF
	//      ; 8 bits of data
	public static CharacterClass OCTET = Regex.range((char) 0x00, (char) 0xff);

	// %x20
	public static CharacterClass SP = Regex.ch((char) 0x20);

	// %x21-7E
	//      ; visible (printing) characters
	public static CharacterClass VCHAR = Regex.range((char) 0x21, (char) 0x7e);

	// SP / HTAB
	//      ; white space
	public static CharacterClass WSP = SP.union(HTAB);

	/* expressions */

	// CR LF
	//      ; Internet standard newline
	public static Expression CRLF = CR.and(LF);

	// *(WSP / CRLF WSP)
	//      ; Use of this linear-white-space rule
	//      ; permits lines containing only white
	//      ; space that are no longer legal in
	//      ; mail headers and have caused
	//      ; interoperability problems in other
	//      ; contexts.
	//      ; Do not use when defining mail
	//      ; headers and use with caution in
	//      ; other contexts.
	public static Expression LWSP = WSP.or(CRLF.and(WSP)).anyTimes();

	private Rfc5234() {
	}
}
