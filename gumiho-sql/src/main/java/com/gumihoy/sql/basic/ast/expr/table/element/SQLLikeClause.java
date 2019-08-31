package com.gumihoy.sql.basic.ast.expr.table.element;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Like tableName [option ...]
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#like%20clause
 *
 * @author kent on 2018/7/6.
 */
public class SQLLikeClause extends AbstractSQLExpr implements ISQLTableElement {

    protected ISQLName name;
    protected final List<SQLOption> options = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLLikeClause clone() {
        SQLLikeClause x = new SQLLikeClause();
        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);
//        for (SQLOption option : this.options) {
//            x.addOption(option);
//        }
        return x;
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

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public List<SQLOption> getOptions() {
        return options;
    }

    public void addOption(SQLOption option) {
        if (option == null) {
            return;
        }
        this.options.add(option);
    }


    /**
     * INCLUDING IDENTITY | EXCLUDING IDENTITY
     * INCLUDING DEFAULTS | EXCLUDING DEFAULTS
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#like%20options
     * <p>
     * { INCLUDING | EXCLUDING } { DEFAULTS | CONSTRAINTS | INDEXES | STORAGE | COMMENTS | ALL }
     * https://www.postgresql.org/docs/9.3/static/sql-createtable.html
     */
    public enum SQLOption implements ISQLASTEnum {

        INCLUDING_IDENTITY("INCLUDING IDENTITY"),
        EXCLUDING_IDENTITY("EXCLUDING IDENTITY"),

        INCLUDING_DEFAULTS("INCLUDING DEFAULTS"),
        EXCLUDING_DEFAULTS("EXCLUDING DEFAULTS"),

        INCLUDING_CONSTRAINTS("INCLUDING_CONSTRAINTS"),
        EXCLUDING_CONSTRAINTS("EXCLUDING_CONSTRAINTS"),

        INCLUDING_INDEXES("INCLUDING INDEXES"),
        EXCLUDING_INDEXES("EXCLUDING INDEXES"),

        INCLUDING_STORAGE("INCLUDING STORAGE"),
        EXCLUDING_STORAGE("EXCLUDING STORAGE"),

        INCLUDING_COMMENTS("INCLUDING COMMENTS"),
        EXCLUDING_COMMENTS("EXCLUDING COMMENTS"),

        INCLUDING_ALL("INCLUDING ALL"),
        EXCLUDING_ALL("EXCLUDING ALL"),
        ;
        public final String upper;

        SQLOption(String upper) {
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }

        @Override
        public String lower() {
            return null;
        }

        @Override
        public String upper() {
            return upper;
        }
    }
}
