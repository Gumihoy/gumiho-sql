package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * COALESCE PARTITION number
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 *
 *
 * @author kent on 2018/6/4.
 */
public class SQLCoalescePartitionDefinition extends AbstractSQLExpr implements ISQLAlterTableAction {

    protected ISQLExpr num;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, num);
//        }
    }

    @Override
    public SQLCoalescePartitionDefinition clone() {
        SQLCoalescePartitionDefinition x = new SQLCoalescePartitionDefinition();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLCoalescePartitionDefinition x) {
        super.cloneTo(x);

        ISQLExpr numClone = this.num.clone();
        x.setNum(numClone);
    }

    public ISQLExpr getNum() {
        return num;
    }

    public void setNum(ISQLExpr num) {
        setChildParent(num);
        this.num = num;
    }
}
