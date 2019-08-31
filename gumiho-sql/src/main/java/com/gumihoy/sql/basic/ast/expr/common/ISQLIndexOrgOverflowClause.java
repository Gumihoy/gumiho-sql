package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * [ INCLUDING column_name ] OVERFLOW [ segment_attributes_clause ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/25.
 */
public interface ISQLIndexOrgOverflowClause extends ISQLExpr {


    public class SQLIncludingClause extends AbstractSQLExpr implements ISQLIndexOrgOverflowClause {
        protected ISQLExpr column;

        public SQLIncludingClause(ISQLExpr column) {
            setColumn(column);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, column);
//            this.acceptChild(visitor, segmentAttributes);
//        }
        }

        @Override
        public SQLIncludingClause clone() {
            ISQLExpr columnClone = this.column.clone();
            SQLIncludingClause x = new SQLIncludingClause(columnClone);
            return x;
        }

        public ISQLExpr getColumn() {
            return column;
        }

        public void setColumn(ISQLExpr column) {
            setChildParent(column);
            this.column = column;
        }
    }


    public class SQLOverflowClause extends AbstractSQLExpr implements ISQLIndexOrgOverflowClause {
        @Override
        public void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLOverflowClause clone() {
            return new SQLOverflowClause();
        }
    }


}
