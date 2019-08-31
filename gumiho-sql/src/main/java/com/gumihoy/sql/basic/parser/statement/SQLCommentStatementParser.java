package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.ISQLCommentStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnAuditPolicyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnColumnStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnEditionStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnIndexTypeStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnMaterializedViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnMiningModelStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnOperatorStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnTypeStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnViewStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-06-25.
 */
public class SQLCommentStatementParser extends AbstractSQLStatementParser {

    public SQLCommentStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }

    public ISQLCommentStatement parse() {
        acceptAndNextToken(SQLToken.TokenKind.COMMENT, true);
        acceptAndNextToken(SQLToken.TokenKind.ON, true);

        ISQLCommentStatement x = null;
        ISQLName name = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.AUDIT)) {
            this.acceptAndNextToken(SQLToken.TokenKind.POLICY, true);
            x = new SQLCommentOnAuditPolicyStatement(this.dbType);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.COLUMN)) {
            x = new SQLCommentOnColumnStatement(this.dbType);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.DATABASE)) {
            x = new SQLCommentOnDatabaseStatement(this.dbType);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.EDITION)) {
            x = new SQLCommentOnEditionStatement(this.dbType);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.INDEX)) {
            x = new SQLCommentOnIndexStatement(this.dbType);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.INDEXTYPE)) {
            x = new SQLCommentOnIndexTypeStatement(this.dbType);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.MATERIALIZED)) {
            this.acceptAndNextToken(SQLToken.TokenKind.VIEW, true);
            x = new SQLCommentOnMaterializedViewStatement(this.dbType);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.MINING)) {
            this.acceptAndNextToken(SQLToken.TokenKind.MODE, true);
            x = new SQLCommentOnMiningModelStatement(this.dbType);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.OPERATOR)) {
            x = new SQLCommentOnOperatorStatement(this.dbType);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.TABLE)) {
            x = new SQLCommentOnTableStatement(this.dbType);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.TYPE)) {
            x = new SQLCommentOnTypeStatement(this.dbType);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.VIEW)) {
            x = new SQLCommentOnViewStatement(this.dbType);

        } else {
            throw new SQLParserException("");
        }

        name = exprParser.parseName();
        this.acceptAndNextToken(SQLToken.TokenKind.IS, true);
        ISQLExpr comment = exprParser.parseExpr();

        x.setName(name);
        x.setComment(comment);

        return x;
    }


}
