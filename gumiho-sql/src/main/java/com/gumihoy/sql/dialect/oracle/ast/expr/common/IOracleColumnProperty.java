package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.common.ISQLColumnProperty;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionDefinition;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.ast.expr.IOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * { object_type_col_properties
 * | nested_table_col_properties
 * | { varray_col_properties | LOB_storage_clause }
 * [ (LOB_partition_storage [, LOB_partition_storage ]...) ]
 * | XMLType_column_properties
 * }...
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/28.
 */
public interface IOracleColumnProperty extends IOracleExpr, ISQLColumnProperty {

    @Override
    IOracleColumnProperty clone();


    /* LOB_storage_clause [ (LOB_partition_storage [, LOB_partition_storage ]...) ]
     *
     * https://docs.oracle.com/cd/B28359_01/server.111/b28286/statements_6002.htm#i2116664
     * @author kent on 2018/6/28.
     */
    public class OracleColumnPropertyLobStorageClause extends AbstractOracleExpr implements IOracleColumnProperty {

        protected OracleLobStorageClause lobStorageClause;

        protected final List<SQLPartitionDefinition> partitions = new ArrayList<>();

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, lobStorageClause);
//                this.acceptChild(visitor, lobPartitionStorages);
//            }
        }

        @Override
        public OracleColumnPropertyLobStorageClause clone() {
            return null;
        }

        public OracleLobStorageClause getLobStorageClause() {
            return lobStorageClause;
        }

        public void setLobStorageClause(OracleLobStorageClause lobStorageClause) {
            setChildParent(lobStorageClause);
            this.lobStorageClause = lobStorageClause;
        }

        public List<SQLPartitionDefinition> getPartitions() {
            return partitions;
        }

        public void addPartition(SQLPartitionDefinition partition) {
            if (partition == null) {
                return;
            }
            setChildParent(partition);
            this.partitions.add(partition);
        }
    }

}
