package io.neuropop.text;

public class Ascii {
	public static final char NUL = 0;
	public static final char SOH = 1;
	public static final char STX = 2;
	public static final char ETX = 3;
	public static final char EOT = 4;
	public static final char ENQ = 5;
	public static final char ACK = 6;
	public static final char BEL = 7;
	public static final char BS = 8;
	public static final char HT = 9;
	public static final char LF = 10;
	public static final char VT = 11;
	public static final char FF = 12;
	public static final char CR = 13;
	public static final char SO = 14;
	public static final char SI = 15;
	public static final char DLE = 16;
	public static final char DC1 = 17;
	public static final char DC2 = 18;
	public static final char DC3 = 19;
	public static final char DC4 = 20;
	public static final char NAK = 21;
	public static final char SYN = 22;
	public static final char ETB = 23;
	public static final char CAN = 24;
	public static final char EM = 25;
	public static final char SUB = 26;
	public static final char ESC = 27;
	public static final char FS = 28;
	public static final char GS = 29;
	public static final char RS = 30;
	public static final char US = 31;
	public static final char SP = 32;
	public static final char DEL = 127;
	
	private Ascii() {
	}
	
	public static boolean isAsciiCharacter(char c) {
		return c >= 0 && c <= 127;
	}
	
	public static boolean isControlCharacter(char c) {
		return c >= 0 && c <= 31 || c == 127;
	}
	
	public static boolean isGraphicCharacter(char c) {
		return c >= 32 && c <= 47
				|| c >= 58 &&  c <= 64
				|| c >= 91 && c <= 96
				|| c >= 123 && c <= 126;
	}
	
	public static boolean isLetter(char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
	}
	
	public static boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}
	
	public static boolean isLetterOrDigit(char c) {
		return isLetter(c) || isDigit(c);
	}
	
	public static boolean isSpace(char c) {
		return c >= 9 && c <= 13 || c == 32;
	}
	
	public static boolean isLowerCase(char c) {
		return c >= 'a' && c <= 'z';
	}
	
	public static boolean isUpperCase(char c) {
		return c >= 'A' && c <= 'Z';
	}
	
	public static char toLowerCase(char c) {
		return isUpperCase(c) ? (char) (c ^ 0x20) : c;
	}
	
	public static char toUpperCase(char c) {
		return isLowerCase(c) ? (char) (c & 0x5f) : c;
	}
}
