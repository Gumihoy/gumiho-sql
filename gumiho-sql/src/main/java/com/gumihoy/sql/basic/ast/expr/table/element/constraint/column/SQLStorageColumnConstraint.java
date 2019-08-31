package com.gumihoy.sql.basic.ast.expr.table.element.constraint.column;

import com.gumihoy.sql.basic.ast.expr.table.element.constraint.AbstractSQLConstraint;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * STORAGE {DISK|MEMORY|DEFAULT}
 * https://dev.mysql.com/doc/refman/5.6/en/create-table.html
 *
 * @author kent on 2018/7/31.
 */
public class SQLStorageColumnConstraint extends AbstractSQLConstraint implements ISQLColumnConstraint {

//    protected StorageType storageType;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        visitor.visit(this);
    }

    @Override
    public SQLStorageColumnConstraint clone() {
        SQLStorageColumnConstraint x = new SQLStorageColumnConstraint();
//        x.storageType = this.storageType;
        return x;
    }

//    public StorageType getStorageType() {
//        return storageType;
//    }
//
//    public void setStorageType(StorageType storageType) {
//        this.storageType = storageType;
//    }

    /**
     * DISK|MEMORY|DEFAULT
     */
//    public enum StorageType implements ISQLEnum {
//        DISK(SQLReserved.FIXED),
//        MEMORY(SQLReserved.DYNAMIC),
//        DEFAULT(SQLReserved.DEFAULT);
//
//        public final SQLReserved name;
//
//        StorageType(SQLReserved name) {
//            this.name = name;
//        }
//
//        @Override
//        public SQLReserved getName() {
//            return name;
//        }
//
//        @Override
//        public String toString() {
//            return name.upper;
//        }
//    }
}
