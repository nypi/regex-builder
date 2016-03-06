package io.neuropop.text;

public class Hex {
	private static final char[] HEX_ALPHABET = "0123456789abcdef".toCharArray();
	
	private Hex() {
	}
	
	public static byte[] fromHexString(String value) {
		if (value == null)
			return null;

		if (value.length() % 2 != 0)
			throw new IllegalArgumentException("error.malformedHexString");
		byte[] result = new byte[value.length() >>> 1];
		for (int i = 0; i < value.length(); i += 2) {
			int d0 = Character.digit(value.charAt(i), 16);
			int d1 = Character.digit(value.charAt(i + 1), 16);
			if (d0 == -1 || d1 == -1)
				throw new IllegalArgumentException("error.malformedHexString");
			
			result[i >>> 1] = (byte) ((d0 << 4) | d1);
		}
		return result;
	}
	
	public static String toHexString(byte[] bytes) {
		if (bytes == null)
			return null;
		
		StringBuilder builder = new StringBuilder(bytes.length << 1);
		for (int i = 0; i < bytes.length; ++i) {
			byte b = bytes[i];
			builder
					.append(HEX_ALPHABET[(b >>> 4) & 0x0f])
					.append(HEX_ALPHABET[b & 0x0f]);
		}
	    return builder.toString();
	}

	public static String toHexString(byte b) {
		return new String(new char[] {
				HEX_ALPHABET[(b >>> 4) & 0x0f],
				HEX_ALPHABET[b & 0x0f]
		});
	}
}
