package com.gumihoy.sql.basic.ast.expr.method.xml;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.method.AbstractSQLFunction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * XMLTABLE([ XMLnamespaces_clause , ] XQuery_string XMLTABLE_options)
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/XMLTABLE.html#GUID-C4A32C58-33E5-4CF1-A1FE-039550D3ECFA
 *
 * @author kent on 2018/5/24.
 */
public class SQLXmlTableFunction extends AbstractSQLFunction {

    protected SQLXmlTableOption option;

    public SQLXmlTableFunction() {
//        super(SQLReserved.XMLCAST.ofExpr());
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }

    public SQLXmlTableOption getOption() {
        return option;
    }

    public void setOption(SQLXmlTableOption option) {
        setChildParent(option);
        this.option = option;
    }

    /**
     *
     */
    public static class SQLXmlNamespacesClause extends AbstractSQLExpr {
        protected final List<ISQLExpr> items = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, items);
//            }
        }

        public List<ISQLExpr> getItems() {
            return items;
        }

        public void addItem(ISQLExpr item) {
            if (item == null) {
                return;
            }
            setChildParent(item);
            this.items.add(item);
        }
    }

    /**
     * [ XML_passing_clause ] [ RETURNING SEQUENCE BY REF ] [ COLUMNS XML_table_column [, XML_table_column]...]
     */
    public static class SQLXmlTableOption extends AbstractSQLExpr {
        protected SQLXmlPassingClause passingClause;
        protected boolean returningSequenceByRef;
        protected final List<SQLXmlTableColumn> columns = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, passingClause);
//                this.acceptChild(visitor, columns);
//            }
        }

        public SQLXmlPassingClause getPassingClause() {
            return passingClause;
        }

        public void setPassingClause(SQLXmlPassingClause passingClause) {
            setChildParent(passingClause);
            this.passingClause = passingClause;
        }

        public boolean isReturningSequenceByRef() {
            return returningSequenceByRef;
        }

        public void setReturningSequenceByRef(boolean returningSequenceByRef) {
            this.returningSequenceByRef = returningSequenceByRef;
        }

        public List<SQLXmlTableColumn> getColumns() {
            return columns;
        }

        public void addColumn(SQLXmlTableColumn column) {
            if (column == null) {
                return;
            }
            setChildParent(column);
            this.columns.add(column);
        }
    }


    public interface SQLXmlTableColumn extends ISQLExpr {
        @Override
        ISQLExpr clone();
    }

    public static abstract class AbstractSQLXmlTableColumn extends AbstractSQLExpr implements SQLXmlTableColumn {
        protected ISQLExpr column;
        protected ISQLExpr path;
        protected ISQLExpr default_;

        public AbstractSQLXmlTableColumn(ISQLExpr column) {
            setColumn(column);
        }

        public void cloneTo(AbstractSQLXmlTableColumn x) {
            ISQLExpr columnClone = column.clone();
            x.setColumn(columnClone);

            ISQLExpr pathClone = path.clone();
            x.setPath(columnClone);

            ISQLExpr defaultClone = default_.clone();
            x.setDefault_(defaultClone);
        }

        public ISQLExpr getColumn() {
            return column;
        }

        public void setColumn(ISQLExpr column) {
            setChildParent(column);
            this.column = column;
        }

        public ISQLExpr getPath() {
            return path;
        }

        public void setPath(ISQLExpr path) {
            setChildParent(path);
            this.path = path;
        }

        public ISQLExpr getDefault_() {
            return default_;
        }

        public void setDefault_(ISQLExpr default_) {
            setChildParent(default_);
            this.default_ = default_;
        }
    }


    /**
     * column FOR ORDINALITY
     */
    public static class SQLXmlTableColumnByForOrdinality extends AbstractSQLXmlTableColumn {

        public SQLXmlTableColumnByForOrdinality(ISQLExpr column) {
            super(column);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, column);
//            }
        }
    }

    /**
     * column datatype/XMLTYPE [ PATH string ] [ DEFAULT expr ]}
     */
    public static class SQLXmlTableColumnByDataType extends AbstractSQLXmlTableColumn {

        protected ISQLDataType dataType;
        protected boolean sequenceByRef;

        public SQLXmlTableColumnByDataType(ISQLExpr column) {
            super(column);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, column);
//                this.acceptChild(visitor, dataType);
//                this.acceptChild(visitor, path);
//                this.acceptChild(visitor, default_);
//            }
        }

        public ISQLDataType getDataType() {
            return dataType;
        }

        public void setDataType(ISQLDataType dataType) {
            setChildParent(dataType);
            this.dataType = dataType;
        }

        public boolean isSequenceByRef() {
            return sequenceByRef;
        }

        public void setSequenceByRef(boolean sequenceByRef) {
            this.sequenceByRef = sequenceByRef;
        }
    }

}
