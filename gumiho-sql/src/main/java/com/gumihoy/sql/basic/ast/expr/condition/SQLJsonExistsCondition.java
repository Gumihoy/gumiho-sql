package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * JSON_EXISTS( expr [ FORMAT JSON ], JSON_basic_path_expression [ JSON_passing_clause] [ JSON_exists_on_error_clause ] )
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SQL-JSON-Conditions.html#GUID-99B9493D-2929-4A09-BA39-A56F8E7319DA
 *
 * @author kent on 2018/5/11.
 */
public class SQLJsonExistsCondition extends AbstractSQLExpr implements ISQLCondition{

    protected ISQLExpr expr;

    protected boolean formatJson;

    protected ISQLExpr pathExpr;

//    protected final List<ISQLElementtASAliasExpr> jsonPassingClauseItems = new ArrayList<>();

//    protected SQLReservedIdentifier onErrorClause;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, expr);
//            this.acceptChild(visitor, pathExpr);
//            this.acceptChild(visitor, jsonPassingClauseItems);
//        }
    }


    @Override
    public SQLJsonExistsCondition clone() {
        SQLJsonExistsCondition x = new SQLJsonExistsCondition();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLJsonExistsCondition x) {
        super.cloneTo(x);

        ISQLExpr exprClone = this.expr.clone();
        x.setExpr(exprClone);

        x.formatJson = this.formatJson;

        ISQLExpr pathExprClone = this.pathExpr.clone();
        x.setPathExpr(pathExprClone);

//        for (ISQLElementASAliasExpr item : jsonPassingClauseItems) {
//            ISQLExprASAliasExpr itemClone = item.clone();
//            x.addJsonPassingClauseItem(itemClone);
//        }
//
//        x.onErrorClause = this.onErrorClause;

    }


    public ISQLExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLExpr expr) {
        this.expr = expr;
    }

    public boolean isFormatJson() {
        return formatJson;
    }

    public void setFormatJson(boolean formatJson) {
        this.formatJson = formatJson;
    }

    public ISQLExpr getPathExpr() {
        return pathExpr;
    }

    public void setPathExpr(ISQLExpr pathExpr) {
        this.pathExpr = pathExpr;
    }

//    public List<ISQLElementASAliasExpr> getJsonPassingClauseItems() {
//        return jsonPassingClauseItems;
//    }
//
//    public void addJsonPassingClauseItem(ISQLElementASAliasExpr item) {
//        if (item == null) {
//            return;
//        }
//        setChildParent(item);
//        this.jsonPassingClauseItems.add(item);
//    }

//    public SQLReservedIdentifier getOnErrorClause() {
//        return onErrorClause;
//    }
//
//    public void setOnErrorClause(SQLReservedIdentifier onErrorClause) {
//        setChildParent(onErrorClause);
//        this.onErrorClause = onErrorClause;
//    }


}
