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
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.SQLDataTypeImpl;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.any.SQLAnyDataDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.any.SQLAnyDataSetDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.any.SQLAnyTypeDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.datetime.SQLDateDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.datetime.SQLTimestampDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.interval.SQLIntervalDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.numeric.*;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.string.*;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.xml.SQLUriTypeDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.xml.SQLXmlTypeDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.SQLExpr;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.select.order.SQLOrderByClause;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.select.order.SQLOrderByItem;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.table.element.constraint.table.*;
//import com.aliyun.gumiho.sql.basic.ast.element.identifier.SQLName;
//import com.aliyun.gumiho.sql.basic.ast.element.pseudocolumn.SQLSequenceExpr;
//import com.aliyun.gumiho.sql.basic.ast.enums.SQLIndexCategory;
//import com.aliyun.gumiho.sql.basic.ast.statement.ddl.comment.SQLCommentOnColumnStatement;
//import com.aliyun.gumiho.sql.basic.ast.statement.ddl.comment.SQLCommentOnTableStatement;
//import com.aliyun.gumiho.sql.basic.ast.statement.ddl.index.SQLAlterIndexStatement;
//import com.aliyun.gumiho.sql.basic.ast.statement.ddl.index.SQLCreateIndexStatement;
//import com.aliyun.gumiho.sql.basic.ast.statement.ddl.index.SQLDropIndexStatement;
//import com.aliyun.gumiho.sql.basic.ast.statement.ddl.table.SQLAlterTableStatement;
//import com.aliyun.gumiho.sql.basic.ast.statement.ddl.table.SQLCreateTableStatement;
//import com.aliyun.gumiho.sql.basic.ast.statement.ddl.table.SQLDropTableStatement;
//import com.aliyun.gumiho.sql.enums.DBType;
//import com.aliyun.gumiho.sql.translate.SQLASTTransformVisitor;
//import com.aliyun.gumiho.sql.translate.SQLTransformConfig;
//import com.aliyun.gumiho.sql.util.SQLUtils;
//
//import java.util.List;
//
///**
// * @author kent onCondition 2018/1/16.
// */
//public class Oracle2SQLServerASTTransformVisitor extends SQLASTTransformVisitor {
//
//    public Oracle2SQLServerASTTransformVisitor(SQLTransformConfig config) {
//        super(config);
//    }
//
//
//    @Override
//    public boolean visit(SQLCreateIndexStatement x) {
//        // 为支持 drop index, 缓存 on table
//        String name = x.getName().getName();
//        String tableName = x.getTableName();
//        config.setIndexTable(name, tableName);
//
//        // bitmap not support, remove
//        if (x.getCategory() == SQLIndexCategory.BITMAP) {
//            x.setCategory(null);
//        }
//
//        // index properties / attributes 不支持，去掉
//        x.getProperties().clear();
//
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLAlterIndexStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDropIndexStatement x) {
//        SQLExpr name = x.getNames().get(0);
//
//        // 设置  on table
//        String tableName = null;
//        if (name instanceof SQLName) {
//            tableName = config.getIndexTable(((SQLName) name).getName());
//        }
//        if (tableName != null) {
//            x.setTable(tableName);
//        }
//
//        // online、force、invalidation 不支持, remove
//        x.setOnline(false);
//        x.setForce(false);
//        x.setInvalidation(null);
//
//        return super.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(SQLCreateTableStatement x) {
//        List<SQLCommentOnColumnStatement> commentOnColumnStatements = x.createCommentOnColumnAndRemove(DBType.Oracle);
//        config.stmtList.addAll(commentOnColumnStatements);
//
//        SQLCommentOnTableStatement commentOnTableStatement = x.createCommentOnTableAndRemove(DBType.Oracle);
//        if (commentOnTableStatement != null) {
//            config.stmtList.add(commentOnTableStatement);
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLAlterTableStatement x) {
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLDropTableStatement x) {
//        return false;
//    }
//
//    // ------------------------- Data Types Start ----------------------------------------
//    @Override
//    public boolean visit(SQLCharDataType x) {
//        if (x.getCharSizeUnit() != null) {
//            x.setCharSizeUnit(null);
//        }
//
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLVarchar2DataType x) {
//        SQLVarcharDataType target = new SQLVarcharDataType();
//
//        if (x.getArguments().size() == 1) {
//            target.addArgument(x.getArguments().get(0).clone());
//        }
//
//        boolean replace = SQLUtils.replaceInParent(x, target);
//        if (replace) {
//            target.accept(this);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLNCharDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNVarchar2DataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNumberDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLFloatDataType x) {
//        return super.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(SQLBinaryFloatDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLBinaryDoubleDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLLongDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLLongRawDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLRawDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDateDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLTimestampDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLIntervalDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLBlobDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLClobDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNClobDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLBFileDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLRowIdDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLURowIdDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCharacterDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCharacterVaryingDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCharVaryingDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNCharVaryingDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLVarcharDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNationalCharacterDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNationalCharacterVaryingDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNationalCharDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNationalCharVaryingDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNumericDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDecimalDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDecDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDoublePrecisionDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLRealDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLAnyDataDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLAnyTypeDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLAnyDataSetDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlTypeDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLUriTypeDataType x) {
//        return super.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(SQLDataTypeImpl x) {
//        return super.visit(x);
//    }
//
//    // ------------------------- Data Types End ----------------------------------------
//
//
//    // ------------------------- Expressions Start ----------------------------------------
//
//    @Override
//    public boolean visit(SQLSequenceExpr x) {
//        return super.visit(x);
//    }
//
//    // ------------------------- Expressions End ----------------------------------------
//
//
//    // ------------------------- column constraint Start ----------------------------------------
//
//    // ------------------------- column constraint End ----------------------------------------
//
//
//    // ------------------------- table constraint Start ----------------------------------------
//    @Override
//    public boolean visit(SQLCheckTableConstraint x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLForeignKeyTableConstraint x) {
//        x.getReferencingColumns();
//        x.getReferencedColumns();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLPrimaryKeyTableConstraint x) {
//        x.getColumns();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLUniqueTableConstraint x) {
//        x.getColumns();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLScopeForTableConstraint x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLRefWithRowIdTableConstraint x) {
//        return super.visit(x);
//    }
//
//
//    // ------------------------- table constraint End ----------------------------------------
//
//
//    // ------------------ Select Details Start ----------------------
//    @Override
//    public boolean visit(SQLOrderByClause x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLOrderByItem x) {
//        return super.visit(x);
//    }
//
//    // ------------------ Select Details End ----------------------
//
//}
