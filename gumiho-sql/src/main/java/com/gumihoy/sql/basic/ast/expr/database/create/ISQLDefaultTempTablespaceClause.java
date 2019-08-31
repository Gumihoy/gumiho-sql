package com.gumihoy.sql.basic.ast.expr.database.create;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLFileSpecification;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * [ BIGFILE | SMALLFILE ] DEFAULT
 * { { TEMPORARY TABLESPACE }
 * | { LOCAL TEMPORARY TABLESPACE FOR { ALL | LEAF } }
 * } tablespace
 * [ TEMPFILE file_specification [, file_specification ]...]
 * [ extent_management_clause ]
 * <p>
 * default_temp_tablespace
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-DATABASE.html#GUID-ECE717DF-F116-4151-927C-2E51BB9DD39C
 *
 * @author kent on 2019-07-21.
 */
public interface ISQLDefaultTempTablespaceClause extends ISQLTablespaceClause {

    @Override
    ISQLDefaultTempTablespaceClause clone();

    SQLFileType getFileType();

    void setFileType(SQLFileType fileType);

    ISQLExpr getName();

    void setName(ISQLExpr name);

    List<SQLFileSpecification> getTempFiles();

    void addTempFile(SQLFileSpecification tempFile);

    SQLExtentManagementClause getExtentManagementClause();

    public void setExtentManagementClause(SQLExtentManagementClause extentManagementClause);


    abstract class AbstractSQLDefaultTempTablespaceClause extends AbstractSQLExpr implements ISQLDefaultTempTablespaceClause {

        protected SQLFileType fileType;
        protected ISQLExpr name;
        protected final List<SQLFileSpecification> tempFiles = new ArrayList<>();
        protected SQLExtentManagementClause extentManagementClause;

        @Override
        public AbstractSQLDefaultTempTablespaceClause clone() {
            throw new UnsupportedOperationException();
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

        public List<SQLFileSpecification> getTempFiles() {
            return tempFiles;
        }

        public void addTempFile(SQLFileSpecification tempFile) {
            if (tempFile == null) {
                return;
            }
            setChildParent(tempFile);
            tempFiles.add(tempFile);
        }

        public SQLExtentManagementClause getExtentManagementClause() {
            return extentManagementClause;
        }

        public void setExtentManagementClause(SQLExtentManagementClause extentManagementClause) {
            setChildParent(extentManagementClause);
            this.extentManagementClause = extentManagementClause;
        }
    }

    class SQLDefaultTemporaryTablespaceClause extends AbstractSQLDefaultTempTablespaceClause {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
                this.acceptChild(visitor, tempFiles);
                this.acceptChild(visitor, extentManagementClause);
            }
        }
    }

    class SQLDefaultLocalTemporaryTablespaceClause extends AbstractSQLDefaultTempTablespaceClause {
        protected SQLForType forType;
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
                this.acceptChild(visitor, tempFiles);
                this.acceptChild(visitor, extentManagementClause);
            }
        }

        public SQLForType getForType() {
            return forType;
        }

        public void setForType(SQLForType forType) {
            this.forType = forType;
        }
    }


    enum SQLFileType implements ISQLASTEnum {

        BIGFILE("bigfile", "BIGFILE"),
        SMALLFILE("smallfile", "SMALLFILE");

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


    public enum  SQLForType implements ISQLASTEnum {
        ALL("all", "ALL"),
        LEAF("leaf", "LEAF");

        public final String lower;
        public final String upper;

        SQLForType(String lower, String upper) {
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
