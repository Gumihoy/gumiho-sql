package com.gumihoy.sql.dialect.edb.ast;

import com.gumihoy.sql.basic.ast.AbstractSQLObject;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractEDBObject extends AbstractSQLObject implements IEDBObject {

    public AbstractEDBObject() {
        super(DBType.EDB);
    }
}
