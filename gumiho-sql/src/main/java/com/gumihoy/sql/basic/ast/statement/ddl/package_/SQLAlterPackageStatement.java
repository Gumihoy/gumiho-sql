package com.gumihoy.sql.basic.ast.statement.ddl.package_;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.package_.alter.ISQLAlterPackageAction;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * ALTER PACKAGE [ schema. ] package_name { package_compile_clause | { EDITIONABLE | NONEDITIONABLE } } ;
 * <p>
 * package_compile_clause: COMPILE [ DEBUG ] [ compiler_parameters_clause ... ] [ REUSE SETTINGS ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/ALTER-PACKAGE-statement.html#GUID-61273667-8D8F-4E79-9D81-072CFFE3A7F1
 *
 * @author kent on 2018/5/31.
 */
public class SQLAlterPackageStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected ISQLName name;

    protected ISQLAlterPackageAction action;

    public SQLAlterPackageStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, action);
        }
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.PACKAGE_ALTER;
    }


    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public ISQLAlterPackageAction getAction() {
        return action;
    }

    public void setAction(ISQLAlterPackageAction action) {
        setChildParent(action);
        this.action = action;
    }
}
