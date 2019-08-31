package com.gumihoy.sql.basic.ast.expr.trigger;

import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#trigger%20event
 * https://www.postgresql.org/docs/10/static/sql-createtrigger.html
 *
 * @author kent on 2018/4/26.
 */
public interface SQLTriggerEvent extends ISQLExpr {

    String getEvent();

    @Override
    SQLTriggerEvent clone();

    interface ISQLTriggerDMLEvent extends SQLTriggerEvent {

    }

    interface ISQLTriggerDDLEvent extends SQLTriggerEvent {

    }

    interface ISQLTriggerDatabaseEvent extends SQLTriggerEvent {

    }

    interface ISQLTriggerEventType extends ISQLASTEnum {
        String getEvent();
    }

}
