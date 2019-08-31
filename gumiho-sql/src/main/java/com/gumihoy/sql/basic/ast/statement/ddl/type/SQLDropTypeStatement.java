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
package com.gumihoy.sql.basic.ast.statement.ddl.type;


import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLDropStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * DROP TYPE <schema-resolved user-defined type name> [CASCADE | RESTRICT]
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#drop%20data%20type%20statement
 * <p>
 * <p>
 * DROP TYPE [ IF EXISTS ] name [, ...] [ CASCADE | RESTRICT ]
 * https://www.postgresql.org/docs/10/static/sql-droptype.html
 * <p>
 * <p>
 * DROP TYPE [ schema. ] type_name [ FORCE | VALIDATE ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/DROP-TYPE-statement.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLDropTypeStatement extends AbstractSQLStatement implements ISQLDropStatement {

    protected boolean ifExists;

    protected final List<ISQLName> names = new ArrayList<>();

    protected SQLDropTypeOption option;


    public SQLDropTypeStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, names);
        }
    }

    @Override
    public SQLDropTypeStatement clone() {
        SQLDropTypeStatement x = new SQLDropTypeStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLDropTypeStatement x) {
        super.cloneTo(x);

        x.ifExists = this.ifExists;

        for (ISQLName name : names) {
            ISQLName cloneName = name.clone();
            x.addName(cloneName);
        }

        x.option = this.option;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
//        boolean replace = replaceInList(names, source, (ISQLName) target, this);
//        if (replace) {
//            return true;
//        }

        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.TYPE_DROP;
    }


    public boolean isIfExists() {
        return ifExists;
    }

    public void setIfExists(boolean ifExists) {
        this.ifExists = ifExists;
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

    public SQLDropTypeOption getOption() {
        return option;
    }

    public void setOption(SQLDropTypeOption option) {
        this.option = option;
    }

    public enum SQLDropTypeOption implements ISQLASTEnum {
        FORCE("FORCE"),
        VALIDATE("VALIDATE"),
        CASCADE("CASCADE"),
        RESTRICT("RESTRICT"),
        ;

        public final String upper;

        SQLDropTypeOption(String upper) {
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }


        @Override
        public String lower() {
            return upper;
        }

        @Override
        public String upper() {
            return upper;
        }
    }
}
