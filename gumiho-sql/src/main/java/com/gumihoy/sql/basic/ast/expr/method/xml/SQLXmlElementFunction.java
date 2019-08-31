package com.gumihoy.sql.basic.ast.expr.method.xml;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.method.AbstractSQLFunction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * XMLELEMENT ( [ ENTITYESCAPING | NOENTITYESCAPING ] [ NAME ] { identifier | EVALNAME value_expr } [, XML_attributes_clause ] [, value_expr [ [AS] c_alias ]]... )
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/XMLELEMENT.html#GUID-DEA75423-00EA-4034-A246-4A774ADC988E
 *
 * @author kent on 2018/5/24.
 */
public class SQLXmlElementFunction extends AbstractSQLFunction {

//    protected SQLReserved entityEscaping;
//    protected SQLReserved evalName;

    public SQLXmlElementFunction() {
//        super(SQLReserved.XMLELEMENT.ofExpr());
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }


//    public SQLReserved getEntityEscaping() {
//        return entityEscaping;
//    }
//
//    public void setEntityEscaping(SQLReserved entityEscaping) {
//        this.entityEscaping = entityEscaping;
//    }
//
//    public SQLReserved getEvalName() {
//        return evalName;
//    }
//
//    public void setEvalName(SQLReserved evalName) {
//        this.evalName = evalName;
//    }

    /**
     * XMLATTRIBUTES ( [ ENTITYESCAPING | NOENTITYESCAPING ] [ SCHEMACHECK | NOSCHEMACHECK ]
     * value_expr [ { [AS] c_alias } | { AS EVALNAME value_expr } ] [, value_expr [ { [AS] c_alias } | { AS EVALNAME value_expr } ] ]...)
     */
    public static class SQLXmlAttributesClause extends AbstractSQLExpr {

//        protected SQLReserved entityEscaping;
//        protected SQLReserved schemaCheck;
        protected final List<ISQLExpr> items = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, items);
//            }
        }

//        public SQLReserved getEntityEscaping() {
//            return entityEscaping;
//        }
//
//        public void setEntityEscaping(SQLReserved entityEscaping) {
//            this.entityEscaping = entityEscaping;
//        }
//
//        public SQLReserved getSchemaCheck() {
//            return schemaCheck;
//        }
//
//        public void setSchemaCheck(SQLReserved schemaCheck) {
//            this.schemaCheck = schemaCheck;
//        }

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
}
