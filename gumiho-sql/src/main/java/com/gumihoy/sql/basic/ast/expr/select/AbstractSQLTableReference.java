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
package com.gumihoy.sql.basic.ast.expr.select;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.util.HashUtils;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * paren :  true :  (  tableReference )  , false: tableReference
 * <p>
 * [ [ AS ] <correlation name> [ <left paren> <derived column list> <right paren> ] ]
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#table%20reference
 *
 * @author kent onCondition 2018/3/20.
 */
public abstract class AbstractSQLTableReference extends AbstractSQLExpr implements ISQLTableReference {

    protected boolean paren;

    protected boolean as;

    protected ISQLIdentifier alias;

    protected final List<ISQLExpr> columns = new ArrayList<>();

//    protected SQLSampleClause sampleClause;

    @Override
    public AbstractSQLTableReference clone() {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

    public void cloneTo(AbstractSQLTableReference x) {
        super.cloneTo(x);

        x.paren = this.paren;

        x.as = this.as;

        if (this.alias != null) {
            ISQLIdentifier aliasClone = alias.clone();
            x.setAlias(aliasClone);
        }

        for (ISQLExpr derivedColumn : this.columns) {
            ISQLExpr derivedColumnClone = derivedColumn.clone();
            x.addColumn(derivedColumnClone);
        }
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == alias
                && target instanceof ISQLIdentifier) {
            setAlias((ISQLIdentifier) target);
            return true;
        }

        boolean replace = replaceInList(columns, source, target, this);
        if (replace) {
            return true;
        }
        return false;
    }


    @Override
    public ISQLName computeAlias() {
        return alias;
    }

    @Override
    public boolean containsAlias(String alias) {
        long aliasLowerHash = HashUtils.fnv_1a_64_lower(alias);
        return containsAlias(aliasLowerHash);
    }

    @Override
    public boolean containsAlias(long aliasLowerHash) {
        if (this.alias == null) {
            return false;
        }
        return this.alias.lowerHash() == aliasLowerHash;
    }

    @Override
    public boolean isParen() {
        return paren;
    }

    @Override
    public void setParen(boolean paren) {
        this.paren = paren;
    }

    @Override
    public boolean isAs() {
        return as;
    }

    @Override
    public void setAs(boolean as) {
        this.as = as;
    }

    @Override
    public ISQLIdentifier getAlias() {
        return alias;
    }

    @Override
    public void setAlias(ISQLIdentifier alias) {
        setChildParent(alias);
        this.alias = alias;
    }

    public void setAlias(String alias) {
        setAlias(SQLUtils.ofName(alias));
    }

    public List<ISQLExpr> getColumns() {
        return columns;
    }

    public void addColumn(ISQLExpr column) {
        if (column == null) {
            return;
        }
        setChildParent(column);
        this.columns.add(column);
    }


//    public SQLSampleClause getSampleClause() {
//        return sampleClause;
//    }
//
//    public void setSampleClause(SQLSampleClause sampleClause) {
//        setChildParent(sampleClause);
//        this.sampleClause = sampleClause;
//    }

}
