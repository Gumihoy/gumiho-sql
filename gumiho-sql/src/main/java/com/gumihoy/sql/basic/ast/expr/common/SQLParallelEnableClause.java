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

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * PARALLEL_ENABLE [ (PARTITION argument BY { ANY | { HASH | RANGE } (column [, column ]...) [ streaming_clause ] | VALUE (column) })
 * <p>
 * https://docs.oracle.com/database/121/LNPLS/create_function.htm#GUID-B71BC5BD-B87C-4054-AAA5-213E856651F2__CIHJFAGA
 *
 * @author kent onCondition 2018/3/18.
 */
public class SQLParallelEnableClause extends AbstractSQLExpr {

    protected final List<ISQLExpr> arguments = new ArrayList<>();

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, arguments);
        }
    }

    @Override
    public SQLParallelEnableClause clone() {
        return null;
    }


    public List<ISQLExpr> getArguments() {
        return arguments;
    }

    public void addArgument(ISQLExpr argument) {
        if (argument == null) {
            return;
        }
        setChildParent(argument);
        this.arguments.add(argument);
    }

    public interface ISQLArgument extends ISQLExpr {
        @Override
        ISQLArgument clone();


        public ISQLExpr getName();

        public void setName(ISQLExpr name);

        public List<ISQLName> getColumns();

        public void addColumn(ISQLName column);

        public ISQLStreamingClause getStreamingClause();

        public void setStreamingClause(ISQLStreamingClause streamingClause);
    }


    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/PARALLEL_ENABLE-clause.html#GUID-CFF3C7D3-6438-44C2-9FAF-569F246C37CA
     *
     * @author kent on 2018/5/31.
     */
    public static abstract class AbstractSQLArgument extends AbstractSQLExpr implements ISQLArgument {

        protected ISQLExpr name;

        protected final List<ISQLName> columns = new ArrayList<>();

        protected ISQLStreamingClause streamingClause;

        @Override
        public AbstractSQLArgument clone() {
            throw new UnsupportedOperationException(getClass().getName());
        }


        public void cloneTo(AbstractSQLArgument x) {
            super.cloneTo(x);

            if (name != null) {
                ISQLExpr nameClone = this.name.clone();
                x.setName(nameClone);
            }

            for (ISQLName column : columns) {
                ISQLName columnClone = column.clone();
                x.addColumn(columnClone);
            }

            if (streamingClause != null) {
                ISQLStreamingClause streamingClauseClone = this.streamingClause.clone();
                x.setStreamingClause(streamingClauseClone);
            }

        }

        public ISQLExpr getName() {
            return name;
        }

        public void setName(ISQLExpr name) {
            this.name = name;
        }

        public List<ISQLName> getColumns() {
            return columns;
        }

        public void addColumn(ISQLName column) {
            if (column == null) {
                return;
            }
            setChildParent(column);
            this.columns.add(column);
        }

        public ISQLStreamingClause getStreamingClause() {
            return streamingClause;
        }

        public void setStreamingClause(ISQLStreamingClause streamingClause) {
            setChildParent(streamingClause);
            this.streamingClause = streamingClause;
        }
    }


    /**
     * PARALLEL_ENABLE LEFT_PAREN PARTITION expr BY ANY  RIGHT_PAREN
     * <p>
     * https://docs.oracle.com/database/121/LNPLS/create_function.htm#GUID-B71BC5BD-B87C-4054-AAA5-213E856651F2__CIHJFAGA
     *
     * @author kent onCondition 2018/3/18.
     */
    public static class SQLPartitionByAnyArgument extends AbstractSQLArgument {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
            }
        }

        @Override
        public SQLPartitionByAnyArgument clone() {
            SQLPartitionByAnyArgument x = new SQLPartitionByAnyArgument();
            this.cloneTo(x);
            return x;
        }
    }

    /**
     * https://docs.oracle.com/database/121/LNPLS/create_function.htm#GUID-B71BC5BD-B87C-4054-AAA5-213E856651F2__CIHJFAGA
     *
     * @author kent onCondition 2018/3/18.
     */
    public static class SQLPartitionByHashArgument extends AbstractSQLArgument {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
                this.acceptChild(visitor, columns);
                this.acceptChild(visitor, streamingClause);
            }
        }

        @Override
        public SQLPartitionByHashArgument clone() {
            return null;
        }
    }


    /**
     * https://docs.oracle.com/database/121/LNPLS/create_function.htm#GUID-B71BC5BD-B87C-4054-AAA5-213E856651F2__CIHJFAGA
     *
     * @author kent onCondition 2018/3/18.
     */
    public static class SQLPartitionByRangeArgument extends AbstractSQLArgument {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
                this.acceptChild(visitor, columns);
                this.acceptChild(visitor, streamingClause);
            }
        }

        @Override
        public SQLPartitionByRangeArgument clone() {
            return null;
        }
    }


    /**
     * https://docs.oracle.com/database/121/LNPLS/create_function.htm#GUID-B71BC5BD-B87C-4054-AAA5-213E856651F2__CIHJFAGA
     *
     * @author kent onCondition 2018/3/18.
     */
    public static class SQLPartitionByValueArgument extends AbstractSQLArgument {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
                this.acceptChild(visitor, columns);
                this.acceptChild(visitor, streamingClause);
            }
        }

        @Override
        public SQLPartitionByValueArgument clone() {
            return null;
        }
    }


    /**
     * { ORDER | CLUSTER } expr BY (column [, column ]...)
     */
    public interface ISQLStreamingClause extends ISQLExpr {
        @Override
        ISQLStreamingClause clone();

        public ISQLExpr getExpr();

        public void setExpr(ISQLExpr expr);

        public List<ISQLExpr> getColumns();

        public void addColumn(ISQLExpr column);
    }

    /**
     * { ORDER | CLUSTER } expr BY (column [, column ]...)
     */
    public static abstract class AbstractSQLStreamingClause extends AbstractSQLExpr implements ISQLStreamingClause {

        protected ISQLExpr expr;

        protected final List<ISQLExpr> columns = new ArrayList<>();

        @Override
        public AbstractSQLStreamingClause clone() {
            throw new UnsupportedOperationException(getClass().getName());
        }

        public void cloneTo(AbstractSQLStreamingClause x) {
            super.cloneTo(x);

            ISQLExpr exprClone = expr.clone();
            x.setExpr(exprClone);

            for (ISQLExpr column : columns) {
                ISQLExpr columnClone = column.clone();
                x.addColumn(columnClone);
            }
        }

        public ISQLExpr getExpr() {
            return expr;
        }

        public void setExpr(ISQLExpr expr) {
            setChildParent(expr);
            this.expr = expr;
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

    }

    /**
     * { ORDER | CLUSTER } expr BY (column [, column ]...)
     */
    public static class SQLStreamingClauseByOrder extends AbstractSQLStreamingClause {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, expr);
                this.acceptChild(visitor, columns);
            }
        }

        @Override
        public SQLStreamingClauseByOrder clone() {
            SQLStreamingClauseByOrder x = new SQLStreamingClauseByOrder();
            this.cloneTo(x);
            return x;
        }
    }

    public static class SQLStreamingClusterByCluster extends AbstractSQLStreamingClause {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, expr);
                this.acceptChild(visitor, columns);
            }
        }

        @Override
        public SQLStreamingClusterByCluster clone() {
            SQLStreamingClusterByCluster x = new SQLStreamingClusterByCluster();
            this.cloneTo(x);
            return x;
        }
    }
}
