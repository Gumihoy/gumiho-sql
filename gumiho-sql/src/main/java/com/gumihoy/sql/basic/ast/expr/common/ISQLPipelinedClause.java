package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * PIPELINED [USING [ "schema" "." ] "implementation_type" ]
 * |
 * PIPELINED ( ROW | TABLE ) POLYMORPHIC USING ["schema" "."] "implementation_package" )
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/PIPELINED-clause.html#GUID-FA182210-C68D-4E03-85B9-A6C681099705
 *
 * @author kent on 2018/6/1.
 */
public interface ISQLPipelinedClause extends ISQLExpr {

    @Override
    ISQLPipelinedClause clone();


    /**
     * PIPELINED
     */
    class SQLPipelinedClause extends AbstractSQLExpr implements ISQLPipelinedClause {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLPipelinedClause clone() {
            return null;
        }
    }


    /**
     * PIPELINED [USING [ "schema" "." ] "implementation_type" ]
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/PIPELINED-clause.html#GUID-FA182210-C68D-4E03-85B9-A6C681099705
     *
     * @author kent on 2018/6/1.
     */
    public class SQLPipelinedUsingClause extends AbstractSQLExpr implements ISQLPipelinedClause {

        protected ISQLName using;

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, using);
//            }
        }

        @Override
        public SQLPipelinedUsingClause clone() {
            SQLPipelinedUsingClause x = new SQLPipelinedUsingClause();

            ISQLName usingClone = this.using.clone();
            x.setUsing(usingClone);
            return x;
        }

        public ISQLName getUsing() {
            return using;
        }

        public void setUsing(ISQLName using) {
            setChildParent(using);
            this.using = using;
        }
    }


    /**
     * PIPELINED ROW POLYMORPHIC USING ["schema" "."] "implementation_package" )
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/PIPELINED-clause.html#GUID-FA182210-C68D-4E03-85B9-A6C681099705
     *
     * @author kent on 2018/6/1.
     */
    public class SQLPipelinedRowClause extends AbstractSQLExpr implements ISQLPipelinedClause {

        protected ISQLName using;

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, using);
//            }
        }

        @Override
        public SQLPipelinedRowClause clone() {
            SQLPipelinedRowClause x = new SQLPipelinedRowClause();

            ISQLName usingClone = this.using.clone();
            x.setUsing(usingClone);
            return x;
        }

        public ISQLName getUsing() {
            return using;
        }

        public void setUsing(ISQLName using) {
            setChildParent(using);
            this.using = using;
        }
    }


    /**
     *
     *  PIPELINED TABLE POLYMORPHIC USING ["schema" "."] "implementation_package" )
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/PIPELINED-clause.html#GUID-FA182210-C68D-4E03-85B9-A6C681099705
     *
     * @author kent on 2018/6/1.
     */
    public class SQLPipelinedTableClause extends AbstractSQLExpr implements ISQLPipelinedClause {

        protected ISQLName using;

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, using);
//            }
        }

        @Override
        public SQLPipelinedTableClause clone() {
            SQLPipelinedTableClause x = new SQLPipelinedTableClause();

            ISQLName usingClone = this.using.clone();
            x.setUsing(usingClone);
            return x;
        }

        public ISQLName getUsing() {
            return using;
        }

        public void setUsing(ISQLName using) {
            setChildParent(using);
            this.using = using;
        }
    }

}
