package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.enums.SQLCharSizeUnit;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.ISQLUpdateIndexClause;

import java.util.ArrayList;
import java.util.List;

/**
 * TRUNCATE PARTITION {partition_names | ALL}
 * https://dev.mysql.com/doc/refman/5.6/en/alter-table.html
 *
 * TRUNCATE partition_extended_names [ { DROP [ ALL ] | REUSE } STORAGE ] [ CASCADE ] [ update_index_clauses [ parallel_clause ] ]
 * <p>
 * partition_extended_names: { PARTITION | PARTITIONS } partition | { FOR ( partition_key_value [, partition_key_value ]... ) } [, partition | { FOR ( partition_key_value [, partition_key_value ]... ) } ]...
 * truncate_partition_subpart
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public abstract class AbstractSQLAlterTableTruncatePartitionAction extends AbstractSQLExpr implements ISQLAlterTablePartitionAction {

    protected final List<ISQLExpr> names = new ArrayList<>();
    protected SQLStorageType storageType;
    protected boolean cascade;
    protected ISQLUpdateIndexClause updateIndexClause;
//    protected ISQLParallelClause parallelClause;

    @Override
    public AbstractSQLAlterTableTruncatePartitionAction clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void cloneTo(AbstractSQLAlterTableTruncatePartitionAction x) {
        super.cloneTo(x);
        for (ISQLExpr name : names) {
            ISQLExpr nameClone = name.clone();
            x.addName(nameClone);
        }

        x.cascade = this.cascade;

        if (this.updateIndexClause != null) {
            ISQLUpdateIndexClause updateIndexClauseClone = this.updateIndexClause.clone();
            x.setUpdateIndexClause(updateIndexClauseClone);
        }

//        if (this.parallelClause != null) {
//            ISQLParallelClause parallelClauseClone = this.parallelClause.clone();
//            x.setParallelClause(parallelClauseClone);
//        }
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


    public SQLStorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(SQLStorageType storageType) {
        this.storageType = storageType;
    }

    public boolean isCascade() {
        return cascade;
    }

    public void setCascade(boolean cascade) {
        this.cascade = cascade;
    }

    public ISQLUpdateIndexClause getUpdateIndexClause() {
        return updateIndexClause;
    }

    public void setUpdateIndexClause(ISQLUpdateIndexClause updateIndexClause) {
        setChildParent(updateIndexClause);
        this.updateIndexClause = updateIndexClause;
    }

//    public ISQLParallelClause getParallelClause() {
//        return parallelClause;
//    }
//
//    public void setParallelClause(ISQLParallelClause parallelClause) {
//        setChildParent(parallelClause);
//        this.parallelClause = parallelClause;
//    }



    public enum SQLStorageType implements ISQLASTEnum {

        DROP_STORAGE("drop storage", "DROP STORAGE"),
        DROP_ALL_STORAGE("drop all storage", "DROP ALL STORAGE"),
        REUSE_STORAGE("reuse storage", "REUSE STORAGE"),;

        public final String lower;
        public final String upper;

        SQLStorageType(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public static SQLCharSizeUnit of(String name) {
            for (SQLCharSizeUnit unit : SQLCharSizeUnit.values()) {
                if (unit.lower.equalsIgnoreCase(name)) {
                    return unit;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return upper;
        }


        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }
    }
}
