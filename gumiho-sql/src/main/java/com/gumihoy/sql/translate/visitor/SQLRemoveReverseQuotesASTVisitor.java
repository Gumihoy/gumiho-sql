package com.gumihoy.sql.translate.visitor;

import com.gumihoy.sql.basic.ast.expr.identifier.SQLReverseQuotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLUnquotedIdentifier;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.util.SQLUtils;

/**
 * Remove Reverse visitor
 * 移除 反引号(`)
 *
 * @author kent on 2018/5/18.
 */
public class SQLRemoveReverseQuotesASTVisitor extends SQLASTTransformVisitor {

    public SQLRemoveReverseQuotesASTVisitor() {
    }

    public SQLRemoveReverseQuotesASTVisitor(SQLTransformConfig config) {
        super(config);
    }

    @Override
    public boolean visit(SQLReverseQuotedIdentifier x) {
        SQLUnquotedIdentifier target = new SQLUnquotedIdentifier(x.getName());
        SQLUtils.replaceInParent(x, target);
        return false;
    }
}
