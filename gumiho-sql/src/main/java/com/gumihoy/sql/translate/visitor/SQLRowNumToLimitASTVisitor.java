package com.gumihoy.sql.translate.visitor;


import com.gumihoy.sql.basic.ast.enums.SQLBinaryOperator;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLHierarchicalQueryClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLFromClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLWhereClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLWithClause;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLAllColumnExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLPropertyExpr;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLIntegerLiteral;
import com.gumihoy.sql.basic.ast.expr.operator.SQLBinaryOperatorExpr;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLRowNumExpr;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectItem;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.limit.ISQLLimitClause;
import com.gumihoy.sql.basic.ast.expr.select.limit.SQLLimitOffsetClause;
import com.gumihoy.sql.dialect.oracle.ast.expr.select.OracleSelectQuery;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.util.SQLUtils;

import java.util.List;

/**
 * ROWNUM to limit
 * ROWNUM 转换 Limit
 *
 * @author kent on 2018/5/18.
 */
public class SQLRowNumToLimitASTVisitor extends SQLASTTransformVisitor {

    private Context context;
    protected boolean removeSelectItemRowNum = true;

    public SQLRowNumToLimitASTVisitor() {
    }

    public SQLRowNumToLimitASTVisitor(SQLTransformConfig config) {
        super(config);
    }

    // ------------------------- Expr Start ----------------------------------------
    @Override
    public boolean visit(SQLRowNumExpr x) {
        if (removeSelectItemRowNum
                && x.getParent() instanceof SQLSelectItem
                && ((SQLSelectItem) x.getParent()).getExpr() == x) {
            SQLUtils.replaceInParent((SQLSelectItem) x.getParent(), null);
            return false;
        }
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLBinaryOperatorExpr x) {
        if (context == null
                || context.query == null) {
            return true;
        }

        ISQLExpr left = x.getLeft();
        SQLBinaryOperator op = x.getOperator();
        ISQLExpr right = x.getRight();

        boolean isRowNum = SQLUtils.isRowNum(left, context.query);

        if (isRowNum) {
            boolean replace = false;
            if (op == SQLBinaryOperator.LESS_THAN) {
                replace = SQLUtils.replaceInParent(x, null);
                if (replace) {
                    context.setLimit(SQLIntegerLiteral.decrement(right));
                    // 如果存在 offset, 重新计算 rowCount
                    context.recalculateLimit();
                }
            } else if (op == SQLBinaryOperator.LESS_THAN_OR_EQUALS) {
                if (SQLUtils.replaceInParent(x, null)) {
                    context.setLimit(right);
                    // 如果存在 offset, 重新计算 rowCount
                    context.recalculateLimit();
                }
            } else if (op == SQLBinaryOperator.EQ) {
                if (SQLUtils.replaceInParent(x, null)) {
                    context.setLimit(right);
                    // 如果存在 offset, 重新计算 rowCount
                    context.recalculateLimit();
                }
            } else if (op == SQLBinaryOperator.GREATER_THAN) {
                if (SQLUtils.replaceInParent(x, null)) {
                    context.setOffset(right);
                    // 如果存在 offset, 重新计算 rowCount
                    context.recalculateLimit();
                }
            } else if (op == SQLBinaryOperator.GREATER_THAN_OR_EQUALS) {
                if (SQLUtils.replaceInParent(x, null)) {
                    context.setOffset(SQLIntegerLiteral.decrement(right));
                    // 如果存在 offset, 重新计算 rowCount
                    context.recalculateLimit();
                }
            }

            if (replace) {
                return false;
            }

        }


        return super.visit(x);
    }
    // ------------------------- Expr End ----------------------------------------


    // ------------------------- Select Details Start ----------------------------------------
    @Override
    public boolean visit(SQLSelectQuery x) {
        context = new Context(context, x);

        return false;
    }

    @Override
    public boolean visit(OracleSelectQuery x) {
        context = new Context(context, x);

        SQLWithClause withClause = x.getWithClause();
        if (withClause != null) {
            withClause.accept(this);
        }

        SQLWhereClause whereClause = x.getWhereClause();
        if (whereClause != null) {
            whereClause.accept(this);
        }

        SQLFromClause fromClause = x.getFromClause();
        if (fromClause != null) {
            fromClause.accept(this);
        }

        ISQLHierarchicalQueryClause hierarchicalQueryClause = x.getHierarchicalQueryClause();
        if (hierarchicalQueryClause != null) {
            hierarchicalQueryClause.accept(this);
        }

        // 移除 ROWNUM
        List<SQLSelectItem> selectItems = x.getSelectItems();
        for (int i = 0; i < selectItems.size(); i++) {
            selectItems.get(i).accept(this);
        }

        // 子查询 合并
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


        return false;
    }

    // ------------------------- Select Details End ----------------------------------------


    private static class Context {
        public final Context parent;
        public final SQLSelectQuery query;

        public Context(Context parent, SQLSelectQuery query) {
            this.parent = parent;
            this.query = query;
        }

        public void setLimit(ISQLExpr x) {
            if (query == null) {
                return;
            }

            // 最小值为: 0
            if (x instanceof SQLIntegerLiteral) {
                long val = ((SQLIntegerLiteral) x).getLongValue();
                if (val < 0) {
                    x = SQLIntegerLiteral.of(0);
                }
            }

            ISQLLimitClause limit = query.getLimitClause();
            if (limit == null) {
                limit = new SQLLimitOffsetClause(x);
            }

            if (limit instanceof SQLLimitOffsetClause) {
                query.setLimitClause(limit);
            }
        }

        public void setOffset(ISQLExpr x) {
            if (query == null) {
                return;
            }

            if (x instanceof SQLIntegerLiteral) {
                long val = ((SQLIntegerLiteral) x).getLongValue();
                if (val < 0) {
                    x = SQLIntegerLiteral.of(0);
                }
            }

            ISQLLimitClause limit = query.getLimitClause();
            if (limit == null) {
                limit = new SQLLimitOffsetClause(x);
            }
            if (limit instanceof SQLLimitOffsetClause) {
                query.setLimitClause(limit);
            }
        }

        public void recalculateLimit() {

            if (query.getLimitClause() == null
                    || !(query.getLimitClause() instanceof SQLLimitOffsetClause)) {
                return;
            }

            SQLLimitOffsetClause limit = (SQLLimitOffsetClause) query.getLimitClause();

            if (limit.getRowCountExpr() != null
                    && limit.getOffsetExpr() != null) {

                ISQLExpr rowCountExpr = SQLIntegerLiteral.subtract(limit.getRowCountExpr(), limit.getOffsetExpr());
                limit.setRowCountExpr(rowCountExpr);

            }
        }

    }


}
