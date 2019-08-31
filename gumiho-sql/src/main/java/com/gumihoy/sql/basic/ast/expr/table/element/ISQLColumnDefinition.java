package com.gumihoy.sql.basic.ast.expr.table.element;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.ISQLColumnConstraint;

import java.util.List;

/**
 * @author kent on 2018/7/14.
 */
public interface ISQLColumnDefinition extends ISQLTableElement {
    @Override
    ISQLColumnDefinition clone();

    List<ISQLColumnConstraint> getColumnConstraints();
    void addColumnConstraint(ISQLColumnConstraint columnConstraint);

    boolean isReferencesColumn();

    List<ISQLName> referencedTables();
}
