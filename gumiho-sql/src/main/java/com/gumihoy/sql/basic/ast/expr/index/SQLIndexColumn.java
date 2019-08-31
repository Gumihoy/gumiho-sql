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
package com.gumihoy.sql.basic.ast.expr.index;

import com.gumihoy.sql.basic.ast.enums.SQLOrderingSpecification;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLCollateOptionExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * col_name [(length)] [ASC | DESC]
 * https://dev.mysql.com/doc/refman/5.6/en/create-index.html
 *
 * { column_name | ( expression ) } [ COLLATE collation ] [ opclass ] [ ASC | DESC ] [ NULLS { FIRST | LAST } ]
 * <p>
 * https://www.postgresql.org/docs/devel/static/sql-createindex.html
 *
 * @author kent onCondition 2018/3/26.
 */
public class SQLIndexColumn extends AbstractSQLExpr {

    protected ISQLExpr name;

    protected ISQLExpr length;

    protected SQLCollateOptionExpr collateClause;
    protected SQLOrderingSpecification orderingSpecification;
//    protected SQLNullOrderingType nullOrdering;


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, length);
//        }
    }

    @Override
    public SQLIndexColumn clone() {
        SQLIndexColumn x = new SQLIndexColumn();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLIndexColumn x) {
        super.cloneTo(x);

        ISQLExpr nameClone = this.name.clone();
        x.setName(nameClone);

        if (this.length != null) {
            ISQLExpr lengthClone = this.length.clone();
            x.setLength(lengthClone);
        }

        if (this.collateClause != null) {
            SQLCollateOptionExpr collateClauseClone = this.collateClause.clone();
            x.setCollateClause(collateClauseClone);
        }

        x.orderingSpecification = this.orderingSpecification;

//        x.nullOrdering = this.nullOrdering;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name) {
            this.setName(target);
            return true;
        }

        if (source == length) {
            this.setLength(target);
            return true;
        }

        return false;
    }

    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }

    public ISQLExpr getLength() {
        return length;
    }

    public void setLength(ISQLExpr length) {
        setChildParent(length);
        this.length = length;
    }

    public SQLCollateOptionExpr getCollateClause() {
        return collateClause;
    }

    public void setCollateClause(SQLCollateOptionExpr collateClause) {
        setChildParent(collateClause);
        this.collateClause = collateClause;
    }

    public SQLOrderingSpecification getOrderingSpecification() {
        return orderingSpecification;
    }

    public void setOrderingSpecification(SQLOrderingSpecification orderingSpecification) {
        this.orderingSpecification = orderingSpecification;
    }

//    public SQLNullOrderingType getNullOrdering() {
//        return nullOrdering;
//    }
//
//    public void setNullOrdering(SQLNullOrderingType nullOrdering) {
//        this.nullOrdering = nullOrdering;
//    }
}
