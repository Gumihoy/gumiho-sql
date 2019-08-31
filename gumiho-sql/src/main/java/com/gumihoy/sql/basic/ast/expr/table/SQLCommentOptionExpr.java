//package com.gumihoy.sql.basic.ast.element.table;
//
//import com.aliyun.gumiho.sql.basic.ast.element.expr.SQLExpr;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.SQLSetOptionExpr;
//import com.aliyun.gumiho.sql.basic.ast.enums.SQLReserved;
//
///**
// * COMMENT [=] 'string'
// *
// * @author kent on 2018/7/30.
// */
//public class SQLCommentOptionExpr extends SQLSetOptionExpr {
//
//    public SQLCommentOptionExpr() {
//        super(SQLReserved.COMMENT.ofExpr());
//    }
//
//    public SQLCommentOptionExpr(ISQLElement value) {
//        super(SQLReserved.COMMENT.ofExpr(), value);
//    }
//
//    public SQLCommentOptionExpr(boolean equalSign, ISQLExpr value) {
//        super(SQLReserved.COMMENT.ofExpr(), equalSign, value);
//    }
//
//    public static SQLCommentOptionExpr of(ISQLElement value) {
//        return new SQLCommentOptionExpr(value);
//    }
//
//}
