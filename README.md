# Java Regular Expression Builder

Motivation behind this tool was keeping mind safe when implementing ABNF rules (from various RFC docs) for validating/parsing purposes in Java. It aims to allow to translate rules into Java expressions in a relaxed mode and obtain a valid regular expression pattern as a result.

But I believe it can be used in some simple cases when you don't want to bring regex syntax into your Java code.

Available via the [JitPack build](https://jitpack.io/#nypi/regex-builder/-8a758d59a5-1).

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
	<version>-8a758d59a5-1</version>
</dependency>
```

## Usage Example

For example, below is the rule set of the IPv6 mailbox address literal as defined in the [RFC 5321](https://tools.ietf.org/html/rfc5321).

```
Snum = 1*3DIGIT
     ; representing a decimal integer
     ; value in the range 0 through 255.
```

Simple ```1*3DIGIT``` rule definition allows some invalid numbers. To make it strict a little more complex expression will be used in the code.

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

And this part demonstrates how it can be translated into the Java regex pattern.

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

Now `ipv6Addr.toPattern()` brings you this handy Java regex.

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


