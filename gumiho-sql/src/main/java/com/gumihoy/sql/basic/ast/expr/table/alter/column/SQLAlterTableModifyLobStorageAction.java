package com.gumihoy.sql.basic.ast.expr.table.alter.column;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * MODIFY LOB (LOB_item) (modify_LOB_parameters)
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableModifyLobStorageAction extends AbstractSQLExpr implements ISQLAlterTableColumnAction {

    protected ISQLName name;
    protected final List<ISQLExpr> parameters = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, parameters);
//        }
    }

    @Override
    public SQLAlterTableModifyLobStorageAction clone() {
        SQLAlterTableModifyLobStorageAction x = new SQLAlterTableModifyLobStorageAction();

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

        for (ISQLExpr parameter : this.parameters) {
            ISQLExpr parameterClone = parameter.clone();
            x.addParameter(parameterClone);
        }

        return x;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }
        boolean replace = replaceInList(parameters, source, target, this);
        if (replace) {
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

    public List<ISQLExpr> getParameters() {
        return parameters;
    }

    public void addParameter(ISQLExpr parameter) {
        if (parameter == null) {
            return;
        }
        setChildParent(parameter);
        this.parameters.add(parameter);
    }


}
