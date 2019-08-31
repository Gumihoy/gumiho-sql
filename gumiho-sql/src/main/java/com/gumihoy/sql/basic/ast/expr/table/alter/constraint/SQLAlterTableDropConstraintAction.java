package com.gumihoy.sql.basic.ast.expr.table.alter.constraint;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * DROP CONSTRAINT <constraint name> <drop behavior>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#drop%20table%20constraint%20definition
 * <p>
 * <p>
 * DROP CONSTRAINT [ IF EXISTS ]  constraint_name [ RESTRICT | CASCADE ]
 * https://www.postgresql.org/docs/devel/static/sql-altertable.html
 * <p>
 * DROP CONSTRAINT constraint_name [ CASCADE ] } [ ONLINE ]
 * drop_constraint_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableDropConstraintAction extends AbstractSQLAlterTableDropConstraintAction implements ISQLAlterTableAction {

    protected boolean ifExists;
    protected ISQLName name;

    public SQLAlterTableDropConstraintAction() {
    }

    public SQLAlterTableDropConstraintAction(ISQLName name) {
        setName(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if(visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLAlterTableDropConstraintAction clone() {
        SQLAlterTableDropConstraintAction x = new SQLAlterTableDropConstraintAction();
        this.cloneTo(x);

        x.ifExists = this.ifExists;
        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);
        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName)target);
            return true;
        }
        return false;
    }

    public boolean isIfExists() {
        return ifExists;
    }

    public void setIfExists(boolean ifExists) {
        this.ifExists = ifExists;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        this.name = name;
    }

//    public SQLCascadeType getCascade() {
//        return cascade;
//    }
//
//    public void setCascade(SQLCascadeType cascade) {
//        this.cascade = cascade;
//    }

}
