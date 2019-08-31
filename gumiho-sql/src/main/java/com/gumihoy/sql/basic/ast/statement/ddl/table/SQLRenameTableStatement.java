package com.gumihoy.sql.basic.ast.statement.ddl.table;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * RENAME TABLE tbl_name TO new_tbl_name [, tbl_name2 TO new_tbl_name2] ...
 * https://dev.mysql.com/doc/refman/8.0/en/rename-table.html
 * <p>
 * RENAME TABLE tbl_name TO new_tbl_name
 * https://help.aliyun.com/document_detail/71318.html?spm=a2c4g.11186623.6.695.7065614bab5dRr
 * <p>
 * RENAME TABLE old_name TO new_name
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/RENAME.html
 *
 * @author kent on 2018/6/4.
 */
public class SQLRenameTableStatement extends AbstractSQLStatement {

    protected final List<Item> items = new ArrayList<>();

    public SQLRenameTableStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, items);
//        }
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.TABLE_RENAME;
    }


    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.items.add(item);
    }


    /**
     * oldName TO newName
     */
    public static class Item extends AbstractSQLExpr {
        protected ISQLName oldName;
        protected ISQLName newName;

        public Item(ISQLName oldName, ISQLName newName) {
            setOldName(oldName);
            setNewName(newName);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, oldName);
//                this.acceptChild(visitor, newName);
//            }
        }

        @Override
        public Item clone() {
            ISQLName oldNameClone = this.oldName.clone();
            ISQLName newName = this.newName.clone();
            Item x = new Item(oldNameClone, newName);
            return x;
        }

        public ISQLName getOldName() {
            return oldName;
        }

        public void setOldName(ISQLName oldName) {
            setChildParent(oldName);
            this.oldName = oldName;
        }

        public ISQLName getNewName() {
            return newName;
        }

        public void setNewName(ISQLName newName) {
            setChildParent(newName);
            this.newName = newName;
        }
    }

}
