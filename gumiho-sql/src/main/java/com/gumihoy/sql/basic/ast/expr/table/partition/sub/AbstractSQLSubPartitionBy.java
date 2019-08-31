package com.gumihoy.sql.basic.ast.expr.table.partition.sub;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLStoreInClause;

import java.util.ArrayList;
import java.util.List;

/**
 * [SUBPARTITION BY
 * { [LINEAR] HASH(expr)
 * | [LINEAR] KEY [ALGORITHM={1|2}] (column_list) }
 * [SUBPARTITIONS num]
 * ]
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html
 *
 * @author kent onCondition 2018/4/10.
 */
public abstract class AbstractSQLSubPartitionBy extends AbstractSQLExpr implements ISQLSubPartitionBy {

    protected boolean linear;
    protected final List<ISQLExpr> columns = new ArrayList<>();

    protected ISQLExpr subpartitionsNum;
    protected SQLStoreInClause storeInClause;

    protected SQLSubpartitionTemplate subpartitionTemplate;

    @Override
    public AbstractSQLSubPartitionBy clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void cloneTo(AbstractSQLSubPartitionBy x) {
        super.cloneTo(x);

        for (ISQLExpr column : this.columns) {
            ISQLExpr columnClone = column.clone();
            x.addColumn(columnClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        boolean replace = replaceInList(columns, source, target, this);
        if (replace) {
            return true;
        }

        if (source == subpartitionsNum) {
            setSubpartitionsNum(target);
            return true;
        }

        return false;
    }

    public boolean isLinear() {
        return linear;
    }

    public void setLinear(boolean linear) {
        this.linear = linear;
    }

    public List<ISQLExpr> getColumns() {
        return columns;
    }

    public void addColumn(ISQLExpr column) {
        if (column == null) {
            return;
        }
        setChildParent(column);
        this.columns.add(column);
    }

    public ISQLExpr getSubpartitionsNum() {
        return subpartitionsNum;
    }

    public void setSubpartitionsNum(ISQLExpr subpartitionsNum) {
        setChildParent(subpartitionsNum);
        this.subpartitionsNum = subpartitionsNum;
    }

    public SQLStoreInClause getStoreInClause() {
        return storeInClause;
    }

    public void setStoreInClause(SQLStoreInClause storeInClause) {
        setChildParent(storeInClause);
        this.storeInClause = storeInClause;
    }

    public SQLSubpartitionTemplate getSubpartitionTemplate() {
        return subpartitionTemplate;
    }

    public void setSubpartitionTemplate(SQLSubpartitionTemplate subpartitionTemplate) {
        setChildParent(subpartitionTemplate);
        this.subpartitionTemplate = subpartitionTemplate;
    }
}
