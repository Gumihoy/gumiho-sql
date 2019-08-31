package com.gumihoy.sql.dialect.mysql.parser.statement;

import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableCheckPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableOptimizePartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableRebuildPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableRemovePartitioningAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableRepairPartitionAction;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLTableStatementParser;
import com.gumihoy.sql.dialect.mysql.parser.MySQLExprParser;

/**
 * @author kent on 2019-06-25.
 */
public class MySQLTableStatementParser extends SQLTableStatementParser {

    public MySQLTableStatementParser(MySQLExprParser exprParser) {
        super(exprParser);
    }




    @Override
    public ISQLAlterTableAction parseAlterAction() {
//        MODIFY [COLUMN] col_name column_definition
//        [FIRST | AFTER col_name]
//  | ORDER BY col_name [, col_name] ...
//  | RENAME COLUMN old_col_name TO new_col_name
//                | RENAME {INDEX|KEY} old_index_name TO new_index_name
//                | RENAME [TO|AS] new_tbl_name
//                | {WITHOUT|WITH} VALIDATION
//                | ADD PARTITION (partition_definition)
//                | DROP PARTITION partition_names
//  | DISCARD PARTITION {partition_names | ALL} TABLESPACE
//                | IMPORT PARTITION {partition_names | ALL} TABLESPACE
//                | TRUNCATE PARTITION {partition_names | ALL}
//  | COALESCE PARTITION number
//                | REORGANIZE PARTITION partition_names INTO (partition_definitions)
//                | EXCHANGE PARTITION partition_name WITH TABLE tbl_name [{WITH|WITHOUT} VALIDATION]
//  | ANALYZE PARTITION {partition_names | ALL}

        if (this.accept(SQLToken.TokenKind.ADD)) {
            return parseCheckPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.ALTER)) {
            return parseCheckPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.RENAME)) {
            return parseCheckPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.DROP)) {
            return parseCheckPartitionAction();
        }

        if (this.accept(SQLToken.TokenKind.MODIFY)) {
            return parseCheckPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.CHANGE)) {
            return parseCheckPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.CONVERT)) {
            return parseCheckPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.DISABLE)) {
            return parseCheckPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.ENABLE)) {
            return parseCheckPartitionAction();
        }

        if (this.accept(SQLToken.TokenKind.DISCARD)) {
            return parseCheckPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.IMPORT)) {
            return parseCheckPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.FORCE)) {
            return parseCheckPartitionAction();
        }

        if (this.accept(SQLToken.TokenKind.ORDER)) {
            return parseCheckPartitionAction();
        }


        if (this.accept(SQLToken.TokenKind.TRUNCATE)) {
            return parseCheckPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.COALESCE)) {
            return parseCheckPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.REORGANIZE)) {
            return parseCheckPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.EXCHANGE)) {
            return parseCheckPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.ANALYZE)) {
            return parseCheckPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.CHECK)) {
            return parseCheckPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.OPTIMIZE)) {
            return parseOptimizePartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.REBUILD)) {
            return parseRebuildPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.REPAIR)) {
            return parseRepairPartitionAction();
        }
        if (this.accept(SQLToken.TokenKind.REMOVE)) {
            return parseRemovePartitioningAction();
        }
        return super.parseAlterAction();
    }



    public SQLAlterTableCheckPartitionAction parseCheckPartitionAction() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.CHECK)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITION, true);
        SQLAlterTableCheckPartitionAction x = new SQLAlterTableCheckPartitionAction();
        for (;;) {
            x.addName(exprParser.parseExpr());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        return x;
    }
    public SQLAlterTableOptimizePartitionAction parseOptimizePartitionAction() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.OPTIMIZE)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITION, true);
        SQLAlterTableOptimizePartitionAction x = new SQLAlterTableOptimizePartitionAction();
        for (;;) {
            x.addName(exprParser.parseExpr());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        return x;
    }
    public SQLAlterTableRebuildPartitionAction parseRebuildPartitionAction() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.REBUILD)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITION, true);
        SQLAlterTableRebuildPartitionAction x = new SQLAlterTableRebuildPartitionAction();
        for (;;) {
            x.addName(exprParser.parseExpr());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        return x;
    }
    public SQLAlterTableRepairPartitionAction parseRepairPartitionAction() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.REPAIR)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITION, true);
        SQLAlterTableRepairPartitionAction x = new SQLAlterTableRepairPartitionAction();
        for (;;) {
            x.addName(exprParser.parseExpr());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        return x;
    }
    public SQLAlterTableRemovePartitioningAction parseRemovePartitioningAction() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.REMOVE)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITIONING, true);
        return new SQLAlterTableRemovePartitioningAction();
    }




}
