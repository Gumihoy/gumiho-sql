package com.gumihoy.sql.basic.ast.expr.database.create;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLFileSpecification;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kent on 2019-07-19.
 */
public interface ISQLCreateDatabaseAction extends ISQLExpr {

    @Override
    ISQLCreateDatabaseAction clone();


    /**
     * USER SYS IDENTIFIED BY password
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/create_database.html
     *
     * @author kent on 2019-07-19.
     */
    class SQLUserSysIdentifiedByAction extends AbstractSQLExpr implements ISQLCreateDatabaseAction {

        protected ISQLExpr password;

        public SQLUserSysIdentifiedByAction(ISQLExpr password) {
            setPassword(password);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, password);
            }
        }


        @Override
        public SQLUserSysIdentifiedByAction clone() {
            return null;
        }

        public ISQLExpr getPassword() {
            return password;
        }

        public void setPassword(ISQLExpr password) {
            setChildParent(password);
            this.password = password;
        }
    }

    /**
     * USER SYSTEM IDENTIFIED BY password
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/create_database.html
     *
     * @author kent on 2019-07-19.
     */
    class SQLUserSystemIdentifiedByAction extends AbstractSQLExpr implements ISQLCreateDatabaseAction {

        protected ISQLExpr password;

        public SQLUserSystemIdentifiedByAction(ISQLExpr password) {
            setPassword(password);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, password);
            }
        }


        @Override
        public SQLUserSystemIdentifiedByAction clone() {
            return null;
        }

        public ISQLExpr getPassword() {
            return password;
        }

        public void setPassword(ISQLExpr password) {
            setChildParent(password);
            this.password = password;
        }
    }

    /**
     * CONTROLFILE REUSE
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/create_database.html
     *
     * @author kent on 2019-07-19.
     */
    class SQLControlfileReuseAction extends AbstractSQLExpr implements ISQLCreateDatabaseAction {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }


        @Override
        public SQLControlfileReuseAction clone() {
            return null;
        }
    }

    /**
     * MAXDATAFILES integer
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/create_database.html
     *
     * @author kent on 2019-07-19.
     */
    class SQLMaxDataFilesAction extends AbstractSQLExpr implements ISQLCreateDatabaseAction {

        protected ISQLExpr value;

        public SQLMaxDataFilesAction(ISQLExpr value) {
            setValue(value);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }

        @Override
        public SQLMaxDataFilesAction clone() {
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
     * MAXINSTANCES integer
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/create_database.html
     *
     * @author kent on 2019-07-19.
     */
    public class SQLMaxInstancesAction extends AbstractSQLExpr implements ISQLCreateDatabaseAction {

        protected ISQLExpr value;

        public SQLMaxInstancesAction(ISQLExpr value) {
            setValue(value);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }


        @Override
        public SQLMaxInstancesAction clone() {
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
     * NATIONAL CHARACTER SET charset
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/create_database.html
     *
     * @author kent on 2019-07-19.
     */
    class SQLNationalCharacterSetAction extends AbstractSQLExpr implements ISQLCreateDatabaseAction {

        protected ISQLExpr charset;

        public SQLNationalCharacterSetAction(ISQLExpr charset) {
            setCharset(charset);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, charset);
            }
        }


        @Override
        public SQLNationalCharacterSetAction clone() {
            return null;
        }

        public ISQLExpr getCharset() {
            return charset;
        }

        public void setCharset(ISQLExpr charset) {
            setChildParent(charset);
            this.charset = charset;
        }
    }

    /**
     * Set Default type TableSpace
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/create_database.html
     *
     * @author kent on 2019-07-19.
     */
    static class SQLSetDefaultTableSpaceAction extends AbstractSQLExpr implements ISQLCreateDatabaseAction {
        protected SQLFileType fileType;

        public SQLSetDefaultTableSpaceAction(SQLFileType fileType) {
            this.fileType = fileType;
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLSetDefaultTableSpaceAction clone() {
            return null;
        }

        public SQLFileType getFileType() {
            return fileType;
        }

        public void setFileType(SQLFileType fileType) {
            this.fileType = fileType;
        }
    }


    /**
     * [ BIGFILE | SMALLFILE ] USER_DATA TABLESPACE tablespace_name
     * DATAFILE datafile_tempfile_spec [, datafile_tempfile_spec ]...
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/create_database.html
     *
     * @author kent on 2019-07-19.
     */
    class SQLUserDataTableSpaceAction extends AbstractSQLExpr implements ISQLCreateDatabaseAction {
        protected SQLFileType fileType;
        protected ISQLExpr name;
        protected final List<SQLFileSpecification> dataFiles = new ArrayList<>();

        public SQLUserDataTableSpaceAction(ISQLExpr name, SQLFileSpecification ... dataFiles) {
            setName(name);
            for (SQLFileSpecification dataFile : dataFiles) {
                addDataFile(dataFile);
            }
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
                this.acceptChild(visitor, dataFiles);
            }
        }


        @Override
        public SQLUserDataTableSpaceAction clone() {
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
            this.dataFiles.add(dataFile);
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


}
