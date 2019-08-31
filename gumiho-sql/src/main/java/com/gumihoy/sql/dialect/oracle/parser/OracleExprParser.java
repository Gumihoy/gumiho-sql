package com.gumihoy.sql.dialect.oracle.parser;

import static com.gumihoy.sql.basic.parser.SQLToken.TokenKind.*;
import static com.gumihoy.sql.basic.parser.SQLToken.TokenKind.COMMA;

import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.enums.SQLSetQuantifier;
import com.gumihoy.sql.basic.ast.enums.SQLVisibleType;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.*;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLPropertyExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLUnquotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.method.SQLChrFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLMethodInvocation;
import com.gumihoy.sql.basic.ast.expr.method.SQLTranslateUsingFunction;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLSequenceExpr;
import com.gumihoy.sql.basic.ast.expr.pseudorecords.SQLPseudoNewRecordName;
import com.gumihoy.sql.basic.ast.expr.pseudorecords.SQLPseudoOldRecordName;
import com.gumihoy.sql.basic.ast.expr.pseudorecords.SQLPseudoParentRecordName;
import com.gumihoy.sql.basic.ast.expr.select.ISQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.ISQLTableReference;
import com.gumihoy.sql.basic.ast.expr.select.SQLParenSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.SQLTableFunctionTableReference;
import com.gumihoy.sql.basic.ast.expr.select.group.SQLGroupByClause;
import com.gumihoy.sql.basic.ast.expr.select.limit.ISQLLimitClause;
import com.gumihoy.sql.basic.ast.expr.table.ISQLIdentityOption;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLColumnDefinition;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.ISQLColumnConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.*;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionDefinition;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionDefinition;
import com.gumihoy.sql.basic.ast.statement.ISQLStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLDeleteStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLUpdateStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLFetchStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLKeyWord;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.*;
import com.gumihoy.sql.config.SQLParseConfig;
import com.gumihoy.sql.dialect.oracle.ast.expr.common.*;
import com.gumihoy.sql.dialect.oracle.ast.expr.select.OracleSelectQuery;
import com.gumihoy.sql.dialect.oracle.ast.expr.table.element.IOracleSupplementLoggingProp;
import com.gumihoy.sql.dialect.oracle.ast.expr.table.element.OracleSQLSupplementalLogGrpClause;
import com.gumihoy.sql.dialect.oracle.parser.statement.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author kent on 2019-06-15.
 */
public class OracleExprParser extends SQLExprParser {

    public OracleExprParser(String sql) {
        super(new OracleLexer(sql));
    }

    public OracleExprParser(String sql, SQLParseConfig config) {
        super(new OracleLexer(sql, config));
    }

    public OracleExprParser(SQLLexer lexer) {
        super(lexer);
    }

    protected static final Set<Long> ORACLE_NO_ARGUMENT_FUNCTION = new HashSet<>();

