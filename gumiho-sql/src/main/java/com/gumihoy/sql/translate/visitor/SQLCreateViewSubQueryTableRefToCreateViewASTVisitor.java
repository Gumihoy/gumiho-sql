package com.gumihoy.sql.translate.visitor;

import com.gumihoy.sql.basic.ast.expr.select.ISQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.SQLObjectNameTableReference;
import com.gumihoy.sql.basic.ast.expr.select.SQLSubQueryTableReference;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLCreateViewStatement;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.util.SQLUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create View SubQueryTableReference => create view
 * <p>
 * 子查询 => 创建视图
 *
 * @author kent on 2018/5/18.
 */
public class SQLCreateViewSubQueryTableRefToCreateViewASTVisitor extends SQLASTTransformVisitor {

    protected boolean createView = false;
    protected String owner;
    protected String viewName;
    protected AtomicInteger count = new AtomicInteger(1);

    public SQLCreateViewSubQueryTableRefToCreateViewASTVisitor(SQLTransformConfig config) {
        super(config);
    }


    @Override
    public boolean visit(SQLCreateViewStatement x) {
        createView = true;
        owner = x.getOwner();
        viewName = x.getViewName();
        return true;
    }


    @Override
    public boolean visit(SQLSubQueryTableReference x) {
        if (!createView) {
            return false;
        }
        createViewAndTransform(config, x);
        return true;
    }

//    @Override
//    public boolean visit(OracleSubQueryTableReference x) {
//        if (!createView) {
//            return false;
//        }
//        visit((SQLSubQueryTableReference) x);
//        return true;
//    }


    public void createViewAndTransform(SQLTransformConfig config, SQLSubQueryTableReference x) {
        ISQLSelectQuery subQuery = x.getSubQuery();
        String viewName = this.viewName + "_" + count.getAndIncrement();
        SQLCreateViewStatement createView = new SQLCreateViewStatement(x.getDbType());
        createView.setOrReplace(true);
        createView.setName(owner, viewName);
        createView.setSubQuery(subQuery);

        SQLObjectNameTableReference target = new SQLObjectNameTableReference();
        target.setName(owner, viewName);
        target.setAlias(x.getAlias());

        boolean replace = SQLUtils.replaceInParent(x, target);
        if (replace) {
            config.stmtList.add(config.index, createView);
        }

    }
}
