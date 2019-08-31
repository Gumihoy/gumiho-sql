package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLColumnProperty;
import com.gumihoy.sql.basic.ast.expr.common.ISQLPhysicalProperty;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.select.ISQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.SQLObjectNameTableReference;
import com.gumihoy.sql.basic.ast.expr.table.SQLObjectTableSubstitution;
import com.gumihoy.sql.basic.ast.expr.table.SQLOidClause;
import com.gumihoy.sql.basic.ast.expr.table.SQLOidIndexClause;
import com.gumihoy.sql.basic.ast.expr.table.SQLXmlTypeVirtualColumns;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.SQLAlterTableModifyOpaqueTypeAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.SQLAlterTableRenameAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableModifyColumnAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableAddPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.partition.ISQLPartitionBy;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLAlterTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLCreateTableStatement;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLTableStatementParser;
import com.gumihoy.sql.dialect.oracle.parser.OracleExprParser;

import java.util.List;

/**
 * @author kent on 2019-07-10.
 */
public class OracleTableStatementParser extends SQLTableStatementParser {

    public OracleTableStatementParser(OracleExprParser exprParser) {
        super(exprParser);
    }

    @Override
    public SQLCreateTableStatement parseCreate() {
        SQLCreateTableStatement x = new SQLCreateTableStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        SQLCreateTableStatement.SQLTableScope scope = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.GLOBAL)) {
            this.acceptAndNextToken(SQLToken.TokenKind.TEMPORARY, true);
            scope = SQLCreateTableStatement.SQLTableScope.GLOBAL_TEMPORARY;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.PRIVATE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.TEMPORARY, true);
            scope = SQLCreateTableStatement.SQLTableScope.PRIVATE_TEMPORARY;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.SHARED)) {
            scope = SQLCreateTableStatement.SQLTableScope.SHARDED;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.DUPLICATED)) {
            scope = SQLCreateTableStatement.SQLTableScope.DUPLICATED;
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

        x.setSharingClause(exprParser.parseSharingClause());


        boolean of = false;
        if (this.acceptAndNextToken(SQLToken.TokenKind.OF)) {
            of = true;
            ISQLDataType ofDataType = exprParser.parseDataType();
            x.setOfDataType(ofDataType);
        }

        if (of) {
            x.setObjectTableSubstitution(parseObjectTableSubstitution());
        }

        exprParser.parseTableElements(x.getTableElements(), x);

        x.setCollationExpr(exprParser.parseCollateClause());

        if (of && this.acceptAndNextToken(SQLToken.TokenKind.XMLTYPE)) {
            x.setXmlTypeStorage(getExprParser().parseXmlTypeStorage(true));
        }

        if (of) {
            x.setXmlSchemaSpec(getExprParser().parseXmlSchemaSpec());
            x.setXmlTypeVirtualColumn(parseXmlTypeVirtualColumns());
        }

        parseOnCommits(x);

        // oid_clause
        if (of) {
            x.setOidClause(parseOidClause());
            x.setOidIndexClause(parseOidIndexClause());
        }


        parseCreateProperties(x);

        ISQLPartitionBy partitionBy = exprParser.parsePartitionBy();
        x.setPartitionBy(partitionBy);

        x.setParallelClause(exprParser.parseParallelClause());


        if (this.acceptAndNextToken(SQLToken.TokenKind.AS)) {
            x.setAs(true);

            ISQLSelectQuery subQuery = exprParser.parseSelectQuery();
            x.setSubQuery(subQuery);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.MEMOPTIMIZE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.FOR, true);
            this.acceptAndNextToken(SQLToken.TokenKind.READ, true);
            x.setMemOptimizeForRead(true);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.PARENT)) {
            x.setParentTable(exprParser.parseName());
        }

        return x;
    }



    public SQLObjectTableSubstitution parseObjectTableSubstitution() {
        if (!this.accept(SQLToken.TokenKind.NOT)
                && !this.accept(SQLToken.TokenKind.SUBSTITUTABLE)) {
            return null;
        }

        boolean not = this.acceptAndNextToken(SQLToken.TokenKind.NOT);
        this.accept(SQLToken.TokenKind.SUBSTITUTABLE, true);
        this.accept(SQLToken.TokenKind.AT, true);
        this.accept(SQLToken.TokenKind.ALL, true);
        this.accept(SQLToken.TokenKind.LEVELS, true);

        return new SQLObjectTableSubstitution(not);
    }

    public SQLOidClause parseOidClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.OBJECT)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.IDENTIFIER, true);
        this.acceptAndNextToken(SQLToken.TokenKind.IS, true);

        SQLOidClause.SQLType type = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.SYSTEM)) {
            this.acceptAndNextToken(SQLToken.TokenKind.GENERATED, true);
            type = SQLOidClause.SQLType.SYSTEM_GENERATED;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.PRIMARY)) {
            this.acceptAndNextToken(SQLToken.TokenKind.KEY, true);
            type = SQLOidClause.SQLType.PRIMARY_KEY;

        } else {
            throw new SQLParserException(errorInfo());
        }

        return new SQLOidClause(type);
    }

    public SQLOidIndexClause parseOidIndexClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.OIDINDEX)) {
            return null;
        }

        SQLOidIndexClause x = new SQLOidIndexClause();
        if (!this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            x.setName(exprParser.parseName());
        }
        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            if (this.accept(SQLToken.TokenKind.TABLESPACE)) {
                x.addItem(exprParser.parseTableSpaceClause());

            } else if (getExprParser().isParsePhysicalAttribute()) {
                x.addItem(getExprParser().parsePhysicalAttribute());
            } else {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        return x;
    }

    public SQLXmlTypeVirtualColumns parseXmlTypeVirtualColumns() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.VIRTUAL)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.COLUMNS, true);

        SQLXmlTypeVirtualColumns x = new SQLXmlTypeVirtualColumns();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            ISQLName column = exprParser.parseName();
            this.acceptAndNextToken(SQLToken.TokenKind.AS, true);

            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            ISQLExpr expr = exprParser.parseExpr();
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            SQLXmlTypeVirtualColumns.SQLItem item = new SQLXmlTypeVirtualColumns.SQLItem(column, expr);
            x.addItem(item);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        return x;
    }

    public void parseCreateProperties(SQLCreateTableStatement x) {
        List<ISQLPhysicalProperty> physicalProperties = getExprParser().parsePhysicalProperty();
        x.addProperties(physicalProperties);

        List<ISQLColumnProperty> columnProperties = getExprParser().parseColumnProperties();
        x.addProperties(columnProperties);


    }


    @Override
    public SQLAlterTableStatement parseAlter() {
        SQLAlterTableStatement x = new SQLAlterTableStatement(this.dbType);

        this.acceptAndNextToken(SQLToken.TokenKind.ALTER, true);
        this.acceptAndNextToken(SQLToken.TokenKind.TABLE, true);

        ISQLName name = exprParser.parseName();
        SQLObjectNameTableReference tableReference = new SQLObjectNameTableReference(name);
        x.setTableReference(tableReference);

        x.setMemOptimizeRead(parseMemOptimizeRead());

        for (; ; ) {
            ISQLExpr action = parseAlterAction();
            if (action == null) {
                break;
            }
            x.addAction(action);
        }

        return x;
    }


    @Override
    public ISQLAlterTableAction parseAlterAction() {
        SQLLexer.SQLMake make = this.make();

        if (this.acceptAndNextToken(SQLToken.TokenKind.ADD)) {

            // ------------- COLUMN -----
            if (this.acceptAndNextToken(SQLToken.TokenKind.COLUMN)) {
                this.reset(make);
                return parseAlterTableAddColumnAction();

            }

            // ------------- Constraint -----
            if (exprParser.isTableConstraint()) {
                this.reset(make);
                return parseAlterTableAddTableConstraintAction();

            }

            // ------------- PARTITION -----
            if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
                this.reset(make);
                return parseAlterTableAddPartitionAction();
            }


            if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
                if (this.acceptAndNextToken(SQLToken.TokenKind.PERIOD)) {
                    this.reset(make);
                    return parseAlterTableAddPeriodAction();
                }

                if (exprParser.isTableConstraint()) {
                    this.reset(make);
                    return parseAlterTableAddTableConstraintAction();
                }
                if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
                    this.reset(make);
                    return parseAlterTableAddPartitionAction();
                }

                if (this.acceptAndNextToken(SQLToken.TokenKind.OVERFLOW)) {

                } else {
                    this.reset(make);
                    return parseAlterTableAddColumnAction();
                }

            }

            throw new SQLParserException(errorInfo());
        }



        if (this.acceptAndNextToken(SQLToken.TokenKind.MODIFY)) {
            // ------------- COLUMN -----
            if (this.acceptAndNextToken(SQLToken.TokenKind.COLUMN)) {
                this.reset(make);
                return parseAlterTableModifyColumnAction();
            }

            if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
                this.reset(make);
                return parseAlterTableModifyColumnsAction();
            }

            // ------------- CONSTRAINT -----
            if (this.acceptAndNextToken(SQLToken.TokenKind.CONSTRAINT)) {
                this.reset(make);
                return parseAlterTableModifyConstraintAction();
            }

            if (this.acceptAndNextToken(SQLToken.TokenKind.PRIMARY)) {
                this.reset(make);
                return parseAlterTableModifyPrimaryKeyConstraintAction();
            }

            if (this.acceptAndNextToken(SQLToken.TokenKind.UNIQUE)) {
                this.reset(make);
                return parseAlterTableModifyUniqueConstraintAction();
            }

            // ------------- COLUMN -----

            throw new SQLParserException(errorInfo());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.RENAME)) {
            // ------------- CONSTRAINT -----
            if (this.acceptAndNextToken(SQLToken.TokenKind.CONSTRAINT)) {
                this.reset(make);
                return parseAlterTableRenameConstraintAction();

            }

            this.reset(make);
            return parseAlterTableRenameAction();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.DROP)) {

            // ------------- COLUMN -----
            if (this.acceptAndNextToken(SQLToken.TokenKind.COLUMN)) {
                this.reset(make);
                return parseAlterTableDropColumnAction();
            }
            if (this.acceptAndNextToken(SQLToken.TokenKind.UNUSED)) {
                this.reset(make);
                return parseAlterTableDropUnusedColumnsAction();
            }
            if (this.acceptAndNextToken(SQLToken.TokenKind.COLUMNS)) {
                this.reset(make);
                return parseAlterTableDropColumnsContinueAction();
            }


            // ------------- CONSTRAINT -----
            if (this.acceptAndNextToken(SQLToken.TokenKind.CONSTRAINT)) {
                this.reset(make);
                return parseAlterTableDropConstraintAction();
            }
            if (this.acceptAndNextToken(SQLToken.TokenKind.PRIMARY)) {
                this.reset(make);
                return parseAlterTableDropPrimaryKeyConstraintAction();
            }
            if (this.acceptAndNextToken(SQLToken.TokenKind.UNIQUE)) {
                this.reset(make);
                return parseAlterTableDropUniqueConstraintAction();
            }




            if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
                if (this.acceptAndNextToken(SQLToken.TokenKind.PERIOD)) {
                    this.reset(make);
                    return parseAlterTableDropPeriodAction();
                }

                this.reset(make);
                return parseAlterTableDropColumnsAction();
            }

            throw new SQLParserException(errorInfo());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.SET)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.UNUSED)) {
                if (this.acceptAndNextToken(SQLToken.TokenKind.COLUMN)) {
                    this.reset(make);
                    return parseAlterTableSetUnusedColumnAction();
                }

                if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
                    this.reset(make);
                    return parseAlterTableSetUnusedColumnsAction();
                }

            }

            throw new SQLParserException(errorInfo());
        }


        if (this.accept(SQLToken.TokenKind.ENABLE)) {

        }
        if (this.accept(SQLToken.TokenKind.DISABLE)) {

        }


        return null;
    }


    // -------------------------- Column --------------------------

    public SQLAlterTableModifyColumnAction parseAlterTableModifyColumnAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.MODIFY, true);

        SQLAlterTableModifyColumnAction x = new SQLAlterTableModifyColumnAction();
        x.setColumn_(this.acceptAndNextToken(SQLToken.TokenKind.COLUMN, true));

        x.setSubstitution(parseObjectTableSubstitution());
        x.setForce(this.acceptAndNextToken(SQLToken.TokenKind.FORCE));

        return x;
    }


    // -------------------------- Partition --------------------------

    public SQLAlterTableAddPartitionAction parseAlterTableAddPartitionAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.ADD, true);

        SQLAlterTableAddPartitionAction x = new SQLAlterTableAddPartitionAction();
        for (;;) {
            x.addPartition(exprParser.parsePartitionDefinition());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.BEFORE)) {
            x.setBefore(exprParser.parseExpr());
        }



        return x;
    }


    public SQLAlterTableRenameAction parseAlterTableRenameAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.RENAME, true);

        SQLAlterTableRenameAction x = new SQLAlterTableRenameAction();

        x.setName(exprParser.parseName());

        this.acceptAndNextToken(SQLToken.TokenKind.TO, true);
        x.setOperator(SQLAlterTableRenameAction.SQLRenameOperator.TO);

        x.setNewName(exprParser.parseName());

        return x;
    }


    public SQLAlterTableModifyOpaqueTypeAction parseModifyOpaqueTypeAction() {
        this.acceptAndNextToken(SQLToken.TokenKind.MODIFY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.OPAQUE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.TYPE, true);

        SQLAlterTableModifyOpaqueTypeAction x = new SQLAlterTableModifyOpaqueTypeAction();
        x.setColumn(exprParser.parseExpr());

        this.acceptAndNextToken(SQLToken.TokenKind.SCOPE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            x.addName(exprParser.parseExpr());
            if (this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        this.acceptAndNextToken(SQLToken.TokenKind.UNPACKAGE, true);

        return x;
    }


    @Override
    public OracleExprParser getExprParser() {
        return (OracleExprParser) exprParser;
    }
}
