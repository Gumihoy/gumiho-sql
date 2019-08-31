package com.gumihoy.sql.translate.visitor.oracle2.edb;

import com.gumihoy.sql.basic.ast.expr.common.SQLVariableExpr;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;

import java.util.concurrent.atomic.AtomicLong;

/**
 * ? => $1
 *
 * @author kent on 2018/5/18.
 */
public class Oracle2EDBVariableExprToPositionParameterASTVisitor extends SQLASTTransformVisitor {

    protected AtomicLong atomicLong = new AtomicLong(0);

    public Oracle2EDBVariableExprToPositionParameterASTVisitor() {
    }

    public Oracle2EDBVariableExprToPositionParameterASTVisitor(SQLTransformConfig config) {
        super(config);
    }

    @Override
    public boolean visit(SQLVariableExpr x) {
        long index = atomicLong.incrementAndGet();
//        PostgreSQLSQLPositionVariableExpr target = new PostgreSQLSQLPositionVariableExpr(index);
//        boolean replaceInParent = SQLUtils.replaceInParent(x, target);
//        if (!replaceInParent) {
//            atomicLong.decrementAndGet();
//        }
        return true;
    }



}
