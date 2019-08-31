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
package com.gumihoy.sql.dialect.mariadb.ast.expr.select;


import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLPartitionExtensionClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectQuery;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.mariadb.ast.expr.IMariaDBExpr;
import com.gumihoy.sql.dialect.mariadb.visitor.IMariaDBASTVisitor;
import com.gumihoy.sql.dialect.mysql.ast.expr.AbstractMySQLExpr;
import com.gumihoy.sql.dialect.mysql.ast.expr.IMySQLExpr;
import com.gumihoy.sql.dialect.mysql.visitor.IMySQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * SELECT
 * [ALL | DISTINCT | DISTINCTROW ]
 * [HIGH_PRIORITY]
 * [STRAIGHT_JOIN]
 * [SQL_SMALL_RESULT] [SQL_BIG_RESULT] [SQL_BUFFER_RESULT]
 * [SQL_NO_CACHE] [SQL_CALC_FOUND_ROWS]
 * select_expr [, select_expr ...]
 * [FROM table_references
 * [PARTITION partition_list]
 * [WHERE where_condition]
 * [GROUP BY {col_name | expr | position}, ... [WITH ROLLUP]]
 * [HAVING where_condition]
 * [WINDOW window_name AS (window_spec)
 * [, window_name AS (window_spec)] ...]
 * [ORDER BY {col_name | expr | position}
 * [ASC | DESC], ... [WITH ROLLUP]]
 * [LIMIT {[offset,] row_count | row_count OFFSET offset}]
 * [INTO OUTFILE 'file_name'
 * [CHARACTER SET charset_name]
 * export_options
 * | INTO DUMPFILE 'file_name'
 * | INTO var_name [, var_name]]
 * [FOR {UPDATE | SHARE} [OF tbl_name [, tbl_name] ...] [NOWAIT | SKIP LOCKED]
 * | LOCK IN SHARE MODE]]
 * <p>
 * https://dev.mysql.com/doc/refman/8.0/en/select.html
 *
 * @author kent onCondition 2018/3/21.
 */
public class MariaDBSelectQuery extends SQLSelectQuery implements IMariaDBExpr {

    protected boolean highPriority;
    protected boolean straightJoin;
    protected boolean smallResult;
    protected boolean bigResult;
    protected boolean bufferResult;
    protected SQLCache cache;
    protected boolean calcFoundRows;

    protected ISQLPartitionExtensionClause partitionExtensionClause;

    protected MySQLProcedureClause procedureClause;

    protected IMySQLIntoClause intoClause;

    protected boolean lockInShareMode;


