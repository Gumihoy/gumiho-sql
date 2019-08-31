package com.gumihoy.sql.basic.visitor;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.comment.SQLMinusCommentExpr;
import com.gumihoy.sql.basic.ast.expr.comment.SQLMultiLineCommentExpr;
import com.gumihoy.sql.basic.ast.expr.comment.SQLSharpCommentExpr;
import com.gumihoy.sql.basic.ast.expr.comment.hint.SQLMinusHintExpr;
import com.gumihoy.sql.basic.ast.expr.comment.hint.SQLMultiLineHintExpr;
import com.gumihoy.sql.basic.ast.expr.common.*;
import com.gumihoy.sql.basic.ast.expr.condition.*;
import com.gumihoy.sql.basic.ast.expr.correlation.SQLCorrelationNewName;
import com.gumihoy.sql.basic.ast.expr.correlation.SQLCorrelationOldName;
import com.gumihoy.sql.basic.ast.expr.correlation.SQLCorrelationParentName;
import com.gumihoy.sql.basic.ast.expr.database.SQLUpgradeDataDirectoryNameExpr;
import com.gumihoy.sql.basic.ast.expr.database.create.*;
import com.gumihoy.sql.basic.ast.expr.databaselink.SQLConnectToCurrentUserClause;
import com.gumihoy.sql.basic.ast.expr.databaselink.SQLConnectToIdentifiedByClause;
import com.gumihoy.sql.basic.ast.expr.databaselink.SQLDBLinkAuthenticationClause;
import com.gumihoy.sql.basic.ast.expr.datatype.SQLDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.any.SQLAnyDataDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.any.SQLAnyDataSetDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.any.SQLAnyTypeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.bool.SQLBoolDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.bool.SQLBooleanDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLDateDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLDateTimeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLTimeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLTimestampDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLYearDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.interval.SQLIntervalDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.media.*;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.*;
import com.gumihoy.sql.basic.ast.expr.datatype.percent.SQLPercentRowTypeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.percent.SQLPercentTypeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.reference.SQLRefCursorDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.reference.SQLRefDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.spatial.*;
import com.gumihoy.sql.basic.ast.expr.datatype.string.*;
import com.gumihoy.sql.basic.ast.expr.datatype.xml.SQLUriTypeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.xml.SQLXmlTypeDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLAllColumnExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLDBLinkExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLDoubleQuotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLPropertyExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLReverseQuotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLUnquotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.*;
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
import com.gumihoy.sql.basic.ast.expr.literal.string.SQLNQStringLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.string.SQLNStringLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.string.SQLQStringLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.string.SQLStringLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.string.SQLUStringLiteral;
import com.gumihoy.sql.basic.ast.expr.method.*;
import com.gumihoy.sql.basic.ast.expr.method.aggreate.SQLAggregateFunction;
import com.gumihoy.sql.basic.ast.expr.method.datamining.SQLDataMiningFunction;
import com.gumihoy.sql.basic.ast.expr.method.json.SQLJsonFunction;
import com.gumihoy.sql.basic.ast.expr.method.window.SQLWindowFunction;
import com.gumihoy.sql.basic.ast.expr.method.xml.SQLXmlCastFunction;
import com.gumihoy.sql.basic.ast.expr.method.xml.SQLXmlColAttValFunction;
import com.gumihoy.sql.basic.ast.expr.method.xml.SQLXmlElementFunction;
import com.gumihoy.sql.basic.ast.expr.operator.SQLBinaryOperatorExpr;
import com.gumihoy.sql.basic.ast.expr.operator.SQLUnaryOperatorExpr;
import com.gumihoy.sql.basic.ast.expr.package_.alter.ISQLAlterPackageAction;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLConnectByIsCycleExpr;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLConnectByIsLeafExpr;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLLevelExpr;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLRowIdExpr;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLRowNumExpr;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLSequenceExpr;
import com.gumihoy.sql.basic.ast.expr.pseudorecords.SQLPseudoNewRecordName;
import com.gumihoy.sql.basic.ast.expr.pseudorecords.SQLPseudoOldRecordName;
import com.gumihoy.sql.basic.ast.expr.pseudorecords.SQLPseudoParentRecordName;
import com.gumihoy.sql.basic.ast.expr.role.AbstractSQLRoleAction;
import com.gumihoy.sql.basic.ast.expr.role.SQLRoleIdentifiedByAction;
import com.gumihoy.sql.basic.ast.expr.role.SQLRoleIdentifiedExternallyAction;
import com.gumihoy.sql.basic.ast.expr.role.SQLRoleIdentifiedGloballyAction;
import com.gumihoy.sql.basic.ast.expr.role.SQLRoleIdentifiedUsingAction;
import com.gumihoy.sql.basic.ast.expr.role.SQLRoleNotIdentifiedAction;
import com.gumihoy.sql.basic.ast.expr.select.*;
import com.gumihoy.sql.basic.ast.expr.select.group.SQLGroupByClause;
import com.gumihoy.sql.basic.ast.expr.select.group.SQLHavingClause;
import com.gumihoy.sql.basic.ast.expr.select.limit.SQLLimitOffsetClause;
import com.gumihoy.sql.basic.ast.expr.select.limit.SQLOffsetFetchClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByItem;
import com.gumihoy.sql.basic.ast.expr.sequence.*;
import com.gumihoy.sql.basic.ast.expr.synonym.alter.ISQLAlterSynonymAction;
import com.gumihoy.sql.basic.ast.expr.table.ISQLEnableDisableClause;
import com.gumihoy.sql.basic.ast.expr.table.ISQLIdentityOption;
import com.gumihoy.sql.basic.ast.expr.table.alter.SQLAlterTableMoveTableAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.SQLAlterTableRenameAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.*;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.*;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.*;
import com.gumihoy.sql.basic.ast.expr.table.alter.trigger.SQLAlterTableDisableTriggerAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.trigger.SQLAlterTableEnableTriggerAction;
import com.gumihoy.sql.basic.ast.expr.table.element.AbstractSQLReferencesConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLColumnDefinition;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLLikeClause;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLVirtualColumnDefinition;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.ISQLConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.*;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.*;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLCheckTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLForeignKeyTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLPrimaryKeyTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLRefWithRowIdTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLScopeForTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLUniqueTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.partition.*;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionByHash;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionByKey;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionByList;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionByRange;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionDefinition;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubpartitionTemplate;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValues;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValuesIn;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValuesLessThan;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValuesLessThanMaxValue;
import com.gumihoy.sql.basic.ast.expr.table.partitionset.SQLPartitionsetByList;
import com.gumihoy.sql.basic.ast.expr.table.partitionset.SQLPartitionsetByRange;
import com.gumihoy.sql.basic.ast.expr.table.partitionset.SQLPartitionsetDefinition;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerCompoundTriggerBlock;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerDDLEvent;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerDMLEvent;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerDatabaseEvent;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerReferencingClause;
import com.gumihoy.sql.basic.ast.expr.trigger.alter.ISQLAlterTriggerAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.AbstractSQLAlterTypeAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.ISQLAlterTypeAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.SQLAlterTypeAddAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.SQLAlterTypeDropAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.SQLAlterTypeModifyAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.SQLAlterTypeReplaceAction;
import com.gumihoy.sql.basic.ast.expr.view.alter.*;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.*;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLAlterDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLCreateDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLDropDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.databaselink.SQLAlterDatabaseLinkStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.databaselink.SQLCreateDatabaseLinkStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.databaselink.SQLDropDatabaseLinkStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.domain.SQLAlterDomainStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.domain.SQLCreateDomainStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.domain.SQLDropDomainStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.event.SQLAlterEventStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.event.SQLCreateEventStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.event.SQLDropEventStatement;
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
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLRenameTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLTruncateTableStatement;
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
import com.gumihoy.sql.basic.ast.statement.dml.*;
import com.gumihoy.sql.basic.ast.statement.fcl.*;

