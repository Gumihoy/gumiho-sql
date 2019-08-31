package com.gumihoy.sql.basic.ast.expr.datatype.percent;

import com.gumihoy.sql.basic.ast.expr.datatype.AbstractSQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * xx%TYPE
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/TYPE-attribute.html#GUID-EAB44F7E-B2AB-4AC6-B83D-B586193D75FC
 *
 * @author kent on 2018/4/20.
 */
public class SQLPercentTypeDataType extends AbstractSQLDataType {

    public SQLPercentTypeDataType(ISQLName name) {
        super(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }


}
