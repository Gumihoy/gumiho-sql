package com.gumihoy.sql.basic.ast.statement.ddl.table;

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * TRUNCATE [TABLE] tbl_name
 * https://dev.mysql.com/doc/refman/5.6/en/truncate-table.html
 * <p>
 * TRUNCATE [ TABLE ] [ ONLY ] name [ * ] [, ... ] [RESTART IDENTITY | CONTINUE IDENTITY ] [ CASCADE | RESTRICT ]
 * https://www.postgresql.org/docs/devel/static/sql-truncate.html
 * <p>
 * TRUNCATE TABLE [schema.] table [ {PRESERVE | PURGE} MATERIALIZED VIEW LOG ] [ {DROP [ ALL ] | REUSE} STORAGE ] [ CASCADE ] ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/TRUNCATE-TABLE.html
 *
 * @author kent on 2018/6/4.
 */
public class SQLTruncateTableStatement extends AbstractSQLStatement {

    protected boolean table = true;
    protected boolean only = false;
    protected final List<ISQLName> names = new ArrayList<>();

    protected SQLMaterializedViewLogType materializedViewLog;
    protected SQLStorageType storage;
//    protected SQLCascadeType cascade;

    public SQLTruncateTableStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, names);
//        }
    }

    @Override
    public SQLTruncateTableStatement clone() {
        SQLTruncateTableStatement x = new SQLTruncateTableStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLTruncateTableStatement x) {
        super.cloneTo(x);

        x.table = this.table;
        x.only = this.only;

        for (ISQLName name : this.names) {
            ISQLName nameClone = name.clone();
            x.addName(nameClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (target == null) {
            boolean replace = replaceInList(names, source, null, this);
            if (replace) {
                return true;
            }
            return false;
        }

        if (target instanceof ISQLName) {
            boolean replace = replaceInList(names, source, (ISQLName) target, this);
            if (replace) {
                return true;
            }
        }

        return false;
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.TABLE_TRUNCATE;
    }


    public boolean isTable() {
        return table;
    }

    public SQLTruncateTableStatement setTable(boolean table) {
        this.table = table;
        return this;
    }

    public boolean isOnly() {
        return only;
    }

    public SQLTruncateTableStatement setOnly(boolean only) {
        this.only = only;
        return this;
    }

    public List<ISQLName> getNames() {
        return names;
    }

    public void addName(ISQLName name) {
        if (name == null) {
            return;
        }
        setChildParent(name);
        this.names.add(name);
    }


    public SQLMaterializedViewLogType getMaterializedViewLog() {
        return materializedViewLog;
    }

    public void setMaterializedViewLog(SQLMaterializedViewLogType materializedViewLog) {
        this.materializedViewLog = materializedViewLog;
    }

    public SQLStorageType getStorage() {
        return storage;
    }

    public void setStorage(SQLStorageType storage) {
        this.storage = storage;
    }

//    public SQLCascadeType getCascade() {
//        return cascade;
//    }
//
//    public void setCascade(SQLCascadeType cascade) {
//        this.cascade = cascade;
//    }

    public enum SQLMaterializedViewLogType implements ISQLASTEnum {
        PRESERVE_MATERIALIZED_VIEW_LOG("PRESERVE_MATERIALIZED_VIEW_LOG"),
        PURGE_MATERIALIZED_VIEW_LOG("PURGE_MATERIALIZED_VIEW_LOG"),;

        public final String upper;

        SQLMaterializedViewLogType(String upper) {
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }


        @Override
        public String lower() {
            return null;
        }

        @Override
        public String upper() {
            return upper;
        }
    }


    public enum SQLStorageType implements ISQLASTEnum {
        DROP_STORAGE("DROP_STORAGE"),
        DROP_ALL_STORAGE("DROP_ALL_STORAGE"),
        REUSE_STORAGE("REUSE_STORAGE"),;

        public final String upper;

        SQLStorageType(String upper) {
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }


        @Override
        public String lower() {
            return null;
        }

        @Override
        public String upper() {
            return upper;
        }
    }

}
