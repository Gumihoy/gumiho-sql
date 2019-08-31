package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.ast.expr.IOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * MEMCOMPRESS FOR { DML | QUERY [ LOW | HIGH ] | CAPACITY [ LOW | HIGH ] } | NO MEMCOMPRESS
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/25.
 */
public interface ISQLInMemoryMemCompressClause extends ISQLExpr, ISQLInMemoryAttribute {
    @Override
    ISQLInMemoryMemCompressClause clone();


    /**
     * MEMCOMPRESS FOR { DML | QUERY [ LOW | HIGH ] | CAPACITY [ LOW | HIGH ] } | NO MEMCOMPRESS
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/25.
     */
    public class SQLInMemoryMemCompressClause extends AbstractSQLExpr implements ISQLInMemoryMemCompressClause {

//        protected MemCompressMethod method;

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLInMemoryMemCompressClause clone() {
            SQLInMemoryMemCompressClause x = new SQLInMemoryMemCompressClause();

//            x.method = this.method;

            return x;
        }


//        public MemCompressMethod getMethod() {
//            return method;
//        }
//
//        public void setMethod(MemCompressMethod method) {
//            this.method = method;
//        }

//        public enum MemCompressMethod {
//            DML(SQLReserved.DML),
//            QUERY(SQLReserved.QUERY),
//            QUERY_LOW(SQLReserved.QUERY_LOW),
//            QUERY_HIGH(SQLReserved.QUERY_HIGH),
//            CAPACITY(SQLReserved.CAPACITY),
//            CAPACITY_LOW(SQLReserved.CAPACITY_LOW),
//            CAPACITY_HIGH(SQLReserved.CAPACITY_HIGH),;
//
//            public final SQLReserved name;
//
//            MemCompressMethod(SQLReserved name) {
//                this.name = name;
//            }
//
//            @Override
//            public String toString() {
//                return name.upper;
//            }
//        }

    }



    /**
     * MEMCOMPRESS FOR { DML | QUERY [ LOW | HIGH ] | CAPACITY [ LOW | HIGH ] } | NO MEMCOMPRESS
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/25.
     */
    public class SQLInMemoryNoMemCompressClause extends AbstractSQLExpr implements ISQLInMemoryMemCompressClause {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLInMemoryNoMemCompressClause clone() {
            SQLInMemoryNoMemCompressClause x = new SQLInMemoryNoMemCompressClause();
            return x;
        }
    }

    
}
