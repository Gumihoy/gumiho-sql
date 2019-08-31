package com.gumihoy.sql.basic.ast.expr.database.create;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLFileSpecification;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kent on 2019-07-20.
 */
public interface ISQLDatabaseLoggingClause extends ISQLCreateDatabaseAction {

    @Override
    ISQLDatabaseLoggingClause clone();

    /**
     * LOGFILE [ GROUP integer ] file_specification [, [ GROUP integer ] file_specification ]...
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/create_database.html
     *
     * @author kent on 2019-07-19.
     */
    class SQLLogFileAction extends AbstractSQLExpr implements ISQLCreateDatabaseAction {

        protected final List<SQLLogFileActionItem> items = new ArrayList<>();

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, items);
            }
        }


        @Override
        public SQLLogFileAction clone() {
            return null;
        }


        public List<SQLLogFileActionItem> getItems() {
            return items;
        }

        public void addItem(SQLLogFileActionItem item) {
            if (item == null) {
                return;
            }
            setChildParent(item);
            items.add(item);
        }

        /**
         * [ GROUP integer ] file_specification
         */
        public static class SQLLogFileActionItem extends AbstractSQLExpr {

            protected ISQLExpr groupValue;
            protected SQLFileSpecification fileSpecification;

            public SQLLogFileActionItem(ISQLExpr groupValue, SQLFileSpecification fileSpecification) {
                setGroupValue(groupValue);
                setFileSpecification(fileSpecification);
            }

            @Override
            protected void accept0(ISQLASTVisitor visitor) {
                if (visitor.visit(this)) {
                    this.acceptChild(visitor, groupValue);
                    this.acceptChild(visitor, fileSpecification);
                }
            }


            public ISQLExpr getGroupValue() {
                return groupValue;
            }

            public void setGroupValue(ISQLExpr groupValue) {
                setChildParent(groupValue);
                this.groupValue = groupValue;
            }

            public SQLFileSpecification getFileSpecification() {
                return fileSpecification;
            }

            public void setFileSpecification(SQLFileSpecification fileSpecification) {
                setChildParent(fileSpecification);
                this.fileSpecification = fileSpecification;
            }
        }
    }


    /**
     * MAXLOGFILES integer
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/create_database.html
     *
     * @author kent on 2019-07-19.
     */
    class SQLMaxLogFilesAction extends AbstractSQLExpr implements ISQLDatabaseLoggingClause {

        protected ISQLExpr value;

        public SQLMaxLogFilesAction(ISQLExpr value) {
            setValue(value);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }


        @Override
        public SQLMaxLogFilesAction clone() {
            return null;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }


    /**
     * MAXLOGMEMBERS integer
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/create_database.html
     *
     * @author kent on 2019-07-19.
     */
    class SQLMaxLogMembersAction extends AbstractSQLExpr implements ISQLDatabaseLoggingClause {

        protected ISQLExpr value;

        public SQLMaxLogMembersAction(ISQLExpr value) {
            setValue(value);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }


        @Override
        public SQLMaxLogMembersAction clone() {
            return null;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }


    /**
     * MAXLOGHISTORY integer
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/create_database.html
     *
     * @author kent on 2019-07-19.
     */
    class SQLMaxLogHistoryAction extends AbstractSQLExpr implements ISQLDatabaseLoggingClause {

        protected ISQLExpr value;

        public SQLMaxLogHistoryAction(ISQLExpr value) {
            setValue(value);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }


        @Override
        public SQLMaxLogMembersAction clone() {
            return null;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }


    /**
     * ARCHIVELOG
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/create_database.html
     *
     * @author kent on 2019-07-19.
     */
    class SQLArchiveLogAction extends AbstractSQLExpr implements ISQLDatabaseLoggingClause {


        @Override
        public void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLArchiveLogAction clone() {
            return null;
        }

    }

    /**
     * NOARCHIVELOG
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/create_database.html
     *
     * @author kent on 2019-07-19.
     */
    class SQLNoArchiveLogAction extends AbstractSQLExpr implements ISQLDatabaseLoggingClause {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLNoArchiveLogAction clone() {
            return null;
        }
    }

    /**
     * FORCE LOGGING
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/database_logging_clauses.html
     *
     * @author kent on 2019-07-19.
     */
    class SQLForceLoggingAction extends AbstractSQLExpr implements ISQLDatabaseLoggingClause {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLForceLoggingAction clone() {
            return null;
        }
    }

    /**
     * SET STANDBY NOLOGGING FOR {DATA AVAILABILITY | LOAD PERFORMANCE}
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/database_logging_clauses.html
     *
     * @author kent on 2019-07-19.
     */
    class SQLSetStandbyNoLoggingAction extends AbstractSQLExpr implements ISQLDatabaseLoggingClause {

        protected SQLSetStandbyNoLoggingType type;

        public SQLSetStandbyNoLoggingAction(SQLSetStandbyNoLoggingType type) {
            this.type = type;
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLForceLoggingAction clone() {
            return null;
        }

        public SQLSetStandbyNoLoggingType getType() {
            return type;
        }

        public void setType(SQLSetStandbyNoLoggingType type) {
            this.type = type;
        }
    }


    enum SQLSetStandbyNoLoggingType implements ISQLASTEnum {

        DATA_AVAILABILITY("data availability", "DATA AVAILABILITY"),
        LOAD_PERFORMANCE("load performance", "LOAD PERFORMANCE"),
        ;

        public final String lower;
        public final String upper;

        SQLSetStandbyNoLoggingType(String lower, String upper) {
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
