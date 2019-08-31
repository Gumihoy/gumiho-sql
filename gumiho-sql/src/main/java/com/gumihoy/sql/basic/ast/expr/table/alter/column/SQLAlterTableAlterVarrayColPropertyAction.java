package com.gumihoy.sql.basic.ast.expr.table.alter.column;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * MODIFY VARRAY varray_item ( modify_LOB_parameters )
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/11.
 */
public class SQLAlterTableAlterVarrayColPropertyAction extends AbstractSQLExpr implements ISQLAlterTableDropColumnAction {

    protected ISQLExpr name;
    protected final List<ISQLExpr> parameters = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if(visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, parameters);
//        }
    }

    @Override
    public SQLAlterTableAlterVarrayColPropertyAction clone() {
        SQLAlterTableAlterVarrayColPropertyAction x = new SQLAlterTableAlterVarrayColPropertyAction();

        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name) {
            setName(target);
            return true;
        }

        return false;
    }

    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }

    public List<ISQLExpr> getParameters() {
        return parameters;
    }
}
