package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLLobParameter;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * RETENTION [ MAX | MIN integer | AUTO | NONE ]
 *
 * LOB_retention_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/25.
 */
public class OracleLobRetentionClause extends AbstractOracleExpr implements ISQLLobParameter {

    protected OptionType optionType;
    protected ISQLExpr value;

    @Override
    public void accept0(IOracleASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, value);
//        }
    }

    @Override
    public OracleLobRetentionClause clone() {
        OracleLobRetentionClause x = new OracleLobRetentionClause();
        this.cloneTo(x);

        x.optionType = this.optionType;

        if (this.value != null) {
            ISQLExpr valueClone = this.value.clone();
            x.setValue(valueClone);
        }
        return x;
    }

    public OptionType getOptionType() {
        return optionType;
    }

    public void setOptionType(OptionType optionType) {
        this.optionType = optionType;
    }

    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        setChildParent(value);
        this.value = value;
    }

    public enum OptionType implements ISQLASTEnum {
        MAX("MAX", "MAX"),
        MIN("MIN", "MIN"),
        AUTO("AUTO", "AUTO"),
        NONE("NONE", "NONE"),;

        public final String lower;
        public final String upper;


        OptionType(String lower, String upper) {
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
