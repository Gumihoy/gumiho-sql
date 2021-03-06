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
//package com.gumihoy.sql.translate.visitor.oracle2.sqlserver;
//
//import static com.aliyun.gumiho.sql.basic.ast.enums.SQLReserved.AS;
//
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.SQLDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.reference.SQLRefDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.ISQLSegmentAttributesClause;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.SQLExpr;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.SQLExprAsObjectExpr;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.SQLParameterDeclaration;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.select.SQLWithClause;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.sequence.SQLSequenceNoCacheOption;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.sequence.SQLSequenceNoCycleOption;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.sequence.SQLSequenceNoMaxValueOption;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.sequence.SQLSequenceNoMinValueOption;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.table.SQLObjectTableSubstitution;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.table.SQLOidClause;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.table.SQLOidIndexClause;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.table.element.SQLColumnDefinition;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.table.partition.SQLPartitionByHash;
//import com.aliyun.gumiho.sql.basic.ast.element.identifier.SQLName;
//import com.aliyun.gumiho.sql.basic.ast.element.literal.SQLBooleanLiteral;
//import com.aliyun.gumiho.sql.basic.ast.enums.SQLASType;
//import com.aliyun.gumiho.sql.basic.ast.enums.SQLBasicFileType;
//import com.aliyun.gumiho.sql.basic.ast.enums.SQLLocalType;
//import com.aliyun.gumiho.sql.basic.ast.enums.SQLReserved;
//import com.aliyun.gumiho.sql.basic.ast.statement.fcl.SQLIfStatement;
//import com.aliyun.gumiho.sql.basic.ast.statement.tcl.SQLSetTransactionStatement;
//import com.aliyun.gumiho.sql.config.SQLOutputConfig;
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
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.databaselink.create.OracleSQLConnectToCurrentUserClause;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.databaselink.create.OracleSQLConnectToIdentifiedByClause;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.databaselink.create.OracleSQLDBLinkAuthenticationClause;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.select.*;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.OracleSQLXMLTypeVirtualColumns;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.OracleSQLXmlTypeStorage;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.alter.column.OracleSQLAlterTableAddPeriodAction;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.alter.column.OracleSQLAlterTableDropPeriodAction;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.alter.partition.OracleSQLAlterTableModifyDefaultAttrsAction;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.alter.supplementallog.OracleSQLAlterTableAddSupplementalLogAction;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.alter.supplementallog.OracleSQLAlterTableDropSupplementalLogAction;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.element.OracleSQLPeriodDefinition;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.element.OracleSQLSupplementalIdKeyClause;
//import com.aliyun.gumiho.sql.dialect.oracle.ast.element.expr.table.element.OracleSQLSupplementalLogGrpClause;
//import com.aliyun.gumiho.sql.dialect.oracle.visitor.OracleSQLASTOutputVisitor;
//import com.aliyun.gumiho.sql.dialect.oracle.visitor.OracleSQLASTVisitor;
//import com.aliyun.gumiho.sql.dialect.sqlserver.visitor.SQLServerSQLASTOutputVisitor;
//
//import java.util.List;
//
///**
// * @author kent onCondition 2018/1/16.
// */
//public class Oracle2SQLServerASTOutputVisitor extends SQLServerSQLASTOutputVisitor implements OracleSQLASTVisitor {
//
//    protected OracleSQLASTOutputVisitor oracleSQLASTOutputVisitor;
//
//    public Oracle2SQLServerASTOutputVisitor(StringBuilder appender, SQLOutputConfig config) {
//        super(appender, config);
//        this.oracleSQLASTOutputVisitor = new OracleSQLASTOutputVisitor(appender, config);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAssignmentStatement x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLIfStatement.SQLElseIf x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
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
//        print(SQLReserved.DATAFILE);
//        printSpaceAfterAccept(x.getName());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAllocateExtentClause.OracleSQLAllocateExtentInstanceClauseItem x) {
//        print(SQLReserved.INSTANCE);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLDeallocateUnusedClause x) {
//        print(SQLReserved.DEALLOCATE_UNUSED);
//
//        OracleSQLSizeClause keep = x.keep;
//        if (keep != null) {
//            print(SQLReserved.KEEP);
//            keep.accept(this);
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLDeferredSegmentCreation x) {
//        print(SQLReserved.SEGMENT);
//        printSpaceAfterValue(SQLReserved.CREATION);
//        printSpaceAfterValue(x.getType().name);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLDeletingExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLDependenthandlingClause.OracleSQLInvalidate x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLDependenthandlingClause.OracleSQLCascade x) {
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
//        print(isLowerCase() ? "autoextend off" : "AUTOEXTEND OFF");
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAutoExtendOnClause x) {
//        print(isLowerCase() ? "autoextend on" : "AUTOEXTEND ON");
//
//        SQLExpr maxsize = x.maxsize;
//        if (maxsize != null) {
//            maxsize.accept(this);
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLoggingClause x) {
//        print(SQLReserved.LOGGING);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLNoLoggingClause x) {
//        print(SQLReserved.NOLOGGING);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLFilesystemLikeLogging x) {
//        print(SQLReserved.FILESYSTEM_LIKE_LOGGING);
//        return false;
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
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLHeapOrgTableClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLIndexOrgOverflowClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(IOracleSQLIndexOrgOverflowClause.OracleSQLIncludeClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(IOracleSQLIndexOrgOverflowClause.OracleSQLOverflowExpr x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(IOracleSQLPhysicalAttributesClause.OracleSQLUsageQueueClause x) {
//        print(SQLReserved.USAGE_QUEUE);
//        return false;
//    }
//
//    @Override
//    public boolean visit(IOracleSQLPhysicalAttributesClause.OracleSQLPctfreeClause x) {
//        print(SQLReserved.PCTFREE);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(IOracleSQLPhysicalAttributesClause.OracleSQLPctusedClause x) {
//        print(SQLReserved.PCTUSED);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(IOracleSQLPhysicalAttributesClause.OracleSQLInitransClause x) {
//        print(SQLReserved.INITRANS);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(IOracleSQLPhysicalAttributesClause.OracleSQLMaxTransClause x) {
//        print(SQLReserved.MAXTRANS);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLSizeClause x) {
//
//        print(x.getValue());
//
//        OracleSQLSizeClause.Type type = x.getType();
//        if (type != null) {
//            print(type.name);
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(IOracleSQLSubstitutableColumnClause.OracleSQLSubstitutableColumnIsOFClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(IOracleSQLSubstitutableColumnClause.OracleSQLSubstitutableColumnAtAllLevelsClause x) {
//        if (x.isNot()) {
//            print(SQLReserved.NOT);
//        }
//        print(SQLReserved.SUBSTITUTABLE_AT_ALL_LEVELS);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLTableCompressionByCompress x) {
//        print(SQLReserved.COMPRESS);
//
//        OracleSQLTableCompressionByCompress.ForOperations forOperations = x.getForOperations();
//        if (forOperations != null) {
//            printSpaceAfterValue(SQLReserved.FOR);
//            printSpaceAfterValue(forOperations.name);
//            printSpaceAfterValue(SQLReserved.OPERATIONS);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLTableCompressionByRowStoreCompress x) {
//        print(SQLReserved.ROW_STORE_COMPRESS);
//
//        OracleSQLTableCompressionByRowStoreCompress.RowStoreCompressType compressType = x.getCompressType();
//        if (compressType != null) {
//            printSpaceAfterValue(x.getCompressType().name);
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLTableCompressionByColumnStoreCompress x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLTableCompressionByNoCompress x) {
//        print(SQLReserved.NOCOMPRESS);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLPrefixCompression x) {
//        print(SQLReserved.COMPRESS);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLPrefixNoCompression x) {
//        print(SQLReserved.NOCOMPRESS);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAdvancedIndexCompression x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAdvancedIndexNoCompression x) {
//        print(SQLReserved.NOCOMPRESS);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryClause x) {
//        print(SQLReserved.INMEMORY);
//        printSpaceAfterAccept(x.getInMemoryAttributes());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLNoInMemoryClause x) {
//        print(SQLReserved.NO_INMEMORY);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryTableClause x) {
//        print(x.getInMemoryClause());
//        printSpaceAfterAccept(x.getInMemoryColumnClauses(), " ");
//        return false;
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLInMemoryAttributes x) {
//        print(x.getInMemoryMemCompress());
//        print(x.getInMemoryPriority());
//        print(x.getInMemoryDistribute());
//        print(x.getInMemoryDuplicate());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryMemCompressClause x) {
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryNoMemCompressClause x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryPriority x) {
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryDistribute x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryDuplicateClause x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryDuplicateAllClause x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryNoDuplicateClause x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLInMemoryColumnClause x) {
//        print(SQLReserved.INMEMORY);
//        printSpaceAfterAccept(x.getInMemoryMemCompress());
//        printSpaceAndLnAndAccept(x.getColumns(), ",", true);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLNoInMemoryColumnClause x) {
//        print(SQLReserved.NO_INMEMORY);
//        printSpaceAndLnAndAccept(x.getColumns(), ",", true);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLIlmClauseAddPolicyOption x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLIlmClauseDeletePolicyOption x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLIlmClauseEnablePolicyOption x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLIlmClauseDisablePolicyOption x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLIlmClauseDeleteAllOption x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLIlmClauseEnableAllOption x) {
//        return false;
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
//        return false;
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
//        if (x.getItems() == null
//                || x.getItems().size() == 0) {
//            return false;
//        }
//
//        print(SQLReserved.STORAGE);
//        printSpaceAndLnAndAccept(x.getItems(), ",", true);
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageInitialSizeClause x) {
//        print(SQLReserved.INITIAL);
//        printSpaceAfterAccept(x.getSizeClause());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageNextSizeClause x) {
//        print(SQLReserved.NEXT);
//        printSpaceAfterAccept(x.getSizeClause());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageMinExtentsClause x) {
//        print(SQLReserved.MINEXTENTS);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageMaxExtentsClause x) {
//        print(SQLReserved.MAXEXTENTS);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStoragePctIncreaseClause x) {
//        print(SQLReserved.PCTINCREASE);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageFreeListsClause x) {
//        print(SQLReserved.FREELISTS);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageFreeListGroupsClause x) {
//        print(SQLReserved.FREELIST_GROUPS);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageOptimalClause x) {
//        print(SQLReserved.OPTIMAL);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageBufferPoolClause x) {
//        print(SQLReserved.BUFFER_POOL);
//        printSpaceAfterValue(x.getType().name);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageFlashCacheClause x) {
//        print(SQLReserved.FLASH_CACHE);
//        printSpaceAfterValue(x.getType().name);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageCellFlashCacheClause x) {
//        print(SQLReserved.CELL_FLASH_CACHE);
//        printSpaceAfterValue(x.getType().name);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLStorageClause.OracleSQLStorageEncryptClause x) {
//        print(SQLReserved.ENCRYPT);
//        return false;
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLNestedTableColProperty x) {
//        print(SQLReserved.NESTED_TABLE);
//        printSpaceAfterAccept(x.getNestedItem());
//
////        List<SQLExpr> x.getStoreAsItems();
//
//        printSpaceAfterAccept(x.getSubstitutableColumnClause());
//
//        SQLLocalType localType = x.getLocalType();
//        if (localType != null) {
//            printSpaceAfterValue(localType.name);
//        }
//
//        printlnAfterValue(SQLReserved.STORE_AS);
//        printSpaceAfterAccept(x.getStorageTable());
//        printSpaceAndLnAndAccept(x.getStoreAsItems(), ",", true);
//
//        OracleSQLNestedTableColProperty.ReturnOption returnOption = x.getReturnOption();
//        if (returnOption != null) {
//            printSpaceAfterValue(returnOption.name);
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLNestedTableColProperty.SQLColumnValue x) {
//        print(SQLReserved.COLUMN_VALUE);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobStorageClause x) {
//        print(SQLReserved.LOB);
//        printSpaceAfterAccept(x.getItems(), ", ", true);
//
//        printSpaceAfterValue(SQLReserved.STORE_AS);
//        printSpaceAfterAccept(x.getParameters(), " ");
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLocationClause x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLocationClause.LocationItem x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterEnable x) {
//        print(SQLReserved.ENABLE);
//        printSpaceAfterValue(SQLReserved.STORAGE_IN_ROW);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterDisable x) {
//        print(SQLReserved.DISABLE);
//        printSpaceAfterValue(SQLReserved.STORAGE_IN_ROW);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterChunk x) {
//        print(SQLReserved.CHUNK);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterPctversion x) {
//        print(SQLReserved.PCTVERSION);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterRebuildFreepools x) {
//        print(SQLReserved.REBUILD);
//        printSpaceAfterValue(SQLReserved.FREEPOOLS);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterFreepools x) {
//        print(SQLReserved.FREEPOOLS);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterEncrypt x) {
//        print(SQLReserved.ENCRYPT);
//        printSpaceAfterAccept(x.getEncryptionSpec());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterDecrypt x) {
//        print(SQLReserved.DECRYPT);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterCache x) {
//        print(SQLReserved.CACHE);
//        printSpaceAfterAccept(x.getLoggingClause());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterNoCache x) {
//        print(SQLReserved.NOCACHE);
//        printSpaceAfterAccept(x.getLoggingClause());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobParameterCacheReads x) {
//        print(SQLReserved.CACHE_READS);
//        printSpaceAfterAccept(x.getLoggingClause());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobRetentionClause x) {
//        print(SQLReserved.RETENTION);
//        printSpaceAfterValue(x.getOptionType());
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobDeduplicateClause x) {
//        print(SQLReserved.DEDUPLICATE);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobKeepDuplicatesClause x) {
//        print(SQLReserved.KEEP_DUPLICATES);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobCompressionClause x) {
//        print(SQLReserved.COMPRESS);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobNoCompressionClause x) {
//        print(SQLReserved.NOCOMPRESS);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobPartitionStorage x) {
//        print(SQLReserved.PARTITION);
//        printSpaceAfterAccept(x.getName());
//        printSpaceAfterAccept(x.getItems(), " ");
//
//        SQLExpr subPartitionName = x.getSubPartitionName();
//        if (subPartitionName != null) {
//            printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//
//            this.incrementIndent();
//            println();
//
//            printSpaceAfterAccept(subPartitionName);
//            printSpaceAfterAccept(x.getSubPartitionItems(), " ");
//
//            println();
//            this.decrementIndent();
//            print(SQLReserved.RIGHT_PAREN);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobPartitioningStorage x) {
//        print(SQLReserved.LOB);
//        printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//        print(x.getItem());
//        print(SQLReserved.RIGHT_PAREN);
//
//        printSpaceAfterValue(SQLReserved.STORE_AS);
//
//        SQLBasicFileType fileType = x.getFileType();
//        if (fileType != null) {
//            printSpaceAfterValue(fileType.name);
//        }
//
//        printSpaceAfterAccept(x.getSegName());
//
//        ISQLSegmentAttributesClause segmentAttributesClause = x.getSegmentAttributesClause();
//        if (segmentAttributesClause != null) {
//            printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//            print(segmentAttributesClause);
//            print(SQLReserved.RIGHT_PAREN);
//        }
//
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLMaxSizeClause x) {
//        print(SQLReserved.MAXSIZE);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLMappingTableClause x) {
//        print(SQLReserved.MAPPING_TABLE);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLNoMappingTableClause x) {
//        print(SQLReserved.NOMAPPING);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLPctthresholdClause x) {
//        print(SQLReserved.PCTTHRESHOLD);
//        printSpaceAfterAccept(x.getValue());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLObjectTypeColProperty x) {
//        print(SQLReserved.COLUMN);
//        printSpaceAfterAccept(x.getColumn());
//        printSpaceAfterAccept(x.getSubstitutableColumnClause());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLVarrayColPropertyColumnProperty x) {
//        print(x.getVarrayColProperty());
//        printSpaceAndLnAndAccept(x.getLobPartitionStorages(), ",", true);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobStorageClauseColumnProperty x) {
//        print(x.getLobStorageClause());
//        printSpaceAndLnAndAccept(x.getLobPartitionStorages(), ",", true);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLLobStorageParameters x) {
//        printlnAndAccept(x.getLobStorageParameters(), ",", true);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLIsOpenImplicitCursorExpr x) {
//        print(SQLReserved.SQL);
//        print(SQLReserved.PERCENT_ISOPEN);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLFoundImplicitCursorExpr x) {
//        print(SQLReserved.SQL);
//        print(SQLReserved.PERCENT_FOUND);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLNotFoundImplicitCursorExpr x) {
//        print(SQLReserved.SQL);
//        print(SQLReserved.PERCENT_NOTFOUND);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowcountImplicitCursorExpr x) {
//        print(SQLReserved.SQL);
//        print(SQLReserved.PERCENT_ROWCOUNT);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLBulkRowcountImplicitCursorExpr x) {
//        print(SQLReserved.SQL);
//        print(SQLReserved.PERCENT_BULK_ROWCOUNT);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLBulkExceptionsCountImplicitCursorExpr x) {
//        print(SQLReserved.SQL);
//        print(SQLReserved.PERCENT_BULK_EXCEPTIONS);
//        print(SQLReserved.PERIOD);
//        print(SQLReserved.COUNT);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLBulkExceptionImplicitCursorExpr x) {
//        print(SQLReserved.SQL);
//        print(SQLReserved.PERCENT_BULK_EXCEPTIONS);
//        print(SQLReserved.LEFT_PAREN);
//        print(x.getIndex());
//        print(SQLReserved.RIGHT_PAREN);
//        print(x.getFunction());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLIsOpenNameCursorExpr x) {
//        print(x.getName());
//        print(SQLReserved.PERCENT_ISOPEN);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLFoundNameCursorExpr x) {
//        print(x.getName());
//        print(SQLReserved.PERCENT_FOUND);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLNotFoundNameCursorExpr x) {
//        print(x.getName());
//        print(SQLReserved.PERCENT_NOTFOUND);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowcountNameCursorExpr x) {
//        print(x.getName());
//        print(SQLReserved.PERCENT_ROWCOUNT);
//        return false;
//    }
//
//    // ------------ Common SQL DDL Clauses End -----------------------------------------------------------
//
//
//    // ----------- PL/SQL Language Elements Start ------------------------------------------------------------
//
//    @Override
//    public boolean visit(OracleParallelEnableByAnyClause x) {
//        print(SQLReserved.PARALLEL_ENABLE);
//        printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//
//        print(SQLReserved.PARTITION);
//        printSpaceAfterAccept(x.getArgument());
//        printSpaceAfterValue(SQLReserved.BY);
//        printSpaceAfterValue(SQLReserved.ANY);
//
//        printSpaceAfterValue(SQLReserved.RIGHT_PAREN);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleParallelEnableByHashClause x) {
//        print(SQLReserved.PARALLEL_ENABLE);
//        printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//
//        print(SQLReserved.PARTITION);
//        printSpaceAfterAccept(x.getArgument());
//        printSpaceAfterValue(SQLReserved.BY);
//        printSpaceAfterValue(SQLReserved.HASH);
//
//        printSpaceAfterAccept(x.getColumns(), ", ", true);
//
//        printSpaceAfterAccept(x.getStreamingClause());
//
//        printSpaceAfterValue(SQLReserved.RIGHT_PAREN);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleParallelEnableByRangeClause x) {
//        print(SQLReserved.PARALLEL_ENABLE);
//        printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//
//        print(SQLReserved.PARTITION);
//        printSpaceAfterAccept(x.getArgument());
//        printSpaceAfterValue(SQLReserved.BY);
//        printSpaceAfterValue(SQLReserved.RANGE);
//
//        printSpaceAfterAccept(x.getColumns(), ", ", true);
//
//        printSpaceAfterAccept(x.getStreamingClause());
//
//        printSpaceAfterValue(SQLReserved.RIGHT_PAREN);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleParallelEnableByValueClause x) {
//        print(SQLReserved.PARALLEL_ENABLE);
//        printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//
//        print(SQLReserved.PARTITION);
//        printSpaceAfterAccept(x.getArgument());
//        printSpaceAfterValue(SQLReserved.BY);
//        printSpaceAfterValue(SQLReserved.VALUE);
//
//        printSpaceAfterAccept(x.getColumns(), ", ", true);
//
//        printSpaceAfterValue(SQLReserved.RIGHT_PAREN);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleParallelEnableClause x) {
//        print(SQLReserved.PARALLEL_ENABLE);
//        return false;
//    }
//
//    @Override
//    public boolean visit(AbstractOracleSQLParallelEnableClause.OracleSQLStreamingClauseByOrder x) {
//        print(SQLReserved.ORDER);
//        printSpaceAfterAccept(x.getExpr());
//
//        printSpaceAfterValue(SQLReserved.BY);
//        printSpaceAfterAccept(x.getColumns(), ", ", true);
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(AbstractOracleSQLParallelEnableClause.OracleSQLStreamingClusterByCluster x) {
//
//        print(SQLReserved.CLUSTER);
//        printSpaceAfterAccept(x.getExpr());
//
//        printSpaceAfterValue(SQLReserved.BY);
//        printSpaceAfterAccept(x.getColumns(), ", ", true);
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAccessibleByClause x) {
//        print(SQLReserved.ACCESSIBLE_BY);
//        printAccept(x.getAccessorClauses(), ", ", true);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAccessorClause x) {
//        OracleSQLAccessorClause.SQLUnitKind unitKind = x.getUnitKind();
//        if (unitKind != null) {
//            print(unitKind.name);
//            printSpace();
//        }
//
//        x.getName().accept(this);
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAggregateClause x) {
//        print(SQLReserved.AGGREGATE_USING);
//
//        printSpace();
//        x.getName().accept(this);
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAutonomousTransPragma x) {
//        print(SQLReserved.PRAGMA_AUTONOMOUS_TRANSACTION);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLBlock x) {
//
//        if (x.getLabels().size() > 0) {
//            printlnAndAccept(x.getLabels());
//            println();
//        }
//
//        if (x.getDeclares().size() > 0) {
//
//            print(SQLReserved.DECLARE);
//
//            this.incrementIndent();
//            println();
//            printlnAndAccept(x.getDeclares());
//            this.decrementIndent();
//
//            println();
//        }
//
//        x.getBody().accept(this);
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLCallSpec.OracleSQLJavaDeclaration x) {
//        print(SQLReserved.LANGUAGE_JAVA_NAME);
//
//        printSpace();
//        x.getName().accept(this);
//
//        return false;
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLCallSpec.OracleSQLCDeclaration x) {
//        print(x.getType().name);
//
//        printSpaceAfterAccept(x.getNames(), " ");
//
//        List<SQLExpr> agentInArguments = x.getAgentInArguments();
//        if (agentInArguments != null
//                && agentInArguments.size() != 0) {
//            printSpaceAfterValue(SQLReserved.AGENT_IN);
//            printSpaceAfterAccept(agentInArguments, ", ", true);
//        }
//
//        if (x.isWithContext()) {
//            printSpaceAfterValue(SQLReserved.WITH_CONTEXT);
//        }
//
//        List<OracleSQLCallSpec.OracleSQLExternalParameter> parameters = x.getParameters();
//        if (parameters != null
//                && parameters.size() != 0) {
//            printSpaceAfterValue(SQLReserved.PARAMETERS);
//            printSpaceAfterAccept(parameters, ", ", true);
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLCallSpec.LanguageCNameExpr x) {
//        print(SQLReserved.NAME);
//        printSpaceAfterAccept(x.getName());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLCallSpec.LanguageCLibraryExpr x) {
//        print(SQLReserved.LIBRARY);
//        printSpaceAfterAccept(x.getName());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLCallSpec.OracleSQLContextExternalParameter x) {
//        print(SQLReserved.CONTEXT);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLCallSpec.OracleSQLSelfExternalParameter x) {
//        print(SQLReserved.SELF);
//
//        OracleSQLCallSpec.OracleSQLExternalParameterProperty property = x.getProperty();
//        if (property != null) {
//            printSpaceAfterValue(property.name);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLCallSpec.OracleSQLReturnExternalParameter x) {
//        x.getName().accept(this);
//
//        OracleSQLCallSpec.OracleSQLExternalParameterProperty property = x.getProperty();
//        if (property != null) {
//            printSpaceAfterValue(property.name);
//        }
//
//        if (x.isByReference()) {
//            printSpaceAfterValue(SQLReserved.BY_REFERENCE);
//        }
//
//        printSpaceAfterAccept(x.getDataType());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLCollectionTypeDefinition x) {
//        print(SQLReserved.TYPE);
//
//        printSpace();
//        x.getName().accept(this);
//
//        printSpace();
//        print(SQLReserved.IS);
//
//
//        printSpace();
//        x.getDataType().accept(this);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLConstantDeclaration x) {
//        x.getName().accept(this);
//        printSpaceAfterValue(SQLReserved.CONSTANT);
//
//        printSpace();
//        x.getDataType().accept(this);
//
//        if (x.isNotNull()) {
//            printSpaceAfterValue(SQLReserved.NOT_NULL);
//        }
//
//        printSpaceAfterAccept(x.getDefaultClause());
//
//        return false;
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLConstructorDeclaration x) {
//        if (x.isFinal_()) {
//            print(SQLReserved.FINAL);
//            printSpace();
//        }
//
//        if (x.isInstantiable()) {
//            print(SQLReserved.INSTANTIABLE);
//            printSpace();
//        }
//
//        print(SQLReserved.CONSTRUCTOR_FUNCTION);
//        printSpaceAfterAccept(x.getDataType());
//
//        printSpaceAfterValue(SQLReserved.RETURN_SELF_AS_RESULT);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLConstructorDefinition x) {
//        if (x.isFinal_()) {
//            print(SQLReserved.FINAL);
//            printSpace();
//        }
//
//        if (x.isInstantiable()) {
//            print(SQLReserved.INSTANTIABLE);
//            printSpace();
//        }
//
//        print(SQLReserved.CONSTRUCTOR_FUNCTION);
//        printSpaceAfterAccept(x.getDataType());
//
//        SQLDataType selfInOutDataType = x.getSelfInOutDataType();
//        List<SQLParameterDeclaration> parameters = x.getParameters();
//
//        boolean paren = selfInOutDataType != null
//                || (parameters != null && parameters.size() > 0);
//
//        if (paren) {
//            printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//            this.incrementIndent();
//            println();
//        }
//
//        if (selfInOutDataType != null) {
//            print(SQLReserved.SELF_IN_OUT);
//            printSpaceAfterAccept(selfInOutDataType);
//
//            if (parameters != null
//                    && parameters.size() > 0) {
//                print(SQLReserved.COMMA);
//            }
//        }
//
//        printlnAndAccept(parameters, ",");
//
//        if (paren) {
//            this.decrementIndent();
//            println();
//            printSpaceAfterValue(SQLReserved.RIGHT_PAREN);
//        }
//
//        printSpaceAfterValue(SQLReserved.RETURN_SELF_AS_RESULT);
//
//        SQLASType as = x.getAs();
//        if (as != null) {
//            printSpaceAfterValue(as.name);
//        }
//
//        printIndentAndLnAndAccept(x.getDeclareSections());
//
//        printIndentLnAndAccept(x.getAsExpr());
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLCoveragePragma x) {
//        print(SQLReserved.PRAGMA_COVERAGE);
//        printSpaceAfterAccept(x.getArguments(), ", ", true);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLCursorDeclaration x) {
//        print(SQLReserved.CURSOR);
//        printSpaceAfterAccept(x.getName());
//
//        printlnAndAccept(x.getParameters(), ",", true);
//
//        printSpaceAfterValue(SQLReserved.RETURN);
//        printSpaceAfterAccept(x.getReturnDataType());
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLCursorDefinition x) {
//        print(SQLReserved.CURSOR);
//        printSpaceAfterAccept(x.getName());
//
//        printSpaceAndLnAndAccept(x.getParameters(), ",", true);
//
//        SQLDataType dataType = x.getReturnDataType();
//        if (dataType != null) {
//            printSpaceAfterValue(SQLReserved.RETURN);
//            printSpaceAfterAccept(x.getReturnDataType());
//        }
//
//        printSpaceAfterValue(SQLReserved.IS);
//
//        incrementIndent();
//        println();
//        x.getSelectStatement().accept(this);
//        decrementIndent();
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLDeprecatePragma x) {
//        print(SQLReserved.PRAGMA_DEPRECATE);
//        printSpaceAfterAccept(x.getArguments(), ", ", true);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLElementSpec x) {
//        List<OracleSQLElementSpec.OracleSQLInheritanceType> inheritances = x.getInheritances();
//        if (inheritances.size() > 0) {
//            printlnAfterValue(inheritances);
//            println();
//        }
//
//        printlnAndAccept(x.getItems());
//
//        OracleSQLRestrictReferencesPragma restrictReferencesPragma = x.getRestrictReferencesPragma();
//        if (restrictReferencesPragma != null) {
//            print(",");
//            printlnAndAccept(restrictReferencesPragma);
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLElementSpec.SQLExternalNameClause x) {
//        print(SQLReserved.EXTERNAL_NAME);
//        printSpaceAfterAccept(x.getName());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLElementSpec.SQLExternalVariableNameClause x) {
//        print(SQLReserved.EXTERNAL);
//        printSpaceAfterValue(SQLReserved.VARIABLE_NAME);
//        printSpaceAfterAccept(x.getName());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLExceptionDeclaration x) {
//        x.getName().accept(this);
//
//        printSpace();
//        print(SQLReserved.EXCEPTION);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLExceptionHandler x) {
//        print(SQLReserved.WHEN);
//
//        printSpace();
//        printAccept(x.getExceptions(), " OR ");
//
//        printSpace();
//        print(SQLReserved.THEN);
//
//        this.incrementIndent();
//        println();
//        printlnAndAccept(x.getBodyItems());
//        this.decrementIndent();
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLExceptionInitPragma x) {
//        print(SQLReserved.PRAGMA_EXCEPTION_INIT);
//        printSpaceAfterAccept(x.getArguments(), ", ", true);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLExecuteImmediateStatement x) {
//        print(SQLReserved.EXECUTE_IMMEDIATE);
//        printSpaceAfterAccept(x.getDynamicSQLStmt());
//
//        if (x.isBulkCollect()) {
//            printlnAfterValue(SQLReserved.BULK_COLLECT);
//        }
//
//        List<SQLExpr> intoItems = x.getIntoItems();
//        if (intoItems != null
//                && intoItems.size() > 0) {
//            printSpaceAfterValue(SQLReserved.INTO);
//            printSpaceAfterAccept(intoItems, ", ");
//        }
//
//        printlnAndAccept(x.getUsingClause());
//
//        printlnAndAccept(x.getReturningIntoClause());
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLExternalTableClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLAccessParametersClause x) {
//        print(SQLReserved.ACCESS_PARAMETERS);
//        printSpaceAfterAccept(x.getItem());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAccessParametersClause.UsingClobClause x) {
//        print(SQLReserved.USING_CLOB);
//        printIndentLnAndAccept(x.getSubQuery());
//        return false;
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
//        print(SQLReserved.PRAGMA_INLINE);
//        printSpaceAfterAccept(x.getArguments(), ", ", true);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLInvokerRightsClause x) {
//        print(x.getAuthidType().name);
//        return false;
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLMapOrderFunctionDeclaration x) {
//        print(x.getType().name);
//        printSpaceAfterValue(SQLReserved.MEMBER);
//        printSpaceAfterAccept(x.getExpr());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLPipelinedByRowClause x) {
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLPipelinedByTableClause x) {
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLPipelinedByUsingClause x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLPipelinedClause x) {
//        print(SQLReserved.PIPELINED);
//        return false;
//    }
//
//
//    @Override
//    public boolean visit(OracleSQLProcedureDeclaration x) {
//        print(SQLReserved.PROCEDURE);
//        printSpaceAfterAccept(x.getName());
//
//        printSpaceAndLnAndAccept(x.getParameters(), ",", true);
//
//
//        List<SQLExpr> properties = x.getProperties();
//        if (properties != null
//                && properties.size() > 0) {
//            println();
//            printlnAndAccept(properties);
//        }
//
//        return false;
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
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLRestrictReferencesPragma x) {
//        print(SQLReserved.PRAGMA_RESTRICT_REFERENCES);
//        printSpaceAfterAccept(x.getArguments(), ", ", true);
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLResultCacheClause x) {
//        print(SQLReserved.RESULT_CACHE);
//
//        List<SQLExpr> dataSources = x.getDataSources();
//        if (dataSources != null) {
//            printSpaceAfterValue(SQLReserved.RELIES_ON);
//            printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//            printAccept(x.getDataSources(), ", ");
//            print(SQLReserved.RIGHT_PAREN);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLSeriallyReusablePragma x) {
//        print(SQLReserved.PRAGMA_SERIALLY_REUSABLE);
//        printSpaceAfterAccept(x.getArguments(), ", ", true);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLSubtypeDefinition x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLUDFPragma x) {
//        print(SQLReserved.PRAGMA_UDF);
//        return false;
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
//
//        return false;
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
//    @Override
//    public boolean visit(OracleSQLLogFileClause.SQLItem x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
//
//    @Override
//    public boolean visit(OracleSQLDefaultTablespace x) {
//        return false;
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
//        return false;
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
//    @Override
//    public boolean visit(OracleSQLConnectToCurrentUserClause x) {
//        return oracleSQLASTOutputVisitor.visit(x);
//    }
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
//
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
//        print(SQLReserved.HIERARCHIES);
//        printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//        printAccept(x.getNames(), ", ");
//        print(SQLReserved.RIGHT_PAREN);
//        return false;
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
//        print(SQLReserved.WITH);
//
//        this.incrementIndent();
//        println();
//
//        List<SQLExpr> plsqlDeclarations = x.getPlsqlDeclarations();
//        if (plsqlDeclarations.size() > 0) {
//            printlnAndAccept(plsqlDeclarations);
//        }
//
//        List<SQLWithClause.SQLWithElement> withElements = x.getWithElements();
//        if (withElements.size() > 0) {
//            printlnAndAccept(withElements, ",");
//        }
//
//        this.decrementIndent();
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLWithClause.OracleSQLSubAvFactoringClause x) {
//        x.getName().accept(this);
//        printSpaceAfterValue(SQLReserved.ANALYTIC_VIEW);
//        printSpaceAfterValue(AS);
//
//        printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//
//        this.incrementIndent();
//        println();
//
//        x.getSubAvClause().accept(this);
//
//        this.decrementIndent();
//        println();
//
//        print(SQLReserved.RIGHT_PAREN);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause x) {
//        print(SQLReserved.MODEL);
//
//        this.incrementIndent();
//
//        printlnAndAccept(x.getCellReferenceOptions());
//
//        printlnAfterValue(x.getReturnRowsClause());
//
//        printlnAndAccept(x.getReferenceModelClauses());
//
//        printlnAndAccept(x.getMainModel());
//
//        this.decrementIndent();
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLCellReferenceOptions x) {
//
//        boolean hasPrint = false;
//        OracleSQLModelClause.OracleSQLCellReferenceNavOptionType navOptionType = x.getNavOptionType();
//        if (navOptionType != null) {
//            print(navOptionType.name);
//            printSpaceAfterValue(SQLReserved.NAV);
//            hasPrint = true;
//        }
//
//        OracleSQLModelClause.OracleSQLCellReferenceUniqueOptionType uniqueOptionType = x.getUniqueOptionType();
//        if (uniqueOptionType != null) {
//            if (hasPrint) {
//                printSpace();
//            }
//            print(SQLReserved.UNIQUE);
//            printSpaceAfterValue(uniqueOptionType.name);
//            hasPrint = true;
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLReferenceModelClause x) {
//        print(SQLReserved.REFERENCE);
//        printSpaceAfterAccept(x.getName());
//
//        printSpaceAfterValue(SQLReserved.ON);
//        printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//        printSpaceAfterAccept(x.getSubQuery());
//        print(SQLReserved.RIGHT_PAREN);
//
//
//        printSpaceAfterAccept(x.getModelColumnClauses());
//
//        printSpaceAfterAccept(x.getCellReferenceOptions());
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLMainModel x) {
//        SQLName name = x.getName();
//        if (name != null) {
//            print(SQLReserved.MAIN);
//            printSpaceAfterAccept(name);
//            printSpace();
//        }
//
//        print(x.getModelColumnClauses());
//
//        printlnAndAccept(x.getCellReferenceOptions());
//
//        printlnAndAccept(x.getModelRulesClause());
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLModelColumnClauses x) {
//
//        boolean hashPrint = false;
//
//        List<OracleSQLModelClause.OracleSQLModelColumnClausesItem> partitionByItems = x.getPartitionByItems();
//        if (partitionByItems != null
//                && partitionByItems.size() > 0) {
//            print(SQLReserved.PARTITION_BY);
//            printSpaceAfterAccept(partitionByItems, "", true);
//            hashPrint = true;
//        }
//
//        List<OracleSQLModelClause.OracleSQLModelColumnClausesItem> dimensionByItems = x.getDimensionByItems();
//        if (dimensionByItems != null
//                && dimensionByItems.size() > 0) {
//            if (hashPrint) {
//                println();
//            }
//            print(SQLReserved.DIMENSION);
//            printSpaceAfterValue(SQLReserved.BY);
//            printSpaceAfterAccept(dimensionByItems, ", ", true);
//            hashPrint = true;
//        }
//
//
//        List<OracleSQLModelClause.OracleSQLModelColumnClausesItem> measuresItems = x.getMeasuresItems();
//        if (measuresItems != null
//                && measuresItems.size() > 0) {
//            if (hashPrint) {
//                println();
//            }
//            print(SQLReserved.MEASURES);
//            printSpaceAfterAccept(measuresItems, ", ", true);
//            hashPrint = true;
//        }
//
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLModelColumnClausesItem x) {
//        print(x.getExpr());
//        printSpaceAfterAccept(x.getAlias());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLModelRulesClause x) {
//
//        if (x.isRules()) {
//            print(SQLReserved.RULES);
//            printSpace();
//        }
//
//        OracleSQLModelClause.OracleSQLModelRulesType rulesType = x.getRulesType();
//        if (rulesType != null) {
//            print(rulesType.name);
//            printSpace();
//        }
//
//        OracleSQLModelClause.OracleSQLModelRulesOrderType order = x.getOrder();
//        if (order != null) {
//            print(order.name);
//            printSpace();
//        }
//
//        OracleSQLModelClause.OracleSQLModelIterateClause modelIterateClause = x.getModelIterateClause();
//        if (modelIterateClause != null) {
//            print(modelIterateClause);
//            printSpace();
//        }
//
//        printlnAndAccept(x.getItems(), ",", true);
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLModelRulesClauseItem x) {
//        OracleSQLModelClause.OracleSQLModelRulesType rulesType = x.getRulesType();
//        if (rulesType != null) {
//            print(rulesType.name);
//            printSpace();
//        }
//
//        print(x.getCellAssignment());
//
//        printSpaceAfterAccept(x.getOrderByClause());
//
//        printSpaceAfterValue(SQLReserved.EQUALS_OP);
//
//        printSpaceAfterAccept(x.getExpr());
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLModelClause.OracleSQLModelIterateClause x) {
//        print(SQLReserved.ITERATE);
//        printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//        x.getValue().accept(this);
//        print(SQLReserved.RIGHT_PAREN);
//
//        SQLExpr condition = x.getCondition();
//        if (condition != null) {
//            printSpaceAfterValue(SQLReserved.UNTIL);
//            printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//            condition.accept(this);
//            print(SQLReserved.RIGHT_PAREN);
//        }
//
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLSingleColumnForLoop x) {
//        print(SQLReserved.FOR);
//        printSpaceAfterAccept(x.getName());
//
//        printSpaceAfterAccept(x.getCondition());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLSingleColumnForLoop.OracleSQLSingleColumnForLoopInConditionExpr x) {
//        print(SQLReserved.IN);
//        printSpaceAfterAccept(x.getArguments(), ", ", true);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLSingleColumnForLoop.OracleSQLSingleColumnForLoopFromToConditionExpr x) {
//        SQLExpr pattern = x.getPattern();
//        if (pattern != null) {
//            print(SQLReserved.LIKE);
//            printSpaceAfterAccept(pattern);
//            printSpace();
//        }
//
//        print(SQLReserved.FROM);
//        printSpaceAfterAccept(x.getFrom());
//
//        printSpaceAfterValue(SQLReserved.TO);
//        printSpaceAfterAccept(x.getTo());
//
//        printSpaceAfterValue(x.getIncrement());
//        printSpaceAfterAccept(x.getIncrementVal());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLMultiColumnForLoop x) {
//        print(SQLReserved.FOR);
//        printSpaceAfterAccept(x.getForItems(), ", ", true);
//
//        printSpaceAfterValue(SQLReserved.IN);
//        printSpaceAfterAccept(x.getForItems(), ", ", true);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause x) {
//        print(SQLReserved.MATCH_RECOGNIZE);
//        printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//
//        incrementIndent();
//
//        printlnAndAccept(x.getPartitionByClause());
//        printlnAndAccept(x.getOrderByClause());
//
//        List<SQLExprAsObjectExpr> measuresItems = x.getMeasuresItems();
//        if (measuresItems != null
//                && measuresItems.size() > 0) {
//            printlnAfterValue(SQLReserved.MEASURES);
//            this.incrementIndent();
//            println();
//            printlnAndAccept(x.getMeasuresItems(), ", ");
//            this.decrementIndent();
//        }
//
//        printlnAndAccept(x.getSkipToOption());
//
//        printlnAfterValue(SQLReserved.PATTERN);
//        printSpaceAfterValue(SQLReserved.LEFT_PAREN);
//        printSpaceAfterAccept(x.getPattern());
//        print(SQLReserved.RIGHT_PAREN);
//
//        printlnAndAccept(x.getPatternSubsetClause());
//
//        printlnAfterValue(SQLReserved.DEFINE);
//        this.incrementIndent();
//        println();
//        printlnAndAccept(x.getDefineItems(), ",");
//        this.decrementIndent();
//
//        decrementIndent();
//        println();
//
//        print(SQLReserved.RIGHT_PAREN);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternMeasures x) {
//        print(SQLReserved.MEASURES);
//        printSpaceAfterAccept(x.getMeasureColumns(), ", ");
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternSkipToNextRowOption x) {
//        print(SQLReserved.AFTER_MATCH_SKIP_TO);
//        printSpaceAfterValue(SQLReserved.NEXT_ROW);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternSkipPastLastRowOption x) {
//        print(SQLReserved.AFTER_MATCH_SKIP_PAST);
//        printSpaceAfterValue(SQLReserved.LAST_ROW);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternSkipToVarOption x) {
//        print(SQLReserved.AFTER_MATCH_SKIP_TO);
//        printSpaceAfterAccept(x.getName());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternSkipToFirstVarOption x) {
//        print(SQLReserved.AFTER_MATCH_SKIP_TO);
//        printSpaceAfterValue(SQLReserved.FIRST);
//        printSpaceAfterAccept(x.getName());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternSkipToLastVarOption x) {
//        print(SQLReserved.AFTER_MATCH_SKIP_TO);
//        printSpaceAfterValue(SQLReserved.LAST);
//        printSpaceAfterAccept(x.getName());
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternSubsetClause x) {
//        print(SQLReserved.SUBSET);
//        printSpaceAfterAccept(x.getItems(), ", ");
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLRowPatternClause.OracleSQLRowPatternSubsetItem x) {
//        x.getName().accept(this);
//
//        printSpaceAfterValue(SQLReserved.EQUALS_OP);
//
//        printSpaceAfterAccept(x.getItems(), ", ", true);
//
//        return false;
//    }
//
//    // ----------- SELECT Details End ------------------------------------------------------------
//
//
//    // ----------- Table Details Start ------------------------------------------------------------
//    @Override
//    public boolean visit(SQLColumnDefinition x) {
//        return true;
//    }
//
//    @Override
//    public boolean visit(OracleSQLXmlTypeStorage.OracleSQLXmlTypeStorageAsObjectRelational x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLXmlTypeStorage.OracleSQLXmlTypeStorageAsClob x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLXmlTypeStorage.OracleSQLXmlTypeStorageAsBinaryXml x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLXmlTypeStorage.OracleSQLXmlTypeStorageAsAllVarrays x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLXMLTypeVirtualColumns x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLXMLTypeVirtualColumns.SQLItem x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableAddPeriodAction x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableDropPeriodAction x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableModifyDefaultAttrsAction x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableModifyDefaultAttrsAction.SQLForPartition x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableModifyDefaultAttrsAction.SQLForPartitionFor x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableModifyDefaultAttrsAction.LobItem x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableModifyDefaultAttrsAction.VarrayItem x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableAddSupplementalLogAction x) {
//        print(SQLReserved.ADD);
//        printSpaceAfterAccept(x.getItems(), ", ");
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLAlterTableDropSupplementalLogAction x) {
//        print(SQLReserved.DROP);
//        printSpaceAfterAccept(x.getItems(), ", ");
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLSupplementalLogGrpClause x) {
//        print(SQLReserved.SUPPLEMENTAL_LOG_GROUP);
//        printSpaceAndLnAndAccept(x.getItems(), ",", true);
//        if (x.isAlways()) {
//            printSpaceAfterValue(SQLReserved.ALWAYS);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLSupplementalLogGrpClause.GroupItem x) {
//        print(x.getColumn());
//        if (x.isNotLog()) {
//            printSpaceAfterValue(SQLReserved.NOT_LOG);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLSupplementalIdKeyClause x) {
//        print(SQLReserved.SUPPLEMENTAL_LOG_DATA);
//        printSpaceAndLnAfterValue(x.getItems(), ",", true);
//        printSpaceAfterValue(SQLReserved.COLUMNS);
//        return false;
//    }
//
//    @Override
//    public boolean visit(OracleSQLPeriodDefinition x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLObjectTableSubstitution x) {
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLOidClause x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLOidIndexClause x) {
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLPartitionByHash x) {
//        print(SQLReserved.PARTITION_BY);
//
//        if (x.isLinear()) {
//            printSpaceAfterValue(SQLReserved.LINEAR);
//        }
//
//        printSpaceAfterValue(SQLReserved.HASH);
//        printSpaceAfterAccept(x.getColumns(), ", ", true);
//
//        printlnAndAccept(x.getSubPartitionBy());
//
//        SQLExpr partitionsQuantity = x.getPartitionsNum();
//        if (partitionsQuantity != null) {
//
//            printlnAfterValue(SQLReserved.PARTITIONS);
//            printSpaceAfterAccept(partitionsQuantity);
//        }
//
//        printlnAndAccept(x.getStoreInClause());
//        printlnAndAccept(x.getCompression());
//        printlnAndAccept(x.getOverflowStoreInClause());
//
//        printSpaceAndLnAndAccept(x.getPartitions(), ",", true);
//        return false;
//    }
//
//    // ----------- Table Details End ------------------------------------------------------------
//
//}
