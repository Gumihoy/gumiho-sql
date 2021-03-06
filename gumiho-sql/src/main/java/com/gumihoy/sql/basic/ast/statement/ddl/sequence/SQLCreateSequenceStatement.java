/*
 * Copyright (C) 2017-2018 kent(kent.bohai@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gumihoy.sql.basic.ast.statement.ddl.sequence;


import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLSharingClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceMaxValueOption;
import com.gumihoy.sql.basic.ast.expr.sequence.SQLSequenceMinValueOption;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * CREATE SEQUENCE name [ <sequence generator options> ]
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#sequence%20generator%20definition
 * <p>
 * <p>
 * CREATE [ TEMPORARY | TEMP ] SEQUENCE [ IF NOT EXISTS ] name [ AS data_type ] [ INCREMENT [ BY ] increment ] [ MINVALUE minvalue | NO MINVALUE ] [ MAXVALUE maxvalue | NO MAXVALUE ] [ START [ WITH ] start ] [ CACHE cache ] [ [ NO ] CYCLE ] [ OWNED BY { table_name.column_name | NONE } ]
 * <p>
 * https://www.postgresql.org/docs/10/static/sql-createsequence.html
 * <p>
 * CREATE SEQUENCE [ schema. ] sequence [ SHARING = { METADATA | DATA | NONE } ] [ { INCREMENT BY | START WITH } integer | { MAXVALUE integer | NOMAXVALUE } | { MINVALUE integer | NOMINVALUE } | { CYCLE | NOCYCLE } | { CACHE integer | NOCACHE } | { ORDER | NOORDER } | { KEEP | NOKEEP } | { SCALE {EXTEND | NOEXTEND} | NOSCALE } | { SESSION | GLOBAL } ]... ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-SEQUENCE.html#GUID-E9C78A8C-615A-4757-B2A8-5E6EFB130571
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLCreateSequenceStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected SQLScope scope;

    protected boolean ifNotExist = false;

    protected ISQLName name;

    protected SQLSharingClause sharingClause;


    // ---------- options ------------------
    protected final List<ISQLExpr> options = new ArrayList<>();


    public SQLCreateSequenceStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, sharingClause);
            this.acceptChild(visitor, options);
        }
    }

    @Override
    public SQLCreateSequenceStatement clone() {
        SQLCreateSequenceStatement x = new SQLCreateSequenceStatement(this.dbType);

        cloneTo(x);

        return x;
    }

    public void cloneTo(SQLCreateSequenceStatement x) {
        super.cloneTo(x);


        x.scope = this.scope;

        x.setIfNotExist(this.ifNotExist);

        x.setName(this.name);


        if (this.sharingClause != null) {
            SQLSharingClause sharingClauseClone = this.sharingClause.clone();
            x.setSharingClause(sharingClauseClone);
        }

        for (ISQLExpr option : this.options) {
            ISQLExpr cloneOption = option.clone();
            x.addOption(cloneOption);
        }

    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name) {
            this.setName((ISQLName) target);
            return true;
        }
        return false;
    }



    public SQLSequenceMinValueOption minValueOption() {
        for (ISQLExpr option : this.options) {
            if (option instanceof SQLSequenceMinValueOption) {
                return (SQLSequenceMinValueOption) option;
            }
        }
        return null;
    }

    public SQLSequenceMaxValueOption maxValueOption() {
        for (ISQLExpr option : this.options) {
            if (option instanceof SQLSequenceMaxValueOption) {
                return (SQLSequenceMaxValueOption) option;
            }
        }
        return null;
    }


    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.SEQUENCE_CREATE;
    }


    public SQLScope getScope() {
        return scope;
    }

    public void setScope(SQLScope scope) {
        this.scope = scope;
    }

    public boolean isIfNotExist() {
        return ifNotExist;
    }

    public void setIfNotExist(boolean ifNotExist) {
        this.ifNotExist = ifNotExist;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        this.name = name;
    }

    public SQLSharingClause getSharingClause() {
        return sharingClause;
    }

    public void setSharingClause(SQLSharingClause sharingClause) {
        this.sharingClause = sharingClause;
    }

    public List<ISQLExpr> getOptions() {
        return options;
    }

    public void addOption(ISQLExpr option) {
        if (option == null) {
            return;
        }
        option.setParent(this);
        this.options.add(option);
    }

    public enum SQLScope {
        TEMPORARY("TEMPORARY"),
        TEMP("TEMP"),;
        public final String name;

        SQLScope(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
