package com.gumihoy.sql.basic.ast.expr.database.create;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLFileSpecification;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * DEFAULT TABLESPACE tablespace
 * [ DATAFILE datafile_tempfile_spec ]
 * [ extent_management_clause ]
 * <p>
 * default_tablespace
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-DATABASE.html#GUID-ECE717DF-F116-4151-927C-2E51BB9DD39C
 *
 * @author kent on 2019-07-21.
 */
public class SQLDefaultTablespaceClause extends AbstractSQLExpr implements ISQLTablespaceClause {

    protected ISQLExpr name;
    protected SQLFileSpecification dataFile;
    protected SQLExtentManagementClause extentManagementClause;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, dataFile);
            this.acceptChild(visitor, extentManagementClause);
        }
    }

    @Override
    public SQLDefaultTablespaceClause clone() {
        return null;
    }

    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }

    public SQLFileSpecification getDataFile() {
        return dataFile;
    }

    public void setDataFile(SQLFileSpecification dataFile) {
        setChildParent(dataFile);
        this.dataFile = dataFile;
    }

    public SQLExtentManagementClause getExtentManagementClause() {
        return extentManagementClause;
    }

    public void setExtentManagementClause(SQLExtentManagementClause extentManagementClause) {
        setChildParent(extentManagementClause);
        this.extentManagementClause = extentManagementClause;
    }
}
