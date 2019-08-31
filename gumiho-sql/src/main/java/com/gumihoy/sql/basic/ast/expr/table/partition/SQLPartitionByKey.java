package com.gumihoy.sql.basic.ast.expr.table.partition;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * PARTITION BY LINEAR? KEY LEFT_PAREN expr RIGHT_PAREN (PARTITIONS partitionsNum=expr)? iSubPartitionBy?
 * (LEFT_PAREN partitionDefinition (COMMA partitionDefinition)* RIGHT_PAREN)?
 *
 * https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 * <p>
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html
 *
 * @author kent onCondition 2018/4/10.
 */
public class SQLPartitionByKey extends AbstractSQLPartitionBy {

    protected ISQLExpr algorithm;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, algorithm);
            this.acceptChild(visitor, columns);
            this.acceptChild(visitor, partitionsNum);
//            this.acceptChild(visitor, storeInClause);
            this.acceptChild(visitor, subPartitionBy);
            this.acceptChild(visitor, partitions);
        }
    }

    @Override
    public SQLPartitionByKey clone() {
        SQLPartitionByKey x = new SQLPartitionByKey();
        this.cloneTo(x);
        return x;
    }

    public ISQLExpr getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(ISQLExpr algorithm) {
        setChildParent(algorithm);
        this.algorithm = algorithm;
    }
}
