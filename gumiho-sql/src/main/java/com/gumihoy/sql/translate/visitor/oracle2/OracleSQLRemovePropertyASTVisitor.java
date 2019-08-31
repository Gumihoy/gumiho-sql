package com.gumihoy.sql.translate.visitor.oracle2;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLCharacterSetOptionExpr;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.*;
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
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValuesLessThan;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValuesLessThanMaxValue;
import com.gumihoy.sql.basic.ast.expr.table.partitionset.SQLPartitionsetByList;
import com.gumihoy.sql.basic.ast.expr.table.partitionset.SQLPartitionsetByRange;
import com.gumihoy.sql.basic.ast.expr.table.partitionset.SQLPartitionsetDefinition;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLCreateTableStatement;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;

/**
 * Remove physical property
 * Remove table property
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html
 *
 * @author kent on 2018/5/18.
 */
public class OracleSQLRemovePropertyASTVisitor extends SQLASTTransformVisitor {

    public OracleSQLRemovePropertyASTVisitor() {
    }

    public OracleSQLRemovePropertyASTVisitor(SQLTransformConfig config) {
        super(config);
    }

    @Override
    public boolean visit(SQLCreateTableStatement x) {

        // 清除属性
        for (int i = x.getProperties().size() - 1; i >= 0; i--) {
            ISQLExpr property = x.getProperties().get(i);
            if (
//                    property instanceof SQLEngineOptionExpr
                    property instanceof SQLCharacterSetOptionExpr
//                    || property instanceof SQLCommentOptionExpr
            ) {
                continue;
            }
            x.getProperties().remove(i);
        }

//        x.setReadOnly(null);
//        x.setIndexingOn(null);
//        x.setCache(null);
        x.setResultCache(null);
        x.setParallelClause(null);
//        x.setRowDependencies(null);
        x.getEnableDisableClauses().clear();
        x.setRowMovementClause(null);
        x.setFlashbackArchiveClause(null);
        x.setRowArchival(false);
//        x.setForExchangeWithTableClause(null);

        return super.visit(x);
    }


    // ------------------------- column constraint Start ----------------------------------------
    @Override
    public boolean visit(SQLCheckColumnConstraint x) {
        x.getOptions().clear();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLNotNullColumnConstraint x) {
        x.getOptions().clear();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLNullColumnConstraint x) {
        x.getOptions().clear();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPrimaryKeyColumnConstraint x) {
        x.getOptions().clear();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLReferencesColumnConstraint x) {
        x.getOptions().clear();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLUniqueColumnConstraint x) {
        x.getOptions().clear();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLScopeIsColumnConstraint x) {
        x.getOptions().clear();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLWithRowIdColumnConstraint x) {
        x.getOptions().clear();
        return super.visit(x);
    }
    // ------------------------- column constraint End ----------------------------------------


    // ------------------------- table constraint Start ----------------------------------------
    @Override
    public boolean visit(SQLCheckTableConstraint x) {
        x.getOptions().clear();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLForeignKeyTableConstraint x) {
        x.getOptions().clear();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPrimaryKeyTableConstraint x) {
        x.getOptions().clear();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLUniqueTableConstraint x) {
        x.getOptions().clear();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLScopeForTableConstraint x) {
        x.getOptions().clear();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLRefWithRowIdTableConstraint x) {
        x.getOptions().clear();
        return super.visit(x);
    }
    // ------------------------- table constraint End ----------------------------------------


    // ------------------ Table Details Start ----------------------

    @Override
    public boolean visit(SQLPartitionByConsistentHash x) {
//        x.setTableSpaceClause(null);
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPartitionByHash x) {
//        x.setCompression(null);
//        x.setOverflowStoreInClause(null);
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPartitionByKey x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPartitionByList x) {
        return super.visit(x);
    }


    @Override
    public boolean visit(SQLPartitionByRange x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPartitionByReference x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPartitionBySystem x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPartitionDefinition x) {
        x.getOptions().clear();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPartitionsetByList x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPartitionsetByRange x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPartitionsetDefinition x) {
        x.getOptions().clear();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLSubPartitionByHash x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLSubPartitionByKey x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLSubPartitionByList x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLSubPartitionByRange x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLSubpartitionTemplate x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLSubPartitionDefinition x) {
        x.getOptions().clear();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPartitionValuesLessThan x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPartitionValuesLessThanMaxValue x) {
        return super.visit(x);
    }

    // ------------------ Table Details End ----------------------
}
