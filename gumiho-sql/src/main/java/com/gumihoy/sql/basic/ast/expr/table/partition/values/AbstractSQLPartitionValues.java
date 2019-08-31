package com.gumihoy.sql.basic.ast.expr.table.partition.values;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

import java.util.ArrayList;
import java.util.List;

/**
 * partition_definition 中的 VALUES
 * https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 * <p>
 * range_values_clause、list_values_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html
 *
 * @author kent onCondition 2018/4/9.
 */
public abstract class AbstractSQLPartitionValues extends AbstractSQLExpr implements ISQLPartitionValues {

    protected final List<ISQLExpr> values = new ArrayList<>();

    @Override
    public AbstractSQLPartitionValues clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void cloneTo(AbstractSQLPartitionValues x) {
        super.cloneTo(x);

        for (ISQLExpr value : values) {
            ISQLExpr valueClone = value.clone();
            x.addValue(valueClone);
        }
    }

    @Override
    public List<ISQLExpr> getValues() {
        return values;
    }

    public void addValue(ISQLExpr value) {
        if (value == null) {
            return;
        }
        setChildParent(value);
        this.values.add(value);
    }
}
