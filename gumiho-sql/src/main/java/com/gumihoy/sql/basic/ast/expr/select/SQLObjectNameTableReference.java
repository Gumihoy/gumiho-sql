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


import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLPartitionExtensionClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLPropertyExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.util.HashUtils;
import com.gumihoy.sql.util.SQLUtils;

/**
 * <tableName> option [ [ AS ] <correlation name> [ <left paren> <derived column list> <right paren> ] [ <sample clause> ]
 * [ONLY] (  )
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#table%20primary
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#table%20reference
 * <p>
 * tbl_name [PARTITION (partition_names)] [[AS] alias] [index_hint_list]
 * https://dev.mysql.com/doc/refman/5.7/en/join.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
 *
 * @author kent onCondition 2018/3/20.
 */
public class SQLObjectNameTableReference extends AbstractSQLTableReference {

    protected boolean only;

    protected ISQLName name;

    protected ISQLPartitionExtensionClause partitionExtensionClause;


    protected ISQLExpr option;


//    protected final List<SQLIndexHint> indexHints = new ArrayList<>();


    public SQLObjectNameTableReference() {
    }

    public SQLObjectNameTableReference(String name) {
        setName(SQLUtils.ofName(name));
    }

    public SQLObjectNameTableReference(ISQLName name) {
        setName(name);
    }

    public SQLObjectNameTableReference(String name, String alias) {
        setName(SQLUtils.ofName(name));
        setAlias(SQLUtils.ofName(alias));
    }

    public SQLObjectNameTableReference(String name, boolean as, String alias) {
        setName(SQLUtils.ofName(name));
        setAs(as);
        setAlias(SQLUtils.ofName(alias));
    }

    public SQLObjectNameTableReference(ISQLName name, ISQLIdentifier alias) {
        this(name, false, alias);
    }

    public SQLObjectNameTableReference(ISQLName name, boolean as, ISQLIdentifier alias) {
        this(name, null, as, alias);
    }

    public SQLObjectNameTableReference(ISQLName name, ISQLPartitionExtensionClause partitionExtensionClause, boolean as, ISQLIdentifier alias) {
        setName(name);
        setPartitionExtensionClause(partitionExtensionClause);
        this.as = as;
        setAlias(alias);
    }


    public static final SQLObjectNameTableReference of(String name) {
        return new SQLObjectNameTableReference(name);
    }

    public static final SQLObjectNameTableReference of(String name, String alias) {
        return of(name, false, alias);
    }

    public static final SQLObjectNameTableReference of(String name, boolean as, String alias) {
        return new SQLObjectNameTableReference(name, as, alias);
    }

    public static final SQLObjectNameTableReference of(ISQLName name) {
        return new SQLObjectNameTableReference(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, partitionExtensionClause);
//            this.acceptChild(visitor, option);
            this.acceptChild(visitor, alias);
            this.acceptChild(visitor, columns);

        }
    }

    @Override
    public SQLObjectNameTableReference clone() {
        ISQLName nameClone = this.name.clone();
        SQLObjectNameTableReference x = new SQLObjectNameTableReference(nameClone);

        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLObjectNameTableReference x) {
        super.cloneTo(x);
        x.only = this.only;
        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

        if (this.option != null) {
            ISQLExpr optionClone = option.clone();
            x.setOption(optionClone);
        }

//        for (SQLIndexHint indexHint : indexHints) {
//            SQLIndexHint indexHintClone = indexHint.clone();
//            x.addIndexHint(indexHintClone);
//        }
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        boolean replace = super.replace(source, target);
        if (replace) {
            return true;
        }

        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }

        return false;
    }


    @Override
    public ISQLName computeAlias() {
        ISQLName computeAlias = getAlias();
        if (computeAlias == null) {
            computeAlias = SQLUtils.ofName(name.getSimpleName());
        }
        return computeAlias;
    }

    @Override
    public boolean containsAlias(String alias) {
        long aliasLowerHash = HashUtils.fnv_1a_64_lower(alias);
        return containsAlias(aliasLowerHash);
    }

    @Override
    public boolean containsAlias(long aliasLowerHash) {
        boolean containsAlias = super.containsAlias(aliasLowerHash);
        if (containsAlias) {
            return true;
        }
        if (name == null) {
            return false;
        }
        return name.lowerHash() == aliasLowerHash;
    }

    public boolean isOnly() {
        return only;
    }

    public void setOnly(boolean only) {
        this.only = only;
    }

    public ISQLName getName() {
        return name;
    }

    public String getTableName() {
        if (name != null) {
            return name.getSimpleName();
        }
        return null;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public void setName(String name) {
        if (name == null) {
            return;
        }
        setName(SQLUtils.ofName(name));
    }

    public void setName(String owner, String tableName) {
        if (tableName == null) {
            return;
        }
        ISQLName name;
        if (owner == null) {
            name = SQLUtils.ofName(tableName);
        } else {
            name = new SQLPropertyExpr(owner, tableName);
        }
        setName(name);
    }

    public ISQLPartitionExtensionClause getPartitionExtensionClause() {
        return partitionExtensionClause;
    }

    public void setPartitionExtensionClause(ISQLPartitionExtensionClause partitionExtensionClause) {
        setChildParent(partitionExtensionClause);
        this.partitionExtensionClause = partitionExtensionClause;
    }

    public ISQLExpr getOption() {
        return option;
    }

    public void setOption(ISQLExpr option) {
        setChildParent(option);
        this.option = option;
    }


    //    public List<SQLIndexHint> getIndexHints() {
//        return indexHints;
//    }
//
//    public void addIndexHint(SQLIndexHint indexHint) {
//        if (indexHint == null) {
//            return;
//        }
//        setChildParent(indexHint);
//        this.indexHints.add(indexHint);
//    }

}
