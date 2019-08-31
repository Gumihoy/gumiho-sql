package com.gumihoy.sql.basic.ast.expr.datatype.percent;


import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.datatype.AbstractSQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * xx%ROWTYPE
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/ROWTYPE-attribute.html#GUID-4E0B9FE2-909D-444A-9B4A-E0243B7FCB99
 *
 * @author kent on 2018/4/20.
 */
public class SQLPercentRowTypeDataType extends AbstractSQLDataType {

    public SQLPercentRowTypeDataType(ISQLName name) {
        super(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLPercentRowTypeDataType clone() {
        ISQLName nameClone = this.name.clone();
        SQLPercentRowTypeDataType x = new SQLPercentRowTypeDataType(nameClone);
        return x;
    }

    @Override
    public void cloneTo(ISQLObject x) {
        super.cloneTo(x);
    }


}
