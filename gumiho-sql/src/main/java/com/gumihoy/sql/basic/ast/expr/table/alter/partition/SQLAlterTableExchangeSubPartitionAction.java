package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * EXCHANGE PARTITION partition_name WITH TABLE tbl_name [{WITH|WITHOUT} VALIDATION]
 * https://dev.mysql.com/doc/refman/5.6/en/alter-table.html
 *
 * EXCHANGE SUBPARTITION partition_name WITH TABLE [ schema. ] table [ { INCLUDING | EXCLUDING } INDEXES ] [ { WITH | WITHOUT } VALIDATION ] [ exceptions_clause ] [ update_index_clauses [ parallel_clause ] ] [ CASCADE ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/12.
 */
public class SQLAlterTableExchangeSubPartitionAction extends AbstractSQLAlterTableExchangePartitionAction implements ISQLAlterTablePartitionAction {

    protected ISQLName name;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, table);
//            this.acceptChild(visitor, exceptionsClause);
//            this.acceptChild(visitor, updateIndexClause);
//            this.acceptChild(visitor, parallelClause);
//        }
    }

    @Override
    public SQLAlterTableExchangeSubPartitionAction clone() {
        SQLAlterTableExchangeSubPartitionAction x = new SQLAlterTableExchangeSubPartitionAction();
        super.cloneTo(x);
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
