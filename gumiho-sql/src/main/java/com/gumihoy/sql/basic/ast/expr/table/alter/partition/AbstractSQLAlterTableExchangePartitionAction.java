package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.ISQLUpdateIndexClause;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.SQLExceptionsClause;

/**
 * EXCHANGE PARTITION partition_name WITH TABLE tbl_name [{WITH|WITHOUT} VALIDATION]
 * https://dev.mysql.com/doc/refman/5.6/en/alter-table.html
 * <p>
 * EXCHANGE PARTITION partition_name WITH TABLE [ schema. ] table [ { INCLUDING | EXCLUDING } INDEXES ] [ { WITH | WITHOUT } VALIDATION ] [ exceptions_clause ] [ update_index_clauses [ parallel_clause ] ] [ CASCADE ]
 * <p>
 * exchange_partition_subpart
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/12.
 */
public abstract class AbstractSQLAlterTableExchangePartitionAction extends AbstractSQLExpr implements ISQLAlterTablePartitionAction {

    protected ISQLExpr table;
//    protected SQLIncludingType indexes;
//    protected SQLWithType validation;
    protected SQLExceptionsClause exceptionsClause;
    protected ISQLUpdateIndexClause updateIndexClause;
//    protected ISQLParallelClause parallelClause;
    protected boolean cascade;


    @Override
    public AbstractSQLAlterTableExchangePartitionAction clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void cloneTo(AbstractSQLAlterTableExchangePartitionAction x) {
        super.cloneTo(x);

    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        return false;
    }


    public ISQLExpr getTable() {
        return table;
    }

    public void setTable(ISQLExpr table) {
        setChildParent(table);
        this.table = table;
    }

//    public SQLIncludingType getIndexes() {
//        return indexes;
//    }
//
//    public void setIndexes(SQLIncludingType indexes) {
//        this.indexes = indexes;
//    }
//
//    public SQLWithType getValidation() {
//        return validation;
//    }
//
//    public void setValidation(SQLWithType validation) {
//        this.validation = validation;
//    }
//
//    public SQExceptionsClause getExceptionsClause() {
//        return exceptionsClause;
//    }
//
//    public void setExceptionsClause(SQExceptionsClause exceptionsClause) {
//        this.exceptionsClause = exceptionsClause;
//    }
//
//    public ISQLUpdateIndexClause getUpdateIndexClause() {
//        return updateIndexClause;
//    }
//
//    public void setUpdateIndexClause(ISQLUpdateIndexClause updateIndexClause) {
//        setChildParent(updateIndexClause);
//        this.updateIndexClause = updateIndexClause;
//    }
//
//    public ISQLParallelClause getParallelClause() {
//        return parallelClause;
//    }
//
//    public void setParallelClause(ISQLParallelClause parallelClause) {
//        setChildParent(parallelClause);
//        this.parallelClause = parallelClause;
//    }

    public boolean isCascade() {
        return cascade;
    }

    public void setCascade(boolean cascade) {
        this.cascade = cascade;
    }
}
