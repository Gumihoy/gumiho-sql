package com.gumihoy.sql.basic.ast.expr.common;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;

import java.util.ArrayList;
import java.util.List;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/element-specification.html#GUID-20D95D8A-5C17-4C89-9AAB-1852CDB57CE2
 *
 * @author kent on 2018/6/17.
 */
public abstract class SQLConstructorHeading extends AbstractSQLExpr {

    protected boolean final_;

    protected boolean instantiable;

    protected ISQLDataType dataType;

    protected ISQLDataType selfInOutDataType;

    protected final List<SQLParameterDeclaration> parameters = new ArrayList<>();


    @Override
    public SQLConstructorHeading clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void cloneTo(SQLConstructorHeading x) {
        super.cloneTo(x);
        x.final_ = this.final_;
        x.instantiable = this.instantiable;

        if (this.dataType != null) {
            ISQLDataType dataTypeClone = this.dataType.clone();
            x.setDataType(dataTypeClone);
        }

        if (this.selfInOutDataType != null) {
            ISQLDataType selfInOutDataTypeClone = this.selfInOutDataType.clone();
            x.setSelfInOutDataType(selfInOutDataTypeClone);
        }

        for (SQLParameterDeclaration parameter : parameters) {
            SQLParameterDeclaration parameterClone = parameter.clone();
            x.addParameter(parameterClone);
        }
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == dataType
                && target instanceof ISQLDataType) {
            setDataType((ISQLDataType) target);
            return true;
        }

        if (source == selfInOutDataType
                && target instanceof ISQLDataType) {
            setSelfInOutDataType((ISQLDataType) target);
            return true;
        }

        return false;
    }

    public boolean isFinal_() {
        return final_;
    }

    public void setFinal_(boolean final_) {
        this.final_ = final_;
    }

    public boolean isInstantiable() {
        return instantiable;
    }

    public void setInstantiable(boolean instantiable) {
        this.instantiable = instantiable;
    }

    public ISQLDataType getDataType() {
        return dataType;
    }

    public void setDataType(ISQLDataType dataType) {
        setChildParent(dataType);
        this.dataType = dataType;
    }

    public ISQLDataType getSelfInOutDataType() {
        return selfInOutDataType;
    }

    public void setSelfInOutDataType(ISQLDataType selfInOutDataType) {
        setChildParent(selfInOutDataType);
        this.selfInOutDataType = selfInOutDataType;
    }

    public List<SQLParameterDeclaration> getParameters() {
        return parameters;
    }

    public void addParameter(SQLParameterDeclaration parameter) {
        if (parameter == null) {
            return;
        }
        setChildParent(parameter);
        this.parameters.add(parameter);
    }
}
