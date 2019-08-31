package com.gumihoy.sql.basic.ast.expr.table.alter.constraint;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * DROP CONSTRAINT <constraint name> <drop behavior>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#drop%20table%20constraint%20definition
 * <p>
 * <p>
 * DROP CONSTRAINT [ IF EXISTS ]  constraint_name [ RESTRICT | CASCADE ]
 * https://www.postgresql.org/docs/devel/static/sql-altertable.html
 * <p>
 * DROP UNIQUE (column [, column ]...) [ CASCADE ] [ { KEEP | DROP } INDEX ] [ ONLINE ]
 * drop_constraint_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableDropUniqueConstraintAction extends AbstractSQLAlterTableDropConstraintAction implements ISQLAlterTableAction {

    protected final List<ISQLExpr> columns = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, columns);
        }
    }

    @Override
    public SQLAlterTableDropUniqueConstraintAction clone() {
        SQLAlterTableDropUniqueConstraintAction x = new SQLAlterTableDropUniqueConstraintAction();
        this.cloneTo(x);

        for (ISQLExpr column : this.columns) {
            ISQLExpr columnClone = column.clone();
            x.addColumn(columnClone);
        }
        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        boolean replace = replaceInList(columns, source, target, this);
        if (replace) {
            return true;
        }
        return false;
    }

    public List<ISQLExpr> getColumns() {
        return columns;
    }

    public void addColumn(ISQLExpr column) {
        if (column == null) {
            return;
        }
        setChildParent(column);
        this.columns.add(column);
    }
}
