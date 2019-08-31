package com.gumihoy.sql.translate.visitor;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLAllColumnExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLPropertyExpr;
import com.gumihoy.sql.basic.ast.expr.select.ISQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectItem;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.SQLSubQueryTableReference;
import com.gumihoy.sql.basic.ast.expr.select.limit.ISQLLimitClause;
import com.gumihoy.sql.basic.ast.expr.select.limit.SQLLimitOffsetClause;
import com.gumihoy.sql.dialect.mysql.ast.expr.select.MySQLSelectQuery;
import com.gumihoy.sql.dialect.oracle.ast.expr.select.OracleSelectQuery;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.util.SQLUtils;

import java.util.List;

/**
 * 优化
 * 1、子查询 和 父查询 合并（父是*、子查询是*、父子selectItem相同） && (只有limit or 没有limit)
 * 2、
 *
 * @author kent on 2018/5/18.
 */
public class SQLOptimizationASTVisitor extends SQLASTTransformVisitor {

    public SQLOptimizationASTVisitor() {
    }

    public SQLOptimizationASTVisitor(SQLTransformConfig config) {
        super(config);
    }


    // ------------------------- Expr Start ----------------------------------------

    // ------------------------- Expr End ----------------------------------------


    // ------------------------- Select Details Start ----------------------------------------
    @Override
    public boolean visit(SQLSelectQuery x) {
        optimizationQuery(x);
        return true;
    }

    @Override
    public boolean visit(OracleSelectQuery x) {
        optimizationQuery(x);
        return true;
    }

    @Override
    public boolean visit(MySQLSelectQuery x) {
        optimizationQuery(x);
        return true;
    }

    /**
     * 子查询 和 父查询 合并（父是*、子查询是*、父子selectItem相同） && (只有limit or 没有limit)
     */
    public void optimizationQuery(SQLSelectQuery x) {
        List<SQLSelectItem> selectItems = x.getSelectItems();

        // * / x.*
        boolean allColumn = false;

        if (selectItems.size() == 1) {
            ISQLExpr expr = selectItems.get(0).getExpr();
            if (expr instanceof SQLAllColumnExpr) {
                allColumn = true;
            } else if (expr instanceof SQLPropertyExpr
                    && ((SQLPropertyExpr) expr).getName() instanceof SQLAllColumnExpr) {
                allColumn = true;
            }
        }

        boolean subQueryTableReference = x.getFromClause() != null
                && x.getFromClause().getTableReference() instanceof SQLSubQueryTableReference;

        if (!subQueryTableReference) {
            return;
        }

        ISQLSelectQuery subQuery = ((SQLSubQueryTableReference) x.getFromClause().getTableReference()).getSubQuery();

        boolean subSelectQueryQuery = subQuery instanceof SQLSelectQuery;

        boolean subAllColumn = false;
        if (!allColumn
                && subSelectQueryQuery) {

            List<SQLSelectItem> subSelectItems = ((SQLSelectQuery) subQuery).getSelectItems();

            if (subSelectItems.size() == 1) {
                ISQLExpr expr = subSelectItems.get(0).getExpr();
                if (expr instanceof SQLAllColumnExpr) {
                    subAllColumn = true;
                } else if (expr instanceof SQLPropertyExpr
                        && ((SQLPropertyExpr) expr).getName() instanceof SQLAllColumnExpr) {
                    subAllColumn = true;
                }
            }

            if (!allColumn
                    && !subAllColumn) {

            }

        }


        if (allColumn
                && subSelectQueryQuery
                && x.getGroupByClause() == null
                && x.getOrderByClause() == null) {

            ISQLLimitClause limitClause = x.getLimitClause();

            if (limitClause instanceof SQLLimitOffsetClause) {
                ISQLLimitClause subQueryLimitClause = subQuery.getLimitClause();

                if (subQueryLimitClause == null) {
                    subQuery.setLimitClause(limitClause);

                } else if (subQueryLimitClause instanceof SQLLimitOffsetClause) {
                    if (((SQLLimitOffsetClause) limitClause).getRowCountExpr() != null) {

                        if (((SQLLimitOffsetClause) subQueryLimitClause).getRowCountExpr() != null) {

                        } else {
                            ((SQLLimitOffsetClause) subQueryLimitClause).setRowCountExpr(((SQLLimitOffsetClause) limitClause).getRowCountExpr());
                        }

                    }

                    if (((SQLLimitOffsetClause) limitClause).getOffsetExpr() != null) {

                        if (((SQLLimitOffsetClause) subQueryLimitClause).getOffsetExpr() != null) {

                        } else {
                            ((SQLLimitOffsetClause) subQueryLimitClause).setOffsetExpr(((SQLLimitOffsetClause) limitClause).getOffsetExpr());
                        }
                    }

                }
            }

            SQLUtils.replaceInParent(x, subQuery);
        }


    }

    // ------------------------- Select Details End ----------------------------------------


}
