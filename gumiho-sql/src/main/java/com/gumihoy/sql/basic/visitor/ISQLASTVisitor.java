package com.gumihoy.sql.basic.visitor;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.comment.SQLMinusCommentExpr;
import com.gumihoy.sql.basic.ast.expr.comment.SQLMultiLineCommentExpr;
import com.gumihoy.sql.basic.ast.expr.comment.SQLSharpCommentExpr;
import com.gumihoy.sql.basic.ast.expr.comment.hint.SQLMinusHintExpr;
import com.gumihoy.sql.basic.ast.expr.comment.hint.SQLMultiLineHintExpr;
import com.gumihoy.sql.basic.ast.expr.common.*;
import com.gumihoy.sql.basic.ast.expr.condition.SQLBetweenCondition;
import com.gumihoy.sql.basic.ast.expr.condition.SQLEqualsPathCondition;
import com.gumihoy.sql.basic.ast.expr.condition.SQLExistsCondition;
import com.gumihoy.sql.basic.ast.expr.condition.SQLInCondition;
import com.gumihoy.sql.basic.ast.expr.condition.SQLIsCondition;
import com.gumihoy.sql.basic.ast.expr.condition.SQLIsJsonCondition;
import com.gumihoy.sql.basic.ast.expr.condition.SQLIsOfTypeCondition;
import com.gumihoy.sql.basic.ast.expr.condition.SQLLikeCondition;
import com.gumihoy.sql.basic.ast.expr.condition.SQLRegexpCondition;
import com.gumihoy.sql.basic.ast.expr.condition.SQLRlikeCondition;
import com.gumihoy.sql.basic.ast.expr.condition.SQLSoundsLikeCondition;
import com.gumihoy.sql.basic.ast.expr.condition.SQLUnderPathCondition;
import com.gumihoy.sql.basic.ast.expr.correlation.SQLCorrelationNewName;
import com.gumihoy.sql.basic.ast.expr.correlation.SQLCorrelationOldName;
import com.gumihoy.sql.basic.ast.expr.correlation.SQLCorrelationParentName;
import com.gumihoy.sql.basic.ast.expr.database.SQLUpgradeDataDirectoryNameExpr;
import com.gumihoy.sql.basic.ast.expr.database.create.ISQLCreateDatabaseAction;
import com.gumihoy.sql.basic.ast.expr.database.create.ISQLDatabaseLoggingClause;
import com.gumihoy.sql.basic.ast.expr.database.create.ISQLDefaultTempTablespaceClause;
import com.gumihoy.sql.basic.ast.expr.database.create.ISQLTablespaceClause;
import com.gumihoy.sql.basic.ast.expr.database.create.ISQLUndoModeClause;
import com.gumihoy.sql.basic.ast.expr.database.create.SQLDefaultTablespaceClause;
import com.gumihoy.sql.basic.ast.expr.database.create.SQLEnablePluggableDatabaseClause;
import com.gumihoy.sql.basic.ast.expr.database.create.SQLExtentManagementClause;
import com.gumihoy.sql.basic.ast.expr.database.create.SQLFileNameConvertClause;
import com.gumihoy.sql.basic.ast.expr.database.create.SQLSetTimeZoneClause;
import com.gumihoy.sql.basic.ast.expr.database.create.SQLTablespaceDataFileClause;
import com.gumihoy.sql.basic.ast.expr.database.create.SQLUndoTablespaceClause;
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
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLBigIntDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLBinaryDoubleDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLBinaryFloatDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLBinaryIntegerDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLBitDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLDecDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLDecimalDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLDoubleDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLDoublePrecisionDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLFloatDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLIntDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLIntegerDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLMediumIntDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLNumberDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLNumericDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLPlsIntegerDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLRealDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLSmallIntDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLTinyIntDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.percent.SQLPercentRowTypeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.percent.SQLPercentTypeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.reference.SQLRefCursorDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.reference.SQLRefDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.spatial.*;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLBFileDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLBinaryDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLBlobDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLCharDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLCharLargeObjectDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLCharVaryingDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLCharacterDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLCharacterLargeObjectDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLCharacterVaryingDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLClobDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLEnumDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLLongBlobDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLLongDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLLongRawDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLLongTextDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLMediumBlobDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLMediumTextDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLNCharDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLNClobDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLNVarchar2DataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLNationalCharDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLNationalCharVaryingDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLNationalCharacterDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLNationalCharacterLargeObjectDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLNationalCharacterVaryingDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLNationalVarcharDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLRawDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLRowIdDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLSetDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLStringDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLTextDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLTinyBlobDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLTinyTextDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLURowIdDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLVarchar2DataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.SQLVarcharDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.xml.SQLUriTypeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.xml.SQLXmlTypeDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLAllColumnExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLDBLinkExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLDoubleQuotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLPropertyExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLReverseQuotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLUnquotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexAddPartition;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexCoalescePartition;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexDropPartition;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexModifyDefaultAttributes;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexModifyPartition;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexModifySubPartition;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexRenamePartition;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexRenameSubPartition;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.SQLAlterIndexSplitPartition;
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
import com.gumihoy.sql.basic.ast.expr.method.SQLCastFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLCharFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLChrFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLCollectionMethodInvocation;
import com.gumihoy.sql.basic.ast.expr.method.SQLConvertUsingFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLCubeTableFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLExtractFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLFirstFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLLastFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLListAggFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLMethodInvocation;
import com.gumihoy.sql.basic.ast.expr.method.SQLPositionFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLSubStrFromFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLSubStringFromFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLTranslateUsingFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLTreatFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLTrimFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLValidateConversionFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLWeightStringFunction;
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
import com.gumihoy.sql.basic.ast.expr.select.SQLForUpdateClause;
import com.gumihoy.sql.basic.ast.expr.select.SQLJoinTableReference;
import com.gumihoy.sql.basic.ast.expr.select.SQLLockInShareModeClause;
import com.gumihoy.sql.basic.ast.expr.select.SQLObjectNameTableReference;
import com.gumihoy.sql.basic.ast.expr.select.SQLParenSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectItem;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectTargetItem;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectUnionQuery;
import com.gumihoy.sql.basic.ast.expr.select.SQLSubQueryTableReference;
import com.gumihoy.sql.basic.ast.expr.select.SQLTableFunctionTableReference;
import com.gumihoy.sql.basic.ast.expr.select.SQLUnNestFunctionTableReference;
import com.gumihoy.sql.basic.ast.expr.select.group.SQLGroupByClause;
import com.gumihoy.sql.basic.ast.expr.select.group.SQLHavingClause;
import com.gumihoy.sql.basic.ast.expr.select.limit.SQLLimitOffsetClause;
import com.gumihoy.sql.basic.ast.expr.select.limit.SQLOffsetFetchClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByItem;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceCacheOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceCycleOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceGlobalOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceIncrementByOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceKeepOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceMaxValueOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceMinValueOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceNoCacheOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceNoCycleOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceNoKeepOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceNoMaxValueOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceNoMinValueOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceNoOrderOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceNoScaleOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceOrderOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceScaleOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceSessionOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceStartWithOption;
import com.gumihoy.sql.basic.ast.expr.synonym.alter.ISQLAlterSynonymAction;
import com.gumihoy.sql.basic.ast.expr.table.ISQLEnableDisableClause;
import com.gumihoy.sql.basic.ast.expr.table.ISQLIdentityOption;
import com.gumihoy.sql.basic.ast.expr.table.alter.SQLAlterTableMoveTableAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.SQLAlterTableRenameAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableAddColumnAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableAddPeriodAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableAlterColumnAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableAlterVarrayColPropertyAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableChangeColumnAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableDropColumnAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableDropColumnsAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableDropColumnsContinueAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableDropPeriodAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableDropUnusedColumnsAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableModifyCollectionRetrievalAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableModifyColumnAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableModifyColumnsAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableModifyLobStorageAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableOrderByColumnAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableRenameColumnAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableSetUnusedColumnAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.SQLAlterTableSetUnusedColumnsAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableAddTableConstraintAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableAlterConstraintAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableAlterIndexConstraintAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableDisableKeyAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableDropConstraintAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableDropIndexConstraintAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableDropKeyConstraintAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableDropPrimaryKeyConstraintAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableDropUniqueConstraintAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableEnableKeyAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableModifyConstraintAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableModifyPrimaryKeyConstraintAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableModifyUniqueConstraintAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableRenameConstraintAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableRenameIndexConstraintAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.SQLAlterTableRenameKeyConstraintAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableAddPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableAnalyzePartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableCheckPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableCoalescePartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableDropPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableDropPartitionForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableDropPartitionsAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableDropPartitionsForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableDropSubPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableDropSubPartitionForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableDropSubPartitionsAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableDropSubPartitionsForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableExchangePartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableExchangePartitionForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableExchangeSubPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableExchangeSubPartitionForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableMergePartitionsAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableMergePartitionsToAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableMergeSubPartitionsAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableMergeSubPartitionsToAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableModifyPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableModifyPartitionForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableModifySubPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableModifySubPartitionForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableMovePartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableMovePartitionForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableMoveSubPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableMoveSubPartitionForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableOptimizePartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableRebuildPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableRemovePartitioningAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableRenamePartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableRenamePartitionForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableRenameSubPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableRenameSubPartitionForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableReorganizePartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableRepairPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableSetIntervalAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableSetPartitioningAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableSetStoreInAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableSetSubPartitionTemplateAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableSplitPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableSplitPartitionForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableSplitSubPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableSplitSubPartitionForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableTruncatePartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableTruncatePartitionForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableTruncatePartitionsAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableTruncatePartitionsForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableTruncateSubPartitionAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableTruncateSubPartitionForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableTruncateSubPartitionsAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.SQLAlterTableTruncateSubPartitionsForAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.trigger.SQLAlterTableDisableTriggerAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.trigger.SQLAlterTableEnableTriggerAction;
import com.gumihoy.sql.basic.ast.expr.table.element.AbstractSQLReferencesConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLColumnDefinition;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLLikeClause;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLVirtualColumnDefinition;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.ISQLConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.SQLCheckColumnConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.SQLNotNullColumnConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.SQLNullColumnConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.SQLPrimaryKeyColumnConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.SQLReferencesColumnConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.SQLScopeIsColumnConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.SQLUniqueColumnConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.SQLWithRowIdColumnConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.SQLDeferrableConstraintState;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.SQLDisableConstraintState;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.SQLEnableConstraintState;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.SQLInitiallyDeferredConstraintState;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.SQLInitiallyImmediateConstraintState;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.SQLNoRelyConstraintState;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.SQLNoValidateConstraintState;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.SQLNotDeferrableConstraintState;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.SQLRelyConstraintState;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.SQLValidateConstraintState;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLCheckTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLForeignKeyTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLPrimaryKeyTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLRefWithRowIdTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLScopeForTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLUniqueTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionByConsistentHash;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionByHash;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionByKey;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionByList;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionByListColumns;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionByRange;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionByRangeColumns;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionByReference;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionBySystem;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionDefinition;
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
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnAuditPolicyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnColumnStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnEditionStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnIndexTypeStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnMaterializedViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnMiningModelStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnOperatorStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnRoleStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnSequenceStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnServerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnTablespaceStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnTypeStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.SQLCommentOnViewStatement;
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
import com.gumihoy.sql.basic.ast.statement.dml.SQLCallStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLDeleteStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLExplainStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLInsertStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLLockTableStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLMergeStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLMultiInsertStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLSelectIntoStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLSelectStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLUpdateStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLAssignmentStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLCaseStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLCloseStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLContinueStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLExecuteImmediateStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLExitStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLFetchStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLForAllStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLForLoopStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLGotoStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLIfStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLIterateStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLLeaveStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLLoopStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLNullStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLOpenForStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLOpenStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLPipeRowStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLRaiseStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLRepeatStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLReturnStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLWhileLoopStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLWhileStatement;

