package com.gumihoy.sql.basic.ast.expr.table.partition.sub;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * [SUBPARTITION BY
 * { [LINEAR] HASH(expr)
 * | [LINEAR] KEY [ALGORITHM={1|2}] (column_list) }
 * [SUBPARTITIONS num]
 * ]
 * https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 * <p>
 * <p>
 * SUBPARTITION BY RANGE ( column [, column]... ) [subpartition_template]
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html
 *
 * @author kent onCondition 2018/4/10.
 */
public class SQLSubPartitionByKey extends AbstractSQLSubPartitionBy {

    protected ISQLExpr algorithm;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, algorithm);
            this.acceptChild(visitor, columns);
            this.acceptChild(visitor, subpartitionsNum);
        }
    }

    @Override
    public AbstractSQLSubPartitionBy clone() {
        return super.clone();
    }

    public ISQLExpr getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(ISQLExpr algorithm) {
        setChildParent(algorithm);
        this.algorithm = algorithm;
    }
}
