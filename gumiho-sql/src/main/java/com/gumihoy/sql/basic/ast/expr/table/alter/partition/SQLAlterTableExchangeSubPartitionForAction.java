package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * EXCHANGE PARTITION FOR (partition_name [, partition_name]) WITH TABLE [ schema. ] table
 * [ { INCLUDING | EXCLUDING } INDEXES ] [ { WITH | WITHOUT } VALIDATION ] [ exceptions_clause ] [ update_index_clauses [ parallel_clause ] ] [ CASCADE ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/12.
 */
public class SQLAlterTableExchangeSubPartitionForAction extends AbstractSQLAlterTableExchangePartitionAction implements ISQLAlterTablePartitionAction {

    protected final List<ISQLExpr> names = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, names);
//            this.acceptChild(visitor, table);
//            this.acceptChild(visitor, exceptionsClause);
//            this.acceptChild(visitor, updateIndexClause);
//            this.acceptChild(visitor, parallelClause);
//        }
    }

    @Override
    public SQLAlterTableExchangeSubPartitionForAction clone() {
        SQLAlterTableExchangeSubPartitionForAction x = new SQLAlterTableExchangeSubPartitionForAction();
        super.cloneTo(x);

        for (ISQLExpr name : this.names) {
            ISQLExpr nameClone = name.clone();
            x.addName(nameClone);
        }
        return x;
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
