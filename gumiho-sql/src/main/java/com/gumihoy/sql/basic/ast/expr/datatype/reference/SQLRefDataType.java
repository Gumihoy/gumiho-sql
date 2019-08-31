package com.gumihoy.sql.basic.ast.expr.datatype.reference;


import com.gumihoy.sql.basic.ast.expr.datatype.AbstractSQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * REF <left paren> <referenced type> <right paren> [ scope tableName]
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#reference%20type
 * <p>
 * REF name
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/Data-Types.html#GUID-1E278F1C-0EC1-4626-8D93-80D8230AB8F1
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/datatype-attribute.html#GUID-B4A364AB-7CC2-4B3F-AF52-09A752029C8E
 *
 * @author kent on 2018/4/19.
 */
public class SQLRefDataType extends AbstractSQLDataType implements ISQLRefDataType {

    protected ISQLName tableName;

    public SQLRefDataType() {
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, arguments);
            this.acceptChild(visitor, tableName);
        }
    }


    public ISQLName getTableName() {
        return tableName;
    }

    public void setTableName(ISQLName tableName) {
        setChildParent(tableName);
        this.tableName = tableName;
    }

}
