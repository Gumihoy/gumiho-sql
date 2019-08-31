package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * ([ TYPE access_driver_type ] [ external_table_data_props ] ) [ REJECT LIMIT { integer | UNLIMITED } ] [ inmemory_table_clause ]
 * <p>
 * external_table_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/25.
 */
public class SQLExternalTableClause extends AbstractSQLExpr {

    protected ISQLName accessDriverType;
    protected final List<ISQLExpr> externalTableDataProps = new ArrayList<>();
    protected ISQLExpr rejectLimit;
//    protected SQLInMemoryTableClause inMemoryTableClause;

    @Override
    public void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, accessDriverType);
//            this.acceptChild(visitor, externalTableDataProps);
//            this.acceptChild(visitor, rejectLimit);
//            this.acceptChild(visitor, inMemoryTableClause);
//        }
    }

    @Override
    public SQLExternalTableClause clone() {
        SQLExternalTableClause x = new SQLExternalTableClause();

        return x;
    }

    public ISQLName getAccessDriverType() {
        return accessDriverType;
    }

    public void setAccessDriverType(ISQLName accessDriverType) {
        setChildParent(accessDriverType);
        this.accessDriverType = accessDriverType;
    }

    public List<ISQLExpr> getExternalTableDataProps() {
        return externalTableDataProps;
    }

    public void addExternalTableDataProp(ISQLExpr externalTableDataProp) {
        if (externalTableDataProp == null) {
            return;
        }
        setChildParent(externalTableDataProp);
        this.externalTableDataProps.add(externalTableDataProp);
    }
    public void addExternalTableDataProps(List<ISQLExpr> externalTableDataProps) {
        if (externalTableDataProps == null) {
            return;
        }
        for (ISQLExpr externalTableDataProp : externalTableDataProps) {
            addExternalTableDataProp(externalTableDataProp);
        }
    }

    public ISQLExpr getRejectLimit() {
        return rejectLimit;
    }

    public void setRejectLimit(ISQLExpr rejectLimit) {
        setChildParent(rejectLimit);
        this.rejectLimit = rejectLimit;
    }

//    public OracleSQLInMemoryTableClause getInMemoryTableClause() {
//        return inMemoryTableClause;
//    }
//
//    public void setInMemoryTableClause(OracleSQLInMemoryTableClause inMemoryTableClause) {
//        setChildParent(inMemoryTableClause);
//        this.inMemoryTableClause = inMemoryTableClause;
//    }
}
