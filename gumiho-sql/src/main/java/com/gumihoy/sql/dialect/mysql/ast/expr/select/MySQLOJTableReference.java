package com.gumihoy.sql.dialect.mysql.ast.expr.select;

import com.gumihoy.sql.basic.ast.expr.select.AbstractSQLTableReference;
import com.gumihoy.sql.basic.ast.expr.select.ISQLTableReference;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.mysql.visitor.IMySQLASTVisitor;

/**
 *
 * { OJ table_reference }
 *
 * https://dev.mysql.com/doc/refman/5.7/en/join.html
 *
 * @author kent on 2018/5/3.
 */
public class MySQLOJTableReference extends AbstractSQLTableReference implements IMySQLTableReference {

    protected ISQLTableReference tableReference;

    public MySQLOJTableReference() {
    }

    public MySQLOJTableReference(ISQLTableReference tableReference) {
        setTableReference(tableReference);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor instanceof IMySQLASTVisitor) {
            this.accept0((IMySQLASTVisitor) visitor);
        } else {
            throw new UnsupportedOperationException(getClass().getName());
        }
    }

    @Override
    public void accept0(IMySQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, tableReference);
        }
    }

    @Override
    public MySQLOJTableReference clone() {
        MySQLOJTableReference x = new MySQLOJTableReference();

        this.cloneTo(x);
        return x;
    }


    public void cloneTo(MySQLOJTableReference x) {
        super.cloneTo(x);

        ISQLTableReference tableReferenceClone = this.tableReference.clone();
        x.setTableReference(tableReferenceClone);
    }

    public ISQLTableReference getTableReference() {
        return tableReference;
    }

    public void setTableReference(ISQLTableReference tableReference) {
        setChildParent(tableReference);
        this.tableReference = tableReference;
    }
}