/**
 * @author kent on 2019-06-14.
 */
public interface ISQLASTVisitor {

    void preVisit(ISQLObject x);

    void postVisit(ISQLObject x);



    // ---------------------------- Comment Start ------------------------------------
    boolean visit(SQLMultiLineCommentExpr x);
    boolean visit(SQLMinusCommentExpr x);
    boolean visit(SQLSharpCommentExpr x);

    boolean visit(SQLMultiLineHintExpr x);
    boolean visit(SQLMinusHintExpr x);
    // ---------------------------- Comment End ------------------------------------



    // ---------------------------- Literal Start ------------------------------------
    boolean visit(SQLNQStringLiteral x);
    boolean visit(SQLNStringLiteral x);
    boolean visit(SQLQStringLiteral x);
    boolean visit(SQLStringLiteral x);
    boolean visit(SQLUStringLiteral x);

    boolean visit(SQLBinaryDoubleLiteral x);
    boolean visit(SQLBinaryFloatLiteral x);
    boolean visit(SQLDecimalLiteral x);
    boolean visit(SQLFloatingPointLiteral x);
    boolean visit(SQLIntegerLiteral x);
    boolean visit(SQLBitValueLiteral x);
    boolean visit(SQLHexaDecimalLiteral x);

    boolean visit(SQLDateLiteral x);
    boolean visit(SQLTimeLiteral x);
    boolean visit(SQLTimestampLiteral x);

    boolean visit(SQLIntervalLiteral x);

    boolean visit(SQLBooleanLiteral x);
    // ---------------------------- Literal End ------------------------------------


    // ---------------------------- Identifier Start ------------------------------------
    boolean visit(SQLUnquotedIdentifier x);
    boolean visit(SQLDoubleQuotedIdentifier x);
    boolean visit(SQLReverseQuotedIdentifier x);

    boolean visit(SQLPropertyExpr x);

    boolean visit(SQLAllColumnExpr x);

    boolean visit(SQLDBLinkExpr x);
    // ---------------------------- Identifier End ------------------------------------

    // ---------------------------- Correlation Start ------------------------------------
    boolean visit(SQLCorrelationNewName x);
    boolean visit(SQLCorrelationOldName x);
    boolean visit(SQLCorrelationParentName x);
    // ---------------------------- Correlation End ------------------------------------

    // ---------------------------- PseudoRecord Start ------------------------------------
    boolean visit(SQLPseudoNewRecordName x);
    boolean visit(SQLPseudoOldRecordName x);
    boolean visit(SQLPseudoParentRecordName x);
    // ---------------------------- PseudoRecord End ------------------------------------

    // ---------------------------- Common Start ------------------------------------
    boolean visit(SQLVariableExpr x);
    boolean visit(SQLBindVariableExpr x);
    boolean visit(SQLPlaceholderExpr x);

    boolean visit(SQLWithClause x);
    boolean visit(SQLWithClause.SQLSubQueryFactoringClause x);
    boolean visit(SQLWithClause.SQLSearchClause x);
    boolean visit(SQLWithClause.SQLCycleClause x);


    boolean visit(SQLFromClause x);
    boolean visit(SQLWhereClause x);
    boolean visit(SQLWindowClause x);
    boolean visit(SQLWindowClause.SQLWindowClauseItem x);
    boolean visit(SQLPartitionClause x);
    boolean visit(SQLWindowSpecification x);
    boolean visit(SQLWindowFrameClause x);
    boolean visit(SQLWindowFrameClause.SQLCurrentRow x);
    boolean visit(SQLWindowFrameClause.SQLWindowFramePreceding x);
    boolean visit(SQLWindowFrameClause.SQLWindowFrameFollowing x);
    boolean visit(SQLWindowFrameClause.SQLWindowFrameBetween x);

    boolean visit(SQLCaseExpr x);
    boolean visit(SQLCaseExpr.SQLCaseExprWhenItem x);
    boolean visit(SQLCaseExpr.SQLCaseExprElseClause x);

