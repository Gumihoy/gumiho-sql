package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * SEGMENT CREATION { IMMEDIATE | DEFERRED }
 * <p>
 * deferred_segment_creation
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
 *
 * @author kent on 2018/6/22.
 */
public interface ISQLDeferredSegmentCreation extends ISQLPhysicalProperty {
    @Override
    ISQLDeferredSegmentCreation clone();


     class SQLSegmentCreationImmediate extends AbstractSQLExpr implements ISQLDeferredSegmentCreation {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLSegmentCreationImmediate clone() {
            return new SQLSegmentCreationImmediate();
        }
    }


    class SQLSegmentCreationDeferred extends AbstractSQLExpr implements ISQLDeferredSegmentCreation {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLSegmentCreationDeferred clone() {
            return new SQLSegmentCreationDeferred();
        }
    }
}
