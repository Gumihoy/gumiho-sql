package com.gumihoy.sql.basic.ast.expr.table.alter.constraint;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * DROP KEY index_name
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableDropKeyConstraintAction extends AbstractSQLExpr implements ISQLAlterTableDropTableConstraintAction {

    protected ISQLName name;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
    }

    @Override
    public SQLAlterTableDropKeyConstraintAction clone() {
        SQLAlterTableDropKeyConstraintAction x = new SQLAlterTableDropKeyConstraintAction();

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

        return x;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }
}
