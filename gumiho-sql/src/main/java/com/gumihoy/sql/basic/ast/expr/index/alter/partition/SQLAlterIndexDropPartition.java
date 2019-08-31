package com.gumihoy.sql.basic.ast.expr.index.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * DROP PARTITION partition_name
 * <p>
 * drop_index_partition
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-INDEX.html#GUID-D8F648E7-8C07-4C89-BB71-862512536558
 *
 * @author kent on 2019-08-02.
 */
public class SQLAlterIndexDropPartition extends AbstractSQLExpr implements ISQLAlterIndexPartitionAction {

    protected ISQLExpr name;

    public SQLAlterIndexDropPartition(ISQLExpr name) {
        setName(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLAlterIndexDropPartition clone() {
        return null;
    }


    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        this.name = name;
    }

}
