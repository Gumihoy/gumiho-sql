package com.gumihoy.sql.basic.ast.expr.comment.hint;

import com.gumihoy.sql.basic.ast.expr.comment.ISQLComment;

/**
 * @author kent on 2019-07-18.
 */
public interface ISQLHint extends ISQLComment {
    @Override
    ISQLHint clone();
}
