/*
 * Copyright (C) 2017-2018 kent(kent.bohai@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gumihoy.sql.basic.ast.expr.select;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLPartitionClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.operator.SQLBinaryOperatorExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.util.HashUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * XX | (XX)
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#joined%20table
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/join.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
 *
 * @author kent onCondition 2018/3/20.
 */
public class SQLJoinTableReference extends AbstractSQLTableReference implements ISQLTableReference {

    protected ISQLTableReference left;
    protected SQLPartitionClause leftPartitionClause;

    protected SQLJoinType joinType;

    protected ISQLTableReference right;
    protected SQLPartitionClause rightPartitionClause;

    protected final List<ISQLJoinCondition> conditions = new ArrayList<>();

    public SQLJoinTableReference() {
    }

    public SQLJoinTableReference(ISQLTableReference left, SQLJoinType joinType, ISQLTableReference right) {
        setLeft(left);
        setRight(right);
        this.joinType = joinType;
    }

    public SQLJoinTableReference(boolean paren, ISQLTableReference left, SQLJoinType joinType, ISQLTableReference right) {
        this.paren = paren;
        setLeft(left);
        setRight(right);
        this.joinType = joinType;
    }

    public static SQLJoinTableReference of(ISQLTableReference left, SQLJoinType joinType, ISQLTableReference right) {
        return new SQLJoinTableReference(left, joinType, right);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, left);
            this.acceptChild(visitor, leftPartitionClause);
            this.acceptChild(visitor, right);
            this.acceptChild(visitor, rightPartitionClause);
            this.acceptChild(visitor, conditions);
        }
    }


    @Override
    public SQLJoinTableReference clone() {
        SQLJoinTableReference x = new SQLJoinTableReference();

        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLJoinTableReference x) {
        super.cloneTo(x);

        ISQLTableReference leftClone = this.left.clone();
        x.setLeft(leftClone);

        if (this.leftPartitionClause != null) {
            SQLPartitionClause leftPartitionClauseClone = this.leftPartitionClause.clone();
            x.setLeftPartitionClause(leftPartitionClauseClone);
        }

        x.joinType = this.joinType;

        ISQLTableReference rightClone = this.right.clone();
        x.setRight(rightClone);

        if (this.rightPartitionClause != null) {
            SQLPartitionClause rightPartitionClauseClone = this.rightPartitionClause.clone();
            x.setRightPartitionClause(rightPartitionClauseClone);
        }

        for (ISQLJoinCondition condition : conditions) {
            ISQLJoinCondition conditionClone = condition.clone();
            x.addCondition(conditionClone);
        }

    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {

        if (target == null) {
            boolean replace = replaceInList(conditions, source, null, this);
            if (replace) {
                return true;
            }
            return false;
        }


        if (source == left
                && target instanceof ISQLTableReference) {
            setLeft((ISQLTableReference) target);
            return true;
        }

        if (source == right
                && target instanceof ISQLTableReference) {
            setRight((ISQLTableReference) target);
            return true;
        }

        if (target instanceof ISQLJoinCondition) {
            boolean replace = replaceInList(conditions, source, (ISQLJoinCondition) target, this);
            if (replace) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAlias(String alias) {
        long aliasLowerHash = HashUtils.fnv_1a_64_lower(alias);
        return containsAlias(aliasLowerHash);
    }

    @Override
    public boolean containsAlias(long aliasLowerHash) {
        if (left != null && left.containsAlias(aliasLowerHash)) {
            return true;
        }
        if (right != null && right.containsAlias(aliasLowerHash)) {
            return true;
        }
        return false;
    }

    public boolean addOnConditionIfAbsent(ISQLExpr condition) {

        if (this.conditions != null
                && this.conditions.size() > 1) {
            throw new UnsupportedOperationException();
        }

        ISQLJoinCondition item = this.conditions.get(0);

        if (item != null
                && !(item instanceof SQLJoinOnCondition)) {
            return false;
        }

        if (this.containsCondition(condition)) {
            return false;
        }

        SQLJoinOnCondition onCondition = (SQLJoinOnCondition) item;

        if (onCondition != null) {
            condition = SQLBinaryOperatorExpr.and(onCondition.getCondition(), condition);
        }
        this.conditions.set(0, SQLJoinOnCondition.of(condition));
        return true;
    }

    public boolean addOnConditionIfAbsent(ISQLName leftAlias, SQLJoinType joinType, ISQLName rightAlias, SQLBinaryOperatorExpr condition) {

//        if (leftAlias == null
//                && rightAlias == null) {
//            return false;
//        }
//
//        if (leftAlias == null
//                && rightAlias != null
//                && SQLUtils.nameEqualsIgnoreCase(rightAlias, this.right.computeAlias(), false)) {
//            boolean addOnConditionIfAbsent = this.addOnConditionIfAbsent(condition);
//            if (addOnConditionIfAbsent
//                    && this.joinType == SQLJoinType.COMMA) {
//                this.setJoinType(SQLJoinType.INNER_JOIN);
//            }
//            return addOnConditionIfAbsent;
//        }
//
//
//        if (leftAlias == null
//                || rightAlias == null
//                || joinType == null) {
//            return false;
//        }
//
//        // A , B     B == right => A joinType B ON condition
//        if (SQLUtils.nameEqualsIgnoreCase(this.right.computeAlias(), rightAlias, false)) {
//            if (this.joinType == SQLJoinType.COMMA || this.joinType == joinType) {
//                boolean addOnConditionIfAbsent = this.addOnConditionIfAbsent(condition);
//                if (addOnConditionIfAbsent) {
//                    this.setJoinType(joinType);
//                }
//                return addOnConditionIfAbsent;
//            }
//            return false;
//        }
//
//
//        // A, B     B = left => A join B ON condition
//        if (SQLUtils.nameEqualsIgnoreCase(this.right.computeAlias(), leftAlias, false)) {
//            if (this.joinType == SQLJoinType.COMMA) {
//                boolean addOnConditionIfAbsent = this.addOnConditionIfAbsent(condition);
//                if (addOnConditionIfAbsent) {
//                    if (joinType == SQLJoinType.RIGHT_JOIN) {
//                        setJoinType(SQLJoinType.LEFT_JOIN);
//                    } else if (joinType == SQLJoinType.LEFT_JOIN) {
//                        setJoinType(SQLJoinType.RIGHT_JOIN);
//                    }
//                }
//                return addOnConditionIfAbsent;
//            }
//            return false;
//        }
//
//
//        if (this.left instanceof SQLJoinTableReference) {
//            return ((SQLJoinTableReference) this.left).addOnConditionIfAbsent(leftAlias, joinType, rightAlias, condition);
//        }

        return false;
    }


    public boolean containsCondition(ISQLExpr condition) {
        for (ISQLJoinCondition item : this.conditions) {
            boolean equals = item.equals(condition);
            if (equals) {
                return true;
            }
        }
        return false;
    }

    public static ISQLTableReference addTableReference(ISQLTableReference left, SQLJoinType joinType, ISQLTableReference right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        if (joinType == null) {
            throw new IllegalArgumentException("join type is null.");
        }

        if (right instanceof SQLJoinTableReference) {
            return addTableReference(addTableReference(left, joinType, ((SQLJoinTableReference) right).getLeft()), ((SQLJoinTableReference) right).getJoinType(), ((SQLJoinTableReference) right).getRight());
        }

        return SQLJoinTableReference.of(left, joinType, right);
    }

    public ISQLTableReference getLeft() {
        return left;
    }

    public void setLeft(ISQLTableReference left) {
        setChildParent(left);
        this.left = left;
    }

    public SQLPartitionClause getLeftPartitionClause() {
        return leftPartitionClause;
    }

    public void setLeftPartitionClause(SQLPartitionClause leftPartitionClause) {
        setChildParent(leftPartitionClause);
        this.leftPartitionClause = leftPartitionClause;
    }

    public SQLJoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(SQLJoinType joinType) {
        this.joinType = joinType;
    }

    public ISQLTableReference getRight() {
        return right;
    }

    public void setRight(ISQLTableReference right) {
        setChildParent(right);
        this.right = right;
    }

    public SQLPartitionClause getRightPartitionClause() {
        return rightPartitionClause;
    }

    public void setRightPartitionClause(SQLPartitionClause rightPartitionClause) {
        setChildParent(rightPartitionClause);
        this.rightPartitionClause = rightPartitionClause;
    }

    public List<ISQLJoinCondition> getConditions() {
        return conditions;
    }

    public void addCondition(ISQLJoinCondition condition) {
        if (condition == null) {
            return;
        }
        setChildParent(condition);
        this.conditions.add(condition);
    }

    public void setCondition(ISQLJoinCondition condition) {
        if (condition == null) {
            return;
        }
        setChildParent(condition);
        this.conditions.clear();
        this.conditions.add(condition);
    }

    public interface ISQLJoinCondition extends ISQLExpr {
        @Override
        ISQLJoinCondition clone();
    }

    /**
     * ON <search condition>
     * on xx = xx
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#join%20condition
     */
    public static class SQLJoinOnCondition extends AbstractSQLExpr implements ISQLJoinCondition {
        protected ISQLExpr condition;

        public SQLJoinOnCondition() {
        }

        public SQLJoinOnCondition(ISQLExpr condition) {
            setCondition(condition);
        }

        public static SQLJoinOnCondition of(ISQLExpr condition) {
            return new SQLJoinOnCondition(condition);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, condition);
            }
        }

        @Override
        public SQLJoinOnCondition clone() {
            SQLJoinOnCondition x = new SQLJoinOnCondition();

            ISQLExpr conditionClone = this.condition.clone();
            x.setCondition(conditionClone);

            return x;
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            if (source == condition) {
                setCondition(target);
                return true;
            }
            return false;
        }

        public ISQLExpr getCondition() {
            return condition;
        }

        public void setCondition(ISQLExpr condition) {
            setChildParent(condition);
            this.condition = condition;
        }
    }

    /**
     * USING <left paren> <join column list> <right paren>
     * <p>
     * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#named%20columns%20join
     */
    public static class SQLJoinUsingCondition extends AbstractSQLExpr implements ISQLJoinCondition {
        protected final List<ISQLExpr> columns = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, columns);
            }
        }

        @Override
        public SQLJoinUsingCondition clone() {
            SQLJoinUsingCondition x = new SQLJoinUsingCondition();

            for (ISQLExpr column : this.columns) {
                ISQLExpr columnClone = column.clone();
                x.addColumn(columnClone);
            }
            return x;
        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            boolean replace = replaceInList(columns, source, target, this);
            if (replace) {
                return true;
            }
            return false;
        }

        public List<ISQLExpr> getColumns() {
            return columns;
        }

        public void addColumn(ISQLExpr column) {
            if (column == null) {
                return;
            }
            setChildParent(column);
            this.columns.add(column);
        }
    }


    public enum SQLJoinType implements ISQLASTEnum {
        COMMA(","),

        JOIN("JOIN", "JOIN"),

        UNION_JOIN("union join", "UNION JOIN"),

        INNER_JOIN("inner join", "INNER JOIN"),

        CROSS_JOIN("cross join", "CROSS JOIN"),
        CROSS_APPLY("cross apply", "CROSS APPLY"),

        LEFT_JOIN("left join", "LEFT JOIN"),
        LEFT_OUTER_JOIN("left outer join", "LEFT OUTER JOIN"),

        LEFT_SEMI_JOIN("LEFT_SEMI_JOIN", "LEFT_SEMI_JOIN"),
        LEFT_ANTI_JOIN("LEFT_ANTI_JOIN", "LEFT_ANTI_JOIN"),

        RIGHT_JOIN("right join", "RIGHT JOIN"),
        RIGHT_OUTER_JOIN("right outer join", "RIGHT OUTER JOIN"),

        FULL_JOIN("full join", "FULL JOIN"),
        FULL_OUTER_JOIN("full outer join", "FULL OUTER JOIN"),

        NATURAL_JOIN("natural join", "NATURAL JOIN"),
        NATURAL_INNER_JOIN("natural inner join", "NATURAL INNER JOIN"),

        NATURAL_LEFT_JOIN("natural left join", "NATURAL LEFT JOIN"),
        NATURAL_LEFT_OUTER_JOIN("natural left outer join", "NATURAL LEFT OUTER JOIN"),

        NATURAL_RIGHT_JOIN("natural right join", "NATURAL RIGHT JOIN"),
        NATURAL_RIGHT_OUTER_JOIN("natural right outer join", "NATURAL RIGHT OUTER JOIN"),

        NATURAL_FULL_JOIN("natural full join", "NATURAL FULL JOIN"),
        NATURAL_FULL_OUTER_JOIN("natural full outer join", "NATURAL FULL OUTER JOIN"),

        STRAIGHT_JOIN("straight_join", "STRAIGHT_JOIN"),
        STRAIGHT_UNDERLINE_JOIN("straight_underline_join", "STRAIGHT_UNDERLINE_JOIN"),

        OUTER_APPLY("outer apply", "OUTER APPLY"),
        ;

        public final String lower;
        public final String upper;

        SQLJoinType(String name) {
            this(name, name);
        }

        SQLJoinType(String lower, String upper) {
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


    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
     *
     * @param joinType SQLJoinType
     * @return true :is Outer Join  , false : no is Outer Join
     */
    public boolean isOuterJoin(SQLJoinType joinType) {
        switch (joinType) {
//            case FULL_JOIN:
//            case FULL_OUTER_JOIN:
//            case NATURAL_FULL_JOIN:
//            case NATURAL_FULL_OUTER_JOIN:
//
//            case LEFT_JOIN:
//            case LEFT_OUTER_JOIN:
//            case NATURAL_LEFT_JOIN:
//            case NATURAL_LEFT_OUTER_JOIN:
//
//            case RIGHT_JOIN:
//            case RIGHT_OUTER_JOIN:
//            case NATURAL_RIGHT_JOIN:
//            case NATURAL_RIGHT_OUTER_JOIN:
//                return true;
            default:
                return false;
        }
    }

}