    boolean visit(SQLListExpr x);
    boolean visit(SQLNullExpr x);

    boolean visit(SQLSelectQueryExpr x);
    boolean visit(ISQLSubQueryRestrictionClause.SQLWithReadOnly x);
    boolean visit(ISQLSubQueryRestrictionClause.SQLWithCheckOption x);

    boolean visit(ISQLPartitionExtensionClause.SQLPartitionClause x);
    boolean visit(ISQLPartitionExtensionClause.SQLPartitionForClause x);
    boolean visit(ISQLPartitionExtensionClause.SQLSubPartitionClause x);
    boolean visit(ISQLPartitionExtensionClause.SQLSubPartitionForClause x);

    boolean visit(SQLCurrentOfClause x);

    boolean visit(ISQLReturningClause.SQLReturnIntoClause x);
    boolean visit(ISQLReturningClause.SQLReturningIntoClause x);
    boolean visit(SQLErrorLoggingClause x);

    boolean visit(SQLValuesClause x);
    boolean visit(SQLValuesClause.SQLValuesItem x);
    boolean visit(SQLSetClause x);

    boolean visit(SQLAssignmentExpr x);


    boolean visit(ISQLHierarchicalQueryClause.SQLHierarchicalQueryConnectByStartWithClause x);
    boolean visit(ISQLHierarchicalQueryClause.SQLHierarchicalQueryStartWithConnectByClause x);
    boolean visit(SQLOuterJoinExpr x);

    boolean visit(SQLSetOptionExpr x);
    boolean visit(SQLCharacterSetOptionExpr x);
    boolean visit(SQLCollateOptionExpr x);
    boolean visit(SQLAlgorithmOptionExpr x);
    boolean visit(SQLDefinerOptionExpr x);

    boolean visit(SQLParameterDeclaration x);

    boolean visit(SQLDefaultClause x);
    boolean visit(SQLDefaultOnNullClause x);

    boolean visit(ISQLUsingIndexClause.SQLUsingIndexClause x);
    boolean visit(ISQLUsingIndexClause.SQLCreateIndexStatementItem x);
    boolean visit(ISQLUsingIndexClause.SQLUsingNoIndexClause x);

    boolean visit(SQLStoreInClause x);

    boolean visit(SQLAllocateExtentClause x);
    boolean visit(SQLDeallocateUnusedClause x);

    boolean visit(SQLFileSpecification x);
    boolean visit(SQLFileSpecification.SQLAutoExtendOnClause x);
    boolean visit(SQLFileSpecification.SQLAutoExtendOffClause x);
    boolean visit(SQLFileSpecification.SQLMaxSizeClause x);

    boolean visit(ISQLLoggingClause.SQLLoggingClause x);
    boolean visit(ISQLLoggingClause.SQLNoLoggingClause x);
    boolean visit(ISQLLoggingClause.SQLFilesystemLikeLogging x);

    boolean visit(ISQLParallelClause.SQLParallelClause x);
    boolean visit(ISQLParallelClause.SQLNoParallelClause x);

    boolean visit(ISQLPhysicalAttribute.SQLPctfree x);
    boolean visit(ISQLPhysicalAttribute.SQLPctused x);
    boolean visit(ISQLPhysicalAttribute.SQLInitrans x);

    boolean visit(SQLSizeClause x);
    boolean visit(SQLStorageClause x);
    boolean visit(SQLStorageClause.SQLInitialSizeClause x);
    boolean visit(SQLStorageClause.SQLNextSizeClause x);
    boolean visit(SQLStorageClause.SQLMinExtentsClause x);
    boolean visit(SQLStorageClause.SQLMaxExtentsClause x);
    boolean visit(SQLStorageClause.SQLPctIncreaseClause x);
    boolean visit(SQLStorageClause.SQLFreeListsClause x);
    boolean visit(SQLStorageClause.SQLFreeListGroupsClause x);
    boolean visit(SQLStorageClause.SQLOptimalClause x);
    boolean visit(SQLStorageClause.SQLBufferPoolClause x);
    boolean visit(SQLStorageClause.SQLFlashCacheClause x);
    boolean visit(SQLStorageClause.SQLEncryptClause x);


    boolean visit(SQLTableSpaceSetClause x);

    boolean visit(ISQLCompression.SQLNoCompress x);
    boolean visit(ISQLCompression.SQLCompress x);
    boolean visit(ISQLCompression.SQLRowStoreCompress x);
    boolean visit(ISQLCompression.SQLColumnStoreCompress x);
    boolean visit(ISQLCompression.SQLCompressAdvanced x);


    // 13.1 ACCESSIBLE BY Clause
    boolean visit(SQLAccessibleByClause x);
    boolean visit(SQLAccessibleByClause.SQLAccessor x);
    // 13.2 AGGREGATE Clause
    boolean visit(SQLAggregateClause x);

    // 13.6 Block
    boolean visit(SQLLabel x);
    boolean visit(SQLBlock x);
    boolean visit(SQLBody x);
    boolean visit(SQLLabelStatement x);

    boolean visit(SQLSubtypeDefinition x);
    boolean visit(SQLSubtypeDefinition.SQLSubtypeConstraint x);
    boolean visit(SQLSubtypeDefinition.SQLSubtypeRangeConstraint x);

    boolean visit(SQLProcedureInvocation x);

    // 13.7 Call Specification
    boolean visit(ISQLCallSpec.SQLJavaDeclaration x);
    boolean visit(ISQLCallSpec.SQLCDeclarationLanguageC x);
    boolean visit(ISQLCallSpec.SQLCDeclarationExternal x);
    boolean visit(ISQLCallSpec.SQLNameExpr x);
    boolean visit(ISQLCallSpec.SQLLibraryExpr x);
    boolean visit(ISQLCallSpec.SQLExternalParameter x);

    // 13.10


    // 13.11 Collection Variable Declaration
    boolean visit(ISQLCollectionTypeDefinition.SQLAssocArrayTypeDefinition x);
    boolean visit(ISQLCollectionTypeDefinition.SQLNestedTableTypeDefinition x);
    boolean visit(ISQLCollectionTypeDefinition.SQLVarrayTypeDefinition x);
    boolean visit(ISQLCollectionTypeDefinition.SQLArrayTypeDefinition x);
    boolean visit(ISQLCollectionTypeDefinition.SQLVaryingArrayTypeDefinition x);

    // 13.13 COMPILE Clause
    boolean visit(SQLCompileClause x);
    boolean visit(SQLCompileClause.SQLParameter x);

    // 13.14 Constant Declaration
    boolean visit(SQLConstantDeclaration x);

    // 13.18 Cursor Variable Declaration
    boolean visit(SQLRefCursorTypeDefinition x);

    // 13.23 DETERMINISTIC Clause
    boolean visit(SQLDeterministicClause x);

    // 13.26 Exception Declaration
    boolean visit(SQLExceptionDeclaration x);
    // 3.27 Exception Handler
    boolean visit(SQLExceptionHandler x);

    // 13.30 Explicit Cursor Declaration and Definition
    boolean visit(SQLCursorDeclaration x);
    boolean visit(SQLCursorDefinition x);

    // 13.36 Function Declaration and Definition
    boolean visit(SQLFunctionDeclaration x);
    boolean visit(SQLFunctionDefinition x);

    // 13.41 Invoker’s Rights and Definer’s Rights Clause
    boolean visit(SQLInvokerRightsClause x);

    // 13.47 PARALLEL_ENABLE Clause
    boolean visit(SQLParallelEnableClause x);
    boolean visit(SQLParallelEnableClause.SQLPartitionByAnyArgument x);
    boolean visit(SQLParallelEnableClause.SQLPartitionByHashArgument x);
    boolean visit(SQLParallelEnableClause.SQLPartitionByRangeArgument x);
    boolean visit(SQLParallelEnableClause.SQLPartitionByValueArgument x);
    boolean visit(SQLParallelEnableClause.SQLStreamingClauseByOrder x);
    boolean visit(SQLParallelEnableClause.SQLStreamingClusterByCluster x);

