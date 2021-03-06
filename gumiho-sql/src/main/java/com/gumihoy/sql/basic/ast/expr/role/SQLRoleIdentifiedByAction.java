package com.gumihoy.sql.basic.ast.expr.role;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * CREATE ROLE role
 * [ NOT IDENTIFIED
 * | IDENTIFIED { BY password
 * | USING [ schema. ] package
 * | EXTERNALLY
 * | GLOBALLY AS domain_name_of directory_group
 * }
 * ] [ CONTAINER = { CURRENT | ALL } ] ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-ROLE.html#GUID-B2252DC5-5AE7-49B7-9048-98062993E450
 *
 * @author kent on 2019-08-01.
 */
public class SQLRoleIdentifiedByAction extends AbstractSQLRoleAction {

    protected ISQLExpr password;

    public SQLRoleIdentifiedByAction(ISQLExpr password) {
        setPassword(password);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, password);
        }
    }

    public ISQLExpr getPassword() {
        return password;
    }

    public void setPassword(ISQLExpr password) {
        this.password = password;
    }
}
