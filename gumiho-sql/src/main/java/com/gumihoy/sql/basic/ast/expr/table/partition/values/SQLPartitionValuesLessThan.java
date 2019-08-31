package com.gumihoy.sql.basic.ast.expr.table.partition.values;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * VALUES LESS THAN ( expr [,expr]...)
 * <p>
 * partition_definition 中的 VALUES
 * https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 * <p>
 * <p>
 * VALUES LESS THAN ( expr [,expr]...)
 * <p>
 * range_values_clause、list_values_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html
 *
 * @author kent onCondition 2018/4/9.
 */
public class SQLPartitionValuesLessThan extends AbstractSQLPartitionValues {


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, values);
        }
    }

    @Override
    public SQLPartitionValuesLessThan clone() {
        SQLPartitionValuesLessThan x = new SQLPartitionValuesLessThan();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLPartitionValuesLessThan x) {
        super.cloneTo(x);
    }
}
