package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.index.SQLIndexColumn;
import com.gumihoy.sql.basic.ast.expr.index.alter.ISQLAlterIndexAction;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexAddPartition;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexCoalescePartition;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexDropPartition;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexModifyDefaultAttributes;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexModifyPartition;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexModifySubPartition;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexRenamePartition;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexRenameSubPartition;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexSplitPartition;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLAlterIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLCreateIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLDropIndexStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;

import java.util.List;

/**
 * @author kent on 2019-06-25.
 */
public class SQLIndexStatementParser extends AbstractSQLStatementParser {

    public SQLIndexStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateIndexStatement parseCreate() {
        throw new UnsupportedOperationException("Create Index.");
    }

    public SQLCreateIndexStatement.SQLCategory parseCategory() {
    if (this.acceptAndNextToken(SQLToken.TokenKind.UNIQUE)) {
        return SQLCreateIndexStatement.SQLCategory.UNIQUE;
    }
        if (this.acceptAndNextToken(SQLToken.TokenKind.BITMAP)) {
            return SQLCreateIndexStatement.SQLCategory.BITMAP;
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.FULLTEXT)) {
            return SQLCreateIndexStatement.SQLCategory.FULLTEXT;
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.SPATIAL)) {
            return SQLCreateIndexStatement.SQLCategory.SPATIAL;
        }
        return null;
    }

    public void parseIndexColumns(List<SQLIndexColumn> columns, ISQLObject parent) {
        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {

            for (;;) {
                SQLIndexColumn column = parseIndexColumn();
                column.setParent(parent);
                columns.add(column);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }

            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }
    }
    public SQLIndexColumn parseIndexColumn() {
        ISQLExpr name = exprParser.parseExpr();

        return new SQLIndexColumn(name);
    }



    public SQLAlterIndexStatement parseAlter() {
        throw new UnsupportedOperationException("Alter Index.");
    }


    public ISQLAlterIndexAction parseAlterAction() {
        return null;
    }


    // ----------------------------- Partition -------------------------------------------

    public SQLAlterIndexAddPartition parseAddPartition() {
        this.acceptAndNextToken(SQLToken.TokenKind.ADD, true);
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITION, true);

        SQLAlterIndexAddPartition x = new SQLAlterIndexAddPartition();
        if (!this.accept(SQLToken.TokenKind.TABLESPACE)
                && !exprParser.isParseIndexCompression()
                && !exprParser.isParseParallelClause()) {
            x.setName(exprParser.parseName());
        }


        x.setTableSpaceClause(exprParser.parseTableSpaceClause());
        x.setIndexCompression(exprParser.parseIndexCompression());
        x.setParallelClause(exprParser.parseParallelClause());
        return x;
    }

    public SQLAlterIndexModifyDefaultAttributes parseModifyDefaultAttributes() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.MODIFY)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.DEFAULT, true);
        this.acceptAndNextToken(SQLToken.TokenKind.ATTRIBUTES, true);
        SQLAlterIndexModifyDefaultAttributes x = new SQLAlterIndexModifyDefaultAttributes();

        if (this.acceptAndNextToken(SQLToken.TokenKind.FOR)) {
            this.acceptAndNextToken(SQLToken.TokenKind.PARTITION, true);
            x.setPartition(exprParser.parseExpr());
        }

        for (; ; ) {
            ISQLExpr attribute = null;
            if (exprParser.isParsePhysicalAttribute()) {
                attribute = exprParser.parsePhysicalAttribute();

            } else if (this.accept(SQLToken.TokenKind.TABLESPACE)) {
                attribute = exprParser.parseTableSpaceClause();

            } else if (exprParser.isParseLoggingClause()) {
                attribute = exprParser.parseLoggingClause();
            }
            if (attribute == null) {
                break;
            }
            x.addAttribute(attribute);
        }

        return x;
    }

    public SQLAlterIndexModifyPartition parseModifyPartition() {
        this.acceptAndNextToken(SQLToken.TokenKind.MODIFY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITION, true);

        SQLAlterIndexModifyPartition x = new SQLAlterIndexModifyPartition();
        x.setName(exprParser.parseName());

        for (; ; ) {
            ISQLExpr option = null;
            if (this.accept(SQLToken.TokenKind.DEALLOCATE)) {
                option = exprParser.parseDeallocateUnusedClause();

            } else if (this.accept(SQLToken.TokenKind.ALLOCATE)) {
                option = exprParser.parseAllocateExtentClause();

            } else if (exprParser.isParsePhysicalAttribute()) {
                option = exprParser.parsePhysicalAttribute();

            } else if (exprParser.isParseLoggingClause()) {
                option = exprParser.parseLoggingClause();

            } else if (exprParser.isParseIndexCompression()) {
                option = exprParser.parseIndexCompression();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.PARAMETERS)) {
                this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
                option = new SQLAlterIndexModifyPartition.SQLParametersOption(this.exprParser.parseExpr());
                this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.COALESCE)) {
                option = new SQLAlterIndexModifyPartition.SQLCoalesceOption(this.acceptAndNextToken(SQLToken.TokenKind.CLEANUP));

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.UPDATE)) {

                this.acceptAndNextToken(SQLToken.TokenKind.BLOCK, true);
                this.acceptAndNextToken(SQLToken.TokenKind.REFERENCES, true);

                option = new SQLAlterIndexModifyPartition.SQLUpdateBlockReferencesOption();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.UNUSABLE)) {
                option = new SQLAlterIndexModifyPartition.SQLUnusableOption();

            }
            if (option == null) {
                break;
            }
            x.addOption(option);
        }

        return x;
    }

    public SQLAlterIndexModifySubPartition parseModifySubPartition() {
        this.acceptAndNextToken(SQLToken.TokenKind.MODIFY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITION, true);

        SQLAlterIndexModifySubPartition x = new SQLAlterIndexModifySubPartition();
        x.setName(exprParser.parseName());

        ISQLExpr option = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.UNUSABLE)) {
            option = new SQLAlterIndexModifySubPartition.SQLUnusableOption();

        } else if (this.accept(SQLToken.TokenKind.ALLOCATE)) {
            option = exprParser.parseAllocateExtentClause();

        } else if (this.accept(SQLToken.TokenKind.DEALLOCATE)) {
            option = exprParser.parseDeallocateUnusedClause();

        } else {
            throw new SQLParserException(errorInfo());
        }
        x.setOption(option);
        return x;
    }

    public SQLAlterIndexRenamePartition parseRenamePartition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.RENAME)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITION, true);

        SQLAlterIndexRenamePartition x = new SQLAlterIndexRenamePartition();
        x.setName(exprParser.parseName());

        this.acceptAndNextToken(SQLToken.TokenKind.TO, true);
        x.setNewName(exprParser.parseName());


        return x;
    }

    public SQLAlterIndexRenameSubPartition parseRenameSubPartition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.RENAME)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITION, true);

        SQLAlterIndexRenameSubPartition x = new SQLAlterIndexRenameSubPartition();
        x.setName(exprParser.parseName());

        this.acceptAndNextToken(SQLToken.TokenKind.TO, true);
        x.setNewName(exprParser.parseName());

        return x;
    }

    public SQLAlterIndexDropPartition parseDropPartition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.DROP)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITION, true);
        return new SQLAlterIndexDropPartition(exprParser.parseName());
    }

    public SQLAlterIndexSplitPartition parseSplitPartition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.SPLIT)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITION, true);

        SQLAlterIndexSplitPartition x = new SQLAlterIndexSplitPartition();
        x.setName(exprParser.parseName());

        this.acceptAndNextToken(SQLToken.TokenKind.AT, true);
        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (;;) {
            x.addAtValue(exprParser.parseExpr());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                  break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        if (this.acceptAndNextToken(SQLToken.TokenKind.INTO)) {
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            for (;;) {
                x.addPartition(exprParser.parsePartitionDefinition());
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        x.setParallelClause(exprParser.parseParallelClause());
        return x;
    }

    public SQLAlterIndexCoalescePartition parseCoalescePartition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.COALESCE)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITION, true);

        SQLAlterIndexCoalescePartition x = new SQLAlterIndexCoalescePartition();
        x.setParallelClause(exprParser.parseParallelClause());

        return x;
    }


    public SQLDropIndexStatement parseDrop() {
        throw new UnsupportedOperationException("Drop Index.");
    }


}
