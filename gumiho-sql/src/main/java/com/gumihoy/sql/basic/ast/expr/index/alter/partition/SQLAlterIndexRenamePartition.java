package com.gumihoy.sql.basic.ast.expr.index.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * RENAME
 *   { PARTITION partition | SUBPARTITION subpartition }
 * TO new_name
 * <p>
 * rename_index_partition
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-INDEX.html#GUID-D8F648E7-8C07-4C89-BB71-862512536558
 *
 * @author kent on 2019-08-02.
 */
public class SQLAlterIndexRenamePartition extends AbstractSQLExpr implements ISQLAlterIndexPartitionAction {

    protected ISQLExpr name;
    protected ISQLExpr newName;
    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, newName);
        }
    }

    @Override
    public SQLAlterIndexRenamePartition clone() {
        return null;
    }


    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        this.name = name;
    }

    public ISQLExpr getNewName() {
        return newName;
    }

    public void setNewName(ISQLExpr newName) {
        this.newName = newName;
    }
}
