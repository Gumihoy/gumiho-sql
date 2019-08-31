package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.expr.sequence.ISQLCreateSequenceOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceCacheOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceCycleOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceGlobalOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceIncrementByOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceKeepOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceMaxValueOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceMinValueOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceNoCacheOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceNoCycleOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceNoKeepOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceNoMaxValueOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceNoMinValueOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceNoOrderOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceNoScaleOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceOrderOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceScaleOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceSessionOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceStartWithOption;
import com.gumihoy.sql.basic.ast.expr.sequence.alter.ISQLAlterSequenceAction;
import com.gumihoy.sql.basic.ast.statement.ddl.sequence.SQLAlterSequenceStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.sequence.SQLCreateSequenceStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.sequence.SQLDropSequenceStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-06-25.
 */
public class SQLSequenceStatementParser extends AbstractSQLStatementParser {

    public SQLSequenceStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateSequenceStatement parseCreate() {
        SQLCreateSequenceStatement x = new SQLCreateSequenceStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);
        acceptAndNextToken(SQLToken.TokenKind.SEQUENCE, true);

        x.setIfNotExist(exprParser.parseIfNotExists());
        x.setName(exprParser.parseName());

        x.setSharingClause(exprParser.parseSharingClause());

        for (; ; ) {
            ISQLCreateSequenceOption option = parseCreateOption();
            if (option == null) {
                break;
            }
            x.addOption(option);
        }