/**
 * @author kent on 2019-06-14.
 */
public class SQLASTVisitorAdapter implements ISQLASTVisitor {

    @Override
    public void preVisit(ISQLObject x) {

    }

    @Override
    public void postVisit(ISQLObject x) {

    }


    // ---------------------------- Comment Start ------------------------------------

    @Override
    public boolean visit(SQLMultiLineCommentExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLMinusCommentExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLSharpCommentExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLMultiLineHintExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLMinusHintExpr x) {
        return true;
    }

    // ---------------------------- Comment End ------------------------------------
    
    


    // ---------------------------- Literal Start ------------------------------------

    @Override
    public boolean visit(SQLNQStringLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLNStringLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLQStringLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLStringLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLUStringLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLBinaryDoubleLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLBinaryFloatLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLDecimalLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLFloatingPointLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLIntegerLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLBitValueLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLHexaDecimalLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLDateLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLTimeLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLTimestampLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLIntervalLiteral x) {
        return true;
    }

    @Override
    public boolean visit(SQLBooleanLiteral x) {
        return true;
    }

    // ---------------------------- Literal End ------------------------------------


    // ---------------------------- Identifier Start ------------------------------------

    @Override
    public boolean visit(SQLUnquotedIdentifier x) {
        return true;
    }

    @Override
    public boolean visit(SQLDoubleQuotedIdentifier x) {
        return true;
    }

    @Override
    public boolean visit(SQLReverseQuotedIdentifier x) {
        return true;
    }

    @Override
    public boolean visit(SQLPropertyExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLAllColumnExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLDBLinkExpr x) {
        return true;
    }

    // ---------------------------- Identifier End ------------------------------------



    // ---------------------------- Correlation Start ------------------------------------

    @Override
    public boolean visit(SQLCorrelationNewName x) {
        return false;
    }

    @Override
    public boolean visit(SQLCorrelationOldName x) {
        return false;
    }

    @Override
    public boolean visit(SQLCorrelationParentName x) {
        return false;
    }

    // ---------------------------- Correlation End ------------------------------------

    // ---------------------------- PseudoRecord Start ------------------------------------

    @Override
    public boolean visit(SQLPseudoNewRecordName x) {
        return false;
    }

    @Override
    public boolean visit(SQLPseudoOldRecordName x) {
        return false;
    }

    @Override
    public boolean visit(SQLPseudoParentRecordName x) {
        return false;
    }

    // ---------------------------- PseudoRecord End ------------------------------------


    // ---------------------------- common Start ------------------------------------

    @Override
    public boolean visit(SQLVariableExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLBindVariableExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLPlaceholderExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLWithClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLWithClause.SQLSubQueryFactoringClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLWithClause.SQLSearchClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLWithClause.SQLCycleClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLFromClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLWhereClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLWindowClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLWindowClause.SQLWindowClauseItem x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLWindowSpecification x) {
        return true;
    }

    @Override
    public boolean visit(SQLWindowFrameClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLWindowFrameClause.SQLCurrentRow x) {
        return true;
    }

    @Override
    public boolean visit(SQLWindowFrameClause.SQLWindowFramePreceding x) {
        return true;
    }

    @Override
    public boolean visit(SQLWindowFrameClause.SQLWindowFrameFollowing x) {
        return true;
    }

    @Override
    public boolean visit(SQLWindowFrameClause.SQLWindowFrameBetween x) {
        return true;
    }

    @Override
    public boolean visit(SQLCaseExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLCaseExpr.SQLCaseExprWhenItem x) {
        return true;
    }

    @Override
    public boolean visit(SQLCaseExpr.SQLCaseExprElseClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLListExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLNullExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLSelectQueryExpr x) {
        return true;
    }

    @Override
    public boolean visit(ISQLSubQueryRestrictionClause.SQLWithReadOnly x) {
        return false;
    }

