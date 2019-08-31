package com.gumihoy.sql.translate.visitor;

import com.gumihoy.sql.basic.ast.expr.table.element.ISQLTableElement;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLColumnDefinition;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLLikeClause;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLVirtualColumnDefinition;
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
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValues;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValuesIn;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValuesLessThan;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValuesLessThanMaxValue;
import com.gumihoy.sql.basic.ast.expr.table.partitionset.SQLPartitionsetByList;
import com.gumihoy.sql.basic.ast.expr.table.partitionset.SQLPartitionsetByRange;
import com.gumihoy.sql.basic.ast.expr.table.partitionset.SQLPartitionsetDefinition;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLCreateTableStatement;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;

/**
 * rename column
 * 修改字段（创建表字段、创建视图字段等）
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html
 *
 * @author kent on 2018/5/18.
 */
public class SQLRenameColumnASTVisitor extends SQLASTTransformVisitor {

    public SQLRenameColumnASTVisitor(SQLTransformConfig config) {
        super(config);
    }

    @Override
    public boolean visit(SQLCreateTableStatement x) {
        SQLTransformConfig.TableMapping tableMapping = config.findTableMapping(x.getName());
        if (tableMapping == null) {
            return false;
        }

        for (int i = x.getTableElements().size() - 1; i >= 0; i--) {

            ISQLTableElement tableElement = x.getTableElements().get(i);

            if (!(tableElement instanceof SQLColumnDefinition)) {
                continue;
            }

            SQLColumnDefinition column = (SQLColumnDefinition) tableElement;

            // modify
            SQLTransformConfig.ColumnMapping columnMapping = tableMapping.findColumnMapping(column.getColumnName());
            if (columnMapping != null) {
                column.setColumnName(columnMapping.targetName);
            }

            // remove
            boolean isRemoveColumn = tableMapping.isRemoveColumn(column.getColumnName());
            if (isRemoveColumn) {
                x.getTableElements().remove(i);
            }
        }


        return false;
    }


    // ------------------------- column constraint Start ----------------------------------------
    @Override
    public boolean visit(SQLCheckColumnConstraint x) {
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
    public boolean visit(SQLPrimaryKeyColumnConstraint x) {
        return true;
    }

    @Override
    public boolean visit(SQLReferencesColumnConstraint x) {

        return true;
    }

    @Override
    public boolean visit(SQLUniqueColumnConstraint x) {

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
    // ------------------------- column constraint End ----------------------------------------


    // ------------------------- table constraint Start ----------------------------------------
    @Override
    public boolean visit(SQLCheckTableConstraint x) {

        return true;
    }

    @Override
    public boolean visit(SQLForeignKeyTableConstraint x) {

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
    public boolean visit(SQLScopeForTableConstraint x) {

        return true;
    }

    @Override
    public boolean visit(SQLRefWithRowIdTableConstraint x) {
        return true;
    }
    // ------------------------- table constraint End ----------------------------------------


    // ------------------ Table Details Start ----------------------
    @Override
    public boolean visit(SQLColumnDefinition x) {

        return true;
    }

    @Override
    public boolean visit(SQLVirtualColumnDefinition x) {

        return true;
    }

    @Override
    public boolean visit(SQLLikeClause x) {

        return true;
    }

    @Override
    public boolean visit(SQLPartitionByConsistentHash x) {
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
    public boolean visit(SQLPartitionByList x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionByListColumns x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionByRange x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionByRangeColumns x) {
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
    public boolean visit(SQLPartitionDefinition x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionsetByList x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionsetByRange x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionsetDefinition x) {
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
    public boolean visit(SQLSubPartitionByList x) {
        return true;
    }

    @Override
    public boolean visit(SQLSubPartitionByRange x) {
        return true;
    }

    @Override
    public boolean visit(SQLSubpartitionTemplate x) {
        return true;
    }

    @Override
    public boolean visit(SQLSubPartitionDefinition x) {
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
    public boolean visit(SQLPartitionValuesIn x) {
        return true;
    }

    @Override
    public boolean visit(SQLPartitionValues x) {
        return true;
    }
// ------------------ Table Details Start ----------------------
}
