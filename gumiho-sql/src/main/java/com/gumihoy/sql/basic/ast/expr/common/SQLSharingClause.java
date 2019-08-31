package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * @author kent on 2019-07-24.
 */
public class SQLSharingClause extends AbstractSQLExpr {

    protected SQLValue value;

    public SQLSharingClause(SQLValue value) {
        this.value = value;
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {

    }

    @Override
    public SQLSharingClause clone() {
        return new SQLSharingClause(this.value);
    }

    /**
     * { METADATA | DATA | EXTENDED DATA | NONE }
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/SHARING-clause.html
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/create_table.html
     */
    public enum SQLValue implements ISQLASTEnum {

        METADATA("metadata", "METADATA"),
        DATA("data", "DATA"),
        EXTENDED_DATA("extended data", "EXTENDED DATA"),
        NONE("none", "NONE");

        public final String lower;
        public final String upper;

        SQLValue(String lower, String upper) {
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