    @Override
    public boolean visit(ISQLSubQueryRestrictionClause.SQLWithCheckOption x) {
        return false;
    }

    @Override
    public boolean visit(ISQLPartitionExtensionClause.SQLPartitionClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPartitionExtensionClause.SQLPartitionForClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPartitionExtensionClause.SQLSubPartitionClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPartitionExtensionClause.SQLSubPartitionForClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLCurrentOfClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLReturningClause.SQLReturnIntoClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLReturningClause.SQLReturningIntoClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLErrorLoggingClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLValuesClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLValuesClause.SQLValuesItem x) {
        return true;
    }

    @Override
    public boolean visit(SQLSetClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLAssignmentExpr x) {
        return true;
    }

    @Override
    public boolean visit(ISQLHierarchicalQueryClause.SQLHierarchicalQueryConnectByStartWithClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLHierarchicalQueryClause.SQLHierarchicalQueryStartWithConnectByClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLOuterJoinExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLSetOptionExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLCharacterSetOptionExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLCollateOptionExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlgorithmOptionExpr x) {
        return false;
    }

    @Override
    public boolean visit(SQLDefinerOptionExpr x) {
        return false;
    }

    @Override
    public boolean visit(SQLParameterDeclaration x) {
        return true;
    }

    @Override
    public boolean visit(SQLDefaultClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLDefaultOnNullClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLUsingIndexClause.SQLUsingIndexClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLUsingIndexClause.SQLCreateIndexStatementItem x) {
        return true;
    }

    @Override
    public boolean visit(ISQLUsingIndexClause.SQLUsingNoIndexClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLStoreInClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLAllocateExtentClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLDeallocateUnusedClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLFileSpecification x) {
        return true;
    }

    @Override
    public boolean visit(SQLFileSpecification.SQLAutoExtendOnClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLFileSpecification.SQLAutoExtendOffClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLFileSpecification.SQLMaxSizeClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLLoggingClause.SQLLoggingClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLLoggingClause.SQLNoLoggingClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLLoggingClause.SQLFilesystemLikeLogging x) {
        return true;
    }

    @Override
    public boolean visit(ISQLParallelClause.SQLParallelClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLParallelClause.SQLNoParallelClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPhysicalAttribute.SQLPctfree x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPhysicalAttribute.SQLPctused x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPhysicalAttribute.SQLInitrans x) {
        return true;
    }

    @Override
    public boolean visit(SQLSizeClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLStorageClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLInitialSizeClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLNextSizeClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLMinExtentsClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLMaxExtentsClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLPctIncreaseClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLFreeListsClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLFreeListGroupsClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLOptimalClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLBufferPoolClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLFlashCacheClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLEncryptClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLTableSpaceSetClause x) {
        return false;
    }


    @Override
    public boolean visit(ISQLCompression.SQLNoCompress x) {
        return false;
    }

    @Override
    public boolean visit(ISQLCompression.SQLCompress x) {
        return false;
    }

    @Override
    public boolean visit(ISQLCompression.SQLRowStoreCompress x) {
        return false;
    }

    @Override
    public boolean visit(ISQLCompression.SQLColumnStoreCompress x) {
        return false;
    }

    @Override
    public boolean visit(ISQLCompression.SQLCompressAdvanced x) {
        return false;
    }

    @Override
    public boolean visit(SQLAccessibleByClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLAccessibleByClause.SQLAccessor x) {
        return true;
    }

    @Override
    public boolean visit(SQLAggregateClause x) {
        return true;
    }


    @Override
    public boolean visit(SQLLabel x) {
        return true;
    }

    @Override
    public boolean visit(SQLBlock x) {
        return true;
    }

    @Override
    public boolean visit(SQLBody x) {
        return true;
    }

    @Override
    public boolean visit(SQLLabelStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLSubtypeDefinition x) {
        return true;
    }

    @Override
    public boolean visit(SQLSubtypeDefinition.SQLSubtypeConstraint x) {
        return true;
    }

    @Override
    public boolean visit(SQLSubtypeDefinition.SQLSubtypeRangeConstraint x) {
        return true;
    }

    @Override
    public boolean visit(SQLProcedureInvocation x) {
        return false;
    }

    @Override
    public boolean visit(ISQLCallSpec.SQLJavaDeclaration x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCallSpec.SQLCDeclarationLanguageC x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCallSpec.SQLCDeclarationExternal x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCallSpec.SQLNameExpr x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCallSpec.SQLLibraryExpr x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCallSpec.SQLExternalParameter x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCollectionTypeDefinition.SQLAssocArrayTypeDefinition x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCollectionTypeDefinition.SQLNestedTableTypeDefinition x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCollectionTypeDefinition.SQLVarrayTypeDefinition x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCollectionTypeDefinition.SQLArrayTypeDefinition x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCollectionTypeDefinition.SQLVaryingArrayTypeDefinition x) {
        return true;
    }

    @Override
    public boolean visit(SQLCompileClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLCompileClause.SQLParameter x) {
        return true;
    }

    @Override
    public boolean visit(SQLConstantDeclaration x) {
        return true;
    }

    @Override
    public boolean visit(SQLRefCursorTypeDefinition x) {
        return true;
    }

    @Override
    public boolean visit(SQLDeterministicClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLExceptionDeclaration x) {
        return true;
    }

    @Override
    public boolean visit(SQLExceptionHandler x) {
        return true;
    }

    @Override
    public boolean visit(SQLCursorDeclaration x) {
        return true;
    }

    @Override
    public boolean visit(SQLCursorDefinition x) {
        return true;
    }

    @Override
    public boolean visit(SQLFunctionDeclaration x) {
        return true;
    }

    @Override
    public boolean visit(SQLFunctionDefinition x) {
        return true;
    }

    @Override
    public boolean visit(SQLInvokerRightsClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLParallelEnableClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLParallelEnableClause.SQLPartitionByAnyArgument x) {
        return true;
    }

