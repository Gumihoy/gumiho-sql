package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * collection.name (expr (, expr)...)
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/collection-method.html#GUID-7AF1A3C4-D04B-4F91-9D7B-C92C75E3A300
 *
 * @author kent on 2018/6/14.
 */
public class SQLCollectionMethodInvocation extends AbstractSQLExpr {

    protected ISQLName collection;

    protected SQLCollectionMethodName name;

    protected final List<ISQLExpr> arguments = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, collection);
//            this.acceptChild(visitor, arguments);
//        }
    }

    public ISQLName getCollection() {
        return collection;
    }

    public void setCollection(ISQLName collection) {
        setChildParent(collection);
        this.collection = collection;
    }

    public SQLCollectionMethodName getName() {
        return name;
    }

    public void setName(SQLCollectionMethodName name) {
        this.name = name;
    }

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

    public enum SQLCollectionMethodName {
//        COUNT(SQLReserved.COUNT),
//        DELETE(SQLReserved.DELETE),
//        EXISTS(SQLReserved.EXISTS),
//        EXTEND(SQLReserved.EXTEND),
//        FIRST(SQLReserved.FIRST),
//        LAST(SQLReserved.LAST),
//        LIMIT(SQLReserved.LIMIT),
//        NEXT(SQLReserved.NEXT),
//        PRIOR(SQLReserved.PRIOR),
//        TRIM(SQLReserved.TRIM),;
//
//        public final SQLReserved name;
//
//        SQLCollectionMethodName(SQLReserved name) {
//            this.name = name;
//        }
//
//
    }
}
