package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * IMPORT PARTITION {partition_names | ALL} TABLESPACE
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 *
 *
 * @author kent on 2018/6/4.
 */
public class SQLImportPartitionDefinition extends AbstractSQLExpr implements ISQLAlterTableAction {

    protected final List<ISQLExpr> names = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, names);
//        }
    }

    @Override
    public SQLImportPartitionDefinition clone() {
        SQLImportPartitionDefinition x = new SQLImportPartitionDefinition();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLImportPartitionDefinition x) {
        super.cloneTo(x);
        for (ISQLExpr name : this.names) {
            ISQLExpr nameClone = name.clone();
            x.addName(nameClone);
        }
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
