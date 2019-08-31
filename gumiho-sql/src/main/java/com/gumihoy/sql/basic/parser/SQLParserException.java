package com.gumihoy.sql.basic.parser;

/**
 * @author kent on 2019-06-14.
 */
public class SQLParserException extends RuntimeException {

    public SQLParserException() {
    }

    public SQLParserException(String message) {
        super(message);
    }

    public SQLParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLParserException(int line, int col, String message) {
        super("line " + line + ", col " + col + ", " + message);
    }

    public SQLParserException(int line, int col, String message, Throwable cause) {
        super("line " + line + ", col " + col + ", " + message, cause);
    }
}