    // 13.49 PIPELINED Clause
    boolean visit(ISQLPipelinedClause.SQLPipelinedClause x);
    boolean visit(ISQLPipelinedClause.SQLPipelinedUsingClause x);
    boolean visit(ISQLPipelinedClause.SQLPipelinedRowClause x);
    boolean visit(ISQLPipelinedClause.SQLPipelinedTableClause x);

    // 13.50 Procedure Declaration and Definition
    boolean visit(SQLProcedureDeclaration x);
    boolean visit(SQLProcedureDefinition x);

    // 13.52 Record Variable Declaration
    boolean visit(SQLRecordTypeDefinition x);
    boolean visit(SQLRecordTypeDefinition.SQLFieldDefinition x);

    // 13.61 SHARING Clause
    boolean visit(SQLSharingClause x);

    boolean visit(SQLVariableDeclaration x);


    boolean visit(ISQLPragma.SQLAutonomousTransactionPragma x);
    boolean visit(ISQLPragma.SQLCoveragePragma x);
    boolean visit(ISQLPragma.SQLDeprecatePragma x);
    boolean visit(ISQLPragma.SQLExceptionInitPragma x);
    boolean visit(ISQLPragma.SQLInlinePragma x);
    boolean visit(ISQLPragma.SQLRestrictReferencesPragma x);
    boolean visit(ISQLPragma.SQLSeriallyReusablePragma x);
    boolean visit(ISQLPragma.SQLUDFPragma x);

    // ---------------------------- Common End ------------------------------------



    // ---------------------------- Operator Start ------------------------------------
    boolean visit(SQLUnaryOperatorExpr x);
    boolean visit(SQLBinaryOperatorExpr x);
    // ---------------------------- Operator End ------------------------------------



    // ---------------------------- Pseudo Columns Start ------------------------------------
    boolean visit(SQLConnectByIsCycleExpr x);
    boolean visit(SQLConnectByIsLeafExpr x);

    boolean visit(SQLLevelExpr x);

    boolean visit(SQLRowIdExpr x);
    boolean visit(SQLRowNumExpr x);

    boolean visit(SQLSequenceExpr x);
    // ---------------------------- Pseudo Columns End ------------------------------------



    // ---------------------------- Condition Start ------------------------------------
    boolean visit(SQLIsCondition x);
    boolean visit(SQLLikeCondition x);
    boolean visit(SQLRegexpCondition x);
    boolean visit(SQLRlikeCondition x);
    boolean visit(SQLSoundsLikeCondition x);

    boolean visit(SQLBetweenCondition x);
    boolean visit(SQLInCondition x);
    boolean visit(SQLExistsCondition x);
    boolean visit(SQLIsOfTypeCondition x);
    boolean visit(SQLIsOfTypeCondition.Item x);

    boolean visit(SQLEqualsPathCondition x);
    boolean visit(SQLUnderPathCondition x);

    boolean visit(SQLIsJsonCondition x);
    // ---------------------------- Condition End ------------------------------------



    // ---------------------------- DataType Start ------------------------------------
    boolean visit(SQLStringDataType x);

    boolean visit(SQLCharacterDataType x);
    boolean visit(SQLCharacterVaryingDataType x);
    boolean visit(SQLCharacterLargeObjectDataType x);

    boolean visit(SQLCharDataType x);
    boolean visit(SQLCharVaryingDataType x);
    boolean visit(SQLCharLargeObjectDataType x);

    boolean visit(SQLNCharDataType x);
    boolean visit(SQLNVarchar2DataType x);

    boolean visit(SQLVarcharDataType x);
    boolean visit(SQLVarchar2DataType x);

    boolean visit(SQLClobDataType x);
    boolean visit(SQLNClobDataType x);

    boolean visit(SQLNationalCharacterDataType x);
    boolean visit(SQLNationalCharacterVaryingDataType x);
    boolean visit(SQLNationalCharacterLargeObjectDataType x);
    boolean visit(SQLNationalCharDataType x);
    boolean visit(SQLNationalCharVaryingDataType x);
    boolean visit(SQLNationalVarcharDataType x);

    boolean visit(SQLBinaryDataType x);

    boolean visit(SQLTinyBlobDataType x);
    boolean visit(SQLBlobDataType x);
    boolean visit(SQLMediumBlobDataType x);
    boolean visit(SQLLongBlobDataType x);


    boolean visit(SQLTinyTextDataType x);
    boolean visit(SQLTextDataType x);
    boolean visit(SQLMediumTextDataType x);
    boolean visit(SQLLongTextDataType x);

    boolean visit(SQLEnumDataType x);
    boolean visit(SQLSetDataType x);


    boolean visit(SQLLongDataType x);
    boolean visit(SQLLongRawDataType x);
    boolean visit(SQLRawDataType x);

    boolean visit(SQLBFileDataType x);

    boolean visit(SQLRowIdDataType x);
    boolean visit(SQLURowIdDataType x);

    boolean visit(SQLBitDataType x);
    boolean visit(SQLNumericDataType x);
    boolean visit(SQLNumberDataType x);
    boolean visit(SQLDecDataType x);
    boolean visit(SQLDecimalDataType x);
    boolean visit(SQLTinyIntDataType x);
    boolean visit(SQLSmallIntDataType x);
    boolean visit(SQLMediumIntDataType x);
    boolean visit(SQLIntDataType x);
    boolean visit(SQLIntegerDataType x);
    boolean visit(SQLBigIntDataType x);

    boolean visit(SQLPlsIntegerDataType x);
    boolean visit(SQLBinaryIntegerDataType x);

    boolean visit(SQLFloatDataType x);
    boolean visit(SQLRealDataType x);
    boolean visit(SQLDoubleDataType x);
    boolean visit(SQLDoublePrecisionDataType x);
    boolean visit(SQLBinaryFloatDataType x);
    boolean visit(SQLBinaryDoubleDataType x);

    boolean visit(SQLBoolDataType x);
    boolean visit(SQLBooleanDataType x);

    boolean visit(SQLDateDataType x);
    boolean visit(SQLDateTimeDataType x);
    boolean visit(SQLTimeDataType x);
    boolean visit(SQLTimestampDataType x);
    boolean visit(SQLYearDataType x);

    boolean visit(SQLIntervalDataType x);


    boolean visit(SQLRefCursorDataType x);
    boolean visit(SQLRefDataType x);


    boolean visit(SQLAnyDataDataType x);
    boolean visit(SQLAnyTypeDataType x);
    boolean visit(SQLAnyDataSetDataType x);

    boolean visit(SQLUriTypeDataType x);
    boolean visit(SQLXmlTypeDataType x);

    boolean visit(SQLGeometryCollectionDataType x);
    boolean visit(SQLGeometryDataType x);
    boolean visit(SQLLineStringDataType x);
    boolean visit(SQLMultiLineStringDataType x);
    boolean visit(SQLMultiPointDataType x);
    boolean visit(SQLMultiPolygonDataType x);
    boolean visit(SQLPointDataType x);
    boolean visit(SQLPolygonDataType x);
    boolean visit(SQLSDOGeometryDataType x);
    boolean visit(SQLSDOGeoRasterDataType x);
    boolean visit(SQLSDOTopoGeometryDataType x);

    boolean visit(SQLORDAudioDataType x);
    boolean visit(SQLORDDicomDataType x);
    boolean visit(SQLORDDocDataType x);
    boolean visit(SQLORDImageDataType x);
    boolean visit(SQLORDVideoDataType x);
    boolean visit(SQLSIAverageColorDataType x);
    boolean visit(SQLSIColorDataType x);
    boolean visit(SQLSIColorHistogramDataType x);
    boolean visit(SQLSIFeatureListDataType x);
    boolean visit(SQLSIPositionalColorDataType x);
    boolean visit(SQLSIStillImageDataType x);
    boolean visit(SQLSITextureDataType x);

    boolean visit(SQLPercentTypeDataType x);
    boolean visit(SQLPercentRowTypeDataType x);


    boolean visit(SQLDataType x);
    // ---------------------------- DataType End ------------------------------------



    // ---------------------------- Function Start ------------------------------------
    boolean visit(SQLMethodInvocation x);

