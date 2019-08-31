package com.gumihoy.sql.basic.ast.expr.table.alter.column;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ALTER [ COLUMN ] <column name> <alter column action>
 * <alter column action>    ::=
 * <set column default clause>
 * |     <drop column default clause>
 * |     <add column scope clause>
 * |     <drop column scope clause>
 * |     <alter identity column specification>
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#alter%20column%20definition
 * <p>
 * <p>
 * <p>
 * ALTER [ COLUMN ] column_name [ SET DATA ] TYPE data_type [ COLLATE collation ] [ USING expression ]
 * ALTER [ COLUMN ] column_name SET DEFAULT expression
 * ALTER [ COLUMN ] column_name DROP DEFAULT
 * ALTER [ COLUMN ] column_name { SET | DROP } NOT NULL
 * ALTER [ COLUMN ] column_name ADD GENERATED { ALWAYS | BY DEFAULT } AS IDENTITY [ ( sequence_options ) ]
 * ALTER [ COLUMN ] column_name { SET GENERATED { ALWAYS | BY DEFAULT } | SET sequence_option | RESTART [ [ WITH ] restart ] } [...]
 * ALTER [ COLUMN ] column_name DROP IDENTITY [ IF EXISTS ]
 * ALTER [ COLUMN ] column_name SET STATISTICS integer
 * ALTER [ COLUMN ] column_name SET ( attribute_option = value [, ... ] )
 * ALTER [ COLUMN ] column_name RESET ( attribute_option [, ... ] )
 * ALTER [ COLUMN ] column_name SET STORAGE { PLAIN | EXTERNAL | EXTENDED | MAIN }
 * https://www.postgresql.org/docs/devel/static/sql-altertable.html
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableAlterColumnAction extends AbstractSQLExpr implements ISQLAlterTableColumnAction {

    protected boolean column;

    protected ISQLName name;

    protected SQLAlterColumnAction action;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, action);
//        }
    }

    @Override
    public SQLAlterTableAlterColumnAction clone() {
        return null;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }
        return false;
    }


    public boolean isColumn() {
        return column;
    }

    public void setColumn(boolean column) {
        this.column = column;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public SQLAlterColumnAction getAction() {
        return action;
    }

    public void setAction(SQLAlterColumnAction action) {
        setChildParent(action);
        this.action = action;
    }


    /**
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#alter%20column%20action
     */
    public interface SQLAlterColumnAction extends ISQLExpr {
        @Override
        SQLAlterColumnAction clone();
    }

    /**
     * SET <default clause>
     * <p>
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#set%20column%20default%20clause
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#alter%20column%20definition
     */
    public static class SQLSetColumnDefaultClause extends AbstractSQLExpr implements SQLAlterColumnAction {

        protected ISQLExpr value;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }

        @Override
        public SQLSetColumnDefaultClause clone() {
            SQLSetColumnDefaultClause x = new SQLSetColumnDefaultClause();
            return x;
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            if (source == value) {
                setValue(target);
                return true;
            }
            return false;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            this.value = value;
        }
    }

    /**
     * DROP DEFAULT
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#drop%20column%20default%20clause
     */
    public static class SQLDropColumnDefaultClause extends AbstractSQLExpr implements SQLAlterColumnAction {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLDropColumnDefaultClause clone() {
            SQLDropColumnDefaultClause x = new SQLDropColumnDefaultClause();
            return x;
        }
    }

    /**
     * ADD <scope clause>
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#add%20column%20scope%20clause
     */
    public static class SQLAddColumnScopeClause extends AbstractSQLExpr implements SQLAlterColumnAction {

//        protected SQLScopeClause scopeClause;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, scopeClause);
//            }
        }

        @Override
        public SQLDropColumnDefaultClause clone() {
            SQLDropColumnDefaultClause x = new SQLDropColumnDefaultClause();
            return x;
        }

//        public SQLScopeClause getScopeClause() {
//            return scopeClause;
//        }
//
//        public void setScopeClause(SQLScopeClause scopeClause) {
//            setChildParent(scopeClause);
//            this.scopeClause = scopeClause;
//        }
    }


    /**
     *   DROP SCOPE <drop behavior>
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#add%20column%20scope%20clause
     */
    public static class SQLDropColumnScopeClause extends AbstractSQLExpr implements SQLAlterColumnAction {

//        protected SQLCascadeType action;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//            }
        }

        @Override
        public SQLDropColumnDefaultClause clone() {
            SQLDropColumnDefaultClause x = new SQLDropColumnDefaultClause();
            return x;
        }
//
//        public SQLCascadeType getAction() {
//            return action;
//        }
//
//        public void setAction(SQLCascadeType action) {
//            this.action = action;
//        }
    }





}
