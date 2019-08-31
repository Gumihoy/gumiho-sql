package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLInMemoryMemCompressClause;
import com.gumihoy.sql.basic.ast.expr.common.ISQLPhysicalProperty;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.ast.expr.IOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * INMEMORY [ inmemory_memcompress ] | NO INMEMORY } ( column [, column ]... )
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/25.
 */
public interface IOracleInMemoryColumnClause extends IOracleExpr, ISQLPhysicalProperty {
    @Override
    IOracleInMemoryColumnClause clone();


    /**
     * INMEMORY [ inmemory_memcompress ] | NO INMEMORY } ( column [, column ]... )
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/25.
     */
    public abstract class AbstractOracleSQLIneMemoryColumnClause extends AbstractOracleExpr implements IOracleInMemoryColumnClause {

        protected final List<ISQLExpr> columns = new ArrayList<>();

        @Override
        public AbstractOracleSQLIneMemoryColumnClause clone() {
            throw new UnsupportedOperationException(getClass().getName());
        }

        public void cloneTo(AbstractOracleSQLIneMemoryColumnClause x) {
            super.cloneTo(x);

            for (ISQLExpr column : this.columns) {
                ISQLExpr columnClone = column.clone();
                x.addColumn(columnClone);
            }
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
     * INMEMORY [ inmemory_memcompress ] | NO INMEMORY } ( column [, column ]... )
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/25.
     */
    public class OracleSQLInMemoryColumnClause extends AbstractOracleSQLIneMemoryColumnClause {

        protected ISQLInMemoryMemCompressClause inMemoryMemCompress;

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, inMemoryMemCompress);
//                this.acceptChild(visitor, columns);
//            }
        }

        @Override
        public OracleSQLInMemoryColumnClause clone() {
            OracleSQLInMemoryColumnClause x = new OracleSQLInMemoryColumnClause();
            this.cloneTo(x);
            return x;
        }

        public void cloneTo(OracleSQLInMemoryColumnClause x) {
            super.cloneTo(x);

            if (inMemoryMemCompress != null) {
                ISQLInMemoryMemCompressClause inMemoryMemCompressClone = this.inMemoryMemCompress.clone();
                x.setInMemoryMemCompress(inMemoryMemCompressClone);
            }
        }

        public ISQLInMemoryMemCompressClause getInMemoryMemCompress() {
            return inMemoryMemCompress;
        }

        public void setInMemoryMemCompress(ISQLInMemoryMemCompressClause inMemoryMemCompress) {
            setChildParent(inMemoryMemCompress);
            this.inMemoryMemCompress = inMemoryMemCompress;
        }
    }


    /**
     * NO INMEMORY ( column [, column ]... )
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/25.
     */
    public class OracleSQLNoInMemoryColumnClause extends AbstractOracleSQLIneMemoryColumnClause {

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, columns);
//            }
        }

        @Override
        public OracleSQLNoInMemoryColumnClause clone() {
            OracleSQLNoInMemoryColumnClause x = new OracleSQLNoInMemoryColumnClause();
            this.cloneTo(x);
            return x;
        }


    }
}
