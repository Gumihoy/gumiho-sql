package com.gumihoy.sql.basic.ast.expr.table;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * partition_attributes
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/12.
 */
public interface SQLPartitionAttribute extends ISQLExpr {

    @Override
    SQLPartitionAttribute clone();

    /**
     * LOB lob_item ( parameter [ parameter...])
     */
    class SQLLobClause extends AbstractSQLExpr implements SQLPartitionAttribute {
        protected ISQLExpr name;
        protected final List<ISQLExpr> parameters = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, parameters);
//            }
        }

        @Override
        public SQLLobClause clone() {
            SQLLobClause x = new SQLLobClause();
            return x;
        }

        public ISQLExpr getName() {
            return name;
        }

        public void setName(ISQLExpr name) {
            setChildParent(name);
            this.name = name;
        }

        public List<ISQLExpr> getParameters() {
            return parameters;
        }

        public void addParameter(ISQLExpr parameter) {
            if (parameter == null) {
                return;
            }
            setChildParent(parameter);
            this.parameters.add(parameter);
        }
    }

    /**
     * VARRAY varray ( parameter [ parameter...])
     */
    class SQLVarrayClause extends AbstractSQLExpr implements SQLPartitionAttribute {
        protected ISQLExpr name;
        protected final List<ISQLExpr> parameters = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, parameters);
//            }
        }

        @Override
        public SQLLobClause clone() {
            SQLLobClause x = new SQLLobClause();
            return x;
        }

        public ISQLExpr getName() {
            return name;
        }

        public void setName(ISQLExpr name) {
            setChildParent(name);
            this.name = name;
        }

        public List<ISQLExpr> getParameters() {
            return parameters;
        }

        public void addParameter(ISQLExpr parameter) {
            if (parameter == null) {
                return;
            }
            setChildParent(parameter);
            this.parameters.add(parameter);
        }
    }
}
