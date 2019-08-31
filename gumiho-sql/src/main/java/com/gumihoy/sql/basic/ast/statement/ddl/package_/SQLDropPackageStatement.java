package com.gumihoy.sql.basic.ast.statement.ddl.package_;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * DROP PACKAGE [ schema. ] package ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/DROP-PACKAGE-statement.html#GUID-91CB731B-471A-409B-A22B-4C1AF9E5903B
 *
 * @author kent on 2018/5/31.
 */
public class SQLDropPackageStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected ISQLName name;

    public SQLDropPackageStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLDropPackageStatement clone() {
        SQLDropPackageStatement x = new SQLDropPackageStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLDropPackageStatement x) {
        super.cloneTo(x);
        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name) {
            this.setName((ISQLName) target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.PACKAGE_DROP;
    }


    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }
}