    {
        ORACLE_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.CURRENT_DATE.lowerHash);
        ORACLE_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.CURRENT_TIMESTAMP.lowerHash);
        ORACLE_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.DBTIMEZONE.lowerHash);
        ORACLE_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.ITERATION_NUMBER.lowerHash);
        ORACLE_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.LOCALTIMESTAMP.lowerHash);
        ORACLE_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.ORA_INVOKING_USER.lowerHash);
        ORACLE_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.ORA_INVOKING_USERID.lowerHash);
        ORACLE_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.SESSIONTIMEZONE.lowerHash);
        ORACLE_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.SYSDATE.lowerHash);
        ORACLE_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.SYSTIMESTAMP.lowerHash);
        ORACLE_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.UID.lowerHash);
        ORACLE_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.USER.lowerHash);
        ORACLE_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.SQLCODE.lowerHash);
        ORACLE_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.SQLERRM.lowerHash);

        noArgumentFunctionLowerHash = ORACLE_NO_ARGUMENT_FUNCTION;
    }

    protected static final Set<Long> ORACLE_SPECIAL_FUNCTION = new HashSet<>();

    {
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.CHR.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.TRANSLATE.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.TRIM.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.EXTRACT.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.CAST.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.TREAT.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.VALIDATE_CONVERSION.lowerHash);

        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.CLUSTER_DETAILS.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.CLUSTER_DISTANCE.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.CLUSTER_ID.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.CLUSTER_PROBABILITY.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.CLUSTER_SET.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.FEATURE_COMPARE.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.FEATURE_DETAILS.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.FEATURE_ID.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.FEATURE_SET.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.FEATURE_VALUE.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.ORA_DM_PARTITION_NAME.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.PREDICTION.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.PREDICTION_BOUNDS.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.PREDICTION_COST.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.PREDICTION_DETAILS.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.PREDICTION_PROBABILITY.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.PREDICTION_SET.lowerHash);

        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.XMLCAST.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.XMLCOLATTVAL.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.XMLELEMENT.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.XMLEXISTS.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.XMLFOREST.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.XMLPARSE.lowerHash);
        ORACLE_SPECIAL_FUNCTION.add(SQLKeyWord.XMLPI.lowerHash);

        specialFunctionLowerHash = ORACLE_SPECIAL_FUNCTION;
    }

    @Override
    public ISQLStatement parseStatement() {
        ISQLStatement statement = super.parseStatement();
        if (statement != null) {
            return statement;
        }

        if (this.accept(SQLToken.TokenKind.EXECUTE)) {
            return parseExecuteImmediateStatement();
        }

        if (this.accept(FORALL)) {
            return parseForAllStatement();
        }

        SQLLexer.SQLMake make = this.make();
        ISQLIdentifier name = parseIdentifier();
        if (this.acceptAndNextToken(DOT)
                && isCollectionMethodInvocationName()) {
            this.reset(make);
            return parseCollectionMethodInvocation();
        }

        this.reset(make);

        return parseProcedureInvocation();
    }


    @Override
    public SQLCommentStatementParser createCommentStatementParser() {
        return super.createCommentStatementParser();
    }

    @Override
    protected SQLDatabaseStatementParser createDatabaseStatementParser() {
        return new OracleDatabaseStatementParser(this);
    }

    @Override
    protected SQLDatabaseLinkStatementParser createDatabaseLinkStatementParser() {
        return new SQLDatabaseLinkStatementParser(this);
    }

    @Override
    protected SQLFunctionStatementParser createFunctionStatementParser() {
        return new OracleFunctionStatementParser(this);
    }

    @Override
    protected SQLIndexStatementParser createIndexStatementParser() {
        return new OracleIndexStatementParser(this);
    }

    @Override
    protected SQLMaterializedViewStatementParser createMaterializedViewStatementParser() {
        return new OracleMaterializedViewStatementParser(this);
    }

    @Override
    protected SQLPackageStatementParser createPackageStatementParser() {
        return new OraclePackageStatementParser(this);
    }

    @Override
    protected SQLPackageBodyStatementParser createPackageBodyStatementParser() {
        return new OraclePackageBodyStatementParser(this);
    }

    @Override
    protected SQLProcedureStatementParser createProcedureStatementParser() {
        return new OracleProcedureStatementParser(this);
    }

    @Override
    protected SQLRoleStatementParser createRoleStatementParser() {
        return new OracleRoleStatementParser(this);
    }

    @Override
    protected SQLSchemaStatementParser createSchemaStatementParser() {
        return super.createSchemaStatementParser();
    }

    @Override
    protected SQLSequenceStatementParser createSequenceStatementParser() {
        return new OracleSequenceStatementParser(this);
    }

    @Override
    protected SQLSynonymStatementParser createSynonymStatementParser() {
        return new OracleSynonymStatementParser(this);
    }

    @Override
    protected SQLTableStatementParser createTableStatementParser() {
        return new OracleTableStatementParser(this);
    }

    @Override
    protected SQLTriggerStatementParser createTriggerStatementParser() {
        return new OracleTriggerStatementParser(this);
    }

    @Override
    protected SQLTypeStatementParser createTypeStatementParser() {
        return new OracleTypeStatementParser(this);
    }

    @Override
    protected SQLTypeBodyStatementParser createTypeBodyStatementParser() {
        return new OracleTypeBodyStatementParser(this);
    }

    @Override
    protected SQLUserStatementParser createUserStatementParser() {
        return super.createUserStatementParser();
    }

    @Override
    protected SQLViewStatementParser createViewStatementParser() {
        return new OracleViewStatementParser(this);
    }


    @Override
    public SQLDeleteStatement parseDeleteStatement() {

        acceptAndNextToken(SQLToken.TokenKind.DELETE, true);

        SQLDeleteStatement x = new SQLDeleteStatement(this.dbType);

        boolean from = acceptAndNextToken(SQLToken.TokenKind.FROM);
        x.setFrom(from);

        ISQLTableReference tableReference = parseTableReference();
        x.setTableReference(tableReference);

        SQLWhereClause whereClause = parseWhere();
        x.setWhereClause(whereClause);

        ISQLReturningClause returningClause = parseReturningClause();
        x.setReturningClause(returningClause);

        SQLErrorLoggingClause errorLoggingClause = parseErrorLoggingClause();
        x.setErrorLoggingClause(errorLoggingClause);

        return x;
    }


    public SQLInsertStatementParser createInsertStatementParser() {
        return new OracleInsertStatementParser(this);
    }

    @Override
    public SQLUpdateStatement parseUpdateStatement() {
        SQLUpdateStatement x = super.parseUpdateStatement();

        ISQLReturningClause returningClause = parseReturningClause();
        x.setReturningClause(returningClause);

        SQLErrorLoggingClause errorLoggingClause = parseErrorLoggingClause();
        x.setErrorLoggingClause(errorLoggingClause);

        return x;
    }

    @Override
    public SQLFetchStatement parseFetchStatement() {
        if (!this.acceptAndNextToken(FETCH)) {
            return null;
        }
        SQLFetchStatement x = new SQLFetchStatement(this.dbType);
        x.setDirection(parseExpr());

        if (this.acceptAndNextToken(BULK)) {
            this.acceptAndNextToken(COLLECT, true);
            x.setBulkCollect(true);
        }

        this.acceptAndNextToken(INTO, true);
        for(;;) {
            x.addIntoItem(parseExpr());
            if (!this.acceptAndNextToken(COMMA)) {
                break;
            }
        }

        if (this.acceptAndNextToken(LIMIT)) {
            x.setLimitExpr(parseExpr());
        }

        return x;
    }


    /**
     * 2   |   *, /    |   Multiplication, division
     * 3   |   +, - (as binary operators), &#124;&#124;  |   Addition, subtraction, concatenation
     * 4   |   =, !=, <, >, <=, >= |   comparison
     * 5   |   IS [NOT] NULL, LIKE, [NOT] BETWEEN, [NOT] IN, EXISTS, IS OF type    |   comparison
     * 6   |   NOT |   exponentiation, logical negation
     * 7   |   AND |   conjunction
     * 8   |   OR  |   disjunction
     */
    @Override
    public ISQLExpr parseExpr(ISQLExpr expr) {
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
     * x .xx
     *
     * @param expr
     * @return xx.xx
     */
    public ISQLExpr parseDotExpr(ISQLExpr expr) {
        if (!this.acceptAndNextToken(DOT)) {
            return expr;
        }

        if (expr instanceof ISQLIdentifier) {
            ISQLIdentifier identifier = (ISQLIdentifier) expr;
            if (identifier.lowerHash() == SQLKeyWord.NEW.lowerHash) {
                return new SQLPseudoNewRecordName(parseIdentifier());
            } else if (identifier.lowerHash() == SQLKeyWord.OLD.lowerHash) {
                return new SQLPseudoOldRecordName(parseIdentifier());
            } else if (identifier.lowerHash() == SQLKeyWord.PARENT.lowerHash) {
                return new SQLPseudoParentRecordName(parseIdentifier());
            }
        }

        if (this.acceptAndNextToken(CURRVAL)) {

            expr = new SQLSequenceExpr(expr, SQLSequenceExpr.SQLSequenceFunction.CURRVAL);

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.NEXTVAL)) {

            expr = new SQLSequenceExpr(expr, SQLSequenceExpr.SQLSequenceFunction.NEXTVAL);

        } else {

            ISQLIdentifier right = parseIdentifier();
            expr = new SQLPropertyExpr(expr, right);
            expr = parseDotExpr(expr);
        }

        return parseExprPrimary(expr);
    }

    @Override
    public ISQLExpr parseFunction(ISQLExpr expr) {
        return super.parseFunction(expr);
    }

    @Override
    public ISQLExpr parseSpecialFunction(ISQLExpr expr) {
        expr = super.parseSpecialFunction(expr);

        if (!(expr instanceof SQLUnquotedIdentifier)) {
            return expr;
        }

        SQLUnquotedIdentifier identifier = (SQLUnquotedIdentifier) expr;
        long lowerHash = identifier.lowerHash();

        if (!specialFunctionLowerHash.contains(lowerHash)) {
            return expr;
        }

        if (lowerHash == SQLKeyWord.CHR.lowerHash) {
            return parseChrFunction(identifier);
        }

        if (lowerHash == SQLKeyWord.TRANSLATE.lowerHash) {
            return parseTranslateFunction(identifier);
        }

        return expr;
    }


    protected ISQLExpr parseChrFunction(ISQLExpr name) {
        ISQLExpr argument = parseExpr();

        ISQLExpr usingValue = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.USING)) {
            usingValue = parseExpr();
        }

        return new SQLChrFunction(argument, usingValue);
    }

    protected ISQLExpr parseTranslateFunction(ISQLExpr name) {
        ISQLExpr argument = parseExpr();

        ISQLExpr usingValue = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.USING)) {
            usingValue = parseExpr();
        }

        return new SQLTranslateUsingFunction(argument, usingValue);
    }

    @Override
    public ISQLExpr parseCallExpr(ISQLExpr name) {
        if (!this.acceptAndNextToken(EQGT)) {
            return name;
        }

        ISQLExpr value = parseExpr();
        return new SQLCallExpr(name, value);
    }

    @Override
    public ISQLSelectQuery parseQueryBlockPrimary() {
        if (lexer.token().kind == SQLToken.TokenKind.LPAREN) {
            nextToken();

            ISQLSelectQuery selectQuery = parseSelectQuery();
            SQLParenSelectQuery query = new SQLParenSelectQuery(selectQuery);
            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            return query;

        }

        OracleSelectQuery query = new OracleSelectQuery();

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

        if (this.acceptAndNextToken(BULK)
                && this.acceptAndNextToken(COLLECT)) {
            query.setBulkCollect(true);
        }

        parseSelectTargetItems(query);

        SQLFromClause fromClause = parseFrom();
        query.setFromClause(fromClause);

        SQLWhereClause whereClause = parseWhere();
        query.setWhereClause(whereClause);

        ISQLHierarchicalQueryClause hierarchicalQueryClause = parseHierarchicalQuery();
        query.setHierarchicalQueryClause(hierarchicalQueryClause);

        SQLGroupByClause groupByClause = parseGroupBy();
        query.setGroupByClause(groupByClause);

        SQLWindowClause windowClause = parseWindow();
        query.setWindowClause(windowClause);

        return query;
    }


    /**
     * TABLE (expr) [(+)] [[as] alias]
     */
    public SQLTableFunctionTableReference parserTableFunctionTableReference() {
        boolean accept = acceptAndNextToken(SQLToken.TokenKind.TABLE);
        if (!accept) {
            return null;
        }

        acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        ISQLExpr expr = parseExpr();
        acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        SQLTableFunctionTableReference x = new SQLTableFunctionTableReference(expr);


        if (lexer.token().kind == SQLToken.TokenKind.LPAREN) {
            nextToken();
            acceptAndNextToken(SQLToken.TokenKind.PLUS, true);
            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

            x.setOuterJoin(true);
        }

        boolean as = false;
        ISQLIdentifier alias = null;
        if (lexer.token().kind == SQLToken.TokenKind.AS) {
            nextToken();

            as = true;
            alias = parseIdentifier();

            if (alias == null) {
                throw new SQLParserException("");
            }
        } else {
            alias = parseIdentifier();
        }
        x.setAs(as);
        x.setAlias(alias);

        return x;
    }


    public ISQLLimitClause parseLimit() {
        return parseOffsetFetchClause();
    }


    @Override
    public SQLColumnDefinition parseColumnDefinition() {
        if (!isIdentifier()) {
            return null;
        }

        SQLColumnDefinition x = new SQLColumnDefinition();

        ISQLName name = parseName();
        x.setName(name);

        if (!this.accept(SQLToken.TokenKind.COLLATE)
                && !this.accept(SQLToken.TokenKind.SORT)
                && !isParseVisibleType()
                && !this.accept(SQLToken.TokenKind.DEFAULT)
                && !this.accept(SQLToken.TokenKind.GENERATED)
                && !this.accept(SQLToken.TokenKind.AS)
                && !this.accept(SQLToken.TokenKind.ENCRYPT)
                && !isColumnConstraint()) {

            ISQLDataType dataType = parseDataType();
            x.setDataType(dataType);

        }


        if (this.accept(SQLToken.TokenKind.COLLATE)) {
            SQLCollateOptionExpr collateOptionExpr = parseCollateClause();
            x.setCollateClause(collateOptionExpr);
        }


        if (this.acceptAndNextToken(SQLToken.TokenKind.SORT)) {
            x.setSort(true);
        }

        SQLVisibleType visibleType = parseVisibleType();
        x.setVisible(visibleType);

        if (this.accept(SQLToken.TokenKind.DEFAULT)) {

            ISQLExpr defaultClause = parseDefaultClause();
            x.setDefaultClause(defaultClause);

        } else if (this.accept(SQLToken.TokenKind.GENERATED)) {
            nextToken();
            if (this.acceptAndNextToken(SQLToken.TokenKind.ALWAYS)) {

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.BY)) {
                this.acceptAndNextToken(SQLToken.TokenKind.DEFAULT, true);

                if (this.acceptAndNextToken(SQLToken.TokenKind.ON)) {
                    this.acceptAndNextToken(SQLToken.TokenKind.NULL, true);
                }

            }

            this.acceptAndNextToken(SQLToken.TokenKind.AS, true);

            if (this.acceptAndNextToken(SQLToken.TokenKind.IDENTITY)) {

                if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
                    for (; ; ) {
                        ISQLIdentityOption option = parseIdentityOption();
                        if (option == null) {
                            break;
                        }
                    }
                    this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
                }

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {

            }

        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.ENCRYPT)) {

        }


        for (; ; ) {
            ISQLColumnConstraint columnConstraint = parseColumnConstraint();
            x.addColumnConstraint(columnConstraint);
            if (this.accept(SQLToken.TokenKind.COMMA)
                    || columnConstraint == null) {
                break;
            }
        }


        return x;
    }


    @Override
    public ISQLExpr parseConstraintOption() {

        ISQLExpr option = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.NOT)) {

            this.acceptAndNextToken(SQLToken.TokenKind.DEFERRABLE, true);
            option = new SQLNotDeferrableConstraintState();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.DEFERRABLE)) {

            option = new SQLDeferrableConstraintState();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.INITIALLY)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.IMMEDIATE)) {

                option = new SQLInitiallyImmediateConstraintState();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.DEFERRED)) {

                option = new SQLInitiallyDeferredConstraintState();

            } else {
                throw new SQLParserException(errorInfo());
            }


        } else if (this.acceptAndNextToken(SQLToken.TokenKind.RELY)) {

            option = new SQLRelyConstraintState();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.NORELY)) {

            option = new SQLNoRelyConstraintState();

        } else if (this.accept(SQLToken.TokenKind.USING)) {

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.ENABLE)) {

            option = new SQLEnableConstraintState();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.DISABLE)) {

            option = new SQLDisableConstraintState();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.VALIDATE)) {

            option = new SQLValidateConstraintState();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.NOVALIDATE)) {

            option = new SQLNoValidateConstraintState();

        } else if (this.accept(SQLToken.TokenKind.EXCEPTIONS)) {
            option = parseExceptionsClause();
        }

        return option;
    }

    public boolean isParsePhysicalProperty() {
        return this.accept(SQLToken.TokenKind.SEGMENT)
                || isParseSegmentAttribute()
                || isParseCompression()
                || this.accept(SQLToken.TokenKind.ORGANIZATION)
                || this.accept(SQLToken.TokenKind.CLUSTER);
    }

    public List<ISQLPhysicalProperty> parsePhysicalProperty() {
        List<ISQLPhysicalProperty> properties = new ArrayList<>();
        if (this.accept(SQLToken.TokenKind.SEGMENT)) {
            properties.add(parseDeferredSegmentCreation());
        }

        if (isParseSegmentAttribute()) {
            for (; ; ) {
                properties.add(parseSegmentAttribute());
                if (!isParseSegmentAttribute()) {
                    break;
                }
            }

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.ORGANIZATION)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.HEAP)) {
                ISQLPhysicalProperty.SQLPhysicalPropertyOrganizationHeapClause organizationClause = new ISQLPhysicalProperty.SQLPhysicalPropertyOrganizationHeapClause();
                properties.add(organizationClause);

                for (; ; ) {
                    if (!isParseSegmentAttribute()) {
                        break;
                    }
                    organizationClause.addItem(parseSegmentAttribute());
                }

                organizationClause.addItems(parseHeapOrgTableClause());

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.INDEX)) {
                ISQLPhysicalProperty.SQLPhysicalPropertyOrganizationIndexClause organizationClause = new ISQLPhysicalProperty.SQLPhysicalPropertyOrganizationIndexClause();
                properties.add(organizationClause);

                organizationClause.addItems(parseIndexOrgTableClause());

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.EXTERNAL)) {
                ISQLPhysicalProperty.SQLPhysicalPropertyOrganizationExternalClause organizationClause = new ISQLPhysicalProperty.SQLPhysicalPropertyOrganizationExternalClause();
                properties.add(organizationClause);

                organizationClause.setExternalTableClause(parseExternalTableClause());
            }


        } else if (this.acceptAndNextToken(SQLToken.TokenKind.CLUSTER)) {
            ISQLPhysicalProperty.SQLPhysicalPropertyClusterClause x = new ISQLPhysicalProperty.SQLPhysicalPropertyClusterClause();
            x.setCluster(parseName());
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            for (; ; ) {
                x.addColumn(parseExpr());
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return null;
    }

    public boolean isParseInMemoryClause() {
        return this.accept(SQLToken.TokenKind.INMEMORY)
                || this.accept(SQLToken.TokenKind.NO);
    }

    public ISQLInMemoryClause parseInMemoryClause() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.INMEMORY)) {
            parseInMemoryMemCompressClause();
            return new ISQLInMemoryClause.SQLInMemoryClause();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.NO)) {
            this.acceptAndNextToken(SQLToken.TokenKind.INMEMORY, true);
            return new ISQLInMemoryClause.SQLNoInMemoryClause();
        }
        return null;
    }

    public boolean isParseInMemoryMemCompressClause() {
        return this.accept(SQLToken.TokenKind.MEMCOMPRESS)
                || this.accept(SQLToken.TokenKind.NO);
    }

    public ISQLInMemoryMemCompressClause parseInMemoryMemCompressClause() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.MEMCOMPRESS)) {
            this.acceptAndNextToken(SQLToken.TokenKind.FOR, true);

            if (this.acceptAndNextToken(SQLToken.TokenKind.DML)) {

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.QUERY)) {

                if (this.acceptAndNextToken(SQLToken.TokenKind.LOW)) {

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.HIGH)) {

                } else {
                    throw new SQLParserException(errorInfo());
                }

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.CAPACITY)) {
                if (this.acceptAndNextToken(SQLToken.TokenKind.LOW)) {

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.HIGH)) {

                } else {
                    throw new SQLParserException(errorInfo());
                }

            } else {
                throw new SQLParserException(errorInfo());
            }

            return new ISQLInMemoryMemCompressClause.SQLInMemoryMemCompressClause();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.NO)) {
            this.acceptAndNextToken(SQLToken.TokenKind.MEMCOMPRESS, true);
            return new ISQLInMemoryMemCompressClause.SQLInMemoryNoMemCompressClause();
        }
        return null;
    }

    public List<ISQLExpr> parseInmemoryTableClause() {

        return null;
    }


    public List<ISQLColumnProperty> parseColumnProperties() {
        List<ISQLColumnProperty> properties = new ArrayList<>();
        for (; ; ) {
            if (!isParseColumnProperty()) {
                break;
            }
            properties.add(parseColumnProperty());
        }
        return properties;
    }

    public boolean isParseColumnProperty() {
        return this.accept(SQLToken.TokenKind.COLUMN)
                || this.accept(SQLToken.TokenKind.NESTED)
                || this.accept(SQLToken.TokenKind.VARRAY)
                || this.accept(SQLToken.TokenKind.LOB)
                || this.accept(SQLToken.TokenKind.XMLTYPE);
    }

    public ISQLColumnProperty parseColumnProperty() {
        if (this.accept(SQLToken.TokenKind.COLUMN)) {
            return parseObjectTypeColProperty();
        }
        if (this.accept(SQLToken.TokenKind.NESTED)) {
            return parseNestedTableColProperty();
        }
        if (this.accept(SQLToken.TokenKind.VARRAY)) {

        }
        if (this.accept(SQLToken.TokenKind.LOB)) {
            return parseColumnPropertyLobStorageClause();
        }
        if (this.accept(SQLToken.TokenKind.XMLTYPE)) {
            return parseXmlTypeColumnProperty();
        }
        return null;
    }

    public OracleObjectTypeColProperty parseObjectTypeColProperty() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.COLUMN)) {
            return null;
        }

        OracleObjectTypeColProperty x = new OracleObjectTypeColProperty();
        x.setColumn(parseExpr());
        x.setSubstitutableColumnClause(parseSubstitutableColumnClause());
        return x;
    }

    public IOracleSubstitutableColumnClause parseSubstitutableColumnClause() {

        return null;
    }

    public OracleNestedTableColProperty parseNestedTableColProperty() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.NESTED)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.TABLE, true);

        OracleNestedTableColProperty x = new OracleNestedTableColProperty();
        x.setNestedItem(parseExpr());

        x.setSubstitutableColumnClause(parseSubstitutableColumnClause());

        if (this.acceptAndNextToken(SQLToken.TokenKind.LOCAL)) {

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.GLOBAL)) {

        }

        this.acceptAndNextToken(SQLToken.TokenKind.STORE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.AS, true);
        x.setStorageTable(parseName());

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {

            for (; ; ) {
                ISQLExpr storeAsItem = null;
                if (isParsePhysicalProperty()) {
                    x.addStoreAsItems(parsePhysicalProperty());
                } else if (isParseColumnProperty()) {
                    x.addStoreAsItems(parseColumnProperties());
                } else {
                    if (!this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
                        break;
                    }
                }

            }

            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return x;
    }

    public OracleLobStorageClause parseLobStorageClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.LOB)) {
            return null;
        }

        OracleLobStorageClause x = new OracleLobStorageClause();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            x.addItem(parseExpr());
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        this.acceptAndNextToken(SQLToken.TokenKind.STORE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.AS, true);

        for (; ; ) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
                OracleLobStorageClause.SQLLobStorageParameters parameters = new OracleLobStorageClause.SQLLobStorageParameters();
                for (; ; ) {
                    ISQLLobStorageParameter parameter = parseLobStorageParameter();
                    if (!isParseLobStorageParameter()) {
                        break;
                    }
                    parameters.addParameter(parameter);
                }
                this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

                x.addParameter(parameters);

            } else if (this.accept(SQLToken.TokenKind.SECUREFILE)) {
                x.addParameter(parseName());
            } else if (this.accept(SQLToken.TokenKind.BASICFILE)) {
                x.addParameter(parseName());
            } else if (isIdentifier()) {
                x.addParameter(parseName());
            } else {
                break;
            }

        }

        return x;
    }

    public IOracleColumnProperty.OracleColumnPropertyLobStorageClause parseColumnPropertyLobStorageClause() {
        if (!this.accept(SQLToken.TokenKind.LOB)) {
            return null;
        }

        IOracleColumnProperty.OracleColumnPropertyLobStorageClause x = new IOracleColumnProperty.OracleColumnPropertyLobStorageClause();
        x.setLobStorageClause(parseLobStorageClause());

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {

            for (; ; ) {
                x.addPartition(parsePartitionDefinition());
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }

            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return x;
    }

    public boolean isParseLobStorageParameter() {
        return this.accept(SQLToken.TokenKind.TABLESPACE)
                || this.isParseLobParameter()
                || this.accept(SQLToken.TokenKind.STORAGE);
    }

    public ISQLLobStorageParameter parseLobStorageParameter() {
        if (this.accept(SQLToken.TokenKind.TABLESPACE)) {
            return parseTableSpaceClause();
        }

        if (this.isParseLobParameter()) {
            return parseLobParameter();
        }

        if (this.accept(SQLToken.TokenKind.STORAGE)) {
            return parseStorageClause();
        }

        return null;
    }

    public boolean isParseLobParameter() {
        return this.accept(SQLToken.TokenKind.ENABLE)
                || this.accept(SQLToken.TokenKind.DISABLE)
                || this.accept(SQLToken.TokenKind.CHUNK)
                || this.accept(SQLToken.TokenKind.PCTVERSION)
                || this.accept(SQLToken.TokenKind.FREEPOOLS)
                || this.accept(SQLToken.TokenKind.ENCRYPT)
                || this.accept(SQLToken.TokenKind.DECRYPT)
                || this.accept(SQLToken.TokenKind.CACHE)
                || this.accept(SQLToken.TokenKind.NOCACHE);
    }

    public ISQLLobParameter parseLobParameter() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.ENABLE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.STORAGE, true);
            this.acceptAndNextToken(SQLToken.TokenKind.IN, true);
            this.acceptAndNextToken(SQLToken.TokenKind.ROW, true);

            return new ISQLLobParameter.SQLEnableStorageInRow();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.DISABLE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.STORAGE, true);
            this.acceptAndNextToken(SQLToken.TokenKind.IN, true);
            this.acceptAndNextToken(SQLToken.TokenKind.ROW, true);

            return new ISQLLobParameter.SQLDisableStorageInRow();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.CHUNK)) {
            return new ISQLLobParameter.SQLChunk(parseExpr());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.PCTVERSION)) {
            return new ISQLLobParameter.SQLPctversion(parseExpr());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.PCTVERSION)) {
            return new ISQLLobParameter.SQLPctversion(parseExpr());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.PCTVERSION)) {
            return new ISQLLobParameter.SQLPctversion(parseExpr());
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.PCTVERSION)) {
            return new ISQLLobParameter.SQLPctversion(parseExpr());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.ENCRYPT)) {

            return new ISQLLobParameter.SQLEncrypt();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.DECRYPT)) {
            return new ISQLLobParameter.SQLDecrypt();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.CACHE)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.READS)) {
                return new ISQLLobParameter.SQLCacheReads(parseLoggingClause());
            }
            return new ISQLLobParameter.SQLCache(parseLoggingClause());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.NOCACHE)) {
            return new ISQLLobParameter.SQLNoCache(parseLoggingClause());
        }

        return null;
    }

    public OracleLobRetentionClause parseLobRetentionClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.RETENTION)) {
            return null;
        }
        OracleLobRetentionClause x = new OracleLobRetentionClause();
        OracleLobRetentionClause.OptionType optionType = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.MAX)) {
            optionType = OracleLobRetentionClause.OptionType.MAX;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.MIN)) {
            optionType = OracleLobRetentionClause.OptionType.MIN;
            x.setValue(parseExpr());

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.AUTO)) {
            optionType = OracleLobRetentionClause.OptionType.AUTO;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.NONE)) {
            optionType = OracleLobRetentionClause.OptionType.NONE;
        }
        return x;
    }

    public IOracleLobDeduplicateClause parseLobDeduplicateClause() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.DEDUPLICATE)) {
            return new IOracleLobDeduplicateClause.OracleLobDeduplicateClause();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.KEEP_DUPLICATES)) {
            return new IOracleLobDeduplicateClause.OracleLobKeepDuplicatesClause();
        }
        return null;
    }

    public ISQLExpr parseLobPartitioningStorage() {
        return null;
    }


    public OracleXmlTypeColumnProperty parseXmlTypeColumnProperty() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.XMLTYPE)) {
            return null;
        }

        OracleXmlTypeColumnProperty x = new OracleXmlTypeColumnProperty();
        x.setColumn(this.acceptAndNextToken(SQLToken.TokenKind.COLUMN));
        x.setName(parseExpr());

        x.setXmlTypeStorage(parseXmlTypeStorage(false));
        x.setXmlSchemaSpec(parseXmlSchemaSpec());

        return x;
    }

    public ISQLXmlTypeStorage parseXmlTypeStorage(boolean acceptThrowException) {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.STORE, acceptThrowException)) {
            return null;
        }


        if (this.acceptAndNextToken(SQLToken.TokenKind.AS)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.OBJECT)) {
                this.acceptAndNextToken(SQLToken.TokenKind.RELATIONAL, true);
                return new ISQLXmlTypeStorage.SQLXmlTypeStorageAsObjectRelational();
            } else {

                if (this.acceptAndNextToken(SQLToken.TokenKind.SECUREFILE)) {

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.BASICFILE)) {

                }

                if (this.acceptAndNextToken(SQLToken.TokenKind.CLOB)) {
                    ISQLXmlTypeStorage.OracleSQLXmlTypeStorageAsClob x = new ISQLXmlTypeStorage.OracleSQLXmlTypeStorageAsClob();
                    if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
                        for (; ; ) {
                            if (!this.isParseLobParameter()) {
                                break;
                            }
                            x.addParameter(parseLobParameter());
                        }
                        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
                    }

                    return x;
                } else if (this.accept(SQLToken.TokenKind.BINARY)) {
                    this.acceptAndNextToken(SQLToken.TokenKind.XML, true);

                    ISQLXmlTypeStorage.OracleSQLXmlTypeStorageAsBinaryXml x = new ISQLXmlTypeStorage.OracleSQLXmlTypeStorageAsBinaryXml();
                    if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
                        for (; ; ) {
                            if (!this.isParseLobParameter()) {
                                break;
                            }
                            x.addParameter(parseLobParameter());
                        }
                        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
                    }
                    return x;

                } else {
                    throw new SQLParserException(errorInfo());
                }

            }

        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.ALL)) {
            this.acceptAndNextToken(SQLToken.TokenKind.VARRAYS, true);
            this.acceptAndNextToken(SQLToken.TokenKind.AS, true);

            ISQLXmlTypeStorage.SQLStoreAllVarraysAsType type = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.LOBS)) {
                type = ISQLXmlTypeStorage.SQLStoreAllVarraysAsType.LOBS;

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.TABLES)) {
                type = ISQLXmlTypeStorage.SQLStoreAllVarraysAsType.TABLES;

            } else {
                throw new SQLParserException(errorInfo());
            }

            return new ISQLXmlTypeStorage.OracleSQLXmlTypeStorageAsAllVarrays(type);
        }
        throw new SQLParserException(errorInfo());

    }

    public SQLXmlSchemaSpec parseXmlSchemaSpec() {
        if (!this.accept(SQLToken.TokenKind.XMLSCHEMA)
                && !this.accept(SQLToken.TokenKind.ELEMENT)) {
            return null;
        }

        SQLXmlSchemaSpec x = new SQLXmlSchemaSpec();
        if (this.acceptAndNextToken(SQLToken.TokenKind.XMLSCHEMA)) {
            x.setXmlSchemaUrl(parseExpr());
        }

        this.acceptAndNextToken(SQLToken.TokenKind.ELEMENT, true);
        ISQLExpr element = parseExpr();
        if (this.acceptAndNextToken(SQLToken.TokenKind.SHARP)) {
            x.setXmlSchemaUrl(element);
            element = parseExpr();
        }
        x.setElement(element);

        if (this.acceptAndNextToken(SQLToken.TokenKind.STORE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.ALL, true);
            this.acceptAndNextToken(SQLToken.TokenKind.VARRAYS, true);
            this.acceptAndNextToken(SQLToken.TokenKind.AS, true);

            if (this.acceptAndNextToken(SQLToken.TokenKind.LOBS)) {

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.TABLES)) {

            } else {
                throw new SQLParserException(errorInfo());
            }
        }

        for (; ; ) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.ALLOW)) {
                if (this.acceptAndNextToken(SQLToken.TokenKind.NONSCHEMA)) {

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.NONSCHEMA)) {
                    break;
                } else {
                    throw new SQLParserException(errorInfo());
                }

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.DISALLOW)) {
                if (this.acceptAndNextToken(SQLToken.TokenKind.NONSCHEMA)) {

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.NONSCHEMA)) {
                    break;
                } else {
                    throw new SQLParserException(errorInfo());
                }

            } else {
                break;
            }
        }

        return x;
    }


    public boolean isParseRowMovementClause() {
        return this.accept(SQLToken.TokenKind.ENABLE)
                || this.accept(SQLToken.TokenKind.DISABLE);
    }

    public IOracleRowMovementClause parseRowMovementClause() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.ENABLE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.ROW, true);
            this.acceptAndNextToken(SQLToken.TokenKind.MOVEMENT, true);
            return new IOracleRowMovementClause.OracleEnableRowMovementClause();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.DISABLE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.ROW, true);
            this.acceptAndNextToken(SQLToken.TokenKind.MOVEMENT, true);
            return new IOracleRowMovementClause.OracleDisableRowMovementClause();
        }
        return null;
    }

    public boolean isParseFlashbackArchiveClause() {
        return this.accept(SQLToken.TokenKind.FLASHBACK)
                || this.accept(SQLToken.TokenKind.NO);
    }

    public IOracleFlashbackArchiveClause parseFlashbackArchiveClause() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.FLASHBACK)) {
            this.acceptAndNextToken(SQLToken.TokenKind.ARCHIVE, true);
            return new IOracleFlashbackArchiveClause.SQLFlashbackArchiveClause(parseName());
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.NO)) {
            this.acceptAndNextToken(SQLToken.TokenKind.FLASHBACK, true);
            this.acceptAndNextToken(SQLToken.TokenKind.ARCHIVE, true);
            return new IOracleFlashbackArchiveClause.SQLNoFlashbackArchiveClause();
        }
        return null;
    }


    public List<ISQLExpr> parseHeapOrgTableClause() {
        List<ISQLExpr> x = new ArrayList<>();
        if (this.isParseCompression()) {
            x.add(parseCompression());
        }
        x.addAll(parseInmemoryTableClause());
        if (this.accept(SQLToken.TokenKind.ILM)) {

        }
        return x;
    }

    public List<ISQLExpr> parseIndexOrgTableClause() {
        List<ISQLExpr> x = new ArrayList<>();

        for (; ; ) {
            if (this.isParseMappingTableClause()) {
                x.add(parseMappingTableClause());

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.PCTTHRESHOLD)) {
                parseExpr();

            } else if (this.isParsePrefixCompression()) {
                x.add(parsePrefixCompression());

            } else if (this.isParseIndexOrgOverflowClause()) {
                x.add(parseIndexOrgOverflowClause());

            } else {
                break;
            }
        }

        return x;
    }


    public boolean isParseMappingTableClause() {
        return this.accept(SQLToken.TokenKind.MAPPING)
                || this.accept(SQLToken.TokenKind.NOMAPPING);
    }

    public IOracleMappingTableClause parseMappingTableClause() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.MAPPING)) {
            this.acceptAndNextToken(SQLToken.TokenKind.TABLE, true);
            return new IOracleMappingTableClause.OracleSQLMappingTableClause();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.NOMAPPING)) {
            return new IOracleMappingTableClause.OracleNoMappingTableClause();
        }
        return null;
    }


    public boolean isParseIndexOrgOverflowClause() {
        return this.accept(SQLToken.TokenKind.INCLUDING)
                || this.accept(SQLToken.TokenKind.OVERFLOW)
                || this.isParseSegmentAttribute();
    }

    public ISQLIndexOrgOverflowClause parseIndexOrgOverflowClause() {

        if (this.acceptAndNextToken(SQLToken.TokenKind.INCLUDING)) {
            return new ISQLIndexOrgOverflowClause.SQLIncludingClause(parseExpr());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.OVERFLOW)) {
            return new ISQLIndexOrgOverflowClause.SQLOverflowClause();
        }

        return parseSegmentAttribute();
    }


    public IOracleSupplementLoggingProp parseSupplementLoggingProp() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.SUPPLEMENT)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.LOG, true);

        if (this.acceptAndNextToken(SQLToken.TokenKind.GROUP)) {
            OracleSQLSupplementalLogGrpClause x = new OracleSQLSupplementalLogGrpClause();
            x.setName(parseName());

            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            for (; ; ) {
                ISQLName column = parseName();
                boolean noLog = false;
                if (this.acceptAndNextToken(SQLToken.TokenKind.NO)) {
                    this.acceptAndNextToken(SQLToken.TokenKind.LOG, true);
                    noLog = true;
                }
                OracleSQLSupplementalLogGrpClause.GroupItem item = new OracleSQLSupplementalLogGrpClause.GroupItem(column, noLog);
                x.addItem(item);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);


            x.setAlways(this.acceptAndNextToken(SQLToken.TokenKind.ALWAYS));

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.DATA)) {

            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

            for (; ; ) {
                if (this.acceptAndNextToken(SQLToken.TokenKind.ALL)) {

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.PRIMARY)) {
                    this.acceptAndNextToken(SQLToken.TokenKind.KEY, true);
                } else if (this.acceptAndNextToken(SQLToken.TokenKind.UNIQUE)) {

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.FOREIGN)) {
                    this.acceptAndNextToken(SQLToken.TokenKind.KEY, true);
                } else {
                    break;
                }
            }

            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            this.acceptAndNextToken(SQLToken.TokenKind.COLUMNS, true);
        }

        throw new SQLParserException(errorInfo());
    }


    public SQLExternalTableClause parseExternalTableClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            return null;
        }

        SQLExternalTableClause x = new SQLExternalTableClause();
        if (this.acceptAndNextToken(SQLToken.TokenKind.TYPE)) {
            x.setAccessDriverType(parseName());
        }

        x.addExternalTableDataProps(parseExternalTableDataProps());

        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        if (this.acceptAndNextToken(SQLToken.TokenKind.REJECT)) {
            this.acceptAndNextToken(SQLToken.TokenKind.LIMIT, true);
            x.setRejectLimit(parseExpr());
        }

        return x;
    }

    public List<ISQLExpr> parseExternalTableDataProps() {
        List<ISQLExpr> x = new ArrayList<>();

        if (this.accept(SQLToken.TokenKind.DEFAULT)) {
            x.add(parseDirectoryOptionExpr());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.ACCESS)) {
            x.add(parseAccessParametersClause());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.LOCAL)) {
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            for (; ; ) {
                x.add(parseExpr());
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }
        return x;
    }

    public OracleAccessParametersClause parseAccessParametersClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.ACCESS)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.PARAMETERS);

        OracleAccessParametersClause x = new OracleAccessParametersClause();

        if (this.acceptAndNextToken(SQLToken.TokenKind.USING)) {
            this.acceptAndNextToken(SQLToken.TokenKind.CLOB, true);
            x.setItem(new OracleAccessParametersClause.UsingClobClause(parseSelectQuery()));

        } else {
            x.setItem(parseExpr());
        }

        return x;
    }


    @Override
    public SQLPartitionDefinition parsePartitionDefinition() {
        return super.parsePartitionDefinition();
    }

    @Override
    public ISQLExpr parsePartitionDefinitionOption() {
        SQLLexer.SQLMake make = this.make();

        if (this.accept(SQLToken.TokenKind.SEGMENT)) {
            return parseDeferredSegmentCreation();
        }

        if (this.accept(SQLToken.TokenKind.READ)) {
            return parseReadOnlyClause();
        }
        if (this.accept(SQLToken.TokenKind.INDEXING)) {
            return parseIndexingClause();
        }
        if (this.isParseCompression()) {
            return parseCompileClause();
        }

        if (this.isParseSegmentAttribute()) {
            return parseSegmentAttribute();
        }

        if (this.isParseInMemoryClause()) {
            return parseInMemoryClause();
        }

        if (this.accept(SQLToken.TokenKind.ILM)) {

        }

        if (this.accept(SQLToken.TokenKind.OVERFLOW)) {

        }

        if (this.accept(SQLToken.TokenKind.LOB)) {
            return parseLobStorageClause();
        }

        if (this.accept(SQLToken.TokenKind.VARRAY)) {

        }

        if (this.accept(SQLToken.TokenKind.NESTED)) {

        }


        if (this.accept(SQLToken.TokenKind.DEFAULT)) {
            return parseDirectoryOptionExpr();
        }

        if (this.accept(SQLToken.TokenKind.LOCATION)) {

        }

        if (this.accept(SQLToken.TokenKind.TABLESPACE)) {
            return parseTableSpaceClause();
        }


        if (this.accept(SQLToken.TokenKind.VARRAY)) {
            parseExpr();
            this.acceptAndNextToken(SQLToken.TokenKind.STORE, true);
            this.acceptAndNextToken(SQLToken.TokenKind.AS, true);

            if (this.acceptAndNextToken(SQLToken.TokenKind.SECUREFILE)) {

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.BASICFILE)) {

            }
            this.acceptAndNextToken(SQLToken.TokenKind.LOB, true);
            parseName();
        }
        return null;
    }

    @Override
    public SQLSubPartitionDefinition parseSubPartitionDefinition() {
        return super.parseSubPartitionDefinition();
    }

    @Override
    public void parseSubPartitionDefinitionOptions(SQLSubPartitionDefinition x) {
        super.parseSubPartitionDefinitionOptions(x);
    }

    /**
     * 13.1 ACCESSIBLE BY Clause
     */
    public SQLAccessibleByClause parseAccessibleByClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.ACCESSIBLE)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.BY, true);
        SQLAccessibleByClause x = new SQLAccessibleByClause();

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            SQLAccessibleByClause.SQLAccessor accessor = parseAccessor();
            x.addAccessor(accessor);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        return x;
    }

    public SQLAccessibleByClause.SQLAccessor parseAccessor() {

        SQLAccessibleByClause.SQLUnitKind unitKind = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.FUNCTION)) {
            unitKind = SQLAccessibleByClause.SQLUnitKind.FUNCTION;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.PROCEDURE)) {
            unitKind = SQLAccessibleByClause.SQLUnitKind.PROCEDURE;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.PACKAGE)) {
            unitKind = SQLAccessibleByClause.SQLUnitKind.PACKAGE;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.TRIGGER)) {
            unitKind = SQLAccessibleByClause.SQLUnitKind.TRIGGER;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.TYPE)) {
            unitKind = SQLAccessibleByClause.SQLUnitKind.TYPE;

        }

        ISQLName name = parseName();
        return new SQLAccessibleByClause.SQLAccessor(unitKind, name);
    }


    /**
     * 13.2 AGGREGATE Clause
     */
    public SQLAggregateClause parseAggregateClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.AGGREGATE)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.USING, true);
        ISQLName name = parseName();
        return new SQLAggregateClause(name);
    }


    /**
     * 13.6 Block
     */
    public boolean isParseBlock() {
        return this.accept(SQLToken.TokenKind.LTLT)
                || this.accept(SQLToken.TokenKind.DECLARE)
                || this.accept(SQLToken.TokenKind.BEGIN);
    }

    @Override
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

        if (this.acceptAndNextToken(SQLToken.TokenKind.DECLARE)) {
            for (; ; ) {
                ISQLExpr declare = parseDeclareSection();
                if (declare == null) {
                    break;
                }
                x.addDeclare(declare);
            }
        }

        x.setBody(parseBody());
        return x;
    }

    public ISQLExpr parseDeclareSection() {
        SQLLexer.SQLMake make = this.make();

        // Type Definition
        if (this.accept(SQLToken.TokenKind.TYPE)
                || this.accept(SQLToken.TokenKind.SUBTYPE)) {
            return parseTypeDefinition();
        }

        // Cursor Declaration、Cursor Definition
        if (this.accept(SQLToken.TokenKind.CURSOR)) {
            return parseCursorDeclarationOrDefinition();
        }

        // Function Declaration、Function Definition
        if (this.accept(SQLToken.TokenKind.FUNCTION)) {
            return parseFunctionDeclarationOrDefinition();
        }
        // Procedure Declaration、Procedure Definition
        if (this.accept(SQLToken.TokenKind.PROCEDURE)) {
            return parseProcedureDeclarationOrDefinition();
        }

        // Pragma
        if (this.accept(SQLToken.TokenKind.PRAGMA)) {
            return parsePragma();
        }

        // item_declaration
        ISQLExpr expr = parseExpr();
        if (this.acceptAndNextToken(SQLToken.TokenKind.CONSTANT)) {
            this.reset(make);
            return parseConstantDeclaration();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.EXCEPTION)) {
            this.reset(make);
            return parseExceptionDeclaration();
        }

        this.reset(make);
        return parseVariableDeclaration();
    }

    @Override
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

        if (this.acceptAndNextToken(SQLToken.TokenKind.EXCEPTION)) {
            for (; ; ) {
                SQLExceptionHandler exceptionHandler = parseExceptionHandler();
                if (exceptionHandler == null) {
                    break;
                }
                x.addExceptionHandler(exceptionHandler);
            }
        }

        this.acceptAndNextToken(SQLToken.TokenKind.END, true);
        ISQLName endName = parseName();
        x.setEndName(endName);

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
        x.setAfterSemi(true);

        return x;
    }


    public ISQLTypeDefinition parseTypeDefinition() {
        SQLLexer.SQLMake make = this.make();

        if (this.acceptAndNextToken(SQLToken.TokenKind.TYPE)) {
            ISQLName name = parseName();

            if (this.acceptAndNextToken(SQLToken.TokenKind.IS)) {

                /// Collection Type Definition
                if (this.acceptAndNextToken(SQLToken.TokenKind.TABLE)) {
                    this.reset(make);
                    return parseINestedTableTypeDefinition();
                }
                if (this.acceptAndNextToken(SQLToken.TokenKind.VARRAY)) {
                    this.reset(make);
                    return parseVarrayTypeDefinition();
                }
                if (this.acceptAndNextToken(SQLToken.TokenKind.ARRAY)) {
                    this.reset(make);
                    return parseArrayTypeDefinition();
                }
                if (this.acceptAndNextToken(SQLToken.TokenKind.VARYING)) {
                    this.reset(make);
                    return parseVaryingArrayTypeDefinition();
                }

                // Record Type Definition
                if (this.acceptAndNextToken(SQLToken.TokenKind.RECORD)) {
                    this.reset(make);
                    return parseRecordTypeDefinition();
                }

                // Ref Cursor Type Definition
                if (this.acceptAndNextToken(SQLToken.TokenKind.REF)) {
                    this.reset(make);
                    return parseRefCursorTypeDefinition();
                }

                throw new SQLParserException(errorInfo());
            }

            throw new SQLParserException(errorInfo());
        }


        if (this.accept(SQLToken.TokenKind.SUBTYPE)) {
            return parseSubtypeDefinition();
        }

        this.reset(make);
        return null;
    }

    public SQLSubtypeDefinition parseSubtypeDefinition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.SUBTYPE)) {
            return null;
        }

        SQLSubtypeDefinition x = new SQLSubtypeDefinition();

        ISQLName name = parseName();
        x.setName(name);

        this.acceptAndNextToken(SQLToken.TokenKind.IS, true);

        ISQLDataType dataType = parseDataType();
        x.setDataType(dataType);

        ISQLExpr option = null;
        if (this.accept(SQLToken.TokenKind.CHARACTER)) {
            option = parseCharacterSetClause();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.RANGE)) {

            ISQLExpr lowValue = parseExpr();
            this.acceptAndNextToken(SQLToken.TokenKind.DOTDOT, true);
            ISQLExpr highValue = parseExpr();

            option = new SQLSubtypeDefinition.SQLSubtypeRangeConstraint(lowValue, highValue);

        } else {
            SQLLexer.SQLMake make = this.make();

            this.acceptAndNextToken(SQLToken.TokenKind.COMMA);

            this.reset(make);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.NOT)) {
            this.acceptAndNextToken(SQLToken.TokenKind.NULL, true);
            x.setNotNull(true);
        }

        return x;
    }

    public SQLProcedureInvocation parseProcedureInvocation() {
        if (!isIdentifier()) {
            return null;
        }
        SQLProcedureInvocation x = new SQLProcedureInvocation(parseName());
        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            x.setParen(true);
            for (; ; ) {
                ISQLExpr argument = parseExpr();
                x.addArgument(argument);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }
        return x;
    }


    /**
     * 13.7 Call Specification
     */
    public boolean isParseCallSpec() {
        return this.accept(SQLToken.TokenKind.LANGUAGE) || this.accept(SQLToken.TokenKind.EXTERNAL);
    }

    public ISQLCallSpec parseCallSpec() {
        if (!isParseCallSpec()) {
            return null;
        }

        ISQLCallSpec.AbstractSQLCDeclaration x = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.LANGUAGE)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.JAVA)) {
                this.acceptAndNextToken(SQLToken.TokenKind.NAME, true);

                return new ISQLCallSpec.SQLJavaDeclaration(parseName());

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.C)) {
                x = new ISQLCallSpec.SQLCDeclarationLanguageC();

            } else {
                throw new SQLParserException(errorInfo());
            }

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.EXTERNAL)) {
            x = new ISQLCallSpec.SQLCDeclarationExternal();
        } else {
            throw new SQLParserException(errorInfo());
        }

        for (; ; ) {
            if (!this.accept(SQLToken.TokenKind.NAME)
                    && !this.accept(SQLToken.TokenKind.LIBRARY)) {
                break;
            }
            ISQLExpr name = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.NAME)) {
                name = new ISQLCallSpec.SQLNameExpr(parseName());

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.LIBRARY)) {
                name = new ISQLCallSpec.SQLLibraryExpr(parseName());

            }
            x.addName(name);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.AGENT)) {
            this.acceptAndNextToken(SQLToken.TokenKind.IN, true);
            for (; ; ) {
                x.addAgentInArgument(parseExpr());
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.WITH)) {
            this.acceptAndNextToken(SQLToken.TokenKind.CONTEXT, true);
            x.setWithContext(true);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.PARAMETERS)) {
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            for (; ; ) {

                ISQLCallSpec.SQLExternalParameter parameter = new ISQLCallSpec.SQLExternalParameter();

                ISQLName name = null;
                if (this.accept(SQLToken.TokenKind.CONTEXT)
                        || this.accept(SQLToken.TokenKind.SELF)
                        || this.accept(SQLToken.TokenKind.RETURN)) {
                    name = new SQLUnquotedIdentifier(getStringValue());

                } else {
                    name = parseName();
                }
                parameter.setName(name);

                ISQLCallSpec.SQLExternalParameterProperty property = null;
                if (this.acceptAndNextToken(SQLToken.TokenKind.TDO)) {
                    property = ISQLCallSpec.SQLExternalParameterProperty.TDO;

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.INDICATOR)) {
                    property = ISQLCallSpec.SQLExternalParameterProperty.INDICATOR;

                    if (this.acceptAndNextToken(SQLToken.TokenKind.STRUCT)) {
                        property = ISQLCallSpec.SQLExternalParameterProperty.INDICATOR_STRUCT;
                    } else if (this.acceptAndNextToken(SQLToken.TokenKind.TDO)) {
                        property = ISQLCallSpec.SQLExternalParameterProperty.INDICATOR_TDO;
                    }


                } else if (this.acceptAndNextToken(SQLToken.TokenKind.LENGTH)) {
                    property = ISQLCallSpec.SQLExternalParameterProperty.LENGTH;

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.DURATION)) {
                    property = ISQLCallSpec.SQLExternalParameterProperty.DURATION;

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.MAXLEN)) {
                    property = ISQLCallSpec.SQLExternalParameterProperty.MAXLEN;

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.CHARSETID)) {
                    property = ISQLCallSpec.SQLExternalParameterProperty.CHARSETID;

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.CHARSETFORM)) {
                    property = ISQLCallSpec.SQLExternalParameterProperty.CHARSETFORM;

                }
                parameter.setProperty(property);

                if (this.acceptAndNextToken(SQLToken.TokenKind.BY)) {
                    this.acceptAndNextToken(SQLToken.TokenKind.REFERENCE, true);
                    parameter.setByReference(true);
                }

                parameter.setDataType(parseDataType());


                x.addParameter(parameter);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }

            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }


        return x;
    }

    /**
     * 13.10 Collection Method Invocation
     */
    public boolean isCollectionMethodInvocationName() {
        if (this.accept(SQLToken.TokenKind.COUNT)
                || this.accept(SQLToken.TokenKind.DELETE)
                || this.accept(SQLToken.TokenKind.EXISTS)
                || this.accept(SQLToken.TokenKind.EXTEND)
                || this.accept(SQLToken.TokenKind.FIRST)
                || this.accept(SQLToken.TokenKind.LAST)
                || this.accept(SQLToken.TokenKind.LIMIT)
                || this.accept(SQLToken.TokenKind.NEXT)
                || this.accept(SQLToken.TokenKind.PRIOR)
                || this.accept(SQLToken.TokenKind.TRIM)) {
            return true;
        }
        return false;
    }

    public SQLMethodInvocation parseCollectionMethodInvocation() {
        SQLLexer.SQLMake make = this.make();

        ISQLIdentifier owner = parseIdentifier();
        if (this.acceptAndNextToken(DOT)) {

            if (!isCollectionMethodInvocationName()) {
                this.reset(make);
                return null;
            }
            ISQLIdentifier name = parseIdentifier();

            SQLPropertyExpr fullNameExpr = new SQLPropertyExpr(owner, name);

            SQLMethodInvocation methodInvocation = new SQLMethodInvocation(fullNameExpr);

            if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
                for (; ; ) {
                    ISQLExpr argument = parseExpr();
                    methodInvocation.addArgument(argument);
                    if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                        break;
                    }
                }
                this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            }

            return methodInvocation;
        }

        this.reset(make);
        return null;
    }

    /**
     * 13.11 Collection Variable Declaration
     */
    public ISQLCollectionTypeDefinition.AbstractSQLNestedTableTypeDefinition parseINestedTableTypeDefinition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.TYPE)) {
            return null;
        }

        ISQLCollectionTypeDefinition.AbstractSQLNestedTableTypeDefinition x = null;

        ISQLName name = parseName();

        this.acceptAndNextToken(SQLToken.TokenKind.IS, true);
        this.acceptAndNextToken(SQLToken.TokenKind.TABLE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.OF, true);

        ISQLDataType dataType = parseDataType();

        boolean notNull = false;
        if (this.acceptAndNextToken(SQLToken.TokenKind.NOT)) {
            this.acceptAndNextToken(SQLToken.TokenKind.NULL, true);
            notNull = true;
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.INDEX)) {
            this.acceptAndNextToken(SQLToken.TokenKind.BY, true);

            ISQLDataType indexByDataType = parseDataType();

            this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
            return new ISQLCollectionTypeDefinition.SQLAssocArrayTypeDefinition(name, dataType, notNull, indexByDataType);
        }

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
        return new ISQLCollectionTypeDefinition.SQLNestedTableTypeDefinition(name, dataType, notNull);
    }

    public ISQLCollectionTypeDefinition.SQLVarrayTypeDefinition parseVarrayTypeDefinition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.TYPE)) {
            return null;
        }

        ISQLName name = parseName();

        this.acceptAndNextToken(SQLToken.TokenKind.IS, true);
        this.acceptAndNextToken(SQLToken.TokenKind.VARRAY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        ISQLExpr size = parseExpr();
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        this.acceptAndNextToken(SQLToken.TokenKind.OF, true);

        ISQLDataType dataType = parseDataType();

        boolean notNull = false;
        if (this.acceptAndNextToken(SQLToken.TokenKind.NOT)) {
            this.acceptAndNextToken(SQLToken.TokenKind.NULL, true);
            notNull = true;
        }

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);

        return new ISQLCollectionTypeDefinition.SQLVarrayTypeDefinition(name, size, dataType, notNull);
    }

    public ISQLCollectionTypeDefinition.SQLArrayTypeDefinition parseArrayTypeDefinition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.TYPE)) {
            return null;
        }
        ISQLName name = parseName();

        this.acceptAndNextToken(SQLToken.TokenKind.IS, true);
        this.acceptAndNextToken(SQLToken.TokenKind.ARRAY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        ISQLExpr size = parseExpr();
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        this.acceptAndNextToken(SQLToken.TokenKind.OF, true);

        ISQLDataType dataType = parseDataType();

        boolean notNull = false;
        if (this.acceptAndNextToken(SQLToken.TokenKind.NOT)) {
            this.acceptAndNextToken(SQLToken.TokenKind.NULL, true);
            notNull = true;
        }

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);

        return new ISQLCollectionTypeDefinition.SQLArrayTypeDefinition(name, size, dataType, notNull);
    }

    public ISQLCollectionTypeDefinition.SQLVaryingArrayTypeDefinition parseVaryingArrayTypeDefinition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.TYPE)) {
            return null;
        }
        ISQLName name = parseName();

        this.acceptAndNextToken(SQLToken.TokenKind.IS, true);
        this.acceptAndNextToken(SQLToken.TokenKind.VARYING, true);
        this.acceptAndNextToken(SQLToken.TokenKind.ARRAY, true);
        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        ISQLExpr size = parseExpr();
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        this.acceptAndNextToken(SQLToken.TokenKind.OF, true);

        ISQLDataType dataType = parseDataType();

        boolean notNull = false;
        if (this.acceptAndNextToken(SQLToken.TokenKind.NOT)) {
            this.acceptAndNextToken(SQLToken.TokenKind.NULL, true);
            notNull = true;
        }

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
        return new ISQLCollectionTypeDefinition.SQLVaryingArrayTypeDefinition(name, size, dataType, notNull);
    }


    /**
     * 13.13 COMPILE Clause
     * COMPILE [ DEBUG ] [ PACKAGE | SPECIFICATION | BODY ] [ compiler_parameters_clause ... ] [ REUSE SETTINGS ]
     */
    public SQLCompileClause parseCompileClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.COMPILE)) {
            return null;
        }

        SQLCompileClause x = new SQLCompileClause();
        if (this.acceptAndNextToken(SQLToken.TokenKind.DEBUG)) {
            x.setDebug(true);
        }

        SQLCompileClause.SQLCompiler compiler = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.PACKAGE)) {
            compiler = SQLCompileClause.SQLCompiler.PACKAGE;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.SPECIFICATION)) {
            compiler = SQLCompileClause.SQLCompiler.SPECIFICATION;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.BODY)) {
            compiler = SQLCompileClause.SQLCompiler.BODY;

        }
        x.setCompiler(compiler);

        for (; ; ) {
            if (this.accept(SQLToken.TokenKind.REUSE)) {
                break;
            }
            SQLLexer.SQLMake make = this.make();
            ISQLName name = parseName();
            boolean eq = this.acceptAndNextToken(SQLToken.TokenKind.EQ);
            if (!eq) {
                this.reset(make);
                break;
            }
            ISQLExpr value = parseExpr();

            SQLCompileClause.SQLParameter parameter = new SQLCompileClause.SQLParameter(name, value);
            x.addParameter(parameter);
        }


        if (this.acceptAndNextToken(SQLToken.TokenKind.REUSE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.SETTINGS, true);
        }

        return x;
    }


    /**
     * 13.14 Constant Declaration
     */
    public SQLConstantDeclaration parseConstantDeclaration() {
        SQLLexer.SQLMake make = this.make();

        ISQLName name = parseName();
        if (!this.acceptAndNextToken(SQLToken.TokenKind.CONSTANT)) {
            this.reset(make);
            return null;
        }

        ISQLDataType dataType = parseDataType();

        boolean notNull = false;
        if (this.acceptAndNextToken(SQLToken.TokenKind.NOT)) {
            this.acceptAndNextToken(SQLToken.TokenKind.NULL, true);
            notNull = true;
        }

        SQLDefaultClause defaultClause = (SQLDefaultClause) parseDefaultClause();
        SQLConstantDeclaration x = new SQLConstantDeclaration(name, dataType, notNull, defaultClause);

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
        x.setAfterSemi(true);

        return x;
    }


    /**
     * 13.18 Cursor Variable Declaration
     */
    public SQLRefCursorTypeDefinition parseRefCursorTypeDefinition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.TYPE)) {
            return null;
        }

        ISQLName name = parseName();
        this.acceptAndNextToken(SQLToken.TokenKind.IS, true);
        this.acceptAndNextToken(SQLToken.TokenKind.REF, true);
        this.acceptAndNextToken(SQLToken.TokenKind.CURSOR, true);


        ISQLDataType returnDataType = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.RETURN)) {
            returnDataType = parseDataType();
        }
        SQLRefCursorTypeDefinition x = new SQLRefCursorTypeDefinition(name, returnDataType);

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
        x.setAfterSemi(true);

        return x;
    }


    /**
     * 13.23 DETERMINISTIC Clause
     */
    public SQLDeterministicClause parseDeterministicClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.DETERMINISTIC)) {
            return null;
        }
        return new SQLDeterministicClause();
    }

    /**
     * 13.24 Element Specification
     */
    public boolean isParseElementSpec() {
        if (this.accept(SQLToken.TokenKind.OVERRIDING)
                || this.accept(SQLToken.TokenKind.FINAL)
                || this.accept(SQLToken.TokenKind.INSTANTIABLE)
                || this.accept(SQLToken.TokenKind.NOT)
                || isParseSubProgramSpec()
                || isParseConstructorSpec()
                || isParseMapOrderFunctionSpec()) {

            return true;
        }
        return false;
    }

    public SQLElementSpec parseElementSpec() {
        SQLElementSpec.SQLInheritanceType type = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.OVERRIDING)) {
            type = SQLElementSpec.SQLInheritanceType.OVERRIDING;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.FINAL)) {
            type = SQLElementSpec.SQLInheritanceType.FINAL;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.INSTANTIABLE)) {
            type = SQLElementSpec.SQLInheritanceType.INSTANTIABLE;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.NOT)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.OVERRIDING)) {
                type = SQLElementSpec.SQLInheritanceType.NOT_OVERRIDING;

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.FINAL)) {
                type = SQLElementSpec.SQLInheritanceType.NOT_FINAL;

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.INSTANTIABLE)) {
                type = SQLElementSpec.SQLInheritanceType.NOT_INSTANTIABLE;

            } else {
                throw new SQLParserException();
            }

        }

        SQLElementSpec x = new SQLElementSpec();

        for (; ; ) {
            ISQLExpr item = null;

            if (this.accept(SQLToken.TokenKind.MEMBER)
                    || this.accept(SQLToken.TokenKind.STATIC)) {
                item = parseSubProgramSpec();

            } else if (this.accept(SQLToken.TokenKind.FINAL)
                    || this.accept(SQLToken.TokenKind.INSTANTIABLE)
                    || this.accept(SQLToken.TokenKind.CONSTRUCTOR)) {
                item = parseConstructorSpec();

            } else if (this.accept(SQLToken.TokenKind.MAP)
                    || this.accept(SQLToken.TokenKind.ORDER)) {
                item = parseMapOrderFunctionSpec();

            }
            x.addItem(item);
            if (item == null
                    || this.accept(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
            x.setParent(parsePragma());
        }


        return x;
    }

    public boolean isParseSubProgramSpec() {
        if (this.accept(SQLToken.TokenKind.MEMBER)
                || this.accept(SQLToken.TokenKind.STATIC)) {
            return true;
        }
        return false;
    }

    public SQLElementSpec.SQLSubProgramSpec parseSubProgramSpec() {
        if (!this.accept(SQLToken.TokenKind.MEMBER)
                && !this.accept(SQLToken.TokenKind.STATIC)) {
            return null;
        }

        SQLElementSpec.SQLSubProgramSpecType type = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.MEMBER)) {
            type = SQLElementSpec.SQLSubProgramSpecType.MEMBER;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.STATIC)) {
            type = SQLElementSpec.SQLSubProgramSpecType.STATIC;

        }

        SQLElementSpec.SQLSubProgramSpec x = new SQLElementSpec.SQLSubProgramSpec();
        x.setType(type);

        ISQLExpr spec = null;
        if (this.accept(SQLToken.TokenKind.PROCEDURE)) {
            spec = parseProcedureSpec();

        } else if (this.accept(SQLToken.TokenKind.FUNCTION)) {
            spec = parseFunctionSpec();

        } else {
            throw new SQLParserException();
        }
        x.setSpec(spec);


        return x;
    }

    public SQLElementSpec.SQLProcedureSpec parseProcedureSpec() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.PROCEDURE)) {
            return null;
        }
        SQLElementSpec.SQLProcedureSpec x = new SQLElementSpec.SQLProcedureSpec();
        x.setName(parseName());

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            SQLParameterDeclaration parameter = parseParameterDeclaration();
            x.addParameter(parameter);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        SQLASType as = parseAsType(false);
        if (as == null) {
            return x;
        }
        x.setAs(as);
        x.setCallSpec(parseCallSpec());

        return x;
    }

    public SQLElementSpec.SQLFunctionSpec parseFunctionSpec() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.FUNCTION)) {
            return null;
        }
        SQLElementSpec.SQLFunctionSpec x = new SQLElementSpec.SQLFunctionSpec();
        x.setName(parseName());

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            SQLParameterDeclaration parameter = parseParameterDeclaration();
            x.addParameter(parameter);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        this.acceptAndNextToken(SQLToken.TokenKind.RETURN, true);
        x.setReturnDataType(parseDataType());

        SQLASType as = parseAsType(false);
        if (as == null) {
            return x;
        }
        x.setAs(as);
        x.setCallSpec(parseCallSpec());


        return x;
    }

    public boolean isParseConstructorSpec() {
        if (this.accept(SQLToken.TokenKind.FINAL)
                || this.accept(SQLToken.TokenKind.INSTANTIABLE)
                || this.accept(SQLToken.TokenKind.CONSTRUCTOR)) {
            return true;
        }
        return false;
    }

    public SQLElementSpec.SQLConstructorSpec parseConstructorSpec() {
        SQLLexer.SQLMake make = this.make();
        boolean final_ = this.acceptAndNextToken(SQLToken.TokenKind.FINAL);
        boolean instantiable = this.acceptAndNextToken(SQLToken.TokenKind.INSTANTIABLE);

        if (!this.acceptAndNextToken(SQLToken.TokenKind.CONSTRUCTOR)) {
            this.reset(make);
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.FUNCTION, true);

        SQLElementSpec.SQLConstructorSpec x = new SQLElementSpec.SQLConstructorSpec();
        x.setFinal_(final_);
        x.setInstantiable(instantiable);
        x.setDataType(parseDataType());

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.SELF)) {
                this.acceptAndNextToken(SQLToken.TokenKind.IN, true);
                this.acceptAndNextToken(SQLToken.TokenKind.OUT, true);
                x.setSelfInOutDataType(parseDataType());
            }
            for (; ; ) {
                SQLParameterDeclaration parameter = parseParameterDeclaration();
                x.addParameter(parameter);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }

            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        this.acceptAndNextToken(SQLToken.TokenKind.RETURN, true);
        this.acceptAndNextToken(SQLToken.TokenKind.SELF, true);
        this.acceptAndNextToken(SQLToken.TokenKind.AS, true);
        this.acceptAndNextToken(SQLToken.TokenKind.RESULT, true);

        SQLASType as = parseAsType(false);
        if (as == null) {
            return x;
        }
        x.setAs(as);
        x.setCallSpec(parseCallSpec());

        return x;
    }

    public boolean isParseMapOrderFunctionSpec() {
        if (this.accept(SQLToken.TokenKind.MAP)
                || this.accept(SQLToken.TokenKind.ORDER)) {
            return true;
        }
        return false;
    }

    public SQLElementSpec.SQLMapOrderFunctionSpec parseMapOrderFunctionSpec() {
        if (!this.accept(SQLToken.TokenKind.MAP)
                && !this.accept(SQLToken.TokenKind.ORDER)) {
            return null;
        }

        SQLElementSpec.SQLMapOrderFunctionSpec x = new SQLElementSpec.SQLMapOrderFunctionSpec();
        SQLElementSpec.SQLMapOrderFunctionSpecType type = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.MAP)) {
            type = SQLElementSpec.SQLMapOrderFunctionSpecType.MAP;
        } else if (this.acceptAndNextToken(SQLToken.TokenKind.ORDER)) {
            type = SQLElementSpec.SQLMapOrderFunctionSpecType.ORDER;
        }
        x.setType(type);

        this.acceptAndNextToken(SQLToken.TokenKind.MEMBER, true);

        SQLElementSpec.SQLFunctionSpec functionSpec = parseFunctionSpec();
        x.setFunctionSpec(functionSpec);

        return x;
    }


    /**
     * 13.26 Exception Declaration
     */
    public SQLExceptionDeclaration parseExceptionDeclaration() {
        SQLLexer.SQLMake make = this.make();

        ISQLName name = parseName();
        if (!this.acceptAndNextToken(SQLToken.TokenKind.EXCEPTION)) {
            this.reset(make);
            return null;
        }

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);

        SQLExceptionDeclaration x = new SQLExceptionDeclaration(name);
        x.setAfterSemi(true);

        return x;
    }


    /**
     * 13.27 Exception Handler
     */
    public SQLExceptionHandler parseExceptionHandler() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.WHEN)) {
            return null;
        }

        SQLExceptionHandler x = new SQLExceptionHandler();
        for (; ; ) {
            ISQLName exception = parseName();
            x.addException(exception);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.OR)) {
                break;
            }
        }

        this.acceptAndNextToken(SQLToken.TokenKind.THEN, true);
        parseLabelStatements(x.getStatements(), x);
        return x;
    }


    /**
     * 13.30 Explicit Cursor Declaration and Definition
     */
    public ISQLExpr parseCursorDeclarationOrDefinition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.CURSOR)) {
            return null;
        }

        ISQLExpr x = null;

        ISQLName name = parseName();

        List<SQLParameterDeclaration> parameters = new ArrayList<>();
        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (; ; ) {
                SQLParameterDeclaration parameter = parseParameterDeclaration();
                parameters.add(parameter);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        ISQLDataType returnDataType = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.RETURN)) {
            returnDataType = parseDataType();

            if (!this.accept(SQLToken.TokenKind.IS)) {
                this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
                x = new SQLCursorDeclaration(name, parameters, returnDataType);
                x.setAfterSemi(true);
                return x;
            }

        }

        this.acceptAndNextToken(SQLToken.TokenKind.IS, true);
        ISQLStatement selectStatement = parseSelectStatement();

        x = new SQLCursorDefinition(name, parameters, returnDataType, selectStatement);

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
        x.setAfterSemi(true);

        return x;
    }

    public SQLCursorDeclaration parseCursorDeclaration() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.CURSOR)) {
            return null;
        }

        SQLCursorDeclaration x = new SQLCursorDeclaration();

        ISQLName name = parseName();
        x.setName(name);

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (; ; ) {
                SQLParameterDeclaration parameter = parseParameterDeclaration();
                x.addParameter(parameter);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        this.acceptAndNextToken(SQLToken.TokenKind.RETURN, true);
        ISQLDataType returnDataType = parseDataType();
        x.setReturnDataType(returnDataType);

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
        x.setAfterSemi(true);

        return x;
    }

    public SQLCursorDefinition parseCursorDefinition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.CURSOR)) {
            return null;
        }
        SQLCursorDefinition x = new SQLCursorDefinition();

        ISQLName name = parseName();
        x.setName(name);

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (; ; ) {
                SQLParameterDeclaration parameter = parseParameterDeclaration();
                x.addParameter(parameter);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.RETURN)) {
            ISQLDataType returnDataType = parseDataType();
            x.setReturnDataType(returnDataType);
        }

        this.acceptAndNextToken(SQLToken.TokenKind.IS, true);

        ISQLStatement selectStatement = parseSelectStatement();
        x.setSelectStatement(selectStatement);

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
        x.setAfterSemi(true);
        return x;
    }


    /**
     * 13.35 Formal Parameter Declaration
     */
    public SQLParameterDeclaration parseParameterDeclaration() {
        SQLParameterDeclaration x = new SQLParameterDeclaration();

        ISQLName name = parseName();
        x.setName(name);

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

        if (this.acceptAndNextToken(SQLToken.TokenKind.NOCOPY)) {
            x.setNoCopy(true);
        }

        ISQLDataType dataType = parseDataType();
        x.setDataType(dataType);

        SQLDefaultClause defaultClause = (SQLDefaultClause) parseDefaultClause();
        x.setDefaultClause(defaultClause);

        return x;
    }


    /**
     * 13.36 Function Declaration and Definition
     */
    public ISQLExpr parseFunctionDeclarationOrDefinition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.FUNCTION)) {
            return null;
        }

        ISQLExpr x = null;

        ISQLName name = parseName();

        List<SQLParameterDeclaration> parameters = new ArrayList<>();
        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (; ; ) {
                SQLParameterDeclaration parameter = parseParameterDeclaration();
                parameters.add(parameter);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        ISQLDataType returnDataType = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.RETURN)) {
            returnDataType = parseDataType();
        }

        List<ISQLExpr> properties = new ArrayList<>();
        for (; ; ) {
            ISQLExpr property = parseFunctionProperty();
            if (property == null) {
                break;
            }
            properties.add(property);
        }

        if (!this.accept(SQLToken.TokenKind.IS)
                && !this.accept(SQLToken.TokenKind.AS)) {

            x = new SQLFunctionDeclaration(name, parameters, returnDataType, properties);

            this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
            x.setAfterSemi(true);

            return x;
        }

        SQLASType as = parseAsType(true);

        List<ISQLExpr> declareSections = new ArrayList<>();
        for (; ; ) {
            if (this.accept(SQLToken.TokenKind.BEGIN)
                    || isParseCallSpec()) {
                break;
            }
            ISQLExpr declareSection = parseDeclareSection();
            if (declareSection == null) {
                break;
            }
            declareSections.add(declareSection);
        }

        ISQLExpr asExpr = null;
        if (this.accept(SQLToken.TokenKind.BEGIN)) {
            asExpr = parseBody();

        } else if (this.isParseCallSpec()) {
            asExpr = parseCallSpec();

        } else {
            throw new SQLParserException();

        }

        x = new SQLFunctionDefinition(name, parameters, returnDataType, properties, as, declareSections, asExpr);

        return x;
    }

    public ISQLExpr parseFunctionProperty() {
        if (this.accept(SQLToken.TokenKind.DETERMINISTIC)) {
            return null;
        }
        if (this.accept(SQLToken.TokenKind.PIPELINED)) {
            return parsePipelinedClause();
        }
        if (this.accept(SQLToken.TokenKind.PARALLEL_ENABLE)) {
            return null;
        }
        if (this.accept(SQLToken.TokenKind.RESULT_CACHE)) {
            return null;
        }
        return null;
    }


    /**
     * 13.41 Invoker’s Rights and Definer’s Rights Clause
     */
    public SQLInvokerRightsClause parseInvokerRightsClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.AUTHID)) {
            return null;
        }

        SQLInvokerRightsClause.SQLAuthIdType authidType = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.CURRENT_USER)) {
            authidType = SQLInvokerRightsClause.SQLAuthIdType.CURRENT_USER;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.DEFINER)) {
            authidType = SQLInvokerRightsClause.SQLAuthIdType.DEFINER;

        } else {
            throw new SQLParserException();
        }

        return new SQLInvokerRightsClause(authidType);
    }

    /**
     * 13.47 PARALLEL_ENABLE Clause
     */
    public SQLParallelEnableClause parseParallelEnableClasue() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.PARALLEL_ENABLE)) {
            return null;
        }

        SQLParallelEnableClause x = new SQLParallelEnableClause();

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {

            for (; ; ) {
                SQLParallelEnableClause.ISQLArgument argument = null;
                if (this.acceptAndNextToken(SQLToken.TokenKind.PARTITION)) {
                    ISQLName name = parseName();

                    this.acceptAndNextToken(SQLToken.TokenKind.BY, true);

                    if (this.acceptAndNextToken(SQLToken.TokenKind.ANY)) {
                        argument = new SQLParallelEnableClause.SQLPartitionByAnyArgument();

                    } else if (this.acceptAndNextToken(SQLToken.TokenKind.HASH)) {
                        argument = new SQLParallelEnableClause.SQLPartitionByHashArgument();

                    } else if (this.acceptAndNextToken(SQLToken.TokenKind.RANGE)) {
                        argument = new SQLParallelEnableClause.SQLPartitionByRangeArgument();

                    } else if (this.acceptAndNextToken(SQLToken.TokenKind.VALUE)) {
                        argument = new SQLParallelEnableClause.SQLPartitionByValueArgument();

                    } else {
                        throw new SQLParserException();
                    }
                    if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
                        for (; ; ) {
                            argument.addColumn(parseName());
                            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                                break;
                            }
                        }
                        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
                    }

                    SQLParallelEnableClause.ISQLStreamingClause streamingClause = null;
                    if (this.accept(SQLToken.TokenKind.ORDER)
                            || this.accept(SQLToken.TokenKind.CLUSTER)) {

                        if (this.acceptAndNextToken(SQLToken.TokenKind.ORDER)) {
                            streamingClause = new SQLParallelEnableClause.SQLStreamingClauseByOrder();

                        } else if (this.acceptAndNextToken(SQLToken.TokenKind.CLUSTER)) {
                            streamingClause = new SQLParallelEnableClause.SQLStreamingClusterByCluster();

                        }
                        ISQLExpr expr = parseExpr();
                        streamingClause.setExpr(expr);

                        this.acceptAndNextToken(SQLToken.TokenKind.BY, true);

                        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
                        for (; ; ) {
                            streamingClause.addColumn(parseName());
                            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                                break;
                            }
                        }
                        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
                    }
                    argument.setStreamingClause(streamingClause);


                    x.addArgument(argument);

                } else {
                    throw new SQLParserException();
                }
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }

            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return x;
    }

    /**
     * 13.49 PIPELINED Clause
     */
    public ISQLPipelinedClause parsePipelinedClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.PIPELINED)) {
            return null;
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.USING)) {

            return new ISQLPipelinedClause.SQLPipelinedUsingClause();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.ROW)) {

        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.TABLE)) {

        }


        return new ISQLPipelinedClause.SQLPipelinedClause();
    }

    /**
     * 13.50 Procedure Declaration and Definition
     */
    public ISQLExpr parseProcedureDeclarationOrDefinition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.PROCEDURE)) {
            return null;
        }

        ISQLExpr x = null;

        ISQLName name = parseName();

        List<SQLParameterDeclaration> parameters = new ArrayList<>();
        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (; ; ) {
                SQLParameterDeclaration parameter = parseParameterDeclaration();
                parameters.add(parameter);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        List<ISQLExpr> propertys = new ArrayList<>();
        for (; ; ) {
            ISQLExpr property = parseProcedureProperty();
            if (property == null) {
                break;
            }
            propertys.add(property);
        }

        if (!this.accept(SQLToken.TokenKind.AS)
                && !this.accept(SQLToken.TokenKind.IS)) {

            x = new SQLProcedureDeclaration(name, parameters, propertys);

            this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
            x.setAfterSemi(true);

            return x;
        }

        SQLASType as = parseAsType(true);

        List<ISQLExpr> declareSections = new ArrayList<>();
        for (; ; ) {
            if (this.accept(SQLToken.TokenKind.BEGIN)
                    || isParseCallSpec()) {
                break;
            }
            ISQLExpr declareSection = parseDeclareSection();
            if (declareSection == null) {
                break;
            }
            declareSections.add(declareSection);
        }

        ISQLExpr asExpr = null;
        if (this.accept(SQLToken.TokenKind.BEGIN)) {
            asExpr = parseBody();

        } else if (this.isParseCallSpec()) {
            asExpr = parseCallSpec();

        } else {
            throw new SQLParserException();
        }

        x = new SQLProcedureDefinition(name, parameters, propertys, as, declareSections, asExpr);

        return x;
    }

    public ISQLExpr parseProcedureProperty() {
        if (this.accept(SQLToken.TokenKind.ACCESSIBLE)) {
            return parseAccessibleByClause();
        }

        if (this.accept(SQLToken.TokenKind.DEFAULT)) {
            return parseCollateClause();
        }

        if (this.accept(SQLToken.TokenKind.AUTHID)) {
            return parseInvokerRightsClause();
        }
        return null;
    }


    /**
     * 13.52 Record Variable Declaration
     */
    public SQLRecordTypeDefinition parseRecordTypeDefinition() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.TYPE)) {
            return null;
        }
        ISQLName name = parseName();

        this.acceptAndNextToken(SQLToken.TokenKind.IS, true);
        this.acceptAndNextToken(SQLToken.TokenKind.RECORD, true);

        SQLRecordTypeDefinition x = new SQLRecordTypeDefinition(name);

        this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
        for (; ; ) {
            SQLRecordTypeDefinition.SQLFieldDefinition field = parseRecordTypeDefinitionFieldDefinition();
            x.addField(field);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }
        this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
        x.setAfterSemi(true);

        return x;
    }

    public SQLRecordTypeDefinition.SQLFieldDefinition parseRecordTypeDefinitionFieldDefinition() {
        ISQLName name = parseName();
        ISQLDataType dataType = parseDataType();

        boolean notNull = false;
        if (this.acceptAndNextToken(SQLToken.TokenKind.NOT)) {
            this.acceptAndNextToken(SQLToken.TokenKind.NULL, true);
            notNull = true;
        }

        SQLDefaultClause defaultClause = (SQLDefaultClause) parseDefaultClause();

        return new SQLRecordTypeDefinition.SQLFieldDefinition(name, dataType, notNull, defaultClause);
    }


    /**
     * 13.56 RESULT_CACHE Clause
     */

    public ISQLResultCacheClause parseResultCacheClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.RESULT_CACHE)) {
            return null;
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.RELIES_ON)) {
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);

            ISQLResultCacheClause.SQLResultCacheReliesOnClause x = new ISQLResultCacheClause.SQLResultCacheReliesOnClause();

            for (; ; ) {
                ISQLExpr item = parseExpr();
                if (item == null) {
                    break;
                }
                x.addItem(item);
            }

            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            return x;
        }

        return new ISQLResultCacheClause.SQLResultCacheClause();
    }

    /**
     * 13.58 Scalar Variable Declaration
     */
    public SQLVariableDeclaration parseVariableDeclaration() {
        if (!this.accept(SQLToken.TokenKind.UNQUOTED_IDENTIFIER)
                && !this.accept(SQLToken.TokenKind.DOUBLE_QUOTED_IDENTIFIER)
                && !this.accept(SQLToken.TokenKind.REVERSE_QUOTED_IDENTIFIER)) {
            return null;
        }
        ISQLName name = parseName();
        ISQLDataType dataType = parseDataType();

        boolean notNull = false;
        if (this.acceptAndNextToken(SQLToken.TokenKind.NOT)) {
            this.acceptAndNextToken(SQLToken.TokenKind.NULL, true);
            notNull = true;
        }

        SQLDefaultClause defaultClause = (SQLDefaultClause) parseDefaultClause();

        SQLVariableDeclaration x = new SQLVariableDeclaration(name, dataType, notNull, defaultClause);

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
        x.setAfterSemi(true);

        return x;
    }


    public ISQLPragma parsePragma() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.PRAGMA)) {
            return null;
        }

        ISQLPragma x = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.AUTONOMOUS_TRANSACTION)) {
            x = new ISQLPragma.SQLAutonomousTransactionPragma();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.COVERAGE)) {
            x = new ISQLPragma.SQLCoveragePragma();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.DEPRECATE)) {
            x = new ISQLPragma.SQLDeprecatePragma();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.EXCEPTION_INIT)) {
            x = new ISQLPragma.SQLExceptionInitPragma();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.INLINE)) {
            x = new ISQLPragma.SQLInlinePragma();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.RESTRICT_REFERENCES)) {
            x = new ISQLPragma.SQLRestrictReferencesPragma();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.SERIALLY_REUSABLE)) {
            x = new ISQLPragma.SQLSeriallyReusablePragma();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.UDF)) {
            x = new ISQLPragma.SQLUDFPragma();

        }

        if (x == null) {
            throw new SQLParserException();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (; ; ) {
                ISQLExpr argument = parseExpr();
                x.addArgument(argument);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }

            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
        x.setAfterSemi(true);

        return x;
    }

}