    boolean visit(SQLCastFunction x);
    boolean visit(SQLCharFunction x);
    boolean visit(SQLChrFunction x);
    boolean visit(SQLCollectionMethodInvocation x);
    boolean visit(SQLConvertUsingFunction x);
    boolean visit(SQLCubeTableFunction x);
    boolean visit(SQLExtractFunction x);
    boolean visit(SQLFirstFunction x);
    boolean visit(SQLLastFunction x);
    boolean visit(SQLListAggFunction x);
    boolean visit(SQLPositionFunction x);
    boolean visit(SQLSubStrFromFunction x);
    boolean visit(SQLSubStringFromFunction x);
    boolean visit(SQLTranslateUsingFunction x);
    boolean visit(SQLTreatFunction x);
    boolean visit(SQLTrimFunction x);
    boolean visit(SQLValidateConversionFunction x);
    boolean visit(SQLWeightStringFunction x);

    boolean visit(SQLDataMiningFunction x);

    boolean visit(SQLXmlCastFunction x);
    boolean visit(SQLXmlColAttValFunction x);
    boolean visit(SQLXmlElementFunction x);

    boolean visit(SQLJsonFunction x);
    boolean visit(SQLAggregateFunction x);
    boolean visit(SQLWindowFunction x);

    // ---------------------------- Function End ------------------------------------



    // ---------------------------- DDL Start ------------------------------------
    boolean visit(SQLCommentOnAuditPolicyStatement x);
    boolean visit(SQLCommentOnColumnStatement x);
    boolean visit(SQLCommentOnDatabaseStatement x);
    boolean visit(SQLCommentOnEditionStatement x);
    boolean visit(SQLCommentOnIndexStatement x);
    boolean visit(SQLCommentOnIndexTypeStatement x);
    boolean visit(SQLCommentOnMaterializedViewStatement x);
    boolean visit(SQLCommentOnMiningModelStatement x);
    boolean visit(SQLCommentOnOperatorStatement x);
    boolean visit(SQLCommentOnRoleStatement x);
    boolean visit(SQLCommentOnSequenceStatement x);
    boolean visit(SQLCommentOnServerStatement x);
    boolean visit(SQLCommentOnTablespaceStatement x);
    boolean visit(SQLCommentOnTableStatement x);
    boolean visit(SQLCommentOnTypeStatement x);
    boolean visit(SQLCommentOnViewStatement x);



    boolean visit(SQLCreateDatabaseStatement x);
    boolean visit(SQLAlterDatabaseStatement x);
    boolean visit(SQLDropDatabaseStatement x);

    boolean visit(SQLCreateDatabaseLinkStatement x);
    boolean visit(SQLAlterDatabaseLinkStatement x);
    boolean visit(SQLDropDatabaseLinkStatement x);


    boolean visit(SQLCreateDomainStatement x);
    boolean visit(SQLAlterDomainStatement x);
    boolean visit(SQLDropDomainStatement x);


    boolean visit(SQLCreateEventStatement x);
    boolean visit(SQLAlterEventStatement x);
    boolean visit(SQLDropEventStatement x);

    boolean visit(SQLCreateFunctionStatement x);
    boolean visit(SQLAlterFunctionStatement x);
    boolean visit(SQLDropFunctionStatement x);

    boolean visit(SQLCreateIndexStatement x);
    boolean visit(SQLAlterIndexStatement x);
    boolean visit(SQLDropIndexStatement x);

    boolean visit(SQLCreateMaterializedViewStatement x);
    boolean visit(SQLAlterMaterializedViewStatement x);
    boolean visit(SQLDropMaterializedViewStatement x);

    boolean visit(SQLCreatePackageStatement x);
    boolean visit(SQLAlterPackageStatement x);
    boolean visit(SQLDropPackageStatement x);

    boolean visit(SQLCreatePackageBodyStatement x);
    boolean visit(SQLAlterPackageBodyStatement x);
    boolean visit(SQLDropPackageBodyStatement x);

    boolean visit(SQLCreateProcedureStatement x);
    boolean visit(SQLAlterProcedureStatement x);
    boolean visit(SQLDropProcedureStatement x);

    boolean visit(SQLCreateRoleStatement x);
    boolean visit(SQLAlterRoleStatement x);
    boolean visit(SQLDropRoleStatement x);

    boolean visit(SQLCreateSchemaStatement x);
    boolean visit(SQLAlterSchemaStatement x);
    boolean visit(SQLDropSchemaStatement x);

    boolean visit(SQLCreateSequenceStatement x);
    boolean visit(SQLAlterSequenceStatement x);
    boolean visit(SQLDropSequenceStatement x);

    boolean visit(SQLCreateSynonymStatement x);
    boolean visit(SQLAlterSynonymStatement x);
    boolean visit(SQLDropSynonymStatement x);

    boolean visit(SQLCreateTableStatement x);
    boolean visit(SQLAlterTableStatement x);
    boolean visit(SQLDropTableStatement x);
    boolean visit(SQLRenameTableStatement x);
    boolean visit(SQLTruncateTableStatement x);

    boolean visit(SQLCreateTriggerStatement x);
    boolean visit(SQLAlterTriggerStatement x);
    boolean visit(SQLDropTriggerStatement x);

    boolean visit(SQLCreateTypeStatement x);
    boolean visit(SQLAlterTypeStatement x);
    boolean visit(SQLDropTypeStatement x);

    boolean visit(SQLCreateTypeBodyStatement x);
    boolean visit(SQLAlterTypeBodyStatement x);
    boolean visit(SQLDropTypeBodyStatement x);

    boolean visit(SQLCreateUserStatement x);
    boolean visit(SQLAlterUserStatement x);
    boolean visit(SQLDropUserStatement x);

    boolean visit(SQLCreateViewStatement x);
    boolean visit(SQLAlterViewStatement x);
    boolean visit(SQLDropViewStatement x);
    // ---------------------------- DDL End ------------------------------------




    // ---------------------------- DML Start ------------------------------------
    boolean visit(SQLCallStatement x);
    boolean visit(SQLDeleteStatement x);
    boolean visit(SQLExplainStatement x);
    boolean visit(SQLInsertStatement x);
    boolean visit(SQLMultiInsertStatement x);
    boolean visit(SQLLockTableStatement x);
    boolean visit(SQLMergeStatement x);
    boolean visit(SQLSelectStatement x);
    boolean visit(SQLSelectIntoStatement x);
    boolean visit(SQLUpdateStatement x);

    // ---------------------------- DML End ------------------------------------


    // ---------------------------- FCL End ------------------------------------
    boolean visit(SQLAssignmentStatement x);
    boolean visit(SQLCaseStatement x);
    boolean visit(SQLCaseStatement.SQLCaseStatementWhenItem x);
    boolean visit(SQLCaseStatement.SQLCaseStatementElseClause x);

    boolean visit(SQLCloseStatement x);
    boolean visit(SQLContinueStatement x);
    boolean visit(SQLExecuteImmediateStatement x);
    boolean visit(SQLExitStatement x);
    boolean visit(SQLFetchStatement x);
    boolean visit(SQLForAllStatement x);
    boolean visit(SQLForAllStatement.SQLBoundsRangeClause x);
    boolean visit(SQLForAllStatement.SQLBoundsIndicesOfClause x);
    boolean visit(SQLForAllStatement.SQLBoundsValueOfClause x);

    boolean visit(SQLForLoopStatement x);

    boolean visit(SQLGotoStatement x);
    boolean visit(SQLIfStatement x);
    boolean visit(SQLIterateStatement x);
    boolean visit(SQLLeaveStatement x);
    boolean visit(SQLLoopStatement x);
    boolean visit(SQLNullStatement x);
    boolean visit(SQLOpenForStatement x);
    boolean visit(SQLOpenStatement x);
    boolean visit(SQLPipeRowStatement x);
    boolean visit(SQLRaiseStatement x);
    boolean visit(SQLRepeatStatement x);
    boolean visit(SQLReturnStatement x);
    boolean visit(SQLWhileLoopStatement x);
    boolean visit(SQLWhileStatement x);
    // ---------------------------- FCL End ------------------------------------


    // ---------------------------- TC Start ------------------------------------

    // ---------------------------- TC End ------------------------------------









