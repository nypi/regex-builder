package io.neuropop.util.regex;

import org.junit.Assert;
import org.junit.Test;

public class CharacterClassSetTest {

	@Test(expected = Exception.class)
	public void testEmptyCharacterClassSetFromChars() {
		CharacterClassSet set = new CharacterClassSet((char[]) null);
	}

	@Test(expected = Exception.class)
	public void testEmptyCharacterClassSetFromEmptyChars() {
		CharacterClassSet set = new CharacterClassSet(new char[0]);
	}

	@Test(expected = Exception.class)
	public void testEmptyCharacterClassSetFromClasses() {
		CharacterClassSet set = new CharacterClassSet((CharacterClass[]) null);
	}

	@Test(expected = Exception.class)
	public void testEmptyCharacterClassSetFromEmptyClasses() {
		CharacterClassSet set = new CharacterClassSet(new CharacterClass[0]);
	}

	@Test(expected = Exception.class)
	public void testEmptyCharacterClassSetFromNullClasses() {
		CharacterClassSet set = new CharacterClassSet(new CharacterClass[] {
				null, null, null
		});
	}

	@Test
	public void testAllowNullClasses() {
		CharacterClassSet set = new CharacterClassSet(new CharacterClass[] {
				new LiteralCharacter('a'),
				null
		});
		Assert.assertEquals(set.decl(), "a");
	}

	@Test
	public void testCharactersDeduplication() {
		CharacterClassSet set = new CharacterClassSet('a', 'a', 'a');
		Assert.assertEquals(set.decl(), "a");
	}

	@Test
	public void testCharactersOrder() {
		CharacterClassSet set = new CharacterClassSet('z', 'a', 'Z');
		Assert.assertEquals(set.decl(), "zaZ");
	}

	@Test(expected = Exception.class)
	public void testNegationInSetForbidden() {
		CharacterClassSet set = new CharacterClassSet(
				new LogicalCharacterClass.Negation(new LiteralCharacter('a')),
				new LiteralCharacter('z'));
	}

	@Test(expected = Exception.class)
	public void testUnionInSetForbidden() {
		CharacterClassSet set = new CharacterClassSet(
				new LogicalCharacterClass.Union(new LiteralCharacter('a'), new LiteralCharacter('b')),
				new LiteralCharacter('z'));
	}

	@Test(expected = Exception.class)
	public void testIntersectionInSetForbidden() {
		CharacterClassSet set = new CharacterClassSet(
				new LogicalCharacterClass.Intersection(new LiteralCharacter('a'), new LiteralCharacter('b')),
				new LiteralCharacter('z'));
	}
}
