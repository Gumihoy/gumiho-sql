package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * NESTED TABLE
 * { nested_item | COLUMN_VALUE }
 * [ substitutable_column_clause ]
 * [ LOCAL | GLOBAL ]
 * STORE AS storage_table
 * [ ( { (object_properties)
 * | [ physical_properties ]
 * | [ column_properties ]
 * }...
 * )
 * ]
 * [ RETURN [ AS ]  { LOCATOR | VALUE } ]
 * <p>
 * <p>
 * nested_table_col_properties
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
 *
 * @author kent on 2018/6/22.
 */
public class OracleNestedTableColProperty extends AbstractOracleExpr implements IOracleColumnProperty {

    protected ISQLExpr nestedItem;
    protected IOracleSubstitutableColumnClause substitutableColumnClause;

    protected SQLLocalType localType;

    protected ISQLExpr storageTable;

    protected final List<ISQLExpr> storeAsItems = new ArrayList<>();

    protected ReturnOption returnOption;


    @Override
    public void accept0(IOracleASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, nestedItem);
//            this.acceptChild(visitor, substitutableColumnClause);
//            this.acceptChild(visitor, storageTable);
//            this.acceptChild(visitor, storeAsItems);
//        }
    }

    @Override
    public OracleNestedTableColProperty clone() {
        OracleNestedTableColProperty x = new OracleNestedTableColProperty();
        return x;
    }


    public ISQLExpr getNestedItem() {
        return nestedItem;
    }

    public void setNestedItem(ISQLExpr nestedItem) {
        setChildParent(nestedItem);
        this.nestedItem = nestedItem;
    }

    public IOracleSubstitutableColumnClause getSubstitutableColumnClause() {
        return substitutableColumnClause;
    }

    public void setSubstitutableColumnClause(IOracleSubstitutableColumnClause substitutableColumnClause) {
        setChildParent(substitutableColumnClause);
        this.substitutableColumnClause = substitutableColumnClause;
    }

//    public SQLLocalType getLocalType() {
//        return localType;
//    }
//
//    public void setLocalType(SQLLocalType localType) {
//        this.localType = localType;
//    }

    public ISQLExpr getStorageTable() {
        return storageTable;
    }

    public void setStorageTable(ISQLExpr storageTable) {
        setChildParent(storageTable);
        this.storageTable = storageTable;
    }

    public List<ISQLExpr> getStoreAsItems() {
        return storeAsItems;
    }

    public void addStoreAsItem(ISQLExpr storeAsItem) {
        if (storeAsItem == null) {
            return;
        }
        setChildParent(storeAsItem);
        this.storeAsItems.add(storeAsItem);
    }

    public void addStoreAsItems(List<? extends ISQLExpr> storeAsItems) {
        if (storeAsItems == null) {
            return;
        }
        for (ISQLExpr storeAsItem : storeAsItems) {
            addStoreAsItem(storeAsItem);
        }
    }

    public ReturnOption getReturnOption() {
        return returnOption;
    }

    public void setReturnOption(ReturnOption returnOption) {
        this.returnOption = returnOption;
    }

    /**
     * COLUMN_VALUE
     */
    public static class SQLColumnValue extends AbstractOracleExpr {
        public static SQLColumnValue of() {
            return new SQLColumnValue();
        }

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLColumnValue clone() {
            SQLColumnValue x = new SQLColumnValue();
            return x;
        }
    }

    /**
     * LOCAL | GLOBAL
     *
     * @author kent on 2018/6/27.
     */
    public enum SQLLocalType implements ISQLASTEnum {

        LOCAL("local", "LOCAL"),
        GLOBAL("global", "GLOBAL");
        public final String lower;
        public final String upper;


        SQLLocalType(String lower, String upper) {
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

    public enum ReturnOption implements ISQLASTEnum {

//        RETURN_LOCATOR(SQLReserved.RETURN_LOCATOR),
//        RETURN_AS_LOCATOR(SQLReserved.RETURN_AS_LOCATOR),
//        RETURN_VALUE(SQLReserved.RETURN_VALUE),
//        RETURN_AS_VALUE(SQLReserved.RETURN_AS_VALUE),;
        ;
        public final String lower;
        public final String upper;


        ReturnOption(String lower, String upper) {
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
}
