package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * EXTRACT( { YEAR | MONTH | DAY | HOUR | MINUTE | SECOND | TIMEZONE_HOUR | TIMEZONE_MINUTE | TIMEZONE_REGION | TIMEZONE_ABBR } FROM { expr })
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#extract%20expression
 * <p>
 * EXTRACT(unit FROM date)
 * https://dev.mysql.com/doc/refman/8.0/en/date-and-time-functions.html#function_extract
 * <p>
 * https://www.postgresql.org/docs/devel/static/functions-datetime.html#FUNCTIONS-DATETIME-EXTRACT
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/EXTRACT-datetime.html#GUID-36E52BF8-945D-437D-9A3C-6860CABD210E
 *
 * @author kent on 2018/4/30.
 */
public class SQLExtractFunction extends AbstractSQLFunction {

    protected SQLUnit unit;

    public SQLExtractFunction() {
    }

    public SQLExtractFunction(SQLUnit unit, ISQLExpr argument) {
        this.unit = unit;
        this.addArgument(argument);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, arguments);
        }
    }

    public SQLUnit getUnit() {
        return unit;
    }

    public void setUnit(SQLUnit unit) {
        this.unit = unit;
    }

    {
//        this.addFunctionType(SQLFunctionType.Datetime);
    }

    /**
     * https://dev.mysql.com/doc/refman/8.0/en/date-and-time-functions.html#function_date-add
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/EXTRACT-datetime.html#GUID-36E52BF8-945D-437D-9A3C-6860CABD210E
     */
    public enum SQLUnit implements ISQLASTEnum  {

        MICROSECOND("MICROSECOND","MICROSECOND"),
        SECOND("SECOND","SECOND"),
        MINUTE("MINUTE","MINUTE"),
        HOUR("HOUR","HOUR"),
        DAY("DAY","DAY"),
        WEEK("WEEK","WEEK"),
        MONTH("MONTH","MONTH"),
        QUARTER("QUARTER","QUARTER"),
        YEAR("YEAR","YEAR"),
        SECOND_MICROSECOND("SECOND_MICROSECOND","SECOND_MICROSECOND"),
        MINUTE_MICROSECOND("MINUTE_MICROSECOND","MINUTE_MICROSECOND"),
        MINUTE_SECOND("MINUTE_SECOND","MINUTE_SECOND"),
        HOUR_MICROSECOND("HOUR_MICROSECOND","HOUR_MICROSECOND"),
        HOUR_SECOND("HOUR_SECOND","HOUR_SECOND"),
        HOUR_MINUTE("HOUR_MINUTE","HOUR_MINUTE"),
        DAY_MICROSECOND("DAY_MICROSECOND","DAY_MICROSECOND"),
        DAY_SECOND("DAY_SECOND","DAY_SECOND"),
        DAY_MINUTE("DAY_MINUTE","DAY_MINUTE"),
        DAY_HOUR("DAY_HOUR","DAY_HOUR"),
        YEAR_MONTH("YEAR_MONTH","YEAR_MONTH"),

        TIMEZONE_HOUR("TIMEZONE_HOUR","TIMEZONE_HOUR"),
        TIMEZONE_MINUTE("TIMEZONE_MINUTE","TIMEZONE_MINUTE"),
        TIMEZONE_REGION("TIMEZONE_REGION","TIMEZONE_REGION"),
        TIMEZONE_ABBR("TIMEZONE_ABBR","TIMEZONE_ABBR"),;

        public final String lower;
        public final String upper;

        SQLUnit(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }


        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }


    }

}
