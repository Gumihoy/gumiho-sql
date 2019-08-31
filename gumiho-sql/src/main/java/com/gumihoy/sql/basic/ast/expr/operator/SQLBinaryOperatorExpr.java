package com.gumihoy.sql.basic.ast.expr.operator;

import com.gumihoy.sql.basic.ast.ISQLReplaceable;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.condition.ISQLCondition;
import com.gumihoy.sql.basic.ast.enums.SQLBinaryOperator;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * operand1 operator operand2
 * (operand1 operator operand2)
 * <p>
 * <p>
 * A binary operator operates onCondition two operands.
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/About-SQL-Operators.html#GUID-CF1DBF8D-966F-4E5E-8AC8-9BF777B984D8
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Compound-Expressions.html#GUID-533C7BA0-C8B4-4323-81EA-1379657AF64A
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Multiset-Operators.html#GUID-793FCBB0-A97C-4884-BCAC-DD0542EA746B
 *
 * @author kent onCondition 2018/4/4.
 */
public class SQLBinaryOperatorExpr extends AbstractSQLExpr implements ISQLCondition {

    private boolean paren = false;

    protected ISQLExpr left;
    protected SQLBinaryOperator operator;
    protected ISQLExpr right;

    public SQLBinaryOperatorExpr() {
    }

    public SQLBinaryOperatorExpr(ISQLExpr left, SQLBinaryOperator operator, ISQLExpr right) {
        this(false, left, operator, right);
    }

    public SQLBinaryOperatorExpr(boolean paren, ISQLExpr left, SQLBinaryOperator operator, ISQLExpr right) {
        this.paren = paren;
        setLeft(left);
        this.operator = operator;
        setRight(right);
    }

    public static SQLBinaryOperatorExpr of(ISQLExpr left, SQLBinaryOperator operator, ISQLExpr right) {
        return new SQLBinaryOperatorExpr(left, operator, right);
    }

    public static SQLBinaryOperatorExpr of(boolean paren, ISQLExpr left, SQLBinaryOperator operator, ISQLExpr right) {
        return new SQLBinaryOperatorExpr(paren, left, operator, right);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, left);
            this.acceptChild(visitor, right);
        }
    }

    @Override
    public SQLBinaryOperatorExpr clone() {
        SQLBinaryOperatorExpr x = new SQLBinaryOperatorExpr();

        ISQLExpr leftClone = this.left.clone();
        x.setLeft(leftClone);

        x.operator = this.operator;

        ISQLExpr rightClone = this.right.clone();
        x.setRight(rightClone);


        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {

        if (left == source) {
            if (target == null) {
                if (parent instanceof ISQLReplaceable) {
                    return ((ISQLReplaceable) parent).replace(this, right);
                } else {
                    return false;
                }
            }
            this.setLeft(target);
            return true;
        }

        if (right == source) {
            if (target == null) {
                if (parent instanceof ISQLReplaceable) {
                    return ((ISQLReplaceable) parent).replace(this, left);
                } else {
                    return false;
                }
            }
            this.setRight(target);
            return true;
        }

        return false;
    }

    public static ISQLExpr and(ISQLExpr left, ISQLExpr right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        if (right instanceof SQLBinaryOperatorExpr
                && !((SQLBinaryOperatorExpr) right).isParen()) {

            SQLBinaryOperatorExpr binaryOperatorExpr = (SQLBinaryOperatorExpr) right;
            if (((SQLBinaryOperatorExpr) right).getOperator() == SQLBinaryOperator.And) {
                return and(and(left, binaryOperatorExpr.getLeft()), binaryOperatorExpr.getRight());

            } else if (((SQLBinaryOperatorExpr) right).getOperator() == SQLBinaryOperator.OR) {
                return or(and(left, binaryOperatorExpr.getLeft()), binaryOperatorExpr.getRight());
            }
        }

        return SQLBinaryOperatorExpr.of(left, SQLBinaryOperator.And, right);
    }

    public static ISQLExpr or(ISQLExpr left, ISQLExpr right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        if (right instanceof SQLBinaryOperatorExpr
                && !((SQLBinaryOperatorExpr) right).isParen()) {
            SQLBinaryOperatorExpr binaryOperatorExpr = (SQLBinaryOperatorExpr) right;

            if (((SQLBinaryOperatorExpr) right).getOperator() == SQLBinaryOperator.And) {
                return and(or(left, binaryOperatorExpr.getLeft()), binaryOperatorExpr.getRight());

            } else if (((SQLBinaryOperatorExpr) right).getOperator() == SQLBinaryOperator.OR) {
                return or(or(left, binaryOperatorExpr.getLeft()), binaryOperatorExpr.getRight());

            }
        }

        return SQLBinaryOperatorExpr.of(left, SQLBinaryOperator.OR, right);
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SQLBinaryOperatorExpr that = (SQLBinaryOperatorExpr) o;

        if (left != null ? !left.equals(that.left) : that.left != null) {
            return false;
        }
        if (operator != that.operator) {
            return false;
        }
        return right != null ? right.equals(that.right) : that.right == null;
    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }



    public boolean isParen() {
        return paren;
    }

    public void setParen(boolean paren) {
        this.paren = paren;
    }


    public ISQLExpr getLeft() {
        return left;
    }

    public void setLeft(ISQLExpr left) {
        setChildParent(left);
        this.left = left;
    }

    public SQLBinaryOperator getOperator() {
        return operator;
    }

    public void setOperator(SQLBinaryOperator operator) {
        this.operator = operator;
    }

    public ISQLExpr getRight() {
        return right;
    }

    public void setRight(ISQLExpr right) {
        setChildParent(right);
        this.right = right;
    }

}