    public MariaDBSelectQuery() {
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor instanceof IMariaDBASTVisitor) {
            accept0((IMariaDBASTVisitor) visitor);
        } else {
            super.accept0(visitor);
        }
    }

    @Override
    public void accept0(IMariaDBASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, selectItems);
            this.acceptChild(visitor, fromClause);
            this.acceptChild(visitor, partitionExtensionClause);
            this.acceptChild(visitor, whereClause);
            this.acceptChild(visitor, groupByClause);
            this.acceptChild(visitor, orderByClause);
            this.acceptChild(visitor, limitClause);
            this.acceptChild(visitor, procedureClause);

            this.acceptChild(visitor, lockClause);
        }
    }


    public boolean isHighPriority() {
        return highPriority;
    }

    public void setHighPriority(boolean highPriority) {
        this.highPriority = highPriority;
    }

    public boolean isStraightJoin() {
        return straightJoin;
    }

    public void setStraightJoin(boolean straightJoin) {
        this.straightJoin = straightJoin;
    }

    public boolean isSmallResult() {
        return smallResult;
    }

    public void setSmallResult(boolean smallResult) {
        this.smallResult = smallResult;
    }

    public boolean isBigResult() {
        return bigResult;
    }

    public void setBigResult(boolean bigResult) {
        this.bigResult = bigResult;
    }

    public boolean isBufferResult() {
        return bufferResult;
    }

    public void setBufferResult(boolean bufferResult) {
        this.bufferResult = bufferResult;
    }

    public SQLCache getCache() {
        return cache;
    }

    public void setCache(SQLCache cache) {
        this.cache = cache;
    }

    public boolean isCalcFoundRows() {
        return calcFoundRows;
    }

    public void setCalcFoundRows(boolean calcFoundRows) {
        this.calcFoundRows = calcFoundRows;
    }

    public ISQLPartitionExtensionClause getPartitionExtensionClause() {
        return partitionExtensionClause;
    }

    public void setPartitionExtensionClause(ISQLPartitionExtensionClause partitionExtensionClause) {
        this.partitionExtensionClause = partitionExtensionClause;
    }

    public void setProcedureClause(MySQLProcedureClause procedureClause) {
        this.procedureClause = procedureClause;
    }

    public IMySQLIntoClause getIntoClause() {
        return intoClause;
    }

    public void setIntoClause(IMySQLIntoClause intoClause) {
        this.intoClause = intoClause;
    }

    public boolean isLockInShareMode() {
        return lockInShareMode;
    }

    public void setLockInShareMode(boolean lockInShareMode) {
        this.lockInShareMode = lockInShareMode;
    }


    public enum SQLCache implements ISQLASTEnum {

        SQL_CACHE("sql_cache", "SQL_CACHE"),
        SQL_NO_CACHE("sql_no_cache", "SQL_NO_CACHE"),
        ;

        public final String lower;
        public final String upper;

        SQLCache(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }


        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }

    }


    public class MySQLProcedureClause extends AbstractMySQLExpr {

        protected ISQLName name;

        protected final List<ISQLExpr> arguments = new ArrayList<>();

        @Override
        public void accept0(IMySQLASTVisitor visitor) {

        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            this.name = name;
        }

        public List<ISQLExpr> getArguments() {
            return arguments;
        }

        public void addArguments(ISQLExpr argument) {

        }
    }

    public interface IMySQLIntoClause extends IMySQLExpr {
        @Override
        IMySQLIntoClause clone();
    }

    public class MySQLIntoClause extends AbstractMySQLExpr implements IMySQLIntoClause {

        protected final List<ISQLExpr> names = new ArrayList<>();

        @Override
        public void accept0(IMySQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, names);
//            }
        }

        @Override
        public MySQLIntoClause clone() {
            MySQLIntoClause x = new MySQLIntoClause();
            return x;
        }

    }

    public class MySQLIntoOutFileClause extends AbstractMySQLExpr implements IMySQLIntoClause {

        //        protected SQLCharLiteral name;
//        protected SQLCharacterSetExpr characterSetExpr;
        protected ISQLExpr option;

        @Override
        public void accept0(IMySQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, name);
//                this.acceptChild(visitor, characterSetExpr);
//                this.acceptChild(visitor, option);
//            }
        }

        @Override
        public MySQLIntoOutFileClause clone() {
            MySQLIntoOutFileClause x = new MySQLIntoOutFileClause();
            return x;
        }


//        public SQLCharLiteral getName() {
//            return name;
//        }
//
//        public void setName(SQLCharLiteral name) {
//            this.name = name;
//        }

//        public SQLCharacterSetExpr getCharacterSetExpr() {
//            return characterSetExpr;
//        }
//
//        public void setCharacterSetExpr(SQLCharacterSetExpr characterSetExpr) {
//            this.characterSetExpr = characterSetExpr;
//        }

        public ISQLExpr getOption() {
            return option;
        }

        public void setOption(ISQLExpr option) {
            setChildParent(option);
            this.option = option;
        }
    }


    public class MySQLIntoDumpFileClause extends AbstractMySQLExpr implements IMySQLIntoClause {
//        protected SQLCharLiteral name;

        @Override
        public void accept0(IMySQLASTVisitor visitor) {

        }

        @Override
        public MySQLIntoDumpFileClause clone() {
            MySQLIntoDumpFileClause x = new MySQLIntoDumpFileClause();
            return x;
        }


//        public SQLCharLiteral getName() {
//            return name;
//        }
//
//        public void setName(SQLCharLiteral name) {
//            this.name = name;
//        }
    }


}
