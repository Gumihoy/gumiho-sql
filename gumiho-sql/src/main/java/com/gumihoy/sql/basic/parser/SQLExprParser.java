package com.gumihoy.sql.basic.parser;

import static com.gumihoy.sql.basic.parser.SQLToken.TokenKind.*;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.enums.*;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.*;
import com.gumihoy.sql.basic.ast.expr.condition.*;
import com.gumihoy.sql.basic.ast.expr.correlation.SQLCorrelationNewName;
import com.gumihoy.sql.basic.ast.expr.correlation.SQLCorrelationOldName;
import com.gumihoy.sql.basic.ast.expr.correlation.SQLCorrelationParentName;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.SQLDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.any.SQLAnyDataDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.any.SQLAnyDataSetDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.any.SQLAnyTypeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.bool.SQLBoolDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.bool.SQLBooleanDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.ISQLDateTimeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLDateDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLDateTimeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLTimeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLTimestampDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLYearDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.interval.SQLIntervalDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.*;
import com.gumihoy.sql.basic.ast.expr.datatype.percent.SQLPercentRowTypeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.percent.SQLPercentTypeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.reference.ISQLRefDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.reference.SQLRefCursorDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.reference.SQLRefDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.*;
import com.gumihoy.sql.basic.ast.expr.datatype.xml.SQLUriTypeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.xml.SQLXmlTypeDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.*;
import com.gumihoy.sql.basic.ast.expr.insert.ISQLInsertValuesClause;
import com.gumihoy.sql.basic.ast.expr.literal.SQLBooleanLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.datetime.SQLDateLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.datetime.SQLTimeLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.datetime.SQLTimestampLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.interval.SQLIntervalLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLBinaryDoubleLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLBinaryFloatLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLBitValueLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLDecimalLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLFloatingPointLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLHexaDecimalLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLIntegerLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.string.SQLNStringLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.string.SQLStringLiteral;
import com.gumihoy.sql.basic.ast.expr.method.SQLExtractFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLMethodInvocation;
import com.gumihoy.sql.basic.ast.expr.operator.SQLBinaryOperatorExpr;
import com.gumihoy.sql.basic.ast.expr.operator.SQLUnaryOperatorExpr;
import com.gumihoy.sql.basic.ast.expr.select.*;
import com.gumihoy.sql.basic.ast.expr.select.group.SQLGroupByClause;
import com.gumihoy.sql.basic.ast.expr.select.group.SQLHavingClause;
import com.gumihoy.sql.basic.ast.expr.select.limit.ISQLLimitClause;
import com.gumihoy.sql.basic.ast.expr.select.limit.SQLLimitOffsetClause;
import com.gumihoy.sql.basic.ast.expr.select.limit.SQLOffsetFetchClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByItem;
import com.gumihoy.sql.basic.ast.expr.table.ISQLIdentityOption;
import com.gumihoy.sql.basic.ast.expr.table.element.AbstractSQLReferencesConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.ISQLTableElement;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLColumnDefinition;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLLikeClause;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.ISQLConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.*;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.SQLExceptionsClause;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.ISQLTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLCheckTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLForeignKeyTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLPrimaryKeyTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLRefWithRowIdTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLScopeForTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLUniqueTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.partition.*;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.ISQLSubPartitionBy;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionByHash;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionByKey;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionByList;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionByRange;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionDefinition;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubpartitionTemplate;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.ISQLPartitionValues;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValues;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValuesIn;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValuesLessThan;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValuesLessThanMaxValue;
import com.gumihoy.sql.basic.ast.expr.update.ISQLUpdateSetClause;
import com.gumihoy.sql.basic.ast.statement.ISQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLAlterDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLCreateDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLDropDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.databaselink.SQLAlterDatabaseLinkStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.databaselink.SQLCreateDatabaseLinkStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.databaselink.SQLDropDatabaseLinkStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLAlterFunctionStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLCreateFunctionStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLDropFunctionStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLAlterIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLCreateIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLDropIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.materializedview.SQLAlterMaterializedViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.materializedview.SQLCreateMaterializedViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.materializedview.SQLDropMaterializedViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.package_.SQLAlterPackageStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.package_.SQLCreatePackageStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.package_.SQLDropPackageStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.packagebody.SQLAlterPackageBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.packagebody.SQLCreatePackageBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.packagebody.SQLDropPackageBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.procedure.SQLAlterProcedureStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.procedure.SQLCreateProcedureStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.procedure.SQLDropProcedureStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.role.SQLAlterRoleStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.role.SQLCreateRoleStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.role.SQLDropRoleStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.schema.SQLAlterSchemaStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.schema.SQLCreateSchemaStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.schema.SQLDropSchemaStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.sequence.SQLAlterSequenceStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.sequence.SQLCreateSequenceStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.sequence.SQLDropSequenceStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.synonym.SQLAlterSynonymStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.synonym.SQLCreateSynonymStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.synonym.SQLDropSynonymStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLAlterTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLCreateTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLDropTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLAlterTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLCreateTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLDropTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.type.SQLAlterTypeStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.type.SQLCreateTypeStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.type.SQLDropTypeStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.typebody.SQLAlterTypeBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.typebody.SQLCreateTypeBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.typebody.SQLDropTypeBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.user.SQLAlterUserStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.user.SQLCreateUserStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.user.SQLDropUserStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLAlterViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLCreateViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLDropViewStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLCallStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLDeleteStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLUpdateStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.*;
import com.gumihoy.sql.basic.parser.statement.*;
import com.gumihoy.sql.config.SQLParseConfig;
import com.gumihoy.sql.enums.DBType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author kent on 2019-06-15.
 */
public class SQLExprParser extends SQLParser {

    protected static final Set<Long> NO_ARGUMENT_FUNCTION = new HashSet<>();
    protected static final Set<Long> SPECIAL_FUNCTION = new HashSet<>();
    protected static final Set<Long> AGGREGATE_FUNCTION = new HashSet<>();

    protected Set<Long> noArgumentFunctionLowerHash = NO_ARGUMENT_FUNCTION;
    protected Set<Long> specialFunctionLowerHash = SPECIAL_FUNCTION;
    protected Set<Long> aggregateFunctionLowerHash = AGGREGATE_FUNCTION;

    public SQLExprParser(String sql) {
        this(sql, DBType.SQL);
    }

    public SQLExprParser(String sql, DBType dbType) {
        this(new SQLLexer(sql, dbType));
    }

    public SQLExprParser(String sql, DBType dbType, SQLParseConfig config) {
        this(new SQLLexer(sql, dbType, config));
    }

    public SQLExprParser(SQLLexer lexer) {
        this(lexer, null);
    }

    public SQLExprParser(SQLLexer lexer, SQLParseConfig config) {
        super(lexer, config);
    }

    {
        SPECIAL_FUNCTION.add(SQLKeyWord.EXTRACT.lowerHash);
    }


    public void parseStatements(List<ISQLObject> statementList, ISQLObject parent) {
        for (; ; ) {
            if (this.accept(SQLToken.TokenKind.EOF)) {
                break;
            }

            if (this.acceptAndNextToken(SEMI)) {
                if (statementList.size() > 0) {
                    ISQLObject lastStmt = statementList.get(statementList.size() - 1);
                    lastStmt.setAfterSemi(true);
                }
                continue;
            }

            if (this.acceptAndNextToken(SQLToken.TokenKind.SLASH)) {
                continue;
            }

            ISQLStatement stmt = parseStatement();
            if (stmt == null) {
                break;
            }
            stmt.setParent(parent);
            statementList.add(stmt);
        }

    }

    public ISQLStatement parseStatement() {

        // DDL
        if (this.accept(ALTER)) {
            return parseAlterStatement();
        }
        if (this.accept(ANALYZE)) {
            return parseAnalyzeStatement();
        }
        if (this.accept(ASSOCIATE)) {
            return null;
        }
        if (this.accept(AUDIT)) {
            return null;
        }
        if (this.accept(COMMENT)) {
            return parseCommentStatement();
        }
        if (this.accept(CREATE)) {
            return parseCreateStatement();
        }
        if (this.accept(DROP)) {
            return parseDropStatement();
        }
        if (this.accept(RENAME)) {
            return null;
        }

        // DML
        if (this.accept(CALL)) {
            return parseCallStatement();
        }
        if (this.accept(DELETE)) {
            return parseDeleteStatement();
        }
        if (this.accept(EXPLAIN)) {
            return parseExplainStatement();
        }
        if (this.accept(INSERT)) {
            return parseInsertStatement();
        }
        if (this.accept(LOCK)) {
            return parseLockStatement();
        }
        if (this.accept(WITH)) {
            return parseWithStatement();
        }
        if (this.accept(SELECT)) {
            return parseSelectStatement();
        }
        if (this.accept(LPAREN)) {
            return parseLParenStatement();
        }
        if (this.accept(UPDATE)) {
            return parseUpdateStatement();
        }


        // FCL
        if (this.accept(CASE)) {
            return parseCaseStatement();
        }
        if (this.accept(CLOSE)) {
            return parseCloseStatement();
        }
        if (this.accept(CONTINUE)) {
            return parseContinueStatement();
        }
        if (this.accept(EXIT)) {
            return parseExitStatement();
        }
        if (this.accept(FETCH)) {
            return parseFetchStatement();
        }
        if (this.accept(SQLToken.TokenKind.FOR)) {
            return parseForLoopStatement();
        }
        if (this.accept(GOTO)) {
            return parseGotoStatement();
        }
        if (this.accept(IF)) {
            return parseIfStatement();
        }
        if (this.accept(LOOP)) {
            return parseLoopStatement();
        }
        if (this.accept(NULL)) {
            return parseNullStatement();
        }
        if (this.accept(OPEN)) {
            return parseOpenStatement();
        }
        if (this.accept(PIPE)) {
            return parsePipeRowStatement();
        }
        if (this.accept(RAISE)) {
            return parseRaiseStatement();
        }
        if (this.accept(REPEAT)) {
            return parseRepeatStatement();
        }
        if (this.accept(RETURN)) {
            return parseReturnStatement();
        }
        if (this.accept(WHILE)) {
            return parseWhileLoopStatement();
        }

        // TC
        if (this.accept(COMMIT)) {
            return parseCommitStatement();
        }
        if (this.accept(ROLLBACK)) {
            return parseRollbackStatement();
        }
        if (this.accept(SAVEPOINT)) {
            return null;
        }

        // SC
        if (this.accept(SET)) {
            return parseSetStatement();
        }

        SQLLexer.SQLMake make = this.make();
        ISQLExpr expr = parseExpr();
        if (this.accept(SQLToken.TokenKind.ASSIGN)) {
            this.reset(make);
            return parseAssignmentStatement();
        }


        if (this.accept(SQLToken.TokenKind.LOOP)) {
            this.reset(make);
            return parseLoopStatement();

        }
        if (this.accept(SQLToken.TokenKind.WHILE)) {
            this.reset(make);
            return parseWhileStatement();
        }
        this.reset(make);
        return null;
    }

    public ISQLStatement parseAlterStatement() {
        SQLLexer.SQLMake make = lexer.make();

        acceptAndNextToken(SQLToken.TokenKind.ALTER, true);

        // DATABASE LINK
        acceptAndNextToken(SQLToken.TokenKind.SHARED);

        // DATABASE LINK、SYNONYM
        acceptAndNextToken(SQLToken.TokenKind.PUBLIC);

        // VIEW
        parseAlgorithmOptionExpr();
        parseDefinerOptionExpr();
        parseSQLSecurity();


        // DATABASE、DATABASE LINK
        if (this.acceptAndNextToken(SQLToken.TokenKind.DATABASE)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.LINK)) {
                this.reset(make);
                return parseAlterDatabaseLinkStatement();
            }

