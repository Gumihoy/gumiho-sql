package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * constant CONSTANT datatype [NOT NULL] { := | DEFAULT } expression ;
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/constant-declaration.html#GUID-C6DA65F8-3F0C-43F3-8BC6-231064E8C1B6
 * @author kent on 2019-07-25.
 */
public class SQLConstantDeclaration extends AbstractSQLExpr {

    protected ISQLName name;
    protected ISQLDataType dataType;
    protected boolean notNull;
    protected SQLDefaultClause defaultClause;

    public SQLConstantDeclaration(ISQLName name, ISQLDataType dataType, SQLDefaultClause defaultClause) {
        this(name, dataType, false, defaultClause);
    }

    public SQLConstantDeclaration(ISQLName name, ISQLDataType dataType, boolean notNull, SQLDefaultClause defaultClause) {
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
        this.name = name;
    }

    public ISQLDataType getDataType() {
        return dataType;
    }

    public void setDataType(ISQLDataType dataType) {
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
        this.defaultClause = defaultClause;
    }
}