    @Override
    public boolean visit(SQLParallelEnableClause.SQLPartitionByHashArgument x) {
        return true;
    }

    @Override
    public boolean visit(SQLParallelEnableClause.SQLPartitionByRangeArgument x) {
        return true;
    }

    @Override
    public boolean visit(SQLParallelEnableClause.SQLPartitionByValueArgument x) {
        return true;
    }

    @Override
    public boolean visit(SQLParallelEnableClause.SQLStreamingClauseByOrder x) {
        return true;
    }

    @Override
    public boolean visit(SQLParallelEnableClause.SQLStreamingClusterByCluster x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPipelinedClause.SQLPipelinedClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPipelinedClause.SQLPipelinedUsingClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPipelinedClause.SQLPipelinedRowClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPipelinedClause.SQLPipelinedTableClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLProcedureDeclaration x) {
        return true;
    }

    @Override
    public boolean visit(SQLProcedureDefinition x) {
        return true;
    }

    @Override
    public boolean visit(SQLRecordTypeDefinition x) {
        return true;
    }

    @Override
    public boolean visit(SQLRecordTypeDefinition.SQLFieldDefinition x) {
        return true;
    }

    @Override
    public boolean visit(SQLSharingClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLVariableDeclaration x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPragma.SQLAutonomousTransactionPragma x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPragma.SQLCoveragePragma x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPragma.SQLDeprecatePragma x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPragma.SQLExceptionInitPragma x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPragma.SQLInlinePragma x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPragma.SQLRestrictReferencesPragma x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPragma.SQLSeriallyReusablePragma x) {
        return true;
    }

    @Override
    public boolean visit(ISQLPragma.SQLUDFPragma x) {
        return true;
    }

    // ---------------------------- common End ------------------------------------



    // ---------------------------- Operator Start ------------------------------------

    @Override
    public boolean visit(SQLUnaryOperatorExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLBinaryOperatorExpr x) {
        return true;
    }

    // ---------------------------- Operator End ------------------------------------



    // ---------------------------- Pseudo Columns Start ------------------------------------

    @Override
    public boolean visit(SQLConnectByIsCycleExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLConnectByIsLeafExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLLevelExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLRowIdExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLRowNumExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLSequenceExpr x) {
        return true;
    }

    // ---------------------------- Pseudo Columns End ------------------------------------
    

    // ---------------------------- Condition Start ------------------------------------

    @Override
    public boolean visit(SQLIsCondition x) {
        return true;
    }

    @Override
    public boolean visit(SQLLikeCondition x) {
        return true;
    }

    @Override
    public boolean visit(SQLRegexpCondition x) {
        return true;
    }

    @Override
    public boolean visit(SQLRlikeCondition x) {
        return true;
    }

    @Override
    public boolean visit(SQLSoundsLikeCondition x) {
        return true;
    }

    @Override
    public boolean visit(SQLBetweenCondition x) {
        return true;
    }

    @Override
    public boolean visit(SQLInCondition x) {
        return true;
    }

    @Override
    public boolean visit(SQLExistsCondition x) {
        return true;
    }

    @Override
    public boolean visit(SQLIsOfTypeCondition x) {
        return true;
    }

    @Override
    public boolean visit(SQLIsOfTypeCondition.Item x) {
        return true;
    }

    @Override
    public boolean visit(SQLEqualsPathCondition x) {
        return true;
    }

    @Override
    public boolean visit(SQLUnderPathCondition x) {
        return true;
    }

    @Override
    public boolean visit(SQLIsJsonCondition x) {
        return true;
    }

    // ---------------------------- Condition End ------------------------------------



    // ---------------------------- DataType Start ------------------------------------

    @Override
    public boolean visit(SQLStringDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLCharacterDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLCharacterVaryingDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLCharacterLargeObjectDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLCharDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLCharVaryingDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLCharLargeObjectDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLNCharDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLNVarchar2DataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLVarcharDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLVarchar2DataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLClobDataType x) {
        return true;
    }


    @Override
    public boolean visit(SQLNClobDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLNationalCharacterDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLNationalCharacterVaryingDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLNationalCharacterLargeObjectDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLNationalCharDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLNationalCharVaryingDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLNationalVarcharDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLBinaryDataType x) {
        return true;
    }


    @Override
    public boolean visit(SQLTinyBlobDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLBlobDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLMediumBlobDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLLongBlobDataType x) {
        return true;
    }


    @Override
    public boolean visit(SQLTinyTextDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLTextDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLMediumTextDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLLongTextDataType x) {
        return true;
    }


    @Override
    public boolean visit(SQLEnumDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLSetDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLLongDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLLongRawDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLRawDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLBFileDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLRowIdDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLURowIdDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLBitDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLNumericDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLNumberDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLDecDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLDecimalDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLTinyIntDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLSmallIntDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLMediumIntDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLIntDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLIntegerDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLBigIntDataType x) {
        return true;
    }


    @Override
    public boolean visit(SQLPlsIntegerDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLBinaryIntegerDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLFloatDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLRealDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLDoubleDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLDoublePrecisionDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLBinaryFloatDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLBinaryDoubleDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLBoolDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLBooleanDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLDateDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLDateTimeDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLTimeDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLTimestampDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLYearDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLIntervalDataType x) {
        return true;
    }


    @Override
    public boolean visit(SQLRefCursorDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLRefDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLAnyDataDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLAnyTypeDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLAnyDataSetDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLUriTypeDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLXmlTypeDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLGeometryCollectionDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLGeometryDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLLineStringDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLMultiLineStringDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLMultiPointDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLMultiPolygonDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLPointDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLPolygonDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSDOGeometryDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSDOGeoRasterDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSDOTopoGeometryDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLORDAudioDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLORDDicomDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLORDDocDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLORDImageDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLORDVideoDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSIAverageColorDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSIColorDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSIColorHistogramDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSIFeatureListDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSIPositionalColorDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSIStillImageDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSITextureDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLPercentTypeDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLPercentRowTypeDataType x) {
        return true;
    }

