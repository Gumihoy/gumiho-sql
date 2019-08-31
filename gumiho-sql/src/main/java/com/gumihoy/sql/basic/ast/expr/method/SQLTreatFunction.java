package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * TREAT(expr AS ([ REF ] [ schema. ]type | JSON)
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/TREAT.html#GUID-037C0CD3-C256-4A02-80E0-C6F15147C5BF
 *
 * @author kent on 2018/5/21.
 */
public class SQLTreatFunction extends AbstractSQLFunction {

    protected boolean ref;

    protected ISQLDataType dataType;

    public SQLTreatFunction() {
//        super(SQLReserved.TREAT.ofExpr());
    }

    public SQLTreatFunction(ISQLExpr argument, boolean ref, ISQLDataType dataType) {
//        super(SQLReserved.TREAT.ofExpr());
        this.addArgument(argument);
        this.ref = ref;
        setDataType(dataType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//            this.acceptChild(visitor, dataType);
//        }
    }


    public boolean isRef() {
        return ref;
    }

    public void setRef(boolean ref) {
        this.ref = ref;
    }

    public ISQLDataType getDataType() {
        return dataType;
    }

    public void setDataType(ISQLDataType dataType) {
        setChildParent(dataType);
        this.dataType = dataType;
    }
}
