package com.gumihoy.sql.basic.parser.character;

/**
 * @author kent on 2019-06-16.
 */
public abstract class AbstractSQLCharacterData {

    protected static final int ERROR = 0xFFFFFFFF;

    protected abstract int getProperties(int ch);

    public abstract boolean isWhitespace(int ch);

    public abstract boolean isSQLIdentifierStart(int ch);

    public abstract boolean isSQLIdentifierPart(int ch);


    public static AbstractSQLCharacterData of(int ch) {
        if (ch >>> 8 == 0) {     // fast-path
            return SQLCharacterDataLatin1.instance;
        } else {
            switch (ch >>> 16) {  //plane 00-16
                case (0):
                    return SQLCharacterData00.instance;
                case (1):
                    return SQLCharacterData01.instance;
                case (2):
                    return SQLCharacterData02.instance;
                case (14):
                    return SQLCharacterData0E.instance;
//                case (15):   // Private Use
//                case (16):   // Private Use
//                    return SQLCharacterDataPrivateUse.instance;
                default:
                    return SQLCharacterDataUndefined.instance;
            }
        }
    }

}
