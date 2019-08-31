package com.gumihoy.sql.basic.parser.character;

/**
 * @author kent on 2019-06-16.
 */
public class SQLCharacterDataUndefined extends AbstractSQLCharacterData {

    public static SQLCharacterDataUndefined instance = new SQLCharacterDataUndefined();

    private SQLCharacterDataUndefined() {
    }

    @Override
    protected int getProperties(int ch) {
        return 0;
    }

    @Override
    public boolean isWhitespace(int ch) {
        return false;
    }

    @Override
    public boolean isSQLIdentifierStart(int ch) {
        return false;
    }

    @Override
    public boolean isSQLIdentifierPart(int ch) {
        return false;
    }
}
