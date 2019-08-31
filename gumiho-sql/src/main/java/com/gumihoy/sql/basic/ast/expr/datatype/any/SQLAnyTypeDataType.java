package com.gumihoy.sql.basic.ast.expr.datatype.any;

import com.gumihoy.sql.basic.ast.expr.datatype.AbstractSQLSysDataType;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * SYS.AnyType
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/Data-Types.html#GUID-A3C0D836-BADB-44E5-A5D4-265BA5968483
 *
 * @author kent on 2018/4/19.
 */
public class SQLAnyTypeDataType extends AbstractSQLSysDataType {

    public SQLAnyTypeDataType() {
        this(false);
    }

    public SQLAnyTypeDataType(boolean sysOwner) {
        super(sysOwner, "ANYTYPE");
    }

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