        return x;
    }

    public ISQLCreateSequenceOption parseCreateOption() {
        if (this.accept(SQLToken.TokenKind.INCREMENT)) {
            return parseIncrementBy();
        }
        if (this.accept(SQLToken.TokenKind.START)) {
            return parseStartWith();
        }
        if (this.accept(SQLToken.TokenKind.MAXVALUE)) {
            return parseMaxValue();
        }
        if (this.accept(SQLToken.TokenKind.NOMAXVALUE)) {
            return parseNoMaxValue();
        }
        if (this.accept(SQLToken.TokenKind.MINVALUE)) {
            return parseMinValue();
        }
        if (this.accept(SQLToken.TokenKind.NOMINVALUE)) {
            return parseNoMinValue();
        }
        if (this.accept(SQLToken.TokenKind.CYCLE)) {
            return parseCycle();
        }
        if (this.accept(SQLToken.TokenKind.NOCYCLE)) {
            return parseNoCycle();
        }
        if (this.accept(SQLToken.TokenKind.CACHE)) {
            return parseCache();
        }
        if (this.accept(SQLToken.TokenKind.NOCACHE)) {
            return parseNoCache();
        }

        if (this.accept(SQLToken.TokenKind.ORDER)) {
            return parseOrder();
        }
        if (this.accept(SQLToken.TokenKind.NOORDER)) {
            return parseNoOrder();
        }
        if (this.accept(SQLToken.TokenKind.KEEP)) {
            return parseMinValue();
        }
        if (this.accept(SQLToken.TokenKind.NOKEEP)) {
            return parseNoKeep();
        }
        if (this.accept(SQLToken.TokenKind.SCALE)) {
            return parseScale();
        }
        if (this.accept(SQLToken.TokenKind.NOSCALE)) {
            return parseNoScale();
        }
        if (this.accept(SQLToken.TokenKind.SESSION)) {
            return parseSession();
        }
        if (this.accept(SQLToken.TokenKind.GLOBAL)) {
            return parseGlobal();
        }
        return null;
    }


    public SQLAlterSequenceStatement parseAlter() {
        SQLAlterSequenceStatement x = new SQLAlterSequenceStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        this.acceptAndNextToken(SQLToken.TokenKind.SEQUENCE, true);

        x.setIfExists(exprParser.parseIfExists());
        x.setName(exprParser.parseName());

        for (; ; ) {
            ISQLAlterSequenceAction action = parseAlterAction();
            if (action == null) {
                break;
            }
            x.addAction(action);
        }
        return x;
    }

    public ISQLAlterSequenceAction parseAlterAction() {
        if (this.accept(SQLToken.TokenKind.INCREMENT)) {
            return parseIncrementBy();
        }
        if (this.accept(SQLToken.TokenKind.MAXVALUE)) {
            return parseMaxValue();
        }
        if (this.accept(SQLToken.TokenKind.NOMAXVALUE)) {
            return parseNoMaxValue();
        }
        if (this.accept(SQLToken.TokenKind.MINVALUE)) {
            return parseMinValue();
        }
        if (this.accept(SQLToken.TokenKind.NOMINVALUE)) {
            return parseNoMinValue();
        }
        if (this.accept(SQLToken.TokenKind.CYCLE)) {
            return parseCycle();
        }
        if (this.accept(SQLToken.TokenKind.NOCYCLE)) {
            return parseNoCycle();
        }
        if (this.accept(SQLToken.TokenKind.CACHE)) {
            return parseCache();
        }
        if (this.accept(SQLToken.TokenKind.NOCACHE)) {
            return parseNoCache();
        }

        if (this.accept(SQLToken.TokenKind.ORDER)) {
            return parseOrder();
        }
        if (this.accept(SQLToken.TokenKind.NOORDER)) {
            return parseNoOrder();
        }
        if (this.accept(SQLToken.TokenKind.KEEP)) {
            return parseMinValue();
        }
        if (this.accept(SQLToken.TokenKind.NOKEEP)) {
            return parseNoKeep();
        }
        if (this.accept(SQLToken.TokenKind.SCALE)) {
            return parseScale();
        }
        if (this.accept(SQLToken.TokenKind.NOSCALE)) {
            return parseNoScale();
        }
        if (this.accept(SQLToken.TokenKind.SESSION)) {
            return parseSession();
        }
        if (this.accept(SQLToken.TokenKind.GLOBAL)) {
            return parseGlobal();
        }
        return null;
    }

    public SQLSequenceIncrementByOption parseIncrementBy() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.INCREMENT)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.BY, true);

        return new SQLSequenceIncrementByOption(exprParser.parseExpr());
    }

    public SQLSequenceStartWithOption parseStartWith() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.START)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.WITH, true);

        return new SQLSequenceStartWithOption(exprParser.parseExpr());
    }

    public SQLSequenceMaxValueOption parseMaxValue() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.MAXVALUE)) {
            return null;
        }
        return new SQLSequenceMaxValueOption(exprParser.parseExpr());
    }

    public SQLSequenceNoMaxValueOption parseNoMaxValue() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.NOMAXVALUE)) {
            return null;
        }
        return new SQLSequenceNoMaxValueOption();
    }

    public SQLSequenceMinValueOption parseMinValue() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.MINVALUE)) {
            return null;
        }
        return new SQLSequenceMinValueOption(exprParser.parseExpr());
    }

    public SQLSequenceNoMinValueOption parseNoMinValue() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.NOMINVALUE)) {
            return null;
        }
        return new SQLSequenceNoMinValueOption();
    }

    public SQLSequenceCycleOption parseCycle() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.CYCLE)) {
            return null;
        }
        return new SQLSequenceCycleOption();
    }

    public SQLSequenceNoCycleOption parseNoCycle() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.NOCYCLE)) {
            return null;
        }
        return new SQLSequenceNoCycleOption();
    }

    public SQLSequenceCacheOption parseCache() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.CACHE)) {
            return null;
        }
        return new SQLSequenceCacheOption(exprParser.parseExpr());
    }

    public SQLSequenceNoCacheOption parseNoCache() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.NOCACHE)) {
            return null;
        }
        return new SQLSequenceNoCacheOption();
    }

    public SQLSequenceOrderOption parseOrder() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.ORDER)) {
            return null;
        }
        return new SQLSequenceOrderOption();
    }

    public SQLSequenceNoOrderOption parseNoOrder() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.NOORDER)) {
            return null;
        }
        return new SQLSequenceNoOrderOption();
    }

    public SQLSequenceKeepOption parseKeep() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.KEEP)) {
            return null;
        }
        return new SQLSequenceKeepOption();
    }

    public SQLSequenceNoKeepOption parseNoKeep() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.NOKEEP)) {
            return null;
        }
        return new SQLSequenceNoKeepOption();
    }

    public SQLSequenceScaleOption parseScale() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.SCALE)) {
            return null;
        }

        SQLSequenceScaleOption.SQLType type = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.EXTEND)) {
            type = SQLSequenceScaleOption.SQLType.EXTEND;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.NOEXTEND)) {
            type = SQLSequenceScaleOption.SQLType.NOEXTEND;

        } else {
            throw new SQLParserException(errorInfo());
        }
        return new SQLSequenceScaleOption(type);
    }

    public SQLSequenceNoScaleOption parseNoScale() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.NOSCALE)) {
            return null;
        }
        return new SQLSequenceNoScaleOption();
    }

    public SQLSequenceSessionOption parseSession() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.SESSION)) {
            return null;
        }
        return new SQLSequenceSessionOption();
    }

    public SQLSequenceGlobalOption parseGlobal() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.GLOBAL)) {
            return null;
        }
        return new SQLSequenceGlobalOption();
    }

    public SQLDropSequenceStatement parseDrop() {
        SQLDropSequenceStatement x = new SQLDropSequenceStatement(this.dbType);
        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.SEQUENCE, true);

        for (; ; ) {
            x.addName(exprParser.parseName());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        return x;
    }

}
