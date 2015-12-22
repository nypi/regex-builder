# Java Regular Expression Builder

Tool for building regular expressions in Java.

Some benefits of using a regex builder instead of a plain regex:

 - There is no need to remember details of the regex syntax (though, you still need to understand the semantics).
 - You can get rid of 'programs inside programs', which is somehow annoying.
 - With a builder it is harder to create a syntactically incorrect expression.

Available via the [JitPack build](https://jitpack.io/#nypi/regex-builder/d8599b80c0).

```
<repository>
	<id>jitpack.io</id>
	<url>https://jitpack.io</url>
</repository>
```

```
<dependency>
	<groupId>com.github.nypi</groupId>
	<artifactId>regex-builder</artifactId>
	<version>d8599b80c0</version>
</dependency>
```

## Usage Example

Below is a rule set of the IPv6 mailbox address literal as defined in [RFC 5321](https://tools.ietf.org/html/rfc5321).

```
Snum = 1*3DIGIT
     ; representing a decimal integer
     ; value in the range 0 through 255.
```

Simple ```1*3DIGIT``` definition allows some invalid numbers. Therefore, a little more complex expression will be used in the code.

```
IPv4-address-literal = Snum 3("."  Snum)
IPv6-hex = 1*4HEXDIG
IPv6-full = IPv6-hex 7(":" IPv6-hex)
IPv6-comp = [IPv6-hex *5(":" IPv6-hex)] "::" [IPv6-hex *5(":" IPv6-hex)]
     ; The "::" represents at least 2 16-bit groups of
     ; zeros.  No more than 6 groups in addition to the
     ; "::" may be present.
IPv6v4-full = IPv6-hex 5(":" IPv6-hex) ":" IPv4-address-literal
IPv6v4-comp = [IPv6-hex *3(":" IPv6-hex)] "::" 
     [IPv6-hex *3(":" IPv6-hex) ":"] 
     IPv4-address-literal
     ; The "::" represents at least 2 16-bit groups of
     ; zeros.  No more than 4 groups in addition to the
     ; "::" and IPv4-address-literal may be present.
IPv6-addr = IPv6-full / IPv6-comp / IPv6v4-full / IPv6v4-comp
```

This part is a corresponding Java code.

```java
CharacterClass DIGIT = Regex.digit();
CharacterClass HEXDIG = Regex.digit()
		.union(Regex.range('A', 'F'))
		.union(Regex.range('a', 'f'));

Expression snum = DIGIT
		.or(Regex.range('1', '9').and(DIGIT))
		.or(Regex.ch('1').and(DIGIT).and(DIGIT))
		.or(Regex.ch('2').and(Regex.range('0', '4')).and(DIGIT))
		.or(Regex.str("25").and(Regex.range('0', '5')));

Expression ipv4AddressLiteral = snum.and(Regex.ch('.').and(snum).repeat(3));		

Expression ipv6Hex = HEXDIG.repeat(1, 4);

Expression ipv6Full = ipv6Hex.and(Regex.ch(':').and(ipv6Hex).repeat(7));

Expression ipv6CompPart = ipv6Hex.and(Regex.ch(':').and(ipv6Hex).atMost(5));
Expression ipv6Comp = ipv6CompPart.atMostOnce()
		.and(Regex.str("::"))
		.and(ipv6CompPart.atMostOnce());

Expression ipv6v4Full = ipv6Hex.and(Regex.ch(':').and(ipv6Hex).repeat(5))
		.and(Regex.ch(':'))
		.and(ipv4AddressLiteral);

Expression ipv6v4CompPart = ipv6Hex.and(Regex.ch(':').and(ipv6Hex).atMost(3));
Expression ipv6v4Comp = ipv6v4CompPart.atMostOnce()
		.and(Regex.str("::"))
		.and(ipv6v4CompPart.and(Regex.ch(':')).atMostOnce())
		.and(ipv4AddressLiteral);

Expression ipv6Addr = ipv6Full
		.or(ipv6Comp)
		.or(ipv6v4Full)
		.or(ipv6v4Comp);
```

Finally, `ipv6Addr.toPattern()` brings you this handy Java regex.

```
[\dA-Fa-f]{1,4}(?:\:[\dA-Fa-f]{1,4}){7}|(?:[\dA-Fa-f]{1,4}
(?:\:[\dA-Fa-f]{1,4}){0,5})?\:\:(?:[\dA-Fa-f]{1,4}
(?:\:[\dA-Fa-f]{1,4}){0,5})?|[\dA-Fa-f]{1,4}
(?:\:[\dA-Fa-f]{1,4}){5}\:(?:\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])
(?:\.(?:\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])){3}|
(?:[\dA-Fa-f]{1,4}(?:\:[\dA-Fa-f]{1,4}){0,3})?\:\:(?:[\dA-Fa-f]{1,4}
(?:\:[\dA-Fa-f]{1,4}){0,3}\:)?(?:\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])
(?:\.(?:\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])){3}
```


