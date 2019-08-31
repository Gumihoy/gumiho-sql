package com.gumihoy.sql.basic.ast.expr.table.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * UPGRADE [ [NOT ] INCLUDING DATA ] [ column_properties ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableUpgradeTableAction extends AbstractSQLExpr implements ISQLAlterTableAction {

//    protected SQLOption option;
    protected final List<ISQLExpr> properties = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, properties);
//        }
    }

    @Override
    public SQLAlterTableUpgradeTableAction clone() {
        SQLAlterTableUpgradeTableAction x = new SQLAlterTableUpgradeTableAction();
//        x.option = this.option;

        for (ISQLExpr property : this.properties) {
            ISQLExpr propertyClone = property.clone();
            x.addProperty(propertyClone);
        }

        return x;
    }


//    public SQLOption getOption() {
//        return option;
//    }
//
//    public void setOption(SQLOption option) {
//        this.option = option;
//    }

    public List<ISQLExpr> getProperties() {
        return properties;
    }

    public void addProperty(ISQLExpr property) {
        if (property == null) {
            return;
        }
        setChildParent(property);
        this.properties.add(property);
    }

//    public enum SQLOption implements ISQLEnum {
//        INCLUDING_DATA(SQLReserved.INCLUDING_DATA),
//        NOT_INCLUDING_DATA (SQLReserved.NOT_INCLUDING_DATA),;
//
//        public final SQLReserved name;
//
//        SQLOption(SQLReserved name) {
//            this.name = name;
//        }
//
//        @Override
//        public String toString() {
//            return name.upper;
//        }
//
//        @Override
//        public SQLReserved getName() {
//            return name;
//        }
//    }
}