    // ---------------------------- Database expr Start ------------------------------------
    boolean visit(ISQLCreateDatabaseAction.SQLUserSysIdentifiedByAction x);
    boolean visit(ISQLCreateDatabaseAction.SQLUserSystemIdentifiedByAction x);
    boolean visit(ISQLCreateDatabaseAction.SQLControlfileReuseAction x);
    boolean visit(ISQLCreateDatabaseAction.SQLMaxDataFilesAction x);
    boolean visit(ISQLCreateDatabaseAction.SQLMaxInstancesAction x);
    boolean visit(ISQLCreateDatabaseAction.SQLNationalCharacterSetAction x);
    boolean visit(ISQLCreateDatabaseAction.SQLSetDefaultTableSpaceAction x);
    boolean visit(ISQLCreateDatabaseAction.SQLUserDataTableSpaceAction x);

    boolean visit(ISQLDatabaseLoggingClause.SQLLogFileAction x);
    boolean visit(ISQLDatabaseLoggingClause.SQLLogFileAction.SQLLogFileActionItem x);
    boolean visit(ISQLDatabaseLoggingClause.SQLMaxLogFilesAction x);
    boolean visit(ISQLDatabaseLoggingClause.SQLMaxLogMembersAction x);
    boolean visit(ISQLDatabaseLoggingClause.SQLMaxLogHistoryAction x);
    boolean visit(ISQLDatabaseLoggingClause.SQLArchiveLogAction x);
    boolean visit(ISQLDatabaseLoggingClause.SQLNoArchiveLogAction x);
    boolean visit(ISQLDatabaseLoggingClause.SQLForceLoggingAction x);
    boolean visit(ISQLDatabaseLoggingClause.SQLSetStandbyNoLoggingAction x);

    boolean visit(ISQLTablespaceClause.SQLExtentManagementLocalClause x);
    boolean visit(ISQLTablespaceClause.SQLDataFileClause x);
    boolean visit(ISQLTablespaceClause.SQLSysauxDataFileClause x);

    boolean visit(SQLDefaultTablespaceClause x);
    boolean visit(ISQLDefaultTempTablespaceClause.SQLDefaultTemporaryTablespaceClause x);
    boolean visit(ISQLDefaultTempTablespaceClause.SQLDefaultLocalTemporaryTablespaceClause x);

    boolean visit(SQLExtentManagementClause x);
    boolean visit(SQLExtentManagementClause.SQLAutoAllocateExpr x);
    boolean visit(SQLExtentManagementClause.SQLUniformExpr x);

    boolean visit(SQLUndoTablespaceClause x);

    boolean visit(SQLSetTimeZoneClause x);

    boolean visit(SQLEnablePluggableDatabaseClause x);

    boolean visit(SQLFileNameConvertClause x);
    boolean visit(SQLFileNameConvertClause.SQLNoneValue x);
    boolean visit(SQLFileNameConvertClause.SQLValues x);
    boolean visit(SQLFileNameConvertClause.SQLItem x);

    boolean visit(SQLTablespaceDataFileClause x);
    boolean visit(SQLTablespaceDataFileClause.SQLSizeSizeClauseItem x);

    boolean visit(ISQLUndoModeClause.SQLUndoModeOnClause x);
    boolean visit(ISQLUndoModeClause.SQLUndoModeOffClause x);

    boolean visit(SQLUpgradeDataDirectoryNameExpr x);
    // ---------------------------- Database expr End ------------------------------------


    // ---------------------------- Database Link expr Start ------------------------------------
    boolean visit(SQLConnectToCurrentUserClause x);
    boolean visit(SQLConnectToIdentifiedByClause x);
    boolean visit(SQLDBLinkAuthenticationClause x);
    // ---------------------------- Database Link expr End ------------------------------------



    // ---------------------------- Index expr Start ------------------------------------

    boolean visit(SQLAlterIndexAddPartition x);
    boolean visit(SQLAlterIndexCoalescePartition x);
    boolean visit(SQLAlterIndexDropPartition x);
    boolean visit(SQLAlterIndexModifyDefaultAttributes x);
    boolean visit(SQLAlterIndexModifyPartition x);
    boolean visit(SQLAlterIndexModifyPartition.SQLParametersOption x);
    boolean visit(SQLAlterIndexModifyPartition.SQLCoalesceOption x);
    boolean visit(SQLAlterIndexModifyPartition.SQLUpdateBlockReferencesOption x);
    boolean visit(SQLAlterIndexModifyPartition.SQLUnusableOption x);
    boolean visit(SQLAlterIndexModifySubPartition x);
    boolean visit(SQLAlterIndexModifySubPartition.SQLUnusableOption x);
    boolean visit(SQLAlterIndexRenamePartition x);
    boolean visit(SQLAlterIndexRenameSubPartition x);
    boolean visit(SQLAlterIndexSplitPartition x);
    // ---------------------------- Index expr End ------------------------------------






    // ---------------------------- Package expr Start ------------------------------------
    boolean visit(ISQLAlterPackageAction.SQLAlterPackageEditionAbleAction x);
    boolean visit(ISQLAlterPackageAction.SQLAlterPackageNoneEditionAbleAction x);
    // ---------------------------- Package expr End ------------------------------------


    // ---------------------------- Role expr Start ------------------------------------
    boolean visit(SQLRoleNotIdentifiedAction x);
    boolean visit(SQLRoleIdentifiedByAction x);
    boolean visit(SQLRoleIdentifiedUsingAction x);
    boolean visit(SQLRoleIdentifiedExternallyAction x);
    boolean visit(SQLRoleIdentifiedGloballyAction x);
    boolean visit(AbstractSQLRoleAction.SQLContainerClause x);
    // ---------------------------- Role expr End ------------------------------------


    // ---------------------------- Sequence expr Start ------------------------------------
    boolean visit(SQLSequenceStartWithOption x);
    boolean visit(SQLSequenceIncrementByOption x);
    boolean visit(SQLSequenceMaxValueOption x);
    boolean visit(SQLSequenceNoMaxValueOption x);
    boolean visit(SQLSequenceMinValueOption x);
    boolean visit(SQLSequenceNoMinValueOption x);
    boolean visit(SQLSequenceCycleOption x);
    boolean visit(SQLSequenceNoCycleOption x);
    boolean visit(SQLSequenceCacheOption x);
    boolean visit(SQLSequenceNoCacheOption x);
    boolean visit(SQLSequenceOrderOption x);
    boolean visit(SQLSequenceNoOrderOption x);
    boolean visit(SQLSequenceKeepOption x);
    boolean visit(SQLSequenceNoKeepOption x);
    boolean visit(SQLSequenceScaleOption x);
    boolean visit(SQLSequenceNoScaleOption x);
    boolean visit(SQLSequenceSessionOption x);
    boolean visit(SQLSequenceGlobalOption x);
    // ---------------------------- Sequence expr End ------------------------------------


    // ---------------------------- Synonym expr Start ------------------------------------
    boolean visit(ISQLAlterSynonymAction.SQLAlterSynonymEditionAbleAction x);
    boolean visit(ISQLAlterSynonymAction.SQLAlterSynonymNonEditionAbleAction x);
    boolean visit(ISQLAlterSynonymAction.SQLAlterSynonymCompileAction x);
    // ---------------------------- Synonym expr End ------------------------------------



    // ---------------------------- Table expr Start ------------------------------------
    boolean visit(SQLColumnDefinition x);
    boolean visit(SQLVirtualColumnDefinition x);
    boolean visit(SQLPrimaryKeyTableConstraint x);
    boolean visit(SQLUniqueTableConstraint x);
    boolean visit(SQLForeignKeyTableConstraint x);
    boolean visit(SQLCheckTableConstraint x);
    boolean visit(SQLScopeForTableConstraint x);
    boolean visit(SQLRefWithRowIdTableConstraint x);
    boolean visit(SQLLikeClause x);


