package com.gumihoy.sql.basic.ast.statement.dml;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLWhereClause;
import com.gumihoy.sql.basic.ast.expr.select.SQLObjectNameTableReference;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#merge%20statement
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/MERGE.html
 *
 * @author kent onCondition 2018/4/8.
 */
public class SQLMergeStatement extends AbstractSQLStatement {


    protected SQLObjectNameTableReference into;

    protected ISQLExpr using;

    protected ISQLExpr onCondition;

    protected final List<SQLMergeWhenClause> mergeWhenClauses = new ArrayList<>();


    public SQLMergeStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, into);
//            this.acceptChild(visitor, using);
//            this.acceptChild(visitor, onCondition);
//            this.acceptChild(visitor, mergeWhenClauses);
//        }
    }

    @Override
    public SQLMergeStatement clone() {
        SQLMergeStatement x = new SQLMergeStatement(this.dbType);

        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLMergeStatement x) {
        super.cloneTo(x);

    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.MERGE;
    }



    public SQLObjectNameTableReference getInto() {
        return into;
    }

    public void setInto(SQLObjectNameTableReference into) {
        this.into = into;
    }

    public ISQLExpr getUsing() {
        return using;
    }

    public void setUsing(ISQLExpr using) {
        this.using = using;
    }

    public ISQLExpr getOnCondition() {
        return onCondition;
    }

    public void setOnCondition(ISQLExpr onCondition) {
        this.onCondition = onCondition;
    }

    public List<SQLMergeWhenClause> getMergeWhenClauses() {
        return mergeWhenClauses;
    }

    public void addMergeWhenClause(SQLMergeWhenClause mergeWhenClause) {
        if (mergeWhenClause == null) {
            return;
        }
        mergeWhenClause.setParent(this);
        this.mergeWhenClauses.add(mergeWhenClause);
    }




    /**
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#merge%20when%20clause
     */
    public interface SQLMergeWhenClause extends ISQLExpr {
    }

    /**
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#merge%20when%20matched%20clause
     * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/MERGE.html#GUID-5692CCB7-24D9-4C0E-81A7-A22436DC968F
     */
    public static class SQLMergeWhenMatchClause extends AbstractSQLExpr implements SQLMergeWhenClause {

        protected ISQLExpr matchThen;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, matchThen);
//            }
        }

        public ISQLExpr getMatchThen() {
            return matchThen;
        }

        public void setMatchThen(ISQLExpr matchThen) {
            this.matchThen = matchThen;
        }
    }

    /**
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#merge%20when%20not%20matched%20clause
     * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/MERGE.html#GUID-5692CCB7-24D9-4C0E-81A7-A22436DC968F
     */
    public static class SQLMergeWhenNotMatchClause extends AbstractSQLExpr implements SQLMergeWhenClause {

        protected ISQLExpr notMatchThen;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, notMatchThen);
//            }
        }

        public ISQLExpr getNotMatchThen() {
            return notMatchThen;
        }

        public void setNotMatchThen(ISQLExpr notMatchThen) {
            this.notMatchThen = notMatchThen;
        }
    }

    public static class SQLMergeUpdateClause extends AbstractSQLExpr {


        protected SQLWhereClause whereClause;

        protected SQLWhereClause deleteWhereClause;


        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }
    }

    /**
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#merge%20when%20not%20matched%20clause
     * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/MERGE.html#GUID-5692CCB7-24D9-4C0E-81A7-A22436DC968F
     */
    public static class SQLMergeInsertClause extends AbstractSQLExpr {

        private List<ISQLExpr> columns = new ArrayList<ISQLExpr>();

//        protected SQLValuesClause valuesClause;

        protected SQLWhereClause whereClause;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }
    }
}
