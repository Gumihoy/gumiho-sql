package com.gumihoy.sql.util;

import com.gumihoy.sql.basic.parser.character.AbstractSQLCharacterData;

/**
 * @author kent on 2019-06-16.
 */
public final class SQLCharacterUtils {

    public static boolean isNewLine(char ch) {
        return ch == '\r' || ch == '\n';
    }

    public static boolean isWhitespace(char ch) {
        return isWhitespace((int) ch);
    }

    public static boolean isWhitespace(int codePoint) {
        return AbstractSQLCharacterData.of(codePoint).isWhitespace(codePoint);
    }

    public static boolean isSQLIdentifierStart(char ch) {
        return isSQLIdentifierStart((int) ch);
    }

    public static boolean isSQLIdentifierStart(int codePoint) {
        return AbstractSQLCharacterData.of(codePoint).isSQLIdentifierStart(codePoint);
    }

    public static boolean isSQLIdentifierPart(char ch) {
        return isSQLIdentifierPart((int) ch);
    }

    public static boolean isSQLIdentifierPart(int codePoint) {
        return AbstractSQLCharacterData.of(codePoint).isSQLIdentifierPart(codePoint);
    }

    public static void main(String[] args) {
        System.out.println(isSQLIdentifierStart('@'));
        System.out.println(isSQLIdentifierPart('@'));
    }
}
