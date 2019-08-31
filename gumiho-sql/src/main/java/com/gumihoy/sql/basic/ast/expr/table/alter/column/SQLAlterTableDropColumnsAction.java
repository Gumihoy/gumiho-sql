package com.gumihoy.sql.basic.ast.expr.table.alter.column;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * DROP [ COLUMN ] <column name> <drop behavior>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#drop%20column%20definition
 * <p>
 * DROP [COLUMN] col_name
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 * <p>
 * DROP [ COLUMN ] [ IF EXISTS ] column_name [ RESTRICT | CASCADE ]
 * https://www.postgresql.org/docs/devel/static/sql-altertable.html
 * <p>
 * <p>
 * DROP [COLUMN] columns+=expr iAlterTableDropColumnActionOption* (CHECKPOINT checkPoint=expr)?
 * DROP LEFT_PAREN columns+=expr (COMMA columns+=expr)* RIGHT_PAREN iAlterTableDropColumnActionOption* (CHECKPOINT checkPoint=expr)?
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableDropColumnsAction extends AbstractSQLAlterTableDropColumnAction implements ISQLAlterTableDropColumnAction {

    protected final List<ISQLExpr> names = new ArrayList<>();

//    protected SQLCascadeType behavior;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, names);
            this.acceptChild(visitor, checkPoint);
        }
    }

    @Override
    public SQLAlterTableDropColumnsAction clone() {
        SQLAlterTableDropColumnsAction x = new SQLAlterTableDropColumnsAction();

        for (ISQLExpr name : this.names) {
            ISQLExpr nameClone = name.clone();
            x.addName(nameClone);
        }


//        x.behavior = this.behavior;
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

//    public SQLCascadeType getBehavior() {
//        return behavior;
//    }
//
//    public void setBehavior(SQLCascadeType behavior) {
//        this.behavior = behavior;
//    }
}