            this.reset(make);
            return parseAlterDatabaseStatement();
        }

        // FUNCTION
        if (this.acceptAndNextToken(SQLToken.TokenKind.FUNCTION)) {
            this.reset(make);
            return parseAlterFunctionStatement();
        }

        // INDEX
        if (this.acceptAndNextToken(SQLToken.TokenKind.INDEX)) {
            this.reset(make);
            return parseAlterIndexStatement();
        }

        //  MATERIALIZED VIEW
        if (this.acceptAndNextToken(SQLToken.TokenKind.MATERIALIZED)) {
            this.acceptAndNextToken(SQLToken.TokenKind.VIEW, true);

            if (this.acceptAndNextToken(SQLToken.TokenKind.LOG)) {
                this.reset(make);
                return parseAlterFunctionStatement();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.ZONEMAP)) {
                return null;
            }

            this.reset(make);
            return parseAlterMaterializedViewStatement();
        }

        // PACKAGE
        if (this.acceptAndNextToken(SQLToken.TokenKind.PACKAGE)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.BODY)) {
                this.reset(make);
                return parseAlterPackageBodyStatement();
            }
            this.reset(make);
            return parseAlterPackageStatement();
        }


        // SCHEMA
        if (this.acceptAndNextToken(SQLToken.TokenKind.SCHEMA)) {
            this.reset(make);
            return parseAlterSchemaStatement();
        }

        // PROCEDURE
        if (this.acceptAndNextToken(SQLToken.TokenKind.PROCEDURE)) {
            this.reset(make);
            return parseAlterProcedureStatement();
        }

        // ROLE
        if (this.acceptAndNextToken(SQLToken.TokenKind.ROLE)) {
            this.reset(make);
            return parseAlterRoleStatement();
        }

        // SEQUENCE
        if (this.acceptAndNextToken(SQLToken.TokenKind.SEQUENCE)) {
            this.reset(make);
            return parseAlterSequenceStatement();
        }

        // SESSION
        if (this.acceptAndNextToken(SQLToken.TokenKind.SESSION)) {
            this.reset(make);
            return parseAlterSynonymStatement();
        }

        // SYNONYM
        if (this.acceptAndNextToken(SQLToken.TokenKind.SYNONYM)) {
            this.reset(make);
            return parseAlterSynonymStatement();
        }

        // SYSTEM
        if (this.acceptAndNextToken(SQLToken.TokenKind.SYSTEM)) {
            this.reset(make);
            return parseAlterTriggerStatement();
        }

        // TABLE
        if (this.acceptAndNextToken(SQLToken.TokenKind.TABLE)) {
            this.reset(make);
            return parseAlterTableStatement();
        }

        // TRIGGER
        if (this.acceptAndNextToken(SQLToken.TokenKind.TRIGGER)) {
            this.reset(make);
            return parseAlterTriggerStatement();
        }

        // TYPE
        if (this.acceptAndNextToken(SQLToken.TokenKind.TYPE)) {
            this.reset(make);
            return parseAlterTypeStatement();
        }

        // USER
        if (this.acceptAndNextToken(SQLToken.TokenKind.USER)) {
            this.reset(make);
            return parseAlterUserStatement();
        }

        // VIEW
        if (this.acceptAndNextToken(SQLToken.TokenKind.VIEW)) {
            this.reset(make);
            return parseAlterViewStatement();
        }

        throw new SQLParserException("TODO:" + lexer.token);
    }


    public ISQLStatement parseAnalyzeStatement() {
        throw new SQLParserException("TODO:" + lexer.token);
    }

    public ISQLStatement parseCommentStatement() {
        SQLCommentStatementParser parser = createCommentStatementParser();
        return parser.parse();
    }

    public SQLCommentStatementParser createCommentStatementParser() {
        return new SQLCommentStatementParser(this);
    }


    public ISQLStatement parseCreateStatement() {
        SQLLexer.SQLMake make = this.make();

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        // Database Link
        acceptAndNextToken(SQLToken.TokenKind.SHARED);
        acceptAndNextToken(SQLToken.TokenKind.PUBLIC);

        // Table
        acceptAndNextToken(SQLToken.TokenKind.GLOBAL);
        acceptAndNextToken(SQLToken.TokenKind.PRIVATE);
        acceptAndNextToken(SQLToken.TokenKind.TEMPORARY);
        acceptAndNextToken(SQLToken.TokenKind.DUPLICATED);

        // Function、
        // Package、Package Body
        // Type、Type BODY
        // VIEW
        parseOrReplace();

        // VIEW
        this.acceptAndNextToken(NO);
        this.acceptAndNextToken(FORCE);
        this.parseAlgorithmOptionExpr();
        this.parseDefinerOptionExpr();
        this.parseSQLSecurity();


        // Function
        // Package、Package Body
        // Type、Type BOdy
        // VIEW
        parseEditionAbleType();


        // DATABASE、DATABASE LINK
        if (this.acceptAndNextToken(SQLToken.TokenKind.DATABASE)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.LINK)) {
                this.reset(make);
                return parseCreateDatabaseLinkStatement();
            }

            this.reset(make);
            return parseCreateDatabaseStatement();
        }

        // FUNCTION
        if (this.acceptAndNextToken(SQLToken.TokenKind.FUNCTION)) {
            this.reset(make);
            return parseCreateFunctionStatement();
        }

        // INDEX
        if (this.acceptAndNextToken(SQLToken.TokenKind.INDEX)) {
            this.reset(make);
            return parseCreateIndexStatement();
        }

        // MATERIALIZED VIEW
        if (this.acceptAndNextToken(SQLToken.TokenKind.MATERIALIZED)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.VIEW)) {

                if (this.acceptAndNextToken(SQLToken.TokenKind.LOG)) {
                    this.reset(make);
                    return parseCreateMaterializedViewStatement();
                }

                this.reset(make);
                return parseCreateMaterializedViewStatement();
            }

            if (this.acceptAndNextToken(SQLToken.TokenKind.ZONEMAP)) {
                throw new SQLParserException("TODO.");
            }

        }

        // PACKAGE、PACKAGE BODY
        if (this.acceptAndNextToken(SQLToken.TokenKind.PACKAGE)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.BODY)) {
                this.reset(make);
                return parseCreatePackageBodyStatement();
            }
            this.reset(make);
            return parseCreatePackageStatement();
        }

        // PROCEDURE
        if (this.acceptAndNextToken(SQLToken.TokenKind.PROCEDURE)) {
            this.reset(make);
            return parseCreateProcedureStatement();
        }

        // ROLE
        if (this.acceptAndNextToken(SQLToken.TokenKind.ROLE)) {
            this.reset(make);
            return parseCreateRoleStatement();
        }

        // SCHEMA
        if (this.acceptAndNextToken(SQLToken.TokenKind.SCHEMA)) {
            this.reset(make);
            return parseCreateSchemaStatement();
        }

        // SEQUENCE
        if (this.acceptAndNextToken(SQLToken.TokenKind.SEQUENCE)) {
            this.reset(make);
            return parseCreateSequenceStatement();
        }
        // SYNONYM
        if (this.acceptAndNextToken(SQLToken.TokenKind.SYNONYM)) {
            this.reset(make);
            return parseCreateSynonymStatement();
        }
        // TABLE
        if (this.acceptAndNextToken(SQLToken.TokenKind.TABLE)) {
            this.reset(make);
            return parseCreateTableStatement();
        }

        // TRIGGER
        if (this.acceptAndNextToken(SQLToken.TokenKind.TRIGGER)) {
            this.reset(make);
            return parseCreateTriggerStatement();
        }

        // TYPE、TYPE BODY
        if (this.acceptAndNextToken(SQLToken.TokenKind.TYPE)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.BODY)) {
                this.reset(make);
                return parseCreateTypeBodyStatement();
            }
            this.reset(make);
            return parseCreateTypeStatement();
        }

        // USER
        if (this.acceptAndNextToken(SQLToken.TokenKind.USER)) {
            this.reset(make);
            return parseCreateUserStatement();
        }

        // VIEW
        if (this.acceptAndNextToken(SQLToken.TokenKind.VIEW)) {
            this.reset(make);
            return parseCreateViewStatement();
        }

        throw new SQLParserException("TODO:" + lexer.token);
    }


    public ISQLStatement parseDropStatement() {
        SQLLexer.SQLMake make = lexer.make();

        acceptAndNextToken(SQLToken.TokenKind.DROP, true);

        // SYNONYM
        acceptAndNextToken(SQLToken.TokenKind.PUBLIC);

        // Table
        acceptAndNextToken(SQLToken.TokenKind.TEMPORARY);

        if (acceptAndNextToken(SQLToken.TokenKind.DATABASE)) {

            if (acceptAndNextToken(SQLToken.TokenKind.LINK)) {
                this.reset(make);
                return parseDropDatabaseLinkStatement();
            }

            this.reset(make);
            return parseDropDatabaseStatement();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.FUNCTION)) {
            this.reset(make);
            return parseDropFunctionStatement();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.INDEX)) {
            this.reset(make);
            return parseDropIndexStatement();
        }

        // MATERIALIZED
        if (this.acceptAndNextToken(SQLToken.TokenKind.MATERIALIZED)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.VIEW)) {

                if (this.acceptAndNextToken(SQLToken.TokenKind.LOG)) {
                    this.reset(make);
                    return parseDropMaterializedViewStatement();
                }

                this.reset(make);
                return parseDropMaterializedViewStatement();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.ZONEMAP)) {


            }


            throw new SQLParserException(errorInfo());
        }

        // PACKAGE、PACKAGE BODY
        if (this.acceptAndNextToken(SQLToken.TokenKind.PACKAGE)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.BODY)) {
                this.reset(make);
                return parseDropPackageBodyStatement();
            }

            this.reset(make);
            return parseDropPackageStatement();
        }

        // PROCEDURE
        if (this.acceptAndNextToken(SQLToken.TokenKind.PROCEDURE)) {
            this.reset(make);
            return parseDropProcedureStatement();
        }

        // ROLE
        if (this.acceptAndNextToken(SQLToken.TokenKind.ROLE)) {
            this.reset(make);
            return parseDropRoleStatement();
        }

        // SCHEMA
        if (this.acceptAndNextToken(SQLToken.TokenKind.SCHEMA)) {
            this.reset(make);
            return parseDropSchemaStatement();
        }

        // SEQUENCE
        if (this.acceptAndNextToken(SQLToken.TokenKind.SEQUENCE)) {
            this.reset(make);
            return parseDropSequenceStatement();
        }
        // SYNONYM
        if (this.acceptAndNextToken(SQLToken.TokenKind.SYNONYM)) {
            this.reset(make);
            return parseDropSynonymStatement();
        }
        // TABLE
        if (this.acceptAndNextToken(SQLToken.TokenKind.TABLE)) {
            this.reset(make);
            return parseDropTableStatement();
        }

        // TRIGGER
        if (this.acceptAndNextToken(SQLToken.TokenKind.TRIGGER)) {
            this.reset(make);
            return parseDropTriggerStatement();
        }

        // TYPE、TYPE BODY
        if (this.acceptAndNextToken(SQLToken.TokenKind.TYPE)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.BODY)) {
                this.reset(make);
                return parseDropTypeBodyStatement();
            }

            this.reset(make);
            return parseDropTypeStatement();
        }

        // USER
        if (this.acceptAndNextToken(SQLToken.TokenKind.USER)) {
            this.reset(make);
            return parseDropUserStatement();
        }

        // VIEW
        if (this.acceptAndNextToken(SQLToken.TokenKind.VIEW)) {
            this.reset(make);
            return parseDropViewStatement();
        }

        throw new SQLParserException("TODO:" + errorInfo());

    }

    protected SQLCreateDatabaseStatement parseCreateDatabaseStatement() {
        SQLDatabaseStatementParser parser = createDatabaseStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterDatabaseStatement parseAlterDatabaseStatement() {
        SQLDatabaseStatementParser parser = createDatabaseStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropDatabaseStatement parseDropDatabaseStatement() {
        SQLDatabaseStatementParser parser = createDatabaseStatementParser();
        return parser.parseDrop();
    }

    protected SQLDatabaseStatementParser createDatabaseStatementParser() {
        return new SQLDatabaseStatementParser(this);
    }


    protected SQLCreateDatabaseLinkStatement parseCreateDatabaseLinkStatement() {
        SQLDatabaseLinkStatementParser parser = createDatabaseLinkStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterDatabaseLinkStatement parseAlterDatabaseLinkStatement() {
        SQLDatabaseLinkStatementParser parser = createDatabaseLinkStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropDatabaseLinkStatement parseDropDatabaseLinkStatement() {
        SQLDatabaseLinkStatementParser parser = createDatabaseLinkStatementParser();
        return parser.parseDrop();
    }

    protected SQLDatabaseLinkStatementParser createDatabaseLinkStatementParser() {
        return new SQLDatabaseLinkStatementParser(this);
    }


    protected SQLCreateFunctionStatement parseCreateFunctionStatement() {
        SQLFunctionStatementParser parser = createFunctionStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterFunctionStatement parseAlterFunctionStatement() {
        SQLFunctionStatementParser parser = createFunctionStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropFunctionStatement parseDropFunctionStatement() {
        SQLFunctionStatementParser parser = createFunctionStatementParser();
        return parser.parseDrop();
    }

    protected SQLFunctionStatementParser createFunctionStatementParser() {
        return new SQLFunctionStatementParser(this);
    }


    protected SQLCreateIndexStatement parseCreateIndexStatement() {
        SQLIndexStatementParser parser = createIndexStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterIndexStatement parseAlterIndexStatement() {
        SQLIndexStatementParser parser = createIndexStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropIndexStatement parseDropIndexStatement() {
        SQLIndexStatementParser parser = createIndexStatementParser();
        return parser.parseDrop();
    }

    protected SQLIndexStatementParser createIndexStatementParser() {
        return new SQLIndexStatementParser(this);
    }


    protected SQLCreateMaterializedViewStatement parseCreateMaterializedViewStatement() {
        SQLMaterializedViewStatementParser parser = createMaterializedViewStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterMaterializedViewStatement parseAlterMaterializedViewStatement() {
        SQLMaterializedViewStatementParser parser = createMaterializedViewStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropMaterializedViewStatement parseDropMaterializedViewStatement() {
        SQLMaterializedViewStatementParser parser = createMaterializedViewStatementParser();
        return parser.parseDrop();
    }

    protected SQLMaterializedViewStatementParser createMaterializedViewStatementParser() {
        return new SQLMaterializedViewStatementParser(this);
    }


    protected SQLCreatePackageStatement parseCreatePackageStatement() {
        SQLPackageStatementParser parser = createPackageStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterPackageStatement parseAlterPackageStatement() {
        SQLPackageStatementParser parser = createPackageStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropPackageStatement parseDropPackageStatement() {
        SQLPackageStatementParser parser = createPackageStatementParser();
        return parser.parseDrop();
    }

    protected SQLPackageStatementParser createPackageStatementParser() {
        return new SQLPackageStatementParser(this);
    }


    protected SQLCreatePackageBodyStatement parseCreatePackageBodyStatement() {
        SQLPackageBodyStatementParser parser = createPackageBodyStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterPackageBodyStatement parseAlterPackageBodyStatement() {
        SQLPackageBodyStatementParser parser = createPackageBodyStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropPackageBodyStatement parseDropPackageBodyStatement() {
        SQLPackageBodyStatementParser parser = createPackageBodyStatementParser();
        return parser.parseDrop();
    }

    protected SQLPackageBodyStatementParser createPackageBodyStatementParser() {
        return new SQLPackageBodyStatementParser(this);
    }


    protected SQLCreateProcedureStatement parseCreateProcedureStatement() {
        SQLProcedureStatementParser parser = createProcedureStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterProcedureStatement parseAlterProcedureStatement() {
        SQLProcedureStatementParser parser = createProcedureStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropProcedureStatement parseDropProcedureStatement() {
        SQLProcedureStatementParser parser = createProcedureStatementParser();
        return parser.parseDrop();
    }

    protected SQLProcedureStatementParser createProcedureStatementParser() {
        return new SQLProcedureStatementParser(this);
    }


    protected SQLCreateRoleStatement parseCreateRoleStatement() {
        SQLRoleStatementParser parser = createRoleStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterRoleStatement parseAlterRoleStatement() {
        SQLRoleStatementParser parser = createRoleStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropRoleStatement parseDropRoleStatement() {
        SQLRoleStatementParser parser = createRoleStatementParser();
        return parser.parseDrop();
    }

    protected SQLRoleStatementParser createRoleStatementParser() {
        return new SQLRoleStatementParser(this);
    }


    protected SQLCreateSchemaStatement parseCreateSchemaStatement() {
        SQLSchemaStatementParser parser = createSchemaStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterSchemaStatement parseAlterSchemaStatement() {
        SQLSchemaStatementParser parser = createSchemaStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropSchemaStatement parseDropSchemaStatement() {
        SQLSchemaStatementParser parser = createSchemaStatementParser();
        return parser.parseDrop();
    }

    protected SQLSchemaStatementParser createSchemaStatementParser() {
        return new SQLSchemaStatementParser(this);
    }


    // SEQUENCE
    protected SQLCreateSequenceStatement parseCreateSequenceStatement() {
        SQLSequenceStatementParser parser = createSequenceStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterSequenceStatement parseAlterSequenceStatement() {
        SQLSequenceStatementParser parser = createSequenceStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropSequenceStatement parseDropSequenceStatement() {
        SQLSequenceStatementParser parser = createSequenceStatementParser();
        return parser.parseDrop();
    }

    protected SQLSequenceStatementParser createSequenceStatementParser() {
        return new SQLSequenceStatementParser(this);
    }

    // SYNONYM
    protected SQLCreateSynonymStatement parseCreateSynonymStatement() {
        SQLSynonymStatementParser parser = createSynonymStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterSynonymStatement parseAlterSynonymStatement() {
        SQLSynonymStatementParser parser = createSynonymStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropSynonymStatement parseDropSynonymStatement() {
        SQLSynonymStatementParser parser = createSynonymStatementParser();
        return parser.parseDrop();
    }

    protected SQLSynonymStatementParser createSynonymStatementParser() {
        return new SQLSynonymStatementParser(this);
    }

    // TABLE
    protected SQLCreateTableStatement parseCreateTableStatement() {
        SQLTableStatementParser parser = createTableStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterTableStatement parseAlterTableStatement() {
        SQLTableStatementParser parser = createTableStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropTableStatement parseDropTableStatement() {
        SQLTableStatementParser parser = createTableStatementParser();
        return parser.parseDrop();
    }

    protected SQLTableStatementParser createTableStatementParser() {
        return new SQLTableStatementParser(this);
    }


    // TRIGGER
    protected SQLCreateTriggerStatement parseCreateTriggerStatement() {
        SQLTriggerStatementParser parser = createTriggerStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterTriggerStatement parseAlterTriggerStatement() {
        SQLTriggerStatementParser parser = createTriggerStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropTriggerStatement parseDropTriggerStatement() {
        SQLTriggerStatementParser parser = createTriggerStatementParser();
        return parser.parseDrop();
    }

    protected SQLTriggerStatementParser createTriggerStatementParser() {
        return new SQLTriggerStatementParser(this);
    }


    // TYPE
    protected SQLCreateTypeStatement parseCreateTypeStatement() {
        SQLTypeStatementParser parser = createTypeStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterTypeStatement parseAlterTypeStatement() {
        SQLTypeStatementParser parser = createTypeStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropTypeStatement parseDropTypeStatement() {
        SQLTypeStatementParser parser = createTypeStatementParser();
        return parser.parseDrop();
    }

    protected SQLTypeStatementParser createTypeStatementParser() {
        return new SQLTypeStatementParser(this);
    }

    // TYPE BODY
    protected SQLCreateTypeBodyStatement parseCreateTypeBodyStatement() {
        SQLTypeBodyStatementParser parser = createTypeBodyStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterTypeBodyStatement parseAlterTypeBodyStatement() {
        SQLTypeBodyStatementParser parser = createTypeBodyStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropTypeBodyStatement parseDropTypeBodyStatement() {
        SQLTypeBodyStatementParser parser = createTypeBodyStatementParser();
        return parser.parseDrop();
    }

    protected SQLTypeBodyStatementParser createTypeBodyStatementParser() {
        return new SQLTypeBodyStatementParser(this);
    }


    // USER
    protected SQLCreateUserStatement parseCreateUserStatement() {
        SQLUserStatementParser parser = createUserStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterUserStatement parseAlterUserStatement() {
        SQLUserStatementParser parser = createUserStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropUserStatement parseDropUserStatement() {
        SQLUserStatementParser parser = createUserStatementParser();
        return parser.parseDrop();
    }

    protected SQLUserStatementParser createUserStatementParser() {
        return new SQLUserStatementParser(this);
    }


    // VIEW
    protected SQLCreateViewStatement parseCreateViewStatement() {
        SQLViewStatementParser parser = createViewStatementParser();
        return parser.parseCreate();
    }

    protected SQLAlterViewStatement parseAlterViewStatement() {
        SQLViewStatementParser parser = createViewStatementParser();
        return parser.parseAlter();
    }

    protected SQLDropViewStatement parseDropViewStatement() {
        SQLViewStatementParser parser = createViewStatementParser();
        return parser.parseDrop();
    }

    protected SQLViewStatementParser createViewStatementParser() {
        return new SQLViewStatementParser(this);
    }


    public SQLCallStatement parseCallStatement() {
        this.acceptAndNextToken(SQLToken.TokenKind.CALL, true);
        SQLCallStatement x = new SQLCallStatement(this.dbType);
        x.setExpr(parseExpr());

        if (this.acceptAndNextToken(SQLToken.TokenKind.INTO)) {
            x.setInto(parseExpr());
        }
        return x;
    }

    public SQLDeleteStatement parseDeleteStatement() {

        acceptAndNextToken(SQLToken.TokenKind.DELETE, true);

        SQLDeleteStatement x = new SQLDeleteStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.FROM, true);

        ISQLTableReference tableReference = parseTableReference();
        x.setTableReference(tableReference);

        SQLWhereClause whereClause = parseWhere();
        x.setWhereClause(whereClause);

        return x;
    }

    public ISQLStatement parseExplainStatement() {
        acceptAndNextToken(SQLToken.TokenKind.EXPLAIN, true);

        parseExplainableStmt();

        return null;
    }


    public ISQLStatement parseExplainableStmt() {

//        switch (lexer.token.kind) {
//            case
//        }

        return null;
    }


    public ISQLStatement parseInsertStatement() {
        SQLInsertStatementParser parser = createInsertStatementParser();
        return parser.parse();
    }

    public SQLInsertStatementParser createInsertStatementParser() {
        return new SQLInsertStatementParser(this);
    }

    /**
     * SubQuery: [WITH ...] SELECT ...
     * VALUES (), ()...
     * DEFAULT VALUES
     * <p>
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#insert%20columns%20and%20source
     */
    public ISQLInsertValuesClause parseInsertValuesClause() {

        ISQLInsertValuesClause x = null;

        if (this.accept(SQLToken.TokenKind.VALUES)
                || this.accept(SQLToken.TokenKind.VALUE)) {
            x = parseValuesClause();

        } else if (this.accept(SQLToken.TokenKind.SELECT)) {
            x = parseSelectQueryExpr();

        }

        return x;
    }

    public SQLValuesClause parseValuesClause() {
        if (!this.accept(SQLToken.TokenKind.VALUES)
                && !this.accept(SQLToken.TokenKind.VALUE)) {
            return null;
        }

        SQLValuesClause.Type type = SQLValuesClause.Type.VALUES;
        if (this.accept(SQLToken.TokenKind.VALUE)) {
            type = SQLValuesClause.Type.VALUE;
        }
        nextToken();

        SQLValuesClause x = new SQLValuesClause();
        x.setType(type);
        for (; ; ) {

            SQLValuesClause.SQLValuesItem item = new SQLValuesClause.SQLValuesItem(parseExpr());
            x.addValue(item);
            if (!this.acceptAndNextToken(COMMA)) {
                break;
            }
        }

        return x;
    }

    public SQLSetClause parseSetClause() {
        if (lexer.token.kind != SQLToken.TokenKind.SET
                && lexer.lowerHash != SQLKeyWord.SET.lowerHash) {
            return null;
        }
        nextToken();

        SQLSetClause x = new SQLSetClause();
        for (; ; ) {
            ISQLExpr item = parseAssignmentOperatorExpr();
            x.addItem(item);
            if (lexer.token.kind != COMMA) {
                break;
            }
            nextToken();
        }

        return x;
    }


    public ISQLStatement parseLockStatement() {
        acceptAndNextToken(SQLToken.TokenKind.EXPLAIN, true);

        return null;
    }

    public ISQLStatement parseWithStatement() {
        return null;
    }

    public ISQLStatement parseSelectStatement() {
        SQLSelectStatementParser parser = createSQLSelectStatementParser();
        return parser.parser();
    }

    protected SQLSelectStatementParser createSQLSelectStatementParser() {
        return new SQLSelectStatementParser(this);
    }

    public ISQLStatement parseLParenStatement() {
        return parseSelectStatement();

    }

    public SQLUpdateStatement parseUpdateStatement() {
        acceptAndNextToken(SQLToken.TokenKind.UPDATE, true);

        SQLUpdateStatement x = new SQLUpdateStatement(this.dbType);

        ISQLTableReference tableReference = parseTableReference();
        x.setTableReference(tableReference);

        ISQLUpdateSetClause updateSetClause = parseUpdateSetClause();
        x.setUpdateSetClause(updateSetClause);

        SQLWhereClause whereClause = parseWhere();
        x.setWhereClause(whereClause);

        return x;
    }

    public ISQLUpdateSetClause parseUpdateSetClause() {
        ISQLUpdateSetClause x = null;
        if (lexer.token.kind == SQLToken.TokenKind.SET
                || lexer.lowerHash == SQLKeyWord.SET.lowerHash) {

            x = parseSetClause();

        }

        return x;
    }


    // ------------------ FCL Start ---------------------
    public SQLLabel parseLabel() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.LTLT)) {
            return null;
        }

        ISQLName name = parseName();
        this.acceptAndNextToken(SQLToken.TokenKind.GTGT, true);

        return new SQLLabel(name);
    }

    public void parseLabelStatements(List<SQLLabelStatement> statements, ISQLObject parent) {
        for (; ; ) {
            SQLLabelStatement statement = parseLabelStatement();
            if (statement == null) {
                break;
            }
            statement.setParent(parent);
            statements.add(statement);
        }
    }

    public SQLLabelStatement parseLabelStatement() {

        List<SQLLabel> labels = new ArrayList<>();
        for (; ; ) {
            SQLLabel label = parseLabel();
            if (label == null) {
                break;
            }
            labels.add(label);
        }

        ISQLStatement statement = parseStatement();

        if (statement == null) {
            if (labels.size() == 0) {
                return null;
            }
            throw new SQLParserException(errorInfo());
        }

        SQLLabelStatement x = new SQLLabelStatement();
        boolean semi = this.acceptAndNextToken(SQLToken.TokenKind.SEMI);
        statement.setAfterSemi(semi);
        x.setStatement(statement);

        return x;
    }

    public SQLAssignmentStatement parseAssignmentStatement() {
        ISQLExpr target = parseExpr();
        this.acceptAndNextToken(SQLToken.TokenKind.ASSIGN, true);
        ISQLExpr expr = parseExpr();
        return new SQLAssignmentStatement(target, expr, this.dbType);
    }

    public SQLCaseStatement parseCaseStatement() {
        if (!this.acceptAndNextToken(CASE)) {
            return null;
        }

        SQLCaseStatement x = new SQLCaseStatement(this.dbType);
        if (!this.accept(WHEN)) {
            x.setSelector(parseExpr());
        }

        this.acceptAndNextToken(WHEN, true);
        do {
            SQLCaseStatement.SQLCaseStatementWhenItem whenItem = new SQLCaseStatement.SQLCaseStatementWhenItem();

            whenItem.setExpr(parseExpr());

            this.acceptAndNextToken(THEN, true);

            ISQLStatement statement = parseStatement();
            boolean semi = this.acceptAndNextToken(SEMI);
            statement.setAfterSemi(semi);

            whenItem.setStatement(statement);

            x.addWhenItem(whenItem);
        } while (this.acceptAndNextToken(WHEN));

        if (this.acceptAndNextToken(ELSE)) {
            SQLCaseStatement.SQLCaseStatementElseClause elseClause = new SQLCaseStatement.SQLCaseStatementElseClause();
            parseLabelStatements(elseClause.getStatements(), elseClause);
            x.setElseClause(elseClause);
        }

        this.acceptAndNextToken(END, true);
        this.acceptAndNextToken(CASE, true);

        x.setEndLabel(parseName());

        return x;
    }

    public SQLCloseStatement parseCloseStatement() {
        if (!this.acceptAndNextToken(CLOSE)) {
            return null;
        }
        SQLCloseStatement x = new SQLCloseStatement(this.dbType);
        x.setName(parseExpr());
        return x;
    }

    public SQLContinueStatement parseContinueStatement() {
        if (!this.acceptAndNextToken(CONTINUE)) {
            return null;
        }
        SQLContinueStatement x = new SQLContinueStatement(this.dbType);
        x.setName(parseName());
        if (this.acceptAndNextToken(WHEN)) {
            x.setCondition(parseExpr());
        }
        return x;
    }

    public SQLExecuteImmediateStatement parseExecuteImmediateStatement() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.EXECUTE)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.IMMEDIATE, true);

        SQLExecuteImmediateStatement x = new SQLExecuteImmediateStatement(this.dbType);
        x.setDynamicSQLStmt(parseExpr());


        if (this.acceptAndNextToken(USING)) {

        }

        return x;
    }

    public SQLExitStatement parseExitStatement() {
        if (!this.acceptAndNextToken(EXIT)) {
            return null;
        }
        SQLExitStatement x = new SQLExitStatement(this.dbType);


        return x;
    }

    public SQLFetchStatement parseFetchStatement() {
        throw new SQLParserException(errorInfo());
    }

    public SQLForAllStatement parseForAllStatement() {
        if (!this.acceptAndNextToken(FORALL)) {
            return null;
        }
        SQLForAllStatement x = new SQLForAllStatement(this.dbType);
        x.setName(parseName());

        this.acceptAndNextToken(IN, true);

        SQLForAllStatement.ISQLBoundsClause boundsClause = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.INDICES)) {
            this.acceptAndNextToken(OF, true);
            SQLForAllStatement.SQLBoundsIndicesOfClause indicesOfClause = new SQLForAllStatement.SQLBoundsIndicesOfClause(parseExpr());
            if (this.acceptAndNextToken(BETWEEN)) {
                ISQLExpr between = parseExpr();
                indicesOfClause.setBetween(between);

                this.acceptAndNextToken(END, true);
                ISQLExpr end = parseExpr();
                indicesOfClause.setBetween(end);

            }
            boundsClause = indicesOfClause;
        } else if (this.acceptAndNextToken(SQLToken.TokenKind.VALUES)) {
            this.acceptAndNextToken(OF, true);
            boundsClause = new SQLForAllStatement.SQLBoundsValueOfClause(parseExpr());
        } else {
            ISQLExpr lower = parseExpr();
            this.acceptAndNextToken(DOTDOT, true);
            ISQLExpr upper = parseExpr();
            boundsClause = new SQLForAllStatement.SQLBoundsRangeClause(lower, upper);
        }
        x.setBoundsClause(boundsClause);

        if (this.acceptAndNextToken(SQLToken.TokenKind.SAVE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.EXCEPTIONS, true);
            x.setBeforeSaveExceptions(true);
        }

        x.setStatement(parseStatement());

        if (this.acceptAndNextToken(SQLToken.TokenKind.SAVE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.EXCEPTIONS, true);
            x.setAfterSaveExceptions(true);
        }
        return x;
    }

    public SQLForLoopStatement parseForLoopStatement() {
        if (!this.acceptAndNextToken(FOR)) {
            return null;
        }

        SQLForLoopStatement x = new SQLForLoopStatement(this.dbType);
        x.setName(parseName());

        this.acceptAndNextToken(IN, true);
        if (this.acceptAndNextToken(SQLToken.TokenKind.REVERSE)) {
            x.setReverse(true);
        }

        ISQLExpr lower = parseExpr();
        x.setLower(lower);
        this.acceptAndNextToken(DOTDOT, true);
        ISQLExpr upper = parseExpr();
        x.setUpper(upper);

        if (this.acceptAndNextToken(BY)) {
            x.setBy(parseExpr());
        }

        this.acceptAndNextToken(LOOP, true);

        parseLabelStatements(x.getStatements(), x);

        this.acceptAndNextToken(END, true);
        this.acceptAndNextToken(LOOP, true);
        x.setEndLabel(parseName());

        return x;
    }

    public SQLGotoStatement parseGotoStatement() {

        return null;
    }

    public SQLIfStatement parseIfStatement() {
        SQLIfStatementParser parser = createIfStatementParser();
        return parser.parse();
    }

    public SQLIfStatementParser createIfStatementParser() {
        return new SQLIfStatementParser(this);
    }

    public SQLIterateStatement parseIterateStatement() {
        return null;
    }

    public SQLLeaveStatement parseLeaveStatement() {
        return null;
    }

    public SQLLoopStatement parseLoopStatement() {
        return null;
    }

    public SQLNullStatement parseNullStatement() {
        if (!this.acceptAndNextToken(NULL)) {
            return null;
        }
        return new SQLNullStatement(this.dbType);
    }

    public SQLOpenForStatement parseOpenForStatement() {
        if (!this.acceptAndNextToken(OPEN)) {
            return null;
        }
        SQLOpenForStatement x = new SQLOpenForStatement(this.dbType);
        ISQLExpr open = parseExpr();
        x.setOpen(open);

        this.acceptAndNextToken(FOR, true);
        ISQLExpr for_ = parseExpr();
        x.setFor_(for_);


        return x;
    }

    public SQLOpenStatement parseOpenStatement() {
        if (!this.acceptAndNextToken(OPEN)) {
            return null;
        }

        SQLOpenStatement x = new SQLOpenStatement(this.dbType);


        return x;
    }

    public SQLPipeRowStatement parsePipeRowStatement() {
        if (!this.acceptAndNextToken(PIPE)) {
            return null;
        }
        this.acceptAndNextToken(ROW, true);

        SQLPipeRowStatement x = new SQLPipeRowStatement(this.dbType);


        return x;
    }

    public SQLRaiseStatement parseRaiseStatement() {
        if (!this.acceptAndNextToken(RAISE)) {
            return null;
        }

        SQLRaiseStatement x = new SQLRaiseStatement(this.dbType);
        ISQLExpr exception = parseExpr();
        x.setException(exception);
        return x;
    }

    public SQLRepeatStatement parseRepeatStatement() {

        return null;
    }

    public SQLReturnStatement parseReturnStatement() {
        this.acceptAndNextToken(SQLToken.TokenKind.RETURN, true);
        ISQLExpr expr = parseExpr();
        SQLReturnStatement x = new SQLReturnStatement(this.dbType);
        x.setExpr(expr);
        return x;
    }

    public SQLWhileLoopStatement parseWhileLoopStatement() {
        return null;
    }

    public SQLWhileStatement parseWhileStatement() {
        return null;
    }
    // ------------------ FCL End ---------------------


    public ISQLStatement parseCommitStatement() {
        return null;
    }

    public ISQLStatement parseRollbackStatement() {
        return null;
    }


    public ISQLStatement parseSetStatement() {

        return null;
    }


    public boolean parseIfExists() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.IF)) {
            return false;
        }

        acceptAndNextToken(SQLToken.TokenKind.EXISTS, true);

        return true;
    }

    public boolean parseIfNotExists() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.IF)) {
            return false;
        }
        acceptAndNextToken(NOT, true);
        acceptAndNextToken(SQLToken.TokenKind.EXISTS, true);
        return true;
    }

    public boolean parseOrReplace() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.OR)) {
            return false;
        }

        acceptAndNextToken(SQLToken.TokenKind.REPLACE, true);
        return true;
    }

    public SQLEditionAbleType parseEditionAbleType() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.EDITIONING)) {
            return SQLEditionAbleType.EDITIONING;
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.EDITIONABLE)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.EDITIONING)) {
                return SQLEditionAbleType.EDITIONABLE_EDITIONING;
            }
            return SQLEditionAbleType.EDITIONABLE;
        } else if (this.acceptAndNextToken(SQLToken.TokenKind.NONEDITIONABLE)) {
            return SQLEditionAbleType.NONEDITIONABLE;
        }
        return null;
    }

    public SQLSharingClause parseSharingClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.SHARING)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.EQ, true);

        SQLSharingClause.SQLValue value = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.METADATA)) {
            value = SQLSharingClause.SQLValue.METADATA;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.DATA)) {
            value = SQLSharingClause.SQLValue.DATA;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.EXTENDED)) {
            this.acceptAndNextToken(SQLToken.TokenKind.DATA, true);
            value = SQLSharingClause.SQLValue.EXTENDED_DATA;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.NONE)) {
            value = SQLSharingClause.SQLValue.NONE;

        } else {
            throw new SQLParserException();
        }

        return new SQLSharingClause(value);
    }

    public SQLASType parseAsType(boolean throwException) {
        if (this.acceptAndNextToken(SQLToken.TokenKind.IS)) {
            return SQLASType.IS;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.AS)) {
            return SQLASType.AS;
        }

        if (throwException) {
            throw new SQLParserException();
        }

        return null;
    }


    /**
     * parse expr
     */
    public ISQLExpr parseExpr() {
        if (lexer.token.kind == EOF
                || lexer.token.kind == SEMI
                || lexer.token.kind == COMMA) {
            return null;
        }

        ISQLExpr expr = parseExprPrimary();
        if (expr == null) {
            return null;
        }
        addBeforeComments(expr);

        expr = parseExpr(expr);
        return expr;
    }

    /**
     * 5   |   ^
     * 6   |   *, /, DIV, %, MOD
     * 7   |   -, +, ||
     * 8   |   <<, >>
     * 9   |   &
     * 10  |   |
     * 11  |   = (comparison), <=>, >=, >, <=, <, <>, !=, IS, LIKE, REGEXP, IN, BETWEEN, CASE, WHEN, THEN, ELSE
     * 13  |   NOT
     * 14  |   AND, &&
     * 15  |   XOR
     * 16  |   OR
     * 17  |   :=
     */
    public ISQLExpr parseExpr(ISQLExpr expr) {
        if (lexer.token.kind == EOF
                || lexer.token.kind == SEMI) {
            return expr;
        }

        expr = parseBitXorOperatorExpr(expr);
        expr = parseMultiplicativeOperatorExpr(expr);
        expr = parseAdditiveOperatorExpr(expr);
        expr = parseShiftOperatorExpr(expr);
        expr = parseBitAndOperatorExpr(expr);
        expr = parseBitOrOperatorExpr(expr);
        expr = parseComparisonOperatorExpr(expr);
        expr = parseAndOperatorExpr(expr);
        expr = parseXorOperatorExpr(expr);
        expr = parseOrOperatorExpr(expr);
        return expr;
    }

    /**
     * 左递归转换 => primary
     */
    public ISQLExpr parseExprPrimary() {
        SQLToken token = token();

        ISQLExpr x = null;
        switch (token.kind) {

            case STRING_LITERAL:
                x = new SQLStringLiteral(getStringValue());
                nextToken();
                break;
            case N_STRING_LITERAL:
                x = new SQLNStringLiteral(getStringValue());
                nextToken();
                break;
            case INTEGER_LITERAL:
                x = new SQLIntegerLiteral(getStringValue());
                nextToken();
                break;
            case DECIMAL_LITERAL:
                x = new SQLDecimalLiteral(getStringValue());
                nextToken();
                break;
            case BIT_VALUE_LITERAL:
                x = new SQLBitValueLiteral(getStringValue());
                nextToken();
                break;
            case HEXA_DECIMAL_LITERAL:
                x = new SQLHexaDecimalLiteral(getStringValue());
                nextToken();
                break;
            case BEGIN:
                nextToken();
                break;
            case FLOATING_POINT_LITERAL:
                x = new SQLFloatingPointLiteral(getStringValue());
                nextToken();
                break;
            case BINARY_FLOAT_LITERAL:
                x = new SQLBinaryFloatLiteral(getStringValue());
                nextToken();
                break;
            case BINARY_DOUBLE_LITERAL:
                x = new SQLBinaryDoubleLiteral(getStringValue());
                nextToken();
                break;

            case TRUE:
            case FALSE:
                x = parseBooleanLiteral();
                break;

            case NULL:
                x = new SQLNullExpr();
                nextToken();
                break;
            case DATE:
                x = parseDateLiteral();
                break;
            case TIME:
                x = parseTimeLiteral();
                break;
            case TIMESTAMP:
                x = parseTimestampLiteral();
                break;


            case QUES:
                x = new SQLVariableExpr();
                nextToken();
                break;
            case COLON:
                nextToken();
                if (this.acceptAndNextToken(NEW)) {
                    this.acceptAndNextToken(DOT, true);
                    x = new SQLCorrelationNewName(parseIdentifier());
                    return x;
                } else if (this.acceptAndNextToken(OLD)) {
                    this.acceptAndNextToken(DOT, true);
                    x = new SQLCorrelationOldName(parseIdentifier());
                    return x;
                } else if (this.acceptAndNextToken(PARENT)) {
                    this.acceptAndNextToken(DOT, true);
                    x = new SQLCorrelationParentName(parseIdentifier());
                    return x;
                }

                ISQLIdentifier name = parseIdentifier();


                SQLBindVariableExpr variableExpr = new SQLBindVariableExpr(name);

                x = variableExpr;

                SQLLexer.SQLMake make = this.make();
                boolean indicator = false;
                if (this.acceptAndNextToken(SQLToken.TokenKind.INDICATOR)) {
                    boolean accept = this.accept(SQLToken.TokenKind.COLON);
                    if (!accept) {
                        this.reset(make);
                        return variableExpr;
                    }
                }

                if (this.acceptAndNextToken(SQLToken.TokenKind.COLON)) {
                    ISQLIdentifier indicatorName = parseIdentifier();
                    SQLBindVariableExpr indicatorExpr = new SQLBindVariableExpr(indicatorName);
                    return new SQLPlaceholderExpr(variableExpr, indicator, indicatorExpr);
                }

                break;

            case UNQUOTED_IDENTIFIER:
                x = new SQLUnquotedIdentifier(getStringValue());
                nextToken();
                break;
            case DOUBLE_QUOTED_IDENTIFIER:
                x = new SQLDoubleQuotedIdentifier(getStringValue());
                nextToken();
                break;
            case REVERSE_QUOTED_IDENTIFIER:
                x = new SQLReverseQuotedIdentifier(getStringValue());
                nextToken();
                break;
            case STAR:
                x = new SQLAllColumnExpr();
                nextToken();
                break;

            case INTERVAL:
                x = parseIntervalLiteral();
                break;
            case BINARY:
                x = parseExpr();
                x = new SQLUnaryOperatorExpr(SQLUnaryOperator.BIT_INVERSION, x);
                break;

            case PLUS:
                nextToken();
                x = parseExpr();
                x = new SQLUnaryOperatorExpr(SQLUnaryOperator.POSITIVE, x);
                break;
            case SUB:
                nextToken();
                x = parseExpr();
                x = new SQLUnaryOperatorExpr(SQLUnaryOperator.NEGATIVE, x);
                break;
            case TILDE:
                nextToken();
                x = parseExpr();
                x = new SQLUnaryOperatorExpr(SQLUnaryOperator.BIT_INVERSION, x);
                break;
            case PRIOR:
                nextToken();
                x = parseExpr();
                x = new SQLUnaryOperatorExpr(SQLUnaryOperator.PRIOR, x);
                break;
            case CONNECT_BY_ROOT:
                nextToken();
                x = parseExpr();
                x = new SQLUnaryOperatorExpr(SQLUnaryOperator.CONNECT_BY_ROOT, x);
                break;
            case CASE:
                x = parseCaseExpr();
                break;

            case WITH:
            case SELECT:
                x = parseSelectQueryExpr();
                break;

            case EXISTS:
                x = parseExistsCondition();
                break;
            case LPAREN:
                nextToken();

                x = parseExpr();

                if (lexer.token.kind == SQLToken.TokenKind.COMMA) {

                    SQLListExpr listExpr = new SQLListExpr();
                    listExpr.addItem(x);
                    do {
                        nextToken();
                        listExpr.addItem(parseExpr());
                    } while (lexer.token.kind == SQLToken.TokenKind.COMMA);

                    x = listExpr;
                }

                if (x instanceof SQLUnaryOperatorExpr) {
                    ((SQLUnaryOperatorExpr) x).setParen(true);
                } else if (x instanceof SQLBinaryOperatorExpr) {
                    ((SQLBinaryOperatorExpr) x).setParen(true);
                } else if (x instanceof SQLSelectQueryExpr) {
                    ((SQLSelectQueryExpr) x).setParen(true);
                } else {

                    SQLListExpr listExpr = new SQLListExpr();
                    listExpr.addItem(x);
                    x = listExpr;
                }

                acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

                break;

            default:
                break;
        }

        if (x == null) {
            return null;
        }

        x = parseExprPrimary(x);
        return x;
    }

    public ISQLExpr parseExprPrimary(ISQLExpr expr) {
        if (expr == null) {
            throw new SQLParserException("" + this.lexer.prevToken);
        }
        if (lexer.token.kind == SQLToken.TokenKind.EOF
                || lexer.token.kind == SQLToken.TokenKind.SEMI) {
            return expr;
        }

        SQLToken token = token();

        if (token.kind == SQLToken.TokenKind.DOT) {
            return parseDotExpr(expr);
        }

        if (token.kind == SQLToken.TokenKind.MONKEYS_AT) {
            nextToken();
            ISQLExpr dblink = parseExpr();
            expr = new SQLDBLinkExpr(expr, dblink);
            return parseExprPrimary(expr);
        }

        if (this.accept(SQLToken.TokenKind.EQGT)) {
            return parseCallExpr(expr);
        }

        if (lexer.token.kind != SQLToken.TokenKind.LPAREN) {
            return parseNoArgumentFunction(expr);
        }

        if (token.kind == SQLToken.TokenKind.LPAREN && expr instanceof ISQLName) {
            return parseFunction(expr);
        }

        return expr;
    }

    public boolean isIdentifier() {
        return isIdentifier(this.lexer.kind);
    }

    public static boolean isIdentifier(SQLToken.TokenKind kind) {
        if (kind == UNQUOTED_IDENTIFIER
                || kind == DOUBLE_QUOTED_IDENTIFIER
                || kind == REVERSE_QUOTED_IDENTIFIER) {
            return true;
        }
        return false;
    }

    /**
     * E: T.T.T
     * T: ID
     */
    public ISQLName parseName() {
        ISQLIdentifier x = parseIdentifier();
        return parseName(x);
    }

    public ISQLIdentifier parseIdentifier() {
        ISQLIdentifier x = null;
        switch (lexer.token.kind) {
            case UNQUOTED_IDENTIFIER:
                x = new SQLUnquotedIdentifier(getStringValue());
                break;
            case DOUBLE_QUOTED_IDENTIFIER:
                x = new SQLDoubleQuotedIdentifier(getStringValue());
                break;
            case REVERSE_QUOTED_IDENTIFIER:
                x = new SQLReverseQuotedIdentifier(getStringValue());
                break;
            case STAR:
                x = new SQLAllColumnExpr();
                break;
            default:
        }

        if (x != null) {
            nextToken();
        }

        return x;
    }

    public ISQLExpr parseUnquotedIdentifier() {
        if (lexer.token.kind != UNQUOTED_IDENTIFIER) {
            return null;
        }
        ISQLExpr x = null;

        x = new SQLUnquotedIdentifier(getStringValue());

        return x;
    }


    public ISQLName parseName(ISQLName x) {
        switch (lexer.token.kind) {
            case DOT:
                nextToken();
                ISQLIdentifier right = parseIdentifier();
                ISQLName name = new SQLPropertyExpr(x, right);
                name = parseName(name);
                return name;
            case MONKEYS_AT:
                nextToken();
                ISQLName dblink = parseName();
                name = SQLDBLinkExpr.of(x, dblink);
                name = parseName(name);
                return name;
            default:
        }

        return x;
    }

    public ISQLExpr parseBooleanLiteral() {
        boolean value = token().kind == SQLToken.TokenKind.TRUE;
        ISQLExpr x = new SQLBooleanLiteral(value);
        nextToken();
        return x;
    }

    public ISQLExpr parseIntervalLiteral() {
        boolean accept = acceptAndNextToken(SQLToken.TokenKind.INTERVAL);
        if (!accept) {
            return null;
        }

        SQLIntervalLiteral x = new SQLIntervalLiteral();

        ISQLExpr value = parseExpr();
        x.setValue(value);


        ISQLIdentifier unitName = parseIdentifier();
        SQLIntervalUnit unit = SQLIntervalUnit.of(unitName.getSimpleName());
        x.setUnit(unit);

        if (lexer.token.kind == SQLToken.TokenKind.LPAREN) {

            do {
                nextToken();
                ISQLExpr precision = parseExpr();
                x.addPrecisions(precision);
            } while (lexer.token.kind == COMMA);


            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        if (lexer.token.kind == SQLToken.TokenKind.TO
                || lexer.lowerHash == SQLKeyWord.TO.lowerHash) {
            nextToken();

            ISQLIdentifier toUnitName = parseIdentifier();
            SQLIntervalUnit toUnit = SQLIntervalUnit.of(toUnitName.getSimpleName());
            x.setToUnit(toUnit);

            if (lexer.token.kind == SQLToken.TokenKind.LPAREN) {

                do {
                    nextToken();
                    ISQLExpr toPrecision = parseExpr();
                    x.addToPrecision(toPrecision);
                } while (lexer.token.kind == COMMA);


                acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            }
        }


        return x;
    }

    public ISQLExpr parseDateLiteral() {
        boolean accept = acceptAndNextToken(SQLToken.TokenKind.DATE);
        if (!accept) {
            return null;
        }
        ISQLExpr expr = parseExpr();

        return new SQLDateLiteral(expr);
    }

    public ISQLExpr parseTimeLiteral() {
        boolean accept = acceptAndNextToken(SQLToken.TokenKind.TIME);
        if (!accept) {
            return null;
        }
        ISQLExpr expr = parseExpr();
        SQLTimeLiteral x = new SQLTimeLiteral(expr);
        return x;
    }

    public ISQLExpr parseTimestampLiteral() {
        boolean accept = acceptAndNextToken(SQLToken.TokenKind.TIMESTAMP);
        if (!accept) {
            return null;
        }
        ISQLExpr expr = parseExpr();
        SQLTimestampLiteral x = new SQLTimestampLiteral(expr);

        if (lexer.token.kind == SQLToken.TokenKind.AT
                || lexer.lowerHash == SQLKeyWord.AT.lowerHash) {
            nextToken();

            if (lexer.token.kind == SQLToken.TokenKind.TIME
                    || lexer.lowerHash == SQLKeyWord.TIME.lowerHash) {
                nextToken();
            } else {
                throw new SQLParserException(errorInfo());
            }
            if (lexer.token.kind == SQLToken.TokenKind.ZONE
                    || lexer.lowerHash == SQLKeyWord.ZONE.lowerHash) {
                nextToken();
            } else {
                throw new SQLParserException(errorInfo());
            }

            ISQLExpr atTimeZone = parseExpr();
            x.setAtTimeZone(atTimeZone);

        }


        return x;
    }


    /**
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#data%20type
     */
    public ISQLDataType parseDataType() {
        if (this.accept(EOF)
                || this.accept(SQLToken.TokenKind.LPAREN)
                || this.accept(SQLToken.TokenKind.RPAREN)
                || this.accept(COMMA)
                || this.accept(SEMI)) {
            return null;
        }
        SQLToken token = token();

        ISQLDataType x = null;


        if (this.accept(SQLToken.TokenKind.STRING)) {
            return parseStringDataType();
        }

        if (accept(SQLToken.TokenKind.NATIONAL)) {
            return parseNationalDataType();
        }


        if (accept(SQLToken.TokenKind.CHARACTER)) {
            return parseCharacterDataType();
        }

        if (token.kind == SQLToken.TokenKind.CHAR
                || lexer.lowerHash == SQLKeyWord.CHAR.lowerHash) {

            return parseCharDataType();
        }

        if (token.kind == SQLToken.TokenKind.VARCHAR
                || lexer.lowerHash == SQLKeyWord.VARCHAR.lowerHash) {

            return parseVarcharDataType();
        }

        if (token.kind == SQLToken.TokenKind.VARCHAR2
                || lexer.lowerHash == SQLKeyWord.VARCHAR2.lowerHash) {

            return parseVarchar2DataType();
        }

        if (token.kind == SQLToken.TokenKind.NCHAR
                || lexer.lowerHash == SQLKeyWord.NCHAR.lowerHash) {
            return parseNCharDataType();
        }
        if (token.kind == SQLToken.TokenKind.NVARCHAR2
                || lexer.lowerHash == SQLKeyWord.NVARCHAR2.lowerHash) {
            return parseNvarchar2DataType();
        }

        if (token.kind == SQLToken.TokenKind.BINARY
                || lexer.lowerHash == SQLKeyWord.BINARY.lowerHash) {

            return parseBinaryDataType();
        }

        if (token.kind == SQLToken.TokenKind.VARBINARY
                || lexer.lowerHash == SQLKeyWord.VARBINARY.lowerHash) {

            return parseVarbinaryDataType();
        }


        if (token.kind == SQLToken.TokenKind.TINYBLOB
                || lexer.lowerHash == SQLKeyWord.TINYBLOB.lowerHash) {

            return parseTinyBlobDataType();
        }
        if (token.kind == SQLToken.TokenKind.BLOB
                || lexer.lowerHash == SQLKeyWord.BLOB.lowerHash) {

            return parseBlobDataType();
        }
        if (token.kind == SQLToken.TokenKind.MEDIUMBLOB
                || lexer.lowerHash == SQLKeyWord.MEDIUMBLOB.lowerHash) {

            return parseMediumBlobDataType();
        }
        if (token.kind == SQLToken.TokenKind.LONGBLOB
                || lexer.lowerHash == SQLKeyWord.LONGBLOB.lowerHash) {

            return parseLongBlobDataType();
        }


        if (token.kind == SQLToken.TokenKind.TINYTEXT
                || lexer.lowerHash == SQLKeyWord.TINYTEXT.lowerHash) {

            return parseTinyTextDataType();
        }
        if (token.kind == SQLToken.TokenKind.TEXT
                || lexer.lowerHash == SQLKeyWord.TEXT.lowerHash) {

            return parseTextDataType();
        }
        if (token.kind == SQLToken.TokenKind.MEDIUMTEXT
                || lexer.lowerHash == SQLKeyWord.MEDIUMTEXT.lowerHash) {

            return parseMediumTextDataType();
        }
        if (token.kind == SQLToken.TokenKind.LONGTEXT
                || lexer.lowerHash == SQLKeyWord.LONGTEXT.lowerHash) {

            return parseLongTextDataType();
        }


        if (token.kind == SQLToken.TokenKind.ENUM
                || lexer.lowerHash == SQLKeyWord.ENUM.lowerHash) {

            return parseEnumDataType();
        }
        if (token.kind == SQLToken.TokenKind.SET
                || lexer.lowerHash == SQLKeyWord.SET.lowerHash) {

            return parseSetDataType();
        }

        if (token.kind == SQLToken.TokenKind.LONG
                || lexer.lowerHash == SQLKeyWord.LONG.lowerHash) {
            return parseLongDataType();
        }
        if (token.kind == SQLToken.TokenKind.RAW
                || lexer.lowerHash == SQLKeyWord.RAW.lowerHash) {
            return parseRawDataType();
        }
        if (token.kind == SQLToken.TokenKind.CLOB
                || lexer.lowerHash == SQLKeyWord.CLOB.lowerHash) {
            return parseClobDataType();
        }
        if (token.kind == SQLToken.TokenKind.NCLOB
                || lexer.lowerHash == SQLKeyWord.NCLOB.lowerHash) {
            return parseNClobDataType();
        }
        if (token.kind == SQLToken.TokenKind.BFILE
                || lexer.lowerHash == SQLKeyWord.BFILE.lowerHash) {
            return parseBFileDataType();
        }

        if (token.kind == SQLToken.TokenKind.ROWID
                || lexer.lowerHash == SQLKeyWord.ROWID.lowerHash) {
            return parseRowIdDataType();
        }
        if (token.kind == SQLToken.TokenKind.UROWID
                || lexer.lowerHash == SQLKeyWord.UROWID.lowerHash) {
            return parseURowIdDataType();
        }


        // ------------------- numeric -------------------

        if (token.kind == SQLToken.TokenKind.NUMERIC
                || lexer.lowerHash == SQLKeyWord.NUMERIC.lowerHash) {
            return parseNumericDataType();
        }
        if (token.kind == SQLToken.TokenKind.NUMBER
                || lexer.lowerHash == SQLKeyWord.NUMBER.lowerHash) {
            return parseNumberDataType();
        }

        if (token.kind == SQLToken.TokenKind.BIT
                || lexer.lowerHash == SQLKeyWord.BIT.lowerHash) {
            return parseBitDataType();
        }

        if (token.kind == SQLToken.TokenKind.TINYINT
                || lexer.lowerHash == SQLKeyWord.TINYINT.lowerHash) {
            return parseTinyIntDataType();
        }
        if (token.kind == SQLToken.TokenKind.SMALLINT
                || lexer.lowerHash == SQLKeyWord.SMALLINT.lowerHash) {
            return parseSmallIntDataType();
        }
        if (token.kind == SQLToken.TokenKind.MEDIUMINT
                || lexer.lowerHash == SQLKeyWord.MEDIUMINT.lowerHash) {
            return parseMediumIntDataType();
        }
        if (token.kind == SQLToken.TokenKind.INT
                || lexer.lowerHash == SQLKeyWord.INT.lowerHash) {
            return parseIntDataType();
        }
        if (token.kind == SQLToken.TokenKind.INTEGER
                || lexer.lowerHash == SQLKeyWord.INTEGER.lowerHash) {
            return parseIntegerDataType();
        }
        if (token.kind == SQLToken.TokenKind.BIGINT
                || lexer.lowerHash == SQLKeyWord.BIGINT.lowerHash) {
            return parseBigIntDataType();
        }

        if (this.accept(SQLToken.TokenKind.PLS_INTEGER)) {
            return parsePlsIntegerDataType();
        }
        if (this.accept(SQLToken.TokenKind.BINARY_INTEGER)) {
            return parseBinaryIntegerDataType();
        }


        if (token.kind == SQLToken.TokenKind.DECIMAL
                || lexer.lowerHash == SQLKeyWord.DECIMAL.lowerHash) {
            return parseDecimalDataType();
        }
        if (token.kind == SQLToken.TokenKind.DEC
                || lexer.lowerHash == SQLKeyWord.DEC.lowerHash) {
            return parseDecDataType();
        }
        if (token.kind == SQLToken.TokenKind.FLOAT
                || lexer.lowerHash == SQLKeyWord.FLOAT.lowerHash) {
            return parseFloatDataType();
        }
        if (token.kind == SQLToken.TokenKind.DOUBLE
                || lexer.lowerHash == SQLKeyWord.DOUBLE.lowerHash) {
            return parseDoubleDataType();
        }
        if (token.kind == SQLToken.TokenKind.REAL
                || lexer.lowerHash == SQLKeyWord.REAL.lowerHash) {
            return parseRealDataType();
        }
        if (token.kind == SQLToken.TokenKind.BINARY_FLOAT
                || lexer.lowerHash == SQLKeyWord.BINARY_FLOAT.lowerHash) {
            return parseBinaryFloatDataType();
        }
        if (token.kind == SQLToken.TokenKind.BINARY_DOUBLE
                || lexer.lowerHash == SQLKeyWord.BINARY_DOUBLE.lowerHash) {
            return parseBinaryDoubleDataType();
        }


        // ------------------- boolean -------------------

        if (token.kind == SQLToken.TokenKind.BOOL
                || lexer.lowerHash == SQLKeyWord.BOOL.lowerHash) {
            return parseBoolDataType();
        }
        if (token.kind == SQLToken.TokenKind.BOOLEAN
                || lexer.lowerHash == SQLKeyWord.BOOLEAN.lowerHash) {
            return parseBooleanDataType();
        }


        // ------------------- DataTime -------------------

        if (token.kind == SQLToken.TokenKind.DATE
                || lexer.lowerHash == SQLKeyWord.DATE.lowerHash) {
            return parseDateDataType();
        }
        if (token.kind == SQLToken.TokenKind.DATETIME
                || lexer.lowerHash == SQLKeyWord.DATETIME.lowerHash) {
            return parseDateTimeDataType();
        }
        if (token.kind == SQLToken.TokenKind.TIMESTAMP
                || lexer.lowerHash == SQLKeyWord.TIMESTAMP.lowerHash) {
            return parseTimestampDataType();
        }
        if (token.kind == SQLToken.TokenKind.TIME
                || lexer.lowerHash == SQLKeyWord.TIME.lowerHash) {
            return parseTimeDataType();
        }
        if (token.kind == SQLToken.TokenKind.YEAR
                || lexer.lowerHash == SQLKeyWord.YEAR.lowerHash) {
            return parseYearDataType();
        }

        if (token.kind == SQLToken.TokenKind.INTERVAL
                || lexer.lowerHash == SQLKeyWord.INTERVAL.lowerHash) {
            return parseIntervalDataType();
        }

        if (this.accept(SQLToken.TokenKind.REF)) {
            return parseRefDataType();
        }


        // ------------------- Any -------------------
        if (this.accept(SQLToken.TokenKind.ANYDATA)) {
            return parseAnyDataDataType();
        }
        if (this.accept(SQLToken.TokenKind.ANYTYPE)) {
            return parseAnyTypeDataType();
        }
        if (this.accept(SQLToken.TokenKind.ANYDATASET)) {
            return parseAnyDataSetDataType();
        }

        // ------------------- Xml -------------------
        if (this.accept(SQLToken.TokenKind.XMLTYPE)) {
            return parseXmlTypeDataType();
        }
        if (this.accept(SQLToken.TokenKind.URITYPE)) {
            return parseUriTypeDataType();
        }

        // ------------------- Spatial -------------------


        // ------------------- Media -------------------


        ISQLName name = parseName();
        x = new SQLDataType(name);

        return parseDataType(x);
    }

    public ISQLDataType parseDataType(ISQLDataType x) {
        if (this.acceptAndNextToken(SQLToken.TokenKind.PERCENT)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.TYPE)) {
                return new SQLPercentTypeDataType(x.getName());

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.ROWTYPE)) {
                return new SQLPercentRowTypeDataType(x.getName());

            }

            throw new SQLParserException("TODO." + errorInfo());
        }

        return x;
    }


    public ISQLDataType parseStringDataType() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.STRING)) {
            return null;
        }

        ISQLDataType x = new SQLStringDataType();
        parseDataTypeArgument(x);
        return x;
    }

    public ISQLDataType parseNationalDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.NATIONAL
                && lexer.lowerHash != SQLKeyWord.NATIONAL.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = null;
        if (lexer.token.kind == SQLToken.TokenKind.CHAR
                || lexer.lowerHash == SQLKeyWord.CHAR.lowerHash) {

            nextToken();

            x = new SQLNationalCharDataType();

        } else if (lexer.token.kind == SQLToken.TokenKind.VARCHAR
                || lexer.lowerHash == SQLKeyWord.VARCHAR.lowerHash) {

            nextToken();

            x = new SQLNationalVarcharDataType();

        } else {
            throw new SQLParserException(errorInfo());
        }

        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseCharacterDataType() {
        acceptAndNextToken(SQLToken.TokenKind.CHARACTER);
        if (lexer.token.kind == SQLToken.TokenKind.VARYING
                || lexer.lowerHash == SQLKeyWord.VARYING.lowerHash) {
            nextToken();
        }
        return null;
    }

    public ISQLDataType parseCharDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.CHAR
                && lexer.lowerHash != SQLKeyWord.CHAR.lowerHash) {
            return null;
        }
        nextToken();

        AbstractSQLCharDataType x = new SQLCharDataType();
        if (lexer.token.kind == SQLToken.TokenKind.VARYING
                || lexer.lowerHash == SQLKeyWord.VARYING.lowerHash) {
            nextToken();

            x = new SQLCharVaryingDataType();

        } else if (lexer.token.kind == SQLToken.TokenKind.LARGE
                || lexer.lowerHash == SQLKeyWord.LARGE.lowerHash) {

            nextToken();

            if (lexer.lowerHash != SQLKeyWord.OBJECT.lowerHash) {
                throw new SQLParserException("");
            }

            nextToken();

            x = new SQLCharLargeObjectDataType();

        }

        if (lexer.token.kind == SQLToken.TokenKind.LPAREN) {
            x.setParen(true);
            nextToken();
            ISQLExpr argument = parseExpr();
            x.addArgument(argument);
            if (lexer.lowerHash == SQLKeyWord.BYTE.lowerHash) {
                nextToken();
                x.setCharSizeUnit(SQLCharSizeUnit.BYTE);
            } else if (lexer.lowerHash == SQLKeyWord.CHAR.lowerHash) {
                nextToken();
                x.setCharSizeUnit(SQLCharSizeUnit.CHAR);
            }

            while (lexer.token.kind == COMMA) {
                nextToken();
                argument = parseExpr();
                x.addArgument(argument);
            }

            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }


        return x;
    }


    public ISQLDataType parseVarcharDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.VARCHAR
                && lexer.lowerHash != SQLKeyWord.VARCHAR.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLVarcharDataType();
        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseVarchar2DataType() {
        if (lexer.token.kind != SQLToken.TokenKind.VARCHAR2
                && lexer.lowerHash != SQLKeyWord.VARCHAR2.lowerHash) {
            return null;
        }
        nextToken();

        SQLVarchar2DataType x = new SQLVarchar2DataType();
        if (lexer.token.kind == SQLToken.TokenKind.LPAREN) {
            x.setParen(true);
            nextToken();
            ISQLExpr argument = parseExpr();
            x.addArgument(argument);
            if (lexer.lowerHash == SQLKeyWord.BYTE.lowerHash) {
                nextToken();
                x.setCharSizeUnit(SQLCharSizeUnit.BYTE);
            } else if (lexer.lowerHash == SQLKeyWord.CHAR.lowerHash) {
                nextToken();
                x.setCharSizeUnit(SQLCharSizeUnit.CHAR);
            }

            while (lexer.token.kind == COMMA) {
                nextToken();
                argument = parseExpr();
                x.addArgument(argument);
            }

            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return x;
    }

    public ISQLDataType parseNCharDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.NCHAR
                && lexer.lowerHash != SQLKeyWord.NCHAR.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLNCharDataType();
        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseNvarchar2DataType() {
        if (lexer.token.kind != SQLToken.TokenKind.NVARCHAR2
                && lexer.lowerHash != SQLKeyWord.NVARCHAR2.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLNVarchar2DataType();
        parseDataTypeArgument(x);

        return x;
    }


    public ISQLDataType parseBinaryDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.BINARY
                && lexer.lowerHash != SQLKeyWord.BINARY.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLBinaryDataType();

        parseDataTypeArgument(x);

        return x;
    }


    public ISQLDataType parseVarbinaryDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.VARBINARY
                && lexer.lowerHash != SQLKeyWord.VARBINARY.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLVarBinaryDataType();

        parseDataTypeArgument(x);

        return x;
    }


    public ISQLDataType parseTinyBlobDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.TINYBLOB
                && lexer.lowerHash != SQLKeyWord.TINYBLOB.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLTinyBlobDataType();

        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseBlobDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.BLOB
                && lexer.lowerHash != SQLKeyWord.BLOB.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLBlobDataType();

        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseMediumBlobDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.MEDIUMBLOB
                && lexer.lowerHash != SQLKeyWord.MEDIUMBLOB.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLMediumBlobDataType();

        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseLongBlobDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.LONGBLOB
                && lexer.lowerHash != SQLKeyWord.LONGBLOB.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLLongBlobDataType();

        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseTinyTextDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.TINYTEXT
                && lexer.lowerHash != SQLKeyWord.TINYTEXT.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLTinyTextDataType();

        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseTextDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.TEXT
                && lexer.lowerHash != SQLKeyWord.TEXT.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLTextDataType();

        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseMediumTextDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.MEDIUMTEXT
                && lexer.lowerHash != SQLKeyWord.MEDIUMTEXT.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLMediumTextDataType();

        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseLongTextDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.LONGTEXT
                && lexer.lowerHash != SQLKeyWord.LONGTEXT.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLLongTextDataType();

        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseEnumDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.ENUM
                && lexer.lowerHash != SQLKeyWord.ENUM.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLEnumDataType();

        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseSetDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.SET
                && lexer.lowerHash != SQLKeyWord.SET.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLSetDataType();

        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseLongDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.LONG
                && lexer.lowerHash != SQLKeyWord.LONG.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLLongDataType();
        if (lexer.token.kind == SQLToken.TokenKind.RAW
                || lexer.lowerHash == SQLKeyWord.RAW.lowerHash) {
            nextToken();
            x = new SQLLongRawDataType();
        }

        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseRawDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.RAW
                && lexer.lowerHash != SQLKeyWord.RAW.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLRawDataType();
        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseClobDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.CLOB
                && lexer.lowerHash != SQLKeyWord.CLOB.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLClobDataType();
        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseNClobDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.NCLOB
                && lexer.lowerHash != SQLKeyWord.NCLOB.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLNClobDataType();
        parseDataTypeArgument(x);

        return x;
    }


    public ISQLDataType parseBFileDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.BFILE
                && lexer.lowerHash != SQLKeyWord.BFILE.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLBFileDataType();
        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseRowIdDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.ROWID
                && lexer.lowerHash != SQLKeyWord.ROWID.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLRowIdDataType();
        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseURowIdDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.UROWID
                && lexer.lowerHash != SQLKeyWord.UROWID.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLURowIdDataType();
        parseDataTypeArgument(x);

        return x;
    }


    public ISQLDataType parseNumericDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.NUMERIC
                && lexer.lowerHash != SQLKeyWord.NUMERIC.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLNumericDataType();
        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseNumberDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.NUMBER
                && lexer.lowerHash != SQLKeyWord.NUMBER.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLNumberDataType();
        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseBitDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.BIT
                && lexer.lowerHash != SQLKeyWord.BIT.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLBitDataType();
        parseDataTypeArgument(x);

        return x;
    }

    public ISQLDataType parseTinyIntDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.TINYINT
                && lexer.lowerHash != SQLKeyWord.TINYINT.lowerHash) {
            return null;
        }
        nextToken();

        SQLTinyIntDataType x = new SQLTinyIntDataType();
        parseDataTypeArgument(x);
        parseNumericDataType(x);

        return x;
    }

    public ISQLDataType parseSmallIntDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.SMALLINT
                && lexer.lowerHash != SQLKeyWord.SMALLINT.lowerHash) {
            return null;
        }
        nextToken();

        SQLSmallIntDataType x = new SQLSmallIntDataType();
        parseDataTypeArgument(x);
        parseNumericDataType(x);

        return x;
    }

    public ISQLDataType parseMediumIntDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.MEDIUMINT
                && lexer.lowerHash != SQLKeyWord.MEDIUMINT.lowerHash) {
            return null;
        }
        nextToken();

        SQLMediumIntDataType x = new SQLMediumIntDataType();
        parseDataTypeArgument(x);
        parseNumericDataType(x);

        return x;
    }

    public ISQLDataType parseIntDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.INT
                && lexer.lowerHash != SQLKeyWord.INT.lowerHash) {
            return null;
        }
        nextToken();

        SQLIntDataType x = new SQLIntDataType();
        parseDataTypeArgument(x);
        parseNumericDataType(x);

        return x;
    }

    public ISQLDataType parseIntegerDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.INTEGER
                && lexer.lowerHash != SQLKeyWord.INTEGER.lowerHash) {
            return null;
        }
        nextToken();

        SQLIntegerDataType x = new SQLIntegerDataType();
        parseDataTypeArgument(x);
        parseNumericDataType(x);

        return x;
    }

    public ISQLDataType parseBigIntDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.BIGINT
                && lexer.lowerHash != SQLKeyWord.BIGINT.lowerHash) {
            return null;
        }
        nextToken();

        SQLBigIntDataType x = new SQLBigIntDataType();
        parseDataTypeArgument(x);
        parseNumericDataType(x);

        return x;
    }

    public ISQLDataType parsePlsIntegerDataType() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.PLS_INTEGER)) {
            return null;
        }

        return new SQLPlsIntegerDataType();
    }

    public ISQLDataType parseBinaryIntegerDataType() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.BINARY_INTEGER)) {
            return null;
        }

        return new SQLBinaryIntegerDataType();
    }

    public ISQLDataType parseDecimalDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.DECIMAL
                && lexer.lowerHash != SQLKeyWord.DECIMAL.lowerHash) {
            return null;
        }
        nextToken();

        SQLDecimalDataType x = new SQLDecimalDataType();
        parseDataTypeArgument(x);
        parseNumericDataType(x);

        return x;
    }

    public ISQLDataType parseDecDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.DEC
                && lexer.lowerHash != SQLKeyWord.DEC.lowerHash) {
            return null;
        }
        nextToken();

        SQLDecDataType x = new SQLDecDataType();
        parseDataTypeArgument(x);
        parseNumericDataType(x);

        return x;
    }

    public ISQLDataType parseFloatDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.FLOAT
                && lexer.lowerHash != SQLKeyWord.FLOAT.lowerHash) {
            return null;
        }
        nextToken();

        SQLFloatDataType x = new SQLFloatDataType();
        parseDataTypeArgument(x);
        parseNumericDataType(x);

        return x;
    }

    public ISQLDataType parseDoubleDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.DOUBLE
                && lexer.lowerHash != SQLKeyWord.DOUBLE.lowerHash) {
            return null;
        }
        nextToken();

        ISQLNumericDataType x = new SQLDoubleDataType();
        if (lexer.token.kind == SQLToken.TokenKind.PRECISION
                || lexer.lowerHash == SQLKeyWord.PRECISION.lowerHash) {
            nextToken();
            x = new SQLDoublePrecisionDataType();
        }

        parseDataTypeArgument(x);
        parseNumericDataType(x);

        return x;
    }

    public ISQLDataType parseRealDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.REAL
                && lexer.lowerHash != SQLKeyWord.REAL.lowerHash) {
            return null;
        }
        nextToken();

        SQLRealDataType x = new SQLRealDataType();
        parseDataTypeArgument(x);
        parseNumericDataType(x);
        return x;
    }

    public ISQLDataType parseBinaryFloatDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.BINARY_FLOAT
                && lexer.lowerHash != SQLKeyWord.BINARY_FLOAT.lowerHash) {
            return null;
        }
        nextToken();

        SQLBinaryFloatDataType x = new SQLBinaryFloatDataType();
        parseDataTypeArgument(x);
        parseNumericDataType(x);
        return x;
    }

    public ISQLDataType parseBinaryDoubleDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.BINARY_DOUBLE
                && lexer.lowerHash != SQLKeyWord.BINARY_DOUBLE.lowerHash) {
            return null;
        }
        nextToken();

        SQLBinaryDoubleDataType x = new SQLBinaryDoubleDataType();
        parseDataTypeArgument(x);
        parseNumericDataType(x);
        return x;
    }


    public void parseNumericDataType(ISQLNumericDataType x) {
        if (lexer.token.kind == SQLToken.TokenKind.UNSIGNED
                || lexer.lowerHash == SQLKeyWord.UNSIGNED.lowerHash) {
            nextToken();
            x.setUnsigned(true);
        }

        if (lexer.token.kind == SQLToken.TokenKind.ZEROFILL
                || lexer.lowerHash == SQLKeyWord.ZEROFILL.lowerHash) {
            nextToken();
            x.setZerofill(true);
        }

    }


    public ISQLDataType parseBoolDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.BOOL
                && lexer.lowerHash != SQLKeyWord.BOOL.lowerHash) {
            return null;
        }
        nextToken();

        ISQLDataType x = new SQLBoolDataType();

        return x;
    }

    public ISQLDataType parseBooleanDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.BOOLEAN
                && lexer.lowerHash != SQLKeyWord.BOOLEAN.lowerHash) {
            return null;
        }
        nextToken();
        ISQLDataType x = new SQLBooleanDataType();

        return x;
    }


    public ISQLDataType parseDateDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.DATE
                && lexer.lowerHash != SQLKeyWord.DATE.lowerHash) {
            return null;
        }
        nextToken();
        ISQLDataType x = new SQLDateDataType();

        return x;
    }

    public ISQLDataType parseDateTimeDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.DATETIME
                && lexer.lowerHash != SQLKeyWord.DATETIME.lowerHash) {
            return null;
        }
        nextToken();
        ISQLDataType x = new SQLDateTimeDataType();
        parseDataTypeArgument(x);
        return x;
    }

    public ISQLDataType parseTimestampDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.TIMESTAMP
                && lexer.lowerHash != SQLKeyWord.TIMESTAMP.lowerHash) {
            return null;
        }
        nextToken();
        ISQLDateTimeDataType x = new SQLTimestampDataType();
        parseDataTypeArgument(x);

        if (lexer.token.kind == SQLToken.TokenKind.WITH
                || lexer.lowerHash == SQLKeyWord.WITH.lowerHash) {
            nextToken();

            x.setWithTimeZoneType(SQLWithTimeZoneType.WITH_TIME_ZONE);

            if (lexer.token.kind == SQLToken.TokenKind.LOCAL
                    || lexer.lowerHash == SQLKeyWord.LOCAL.lowerHash) {
                nextToken();
                x.setWithTimeZoneType(SQLWithTimeZoneType.WITH_LOCAL_TIME_ZONE);
            }

            if (lexer.token.kind == SQLToken.TokenKind.TIME
                    || lexer.lowerHash == SQLKeyWord.TIME.lowerHash) {
                nextToken();
            } else {
                throw new SQLParserException();
            }

            if (lexer.token.kind == SQLToken.TokenKind.ZONE
                    || lexer.lowerHash == SQLKeyWord.ZONE.lowerHash) {
                nextToken();
            } else {
                throw new SQLParserException();
            }
        }

        return x;
    }

    public ISQLDataType parseTimeDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.TIME
                && lexer.lowerHash != SQLKeyWord.TIME.lowerHash) {
            return null;
        }
        nextToken();
        ISQLDataType x = new SQLTimeDataType();
        parseDataTypeArgument(x);
        return x;
    }

    public ISQLDataType parseYearDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.YEAR
                && lexer.lowerHash != SQLKeyWord.YEAR.lowerHash) {
            return null;
        }
        nextToken();
        ISQLDataType x = new SQLYearDataType();
        parseDataTypeArgument(x);
        return x;
    }

    public ISQLDataType parseIntervalDataType() {
        if (lexer.token.kind != SQLToken.TokenKind.INTERVAL
                && lexer.lowerHash != SQLKeyWord.INTERVAL.lowerHash) {
            return null;
        }
        nextToken();

        SQLIntervalDataType x = new SQLIntervalDataType();

        if (lexer.token.kind != SQLToken.TokenKind.LPAREN) {
            ISQLName unitName = parseIdentifier();
            SQLIntervalUnit unit = SQLIntervalUnit.of(unitName.getSimpleName());
            x.setUnit(unit);
        }
        parseDataTypeArgument(x);


        if (lexer.token.kind == SQLToken.TokenKind.TO
                || lexer.lowerHash == SQLKeyWord.TO.lowerHash) {

            nextToken();
            ISQLName toUnitName = parseIdentifier();
            SQLIntervalUnit toUnit = SQLIntervalUnit.of(toUnitName.getSimpleName());
            x.setToUnit(toUnit);

            if (lexer.token.kind == SQLToken.TokenKind.LPAREN) {
                do {
                    nextToken();
                    ISQLExpr toPrecision = parseExpr();
                    x.addToPrecision(toPrecision);
                } while (lexer.token.kind == COMMA);
                acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            }
        }


        return x;
    }


    public ISQLRefDataType parseRefDataType() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.REF)) {
            return null;
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.CURSOR)) {
            return new SQLRefCursorDataType();
        }

        SQLRefDataType x = new SQLRefDataType();
        boolean paren = this.acceptAndNextToken(SQLToken.TokenKind.LPAREN);

        ISQLDataType dataType = parseDataType();
        x.addArgument(dataType);

        x.setParen(paren);

        if (paren) {
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return x;
    }


    public SQLAnyDataDataType parseAnyDataDataType() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.ANYDATA)) {
            return null;
        }
        return new SQLAnyDataDataType();
    }

    public SQLAnyTypeDataType parseAnyTypeDataType() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.ANYTYPE)) {
            return null;
        }
        return new SQLAnyTypeDataType();
    }

    public SQLAnyDataSetDataType parseAnyDataSetDataType() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.ANYDATASET)) {
            return null;
        }
        return new SQLAnyDataSetDataType();
    }

    public SQLXmlTypeDataType parseXmlTypeDataType() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.XMLTYPE)) {
            return null;
        }
        return new SQLXmlTypeDataType();
    }

    public SQLUriTypeDataType parseUriTypeDataType() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.URITYPE)) {
            return null;
        }
        return new SQLUriTypeDataType();
    }


    public void parseDataTypeArgument(ISQLDataType x) {

        if (lexer.token.kind == SQLToken.TokenKind.LPAREN) {
            x.setParen(true);

            do {
                nextToken();
                ISQLExpr argument = parseExpr();
                x.addArgument(argument);

            } while (lexer.token.kind == COMMA);

            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

    }


    /**
     * x .xx
     *
     * @param expr
     * @return xx.xx
     */
    public ISQLExpr parseDotExpr(ISQLExpr expr) {
        if (!this.acceptAndNextToken(DOT)) {
            return expr;
        }


        ISQLIdentifier right = parseIdentifier();
        expr = new SQLPropertyExpr(expr, right);
        expr = parseDotExpr(expr);

        return parseExprPrimary(expr);
    }


    /**
     * name : NoArgumentFunction
     * name (+) : outerJoin
     * name (xx, xx) : function
     *
     * @param expr function name
     * @return function expr
     */
    public ISQLExpr parseFunction(ISQLExpr expr) {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            return expr;
        }

        if (lexer.token.kind == SQLToken.TokenKind.PLUS) {
            nextToken();
            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            return new SQLOuterJoinExpr(expr);
        }

        // 特殊处理
        if (expr instanceof SQLUnquotedIdentifier) {
            SQLUnquotedIdentifier identifier = (SQLUnquotedIdentifier) expr;

            long lowerHash = identifier.lowerHash();

            if (aggregateFunctionLowerHash.contains(lowerHash)) {
                ISQLExpr result = parseAggregateFunction(expr);

                if (result != expr) {
                    this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
                    return parseExprPrimary(result);
                }

            } else if (specialFunctionLowerHash.contains(lowerHash)) {
                ISQLExpr result = parseSpecialFunction(expr);

                if (result != expr) {
                    this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
                    return parseExprPrimary(result);
                }
            }

        }

        SQLMethodInvocation methodInvocation = new SQLMethodInvocation(expr);
        for (; ; ) {

            ISQLExpr argument = parseExpr();
            methodInvocation.addArgument(argument);
            if (lexer.token.kind != SQLToken.TokenKind.COMMA) {
                break;
            }
            nextToken();
        }

        acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        return parseExprPrimary(methodInvocation);
    }


    public ISQLExpr parseNoArgumentFunction(ISQLExpr expr) {
        if (!(expr instanceof SQLUnquotedIdentifier)) {
            return expr;
        }

        SQLUnquotedIdentifier identifier = (SQLUnquotedIdentifier) expr;
        long lowerHash = identifier.lowerHash();

        boolean contains = noArgumentFunctionLowerHash.contains(lowerHash);
        if (contains) {
            SQLMethodInvocation methodInvocation = new SQLMethodInvocation(identifier);
            methodInvocation.setParen(false);
            return methodInvocation;
        }

        return expr;
    }

    public ISQLExpr parseAggregateFunction(ISQLExpr expr) {
        if (!(expr instanceof SQLUnquotedIdentifier)) {
            return expr;
        }
        return expr;
    }

    public ISQLExpr parseSpecialFunction(ISQLExpr expr) {
        if (!(expr instanceof SQLUnquotedIdentifier)) {
            return expr;
        }

        SQLUnquotedIdentifier identifier = (SQLUnquotedIdentifier) expr;
        long lowerHash = identifier.lowerHash();

        if (!specialFunctionLowerHash.contains(lowerHash)) {
            return expr;
        }

        if (lowerHash == SQLKeyWord.EXTRACT.lowerHash) {
            return parseExtractFunction(identifier);
        }

        return expr;
    }


    protected ISQLExpr parseExtractFunction(ISQLExpr name) {

        SQLExtractFunction.SQLUnit unit = null;

        if (this.acceptAndNextToken(SQLToken.TokenKind.MICROSECOND)) {
            unit = SQLExtractFunction.SQLUnit.MICROSECOND;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.SECOND)) {
            unit = SQLExtractFunction.SQLUnit.SECOND;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.MINUTE)) {
            unit = SQLExtractFunction.SQLUnit.MINUTE;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.HOUR)) {
            unit = SQLExtractFunction.SQLUnit.HOUR;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.DAY)) {
            unit = SQLExtractFunction.SQLUnit.DAY;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.WEEK)) {
            unit = SQLExtractFunction.SQLUnit.DAY;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.MONTH)) {
            unit = SQLExtractFunction.SQLUnit.MONTH;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.QUARTER)) {
            unit = SQLExtractFunction.SQLUnit.QUARTER;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.YEAR)) {
            unit = SQLExtractFunction.SQLUnit.YEAR;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.SECOND_MICROSECOND)) {
            unit = SQLExtractFunction.SQLUnit.SECOND_MICROSECOND;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.MINUTE_MICROSECOND)) {
            unit = SQLExtractFunction.SQLUnit.MINUTE_MICROSECOND;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.MINUTE_SECOND)) {
            unit = SQLExtractFunction.SQLUnit.MINUTE_SECOND;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.HOUR_MICROSECOND)) {
            unit = SQLExtractFunction.SQLUnit.HOUR_MICROSECOND;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.HOUR_SECOND)) {
            unit = SQLExtractFunction.SQLUnit.HOUR_SECOND;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.HOUR_MINUTE)) {
            unit = SQLExtractFunction.SQLUnit.HOUR_MINUTE;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.DAY_MICROSECOND)) {
            unit = SQLExtractFunction.SQLUnit.DAY_MICROSECOND;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.DAY_SECOND)) {
            unit = SQLExtractFunction.SQLUnit.DAY_SECOND;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.DAY_MINUTE)) {
            unit = SQLExtractFunction.SQLUnit.DAY_MINUTE;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.YEAR_MONTH)) {
            unit = SQLExtractFunction.SQLUnit.YEAR_MONTH;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.TIMEZONE_HOUR)) {
            unit = SQLExtractFunction.SQLUnit.TIMEZONE_HOUR;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.TIMEZONE_MINUTE)) {
            unit = SQLExtractFunction.SQLUnit.TIMEZONE_MINUTE;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.TIMEZONE_REGION)) {
            unit = SQLExtractFunction.SQLUnit.TIMEZONE_REGION;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.TIMEZONE_ABBR)) {
            unit = SQLExtractFunction.SQLUnit.TIMEZONE_ABBR;

        } else {
            return name;
        }

        this.acceptAndNextToken(SQLToken.TokenKind.FROM, true);
        ISQLExpr argument = parseExpr();

        return new SQLExtractFunction(unit, argument);
    }

    public ISQLExpr parseCallExpr(ISQLExpr name) {
        throw new SQLParserException(errorInfo());
    }


    /**
     *
     */
    public ISQLExpr parseCaseExpr() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.CASE)) {
            return null;
        }

        SQLCaseExpr caseExpr = new SQLCaseExpr();
        if (lexer.token.kind != SQLToken.TokenKind.WHEN
                && lexer.lowerHash != SQLKeyWord.WHEN.lowerHash) {
            ISQLExpr expr = parseExpr();
            caseExpr.setExpr(expr);
        }

        for (; ; ) {

            SQLCaseExpr.SQLCaseExprWhenItem whenItem = parseCaseExprWhenItem();
            caseExpr.addWhenItem(whenItem);

            if (lexer.token.kind != SQLToken.TokenKind.WHEN
                    && lexer.lowerHash != SQLKeyWord.WHEN.lowerHash) {
                break;
            }
        }

        SQLCaseExpr.SQLCaseExprElseClause elseClause = parseCaseExprElseClause();
        caseExpr.setElseClause(elseClause);

        acceptAndNextToken(SQLToken.TokenKind.END, true);

        return caseExpr;
    }

    /**
     * when [xx] then xx
     */
    public SQLCaseExpr.SQLCaseExprWhenItem parseCaseExprWhenItem() {
        if (lexer.token.kind != WHEN
                && lexer.lowerHash != SQLKeyWord.WHEN.lowerHash) {
            throw new SQLParserException("TODO: " + token());
        }

        nextToken();

        SQLCaseExpr.SQLCaseExprWhenItem whenItem = new SQLCaseExpr.SQLCaseExprWhenItem();

        if (lexer.token.kind != SQLToken.TokenKind.THEN
                && lexer.lowerHash != SQLKeyWord.THEN.lowerHash) {
            ISQLExpr expr = parseExpr();
            whenItem.setWhen(expr);
        }

        acceptAndNextToken(SQLKeyWord.THEN, true);

        ISQLExpr expr = parseExpr();
        whenItem.setThen(expr);

        return whenItem;
    }

    public SQLCaseExpr.SQLCaseExprElseClause parseCaseExprElseClause() {
        if (lexer.token.kind != ELSE
                && lexer.lowerHash != SQLKeyWord.ELSE.lowerHash) {
            return null;
        }

        nextToken();

        SQLCaseExpr.SQLCaseExprElseClause elseClause = new SQLCaseExpr.SQLCaseExprElseClause();
        ISQLExpr expr = parseExpr();
        elseClause.setExpr(expr);

        return elseClause;
    }


    public SQLSelectQueryExpr parseSelectQueryExpr() {
        return new SQLSelectQueryExpr(parseSelectQuery());
    }


    public SQLExistsCondition parseExistsCondition() {
        if (lexer.token.kind != SQLToken.TokenKind.EXISTS
                && lexer.lowerHash != SQLKeyWord.EXISTS.lowerHash) {
            return null;
        }
        nextToken();


        acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        ISQLSelectQuery subQuery = parseSelectQuery();
        acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        SQLExistsCondition x = new SQLExistsCondition(subQuery);

        return x;
    }


    /**
     * (expr, expr)
     */
    public SQLListExpr parseListExpr() {
        if (lexer.token.kind != SQLToken.TokenKind.LPAREN) {
            return null;
        }

        SQLListExpr x = new SQLListExpr();
        parseListExpr(x.getItems(), x);
        return x;
    }

    /**
     * (expr, expr)
     */
    public void parseListExpr(List<ISQLExpr> items, ISQLObject parent) {

        acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

        for (; ; ) {
            ISQLExpr expr = parseExpr();
            if (expr == null) {
                throw new SQLParserException("TODO:" + token());
            }
            items.add(expr);
            if (lexer.token.kind != SQLToken.TokenKind.COMMA) {
                break;
            }
            nextToken();
        }

        acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
    }


    /**
     * 该方法不能放到 parserExpr 中，需要单独调用
     * <p>
     * E: T := T |  T = T
     * T: expr
     * <p>
     * op( :=)
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseAssignmentOperatorExpr() {
        ISQLExpr expr = parseExpr();
        expr = parseAssignmentOperatorExpr(expr);
        return expr;
    }

    /**
     * 该方法不能放到 parserExpr 中，需要单独调用
     * <p>
     * E: T := T | T =(特别注意：不能做于condition比较) T
     * T: X or X
     * <p>
     * op(:=)
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseAssignmentOperatorExpr(ISQLExpr expr) {

        SQLToken token = token();
        switch (token.kind) {
            case EQ:
                nextToken();
                ISQLExpr right = parseXorOperatorExpr();
                expr = new SQLAssignmentExpr(expr, SQLAssignmentExpr.Operator.ASSIGN, right);
                expr = parseAssignmentOperatorExpr(expr);
                return expr;
            case ASSIGN:
                nextToken();
                right = parseXorOperatorExpr();
                expr = new SQLAssignmentExpr(expr, SQLAssignmentExpr.Operator.ASSIGN2, right);
                expr = parseAssignmentOperatorExpr(expr);
                return expr;
            default:
        }

        return expr;
    }


    /**
     * E: T (OR | ||) T (OR | ||) T
     * T: X XOR X
     * OR, ||
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseOrOperatorExpr() {
        ISQLExpr expr = parseXorOperatorExpr();
        expr = parseOrOperatorExpr(expr);
        return expr;
    }

    /**
     * E: T OR  T OR T
     * T: X XOR X
     * OR
     */
    public ISQLExpr parseOrOperatorExpr(ISQLExpr expr) {
        SQLToken token = token();
        switch (token.kind) {
            case OR:
                nextToken();
                ISQLExpr right = parseXorOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.OR, right);
                expr = parseOrOperatorExpr(expr);
                break;
            default:
        }
        return expr;
    }


    /**
     * E: T XOR T XOR T
     * T: X (AND, &&) X
     * <p>
     * XOR
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseXorOperatorExpr() {
        ISQLExpr expr = parseAndOperatorExpr();
        expr = parseXorOperatorExpr(expr);
        return expr;
    }

    /**
     * E: T XOR T XOR T
     * T: X (AND, &&) X
     * XOR
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseXorOperatorExpr(ISQLExpr expr) {
        SQLToken token = token();
        switch (token.kind) {
            case XOR:
                nextToken();
                ISQLExpr right = parseAndOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.XOR, right);
                expr = parseXorOperatorExpr(expr);
                break;
            default:
        }
        return expr;
    }

    /**
     * E: T AND T AND T
     * T: X relationalOp X
     * AND, &&
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseAndOperatorExpr() {
        ISQLExpr expr = parseComparisonOperatorExpr();
        expr = parseAndOperatorExpr(expr);
        return expr;
    }

    /**
     * E: T AND T AND T
     * T: NOT X
     * AND, &&
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseAndOperatorExpr(ISQLExpr expr) {
        SQLToken token = token();
        switch (token.kind) {
            case AND:
                nextToken();
                ISQLExpr right = parseComparisonOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.And, right);
                expr = parseAndOperatorExpr(expr);
                break;
            case AMPAMP:
                nextToken();
                right = parseComparisonOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.LOGIC_AND, right);
                expr = parseAndOperatorExpr(expr);
                break;
            default:
        }
        return expr;
    }

    /**
     * E: T op T op T ...
     * T: X | X
     * <p>
     * op: = (comparison), <=>, >=, >, <=, <, <>, !=, IS, [not] LIKE, REGEXP, IN,
     * [not] BETWEEN, CASE, WHEN, THEN, ELSE
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseComparisonOperatorExpr() {
        ISQLExpr expr = parseBitOrOperatorExpr();
        expr = parseComparisonOperatorExpr(expr);
        return expr;
    }

    /**
     * E: T op T op T ...
     * T: X | X
     * <p>
     * = (comparison), <=>, >=, >, <=, <, <>, !=, IS [NOT], LIKE, LIKEC, LIKE2, LIKE4, REGEXP, RLIKE, SOUNDS LIKE, BETWEEN, REGEXP, IN,
     * NOT (LIKE、BETWEEN、IN)
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseComparisonOperatorExpr(ISQLExpr expr) {
        SQLToken token = token();
        if (token.kind == SQLToken.TokenKind.EQ) {
            nextToken();
            ISQLExpr right = parseBitOrOperatorExpr();
            expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.EQ, right);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.LTEQGT) {
            nextToken();
            ISQLExpr right = parseBitOrOperatorExpr();
            expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.LESS_THAN_OR_EQUAL_OR_GREATER_THAN, right);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.GTEQ) {
            nextToken();
            ISQLExpr right = parseBitOrOperatorExpr();
            expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.GREATER_THAN_OR_EQUALS, right);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.GT) {
            nextToken();
            ISQLExpr right = parseBitOrOperatorExpr();
            expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.GREATER_THAN, right);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.LTEQ) {
            nextToken();
            ISQLExpr right = parseBitOrOperatorExpr();
            expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.LESS_THAN_OR_EQUALS, right);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.LT) {
            nextToken();
            ISQLExpr right = parseBitOrOperatorExpr();
            expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.LESS_THAN, right);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.LTGT) {
            nextToken();
            ISQLExpr right = parseBitOrOperatorExpr();
            expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.LESS_THAN_OR_GREATER, right);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.BANGEQ) {
            nextToken();
            ISQLExpr right = parseBitOrOperatorExpr();
            expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.NOT_EQUAL, right);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.IS
                || lexer.lowerHash == SQLKeyWord.IS.lowerHash) {
            nextToken();

            boolean not = false;
            if (lexer.token.kind == NOT
                    || lexer.lowerHash == SQLKeyWord.NOT.lowerHash) {
                not = true;
                nextToken();
            }

            // IS OF condition
            if (lexer.token.kind == SQLToken.TokenKind.OF
                    || lexer.lowerHash == SQLKeyWord.OF.lowerHash) {
                nextToken();

                boolean type = false;
                if (lexer.token.kind == SQLToken.TokenKind.TYPE
                        || lexer.lowerHash == SQLKeyWord.TYPE.lowerHash) {
                    nextToken();
                    type = true;
                }

                SQLIsOfTypeCondition isOfTypeCondition = new SQLIsOfTypeCondition(expr, not, type);

                acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
                for (; ; ) {
                    SQLIsOfTypeCondition.Item item = parseIsOfConditionItem();
                    isOfTypeCondition.addItem(item);
                    if (lexer.token.kind != COMMA) {
                        break;
                    }
                    nextToken();
                }

                acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

                expr = parseComparisonOperatorExpr(isOfTypeCondition);

                return expr;

            } else if (lexer.token.kind == SQLToken.TokenKind.JSON
                    || lexer.lowerHash == SQLKeyWord.JSON.lowerHash) {
                // IS JSON Condition
                nextToken();

                boolean formatJson = false;
                if (lexer.token.kind == SQLToken.TokenKind.FORMAT
                        || lexer.lowerHash == SQLKeyWord.FORMAT.lowerHash) {
                    nextToken();

                    if (lexer.token.kind == SQLToken.TokenKind.FORMAT
                            || lexer.lowerHash == SQLKeyWord.FORMAT.lowerHash) {
                        nextToken();
                    } else {
                        throw new SQLParserException(errorInfo());
                    }
                    formatJson = true;
                }


                if (lexer.token.kind == SQLToken.TokenKind.STRICT
                        || lexer.lowerHash == SQLKeyWord.STRICT.lowerHash) {
                    nextToken();
                } else if (lexer.token.kind == SQLToken.TokenKind.LAX
                        || lexer.lowerHash == SQLKeyWord.LAX.lowerHash) {
                    nextToken();
                }


                Boolean with = null;
                if (lexer.token.kind == SQLToken.TokenKind.WITH
                        || lexer.lowerHash == SQLKeyWord.WITH.lowerHash) {
                    nextToken();
                    with = true;
                } else if (lexer.token.kind == SQLToken.TokenKind.WITHOUT
                        || lexer.lowerHash == SQLKeyWord.WITHOUT.lowerHash) {
                    nextToken();
                    with = false;
                }

                if (with != null) {
                    if (lexer.token.kind == SQLToken.TokenKind.UNIQUE
                            || lexer.lowerHash == SQLKeyWord.UNIQUE.lowerHash) {
                        nextToken();
                    } else {
                        throw new SQLParserException(errorInfo());
                    }
                    if (lexer.token.kind == SQLToken.TokenKind.KEYS
                            || lexer.lowerHash == SQLKeyWord.KEYS.lowerHash) {
                        nextToken();
                    } else {
                        throw new SQLParserException(errorInfo());
                    }
                }

                SQLIsJsonCondition isJsonCondition = new SQLIsJsonCondition(expr);
                expr = parseComparisonOperatorExpr(isJsonCondition);

                return expr;
            }

            ISQLExpr right = parseBitOrOperatorExpr();
            expr = new SQLIsCondition(expr, not, right);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.LIKE) {
            nextToken();
            ISQLExpr right = parseBitOrOperatorExpr();

            ISQLExpr escape = null;
            if (lexer.token.kind == SQLToken.TokenKind.ESCAPE
                    || lexer.lowerHash == SQLKeyWord.ESCAPE.lowerHash) {
                nextToken();
                escape = parseExpr();
            }

            expr = new SQLLikeCondition(expr, false, SQLLikeOperator.LIKE, right, escape);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.LIKEC
                || lexer.lowerHash == SQLKeyWord.LIKEC.lowerHash) {
            nextToken();
            ISQLExpr right = parseBitOrOperatorExpr();

            ISQLExpr escape = null;
            if (lexer.token.kind == SQLToken.TokenKind.ESCAPE
                    || lexer.lowerHash == SQLKeyWord.ESCAPE.lowerHash) {
                nextToken();
                escape = parseExpr();
            }

            expr = new SQLLikeCondition(expr, false, SQLLikeOperator.LIKEC, right, escape);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.LIKE2
                || lexer.lowerHash == SQLKeyWord.LIKE2.lowerHash) {
            nextToken();
            ISQLExpr right = parseBitOrOperatorExpr();

            ISQLExpr escape = null;
            if (lexer.token.kind == SQLToken.TokenKind.ESCAPE
                    || lexer.lowerHash == SQLKeyWord.ESCAPE.lowerHash) {
                nextToken();
                escape = parseExpr();
            }

            expr = new SQLLikeCondition(expr, false, SQLLikeOperator.LIKE2, right, escape);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.LIKE4
                || lexer.lowerHash == SQLKeyWord.LIKE4.lowerHash) {
            nextToken();
            ISQLExpr right = parseBitOrOperatorExpr();

            ISQLExpr escape = null;
            if (lexer.token.kind == SQLToken.TokenKind.ESCAPE
                    || lexer.lowerHash == SQLKeyWord.ESCAPE.lowerHash) {
                nextToken();
                escape = parseExpr();
            }

            expr = new SQLLikeCondition(expr, false, SQLLikeOperator.LIKE4, right, escape);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.REGEXP
                || lexer.lowerHash == SQLKeyWord.REGEXP.lowerHash) {
            nextToken();
            ISQLExpr right = parseBitOrOperatorExpr();
            expr = new SQLRegexpCondition(expr, right);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.RLIKE
                || lexer.lowerHash == SQLKeyWord.RLIKE.lowerHash) {
            nextToken();
            ISQLExpr right = parseBitOrOperatorExpr();
            expr = new SQLRlikeCondition(expr, right);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.SOUNDS
                || lexer.lowerHash == SQLKeyWord.SOUNDS.lowerHash) {
            nextToken();
            acceptAndNextToken(SQLToken.TokenKind.LIKE, true);

            ISQLExpr right = parseBitOrOperatorExpr();
            expr = new SQLSoundsLikeCondition(expr, right);
            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.BETWEEN
                || lexer.lowerHash == SQLKeyWord.BETWEEN.lowerHash) {
            nextToken();

            ISQLExpr between = parseBitOrOperatorExpr();

            acceptAndNextToken(SQLToken.TokenKind.AND, true);
            ISQLExpr and = parseBitOrOperatorExpr();

            expr = new SQLBetweenCondition(expr, between, and);

            expr = parseComparisonOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.IN
                || lexer.lowerHash == SQLKeyWord.IN.lowerHash) {
            nextToken();
            acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

            SQLInCondition inCondition = new SQLInCondition(expr);
            for (; ; ) {
                ISQLExpr value = parseBitOrOperatorExpr();
                inCondition.addValue(value);
                if (lexer.token.kind != COMMA) {
                    break;
                }
                nextToken();
            }
            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            expr = parseComparisonOperatorExpr(inCondition);

        } else if (token.kind == SQLToken.TokenKind.NOT
                || lexer.lowerHash == SQLKeyWord.NOT.lowerHash) {

            expr = parseNotComparisonOperatorExpr(expr);

        }
        return expr;
    }

    public SQLIsOfTypeCondition.Item parseIsOfConditionItem() {
        boolean only = false;
        if (lexer.token.kind == SQLToken.TokenKind.ONLY) {
            nextToken();
            only = true;
        }
        ISQLName name = parseName();
        SQLIsOfTypeCondition.Item x = new SQLIsOfTypeCondition.Item(only, name);
        return x;
    }

    public ISQLExpr parseNotComparisonOperatorExpr(ISQLExpr expr) {
        SQLLexer.SQLMake make = this.make();

        boolean accept = acceptAndNextToken(NOT);
        if (!accept) {
            return expr;
        }


        if (this.acceptAndNextToken(SQLToken.TokenKind.LIKE)) {

            ISQLExpr right = parseBitOrOperatorExpr();

            ISQLExpr escape = null;
            if (lexer.token.kind == SQLToken.TokenKind.ESCAPE
                    || lexer.lowerHash == SQLKeyWord.ESCAPE.lowerHash) {
                nextToken();
                escape = parseExpr();
            }

            expr = new SQLLikeCondition(expr, true, SQLLikeOperator.LIKE, right, escape);
            expr = parseComparisonOperatorExpr(expr);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.LIKEC)) {

            ISQLExpr right = parseBitOrOperatorExpr();

            ISQLExpr escape = null;
            if (lexer.token.kind == SQLToken.TokenKind.ESCAPE
                    || lexer.lowerHash == SQLKeyWord.ESCAPE.lowerHash) {
                nextToken();
                escape = parseExpr();
            }

            expr = new SQLLikeCondition(expr, true, SQLLikeOperator.LIKEC, right, escape);
            expr = parseComparisonOperatorExpr(expr);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.LIKE2)) {

            ISQLExpr right = parseBitOrOperatorExpr();

            ISQLExpr escape = null;
            if (lexer.token.kind == SQLToken.TokenKind.ESCAPE
                    || lexer.lowerHash == SQLKeyWord.ESCAPE.lowerHash) {
                nextToken();
                escape = parseExpr();
            }

            expr = new SQLLikeCondition(expr, true, SQLLikeOperator.LIKE2, right, escape);
            expr = parseComparisonOperatorExpr(expr);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.LIKE4)) {

            ISQLExpr right = parseBitOrOperatorExpr();

            ISQLExpr escape = null;
            if (lexer.token.kind == SQLToken.TokenKind.ESCAPE
                    || lexer.lowerHash == SQLKeyWord.ESCAPE.lowerHash) {
                nextToken();
                escape = parseExpr();
            }

            expr = new SQLLikeCondition(expr, false, SQLLikeOperator.LIKE4, right, escape);
            expr = parseComparisonOperatorExpr(expr);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.REGEXP)) {

            ISQLExpr right = parseBitOrOperatorExpr();
            expr = new SQLRegexpCondition(expr, right);
            expr = parseComparisonOperatorExpr(expr);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.RLIKE)) {

            ISQLExpr right = parseBitOrOperatorExpr();
            expr = new SQLRlikeCondition(expr, right);
            expr = parseComparisonOperatorExpr(expr);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.SOUNDS)) {

            acceptAndNextToken(SQLToken.TokenKind.LIKE, true);

            ISQLExpr right = parseBitOrOperatorExpr();
            expr = new SQLSoundsLikeCondition(expr, right);
            expr = parseComparisonOperatorExpr(expr);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.BETWEEN)) {

            ISQLExpr between = parseBitOrOperatorExpr();

            acceptAndNextToken(SQLToken.TokenKind.AND, true);
            ISQLExpr and = parseBitOrOperatorExpr();

            expr = new SQLBetweenCondition(expr, true, between, and);

            expr = parseComparisonOperatorExpr(expr);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.IN)) {

            acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

            SQLInCondition inCondition = new SQLInCondition(expr, true);
            for (; ; ) {
                ISQLExpr value = parseBitOrOperatorExpr();
                inCondition.addValue(value);
                if (lexer.token.kind != COMMA) {
                    break;
                }
                nextToken();
            }
            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            expr = parseComparisonOperatorExpr(inCondition);

        } else {
            this.reset(make);
        }

        return expr;
    }

    /**
     * E: E | T  | T   => T | T | T
     * T: X & X
     * <p>
     * |
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseBitOrOperatorExpr() {
        ISQLExpr expr = parseBitAndOperatorExpr();
        expr = parseBitOrOperatorExpr(expr);
        return expr;
    }

    /**
     * E: E | T  | T   => T | T | T
     * T: X & X
     * |
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseBitOrOperatorExpr(ISQLExpr expr) {
        SQLToken token = token();
        switch (token.kind) {
            case BAR:
                nextToken();
                ISQLExpr right = parseBitAndOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.BIT_OR, right);
                expr = parseBitOrOperatorExpr(expr);
                break;
            default:
        }
        return expr;
    }

    /**
     * E: T & T & T
     * T: X (<<,>> ) X
     * &
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseBitAndOperatorExpr() {
        ISQLExpr expr = parseShiftOperatorExpr();
        expr = parseBitAndOperatorExpr(expr);
        return expr;
    }

    /**
     * E: T & T & T
     * T: X (<<,>> ) X
     * <p>
     * &
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseBitAndOperatorExpr(ISQLExpr expr) {
        SQLToken token = token();
        switch (token.kind) {
            case AMP:
                nextToken();
                ISQLExpr right = parseShiftOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.BIT_AND, right);
                expr = parseBitAndOperatorExpr(expr);
                break;
            default:
        }
        return expr;
    }

    /**
     * E: T (<<, >>) T (<<, >>)
     * T: X ((+/-)) X
     * <<, >>
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseShiftOperatorExpr() {
        ISQLExpr expr = parseAdditiveOperatorExpr();
        expr = parseShiftOperatorExpr(expr);
        return expr;
    }

    /**
     * E: T (<<, >>) T (<<, >>)
     * T: X ((+/-)) X
     * <<, >>
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseShiftOperatorExpr(ISQLExpr expr) {
        SQLToken token = token();

        switch (token.kind) {
            case LTLT:
                nextToken();
                ISQLExpr right = parseAdditiveOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.LESS_THAN_LESS_THAN, right);
                expr = parseShiftOperatorExpr(expr);
                break;
            case GTGT:
                nextToken();
                right = parseAdditiveOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.GREATER_THAN_GREATER_THAN, right);
                expr = parseShiftOperatorExpr(expr);
                break;
            default:
        }
        return expr;
    }


    /**
     * E: T (+/-) T (+/-) T ...
     * T: X (*, /, DIV, %, MOD) X
     *
     * <p>
     * +, -
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseAdditiveOperatorExpr() {
        ISQLExpr expr = parseMultiplicativeOperatorExpr();
        expr = parseAdditiveOperatorExpr(expr);
        return expr;
    }

    /**
     * E: T (+/-/||) T (+/-/||) T ...
     * T: X (*, /, DIV, %, MOD) X
     * <p>
     * +, -
     */
    public ISQLExpr parseAdditiveOperatorExpr(ISQLExpr expr) {
        SQLToken token = token();
        switch (token.kind) {
            case PLUS:
                nextToken();
                ISQLExpr right = parseMultiplicativeOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.Add, right);
                expr = parseAdditiveOperatorExpr(expr);
                break;
            case SUB:
                nextToken();
                right = parseMultiplicativeOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.Sub, right);
                expr = parseAdditiveOperatorExpr(expr);
                break;
            case BARBAR:
                nextToken();
                right = parseOrOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.Concat, right);
                expr = parseOrOperatorExpr(expr);
                break;
            default:

        }
        return expr;
    }

    /**
     * E: T op T op T ...
     * T: X ^ X
     * OP: *, /, DIV, %, MOD
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseMultiplicativeOperatorExpr() {
        ISQLExpr expr = parseBitXorOperatorExpr();
        expr = parseMultiplicativeOperatorExpr(expr);
        return expr;
    }

    /**
     * E: T op T op T ...
     * T: X ^ X
     * OP: *, /, DIV, %, MOD
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseMultiplicativeOperatorExpr(ISQLExpr expr) {
        SQLToken token = token();
        if (token.kind == SQLToken.TokenKind.STAR) {
            nextToken();
            ISQLExpr right = parseBitXorOperatorExpr();
            expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.Multiply, right);
            expr = parseMultiplicativeOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.SLASH) {
            nextToken();
            ISQLExpr right = parseBitXorOperatorExpr();
            expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.Divide, right);
            expr = parseMultiplicativeOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.PERCENT) {
            nextToken();
            ISQLExpr right = parseBitXorOperatorExpr();
            expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.PERCENT, right);
            expr = parseMultiplicativeOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.DIV
                || lexer.lowerHash == SQLKeyWord.DIV.lowerHash) {
            nextToken();
            ISQLExpr right = parseBitXorOperatorExpr();
            expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.DIV, right);
            expr = parseMultiplicativeOperatorExpr(expr);

        } else if (token.kind == SQLToken.TokenKind.MOD
                || lexer.lowerHash == SQLKeyWord.MOD.lowerHash) {
            nextToken();
            ISQLExpr right = parseBitXorOperatorExpr();
            expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.MOD, right);
            expr = parseMultiplicativeOperatorExpr(expr);
        }

        return expr;
    }

    /**
     * E: T ^ T ^ T
     * T: primaryExpr
     * <p>
     * ^
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseBitXorOperatorExpr() {
        ISQLExpr expr = parseExprPrimary();
        return parseBitXorOperatorExpr(expr);
    }

    /**
     * E: T ^ T ^ T
     * T: primaryExpr
     * <p>
     * ^
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    public ISQLExpr parseBitXorOperatorExpr(ISQLExpr expr) {
        SQLToken token = token();
        switch (token.kind) {
            case CARET:
                nextToken();
                ISQLExpr right = parseBitXorOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.BIT_XOR, right);
                expr = parseBitXorOperatorExpr(expr);
                break;
            default:
        }
        return expr;
    }


    /**
     * [default] CHARACTER SET [=] name
     */
    public SQLCharacterSetOptionExpr parseCharacterSetClause() {
        SQLLexer.SQLMake make = this.make();

        boolean default_ = acceptAndNextToken(SQLToken.TokenKind.DEFAULT);

        if (acceptAndNextToken(SQLToken.TokenKind.CHARACTER)) {
            acceptAndNextToken(SQLToken.TokenKind.SET, true);
            boolean eq = acceptAndNextToken(SQLToken.TokenKind.EQ);
            return new SQLCharacterSetOptionExpr(default_, eq, parseName());
        }

        reset(make);
        return null;
    }

    /**
     * [default] COLLATE [=] name
     */
    public SQLCollateOptionExpr parseCollateClause() {
        SQLLexer.SQLMake make = make();
        boolean default_ = acceptAndNextToken(SQLToken.TokenKind.DEFAULT);

        if (acceptAndNextToken(SQLToken.TokenKind.COLLATE)) {
            boolean eq = acceptAndNextToken(SQLToken.TokenKind.EQ);
            return new SQLCollateOptionExpr(default_, eq, parseName());
        }

        reset(make);
        return null;
    }

    /**
     * ALGORITHM [=] name
     */
    public SQLAlgorithmOptionExpr parseAlgorithmOptionExpr() {
//        SQLLexer.SQLMake make = make();
        if (acceptAndNextToken(SQLToken.TokenKind.ALGORITHM)) {
            boolean eq = acceptAndNextToken(SQLToken.TokenKind.EQ, true);
            return new SQLAlgorithmOptionExpr(parseName());
        }

//        reset(make);
        return null;
    }

    /**
     * DEFINER [=] name
     */
    public SQLDefinerOptionExpr parseDefinerOptionExpr() {
//        SQLLexer.SQLMake make = make();
        if (acceptAndNextToken(SQLToken.TokenKind.DEFINER)) {
            acceptAndNextToken(SQLToken.TokenKind.EQ, true);
            return new SQLDefinerOptionExpr(parseName());
        }
//        reset(make);
        return null;
    }

    /**
     * SQL SECURITY { DEFINER | INVOKER }
     */
    public SQLSecurityType parseSQLSecurity() {
        if (!this.acceptAndNextToken(SQL)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.SECURITY, true);

        if (this.acceptAndNextToken(SQLToken.TokenKind.DEFINER)) {
            return SQLSecurityType.SQL_SECURITY_DEFINER;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.INVOKER)) {
            return SQLSecurityType.SQL_SECURITY_INVOKER;

        }

        throw new SQLParserException(errorInfo());
    }


    public SQLDirectoryOptionExpr parseDirectoryOptionExpr() {
        SQLLexer.SQLMake make = make();
        boolean default_ = acceptAndNextToken(SQLToken.TokenKind.DEFAULT);

        if (acceptAndNextToken(SQLToken.TokenKind.DIRECTORY)) {
            boolean eq = acceptAndNextToken(SQLToken.TokenKind.EQ);
            return new SQLDirectoryOptionExpr(default_, eq, parseName());
        }

        reset(make);
        return null;
    }


    // --------------------------- Parse Select --------------------------------------------------------

    public ISQLSelectQuery parseSelectQuery() {
        ISQLSelectQuery x = parseQueryBlock();
        if (x == null) {
            return null;
        }

        SQLOrderByClause orderByClause = parseOrderBy();
        x.setOrderByClause(orderByClause);

        ISQLLimitClause limitClause = parseLimit();
        x.setLimitClause(limitClause);

        ISQLLockClause lockClause = parseLockClause();
        x.setLockClause(lockClause);

        return x;
    }

    public ISQLSelectQuery parseQueryBlock() {
        ISQLSelectQuery x = parseQueryBlockPrimary();
        x = parseUnionQueryBlock(x);
        return x;
    }


    /**
     * withClause? SELECT setQuantifier? selectItem (COMMA selectItem)* fromClause? whereClause?
     * hierarchicalQueryClause? groupByClause? modelClause? orderByClause? rowLimitingClause? forUpdateClause?
     * <p>
     * LEFT_PAREN iSelectQuery RIGHT_PAREN orderByClause? rowLimitingClause? forUpdateClause?
     */
    public ISQLSelectQuery parseQueryBlockPrimary() {

        if (lexer.token.kind == SQLToken.TokenKind.LPAREN) {
            nextToken();

            ISQLSelectQuery selectQuery = parseSelectQuery();
            SQLParenSelectQuery query = new SQLParenSelectQuery(selectQuery);
            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            return query;

        }

        SQLSelectQuery query = new SQLSelectQuery();

        SQLWithClause withClause = parseWith();

        boolean accept = acceptAndNextToken(SQLToken.TokenKind.SELECT);
        if (!accept) {
            return null;
        }

        query.setWithClause(withClause);

        SQLSetQuantifier setQuantifier = parseSetQuantifier();
        query.setSetQuantifier(setQuantifier);

        parseSelectItems(query);

        parseSelectTargetItems(query);

        SQLFromClause fromClause = parseFrom();
        query.setFromClause(fromClause);

        SQLWhereClause whereClause = parseWhere();
        query.setWhereClause(whereClause);

        SQLGroupByClause groupByClause = parseGroupBy();
        query.setGroupByClause(groupByClause);

        SQLWindowClause windowClause = parseWindow();
        query.setWindowClause(windowClause);

        return query;
    }

    public SQLWithClause parseWith() {
        if (lexer.token.kind != SQLToken.TokenKind.WITH) {
            return null;
        }
        nextToken();

        SQLWithClause x = new SQLWithClause();

        if (this.acceptAndNextToken(SQLToken.TokenKind.RECURSIVE)) {
            x.setRecursive(true);
        }

        for (; ; ) {

            SQLWithClause.SQLWithElement element = parseWithElement();
            x.addWithElement(element);
            if (lexer.token.kind != COMMA) {
                break;
            }
            nextToken();
        }

        return x;
    }

    private SQLWithClause.SQLWithElement parseWithElement() {
        make();

        SQLWithClause.SQLWithElement element = null;

        // SubQueryFactoring
        element = parseSubQueryFactoringClause();

        if (element == null) {
            // SubavFactoringClause

        }

        return element;
    }

    /**
     *
     */
    public SQLWithClause.SQLSubQueryFactoringClause parseSubQueryFactoringClause() {
        SQLLexer.SQLMake make = make();

        SQLWithClause.SQLSubQueryFactoringClause x = new SQLWithClause.SQLSubQueryFactoringClause();

        ISQLName name = parseName();
        x.setQueryName(name);

        if (lexer.token.kind == SQLToken.TokenKind.LPAREN) {

            do {
                nextToken();
                ISQLExpr column = parseName();
                x.addColumn(column);
            } while (lexer.token.kind != COMMA);

            acceptAndNextToken(SQLToken.TokenKind.RPAREN);
        }

        if (lexer.token.kind != SQLToken.TokenKind.AS) {
            reset(make);
            return null;
        }
        nextToken();

        acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

        ISQLStatement statement = parseStatement();
        x.setStatement(statement);

        acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);


        if (lexer.token.kind == SQLToken.TokenKind.SEARCH
                || lexer.lowerHash != SQLKeyWord.SEARCH.lowerHash) {

        }

        if (lexer.token.kind == SQLToken.TokenKind.CYCLE
                || lexer.lowerHash != SQLKeyWord.CYCLE.lowerHash) {

        }


        return x;
    }


    public SQLSetQuantifier parseSetQuantifier() {

        SQLSetQuantifier setQuantifier = null;

        if (lexer.token.kind == SQLToken.TokenKind.DISTINCT
                || lexer.lowerHash == SQLKeyWord.DISTINCT.lowerHash) {

            setQuantifier = SQLSetQuantifier.DISTINCT;

        } else if (lexer.token.kind == SQLToken.TokenKind.ALL
                || lexer.lowerHash == SQLKeyWord.ALL.lowerHash) {

            setQuantifier = SQLSetQuantifier.ALL;

        } else if (lexer.token.kind == SQLToken.TokenKind.UNIQUE
                || lexer.lowerHash == SQLKeyWord.UNIQUE.lowerHash) {

            setQuantifier = SQLSetQuantifier.UNIQUE;

        } else if (lexer.token.kind == SQLToken.TokenKind.DISTINCTROW
                || lexer.lowerHash == SQLKeyWord.DISTINCTROW.lowerHash) {

            setQuantifier = SQLSetQuantifier.DISTINCTROW;

        }

        if (setQuantifier != null) {
            nextToken();
        }

        return setQuantifier;
    }

    public void parseSelectItems(SQLSelectQuery parent) {

        for (; ; ) {

            SQLSelectItem item = parseSelectItem();
            addBeforeComments(item);
            parent.addSelectItem(item);

            if (lexer.token.kind != SQLToken.TokenKind.COMMA) {
                break;
            }
            nextToken();
        }
    }

    public SQLSelectItem parseSelectItem() {
        ISQLExpr expr = parseExpr();

        if (this.accept(FROM)
                || this.accept(BULK)) {
            return new SQLSelectItem(expr);
        }

        boolean as = acceptAndNextToken(SQLToken.TokenKind.AS);

        ISQLExpr alias = parseExpr();
        if (as
                && alias == null) {
            throw new SQLParserException("alias is null. " + errorInfo());
        }

        return new SQLSelectItem(expr, as, alias);
    }


    public void parseSelectTargetItems(SQLSelectQuery parent) {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.INTO)) {
            return;
        }

        for (; ; ) {
            ISQLExpr expr = parseExpr();
            parent.addSelectTargetItem(expr);
            if (!this.acceptAndNextToken(COMMA)) {
                break;
            }
        }
    }

    public SQLFromClause parseFrom() {
        if (lexer.token.kind != SQLToken.TokenKind.FROM) {
            return null;
        }
        nextToken();
        ISQLTableReference tableReference = parseTableReference();

        return new SQLFromClause(tableReference);
    }

    /**
     * E: T (JOIN, COMM) T JOIN T
     * T: primary
     */
    public ISQLTableReference parseTableReference() {
        // Multi Table Delete
        if (lexer.token.kind == SQLToken.TokenKind.FROM) {
            return null;
        }
        ISQLTableReference tableReference = parseTableReferencePrimary();
        tableReference = parseJoinTableReference(tableReference);
        return tableReference;
    }

    /**
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#table%20primary
     */
    public ISQLTableReference parseTableReferencePrimary() {

        ISQLTableReference tableReference = null;

        if (lexer.token.kind == SQLToken.TokenKind.LPAREN) {
            nextToken();
            tableReference = parseTableReference();
            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

            boolean as = false;
            if (lexer.token.kind == SQLToken.TokenKind.AS) {
                nextToken();
                as = true;
            }

            ISQLIdentifier alias = parseIdentifier();
            if (as && alias == null) {
                throw new SQLParserException("");
            }

            tableReference.setParen(true);
            tableReference.setAs(as);
            tableReference.setAlias(alias);

        } else if (lexer.token.kind == SQLToken.TokenKind.WITH
                || lexer.lowerHash == SQLKeyWord.WITH.lowerHash
                || lexer.token.kind == SQLToken.TokenKind.SELECT) {

            ISQLSelectQuery subQuery = parseSelectQuery();

            tableReference = new SQLSubQueryTableReference(subQuery);

        } else if (lexer.token.kind == SQLToken.TokenKind.ONLY
                || lexer.lowerHash == SQLKeyWord.ONLY.lowerHash) {
            nextToken();


        } else if (lexer.token.kind == SQLToken.TokenKind.TABLE
                || lexer.lowerHash == SQLKeyWord.TABLE.lowerHash) {

            tableReference = parserTableFunctionTableReference();

        } else if (lexer.token.kind == SQLToken.TokenKind.UNNEST
                || lexer.lowerHash == SQLKeyWord.UNNEST.lowerHash) {
            nextToken();

        } else if (lexer.token.kind == SQLToken.TokenKind.LATERAL
                || lexer.lowerHash == SQLKeyWord.LATERAL.lowerHash) {


        } else if (isIdentifier(lexer.token.kind)) {

            ISQLName name = parseName();

            ISQLPartitionExtensionClause partitionExtensionClause = parsePartitionExtensionClause();

            boolean as = false;
            if (lexer.token.kind == SQLToken.TokenKind.AS) {
                nextToken();
                as = true;
            }

            ISQLIdentifier alias = parseIdentifier();

            if (as && alias == null) {
                throw new SQLParserException("");
            }

            tableReference = new SQLObjectNameTableReference(name, partitionExtensionClause, as, alias);
        }

        return tableReference;
    }

    /**
     * TABLE (expr) [(+)]
     */
    public SQLTableFunctionTableReference parserTableFunctionTableReference() {
        boolean accept = acceptAndNextToken(SQLToken.TokenKind.TABLE);
        if (!accept) {
            return null;
        }

        ISQLExpr expr = parseExpr();

        SQLTableFunctionTableReference x = new SQLTableFunctionTableReference(expr);

        return x;
    }

    /**
     * Join , comma
     */
    public ISQLTableReference parseJoinTableReference(ISQLTableReference tableReference) {
        SQLToken token = token();

        if (token.kind == SQLToken.TokenKind.RPAREN
                || token.kind == SQLToken.TokenKind.WHERE) {
            return tableReference;
        }

        if (tableReference == null) {
            throw new SQLParserException("");
        }

        SQLJoinTableReference.SQLJoinType joinType = null;
        if (token.kind == COMMA) {
            nextToken();

            joinType = SQLJoinTableReference.SQLJoinType.COMMA;

        } else if (token.kind == SQLToken.TokenKind.JOIN) {
            nextToken();

            joinType = SQLJoinTableReference.SQLJoinType.JOIN;

        } else if (token.kind == SQLToken.TokenKind.INNER) {
            nextToken();

            acceptAndNextToken(SQLToken.TokenKind.JOIN, true);

            joinType = SQLJoinTableReference.SQLJoinType.INNER_JOIN;

        } else if (token.kind == SQLToken.TokenKind.CROSS) {
            nextToken();

            if (lexer.token.kind == SQLToken.TokenKind.JOIN
                    || lexer.lowerHash == SQLKeyWord.JOIN.lowerHash) {
                nextToken();
                joinType = SQLJoinTableReference.SQLJoinType.CROSS_JOIN;

            } else if (lexer.token.kind == SQLToken.TokenKind.APPLY
                    || lexer.lowerHash == SQLKeyWord.APPLY.lowerHash) {
                nextToken();
                joinType = SQLJoinTableReference.SQLJoinType.CROSS_APPLY;

            } else {
                throw new SQLParserException("");
            }

        } else if (token.kind == SQLToken.TokenKind.LEFT) {
            nextToken();

            joinType = SQLJoinTableReference.SQLJoinType.LEFT_JOIN;
            if (lexer.token.kind == SQLToken.TokenKind.OUTER
                    || lexer.lowerHash == SQLKeyWord.OUTER.lowerHash) {
                nextToken();
                joinType = SQLJoinTableReference.SQLJoinType.LEFT_OUTER_JOIN;
            }

            acceptAndNextToken(SQLToken.TokenKind.JOIN, true);

        } else if (token.kind == SQLToken.TokenKind.RIGHT) {
            nextToken();

            joinType = SQLJoinTableReference.SQLJoinType.RIGHT_JOIN;
            if (lexer.token.kind == SQLToken.TokenKind.OUTER
                    || lexer.lowerHash == SQLKeyWord.OUTER.lowerHash) {
                nextToken();
                joinType = SQLJoinTableReference.SQLJoinType.RIGHT_OUTER_JOIN;
            }

            acceptAndNextToken(SQLToken.TokenKind.JOIN, true);

        } else if (token.kind == SQLToken.TokenKind.FULL) {
            nextToken();

            joinType = SQLJoinTableReference.SQLJoinType.FULL_JOIN;
            if (lexer.token.kind == SQLToken.TokenKind.OUTER
                    || lexer.lowerHash == SQLKeyWord.OUTER.lowerHash) {
                nextToken();
                joinType = SQLJoinTableReference.SQLJoinType.FULL_OUTER_JOIN;
            }

            acceptAndNextToken(SQLToken.TokenKind.JOIN, true);

        } else if (token.kind == SQLToken.TokenKind.NATURAL) {
            nextToken();

            joinType = SQLJoinTableReference.SQLJoinType.NATURAL_JOIN;

            if (lexer.token.kind == SQLToken.TokenKind.INNER) {
                nextToken();

                joinType = SQLJoinTableReference.SQLJoinType.NATURAL_INNER_JOIN;

            } else if (lexer.token.kind == SQLToken.TokenKind.LEFT) {
                nextToken();

                joinType = SQLJoinTableReference.SQLJoinType.NATURAL_LEFT_JOIN;

                if (lexer.token.kind == SQLToken.TokenKind.OUTER
                        || lexer.lowerHash == SQLKeyWord.OUTER.lowerHash) {
                    nextToken();
                    joinType = SQLJoinTableReference.SQLJoinType.NATURAL_LEFT_OUTER_JOIN;
                }

            } else if (lexer.token.kind == SQLToken.TokenKind.RIGHT) {
                nextToken();

                joinType = SQLJoinTableReference.SQLJoinType.NATURAL_RIGHT_JOIN;

                if (lexer.token.kind == SQLToken.TokenKind.OUTER
                        || lexer.lowerHash == SQLKeyWord.OUTER.lowerHash) {
                    nextToken();
                    joinType = SQLJoinTableReference.SQLJoinType.NATURAL_RIGHT_OUTER_JOIN;
                }

            } else if (lexer.token.kind == SQLToken.TokenKind.FULL) {
                nextToken();

                joinType = SQLJoinTableReference.SQLJoinType.NATURAL_FULL_JOIN;

                if (lexer.token.kind == SQLToken.TokenKind.OUTER
                        || lexer.lowerHash == SQLKeyWord.OUTER.lowerHash) {
                    nextToken();
                    joinType = SQLJoinTableReference.SQLJoinType.NATURAL_FULL_OUTER_JOIN;
                }
            }

            acceptAndNextToken(SQLToken.TokenKind.JOIN, true);


        } else if (token.kind == SQLToken.TokenKind.OUTER) {
            nextToken();

            if (lexer.token.kind == SQLToken.TokenKind.APPLY
                    || lexer.lowerHash == SQLKeyWord.APPLY.lowerHash) {
                nextToken();
                joinType = SQLJoinTableReference.SQLJoinType.OUTER_APPLY;

            } else {
                throw new SQLParserException("");
            }

        }

        if (joinType != null) {
            ISQLTableReference right = parseTableReferencePrimary();
            SQLJoinTableReference joinTableReference = new SQLJoinTableReference(tableReference, joinType, right);
            parseJoinConditions(joinTableReference);
            return parseJoinTableReference(joinTableReference);
        }

        return tableReference;
    }

    public ISQLPartitionExtensionClause parsePartitionExtensionClause() {

        ISQLPartitionExtensionClause x = null;
        if (lexer.token.kind == SQLToken.TokenKind.PARTITION
                || lexer.lowerHash == SQLKeyWord.PARTITION.lowerHash) {
            nextToken();

            x = new ISQLPartitionExtensionClause.SQLPartitionClause();
            if (lexer.token.kind == SQLToken.TokenKind.FOR
                    || lexer.lowerHash == SQLKeyWord.FOR.lowerHash) {
                nextToken();
                x = new ISQLPartitionExtensionClause.SQLPartitionForClause();
            }

            acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            for (; ; ) {
                ISQLExpr item = parseExpr();
                x.addItem(item);
                if (lexer.token.kind != COMMA) {
                    break;
                }
                nextToken();
            }
            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);


        } else if (lexer.token.kind == SQLToken.TokenKind.SUBPARTITION
                || lexer.lowerHash == SQLKeyWord.SUBPARTITION.lowerHash) {
            nextToken();

            x = new ISQLPartitionExtensionClause.SQLSubPartitionClause();
            if (lexer.token.kind == SQLToken.TokenKind.FOR
                    || lexer.lowerHash == SQLKeyWord.FOR.lowerHash) {
                x = new ISQLPartitionExtensionClause.SQLSubPartitionForClause();
            }

            acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            for (; ; ) {
                ISQLExpr item = parseExpr();
                x.addItem(item);
                if (lexer.token.kind != COMMA) {
                    break;
                }
                nextToken();
            }
            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return x;
    }

    public void parseJoinConditions(SQLJoinTableReference tableReference) {

        for (; ; ) {
            if (lexer.token.kind == SQLToken.TokenKind.ON
                    || lexer.lowerHash == SQLKeyWord.ON.lowerHash) {
                nextToken();

                SQLJoinTableReference.SQLJoinOnCondition onCondition = new SQLJoinTableReference.SQLJoinOnCondition();
                ISQLExpr condition = parseExpr();
                onCondition.setCondition(condition);

                tableReference.addCondition(onCondition);

            } else if (lexer.token.kind == SQLToken.TokenKind.USING
                    || lexer.lowerHash == SQLKeyWord.USING.lowerHash) {
                nextToken();

                SQLJoinTableReference.SQLJoinUsingCondition usingCondition = new SQLJoinTableReference.SQLJoinUsingCondition();
                acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
                for (; ; ) {
                    ISQLExpr column = parseExpr();
                    usingCondition.addColumn(column);
                    if (lexer.token.kind != COMMA) {
                        break;
                    }
                    nextToken();
                }
                acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

                tableReference.addCondition(usingCondition);

            } else {

                break;

            }
        }

    }


    public SQLWhereClause parseWhere() {
        if (lexer.token.kind != SQLToken.TokenKind.WHERE) {
            return null;
        }
        nextToken();

        ISQLExpr condition = null;
        if (lexer.token.kind == SQLToken.TokenKind.CURRENT
                || lexer.lowerHash == SQLKeyWord.CURRENT.lowerHash) {
            nextToken();
            if (lexer.token.kind == SQLToken.TokenKind.OF
                    || lexer.lowerHash == SQLKeyWord.OF.lowerHash) {
                nextToken();

                ISQLName name = parseName();
                condition = new SQLCurrentOfClause(name);
            } else {
                throw new SQLParserException("");
            }

        } else {
            condition = parseExpr();
        }

        return new SQLWhereClause(condition);
    }


    public ISQLHierarchicalQueryClause parseHierarchicalQuery() {
        if (lexer.token.kind == SQLToken.TokenKind.CONNECT
                || lexer.lowerHash == SQLKeyWord.CONNECT.lowerHash) {

            return parseConnectByStartWithClause();

        } else if (lexer.token.kind == SQLToken.TokenKind.START
                || lexer.lowerHash == SQLKeyWord.START.lowerHash) {

            return parseStartWithConnectByClause();

        }
        return null;
    }

    /**
     * CONNECT BY [ NOCYCLE ] condition [ START WITH condition ]
     */
    public ISQLHierarchicalQueryClause parseConnectByStartWithClause() {
        if (lexer.token.kind != SQLToken.TokenKind.CONNECT
                && lexer.lowerHash != SQLKeyWord.CONNECT.lowerHash) {
            return null;
        }
        nextToken();

        if (lexer.token.kind != SQLToken.TokenKind.BY
                && lexer.lowerHash != SQLKeyWord.BY.lowerHash) {
            throw new SQLParserException(errorInfo());
        }
        nextToken();

        boolean nocycle = false;
        if (lexer.token.kind == SQLToken.TokenKind.NOCYCLE
                || lexer.lowerHash == SQLKeyWord.NOCYCLE.lowerHash) {
            nextToken();
            nocycle = true;
        }

        ISQLExpr connectByCondition = parseExpr();

        ISQLExpr startWithCondition = null;
        if (lexer.token.kind == SQLToken.TokenKind.START
                || lexer.lowerHash == SQLKeyWord.START.lowerHash) {
            nextToken();

            if (lexer.token.kind != SQLToken.TokenKind.WITH
                    && lexer.lowerHash != SQLKeyWord.WITH.lowerHash) {
                throw new SQLParserException(errorInfo());
            }
            nextToken();

            startWithCondition = parseExpr();
        }

        return new ISQLHierarchicalQueryClause.SQLHierarchicalQueryConnectByStartWithClause(nocycle, connectByCondition, startWithCondition);
    }

    /**
     * START WITH condition CONNECT BY [ NOCYCLE ] condition
     */
    public ISQLHierarchicalQueryClause parseStartWithConnectByClause() {
        if (lexer.token.kind != SQLToken.TokenKind.START
                && lexer.lowerHash != SQLKeyWord.START.lowerHash) {
            return null;
        }
        nextToken();

        if (lexer.token.kind != SQLToken.TokenKind.WITH
                && lexer.lowerHash != SQLKeyWord.WITH.lowerHash) {
            throw new SQLParserException(errorInfo());
        }
        nextToken();

        ISQLExpr startWithCondition = parseExpr();


        if (lexer.token.kind != SQLToken.TokenKind.CONNECT
                && lexer.lowerHash != SQLKeyWord.CONNECT.lowerHash) {
            throw new SQLParserException(errorInfo());
        }
        nextToken();

        if (lexer.token.kind != SQLToken.TokenKind.BY
                && lexer.lowerHash != SQLKeyWord.BY.lowerHash) {
            throw new SQLParserException(errorInfo());
        }
        nextToken();

        boolean nocycle = false;
        if (lexer.token.kind == SQLToken.TokenKind.NOCYCLE
                || lexer.lowerHash == SQLKeyWord.NOCYCLE.lowerHash) {
            nextToken();
            nocycle = true;
        }

        ISQLExpr connectByCondition = parseExpr();

        return new ISQLHierarchicalQueryClause.SQLHierarchicalQueryStartWithConnectByClause(nocycle, connectByCondition, startWithCondition);
    }


    public SQLGroupByClause parseGroupBy() {
        SQLGroupByClause x = null;
        if (lexer.token.kind == SQLToken.TokenKind.GROUP) {
            nextToken();
            acceptAndNextToken(SQLToken.TokenKind.BY);

            x = new SQLGroupByClause();

            SQLSetQuantifier setQuantifier = parseSetQuantifier();
            x.setQuantifier(setQuantifier);

            for (; ; ) {
                SQLGroupByClause.SQLGroupByItem item = parseGroupByItem();
                x.addItem(item);

                if (lexer.token.kind != COMMA) {
                    break;
                }
                nextToken();
            }

            SQLHavingClause havingClause = parseHavingClause();
            x.setHavingClause(havingClause);

        } else if (lexer.token.kind == SQLToken.TokenKind.HAVING) {
            x = new SQLGroupByClause();
            x.setOrder(false);

            SQLHavingClause havingClause = parseHavingClause();
            x.setHavingClause(havingClause);

            acceptAndNextToken(SQLToken.TokenKind.GROUP, true);
            acceptAndNextToken(SQLToken.TokenKind.BY, true);

            SQLSetQuantifier setQuantifier = parseSetQuantifier();
            x.setQuantifier(setQuantifier);

            for (; ; ) {
                SQLGroupByClause.SQLGroupByItem item = parseGroupByItem();
                x.addItem(item);

                if (lexer.token.kind != COMMA) {
                    break;
                }
                nextToken();
            }
        }

        return x;
    }

    public SQLGroupByClause.SQLGroupByItem parseGroupByItem() {
        ISQLExpr expr = parseExpr();
        SQLGroupByClause.SQLGroupByItem x = new SQLGroupByClause.SQLGroupByItem(expr);
        return x;
    }

    public SQLHavingClause parseHavingClause() {
        if (lexer.token.kind != SQLToken.TokenKind.HAVING
                || lexer.lowerHash != SQLKeyWord.HAVING.lowerHash) {
            return null;
        }
        nextToken();
        ISQLExpr expr = parseExpr();
        SQLHavingClause x = new SQLHavingClause(expr);
        return x;
    }

    /**
     * UNION("union", "UNION"),
     * UNION_ALL("union all", "UNION ALL"),
     * UNION_DISTINCT("union distinct", "UNION DISTINCT"),
     * <p>
     * MINUS("minus", "MINUS"),
     * <p>
     * EXCEPT("except", "EXCEPT"),
     * EXCEPT_ALL("except all", "EXCEPT ALL"),
     * EXCEPT_DISTINCT("except distinct", "EXCEPT DISTINCT"),
     * <p>
     * INTERSECT("INTERSECT", "INTERSECT"),
     * INTERSECT_ALL("intersect all", "INTERSECT ALL"),
     * INTERSECT_DISTINCT("intersect distinct", "INTERSECT DISTINCT"),
     */
    public ISQLSelectQuery parseUnionQueryBlock(ISQLSelectQuery x) {
        SQLToken token = token();

        SQLUnionOperator operator = null;
        ISQLSelectQuery right = null;
        if (token.kind == SQLToken.TokenKind.UNION) {
            nextToken();

            operator = SQLUnionOperator.UNION;

            if (lexer.token.kind == SQLToken.TokenKind.ALL
                    || lexer.lowerHash == SQLKeyWord.ALL.lowerHash) {
                nextToken();

                operator = SQLUnionOperator.UNION_ALL;


            } else if (lexer.token.kind == SQLToken.TokenKind.DISTINCT
                    || lexer.lowerHash == SQLKeyWord.DISTINCT.lowerHash) {
                nextToken();

                operator = SQLUnionOperator.UNION_DISTINCT;

            }

            right = parseQueryBlock();

        } else if (lexer.token.kind == SQLToken.TokenKind.MINUS
                || lexer.lowerHash == SQLKeyWord.MINUS.lowerHash) {
            nextToken();

            operator = SQLUnionOperator.MINUS;
            right = parseQueryBlock();

        } else if (lexer.token.kind == SQLToken.TokenKind.EXCEPT
                || lexer.lowerHash == SQLKeyWord.EXCEPT.lowerHash) {
            operator = SQLUnionOperator.EXCEPT;
            nextToken();

            if (lexer.token.kind == SQLToken.TokenKind.ALL
                    || lexer.lowerHash == SQLKeyWord.ALL.lowerHash) {
                operator = SQLUnionOperator.EXCEPT_ALL;
                nextToken();

            } else if (lexer.token.kind == SQLToken.TokenKind.DISTINCT
                    || lexer.lowerHash == SQLKeyWord.DISTINCT.lowerHash) {
                operator = SQLUnionOperator.EXCEPT_DISTINCT;
                nextToken();
            }

            right = parseQueryBlock();

        } else if (lexer.token.kind == SQLToken.TokenKind.INTERSECT
                || lexer.lowerHash == SQLKeyWord.INTERSECT.lowerHash) {
            operator = SQLUnionOperator.INTERSECT;
            nextToken();

            if (lexer.token.kind == SQLToken.TokenKind.ALL
                    || lexer.lowerHash == SQLKeyWord.ALL.lowerHash) {
                operator = SQLUnionOperator.INTERSECT_ALL;
                nextToken();

            } else if (lexer.token.kind == SQLToken.TokenKind.DISTINCT
                    || lexer.lowerHash == SQLKeyWord.DISTINCT.lowerHash) {
                operator = SQLUnionOperator.INTERSECT_DISTINCT;
                nextToken();
            }

            right = parseQueryBlock();
        }

        if (operator != null) {
            SQLSelectUnionQuery unionQuery = new SQLSelectUnionQuery(x, operator, right);
            return parseUnionQueryBlock(unionQuery);
        }

        return x;
    }


    public SQLOrderByClause parseOrderBy() {
        if (lexer.token.kind != SQLToken.TokenKind.ORDER) {
            return null;
        }
        nextToken();
        acceptAndNextToken(SQLToken.TokenKind.BY);

        SQLOrderByClause x = new SQLOrderByClause();
        for (; ; ) {
            SQLOrderByItem item = parseOrderByItem();
            x.addItem(item);

            if (lexer.token.kind != COMMA) {
                break;
            }
            nextToken();
        }

        return x;
    }

    public SQLOrderByItem parseOrderByItem() {
        ISQLExpr expr = parseExpr();

        SQLOrderByItem.ISQLOrderingSpecification orderingSpecification = null;
        if (lexer.token.kind == SQLToken.TokenKind.ASC
                || lexer.lowerHash == SQLKeyWord.ASC.lowerHash) {

            orderingSpecification = new SQLOrderByItem.SQLASCOrderingSpecification();
            nextToken();

        } else if (lexer.token.kind == SQLToken.TokenKind.DESC
                || lexer.lowerHash == SQLKeyWord.DESC.lowerHash) {

            orderingSpecification = new SQLOrderByItem.SQLDESCOrderingSpecification();
            nextToken();

        } else if (lexer.token.kind == SQLToken.TokenKind.USING
                || lexer.lowerHash == SQLKeyWord.USING.lowerHash) {

            throw new SQLParserException("");

        }

        SQLOrderByItem x = new SQLOrderByItem(expr, orderingSpecification);

        return x;
    }

    /**
     * WINDOW name as (), name as (..)
     *
     * @return
     */
    public SQLWindowClause parseWindow() {
        if (lexer.token.kind != SQLToken.TokenKind.WINDOW
                && lexer.lowerHash != SQLKeyWord.WINDOW.lowerHash) {
            return null;
        }
        nextToken();
        SQLWindowClause x = new SQLWindowClause();

        for (; ; ) {

            ISQLName name = parseName();
            acceptAndNextToken(SQLToken.TokenKind.AS, true);

            acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            SQLWindowSpecification specification = parseWindowSpecification();
            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

            SQLWindowClause.SQLWindowClauseItem item = new SQLWindowClause.SQLWindowClauseItem(name, specification);
            x.addItem(item);
            if (lexer.token.kind != COMMA) {
                break;
            }
            nextToken();
        }


        return x;
    }

    public SQLWindowSpecification parseWindowSpecification() {
        SQLWindowSpecification x = null;

        ISQLName name = parseName();
        if (name != null) {
            if (x == null) {
                x = new SQLWindowSpecification();
            }
            x.setName(name);
        }

        SQLPartitionClause partitionClause = parsePartitionClause();
        if (partitionClause != null) {
            if (x == null) {
                x = new SQLWindowSpecification();
            }
            x.setPartitionClause(partitionClause);
        }

        SQLOrderByClause orderByClause = parseOrderBy();
        if (orderByClause != null) {
            if (x == null) {
                x = new SQLWindowSpecification();
            }
            x.setOrderByClause(orderByClause);
        }

        SQLWindowFrameClause frameClause = parseWindowFrameClause();
        if (frameClause != null) {
            if (x == null) {
                x = new SQLWindowSpecification();
            }
            x.setWindowFrameClause(frameClause);
        }

        return x;
    }

    public SQLPartitionClause parsePartitionClause() {
        if (lexer.token.kind != SQLToken.TokenKind.PARTITION) {
            return null;
        }
        nextToken();

        acceptAndNextToken(SQLToken.TokenKind.BY, true);

        SQLPartitionClause x = new SQLPartitionClause();
        for (; ; ) {
            ISQLExpr item = parseExpr();
            x.addItem(item);
            if (lexer.token.kind != COMMA) {
                break;
            }
            nextToken();
        }

        return x;
    }

    public SQLWindowFrameClause parseWindowFrameClause() {

        SQLWindowFrameClause.SQLWindowFrameUnit unit = null;
        if (lexer.token.kind == SQLToken.TokenKind.RANGE
                || lexer.lowerHash == SQLKeyWord.RANGE.lowerHash) {
            unit = SQLWindowFrameClause.SQLWindowFrameUnit.RANGE;

        } else if (lexer.token.kind == SQLToken.TokenKind.ROWS
                || lexer.lowerHash == SQLKeyWord.ROWS.lowerHash) {
            unit = SQLWindowFrameClause.SQLWindowFrameUnit.ROWS;

        } else if (lexer.token.kind == SQLToken.TokenKind.GROUPS
                || lexer.lowerHash == SQLKeyWord.GROUPS.lowerHash) {
            unit = SQLWindowFrameClause.SQLWindowFrameUnit.GROUPS;

        }

        if (unit != null) {
            nextToken();

            SQLWindowFrameClause x = new SQLWindowFrameClause();
            x.setUnit(unit);

            boolean between = acceptAndNextToken(SQLToken.TokenKind.BETWEEN);
            if (between) {
                nextToken();
                x.setBetween(true);
            }

            ISQLExpr start;
            if (lexer.token.kind == SQLToken.TokenKind.CURRENT
                    || lexer.lowerHash == SQLKeyWord.CURRENT.lowerHash) {

                if (lexer.token.kind == SQLToken.TokenKind.ROW
                        || lexer.lowerHash == SQLKeyWord.ROW.lowerHash) {
                    start = SQLWindowFrameClause.SQLCurrentRow.of();
                } else {
                    throw new SQLParserException("");
                }

            } else {
                ISQLExpr vlaue = parseExpr();

                if (lexer.token.kind == SQLToken.TokenKind.PRECEDING
                        || lexer.lowerHash == SQLKeyWord.PRECEDING.lowerHash) {
                    nextToken();
                    start = new SQLWindowFrameClause.SQLWindowFramePreceding(vlaue);

                } else if (lexer.token.kind == SQLToken.TokenKind.FOLLOWING
                        || lexer.lowerHash == SQLKeyWord.FOLLOWING.lowerHash) {
                    nextToken();
                    start = new SQLWindowFrameClause.SQLWindowFrameFollowing(vlaue);

                } else {
                    throw new SQLParserException("");
                }
            }
            x.setStart(start);


            if (between) {
                acceptAndNextToken(SQLToken.TokenKind.AND, true);

                ISQLExpr end;
                if (lexer.token.kind == SQLToken.TokenKind.CURRENT
                        || lexer.lowerHash == SQLKeyWord.CURRENT.lowerHash) {
                    nextToken();

                    if (lexer.token.kind == SQLToken.TokenKind.ROW
                            || lexer.lowerHash == SQLKeyWord.ROW.lowerHash) {
                        end = SQLWindowFrameClause.SQLCurrentRow.of();
                    } else {
                        throw new SQLParserException("");
                    }

                } else {
                    ISQLExpr vlaue = parseExpr();

                    if (lexer.token.kind == SQLToken.TokenKind.PRECEDING
                            || lexer.lowerHash == SQLKeyWord.PRECEDING.lowerHash) {
                        nextToken();
                        end = new SQLWindowFrameClause.SQLWindowFramePreceding(vlaue);

                    } else if (lexer.token.kind == SQLToken.TokenKind.FOLLOWING
                            || lexer.lowerHash == SQLKeyWord.FOLLOWING.lowerHash) {
                        nextToken();
                        end = new SQLWindowFrameClause.SQLWindowFrameFollowing(vlaue);

                    } else {
                        throw new SQLParserException("");
                    }
                }
                x.setEnd(end);
            }

            return x;
        }

        return null;
    }


    public ISQLLimitClause parseLimit() {
        return parseLimitOffsetClause();
    }

    /**
     * LIMIT row_count
     * LIMIT offset, row_count
     * LIMIT row_count OFFSET offset
     */
    public SQLLimitOffsetClause parseLimitOffsetClause() {
        if (lexer.token.kind != SQLToken.TokenKind.LIMIT
                && lexer.lowerHash != SQLKeyWord.LIMIT.lowerHash) {
            return null;
        }

        nextToken();

        ISQLExpr expr1 = parseExpr();

        boolean offset = false;
        ISQLExpr offsetExpr = null;
        ISQLExpr rowCountExpr = null;

        if (lexer.token.kind == COMMA) {
            nextToken();
            offsetExpr = expr1;
            rowCountExpr = parseExpr();
        } else if (lexer.token.kind == SQLToken.TokenKind.OFFSET
                || lexer.lowerHash == SQLKeyWord.OFFSET.lowerHash) {
            nextToken();

            offset = true;
            rowCountExpr = expr1;
            offsetExpr = parseExpr();
        } else {

            rowCountExpr = expr1;

        }

        SQLLimitOffsetClause x = new SQLLimitOffsetClause(offset, offsetExpr, rowCountExpr);

        return x;
    }

    public SQLOffsetFetchClause parseOffsetFetchClause() {
        if (lexer.token.kind != SQLToken.TokenKind.OFFSET
                && lexer.lowerHash != SQLKeyWord.OFFSET.lowerHash
                && lexer.token.kind != SQLToken.TokenKind.FETCH
                && lexer.lowerHash != SQLKeyWord.FETCH.lowerHash) {
            return null;
        }

        SQLOffsetFetchClause x = new SQLOffsetFetchClause();

        if (lexer.token.kind == SQLToken.TokenKind.OFFSET
                || lexer.lowerHash == SQLKeyWord.OFFSET.lowerHash) {
            nextToken();

            ISQLExpr offsetExpr = parseExpr();
            x.setOffsetExpr(offsetExpr);
        }

        if (lexer.token.kind == SQLToken.TokenKind.FETCH
                || lexer.lowerHash == SQLKeyWord.FETCH.lowerHash) {
            nextToken();

            ISQLExpr rowCountExpr = parseExpr();
            x.setRowCountExpr(rowCountExpr);
        }

        return x;
    }


    /**
     * FOR { UPDATE | NO KEY UPDATE | SHARE | KEY SHARE } [ OF table_name [, ...] ] [ NOWAIT | SKIP LOCKED| WAIT integer  ] [...]
     * <p>
     * LOCK IN SHARE MODE
     */
    public ISQLLockClause parseLockClause() {

        ISQLLockClause x = null;
        if (lexer.token.kind == SQLToken.TokenKind.FOR
                || lexer.lowerHash == SQLKeyWord.FOR.lowerHash) {

            x = parseForUpdate();

        } else if (lexer.token.kind == SQLToken.TokenKind.LOCK
                || lexer.lowerHash == SQLKeyWord.LOCK.lowerHash) {

            acceptAndNextToken(SQLToken.TokenKind.IN, true);

            if (lexer.token.kind == SQLToken.TokenKind.SHARE
                    || lexer.lowerHash == SQLKeyWord.SHARE.lowerHash) {
                nextToken();

                if (lexer.token.kind == SQLToken.TokenKind.MODE
                        || lexer.lowerHash == SQLKeyWord.MODE.lowerHash) {
                    nextToken();

                    x = new SQLLockInShareModeClause();
                } else {
                    throw new SQLParserException();
                }


            } else {
                throw new SQLParserException();
            }

        }

        return x;
    }


    /**
     * FOR { UPDATE | NO KEY UPDATE | SHARE | KEY SHARE } [ OF table_name [, ...] ] [ NOWAIT | SKIP LOCKED| WAIT integer  ] [...]
     */
    public SQLForUpdateClause parseForUpdate() {

        if (lexer.token.kind != SQLToken.TokenKind.FOR
                && lexer.lowerHash != SQLKeyWord.FOR.lowerHash) {
            return null;
        }
        nextToken();

        SQLForUpdateClause x = new SQLForUpdateClause();
        SQLForUpdateClause.SQLForType forType = null;
        if (lexer.token.kind == SQLToken.TokenKind.UPDATE
                || lexer.lowerHash == SQLKeyWord.UPDATE.lowerHash) {
            nextToken();
            forType = SQLForUpdateClause.SQLForType.UPDATE;

        } else if (lexer.token.kind == SQLToken.TokenKind.NO
                || lexer.lowerHash == SQLKeyWord.NO.lowerHash) {
            nextToken();

            if (lexer.token.kind == SQLToken.TokenKind.KEY
                    || lexer.lowerHash == SQLKeyWord.KEY.lowerHash) {
                nextToken();
                acceptAndNextToken(SQLToken.TokenKind.UPDATE, true);

                forType = SQLForUpdateClause.SQLForType.NO_KEY_UPDATE;

            } else {
                throw new SQLParserException();
            }


        } else if (lexer.token.kind == SQLToken.TokenKind.SHARE
                || lexer.lowerHash == SQLKeyWord.SHARE.lowerHash) {
            nextToken();

            forType = SQLForUpdateClause.SQLForType.SHARE;

        } else if (lexer.token.kind == SQLToken.TokenKind.KEY
                || lexer.lowerHash == SQLKeyWord.KEY.lowerHash) {
            nextToken();

            if (lexer.token.kind == SQLToken.TokenKind.SHARE
                    || lexer.lowerHash == SQLKeyWord.SHARE.lowerHash) {
                nextToken();

                forType = SQLForUpdateClause.SQLForType.KEY_SHARE;

            } else {
                throw new SQLParserException();
            }

        } else {
            // TODO
            throw new SQLParserException("TODO");
        }
        x.setForType(forType);

        if (this.acceptAndNextToken(SQLToken.TokenKind.OF)) {

            for (; ; ) {
                x.addColumn(parseName());
                if (lexer.token.kind != COMMA) {
                    break;
                }
                nextToken();
            }
        }


        SQLForUpdateClause.SQLForOption option = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.NOWAIT)) {

            option = new SQLForUpdateClause.SQLForNoWaitOption();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.SKIP)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.LOCKED)) {

                option = new SQLForUpdateClause.SQLForSkipLockedOption();

            } else {
                throw new SQLParserException();
            }

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.WAIT)) {

            ISQLExpr expr = parseExpr();
            option = new SQLForUpdateClause.SQLForWaitOption(expr);
        }
        x.setForOption(option);


        return x;
    }


    public ISQLReturningClause parseReturningClause() {
        if (!this.accept(SQLToken.TokenKind.RETURN)
                && !this.accept(SQLToken.TokenKind.RETURNING)) {
            return null;
        }

        ISQLReturningClause x = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.RETURN)) {
            x = new ISQLReturningClause.SQLReturnIntoClause();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.RETURNING)) {
            x = new ISQLReturningClause.SQLReturningIntoClause();

        }

        //  RETURN INTO data_item [, data_item ]...
        //  RETURN BULK COLLECT INTO data_item [, data_item ]...
        if (this.acceptAndNextToken(SQLToken.TokenKind.BULK)) {
            this.acceptAndNextToken(SQLToken.TokenKind.COLLECT, true);
            x.setBulkCollect(true);
        }

        if (!this.acceptAndNextToken(SQLToken.TokenKind.INTO)) {
            for (; ; ) {
                ISQLExpr returningItem = parseExpr();
                x.addReturningItem(returningItem);
                if (!this.acceptAndNextToken(COMMA)) {
                    break;
                }
            }
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.INTO)) {

            for (; ; ) {
                ISQLExpr intoItem = parseExpr();
                x.addIntoItem(intoItem);
                if (!this.acceptAndNextToken(COMMA)) {
                    break;
                }
            }

        }

        return x;
    }

    public SQLErrorLoggingClause parseErrorLoggingClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.LOG)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.ERRORS, true);

        SQLErrorLoggingClause x = new SQLErrorLoggingClause();
        if (this.acceptAndNextToken(SQLToken.TokenKind.INTO)) {
            ISQLName into = parseName();
            x.setInto(into);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            ISQLExpr expr = parseExpr();
            x.setExpr(expr);
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.REJECT)) {
            this.acceptAndNextToken(SQLToken.TokenKind.LIMIT, true);
            ISQLExpr rejectLimit = parseExpr();
            x.setRejectLimit(rejectLimit);
        }

        return x;
    }

    public ISQLSubQueryRestrictionClause parseSubQueryRestrictionClause() {
        if (!this.acceptAndNextToken(WITH)) {
            return null;
        }

        ISQLSubQueryRestrictionClause x = null;

        if (this.acceptAndNextToken(READ)) {
            this.acceptAndNextToken(ONLY, true);
            x = new ISQLSubQueryRestrictionClause.SQLWithReadOnly();

        } else if (this.accept(CASCADED)
                || this.accept(LOCAL)
                || this.accept(CHECK)) {

            ISQLSubQueryRestrictionClause.SQLWithCheckOption.SQLLevels levels = null;
            if (this.acceptAndNextToken(CASCADED)) {
                levels = ISQLSubQueryRestrictionClause.SQLWithCheckOption.SQLLevels.CASCADED;

            } else if (this.acceptAndNextToken(LOCAL)) {
                levels = ISQLSubQueryRestrictionClause.SQLWithCheckOption.SQLLevels.LOCAL;
            }

            this.acceptAndNextToken(SQLToken.TokenKind.CHECK, true);
            this.acceptAndNextToken(SQLToken.TokenKind.OPTION, true);
            x = new ISQLSubQueryRestrictionClause.SQLWithCheckOption(levels);

        } else {
            throw new SQLParserException(errorInfo());
        }


        if (this.acceptAndNextToken(SQLToken.TokenKind.CONSTRAINT)) {
            x.setConstraint(parseName());
        }

        return x;

    }


    /**
     * (
     * TableElements
     * )
     */
    public void parseTableElements(List<ISQLTableElement> tableElements, ISQLObject parent) {
        boolean accept = acceptAndNextToken(SQLToken.TokenKind.LPAREN);
        if (!accept) {
            return;
        }

        for (; ; ) {

            ISQLTableElement tableElement = parseTableElement();
//            tableElement.addBeforeComment();
            if (tableElement != null) {
                tableElement.setParent(parent);
            }
            tableElements.add(tableElement);

            if (lexer.token().kind != SQLToken.TokenKind.COMMA) {
                break;
            }

            nextToken();
        }

        acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
    }


    public ISQLTableElement parseTableElement() {

        ISQLTableElement x = null;

        SQLToken.TokenKind kind = lexer.token().kind;

        if (isTableConstraint()) {
            x = parseTableConstraint();

        } else if (kind == SQLToken.TokenKind.LIKE
                || lexer.lowerHash() == SQLKeyWord.LIKE.lowerHash) {
            x = parseLikeClause();

        } else if (SQLExprParser.isIdentifier(kind)) {
            x = parseColumnDefinition();
        }

        return x;
    }


    /**
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#column%20definition
     */
    public SQLColumnDefinition parseColumnDefinition() {
        SQLToken.TokenKind kind = lexer.token().kind;
        if (!SQLExprParser.isIdentifier(kind)) {
            return null;
        }

        SQLColumnDefinition x = new SQLColumnDefinition();

        ISQLName name = parseName();
        x.setName(name);

        ISQLDataType dataType = parseDataType();
        x.setDataType(dataType);

        if (this.acceptAndNextToken(SQLToken.TokenKind.SORT)) {
            x.setSort(true);
        }

        SQLCollateOptionExpr collateOptionExpr = parseCollateClause();
        x.setCollateClause(collateOptionExpr);

        SQLVisibleType visibleType = parseVisibleType();
        x.setVisible(visibleType);

        x.setDefaultClause(parseDefaultClause());

        return x;

    }


    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/identity_options.html
     */
    public ISQLIdentityOption parseIdentityOption() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.START)) {

            this.acceptAndNextToken(SQLToken.TokenKind.WITH, true);

            if (this.acceptAndNextToken(SQLToken.TokenKind.LIMIT)) {
                this.acceptAndNextToken(SQLToken.TokenKind.VALUE, true);
                return new ISQLIdentityOption.SQLIdentityStartWithOption(new ISQLIdentityOption.SQLLimitValueExpr());
            }

            ISQLExpr value = parseExpr();
            return new ISQLIdentityOption.SQLIdentityStartWithOption(value);
        }


        if (this.acceptAndNextToken(SQLToken.TokenKind.INCREMENT)) {

            this.acceptAndNextToken(SQLToken.TokenKind.BY, true);
            ISQLExpr value = parseExpr();
            return new ISQLIdentityOption.SQLIdentityIncrementByOption(value);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.MAXVALUE)) {

            ISQLExpr value = parseExpr();
            return new ISQLIdentityOption.SQLIdentityMaxValueOption(value);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.NOMAXVALUE)) {
            return new ISQLIdentityOption.SQLIdentityNoMaxValueOption();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.MINVALUE)) {

            ISQLExpr value = parseExpr();
            return new ISQLIdentityOption.SQLIdentityMinValueOption(value);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.NOMINVALUE)) {
            return new ISQLIdentityOption.SQLIdentityNoMinValueOption();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.CYCLE)) {
            return new ISQLIdentityOption.SQLIdentityCycleOption();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.NOCYCLE)) {
            return new ISQLIdentityOption.SQLIdentityNoCycleOption();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.CACHE)) {

            ISQLExpr value = parseExpr();
            return new ISQLIdentityOption.SQLIdentityCacheOption(value);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.NOCACHE)) {
            return new ISQLIdentityOption.SQLIdentityNoCacheOption();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.ORDER)) {
            return new ISQLIdentityOption.SQLIdentityOrderOption();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.NOORDER)) {
            return new ISQLIdentityOption.SQLIdentityNoOrderOption();
        }

        return null;
    }

    // ---------------------- Like Clause ------


    public ISQLTableElement parseLikeClause() {
        boolean accept = acceptAndNextToken(SQLToken.TokenKind.LIKE);
        if (!accept) {
            return null;
        }

        SQLLikeClause x = new SQLLikeClause();

        ISQLName name = parseName();
        x.setName(name);

        parseLikeClauseOptions(x);

        return x;
    }

    public void parseLikeClauseOptions(SQLLikeClause x) {
        for (; ; ) {
            if (lexer.token().kind == SQLToken.TokenKind.INCLUDING
                    || lexer.lowerHash() == SQLKeyWord.INCLUDING.lowerHash) {

                nextToken();

                SQLLikeClause.SQLOption option = null;
                if (lexer.token().kind == SQLToken.TokenKind.ALL
                        || lexer.lowerHash() == SQLKeyWord.ALL.lowerHash) {

                    option = SQLLikeClause.SQLOption.INCLUDING_ALL;

                } else {
                    throw new SQLParserException("TODO:");
                }

                x.addOption(option);
                nextToken();

            } else if (lexer.token().kind == SQLToken.TokenKind.EXCLUDING
                    || lexer.lowerHash() == SQLKeyWord.EXCLUDING.lowerHash) {
                nextToken();

                SQLLikeClause.SQLOption option = null;
                if (lexer.token().kind == SQLToken.TokenKind.ALL
                        || lexer.lowerHash() == SQLKeyWord.ALL.lowerHash) {
                    option = SQLLikeClause.SQLOption.EXCLUDING_ALL;

                } else {
                    throw new SQLParserException("TODO:");
                }

                x.addOption(option);
                nextToken();

            } else {
                return;

            }
        }
    }


    public boolean isColumnConstraint() {
        return this.accept(SQLToken.TokenKind.CONSTRAINT)
                || this.accept(NOT)
                || this.accept(SQLToken.TokenKind.NULL)
                || this.accept(SQLToken.TokenKind.UNIQUE)
                || this.accept(SQLToken.TokenKind.PRIMARY)
                || this.accept(SQLToken.TokenKind.REFERENCES)
                || this.accept(SQLToken.TokenKind.CHECK)
                || this.accept(SQLToken.TokenKind.SCOPE)
                || this.accept(SQLToken.TokenKind.WITH);
    }


    public ISQLColumnConstraint parseColumnConstraint() {
        if (!isColumnConstraint()) {
            return null;
        }

        ISQLColumnConstraint x = null;

        ISQLName name = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.CONSTRAINT)) {
            name = parseName();
        }

        if (this.acceptAndNextToken(NOT)) {

            this.acceptAndNextToken(SQLToken.TokenKind.NULL, true);
            x = new SQLNotNullColumnConstraint(name);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.NULL)) {

            x = new SQLNullColumnConstraint(name);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.UNIQUE)) {

            x = new SQLUniqueColumnConstraint(name);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.PRIMARY)) {

            this.acceptAndNextToken(SQLToken.TokenKind.KEY, true);
            x = new SQLPrimaryKeyColumnConstraint(name);

        } else if (this.accept(SQLToken.TokenKind.REFERENCES)) {

            SQLReferencesColumnConstraint referencesColumnConstraint = new SQLReferencesColumnConstraint();
            referencesColumnConstraint.setName(name);

            parseReferencesConstraint(referencesColumnConstraint, true);

            x = referencesColumnConstraint;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.CHECK)) {

            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            ISQLExpr condition = parseExpr();
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            x = new SQLCheckColumnConstraint(name, condition);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.SCOPE)) {

            this.acceptAndNextToken(SQLToken.TokenKind.IS, true);
            ISQLName scopeTable = parseName();
            x = new SQLScopeIsColumnConstraint(scopeTable);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.WITH)) {

            this.acceptAndNextToken(SQLToken.TokenKind.ROWID, true);
            x = new SQLWithRowIdColumnConstraint();

        }

        if (x != null) {
            parseConstraintOptions(x);
        }

        return x;
    }

    public boolean isTableConstraint() {
        return this.accept(SQLToken.TokenKind.CONSTRAINT)
                || this.accept(SQLToken.TokenKind.UNIQUE)
                || this.accept(SQLToken.TokenKind.PRIMARY)
                || this.accept(SQLToken.TokenKind.FOREIGN)
                || this.accept(SQLToken.TokenKind.CHECK)
                || this.accept(SQLToken.TokenKind.SCOPE)
                || this.accept(SQLToken.TokenKind.REF);
    }

    public ISQLTableConstraint parseTableConstraint() {
        if (!isTableConstraint()) {
            return null;
        }

        ISQLTableConstraint x = null;
        ISQLName name = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.CONSTRAINT)) {
            name = parseName();
        }


        if (this.acceptAndNextToken(SQLToken.TokenKind.UNIQUE)) {
            x = new SQLUniqueTableConstraint(name);
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            for (; ; ) {
                ISQLExpr column = parseExpr();
                ((SQLUniqueTableConstraint) x).addColumn(column);
                if (!this.accept(COMMA)) {
                    break;
                }
                nextToken();
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.PRIMARY)) {
            this.acceptAndNextToken(SQLToken.TokenKind.KEY, true);
            x = new SQLPrimaryKeyTableConstraint(name);
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            for (; ; ) {
                ISQLExpr column = parseExpr();
                ((SQLPrimaryKeyTableConstraint) x).addColumn(column);
                if (!this.acceptAndNextToken(COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.FOREIGN)) {
            this.acceptAndNextToken(SQLToken.TokenKind.KEY, true);

            x = new SQLForeignKeyTableConstraint();
            x.setName(name);

            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            for (; ; ) {
                ISQLExpr column = parseExpr();
                ((SQLForeignKeyTableConstraint) x).addReferencingColumn(column);
                if (!this.accept(COMMA)) {
                    break;
                }
                nextToken();
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

            parseReferencesConstraint((SQLForeignKeyTableConstraint) x, true);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.CHECK)) {
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            ISQLExpr condition = parseExpr();
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            x = new SQLCheckTableConstraint(name, condition);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.SCOPE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.FOR, true);
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            ISQLExpr ref = parseExpr();
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

            this.acceptAndNextToken(SQLToken.TokenKind.IS, true);

            ISQLName scopeTale = parseName();

            x = new SQLScopeForTableConstraint(ref, scopeTale);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.REF)) {
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            ISQLExpr ref = parseExpr();
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

            this.acceptAndNextToken(SQLToken.TokenKind.WITH, true);
            this.acceptAndNextToken(SQLToken.TokenKind.ROWID, true);

            x = new SQLRefWithRowIdTableConstraint(ref);
        }


        if (x != null) {
            parseConstraintOptions(x);
        }
        return x;
    }

    public void parseReferencesConstraint(AbstractSQLReferencesConstraint x, boolean acceptThrowException) {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.REFERENCES, acceptThrowException)) {
            return;
        }

        ISQLName referencedTable = parseName();
        x.setReferencedTable(referencedTable);

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (; ; ) {
                ISQLExpr column = parseExpr();
                x.addReferencedColumn(column);
                if (!this.accept(COMMA)) {
                    break;
                }
                nextToken();
            }

            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.MATCH)) {
            // FULL | PARTIAL | SIMPLE
            AbstractSQLReferencesConstraint.MatchType matchType = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.FULL)) {
                matchType = AbstractSQLReferencesConstraint.MatchType.FULL;

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.PARTIAL)) {
                matchType = AbstractSQLReferencesConstraint.MatchType.PARTIAL;

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.SIMPLE)) {
                matchType = AbstractSQLReferencesConstraint.MatchType.SIMPLE;

            } else {
                throw new SQLParserException();
            }
            x.setMatchType(matchType);
        }


        for (; ; ) {
            if (!this.acceptAndNextToken(SQLToken.TokenKind.ON)) {
                break;
            }
            AbstractSQLReferencesConstraint.SQLReferentialTriggerAction action = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.UPDATE)) {

                action = new AbstractSQLReferencesConstraint.SQLOnUpdateAction(parseReferentialAction());

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.DELETE)) {
                action = new AbstractSQLReferencesConstraint.SQLOnDeleteAction(parseReferentialAction());

            } else {
                throw new SQLParserException();
            }
            x.addAction(action);
        }
    }

    public SQLReferentialAction parseReferentialAction() {
        SQLReferentialAction action = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.CASCADE)) {
            action = SQLReferentialAction.CASCADE;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.SET)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.NULL)) {
                action = SQLReferentialAction.SET_NULL;
            } else if (this.acceptAndNextToken(SQLToken.TokenKind.DEFAULT)) {
                action = SQLReferentialAction.SET_DEFAULT;
            } else {
                throw new SQLParserException();
            }

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.RESTRICT)) {
            action = SQLReferentialAction.RESTRICT;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.NO)) {
            this.acceptAndNextToken(SQLToken.TokenKind.ACTION, true);
            action = SQLReferentialAction.NO_ACTION;

        }


        return action;
    }

    public void parseConstraintOptions(ISQLConstraint x) {
        for (; ; ) {
            ISQLExpr option = parseConstraintOption();
            x.addOption(option);
            if (this.accept(COMMA)
                    || option == null) {
                break;
            }
        }
    }

    public ISQLExpr parseConstraintOption() {
        return null;
    }

    public ISQLUsingIndexClause parseIUsingIndexClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.USING)) {
            return null;
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.NO)) {
            this.acceptAndNextToken(SQLToken.TokenKind.INDEX, true);
            return new ISQLUsingIndexClause.SQLUsingNoIndexClause();
        }

        this.acceptAndNextToken(SQLToken.TokenKind.INDEX, true);

        ISQLUsingIndexClause.SQLUsingIndexClause x = new ISQLUsingIndexClause.SQLUsingIndexClause();


        return x;
    }

    public SQLExceptionsClause parseExceptionsClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.EXCEPTIONS)) {
            return null;
        }

        this.acceptAndNextToken(SQLToken.TokenKind.INTO, true);
        return new SQLExceptionsClause(parseName());
    }

    public boolean isParseVisibleType() {
        if (this.accept(VISIBLE)
                || this.accept(INVISIBLE)) {
            return true;
        }
        return false;
    }

    public SQLVisibleType parseVisibleType() {
        if (acceptAndNextToken(SQLToken.TokenKind.VISIBLE)) {
            return SQLVisibleType.VISIBLE;
        } else if (acceptAndNextToken(SQLToken.TokenKind.INVISIBLE)) {
            return SQLVisibleType.INVISIBLE;
        }
        return null;
    }

    /**
     * DEFAULT expr
     * DEFAULT ON NULL expr
     */
    public ISQLExpr parseDefaultClause() {
        if (!this.accept(SQLToken.TokenKind.DEFAULT)
                && !this.accept(SQLToken.TokenKind.ASSIGN)) {
            return null;
        }

        SQLDefaultClause.Operator operator = SQLDefaultClause.Operator.DEFAULT;
        if (this.acceptAndNextToken(SQLToken.TokenKind.DEFAULT)) {
            operator = SQLDefaultClause.Operator.DEFAULT;
        } else if (this.acceptAndNextToken(SQLToken.TokenKind.ASSIGN)) {
            operator = SQLDefaultClause.Operator.VAR_ASSIGN_OP;
        }


        if (this.acceptAndNextToken(SQLToken.TokenKind.ON)) {
            this.acceptAndNextToken(SQLToken.TokenKind.NULL, true);
            ISQLExpr expr = parseExpr();
            return new SQLDefaultOnNullClause(expr);
        }

        ISQLExpr expr = parseExpr();
        return new SQLDefaultClause(operator, expr);
    }


    public SQLAllocateExtentClause parseAllocateExtentClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.ALLOCATE)) {
            return null;
        }

        this.acceptAndNextToken(SQLToken.TokenKind.EXTENT, true);

        SQLAllocateExtentClause x = new SQLAllocateExtentClause();

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {

            for (; ; ) {
                SQLAllocateExtentClause.ISQLItem item = null;
                if (this.acceptAndNextToken(SQLToken.TokenKind.SIZE)) {
                    SQLSizeClause sizeClause = parseSizeClause();
                    item = new SQLAllocateExtentClause.SQLAllocateExtentSizeClause(sizeClause);
                } else if (this.acceptAndNextToken(SQLToken.TokenKind.DATAFILE)) {
                    ISQLExpr name = parseExpr();
                    item = new SQLAllocateExtentClause.SQLAllocateExtentDataFileClause(name);

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.INSTANCE)) {
                    ISQLExpr value = parseExpr();
                    item = new SQLAllocateExtentClause.SQLAllocateExtentInstanceClause(value);

                }

                if (item == null) {
                    break;
                }
                x.addItem(item);
            }

            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return x;
    }

    public SQLDeallocateUnusedClause parseDeallocateUnusedClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.DEALLOCATE)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.UNUSED, true);

        SQLDeallocateUnusedClause x = new SQLDeallocateUnusedClause();
        if (this.acceptAndNextToken(SQLToken.TokenKind.KEEP)) {
            SQLSizeClause sizeClause = parseSizeClause();
            x.setKeep(sizeClause);
        }

        return x;
    }

    public SQLFileSpecification parseFileSpecification() {

        SQLFileSpecification x = null;
        if (this.accept(SQLToken.TokenKind.STRING_LITERAL)
                || this.accept(SQLToken.TokenKind.LPAREN)) {
            ISQLExpr file = parseExpr();
            if (x == null) {
                x = new SQLFileSpecification();
            }
            x.setFile(file);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.SIZE)) {
            SQLSizeClause sizeClause = parseSizeClause();
            if (x == null) {
                x = new SQLFileSpecification();
            }
            x.setSizeClause(sizeClause);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.BLOCKSIZE)) {
            SQLSizeClause blockSize = parseSizeClause();
            if (x == null) {
                x = new SQLFileSpecification();
            }
            x.setBlockSize(blockSize);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.REUSE)) {
            if (x == null) {
                x = new SQLFileSpecification();
            }
            x.setReuse(true);
        }

        SQLFileSpecification.ISQLAutoExtendClause autoExtendClause = parseAutoExtendClause();
        if (autoExtendClause != null) {
            if (x == null) {
                x = new SQLFileSpecification();
            }
            x.setAutoextendClause(autoExtendClause);
        }

        return x;
    }

    public SQLFileSpecification.ISQLAutoExtendClause parseAutoExtendClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.AUTOEXTEND)) {
            return null;
        }
        SQLFileSpecification.ISQLAutoExtendClause x = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.ON)) {

            SQLSizeClause next = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.NEXT)) {
                next = parseSizeClause();
            }

            SQLFileSpecification.SQLMaxSizeClause maxsize = parseMaxSizeClause();

            x = new SQLFileSpecification.SQLAutoExtendOnClause(next, maxsize);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.OFF)) {
            x = new SQLFileSpecification.SQLAutoExtendOffClause();

        } else {
            throw new SQLParserException();
        }

        return x;
    }

    public SQLFileSpecification.SQLMaxSizeClause parseMaxSizeClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.MAXSIZE)) {
            return null;
        }

        ISQLExpr value = null;
        if (this.accept(SQLToken.TokenKind.UNLIMITED)) {
            value = parseIdentifier();

        } else {
            value = parseSizeClause();
        }
        return new SQLFileSpecification.SQLMaxSizeClause(value);
    }

    public boolean isParseLoggingClause() {
        return this.accept(SQLToken.TokenKind.LOGGING)
                || this.accept(SQLToken.TokenKind.NOLOGGING)
                || this.accept(SQLToken.TokenKind.FILESYSTEM_LIKE_LOGGING);
    }

    public ISQLLoggingClause parseLoggingClause() {
        ISQLLoggingClause x = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.LOGGING)) {
            x = new ISQLLoggingClause.SQLLoggingClause();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.NOLOGGING)) {
            x = new ISQLLoggingClause.SQLNoLoggingClause();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.FILESYSTEM_LIKE_LOGGING)) {
            x = new ISQLLoggingClause.SQLFilesystemLikeLogging();
        }

        return x;
    }

    public boolean isParseParallelClause() {
        return this.accept(SQLToken.TokenKind.PARALLEL)
                || this.accept(SQLToken.TokenKind.NOPARALLEL);
    }

    public ISQLParallelClause parseParallelClause() {
        ISQLParallelClause x = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.PARALLEL)) {
            ISQLExpr value = null;
            if (this.accept(SQLToken.TokenKind.INTEGER_LITERAL)) {
                value = parseExpr();
            }
            x = new ISQLParallelClause.SQLParallelClause(value);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.NOPARALLEL)) {
            x = new ISQLParallelClause.SQLNoParallelClause();

        }
        return x;
    }


    public boolean isParsePhysicalAttribute() {
        return this.accept(SQLToken.TokenKind.PCTFREE)
                || this.accept(SQLToken.TokenKind.PCTUSED)
                || this.accept(SQLToken.TokenKind.INITRANS)
                || this.accept(SQLToken.TokenKind.STORAGE);
    }

    public ISQLPhysicalAttribute parsePhysicalAttribute() {
        ISQLPhysicalAttribute x = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.PCTFREE)) {
            ISQLExpr value = parseExpr();
            x = new ISQLPhysicalAttribute.SQLPctfree(value);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.PCTUSED)) {
            ISQLExpr value = parseExpr();
            x = new ISQLPhysicalAttribute.SQLPctused(value);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.INITRANS)) {
            ISQLExpr value = parseExpr();
            x = new ISQLPhysicalAttribute.SQLInitrans(value);

        } else {
            x = parseStorageClause();
        }
        return x;
    }

    public SQLSizeClause parseSizeClause() {
        ISQLExpr value = parseExpr();
        if (value == null) {
            throw new SQLParserException();
        }

        SQLSizeClause.Type type = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.K)) {
            type = SQLSizeClause.Type.K;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.M)) {
            type = SQLSizeClause.Type.M;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.G)) {
            type = SQLSizeClause.Type.G;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.T)) {
            type = SQLSizeClause.Type.T;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.P)) {
            type = SQLSizeClause.Type.P;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.E)) {
            type = SQLSizeClause.Type.E;

        }

        return new SQLSizeClause(value, type);
    }

    public SQLStorageClause parseStorageClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.STORAGE)) {
            return null;
        }
        SQLStorageClause x = new SQLStorageClause();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            SQLStorageClause.ISQLItem item = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.INITIAL)) {
                SQLSizeClause sizeClause = parseSizeClause();
                item = new SQLStorageClause.SQLInitialSizeClause(sizeClause);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.NEXT)) {
                SQLSizeClause sizeClause = parseSizeClause();
                item = new SQLStorageClause.SQLNextSizeClause(sizeClause);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.MINEXTENTS)) {
                ISQLExpr value = parseExpr();
                item = new SQLStorageClause.SQLMinExtentsClause(value);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.MAXEXTENTS)) {
                ISQLExpr value = parseExpr();
                item = new SQLStorageClause.SQLMaxExtentsClause(value);

            } else if (this.accept(SQLToken.TokenKind.MAXSIZE)) {
                item = parseMaxSizeClause();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.PCTINCREASE)) {
                ISQLExpr value = parseExpr();
                item = new SQLStorageClause.SQLPctIncreaseClause(value);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.FREELISTS)) {
                ISQLExpr value = parseExpr();
                item = new SQLStorageClause.SQLFreeListsClause(value);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.FREELIST)) {
                this.acceptAndNextToken(SQLToken.TokenKind.GROUPS, true);
                ISQLExpr value = parseExpr();
                item = new SQLStorageClause.SQLFreeListGroupsClause(value);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.OPTIMAL)) {
                item = new SQLStorageClause.SQLOptimalClause();
                ISQLExpr value = null;
                if (this.acceptAndNextToken(SQLToken.TokenKind.NULL)) {
                    value = parseExpr();
                } else if (this.acceptAndNextToken(SQLToken.TokenKind.INTEGER_LITERAL)) {
                    value = parseSizeClause();
                }
                ((SQLStorageClause.SQLOptimalClause) item).setValue(value);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.BUFFER_POOL)) {
                SQLStorageClause.SQLBufferPoolType type = null;
                if (this.acceptAndNextToken(SQLToken.TokenKind.KEEP)) {
                    type = SQLStorageClause.SQLBufferPoolType.KEEP;

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.RECYCLE)) {
                    type = SQLStorageClause.SQLBufferPoolType.RECYCLE;

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.DEFAULT)) {
                    type = SQLStorageClause.SQLBufferPoolType.DEFAULT;

                } else {
                    throw new SQLParserException();
                }

                item = new SQLStorageClause.SQLBufferPoolClause(type);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.FLASH_CACHE)) {
                SQLStorageClause.SQLFlashCacheType type = null;
                if (this.acceptAndNextToken(SQLToken.TokenKind.KEEP)) {
                    type = SQLStorageClause.SQLFlashCacheType.KEEP;

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.NONE)) {
                    type = SQLStorageClause.SQLFlashCacheType.NONE;

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.DEFAULT)) {
                    type = SQLStorageClause.SQLFlashCacheType.DEFAULT;

                } else {
                    throw new SQLParserException();
                }
                item = new SQLStorageClause.SQLFlashCacheClause(type);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.ENCRYPT)) {
                item = new SQLStorageClause.SQLEncryptClause();
            }

            if (item == null) {
                break;
            }
            x.addItem(item);
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        return x;
    }


    public ISQLPartitionBy parsePartitionBy() {
        SQLLexer.SQLMake make = this.make();
        if (!this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
            return null;
        }

        this.acceptAndNextToken(SQLToken.TokenKind.BY, true);

        ISQLPartitionBy x = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.RANGE)) {
            this.reset(make);

            return parsePartitionByRange();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.LIST)) {
            this.reset(make);
            return parsePartitionByList();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.HASH)) {
            this.reset(make);
            return parsePartitionByHash();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.REFERENCE)) {
            this.reset(make);
            return parsePartitionByReference();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.SYSTEM)) {
            this.reset(make);
            return parsePartitionBySystem();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.CONSISTENT)) {
            this.reset(make);
            return parsePartitionByConstraintHash();

        } else {
            throw new SQLParserException(errorInfo());
        }

    }

    public SQLPartitionByRange parsePartitionByRange() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.BY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.RANGE, true);

        SQLPartitionByRange x = new SQLPartitionByRange();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

        for (; ; ) {
            ISQLExpr column = parseExpr();
            x.addColumn(column);
            if (!this.acceptAndNextToken(COMMA)) {
                break;
            }
        }

        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        if (this.acceptAndNextToken(SQLToken.TokenKind.INTERVAL)) {
            boolean intervalParen = this.acceptAndNextToken(SQLToken.TokenKind.LPAREN);

            ISQLExpr interval = parseExpr();

            if (intervalParen) {
                this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            }
            x.setIntervalParen(intervalParen);
            x.setInterval(interval);
        }

        ISQLSubPartitionBy subPartitionBy = parseSubPartitionBy();
        x.setSubPartitionBy(subPartitionBy);

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (; ; ) {
                SQLPartitionDefinition partitionDefinition = parsePartitionDefinition();
                x.addPartition(partitionDefinition);
                if (!this.acceptAndNextToken(COMMA)) {
                    break;
                }
            }

            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return x;
    }


    public SQLPartitionByList parsePartitionByList() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.BY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.LIST, true);

        SQLPartitionByList x = new SQLPartitionByList();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            ISQLExpr column = parseExpr();
            x.addColumn(column);
            if (!this.acceptAndNextToken(COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        ISQLSubPartitionBy subPartitionBy = parseSubPartitionBy();
        x.setSubPartitionBy(subPartitionBy);

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (; ; ) {
                SQLPartitionDefinition partitionDefinition = parsePartitionDefinition();
                x.addPartition(partitionDefinition);
                if (!this.acceptAndNextToken(COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return x;
    }

    public SQLPartitionByHash parsePartitionByHash() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.BY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.HASH, true);

        SQLPartitionByHash x = new SQLPartitionByHash();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            ISQLExpr column = parseExpr();
            x.addColumn(column);
            if (!this.acceptAndNextToken(COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITIONS)) {
            ISQLExpr partitionsNum = parseExpr();
            x.setPartitionsNum(partitionsNum);
        }

        SQLStoreInClause storeInClause = parseStoreInClause();
        x.setStoreInClause(storeInClause);

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (; ; ) {
                SQLPartitionDefinition partitionDefinition = parsePartitionDefinition();
                x.addPartition(partitionDefinition);
                if (!this.acceptAndNextToken(COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return x;
    }


    public SQLPartitionByReference parsePartitionByReference() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.BY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.REFERENCE, true);

        SQLPartitionByReference x = new SQLPartitionByReference();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

        for (; ; ) {
            ISQLExpr column = parseExpr();
            x.addColumn(column);
            if (!this.acceptAndNextToken(COMMA)) {
                break;
            }
        }

        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {

            for (; ; ) {
                SQLPartitionDefinition partitionDefinition = parsePartitionDefinition();
                x.addPartition(partitionDefinition);
                if (!this.acceptAndNextToken(COMMA)) {
                    break;
                }
            }


            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return x;
    }


    public SQLPartitionBySystem parsePartitionBySystem() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.BY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.SYSTEM, true);

        SQLPartitionBySystem x = new SQLPartitionBySystem();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

        for (; ; ) {
            ISQLExpr column = parseExpr();
            x.addColumn(column);
            if (!this.acceptAndNextToken(COMMA)) {
                break;
            }
        }

        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {

            for (; ; ) {
                SQLPartitionDefinition partitionDefinition = parsePartitionDefinition();
                x.addPartition(partitionDefinition);
                if (!this.acceptAndNextToken(COMMA)) {
                    break;
                }
            }


            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return x;
    }


    public SQLPartitionByConsistentHash parsePartitionByConstraintHash() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.BY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.CONSTRAINT, true);
        this.acceptAndNextToken(SQLToken.TokenKind.HASH, true);

        SQLPartitionByConsistentHash x = new SQLPartitionByConsistentHash();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

        for (; ; ) {
            ISQLExpr column = parseExpr();
            x.addColumn(column);
            if (!this.acceptAndNextToken(COMMA)) {
                break;
            }
        }

        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {

            for (; ; ) {
                SQLPartitionDefinition partitionDefinition = parsePartitionDefinition();
                x.addPartition(partitionDefinition);
                if (!this.acceptAndNextToken(COMMA)) {
                    break;
                }
            }


            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return x;
    }


    public ISQLSubPartitionBy parseSubPartitionBy() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITION)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.BY, true);

        ISQLSubPartitionBy x = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.RANGE)) {

            x = new SQLSubPartitionByRange();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.LIST)) {

            x = new SQLSubPartitionByList();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.HASH)) {

            x = new SQLSubPartitionByHash();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.KEY)) {

            x = new SQLSubPartitionByKey();

        }

        if (x != null) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
                for (; ; ) {
                    ISQLExpr column = parseExpr();
                    x.addColumn(column);
                    if (!this.acceptAndNextToken(COMMA)) {
                        break;
                    }
                }
                this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            }

            SQLSubpartitionTemplate subpartitionTemplate = parseSubpartitionTemplate();
            x.setSubpartitionTemplate(subpartitionTemplate);
        }


        return x;
    }

    public SQLSubpartitionTemplate parseSubpartitionTemplate() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITION)) {
            return null;
        }

        this.acceptAndNextToken(SQLToken.TokenKind.TEMPLATE, true);

        SQLSubpartitionTemplate x = new SQLSubpartitionTemplate();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            SQLSubPartitionDefinition subPartition = parseSubPartitionDefinition();
            x.addSubPartition(subPartition);
            if (!this.acceptAndNextToken(COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        return x;
    }


    public SQLPartitionDefinition parsePartitionDefinition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
            return null;
        }

        SQLPartitionDefinition x = new SQLPartitionDefinition();

        if (!this.accept(SQLToken.TokenKind.VALUES)) {
            ISQLName name = parseName();
            x.setName(name);
        }


        ISQLPartitionValues values = parsePartitionValues(false);
        x.setValues(values);

        parsePartitionDefinitionOptions(x);

        if (this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITIONS)) {
            ISQLExpr subpartitionsNum = parseExpr();
            x.setSubpartitionsNum(subpartitionsNum);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (; ; ) {
                SQLSubPartitionDefinition subPartition = parseSubPartitionDefinition();
                x.addSubPartition(subPartition);
                if (!this.acceptAndNextToken(COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return x;
    }

    public void parsePartitionDefinitionOptions(SQLPartitionDefinition x) {
        for (; ; ) {
            ISQLExpr option = parsePartitionDefinitionOption();
            if (option == null) {
                break;
            }
            x.addOption(option);
        }
    }

    public ISQLExpr parsePartitionDefinitionOption() {
        return null;
    }

    public SQLSubPartitionDefinition parseSubPartitionDefinition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.SUBPARTITION)) {
            return null;
        }
        SQLSubPartitionDefinition x = new SQLSubPartitionDefinition();

        if (!this.accept(SQLToken.TokenKind.VALUES)
                && !this.accept(SQLToken.TokenKind.READ)
                && !this.accept(SQLToken.TokenKind.USING)
                && !this.accept(SQLToken.TokenKind.TABLESPACE)
                && !this.accept(SQLToken.TokenKind.OVERFLOW)) {
            ISQLName name = parseName();
            x.setName(name);
        }

        parseSubPartitionDefinitionOptions(x);

        ISQLPartitionValues values = parsePartitionValues(false);
        x.setValues(values);


        return x;
    }

    public void parseSubPartitionDefinitionOptions(SQLSubPartitionDefinition x) {
        if (x == null) {
            return;
        }

    }


    public SQLStoreInClause parseStoreInClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.STORE)) {
            return null;
        }
        SQLStoreInClause x = new SQLStoreInClause();
        this.acceptAndNextToken(SQLToken.TokenKind.IN, true);
        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            ISQLExpr item = parseExpr();
            x.addItem(item);
            if (!this.acceptAndNextToken(COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        return x;
    }

    public ISQLPartitionValues parsePartitionValues(boolean acceptThrowException) {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.VALUES, acceptThrowException)) {
            return null;
        }

        ISQLPartitionValues x = new SQLPartitionValues();

        if (this.acceptAndNextToken(SQLToken.TokenKind.IN)) {

            x = new SQLPartitionValuesIn();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.LESS)) {

            this.acceptAndNextToken(SQLToken.TokenKind.THAN, true);

            if (this.acceptAndNextToken(SQLToken.TokenKind.MAXVALUE)) {
                x = new SQLPartitionValuesLessThanMaxValue();
            } else {
                x = new SQLPartitionValuesLessThan();
            }
        }


        if (x != null) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
                for (; ; ) {
                    ISQLExpr value = parseExpr();
                    x.addValue(value);
                    if (!this.acceptAndNextToken(COMMA)) {
                        break;
                    }
                }
                this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            }

        } else {
            throw new SQLParserException("TODO " + errorInfo());
        }

        return x;
    }

    public SQLDropBehavior parseDropBehavior() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.CASCADE)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.CONSTRAINTS)) {
                return SQLDropBehavior.CASCADE_CONSTRAINTS;
            }
            return SQLDropBehavior.CASCADE;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.RESTRICT)) {
            return SQLDropBehavior.RESTRICT;
        }

        return null;
    }


    public ISQLDeferredSegmentCreation parseDeferredSegmentCreation() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.SEGMENT)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.CREATION, true);

        if (this.acceptAndNextToken(SQLToken.TokenKind.IMMEDIATE)) {
            return new ISQLDeferredSegmentCreation.SQLSegmentCreationImmediate();
        } else if (this.acceptAndNextToken(SQLToken.TokenKind.DEFERRED)) {
            return new ISQLDeferredSegmentCreation.SQLSegmentCreationDeferred();
        } else {
            throw new SQLParserException();
        }
    }


    public ISQLReadOnlyClause parseReadOnlyClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.READ)) {
            return null;
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.ONLY)) {
            return new ISQLReadOnlyClause.SQLReadOnly();
        } else if (this.acceptAndNextToken(SQLToken.TokenKind.WRITE)) {
            return new ISQLReadOnlyClause.SQLReadWrite();
        } else {
            throw new SQLParserException();
        }
    }

    public ISQLIndexingClause parseIndexingClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.INDEXING)) {
            return null;
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.ON)) {
            return new ISQLIndexingClause.SQLIndexingOn();
        } else if (this.acceptAndNextToken(SQLToken.TokenKind.OFF)) {
            return new ISQLIndexingClause.SQLIndexingOff();
        } else {
            throw new SQLParserException();
        }
    }

    public boolean isParseSegmentAttribute() {
        return isParsePhysicalAttribute()
                || this.accept(SQLToken.TokenKind.TABLESPACE)
                || this.isParseLoggingClause();
    }

    public ISQLSegmentAttribute parseSegmentAttribute() {
        if (!isParseSegmentAttribute()) {
            return null;
        }

        if (isParsePhysicalAttribute()) {
            return parsePhysicalAttribute();
        }

        if (this.accept(SQLToken.TokenKind.TABLESPACE)) {
            return parseTableSpaceClause();
        }

        if (isParseLoggingClause()) {
            return parseLoggingClause();
        }

        return null;
    }

    public SQLTableSpaceSetClause parseTableSpaceClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.TABLESPACE)) {
            return null;
        }

        boolean set = this.acceptAndNextToken(SQLToken.TokenKind.SET);
        return new SQLTableSpaceSetClause(parseExpr());
    }

    public boolean isParseCompression() {
        return this.accept(SQLToken.TokenKind.COMPRESS)
                || this.accept(SQLToken.TokenKind.ROW)
                || this.accept(SQLToken.TokenKind.COLUMN)
                || this.accept(SQLToken.TokenKind.NOCOMPRESS);
    }

    public ISQLCompression parseCompression() {
        if (!isParseCompression()) {
            return null;
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.COMPRESS)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.ADVANCED)) {
                return new ISQLCompression.SQLCompress(parseExpr());
            }
            return new ISQLCompression.SQLCompress(parseExpr());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.ROW)) {
            this.acceptAndNextToken(SQLToken.TokenKind.STORE, true);
            this.acceptAndNextToken(SQLToken.TokenKind.COMPRESS, true);
            ISQLCompression.SQLRowStoreCompressType type = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.BASIC)) {
                type = ISQLCompression.SQLRowStoreCompressType.BASIC;
            } else if (this.acceptAndNextToken(SQLToken.TokenKind.ADVANCED)) {
                type = ISQLCompression.SQLRowStoreCompressType.ADVANCED;
            }
            return new ISQLCompression.SQLRowStoreCompress(type);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.COLUMN)) {
            this.acceptAndNextToken(SQLToken.TokenKind.STORE, true);
            this.acceptAndNextToken(SQLToken.TokenKind.COMPRESS, true);

            ISQLCompression.SQLColumnStoreCompressForType forType = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.FOR)) {

                if (this.acceptAndNextToken(SQLToken.TokenKind.QUERY)) {

                    forType = ISQLCompression.SQLColumnStoreCompressForType.FOR_QUERY;

                    if (this.acceptAndNextToken(SQLToken.TokenKind.LOW)) {

                        forType = ISQLCompression.SQLColumnStoreCompressForType.FOR_QUERY_LOW;

                    } else if (this.acceptAndNextToken(SQLToken.TokenKind.HIGH)) {
                        forType = ISQLCompression.SQLColumnStoreCompressForType.FOR_QUERY_HIGH;
                    }

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.ARCHIVE)) {
                    forType = ISQLCompression.SQLColumnStoreCompressForType.FOR_ARCHIVE;

                    if (this.acceptAndNextToken(SQLToken.TokenKind.LOW)) {
                        forType = ISQLCompression.SQLColumnStoreCompressForType.FOR_ARCHIVE_LOW;

                    } else if (this.acceptAndNextToken(SQLToken.TokenKind.HIGH)) {
                        forType = ISQLCompression.SQLColumnStoreCompressForType.FOR_ARCHIVE_HIGH;
                    }

                } else {
                    throw new SQLParserException(errorInfo());
                }
            }

            ISQLCompression.SQLRowLevelLockingType rowLevelLockingType = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.NO)) {
                this.acceptAndNextToken(SQLToken.TokenKind.ROW, true);
                this.acceptAndNextToken(SQLToken.TokenKind.LEVEL, true);
                this.acceptAndNextToken(SQLToken.TokenKind.LOCKING, true);
                rowLevelLockingType = ISQLCompression.SQLRowLevelLockingType.NO_ROW_LEVEL_LOCKING;
            } else if (this.acceptAndNextToken(SQLToken.TokenKind.ROW)) {
                this.acceptAndNextToken(SQLToken.TokenKind.LEVEL, true);
                this.acceptAndNextToken(SQLToken.TokenKind.LOCKING, true);
                rowLevelLockingType = ISQLCompression.SQLRowLevelLockingType.ROW_LEVEL_LOCKING;
            }

            ISQLCompression.SQLColumnStoreCompress x = new ISQLCompression.SQLColumnStoreCompress();
            x.setForType(forType);
            x.setRowLevelLockingType(rowLevelLockingType);

            return x;
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.NOCOMPRESS)) {
            return new ISQLCompression.SQLNoCompress();
        }

        return null;
    }


    public boolean isParseIndexCompression() {
        return this.accept(SQLToken.TokenKind.COMPRESS)
                || this.accept(SQLToken.TokenKind.NOCOMPRESS);
    }

    public ISQLCompression parseIndexCompression() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.COMPRESS)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.ADVANCED)) {
                return new ISQLCompression.SQLCompress(parseExpr());
            }
            return new ISQLCompression.SQLCompress(parseExpr());
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.NOCOMPRESS)) {
            return new ISQLCompression.SQLNoCompress();
        }
        return null;
    }

    public boolean isParsePrefixCompression() {
        return this.accept(SQLToken.TokenKind.COMPRESS)
                || this.accept(SQLToken.TokenKind.NOCOMPRESS);
    }

    public ISQLCompression parsePrefixCompression() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.COMPRESS)) {
            return new ISQLCompression.SQLCompress(parseExpr());
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.NOCOMPRESS)) {
            return new ISQLCompression.SQLNoCompress();
        }
        return null;
    }

    public ISQLCompression parseAdvancedIndexCompression() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.COMPRESS)) {
            this.acceptAndNextToken(SQLToken.TokenKind.ADVANCED);
            return new ISQLCompression.SQLCompressAdvanced(parseExpr());
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.NOCOMPRESS)) {
            return new ISQLCompression.SQLNoCompress();
        }
        return null;
    }

    public ISQLInMemoryClause parseInMemoryClause() {
        return null;
    }


    public SQLParameterDeclaration parseParameterDeclaration() {
        SQLParameterDeclaration x = new SQLParameterDeclaration();

        SQLParameterDeclaration.SQLParameterModel parameterModel = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.IN)) {
            parameterModel = SQLParameterDeclaration.SQLParameterModel.IN;

            if (this.acceptAndNextToken(SQLToken.TokenKind.OUT)) {
                parameterModel = SQLParameterDeclaration.SQLParameterModel.IN_OUT;
            }

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.OUT)) {
            parameterModel = SQLParameterDeclaration.SQLParameterModel.OUT;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.INOUT)) {
            parameterModel = SQLParameterDeclaration.SQLParameterModel.INOUT;

        }
        x.setParameterModel(parameterModel);

        ISQLName name = parseName();
        x.setName(name);

        ISQLDataType dataType = parseDataType();
        x.setDataType(dataType);

        if (this.acceptAndNextToken(SQLToken.TokenKind.AS)) {
            this.acceptAndNextToken(SQLToken.TokenKind.LOCATOR, true);
            x.setLocatorIndication(true);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.RESULT)) {
            x.setResult(true);
        }

        return x;
    }



    public boolean isParseBlock() {
        return this.accept(SQLToken.TokenKind.LTLT)
                || this.accept(SQLToken.TokenKind.DECLARE)
                || this.accept(SQLToken.TokenKind.BEGIN);
    }

    public SQLBlock parseBlock() {
        if (!isParseBlock()) {
            return null;
        }

        SQLBlock x = new SQLBlock();
        for (; ; ) {
            SQLLabel label = parseLabel();
            if (label == null) {
                break;
            }
            x.addLabel(label);
        }

        x.setBody(parseBody());
        return x;
    }


    public SQLBody parseBody() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.BEGIN)) {
            return null;
        }

        SQLBody x = new SQLBody();

        for (; ; ) {
            if (this.accept(SQLToken.TokenKind.EXCEPTION)
                    || this.accept(SQLToken.TokenKind.END)) {
                break;
            }
            SQLLabelStatement statement = parseLabelStatement();
            if (statement == null) {
                throw new SQLParserException(errorInfo());
            }
            x.addStatement(statement);
        }

        this.acceptAndNextToken(SQLToken.TokenKind.END, true);
        ISQLName endName = parseName();
        x.setEndName(endName);

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
        x.setAfterSemi(true);

        return x;
    }


}
