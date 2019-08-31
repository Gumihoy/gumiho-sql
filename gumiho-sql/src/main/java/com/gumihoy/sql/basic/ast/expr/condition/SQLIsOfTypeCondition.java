package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * expr IS [ NOT ] OF [ TYPE ] ([ ONLY ] [ schema. ] type [, [ ONLY ] [ schema. ] type ]...)
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/IS-OF-type-Condition.html#GUID-7254E4C7-0194-4C1F-A3B2-2CFB0AD907CD
 *
 * @author kent on 2018/5/15.
 */
public class SQLIsOfTypeCondition extends AbstractSQLExpr implements ISQLCondition {

    protected ISQLExpr expr;
    protected boolean not;
    protected boolean type;
    protected final List<Item> items = new ArrayList<>();

    public SQLIsOfTypeCondition() {
    }

    public SQLIsOfTypeCondition(ISQLExpr expr, boolean not, boolean type) {
        this.expr = expr;
        this.not = not;
        this.type = type;
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, expr);
            this.acceptChild(visitor, items);
        }
    }

    @Override
    public SQLIsOfTypeCondition clone() {
        SQLIsOfTypeCondition x = new SQLIsOfTypeCondition();
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLIsOfTypeCondition x) {
        super.cloneTo(x);

        ISQLExpr exprClone = expr.clone();
        x.setExpr(exprClone);

        x.not = this.not;
        x.type = this.type;

        for (Item item : this.items) {
            Item itemClone = item.clone();
            x.addItem(itemClone);
        }

    }


    public ISQLExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLExpr expr) {
        setChildParent(expr);
        this.expr = expr;
    }

    public boolean isNot() {
        return not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.items.add(item);
    }




    public static final class Item extends AbstractSQLExpr {

        protected boolean only;
        protected ISQLName name;

        public Item() {
        }

        public Item(ISQLName name) {
            this(false, name);
        }

        public Item(boolean only, ISQLName name) {
            this.only = only;
            setName(name);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
            }
        }

        @Override
        public Item clone() {
            Item x = new Item();

            x.only = this.only;

            ISQLName nameClone = name.clone();
            x.setName(nameClone);

            return x;
        }

        public boolean isOnly() {
            return only;
        }

        public void setOnly(boolean only) {
            this.only = only;
        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            setChildParent(name);
            this.name = name;
        }
    }
}
