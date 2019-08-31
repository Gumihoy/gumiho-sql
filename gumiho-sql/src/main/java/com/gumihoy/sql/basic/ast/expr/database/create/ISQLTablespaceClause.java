package com.gumihoy.sql.basic.ast.expr.database.create;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLFileSpecification;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * { EXTENT MANAGEMENT LOCAL
 * | DATAFILE file_specification [, file_specification ]...
 * | SYSAUX DATAFILE file_specification [, file_specification ]...
 * | default_tablespace
 * | default_temp_tablespace
 * | undo_tablespace
 * }
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/tablespace_clauses.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-DATABASE.html#GUID-ECE717DF-F116-4151-927C-2E51BB9DD39C
 *
 * @author kent on 2019-07-21.
 */
public interface ISQLTablespaceClause extends ISQLCreateDatabaseAction {
    @Override
    ISQLTablespaceClause clone();

    class SQLExtentManagementLocalClause extends AbstractSQLExpr implements ISQLTablespaceClause {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLExtentManagementLocalClause clone() {
            return null;
        }
    }

    class SQLDataFileClause extends AbstractSQLExpr implements ISQLTablespaceClause {
        protected final List<SQLFileSpecification> items = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, items);
            }
        }

        @Override
        public SQLDataFileClause clone() {
            return null;
        }

        public List<SQLFileSpecification> getItems() {
            return items;
        }

        public void addItem(SQLFileSpecification item) {
            if (item == null) {
                return;
            }
            setChildParent(item);
            items.add(item);
        }
    }

    /**
     * SYSAUX DATAFILE file_specification [, file_specification ]...
     */
    class SQLSysauxDataFileClause extends AbstractSQLExpr implements ISQLTablespaceClause {

        protected final List<SQLFileSpecification> dataFiles = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, dataFiles);
            }
        }

        @Override
        public SQLSysauxDataFileClause clone() {
            return null;
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
    }


}
