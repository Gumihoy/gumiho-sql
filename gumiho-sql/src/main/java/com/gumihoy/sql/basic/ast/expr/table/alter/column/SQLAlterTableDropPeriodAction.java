package com.gumihoy.sql.basic.ast.expr.table.alter.column;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

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
public class SQLAlterTableDropPeriodAction extends AbstractSQLAlterTableDropColumnAction implements ISQLAlterTableDropColumnAction {

    protected ISQLExpr name;

//    protected SQLCascadeType behavior;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, checkPoint);
        }
    }

    @Override
    public SQLAlterTableDropPeriodAction clone() {
        SQLAlterTableDropPeriodAction x = new SQLAlterTableDropPeriodAction();

        ISQLExpr nameClone = name.clone();
        x.setName(nameClone);


//        x.behavior = this.behavior;
        return x;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
//        boolean replace = replaceInList(name, source, target, this);
//        if (replace) {
//            return true;
//        }
        return false;
    }


    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }
    //    public SQLCascadeType getBehavior() {
//        return behavior;
//    }
//
//    public void setBehavior(SQLCascadeType behavior) {
//        this.behavior = behavior;
//    }
}
