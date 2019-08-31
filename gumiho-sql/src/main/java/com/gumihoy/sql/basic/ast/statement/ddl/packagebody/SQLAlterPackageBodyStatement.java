package com.gumihoy.sql.basic.ast.statement.ddl.packagebody;

import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CREATE-PACKAGE-statement.html#GUID-03A70A54-90FF-4293-B6B8-F0B35E184AC5
 *
 * @author kent on 2018/5/31.
 */
public class SQLAlterPackageBodyStatement extends AbstractSQLStatement implements ISQLCreateStatement {


    public SQLAlterPackageBodyStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//
//        }
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.PACKAGE_BODY_ALTER;
    }

}
