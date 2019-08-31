package com.gumihoy.sql.basic.ast.expr.table.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * MODIFY OPAQUE TYPE anydata_column STORE ( type_name [, type_name ]... ) UNPACKED
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/11.
 */
public class SQLAlterTableModifyOpaqueTypeAction extends AbstractSQLExpr implements ISQLAlterTableAction {

    protected ISQLExpr column;
    protected final List<ISQLExpr> names = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, column);
//            this.acceptChild(visitor, names);
//        }
    }

    @Override
    public SQLAlterTableModifyOpaqueTypeAction clone() {
        SQLAlterTableModifyOpaqueTypeAction x = new SQLAlterTableModifyOpaqueTypeAction();
        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == column) {
            setColumn(target);
            return true;
        }

        boolean replace = replaceInList(names, source, target, this);
        if (replace) {
            return true;
        }

        return false;
    }

    public ISQLExpr getColumn() {
        return column;
    }

    public void setColumn(ISQLExpr column) {
        setChildParent(column);
        this.column = column;
    }

    public List<ISQLExpr> getNames() {
        return names;
    }

    public void addName(ISQLExpr name) {
        if (name == null) {
            return;
        }
        setChildParent(name);
        this.names.add(name);
    }
}
