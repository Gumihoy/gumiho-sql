package com.gumihoy.sql.basic.ast.expr.operator;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.enums.SQLUnaryOperator;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * operator operand
 * <p>
 * A unary operator operates onCondition only one operand
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/About-SQL-Operators.html#GUID-CF1DBF8D-966F-4E5E-8AC8-9BF777B984D8
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/About-SQL-Operators.html#GUID-FEF44762-F45C-41D9-B380-F6A61AD25338
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Compound-Expressions.html#GUID-533C7BA0-C8B4-4323-81EA-1379657AF64A
 *
 * @author kent onCondition 2018/4/4.
 */
public class SQLUnaryOperatorExpr extends AbstractSQLExpr {

    protected boolean paren;

    protected SQLUnaryOperator operator;

    protected ISQLExpr operand;

    public SQLUnaryOperatorExpr(SQLUnaryOperator operator, ISQLExpr operand) {
        this(false, operator, operand);
    }

    public SQLUnaryOperatorExpr(boolean paren, SQLUnaryOperator operator, ISQLExpr operand) {
        this.paren = paren;
        this.operator = operator;
        setOperand(operand);
    }

    public static SQLUnaryOperatorExpr of(SQLUnaryOperator operator, ISQLExpr operand) {
        return new SQLUnaryOperatorExpr(operator, operand);
    }

    public static SQLUnaryOperatorExpr of(boolean paren, SQLUnaryOperator operator, ISQLExpr operand) {
        return new SQLUnaryOperatorExpr(paren, operator, operand);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, operand);
        }
    }


    public boolean isParen() {
        return paren;
    }

    public void setParen(boolean paren) {
        this.paren = paren;
    }

    public SQLUnaryOperator getOperator() {
        return operator;
    }

    public void setOperator(SQLUnaryOperator operator) {
        this.operator = operator;
    }

    public ISQLExpr getOperand() {
        return operand;
    }

    public void setOperand(ISQLExpr operand) {
        setChildParent(operand);
        this.operand = operand;
    }
}
