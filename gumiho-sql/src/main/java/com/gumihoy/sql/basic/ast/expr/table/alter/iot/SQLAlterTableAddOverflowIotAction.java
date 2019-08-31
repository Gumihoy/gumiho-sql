package com.gumihoy.sql.basic.ast.expr.table.alter.iot;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionDefinition;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * ADD OVERFLOW [ segment_attributes_clause ] [ ( PARTITION [ segment_attributes_clause ] [, PARTITION [ segment_attributes_clause ] ]...)]
 * <p>
 * add_overflow_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/17.
 */
public class SQLAlterTableAddOverflowIotAction extends AbstractSQLExpr implements ISQLAlterTableIotAction {

    protected final List<ISQLExpr> properties = new ArrayList<>();

    protected final List<SQLPartitionDefinition> partitions = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, properties);
//            this.acceptChild(visitor, partitions);
//        }
    }

    @Override
    public SQLAlterTableAddOverflowIotAction clone() {
        SQLAlterTableAddOverflowIotAction x = new SQLAlterTableAddOverflowIotAction();
        return x;
    }


    public List<ISQLExpr> getProperties() {
        return properties;
    }

    public void addProperty(ISQLExpr property) {
        if (property == null) {
            return;
        }
        setChildParent(property);
        this.properties.add(property);
    }

    public List<SQLPartitionDefinition> getPartitions() {
        return partitions;
    }
    public void addPartition(SQLPartitionDefinition partition) {
        if (partition == null) {
            return;
        }
        setChildParent(partition);
        this.partitions.add(partition);
    }

}
