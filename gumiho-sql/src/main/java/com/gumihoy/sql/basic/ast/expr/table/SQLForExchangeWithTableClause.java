//package com.gumihoy.sql.basic.ast.element.table;
//
//import com.aliyun.gumiho.sql.basic.ast.element.expr.AbstractSQLElement;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.SQLExpr;
//import com.aliyun.gumiho.sql.basic.ast.element.identifier.SQLName;
//import com.aliyun.gumiho.sql.basic.visitor.SQLASTVisitor;
//
///**
// * FOR EXCHANGE WITH TABLE [ schema .] table }
// * <p>
// * table_properties
// * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
// *
// * @author kent on 2018/7/10.
// */
//public class SQLForExchangeWithTableClause extends AbstractSQLElement {
//
//    protected ISQLExpr name;
//
//    public SQLForExchangeWithTableClause(ISQLElement name) {
//        setName(name);
//    }
//
//    public static SQLForExchangeWithTableClause of(ISQLElement name) {
//        return new SQLForExchangeWithTableClause(name);
//    }
//
//    @Override
//    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
//    }
//
//    @Override
//    public SQLForExchangeWithTableClause clone() {
//        ISQLExpr nameClone = this.name.clone();
//        SQLForExchangeWithTableClause x = new SQLForExchangeWithTableClause(nameClone);
//        return x;
//    }
//
//    @Override
//    public boolean replace(ISQLElement source, ISQLExpr target) {
//        if (source == name) {
//            setName(target);
//            return true;
//        }
//        return false;
//    }
//
//    public ISQLExpr getName() {
//        return name;
//    }
//
//    public String getTableName() {
//        if (name instanceof ISQLName) {
//            return ((ISQLName) name).getName();
//        }
//        return null;
//    }
//
//    public void setName(ISQLElement name) {
//        setChildParent(name);
//        this.name = name;
//    }
//}
