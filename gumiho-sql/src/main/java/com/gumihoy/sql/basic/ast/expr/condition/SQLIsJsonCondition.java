package com.gumihoy.sql.basic.ast.expr.condition;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * expr IS [ NOT ] JSON [ FORMAT JSON ] [ STRICT | LAX ] [ { WITH | WITHOUT } UNIQUE KEYS ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SQL-JSON-Conditions.html#GUID-99B9493D-2929-4A09-BA39-A56F8E7319DA
 *
 * @author kent on 2018/5/11.
 */
public class SQLIsJsonCondition extends AbstractSQLExpr implements ISQLCondition {

    protected ISQLExpr expr;

    protected boolean not;

    protected boolean formatJson;

//    protected SQLReserved option;
//
//    protected SQLReserved uniqueKeys;

    public SQLIsJsonCondition() {
    }

    public SQLIsJsonCondition(ISQLExpr expr) {
        setExpr(expr);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, expr);
//        }
    }

    @Override
    public SQLIsJsonCondition clone() {
        SQLIsJsonCondition x = new SQLIsJsonCondition();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLIsJsonCondition x) {
        super.cloneTo(x);

        ISQLExpr exprClone = this.expr.clone();
        x.setExpr(exprClone);

        x.not = this.not;

        x.formatJson = this.formatJson;

//        x.option = this.option;
//
//        x.uniqueKeys = this.uniqueKeys;
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

    public boolean isFormatJson() {
        return formatJson;
    }

    public void setFormatJson(boolean formatJson) {
        this.formatJson = formatJson;
    }

//    public SQLReserved getOption() {
//        return option;
//    }
//
//    public void setOption(SQLReserved option) {
//        this.option = option;
//    }
//
//    public SQLReserved getUniqueKeys() {
//        return uniqueKeys;
//    }
//
//    public void setUniqueKeys(SQLReserved uniqueKeys) {
//        this.uniqueKeys = uniqueKeys;
//    }






}
