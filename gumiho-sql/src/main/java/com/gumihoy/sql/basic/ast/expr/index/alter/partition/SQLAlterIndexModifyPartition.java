package com.gumihoy.sql.basic.ast.expr.index.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

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
public class SQLAlterIndexModifyPartition extends AbstractSQLExpr implements ISQLAlterIndexPartitionAction {

    protected ISQLExpr name;
    protected List<ISQLExpr> options = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, options);
        }
    }

    @Override
    public SQLAlterIndexModifyPartition clone() {
        return null;
    }


    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        this.name = name;
    }

    public List<ISQLExpr> getOptions() {
        return options;
    }

    public void setOptions(List<ISQLExpr> options) {
        this.options = options;
    }

    public void addOption(ISQLExpr option) {
        if (option == null) {
            return;
        }
        setChildParent(option);
        this.options.add(option);
    }


    /**
     * PARAMETERS ('ODCI_parameters')
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/modify_index_partition.html
     */
    public static class SQLParametersOption extends AbstractSQLExpr {
        protected ISQLExpr parameter;

        public SQLParametersOption(ISQLExpr parameter) {
            setParameter(parameter);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, parameter);
            }
        }

        public ISQLExpr getParameter() {
            return parameter;
        }

        public void setParameter(ISQLExpr parameter) {
            this.parameter = parameter;
        }
    }

    /**
     * COALESCE [ CLEANUP ]
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/modify_index_partition.html
     */
    public static class SQLCoalesceOption extends AbstractSQLExpr {
        protected boolean cleanup;

        public SQLCoalesceOption(boolean cleanup) {
            this.cleanup = cleanup;
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        public boolean isCleanup() {
            return cleanup;
        }

        public void setCleanup(boolean cleanup) {
            this.cleanup = cleanup;
        }
    }

    /**
     * UPDATE BLOCK REFERENCES
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/modify_index_partition.html
     */
    public static class SQLUpdateBlockReferencesOption extends AbstractSQLExpr {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    /**
     * UNUSABLE
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/modify_index_partition.html
     */
    public static class SQLUnusableOption extends AbstractSQLExpr {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }
    }


}
