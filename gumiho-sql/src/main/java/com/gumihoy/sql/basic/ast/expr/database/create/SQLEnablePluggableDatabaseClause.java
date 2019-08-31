package com.gumihoy.sql.basic.ast.expr.database.create;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 *
 * ENABLE PLUGGABLE DATABASE
 *   [ SEED
 *   [ file_name_convert ]
 *   [ SYSTEM tablespace_datafile_clauses ]
 *   [ SYSAUX tablespace_datafile_clauses ]
 *   ]
 *   [ undo_mode_clause ]
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-DATABASE.html#GUID-ECE717DF-F116-4151-927C-2E51BB9DD39C
 *
 * @author kent on 2019-07-21.
 */
public class SQLEnablePluggableDatabaseClause extends AbstractSQLExpr {

    protected boolean seed;
    protected SQLFileNameConvertClause fileNameConvert;
    protected SQLTablespaceDataFileClause system;
    protected SQLTablespaceDataFileClause sysaux;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, fileNameConvert);
            this.acceptChild(visitor, system);
            this.acceptChild(visitor, sysaux);
        }
    }

    @Override
    public SQLEnablePluggableDatabaseClause clone() {
        return null;
    }

    public boolean isSeed() {
        return seed;
    }

    public void setSeed(boolean seed) {
        this.seed = seed;
    }

    public SQLFileNameConvertClause getFileNameConvert() {
        return fileNameConvert;
    }

    public void setFileNameConvert(SQLFileNameConvertClause fileNameConvert) {
        this.seed = true;
        this.fileNameConvert = fileNameConvert;
    }

    public SQLTablespaceDataFileClause getSystem() {
        return system;
    }

    public void setSystem(SQLTablespaceDataFileClause system) {
        this.system = system;
    }

    public SQLTablespaceDataFileClause getSysaux() {
        return sysaux;
    }

    public void setSysaux(SQLTablespaceDataFileClause sysaux) {
        this.sysaux = sysaux;
    }
}
