package com.gumihoy.sql.translate.visitor;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLDBLinkExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLDoubleQuotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLReverseQuotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLUnquotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.method.SQLMethodInvocation;
import com.gumihoy.sql.basic.ast.expr.select.SQLObjectNameTableReference;
import com.gumihoy.sql.basic.parser.SQLKeyWord;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.util.SQLUtils;

import java.util.List;

/**
 * Add Reverse visitor
 * 添加 反引号(`)
 *
 * @author kent on 2018/5/18.
 */
public class SQLAddReverseQuotesASTVisitor extends SQLASTTransformVisitor {

    public SQLAddReverseQuotesASTVisitor() {
    }

    public SQLAddReverseQuotesASTVisitor(SQLTransformConfig config) {
        super(config);
    }

    @Override
    public boolean visit(SQLDoubleQuotedIdentifier x) {
        SQLReverseQuotedIdentifier target = new SQLReverseQuotedIdentifier(x.getName());
        boolean replace = SQLUtils.replaceInParent(x, target);
        return true;
    }

    @Override
    public boolean visit(SQLUnquotedIdentifier x) {
        long nameLowerHash = x.lowerHash();
        ISQLObject parent = x.getParent();

        // 数据类型
        if ((parent instanceof ISQLDataType
                && ((ISQLDataType) parent).getName() == x)) {
            return true;
        }

        // 方法
        // 1. 方法name
        // 2. mysql Funciton TIMESTAMPADD/TIMESTAMPDIFF 参数
        if (parent instanceof SQLMethodInvocation) {
            SQLMethodInvocation method = (SQLMethodInvocation) parent;
            List<ISQLExpr> arguments = method.getArguments();
            int size = arguments.size();

            if (method.getName() == x) {
                return true;
            }
        }

        // mysql 中 表名 dual 不能加
        if (nameLowerHash == SQLKeyWord.DUAL.lowerHash
                && parent instanceof SQLObjectNameTableReference) {
            return true;
        }

        // DBLink 不能加
        if (parent instanceof SQLDBLinkExpr) {
            return true;
        }

        SQLReverseQuotedIdentifier target = new SQLReverseQuotedIdentifier(x.getName());
        SQLUtils.replaceInParent(x, target);
        return true;
    }

}
