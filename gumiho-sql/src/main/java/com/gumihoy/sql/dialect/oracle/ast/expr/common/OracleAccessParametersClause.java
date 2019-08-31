package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.select.ISQLSelectQuery;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * ACCESS PARAMETERS { (opaque_format_spec) | USING CLOB subquery }
 *
 * external_table_data_props
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 * @author kent on 2018/6/28.
 */
public class OracleAccessParametersClause extends AbstractOracleExpr {

    protected ISQLExpr item;

    @Override
    public void accept0(IOracleASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, item);
//        }
    }

    @Override
    public OracleAccessParametersClause clone() {
        OracleAccessParametersClause x = new OracleAccessParametersClause();
        ISQLExpr itemClone = this.item.clone();
        x.setItem(itemClone);
        return x;
    }

    public ISQLExpr getItem() {
        return item;
    }

    public void setItem(ISQLExpr item) {
        setChildParent(item);
        this.item = item;
    }

    /**
     * USING CLOB subquery
     */
    public static class UsingClobClause extends AbstractOracleExpr {

        protected ISQLSelectQuery subQuery;

        public UsingClobClause(ISQLSelectQuery subQuery) {
            setSubQuery(subQuery);
        }

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, subQuery);
//            }
        }

        @Override
        public UsingClobClause clone() {
            ISQLSelectQuery subQueryClone = subQuery.clone();
            UsingClobClause x = new UsingClobClause(subQueryClone);
            return x;
        }

        public ISQLSelectQuery getSubQuery() {
            return subQuery;
        }

        public void setSubQuery(ISQLSelectQuery subQuery) {
            setChildParent(subQuery);
            this.subQuery = subQuery;
        }
    }
}
