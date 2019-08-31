package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * variable datatype [ [ NOT NULL] {:= | DEFAULT} expression ] ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/scalar-variable-declaration.html#GUID-03124315-0E1E-4154-8EBE-12034CA6AD55
 *
 * @author kent on 2018/6/1.
 */
public class SQLVariableDeclaration extends AbstractSQLExpr {

    protected ISQLName name;

    protected ISQLDataType dataType;

    protected boolean notNull;

    protected SQLDefaultClause defaultClause;


    public SQLVariableDeclaration(ISQLName name, ISQLDataType dataType) {
        this(name, dataType, null);
    }

    public SQLVariableDeclaration(ISQLName name, ISQLDataType dataType, SQLDefaultClause defaultClause) {
        this(name, dataType, false, defaultClause);
    }

    public SQLVariableDeclaration(ISQLName name, ISQLDataType dataType, boolean notNull, SQLDefaultClause defaultClause) {
        setName(name);
        setDataType(dataType);
        this.notNull = notNull;
        setDefaultClause(defaultClause);
        setAfterSemi(true);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, dataType);
            this.acceptChild(visitor, defaultClause);
        }
    }


    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public ISQLDataType getDataType() {
        return dataType;
    }

    public void setDataType(ISQLDataType dataType) {
        setChildParent(dataType);
        this.dataType = dataType;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public SQLDefaultClause getDefaultClause() {
        return defaultClause;
    }

    public void setDefaultClause(SQLDefaultClause defaultClause) {
        setChildParent(defaultClause);
        this.defaultClause = defaultClause;
    }
}
