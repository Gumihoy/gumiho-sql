package com.gumihoy.sql.basic.ast.expr.role;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.role.alter.ISQLAlterRoleAction;
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
public abstract class AbstractSQLRoleAction extends AbstractSQLExpr implements ISQLCreateRoleAction, ISQLAlterRoleAction {

    protected SQLContainerClause containerClause;

    @Override
    public AbstractSQLRoleAction clone() {
        throw new UnsupportedOperationException();
    }

    public void cloneTo(AbstractSQLRoleAction x) {
        super.cloneTo(x);
    }


    public SQLContainerClause getContainerClause() {
        return containerClause;
    }

    public void setContainerClause(SQLContainerClause containerClause) {
        setChildParent(containerClause);
        this.containerClause = containerClause;
    }

    public static class SQLContainerClause extends AbstractSQLExpr implements ISQLCreateRoleAction {
        protected SQLContainerType type;

        public SQLContainerClause(SQLContainerType type) {
            this.type = type;
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLContainerClause clone() {
            return null;
        }

        public SQLContainerType getType() {
            return type;
        }

        public void setType(SQLContainerType type) {
            this.type = type;
        }
    }

    public enum SQLContainerType implements ISQLASTEnum {
        CURRENT("current", "CURRENT"),
        ALL("all", "ALL");

        public final String lower;
        public final String upper;


        SQLContainerType(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }

        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }
    }

}
