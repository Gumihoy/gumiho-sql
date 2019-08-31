package com.gumihoy.sql.translate.visitor;

import com.gumihoy.sql.basic.ast.expr.common.SQLFromClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLWithClause;
import com.gumihoy.sql.basic.ast.expr.select.SQLObjectNameTableReference;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.SQLSubQueryTableReference;
import com.gumihoy.sql.basic.ast.statement.dml.SQLSelectStatement;
import com.gumihoy.sql.dialect.oracle.ast.expr.select.OracleSelectQuery;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.util.SQLUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * WITH alias AS (subQuery)
 * <p>
 * select * from alias  => select * from (subQuery) alias
 *
 * @author kent on 2018/5/18.
 */
public class SQLWithClauseSubQueryTranslateAndRemoveASTVisitor extends SQLASTTransformVisitor {

    protected final ConcurrentHashMap<String, SQLSelectQuery> MAP = new ConcurrentHashMap<>();

    public SQLWithClauseSubQueryTranslateAndRemoveASTVisitor() {
    }

    public SQLWithClauseSubQueryTranslateAndRemoveASTVisitor(SQLTransformConfig config) {
        super(config);
    }

    @Override
    public boolean visit(SQLSelectQuery x) {
        return withClauseSubQueryTranslateAndRemove(x);
    }

    @Override
    public boolean visit(OracleSelectQuery x) {
        return withClauseSubQueryTranslateAndRemove(x);
    }

    @Override
    public boolean visit(SQLObjectNameTableReference x) {
        replace(x);
        return true;
    }

//    @Override
//    public boolean visit(OracleObjectNameTableTableReference x) {
//        replace(x);
//        return true;
//    }


    public boolean withClauseSubQueryTranslateAndRemove(SQLSelectQuery x) {
        if (x.getParent() instanceof SQLSelectStatement
                && x.getParent().getParent() == null
                && x.getWithClause() == null) {
            return false;
        }

        SQLWithClause withClause = x.getWithClause();
        findAndPutWithSubQuery(withClause);

        if (withClause != null) {
            withClause.accept(this);
        }

        SQLFromClause fromClause = x.getFromClause();
        if (fromClause != null) {
            fromClause.accept(this);
        }

        removeWithSubQuery(withClause);

        return false;
    }

    public boolean findAndPutWithSubQuery(SQLWithClause withClause) {
        if (withClause == null
                || withClause.getWithElements() == null
                || withClause.getWithElements().size() == 0) {
            return false;
        }

        for (SQLWithClause.SQLWithElement element : withClause.getWithElements()) {
            if (element instanceof SQLWithClause.SQLSubQueryFactoringClause) {
                MAP.put(((SQLWithClause.SQLSubQueryFactoringClause) element).getQueryName().getSimpleName(), (SQLSelectQuery) ((SQLWithClause.SQLSubQueryFactoringClause) element).getStatement());
            }
        }

        return true;
    }

    public boolean removeWithSubQuery(SQLWithClause x) {
        if (x == null) {
            return false;
        }
        if (x.getWithElements().size() <= MAP.size()) {
            SQLUtils.replaceInParent(x, null);
            return true;
        }
        return false;
    }

    public boolean replace(SQLObjectNameTableReference x) {
        String tableName = x.getTableName();
        if (tableName == null) {
            return false;
        }
        SQLSelectQuery subQuery = MAP.get(tableName);
        if (subQuery == null) {
            return false;
        }
        SQLSubQueryTableReference target = new SQLSubQueryTableReference(subQuery.clone(), true, tableName);
        SQLUtils.replaceInParent(x, target);

        return false;
    }


}
