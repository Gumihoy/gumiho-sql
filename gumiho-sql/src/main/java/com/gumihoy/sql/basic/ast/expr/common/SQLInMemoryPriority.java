package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * PRIORITY { NONE | LOW | MEDIUM | HIGH | CRITICAL }
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/25.
 */
public class SQLInMemoryPriority extends AbstractSQLExpr implements ISQLInMemoryAttribute {

    protected  SQLInMemoryPriorityType priorityType;

    @Override
    public void accept0(ISQLASTVisitor visitor) {
//        visitor.visit(this);
    }

    @Override
    public SQLInMemoryPriority clone() {
        SQLInMemoryPriority x = new SQLInMemoryPriority();

        x.priorityType = this.priorityType;
        return x;
    }


    public SQLInMemoryPriorityType getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(SQLInMemoryPriorityType priorityType) {
        this.priorityType = priorityType;
    }

    public enum SQLInMemoryPriorityType {

//        NONE(SQLReserved.NONE),
//        LOW(SQLReserved.LOW),
//        MEDIUM(SQLReserved.MEDIUM),
//        HIGH(SQLReserved.HIGH),
//        CRITICAL(SQLReserved.CRITICAL),;
//
//        public final SQLReserved name;
//
//        SQLInMemoryPriorityType(SQLReserved name) {
//            this.name = name;
//        }
//
//        @Override
//        public String toString() {
//            return name.upper;
//        }
    }
}
