package com.gumihoy.sql.basic.ast;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

import java.util.List;

/**
 * @author kent on 2019-06-14.
 */
public interface ISQLReplaceable {

    boolean replace(ISQLExpr source, ISQLExpr target);

    default <T extends ISQLExpr> boolean replaceInList(List<T> exprList, ISQLExpr source, T target, ISQLObject parent) {
        if (exprList == null) {
            return false;
        }

        if (target == null) {
            for (int i = exprList.size() - 1; i >= 0; i--) {
                if (source == exprList.get(i)) {
                    exprList.remove(i);
                    return true;
                }
            }
            return false;
        }

        for (int i = 0; i < exprList.size(); i++) {
            if (exprList.get(i) == source) {
                target.setParent(parent);
                exprList.set(i, target);
                return true;
            }
        }
        return false;
    }

}
