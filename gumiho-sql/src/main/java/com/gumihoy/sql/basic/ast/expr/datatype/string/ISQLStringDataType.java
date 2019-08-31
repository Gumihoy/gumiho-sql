package com.gumihoy.sql.basic.ast.expr.datatype.string;

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#predefined%20type
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/string-type-overview.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/Data-Types.html#GUID-A3C0D836-BADB-44E5-A5D4-265BA5968483
 *
 * @author kent on 2018/4/19.
 */
public interface ISQLStringDataType extends ISQLDataType {

    String getCharacterSetName();

    String getCollationName();



    public enum SQLType implements ISQLASTEnum {
        VARYING("VARYING", "VARYING"),
        LARGE_OBJECT("LARGE OBJECT", "LARGE OBJECT"),
        ;

        public final String lower;
        public final String upper;

        SQLType(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }

        @Override
        public String lower() {
            return null;
        }

        @Override
        public String upper() {
            return null;
        }
    }
}
