package com.gumihoy.sql.dialect.edb.visitor;

import com.gumihoy.sql.basic.visitor.SQLASTOutputVisitor;
import com.gumihoy.sql.config.SQLOutputConfig;

/**
 * @author kent on 2019-06-14.
 */
public class EDBASTOutputVisitor extends SQLASTOutputVisitor implements IEDBASTVisitor {

    public EDBASTOutputVisitor(StringBuilder appender) {
        super(appender);
    }

    public EDBASTOutputVisitor(StringBuilder appender, SQLOutputConfig config) {
        super(appender, config);
    }
}
