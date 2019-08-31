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
package com.gumihoy.sql.basic.ast.statement.ddl.typebody;


import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLAlterStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/ALTER-SYNONYM.html
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLAlterTypeBodyStatement extends AbstractSQLStatement implements ISQLAlterStatement {


    protected boolean _public = false;

    protected ISQLName name;

//    protected SQLReserved option;


    public SQLAlterTypeBodyStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
    }


    @Override
    public SQLAlterTypeBodyStatement clone() {
        SQLAlterTypeBodyStatement x = new SQLAlterTypeBodyStatement(this.dbType);
        cloneTo(x);
        return x;
    }


    public void cloneTo(SQLAlterTypeBodyStatement x) {
        super.cloneTo(x);

        x.set_public(this._public);

        ISQLName cloneName = this.name.clone();
        x.setName(cloneName);

//        x.setOption(this.option);
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.TYPE_BODY_ALTER;
    }

    public boolean is_public() {
        return _public;
    }

    public void set_public(boolean _public) {
        this._public = _public;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        this.name = name;
    }

//    public SQLReserved getOption() {
//        return option;
//    }
//
//    public void setOption(SQLReserved option) {
//        this.option = option;
//    }
}
