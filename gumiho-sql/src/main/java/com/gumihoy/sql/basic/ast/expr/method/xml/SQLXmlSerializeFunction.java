package com.gumihoy.sql.basic.ast.expr.method.xml;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.method.AbstractSQLFunction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * XMLSERIALIZE( { DOCUMENT | CONTENT } value_expr [ AS datatype ]
 * [ ENCODING xml_encoding_spec ][ VERSION string_literal ]
 * [ NO INDENT | { INDENT [SIZE = number] } ] [ { HIDE | SHOW } DEFAULTS ])
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/XMLCAST.html#GUID-06563B93-1247-4F0C-B6BE-42DB3B1DB069
 *
 * @author kent on 2018/5/24.
 */
public class SQLXmlSerializeFunction extends AbstractSQLFunction {

//    protected SQLReserved content;
    protected ISQLExpr encoding;
    protected ISQLExpr version;

//    protected SQLReserved indent;
    protected ISQLExpr size;

//    protected SQLReserved defaults;


    public SQLXmlSerializeFunction() {
//        super(SQLReserved.XMLSERIALIZE.ofExpr());
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }


//    public SQLReserved getContent() {
//        return content;
//    }
//
//    public void setContent(SQLReserved content) {
//        this.content = content;
//    }

    public ISQLExpr getEncoding() {
        return encoding;
    }

    public void setEncoding(ISQLExpr encoding) {
        this.encoding = encoding;
    }

    public ISQLExpr getVersion() {
        return version;
    }

    public void setVersion(ISQLExpr version) {
        this.version = version;
    }

//    public SQLReserved getIndent() {
//        return indent;
//    }
//
//    public void setIndent(SQLReserved indent) {
//        this.indent = indent;
//    }

    public ISQLExpr getSize() {
        return size;
    }

    public void setSize(ISQLExpr size) {
        this.size = size;
    }

//    public SQLReserved getDefaults() {
//        return defaults;
//    }
//
//    public void setDefaults(SQLReserved defaults) {
//        this.defaults = defaults;
//    }
}
