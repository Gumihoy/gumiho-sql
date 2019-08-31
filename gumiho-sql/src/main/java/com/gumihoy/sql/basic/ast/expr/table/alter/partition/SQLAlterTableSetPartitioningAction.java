package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * SET PARTITIONING { AUTOMATIC | MANUAL }
 * <p>
 * alter_automatic_partitioning
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableSetPartitioningAction extends AbstractSQLExpr implements ISQLAlterTableAction {

//    protected SQLType setPartition;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        visitor.visit(this);
    }

    @Override
    public SQLAlterTableSetPartitioningAction clone() {
        SQLAlterTableSetPartitioningAction x = new SQLAlterTableSetPartitioningAction();
        this.cloneTo(x);
//        x.setPartition = this.setPartition;
        return x;
    }


//    public SQLType getSetPartition() {
//        return setPartition;
//    }
//
//    public void setSetPartition(SQLType setPartition) {
//        this.setPartition = setPartition;
//    }





//    public enum SQLType implements ISQLEnum {
//        AUTOMATIC(SQLReserved.AUTOMATIC),
//        MANUAL(SQLReserved.MANUAL);
//
//        public final SQLReserved name;
//
//        SQLType(SQLReserved name) {
//            this.name = name;
//        }
//
//        @Override
//        public SQLReserved getName() {
//            return name;
//        }
//    }
}
