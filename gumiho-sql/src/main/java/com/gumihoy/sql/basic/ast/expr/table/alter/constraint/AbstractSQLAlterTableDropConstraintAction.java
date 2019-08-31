package com.gumihoy.sql.basic.ast.expr.table.alter.constraint;

import com.gumihoy.sql.basic.ast.enums.SQLDropBehavior;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;

/**
 * DROP { { PRIMARY KEY | UNIQUE (column [, column ]...) } [ CASCADE ] [ { KEEP | DROP } INDEX ] | CONSTRAINT constraint_name [ CASCADE ] } [ ONLINE ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/11.
 */
public abstract class AbstractSQLAlterTableDropConstraintAction extends AbstractSQLExpr implements ISQLAlterTableDropTableConstraintAction {

    protected SQLDropBehavior dropBehavior;
    protected SQLKeepIndexType index;
    protected boolean online;

    @Override
    public AbstractSQLAlterTableDropConstraintAction clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }


    public void cloneTo(AbstractSQLAlterTableDropConstraintAction x) {
        super.cloneTo(x);
        x.dropBehavior = this.dropBehavior;
        x.index = this.index;
        x.online = this.online;
    }

    public SQLKeepIndexType getIndex() {
        return index;
    }

    public void setIndex(SQLKeepIndexType index) {
        this.index = index;
    }


    public SQLDropBehavior getDropBehavior() {
        return dropBehavior;
    }

    public void setDropBehavior(SQLDropBehavior dropBehavior) {
        this.dropBehavior = dropBehavior;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }



    public enum SQLKeepIndexType implements ISQLASTEnum {
        KEEP_INDEX("keep index", "KEEP INDEX"),
        DROP_INDEX("drop index", "DROP INDEX"),
        ;
        public final String lower;
        public final String upper;


        SQLKeepIndexType(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }

        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }
    }

}
