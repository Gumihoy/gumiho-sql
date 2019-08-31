package com.gumihoy.sql.basic.ast.expr.table.alter.column;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * @author kent on 2018/7/11.
 */
public interface ISQLAlterTableColumnAction extends ISQLAlterTableAction {
    @Override
    ISQLAlterTableColumnAction clone();


    /**
     * FIRST
     * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
     */
    class SQLFirstPropertyExpr extends AbstractSQLExpr {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//            }
        }
    }

    /**
     * AFTER name
     * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
     */
    class SQLAfterPropertyExpr extends AbstractSQLExpr {
        protected ISQLName name;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, name);
//            }
        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            setChildParent(name);
            this.name = name;
        }
    }
}
