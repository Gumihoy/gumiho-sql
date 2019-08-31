package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/11.
 */
public interface ISQLAlterTablePartitionAction extends ISQLAlterTableAction {
    @Override
    ISQLAlterTablePartitionAction clone();


    /**
     * FOR ( name [,name]... )
     */
    class SQLForItem extends AbstractSQLExpr {

        protected final List<ISQLExpr> names = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, names);
//            }
        }

        @Override
        public SQLForItem clone() {
            SQLForItem x = new SQLForItem();
            return x;
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            boolean replace = replaceInList(names, source, target, this);
            if (replace) {
                return true;
            }
            return false;
        }

        public List<ISQLExpr> getNames() {
            return names;
        }

        public void addName(ISQLExpr name) {
            if (name == null) {
                return;
            }
            setChildParent(name);
            this.names.add(name);
        }
    }
}
