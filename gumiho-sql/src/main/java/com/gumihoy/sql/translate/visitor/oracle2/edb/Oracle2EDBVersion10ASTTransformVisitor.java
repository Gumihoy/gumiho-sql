/*
 * Copyright (C) 2017-2018 kent(kent.bohai@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gumihoy.sql.translate.visitor.oracle2.edb;


import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLDefaultClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLNullExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLNumberDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.*;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLPropertyExpr;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLIntegerLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.string.SQLStringLiteral;
import com.gumihoy.sql.basic.ast.expr.method.SQLMethodInvocation;
import com.gumihoy.sql.basic.ast.expr.select.group.SQLGroupByClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByItem;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceMaxValueOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceMinValueOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceStartWithOption;
import com.gumihoy.sql.basic.ast.expr.table.element.ISQLColumnDefinition;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.*;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLAlterDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLCreateDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLDropDatabaseStatement;
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
import com.gumihoy.sql.basic.ast.statement.ddl.materializedview.SQLCreateMaterializedViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.schema.SQLAlterSchemaStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.schema.SQLCreateSchemaStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.schema.SQLDropSchemaStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.sequence.SQLAlterSequenceStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.sequence.SQLCreateSequenceStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.sequence.SQLDropSequenceStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.synonym.SQLAlterSynonymStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.synonym.SQLCreateSynonymStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLAlterTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLDropTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLRenameTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLAlterTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLCreateTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLDropTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.type.SQLAlterTypeStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.type.SQLCreateTypeStatement;
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
import com.gumihoy.sql.basic.ast.statement.dml.SQLSelectIntoStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLSelectStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLUpdateStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.*;
import com.gumihoy.sql.basic.parser.SQLKeyWord;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.util.SQLUtils;

import java.util.List;

/**
 * @author kent onCondition 2018/1/16.
 */
public class Oracle2EDBVersion10ASTTransformVisitor extends SQLASTTransformVisitor {

    public Oracle2EDBVersion10ASTTransformVisitor() {
    }

    public Oracle2EDBVersion10ASTTransformVisitor(SQLTransformConfig config) {
        super(config);
    }


    @Override
    public boolean visit(SQLCommentOnColumnStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCommentOnDatabaseStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCommentOnIndexStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCommentOnMaterializedViewStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCommentOnRoleStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCommentOnSequenceStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCommentOnServerStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCommentOnTablespaceStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCommentOnTableStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCommentOnTypeStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCommentOnViewStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCreateDatabaseStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLAlterDatabaseStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLDropDatabaseStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCreateDomainStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLAlterDomainStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLDropDomainStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCreateEventStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLAlterEventStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLDropEventStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCreateFunctionStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLAlterFunctionStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLDropFunctionStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCreateIndexStatement x) {

        // 字段是 整数 不支持, 去掉
        x.getColumns().removeIf(column -> column.getName() instanceof SQLIntegerLiteral);

        return super.visit(x);
    }

