package com.gumihoy.sql.basic.ast.expr.literal.numeric;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * xxf/xxF
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Literals.html#GUID-083FEFEA-B33F-436B-AEBF-9101A49EF189
 *
 * @author kent on 2018/5/15.
 */
public class SQLBinaryFloatLiteral extends AbstractSQLNumericLiteral {

    public SQLBinaryFloatLiteral(String source) {
        super(source);
        this.value = Float.valueOf(source);
    }

    public SQLBinaryFloatLiteral(Float value) {
        this.source = value.toString();
        this.value = value;
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Float getValue() {
        return value.floatValue();
    }
}
