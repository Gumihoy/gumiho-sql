package com.gumihoy.sql.translate.visitor;

import com.gumihoy.sql.basic.ast.expr.select.SQLJoinTableReference;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;

import java.util.List;

/**
 * xx Join yy Join zz On condition ON condition => xx Join yy On condition Join zz On condition
 *
 * @author kent on 2018/5/18.
 */
public class SQLMultiJointOnConditionToASTVisitor extends SQLASTTransformVisitor {

    public SQLMultiJointOnConditionToASTVisitor() {
    }

    public SQLMultiJointOnConditionToASTVisitor(SQLTransformConfig config) {
        super(config);
    }


    @Override
    public boolean visit(SQLJoinTableReference x) {
        if (x.getConditions() != null
                && x.getConditions().size() > 1) {
            List<SQLJoinTableReference.ISQLJoinCondition> onConditions = x.getConditions();

        }
        return true;
    }


}
