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
public class SQLRoleIdentifiedGloballyAction extends AbstractSQLRoleAction {

    protected ISQLExpr asName;

    public SQLRoleIdentifiedGloballyAction() {
    }

    public SQLRoleIdentifiedGloballyAction(ISQLExpr name) {
        setAsName(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, asName);
        }
    }

    public ISQLExpr getAsName() {
        return asName;
    }

    public void setAsName(ISQLExpr asName) {
        setChildParent(asName);
        this.asName = asName;
    }
}
