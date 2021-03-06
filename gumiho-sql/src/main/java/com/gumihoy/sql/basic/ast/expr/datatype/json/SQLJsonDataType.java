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
package com.gumihoy.sql.basic.ast.expr.datatype.json;

import com.gumihoy.sql.basic.ast.expr.datatype.AbstractSQLDataType;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * JSON
 * <p>
 * https://dev.mysql.com/doc/refman/8.0/en/json.html#json-values
 *
 * @author kent onCondition 2018/3/11.
 */
public class SQLJsonDataType extends AbstractSQLDataType implements ISQLJsonDataType {

    public SQLJsonDataType() {
        super("JSON");
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }

    @Override
    public SQLJsonDataType clone() {

        SQLJsonDataType x = new SQLJsonDataType();

        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLJsonDataType x) {
        super.cloneTo(x);
    }

}
