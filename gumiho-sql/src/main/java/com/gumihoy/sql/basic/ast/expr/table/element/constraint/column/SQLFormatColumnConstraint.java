package com.gumihoy.sql.basic.ast.expr.table.element.constraint.column;

import com.gumihoy.sql.basic.ast.expr.table.element.constraint.AbstractSQLConstraint;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * COLUMN_FORMAT {FIXED|DYNAMIC|DEFAULT}
 * https://dev.mysql.com/doc/refman/5.6/en/create-table.html
 *
 * @author kent on 2018/7/31.
 */
public class SQLFormatColumnConstraint extends AbstractSQLConstraint implements ISQLColumnConstraint {

//    protected FormatType formatType;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        visitor.visit(this);
    }

    @Override
    public SQLFormatColumnConstraint clone() {
        SQLFormatColumnConstraint x = new SQLFormatColumnConstraint();
//        x.formatType = this.formatType;
        return x;
    }

//    public FormatType getFormatType() {
//        return formatType;
//    }
//
//    public void setFormatType(FormatType formatType) {
//        this.formatType = formatType;
//    }

    /**
     * FIXED|DYNAMIC|DEFAULT
     */
//    public enum FormatType implements ISQLEnum {
//        FIXED(SQLReserved.FIXED),
//        DYNAMIC(SQLReserved.DYNAMIC),
//        DEFAULT(SQLReserved.DEFAULT);
//
//        public final SQLReserved name;
//
//        FormatType(SQLReserved name) {
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
