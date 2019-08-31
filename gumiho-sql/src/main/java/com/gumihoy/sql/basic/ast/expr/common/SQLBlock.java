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
package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * [ << label >> ]... [ DECLARE declare_section ] body
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/block.html#GUID-9ACEB9ED-567E-4E1A-A16A-B8B35214FC9D
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/block.html#GUID-9ACEB9ED-567E-4E1A-A16A-B8B35214FC9D
 *
 * @author kent onCondition 2018/3/22.
 */
public class SQLBlock extends AbstractSQLExpr {

    protected final List<SQLLabel> labels = new ArrayList<>();

    protected final List<ISQLObject> declares = new ArrayList<>();

    protected SQLBody body;


    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, labels);
            this.acceptChild(visitor, declares);
            this.acceptChild(visitor, body);
        }
    }


    public List<SQLLabel> getLabels() {
        return labels;
    }

    public void addLabel(SQLLabel label) {
        if (label == null) {
            return;
        }
        setChildParent(label);
        this.labels.add(label);
    }

    public List<ISQLObject> getDeclares() {
        return declares;
    }

    public void addDeclare(ISQLObject declare) {
        if (declare == null) {
            return;
        }
        setChildParent(declare);
        this.declares.add(declare);
    }

    public SQLBody getBody() {
        return body;
    }

    public void setBody(SQLBody body) {
        setChildParent(body);
        this.body = body;
    }
}
