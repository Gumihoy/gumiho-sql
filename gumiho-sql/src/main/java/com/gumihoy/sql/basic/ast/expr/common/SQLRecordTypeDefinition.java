package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * record_type_definition
 * <p>
 * TYPE record_type IS RECORD  ( field_definition [, field_definition]... ) ;
 * field_definition: field datatype [ [ NOT NULL ] { := | DEFAULT } expression ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/record-variable-declaration.html#GUID-704FC014-561E-422C-9636-EDCA3B996AAD
 *
 * @author kent on 2018/4/25.
 */
public class SQLRecordTypeDefinition extends AbstractSQLExpr implements ISQLTypeDefinition {

    protected ISQLName name;

    protected final List<SQLFieldDefinition> fields = new ArrayList<>();

    public SQLRecordTypeDefinition(ISQLName name, SQLFieldDefinition... fields) {
        setName(name);
        for (SQLFieldDefinition field : fields) {
            addField(field);
        }
    }

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, fields);
        }
    }

    @Override
    public SQLRecordTypeDefinition clone() {
        return null;
    }


    public void cloneTo(SQLRecordTypeDefinition x) {
        super.cloneTo(x);
    }

    public ISQLName getName() {
        return name;
    }


    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public List<SQLFieldDefinition> getFields() {
        return fields;
    }

    public void addField(SQLFieldDefinition field) {
        if (field == null) {
            return;
        }
        setChildParent(field);
        this.fields.add(field);
    }


    public static class SQLFieldDefinition extends AbstractSQLExpr {
        protected ISQLName name;

        protected ISQLDataType dataType;

        protected boolean notNull;

        protected SQLDefaultClause defaultClause;


        public SQLFieldDefinition(ISQLName name, ISQLDataType dataType) {
            this(name, dataType, null);
        }

        public SQLFieldDefinition(ISQLName name, ISQLDataType dataType, SQLDefaultClause defaultClause) {
            this(name, dataType, false, defaultClause);
        }

        public SQLFieldDefinition(ISQLName name, ISQLDataType dataType, boolean notNull, SQLDefaultClause defaultClause) {
            setName(name);
            setDataType(dataType);
            this.notNull = notNull;
            setDefaultClause(defaultClause);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
                this.acceptChild(visitor, dataType);
                this.acceptChild(visitor, defaultClause);
            }
        }


        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            setChildParent(name);
            this.name = name;
        }

        public ISQLDataType getDataType() {
            return dataType;
        }

        public void setDataType(ISQLDataType dataType) {
            setChildParent(dataType);
            this.dataType = dataType;
        }

        public boolean isNotNull() {
            return notNull;
        }

        public void setNotNull(boolean notNull) {
            this.notNull = notNull;
        }

        public SQLDefaultClause getDefaultClause() {
            return defaultClause;
        }

        public void setDefaultClause(SQLDefaultClause defaultClause) {
            setChildParent(defaultClause);
            this.defaultClause = defaultClause;
        }
    }
}
