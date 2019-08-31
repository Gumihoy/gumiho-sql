package com.gumihoy.sql.basic.ast.expr.datatype;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;

/**
 * SYS.XXX
 * 系统数据类型
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Data-Types.html#GUID-A3C0D836-BADB-44E5-A5D4-265BA5968483
 *
 * @author kent on 2018/10/16.
 */
public abstract class AbstractSQLSysDataType extends AbstractSQLDataType implements ISQLSysDataType {

    protected boolean sysOwner;

    public AbstractSQLSysDataType() {
    }

    public AbstractSQLSysDataType(String name) {
        setName(name);
    }

    public AbstractSQLSysDataType(ISQLName name) {
        setName(name);
    }

    public AbstractSQLSysDataType(boolean sysOwner, String name) {
        super(name);
        this.sysOwner = sysOwner;
    }

    public AbstractSQLSysDataType(boolean sysOwner, ISQLName name) {
        super(name);
        this.sysOwner = sysOwner;
    }

    @Override
    public AbstractSQLSysDataType clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void cloneTo(AbstractSQLSysDataType x) {
        super.cloneTo(x);
        x.sysOwner = this.sysOwner;
    }


    public boolean isSysOwner() {
        return sysOwner;
    }

    public void setSysOwner(boolean sysOwner) {
        this.sysOwner = sysOwner;
    }
}
