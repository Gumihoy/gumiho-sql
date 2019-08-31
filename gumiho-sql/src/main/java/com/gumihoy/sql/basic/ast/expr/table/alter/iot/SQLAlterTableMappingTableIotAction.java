package com.gumihoy.sql.basic.ast.expr.table.alter.iot;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * MAPPING TABLE { allocate_extent_clause | deallocate_unused_clause}
 * <p>
 * alter_mapping_table_clauses
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/17.
 */
public class SQLAlterTableMappingTableIotAction extends AbstractSQLExpr implements ISQLAlterTableIotAction {

    protected ISQLExpr expr;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, expr);
//        }
    }

    @Override
    public SQLAlterTableMappingTableIotAction clone() {
        SQLAlterTableMappingTableIotAction x = new SQLAlterTableMappingTableIotAction();
        return x;
    }




    public ISQLExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLExpr expr) {
        setChildParent(expr);
        this.expr = expr;
    }
}
