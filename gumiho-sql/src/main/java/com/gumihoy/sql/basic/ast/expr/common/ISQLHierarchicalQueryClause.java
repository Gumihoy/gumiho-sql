package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.condition.ISQLCondition;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * hierarchical_query_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Hierarchical-Queries.html#GUID-0118DF1D-B9A9-41EB-8556-C6E7D6A5A84E
 *
 * @author kent on 2018/5/7.
 */
public interface ISQLHierarchicalQueryClause extends ISQLExpr, ISQLCondition {

    @Override
    ISQLHierarchicalQueryClause clone();

    ISQLExpr getConnectByCondition();

    ISQLExpr getStartWithCondition();


    /**
     * hierarchical_query_clause
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
     *
     * @author kent on 2018/5/7.
     */
    abstract class AbstractSQLHierarchicalQueryClause extends AbstractSQLExpr implements ISQLHierarchicalQueryClause {

        protected boolean noCycle;
        protected ISQLExpr connectByCondition;
        protected ISQLExpr startWithCondition;

        public AbstractSQLHierarchicalQueryClause() {
        }

        public AbstractSQLHierarchicalQueryClause(boolean noCycle, ISQLExpr connectByCondition, ISQLExpr startWithCondition) {
            this.noCycle = noCycle;
            setConnectByCondition(connectByCondition);
            setStartWithCondition(startWithCondition);
        }

        @Override
        public AbstractSQLHierarchicalQueryClause clone() {
            throw new UnsupportedOperationException(getClass().getName());
        }


        public void cloneTo(AbstractSQLHierarchicalQueryClause x) {
            super.cloneTo(x);

            x.noCycle = this.noCycle;

            if (this.connectByCondition != null) {
                ISQLExpr connectByConditionClone = this.connectByCondition.clone();
                x.setConnectByCondition(connectByConditionClone);
            }

            if (this.startWithCondition != null) {
                ISQLExpr startWithConditionClone = this.startWithCondition.clone();
                x.setStartWithCondition(startWithConditionClone);
            }

        }

        @Override
        public boolean replace(ISQLExpr source, ISQLExpr target) {
            if (source == connectByCondition) {
                setConnectByCondition(target);
                return true;
            }
            if (source == startWithCondition) {
                setStartWithCondition(target);
                return true;
            }
            return false;
        }

        public boolean isNoCycle() {
            return noCycle;
        }

        public void setNoCycle(boolean noCycle) {
            this.noCycle = noCycle;
        }

        @Override
        public ISQLExpr getConnectByCondition() {
            return connectByCondition;
        }

        public void setConnectByCondition(ISQLExpr connectByCondition) {
            setChildParent(connectByCondition);
            this.connectByCondition = connectByCondition;
        }

        @Override
        public ISQLExpr getStartWithCondition() {
            return startWithCondition;
        }

        public void setStartWithCondition(ISQLExpr startWithCondition) {
            setChildParent(startWithCondition);
            this.startWithCondition = startWithCondition;
        }
    }


    /**
     * CONNECT BY [ NOCYCLE ] condition [ START WITH condition ]
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Hierarchical-Queries.html#GUID-0118DF1D-B9A9-41EB-8556-C6E7D6A5A84E
     *
     * @author kent on 2018/5/7.
     */
    class SQLHierarchicalQueryConnectByStartWithClause extends AbstractSQLHierarchicalQueryClause implements ISQLHierarchicalQueryClause {

        public SQLHierarchicalQueryConnectByStartWithClause() {
        }

        public SQLHierarchicalQueryConnectByStartWithClause(boolean noCycle, ISQLExpr connectByCondition, ISQLExpr startWithCondition) {
            super(noCycle, connectByCondition, startWithCondition);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, connectByCondition);
                this.acceptChild(visitor, startWithCondition);
            }
        }

        @Override
        public SQLHierarchicalQueryConnectByStartWithClause clone() {
            SQLHierarchicalQueryConnectByStartWithClause x = new SQLHierarchicalQueryConnectByStartWithClause();
            this.cloneTo(x);
            return x;
        }
    }


    /**
     * START WITH condition CONNECT BY [ NOCYCLE ] condition
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Hierarchical-Queries.html#GUID-0118DF1D-B9A9-41EB-8556-C6E7D6A5A84E
     *
     * @author kent on 2018/5/7.
     */
    class SQLHierarchicalQueryStartWithConnectByClause extends AbstractSQLHierarchicalQueryClause implements ISQLHierarchicalQueryClause {

        public SQLHierarchicalQueryStartWithConnectByClause() {
        }

        public SQLHierarchicalQueryStartWithConnectByClause(boolean noCycle, ISQLExpr connectByCondition, ISQLExpr startWithCondition) {
            super(noCycle, connectByCondition, startWithCondition);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, startWithCondition);
                this.acceptChild(visitor, connectByCondition);
            }
        }

        @Override
        public SQLHierarchicalQueryStartWithConnectByClause clone() {
            SQLHierarchicalQueryStartWithConnectByClause x = new SQLHierarchicalQueryStartWithConnectByClause();

            this.cloneTo(x);
            return x;
        }
    }

}