    boolean visit(SQLNotNullColumnConstraint x);
    boolean visit(SQLNullColumnConstraint x);
    boolean visit(SQLUniqueColumnConstraint x);
    boolean visit(SQLPrimaryKeyColumnConstraint x);
    boolean visit(SQLReferencesColumnConstraint x);
    boolean visit(AbstractSQLReferencesConstraint.SQLOnUpdateAction x);
    boolean visit(AbstractSQLReferencesConstraint.SQLOnDeleteAction x);
    boolean visit(SQLCheckColumnConstraint x);
    boolean visit(SQLScopeIsColumnConstraint x);
    boolean visit(SQLWithRowIdColumnConstraint x);

    boolean visit(ISQLConstraint.SQLColumn x);

    boolean visit(SQLDeferrableConstraintState x);
    boolean visit(SQLNotDeferrableConstraintState x);
    boolean visit(SQLInitiallyImmediateConstraintState x);
    boolean visit(SQLInitiallyDeferredConstraintState x);
    boolean visit(SQLRelyConstraintState x);
    boolean visit(SQLNoRelyConstraintState x);
    boolean visit(SQLEnableConstraintState x);
    boolean visit(SQLDisableConstraintState x);
    boolean visit(SQLValidateConstraintState x);
    boolean visit(SQLNoValidateConstraintState x);


    boolean visit(ISQLIdentityOption.SQLIdentityStartWithOption x);
    boolean visit(ISQLIdentityOption.SQLLimitValueExpr x);
    boolean visit(ISQLIdentityOption.SQLIdentityIncrementByOption x);
    boolean visit(ISQLIdentityOption.SQLIdentityMaxValueOption x);
    boolean visit(ISQLIdentityOption.SQLIdentityNoMaxValueOption x);
    boolean visit(ISQLIdentityOption.SQLIdentityMinValueOption x);
    boolean visit(ISQLIdentityOption.SQLIdentityNoMinValueOption x);
    boolean visit(ISQLIdentityOption.SQLIdentityCycleOption x);
    boolean visit(ISQLIdentityOption.SQLIdentityNoCycleOption x);
    boolean visit(ISQLIdentityOption.SQLIdentityCacheOption x);
    boolean visit(ISQLIdentityOption.SQLIdentityNoCacheOption x);
    boolean visit(ISQLIdentityOption.SQLIdentityOrderOption x);
    boolean visit(ISQLIdentityOption.SQLIdentityNoOrderOption x);


    boolean visit(SQLPartitionByRange x);
    boolean visit(SQLPartitionByList x);
    boolean visit(SQLPartitionByHash x);
    boolean visit(SQLPartitionByKey x);
    boolean visit(SQLPartitionByRangeColumns x);
    boolean visit(SQLPartitionByListColumns x);
    boolean visit(SQLPartitionByReference x);
    boolean visit(SQLPartitionBySystem x);
    boolean visit(SQLPartitionByConsistentHash x);


    boolean visit(SQLSubPartitionByRange x);
    boolean visit(SQLSubPartitionByList x);
    boolean visit(SQLSubPartitionByHash x);
    boolean visit(SQLSubPartitionByKey x);
    boolean visit(SQLSubpartitionTemplate x);

    boolean visit(SQLPartitionDefinition x);
    boolean visit(SQLSubPartitionDefinition x);

    boolean visit(SQLPartitionValues x);
    boolean visit(SQLPartitionValuesIn x);
    boolean visit(SQLPartitionValuesLessThan x);
    boolean visit(SQLPartitionValuesLessThanMaxValue x);

    boolean visit(SQLPartitionsetByList x);
    boolean visit(SQLPartitionsetByRange x);
    boolean visit(SQLPartitionsetDefinition x);

    boolean visit(ISQLEnableDisableClause.SQLEnableConstraintClause x);
    boolean visit(ISQLEnableDisableClause.SQLEnableUniqueClause x);
    boolean visit(ISQLEnableDisableClause.SQLEnablePrimaryKeyClause x);
    boolean visit(ISQLEnableDisableClause.SQLDisableConstraintClause x);
    boolean visit(ISQLEnableDisableClause.SQLDisableUniqueClause x);
    boolean visit(ISQLEnableDisableClause.SQLDisablePrimaryKeyClause x);


// ------------------------------------ Alter ------------------------------------

    boolean visit(SQLAlterTableRenameAction x);
    boolean visit(SQLAlterTableMoveTableAction x);

    // ------------- Column --------------
    boolean visit(SQLAlterTableAddColumnAction x);

    boolean visit(SQLAlterTableAlterColumnAction x);

    boolean visit(SQLAlterTableChangeColumnAction x);

    boolean visit(SQLAlterTableOrderByColumnAction x);

    boolean visit(SQLAlterTableModifyColumnAction x);
    boolean visit(SQLAlterTableModifyColumnsAction x);

    boolean visit(SQLAlterTableRenameColumnAction x);

    boolean visit(SQLAlterTableDropColumnAction x);
    boolean visit(SQLAlterTableDropColumnsAction x);
    boolean visit(SQLAlterTableDropColumnsContinueAction x);
    boolean visit(SQLAlterTableDropUnusedColumnsAction x);
    boolean visit(SQLAlterTableSetUnusedColumnAction x);
    boolean visit(SQLAlterTableSetUnusedColumnsAction x);


    boolean visit(SQLAlterTableModifyCollectionRetrievalAction x);
    boolean visit(SQLAlterTableModifyLobStorageAction x);
    boolean visit(SQLAlterTableAlterVarrayColPropertyAction x);


    // ------------- Period --------------
    boolean visit(SQLAlterTableAddPeriodAction x);
    boolean visit(SQLAlterTableDropPeriodAction x);


    // ------------- Constraint --------------
    boolean visit(SQLAlterTableAddTableConstraintAction x);

    boolean visit(SQLAlterTableAlterConstraintAction x);
    boolean visit(SQLAlterTableAlterIndexConstraintAction x);

    boolean visit(SQLAlterTableModifyConstraintAction x);
    boolean visit(SQLAlterTableModifyUniqueConstraintAction x);
    boolean visit(SQLAlterTableModifyPrimaryKeyConstraintAction x);


    boolean visit(SQLAlterTableRenameConstraintAction x);
    boolean visit(SQLAlterTableRenameIndexConstraintAction x);
    boolean visit(SQLAlterTableRenameKeyConstraintAction x);

    boolean visit(SQLAlterTableDropConstraintAction x);
    boolean visit(SQLAlterTableDropUniqueConstraintAction x);
    boolean visit(SQLAlterTableDropPrimaryKeyConstraintAction x);
    boolean visit(SQLAlterTableDropIndexConstraintAction x);
    boolean visit(SQLAlterTableDropKeyConstraintAction x);

    boolean visit(SQLAlterTableEnableKeyAction x);
    boolean visit(SQLAlterTableDisableKeyAction x);



    // ------------- Partition --------------
    boolean visit(SQLAlterTableAddPartitionAction x);

    boolean visit(SQLAlterTableCoalescePartitionAction x);
    boolean visit(SQLAlterTableReorganizePartitionAction x);
    boolean visit(SQLAlterTableAnalyzePartitionAction x);
    boolean visit(SQLAlterTableCheckPartitionAction x);
    boolean visit(SQLAlterTableOptimizePartitionAction x);
    boolean visit(SQLAlterTableRebuildPartitionAction x);
    boolean visit(SQLAlterTableRepairPartitionAction x);

    boolean visit(SQLAlterTableMovePartitionAction x);
    boolean visit(SQLAlterTableMovePartitionForAction x);
    boolean visit(SQLAlterTableMoveSubPartitionAction x);
    boolean visit(SQLAlterTableMoveSubPartitionForAction x);

    boolean visit(SQLAlterTableModifyPartitionAction x);
    boolean visit(SQLAlterTableModifyPartitionForAction x);
    boolean visit(SQLAlterTableModifySubPartitionAction x);
    boolean visit(SQLAlterTableModifySubPartitionForAction x);

    boolean visit(SQLAlterTableRemovePartitioningAction x);

    boolean visit(SQLAlterTableDropPartitionAction x);
    boolean visit(SQLAlterTableDropPartitionForAction x);
    boolean visit(SQLAlterTableDropPartitionsAction x);
    boolean visit(SQLAlterTableDropPartitionsForAction x);
    boolean visit(SQLAlterTableDropSubPartitionAction x);
    boolean visit(SQLAlterTableDropSubPartitionForAction x);
    boolean visit(SQLAlterTableDropSubPartitionsAction x);
    boolean visit(SQLAlterTableDropSubPartitionsForAction x);

