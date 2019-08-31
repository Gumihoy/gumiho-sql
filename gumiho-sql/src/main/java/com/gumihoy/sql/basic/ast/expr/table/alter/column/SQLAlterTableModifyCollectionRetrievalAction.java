package com.gumihoy.sql.basic.ast.expr.table.alter.column;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * MODIFY NESTED TABLE collection_item RETURN AS { LOCATOR | VALUE }
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/11.
 */
public class SQLAlterTableModifyCollectionRetrievalAction extends AbstractSQLExpr implements ISQLAlterTableColumnAction {

    protected ISQLName name;
//    protected SQLOption option;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
    }

    @Override
    public SQLAlterTableModifyCollectionRetrievalAction clone() {
        SQLAlterTableModifyCollectionRetrievalAction x = new SQLAlterTableModifyCollectionRetrievalAction();

        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);

//        x.option = this.option;
        return x;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }

        return false;
    }


    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

//    public SQLOption getOption() {
//        return option;
//    }
//
//    public void setOption(SQLOption option) {
//        this.option = option;
//    }

//    public enum SQLOption implements ISQLEnum {
//        LOCATOR(SQLReserved.LOCATOR),
//        VALUE(SQLReserved.VALUE),;
//        public final SQLReserved name;
//
//        SQLOption(SQLReserved name) {
//            this.name = name;
//        }
//
//
//        @Override
//        public SQLReserved getName() {
//            return name;
//        }
//    }
}