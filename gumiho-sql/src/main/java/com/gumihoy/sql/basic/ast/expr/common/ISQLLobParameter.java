package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * { { ENABLE | DISABLE } STORAGE IN ROW
 * | CHUNK integer
 * | PCTVERSION integer
 * | FREEPOOLS integer
 * | LOB_retention_clause
 * | LOB_deduplicate_clause
 * | LOB_compression_clause
 * | { ENCRYPT encryption_spec | DECRYPT }
 * | { CACHE | NOCACHE | CACHE READS } [ logging_clause ]
 * }...
 * <p>
 * <p>
 * LOB_parameters
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/22.
 */
public interface ISQLLobParameter extends ISQLExpr, ISQLLobStorageParameter {
    @Override
    ISQLLobParameter clone();


    /**
     * ENABLE STORAGE IN ROW
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/22.
     */
    class SQLEnableStorageInRow extends AbstractSQLExpr implements ISQLLobParameter {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLEnableStorageInRow clone() {
            return new SQLEnableStorageInRow();
        }
    }

    /**
     * DISABLE STORAGE IN ROW
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/22.
     */
    class SQLDisableStorageInRow extends AbstractSQLExpr implements ISQLLobParameter {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLDisableStorageInRow clone() {
            return new SQLDisableStorageInRow();
        }
    }


    /**
     * CHUNK integer
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/22.
     */
    class SQLChunk extends AbstractSQLExpr implements ISQLLobParameter {

        protected ISQLExpr value;

        public SQLChunk(ISQLExpr value) {
            setValue(value);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }

        @Override
        public SQLChunk clone() {
            ISQLExpr valueClone = this.value.clone();
            SQLChunk x = new SQLChunk(valueClone);
            return x;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }

    /**
     * PCTVERSION integer
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/22.
     */
    public class SQLPctversion extends AbstractSQLExpr implements ISQLLobParameter {

        protected ISQLExpr value;

        public SQLPctversion(ISQLExpr value) {
            setValue(value);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }

        @Override
        public SQLPctversion clone() {
            ISQLExpr valueClone = this.value.clone();
            SQLPctversion x = new SQLPctversion(valueClone);
            return x;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }


    /**
     * FREEPOOLS integer
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/22.
     */
    public class SQLFreePools extends AbstractSQLExpr implements ISQLLobParameter {

        protected ISQLExpr value;

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }

        @Override
        public SQLFreePools clone() {
            SQLFreePools x = new SQLFreePools();
            ISQLExpr valueClone = this.value.clone();
            x.setValue(valueClone);
            return x;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }

    /**
     * ENCRYPT encryption_spec
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/22.
     */
    public class SQLEncrypt extends AbstractSQLExpr implements ISQLLobParameter {

//        protected SQLEncryptionSpec encryptionSpec;


        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, encryptionSpec);
//            }
        }

        @Override
        public SQLEncrypt clone() {
            SQLEncrypt x = new SQLEncrypt();
//            SQLEncryptionSpec encryptionSpecClone = this.encryptionSpec.clone();
//            x.setEncryptionSpec(encryptionSpecClone);
            return x;
        }

//        public SQLEncryptionSpec getEncryptionSpec() {
//            return encryptionSpec;
//        }
//
//        public void setEncryptionSpec(SQLEncryptionSpec encryptionSpec) {
//            setChildParent(encryptionSpec);
//            this.encryptionSpec = encryptionSpec;
//        }
    }


    /**
     * DECRYPT
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/22.
     */
    public class SQLDecrypt extends AbstractSQLExpr implements ISQLLobParameter {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLDecrypt clone() {
            return new SQLDecrypt();
        }
    }

    /**
     * CACHE [logging_clause]
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/22.
     */
    public class SQLCache extends AbstractSQLExpr implements ISQLLobParameter {

        protected ISQLLoggingClause loggingClause;

        public SQLCache() {
        }

        public SQLCache(ISQLLoggingClause loggingClause) {
            setLoggingClause(loggingClause);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, loggingClause);
//            }
        }

        @Override
        public SQLCache clone() {
            SQLCache x = new SQLCache();
            ISQLLoggingClause loggingClauseClone = this.loggingClause.clone();
            x.setLoggingClause(loggingClauseClone);
            return x;
        }

        public ISQLLoggingClause getLoggingClause() {
            return loggingClause;
        }

        public void setLoggingClause(ISQLLoggingClause loggingClause) {
            setChildParent(loggingClause);
            this.loggingClause = loggingClause;
        }
    }

    /**
     * NOCACHE [logging_clause]
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/22.
     */
    public class SQLNoCache extends AbstractSQLExpr implements ISQLLobParameter {

        protected ISQLLoggingClause loggingClause;

        public SQLNoCache() {
        }

        public SQLNoCache(ISQLLoggingClause loggingClause) {
            setLoggingClause(loggingClause);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, loggingClause);
//            }
        }

        @Override
        public SQLNoCache clone() {
            SQLNoCache x = new SQLNoCache();
            ISQLLoggingClause loggingClauseClone = this.loggingClause.clone();
            x.setLoggingClause(loggingClauseClone);
            return x;
        }

        public ISQLLoggingClause getLoggingClause() {
            return loggingClause;
        }

        public void setLoggingClause(ISQLLoggingClause loggingClause) {
            setChildParent(loggingClause);
            this.loggingClause = loggingClause;
        }
    }

    /**
     * CACHE READS [logging_clause]
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/22.
     */
    public class SQLCacheReads extends AbstractSQLExpr implements ISQLLobParameter {

        protected ISQLLoggingClause loggingClause;

        public SQLCacheReads() {
        }

        public SQLCacheReads(ISQLLoggingClause loggingClause) {
            setLoggingClause(loggingClause);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, loggingClause);
//            }
        }

        @Override
        public SQLCacheReads clone() {
            SQLCacheReads x = new SQLCacheReads();
//            IOracleSQLLoggingClause loggingClauseClone = this.loggingClause.clone();
//            x.setLoggingClause(loggingClauseClone);
            return x;
        }

        public ISQLLoggingClause getLoggingClause() {
            return loggingClause;
        }

        public void setLoggingClause(ISQLLoggingClause loggingClause) {
            setChildParent(loggingClause);
            this.loggingClause = loggingClause;
        }
    }

}
