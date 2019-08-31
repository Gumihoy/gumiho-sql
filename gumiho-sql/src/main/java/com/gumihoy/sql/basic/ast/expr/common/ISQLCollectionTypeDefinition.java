package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * TYPE type IS  { assoc_array_type_def | varray_type_def | nested_table_type_def} ;
 * <p>
 * <p>
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/collection-variable.html#GUID-89A1863C-65A1-40CF-9392-86E9FDC21BE9
 *
 * @author kent on 2019-07-26.
 */
public interface ISQLCollectionTypeDefinition extends ISQLTypeDefinition {
    @Override
    ISQLCollectionTypeDefinition clone();


    abstract class AbstractSQLNestedTableTypeDefinition extends AbstractSQLExpr implements ISQLCollectionTypeDefinition {
        protected ISQLName name;
        protected ISQLDataType dataType;
        protected boolean notNull;

        public AbstractSQLNestedTableTypeDefinition() {
        }

        public AbstractSQLNestedTableTypeDefinition(ISQLName name, ISQLDataType dataType, boolean notNull) {
            setName(name);
            setDataType(dataType);
            this.notNull = notNull;
            setAfterSemi(true);
        }


        @Override
        public AbstractSQLNestedTableTypeDefinition clone() {
            throw new UnsupportedOperationException();
        }

        public void cloneTo(AbstractSQLNestedTableTypeDefinition x) {
            super.cloneTo(x);

        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            this.name = name;
        }

        public ISQLDataType getDataType() {
            return dataType;
        }

        public void setDataType(ISQLDataType dataType) {
            this.dataType = dataType;
        }

        public boolean isNotNull() {
            return notNull;
        }

        public void setNotNull(boolean notNull) {
            this.notNull = notNull;
        }

    }

    /**
     * TABLE OF datatype [ NOT NULL ]
     * INDEX BY { PLS_INTEGER | BINARY_INTEGER | VARCHAR2 ( v_size ) | data_type }
     */
    class SQLAssocArrayTypeDefinition extends AbstractSQLNestedTableTypeDefinition {

        protected ISQLDataType indexByDataType;

        public SQLAssocArrayTypeDefinition(ISQLName name, ISQLDataType dataType, boolean notNull, ISQLDataType indexByDataType) {
            super(name, dataType, notNull);
            setIndexByDataType(indexByDataType);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
                this.acceptChild(visitor, dataType);
                this.acceptChild(visitor, indexByDataType);
            }
        }

        @Override
        public SQLAssocArrayTypeDefinition clone() {
            return null;
        }

        public ISQLDataType getIndexByDataType() {
            return indexByDataType;
        }

        public void setIndexByDataType(ISQLDataType indexByDataType) {
            setChildParent(indexByDataType);
            this.indexByDataType = indexByDataType;
        }
    }

    /**
     * TYPE name IS TABLE OF datatype [ NOT NULL ]
     */
    class SQLNestedTableTypeDefinition extends AbstractSQLNestedTableTypeDefinition {

        public SQLNestedTableTypeDefinition(ISQLName name, ISQLDataType dataType, boolean notNull) {
            super(name, dataType, notNull);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
                this.acceptChild(visitor, dataType);
            }
        }

        @Override
        public SQLAssocArrayTypeDefinition clone() {
            return null;
        }
    }


    /**
     * { VARRAY | [ VARYING ] ARRAY } ( size_limit ) OF datatype [ NOT NULL ]
     */
    abstract class AbstractSQLArrayTypeDefinition extends AbstractSQLExpr implements ISQLCollectionTypeDefinition {

        protected ISQLName name;
        protected ISQLExpr size;
        protected ISQLDataType dataType;
        protected boolean notNull;

        public AbstractSQLArrayTypeDefinition(ISQLName name, ISQLExpr size, ISQLDataType dataType, boolean notNull) {
            setName(name);
            setSize(size);
            setDataType(dataType);
            this.notNull = notNull;
        }

        @Override
        public AbstractSQLArrayTypeDefinition clone() {
            throw new UnsupportedOperationException();
        }

        public void cloneTo(AbstractSQLArrayTypeDefinition x) {
            super.cloneTo(x);
        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            this.name = name;
        }

        public ISQLExpr getSize() {
            return size;
        }

        public void setSize(ISQLExpr size) {
            this.size = size;
        }

        public ISQLDataType getDataType() {
            return dataType;
        }

        public void setDataType(ISQLDataType dataType) {
            this.dataType = dataType;
        }

        public boolean isNotNull() {
            return notNull;
        }

        public void setNotNull(boolean notNull) {
            this.notNull = notNull;
        }
    }

    /**
     * VARRAY ( size_limit ) OF datatype [ NOT NULL ]
     */
    class SQLVarrayTypeDefinition extends AbstractSQLArrayTypeDefinition {

        public SQLVarrayTypeDefinition(ISQLName name, ISQLExpr size, ISQLDataType dataType, boolean notNull) {
            super(name, size, dataType, notNull);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
                this.acceptChild(visitor, size);
                this.acceptChild(visitor, dataType);
            }
        }

        @Override
        public SQLVarrayTypeDefinition clone() {
            return null;
        }
    }

    /**
     * ARRAY ( size_limit ) OF datatype [ NOT NULL ]
     */
    class SQLArrayTypeDefinition extends AbstractSQLArrayTypeDefinition {
        public SQLArrayTypeDefinition(ISQLName name, ISQLExpr size, ISQLDataType dataType, boolean notNull) {
            super(name, size, dataType, notNull);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
                this.acceptChild(visitor, size);
                this.acceptChild(visitor, dataType);
            }
        }

        @Override
        public SQLArrayTypeDefinition clone() {
            return null;
        }
    }

    /**
     * VARYING ARRAY ( size_limit ) OF datatype [ NOT NULL ]
     */
    class SQLVaryingArrayTypeDefinition extends AbstractSQLArrayTypeDefinition {
        public SQLVaryingArrayTypeDefinition(ISQLName name, ISQLExpr size, ISQLDataType dataType, boolean notNull) {
            super(name, size, dataType, notNull);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
                this.acceptChild(visitor, size);
                this.acceptChild(visitor, dataType);
            }
        }

        @Override
        public SQLVaryingArrayTypeDefinition clone() {
            return null;
        }
    }


}