    boolean visit(SQLAlterTableRenamePartitionAction x);
    boolean visit(SQLAlterTableRenamePartitionForAction x);
    boolean visit(SQLAlterTableRenameSubPartitionAction x);
    boolean visit(SQLAlterTableRenameSubPartitionForAction x);

    boolean visit(SQLAlterTableTruncatePartitionAction x);
    boolean visit(SQLAlterTableTruncatePartitionForAction x);
    boolean visit(SQLAlterTableTruncatePartitionsAction x);
    boolean visit(SQLAlterTableTruncatePartitionsForAction x);
    boolean visit(SQLAlterTableTruncateSubPartitionAction x);
    boolean visit(SQLAlterTableTruncateSubPartitionForAction x);
    boolean visit(SQLAlterTableTruncateSubPartitionsAction x);
    boolean visit(SQLAlterTableTruncateSubPartitionsForAction x);

    boolean visit(SQLAlterTableSplitPartitionAction x);
    boolean visit(SQLAlterTableSplitPartitionForAction x);
    boolean visit(SQLAlterTableSplitSubPartitionAction x);
    boolean visit(SQLAlterTableSplitSubPartitionForAction x);

    boolean visit(SQLAlterTableMergePartitionsAction x);
    boolean visit(SQLAlterTableMergePartitionsToAction x);
    boolean visit(SQLAlterTableMergeSubPartitionsAction x);
    boolean visit(SQLAlterTableMergeSubPartitionsToAction x);

    boolean visit(SQLAlterTableExchangePartitionAction x);
    boolean visit(SQLAlterTableExchangePartitionForAction x);
    boolean visit(SQLAlterTableExchangeSubPartitionAction x);
    boolean visit(SQLAlterTableExchangeSubPartitionForAction x);

    boolean visit(SQLAlterTableSetPartitioningAction x);
    boolean visit(SQLAlterTableSetIntervalAction x);
    boolean visit(SQLAlterTableSetStoreInAction x);
    boolean visit(SQLAlterTableSetSubPartitionTemplateAction x);

    // ------------- Trigger --------------
    boolean visit(SQLAlterTableEnableTriggerAction x);
    boolean visit(SQLAlterTableDisableTriggerAction x);

    // ---------------------------- Table expr End ------------------------------------



    // ---------------------------- Trigger expr Start ------------------------------------
    boolean visit(SQLTriggerDatabaseEvent x);
    boolean visit(SQLTriggerDDLEvent x);
    boolean visit(SQLTriggerDMLEvent x);

    boolean visit(SQLTriggerReferencingClause x);
    boolean visit(SQLTriggerReferencingClause.SQLOldItem x);
    boolean visit(SQLTriggerReferencingClause.SQLNewItem x);
    boolean visit(SQLTriggerReferencingClause.SQLParentItem x);

    boolean visit(SQLTriggerCompoundTriggerBlock x);
    boolean visit(SQLTriggerCompoundTriggerBlock.SQLTimingPointSection x);

    boolean visit(ISQLAlterTriggerAction.SQLAlterTriggerEnableAction x);
    boolean visit(ISQLAlterTriggerAction.SQLAlterTriggerDisableAction x);
    boolean visit(ISQLAlterTriggerAction.SQLAlterTriggerRenameToAction x);
    boolean visit(ISQLAlterTriggerAction.SQLAlterTriggerEditionAbleAction x);
    boolean visit(ISQLAlterTriggerAction.SQLAlterTriggerNonEditionAbleAction x);
    // ---------------------------- Trigger expr End ------------------------------------


    // ---------------------------- Type expr Start ------------------------------------
    boolean visit(ISQLAlterTypeAction.SQLAlterTypeEditionAbleAction x);
    boolean visit(ISQLAlterTypeAction.SQLAlterTypeNoneEditionAbleAction x);
    boolean visit(ISQLAlterTypeAction.SQLAlterTypeResetAction x);
    boolean visit(ISQLAlterTypeAction.SQLAlterTypeInstantiableAction x);
    boolean visit(ISQLAlterTypeAction.SQLAlterTypeFinalAction x);
    boolean visit(ISQLAlterTypeAction.SQLAlterTypeNotInstantiableAction x);
    boolean visit(ISQLAlterTypeAction.SQLAlterTypeNotFinalAction x);
    boolean visit(ISQLAlterTypeAction.SQLAttributeClause x);
    boolean visit(ISQLAlterTypeAction.SQLLimitClause x);
    boolean visit(ISQLAlterTypeAction.SQLElementTypeClause x);
    boolean visit(SQLAlterTypeReplaceAction x);
    boolean visit(SQLAlterTypeAddAction x);
    boolean visit(SQLAlterTypeDropAction x);
    boolean visit(SQLAlterTypeModifyAction x);
    boolean visit(AbstractSQLAlterTypeAction.SQLDependentHandlingInvalidateClause x);
    boolean visit(AbstractSQLAlterTypeAction.SQLDependentHandlingCascadeClause x);
    // ---------------------------- Type expr End ------------------------------------

    // ---------------------------- View expr Start ------------------------------------
    boolean visit(SQLAlterViewAddTableConstraintAction x);
    boolean visit(SQLAlterViewModifyConstraintAction x);
    boolean visit(SQLAlterViewDropConstraintAction x);
    boolean visit(SQLAlterViewDropPrimaryKeyConstraintAction x);
    boolean visit(SQLAlterViewDropUniqueConstraintAction x);
    boolean visit(SQLAlterViewCompileAction x);
    boolean visit(SQLAlterViewReadOnlyAction x);
    boolean visit(SQLAlterViewReadWriteAction x);
    boolean visit(SQLAlterViewEditionableAction x);
    boolean visit(SQLAlterViewNonEditionableAction x);
    // ---------------------------- View expr End ------------------------------------




    // ---------------------------- Insert expr Start ------------------------------------
    boolean visit(SQLMultiInsertStatement.SQLAllInsertClause x);
    boolean visit(SQLMultiInsertStatement.SQLConditionalInsertClause x);
    boolean visit(SQLMultiInsertStatement.SQLConditionalInsertWhenClause x);
    boolean visit(SQLMultiInsertStatement.SQLInsertIntoClauseItem x);

    // ---------------------------- Insert expr Start ------------------------------------


    // ---------------------------- Select expr Start ------------------------------------

    boolean visit(SQLSelectQuery x);
    boolean visit(SQLParenSelectQuery x);
    boolean visit(SQLSelectUnionQuery x);

    boolean visit(SQLSelectItem x);
    boolean visit(SQLSelectTargetItem x);

    boolean visit(SQLObjectNameTableReference x);
    boolean visit(SQLSubQueryTableReference x);
    boolean visit(SQLTableFunctionTableReference x);
    boolean visit(SQLUnNestFunctionTableReference x);
    boolean visit(SQLJoinTableReference x);
    boolean visit(SQLJoinTableReference.SQLJoinOnCondition x);
    boolean visit(SQLJoinTableReference.SQLJoinUsingCondition x);

    boolean visit(SQLGroupByClause x);
    boolean visit(SQLGroupByClause.SQLGroupByItem x);
    boolean visit(SQLHavingClause x);

    boolean visit(SQLOrderByClause x);
    boolean visit(SQLOrderByItem x);
    boolean visit(SQLOrderByItem.SQLASCOrderingSpecification x);
    boolean visit(SQLOrderByItem.SQLDESCOrderingSpecification x);
    boolean visit(SQLOrderByItem.SQLUsingCOrderingSpecification x);

    boolean visit(SQLLimitOffsetClause x);
    boolean visit(SQLOffsetFetchClause x);

    boolean visit(SQLForUpdateClause x);
    boolean visit(SQLForUpdateClause.SQLForNoWaitOption x);
    boolean visit(SQLForUpdateClause.SQLForSkipLockedOption x);
    boolean visit(SQLForUpdateClause.SQLForWaitOption x);
    boolean visit(SQLLockInShareModeClause x);
    // ---------------------------- Select expr Start ------------------------------------
}
