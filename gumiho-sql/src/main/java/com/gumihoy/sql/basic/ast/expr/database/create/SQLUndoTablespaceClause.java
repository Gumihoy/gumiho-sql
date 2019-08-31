package com.gumihoy.sql.basic.ast.expr.database.create;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLFileSpecification;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * [ BIGFILE | SMALLFILE ]
 * UNDO TABLESPACE tablespace
 * [ DATAFILE file_specification [, file_specification ]...]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/undo_tablespace.html
 *
 * @author kent on 2019-07-21.
 */
public class SQLUndoTablespaceClause extends AbstractSQLExpr {

    protected SQLFileType fileType;
    protected ISQLExpr name;
    protected final List<SQLFileSpecification> dataFiles = new ArrayList<>();

    public SQLUndoTablespaceClause(ISQLExpr name) {
        setName(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, dataFiles);
        }
    }

    @Override
    public SQLUndoTablespaceClause clone() {
        return null;
    }


    public SQLFileType getFileType() {
        return fileType;
    }

    public void setFileType(SQLFileType fileType) {
        this.fileType = fileType;
    }


    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        this.name = name;
    }

    public List<SQLFileSpecification> getDataFiles() {
        return dataFiles;
    }

    public void addDataFile(SQLFileSpecification dataFile) {
        if (dataFile == null) {
            return;
        }
        setChildParent(dataFile);
        dataFiles.add(dataFile);
    }

    public enum SQLFileType implements ISQLASTEnum {
        BIGFILE("bigfile", "BIGFILE"),
        SMALLFILE("smallfile", "SMALLFILE"),
        ;

        public final String lower;
        public final String upper;

        SQLFileType(String lower, String upper) {
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
