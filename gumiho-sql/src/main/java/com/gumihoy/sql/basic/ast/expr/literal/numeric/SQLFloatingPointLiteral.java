package com.gumihoy.sql.basic.ast.expr.literal.numeric;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.math.BigDecimal;

/**
 * 1.2E3, 1.2E-3, -1.2E3, -1.2E-3
 *
 * https://www.techonthenet.com/postgresql/literals.php
 * https://dev.mysql.com/doc/refman/8.0/en/number-literals.html
 *
 * @author kent on 2019-06-19.
 */
public class SQLFloatingPointLiteral extends AbstractSQLNumericLiteral{

    public SQLFloatingPointLiteral(String source) {
        super(source);
        this.value = new BigDecimal(source);
    }

    public SQLFloatingPointLiteral(BigDecimal value) {
        this.source = value.toString();
        this.value = value;
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }


}
