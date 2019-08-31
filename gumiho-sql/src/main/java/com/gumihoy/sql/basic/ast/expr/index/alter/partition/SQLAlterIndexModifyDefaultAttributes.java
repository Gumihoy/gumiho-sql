package com.gumihoy.sql.basic.ast.expr.index.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * MODIFY DEFAULT ATTRIBUTES [ FOR PARTITION partition ]
 * { physical_attributes_clause
 * | TABLESPACE { tablespace | DEFAULT }
 * | logging_clause
 * }...
 * <p>
 * modify_index_default_attrs
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-INDEX.html#GUID-D8F648E7-8C07-4C89-BB71-862512536558
 *
 * @author kent on 2019-08-02.
 */
public class SQLAlterIndexModifyDefaultAttributes extends AbstractSQLExpr implements ISQLAlterIndexPartitionAction {

    protected ISQLExpr partition;
    protected final List<ISQLExpr> attributes = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, partition);
            this.acceptChild(visitor, attributes);
        }
    }

    @Override
    public SQLAlterIndexModifyDefaultAttributes clone() {
        return null;
    }


    public ISQLExpr getPartition() {
        return partition;
    }

    public void setPartition(ISQLExpr partition) {
        this.partition = partition;
    }

    public List<ISQLExpr> getAttributes() {
        return attributes;
    }

    public void addAttribute(ISQLExpr attribute) {
        if (attribute == null) {
            return;
        }
        setChildParent(attribute);
        attributes.add(attribute);
    }
}
