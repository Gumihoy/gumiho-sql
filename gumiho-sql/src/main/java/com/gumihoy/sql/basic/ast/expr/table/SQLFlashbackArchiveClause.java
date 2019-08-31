//package com.gumihoy.sql.basic.ast.element.table;
//
//import com.aliyun.gumiho.sql.basic.ast.element.expr.AbstractSQLElement;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.SQLExpr;
//import com.aliyun.gumiho.sql.basic.ast.element.identifier.SQLName;
//import com.aliyun.gumiho.sql.basic.visitor.SQLASTVisitor;
//
///**
// * FLASHBACK ARCHIVE [flashback_archive] | NO FLASHBACK ARCHIVE
// *
// *
// * flashback_archive_clause
// * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
// * @author kent on 2018/7/10.
// */
//public class SQLFlashbackArchiveClause extends AbstractSQLElement implements ISQLFlashbackArchiveClause {
//
//    protected ISQLName name;
//
//    @Override
//    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
//    }
//
//    @Override
//    public SQLFlashbackArchiveClause clone() {
//        SQLFlashbackArchiveClause x = new SQLFlashbackArchiveClause();
//
//        if (this.name != null) {
//            ISQLName nameClone = this.name.clone();
//            x.setName(nameClone);
//        }
//
//        return x;
//    }
//
//    @Override
//    public boolean replace(ISQLElement source, ISQLExpr target) {
//        if (source == name
//                && target instanceof ISQLName) {
//            setName((ISQLName)target);
//            return true;
//        }
//        return false;
//    }
//
//    public ISQLName getName() {
//        return name;
//    }
//
//    public void setName(ISQLName name) {
//        setChildParent(name);
//        this.name = name;
//    }
//}
