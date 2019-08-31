package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.method.aggreate.AbstractSQLAggregateFunction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * LISTAGG( [ ALL ]measure_expr [, 'delimiter'] [listagg_overflow_clause] )
 * WITHIN GROUP (order_by_clause) [OVER query_partition_clause]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/LISTAGG.html#GUID-B6E50D8E-F467-425B-9436-F7F8BF38D466
 *
 * @author kent on 2018/5/22.
 */
public class SQLListAggFunction extends AbstractSQLAggregateFunction {

    protected ISQLExpr listAggOverflowClause;

    public SQLListAggFunction() {
//        super(SQLReserved.LISTAGG.ofExpr());
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//            this.acceptChild(visitor, listAggOverflowClause);
//            this.acceptChild(visitor, withinGroup);
//            this.acceptChild(visitor, overClause);
//        }
    }


    public ISQLExpr getListAggOverflowClause() {
        return listAggOverflowClause;
    }

    public void setListAggOverflowClause(ISQLExpr listAggOverflowClause) {
        setChildParent(listAggOverflowClause);
        this.listAggOverflowClause = listAggOverflowClause;
    }


    public interface SQLListAggOverflowClause extends ISQLExpr {
    }

    /**
     * ON OVERFLOW TRUNCATE indicator=[ 'truncation-indicator' ]  withCount=[ { WITH | WITHOUT } COUNT ]
     */
    public static class SQLOnOverflowTruncateClause extends AbstractSQLExpr implements SQLListAggOverflowClause {

        protected ISQLExpr indicator;

//        protected SQLReserved withCount;


        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, indicator);
//            }
        }

        @Override
        public SQLOnOverflowTruncateClause clone() {
            SQLOnOverflowTruncateClause x = new SQLOnOverflowTruncateClause();

            ISQLExpr indicatorClone = this.indicator.clone();
            x.setIndicator(indicatorClone);

//            x.withCount = this.withCount;

            return x;
        }

        public ISQLExpr getIndicator() {
            return indicator;
        }

        public void setIndicator(ISQLExpr indicator) {
            setChildParent(indicator);
            this.indicator = indicator;
        }

//        public SQLReserved getWithCount() {
//            return withCount;
//        }
//
//        public void setWithCount(SQLReserved withCount) {
//            this.withCount = withCount;
//        }
    }

}
