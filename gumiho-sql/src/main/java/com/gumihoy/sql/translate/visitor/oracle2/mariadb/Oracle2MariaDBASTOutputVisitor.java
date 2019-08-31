///*
// * Copyright (C) 2017-2018 kent(kent.bohai@gmail.com).
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.gumihoy.sql.translate.visitor.oracle2.mariadb;
//
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.reference.SQLRefDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.SQLParameterDeclaration;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.sequence.SQLSequenceNoCacheOption;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.sequence.SQLSequenceNoCycleOption;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.sequence.SQLSequenceNoMaxValueOption;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.sequence.SQLSequenceNoMinValueOption;
//import com.aliyun.gumiho.sql.basic.ast.element.literal.SQLBooleanLiteral;
//import com.aliyun.gumiho.sql.basic.ast.enums.SQLReserved;
//import com.aliyun.gumiho.sql.basic.ast.statement.tcl.SQLSetTransactionStatement;
//import com.aliyun.gumiho.sql.config.SQLOutputConfig;
//import com.aliyun.gumiho.sql.dialect.mariadb.visitor.MariaDBSQLVisitor;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.common.*;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.common.allocateextent.OracleSQLAllocateExtentClause;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.common.filespecification.OracleSQLAutoExtendOffClause;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.common.filespecification.OracleSQLAutoExtendOnClause;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.common.filespecification.OracleSQLDataFileTempFileSpec;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.common.filespecification.OracleSQLRedoLogFileSpec;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.common.implicitcursor.*;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.common.logging.OracleSQLFilesystemLikeLogging;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.common.logging.OracleSQLLoggingClause;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.common.logging.OracleSQLNoLoggingClause;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.common.namecursor.OracleSQLFoundNameCursorExpr;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.common.namecursor.OracleSQLIsOpenNameCursorExpr;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.common.namecursor.OracleSQLNotFoundNameCursorExpr;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.common.namecursor.OracleSQLRowcountNameCursorExpr;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.common.storage.OracleSQLStorageClause;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.database.create.*;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.databaselink.create.OracleSQLConnectToIdentifiedByClause;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.databaselink.create.OracleSQLDBLinkAuthenticationClause;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.select.*;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.alter.column.OracleSQLAlterTableAddPeriodAction;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.alter.column.OracleSQLAlterTableDropPeriodAction;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.alter.partition.OracleSQLAlterTableModifyDefaultAttrsAction;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.alter.supplementallog.OracleSQLAlterTableAddSupplementalLogAction;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.alter.supplementallog.OracleSQLAlterTableDropSupplementalLogAction;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.element.OracleSQLPeriodDefinition;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.element.OracleSQLSupplementalIdKeyClause;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.element.OracleSQLSupplementalLogGrpClause;
//import com.aliyun.gumiho.sql.dialect.oracle.visitor.OracleSQLASTOutputVisitor;
//import com.aliyun.gumiho.sql.translate.visitor.oracle2.mysql.Oracle2MySQLASTOutputVisitor;
//
///**
// * @author kent onCondition 2018/1/16.9
// */
//public class Oracle2MariaDBASTOutputVisitor extends Oracle2MySQLASTOutputVisitor implements MariaDBSQLVisitor {
//
//    protected OracleSQLASTOutputVisitor oracleSQLASTOutputVisitor;
//
//    public Oracle2MariaDBASTOutputVisitor(StringBuilder appender, SQLOutputConfig config) {
//        super(appender, config);
//        oracleSQLASTOutputVisitor = new OracleSQLASTOutputVisitor(appender, config);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAssignmentStatement x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(SQLSetTransactionStatement x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    // ------------------------- Data Types Start ----------------------------------------
//
//    @Override
//    public boolean visit(SQLRefDataType x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    // ------------------------- Data Types End ----------------------------------------
//
//    // ------------------------- Literal Start ----------------------------------------
//
//    @Override
//    public boolean visit(SQLBooleanLiteral x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    // ------------------------- Literal End ----------------------------------------
//
//    // ------------------------- Commons Expr Start ----------------------------------------
//
//    @Override
//    public boolean visit(SQLParameterDeclaration x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//
//    // ------------------------- Commons Expr End ----------------------------------------
//
//
//    // --------------- Common SQL DDL Clauses Start ----------------------------
//
//    @Override
//    public boolean visit(OracleSQLAllocateExtentClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAllocateExtentClause.OracleSQLAllocateExtentSizeClauseItem x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAllocateExtentClause.OracleSQLAllocateExtentDataFileClauseItem x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAllocateExtentClause.OracleSQLAllocateExtentInstanceClauseItem x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLDeallocateUnusedClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLDeferredSegmentCreation x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLDataFileTempFileSpec x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLRedoLogFileSpec x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAutoExtendOffClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAutoExtendOnClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLoggingClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLNoLoggingClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLFilesystemLikeLogging x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLPhysicalPropertyOrganizationHeapClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLPhysicalPropertyOrganizationIndexClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLPhysicalPropertyOrganizationExternalClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLPhysicalPropertyClusterClause x) {
//        print(SQLReserved.CLUSTER);
//        printSpaceAfterAccept(x.getCluster());
//        printSpaceAndLnAndAccept(x.getColumns(), ",", true);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLHeapOrgTableClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLIndexOrgOverflowClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(IOracleSQLPhysicalAttributesClause.OracleSQLUsageQueueClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(IOracleSQLPhysicalAttributesClause.OracleSQLPctfreeClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(IOracleSQLPhysicalAttributesClause.OracleSQLPctusedClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(IOracleSQLPhysicalAttributesClause.OracleSQLInitransClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(IOracleSQLPhysicalAttributesClause.OracleSQLMaxTransClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLSizeClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(IOracleSQLSubstitutableColumnClause.OracleSQLSubstitutableColumnIsOFClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(IOracleSQLSubstitutableColumnClause.OracleSQLSubstitutableColumnAtAllLevelsClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLTableCompressionByCompress x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLTableCompressionByRowStoreCompress x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLTableCompressionByColumnStoreCompress x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLTableCompressionByNoCompress x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLPrefixCompression x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLPrefixNoCompression x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAdvancedIndexCompression x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAdvancedIndexNoCompression x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLNoInMemoryClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryTableClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLInMemoryAttributes x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryMemCompressClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryNoMemCompressClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryPriority x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryDistribute x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryDuplicateClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryDuplicateAllClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryNoDuplicateClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryColumnClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLNoInMemoryColumnClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLIlmClauseAddPolicyOption x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLIlmClauseDeletePolicyOption x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLIlmClauseEnablePolicyOption x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLIlmClauseDisablePolicyOption x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLIlmClauseDeleteAllOption x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLIlmClauseEnableAllOption x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLIlmClauseDisableAllOption x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(IOracleSQLIlmClause.OracleSQLIlmCompressionPolicyByTableCompression x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(IOracleSQLIlmClause.OracleSQLIlmCompressionPolicyByRowStoreCompression x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(IOracleSQLIlmClause.OracleSQLIlmCompressionPolicyByColumnStoreCompression x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(IOracleSQLIlmClause.OracleSQLIlmTieringPolicy x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(IOracleSQLIlmClause.OracleSQLIlmInMemoryPolicyBySetInMemory x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(IOracleSQLIlmClause.OracleSQLIlmInMemoryPolicyByModifyInMemory x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(IOracleSQLIlmClause.OracleSQLIlmInMemoryPolicyByNoInMemory x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(IOracleSQLIlmClause.AfterOfClause x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(IOracleSQLIlmClause.OnClause x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(IOracleSQLIlmClause.OracleSQLIlmInMemoryPolicy x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLIlmTimePeriod x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageInitialSizeClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageNextSizeClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageMinExtentsClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageMaxExtentsClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStoragePctIncreaseClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageFreeListsClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageFreeListGroupsClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageOptimalClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageBufferPoolClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageFlashCacheClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageCellFlashCacheClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageEncryptClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLNestedTableColProperty x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLNestedTableColProperty.SQLColumnValue x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobStorageClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLocationClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLocationClause.LocationItem x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterEnable x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterDisable x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterChunk x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterPctversion x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterRebuildFreepools x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterFreepools x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterEncrypt x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterDecrypt x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterCache x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterNoCache x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterCacheReads x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobRetentionClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobDeduplicateClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobKeepDuplicatesClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobCompressionClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobNoCompressionClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobPartitionStorage x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobPartitioningStorage x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLMaxSizeClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLMappingTableClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLNoMappingTableClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLPctthresholdClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLObjectTypeColProperty x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLVarrayColPropertyColumnProperty x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobStorageClauseColumnProperty x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobStorageParameters x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLIsOpenImplicitCursorExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLFoundImplicitCursorExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLNotFoundImplicitCursorExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowcountImplicitCursorExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLBulkRowcountImplicitCursorExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLBulkExceptionsCountImplicitCursorExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLBulkExceptionImplicitCursorExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLIsOpenNameCursorExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLFoundNameCursorExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLNotFoundNameCursorExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowcountNameCursorExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    // ------------ Common SQL DDL Clauses End -----------------------------------------------------------
//
//
//    // ----------- PL/SQL Language Elements Start ------------------------------------------------------------
//
//    @Override
//    public boolean visit(OracleParallelEnableByAnyClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleParallelEnableByHashClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleParallelEnableByRangeClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleParallelEnableByValueClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleParallelEnableClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractOracleSQLParallelEnableClause.OracleSQLStreamingClauseByOrder x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractOracleSQLParallelEnableClause.OracleSQLStreamingClusterByCluster x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAccessibleByClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAccessorClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAggregateClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAutonomousTransPragma x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLBlock x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLCallSpec.OracleSQLJavaDeclaration x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLCallSpec.OracleSQLCDeclaration x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLCallSpec.LanguageCNameExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLCallSpec.LanguageCLibraryExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLCallSpec.OracleSQLContextExternalParameter x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLCallSpec.OracleSQLSelfExternalParameter x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLCallSpec.OracleSQLReturnExternalParameter x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLCollectionTypeDefinition x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLConstantDeclaration x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLConstructorDeclaration x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLConstructorDefinition x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLCoveragePragma x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLCursorDeclaration x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLCursorDefinition x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLDeprecatePragma x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLElementSpec x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLElementSpec.SQLExternalNameClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLElementSpec.SQLExternalVariableNameClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLExceptionDeclaration x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLExceptionHandler x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLExceptionInitPragma x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLExecuteImmediateStatement x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLExternalTableClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAccessParametersClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAccessParametersClause.UsingClobClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLFunctionDeclaration x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLFunctionDefinition x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLFunctionHeading x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLInlinePragma x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLInvokerRightsClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLMapOrderFunctionDeclaration x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLPipelinedByRowClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLPipelinedByTableClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLPipelinedByUsingClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLPipelinedClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLProcedureDeclaration x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLProcedureDefinition x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLProcedureHeading x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLRecordTypeDefinition x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLRefCursorTypeDefinition x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLReliesOnClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLRestrictReferencesPragma x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLResultCacheClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLSeriallyReusablePragma x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLSubtypeDefinition x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLUDFPragma x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLVarrayStorageClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQSubprogramDeclaration x) {
//        print(x.getType().name);
//        printSpaceAfterAccept(x.getExpr());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLOpaqueTypeColumnProperty x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLVarrayColProperty x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLXmlTypeColumnProperty x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    // ----------- PL/SQL Language Elements End ------------------------------------------------------------
//
//
//    // ----------- Database Start ------------------------------------------------------------
//
//    // ----------- create ----------------
//
//    @Override
//    public boolean visit(OracleSQLUserSysClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLUserSystemClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLLogFileClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//
//
//    @Override
//    public boolean visit(OracleSQLDefaultTablespace x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLDefaultTempTablespace x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLUnDoTablespace x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSetTimeZoneClause x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLUserDataTablespaceClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLEnablePluggableDatabase x) {
//        return false;
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLExtentManagementLocalClause x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLExtentManagementLocalAutoAllocateClause x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLExtentManagementLocalUniformClause x) {
//        return false;
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLFileNameConvert x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLFileNameConvertNone x) {
//        return false;
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLTablespaceDataFileClause x) {
//        return false;
//    }
//
//    // ----------- Database End ------------------------------------------------------------
//
//
//    @Override
//    public boolean visit(OracleSQLConnectToIdentifiedByClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLDBLinkAuthenticationClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//
//    // ------------------ Sequence Details Start ----------------------
//    @Override
//    public boolean visit(SQLSequenceNoMaxValueOption x) {
//        print(SQLReserved.NOMAXVALUE);
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLSequenceNoMinValueOption x) {
//        print(SQLReserved.NOMINVALUE);
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLSequenceNoCycleOption x) {
//        print(SQLReserved.NOCYCLE);
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLSequenceNoCacheOption x) {
//        print(SQLReserved.NOCACHE);
//        return false;
//    }
//    // ------------------ Sequence Details End ----------------------
//
//
//    // ----------- SELECT Details Start ------------------------------------------------------------
//
//    @Override
//    public boolean visit(OracleSQLObjectNameTableTableReference x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLObjectNameTableTableReference.OracleSQLModifiedExternalTableClause x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLObjectNameTableTableReference.OracleSQLSampleClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLSubQueryTableReference x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLTableFunctionTableReference x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLContainersFunctionTableReference x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLShardsFunctionTableReference x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLInlineAnalyticViewTableReference x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLFlashbackQueryByAsOfClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLFlashbackQueryByAsOfPeriodForClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLFlashbackQueryByVersionsBetweenClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLFlashbackQueryByVersionsPeriodForClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLHierarchiesClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLPivotClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLPivotClause.OracleSQLPivotItem x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLUnPivotClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLSelectQuery x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLSubAvClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLSubAvClause.OracleSQLCalcMeasClause x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLWaitExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLWithClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLWithClause.OracleSQLSubAvFactoringClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLCellReferenceOptions x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLReferenceModelClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLMainModel x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLModelColumnClauses x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLModelColumnClausesItem x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLModelRulesClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLModelRulesClauseItem x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLModelIterateClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLSingleColumnForLoop x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLSingleColumnForLoop.OracleSQLSingleColumnForLoopInConditionExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLSingleColumnForLoop.OracleSQLSingleColumnForLoopFromToConditionExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLMultiColumnForLoop x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternMeasures x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternSkipToNextRowOption x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternSkipPastLastRowOption x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternSkipToVarOption x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternSkipToFirstVarOption x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternSkipToLastVarOption x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternSubsetClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternSubsetItem x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    // ----------- SELECT Details End ------------------------------------------------------------
//
//
//    // ----------- Table Details Start ------------------------------------------------------------
//    @Override
//    public boolean visit(OracleSQLAlterTableAddPeriodAction x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableDropPeriodAction x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableModifyDefaultAttrsAction x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableModifyDefaultAttrsAction.SQLForPartition x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableModifyDefaultAttrsAction.SQLForPartitionFor x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableModifyDefaultAttrsAction.LobItem x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableModifyDefaultAttrsAction.VarrayItem x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableAddSupplementalLogAction x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableDropSupplementalLogAction x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLSupplementalLogGrpClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLSupplementalLogGrpClause.GroupItem x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLSupplementalIdKeyClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLPeriodDefinition x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    // ----------- Table Details End ------------------------------------------------------------
//}
