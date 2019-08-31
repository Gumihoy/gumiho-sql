package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.ddl.packagebody.SQLAlterPackageBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.packagebody.SQLCreatePackageBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.packagebody.SQLDropPackageBodyStatement;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLPackageBodyStatementParser;
import com.gumihoy.sql.dialect.oracle.parser.OracleExprParser;

/**
 * @author kent on 2019-06-25.
 */
public class OraclePackageBodyStatementParser extends SQLPackageBodyStatementParser {

    public OraclePackageBodyStatementParser(OracleExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreatePackageBodyStatement parseCreate() {
        SQLCreatePackageBodyStatement x = new SQLCreatePackageBodyStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        boolean orReplace = exprParser.parseOrReplace();
        x.setOrReplace(orReplace);

        acceptAndNextToken(SQLToken.TokenKind.PACKAGE, true);
        acceptAndNextToken(SQLToken.TokenKind.BODY, true);

        x.setName(exprParser.parseName());

        SQLASType as = exprParser.parseAsType(true);
        x.setAs(as);

        for (; ; ) {
            ISQLExpr declareSection = parseCreateItem();
            if (declareSection == null) {
                break;
            }
            x.addDeclareSection(declareSection);
        }

        this.acceptAndNextToken(SQLToken.TokenKind.END, true);

        ISQLName endName = exprParser.parseName();
        x.setEndName(endName);

        return x;
    }

    public ISQLExpr parseCreateItem() {
        if (this.accept(SQLToken.TokenKind.END)) {
            return null;
        }
        return getExprParser().parseDeclareSection();
    }


    public SQLAlterPackageBodyStatement parseAlter() {
        return super.parseAlter();
    }


    public SQLDropPackageBodyStatement parseDrop() {
        SQLDropPackageBodyStatement x = new SQLDropPackageBodyStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.PACKAGE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.BODY, true);

        ISQLName name = exprParser.parseName();
        x.setName(name);

        return x;
    }


    @Override
    public OracleExprParser getExprParser() {
        return (OracleExprParser) exprParser;
    }

}
