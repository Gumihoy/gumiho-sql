package com.gumihoy.sql.translate.visitor;

import com.gumihoy.sql.basic.ast.expr.common.SQLBindVariableExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLVariableExpr;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.util.SQLUtils;

/**
 * bindVar -> var (:xxx -> ?)
 *
 * @author kent on 2018/5/18.
 */
public class SQLBindVarToVarASTVisitor extends SQLASTTransformVisitor {

    public SQLBindVarToVarASTVisitor() {
    }

    public SQLBindVarToVarASTVisitor(SQLTransformConfig config) {
        super(config);
    }

    @Override
    public boolean visit(SQLBindVariableExpr x) {
        SQLVariableExpr target = new SQLVariableExpr();
        SQLUtils.replaceInParent(x, target);
        return true;
    }
}
