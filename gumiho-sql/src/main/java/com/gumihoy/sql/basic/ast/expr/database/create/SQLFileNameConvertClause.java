package com.gumihoy.sql.basic.ast.expr.database.create;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * FILE_NAME_CONVERT =
 * { ( 'filename_pattern', 'replacement_filename_pattern'
 * [, 'filename_pattern', 'replacement_filename_pattern' ]... )
 * |
 * NONE
 * }
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/file_name_convert.html
 *
 * @author kent on 2019-07-21.
 */
public class SQLFileNameConvertClause extends AbstractSQLExpr {

    protected ISQLValue value;

    public SQLFileNameConvertClause(ISQLValue value) {
        setValue(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, value);
        }
    }

    @Override
    public SQLFileNameConvertClause clone() {
        return null;
    }

    public ISQLValue getValue() {
        return value;
    }

    public void setValue(ISQLValue value) {
        setChildParent(value);
        this.value = value;
    }

    public interface ISQLValue extends ISQLExpr {
        @Override
        ISQLValue clone();
    }

    public static class SQLNoneValue extends AbstractSQLExpr implements ISQLValue {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLNoneValue clone() {
            return null;
        }
    }


    public static class SQLValues extends AbstractSQLExpr implements ISQLValue {
        final List<SQLItem> items = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, items);
            }
        }

        @Override
        public SQLValues clone() {
            return null;
        }

        public List<SQLItem> getItems() {
            return items;
        }

        public void addItem(SQLItem item) {
            if (item == null) {
                return;
            }
            items.add(item);
        }
    }

    public static class SQLItem extends AbstractSQLExpr {
        protected ISQLExpr name;
        protected ISQLExpr replace;

        public SQLItem(ISQLExpr name, ISQLExpr replace) {
            setName(name);
            setReplace(replace);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
                this.acceptChild(visitor, replace);
            }
        }

        @Override
        public SQLNoneValue clone() {
            return null;
        }

        public ISQLExpr getName() {
            return name;
        }

        public void setName(ISQLExpr name) {
            this.name = name;
        }

        public ISQLExpr getReplace() {
            return replace;
        }

        public void setReplace(ISQLExpr replace) {
            this.replace = replace;
        }
    }
}
