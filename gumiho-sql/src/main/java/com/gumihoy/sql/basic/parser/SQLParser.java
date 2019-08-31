package com.gumihoy.sql.basic.parser;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.config.SQLParseConfig;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-06-13.
 */
public class SQLParser {

    public final SQLLexer lexer;
    protected DBType dbType;
    protected SQLParseConfig config;

    public SQLParser(String sql) {
        this(new SQLLexer(sql));
    }

    public SQLParser(String sql, SQLParseConfig config) {
        this(new SQLLexer(sql, config), config);
    }

    public SQLParser(String sql, DBType dbType) {
        this(new SQLLexer(sql, dbType));
    }

    public SQLParser(SQLLexer lexer) {
        this(lexer, null);
    }

    public SQLParser(SQLLexer lexer, SQLParseConfig config) {
        this.lexer = lexer;
        this.dbType = lexer.dbType;
        if (config == null) {
            config = new SQLParseConfig();
        }
        this.config = config;
    }

    public boolean accept(SQLToken.TokenKind kind) {
        return accept(kind, false);
    }

    public boolean accept(SQLToken.TokenKind kind, boolean throwException) {
        if (lexer.kind == kind
                || (kind.keyWord != null && lexer.lowerHash == kind.keyWord.lowerHash)) {
            return true;
        }
        if (throwException) {
            throw new SQLParserException("Syntax Error: expected " + kind.keyWord.upper + ",  actual " + getStringValue() + ", " + errorInfo());
        }
        return false;
    }

    public boolean acceptAndNextToken(SQLToken.TokenKind kind) {
        return acceptAndNextToken(kind, false);
    }

    public boolean acceptAndNextToken(SQLToken.TokenKind kind, boolean throwException) {
        if (lexer.kind == kind
                || (kind.keyWord != null && lexer.lowerHash == kind.keyWord.lowerHash)) {
            nextToken();
            return true;
        }
        if (throwException) {
            throw new SQLParserException("Syntax Error: expected " + kind.keyWord.upper + ",  actual " + getStringValue() + ", " + errorInfo());
        }
        return false;
    }

    public boolean acceptAndNextToken(SQLKeyWord keyWord) {
        return this.acceptAndNextToken(keyWord, false);
    }

    public boolean acceptAndNextToken(SQLKeyWord keyWord, boolean throwException) {
        if (lexer.lowerHash == keyWord.lowerHash) {
            nextToken();
            return true;
        }
        if (throwException) {
            throw new SQLParserException("Syntax Error: expected " + keyWord + ",  actual " + getStringValue() + "");
        }
        return false;
    }

    public SQLToken token() {
        return lexer.token();
    }

    public void nextToken() {
        lexer.nextToken();
    }


    public String getStringValue() {
        return lexer.getStringValue();
    }

    public SQLLexer.SQLMake make() {
        return lexer.make();
    }

    public void reset(SQLLexer.SQLMake make) {
        lexer.reset(make);
    }

    public void addBeforeComments(ISQLObject x) {
        if (x == null) {
            return;
        }
        x.addBeforeComment(lexer.comments);
        clearComments();
    }

    public void addAfterComments(ISQLObject x) {
        if (x == null) {
            return;
        }
        x.addAfterComment(lexer.comments);
        clearComments();
    }

    public void clearComments() {
        lexer.comments.clear();
    }

    public String errorInfo() {
        String msg = "Syntax error, error in :" + lexer.errorInfo();
        return msg;
    }
}
