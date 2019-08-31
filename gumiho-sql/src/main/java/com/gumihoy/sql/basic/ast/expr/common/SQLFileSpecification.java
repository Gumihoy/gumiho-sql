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
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/file_specification.html#GUID-580FA726-F712-4410-90CF-783A2DA89688
 *
 * @author kent onCondition 2018/3/16.
 */
public class SQLFileSpecification extends AbstractSQLExpr {

    public ISQLExpr file;

    public SQLSizeClause sizeClause;

    public SQLSizeClause blockSize;

    public boolean reuse = false;

    public ISQLAutoExtendClause autoextendClause;


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, file);
            this.acceptChild(visitor, sizeClause);
            this.acceptChild(visitor, blockSize);
            this.acceptChild(visitor, autoextendClause);
        }
    }


    public ISQLExpr getFile() {
        return file;
    }

    public void setFile(ISQLExpr file) {
        this.file = file;
    }

    public SQLSizeClause getSizeClause() {
        return sizeClause;
    }

    public void setSizeClause(SQLSizeClause sizeClause) {
        this.sizeClause = sizeClause;
    }

    public SQLSizeClause getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(SQLSizeClause blockSize) {
        this.blockSize = blockSize;
    }

    public boolean isReuse() {
        return reuse;
    }

    public void setReuse(boolean reuse) {
        this.reuse = reuse;
    }

    public ISQLAutoExtendClause getAutoextendClause() {
        return autoextendClause;
    }

    public void setAutoextendClause(ISQLAutoExtendClause autoextendClause) {
        this.autoextendClause = autoextendClause;
    }

    /**
     * autoextend_clause
     * <p>
     * https://docs.oracle.com/database/121/SQLRF/clauses004.htm#SQLRF01602
     *
     * @author kent onCondition 2018/3/16.
     */
    public interface ISQLAutoExtendClause extends ISQLExpr {
    }

    /**
     * autoextend_clause
     * <p>
     * https://docs.oracle.com/database/121/SQLRF/clauses004.htm#SQLRF01602
     *
     * @author kent onCondition 2018/3/16.
     */
    public static class SQLAutoExtendOnClause extends AbstractSQLExpr implements ISQLAutoExtendClause {


        public SQLSizeClause next;

        public SQLMaxSizeClause maxsize;

        public SQLAutoExtendOnClause() {
        }

        public SQLAutoExtendOnClause(SQLSizeClause next, SQLMaxSizeClause maxsize) {
            setNext(next);
            setMaxsize(maxsize);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, next);
                this.acceptChild(visitor, maxsize);
            }
        }


        public SQLSizeClause getNext() {
            return next;
        }

        public void setNext(SQLSizeClause next) {
            setChildParent(next);
            this.next = next;
        }

        public SQLMaxSizeClause getMaxsize() {
            return maxsize;
        }

        public void setMaxsize(SQLMaxSizeClause maxsize) {
            setChildParent(maxsize);
            this.maxsize = maxsize;
        }
    }

    /**
     * autoextend_clause
     * <p>
     * https://docs.oracle.com/database/121/SQLRF/clauses004.htm#SQLRF01602
     *
     * @author kent onCondition 2018/3/16.
     */
    public static class SQLAutoExtendOffClause extends AbstractSQLExpr implements ISQLAutoExtendClause {

        public SQLAutoExtendOffClause() {
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }
    }


    /**
     * MAXSIZE { UNLIMITED | size_clause }
     * <p>
     * maxsize_clause
     * <p>
     * https://docs.oracle.com/database/121/SQLRF/clauses009.htm#SQLRF30013
     *
     * @author kent onCondition 2018/3/16.
     */
    public static class SQLMaxSizeClause extends AbstractSQLExpr implements SQLStorageClause.ISQLItem {

        protected ISQLExpr value;

        public SQLMaxSizeClause(ISQLExpr value) {
            setValue(value);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }

        @Override
        public SQLMaxSizeClause clone() {
            ISQLExpr valueClone = this.value.clone();
            SQLMaxSizeClause x = new SQLMaxSizeClause(valueClone);
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

}
