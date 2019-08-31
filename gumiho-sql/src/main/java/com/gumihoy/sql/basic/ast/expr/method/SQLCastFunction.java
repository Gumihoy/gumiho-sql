package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#cast%20specification
 * <p>
 * https://dev.mysql.com/doc/refman/5.5/en/cast-functions.html#function_cast
 * <p>
 * CAST({ expr | MULTISET (subquery) } AS type_name [ DEFAULT return_value ON CONVERSION ERROR ] [, fmt [, 'nlsparam' ] ])
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Single-Row-Functions.html#GUID-D16F7FB3-48D9-4354-A58A-357515D402DC
 *
 * @author kent on 2018/4/27.
 */
public class SQLCastFunction extends AbstractSQLFunction {

    protected ISQLDataType dataType;

    public SQLCastFunction() {
//        super(SQLReserved.CAST.ofExpr());
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//            this.acceptChild(visitor, dataType);
//            this.acceptChild(visitor, defaultOnConversionError);
//        }
    }

    public ISQLDataType getDataType() {
        return dataType;
    }

    public void setDataType(ISQLDataType dataType) {
        setChildParent(dataType);
        this.dataType = dataType;
    }
}
