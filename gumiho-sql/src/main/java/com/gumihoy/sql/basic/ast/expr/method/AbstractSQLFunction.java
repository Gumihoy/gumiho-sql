package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.enums.SQLSetQuantifier;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.util.HashUtils;
import com.gumihoy.sql.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * function [( [[ DISTINCT | ALL ] argument [DEFAULT return_value ON CONVERSION ERROR] [, argument, ...]])]
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#method%20invocation
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#static%20method%20invocation
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#aggregate%20function
 * <p>
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/func-op-summary-ref.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Functions.html
 * <p>
 * https://www.postgresql.org/docs/devel/static/functions.html
 *
 * @author kent on 2018/4/24.
 */
public abstract class AbstractSQLFunction extends AbstractSQLExpr implements ISQLFunction {

    protected ISQLExpr name;

    protected String simpleName;
    protected long simpleLowerNameHash;

    protected boolean paren = true;
    protected SQLSetQuantifier setQuantifier;
    protected final List<ISQLExpr> arguments = new ArrayList<>();

    protected boolean deterministic;

    protected SQLDefaultOnConversionError defaultOnConversionError;

//    protected SQLPartitionByClause partitionClause;

    protected SQLOrderByClause orderByClause;

//    protected List<SQLFunctionType> functionTypes = new ArrayList<>();


    public AbstractSQLFunction() {
    }

    public AbstractSQLFunction(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }
        setName(SQLUtils.ofName(name));
    }

    public AbstractSQLFunction(ISQLExpr name) {
        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }
        setName(name);
    }

    @Override
    public AbstractSQLFunction clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void cloneTo(AbstractSQLFunction x) {
        super.cloneTo(x);

        x.name = this.name;
        x.simpleLowerNameHash = this.simpleLowerNameHash;

        ISQLExpr nameExprClone = this.name.clone();
        x.setName(nameExprClone);

        for (ISQLExpr argument : x.arguments) {
            ISQLExpr argumentClone = argument.clone();
            x.addArgument(argumentClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        boolean replace = replaceInList(this.arguments, source, target, this);
        if (replace) {
            return true;
        }

        return false;
    }

    @Override
    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        if (name != null) {
            name.setParent(this);
        }
        clear();
        this.name = name;
    }

    public void clear() {
        this.simpleName = null;
        this.simpleLowerNameHash = 0;
    }

    @Override
    public String getSimpleName() {
        if (simpleName == null) {
            this.simpleName = name.toString();
        }
        return simpleName;
    }

    @Override
    public String getFullName() {
        return null;
    }


    @Override
    public long lowerHash() {
        if (this.simpleLowerNameHash == 0) {
            this.simpleLowerNameHash = HashUtils.fnv_1a_64_lower(getSimpleName());
        }
        return simpleLowerNameHash;
    }

    @Override
    public boolean isParen() {
        return paren;
    }

    @Override
    public void setParen(boolean paren) {
        this.paren = paren;
    }

    public SQLSetQuantifier getSetQuantifier() {
        return setQuantifier;
    }

    public void setSetQuantifier(SQLSetQuantifier setQuantifier) {
        this.setQuantifier = setQuantifier;
    }

    @Override
    public List<ISQLExpr> getArguments() {
        return arguments;
    }

    public void addArgument(ISQLExpr argument) {
        if (argument == null) {
            return;
        }
        argument.setParent(this);
        this.arguments.add(argument);
    }

    public boolean isDeterministic() {
        return deterministic;
    }

    public void setDeterministic(boolean deterministic) {
        this.deterministic = deterministic;
    }

    public SQLDefaultOnConversionError getDefaultOnConversionError() {
        return defaultOnConversionError;
    }

    public void setDefaultOnConversionError(SQLDefaultOnConversionError defaultOnConversionError) {
        setChildParent(defaultOnConversionError);
        this.defaultOnConversionError = defaultOnConversionError;
    }

//    public SQLPartitionByClause getPartitionClause() {
//        return partitionClause;
//    }
//
//    public void setPartitionClause(SQLPartitionByClause partitionClause) {
//        setChildParent(partitionClause);
//        this.partitionClause = partitionClause;
//    }

    public SQLOrderByClause getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(SQLOrderByClause orderByClause) {
        setChildParent(orderByClause);
        this.orderByClause = orderByClause;
    }


//    @Override
//    public List<SQLFunctionType> getFunctionType() {
//        return functionTypes;
//    }
//
//    public void addFunctionType(SQLFunctionType functionType) {
//        if (functionType == null) {
//            return;
//        }
//        this.functionTypes.add(functionType);
//    }


    /**
     * DEFAULT return_value ON CONVERSION ERROR
     */
    public static class SQLDefaultOnConversionError extends AbstractSQLExpr {

        protected ISQLExpr value;

        public SQLDefaultOnConversionError(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }
}
