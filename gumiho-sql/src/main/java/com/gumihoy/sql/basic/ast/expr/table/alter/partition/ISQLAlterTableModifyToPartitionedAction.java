package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.method.aggreate.AbstractSQLAggregateFunction;
import com.gumihoy.sql.basic.ast.expr.table.SQLFilterCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * MODIFY table_partitioning_clauses
 * [ filter_condition ]
 * [ ONLINE ]
 * [ UPDATE INDEXES [ ( index { local_partitioned_index | global_partitioned_index | GLOBAL }
 * [, index { local_partitioned_index | global_partitioned_index | GLOBAL } ]... )
 * ]
 * ]
 * <p>
 * modify_to_partitioned
 *
 * @author kent on 2019-08-02.
 */
public interface ISQLAlterTableModifyToPartitionedAction extends ISQLAlterTablePartitionAction {
    @Override
    ISQLAlterTableModifyToPartitionedAction clone();


    public abstract class AbstractSQLAlterTableModifyToPartitionedAction extends AbstractSQLExpr implements ISQLAlterTableModifyToPartitionedAction {

        protected SQLFilterCondition filterCondition;
        protected boolean online;
        protected final List<ISQLExpr> updateIndexs = new ArrayList<>();

        @Override
        public AbstractSQLAlterTableModifyToPartitionedAction clone() {
            throw new UnsupportedOperationException();
        }


    }






}
