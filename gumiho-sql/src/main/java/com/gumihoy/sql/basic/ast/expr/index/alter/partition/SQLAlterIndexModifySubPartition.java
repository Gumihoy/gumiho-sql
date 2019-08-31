package com.gumihoy.sql.basic.ast.expr.index.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * MODIFY SUBPARTITION subpartition
 * { UNUSABLE
 * | allocate_extent_clause
 * | deallocate_unused_clause
 * }
 * <p>
 * modify_index_partition
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-INDEX.html#GUID-D8F648E7-8C07-4C89-BB71-862512536558
 *
 * @author kent on 2019-08-02.
 */
public class SQLAlterIndexModifySubPartition extends AbstractSQLExpr implements ISQLAlterIndexPartitionAction {

    protected ISQLExpr name;
    protected ISQLExpr option;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, option);
        }
    }

    @Override
    public SQLAlterIndexModifySubPartition clone() {
        return null;
    }


    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        this.name = name;
    }

    public ISQLExpr getOption() {
        return option;
    }

    public void setOption(ISQLExpr option) {
        this.option = option;
    }

    /**
     * UNUSABLE
     */
    public static class SQLUnusableOption extends AbstractSQLExpr {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLUnusableOption clone() {
            return new SQLUnusableOption();
        }
    }

}
