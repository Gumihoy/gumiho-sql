package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * REORGANIZE PARTITION partition_names INTO (partition_definitions)
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 *
 *
 * @author kent on 2018/6/4.
 */
public class SQLExchangePartitionDefinition extends AbstractSQLExpr implements ISQLAlterTableAction {

    protected ISQLExpr name;
    protected ISQLExpr tableName;


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
    }

    @Override
    public SQLExchangePartitionDefinition clone() {
        SQLExchangePartitionDefinition x = new SQLExchangePartitionDefinition();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLExchangePartitionDefinition x) {
        super.cloneTo(x);

        ISQLExpr nameClone = this.name.clone();
        x.setName(nameClone);
    }

    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }
}
