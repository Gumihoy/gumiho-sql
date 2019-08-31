/*
 * Copyright (C) 2017-2018 kent(kent.bohai@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * [ XMLSCHEMA XMLSchema_URL ] ELEMENT { element | XMLSchema_URL # element } [ STORE ALL VARRAYS AS { LOBS | TABLES } ] [ { ALLOW | DISALLOW } NONSCHEMA ] [ { ALLOW | DISALLOW } ANYSCHEMA ]
 * <p>
 * XMLSchema_spec
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent onCondition 2018/3/25.
 */
public class SQLXmlSchemaSpec extends AbstractSQLExpr {

    private ISQLExpr xmlSchemaUrl;

    private ISQLExpr elementXmlSchemaUrl;

    private ISQLExpr element;

//    private OracleSQLStoreAllVarraysAsType storeAllVarraysAsType;

//    private SQLAllowType nonschemaType;
//
//    private SQLAllowType anyschemaType;


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, xmlSchemaUrl);
//            this.acceptChild(visitor, elementXmlSchemaUrl);
//            this.acceptChild(visitor, element);
//            this.acceptChild(visitor, xmlSchemaUrl);
//        }
    }


    public ISQLExpr getXmlSchemaUrl() {
        return xmlSchemaUrl;
    }

    public void setXmlSchemaUrl(ISQLExpr xmlSchemaUrl) {
        this.xmlSchemaUrl = xmlSchemaUrl;
    }

    public ISQLExpr getElementXmlSchemaUrl() {
        return elementXmlSchemaUrl;
    }

    public void setElementXmlSchemaUrl(ISQLExpr elementXmlSchemaUrl) {
        this.elementXmlSchemaUrl = elementXmlSchemaUrl;
    }

    public ISQLExpr getElement() {
        return element;
    }

    public void setElement(ISQLExpr element) {
        this.element = element;
    }

//    public OracleSQLStoreAllVarraysAsType getStoreAllVarraysAsType() {
//        return storeAllVarraysAsType;
//    }
//
//    public void setStoreAllVarraysAsType(OracleSQLStoreAllVarraysAsType storeAllVarraysAsType) {
//        this.storeAllVarraysAsType = storeAllVarraysAsType;
//    }

//    public SQLAllowType getNonschemaType() {
//        return nonschemaType;
//    }
//
//    public void setNonschemaType(SQLAllowType nonschemaType) {
//        this.nonschemaType = nonschemaType;
//    }
//
//    public SQLAllowType getAnyschemaType() {
//        return anyschemaType;
//    }
//
//    public void setAnyschemaType(SQLAllowType anyschemaType) {
//        this.anyschemaType = anyschemaType;
//    }

//    public enum SQLAllowType implements ISQLEnum {
//        ALLOW(SQLReserved.ALLOW),
//        DISALLOW(SQLReserved.DISALLOW);
//
//        public final SQLReserved name;
//
//        SQLAllowType(SQLReserved name) {
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
