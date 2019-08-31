package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.enums.SQLDropBehavior;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.select.ISQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.SQLObjectNameTableReference;
import com.gumihoy.sql.basic.ast.expr.table.ISQLEnableDisableClause;
import com.gumihoy.sql.basic.ast.expr.table.alter.*;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.*;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.*;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.*;
import com.gumihoy.sql.basic.ast.expr.table.alter.trigger.SQLAlterTableDisableTriggerAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.trigger.SQLAlterTableEnableTriggerAction;
import com.gumihoy.sql.basic.ast.expr.table.element.ISQLTableElement;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLColumnDefinition;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.ISQLTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionDefinition;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionDefinition;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLAlterTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLCreateTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLDropTableStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLKeyWord;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-06-25.
 */
public class SQLTableStatementParser extends AbstractSQLStatementParser {


    public SQLTableStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLCreateTableStatement parseCreate() {
        SQLCreateTableStatement x = new SQLCreateTableStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        SQLCreateTableStatement.SQLTableScope scope = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.GLOBAL)) {
            this.acceptAndNextToken(SQLToken.TokenKind.TEMPORARY, true);
            scope = SQLCreateTableStatement.SQLTableScope.GLOBAL_TEMPORARY;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.LOCAL)) {
            this.acceptAndNextToken(SQLToken.TokenKind.TEMPORARY, true);
            scope = SQLCreateTableStatement.SQLTableScope.LOCAL_TEMPORARY;

        }
        x.setScope(scope);


        acceptAndNextToken(SQLToken.TokenKind.TABLE, true);
        if (lexer.token().kind == SQLToken.TokenKind.IF) {
            nextToken();
            acceptAndNextToken(SQLToken.TokenKind.NOT, true);
            acceptAndNextToken(SQLToken.TokenKind.EXISTS, true);
            x.setIfNotExists(true);
        }

        x.setName(exprParser.parseName());


        exprParser.parseTableElements(x.getTableElements(), x);

        ISQLTableElement likeClause = exprParser.parseLikeClause();
        if (likeClause != null) {
            x.setTableElementsParen(false);
            x.addTableElement(likeClause);
        }


        parseCreateProperties(x);


        if (lexer.token().kind == SQLToken.TokenKind.AS) {
            x.setAs(true);
            nextToken();
        }

        ISQLSelectQuery subQuery = exprParser.parseSelectQuery();
        x.setSubQuery(subQuery);


        return x;
    }




    public void parseOnCommits(SQLCreateTableStatement x) {
        for (; ; ) {
            if (!this.acceptAndNextToken(SQLToken.TokenKind.ON)) {
                return;
            }

            this.acceptAndNextToken(SQLToken.TokenKind.COMMIT, true);

            if (this.acceptAndNextToken(SQLToken.TokenKind.DROP)) {
                this.acceptAndNextToken(SQLToken.TokenKind.DEFINITION, true);
                x.setCommitActionDefinition(SQLCreateTableStatement.SQLOnCommitActionDefinitionType.ON_COMMIT_DROP_DEFINITION);
                continue;
            }

            if (this.acceptAndNextToken(SQLToken.TokenKind.PRESERVE)) {

                if (this.acceptAndNextToken(SQLToken.TokenKind.DEFINITION)) {
                    x.setCommitActionDefinition(SQLCreateTableStatement.SQLOnCommitActionDefinitionType.ON_COMMIT_PRESERVE_DEFINITION);
                    continue;
                } else if (this.acceptAndNextToken(SQLToken.TokenKind.ROWS)) {
                    x.setCommitActionRows(SQLCreateTableStatement.SQLOnCommitActionRowsType.ON_COMMIT_PRESERVE_ROWS);
                    continue;
                } else {
                    throw new SQLParserException(errorInfo());
                }
            }

            if (this.acceptAndNextToken(SQLToken.TokenKind.DELETE)) {
                this.acceptAndNextToken(SQLToken.TokenKind.ROWS, true);
                x.setCommitActionRows(SQLCreateTableStatement.SQLOnCommitActionRowsType.ON_COMMIT_DELETE_ROWS);
                continue;
            }
        }

    }


    public void parseCreateProperties(SQLCreateTableStatement x) {
    }


    // ------------------------- Alter -----------------------------------

    public SQLAlterTableStatement parseAlter() {
        SQLAlterTableStatement x = new SQLAlterTableStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        this.acceptAndNextToken(SQLToken.TokenKind.TABLE, true);

        ISQLName name = exprParser.parseName();
        SQLObjectNameTableReference tableReference = new SQLObjectNameTableReference(name);
        x.setTableReference(tableReference);

        for (; ; ) {
            ISQLExpr action = parseAlterAction();
            if (action == null) {
                break;
            }
            x.addAction(action);
        }

        return x;
    }


    public ISQLAlterTableAction parseAlterAction() {
        SQLLexer.SQLMake make = this.make();
        if (this.acceptAndNextToken(SQLToken.TokenKind.ADD)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.COLUMN)) {
                this.reset(make);
                return parseAlterTableAddColumnAction();

            } else if (exprParser.isTableConstraint()) {
                this.reset(make);
                return parseAlterTableAddTableConstraintAction();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
                this.reset(make);
                return parseAlterTableAddPartitionAction();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {

                if (exprParser.isTableConstraint()) {
                    this.reset(make);
                    return parseAlterTableAddTableConstraintAction();

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
                    this.reset(make);
                    return parseAlterTableAddPartitionAction();
                } else if (this.acceptAndNextToken(SQLToken.TokenKind.OVERFLOW)) {

                } else {
                    this.reset(make);
                    return parseAlterTableAddColumnAction();
                }

            }

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.ALTER)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.COLUMN)) {
                this.reset(make);
                return parseAlterTableAddColumnAction();

            }

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.DROP)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.COLUMN)) {
                this.reset(make);
                return parseAlterTableAddColumnAction();

            }

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.RENAME)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
                this.reset(make);
                return parseAlterTableIRenamePartition();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITION)) {
                this.reset(make);
                return parseAlterTableIRenameSubPartition();

            }


        } else if (this.acceptAndNextToken(SQLToken.TokenKind.TRUNCATE)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
                this.reset(make);
                return parseAlterTableITruncatePartition();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITIONS)) {
                this.reset(make);
                return parseAlterTableITruncatePartitions();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITION)) {
                this.reset(make);
                return parseAlterTableITruncateSubPartition();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITIONS)) {
                this.reset(make);
                return parseAlterTableITruncateSubPartitions();

            }


        } else if (this.acceptAndNextToken(SQLToken.TokenKind.SPLIT)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
                this.reset(make);
                return parseAlterTableISplitPartition();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITION)) {
                this.reset(make);
                return parseAlterTableISplitSubPartition();

            }


        } else if (this.acceptAndNextToken(SQLToken.TokenKind.MERGE)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITIONS)) {
                this.reset(make);
                return parseAlterTableIMergePartitions();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITIONS)) {
                this.reset(make);
                return parseAlterTableIMergeSubPartitions();

            }


        } else if (this.acceptAndNextToken(SQLToken.TokenKind.MODIFY)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.CONSTRAINT)) {
                this.reset(make);
                return parseAlterTableModifyConstraintAction();

            }

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.CHANGE)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.COLUMN)) {
                this.reset(make);
                return parseAlterTableChangeColumnAction();

            }

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.ORDER)) {
            this.acceptAndNextToken(SQLToken.TokenKind.BY, true);

            SQLAlterTableOrderByColumnAction x = new SQLAlterTableOrderByColumnAction();
            for (; ; ) {
                ISQLExpr name = exprParser.parseExpr();
                x.addName(name);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }

        }

        this.reset(make);
        return null;
    }


    public SQLAlterTableStatement.SQLMemOptimizeReadType parseMemOptimizeRead() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.MEMOPTIMIZE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.FOR, true);
            this.acceptAndNextToken(SQLToken.TokenKind.READ, true);
            return SQLAlterTableStatement.SQLMemOptimizeReadType.MEMOPTIMIZE_FOR_READ;
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.NO)) {
            this.acceptAndNextToken(SQLToken.TokenKind.MEMOPTIMIZE, true);
            this.acceptAndNextToken(SQLToken.TokenKind.FOR, true);
            this.acceptAndNextToken(SQLToken.TokenKind.READ, true);
            return SQLAlterTableStatement.SQLMemOptimizeReadType.NO_MEMOPTIMIZE_FOR_READ;
        }
        return null;
    }


    public SQLAlterTableRenameAction parseAlterTableRenameAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.RENAME, true);

        SQLAlterTableRenameAction x = new SQLAlterTableRenameAction();

        x.setName(exprParser.parseName());

        SQLAlterTableRenameAction.SQLRenameOperator operator = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.TO)) {
            operator = SQLAlterTableRenameAction.SQLRenameOperator.TO;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.AS)) {
            operator = SQLAlterTableRenameAction.SQLRenameOperator.AS;

        }
        x.setOperator(operator);

        x.setNewName(exprParser.parseName());

        return x;
    }


    // -------------------------- Column --------------------------

    public SQLAlterTableAddColumnAction parseAlterTableAddColumnAction() {
        SQLAlterTableAddColumnAction x = new SQLAlterTableAddColumnAction();

        this.acceptAndNextToken(SQLToken.TokenKind.ADD, true);

        boolean column = this.acceptAndNextToken(SQLToken.TokenKind.COLUMN);
        x.setColumn(column);

        boolean ifNotExists = exprParser.parseIfNotExists();
        x.setIfNotExists(ifNotExists);

        boolean paren = this.acceptAndNextToken(SQLToken.TokenKind.LPAREN);
        x.setParen(paren);

        for (; ; ) {
            SQLColumnDefinition columnDefinition = exprParser.parseColumnDefinition();
            x.addColumn(columnDefinition);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }

        if (paren) {
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return x;
    }

    public SQLAlterTableAddPeriodAction parseAlterTableAddPeriodAction() {
        SQLAlterTableAddPeriodAction x = new SQLAlterTableAddPeriodAction();

        this.acceptAndNextToken(SQLToken.TokenKind.ADD, true);
        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

        this.acceptAndNextToken(SQLToken.TokenKind.PERIOD, true);
        this.acceptAndNextToken(SQLToken.TokenKind.FOR, true);
        x.setColumn(exprParser.parseName());


        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        return x;
    }

    public SQLAlterTableChangeColumnAction parseAlterTableChangeColumnAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.CHANGE, true);

        boolean column = this.acceptAndNextToken(SQLToken.TokenKind.COLUMN);

        SQLAlterTableChangeColumnAction x = new SQLAlterTableChangeColumnAction();
        x.setColumn(column);

        return x;
    }


    public SQLAlterTableModifyColumnAction parseAlterTableModifyColumnAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.MODIFY, true);

        SQLAlterTableModifyColumnAction x = new SQLAlterTableModifyColumnAction();
        x.setColumn_(this.acceptAndNextToken(SQLToken.TokenKind.COLUMN));


        return null;
    }

    public SQLAlterTableModifyColumnsAction parseAlterTableModifyColumnsAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.MODIFY, true);

        SQLAlterTableModifyColumnsAction x = new SQLAlterTableModifyColumnsAction();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (;;) {
            x.addColumn(exprParser.parseColumnDefinition());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        return x;
    }


    protected SQLAlterTableSetUnusedColumnAction parseAlterTableSetUnusedColumnAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.SET, true);
        this.acceptAndNextToken(SQLToken.TokenKind.UNUSED, true);
        this.acceptAndNextToken(SQLToken.TokenKind.COLUMN, true);

        SQLAlterTableSetUnusedColumnAction x = new SQLAlterTableSetUnusedColumnAction();
        x.setName(exprParser.parseExpr());

        parseDropColumnOption(x);

        return x;
    }
    protected SQLAlterTableSetUnusedColumnsAction parseAlterTableSetUnusedColumnsAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.SET, true);
        this.acceptAndNextToken(SQLToken.TokenKind.UNUSED, true);

        SQLAlterTableSetUnusedColumnsAction x = new SQLAlterTableSetUnusedColumnsAction();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (;;) {
            x.addColumn(exprParser.parseExpr());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        parseDropColumnOption(x);

        return x;
    }

    protected SQLAlterTableDropColumnAction parseAlterTableDropColumnAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        SQLAlterTableDropColumnAction x = new SQLAlterTableDropColumnAction();
        x.setColumn(this.acceptAndNextToken(SQLToken.TokenKind.COLUMN));
        x.setName(exprParser.parseName());

        parseDropColumnOption(x);

        return x;
    }

    protected SQLAlterTableDropColumnsAction parseAlterTableDropColumnsAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);

        SQLAlterTableDropColumnsAction x = new SQLAlterTableDropColumnsAction();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            x.addName(exprParser.parseExpr());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        parseDropColumnOption(x);

        return x;
    }

    protected SQLAlterTableDropUnusedColumnsAction parseAlterTableDropUnusedColumnsAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.UNUSED, true);
        this.acceptAndNextToken(SQLToken.TokenKind.COLUMNS, true);

        SQLAlterTableDropUnusedColumnsAction x = new SQLAlterTableDropUnusedColumnsAction();
        parseDropColumnOption(x);
        return x;
    }

    protected SQLAlterTableDropColumnsContinueAction parseAlterTableDropColumnsContinueAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.COLUMNS, true);
        this.acceptAndNextToken(SQLToken.TokenKind.CONTINUE, true);

        SQLAlterTableDropColumnsContinueAction x = new SQLAlterTableDropColumnsContinueAction();
        parseDropColumnOption(x);
        return x;
    }

    protected SQLAlterTableDropPeriodAction parseAlterTableDropPeriodAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

        this.acceptAndNextToken(SQLToken.TokenKind.PERIOD, true);
        this.acceptAndNextToken(SQLToken.TokenKind.FOR, true);

        SQLAlterTableDropPeriodAction x = new SQLAlterTableDropPeriodAction();
        x.setName(exprParser.parseExpr());

        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        return x;
    }

    protected void parseDropColumnOption(AbstractSQLAlterTableDropColumnAction x) {
        for (; ; ) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.CASCADE)) {
                this.acceptAndNextToken(SQLToken.TokenKind.CONSTRAINTS, true);
                x.addOption(AbstractSQLAlterTableDropColumnAction.SQLOption.CASCADE_CONSTRAINTS);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.INVALIDATE)) {
                x.addOption(AbstractSQLAlterTableDropColumnAction.SQLOption.INVALIDATE);

            } else {
                break;
            }
        }

        x.setOnline(this.acceptAndNextToken(SQLToken.TokenKind.ONLINE));
        if (this.acceptAndNextToken(SQLToken.TokenKind.CHECKPOINT)) {
            x.setCheckPoint(exprParser.parseExpr());
        }
    }

    // -------------------------- Constraint --------------------------

    public SQLAlterTableAddTableConstraintAction parseAlterTableAddTableConstraintAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.ADD, true);

        boolean paren = this.acceptAndNextToken(SQLToken.TokenKind.LPAREN);

        ISQLTableConstraint constraint = exprParser.parseTableConstraint();

        if (paren) {

            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        SQLAlterTableAddTableConstraintAction x = new SQLAlterTableAddTableConstraintAction();
        x.setParen(paren);
        x.addConstraint(constraint);


        return x;
    }

    public SQLAlterTableModifyConstraintAction parseAlterTableModifyConstraintAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.MODIFY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.CONSTRAINT, true);

        SQLAlterTableModifyConstraintAction x = new SQLAlterTableModifyConstraintAction(exprParser.parseName());

        return x;
    }

    public SQLAlterTableModifyPrimaryKeyConstraintAction parseAlterTableModifyPrimaryKeyConstraintAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.MODIFY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.PRIMARY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.KEY, true);

        SQLAlterTableModifyPrimaryKeyConstraintAction x = new SQLAlterTableModifyPrimaryKeyConstraintAction();

        return x;
    }

    public SQLAlterTableModifyUniqueConstraintAction parseAlterTableModifyUniqueConstraintAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.MODIFY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.UNIQUE, true);

        SQLAlterTableModifyUniqueConstraintAction x = new SQLAlterTableModifyUniqueConstraintAction();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            x.addColumn(exprParser.parseExpr());
            if (!this.acceptAndNextToken(SQLKeyWord.COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        return x;
    }

    public SQLAlterTableRenameConstraintAction parseAlterTableRenameConstraintAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.RENAME, true);
        this.acceptAndNextToken(SQLToken.TokenKind.CONSTRAINT, true);
        ISQLName name = exprParser.parseName();
        this.acceptAndNextToken(SQLToken.TokenKind.TO, true);
        ISQLName newName = exprParser.parseName();
        return new SQLAlterTableRenameConstraintAction(name, newName);
    }


    public SQLAlterTableDropConstraintAction parseAlterTableDropConstraintAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.CONSTRAINT, true);

        SQLAlterTableDropConstraintAction x = new SQLAlterTableDropConstraintAction();
        x.setIfExists(exprParser.parseIfExists());
        x.setName(exprParser.parseName());
        x.setDropBehavior(exprParser.parseDropBehavior());
        x.setOnline(this.acceptAndNextToken(SQLToken.TokenKind.ONLINE));

        return x;
    }

    public SQLAlterTableDropPrimaryKeyConstraintAction parseAlterTableDropPrimaryKeyConstraintAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.PRIMARY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.KEY, true);

        SQLAlterTableDropPrimaryKeyConstraintAction x = new SQLAlterTableDropPrimaryKeyConstraintAction();
        x.setDropBehavior(exprParser.parseDropBehavior());
        x.setOnline(this.acceptAndNextToken(SQLToken.TokenKind.ONLINE));

        return x;

    }

    public SQLAlterTableDropUniqueConstraintAction parseAlterTableDropUniqueConstraintAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);
        this.acceptAndNextToken(SQLToken.TokenKind.UNIQUE, true);

        SQLAlterTableDropUniqueConstraintAction x = new SQLAlterTableDropUniqueConstraintAction();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            x.addColumn(exprParser.parseExpr());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        x.setDropBehavior(exprParser.parseDropBehavior());
        x.setOnline(this.acceptAndNextToken(SQLToken.TokenKind.ONLINE));

        return x;
    }


    // -------------------------- Partition --------------------------

    public SQLAlterTableAddPartitionAction parseAlterTableAddPartitionAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.ADD, true);
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITION, true);

        SQLAlterTableAddPartitionAction x = new SQLAlterTableAddPartitionAction();

        return x;
    }


    public ISQLAlterTableRenamePartitionAction parseAlterTableIRenamePartition() {
        this.acceptAndNextToken(SQLToken.TokenKind.RENAME, true);
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITION, true);

        AbstractSQLAlterTableRenamePartitionAction x = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.FOR)) {
            x = new SQLAlterTableRenamePartitionForAction();
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            for (; ; ) {
                ISQLExpr name = exprParser.parseExpr();
                ((SQLAlterTableRenamePartitionForAction) x).addName(name);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        } else {

            x = new SQLAlterTableRenamePartitionAction();

            ISQLExpr name = exprParser.parseExpr();
            ((SQLAlterTableRenamePartitionAction) x).setName(name);
        }


        this.acceptAndNextToken(SQLToken.TokenKind.TO, true);

        ISQLExpr newName = exprParser.parseExpr();
        x.setNewName(newName);

        return x;
    }

    public ISQLAlterTableRenamePartitionAction parseAlterTableIRenameSubPartition() {
        this.acceptAndNextToken(SQLToken.TokenKind.RENAME, true);
        this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITION, true);

        AbstractSQLAlterTableRenamePartitionAction x = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.FOR)) {
            x = new SQLAlterTableRenameSubPartitionForAction();
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            for (; ; ) {
                ISQLExpr name = exprParser.parseExpr();
                ((SQLAlterTableRenameSubPartitionForAction) x).addName(name);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        } else {

            x = new SQLAlterTableRenameSubPartitionAction();

            ISQLExpr name = exprParser.parseExpr();
            ((SQLAlterTableRenameSubPartitionAction) x).setName(name);
        }


        this.acceptAndNextToken(SQLToken.TokenKind.TO, true);

        ISQLExpr newName = exprParser.parseExpr();
        x.setNewName(newName);

        return x;
    }


    public AbstractSQLAlterTableTruncatePartitionAction parseAlterTableITruncatePartition() {
        this.acceptAndNextToken(SQLToken.TokenKind.TRUNCATE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITION, true);

        AbstractSQLAlterTableTruncatePartitionAction x = null;
        boolean paren = false;
        if (this.acceptAndNextToken(SQLToken.TokenKind.FOR)) {
            paren = this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

            x = new SQLAlterTableTruncatePartitionForAction();
        } else {

            x = new SQLAlterTableTruncatePartitionAction();
        }

        for (; ; ) {
            ISQLExpr name = exprParser.parseExpr();
            x.addName(name);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }

        if (paren) {
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        parseAlterTableITruncatePartitionOrSubPartitionOption(x);
        return x;
    }

    public AbstractSQLAlterTableTruncatePartitionAction parseAlterTableITruncatePartitions() {
        this.acceptAndNextToken(SQLToken.TokenKind.TRUNCATE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITIONS, true);

        AbstractSQLAlterTableTruncatePartitionAction x = null;
        boolean paren = false;
        if (this.acceptAndNextToken(SQLToken.TokenKind.FOR)) {
            paren = this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

            x = new SQLAlterTableTruncatePartitionsForAction();
        } else {

            x = new SQLAlterTableTruncatePartitionsAction();
        }

        for (; ; ) {
            ISQLExpr name = exprParser.parseExpr();
            x.addName(name);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }

        if (paren) {
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        parseAlterTableITruncatePartitionOrSubPartitionOption(x);
        return x;
    }

    public AbstractSQLAlterTableTruncatePartitionAction parseAlterTableITruncateSubPartition() {
        this.acceptAndNextToken(SQLToken.TokenKind.TRUNCATE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITION, true);

        AbstractSQLAlterTableTruncatePartitionAction x = null;
        boolean paren = false;
        if (this.acceptAndNextToken(SQLToken.TokenKind.FOR)) {
            paren = this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

            x = new SQLAlterTableTruncateSubPartitionForAction();
        } else {

            x = new SQLAlterTableTruncateSubPartitionAction();
        }

        for (; ; ) {
            ISQLExpr name = exprParser.parseExpr();
            x.addName(name);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }

        if (paren) {
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        parseAlterTableITruncatePartitionOrSubPartitionOption(x);
        return x;
    }

    public AbstractSQLAlterTableTruncatePartitionAction parseAlterTableITruncateSubPartitions() {
        this.acceptAndNextToken(SQLToken.TokenKind.TRUNCATE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITIONS, true);

        AbstractSQLAlterTableTruncatePartitionAction x = null;
        boolean paren = false;
        if (this.acceptAndNextToken(SQLToken.TokenKind.FOR)) {
            paren = this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

            x = new SQLAlterTableTruncateSubPartitionsForAction();
        } else {

            x = new SQLAlterTableTruncateSubPartitionsAction();
        }

        for (; ; ) {
            ISQLExpr name = exprParser.parseExpr();
            x.addName(name);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }

        if (paren) {
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        parseAlterTableITruncatePartitionOrSubPartitionOption(x);
        return x;
    }

    public void parseAlterTableITruncatePartitionOrSubPartitionOption(AbstractSQLAlterTableTruncatePartitionAction
                                                                              x) {
        if (x == null) {
            return;
        }

        AbstractSQLAlterTableTruncatePartitionAction.SQLStorageType storageType = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.DROP)) {
            storageType = AbstractSQLAlterTableTruncatePartitionAction.SQLStorageType.DROP_STORAGE;

            if (this.acceptAndNextToken(SQLToken.TokenKind.ALL)) {
                storageType = AbstractSQLAlterTableTruncatePartitionAction.SQLStorageType.DROP_ALL_STORAGE;
            }
            this.acceptAndNextToken(SQLToken.TokenKind.STORAGE, true);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.REUSE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.STORAGE, true);

            storageType = AbstractSQLAlterTableTruncatePartitionAction.SQLStorageType.REUSE_STORAGE;
        }
        x.setStorageType(storageType);

        if (this.acceptAndNextToken(SQLToken.TokenKind.CASCADE)) {
            x.setCascade(true);
        }


    }


    public AbstractSQLAlterTableSplitPartitionAction parseAlterTableISplitPartition() {
        this.acceptAndNextToken(SQLToken.TokenKind.SPLIT, true);
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITION, true);

        AbstractSQLAlterTableSplitPartitionAction x = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.FOR)) {
            x = new SQLAlterTableSplitPartitionForAction();
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            for (; ; ) {
                ISQLExpr name = exprParser.parseExpr();
                ((SQLAlterTableSplitPartitionForAction) x).addName(name);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        } else {

            x = new SQLAlterTableSplitPartitionAction();

            ISQLExpr name = exprParser.parseExpr();
            ((SQLAlterTableSplitPartitionAction) x).setName(name);
        }

        return x;
    }

    public AbstractSQLAlterTableSplitSubPartitionAction parseAlterTableISplitSubPartition() {
        this.acceptAndNextToken(SQLToken.TokenKind.SPLIT, true);
        this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITION, true);

        AbstractSQLAlterTableSplitSubPartitionAction x = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.FOR)) {
            x = new SQLAlterTableSplitSubPartitionForAction();
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            for (; ; ) {
                ISQLExpr name = exprParser.parseExpr();
                ((SQLAlterTableSplitSubPartitionForAction) x).addName(name);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        } else {

            x = new SQLAlterTableSplitSubPartitionAction();

            ISQLExpr name = exprParser.parseExpr();
            ((SQLAlterTableSplitSubPartitionAction) x).setName(name);
        }

        return x;
    }


    public AbstractSQLAlterTableMergePartitionsAction parseAlterTableIMergePartitions() {
        this.acceptAndNextToken(SQLToken.TokenKind.MERGE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.PARTITIONS, true);

        AbstractSQLAlterTableMergePartitionsAction x = null;

        ISQLExpr item = parseMergePartitionItem();

        if (this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
            x = new SQLAlterTableMergePartitionsAction();
            x.addItem(item);

            for (; ; ) {
                item = parseMergePartitionItem();
                x.addItem(item);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.TO)) {

            x = new SQLAlterTableMergePartitionsToAction();
            x.addItem(item);

            item = parseMergePartitionItem();
            x.addItem(item);

        } else {
            throw new SQLParserException();
        }

        parseAlterTableIMergePartitionsOption(x);
        return x;
    }

    void parseAlterTableIMergePartitionsOption(AbstractSQLAlterTableMergePartitionsAction x) {
        if (x == null) {
            return;
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.INTO)) {
            SQLPartitionDefinition partition = exprParser.parsePartitionDefinition();
            x.setPartition(partition);
        }
    }


    public AbstractSQLAlterTableMergeSubPartitionsAction parseAlterTableIMergeSubPartitions() {

        this.acceptAndNextToken(SQLToken.TokenKind.MERGE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITIONS, true);

        AbstractSQLAlterTableMergeSubPartitionsAction x = null;
        ISQLExpr item = parseMergePartitionItem();

        if (this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
            x = new SQLAlterTableMergeSubPartitionsAction();
            x.addItem(item);

            for (; ; ) {
                item = parseMergePartitionItem();
                x.addItem(item);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.TO)) {

            x = new SQLAlterTableMergeSubPartitionsToAction();
            x.addItem(item);

            item = parseMergePartitionItem();
            x.addItem(item);

        } else {
            throw new SQLParserException();
        }

        parseAlterTableIMergeSubPartitionsOption(x);

        return x;
    }

    public void parseAlterTableIMergeSubPartitionsOption(AbstractSQLAlterTableMergeSubPartitionsAction x) {
        if (x == null) {
            return;
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.INTO)) {
            SQLSubPartitionDefinition subPartition = exprParser.parseSubPartitionDefinition();
            x.setSubPartition(subPartition);
        }
    }


    public ISQLExpr parseMergePartitionItem() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.FOR)) {
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

            ISQLAlterTablePartitionAction.SQLForItem x = new ISQLAlterTablePartitionAction.SQLForItem();
            for (; ; ) {
                x.addName(exprParser.parseExpr());
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            return x;
        }

        return exprParser.parseExpr();
    }


    public SQLAlterTableEnableTriggerAction parseEnableTriggerAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.ENABLE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.TRIGGER, true);

        ISQLExpr name = exprParser.parseName();
        SQLAlterTableEnableTriggerAction x = new SQLAlterTableEnableTriggerAction(name);

        return x;
    }

    public SQLAlterTableDisableTriggerAction parseDisableTriggerAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.DISABLE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.TRIGGER, true);

        ISQLExpr name = exprParser.parseName();
        SQLAlterTableDisableTriggerAction x = new SQLAlterTableDisableTriggerAction(name);

        return x;
    }

    // -------------------------- Trigger --------------------------


    // -------------------------- Enable --------------------------
    public ISQLAlterTableAction parseEnableAction() {
        SQLLexer.SQLMake make = this.make();
        if (!this.acceptAndNextToken(SQLToken.TokenKind.ENABLE)) {
            return null;
        }

        // Oracle
        if (this.accept(SQLToken.TokenKind.VALIDATE)
                || this.accept(SQLToken.TokenKind.NOVALIDATE)
                || this.accept(SQLToken.TokenKind.UNIQUE)
                || this.accept(SQLToken.TokenKind.PRIMARY)
                || this.accept(SQLToken.TokenKind.CONSTRAINT)) {
            this.reset(make);
            return parseEnableDisableClause();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.TABLE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.LOCK, true);
            return new SQLAlterTableEnableTableLockAction();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.ALL)) {
            this.acceptAndNextToken(SQLToken.TokenKind.TRIGGER, true);
            return new SQLAlterTableEnableAllTriggersAction();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.CONTAINER_MAP)) {
            return new SQLAlterTableEnableContainerMapAction();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.CONTAINERS_DEFAULT)) {
            return new SQLAlterTableEnableContainersDefaultAction();
        }


        throw new SQLParserException(errorInfo());
    }

    // -------------------------- Disable --------------------------
    public ISQLAlterTableAction parseDisableAction() {
        SQLLexer.SQLMake make = this.make();
        if (!this.acceptAndNextToken(SQLToken.TokenKind.DISABLE)) {
            return null;
        }


        // Oracle
        if (this.accept(SQLToken.TokenKind.VALIDATE)
                || this.accept(SQLToken.TokenKind.NOVALIDATE)
                || this.accept(SQLToken.TokenKind.UNIQUE)
                || this.accept(SQLToken.TokenKind.PRIMARY)
                || this.accept(SQLToken.TokenKind.CONSTRAINT)) {
            this.reset(make);
            return parseEnableDisableClause();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.TABLE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.LOCK, true);
            return new SQLAlterTableDisableTableLockAction();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.ALL)) {
            this.acceptAndNextToken(SQLToken.TokenKind.TRIGGER, true);
            return new SQLAlterTableDisableAllTriggersAction();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.CONTAINER_MAP)) {
            return new SQLAlterTableDisableContainerMapAction();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.CONTAINERS_DEFAULT)) {
            return new SQLAlterTableDisableContainersDefaultAction();
        }


        throw new SQLParserException(errorInfo());
    }

    // -------------------------- Enable Disable Clause --------------------------
    public ISQLEnableDisableClause parseEnableDisableClause() {
        if (!this.accept(SQLToken.TokenKind.ENABLE)
                && !this.accept(SQLToken.TokenKind.DISABLE)) {
            return null;
        }

        SQLLexer.SQLMake make = this.make();

        ISQLEnableDisableClause x = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.ENABLE)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.UNIQUE)) {
                ISQLEnableDisableClause.SQLEnableUniqueClause unique = new ISQLEnableDisableClause.SQLEnableUniqueClause();

                this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
                for (; ; ) {
                    unique.addColumn(exprParser.parseExpr());
                    if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                        break;
                    }
                }
                this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

                x = unique;

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.PRIMARY)) {
                this.acceptAndNextToken(SQLToken.TokenKind.KEY, true);

                x = new ISQLEnableDisableClause.SQLEnablePrimaryKeyClause();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.CONSTRAINT)) {
                ISQLEnableDisableClause.SQLEnableConstraintClause constraint = new ISQLEnableDisableClause.SQLEnableConstraintClause();
                constraint.setName(exprParser.parseExpr());
                x = constraint;
            }

        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.DISABLE)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.UNIQUE)) {
                ISQLEnableDisableClause.SQLDisableUniqueClause unique = new ISQLEnableDisableClause.SQLDisableUniqueClause();

                this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
                for (; ; ) {
                    unique.addColumn(exprParser.parseExpr());
                    if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                        break;
                    }
                }
                this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

                x = unique;

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.PRIMARY)) {
                this.acceptAndNextToken(SQLToken.TokenKind.KEY, true);

                x = new ISQLEnableDisableClause.SQLDisablePrimaryKeyClause();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.CONSTRAINT)) {
                ISQLEnableDisableClause.SQLDisableConstraintClause constraint = new ISQLEnableDisableClause.SQLDisableConstraintClause();
                constraint.setName(exprParser.parseExpr());
                x = constraint;
            }
        }

        if (x != null) {
            x.setUsingIndexClause(exprParser.parseIUsingIndexClause());
            x.setExceptionsClause(exprParser.parseExceptionsClause());
            x.setCascade(this.acceptAndNextToken(SQLToken.TokenKind.CASCADE));

            if (this.acceptAndNextToken(SQLToken.TokenKind.KEEP)) {
                this.acceptAndNextToken(SQLToken.TokenKind.INDEX, true);
                x.setIndexOperator(ISQLEnableDisableClause.SQLIndexOperator.KEEP_INDEX);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.DROP)) {
                this.acceptAndNextToken(SQLToken.TokenKind.INDEX, true);
                x.setIndexOperator(ISQLEnableDisableClause.SQLIndexOperator.DROP_INDEX);
            }

            return x;
        }

        this.reset(make);
        return null;
    }


    public SQLDropTableStatement parseDrop() {
        this.acceptAndNextToken(SQLToken.TokenKind.DROP, true);

        boolean temporary = this.acceptAndNextToken(SQLToken.TokenKind.TEMPORARY);

        this.acceptAndNextToken(SQLToken.TokenKind.TABLE, true);

        boolean ifExists = exprParser.parseIfExists();

        SQLDropTableStatement x = new SQLDropTableStatement(this.dbType);
        x.setTemporary(temporary);
        x.setIfExists(ifExists);

        for (; ; ) {
            ISQLName name = exprParser.parseName();
            x.addName(name);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }

        SQLDropBehavior dropBehavior = exprParser.parseDropBehavior();
        x.setDropBehavior(dropBehavior);

        x.setPurge(this.acceptAndNextToken(SQLToken.TokenKind.PURGE));

        return x;
    }

}
