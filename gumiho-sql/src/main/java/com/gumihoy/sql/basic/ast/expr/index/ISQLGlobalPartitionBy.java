package com.gumihoy.sql.basic.ast.expr.index;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLStoreInClause;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionDefinition;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * GLOBAL PARTITION BY
 * { RANGE (column_list)
 * (index_partitioning_clause)
 * | HASH (column_list)
 * { individual_hash_partitions
 * | hash_partitions_by_quantity
 * }
 * }
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/global_partitioned_index.html
 *
 * @author kent on 2019-08-02.
 */
public interface ISQLGlobalPartitionBy extends ISQLExpr {

    @Override
    ISQLGlobalPartitionBy clone();

    public abstract class AbstractSQLGlobalPartitionBy extends AbstractSQLExpr implements ISQLGlobalPartitionBy {

        protected final List<ISQLExpr> columns = new ArrayList<>();
        protected ISQLExpr partitionsNum;
        protected SQLStoreInClause storeInClause;
        //    protected ISQLCompression compression;
//    protected SQLOverflowStoreInClause overflowStoreInClause;
        protected final List<SQLPartitionDefinition> partitions = new ArrayList<>();

        @Override
        public AbstractSQLGlobalPartitionBy clone() {
            throw new UnsupportedOperationException();
        }


    }

    public class SQLGlobalPartitionByRange extends AbstractSQLGlobalPartitionBy {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }
    }

    public class SQLGlobalPartitionByHash extends AbstractSQLGlobalPartitionBy {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }
    }
}
