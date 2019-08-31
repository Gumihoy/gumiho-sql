package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.ast.statement.ddl.materializedview.SQLAlterMaterializedViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.materializedview.SQLCreateMaterializedViewStatement;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLMaterializedViewStatementParser;
import com.gumihoy.sql.dialect.oracle.parser.OracleExprParser;

/**
 * @author kent on 2019-07-29.
 */
public class OracleMaterializedViewStatementParser extends SQLMaterializedViewStatementParser {

    public OracleMaterializedViewStatementParser(OracleExprParser exprParser) {
        super(exprParser);
    }

    @Override
    public SQLCreateMaterializedViewStatement parseCreate() {
        SQLCreateMaterializedViewStatement x = new SQLCreateMaterializedViewStatement(this.dbType);

        return x;
    }


    @Override
    public SQLAlterMaterializedViewStatement parseAlter() {
        SQLAlterMaterializedViewStatement x = new SQLAlterMaterializedViewStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        this.acceptAndNextToken(SQLToken.TokenKind.TRIGGER, true);


        return x;
    }



    @Override
    public OracleExprParser getExprParser() {
        return (OracleExprParser) exprParser;
    }
}
