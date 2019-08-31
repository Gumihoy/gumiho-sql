package com.gumihoy.sql.translate.visitor.oracle2;

import com.gumihoy.sql.basic.ast.enums.SQLBinaryOperator;
import com.gumihoy.sql.basic.ast.enums.SQLUnionOperator;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLNullExpr;
import com.gumihoy.sql.basic.ast.expr.condition.SQLIsCondition;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLPropertyExpr;
import com.gumihoy.sql.basic.ast.expr.operator.SQLBinaryOperatorExpr;
import com.gumihoy.sql.basic.ast.expr.select.ISQLTableReference;
import com.gumihoy.sql.basic.ast.expr.select.SQLJoinTableReference;
import com.gumihoy.sql.basic.ast.expr.select.SQLObjectNameTableReference;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectItem;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectUnionQuery;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Intersect To Inner Join
 * Minus To Left Join
 *
 * @author kent on 2018/5/18.
 */
public class OracleSQLIntersectOrMinusToJoinASTVisitor extends SQLASTTransformVisitor {

    protected ConcurrentHashMap<String, AtomicInteger> Alias_Map = new ConcurrentHashMap<>();

    public OracleSQLIntersectOrMinusToJoinASTVisitor() {
    }

    public OracleSQLIntersectOrMinusToJoinASTVisitor(SQLTransformConfig config) {
        super(config);
    }

    // ------------------------- Expr Start ----------------------------------------

    // ------------------------- Expr End ----------------------------------------


    // ------------------------- Select Details Start ----------------------------------------

    @Override
    public boolean visit(SQLSelectUnionQuery x) {
        SQLUnionOperator op = x.getOperator();

        if (op != SQLUnionOperator.MINUS
                && op != SQLUnionOperator.INTERSECT) {
            return true;
        }

        if (x.getLeft() instanceof SQLSelectUnionQuery) {
            x.getLeft().accept(this);
        }

        SQLSelectQuery leftSubQuery = (SQLSelectQuery) x.getLeft();
        SQLSelectQuery rightSubQuery = (SQLSelectQuery) x.getRight();

        List<SQLSelectItem> leftSelectItems = leftSubQuery.getSelectItems();
        List<SQLSelectItem> rightSelectItems = rightSubQuery.getSelectItems();

        if (leftSelectItems.size() != rightSelectItems.size()) {
            return true;
        }


        List<ISQLName> leftColumns = new ArrayList<>();
        List<ISQLName> rightColumns = new ArrayList<>();

        for (SQLSelectItem item : leftSelectItems) {
            ISQLExpr expr = item.getExpr();
            if (!(expr instanceof ISQLName)) {
                return true;
            }
            leftColumns.add((ISQLName) expr);
        }

        for (SQLSelectItem item : rightSelectItems) {
            ISQLExpr expr = item.getExpr();
            if (!(expr instanceof ISQLName)) {
                return true;
            }
            rightColumns.add((ISQLName) expr);
        }

        String leftAlias = null;
        if (leftSubQuery.getFromClause().getTableReference().getAlias() != null) {
            leftAlias = leftSubQuery.getFromClause().getTableReference().getAlias().getName();
        }

        String rightAlias = null;
        if (rightSubQuery.getFromClause().getTableReference().getAlias() != null) {
            rightAlias = rightSubQuery.getFromClause().getTableReference().getAlias().getName();
        }

        if (leftAlias == null
                && leftSubQuery.getFromClause().getTableReference() instanceof SQLObjectNameTableReference) {
            String tableName = ((SQLObjectNameTableReference) rightSubQuery.getFromClause().getTableReference()).getTableName();
            String newAlias = tableName.substring(0, 1).toLowerCase();
            AtomicInteger aliasCount = Alias_Map.get(newAlias);
            if (aliasCount == null) {
                aliasCount = new AtomicInteger(1);
            }
            Alias_Map.put(newAlias, aliasCount);
            newAlias += aliasCount.getAndIncrement();
            ((SQLObjectNameTableReference) leftSubQuery.getFromClause().getTableReference()).setAlias(newAlias);
            leftAlias = newAlias;
        }

        if (rightAlias == null
                && rightSubQuery.getFromClause().getTableReference() instanceof SQLObjectNameTableReference) {
            String tableName = ((SQLObjectNameTableReference) rightSubQuery.getFromClause().getTableReference()).getTableName();
            String newAlias = tableName.substring(0, 1).toLowerCase();
            AtomicInteger aliasCount = Alias_Map.get(newAlias);
            if (aliasCount == null) {
                aliasCount = new AtomicInteger(1);
            }
            Alias_Map.put(newAlias, aliasCount);

            newAlias += aliasCount.getAndIncrement();

            ((SQLObjectNameTableReference) rightSubQuery.getFromClause().getTableReference()).setAlias(newAlias);
            rightAlias = newAlias;
        }

        SQLJoinTableReference.SQLJoinType joinType = SQLJoinTableReference.SQLJoinType.LEFT_JOIN;
        if (op == SQLUnionOperator.INTERSECT) {
            joinType = SQLJoinTableReference.SQLJoinType.INNER_JOIN;
        }

        ISQLExpr condition = null;

        for (int i = 0; i < leftColumns.size(); i++) {
            ISQLName leftColumn = leftColumns.get(i).clone();
            if (leftColumn instanceof ISQLIdentifier) {
                leftColumn = SQLPropertyExpr.of(leftAlias, (ISQLIdentifier)leftColumn);
            }

            ISQLName rightColumn = rightColumns.get(i).clone();
            if (rightColumn instanceof ISQLIdentifier) {
                rightColumn = SQLPropertyExpr.of(rightAlias, (ISQLIdentifier)rightColumn);
            }
            condition = SQLBinaryOperatorExpr.and(condition, SQLBinaryOperatorExpr.of(leftColumn, SQLBinaryOperator.EQ, rightColumn));
        }


        ISQLTableReference joinTableReference = SQLJoinTableReference.addTableReference(leftSubQuery.getFromClause().getTableReference(), joinType, rightSubQuery.getFromClause().getTableReference());
        if (joinTableReference instanceof SQLJoinTableReference) {
            ((SQLJoinTableReference) joinTableReference).setCondition(SQLJoinTableReference.SQLJoinOnCondition.of(condition));
        }

        ISQLExpr whereCondition = null;
        if (op == SQLUnionOperator.MINUS) {
            SQLPropertyExpr expr = new SQLPropertyExpr(rightAlias, (ISQLIdentifier) rightColumns.get(0));
            whereCondition = new SQLIsCondition(expr, SQLNullExpr.of());
        }

        for (SQLSelectItem selectItem : leftSubQuery.getSelectItems()) {
            ISQLExpr expr = selectItem.getExpr();
            if (expr instanceof ISQLIdentifier) {
                selectItem.setExpr(SQLPropertyExpr.of(leftAlias, (ISQLIdentifier) expr));
            }
        }

        leftSubQuery.getFromClause().setTableReference(joinTableReference);
        leftSubQuery.andWhereCondition(whereCondition);

        boolean replace = SQLUtils.replaceInParent(x, leftSubQuery);
        if (replace) {

        }

        return false;
    }


    // ------------------------- Select Details End ----------------------------------------


}
