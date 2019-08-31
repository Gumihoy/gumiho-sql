package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 *
 * COMPRESS
 * | ROW STORE COMPRESS [ BASIC | ADVANCED ]
 * | COLUMN STORE COMPRESS [  FOR { QUERY | ARCHIVE } [ LOW | HIGH ] ]
 *   [ [NO] ROW LEVEL LOCKING ]
 * | NOCOMPRESS
 *
 * table_compression
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/table_compression.html
 *
 * index_compression:
 *  * { prefix_compression
 *  * | advanced_index_compression
 *  * }
 *  COMPRESS expr
 *  COMPRESS ADVANCED expr
 *  NOCOMPRESS
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2019-08-02.
 */
public interface ISQLCompression extends ISQLExpr {
    @Override
    ISQLCompression clone();

    public class SQLNoCompress extends AbstractSQLExpr implements ISQLCompression {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLNoCompress clone() {
            return new SQLNoCompress();
        }
    }

    public class SQLCompress extends AbstractSQLExpr implements ISQLCompression {
        protected ISQLExpr value;

        public SQLCompress(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }

        @Override
        public SQLCompress clone() {
            ISQLExpr valueClone = this.value.clone();
            return new SQLCompress(valueClone);
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }


    public class SQLRowStoreCompress extends AbstractSQLExpr implements ISQLCompression {
        protected SQLRowStoreCompressType type;

        public SQLRowStoreCompress(SQLRowStoreCompressType type) {
            this.type = type;
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLRowStoreCompress clone() {

            return new SQLRowStoreCompress(this.type);
        }

        public SQLRowStoreCompressType getType() {
            return type;
        }

        public void setType(SQLRowStoreCompressType type) {
            this.type = type;
        }
    }

    public enum SQLRowStoreCompressType implements ISQLASTEnum {

        BASIC("basic", "BASIC"),
        ADVANCED("advanced", "ADVANCED");
        public final String lower;
        public final String upper;


        SQLRowStoreCompressType(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }

        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }
    }


    public class SQLColumnStoreCompress extends AbstractSQLExpr implements ISQLCompression {
        protected SQLColumnStoreCompressForType forType;
        protected SQLRowLevelLockingType rowLevelLockingType;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLColumnStoreCompress clone() {
            return null;
        }

        public SQLColumnStoreCompressForType getForType() {
            return forType;
        }

        public void setForType(SQLColumnStoreCompressForType forType) {
            this.forType = forType;
        }

        public SQLRowLevelLockingType getRowLevelLockingType() {
            return rowLevelLockingType;
        }

        public void setRowLevelLockingType(SQLRowLevelLockingType rowLevelLockingType) {
            this.rowLevelLockingType = rowLevelLockingType;
        }
    }

    public enum SQLColumnStoreCompressForType implements ISQLASTEnum {
        FOR_QUERY("for query", "for query"),
        FOR_ARCHIVE("for archive", "for archive"),
        FOR_QUERY_LOW("for query low", "for query low"),
        FOR_ARCHIVE_LOW("for archive low", "for archive low"),
        FOR_QUERY_HIGH("for query high", "for query high"),
        FOR_ARCHIVE_HIGH("for archive high", "for archive high"),;

        public final String lower;
        public final String upper;


        SQLColumnStoreCompressForType(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }

        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }
    }

    public enum SQLRowLevelLockingType implements ISQLASTEnum {

        ROW_LEVEL_LOCKING("ROW LEVEL LOCKING", "ROW LEVEL LOCKING"),
        NO_ROW_LEVEL_LOCKING("NO ROW LEVEL LOCKING", "NO ROW LEVEL LOCKING");

        public final String lower;
        public final String upper;


        SQLRowLevelLockingType(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }

        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }
    }





    public class SQLCompressAdvanced extends AbstractSQLExpr implements ISQLCompression {
        protected ISQLExpr value;

        public SQLCompressAdvanced(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }

        @Override
        public SQLCompressAdvanced clone() {
            ISQLExpr valueClone = this.value.clone();
            return new SQLCompressAdvanced(valueClone);
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }


}
