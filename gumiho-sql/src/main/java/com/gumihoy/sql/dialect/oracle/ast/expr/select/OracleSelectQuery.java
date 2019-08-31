package com.gumihoy.sql.dialect.oracle.ast.expr.select;


import com.gumihoy.sql.basic.ast.expr.common.ISQLHierarchicalQueryClause;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectQuery;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.oracle.ast.expr.IOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
 *
 * @author kent on 2018/5/7.
 */
public class OracleSelectQuery extends SQLSelectQuery implements IOracleExpr {

    protected ISQLHierarchicalQueryClause hierarchicalQueryClause;
//
//    protected OracleSQLModelClause modelClause;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor instanceof IOracleASTVisitor) {
            accept0((IOracleASTVisitor) visitor);
        } else {
            throw new IllegalArgumentException("not support visitor type : " + visitor.getClass().getName());
        }
    }

    @Override
    public void accept0(IOracleASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, withClause);
            this.acceptChild(visitor, selectItems);
            this.acceptChild(visitor, selectTargetItems);
            this.acceptChild(visitor, fromClause);
            this.acceptChild(visitor, whereClause);
            this.acceptChild(visitor, hierarchicalQueryClause);
            this.acceptChild(visitor, groupByClause);
//            this.acceptChild(visitor, modelClause);

            this.acceptChild(visitor, orderByClause);
            this.acceptChild(visitor, limitClause);
            this.acceptChild(visitor, lockClause);
        }
    }

    @Override
    public OracleSelectQuery clone() {
        OracleSelectQuery x = new OracleSelectQuery();

        this.cloneTo(x);
        return x;
    }


    public void cloneTo(OracleSelectQuery x) {
        super.cloneTo(x);

//        SQLHierarchicalQueryClause hierarchicalQueryClauseClone = this.hierarchicalQueryClause.clone();
//        x.setHierarchicalQueryClause(hierarchicalQueryClauseClone);
//
//        OracleSQLModelClause modelClauseClone = x.modelClause.clone();

    }

    public ISQLHierarchicalQueryClause getHierarchicalQueryClause() {
        return hierarchicalQueryClause;
    }

    public void setHierarchicalQueryClause(ISQLHierarchicalQueryClause hierarchicalQueryClause) {
        setChildParent(hierarchicalQueryClause);
        this.hierarchicalQueryClause = hierarchicalQueryClause;
    }
//
//    public OracleSQLModelClause getModelClause() {
//        return modelClause;
//    }
//
//    public void setModelClause(OracleSQLModelClause modelClause) {
//        this.modelClause = modelClause;
//    }
}
