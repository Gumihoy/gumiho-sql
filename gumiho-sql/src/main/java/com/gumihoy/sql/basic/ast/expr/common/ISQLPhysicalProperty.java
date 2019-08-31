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
 * physical_properties
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
 *
 * @author kent onCondition 2018/3/23.
 */
public interface ISQLPhysicalProperty extends ISQLExpr {
    @Override
    ISQLPhysicalProperty clone();





    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
     *
     * @author kent on 2018/6/22.
     */
    public interface ISQLPhysicalPropertyOrganizationClause extends ISQLPhysicalProperty {
        @Override
        ISQLPhysicalPropertyOrganizationClause clone();
    }

    /**
     * [ deferred_segment_creation ] segment_attributes_clause [ table_compression ] [ inmemory_table_clause ] [ ilm_clause ]
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
     *
     * @author kent on 2018/6/22.
     */
    public class SQLPhysicalPropertyOrganizationHeapClause  extends AbstractSQLExpr implements ISQLPhysicalPropertyOrganizationClause {

        protected final List<ISQLExpr> items = new ArrayList<>();


        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, segmentAttributesClauses);
//                this.acceptChild(visitor, heapOrgTableClause);
//            }
        }

        @Override
        public SQLPhysicalPropertyOrganizationHeapClause clone() {
            SQLPhysicalPropertyOrganizationHeapClause x = new SQLPhysicalPropertyOrganizationHeapClause();
            this.cloneTo(x);

            return x;
        }


        public List<ISQLExpr> getItems() {
            return items;
        }

        public void addItem(ISQLExpr item) {
            if (item == null) {
                return;
            }
            setChildParent(item);
            this.items.add(item);
        }
        public void addItems(List<ISQLExpr> items) {
            if (items == null) {
                return;
            }
            for (ISQLExpr item : items) {
                addItem(item);
            }
        }
    }


    /**
     * ORGANIZATION INDEX
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
     *
     * @author kent on 2018/6/22.
     */
    public class SQLPhysicalPropertyOrganizationIndexClause  extends AbstractSQLExpr implements ISQLPhysicalPropertyOrganizationClause {

        protected final List<ISQLExpr> items = new ArrayList<>();


        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, items);
//            }
        }

        @Override
        public SQLPhysicalPropertyOrganizationIndexClause clone() {
            SQLPhysicalPropertyOrganizationIndexClause x = new SQLPhysicalPropertyOrganizationIndexClause();
            return x;
        }


        public List<ISQLExpr> getItems() {
            return items;
        }

        public void addItem (ISQLExpr item) {
            if (item == null) {
                return;
            }
            setChildParent(item);
            this.items.add(item);
        }

        public void addItems(List<ISQLExpr> items) {
            if (items == null) {
                return;
            }
            for (ISQLExpr item : items) {
                addItem(item);
            }
        }

    }

    /**
     * ORGANIZATION EXTERNAL external_table_clause
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
     *
     * @author kent on 2018/6/22.
     */
    public class SQLPhysicalPropertyOrganizationExternalClause extends AbstractSQLExpr implements ISQLPhysicalPropertyOrganizationClause {

        protected SQLExternalTableClause externalTableClause;


        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, externalTableClause);
//            }
        }

        @Override
        public SQLPhysicalPropertyOrganizationExternalClause clone() {
            SQLPhysicalPropertyOrganizationExternalClause x = new SQLPhysicalPropertyOrganizationExternalClause();
            return x;
        }


        public SQLExternalTableClause getExternalTableClause() {
            return externalTableClause;
        }

        public void setExternalTableClause(SQLExternalTableClause externalTableClause) {
            setChildParent(externalTableClause);
            this.externalTableClause = externalTableClause;
        }
    }



    /**
     * CLUSTER cluster (column [, column ]...)
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
     *
     * @author kent on 2018/6/22.
     */
    public static class SQLPhysicalPropertyClusterClause extends AbstractSQLExpr implements ISQLPhysicalProperty {

        protected ISQLExpr cluster;

        protected final List<ISQLExpr> columns = new ArrayList<>();


        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, cluster);
//                this.acceptChild(visitor, columns);
//            }
        }

        @Override
        public SQLPhysicalPropertyClusterClause clone() {
            SQLPhysicalPropertyClusterClause x = new SQLPhysicalPropertyClusterClause();

            ISQLExpr clusterClone = this.cluster.clone();
            x.setCluster(clusterClone);

            for (ISQLExpr column : this.columns) {
                ISQLExpr columnClone = column.clone();
                x.addColumn(columnClone);
            }

            return x;
        }

        public ISQLExpr getCluster() {
            return cluster;
        }

        public String getClusterName() {
            if (cluster instanceof ISQLName) {
                return ((ISQLName) cluster).getSimpleName();
            }
            return null;
        }

        public void setCluster(ISQLExpr cluster) {
            setChildParent(cluster);
            this.cluster = cluster;
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
}