    @Override
    public boolean visit(SQLDataType x) {
        return true;
    }


    // ---------------------------- DataType End ------------------------------------



    // ---------------------------- Function Start ------------------------------------

    @Override
    public boolean visit(SQLMethodInvocation x) {
        return true;
    }

    @Override
    public boolean visit(SQLCastFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLCharFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLChrFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLCollectionMethodInvocation x) {
        return true;
    }

    @Override
    public boolean visit(SQLConvertUsingFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLCubeTableFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLExtractFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLFirstFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLLastFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLListAggFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLPositionFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLSubStrFromFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLSubStringFromFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLTranslateUsingFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLTreatFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLTrimFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLValidateConversionFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLWeightStringFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLDataMiningFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLXmlCastFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLXmlColAttValFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLXmlElementFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLJsonFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAggregateFunction x) {
        return true;
    }

    @Override
    public boolean visit(SQLWindowFunction x) {
        return true;
    }

    // ---------------------------- Function End ------------------------------------


    // ---------------------------- DDL Start ------------------------------------

    @Override
    public boolean visit(SQLCommentOnAuditPolicyStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCommentOnColumnStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCommentOnDatabaseStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCommentOnEditionStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCommentOnIndexStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCommentOnIndexTypeStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCommentOnMaterializedViewStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCommentOnMiningModelStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCommentOnOperatorStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCommentOnRoleStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCommentOnSequenceStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCommentOnServerStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCommentOnTablespaceStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCommentOnTableStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCommentOnTypeStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCommentOnViewStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateDatabaseStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterDatabaseStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropDatabaseStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateDatabaseLinkStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterDatabaseLinkStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropDatabaseLinkStatement x) {
        return true;
    }


    @Override
    public boolean visit(SQLCreateDomainStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterDomainStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropDomainStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateEventStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterEventStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropEventStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateFunctionStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterFunctionStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropFunctionStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateIndexStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterIndexStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropIndexStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateMaterializedViewStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterMaterializedViewStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropMaterializedViewStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreatePackageStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterPackageStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropPackageStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreatePackageBodyStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterPackageBodyStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropPackageBodyStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateProcedureStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterProcedureStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropProcedureStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateRoleStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterRoleStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropRoleStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateSchemaStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterSchemaStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropSchemaStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateSequenceStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterSequenceStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropSequenceStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateSynonymStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterSynonymStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropSynonymStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateTableStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropTableStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLRenameTableStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLTruncateTableStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateTriggerStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTriggerStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropTriggerStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateTypeStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTypeStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropTypeStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateTypeBodyStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTypeBodyStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropTypeBodyStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateUserStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterUserStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropUserStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCreateViewStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterViewStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropViewStatement x) {
        return true;
    }

    // ---------------------------- DDL End ------------------------------------




    // ---------------------------- DML Start ------------------------------------

    @Override
    public boolean visit(SQLCallStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDeleteStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLExplainStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLInsertStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLMultiInsertStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLLockTableStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLMergeStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLSelectStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLSelectIntoStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLUpdateStatement x) {
        return true;
    }

    // ---------------------------- DML End ------------------------------------



    // ---------------------------- FCL End ------------------------------------

    @Override
    public boolean visit(SQLAssignmentStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCaseStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCaseStatement.SQLCaseStatementWhenItem x) {
        return false;
    }

    @Override
    public boolean visit(SQLCaseStatement.SQLCaseStatementElseClause x) {
        return false;
    }

    @Override
    public boolean visit(SQLCloseStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLContinueStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLExecuteImmediateStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLExitStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLFetchStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLForAllStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLForAllStatement.SQLBoundsRangeClause x) {
        return false;
    }

    @Override
    public boolean visit(SQLForAllStatement.SQLBoundsIndicesOfClause x) {
        return false;
    }

    @Override
    public boolean visit(SQLForAllStatement.SQLBoundsValueOfClause x) {
        return false;
    }

    @Override
    public boolean visit(SQLForLoopStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLGotoStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLIfStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLIterateStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLLeaveStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLLoopStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLNullStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLOpenForStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLOpenStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLPipeRowStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLRaiseStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLRepeatStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLReturnStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLWhileLoopStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLWhileStatement x) {
        return true;
    }

    // ---------------------------- FCL End ------------------------------------








