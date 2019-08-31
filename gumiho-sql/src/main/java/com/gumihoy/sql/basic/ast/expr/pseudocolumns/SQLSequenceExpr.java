package com.gumihoy.sql.basic.ast.expr.pseudocolumns;

import com.gumihoy.sql.basic.ast.enums.SQLCharSizeUnit;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * sequence. { CURRVAL | NEXTVAL }
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Simple-Expressions.html#GUID-0E033897-60FB-40D7-A5F3-498B0FCC31B0
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Sequence-Pseudocolumns.html#GUID-693B576A-191D-45F5-B7CB-88D0EA821B44
 *
 * @author kent on 2018/5/16.
 */
public class SQLSequenceExpr extends AbstractSQLExpr implements ISQLPseudoColumn {

    protected ISQLExpr sequence;
    protected SQLSequenceFunction name;


    public SQLSequenceExpr(ISQLExpr sequence, SQLSequenceFunction name) {
        setSequence(sequence);
        this.name = name;
    }

    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, sequence);
        }
    }

    @Override
    public SQLSequenceExpr clone() {

        ISQLExpr sequenceName = this.sequence.clone();

        SQLSequenceExpr x = new SQLSequenceExpr(sequenceName, this.name);

        return x;
    }

    public ISQLExpr getSequence() {
        return sequence;
    }

    public void setSequence(ISQLExpr sequence) {
        setChildParent(sequence);
        this.sequence = sequence;
    }

    public SQLSequenceFunction getName() {
        return name;
    }

    public void setName(SQLSequenceFunction name) {
        this.name = name;
    }


    public enum SQLSequenceFunction implements ISQLASTEnum {

        CURRVAL("currval", "CURRVAL"),
        NEXTVAL("nextval", "NEXTVAL"),;

        public final String lower;
        public final String upper;

        SQLSequenceFunction(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public static SQLCharSizeUnit of(String name) {
            for (SQLCharSizeUnit unit : SQLCharSizeUnit.values()) {
                if (unit.lower.equalsIgnoreCase(name)) {
                    return unit;
                }
            }
            return null;
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
