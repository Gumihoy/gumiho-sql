package com.gumihoy.sql.basic.ast.expr.table.partition.values;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * VALUES IN ( expr [,expr]...)
 * <p>
 * partition_definition 中的 VALUES
 * https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 *
 * @author kent onCondition 2018/4/9.
 */
public class SQLPartitionValuesIn extends AbstractSQLPartitionValues {


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, values);
        }
    }

    @Override
    public SQLPartitionValuesIn clone() {
        SQLPartitionValuesIn x = new SQLPartitionValuesIn();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLPartitionValuesIn x) {
        super.cloneTo(x);
    }

}