    @Override
    public boolean visit(SQLAlterIndexStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLDropIndexStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCreateMaterializedViewStatement x) {

        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLCreateMaterializedViewStatement.SQLColumn x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLAlterMaterializedViewStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDropMaterializedViewStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCreatePackageStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLAlterPackageStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDropPackageStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCreatePackageBodyStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLAlterPackageBodyStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDropPackageBodyStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCreateProcedureStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLAlterProcedureStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDropProcedureStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCreateRoleStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLAlterRoleStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDropRoleStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCreateRollbackSegmentStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDropRollbackSegmentStatement x) {
//        return super.visit(x);
//    }

    @Override
    public boolean visit(SQLCreateSchemaStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLAlterSchemaStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLDropSchemaStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCreateSequenceStatement x) {
        for (int i = x.getOptions().size() - 1; i >= 0; i--) {
            ISQLExpr option = x.getOptions().get(i);

//            if (!(option instanceof PPSQLSequenceOption)) {
//                x.getOptions().remove(i);
//            }
        }
        return true;
    }


    @Override
    public boolean visit(SQLAlterSequenceStatement x) {
        for (int i = x.getActions().size() - 1; i >= 0; i--) {
            ISQLExpr option = x.getActions().get(i);

//            if (!(option instanceof PPASSQLSequenceOption)) {
//                x.getOptions().remove(i);
//            }
        }
        return true;
    }

    @Override
    public boolean visit(SQLDropSequenceStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCreateSynonymStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLAlterSynonymStatement x) {
        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLDropSynonymStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCreateTableStatement x) {
//        return super.visit(x);
//    }

    @Override
    public boolean visit(SQLAlterTableStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLDropTableStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCreateTriggerStatement x) {
        if (x.getName() instanceof SQLPropertyExpr) {
            ISQLName name = ((SQLPropertyExpr) x.getName()).getName();
            boolean replace = SQLUtils.replaceInParent(x.getName(), name);
            if (replace) {

            }
        }

        return super.visit(x);
    }

    @Override
    public boolean visit(SQLAlterTriggerStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLDropTriggerStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCreateTypeStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLAlterTypeStatement x) {
        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLDropTypeStatement x) {
//        return super.visit(x);
//    }

    @Override
    public boolean visit(SQLCreateTypeBodyStatement x) {
        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLCreateTypeBodyStatement.SQLCreateBodyItem x) {
//        return super.visit(x);
//    }

    @Override
    public boolean visit(SQLAlterTypeBodyStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLDropTypeBodyStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCreateUserStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLAlterUserStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLDropUserStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCreateViewStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLAlterViewStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLDropViewStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLOpenStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCloseStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCallStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLInsertStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLRenameTableStatement x) {
        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLRenameTableStatement.Item x) {
//        return super.visit(x);
//    }

//    @Override
//    public boolean visit(SQLReplaceStatement x) {
//        return super.visit(x);
//    }

    @Override
    public boolean visit(SQLDeleteStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLUpdateStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLExplainStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLSelectStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLSelectIntoStatement x) {
        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLLockTableStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLLockTableStatement.SQLLockTableItem x) {
//        return super.visit(x);
//    }

    @Override
    public boolean visit(SQLContinueStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLExitStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLFetchStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLGotoStatement x) {
        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLMergeStatement x) {
//        return super.visit(x);
//    }

    @Override
    public boolean visit(SQLPipeRowStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLRaiseStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLIfStatement x) {
        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLIfStatement.SQLElseIf x) {
//        return super.visit(x);
//    }

    @Override
    public boolean visit(SQLIterateStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLLeaveStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLLoopStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLNullStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLOpenForStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLRepeatStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLReturnStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCaseStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCaseStatement.SQLCaseStatementWhenItem x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCaseStatement.SQLCaseStatementElseClause x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLForAllStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLForLoopStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLWhileLoopStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLWhileStatement x) {
        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLCommitStatement x) {
//        return super.visit(x);
//    }

//    @Override
//    public boolean visit(SQLRollbackStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLSavepointStatement x) {
//        return super.visit(x);
//    }

//    @Override
//    public boolean visit(SQLReleaseSavepointStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLSetConstraintStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLSetConstraintsStatement x) {
//        return super.visit(x);
//    }

//    @Override
//    public boolean visit(ISQLSetConstraintsStatement.SQLAllItem x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLSetTransactionStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLUseStatement x) {
//        return super.visit(x);
//    }


    // ------------------------- Data Types Start ----------------------------------------

//    @Override
//    public boolean visit(SQLBoolDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLBooleanDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLArrayDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLAssocArrayDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLMultisetDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNestedTableDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLVarrayDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLVaryingArrayDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDateDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLTimeDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDateTimeDataType x) {
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
//    public boolean visit(SQLJsonDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLMoneyDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLBigintDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLBinaryDoubleDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLBinaryFloatDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLBinaryIntegerDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLBitDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDecDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDecimalDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDoublePrecisionDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLFixedDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLFloatDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLIntDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLIntegerDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLMediumintDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNaturalDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNaturalnDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLPositiveDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLPositivenDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLSigntypeDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLSimpleDoubleDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLSimpleFloatDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLSimpleIntegerDataType x) {
//        return super.visit(x);
//    }

    @Override
    public boolean visit(SQLNumberDataType x) {
        List<ISQLExpr> arguments = x.getArguments();
        int argSize = arguments.size();

        if (argSize == 2) {
            ISQLExpr arg0 = arguments.get(0);
            ISQLExpr arg1 = arguments.get(1);

            if (arg0 instanceof SQLIntegerLiteral
                    && arg1 instanceof SQLIntegerLiteral
                    && ((SQLIntegerLiteral) arg0).getLongValue() < ((SQLIntegerLiteral) arg1).getLongValue()) {
                ((SQLIntegerLiteral) arg1).setValue(((SQLIntegerLiteral) arg0).getValue());
            }

        }


        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLNumericDataType x) {
//        return super.visit(x);
//    }

//    @Override
//    public boolean visit(SQLPlsIntegerDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLRealDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLSmallintDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLTinyintDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLObjectDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLRecordDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLRefCursorDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLRefDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLRowDataTypeImpl x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLSDOGeometryDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLSDOGeoRasterDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLSDOTopoGeometryDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLBFileDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLBlobDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCharacterDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCharacterLargeObjectDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCharacterVaryingDataType x) {
//        return super.visit(x);
//    }

    @Override
    public boolean visit(SQLCharDataType x) {
        if (x.getCharSizeUnit() != null) {
            x.setCharSizeUnit(null);
        }
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCharLargeObjectDataType x) {
        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLCharVaryingDataType x) {
//        return super.visit(x);
//    }

    @Override
    public boolean visit(SQLClobDataType x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLLongBlobDataType x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLLongDataType x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLLongRawDataType x) {
        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLNationalCharacterDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNationalCharacterLargeObjectDataType x) {
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
//    public boolean visit(SQLNCharDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNCharLargeObjectDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNCharVaryingDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNClobDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNVarchar2DataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLRawDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLRowIdDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLStringDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLTextDataType x) {
//        return super.visit(x);
//    }

    @Override
    public boolean visit(SQLURowIdDataType x) {
        List<ISQLExpr> arguments = x.getArguments();
        int argSize = arguments.size();

        ISQLDataType replaceDataType = null;
        if (argSize == 0) {
            replaceDataType = SQLTextDataType.of();

        } else {
            replaceDataType = SQLVarcharDataType.of();

        }

        boolean replace = SQLUtils.replaceInParent(x, replaceDataType);
        if (replace) {
            replaceDataType.accept(this);
        }
        return false;
    }

    @Override
    public boolean visit(SQLVarchar2DataType x) {
        if (x.getCharSizeUnit() != null) {
            x.setCharSizeUnit(null);
        }
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLVarcharDataType x) {
        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLUriTypeDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlTypeDataType x) {
//        return super.visit(x);
//    }

    @Override
    public boolean visit(SQLEnumDataType x) {
        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLPercentRowTypeDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLTableDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLPercentTypeDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDataTypeImpl x) {
//        return super.visit(x);
//    }

    // ------------------------- Data Types End ----------------------------------------

    // ------------------------- Identifier Start ----------------------------------------
    // ------------------------- Identifier End ----------------------------------------


    // ------------------------- Conditions Start ----------------------------------------

    // ------------------------- Conditions End ----------------------------------------


    // ------------------------- Functions Start ----------------------------------------

//    @Override
//    public boolean visit(SQLAggregateFunction x) {
//        // 聚合函数相互嵌套不支持
//        if (x.getParent() instanceof SQLAggregateFunction) {
//
//        }
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLAggregateFunction.SQLWithinGroupSpecification x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLAggregateFunction.SQLFilterClause x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDataMiningFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLDataMiningFunction.SQLIntoArgumentExpr x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLDataMiningFunction.SQLForArgumentExpr x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLDataMiningFunction.SQLCostMatrixClause x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLDataMiningFunction.SQLMiningAttributeClause x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLJsonFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLJsonFunction.SQLFormatJsonArgumentExpr x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLJsonFunction.SQLKeyValueArgumentExpr x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLJsonFunction.SQLJsonReturningClause x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLJsonFunction.SQLDefaultOnErrorExpr x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLJsonFunction.SQLDefaultOnEmptyExpr x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLJsonFunction.SQLJsonColumnsClause x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLJsonFunction.SQLJsonExistsColumn x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLJsonFunction.SQLJsonQueryColumn x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLJsonFunction.SQLJsonValueColumn x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLJsonFunction.SQLJsonNestedPathColumn x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLJsonFunction.SQLJsonOrdinalityColumn x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCastFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLExtractFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLFirstFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLLastFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLListAggFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLListAggFunction.SQLOnOverflowTruncateClause x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCubeTableFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCubeTableFunction.SQLArgumentExpr x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCubeTableFunction.SQLCubeTableOptionExpr x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLChrFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCollectionMethodInvocation x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLTreatFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLTrimFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLValidateConversionFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLTranslateUsingFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLWindowFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLOverClause x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLOverWindowNameClause x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLWindowSpecification x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLWindowFrameClause x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLWindowFrameClause.SQLWindowFrameBetween x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLWindowFrameClause.SQLWindowFramePreceding x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLWindowFrameClause.SQLWindowFrameFollowing x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlNameExprArgument x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlEvalNameExprArgument x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlPassingClause x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlCastFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlColAttValFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlParseFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlTableFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlTableFunction.SQLXmlNamespacesClause x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlTableFunction.SQLXmlTableOption x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlTableFunction.SQLXmlTableColumnByForOrdinality x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlTableFunction.SQLXmlTableColumnByDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlElementFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlElementFunction.SQLXmlAttributesClause x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlExistsFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlForestFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlPiFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlQueryFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlRootFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlRootFunction.SQLVersionArgument x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlSerializeFunction x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLMethodInvocation x) {
//
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(AbstractSQLFunction.SQLDefaultOnConversionError x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLStaticMethodInvocation x) {
//        return super.visit(x);
//    }


    // ------------------------- Functions End ----------------------------------------


    // ------------------------- Commons Expr Start ----------------------------------------
    @Override
    public boolean visit(SQLDefaultClause x) {

        // := => default
        if (x.getOperator() == SQLDefaultClause.Operator.VAR_ASSIGN_OP) {
            x.setOperator(SQLDefaultClause.Operator.DEFAULT);

        }

        // default '' => 删除, 不需要默认值
        if (x.getExpr() instanceof SQLStringLiteral
                && x.getParent() instanceof ISQLColumnDefinition) {
            boolean replace = SQLUtils.replaceInParent(x, null);
            if (replace) {
                // todo
            }
        }
        return true;
    }

    // ------------------------- column constraint Start ----------------------------------------

//    @Override
//    public boolean visit(SQLCheckColumnConstraint x) {
//        x.getOptions().clear();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNotNullColumnConstraint x) {
//        x.getOptions().clear();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNullColumnConstraint x) {
//        x.getOptions().clear();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLPrimaryKeyColumnConstraint x) {
//        x.getOptions().clear();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLReferencesColumnConstraint x) {
//        x.getOptions().clear();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLUniqueColumnConstraint x) {
//        x.getOptions().clear();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLScopeIsColumnConstraint x) {
//        x.getOptions().clear();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLWithRowIdColumnConstraint x) {
//        x.getOptions().clear();
//        return super.visit(x);
//    }
    // ------------------------- column constraint End ----------------------------------------


    // ------------------------- table constraint Start ----------------------------------------
//    @Override
//    public boolean visit(SQLCheckTableConstraint x) {
//        x.getOptions().clear();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLForeignKeyTableConstraint x) {
//        x.getOptions().clear();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLPrimaryKeyTableConstraint x) {
//        x.getOptions().clear();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLUniqueTableConstraint x) {
//        x.getOptions().clear();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLScopeForTableConstraint x) {
//        x.getOptions().clear();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLRefWithRowIdTableConstraint x) {
//        x.getOptions().clear();
//        return super.visit(x);
//    }

    // ------------------------- table constraint End ----------------------------------------


    // ------------------------- constraint option Start ----------------------------------------

    // ------------------------- constraint option  End ----------------------------------------

    // ------------------------- Commons Expr End ----------------------------------------


    // ------------------ Select Details Start ----------------------
    @Override
    public boolean visit(SQLGroupByClause x) {
        List<SQLGroupByClause.SQLGroupByItem> items = x.getItems();
        for (int i = items.size() - 1; i >= 0; i--) {
            SQLGroupByClause.SQLGroupByItem item = items.get(i);
            ISQLExpr expr = item.getExpr();

            // remove null
            if (expr instanceof SQLNullExpr) {
                items.remove(i);
                continue;
            }

            // '1' => to_char('1')
            if (expr instanceof SQLStringLiteral) {
                SQLMethodInvocation toChar = new SQLMethodInvocation(SQLKeyWord.TO_CHAR.upper);
                SQLUtils.replaceInParent(expr, toChar);
                continue;
            }

        }

        return true;
    }

    @Override
    public boolean visit(SQLOrderByClause x) {
        List<SQLOrderByItem> items = x.getItems();
        for (int i = items.size() - 1; i >= 0; i--) {
            SQLOrderByItem item = items.get(i);
            ISQLExpr expr = item.getSortKey();

            // remove null
            if (expr instanceof SQLNullExpr) {
                items.remove(i);
                continue;
            }

        }
        return true;
    }

    // ------------------ Select Details End ----------------------


    // ------------------ Sequence Details Start ----------------------

    @Override
    public boolean visit(SQLSequenceStartWithOption x) {
        if (x.getParent() instanceof SQLCreateSequenceStatement) {
            SQLCreateSequenceStatement createSequenceStatement = (SQLCreateSequenceStatement) x.getParent();
            SQLSequenceMaxValueOption maxValueOption = createSequenceStatement.maxValueOption();
            if (maxValueOption == null
                    || !(maxValueOption.getValue() instanceof SQLIntegerLiteral)) {
                return true;
            }
            if (!(x.getValue() instanceof SQLIntegerLiteral)) {
                return true;
            }

            boolean gt = ((SQLIntegerLiteral) x.getValue()).gt(((SQLIntegerLiteral)maxValueOption.getValue()).getValue());
            if (gt) {
                x.setValue(maxValueOption.getValue());
            }
        }
        return true;
    }

    @Override
    public boolean visit(SQLSequenceMaxValueOption x) {
        // value > Long.MAX_VALUE
        if (!(x.getValue() instanceof SQLIntegerLiteral)) {
            return true;
        }
        boolean gt = ((SQLIntegerLiteral) x.getValue()).gt(Long.MAX_VALUE);
        if (gt) {
            x.setValue(SQLIntegerLiteral.max());
        }
        return true;
    }

    @Override
    public boolean visit(SQLSequenceMinValueOption x) {
        // value < Long.MIN_VALUE

        if (!(x.getValue() instanceof SQLIntegerLiteral)) {
            return true;
        }
        boolean lt = ((SQLIntegerLiteral) x.getValue()).lt(Long.MIN_VALUE);
        if (lt) {
            x.setValue(SQLIntegerLiteral.min());
        }
        return true;
    }

    // ------------------ Sequence Details End ----------------------
}
