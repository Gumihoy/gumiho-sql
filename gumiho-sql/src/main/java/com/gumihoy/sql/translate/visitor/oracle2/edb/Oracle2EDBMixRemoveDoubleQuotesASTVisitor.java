package com.gumihoy.sql.translate.visitor.oracle2.edb;

import com.gumihoy.sql.basic.ast.expr.identifier.SQLDoubleQuotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLUnquotedIdentifier;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.util.SQLUtils;

/**
 * Remove Double Quotes
 * 1、有小写字母保留双引号
 * 2、数字开头保留双引号
 * 3、特殊字符保留双引号
 *
 * @author kent on 2018/5/18.
 */
public class Oracle2EDBMixRemoveDoubleQuotesASTVisitor extends SQLASTTransformVisitor {

    public Oracle2EDBMixRemoveDoubleQuotesASTVisitor() {
    }

    public Oracle2EDBMixRemoveDoubleQuotesASTVisitor(SQLTransformConfig config) {
        super(config);
    }

    @Override
    public boolean visit(SQLDoubleQuotedIdentifier x) {
        String name = x.getSimpleName();

        if (SQLUtils.isStartWithNumber(name)
                || SQLUtils.containsSpecialCharacter(name)
                || SQLUtils.hasLowerLetter(name)) {
            return true;
        }

        SQLUnquotedIdentifier target = new SQLUnquotedIdentifier(name);
        boolean replace = SQLUtils.replaceInParent(x, target);
        if (replace) {

        }
        return true;
    }


}