    // ---------------------------- Database expr Start ------------------------------------

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLUserSysIdentifiedByAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLUserSystemIdentifiedByAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLControlfileReuseAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLMaxDataFilesAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLMaxInstancesAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLNationalCharacterSetAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLSetDefaultTableSpaceAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLUserDataTableSpaceAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLLogFileAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLLogFileAction.SQLLogFileActionItem x) {
        return true;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLMaxLogFilesAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLMaxLogMembersAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLMaxLogHistoryAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLArchiveLogAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLNoArchiveLogAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLForceLoggingAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLSetStandbyNoLoggingAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLTablespaceClause.SQLExtentManagementLocalClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLTablespaceClause.SQLDataFileClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLTablespaceClause.SQLSysauxDataFileClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLDefaultTablespaceClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLDefaultTempTablespaceClause.SQLDefaultTemporaryTablespaceClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLDefaultTempTablespaceClause.SQLDefaultLocalTemporaryTablespaceClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLExtentManagementClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLExtentManagementClause.SQLAutoAllocateExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLExtentManagementClause.SQLUniformExpr x) {
        return true;
    }

    @Override
    public boolean visit(SQLUndoTablespaceClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLSetTimeZoneClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLEnablePluggableDatabaseClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLFileNameConvertClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLFileNameConvertClause.SQLNoneValue x) {
        return true;
    }

    @Override
    public boolean visit(SQLFileNameConvertClause.SQLValues x) {
        return true;
    }

    @Override
    public boolean visit(SQLFileNameConvertClause.SQLItem x) {
        return true;
    }

    @Override
    public boolean visit(SQLTablespaceDataFileClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLTablespaceDataFileClause.SQLSizeSizeClauseItem x) {
        return true;
    }

    @Override
    public boolean visit(ISQLUndoModeClause.SQLUndoModeOnClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLUndoModeClause.SQLUndoModeOffClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLUpgradeDataDirectoryNameExpr x) {
        return true;
    }

    // ---------------------------- Database expr End ------------------------------------


    // ---------------------------- Database Link expr Start ------------------------------------

    @Override
    public boolean visit(SQLConnectToCurrentUserClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLConnectToIdentifiedByClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLDBLinkAuthenticationClause x) {
        return true;
    }

    // ---------------------------- Database Link expr End ------------------------------------




    // ---------------------------- Index expr Start ------------------------------------

    @Override
    public boolean visit(SQLAlterIndexAddPartition x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexCoalescePartition x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexDropPartition x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifyDefaultAttributes x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifyPartition x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifyPartition.SQLParametersOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifyPartition.SQLCoalesceOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifyPartition.SQLUpdateBlockReferencesOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifyPartition.SQLUnusableOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifySubPartition x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifySubPartition.SQLUnusableOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexRenamePartition x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexRenameSubPartition x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexSplitPartition x) {
        return false;
    }


    // ---------------------------- Index expr End ------------------------------------



    // ---------------------------- Package expr Start ------------------------------------

    @Override
    public boolean visit(ISQLAlterPackageAction.SQLAlterPackageEditionAbleAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLAlterPackageAction.SQLAlterPackageNoneEditionAbleAction x) {
        return true;
    }

    // ---------------------------- Package expr End ------------------------------------


    // ---------------------------- Role expr Start ------------------------------------

    @Override
    public boolean visit(SQLRoleNotIdentifiedAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLRoleIdentifiedByAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLRoleIdentifiedUsingAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLRoleIdentifiedExternallyAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLRoleIdentifiedGloballyAction x) {
        return false;
    }

    @Override
    public boolean visit(AbstractSQLRoleAction.SQLContainerClause x) {
        return false;
    }

    // ---------------------------- Role expr End ------------------------------------


    // ---------------------------- Sequence expr Start ------------------------------------

    @Override
    public boolean visit(SQLSequenceStartWithOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceIncrementByOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceMaxValueOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceNoMaxValueOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceMinValueOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceNoMinValueOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceCycleOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceNoCycleOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceCacheOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceNoCacheOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceOrderOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceNoOrderOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceKeepOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceNoKeepOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceScaleOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceNoScaleOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceSessionOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLSequenceGlobalOption x) {
        return false;
    }

    // ---------------------------- Sequence expr End ------------------------------------


    // ---------------------------- Synonym expr Start ------------------------------------

    @Override
    public boolean visit(ISQLAlterSynonymAction.SQLAlterSynonymEditionAbleAction x) {
        return false;
    }

    @Override
    public boolean visit(ISQLAlterSynonymAction.SQLAlterSynonymNonEditionAbleAction x) {
        return false;
    }

    @Override
    public boolean visit(ISQLAlterSynonymAction.SQLAlterSynonymCompileAction x) {
        return false;
    }

    // ---------------------------- Synonym expr End ------------------------------------


    // ---------------------------- Table expr Start ------------------------------------

    @Override
    public boolean visit(SQLColumnDefinition x) {
        return true;
    }

    @Override
    public boolean visit(SQLVirtualColumnDefinition x) {
        return true;
    }

    @Override
    public boolean visit(SQLPrimaryKeyTableConstraint x) {
        return true;
    }

    @Override
    public boolean visit(SQLUniqueTableConstraint x) {
        return true;
    }

    @Override
    public boolean visit(SQLForeignKeyTableConstraint x) {
        return true;
    }

    @Override
    public boolean visit(SQLCheckTableConstraint x) {
        return true;
    }

    @Override
    public boolean visit(SQLScopeForTableConstraint x) {
        return true;
    }

    @Override
    public boolean visit(SQLRefWithRowIdTableConstraint x) {
        return true;
    }

    @Override
    public boolean visit(SQLLikeClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLNotNullColumnConstraint x) {
        return true;
    }

    @Override
    public boolean visit(SQLNullColumnConstraint x) {
        return true;
    }

    @Override
    public boolean visit(SQLUniqueColumnConstraint x) {
        return true;
    }

    @Override
    public boolean visit(SQLPrimaryKeyColumnConstraint x) {
        return true;
    }

    @Override
    public boolean visit(SQLReferencesColumnConstraint x) {
        return true;
    }

    @Override
    public boolean visit(AbstractSQLReferencesConstraint.SQLOnUpdateAction x) {
        return true;
    }

    @Override
    public boolean visit(AbstractSQLReferencesConstraint.SQLOnDeleteAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLCheckColumnConstraint x) {
        return true;
    }

    @Override
    public boolean visit(SQLScopeIsColumnConstraint x) {
        return true;
    }

    @Override
    public boolean visit(SQLWithRowIdColumnConstraint x) {
        return true;
    }

    @Override
    public boolean visit(ISQLConstraint.SQLColumn x) {
        return true;
    }

    @Override
    public boolean visit(SQLDeferrableConstraintState x) {
        return true;
    }

    @Override
    public boolean visit(SQLNotDeferrableConstraintState x) {
        return true;
    }

    @Override
    public boolean visit(SQLInitiallyImmediateConstraintState x) {
        return true;
    }

    @Override
    public boolean visit(SQLInitiallyDeferredConstraintState x) {
        return true;
    }

    @Override
    public boolean visit(SQLRelyConstraintState x) {
        return true;
    }

    @Override
    public boolean visit(SQLNoRelyConstraintState x) {
        return true;
    }

    @Override
    public boolean visit(SQLEnableConstraintState x) {
        return true;
    }

    @Override
    public boolean visit(SQLDisableConstraintState x) {
        return true;
    }

    @Override
    public boolean visit(SQLValidateConstraintState x) {
        return true;
    }

    @Override
    public boolean visit(SQLNoValidateConstraintState x) {
        return true;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityStartWithOption x) {
        return true;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLLimitValueExpr x) {
        return true;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityIncrementByOption x) {
        return true;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityMaxValueOption x) {
        return true;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityNoMaxValueOption x) {
        return true;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityMinValueOption x) {
        return true;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityNoMinValueOption x) {
        return true;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityCycleOption x) {
        return true;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityNoCycleOption x) {
        return true;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityCacheOption x) {
        return true;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityNoCacheOption x) {
        return true;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityOrderOption x) {
        return true;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityNoOrderOption x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionByRange x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionByList x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionByHash x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionByKey x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionByRangeColumns x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionByListColumns x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionByReference x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionBySystem x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionByConsistentHash x) {
        return true;
    }

    @Override
    public boolean visit(SQLSubPartitionByRange x) {
        return true;
    }

    @Override
    public boolean visit(SQLSubPartitionByList x) {
        return true;
    }

    @Override
    public boolean visit(SQLSubPartitionByHash x) {
        return true;
    }

    @Override
    public boolean visit(SQLSubPartitionByKey x) {
        return true;
    }

    @Override
    public boolean visit(SQLSubpartitionTemplate x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionDefinition x) {
        return true;
    }

    @Override
    public boolean visit(SQLSubPartitionDefinition x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionValues x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionValuesIn x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionValuesLessThan x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionValuesLessThanMaxValue x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionsetByList x) {
        return false;
    }

    @Override
    public boolean visit(SQLPartitionsetByRange x) {
        return false;
    }

    @Override
    public boolean visit(SQLPartitionsetDefinition x) {
        return false;
    }

    @Override
    public boolean visit(ISQLEnableDisableClause.SQLEnableConstraintClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLEnableDisableClause.SQLEnableUniqueClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLEnableDisableClause.SQLEnablePrimaryKeyClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLEnableDisableClause.SQLDisableConstraintClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLEnableDisableClause.SQLDisableUniqueClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLEnableDisableClause.SQLDisablePrimaryKeyClause x) {
        return true;
    }


    // ------------------------------------ Alter ------------------------------------

    @Override
    public boolean visit(SQLAlterTableRenameAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableMoveTableAction x) {
        return true;
    }


    // ------------- Column --------------


    @Override
    public boolean visit(SQLAlterTableAddColumnAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableAlterColumnAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableChangeColumnAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableOrderByColumnAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableModifyColumnAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableModifyColumnsAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableRenameColumnAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropColumnAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropColumnsAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropColumnsContinueAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropUnusedColumnsAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableSetUnusedColumnAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableSetUnusedColumnsAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableModifyCollectionRetrievalAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableModifyLobStorageAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableAlterVarrayColPropertyAction x) {
        return true;
    }

    // ------------- Period --------------
    @Override
    public boolean visit(SQLAlterTableAddPeriodAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropPeriodAction x) {
        return true;
    }


    // ------------- Constraint --------------

    @Override
    public boolean visit(SQLAlterTableAddTableConstraintAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableAlterConstraintAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableAlterIndexConstraintAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableModifyConstraintAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableModifyUniqueConstraintAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableModifyPrimaryKeyConstraintAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableRenameConstraintAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableRenameIndexConstraintAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableRenameKeyConstraintAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropConstraintAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropUniqueConstraintAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropPrimaryKeyConstraintAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropIndexConstraintAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropKeyConstraintAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableEnableKeyAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDisableKeyAction x) {
        return true;
    }


    // ------------- Partition --------------

    @Override
    public boolean visit(SQLAlterTableAddPartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableCoalescePartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableReorganizePartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableAnalyzePartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableCheckPartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableOptimizePartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableRebuildPartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableRepairPartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableMovePartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableMovePartitionForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableMoveSubPartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableMoveSubPartitionForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableModifyPartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableModifyPartitionForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableModifySubPartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableModifySubPartitionForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableRemovePartitioningAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropPartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropPartitionForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropPartitionsAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropPartitionsForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropSubPartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropSubPartitionForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropSubPartitionsAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDropSubPartitionsForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableRenamePartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableRenamePartitionForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableRenameSubPartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableRenameSubPartitionForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableTruncatePartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableTruncatePartitionForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableTruncatePartitionsAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableTruncatePartitionsForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableTruncateSubPartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableTruncateSubPartitionForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableTruncateSubPartitionsAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableTruncateSubPartitionsForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableSplitPartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableSplitPartitionForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableSplitSubPartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableSplitSubPartitionForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableMergePartitionsAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableMergePartitionsToAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableMergeSubPartitionsAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableMergeSubPartitionsToAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableExchangePartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableExchangePartitionForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableExchangeSubPartitionAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableExchangeSubPartitionForAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableSetPartitioningAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableSetIntervalAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableSetStoreInAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableSetSubPartitionTemplateAction x) {
        return true;
    }


    // ------------- Trigger --------------

    @Override
    public boolean visit(SQLAlterTableEnableTriggerAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTableDisableTriggerAction x) {
        return true;
    }


    // ---------------------------- Table expr End ------------------------------------





    // ---------------------------- Trigger expr End ------------------------------------

    @Override
    public boolean visit(SQLTriggerDatabaseEvent x) {
        return false;
    }

    @Override
    public boolean visit(SQLTriggerDDLEvent x) {
        return false;
    }

    @Override
    public boolean visit(SQLTriggerDMLEvent x) {
        return false;
    }

    @Override
    public boolean visit(SQLTriggerReferencingClause x) {
        return false;
    }

    @Override
    public boolean visit(SQLTriggerReferencingClause.SQLOldItem x) {
        return false;
    }

    @Override
    public boolean visit(SQLTriggerReferencingClause.SQLNewItem x) {
        return false;
    }

    @Override
    public boolean visit(SQLTriggerReferencingClause.SQLParentItem x) {
        return false;
    }

    @Override
    public boolean visit(SQLTriggerCompoundTriggerBlock x) {
        return false;
    }

    @Override
    public boolean visit(SQLTriggerCompoundTriggerBlock.SQLTimingPointSection x) {
        return false;
    }

    @Override
    public boolean visit(ISQLAlterTriggerAction.SQLAlterTriggerEnableAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLAlterTriggerAction.SQLAlterTriggerDisableAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLAlterTriggerAction.SQLAlterTriggerRenameToAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLAlterTriggerAction.SQLAlterTriggerEditionAbleAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLAlterTriggerAction.SQLAlterTriggerNonEditionAbleAction x) {
        return true;
    }


    // ---------------------------- Trigger expr End ------------------------------------


    // ---------------------------- Type expr End ------------------------------------

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAlterTypeEditionAbleAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAlterTypeNoneEditionAbleAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAlterTypeResetAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAlterTypeInstantiableAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAlterTypeFinalAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAlterTypeNotInstantiableAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAlterTypeNotFinalAction x) {
        return true;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAttributeClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLLimitClause x) {
        return true;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLElementTypeClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTypeReplaceAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTypeAddAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTypeDropAction x) {
        return true;
    }

    @Override
    public boolean visit(SQLAlterTypeModifyAction x) {
        return true;
    }

    @Override
    public boolean visit(AbstractSQLAlterTypeAction.SQLDependentHandlingInvalidateClause x) {
        return true;
    }

    @Override
    public boolean visit(AbstractSQLAlterTypeAction.SQLDependentHandlingCascadeClause x) {
        return true;
    }

    // ---------------------------- Type expr End ------------------------------------


    // ---------------------------- View expr Start ------------------------------------

    @Override
    public boolean visit(SQLAlterViewAddTableConstraintAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewModifyConstraintAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewDropConstraintAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewDropPrimaryKeyConstraintAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewDropUniqueConstraintAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewCompileAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewReadOnlyAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewReadWriteAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewEditionableAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewNonEditionableAction x) {
        return false;
    }

    // ---------------------------- View expr End ------------------------------------



    // ---------------------------- Insert expr Start ------------------------------------

    @Override
    public boolean visit(SQLMultiInsertStatement.SQLAllInsertClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLMultiInsertStatement.SQLConditionalInsertClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLMultiInsertStatement.SQLConditionalInsertWhenClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLMultiInsertStatement.SQLInsertIntoClauseItem x) {
        return true;
    }

    // ---------------------------- Insert expr Start ------------------------------------




    // ---------------------------- Select expr Start ------------------------------------

    @Override
    public boolean visit(SQLSelectQuery x) {
        return true;
    }

    @Override
    public boolean visit(SQLParenSelectQuery x) {
        return true;
    }

    @Override
    public boolean visit(SQLSelectUnionQuery x) {
        return true;
    }

    @Override
    public boolean visit(SQLSelectItem x) {
        return true;
    }

    @Override
    public boolean visit(SQLSelectTargetItem x) {
        return true;
    }

    @Override
    public boolean visit(SQLObjectNameTableReference x) {
        return true;
    }

    @Override
    public boolean visit(SQLSubQueryTableReference x) {
        return true;
    }

    @Override
    public boolean visit(SQLTableFunctionTableReference x) {
        return true;
    }

    @Override
    public boolean visit(SQLUnNestFunctionTableReference x) {
        return true;
    }

    @Override
    public boolean visit(SQLJoinTableReference x) {
        return true;
    }

    @Override
    public boolean visit(SQLJoinTableReference.SQLJoinOnCondition x) {
        return true;
    }

    @Override
    public boolean visit(SQLJoinTableReference.SQLJoinUsingCondition x) {
        return true;
    }

    @Override
    public boolean visit(SQLGroupByClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLGroupByClause.SQLGroupByItem x) {
        return true;
    }

    @Override
    public boolean visit(SQLHavingClause x) {
        return true;
    }


    @Override
    public boolean visit(SQLOrderByClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLOrderByItem x) {
        return true;
    }

    @Override
    public boolean visit(SQLOrderByItem.SQLASCOrderingSpecification x) {
        return true;
    }

    @Override
    public boolean visit(SQLOrderByItem.SQLDESCOrderingSpecification x) {
        return true;
    }

    @Override
    public boolean visit(SQLOrderByItem.SQLUsingCOrderingSpecification x) {
        return true;
    }

    @Override
    public boolean visit(SQLLimitOffsetClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLOffsetFetchClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLForUpdateClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLForUpdateClause.SQLForNoWaitOption x) {
        return true;
    }

    @Override
    public boolean visit(SQLForUpdateClause.SQLForSkipLockedOption x) {
        return true;
    }

    @Override
    public boolean visit(SQLForUpdateClause.SQLForWaitOption x) {
        return true;
    }

    @Override
    public boolean visit(SQLLockInShareModeClause x) {
        return true;
    }
// ---------------------------- Select expr Start ------------------------------------

}
