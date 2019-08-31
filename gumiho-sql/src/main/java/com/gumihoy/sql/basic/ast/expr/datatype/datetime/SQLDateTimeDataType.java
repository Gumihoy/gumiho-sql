package com.gumihoy.sql.basic.ast.expr.datatype.datetime;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLIntegerLiteral;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#datetime%20type
 * <p>
 * DATETIME[(fsp)]
 * https://dev.mysql.com/doc/refman/5.7/en/date-and-time-type-overview.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/Data-Types.html#GUID-A3C0D836-BADB-44E5-A5D4-265BA5968483
 *
 * @author kent on 2018/4/19.
 */
public class SQLDateTimeDataType extends AbstractSQLDateTimeDataType implements ISQLDateTimeDataType {

    public SQLDateTimeDataType() {
        super("DATETIME");
    }

    public SQLDateTimeDataType(int len) {
        super("DATETIME");
        this.addArgument(SQLIntegerLiteral.of(len));
    }

    public SQLDateTimeDataType(ISQLExpr arg) {
        super("DATETIME", arg);
    }

    public static SQLDateTimeDataType of(int len) {
        return new SQLDateTimeDataType(len);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, arguments);
        }
    }

}
