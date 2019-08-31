package com.gumihoy.sql.translate.visitor;


import com.gumihoy.sql.basic.ast.expr.identifier.SQLDoubleQuotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLUnquotedIdentifier;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.util.SQLUtils;

/**
 * Remove Double Quotes
 *
 * @author kent on 2018/5/18.
 */
public class SQLRemoveDoubleQuotesASTVisitor extends SQLASTTransformVisitor {

    @Override
    public boolean visit(SQLDoubleQuotedIdentifier x) {
        SQLUnquotedIdentifier target = new SQLUnquotedIdentifier(x.getName());
        boolean replaceInParent = SQLUtils.replaceInParent(x, target);
        return true;
    }
}
