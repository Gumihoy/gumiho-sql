package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * COLUMN column substitutable_column_clause
 * <p>
 * <p>
 * object_type_col_properties
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
 *
 * @author kent on 2018/6/22.
 */
public class OracleObjectTypeColProperty extends AbstractOracleExpr implements IOracleColumnProperty {

    protected ISQLExpr column;
    protected IOracleSubstitutableColumnClause substitutableColumnClause;

    @Override
    public void accept0(IOracleASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, column);
//            this.acceptChild(visitor, substitutableColumnClause);
//        }
    }

    @Override
    public OracleObjectTypeColProperty clone() {
        OracleObjectTypeColProperty x = new OracleObjectTypeColProperty();

        return x;
    }


    public ISQLExpr getColumn() {
        return column;
    }

    public void setColumn(ISQLExpr column) {
        setChildParent(column);
        this.column = column;
    }

    public IOracleSubstitutableColumnClause getSubstitutableColumnClause() {
        return substitutableColumnClause;
    }

    public void setSubstitutableColumnClause(IOracleSubstitutableColumnClause substitutableColumnClause) {
        setChildParent(substitutableColumnClause);
        this.substitutableColumnClause = substitutableColumnClause;
    }
}
