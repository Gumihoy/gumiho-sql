package com.gumihoy.sql.basic.ast.expr.trigger;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * { FOLLOWS | PRECEDES } [ schmema.] trigger [ , [ schmema.] trigger ]...
 * <p>
 * trigger_ordering_clause
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CREATE-TRIGGER-statement.html#GUID-AF9E33F1-64D1-4382-A6A4-EC33C36F237B
 *
 * @author kent on 2018/4/26.
 */
public class SQLTriggerOrderingClause extends AbstractSQLExpr {

    protected Type type;

    protected final List<ISQLName> names = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, names);
//        }
    }

    @Override
    public SQLTriggerOrderingClause clone() {
        SQLTriggerOrderingClause x = new SQLTriggerOrderingClause();
        x.type = this.type;

        for (ISQLName name : names) {
            ISQLName nameClone = name.clone();
            x.addName(nameClone);
        }
        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (target instanceof ISQLName) {
            boolean replace = replaceInList(names, source, (ISQLName) target, this);
            if (replace) {
                return true;
            }
        }

        return false;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<ISQLName> getNames() {
        return names;
    }

    public void addName(ISQLName name) {
        if (name == null) {
            return;
        }
        setChildParent(name);
        this.names.add(name);
    }

    public enum Type implements ISQLASTEnum {

        FOLLOWS("FOLLOWS"),
        PRECEDES("PRECEDES");
//
        public final String upper;

        Type(String upper) {
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }


        @Override
        public String lower() {
            return upper;
        }

        @Override
        public String upper() {
            return upper;
        }
    }
}
