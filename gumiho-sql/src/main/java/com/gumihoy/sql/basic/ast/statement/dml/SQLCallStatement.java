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
package com.gumihoy.sql.basic.ast.statement.dml;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * CALL [ <schema name> <period> ] <qualified identifier> <left paren> [ <SQL argument> [ { <comma> <SQL argument> }... ] ] <right paren>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#call%20statement
 * <p>
 * CALL sp_name([parameter[,...]])
 * CALL sp_name[()]
 * https://dev.mysql.com/doc/refman/5.7/en/call.html
 * <p>
 * CALL   [ schema. ] [ type. | package. ] { function | procedure | method } [ @dblink_name ] ( [ argument [, argument ]... ] )
 * [ INTO :host_variable [ [ INDICATOR ] :indicator_variable ] ] ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CALL.html#GUID-6CD7B9C4-E5DC-4F3C-9B6A-876AD2C63545
 *
 * @author kent onCondition 2018/1/23.
 */
public class SQLCallStatement extends AbstractSQLStatement implements ISQLDMLStatement {

    protected ISQLExpr expr;

    protected ISQLExpr into;

    public SQLCallStatement(DBType dbType) {
        super(dbType);
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, expr);
            this.acceptChild(visitor, into);
        }
    }

    @Override
    public SQLCallStatement clone() {
        SQLCallStatement x = new SQLCallStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLCallStatement x) {
        super.cloneTo(x);

        ISQLExpr exprClone = this.expr.clone();
        x.setExpr(exprClone);

        if (this.into != null) {
            ISQLExpr intoClone = this.into.clone();
            x.setInto(intoClone);
        }
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;
    }



    public ISQLExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLExpr expr) {
        setChildParent(expr);
        this.expr = expr;
    }

    public ISQLExpr getInto() {
        return into;
    }

    public void setInto(ISQLExpr into) {
        setChildParent(into);
        this.into = into;
    }
}
