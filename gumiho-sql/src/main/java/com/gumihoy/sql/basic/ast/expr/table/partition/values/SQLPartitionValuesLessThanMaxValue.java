package com.gumihoy.sql.basic.ast.expr.table.partition.values;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * VALUES LESS THAN MAXVALUE
 * <p>
 * partition_definition 中的 VALUES
 * https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 *
 * @author kent onCondition 2018/4/9.
 */
public class SQLPartitionValuesLessThanMaxValue extends AbstractSQLPartitionValues {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLPartitionValuesLessThanMaxValue clone() {
        SQLPartitionValuesLessThanMaxValue x = new SQLPartitionValuesLessThanMaxValue();
        this.cloneTo(x);
        return x;
    }

}
