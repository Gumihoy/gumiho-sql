package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * VARRAY varray_item { [ substitutable_column_clause ] varray_storage_clause | substitutable_column_clause }
 *
 * varray_col_properties
 * https://docs.oracle.com/cd/B28359_01/server.111/b28286/statements_6002.htm#i2116664
 * @author kent on 2018/6/28.
 */
public class OracleVarrayColProperty extends AbstractOracleExpr {

    protected ISQLExpr item;

    protected IOracleSubstitutableColumnClause substitutableColumnClause;

//    protected OracleSQLVarrayStorageClause varrayStorageClause;

    @Override
    public void accept0(IOracleASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, item);
//            this.acceptChild(visitor, substitutableColumnClause);
//            this.acceptChild(visitor, varrayStorageClause);
//        }
    }

    @Override
    public OracleVarrayColProperty clone() {
        return null;
    }

    public ISQLExpr getItem() {
        return item;
    }

    public void setItem(ISQLExpr item) {
        setChildParent(item);
        this.item = item;
    }

    public IOracleSubstitutableColumnClause getSubstitutableColumnClause() {
        return substitutableColumnClause;
    }

    public void setSubstitutableColumnClause(IOracleSubstitutableColumnClause substitutableColumnClause) {
        setChildParent(substitutableColumnClause);
        this.substitutableColumnClause = substitutableColumnClause;
    }

//    public OracleSQLVarrayStorageClause getVarrayStorageClause() {
//        return varrayStorageClause;
//    }
//
//    public void setVarrayStorageClause(OracleSQLVarrayStorageClause varrayStorageClause) {
//        setChildParent(varrayStorageClause);
//        this.varrayStorageClause = varrayStorageClause;
//    }
}
