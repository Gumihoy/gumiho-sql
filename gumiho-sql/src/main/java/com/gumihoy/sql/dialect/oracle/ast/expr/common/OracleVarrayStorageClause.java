package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLLobStorageParameter;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * STORE AS [SECUREFILE | BASICFILE] LOB { [LOB_segname] ( LOB_storage_parameters ) | LOB_segname }
 * <p>
 * varray_storage_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
 *
 * @author kent on 2018/6/27.
 */
public class OracleVarrayStorageClause extends AbstractOracleExpr {

//    protected SQLBasicFileType storeAsType;

    protected ISQLExpr segName;

    protected List<ISQLLobStorageParameter> parameters;

    @Override
    public void accept0(IOracleASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, segName);
//            this.acceptChild(visitor, lobStorageParameters);
//        }
    }

    @Override
    public OracleVarrayStorageClause clone() {
        OracleVarrayStorageClause x = new OracleVarrayStorageClause();
        return x;
    }

//    public SQLBasicFileType getStoreAsType() {
//        return storeAsType;
//    }
//
//    public void setStoreAsType(SQLBasicFileType storeAsType) {
//        this.storeAsType = storeAsType;
//    }

    public ISQLExpr getSegName() {
        return segName;
    }

    public void setSegName(ISQLExpr segName) {
        setChildParent(segName);
        this.segName = segName;
    }

    public List<ISQLLobStorageParameter> getParameters() {
        return parameters;
    }

    public void setLobStorageParameter(ISQLLobStorageParameter parameter) {
        if (parameter == null) {
            return;
        }
        if (parameters == null) {
            parameters = new ArrayList<>();
        }
        setChildParent(parameter);
        parameters.add(parameter);
    }
}
