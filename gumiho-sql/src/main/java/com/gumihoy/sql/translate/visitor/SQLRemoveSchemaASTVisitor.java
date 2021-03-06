package com.gumihoy.sql.translate.visitor;

import com.gumihoy.sql.basic.ast.expr.identifier.SQLPropertyExpr;
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
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.util.SQLUtils;

/**
 * Remove Schema visitor
 * 移除 对象、字段等中的Schema
 *
 * @author kent on 2018/5/18.
 */
public class SQLRemoveSchemaASTVisitor extends SQLASTTransformVisitor {

    public SQLRemoveSchemaASTVisitor() {
    }

    public SQLRemoveSchemaASTVisitor(SQLTransformConfig config) {
        super(config);
    }

//    @Override
//    public boolean visit(SQLSetCharacterSetStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLSetCharSetStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLSetDefaultRoleStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLSetNameStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLSetPasswordStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLSetVariableStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLCreateAnalyticStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLAlterAnalyticStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLDropAnalyticStatement x) {
//        return true;
//    }

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

//    @Override
//    public boolean visit(SQLCreateMaterializedViewStatement.SQLColumn x) {
//        return true;
//    }

    @Override
    public boolean visit(SQLAlterMaterializedViewStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropMaterializedViewStatement x) {
        return true;
    }

//    @Override
//    public boolean visit(SQLCreateMaterializedViewLogStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLAlterMaterializedViewLogStatement x) {
//        return true;
//    }
//
//    @Override
//    public boolean visit(SQLDropMaterializedViewLogStatement x) {
//        return true;
//    }

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

//    @Override
//    public boolean visit(SQLCreateRollbackSegmentStatement x) {
//        return true;
//    }
//
//    @Override
//    public boolean visit(SQLDropRollbackSegmentStatement x) {
//        return true;
//    }

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

//    @Override
//    public boolean visit(SQLCreateServerStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLAlterServerStatement x) {
//        return true;
//    }
//
//    @Override
//    public boolean visit(SQLDropServerStatement x) {
//        return true;
//    }

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
        if (x.getName() instanceof SQLPropertyExpr) {
            SQLUtils.replaceInParent(x.getName(), ((SQLPropertyExpr) x.getName()).getName());
        }
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

//    @Override
//    public boolean visit(SQLCreateTypeStatement.SQLExternalNameClause x) {
//        return true;
//    }

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

    @Override
    public boolean visit(SQLOpenStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCloseStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCallStatement x) {
        return true;
    }

//    @Override
//    public boolean visit(SQLLoadDataStatement x) {
//        return true;
//    }
//
//    @Override
//    public boolean visit(SQLLoadXmlStatement x) {
//        return true;
//    }

    @Override
    public boolean visit(SQLInsertStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLRenameTableStatement x) {
        return true;
    }

//    @Override
//    public boolean visit(SQLRenameTableStatement.Item x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLReplaceStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLDoStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLHandlerOpenStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLHandlerReadStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLHandlerCloseStatement x) {
//        return true;
//    }

    @Override
    public boolean visit(SQLDeleteStatement x) {
        return true;
    }

//    @Override
//    public boolean visit(SQLDeleteStatement.SQLUsingClause x) {
//        return true;
//    }

    @Override
    public boolean visit(SQLUpdateStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLExplainStatement x) {
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
    public boolean visit(SQLLockTableStatement x) {
        return true;
    }

//    @Override
//    public boolean visit(SQLLockTableStatement.SQLLockTableItem x) {
//        return true;
//    }
//
//    @Override
//    public boolean visit(SQLUnLockTablesStatement x) {
//        return true;
//    }

    @Override
    public boolean visit(SQLContinueStatement x) {
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
    public boolean visit(SQLGotoStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLMergeStatement x) {
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
    public boolean visit(SQLIfStatement x) {
        return true;
    }

//    @Override
//    public boolean visit(SQLIfStatement.SQLElseIf x) {
//        return true;
//    }

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
    public boolean visit(SQLRepeatStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLReturnStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCaseStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLCaseStatement.SQLCaseStatementWhenItem x) {
        return true;
    }

    @Override
    public boolean visit(SQLCaseStatement.SQLCaseStatementElseClause x) {
        return true;
    }

    @Override
    public boolean visit(SQLForAllStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLForLoopStatement x) {
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

//    @Override
//    public boolean visit(SQLCommitStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLRollbackStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLSavepointStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLReleaseSavepointStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLSetConstraintStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLSetConstraintsStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(ISQLSetConstraintsStatement.SQLAllItem x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLSetTransactionStatement x) {
//        return true;
//    }

//    @Override
//    public boolean visit(SQLUseStatement x) {
//        return true;
//    }
}
