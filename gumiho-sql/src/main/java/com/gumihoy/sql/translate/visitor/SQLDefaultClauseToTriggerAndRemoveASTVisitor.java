package com.gumihoy.sql.translate.visitor;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLBody;
import com.gumihoy.sql.basic.ast.expr.common.SQLDefaultClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.literal.datetime.SQLDateLiteral;
import com.gumihoy.sql.basic.ast.expr.method.ISQLFunction;
import com.gumihoy.sql.basic.ast.expr.operator.SQLBinaryOperatorExpr;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.table.element.ISQLTableElement;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLColumnDefinition;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerDMLEvent;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLCreateTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLCreateTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLIfStatement;
import com.gumihoy.sql.basic.parser.SQLKeyWord;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * default xx => trigger
 * <p>
 *
 * @author kent on 2018/5/18.
 */
public class SQLDefaultClauseToTriggerAndRemoveASTVisitor extends SQLASTTransformVisitor {

    protected final ConcurrentHashMap<String, SQLSelectQuery> MAP = new ConcurrentHashMap<>();

    public SQLDefaultClauseToTriggerAndRemoveASTVisitor() {
    }

    public SQLDefaultClauseToTriggerAndRemoveASTVisitor(SQLTransformConfig config) {
        super(config);
    }

    @Override
    public boolean visit(SQLCreateTableStatement x) {
        ISQLName name = x.getName();

        List<SQLColumnDefinition> columns = new ArrayList<>();
        for (int i = x.getTableElements().size() - 1; i >= 0; i--) {
            ISQLTableElement element = x.getTableElements().get(i);
            if (!(element instanceof SQLColumnDefinition)) {
                continue;
            }
            SQLColumnDefinition column = (SQLColumnDefinition) element;
            if (column.getDefaultClause() == null
                    || !(column.getDefaultClause() instanceof SQLDefaultClause)) {
                continue;
            }

            ISQLExpr expr = ((SQLDefaultClause) column.getDefaultClause()).getExpr();

            if (expr instanceof ISQLFunction) {
                boolean support = false;
                if (((ISQLFunction) expr).getName() instanceof ISQLName) {
                    long methodNameLowerHash = ((ISQLName) ((ISQLFunction) expr).getName()).lowerHash();
                    support = methodNameLowerHash == SQLKeyWord.CURRENT_TIMESTAMP.lowerHash
                            || methodNameLowerHash == SQLKeyWord.CURRENT_DATE.lowerHash
                            || methodNameLowerHash == SQLKeyWord.LOCALTIMESTAMP.lowerHash;
                }
                if (!support) {
                    columns.add(column);
                }
            } else if (expr instanceof SQLBinaryOperatorExpr) {
                columns.add(column);
            } else if (expr instanceof SQLDateLiteral) {
                columns.add(column);
            }
        }

        SQLCreateTriggerStatement createTriggerStatement = createTriggerStatementAndRemove(name, columns, x.getDbType(), x.getTargetDBType());
        if (createTriggerStatement != null) {
            config.stmtList.add(config.index + 1, createTriggerStatement);
        }

        return false;
    }

    /**
     * CREATE TRIGGER before_insert_U3C_MG_BRANCHUPLOG
     * BEFORE INSERT ON `U3C_MG_BRANCHUPLOG`
     * FOR EACH ROW
     * BEGIN
     * IF new.`OPERATEDATE` IS NULL THEN
     * SET new.`OPERATEDATE` = sysdate();
     * END IF;
     * END
     */
    public SQLCreateTriggerStatement createTriggerStatementAndRemove(ISQLName name, List<SQLColumnDefinition> columns, DBType dbType, DBType targetDBType) {
        if (columns == null
                || columns.size() == 0) {
            return null;
        }


        SQLCreateTriggerStatement x = new SQLCreateTriggerStatement(dbType);
        x.setTargetDBType(targetDBType);

        String triggerName = "before_insert_" + name.getSimpleName();
        x.setName(triggerName);
        x.setActionTime(SQLCreateTriggerStatement.SQLTriggerActionTime.BEFORE);
        SQLTriggerDMLEvent event = new SQLTriggerDMLEvent(SQLTriggerDMLEvent.SQLTriggerDMLEventType.INSERT);
        x.addEvent(event);
        x.setOnExpr(name.getSimpleName());

        SQLBody body = new SQLBody();

        for (SQLColumnDefinition column : columns) {
            if (column.getDefaultClause() == null
                    || !(column.getDefaultClause() instanceof SQLDefaultClause)) {
                continue;
            }
            SQLDefaultClause defaultClause = (SQLDefaultClause) column.getDefaultClause();
            ISQLExpr expr = defaultClause.getExpr();

            SQLIfStatement ifStatement = new SQLIfStatement(dbType);

//            SQLIsNullCondition isNullCondition = new SQLIsNullCondition();
//            isNullCondition.setExpr(SQLNewVariableRefExpr.of(column.getColumnName()));

//            ifStatement.setCondition(isNullCondition);

//            SQLSetVariableStatement setVariableStatement = new SQLSetVariableStatement(dbType);
//            SQLNewVariableRefExpr setColumn = SQLNewVariableRefExpr.of(column.getColumnName());
//            SQLAssignmentExpr item = new SQLAssignmentExpr(setColumn, expr);
//            setVariableStatement.addItem(item);
//            setVariableStatement.setAfterSemi(true);

//            ifStatement.addStatement(setVariableStatement);

            // add trigger
//            body.addBodyItem(ifStatement);


            // remove
//            column.setDefaultExpr(null);
        }

        // add trigger body
        x.setTriggerBody(body);

        return x;
    }

}
