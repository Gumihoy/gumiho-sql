package com.gumihoy.sql.basic.ast.expr.select.group;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#grouping%20column%20reference
 * 
 * @author kent on 2018/5/2.
 */
public class SQLGroupingColumnReference extends AbstractSQLExpr {

    protected ISQLExpr columnReference;

//    protected SQLCollateSetExpr collateClause;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {

    }


    public ISQLExpr getColumnReference() {
        return columnReference;
    }

    public void setColumnReference(ISQLExpr columnReference) {
        setChildParent(columnReference);
        this.columnReference = columnReference;
    }

//    public SQLCollateSetExpr getCollateClause() {
//        return collateClause;
//    }
//
//    public void setCollateClause(SQLCollateSetExpr collateClause) {
//        setChildParent(collateClause);
//        this.collateClause = collateClause;
//    }
}
