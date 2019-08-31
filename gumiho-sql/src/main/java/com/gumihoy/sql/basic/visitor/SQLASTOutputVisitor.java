package com.gumihoy.sql.basic.visitor;

import static com.gumihoy.sql.basic.parser.SQLKeyWord.ELSE;
import static com.gumihoy.sql.basic.parser.SQLKeyWord.REFERENCING;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.enums.SQLBinaryOperator;
import com.gumihoy.sql.basic.ast.enums.SQLCharSizeUnit;
import com.gumihoy.sql.basic.ast.enums.SQLEditionAbleType;
import com.gumihoy.sql.basic.ast.enums.SQLIntervalUnit;
import com.gumihoy.sql.basic.ast.enums.SQLSetQuantifier;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.comment.SQLMinusCommentExpr;
import com.gumihoy.sql.basic.ast.expr.comment.SQLMultiLineCommentExpr;
import com.gumihoy.sql.basic.ast.expr.comment.SQLSharpCommentExpr;
import com.gumihoy.sql.basic.ast.expr.comment.hint.SQLMinusHintExpr;
import com.gumihoy.sql.basic.ast.expr.comment.hint.SQLMultiLineHintExpr;
import com.gumihoy.sql.basic.ast.expr.common.*;
import com.gumihoy.sql.basic.ast.expr.condition.*;
import com.gumihoy.sql.basic.ast.expr.correlation.SQLCorrelationNewName;
import com.gumihoy.sql.basic.ast.expr.correlation.SQLCorrelationOldName;
import com.gumihoy.sql.basic.ast.expr.correlation.SQLCorrelationParentName;
import com.gumihoy.sql.basic.ast.expr.database.SQLUpgradeDataDirectoryNameExpr;
import com.gumihoy.sql.basic.ast.expr.database.create.*;
import com.gumihoy.sql.basic.ast.expr.databaselink.SQLConnectToCurrentUserClause;
import com.gumihoy.sql.basic.ast.expr.databaselink.SQLConnectToIdentifiedByClause;
import com.gumihoy.sql.basic.ast.expr.databaselink.SQLDBLinkAuthenticationClause;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.SQLDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.any.SQLAnyDataDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.any.SQLAnyDataSetDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.any.SQLAnyTypeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.bool.SQLBoolDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.bool.SQLBooleanDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLDateDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLDateTimeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLTimeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLTimestampDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLYearDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.interval.SQLIntervalDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.media.*;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.*;
import com.gumihoy.sql.basic.ast.expr.datatype.percent.SQLPercentRowTypeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.percent.SQLPercentTypeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.reference.SQLRefCursorDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.reference.SQLRefDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.spatial.*;
import com.gumihoy.sql.basic.ast.expr.datatype.string.*;
import com.gumihoy.sql.basic.ast.expr.datatype.xml.SQLUriTypeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.xml.SQLXmlTypeDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.*;
import com.gumihoy.sql.basic.ast.expr.index.alter.partition.*;
import com.gumihoy.sql.basic.ast.expr.literal.SQLBooleanLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.datetime.SQLDateLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.datetime.SQLTimeLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.datetime.SQLTimestampLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.interval.SQLIntervalLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLBinaryDoubleLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLBinaryFloatLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLBitValueLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLDecimalLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLFloatingPointLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLHexaDecimalLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLIntegerLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.string.SQLNQStringLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.string.SQLNStringLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.string.SQLQStringLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.string.SQLStringLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.string.SQLUStringLiteral;
import com.gumihoy.sql.basic.ast.expr.method.*;
import com.gumihoy.sql.basic.ast.expr.method.aggreate.SQLAggregateFunction;
import com.gumihoy.sql.basic.ast.expr.method.datamining.SQLDataMiningFunction;
import com.gumihoy.sql.basic.ast.expr.method.json.SQLJsonFunction;
import com.gumihoy.sql.basic.ast.expr.method.window.SQLWindowFunction;
import com.gumihoy.sql.basic.ast.expr.method.xml.SQLXmlCastFunction;
import com.gumihoy.sql.basic.ast.expr.method.xml.SQLXmlColAttValFunction;
import com.gumihoy.sql.basic.ast.expr.method.xml.SQLXmlElementFunction;
import com.gumihoy.sql.basic.ast.expr.operator.SQLBinaryOperatorExpr;
import com.gumihoy.sql.basic.ast.expr.operator.SQLUnaryOperatorExpr;
import com.gumihoy.sql.basic.ast.expr.package_.alter.ISQLAlterPackageAction;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLConnectByIsCycleExpr;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLConnectByIsLeafExpr;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLLevelExpr;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLRowIdExpr;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLRowNumExpr;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLSequenceExpr;
import com.gumihoy.sql.basic.ast.expr.pseudorecords.SQLPseudoNewRecordName;
import com.gumihoy.sql.basic.ast.expr.pseudorecords.SQLPseudoOldRecordName;
import com.gumihoy.sql.basic.ast.expr.pseudorecords.SQLPseudoParentRecordName;
import com.gumihoy.sql.basic.ast.expr.role.AbstractSQLRoleAction;
import com.gumihoy.sql.basic.ast.expr.role.SQLRoleIdentifiedByAction;
import com.gumihoy.sql.basic.ast.expr.role.SQLRoleIdentifiedExternallyAction;
import com.gumihoy.sql.basic.ast.expr.role.SQLRoleIdentifiedGloballyAction;
import com.gumihoy.sql.basic.ast.expr.role.SQLRoleIdentifiedUsingAction;
import com.gumihoy.sql.basic.ast.expr.role.SQLRoleNotIdentifiedAction;
import com.gumihoy.sql.basic.ast.expr.select.*;
import com.gumihoy.sql.basic.ast.expr.select.group.SQLGroupByClause;
import com.gumihoy.sql.basic.ast.expr.select.group.SQLHavingClause;
import com.gumihoy.sql.basic.ast.expr.select.limit.ISQLLimitClause;
import com.gumihoy.sql.basic.ast.expr.select.limit.SQLLimitOffsetClause;
import com.gumihoy.sql.basic.ast.expr.select.limit.SQLOffsetFetchClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByItem;
import com.gumihoy.sql.basic.ast.expr.sequence.*;
import com.gumihoy.sql.basic.ast.expr.synonym.alter.ISQLAlterSynonymAction;
import com.gumihoy.sql.basic.ast.expr.table.ISQLEnableDisableClause;
import com.gumihoy.sql.basic.ast.expr.table.ISQLIdentityOption;
import com.gumihoy.sql.basic.ast.expr.table.alter.SQLAlterTableMoveTableAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.SQLAlterTableRenameAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.column.*;
import com.gumihoy.sql.basic.ast.expr.table.alter.constraint.*;
import com.gumihoy.sql.basic.ast.expr.table.alter.partition.*;
import com.gumihoy.sql.basic.ast.expr.table.alter.trigger.SQLAlterTableDisableTriggerAction;
import com.gumihoy.sql.basic.ast.expr.table.alter.trigger.SQLAlterTableEnableTriggerAction;
import com.gumihoy.sql.basic.ast.expr.table.element.AbstractSQLReferencesConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.ISQLTableElement;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLColumnDefinition;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLLikeClause;
import com.gumihoy.sql.basic.ast.expr.table.element.SQLVirtualColumnDefinition;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.ISQLConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.*;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.*;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLCheckTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLForeignKeyTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLPrimaryKeyTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLRefWithRowIdTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLScopeForTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLUniqueTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.partition.*;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionByHash;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionByKey;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionByList;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionByRange;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionDefinition;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubpartitionTemplate;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValues;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValuesIn;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValuesLessThan;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.SQLPartitionValuesLessThanMaxValue;
import com.gumihoy.sql.basic.ast.expr.table.partitionset.SQLPartitionsetByList;
import com.gumihoy.sql.basic.ast.expr.table.partitionset.SQLPartitionsetByRange;
import com.gumihoy.sql.basic.ast.expr.table.partitionset.SQLPartitionsetDefinition;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerCompoundTriggerBlock;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerDDLEvent;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerDMLEvent;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerDatabaseEvent;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerReferencingClause;
import com.gumihoy.sql.basic.ast.expr.trigger.alter.ISQLAlterTriggerAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.AbstractSQLAlterTypeAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.ISQLAlterTypeAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.SQLAlterTypeAddAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.SQLAlterTypeDropAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.SQLAlterTypeModifyAction;
import com.gumihoy.sql.basic.ast.expr.type.alter.SQLAlterTypeReplaceAction;
import com.gumihoy.sql.basic.ast.expr.view.alter.*;
import com.gumihoy.sql.basic.ast.statement.ddl.comment.*;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLAlterDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLCreateDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLDropDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.databaselink.SQLAlterDatabaseLinkStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.databaselink.SQLCreateDatabaseLinkStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.databaselink.SQLDropDatabaseLinkStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.domain.SQLAlterDomainStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.domain.SQLCreateDomainStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.domain.SQLDropDomainStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.event.SQLAlterEventStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.event.SQLCreateEventStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.event.SQLDropEventStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLAlterFunctionStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLCreateFunctionStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.function.SQLDropFunctionStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLAlterIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLCreateIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLDropIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.materializedview.SQLAlterMaterializedViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.materializedview.SQLCreateMaterializedViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.materializedview.SQLDropMaterializedViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.package_.SQLAlterPackageStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.package_.SQLCreatePackageStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.package_.SQLDropPackageStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.packagebody.SQLAlterPackageBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.packagebody.SQLCreatePackageBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.packagebody.SQLDropPackageBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.procedure.SQLAlterProcedureStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.procedure.SQLCreateProcedureStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.procedure.SQLDropProcedureStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.role.SQLAlterRoleStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.role.SQLCreateRoleStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.role.SQLDropRoleStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.schema.SQLAlterSchemaStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.schema.SQLCreateSchemaStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.schema.SQLDropSchemaStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.sequence.SQLAlterSequenceStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.sequence.SQLCreateSequenceStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.sequence.SQLDropSequenceStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.synonym.SQLAlterSynonymStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.synonym.SQLCreateSynonymStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.synonym.SQLDropSynonymStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLAlterTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLCreateTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLDropTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLRenameTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLTruncateTableStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLAlterTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLCreateTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLDropTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.type.SQLAlterTypeStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.type.SQLCreateTypeStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.type.SQLDropTypeStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.typebody.SQLAlterTypeBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.typebody.SQLCreateTypeBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.typebody.SQLDropTypeBodyStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.user.SQLAlterUserStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.user.SQLCreateUserStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.user.SQLDropUserStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLAlterViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLCreateViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLDropViewStatement;
import com.gumihoy.sql.basic.ast.statement.dml.*;
import com.gumihoy.sql.basic.ast.statement.fcl.*;
import com.gumihoy.sql.basic.parser.SQLKeyWord;
import com.gumihoy.sql.config.SQLOutputConfig;
import com.gumihoy.sql.print.SQLPrintable;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kent on 2019-06-14.
 */
public class SQLASTOutputVisitor extends SQLASTVisitorAdapter implements SQLPrintable {

    private final StringBuilder appender;
    public int indentCount = 0;
    public int lines = 0;
    protected SQLOutputConfig config;


    public SQLASTOutputVisitor(StringBuilder appender) {
        this(appender, null);
    }

    public SQLASTOutputVisitor(StringBuilder appender, SQLOutputConfig config) {
        this.appender = appender;

        if (config == null) {
            config = new SQLOutputConfig();
        }
        this.config = config;
    }

    @Override
    public boolean isLowerCase() {
        return config.lowerCase;
    }

    public void setLowerCase(boolean lowerCase) {
        config.lowerCase = lowerCase;
    }

    @Override
    public boolean isPrintAfterSemi() {
        return config.printAfterSemi;
    }

    public void setPrintAfterSemi(Boolean printAfterSemi) {
        config.printAfterSemi = printAfterSemi;
    }

    @Override
    public boolean isKeepComment() {
        return config.keepComment;
    }

    public int getIndentCount() {
        return indentCount;
    }

    public void incrementIndent() {
        indentCount++;
    }

    public void decrementIndent() {
        indentCount--;
    }

    public int getLines() {
        return lines;
    }

    public void incrementLines() {
        lines++;
    }

    public int getLineMaxLength() {
        return config.lineMaxLength;
    }

    public void setLineMaxLength(int lineMaxLength) {
        config.lineMaxLength = lineMaxLength;
    }

    @Override
    public void print(char value) {
        appender.append(value);
    }

    @Override
    public void print(int value) {
        appender.append(value);
    }

    @Override
    public void print(long value) {
        appender.append(value);
    }

    @Override
    public void print(float value) {
        appender.append(value);
    }

    @Override
    public void print(double value) {
        appender.append(value);
    }

    @Override
    public void print(CharSequence value) {
        appender.append(value);
    }

    @Override
    public void print(boolean isLowerCase, CharSequence lower, CharSequence upper) {
        appender.append(isLowerCase ? lower : upper);
    }

    @Override
    public void print(SQLKeyWord value) {
        if (value == null) {
            return;
        }
        print(isLowerCase() ? value.lower : value.upper);
    }

    @Override
    public void print(ISQLASTEnum value) {
        if (value == null) {
            return;
        }
        print(isLowerCase() ? value.lower() : value.upper());
    }

    @Override
    public void printAccept(ISQLObject value) {
        if (value == null) {
            return;
        }
        value.accept(this);
    }

    @Override
    public void print(List<? extends ISQLASTEnum> values, String separator) {
        if (values == null
                || values.size() == 0) {
            return;
        }

        for (int i = 0, size = values.size(); i < size; ++i) {
            if (i != 0) {
                print(separator);
            }
            print(values.get(i));
        }
    }

    @Override
    public void printAccept(List<? extends ISQLObject> values, String separator) {
        printAccept(values, separator, false);
    }

    @Override
    public void printAccept(List<? extends ISQLObject> values, String separator, boolean paren) {
        if (values == null
                || values.size() == 0) {
            return;
        }

        if (paren) {
            print('(');
        }

        for (int i = 0, size = values.size(); i < size; ++i) {
            if (i != 0) {
                print(separator);
            }
            values.get(i).accept(this);
        }

        if (paren) {
            print(')');
        }
    }

    @Override
    public void printSpace() {
        appender.append(' ');
    }

    @Override
    public void printSpaceAfterValue(char value) {
        printSpace();
        print(value);
    }

    @Override
    public void printSpaceAfterValue(CharSequence value) {
        if (value == null) {
            return;
        }
        printSpace();
        print(value);
    }

    @Override
    public void printSpaceAfterValue(SQLKeyWord value) {
        if (value == null) {
            return;
        }
        printSpace();
        print(value);
    }

    @Override
    public void printSpaceAfterValue(ISQLASTEnum value) {
        if (value == null) {
            return;
        }
        printSpace();
        print(value);
    }

    @Override
    public void printSpaceAfterAccept(ISQLObject value) {
        if (value == null) {
            return;
        }
        printSpace();
        printAccept(value);
    }

    @Override
    public void printSpaceAfterValue(List<? extends ISQLASTEnum> values, String separator) {
        if (values == null
                || values.size() == 0) {
            return;
        }
        printSpace();
        print(values, separator);
    }

    @Override
    public void printSpaceAfterAccept(List<? extends ISQLObject> values, String separator) {
        printSpaceAfterAccept(values, separator, false);
    }

    @Override
    public void printSpaceAfterAccept(List<? extends ISQLObject> values, String separator, boolean paren) {
        if (values == null
                || values.size() == 0) {
            return;
        }
        printSpace();
        printAccept(values, separator, paren);
    }

    @Override
    public void printSpaceAndLnAfterValue(List<? extends ISQLASTEnum> values, String separator, boolean paren) {
        if (values == null
                || values.size() == 0) {
            return;
        }
        printSpace();
        printlnAfterValue(values, separator, paren);
    }

    @Override
    public void printSpaceAndLnAndAccept(List<? extends ISQLObject> values, String separator, boolean paren) {
        if (values == null
                || values.size() == 0) {
            return;
        }
        printSpace();
        printlnAndAccept(values, separator, paren);
    }

    @Override
    public void println() {
        this.appender.append("\n");
        this.incrementLines();
        printIndent();
    }

    @Override
    public void printlnAfterValue(CharSequence value) {
        if (value == null) {
            return;
        }
        println();
        print(value);
    }

    @Override
    public void printlnAfterValue(SQLKeyWord value) {
        if (value == null) {
            return;
        }
        println();
        print(value);
    }

    @Override
    public void printlnAfterValue(ISQLASTEnum value) {
        if (value == null) {
            return;
        }
        println();
        print(value);
    }

    @Override
    public void printlnAfterValue(List<? extends ISQLASTEnum> values) {
        if (values == null
                || values.size() == 0) {
            return;
        }
        for (int i = 0, size = values.size(); i < size; ++i) {
            if (i != 0) {
                println();
            }
            print(values.get(i));
        }
    }

    @Override
    public void printlnAfterValue(List<? extends ISQLASTEnum> values, String separator, boolean paren) {
        if (values == null
                || values.size() == 0) {
            return;
        }

        if (paren) {
            print('(');
            this.incrementIndent();
            println();
        }

        for (int i = 0, size = values.size(); i < size; ++i) {
            if (i != 0) {
                print(separator);
                println();
            }
            print(values.get(i));
        }

        if (paren) {
            this.decrementIndent();
            println();
            print(')');
        }
    }

    @Override
    public void printlnAndAccept(ISQLObject value) {
        if (value == null) {
            return;
        }
        println();
        value.accept(this);
    }

    @Override
    public void printlnAndAccept(List<? extends ISQLObject> values) {
        if (values == null
                || values.size() == 0) {
            return;
        }

        for (int i = 0, size = values.size(); i < size; ++i) {
            if (i != 0) {
                println();
            }
            values.get(i).accept(this);
        }
    }

    @Override
    public void printlnAndAccept(List<? extends ISQLObject> values, String separator) {
        if (values == null
                || values.size() == 0) {
            return;
        }
        printlnAndAccept(values, separator, false);
    }

    @Override
    public void printlnAndAccept(List<? extends ISQLObject> values, String separator, boolean paren) {
        if (values == null
                || values.size() == 0) {
            return;
        }

        if (paren) {
            print('(');
            this.incrementIndent();
            println();
        }

        for (int i = 0, size = values.size(); i < size; ++i) {
            if (i != 0) {
                print(separator);
                println();
            }
            values.get(i).accept(this);
        }

        if (paren) {
            this.decrementIndent();
            println();
            print(')');
        }
    }

    @Override
    public void printIndent() {
        for (int i = 0; i < this.getIndentCount(); ++i) {
            appender.append('\t');
        }
    }

    @Override
    public void printIndentLnAfterValue(CharSequence value) {
        incrementIndent();
        printlnAfterValue(value);
        decrementIndent();
    }

    @Override
    public void printIndentLnAfterValue(SQLKeyWord value) {
        incrementIndent();
        printlnAfterValue(value);
        decrementIndent();
    }

    @Override
    public void printIndentLnAfterValue(ISQLASTEnum value) {
        incrementIndent();
        printlnAfterValue(value);
        decrementIndent();
    }

    @Override
    public void printIndentLnAfterValue(List<? extends ISQLASTEnum> values) {

    }

    @Override
    public void printIndentLnAfterValue(List<? extends ISQLASTEnum> values, String separator, boolean paren) {

    }

    @Override
    public void printIndentLnAccept(ISQLObject value) {
        if (value == null) {
            return;
        }
        this.incrementIndent();
        printlnAndAccept(value);
        this.decrementIndent();
    }

    @Override
    public void printIndentLnAccept(List<? extends ISQLObject> values) {
        if (values == null
                || values.size() == 0) {
            return;
        }
        this.incrementIndent();
        println();
        printlnAndAccept(values);
        this.decrementIndent();
    }

    @Override
    public void printIndentLnAccept(List<? extends ISQLObject> values, String separator) {
        if (values == null
                || values.size() == 0) {
            return;
        }
        this.incrementIndent();
        println();
        printlnAndAccept(values, separator);
        this.decrementIndent();
    }

    @Override
    public void printIndentLnAccept(List<? extends ISQLObject> values, String separator, boolean paren) {
        if (values == null
                || values.size() == 0) {
            return;
        }
        if (paren) {
            print('(');
        }
        this.incrementIndent();
        println();
        printlnAndAccept(values, separator);
        this.decrementIndent();
        if (paren) {
            println();
            print(')');
        }
    }

    @Override
    public void preVisit(ISQLObject x) {
        if (x.getBeforeComments().size() > 0) {

            printlnAndAccept(x.getBeforeComments());
            println();
        }
    }

    @Override
    public void postVisit(ISQLObject x) {
        boolean printSemi = isPrintAfterSemi() | x.isAfterSemi();
        if (printSemi) {
            print(';');
        }

        if (x.getAfterComments().size() > 0) {
            printlnAndAccept(x.getAfterComments());
            println();
        }
    }


    // ---------------------------- Comment Start ------------------------------------

    @Override
    public boolean visit(SQLMultiLineCommentExpr x) {
        print(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLMinusCommentExpr x) {
        print(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLSharpCommentExpr x) {
        print(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLMultiLineHintExpr x) {
        return false;
    }

    @Override
    public boolean visit(SQLMinusHintExpr x) {
        return false;
    }

    // ---------------------------- Comment End ------------------------------------


    // ---------------------------- Literal Start ------------------------------------

    @Override
    public boolean visit(SQLNQStringLiteral x) {
        print(SQLKeyWord.N);
        print(SQLKeyWord.Q);
        print('\'');
        print(x.getValue());
        print('\'');
        return false;
    }

    @Override
    public boolean visit(SQLNStringLiteral x) {
        print(SQLKeyWord.N);
        print('\'');
        print(x.getValue());
        print('\'');
        return false;
    }

    @Override
    public boolean visit(SQLQStringLiteral x) {
        print(SQLKeyWord.Q);
        print('\'');
        print(x.getValue());
        print("'");
        return false;
    }

    @Override
    public boolean visit(SQLStringLiteral x) {
        print('\'');
        print(x.getValue());
        print('\'');
        return false;
    }

    @Override
    public boolean visit(SQLUStringLiteral x) {
        print("U\'");
        print(x.getValue());
        print('\'');
        return false;
    }

    @Override
    public boolean visit(SQLBinaryDoubleLiteral x) {
        print(x.getValue());
        print(SQLKeyWord.D);
        return false;
    }

    @Override
    public boolean visit(SQLBinaryFloatLiteral x) {
        print(x.getValue());
        print(SQLKeyWord.F);
        return false;
    }

    @Override
    public boolean visit(SQLDecimalLiteral x) {
        print(x.getSource());
        return false;
    }

    @Override
    public boolean visit(SQLFloatingPointLiteral x) {
        print(x.getSource());
        return false;
    }

    @Override
    public boolean visit(SQLIntegerLiteral x) {
        print(x.getSource());
        return false;
    }

    @Override
    public boolean visit(SQLBitValueLiteral x) {
        SQLBitValueLiteral.SQLPrefix prefix = x.getPrefix();
        print(x.getPrefix());

        if (prefix == SQLBitValueLiteral.SQLPrefix.B) {
            print('\'');
        }
        print(x.getValue());
        if (prefix == SQLBitValueLiteral.SQLPrefix.B) {
            print('\'');
        }
        return false;
    }

    @Override
    public boolean visit(SQLHexaDecimalLiteral x) {
        SQLHexaDecimalLiteral.SQLPrefix prefix = x.getPrefix();
        print(prefix);

        if (prefix == SQLHexaDecimalLiteral.SQLPrefix.X) {
            print('\'');
        }
        print(x.getValue());
        if (prefix == SQLHexaDecimalLiteral.SQLPrefix.X) {
            print('\'');
        }
        return false;
    }

    @Override
    public boolean visit(SQLDateLiteral x) {
        print(SQLKeyWord.DATE);

        printSpace();
        x.getValue().accept(this);
        return false;
    }

    @Override
    public boolean visit(SQLTimeLiteral x) {
        print(SQLKeyWord.TIME);

        printSpace();
        x.getValue().accept(this);
        return false;
    }

    @Override
    public boolean visit(SQLTimestampLiteral x) {
        print(SQLKeyWord.TIMESTAMP);
        printSpaceAfterAccept(x.getValue());

        if (x.getAtTimeZone() != null) {
            printSpaceAfterValue(isLowerCase() ? "at time zone" : "AT TIME ZONE");
            printSpaceAfterAccept(x.getAtTimeZone());
        }
        return false;
    }

    @Override
    public boolean visit(SQLIntervalLiteral x) {
        print(SQLKeyWord.INTERVAL);

        printSpaceAfterAccept(x.getValue());

        SQLIntervalUnit unit = x.getUnit();
        if (unit != null) {
            printSpaceAfterValue(unit);
            printAccept(x.getPrecisions(), ", ", true);
        }

        SQLIntervalUnit toUnit = x.getToUnit();
        if (toUnit != null) {
            printSpaceAfterValue(SQLKeyWord.TO);
            printSpaceAfterValue(toUnit);
            printAccept(x.getToPrecisions(), ", ", true);
        }

        return false;
    }

    @Override
    public boolean visit(SQLBooleanLiteral x) {
        Boolean value = x.getValue();
        if (value == null) {
            print(SQLKeyWord.UNKNOWN);
            return false;
        }

        if (value) {
            print(SQLKeyWord.TRUE);
        } else {
            print(SQLKeyWord.FALSE);
        }

        return false;
    }

    // ---------------------------- Literal End ------------------------------------


    // ---------------------------- Identifier Start ------------------------------------

    @Override
    public boolean visit(SQLUnquotedIdentifier x) {
        print(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLDoubleQuotedIdentifier x) {
        print('"');
        print(x.getName());
        print('"');
        return false;
    }

    @Override
    public boolean visit(SQLReverseQuotedIdentifier x) {
        print('`');
        print(x.getName());
        print('`');
        return false;
    }

    @Override
    public boolean visit(SQLPropertyExpr x) {
        printAccept(x.getOwner());
        print('.');
        printAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLAllColumnExpr x) {
        print(SQLKeyWord.STAR);
        return false;
    }

    @Override
    public boolean visit(SQLDBLinkExpr x) {
        x.getName().accept(this);
        print('@');
        x.getDbLink().accept(this);
        return false;
    }

    // ---------------------------- Identifier End ------------------------------------


    // ---------------------------- Correlation Start ------------------------------------

    @Override
    public boolean visit(SQLCorrelationNewName x) {
        print(SQLKeyWord.COL_NEW);
        print('.');
        printAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLCorrelationOldName x) {
        print(SQLKeyWord.COL_OLD);
        print('.');
        printAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLCorrelationParentName x) {
        print(SQLKeyWord.COL_PARENT);
        print('.');
        printAccept(x.getName());
        return false;
    }

    // ---------------------------- Correlation End ------------------------------------

    // ---------------------------- PseudoRecord Start ------------------------------------

    @Override
    public boolean visit(SQLPseudoNewRecordName x) {
        print(SQLKeyWord.NEW);
        print('.');
        printAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLPseudoOldRecordName x) {
        print(SQLKeyWord.OLD);
        print('.');
        printAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLPseudoParentRecordName x) {
        print(SQLKeyWord.PARENT);
        print('.');
        printAccept(x.getName());
        return false;
    }

    // ---------------------------- PseudoRecord End ------------------------------------


    // ---------------------------- Common Start ------------------------------------
    @Override
    public boolean visit(SQLVariableExpr x) {
        print(SQLKeyWord.QUES);
        return false;
    }

    @Override
    public boolean visit(SQLBindVariableExpr x) {
        print(SQLKeyWord.COLON);
        printAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLPlaceholderExpr x) {
        printAccept(x.getHostExpr());

        if (x.isIndicator()) {
            printSpaceAfterValue(SQLKeyWord.INDICATOR);
        }
        printSpaceAfterAccept(x.getIndicatorExpr());
        return false;
    }

    @Override
    public boolean visit(SQLWithClause x) {
        print(SQLKeyWord.WITH);

        if (x.isRecursive()) {
            printSpace();
            print(SQLKeyWord.RECURSIVE);
        }

        List<SQLWithClause.SQLWithElement> withElements = x.getWithElements();
        if (withElements.size() > 0) {
            printSpace();
            printAccept(withElements, ", ");
        }

        return false;
    }

    @Override
    public boolean visit(SQLWithClause.SQLSubQueryFactoringClause x) {
        printAccept(x.getQueryName());

        printSpaceAfterAccept(x.getColumns(), ", ", true);

        printSpaceAfterValue(SQLKeyWord.AS);

        printSpaceAfterValue('(');

        this.incrementIndent();
        println();

        x.getStatement().accept(this);

        this.decrementIndent();
        println();
        print(')');

        printlnAndAccept(x.getSearchClause());
        printlnAndAccept(x.getCycleClause());

        return false;
    }

    @Override
    public boolean visit(SQLWithClause.SQLSearchClause x) {
        print(SQLKeyWord.SEARCH);

//        printSpaceAfterValue(x.getType());
        printSpaceAfterValue(SQLKeyWord.FIRST);
        printSpaceAfterValue(SQLKeyWord.BY);

        printSpaceAfterAccept(x.getOrderByItems(), ", ");


        printSpaceAfterValue(SQLKeyWord.SET);
        printSpace();
        x.getColumn().accept(this);

        return false;
    }

    @Override
    public boolean visit(SQLWithClause.SQLCycleClause x) {
        print(SQLKeyWord.CYCLE);

        printSpaceAfterAccept(x.getCycleColumns(), ", ");

        printSpaceAfterValue(SQLKeyWord.SET);
        printSpace();
        x.getCycleMarkColumn().accept(this);

        printSpaceAfterValue(SQLKeyWord.TO);
        printSpace();
        x.getCycleMarkValue().accept(this);

        printSpaceAfterValue(SQLKeyWord.DEFAULT);
        printSpace();
        x.getNonCycleMarkValue().accept(this);

        return false;
    }

    @Override
    public boolean visit(SQLFromClause x) {
        print(SQLKeyWord.FROM);
        printSpaceAfterAccept(x.getTableReference());
        return false;
    }

    @Override
    public boolean visit(SQLWhereClause x) {
        ISQLExpr condition = x.getCondition();
        if (condition == null) {
            return false;
        }

        print(SQLKeyWord.WHERE);

        printSpace();
        printWhereCondition(condition);
        return false;
    }

    @Override
    public boolean visit(SQLWindowClause x) {
        print(SQLKeyWord.WINDOW);
        printSpaceAfterAccept(x.getItems(), ", ");
        return false;
    }

    @Override
    public boolean visit(SQLWindowClause.SQLWindowClauseItem x) {
        printAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.AS);
        printSpaceAfterValue('(');
        printAccept(x.getSpecification());
        print(')');
        return false;
    }

    @Override
    public boolean visit(SQLPartitionClause x) {
        print(SQLKeyWord.PARTITION);
        printSpaceAfterValue(SQLKeyWord.BY);
        printSpaceAfterAccept(x.getItems(), ", ");
        return false;
    }

    @Override
    public boolean visit(SQLWindowSpecification x) {
        boolean print = false;

        ISQLName name = x.getName();
        if (name != null) {
            name.accept(this);
            print = true;
        }

        SQLPartitionClause partitionClause = x.getPartitionClause();
        if (partitionClause != null) {
            if (print) {
                printSpace();
            }
            partitionClause.accept(this);
            print = true;
        }

        SQLOrderByClause orderByClause = x.getOrderByClause();
        if (orderByClause != null) {
            if (print) {
                printSpace();
            }
            orderByClause.accept(this);
            print = true;
        }

        SQLWindowFrameClause windowFrameClause = x.getWindowFrameClause();
        if (windowFrameClause != null) {
            if (print) {
                printSpace();
            }
            windowFrameClause.accept(this);
            print = true;
        }

        return false;
    }

    @Override
    public boolean visit(SQLWindowFrameClause x) {
        print(x.getUnit());

        if (x.isBetween()) {
            printSpaceAfterValue(SQLKeyWord.BETWEEN);
        }

        printSpaceAfterAccept(x.getStart());

        if (x.isBetween()) {
            printSpaceAfterValue(SQLKeyWord.AND);
            printSpaceAfterAccept(x.getEnd());
        }

        return false;
    }

    @Override
    public boolean visit(SQLWindowFrameClause.SQLCurrentRow x) {
        print(isLowerCase() ? "current row" : "CURRENT ROW");
        return false;
    }

    @Override
    public boolean visit(SQLWindowFrameClause.SQLWindowFramePreceding x) {
        printAccept(x.getValue());
        printSpaceAfterValue(SQLKeyWord.PRECEDING);
        return false;
    }

    @Override
    public boolean visit(SQLWindowFrameClause.SQLWindowFrameFollowing x) {
        printAccept(x.getValue());
        printSpaceAfterValue(SQLKeyWord.FOLLOWING);
        return false;
    }

    @Override
    public boolean visit(SQLWindowFrameClause.SQLWindowFrameBetween x) {
        return false;
    }

    public void printWhereCondition(ISQLExpr x) {
        if (x == null) {
            return;
        }

        ISQLExpr left = x;
        List<Pair<SQLBinaryOperator, ISQLExpr>> groupList = new ArrayList<>();
        for (; ; ) {
            if (left instanceof SQLBinaryOperatorExpr
                    && ((SQLBinaryOperatorExpr) left).getOperator().isLogical()) {

                Pair<SQLBinaryOperator, ISQLExpr> pair = new Pair<>(((SQLBinaryOperatorExpr) left).getOperator(), ((SQLBinaryOperatorExpr) left).getRight());
                groupList.add(pair);

                left = ((SQLBinaryOperatorExpr) left).getLeft();
                continue;
            } else {
                Pair<SQLBinaryOperator, ISQLExpr> pair = new Pair<>(null, left);
                groupList.add(pair);
            }
            break;
        }


        int lineLength = 0;
        this.incrementIndent();
        for (int i = groupList.size() - 1; i >= 0; i--) {
            Pair<SQLBinaryOperator, ISQLExpr> pair = groupList.get(i);
            SQLBinaryOperator operator = pair.getKey();
            ISQLExpr expr = pair.getValue();
            int conditionLength = expr.toString().length();
            lineLength += conditionLength;
            if (i != groupList.size() - 1) {
                if (lineLength > getLineMaxLength()) {
                    println();
                    lineLength = conditionLength;
                } else {
                    printSpace();
                }
            }

            if (operator != null) {
                print(operator);
                printSpace();
            }

            expr.accept(this);
        }

        this.decrementIndent();

    }

    @Override
    public boolean visit(SQLCaseExpr x) {
        print(SQLKeyWord.CASE);

        printSpaceAfterAccept(x.getExpr());

        this.incrementIndent();
        println();
        printlnAndAccept(x.getWhenItems());

        SQLCaseExpr.SQLCaseExprElseClause elseClause = x.getElseClause();
        if (elseClause != null) {
            printlnAndAccept(elseClause);
        }

        this.decrementIndent();
        println();

        print(SQLKeyWord.END);
        return false;
    }

    @Override
    public boolean visit(SQLCaseExpr.SQLCaseExprWhenItem x) {
        print(SQLKeyWord.WHEN);
        printSpaceAfterAccept(x.getWhen());

        printSpaceAfterValue(SQLKeyWord.THEN);
        printSpaceAfterAccept(x.getThen());
        return false;
    }

    @Override
    public boolean visit(SQLCaseExpr.SQLCaseExprElseClause x) {
        print(ELSE);
        printSpaceAfterAccept(x.getExpr());
        return false;
    }

    @Override
    public boolean visit(SQLListExpr x) {
        printAccept(x.getItems(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLNullExpr x) {
        print(SQLKeyWord.NULL);
        return false;
    }


    @Override
    public boolean visit(SQLSelectQueryExpr x) {
        boolean indent = false;
        if (x.isParen()) {
            print('(');
            indent = true;
        }

        if (!indent) {
            indent = x.getParent() instanceof SQLInCondition
                    || x.getParent() instanceof SQLTableFunctionTableReference;
//                    || x.getParent() instanceof SQLAnyClause
//                    || x.getParent() instanceof SQLSomeClause
//                    || x.getParent() instanceof SQLAllClause;
        }

        if (indent) {
            this.incrementIndent();
            println();
        }

        x.getSubQuery().accept(this);

        if (indent) {
            this.decrementIndent();
            println();
        }

        if (x.isParen()) {
            print(')');
        }

        return false;
    }

    @Override
    public boolean visit(ISQLSubQueryRestrictionClause.SQLWithReadOnly x) {
        print(isLowerCase()?"WITH READ ONLY":"WITH READ ONLY");
        if (x.getConstraint() != null) {
            printSpaceAfterValue(SQLKeyWord.CONSTRAINT);
            printSpaceAfterAccept(x.getConstraint());
        }
        return false;
    }

    @Override
    public boolean visit(ISQLSubQueryRestrictionClause.SQLWithCheckOption x) {
        print(SQLKeyWord.WITH);
        printSpaceAfterValue(x.getLevels());
        printSpaceAfterValue(isLowerCase()?"check option":"CHECK OPTION");
        if (x.getConstraint() != null) {
            printSpaceAfterValue(SQLKeyWord.CONSTRAINT);
            printSpaceAfterAccept(x.getConstraint());
        }
        return false;
    }

    @Override
    public boolean visit(ISQLPartitionExtensionClause.SQLPartitionClause x) {
        print(SQLKeyWord.PARTITION);
        printSpaceAfterAccept(x.getItems(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(ISQLPartitionExtensionClause.SQLPartitionForClause x) {
        print(SQLKeyWord.PARTITION);
        printSpaceAfterValue(SQLKeyWord.FOR);
        printSpaceAfterAccept(x.getItems(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(ISQLPartitionExtensionClause.SQLSubPartitionClause x) {
        print(SQLKeyWord.SUBPARTITION);
        printSpaceAfterAccept(x.getItems(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(ISQLPartitionExtensionClause.SQLSubPartitionForClause x) {
        print(SQLKeyWord.SUBPARTITION);
        printSpaceAfterValue(SQLKeyWord.FOR);
        printSpaceAfterAccept(x.getItems(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLCurrentOfClause x) {
        print(isLowerCase() ? "current of" : "CURRENT OF");
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(ISQLReturningClause.SQLReturnIntoClause x) {
        print(SQLKeyWord.RETURN);
        printSpaceAfterAccept(x.getReturningItems(), ", ");

        if (x.isBulkCollect()) {
            printSpaceAfterValue(isLowerCase() ? "bulk collect" : "BULK COLLECT");
        }

        printSpaceAfterValue(SQLKeyWord.INTO);
        printSpaceAfterAccept(x.getIntoItems(), ", ");
        return false;
    }

    @Override
    public boolean visit(ISQLReturningClause.SQLReturningIntoClause x) {
        print(SQLKeyWord.RETURNING);
        printSpaceAfterAccept(x.getReturningItems(), ", ");

        if (x.isBulkCollect()) {
            printSpaceAfterValue(isLowerCase() ? "bulk collect" : "BULK COLLECT");
        }

        printSpaceAfterValue(SQLKeyWord.INTO);
        printSpaceAfterAccept(x.getIntoItems(), ", ");
        return false;
    }

    @Override
    public boolean visit(SQLErrorLoggingClause x) {
        print(isLowerCase() ? "log errors" : "LOG ERRORS");

        ISQLName into = x.getInto();
        if (into != null) {
            printSpaceAfterValue(SQLKeyWord.INTO);
            printSpaceAfterAccept(into);
        }

        ISQLExpr expr = x.getExpr();
        if (expr != null) {
            printSpaceAfterValue('(');
            expr.accept(this);
            print(')');
        }


        ISQLExpr rejectLimit = x.getRejectLimit();
        if (rejectLimit != null) {
            printSpaceAfterValue(isLowerCase() ? "reject limit" : "REJECT LIMIT");
            printSpaceAfterAccept(rejectLimit);
        }

        return false;
    }


    @Override
    public boolean visit(SQLValuesClause x) {
        print(x.getType());
        this.incrementIndent();
        printSpaceAfterAccept(x.getItems(), ", ", false);
        this.decrementIndent();
        return false;
    }

    @Override
    public boolean visit(SQLValuesClause.SQLValuesItem x) {
        printAccept(x.getExpr());
        return false;
    }

    @Override
    public boolean visit(SQLSetClause x) {
        print(SQLKeyWord.SET);

        printSpace();
        printSetItems(x.getItems());
        return false;
    }

    public void printSetItems(List<ISQLExpr> items) {
        int lineLength = 0;

        incrementIndent();
        for (int i = 0; i < items.size(); i++) {
            ISQLExpr setItem = items.get(i);
            int updateSetItemLength = setItem.toString().length();
            lineLength += updateSetItemLength;
            if (i != 0) {
                print(",");
            }
            if (i != 0) {
                if (lineLength > getLineMaxLength()) {
                    println();
                    lineLength = updateSetItemLength;
                } else {
                    printSpace();
                }
            }

            setItem.accept(this);
        }
        decrementIndent();
    }


    @Override
    public boolean visit(SQLAssignmentExpr x) {
        printAccept(x.getColumn());
        printSpaceAfterValue(x.getOperator());
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(ISQLHierarchicalQueryClause.SQLHierarchicalQueryConnectByStartWithClause x) {
        print(isLowerCase() ? "connect by" : "CONNECT BY");

        if (x.isNoCycle()) {
            printSpace();
            print(SQLKeyWord.NOCYCLE);
        }

        printSpaceAfterAccept(x.getConnectByCondition());

        ISQLExpr startWithCondition = x.getStartWithCondition();
        if (startWithCondition != null) {
            printlnAfterValue(isLowerCase() ? "start with" : "START WITH");
            printSpaceAfterAccept(startWithCondition);
        }

        return false;
    }

    @Override
    public boolean visit(ISQLHierarchicalQueryClause.SQLHierarchicalQueryStartWithConnectByClause x) {
        print(isLowerCase(), "start with", "START WITH");

        printSpaceAfterAccept(x.getStartWithCondition());

        printlnAfterValue(isLowerCase() ? "connect by" : "CONNECT BY");

        if (x.isNoCycle()) {
            printSpaceAfterValue(SQLKeyWord.NOCYCLE);
        }

        printSpaceAfterAccept(x.getConnectByCondition());

        return false;
    }

    @Override
    public boolean visit(SQLOuterJoinExpr x) {
        x.getName().accept(this);
        print("(+)");
        return false;
    }

    @Override
    public boolean visit(SQLSetOptionExpr x) {
        if (x.isDefault_()) {
            print(SQLKeyWord.DEFAULT);
            printSpace();
        }
        printAccept(x.getName());

        if (x.isEq()) {
            printSpaceAfterValue(SQLKeyWord.EQ);
        }

        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLCharacterSetOptionExpr x) {
        if (x.isDefault_()) {
            print(SQLKeyWord.DEFAULT);
            printSpace();
        }
        print(isLowerCase() ? "character set" : "CHARACTER SET");

        if (x.isEq()) {
            printSpaceAfterValue(SQLKeyWord.EQ);
        }

        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLCollateOptionExpr x) {
        if (x.isDefault_()) {
            print(SQLKeyWord.DEFAULT);
            printSpace();
        }
        print(SQLKeyWord.COLLATE);

        if (x.isEq()) {
            printSpaceAfterValue(SQLKeyWord.EQ);
        }

        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLAlgorithmOptionExpr x) {
        print(SQLKeyWord.ALGORITHM);

        if (x.isEq()) {
            printSpaceAfterValue(SQLKeyWord.EQ);
        }

        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLDefinerOptionExpr x) {
        print(SQLKeyWord.DEFINER);

        if (x.isEq()) {
            printSpaceAfterValue(SQLKeyWord.EQ);
        }

        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLParameterDeclaration x) {
        boolean print = false;
        SQLParameterDeclaration.SQLParameterModel parameterModel = x.getParameterModel();
        if (parameterModel != null) {
            print(parameterModel);
            print = true;
        }

        ISQLName name = x.getName();
        if (name != null) {
            if (print) {
                printSpace();
            }
            name.accept(this);
            print = true;
        }

        ISQLDataType dataType = x.getDataType();
        if (dataType != null) {
            if (print) {
                printSpace();
            }
            dataType.accept(this);
            print = true;
        }

        if (x.isResult()) {
            if (print) {
                printSpace();
            }
            print(SQLKeyWord.RESULT);
            print = true;
        }

        SQLDefaultClause defaultClause = x.getDefaultClause();
        if (defaultClause != null) {
            if (print) {
                printSpace();
            }
            printAccept(defaultClause);
            print = true;
        }
        return false;
    }

    @Override
    public boolean visit(SQLDefaultClause x) {
        print(x.getOperator());
        printSpaceAfterAccept(x.getExpr());
        return false;
    }

    @Override
    public boolean visit(SQLDefaultOnNullClause x) {
        print(SQLKeyWord.DEFAULT);
        printSpaceAfterValue(isLowerCase() ? "on null" : "ON NULL");
        printSpaceAfterAccept(x.getExpr());
        return false;
    }

    @Override
    public boolean visit(ISQLUsingIndexClause.SQLUsingIndexClause x) {
        print(isLowerCase() ? "using index" : "USING INDEX");
        printSpaceAfterAccept(x.getItems(), " ");
        return false;
    }

    @Override
    public boolean visit(ISQLUsingIndexClause.SQLCreateIndexStatementItem x) {
        print('(');
        printIndentLnAccept(x.getCreateIndexStatement());
        printlnAfterValue(")");
        return false;
    }

    @Override
    public boolean visit(ISQLUsingIndexClause.SQLUsingNoIndexClause x) {
        print(isLowerCase() ? "using no index" : "USING NO INDEX");
        return false;
    }

    @Override
    public boolean visit(SQLStoreInClause x) {
        print(isLowerCase() ? "store in" : "STORE IN");
        printSpaceAndLnAndAccept(x.getItems(), ",", true);
        return false;
    }

    @Override
    public boolean visit(SQLAllocateExtentClause x) {
        print(isLowerCase() ? "allocate extent" : "ALLOCATE EXTENT");
        printSpaceAndLnAndAccept(x.getItems(), ",", true);
        return false;
    }

    @Override
    public boolean visit(SQLDeallocateUnusedClause x) {
        print(isLowerCase() ? "deallocate unused" : "DEALLOCATE UNUSED");
        SQLSizeClause sizeClause = x.getKeep();
        if (sizeClause != null) {
            printSpaceAfterValue(SQLKeyWord.KEEP);
            printSpaceAfterAccept(sizeClause);
        }
        return false;
    }

    @Override
    public boolean visit(SQLFileSpecification x) {
        boolean print = false;

        if (x.getFile() != null) {
            printAccept(x.getFile());
            print = true;
        }

        if (x.getSizeClause() != null) {
            if (print) {
                printSpace();
            }
            print(SQLKeyWord.SIZE);
            printSpaceAfterAccept(x.getSizeClause());
            print = true;
        }

        if (x.getBlockSize() != null) {
            if (print) {
                printSpace();
            }
            print(SQLKeyWord.BLOCKSIZE);
            printSpaceAfterAccept(x.getBlockSize());
            print = true;
        }

        if (x.isReuse()) {
            if (print) {
                printSpace();
            }
            print(SQLKeyWord.REUSE);
            print = true;
        }

        if (x.getAutoextendClause() != null) {
            if (print) {
                printSpace();
            }
            printAccept(x.getAutoextendClause());
            print = true;
        }

        return false;
    }

    @Override
    public boolean visit(SQLFileSpecification.SQLAutoExtendOnClause x) {
        print(isLowerCase() ? "autoextend on" : "AUTOEXTEND ON");

        if (x.getNext() != null) {
            printSpaceAfterValue(SQLKeyWord.NEXT);
            printSpaceAfterAccept(x.getNext());
        }

        printSpaceAfterAccept(x.getMaxsize());
        return false;
    }

    @Override
    public boolean visit(SQLFileSpecification.SQLAutoExtendOffClause x) {
        print(isLowerCase() ? "autoextend off" : "AUTOEXTEND OFF");
        return false;
    }

    @Override
    public boolean visit(SQLFileSpecification.SQLMaxSizeClause x) {
        print(SQLKeyWord.MAXSIZE);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(ISQLLoggingClause.SQLLoggingClause x) {
        print(SQLKeyWord.LOGGING);
        return false;
    }

    @Override
    public boolean visit(ISQLLoggingClause.SQLNoLoggingClause x) {
        print(SQLKeyWord.NOLOGGING);
        return false;
    }

    @Override
    public boolean visit(ISQLLoggingClause.SQLFilesystemLikeLogging x) {
        print(SQLKeyWord.FILESYSTEM_LIKE_LOGGING);
        return false;
    }

    @Override
    public boolean visit(ISQLParallelClause.SQLParallelClause x) {
        print(SQLKeyWord.PARALLEL);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(ISQLParallelClause.SQLNoParallelClause x) {
        print(SQLKeyWord.NOPARALLEL);
        return false;
    }

    @Override
    public boolean visit(ISQLPhysicalAttribute.SQLPctfree x) {
        print(SQLKeyWord.PCTFREE);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(ISQLPhysicalAttribute.SQLPctused x) {
        print(SQLKeyWord.PCTUSED);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(ISQLPhysicalAttribute.SQLInitrans x) {
        print(SQLKeyWord.INITRANS);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLSizeClause x) {
        printAccept(x.getValue());
        print(x.getType());
        return false;
    }

    @Override
    public boolean visit(SQLStorageClause x) {
        print(isLowerCase() ? "storage" : "STORAGE");
        printSpaceAndLnAndAccept(x.getItems(), ",", true);
        return false;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLInitialSizeClause x) {
        print(SQLKeyWord.INITIAL);
        printSpaceAfterAccept(x.getSizeClause());
        return false;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLNextSizeClause x) {
        print(SQLKeyWord.NEXT);
        printSpaceAfterAccept(x.getSizeClause());
        return false;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLMinExtentsClause x) {
        print(SQLKeyWord.MINEXTENTS);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLMaxExtentsClause x) {
        print(SQLKeyWord.MAXEXTENTS);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLPctIncreaseClause x) {
        print(SQLKeyWord.PCTINCREASE);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLFreeListsClause x) {
        print(SQLKeyWord.FREELISTS);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLFreeListGroupsClause x) {
        print(isLowerCase() ? "freelist groups" : "FREELIST GROUPS");
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLOptimalClause x) {
        print(SQLKeyWord.OPTIMAL);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLBufferPoolClause x) {
        print(SQLKeyWord.OPTIMAL);
        printSpaceAfterValue(x.getType());
        return false;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLFlashCacheClause x) {
        print(SQLKeyWord.FLASH_CACHE);
        printSpaceAfterValue(x.getType());
        return false;
    }

    @Override
    public boolean visit(SQLStorageClause.SQLEncryptClause x) {
        print(SQLKeyWord.ENCRYPT);
        return false;
    }

    @Override
    public boolean visit(SQLTableSpaceSetClause x) {
        print(SQLKeyWord.TABLESPACE);
        if (x.isSet()) {
            printSpaceAfterValue(SQLKeyWord.SET);
        }
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(ISQLCompression.SQLNoCompress x) {
        print(SQLKeyWord.NOCOMPRESS);
        return false;
    }

    @Override
    public boolean visit(ISQLCompression.SQLCompress x) {
        print(SQLKeyWord.COMPRESS);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(ISQLCompression.SQLRowStoreCompress x) {
        return false;
    }

    @Override
    public boolean visit(ISQLCompression.SQLColumnStoreCompress x) {
        return false;
    }

    @Override
    public boolean visit(ISQLCompression.SQLCompressAdvanced x) {
        print(SQLKeyWord.COMPRESS);
        printSpaceAfterValue(SQLKeyWord.ADVANCED);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLAccessibleByClause x) {
        print(isLowerCase() ? "accessible by" : "ACCESSIBLE BY");
        printlnAndAccept(x.getAccessors(), ",", true);
        return false;
    }

    @Override
    public boolean visit(SQLAccessibleByClause.SQLAccessor x) {
        SQLAccessibleByClause.SQLUnitKind unitKind = x.getUnitKind();
        if (unitKind != null) {
            print(unitKind);
            printSpace();
        }
        printAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLAggregateClause x) {
        print(isLowerCase() ? "aggregate using" : "AGGREGATE USING");
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLLabel x) {
        print("<<");
        printAccept(x.getLabel());
        print(">>");
        return false;
    }

    @Override
    public boolean visit(SQLBlock x) {
        if (x.getLabels().size() > 0) {
            printlnAndAccept(x.getLabels());
            println();
        }

        if (x.getDeclares().size() > 0) {

            print(SQLKeyWord.DECLARE);

            this.incrementIndent();
            println();
            printlnAndAccept(x.getDeclares());
            this.decrementIndent();

            println();
        }

        x.getBody().accept(this);

        return false;
    }

    @Override
    public boolean visit(SQLBody x) {
        ISQLName beginLabel = x.getBeginLabel();
        if (beginLabel != null) {
            printAccept(beginLabel);
            printSpaceAfterValue(SQLKeyWord.COLON);
            printSpace();
        }

        print(SQLKeyWord.BEGIN);

        this.incrementIndent();
        println();

        printlnAndAccept(x.getStatements());

        printlnExceptionClause(x.getExceptionHandlers());

        this.decrementIndent();

        printlnAfterValue(SQLKeyWord.END);

        printSpaceAfterAccept(x.getEndName());

        return false;
    }

    @Override
    public boolean visit(SQLLabelStatement x) {
        List<SQLLabel> labels = x.getLabels();
        if (labels != null
                && labels.size() > 0) {
            printlnAndAccept(x.getLabels());
            println();
        }
        printAccept(x.getStatement());

        return false;
    }

    @Override
    public boolean visit(SQLSubtypeDefinition x) {
        print(SQLKeyWord.SUBTYPE);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.IS);
        printSpaceAfterAccept(x.getDataType());
        printSpaceAfterAccept(x.getOption());

        if (x.isNotNull()) {
            printSpaceAfterValue(isLowerCase() ? "not null" : "NOT NULL");
        }
        return false;

    }

    @Override
    public boolean visit(SQLSubtypeDefinition.SQLSubtypeConstraint x) {

        return false;
    }

    @Override
    public boolean visit(SQLSubtypeDefinition.SQLSubtypeRangeConstraint x) {
        return false;
    }

    @Override
    public boolean visit(SQLProcedureInvocation x) {
        printAccept(x.getName());
        if (x.isParen()) {
            print('(');
            printAccept(x.getArguments(), ", ");
            print(')');
        }
        return false;
    }

    @Override
    public boolean visit(ISQLCallSpec.SQLJavaDeclaration x) {
        print(SQLKeyWord.LANGUAGE);
        printSpaceAfterValue(SQLKeyWord.JAVA);
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(ISQLCallSpec.SQLCDeclarationLanguageC x) {
        print(SQLKeyWord.LANGUAGE);
        printSpaceAfterValue(SQLKeyWord.C);

        printSpaceAfterAccept(x.getNames(), " ");
        List<ISQLExpr> agentInArguments = x.getAgentInArguments();
        if (agentInArguments != null
                && agentInArguments.size() != 0) {
            printSpaceAfterValue(SQLKeyWord.AGENT);
            printSpaceAfterValue(SQLKeyWord.IN);
            printSpaceAfterAccept(agentInArguments, ", ", true);
        }

        if (x.isWithContext()) {
            printSpaceAfterValue(isLowerCase() ? "with context" : "WITH CONTEXT");
        }

        List<ISQLCallSpec.SQLExternalParameter> parameters = x.getParameters();
        if (parameters != null
                && parameters.size() != 0) {
            printSpaceAfterValue(SQLKeyWord.PARAMETERS);
            printSpaceAfterAccept(parameters, ", ", true);
        }
        return false;
    }

    @Override
    public boolean visit(ISQLCallSpec.SQLCDeclarationExternal x) {
        print(SQLKeyWord.EXTERNAL);
        printSpaceAfterAccept(x.getNames(), " ");
        List<ISQLExpr> agentInArguments = x.getAgentInArguments();
        if (agentInArguments != null
                && agentInArguments.size() != 0) {
            printSpaceAfterValue(SQLKeyWord.AGENT);
            printSpaceAfterValue(SQLKeyWord.IN);
            printSpaceAfterAccept(agentInArguments, ", ", true);
        }

        if (x.isWithContext()) {
            printSpaceAfterValue(isLowerCase() ? "with context" : "WITH CONTEXT");
        }

        List<ISQLCallSpec.SQLExternalParameter> parameters = x.getParameters();
        if (parameters != null
                && parameters.size() != 0) {
            printSpaceAfterValue(SQLKeyWord.PARAMETERS);
            printSpaceAfterAccept(parameters, ", ", true);
        }
        return false;
    }

    @Override
    public boolean visit(ISQLCallSpec.SQLNameExpr x) {
        print(SQLKeyWord.NAME);
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(ISQLCallSpec.SQLLibraryExpr x) {
        print(SQLKeyWord.LIBRARY);
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(ISQLCallSpec.SQLExternalParameter x) {
        printAccept(x.getName());
        printSpaceAfterValue(x.getProperty());

        if (x.isByReference()) {
            printSpaceAfterValue(isLowerCase() ? "by reference" : "BY REFERENCE");
        }
        printSpaceAfterAccept(x.getDataType());
        return false;
    }

    @Override
    public boolean visit(ISQLCollectionTypeDefinition.SQLAssocArrayTypeDefinition x) {
        print(SQLKeyWord.TYPE);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.IS);
        printSpaceAfterValue(SQLKeyWord.TABLE);

        printSpaceAfterValue(SQLKeyWord.OF);
        printSpaceAfterAccept(x.getDataType());

        if (x.isNotNull()) {
            printSpaceAfterValue(isLowerCase() ? "not null" : "NOT NULL");
        }

        ISQLDataType indexByDataType = x.getIndexByDataType();
        if (indexByDataType != null) {
            printSpaceAfterValue(isLowerCase() ? "index by" : "INDEX BY");
            printSpaceAfterAccept(indexByDataType);
        }
        return false;
    }

    @Override
    public boolean visit(ISQLCollectionTypeDefinition.SQLNestedTableTypeDefinition x) {

        print(SQLKeyWord.TYPE);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.IS);
        printSpaceAfterValue(SQLKeyWord.TABLE);

        printSpaceAfterValue(SQLKeyWord.OF);
        printSpaceAfterAccept(x.getDataType());

        if (x.isNotNull()) {
            printSpaceAfterValue(isLowerCase() ? "not null" : "NOT NULL");
        }

        return false;
    }

    @Override
    public boolean visit(ISQLCollectionTypeDefinition.SQLVarrayTypeDefinition x) {
        print(SQLKeyWord.TYPE);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.IS);
        printSpaceAfterValue(SQLKeyWord.VARRAY);

        ISQLExpr size = x.getSize();
        if (size != null) {
            print('(');
            printAccept(size);
            print(')');
        }

        printSpaceAfterValue(SQLKeyWord.OF);
        printSpaceAfterAccept(x.getDataType());

        if (x.isNotNull()) {
            printSpaceAfterValue(isLowerCase() ? "not null" : "NOT NULL");
        }

        return false;
    }

    @Override
    public boolean visit(ISQLCollectionTypeDefinition.SQLArrayTypeDefinition x) {
        print(SQLKeyWord.TYPE);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.IS);
        printSpaceAfterValue(SQLKeyWord.ARRAY);

        ISQLExpr size = x.getSize();
        if (size != null) {
            print('(');
            printAccept(size);
            print(')');
        }

        printSpaceAfterValue(SQLKeyWord.OF);
        printSpaceAfterAccept(x.getDataType());

        if (x.isNotNull()) {
            printSpaceAfterValue(isLowerCase() ? "not null" : "NOT NULL");
        }

        return false;
    }

    @Override
    public boolean visit(ISQLCollectionTypeDefinition.SQLVaryingArrayTypeDefinition x) {
        print(SQLKeyWord.TYPE);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.IS);
        printSpaceAfterValue(SQLKeyWord.VARYING);
        printSpaceAfterValue(SQLKeyWord.ARRAY);

        ISQLExpr size = x.getSize();
        if (size != null) {
            print('(');
            printAccept(size);
            print(')');
        }

        printSpaceAfterValue(SQLKeyWord.OF);
        printSpaceAfterAccept(x.getDataType());

        if (x.isNotNull()) {
            printSpaceAfterValue(isLowerCase() ? "not null" : "NOT NULL");
        }

        return false;
    }

    @Override
    public boolean visit(SQLCompileClause x) {
        print(SQLKeyWord.COMPILE);
        if (x.isDebug()) {
            printSpaceAfterValue(SQLKeyWord.DEBUG);
        }
        printSpaceAfterValue(x.getCompiler());

        printIndentLnAccept(x.getParameters());

        if (x.isReuseSettings()) {
            printSpaceAfterValue(isLowerCase() ? "reuse settings" : "REUSE SETTINGS");
        }
        return false;
    }

    @Override
    public boolean visit(SQLCompileClause.SQLParameter x) {
        printAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.EQ);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLConstantDeclaration x) {
        printAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.CONSTANT);
        printSpaceAfterAccept(x.getDataType());

        if (x.isNotNull()) {
            printSpaceAfterValue(isLowerCase() ? "not null" : "NOT NULL");
        }

        printSpaceAfterAccept(x.getDefaultClause());
        return false;
    }

    @Override
    public boolean visit(SQLRefCursorTypeDefinition x) {
        print(SQLKeyWord.TYPE);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.IS);
        printSpaceAfterValue(SQLKeyWord.REF);
        printSpaceAfterValue(SQLKeyWord.CURSOR);

        ISQLDataType returnDataType = x.getReturnDataType();
        if (returnDataType != null) {
            printSpaceAfterAccept(returnDataType);
        }

        return false;
    }

    @Override
    public boolean visit(SQLDeterministicClause x) {
        print(SQLKeyWord.DETERMINISTIC);
        return false;
    }

    @Override
    public boolean visit(SQLExceptionDeclaration x) {
        printAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.EXCEPTION);
        return false;
    }

    public void printlnExceptionClause(List<SQLExceptionHandler> exceptionHandlers) {
        if (exceptionHandlers == null
                || exceptionHandlers.size() == 0) {
            return;
        }
        printlnAfterValue(SQLKeyWord.EXCEPTION);
        printIndentLnAccept(exceptionHandlers);
    }

    @Override
    public boolean visit(SQLExceptionHandler x) {
        print(SQLKeyWord.WHEN);
        printSpaceAfterAccept(x.getExceptions(), isLowerCase() ? " or " : " OR ");

        printSpaceAfterValue(SQLKeyWord.THEN);
        printIndentLnAccept(x.getStatements());

        return false;
    }

    @Override
    public boolean visit(SQLCursorDeclaration x) {
        print(SQLKeyWord.CURSOR);
        printSpaceAfterAccept(x.getName());

        printSpaceAndLnAndAccept(x.getParameters(), ",", true);

        printSpaceAfterValue(SQLKeyWord.RETURN);
        printSpaceAfterAccept(x.getReturnDataType());

        return false;
    }

    @Override
    public boolean visit(SQLCursorDefinition x) {
        print(SQLKeyWord.CURSOR);
        printSpaceAfterAccept(x.getName());

        printSpaceAndLnAndAccept(x.getParameters(), ",", true);

        ISQLDataType returnDataType = x.getReturnDataType();
        if (returnDataType != null) {
            printSpaceAfterValue(SQLKeyWord.RETURN);
            printSpaceAfterAccept(returnDataType);
        }

        printlnAfterValue(SQLKeyWord.IS);
        printIndentLnAccept(x.getSelectStatement());
        return false;
    }

    @Override
    public boolean visit(SQLFunctionDeclaration x) {
        print(SQLKeyWord.FUNCTION);
        printSpaceAfterAccept(x.getName());

        printSpaceAndLnAndAccept(x.getParameters(), ",", true);

        printSpaceAfterValue(SQLKeyWord.RETURN);
        printSpaceAfterAccept(x.getReturnDataType());

//        printIndentLnAccept(x.getExternalClause());

        List<ISQLExpr> properties = x.getProperties();
        if (properties != null
                && properties.size() > 0) {
            println();
            printlnAndAccept(x.getProperties());
        }

        return false;
    }

    @Override
    public boolean visit(SQLFunctionDefinition x) {
        print(SQLKeyWord.FUNCTION);
        printSpaceAfterAccept(x.getName());

        printSpaceAndLnAndAccept(x.getParameters(), ",", true);

        printSpaceAfterValue(SQLKeyWord.RETURN);
        printSpaceAfterAccept(x.getReturnDataType());

        List<ISQLExpr> properties = x.getProperties();
        if (properties != null
                && properties.size() > 0) {
            println();
            printlnAndAccept(x.getProperties());
        }

        printSpaceAfterValue(x.getAs());

        incrementIndent();
        println();

        List<ISQLExpr> declareSections = x.getDeclareSections();
        if (declareSections != null
                && declareSections.size() > 0) {
            printlnAndAccept(declareSections);
            println();
        }

        printAccept(x.getAsExpr());

        decrementIndent();

        return false;
    }


    @Override
    public boolean visit(SQLInvokerRightsClause x) {
        print(SQLKeyWord.AUTHID);
        printSpaceAfterValue(x.getAuthidType());
        return false;
    }

    @Override
    public boolean visit(SQLParallelEnableClause x) {
        print(SQLKeyWord.PARALLEL_ENABLE);
        printSpaceAndLnAndAccept(x.getArguments(), ",", true);
        return false;
    }

    @Override
    public boolean visit(SQLParallelEnableClause.SQLPartitionByAnyArgument x) {
        print(SQLKeyWord.PARTITION);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.BY);
        printSpaceAfterValue(SQLKeyWord.ANY);
        printSpaceAndLnAndAccept(x.getColumns(), ",", true);
        printSpaceAfterAccept(x.getStreamingClause());
        return false;
    }

    @Override
    public boolean visit(SQLParallelEnableClause.SQLPartitionByHashArgument x) {
        print(SQLKeyWord.PARTITION);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.BY);
        printSpaceAfterValue(SQLKeyWord.HASH);
        printSpaceAndLnAndAccept(x.getColumns(), ",", true);
        printSpaceAfterAccept(x.getStreamingClause());
        return false;
    }

    @Override
    public boolean visit(SQLParallelEnableClause.SQLPartitionByRangeArgument x) {
        print(SQLKeyWord.PARTITION);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.BY);
        printSpaceAfterValue(SQLKeyWord.RANGE);
        printSpaceAndLnAndAccept(x.getColumns(), ",", true);
        printSpaceAfterAccept(x.getStreamingClause());
        return false;
    }

    @Override
    public boolean visit(SQLParallelEnableClause.SQLPartitionByValueArgument x) {
        print(SQLKeyWord.PARTITION);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.BY);
        printSpaceAfterValue(SQLKeyWord.VALUE);
        printSpaceAndLnAndAccept(x.getColumns(), ",", true);
        printSpaceAfterAccept(x.getStreamingClause());
        return false;
    }

    @Override
    public boolean visit(SQLParallelEnableClause.SQLStreamingClauseByOrder x) {
        print(SQLKeyWord.ORDER);
        printSpaceAfterAccept(x.getExpr());
        printSpaceAfterValue(SQLKeyWord.BY);
        printSpaceAndLnAndAccept(x.getColumns(), ",", true);
        return false;
    }

    @Override
    public boolean visit(SQLParallelEnableClause.SQLStreamingClusterByCluster x) {
        print(SQLKeyWord.CLUSTER);
        printSpaceAfterAccept(x.getExpr());
        printSpaceAfterValue(SQLKeyWord.BY);
        printSpaceAndLnAndAccept(x.getColumns(), ",", true);
        return false;
    }

    @Override
    public boolean visit(ISQLPipelinedClause.SQLPipelinedClause x) {

        return false;
    }

    @Override
    public boolean visit(ISQLPipelinedClause.SQLPipelinedUsingClause x) {
        return false;
    }

    @Override
    public boolean visit(ISQLPipelinedClause.SQLPipelinedRowClause x) {
        return false;
    }

    @Override
    public boolean visit(ISQLPipelinedClause.SQLPipelinedTableClause x) {
        return false;
    }

    @Override
    public boolean visit(SQLProcedureDeclaration x) {
        print(SQLKeyWord.PROCEDURE);
        printSpaceAfterAccept(x.getName());

        printSpaceAndLnAndAccept(x.getParameters(), ",", true);


        List<ISQLExpr> properties = x.getProperties();
        if (properties != null
                && properties.size() > 0) {
            println();
            printlnAndAccept(properties);
        }

        return false;
    }

    @Override
    public boolean visit(SQLProcedureDefinition x) {
        print(SQLKeyWord.PROCEDURE);
        printSpaceAfterAccept(x.getName());

        printSpaceAndLnAndAccept(x.getParameters(), ",", true);

        printSpaceAfterValue(x.getAs());

        incrementIndent();
        println();

        List<ISQLExpr> declareSections = x.getDeclareSections();
        if (declareSections != null
                && declareSections.size() > 0) {
            printlnAndAccept(declareSections);
            println();
        }

        x.getAsExpr().accept(this);

        decrementIndent();
        return false;
    }

    @Override
    public boolean visit(SQLRecordTypeDefinition x) {
        print(SQLKeyWord.TYPE);

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);
        printSpaceAfterValue(SQLKeyWord.RECORD);

        printSpaceAndLnAndAccept(x.getFields(), ",", true);

        return false;
    }

    @Override
    public boolean visit(SQLRecordTypeDefinition.SQLFieldDefinition x) {
        printAccept(x.getName());
        printSpaceAfterAccept(x.getDataType());

        if (x.isNotNull()) {
            printSpaceAfterValue(isLowerCase() ? "not null" : "NOT NULL");
        }

        printSpaceAfterAccept(x.getDefaultClause());
        return false;
    }

    @Override
    public boolean visit(SQLSharingClause x) {
        return false;
    }

    @Override
    public boolean visit(SQLVariableDeclaration x) {
        printAccept(x.getName());
        printSpaceAfterAccept(x.getDataType());

        if (x.isNotNull()) {
            printSpaceAfterValue(isLowerCase() ? "not null" : "NOT NULL");
        }

        printSpaceAfterAccept(x.getDefaultClause());
        return false;
    }

    @Override
    public boolean visit(ISQLPragma.SQLAutonomousTransactionPragma x) {
        print(SQLKeyWord.PRAGMA);
        printSpaceAfterValue(SQLKeyWord.AUTONOMOUS_TRANSACTION);
        return false;
    }

    @Override
    public boolean visit(ISQLPragma.SQLCoveragePragma x) {
        print(SQLKeyWord.PRAGMA);
        printSpaceAfterValue(SQLKeyWord.COVERAGE);
        printSpaceAfterAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(ISQLPragma.SQLDeprecatePragma x) {
        print(SQLKeyWord.PRAGMA);
        printSpaceAfterValue(SQLKeyWord.DEPRECATE);
        printSpaceAfterAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(ISQLPragma.SQLExceptionInitPragma x) {
        print(SQLKeyWord.PRAGMA);
        printSpaceAfterValue(SQLKeyWord.EXCEPTION_INIT);
        printSpaceAfterAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(ISQLPragma.SQLInlinePragma x) {
        print(SQLKeyWord.PRAGMA);
        printSpaceAfterValue(SQLKeyWord.INLINE);
        printSpaceAfterAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(ISQLPragma.SQLRestrictReferencesPragma x) {
        print(SQLKeyWord.PRAGMA);
        printSpaceAfterValue(SQLKeyWord.RESTRICT_REFERENCES);
        printSpaceAfterAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(ISQLPragma.SQLSeriallyReusablePragma x) {
        print(SQLKeyWord.PRAGMA);
        printSpaceAfterValue(SQLKeyWord.SERIALLY_REUSABLE);
        return false;
    }

    @Override
    public boolean visit(ISQLPragma.SQLUDFPragma x) {
        print(SQLKeyWord.PRAGMA);
        printSpaceAfterValue(SQLKeyWord.UDF);
        return false;
    }

    // ---------------------------- Common End ------------------------------------


    // ---------------------------- Operator Start ------------------------------------

    @Override
    public boolean visit(SQLUnaryOperatorExpr x) {
        if (x.isParen()) {
            print('(');
        }

        print(x.getOperator());

        ISQLExpr operand = x.getOperand();

        switch (x.getOperator()) {
            case PRIOR:
            case CONNECT_BY_ROOT:
            case COLLATE:
            case BINARY:
                printSpace();
            default:
                break;
        }

        operand.accept(this);

        if (x.isParen()) {
            print(')');
        }
        return false;
    }

    @Override
    public boolean visit(SQLBinaryOperatorExpr x) {
        boolean paren = x.isParen();
        if (paren) {
            print('(');
        }

        printAccept(x.getLeft());

        printSpaceAfterValue(x.getOperator());

        printSpaceAfterAccept(x.getRight());

        if (paren) {
            print(')');
        }

        return false;
    }

    // ---------------------------- Operator End ------------------------------------


    // ---------------------------- Pseudo Columns Start ------------------------------------

    @Override
    public boolean visit(SQLConnectByIsCycleExpr x) {
        return false;
    }

    @Override
    public boolean visit(SQLConnectByIsLeafExpr x) {
        return false;
    }

    @Override
    public boolean visit(SQLLevelExpr x) {
        return false;
    }

    @Override
    public boolean visit(SQLRowIdExpr x) {
        print(SQLKeyWord.ROWID);
        return false;
    }

    @Override
    public boolean visit(SQLRowNumExpr x) {
        print(SQLKeyWord.ROWNUM);
        return false;
    }

    @Override
    public boolean visit(SQLSequenceExpr x) {
        x.getSequence().accept(this);
        print('.');
        print(x.getName());
        return false;
    }

    // ---------------------------- Pseudo Columns End ------------------------------------


    // ---------------------------- Condition Start ------------------------------------

    @Override
    public boolean visit(SQLIsCondition x) {
        printAccept(x.getExpr());

        printSpaceAfterValue(SQLKeyWord.IS);

        if (x.isNot()) {
            printSpaceAfterValue(SQLKeyWord.NOT);
        }
        printSpaceAfterAccept(x.getValue());

        return false;
    }

    @Override
    public boolean visit(SQLLikeCondition x) {
        printAccept(x.getExpr());

        if (x.isNot()) {
            printSpaceAfterValue(SQLKeyWord.NOT);
        }

        printSpaceAfterValue(x.getOperator());

        printSpaceAfterAccept(x.getPattern());

        ISQLExpr escape = x.getEscape();
        if (escape != null) {
            printSpaceAfterValue(SQLKeyWord.ESCAPE);
            printSpaceAfterAccept(x.getEscape());
        }

        return false;
    }

    @Override
    public boolean visit(SQLRegexpCondition x) {
        printAccept(x.getExpr());

        if (x.isNot()) {
            printSpaceAfterValue(SQLKeyWord.NOT);
        }
        printSpaceAfterValue(SQLKeyWord.REGEXP);
        printSpaceAfterAccept(x.getPattern());
        return false;
    }

    @Override
    public boolean visit(SQLRlikeCondition x) {
        printAccept(x.getExpr());
        printSpaceAfterValue(SQLKeyWord.RLIKE);
        printSpaceAfterAccept(x.getPattern());
        return false;
    }

    @Override
    public boolean visit(SQLSoundsLikeCondition x) {
        printAccept(x.getExpr());
        printSpaceAfterValue(SQLKeyWord.SOUNDS);
        printSpaceAfterValue(SQLKeyWord.LIKE);
        printSpaceAfterAccept(x.getPattern());
        return false;
    }

    @Override
    public boolean visit(SQLBetweenCondition x) {
        printAccept(x.getExpr());

        if (x.isNot()) {
            printSpaceAfterValue(SQLKeyWord.NOT);
        }

        printSpaceAfterValue(SQLKeyWord.BETWEEN);
        printSpace();
        x.getBetween().accept(this);

        printSpaceAfterValue(SQLKeyWord.AND);
        printSpace();
        x.getAnd().accept(this);

        return false;
    }

    @Override
    public boolean visit(SQLInCondition x) {
        printAccept(x.getExpr());

        if (x.isNot()) {
            printSpaceAfterValue(SQLKeyWord.NOT);
        }

        printSpaceAfterValue(SQLKeyWord.IN);

        printSpaceAfterAccept(x.getValues(), ", ", true);

        return false;
    }

    @Override
    public boolean visit(SQLExistsCondition x) {
        print(SQLKeyWord.EXISTS);
        printSpaceAfterValue('(');

        this.incrementIndent();
        println();
        x.getSubQuery().accept(this);
        this.decrementIndent();
        println();

        print(')');

        return false;
    }

    @Override
    public boolean visit(SQLIsOfTypeCondition x) {
        printAccept(x.getExpr());

        printSpaceAfterValue(SQLKeyWord.IS);
        if (x.isNot()) {
            printSpaceAfterValue(SQLKeyWord.NOT);
        }

        printSpaceAfterValue(SQLKeyWord.OF);
        if (x.isType()) {
            printSpaceAfterValue(SQLKeyWord.TYPE);
        }

        printSpace();
        printAccept(x.getItems(), ", ", true);

        return false;
    }

    @Override
    public boolean visit(SQLIsOfTypeCondition.Item x) {
        if (x.isOnly()) {
            print(SQLKeyWord.ONLY);
            printSpace();
        }

        x.getName().accept(this);

        return false;
    }

    @Override
    public boolean visit(SQLEqualsPathCondition x) {
        return false;
    }

    @Override
    public boolean visit(SQLUnderPathCondition x) {
        return false;
    }

    @Override
    public boolean visit(SQLIsJsonCondition x) {
        x.getExpr().accept(this);

        printSpaceAfterValue(SQLKeyWord.IS);
        if (x.isNot()) {
            printSpaceAfterValue(SQLKeyWord.NOT);
        }

        printSpaceAfterValue(SQLKeyWord.JSON);

        if (x.isFormatJson()) {
            printSpaceAfterValue(SQLKeyWord.FORMAT);
            printSpaceAfterValue(SQLKeyWord.JSON);
        }

//        SQLKeyWord option = x.getOption();
//        if (option != null) {
//            printSpaceAfterValue(option);
//        }

//        printSpaceAfterValue(x.getUniqueKeys());

        return false;
    }

    // ---------------------------- Condition End ------------------------------------


    // ---------------------------- DataType Start ------------------------------------


    @Override
    public boolean visit(SQLStringDataType x) {
        print(SQLKeyWord.STRING);
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLCharacterDataType x) {
        print(SQLKeyWord.CHARACTER);
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLCharacterVaryingDataType x) {
        print(SQLKeyWord.CHARACTER);
        print(SQLKeyWord.VARYING);
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLCharacterLargeObjectDataType x) {
        print(SQLKeyWord.CHARACTER);
        print(SQLKeyWord.LARGE);
        print(SQLKeyWord.OBJECT);
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLCharDataType x) {
        print(SQLKeyWord.CHAR);

        List<ISQLExpr> arguments = x.getArguments();
        if (arguments.size() > 0) {
            print('(');
            printAccept(arguments, ", ");

            SQLCharSizeUnit sizeUnit = x.getCharSizeUnit();
            if (sizeUnit != null) {
                printSpaceAfterValue(sizeUnit);
            }
            print(')');
        }

        return false;
    }

    @Override
    public boolean visit(SQLCharVaryingDataType x) {
        print(SQLKeyWord.CHAR);
        print(SQLKeyWord.VARYING);
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLCharLargeObjectDataType x) {
        print(SQLKeyWord.CHAR);
        print(SQLKeyWord.LARGE);
        print(SQLKeyWord.OBJECT);
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLNCharDataType x) {
        print(SQLKeyWord.NCHAR);
        printAccept(x.getArguments(), ", ", true);
//        printSpaceAfterAccept(x.getCharacterSetExpr());
//        printSpaceAfterAccept(x.getCollateClause());
        return false;
    }

    @Override
    public boolean visit(SQLNVarchar2DataType x) {
        print(SQLKeyWord.NVARCHAR2);
        printAccept(x.getArguments(), ", ", true);
//        printSpaceAfterAccept(x.getCharacterSetExpr());
//        printSpaceAfterAccept(x.getCollateClause());
        return false;
    }

    @Override
    public boolean visit(SQLVarcharDataType x) {
        print(SQLKeyWord.VARCHAR);
        printAccept(x.getArguments(), ", ", true);
//        printSpaceAfterAccept(x.getCharacterSetExpr());
//        printSpaceAfterAccept(x.getCollateClause());
        return false;
    }

    @Override
    public boolean visit(SQLVarchar2DataType x) {
        print(SQLKeyWord.VARCHAR2);
        List<ISQLExpr> arguments = x.getArguments();
        if (arguments.size() > 0) {
            print('(');
            printAccept(arguments, ", ");

            SQLCharSizeUnit sizeUnit = x.getCharSizeUnit();
            if (sizeUnit != null) {
                printSpaceAfterValue(sizeUnit);
            }
            print(')');
        }

        return false;
    }

    @Override
    public boolean visit(SQLClobDataType x) {
        print(SQLKeyWord.CLOB);
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLNClobDataType x) {
        print(SQLKeyWord.NCLOB);
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLNationalCharacterDataType x) {
        print(isLowerCase() ? "national character" : "NATIONAL CHARACTER");
        return false;
    }

    @Override
    public boolean visit(SQLNationalCharacterVaryingDataType x) {
        print(isLowerCase() ? "national character varying" : "NATIONAL CHARACTER VARYING");
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLNationalCharacterLargeObjectDataType x) {
        print(isLowerCase() ? "national character large object" : "NATIONAL CHARACTER LARGE OBJECT");
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLNationalCharDataType x) {
        print(isLowerCase() ? "national char" : "NATIONAL CHAR");
        printAccept(x.getArguments(), ", ", true);
//        printSpaceAfterAccept(x.getCharacterSetExpr());
//        printSpaceAfterAccept(x.getCollateClause());
        return false;
    }

    @Override
    public boolean visit(SQLNationalCharVaryingDataType x) {
        print(isLowerCase() ? "national char varying" : "NATIONAL CHAR VARYING");
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLNationalVarcharDataType x) {
        print(isLowerCase() ? "national varchar" : "NATIONAL VARCHAR");
        printAccept(x.getArguments(), ", ", true);
//        printSpaceAfterAccept(x.getCharacterSetExpr());
//        printSpaceAfterAccept(x.getCollateClause());
        return false;
    }

    @Override
    public boolean visit(SQLBinaryDataType x) {
        print(SQLKeyWord.BINARY);
        printAccept(x.getArguments(), ", ", true);
        return false;
    }


    @Override
    public boolean visit(SQLTinyBlobDataType x) {
        print(SQLKeyWord.TINYBLOB);
        return false;
    }

    @Override
    public boolean visit(SQLBlobDataType x) {
        print(SQLKeyWord.BLOB);
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLMediumBlobDataType x) {
        print(SQLKeyWord.MEDIUMBLOB);
        return false;
    }

    @Override
    public boolean visit(SQLLongBlobDataType x) {
        print(SQLKeyWord.LONGBLOB);
        return false;
    }

    @Override
    public boolean visit(SQLTinyTextDataType x) {
        print(SQLKeyWord.TINYTEXT);
        return false;
    }

    @Override
    public boolean visit(SQLTextDataType x) {
        print(SQLKeyWord.TEXT);
        return false;
    }

    @Override
    public boolean visit(SQLMediumTextDataType x) {
        print(SQLKeyWord.MEDIUMTEXT);
        return false;
    }

    @Override
    public boolean visit(SQLLongTextDataType x) {
        print(SQLKeyWord.LONGTEXT);
        return false;
    }


    @Override
    public boolean visit(SQLEnumDataType x) {
        print(SQLKeyWord.ENUM);
        printAccept(x.getArguments(), ", ", true);
//        printSpaceAfterAccept(x.getCharacterSetExpr());
//        printSpaceAfterAccept(x.getCollateClause());
        return false;
    }

    @Override
    public boolean visit(SQLSetDataType x) {
        print(SQLKeyWord.SET);
        printAccept(x.getArguments(), ", ", true);
//        printSpaceAfterAccept(x.getCharacterSetExpr());
//        printSpaceAfterAccept(x.getCollateClause());
        return false;
    }


    @Override
    public boolean visit(SQLLongDataType x) {
        print(SQLKeyWord.LONG);
        return false;
    }

    @Override
    public boolean visit(SQLLongRawDataType x) {
        print(SQLKeyWord.LONG);
        printSpaceAfterValue(SQLKeyWord.RAW);
        return false;
    }

    @Override
    public boolean visit(SQLRawDataType x) {
        print(SQLKeyWord.RAW);
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLBFileDataType x) {
        print(SQLKeyWord.BFILE);
        return false;
    }

    @Override
    public boolean visit(SQLRowIdDataType x) {
        print(SQLKeyWord.ROWID);
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLURowIdDataType x) {
        print(SQLKeyWord.UROWID);
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLBitDataType x) {
        print(SQLKeyWord.BIT);
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLNumericDataType x) {
        print(SQLKeyWord.NUMERIC);
        printAccept(x.getArguments(), ", ", true);
        printNumericDataType(x);
        return false;
    }

    @Override
    public boolean visit(SQLNumberDataType x) {
        print(SQLKeyWord.NUMBER);
        printAccept(x.getArguments(), ", ", true);
        printNumericDataType(x);
        return false;
    }

    @Override
    public boolean visit(SQLDecDataType x) {
        print(SQLKeyWord.DEC);
        printAccept(x.getArguments(), ", ", true);
        printNumericDataType(x);
        return false;
    }

    @Override
    public boolean visit(SQLDecimalDataType x) {
        print(SQLKeyWord.DECIMAL);
        printAccept(x.getArguments(), ", ", true);
        printNumericDataType(x);
        return false;
    }

    @Override
    public boolean visit(SQLTinyIntDataType x) {
        print(SQLKeyWord.TINYINT);
        printAccept(x.getArguments(), ", ", true);
        printNumericDataType(x);
        return false;
    }

    @Override
    public boolean visit(SQLSmallIntDataType x) {
        print(SQLKeyWord.SMALLINT);
        printAccept(x.getArguments(), ", ", true);
        printNumericDataType(x);
        return false;
    }

    @Override
    public boolean visit(SQLMediumIntDataType x) {
        print(SQLKeyWord.MEDIUMINT);
        printAccept(x.getArguments(), ", ", true);
        printNumericDataType(x);
        return false;
    }

    @Override
    public boolean visit(SQLIntDataType x) {
        print(SQLKeyWord.INT);
        printAccept(x.getArguments(), ", ", true);
        printNumericDataType(x);
        return false;
    }

    @Override
    public boolean visit(SQLIntegerDataType x) {
        print(SQLKeyWord.INTEGER);
        printAccept(x.getArguments(), ", ", true);
        printNumericDataType(x);
        return false;
    }

    @Override
    public boolean visit(SQLBigIntDataType x) {
        print(SQLKeyWord.BIGINT);
        printAccept(x.getArguments(), ", ", true);
        printNumericDataType(x);
        return false;
    }

    @Override
    public boolean visit(SQLPlsIntegerDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLBinaryIntegerDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLFloatDataType x) {
        print(SQLKeyWord.FLOAT);
        printAccept(x.getArguments(), ", ", true);
        printNumericDataType(x);
        return false;
    }

    @Override
    public boolean visit(SQLRealDataType x) {
        print(SQLKeyWord.REAL);
        printAccept(x.getArguments(), ", ", true);
        printNumericDataType(x);
        return false;
    }

    @Override
    public boolean visit(SQLDoubleDataType x) {
        print(SQLKeyWord.DOUBLE);
        printAccept(x.getArguments(), ", ", true);
        printNumericDataType(x);
        return false;
    }

    @Override
    public boolean visit(SQLDoublePrecisionDataType x) {
        print(isLowerCase() ? "double precision" : "DOUBLE PRECISION");
        printAccept(x.getArguments(), ", ", true);
        printNumericDataType(x);
        return false;
    }

    @Override
    public boolean visit(SQLBinaryFloatDataType x) {
        print(SQLKeyWord.BINARY_FLOAT);
        return false;
    }

    @Override
    public boolean visit(SQLBinaryDoubleDataType x) {
        print(SQLKeyWord.BINARY_DOUBLE);
        return false;
    }

    public void printNumericDataType(ISQLNumericDataType x) {
        if (x.isUnsigned()) {
            printSpaceAfterValue(SQLKeyWord.UNSIGNED);
        }

        if (x.isZerofill()) {
            printSpaceAfterValue(SQLKeyWord.ZEROFILL);
        }
    }


    @Override
    public boolean visit(SQLBoolDataType x) {
        print(SQLKeyWord.BOOL);
        return false;
    }

    @Override
    public boolean visit(SQLBooleanDataType x) {
        print(SQLKeyWord.BOOLEAN);
        return false;
    }

    @Override
    public boolean visit(SQLDateDataType x) {
        print(SQLKeyWord.DATE);
        return false;
    }

    @Override
    public boolean visit(SQLDateTimeDataType x) {
        print(SQLKeyWord.DATETIME);
        printAccept(x.getArguments(), ", ", true);

        printSpaceAfterValue(x.getWithTimeZoneType());
        return false;
    }

    @Override
    public boolean visit(SQLTimeDataType x) {
        print(SQLKeyWord.TIME);
        printAccept(x.getArguments(), ", ");
        printSpaceAfterValue(x.getWithTimeZoneType());
        return false;
    }

    @Override
    public boolean visit(SQLTimestampDataType x) {
        print(SQLKeyWord.TIMESTAMP);
        printAccept(x.getArguments(), ", ", true);
        printSpaceAfterValue(x.getWithTimeZoneType());
        return false;
    }

    @Override
    public boolean visit(SQLYearDataType x) {
        print(SQLKeyWord.YEAR);
        printAccept(x.getArguments(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLIntervalDataType x) {
        print(SQLKeyWord.INTERVAL);
        printSpaceAfterValue(x.getUnit());

        printSpaceAfterAccept(x.getArguments(), ", ", true);

        SQLIntervalUnit toUnit = x.getToUnit();
        if (toUnit != null) {
            printSpaceAfterValue(SQLKeyWord.TO);
            printSpaceAfterValue(toUnit);

            printSpaceAfterAccept(x.getToPrecisions(), ", ", true);
        }

        return false;
    }


    @Override
    public boolean visit(SQLRefCursorDataType x) {
        print(SQLKeyWord.REF);
        printSpaceAfterValue(SQLKeyWord.CURSOR);
        return false;
    }

    @Override
    public boolean visit(SQLRefDataType x) {
        print(SQLKeyWord.REF);
        printSpaceAfterAccept(x.getArguments(), ", ", x.isParen());

        ISQLName tableName = x.getTableName();
        if (tableName != null) {
            printSpaceAfterValue(SQLKeyWord.SCOPE);
            printSpaceAfterAccept(tableName);
        }

        return false;
    }

    @Override
    public boolean visit(SQLAnyDataDataType x) {
        if (x.isSysOwner()) {
            print(SQLKeyWord.SYS);
            print('.');
        }
        print(SQLKeyWord.ANYDATA);
        return false;
    }

    @Override
    public boolean visit(SQLAnyTypeDataType x) {
        if (x.isSysOwner()) {
            print(SQLKeyWord.SYS);
            print('.');
        }
        print(SQLKeyWord.ANYTYPE);
        return false;
    }

    @Override
    public boolean visit(SQLAnyDataSetDataType x) {
        if (x.isSysOwner()) {
            print(SQLKeyWord.SYS);
            print('.');
        }
        print(SQLKeyWord.ANYDATASET);
        return false;
    }

    @Override
    public boolean visit(SQLUriTypeDataType x) {
        print(SQLKeyWord.URITYPE);
        return false;
    }

    @Override
    public boolean visit(SQLXmlTypeDataType x) {
        print(SQLKeyWord.XMLTYPE);
        return false;
    }

    @Override
    public boolean visit(SQLGeometryCollectionDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLGeometryDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLLineStringDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLMultiLineStringDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLMultiPointDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLMultiPolygonDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLPointDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLPolygonDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSDOGeometryDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSDOGeoRasterDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSDOTopoGeometryDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLORDAudioDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLORDDicomDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLORDDocDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLORDImageDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLORDVideoDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSIAverageColorDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSIColorDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSIColorHistogramDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSIFeatureListDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSIPositionalColorDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSIStillImageDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLSITextureDataType x) {
        return false;
    }

    @Override
    public boolean visit(SQLPercentTypeDataType x) {
        printAccept(x.getName());
        print(isLowerCase() ? "%type" : "%TYPE");
        return false;
    }

    @Override
    public boolean visit(SQLPercentRowTypeDataType x) {
        printAccept(x.getName());
        print(isLowerCase() ? "%rowtype" : "%ROWTYPE");
        return false;
    }

    @Override
    public boolean visit(SQLDataType x) {
        printAccept(x.getName());
        if (x.isParen()) {
            print('(');
            printSpaceAfterAccept(x.getArguments(), ", ");
            print(')');
        }

        return false;
    }


    // ---------------------------- DataType End ------------------------------------


    // ---------------------------- Function Start ------------------------------------

    @Override
    public boolean visit(SQLMethodInvocation x) {
        printAccept(x.getName());

        boolean paren = x.isParen();
        if (!paren) {
            return false;
        }

        print('(');

        List<ISQLExpr> arguments = x.getArguments();
        for (int i = 0; i < arguments.size(); i++) {
            ISQLExpr argument = arguments.get(i);

            if (i != 0) {
                print(", ");
            }

            if (i == 0) {
                SQLSetQuantifier setQuantifier = x.getSetQuantifier();
                if (setQuantifier != null) {
                    print(setQuantifier);
                    printSpace();
                }

                printAccept(argument);

                printSpaceAfterAccept(x.getDefaultOnConversionError());

                continue;
            }

            printAccept(argument);
        }

        print(')');
        return false;
    }

    @Override
    public boolean visit(SQLCastFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLCharFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLChrFunction x) {
        print(SQLKeyWord.CHR);

        print('(');
        printAccept(x.getArguments(), ", ");

        if (x.getUsingValue() != null) {
            printSpaceAfterValue(SQLKeyWord.USING);
            printSpaceAfterAccept(x.getUsingValue());
        }
        print(')');

        return false;
    }

    @Override
    public boolean visit(SQLCollectionMethodInvocation x) {
        return false;
    }

    @Override
    public boolean visit(SQLConvertUsingFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLCubeTableFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLExtractFunction x) {
        print(SQLKeyWord.EXTRACT);
        print('(');
        print(x.getUnit());
        printSpaceAfterValue(SQLKeyWord.FROM);
        printSpaceAfterAccept(x.getArguments().get(0));
        print(')');
        return false;
    }

    @Override
    public boolean visit(SQLFirstFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLLastFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLListAggFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLPositionFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLSubStrFromFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLSubStringFromFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLTranslateUsingFunction x) {
        print(SQLKeyWord.TRANSLATE);

        print('(');
        printAccept(x.getArguments(), ", ");

        if (x.getUsingValue() != null) {
            printSpaceAfterValue(SQLKeyWord.USING);
            printSpaceAfterAccept(x.getUsingValue());
        }
        print(')');

        return false;
    }

    @Override
    public boolean visit(SQLTreatFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLTrimFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLValidateConversionFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLWeightStringFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLDataMiningFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLXmlCastFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLXmlColAttValFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLXmlElementFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLJsonFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAggregateFunction x) {
        return false;
    }

    @Override
    public boolean visit(SQLWindowFunction x) {
        return false;
    }


    // ---------------------------- Function End ------------------------------------


    // ---------------------------- DDL Start ------------------------------------

    @Override
    public boolean visit(SQLCommentOnAuditPolicyStatement x) {
        print(isLowerCase() ? "comment on audit policy" : "COMMENT ON AUDIT POLICY");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCommentOnColumnStatement x) {
        print(isLowerCase() ? "comment on column" : "COMMENT ON COLUMN");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCommentOnDatabaseStatement x) {
        print(isLowerCase() ? "comment on database" : "COMMENT ON DATABASE");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCommentOnEditionStatement x) {
        print(isLowerCase() ? "comment on edition" : "COMMENT ON EDITION");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCommentOnIndexStatement x) {
        print(isLowerCase() ? "comment on index" : "COMMENT ON INDEX");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCommentOnIndexTypeStatement x) {
        print(isLowerCase() ? "comment on indextype" : "COMMENT ON INDEXTYPE");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCommentOnMaterializedViewStatement x) {
        print(isLowerCase() ? "comment on materialized view" : "COMMENT ON MATERIALIZED VIEW");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCommentOnMiningModelStatement x) {
        print(isLowerCase() ? "comment on mining model" : "COMMENT ON MINING MODEL");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCommentOnOperatorStatement x) {
        print(isLowerCase() ? "comment on operator" : "COMMENT ON OPERATOR");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCommentOnRoleStatement x) {
        print(isLowerCase() ? "comment on role" : "COMMENT ON ROLE");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCommentOnSequenceStatement x) {
        print(isLowerCase() ? "comment on sequence" : "COMMENT ON SEQUENCE");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCommentOnServerStatement x) {
        print(isLowerCase() ? "comment on server" : "COMMENT ON SERVER");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCommentOnTablespaceStatement x) {
        print(isLowerCase() ? "comment on tablespace" : "COMMENT ON TABLESPACE");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCommentOnTableStatement x) {
        print(isLowerCase() ? "comment on table" : "COMMENT ON TABLE");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCommentOnTypeStatement x) {
        print(isLowerCase() ? "comment on type" : "COMMENT ON TYPE");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCommentOnViewStatement x) {
        print(isLowerCase() ? "comment on view" : "COMMENT ON VIEW");

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IS);

        printSpaceAfterAccept(x.getComment());
        return false;
    }

    @Override
    public boolean visit(SQLCreateDatabaseStatement x) {
        print(SQLKeyWord.CREATE);
        printSpaceAfterValue(SQLKeyWord.DATABASE);

        if (x.isIfNotExists()) {
            printSpaceAfterValue(isLowerCase() ? "if not exists" : "IF NOT EXISTS");
        }

        printSpaceAfterAccept(x.getName());

        printIndentLnAccept(x.getActions());

        return false;
    }

    @Override
    public boolean visit(SQLAlterDatabaseStatement x) {
        print(SQLKeyWord.ALTER);
        printSpaceAfterValue(SQLKeyWord.DATABASE);
        printSpaceAfterAccept(x.getName());
        printIndentLnAccept(x.getItems());
        return false;
    }

    @Override
    public boolean visit(SQLDropDatabaseStatement x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(SQLKeyWord.DATABASE);

        if (x.isIfExists()) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }

        printSpaceAfterAccept(x.getName());

        return false;
    }

    @Override
    public boolean visit(SQLCreateDatabaseLinkStatement x) {
        print(SQLKeyWord.CREATE);

        if (x.isShared()) {
            printSpaceAfterValue(SQLKeyWord.SHARED);
        }

        if (x.isPublic_()) {
            printSpaceAfterValue(SQLKeyWord.PUBLIC);
        }
        printSpaceAfterValue(isLowerCase() ? "database link" : "DATABASE LINK");

        printSpaceAfterAccept(x.getName());

        printIndentLnAccept(x.getActions());

        ISQLExpr using = x.getUsing();
        if (using != null) {
            this.incrementIndent();
            printlnAfterValue(SQLKeyWord.USING);
            printSpaceAfterAccept(using);
            this.decrementIndent();
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterDatabaseLinkStatement x) {
        print(SQLKeyWord.ALTER);
        if (x.isShared()) {
            printSpaceAfterValue(SQLKeyWord.SHARED);
        }

        if (x.isPublic_()) {
            printSpaceAfterValue(SQLKeyWord.PUBLIC);
        }
        printSpaceAfterValue(isLowerCase() ? "database link" : "DATABASE LINK");

        printSpaceAfterAccept(x.getName());
        printIndentLnAccept(x.getAction());
        return false;
    }

    @Override
    public boolean visit(SQLDropDatabaseLinkStatement x) {
        print(SQLKeyWord.DROP);
        if (x.isPublic_()) {
            printSpaceAfterValue(SQLKeyWord.PUBLIC);
        }
        printSpaceAfterValue(isLowerCase() ? "database link" : "DATABASE LINK");
        printSpaceAfterAccept(x.getName());
        return false;
    }


    @Override
    public boolean visit(SQLCreateDomainStatement x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterDomainStatement x) {
        return false;
    }

    @Override
    public boolean visit(SQLDropDomainStatement x) {
        return false;
    }

    @Override
    public boolean visit(SQLCreateEventStatement x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterEventStatement x) {
        return false;
    }

    @Override
    public boolean visit(SQLDropEventStatement x) {
        return false;
    }

    @Override
    public boolean visit(SQLCreateFunctionStatement x) {
        print(SQLKeyWord.CREATE);

//        printSpaceAfterValue(x.getOrReplace());
        printSpaceAfterValue(x.getEditionAbleType());

        if (x.isAggregate()) {
            printSpaceAfterValue(SQLKeyWord.AGGREGATE);
        }

//        printSpaceAfterAccept(x.getDefinerOptionExpr());

        printSpaceAfterValue(SQLKeyWord.FUNCTION);

        printSpaceAfterAccept(x.getName());

        printSpaceAndLnAndAccept(x.getParameters(), ",", true);

        ISQLDataType returnDataType = x.getReturnDataType();
        if (returnDataType != null) {
            printSpaceAfterValue(SQLKeyWord.RETURN);
            printSpaceAfterAccept(returnDataType);
        }

        List<ISQLExpr> options = x.getOptions();
        if (options != null
                && options.size() > 0) {
            println();
            printlnAndAccept(options);
        }

        SQLASType as = x.getAs();
        if (as != null) {
            printlnAfterValue(as);
        }

        this.incrementIndent();
        List<ISQLExpr> declareSections = x.getDeclareSections();
        if (declareSections != null
                && declareSections.size() > 0) {
            println();
            printlnAndAccept(declareSections);
        }

        ISQLExpr asExpr = x.getAsExpr();
        if (asExpr != null) {
            printlnAndAccept(asExpr);
        }
        this.decrementIndent();

        return false;
    }

    @Override
    public boolean visit(SQLAlterFunctionStatement x) {
        print(SQLKeyWord.ALTER);
        printSpaceAfterValue(SQLKeyWord.FUNCTION);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterAccept(x.getAction());
        return false;
    }

    @Override
    public boolean visit(SQLDropFunctionStatement x) {
        print(SQLKeyWord.DROP);

        printSpaceAfterValue(SQLKeyWord.FUNCTION);

        if (x.isIfExists()) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }

        printSpaceAfterAccept(x.getName());

        printSpaceAfterAccept(x.getParameters(), ", ", true);

//        if (x.getOption() != null) {
//            printSpaceAfterValue(x.getOption().name);
//        }

        return false;
    }

    @Override
    public boolean visit(SQLCreateIndexStatement x) {
        print(SQLKeyWord.CREATE);

//        printSpaceAfterValue(x.getCategory());

        printSpaceAfterValue(SQLKeyWord.INDEX);

        if (x.isConcurrently()) {
            printSpaceAfterValue(SQLKeyWord.CONCURRENTLY);
        }

        if (x.isIfNotExists()) {
            printSpaceAfterValue(isLowerCase() ? "if not exists" : "IF NOT EXISTS");
        }

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.ON);

        if (x.isCluster()) {
//            printSpaceAfterValue(SQLKeyWord.CLUSTER);
        }

//        printSpaceAfterAccept(x.getTableReference());

//        printSpaceAfterAccept(x.getColumns(), ", ", true);

        printlnAndAccept(x.getFromClause());
        printlnAndAccept(x.getWhereClause());


        List<ISQLExpr> properties = x.getProperties();
        if (properties != null
                && properties.size() > 0) {
            println();
            printlnAndAccept(properties);
        }

//        printlnAfterValue(x.getUsable());
//        printlnAfterValue(x.getInvalidation());

        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexStatement x) {
        print(SQLKeyWord.ALTER);
        printSpaceAfterValue(SQLKeyWord.INDEX);

        if (x.isIfExists()) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }

        printSpaceAfterAccept(x.getName());

        ISQLName onName = x.getOnName();
        if (onName != null) {
            printSpaceAfterValue(SQLKeyWord.ON);
            printSpaceAfterAccept(onName);
        }

        printIndentLnAccept(x.getActions());
        return false;
    }

    @Override
    public boolean visit(SQLDropIndexStatement x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(SQLKeyWord.INDEX);

        if (x.isConcurrently()) {
            printSpaceAfterValue(SQLKeyWord.CONCURRENTLY);
        }

        if (x.isIfExists()) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }

        printSpaceAfterAccept(x.getNames(), ", ");

        ISQLExpr table = x.getTable();
        if (table != null) {
            printSpaceAfterValue(SQLKeyWord.ON);
            printSpaceAfterAccept(x.getTable());
        }

        if (x.isOnline()) {
            printSpaceAfterValue(SQLKeyWord.ONLINE);
        }

        if (x.isForce()) {
            printSpaceAfterValue(SQLKeyWord.FORCE);
        }

//        printSpaceAfterValue(x.getInvalidation());
//
//        printSpaceAfterValue(x.getCascade());

        printSpaceAfterAccept(x.getOptions(), " ");

        return false;
    }

    @Override
    public boolean visit(SQLCreateMaterializedViewStatement x) {
        print(SQLKeyWord.CREATE);
        printSpaceAfterValue(isLowerCase() ? "materialized view" : "MATERIALIZED VIEW");
        printSpaceAfterAccept(x.getName());

        printSpaceAndLnAndAccept(x.getColumns(), ",", true);

        ISQLDataType ofDataType = x.getOfDataType();
        if (ofDataType != null) {
            printSpaceAfterValue(SQLKeyWord.OF);
            printSpaceAfterAccept(ofDataType);
        }

        printSpaceAndLnAndAccept(x.getColumnConstraints(), ",", true);

//        printlnAndAccept(x.getCollationExpr());

        List<ISQLExpr> properties = x.getProperties();
        if (properties != null
                && properties.size() > 0) {
            println();
            printlnAndAccept(properties);
        }


//        printlnAndAccept(x.getUsingIndex());
//        printlnAndAccept(x.getCreateMVRefresh());
//        printlnAndAccept(x.getEvaluationEditionClause());
//        printlnAndAccept(x.getOnQueryComputationClause());
//        printlnAndAccept(x.getQueryRewriteClause());

        printlnAfterValue(SQLKeyWord.AS);
//        printIndentLnAccept(x.getAsSubQuery());

        return false;
    }

    @Override
    public boolean visit(SQLAlterMaterializedViewStatement x) {
        print(SQLKeyWord.ALTER);
        printSpaceAfterValue(isLowerCase() ? "materialized view" : "MATERIALIZED VIEW");
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLDropMaterializedViewStatement x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(isLowerCase() ? "materialized view" : "MATERIALIZED VIEW");

        if (x.isIfExists()) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }

        printSpaceAfterAccept(x.getNames(), ", ");

        SQLDropMaterializedViewStatement.SQLOption option = x.getOption();
        if (option != null) {
            printSpaceAfterValue(option.name);
        }
        return false;
    }

    @Override
    public boolean visit(SQLCreatePackageStatement x) {
        print(SQLKeyWord.CREATE);

        if (x.isOrReplace()) {
            printSpaceAfterValue(isLowerCase() ? "or replace" : "OR REPLACE");
        }

        SQLEditionAbleType editionAbleType = x.getEditionAbleType();
        if (editionAbleType != null) {
            printSpaceAfterValue(editionAbleType);
        }

        printSpaceAfterValue(SQLKeyWord.PACKAGE);

        printSpaceAfterAccept(x.getName());

        printIndentLnAccept(x.getOptions());

        printlnAfterValue(x.getAs());

        this.incrementIndent();
        println();
        printlnAndAccept(x.getItems());

        this.decrementIndent();
        println();

        print(SQLKeyWord.END);
        printSpaceAfterAccept(x.getEndName());


        return false;
    }

    @Override
    public boolean visit(SQLAlterPackageStatement x) {
        print(SQLKeyWord.ALTER);
        printSpaceAfterValue(SQLKeyWord.PACKAGE);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterAccept(x.getAction());
        return false;
    }

    @Override
    public boolean visit(SQLDropPackageStatement x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(SQLKeyWord.PACKAGE);
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLCreatePackageBodyStatement x) {
        print(SQLKeyWord.CREATE);

        if (x.isOrReplace()) {
            printSpaceAfterValue(isLowerCase() ? "or replace" : "OR REPLACE");
        }

        SQLEditionAbleType editionAbleType = x.getEditionAbleType();
        if (editionAbleType != null) {
            printSpaceAfterValue(editionAbleType);
        }

        printSpaceAfterValue(isLowerCase() ? "package body" : "PACKAGE BODY");

        printSpaceAfterAccept(x.getName());

        printlnAfterValue(x.getAs());

        this.incrementIndent();
        println();
        printlnAndAccept(x.getDeclareSections());

        List<SQLLabelStatement> statements = x.getStatements();
        if (statements != null
                && statements.size() > 0) {
            printlnAfterValue(SQLKeyWord.BEGIN);
            printlnAndAccept(statements);

            List<SQLExceptionHandler> exceptionHandlers = x.getExceptionHandlers();
            if (exceptionHandlers != null
                    && exceptionHandlers.size() > 0) {
                printlnAfterValue(SQLKeyWord.EXCEPTION);
                printIndentLnAccept(x.getExceptionHandlers());
            }
        }


        this.decrementIndent();
        println();

        print(SQLKeyWord.END);
        printSpaceAfterAccept(x.getEndName());

        return false;
    }

    @Override
    public boolean visit(SQLAlterPackageBodyStatement x) {
        print(SQLKeyWord.ALTER);
        printSpaceAfterValue(isLowerCase() ? "package body" : "PACKAG BODY");

        return false;
    }

    @Override
    public boolean visit(SQLDropPackageBodyStatement x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(isLowerCase() ? "package body" : "PACKAGE BODY");
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLCreateProcedureStatement x) {
        print(SQLKeyWord.CREATE);

        if (x.isOrReplace()) {
            printSpaceAfterValue(isLowerCase() ? "or replace" : "OR REPLACE");
        }

//        SQLEditionAbleType editionAbleType = x.getEditionAbleType();
//        if (editionAbleType != null) {
//            printSpaceAfterValue(editionAbleType.name);
//        }

        printSpaceAfterValue(SQLKeyWord.PROCEDURE);
        printSpaceAfterAccept(x.getName());

        printSpaceAndLnAndAccept(x.getParameters(), ",", true);


        printlnAndAccept(x.getSharingClause());

        List<ISQLExpr> options = x.getOptions();
        if (options != null
                && options.size() > 0) {
            printlnAndAccept(options);
        }

        SQLASType as = x.getAs();
        if (as != null) {
            printlnAfterValue(as);
        }

        this.incrementIndent();

        List<ISQLExpr> declareSections = x.getDeclareSections();
        if (declareSections != null
                && declareSections.size() > 0) {
            println();
            printlnAndAccept(declareSections);
        }

        printlnAndAccept(x.getAsExpr());

        this.decrementIndent();

        return false;
    }

    @Override
    public boolean visit(SQLAlterProcedureStatement x) {
        print(SQLKeyWord.ALTER);
        printSpaceAfterValue(SQLKeyWord.PROCEDURE);

        printSpaceAfterAccept(x.getName());
        printSpaceAfterAccept(x.getAction());
        return false;
    }

    @Override
    public boolean visit(SQLDropProcedureStatement x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(SQLKeyWord.PROCEDURE);

        if (x.isIfExists()) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLCreateRoleStatement x) {
        print(SQLKeyWord.CREATE);
        printSpaceAfterValue(SQLKeyWord.ROLE);
        printSpaceAfterAccept(x.getNames(), ", ");
        printIndentLnAccept(x.getActions());
        return false;
    }

    @Override
    public boolean visit(SQLAlterRoleStatement x) {
        print(SQLKeyWord.ALTER);
        printSpaceAfterValue(SQLKeyWord.ROLE);
        printSpaceAfterAccept(x.getName());
        printIndentLnAccept(x.getActions());
        return false;
    }

    @Override
    public boolean visit(SQLDropRoleStatement x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(SQLKeyWord.ROLE);

        if (x.isIfExists()) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }

        printSpaceAfterAccept(x.getNames(), ", ");

        return false;
    }

    @Override
    public boolean visit(SQLCreateSchemaStatement x) {
        print(SQLKeyWord.CREATE);
        printSpaceAfterValue(SQLKeyWord.SCHEMA);

        if (x.isIfNotExists()) {
            printSpaceAfterValue(isLowerCase() ? "if not exists" : "IF NOT EXISTS");
        }

        printSpaceAfterAccept(x.getName());
        printIndentLnAccept(x.getActions());
        return false;
    }

    @Override
    public boolean visit(SQLAlterSchemaStatement x) {
        print(SQLKeyWord.ALTER);
        printSpaceAfterValue(SQLKeyWord.SCHEMA);
        printSpaceAfterAccept(x.getName());
        printIndentLnAccept(x.getActions());
        return false;
    }

    @Override
    public boolean visit(SQLDropSchemaStatement x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(SQLKeyWord.SCHEMA);

        if (x.isIfExists()) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }

        printSpaceAfterAccept(x.getNames(), ", ");

        return false;
    }

    @Override
    public boolean visit(SQLCreateSequenceStatement x) {
        print(SQLKeyWord.CREATE);

        SQLCreateSequenceStatement.SQLScope scope = x.getScope();
        if (scope != null) {
            printSpaceAfterValue(scope.name);
        }

        printSpaceAfterValue(SQLKeyWord.SEQUENCE);

        printSpaceAfterAccept(x.getName());
        printSpaceAfterAccept(x.getSharingClause());

        printIndentLnAccept(x.getOptions());

        return false;
    }

    @Override
    public boolean visit(SQLAlterSequenceStatement x) {
        print(SQLKeyWord.ALTER);

        printSpaceAfterValue(SQLKeyWord.SEQUENCE);

        boolean ifExists = x.isIfExists();
        if (ifExists) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }

        printSpaceAfterAccept(x.getName());
        printIndentLnAccept(x.getActions());

        return false;
    }

    @Override
    public boolean visit(SQLDropSequenceStatement x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(SQLKeyWord.SEQUENCE);

        if (x.isIfExists()) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }

        printSpaceAfterAccept(x.getNames(), ", ");

//        SQLCascadeType dropBehavior = x.getDropBehavior();
//        if (dropBehavior != null) {
//            printSpaceAfterValue(dropBehavior.name);
//        }

        return false;
    }

    @Override
    public boolean visit(SQLCreateSynonymStatement x) {
        print(SQLKeyWord.CREATE);

        if (x.isOrReplace()) {
            printSpaceAfterValue(isLowerCase() ? "or replace" : "OR REPLACE");
        }

        if (x.isPublic_()) {
            printSpaceAfterValue(SQLKeyWord.PUBLIC);
        }

        printSpaceAfterValue(SQLKeyWord.SYNONYM);

        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.FOR);
        printSpaceAfterAccept(x.getForName());

        return false;
    }

    @Override
    public boolean visit(SQLAlterSynonymStatement x) {
        print(SQLKeyWord.ALTER);

        if (x.isPublic_()) {
            printSpaceAfterValue(SQLKeyWord.PUBLIC);
        }

        printSpaceAfterValue(SQLKeyWord.SYNONYM);

        printSpaceAfterAccept(x.getName());
        printSpaceAfterAccept(x.getAction());

        return false;
    }

    @Override
    public boolean visit(SQLDropSynonymStatement x) {
        print(SQLKeyWord.DROP);

        if (x.isPublic_()) {
            printSpaceAfterValue(SQLKeyWord.PUBLIC);
        }

        printSpaceAfterValue(SQLKeyWord.SYNONYM);

        if (x.isIfExists()) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }

        printSpaceAfterAccept(x.getName());

        if (x.isForce()) {
            printSpaceAfterValue(SQLKeyWord.FORCE);
        }

        return false;
    }

    @Override
    public boolean visit(SQLCreateTableStatement x) {
        print(SQLKeyWord.CREATE);

        printSpaceAfterValue(x.getScope());

        printSpaceAfterValue(SQLKeyWord.TABLE);

        if (x.isIfNotExists()) {
            printSpaceAfterValue(isLowerCase() ? "if not exists" : "IF NOT EXISTS");
        }

        printSpaceAfterAccept(x.getName());
        printSpaceAfterAccept(x.getSharingClause());

        ISQLDataType ofDataType = x.getOfDataType();
        if (ofDataType != null) {
            printSpaceAfterValue(SQLKeyWord.OF);
            printSpaceAfterAccept(ofDataType);
        }

//        if (x.getObjectTableSubstitution()) {
//
//        }
//        printlnAndAccept(x.getObjectTableSubstitution());

        printTableElements(x);

        printlnAndAccept(x.getCollationExpr());

//        OracleSQLXmlTypeStorage xmlTypeStorage = x.getXmlTypeStorage();
//        if (xmlTypeStorage != null) {
//            printlnAfterValue(SQLKeyWord.XMLTYPE);
//            printSpaceAfterAccept(x.getXmlTypeStorage());
//        }

//        printlnAndAccept(x.getXmlSchemaSpec());
//        printlnAndAccept(x.getXmlTypeVirtualColumn());
//
        printlnAfterValue(x.getCommitActionDefinition());
        printlnAfterValue(x.getCommitActionRows());

        printlnAndAccept(x.getOidClause());
        printlnAndAccept(x.getOidIndexClause());

        List<ISQLExpr> properties = x.getProperties();
        if (properties != null
                && properties.size() > 0) {
            println();
            printlnAndAccept(properties);
        }


//        printlnAfterValue(x.getReadOnly());
//        printlnAfterValue(x.getIndexingOn());

        printlnAndAccept(x.getPartitionBy());

//        printlnAfterValue(x.getCache());
        printlnAndAccept(x.getResultCache());
//        printlnAndAccept(x.getParallelClause());
//        printlnAfterValue(x.getRowDependencies());

        List<ISQLEnableDisableClause> enableDisableClauses = x.getEnableDisableClauses();
        if (enableDisableClauses != null
                && enableDisableClauses.size() > 0) {
            println();
            printlnAndAccept(enableDisableClauses);
        }
        printlnAndAccept(x.getRowMovementClause());
        printlnAndAccept(x.getFlashbackArchiveClause());
        if (x.isRowArchival()) {
            printlnAfterValue(isLowerCase() ? "row archival" : "ROW ARCHIVAL");
        }
//        printlnAndAccept(x.getForExchangeWithTableClause());

        ISQLSelectQuery subQuery = x.getSubQuery();
        if (subQuery != null) {
            if (x.isAs()) {
                printlnAfterValue(SQLKeyWord.AS);
            }
            printIndentLnAccept(x.getSubQuery());
        }


//        if (x.isMemOptimizeForRead()) {
//            printlnAfterValue(SQLKeyWord.MEMOPTIMIZE_FOR_READ);
//        }

        ISQLExpr parentTable = x.getParentTable();
        if (parentTable != null) {
//            printlnAfterValue(SQLKeyWord.PARENT);
            printSpaceAfterAccept(parentTable);

        }
        return false;
    }

    public void printTableElements(SQLCreateTableStatement x) {
        List<ISQLTableElement> tableElements = x.getTableElements();
        int size = tableElements.size();
        if (size == 0) {
            return;
        }

        printSpace();

        boolean paren = x.isTableElementsParen() || size > 1;
        if (paren) {
            print('(');
            this.incrementIndent();
            println();
        }

        for (int i = 0; i < size; ++i) {
            ISQLTableElement element = tableElements.get(i);

            if (i > 0) {
                print(',');
                println();
            }

            element.accept(this);
        }

        if (paren) {
            this.decrementIndent();
            println();
            print(')');
        }
    }

    @Override
    public boolean visit(SQLAlterTableStatement x) {
        print(SQLKeyWord.ALTER);
        printSpaceAfterValue(SQLKeyWord.TABLE);
        printSpaceAfterAccept(x.getTableReference());

        printIndentLnAccept(x.getActions());

        printSpaceAfterAccept(x.getPartitionBy());
        return false;
    }

    @Override
    public boolean visit(SQLDropTableStatement x) {
        print(SQLKeyWord.DROP);

        if (x.isTemporary()) {
            printSpaceAfterValue(SQLKeyWord.TEMPORARY);
        }

        printSpaceAfterValue(SQLKeyWord.TABLE);

        if (x.isIfExists()) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }

        printSpaceAfterAccept(x.getNames(), ", ");

        printSpaceAfterValue(x.getDropBehavior());

        if (x.isPurge()) {
            printSpaceAfterValue(SQLKeyWord.PURGE);
        }

        return false;
    }

    @Override
    public boolean visit(SQLRenameTableStatement x) {
        print(SQLKeyWord.RENAME);
        printSpaceAfterValue(SQLKeyWord.TABLE);

        printSpaceAfterAccept(x.getItems(), ", ");
        return false;
    }

    @Override
    public boolean visit(SQLTruncateTableStatement x) {
        print(SQLKeyWord.TRUNCATE);
        if (x.isTable()) {
            printSpaceAfterValue(SQLKeyWord.TABLE);
        }
        if (x.isOnly()) {
            printSpaceAfterValue(SQLKeyWord.ONLY);
        }
        printSpaceAfterAccept(x.getNames(), ", ");

        printSpaceAfterValue(x.getMaterializedViewLog());
        printSpaceAfterValue(x.getStorage());
//        printSpaceAfterValue(x.getCascade());

        return false;
    }

    @Override
    public boolean visit(SQLCreateTriggerStatement x) {
        print(SQLKeyWord.CREATE);

        if (x.isOrReplace()) {
            printSpaceAfterValue(isLowerCase() ? "or replace" : "OR REPLACE");
        }

//        SQLEditionAbleType editionAbleType = x.getEditionAbleType();
//        if (editionAbleType != null) {
//            printSpaceAfterValue(editionAbleType.name);
//        }

//        printSpaceAfterAccept(x.getDefinerExpr());

        printSpaceAfterValue(SQLKeyWord.TRIGGER);

        printSpaceAfterAccept(x.getName());

        this.incrementIndent();

        printlnAfterValue(x.getActionTime());
        printIndentLnAccept(x.getEvents(), " OR");

        ISQLExpr onExpr = x.getOnExpr();
        if (onExpr != null) {
            printlnAfterValue(SQLKeyWord.ON);

            ISQLExpr nestedTableColumn = x.getNestedTableColumn();
            if (nestedTableColumn != null) {
                printSpaceAfterValue(SQLKeyWord.NESTED);
                printSpaceAfterValue(SQLKeyWord.TABLE);
                printSpaceAfterAccept(nestedTableColumn);
                printSpaceAfterValue(SQLKeyWord.OF);
            }

            printSpaceAfterAccept(onExpr);
        }

        printlnAndAccept(x.getReferencingClause());

        printlnAfterValue(x.getForEachType());
        printlnAfterValue(x.getEditionType());

        printlnAndAccept(x.getOrderingClause());

        ISQLExpr whenCondition = x.getWhenCondition();
        if (whenCondition != null) {
            printlnAfterValue(SQLKeyWord.WHEN);
            printSpaceAfterValue('(');
            whenCondition.accept(this);
            print(')');
        }

        printlnAndAccept(x.getTriggerBody());

        this.decrementIndent();

        return false;
    }

    @Override
    public boolean visit(SQLAlterTriggerStatement x) {
        print(SQLKeyWord.ALTER);
        printSpaceAfterValue(SQLKeyWord.TRIGGER);
        printSpaceAfterAccept(x.getName());

        ISQLName onTableName = x.getOnTableName();
        if (onTableName != null) {
            printSpaceAfterValue(SQLKeyWord.ON);
            printSpaceAfterAccept(onTableName);
        }

        printSpaceAfterAccept(x.getAction());

        return false;
    }

    @Override
    public boolean visit(SQLDropTriggerStatement x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(SQLKeyWord.TRIGGER);

        if (x.isIfExists()) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }

        printSpaceAfterAccept(x.getName());

        ISQLName onTableName = x.getOnTableName();
        if (onTableName != null) {
            printSpaceAfterValue(SQLKeyWord.ON);
            printSpaceAfterAccept(onTableName);
        }
        printSpaceAfterValue(x.getDropBehavior());

        return false;
    }

    @Override
    public boolean visit(SQLCreateTypeStatement x) {
        print(SQLKeyWord.CREATE);

        if (x.isOrReplace()) {
            printSpaceAfterValue(isLowerCase() ? "or replace" : "OR REPLACE");
        }

//        SQLEditionAbleType editionAbleType = x.getEditionAbleType();
//        if (editionAbleType != null) {
//            printSpaceAfterValue(editionAbleType);
//        }

        printSpaceAfterValue(SQLKeyWord.TYPE);

        printSpaceAfterAccept(x.getName());


        if (x.isForce()) {
            printSpaceAfterValue(SQLKeyWord.FORCE);
        }

        ISQLExpr oIdLiteral = x.getOidLiteral();
        if (oIdLiteral != null) {
            printSpaceAfterValue(SQLKeyWord.OID);
            printSpaceAfterAccept(oIdLiteral);
        }

//        printIndentLnAccept(x.getSharingClause());

        List<ISQLExpr> beforeOptions = x.getProperties();
        if (beforeOptions != null
                && beforeOptions.size() > 0) {
            printSpaceAfterAccept(beforeOptions, " ");
        }

//        printSpaceAfterAccept(x.getObjectSubDataType());

//        SQLASType as = x.getAs();
//        if (as != null) {
//            printSpaceAfterValue(as.name);
//        }

        printSpaceAfterAccept(x.getAsDataType());

        printlnAndAccept(x.getExternalNameClause());

        printSpaceAndLnAndAccept(x.getAttributeDefinitions(), ",", true);

        List<ISQLExpr> afterOptions = x.getOptions();
        if (afterOptions != null
                && afterOptions.size() > 0) {
            println();
            printlnAndAccept(afterOptions);
        }

        return false;
    }

    @Override
    public boolean visit(SQLAlterTypeStatement x) {
        print(SQLKeyWord.ALTER);

        printSpaceAfterValue(SQLKeyWord.TYPE);
        printSpaceAfterAccept(x.getName());

        printIndentLnAccept(x.getActions(), ",");
        return false;
    }

    @Override
    public boolean visit(SQLDropTypeStatement x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(SQLKeyWord.TYPE);

        printSpaceAfterAccept(x.getNames(), ", ");

        SQLDropTypeStatement.SQLDropTypeOption option = x.getOption();
        if (option != null) {
            printSpaceAfterValue(option);
        }
        return false;
    }

    @Override
    public boolean visit(SQLCreateTypeBodyStatement x) {
        print(SQLKeyWord.CREATE);

        if (x.isOrReplace()) {
            printSpaceAfterValue(isLowerCase() ? "or replace" : "OR REPLACE");
        }

//        SQLEditionAbleType editionAbleType = x.getEditionAbleType();
//        if (editionAbleType != null) {
//            printSpaceAfterValue(editionAbleType.name);
//        }

        printSpaceAfterValue(isLowerCase() ? "type body" : "TYPE BODY");

        printSpaceAfterAccept(x.getName());

//        SQLASType as = x.getAs();
//        if (as != null) {
//            printSpaceAfterValue(as.name);
//        }

        printIndentLnAccept(x.getItems());

        printlnAfterValue(SQLKeyWord.END);
        return false;
    }

    @Override
    public boolean visit(SQLAlterTypeBodyStatement x) {
        print(SQLKeyWord.ALTER);
        printSpaceAfterValue(isLowerCase() ? "type body" : "TYPE BODY");
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLDropTypeBodyStatement x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(isLowerCase() ? "type body" : "TYPE BODY");
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLCreateUserStatement x) {
        print(SQLKeyWord.CREATE);
        printSpaceAfterValue(SQLKeyWord.USER);
//        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLAlterUserStatement x) {
        print(SQLKeyWord.ALTER);
        printSpaceAfterValue(SQLKeyWord.USER);
//        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLDropUserStatement x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(SQLKeyWord.USER);
//        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLCreateViewStatement x) {
        print(SQLKeyWord.CREATE);
        if (x.isOrReplace()) {
            printSpaceAfterValue(isLowerCase() ? "or replace" : "OR REPLACE");
        }

        printSpaceAfterValue(x.getForce());
        printSpaceAfterValue(x.getEditionAble());
//
        printSpaceAfterAccept(x.getAlgorithmSetOptionExpr());
        printSpaceAfterAccept(x.getDefinerSetOptionExpr());
        printSpaceAfterValue(x.getSecurityType());

        printSpaceAfterValue(SQLKeyWord.VIEW);
        printSpaceAfterAccept(x.getName());

        printSpaceAfterAccept(x.getSharingClause());

        ISQLDataType ofDataType = x.getOfDataType();
        if (ofDataType != null) {
            printSpaceAfterValue(SQLKeyWord.OF);
            printSpaceAfterAccept(ofDataType);
        }

        printlnAndAccept(x.getXmlSchemaSpec());
        printlnAndAccept(x.getSubView());

        printSpaceAndLnAndAccept(x.getColumns(), ",", true);

//        printlnAndAccept(x.getCollationExpr());
        printlnAfterValue(x.getBequeath());

        printlnAfterValue(SQLKeyWord.AS);

        this.incrementIndent();

        printlnAndAccept(x.getSubQuery());
        printlnAndAccept(x.getSubQueryRestriction());
        printlnAfterValue(x.getContainer());

        this.decrementIndent();

        return false;
    }

    @Override
    public boolean visit(SQLAlterViewStatement x) {
        print(SQLKeyWord.ALTER);

        printSpaceAfterAccept(x.getAlgorithmOptionExpr());
        printSpaceAfterAccept(x.getDefinerOptionExpr());
        printSpaceAfterValue(x.getSecurityType());

        printSpaceAfterValue(SQLKeyWord.VIEW);

        if (x.isIfExists()) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }
        printSpaceAfterAccept(x.getName());

        printSpaceAndLnAndAccept(x.getColumns(), ",", true);

        printIndentLnAccept(x.getAction());

        if (x.getSubQuery() != null) {
            printlnAfterValue(SQLKeyWord.AS);
            this.incrementIndent();
            printlnAndAccept(x.getSubQuery());
            printlnAndAccept(x.getSubQueryRestriction());
            this.decrementIndent();
        }

        return false;
    }

    @Override
    public boolean visit(SQLDropViewStatement x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(SQLKeyWord.VIEW);

        if (x.isIfExists()) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }

        printSpaceAfterAccept(x.getNames(), ", ");

        printSpaceAfterValue(x.getBehavior());
        return false;
    }

    // ---------------------------- DDL End ------------------------------------


    // ---------------------------- DML Start ------------------------------------

    @Override
    public boolean visit(SQLCallStatement x) {
        print(SQLKeyWord.CALL);
        printSpaceAfterAccept(x.getExpr());

        ISQLExpr into = x.getInto();
        if (into != null) {
            printSpaceAfterValue(SQLKeyWord.INTO);
            printSpaceAfterAccept(x.getInto());
        }

        return false;
    }

    @Override
    public boolean visit(SQLDeleteStatement x) {
        SQLWithClause withClause = x.getWithClause();
        if (withClause != null) {
            printAccept(withClause);
            println();
        }

        print(SQLKeyWord.DELETE);

        if (x.isLowPriority()) {
            printSpaceAfterValue(SQLKeyWord.LOW_PRIORITY);
        }

        if (x.isQuick()) {
            printSpaceAfterValue(SQLKeyWord.QUICK);
        }

        if (x.isIgnore()) {
            printSpaceAfterValue(SQLKeyWord.IGNORE);
        }

        printSpaceAfterAccept(x.getMultipleTableReference());

        if (x.isFrom()) {
            printSpaceAfterValue(SQLKeyWord.FROM);
        }

        printSpaceAfterAccept(x.getTableReference());

        printSpaceAfterAccept(x.getPartitionExtensionClause());

        if (x.getUsingTableReference() != null) {
            printIndentLnAfterValue(SQLKeyWord.USING);
            printSpaceAfterAccept(x.getUsingTableReference());
        }

        printlnAndAccept(x.getUsingClause());

        printlnAndAccept(x.getWhereClause());

        printlnAndAccept(x.getOrderByClause());

        printlnAndAccept(x.getLimitClause());

//        printlnAndAccept(x.getReturningClause());
//
//        printlnAndAccept(x.getErrorLoggingClause());

        return false;
    }

    @Override
    public boolean visit(SQLExplainStatement x) {
        return false;
    }

    @Override
    public boolean visit(SQLInsertStatement x) {
        SQLWithClause withClause = x.getWithClause();
        if (withClause != null) {
            printAccept(withClause);
            println();
        }

        print(SQLKeyWord.INSERT);

        SQLInsertStatement.SQLPriority priority = x.getPriority();
        if (priority != null) {
            printSpaceAfterValue(priority);
        }

        if (x.isIgnore()) {
            printSpaceAfterValue(SQLKeyWord.IGNORE);
        }

        if (x.isInto()) {
            printSpaceAfterValue(SQLKeyWord.INTO);
        }


        printSpaceAfterAccept(x.getTableReference());

        printSpaceAfterAccept(x.getPartitionExtensionClause());

        printSpaceAfterAccept(x.getColumns(), ", ", true);

        printlnAndAccept(x.getValuesClause());

        if (x.getOnDuplicateKeyUpdateAssignments().size() > 0) {
            printlnAfterValue(isLowerCase() ? "on duplicate key update" : "ON DUPLICATE KEY UPDATE");
            printSpaceAfterAccept(x.getOnDuplicateKeyUpdateAssignments(), ", ");
        }

        printlnAndAccept(x.getReturningClause());

        printlnAndAccept(x.getErrorLoggingClause());

        return false;
    }

    @Override
    public boolean visit(SQLMultiInsertStatement x) {
        print(SQLKeyWord.INSERT);
        printSpaceAfterAccept(x.getInsertClause());
        printlnAndAccept(x.getSubQuery());
        return false;
    }

    @Override
    public boolean visit(SQLLockTableStatement x) {
        return false;
    }

    @Override
    public boolean visit(SQLMergeStatement x) {
        return false;
    }

    @Override
    public boolean visit(SQLSelectStatement x) {
        printAccept(x.getQuery());
        return false;
    }

    @Override
    public boolean visit(SQLSelectIntoStatement x) {
        return false;
    }

    @Override
    public boolean visit(SQLUpdateStatement x) {
        SQLWithClause withClause = x.getWithClause();
        if (withClause != null) {
            printAccept(withClause);
            println();
        }

        print(SQLKeyWord.UPDATE);

        if (x.isLowPriority()) {
            printSpaceAfterValue(SQLKeyWord.LOW_PRIORITY);
        }

        if (x.isIgnore()) {
            printSpaceAfterValue(SQLKeyWord.IGNORE);
        }

        printSpaceAfterAccept(x.getTableReference());

        printlnAndAccept(x.getUpdateSetClause());

        printlnAndAccept(x.getWhereClause());

        printlnAndAccept(x.getOrderByClause());

        printlnAndAccept(x.getLimitClause());

        printlnAndAccept(x.getReturningClause());

        printlnAndAccept(x.getErrorLoggingClause());

        return false;
    }

    // ---------------------------- DML End ------------------------------------


    // ---------------------------- FCL End ------------------------------------

    @Override
    public boolean visit(SQLAssignmentStatement x) {
        x.getTarget().accept(this);
        printSpaceAfterValue(SQLKeyWord.ASSIGN);
        printSpaceAfterAccept(x.getExpr());
        return false;
    }

    @Override
    public boolean visit(SQLCaseStatement x) {
        print(SQLKeyWord.CASE);
        printSpaceAfterAccept(x.getSelector());

        this.incrementIndent();
        println();
        printlnAndAccept(x.getWhenItems());
        printlnAndAccept(x.getElseClause());

        this.decrementIndent();
        printlnAfterValue(isLowerCase() ? "end case" : "END CASE");
        printSpaceAfterAccept(x.getEndLabel());

        return false;
    }

    @Override
    public boolean visit(SQLCaseStatement.SQLCaseStatementWhenItem x) {
        print(SQLKeyWord.WHEN);
        printSpaceAfterAccept(x.getExpr());

        printSpaceAfterValue(SQLKeyWord.THEN);

        printIndentLnAccept(x.getStatement());

        return false;
    }

    @Override
    public boolean visit(SQLCaseStatement.SQLCaseStatementElseClause x) {
        print(ELSE);
        printIndentLnAccept(x.getStatements());
        return false;
    }

    @Override
    public boolean visit(SQLCloseStatement x) {
        print(SQLKeyWord.CLOSE);

        printSpaceAfterAccept(x.getName());

        return false;
    }

    @Override
    public boolean visit(SQLContinueStatement x) {
        print(SQLKeyWord.CONTINUE);
        printSpaceAfterAccept(x.getName());

        ISQLExpr condition = x.getCondition();
        if (condition != null) {
            printSpaceAfterValue(SQLKeyWord.WHEN);
            printSpaceAfterAccept(condition);
        }

        return false;
    }

    @Override
    public boolean visit(SQLExecuteImmediateStatement x) {
        print(isLowerCase() ? "execute immediate" : "EXECUTE IMMEDIATE");
        printSpaceAfterAccept(x.getDynamicSQLStmt());

        if (x.isBulkCollect()) {
            printSpaceAfterValue(isLowerCase() ? "bulk collect" : "BULK COLLECT");
        }
        printSpaceAfterAccept(x.getIntoItems(), ", ");
        printIndentLnAccept(x.getUsingClause());

        return false;
    }

    @Override
    public boolean visit(SQLExitStatement x) {
        print(SQLKeyWord.EXIT);
        printSpaceAfterAccept(x.getName());

        ISQLExpr condition = x.getCondition();
        if (condition != null) {
            printSpaceAfterValue(SQLKeyWord.WHEN);
            printSpaceAfterAccept(condition);
        }
        return false;
    }

    @Override
    public boolean visit(SQLFetchStatement x) {
        print(SQLKeyWord.FETCH);
        printSpaceAfterAccept(x.getDirection());
        printSpaceAfterValue(x.getFromType());
        printSpaceAfterAccept(x.getName());

        if (x.isBulkCollect()) {
            printSpaceAfterValue(isLowerCase() ? "bulk collect" : "BULK COLLECT");
        }

        printSpaceAfterValue(SQLKeyWord.INTO);
        printSpaceAfterAccept(x.getIntoItems(), ", ");

        ISQLExpr limitExpr = x.getLimitExpr();
        if (limitExpr != null) {
            printSpaceAfterValue(SQLKeyWord.LIMIT);
            printSpaceAfterAccept(limitExpr);
        }
        return false;
    }

    @Override
    public boolean visit(SQLForAllStatement x) {
        print(SQLKeyWord.FORALL);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.IN);
        printSpaceAfterAccept(x.getBoundsClause());
        if (x.isBeforeSaveExceptions()) {
            printIndentLnAfterValue(isLowerCase()?"save exceptions":"SAVE EXCEPTIONS");
        }
        printIndentLnAccept(x.getStatement());
        if (x.isAfterSaveExceptions()) {
            printIndentLnAfterValue(isLowerCase()?"save exceptions":"SAVE EXCEPTIONS");
        }
        return false;
    }

    @Override
    public boolean visit(SQLForAllStatement.SQLBoundsRangeClause x) {
        printAccept(x.getLower());
        printSpaceAfterValue(SQLKeyWord.DOTDOT);
        printSpaceAfterAccept(x.getUpper());
        return false;
    }

    @Override
    public boolean visit(SQLForAllStatement.SQLBoundsIndicesOfClause x) {
        print(isLowerCase() ? "indices of" : "INDICES OF");
        printSpaceAfterAccept(x.getExpr());

        if (x.getBetween() != null
                && x.getAnd() != null) {
            printSpaceAfterValue(SQLKeyWord.BETWEEN);
            printSpaceAfterAccept(x.getBetween());

            printSpaceAfterValue(SQLKeyWord.AND);
            printSpaceAfterAccept(x.getAnd());
        }
        return false;
    }

    @Override
    public boolean visit(SQLForAllStatement.SQLBoundsValueOfClause x) {
        print(isLowerCase() ? "values of" : "VALUES OF");
        printSpaceAfterAccept(x.getExpr());
        return false;
    }

    @Override
    public boolean visit(SQLForLoopStatement x) {
        print(SQLKeyWord.FOR);
        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.IN);
        printSpaceAfterAccept(x.getLower());
        printSpaceAfterValue(SQLKeyWord.DOTDOT);
        printSpaceAfterAccept(x.getUpper());

        if (x.getBy() != null) {
            printSpaceAfterValue(SQLKeyWord.BY);
            printSpaceAfterAccept(x.getBy());
        }
        printSpaceAfterValue(SQLKeyWord.LOOP);

        printIndentLnAccept(x.getStatements());

        print(SQLKeyWord.END);
        printSpaceAfterValue(SQLKeyWord.LOOP);
        printSpaceAfterAccept(x.getEndLabel());

        return false;
    }

    @Override
    public boolean visit(SQLGotoStatement x) {
        print(SQLKeyWord.GOTO);
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLIfStatement x) {
        print(SQLKeyWord.IF);
        printSpaceAfterAccept(x.getCondition());

        printSpaceAfterValue(SQLKeyWord.THEN);
        incrementIndent();
        println();
        printlnAndAccept(x.getStatements());
        decrementIndent();


        List<SQLIfStatement.SQLElseIf> elseIfs = x.getElseIfs();
        if (elseIfs.size() > 0) {
            println();
            printlnAndAccept(elseIfs);
        }

        List<ISQLObject> elseStatements = x.getElseStatements();
        if (elseStatements != null
                && elseStatements.size() > 0) {
            printlnAfterValue(ELSE);
            printIndentLnAccept(elseStatements);
        }

        printlnAfterValue(isLowerCase() ? "end if" : "END IF");
        return false;
    }

    @Override
    public boolean visit(SQLIterateStatement x) {
        return false;
    }

    @Override
    public boolean visit(SQLLeaveStatement x) {
        return false;
    }

    @Override
    public boolean visit(SQLLoopStatement x) {
        ISQLName beginLabel = x.getBeginLabel();
        if (beginLabel != null) {
            printAccept(beginLabel);
            printSpace();
        }

        print(SQLKeyWord.LOOP);

        printIndentLnAccept(x.getStatements());

        println();
        printlnAfterValue(isLowerCase() ? "end loop" : "END LOOP");
        printSpaceAfterAccept(x.getEndLabel());

        return false;
    }

    @Override
    public boolean visit(SQLNullStatement x) {
        print(SQLKeyWord.NULL);
        return false;
    }

    @Override
    public boolean visit(SQLOpenForStatement x) {
        print(SQLKeyWord.OPEN);
        printSpaceAfterAccept(x.getOpen());
        printSpaceAfterValue(SQLKeyWord.FOR);

        ISQLExpr forExpr = x.getFor_();
        if (forExpr instanceof SQLSelectQueryExpr) {
            printIndentLnAccept(forExpr);
        } else {
            printSpaceAfterAccept(forExpr);
        }

//        printSpaceAfterAccept(x.getUsingClause());
        return false;
    }

    @Override
    public boolean visit(SQLOpenStatement x) {
        print(SQLKeyWord.OPEN);
        printSpaceAfterAccept(x.getName());
        printSpaceAndLnAndAccept(x.getParameters(), ",", true);
        return false;
    }

    @Override
    public boolean visit(SQLPipeRowStatement x) {
        print(isLowerCase() ? "pipe row" : "PIPE ROW");
        printlnAfterValue("(");
        printAccept(x.getRow());
        print(')');
        return false;
    }

    @Override
    public boolean visit(SQLRaiseStatement x) {
        print(SQLKeyWord.RAISE);
        printSpaceAfterAccept(x.getException());
        return false;
    }

    @Override
    public boolean visit(SQLRepeatStatement x) {
        return false;
    }

    @Override
    public boolean visit(SQLReturnStatement x) {
        print(SQLKeyWord.RETURN);
        printSpaceAfterAccept(x.getExpr());
        return false;
    }

    @Override
    public boolean visit(SQLWhileLoopStatement x) {
        print(SQLKeyWord.WHILE);
        printSpaceAfterAccept(x.getCondition());
        printSpaceAfterValue(SQLKeyWord.LOOP);

        printIndentLnAccept(x.getStatements());

        printlnAfterValue(isLowerCase() ? "end loop" : "END LOOP");
        printSpaceAfterAccept(x.getEndLabel());
        return false;
    }

    @Override
    public boolean visit(SQLWhileStatement x) {
        print(SQLKeyWord.WHILE);
        printSpaceAfterAccept(x.getCondition());
        printSpaceAfterValue(SQLKeyWord.DO);

        printIndentLnAccept(x.getStatements());

        printlnAfterValue(isLowerCase() ? "end while" : "END WHILE");
        printSpaceAfterAccept(x.getEndLabel());
        return false;
    }

    // ---------------------------- FCL End ------------------------------------


    // ---------------------------- Database expr Start ------------------------------------

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLUserSysIdentifiedByAction x) {
        print(isLowerCase() ? "user sys identified by" : "USER SYS IDENTIFIED BY");
        printSpaceAfterAccept(x.getPassword());
        return false;
    }

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLUserSystemIdentifiedByAction x) {
        print(isLowerCase() ? "user system identified by" : "USER SYSTEM IDENTIFIED BY");
        printSpaceAfterAccept(x.getPassword());
        return false;
    }

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLControlfileReuseAction x) {
        print(isLowerCase() ? "CONTROLFILE REUSE" : "CONTROLFILE REUSE");
        return false;
    }

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLMaxDataFilesAction x) {
        print(isLowerCase() ? "maxdatafiles" : "MAXDATAFILES");
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLMaxInstancesAction x) {
        print(isLowerCase() ? "maxinstances" : "MAXINSTANCES");
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLNationalCharacterSetAction x) {
        print(isLowerCase() ? "national character set" : "NATIONAL CHARACTER SET");
        printSpaceAfterAccept(x.getCharset());
        return false;
    }

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLSetDefaultTableSpaceAction x) {
        print(isLowerCase() ? "set default" : "SET DEFAULT");
        printSpaceAfterValue(x.getFileType());
        printSpaceAfterValue(isLowerCase() ? "tablespace" : "TABLESPACE");
        return false;
    }

    @Override
    public boolean visit(ISQLCreateDatabaseAction.SQLUserDataTableSpaceAction x) {
        if (x.getFileType() != null) {
            print(x.getFileType());
            printSpace();
        }
        print(isLowerCase() ? "user_data tablespace" : "USER_DATA TABLESPACE");
        printSpaceAfterAccept(x.getName());

        this.incrementIndent();
        printlnAfterValue(SQLKeyWord.DATAFILE);
        printSpaceAfterAccept(x.getDataFiles(), ", ");
        this.decrementIndent();

        return false;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLLogFileAction x) {
        print(SQLKeyWord.LOGFILE);
        printIndentLnAccept(x.getItems(), ",");
        return false;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLLogFileAction.SQLLogFileActionItem x) {
        if (x.getGroupValue() != null) {
            print(SQLKeyWord.GROUP);
            printSpaceAfterAccept(x.getGroupValue());
            printSpace();
        }
        printAccept(x.getFileSpecification());
        return false;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLMaxLogFilesAction x) {
        print(SQLKeyWord.MAXLOGFILES);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLMaxLogMembersAction x) {
        print(SQLKeyWord.MAXLOGMEMBERS);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLMaxLogHistoryAction x) {
        print(SQLKeyWord.MAXLOGHISTORY);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLArchiveLogAction x) {
        print(SQLKeyWord.ARCHIVELOG);
        return false;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLNoArchiveLogAction x) {
        print(SQLKeyWord.NOARCHIVELOG);
        return false;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLForceLoggingAction x) {
        print(isLowerCase() ? "noarchivelog" : "NOARCHIVELOG");
        return false;
    }

    @Override
    public boolean visit(ISQLDatabaseLoggingClause.SQLSetStandbyNoLoggingAction x) {
        print(isLowerCase() ? "set standby nologging for" : "SET STANDBY NOLOGGING FOR");
        return false;
    }

    @Override
    public boolean visit(ISQLTablespaceClause.SQLExtentManagementLocalClause x) {
        print(isLowerCase() ? "extent management local" : "EXTENT MANAGEMENT LOCAL");
        return false;
    }

    @Override
    public boolean visit(ISQLTablespaceClause.SQLDataFileClause x) {
        print(SQLKeyWord.DATAFILE);
        printSpaceAndLnAndAccept(x.getItems(), ",", false);
        return false;
    }

    @Override
    public boolean visit(ISQLTablespaceClause.SQLSysauxDataFileClause x) {
        print(isLowerCase() ? "sysaux datafile" : "SYSAUX DATAFILE");
        printSpaceAfterAccept(x.getDataFiles(), ", ");
        return false;
    }

    @Override
    public boolean visit(SQLDefaultTablespaceClause x) {
        print(isLowerCase() ? "default tablespace" : "DEFAULT TABLESPACE");
        printSpaceAfterAccept(x.getName());

        if (x.getDataFile() != null) {
            printSpaceAfterValue(SQLKeyWord.DATAFILE);
            printSpaceAfterAccept(x.getDataFile());
        }

        printSpaceAfterAccept(x.getExtentManagementClause());
        return false;
    }

    @Override
    public boolean visit(ISQLDefaultTempTablespaceClause.SQLDefaultTemporaryTablespaceClause x) {
        if (x.getFileType() != null) {
            print(x.getFileType());
            printSpace();
        }
        print(isLowerCase() ? "default temporary tablespace" : "DEFAULT TEMPORARY TABLESPACE");
        printSpaceAfterAccept(x.getName());

        this.incrementIndent();
        if (x.getTempFiles() != null
                && x.getTempFiles().size() > 0) {
            printlnAfterValue(SQLKeyWord.TEMPFILE);
            printIndentLnAccept(x.getTempFiles(), ",", false);
        }

        printlnAndAccept(x.getExtentManagementClause());
        this.decrementIndent();
        return false;
    }

    @Override
    public boolean visit(ISQLDefaultTempTablespaceClause.SQLDefaultLocalTemporaryTablespaceClause x) {
        if (x.getFileType() != null) {
            print(x.getFileType());
            printSpace();
        }
        print(isLowerCase() ? "default local temporary tablespace for" : "DEFAULT LOCAL TEMPORARY TABLESPACE FOR");
        printSpaceAfterValue(x.getForType());
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLExtentManagementClause x) {
        print(isLowerCase() ? "extent management local" : "EXTENT MANAGEMENT LOCAL");
        printSpaceAfterAccept(x.getExpr());
        return false;
    }

    @Override
    public boolean visit(SQLExtentManagementClause.SQLAutoAllocateExpr x) {
        print(isLowerCase() ? "autoallocate" : "AUTOALLOCATE");
        return false;
    }

    @Override
    public boolean visit(SQLExtentManagementClause.SQLUniformExpr x) {
        print(isLowerCase() ? "uniform" : "UNIFORM");
        if (x.getSizeClause() != null) {
            printSpaceAfterValue(isLowerCase() ? "size" : "SIZE");
            printSpaceAfterAccept(x.getSizeClause());
        }
        return false;
    }

    @Override
    public boolean visit(SQLUndoTablespaceClause x) {
        if (x.getFileType() != null) {
            print(x.getFileType());
            printSpace();
        }

        print(isLowerCase() ? "undo tablespace" : "UNDO TABLESPACE");
        printSpaceAfterAccept(x.getName());

        if (x.getDataFiles() != null
                && x.getDataFiles().size() > 0) {
            this.incrementIndent();
            printlnAfterValue(SQLKeyWord.DATAFILE);
            printSpaceAndLnAndAccept(x.getDataFiles(), ",", false);
            this.decrementIndent();
        }
        return false;
    }

    @Override
    public boolean visit(SQLSetTimeZoneClause x) {
        print(isLowerCase() ? "set time_zone = " : "SET TIME_ZONE = ");
        printAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLEnablePluggableDatabaseClause x) {
        print(isLowerCase() ? "enable pluggable database" : "ENABLE PLUGGABLE DATABASE");

        this.incrementIndent();
        if (x.isSeed()) {
            printlnAfterValue(SQLKeyWord.SEED);
        }

        printlnAndAccept(x.getFileNameConvert());

        if (x.getSystem() != null) {
            printlnAfterValue(SQLKeyWord.SYSTEM);
            printSpaceAfterAccept(x.getSystem());
        }

        if (x.getSysaux() != null) {
            printlnAfterValue(SQLKeyWord.SYSAUX);
            printSpaceAfterAccept(x.getSysaux());
        }
        this.decrementIndent();

        return false;
    }

    @Override
    public boolean visit(SQLFileNameConvertClause x) {
        print(SQLKeyWord.FILE_NAME_CONVERT);
        printSpaceAfterValue(SQLKeyWord.EQ);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLFileNameConvertClause.SQLNoneValue x) {
        print(SQLKeyWord.NONE);
        return false;
    }

    @Override
    public boolean visit(SQLFileNameConvertClause.SQLValues x) {
        printlnAndAccept(x.getItems(), ",", true);
        return false;
    }

    @Override
    public boolean visit(SQLFileNameConvertClause.SQLItem x) {
        printAccept(x.getName());
        print(SQLKeyWord.COMMA);
        printSpaceAfterAccept(x.getReplace());
        return false;
    }

    @Override
    public boolean visit(SQLTablespaceDataFileClause x) {
        print(SQLKeyWord.DATAFILES);
        printSpaceAfterAccept(x.getItems(), " ");
        return false;
    }

    @Override
    public boolean visit(SQLTablespaceDataFileClause.SQLSizeSizeClauseItem x) {
        print(SQLKeyWord.SIZE);
        printSpaceAfterAccept(x.getSizeClause());
        return false;
    }

    @Override
    public boolean visit(ISQLUndoModeClause.SQLUndoModeOnClause x) {
        print(isLowerCase() ? "local undo on" : "LOCAL UNDO ON");
        return false;
    }

    @Override
    public boolean visit(ISQLUndoModeClause.SQLUndoModeOffClause x) {
        print(isLowerCase() ? "local undo off" : "LOCAL UNDO OFF");
        return false;
    }

    @Override
    public boolean visit(SQLUpgradeDataDirectoryNameExpr x) {
        print(isLowerCase() ? "upgrade data directory name" : "UPGRADE DATA DIRECTORY NAME");
        return false;
    }

    // ---------------------------- Database expr End ------------------------------------


    // ---------------------------- Database Link expr Start ------------------------------------

    @Override
    public boolean visit(SQLConnectToCurrentUserClause x) {
        print(isLowerCase() ? "connect to" : "CONNECT TO");
        printSpaceAfterValue(SQLKeyWord.CURRENT_USER);
        return false;
    }

    @Override
    public boolean visit(SQLConnectToIdentifiedByClause x) {
        print(isLowerCase() ? "connect to" : "CONNECT TO");
        printSpaceAfterAccept(x.getUser());

        printSpaceAfterValue(isLowerCase() ? "identified by" : "IDENTIFIED BY");
        printSpaceAfterAccept(x.getPassword());
        printlnAndAccept(x.getDbLinkAuthentication());
        return false;
    }

    @Override
    public boolean visit(SQLDBLinkAuthenticationClause x) {
        print(isLowerCase() ? "authenticated by" : "AUTHENTICATED BY");

        printSpaceAfterAccept(x.getUser());

        printSpaceAfterValue(isLowerCase() ? "identified by" : "IDENTIFIED BY");

        printSpaceAfterAccept(x.getPassword());

        return false;
    }

    // ---------------------------- Database Link expr End ------------------------------------


    // ---------------------------- Index expr Start ------------------------------------

    @Override
    public boolean visit(SQLAlterIndexAddPartition x) {
        print(isLowerCase() ? "add partition" : "ADD PARTITION");
        printSpaceAfterAccept(x.getName());
        printSpaceAfterAccept(x.getTableSpaceClause());
        printSpaceAfterAccept(x.getIndexCompression());
        printSpaceAfterAccept(x.getParallelClause());
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexCoalescePartition x) {
        print(isLowerCase() ? "coalesce partition" : "COALESCE PARTITION");
        printSpaceAfterAccept(x.getParallelClause());
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexDropPartition x) {
        print(isLowerCase() ? "drop partition" : "DROP PARTITION");
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifyDefaultAttributes x) {
        print(isLowerCase() ? "modify default attributes" : "MODIFY DEFAULT ATTRIBUTES");
        ISQLExpr partition = x.getPartition();
        if (partition != null) {
            printSpaceAfterValue(isLowerCase() ? "FOR PARTITION" : "FOR PARTITION");
            printSpaceAfterAccept(partition);
        }
        printIndentLnAccept(x.getAttributes());
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifyPartition x) {
        print(isLowerCase() ? "modify partition" : "MODIFY PARTITION");
        printSpaceAfterAccept(x.getName());
        printIndentLnAccept(x.getOptions());
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifyPartition.SQLParametersOption x) {
        print(SQLKeyWord.PARAMETERS);
        printSpaceAfterValue('(');
        printAccept(x.getParameter());
        print(')');
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifyPartition.SQLCoalesceOption x) {
        print(SQLKeyWord.COALESCE);
        if (x.isCleanup()) {
            printSpaceAfterValue(SQLKeyWord.CLEANUP);
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifyPartition.SQLUpdateBlockReferencesOption x) {
        print(isLowerCase() ? "update block references" : "UPDATE BLOCK REFERENCES");
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifyPartition.SQLUnusableOption x) {
        print(SQLKeyWord.UNUSABLE);
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifySubPartition x) {
        print(isLowerCase() ? "modify subpartition" : "MODIFY SUBPARTITION");
        printSpaceAfterAccept(x.getName());
        printSpaceAfterAccept(x.getOption());
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexModifySubPartition.SQLUnusableOption x) {
        print(SQLKeyWord.UNUSABLE);
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexRenamePartition x) {
        print(isLowerCase() ? "rename partition" : "RENAME PARTITION");
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.TO);
        printSpaceAfterAccept(x.getNewName());
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexRenameSubPartition x) {
        print(isLowerCase() ? "rename subpartition" : "RENAME SUBPARTITION");
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.TO);
        printSpaceAfterAccept(x.getNewName());
        return false;
    }

    @Override
    public boolean visit(SQLAlterIndexSplitPartition x) {
        print(isLowerCase() ? "split partition" : "SPLIT PARTITION");
        printSpaceAfterAccept(x.getName());
        List<ISQLExpr> atValues = x.getAtValues();
        if (atValues.size() > 0) {
            printSpaceAfterValue(SQLKeyWord.AT);
            printSpaceAfterAccept(atValues, ", ", true);
        }

        this.incrementIndent();
        List<SQLPartitionDefinition> partitions = x.getPartitions();
        if (partitions.size() > 0) {
            printlnAfterValue(SQLKeyWord.INTO);
            printSpaceAndLnAndAccept(partitions, ",", true);
        }
        printlnAndAccept(x.getParallelClause());
        this.decrementIndent();
        return false;
    }


    // ---------------------------- Index expr End ------------------------------------


    // ---------------------------- Package expr Start ------------------------------------

    @Override
    public boolean visit(ISQLAlterPackageAction.SQLAlterPackageEditionAbleAction x) {
        print(SQLKeyWord.EDITIONABLE);
        return false;
    }

    @Override
    public boolean visit(ISQLAlterPackageAction.SQLAlterPackageNoneEditionAbleAction x) {
        print(SQLKeyWord.NONEDITIONABLE);
        return false;
    }

    // ---------------------------- Package expr End ------------------------------------


    // ---------------------------- Role expr Start ------------------------------------

    @Override
    public boolean visit(SQLRoleNotIdentifiedAction x) {
        print(SQLKeyWord.NOT);
        printSpaceAfterValue(SQLKeyWord.IDENTIFIED);
        return false;
    }

    @Override
    public boolean visit(SQLRoleIdentifiedByAction x) {
        print(SQLKeyWord.IDENTIFIED);
        printSpaceAfterValue(SQLKeyWord.BY);
        printSpaceAfterAccept(x.getPassword());
        return false;
    }

    @Override
    public boolean visit(SQLRoleIdentifiedUsingAction x) {
        print(SQLKeyWord.IDENTIFIED);
        printSpaceAfterValue(SQLKeyWord.USING);
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLRoleIdentifiedExternallyAction x) {
        print(SQLKeyWord.IDENTIFIED);
        printSpaceAfterValue(SQLKeyWord.EXTERNALLY);
        return false;
    }

    @Override
    public boolean visit(SQLRoleIdentifiedGloballyAction x) {
        print(SQLKeyWord.IDENTIFIED);
        printSpaceAfterValue(SQLKeyWord.GLOBALLY);
        ISQLExpr asName = x.getAsName();
        if (asName != null) {
            printSpaceAfterValue(SQLKeyWord.AS);
            printSpaceAfterAccept(asName);
        }
        return false;
    }

    @Override
    public boolean visit(AbstractSQLRoleAction.SQLContainerClause x) {
        print(SQLKeyWord.CONTAINER);
        printSpaceAfterValue(SQLKeyWord.EQ);
        printSpaceAfterValue(x.getType());
        return false;
    }

    // ---------------------------- Role expr End ------------------------------------

    // ---------------------------- Sequence expr Start ------------------------------------

    @Override
    public boolean visit(SQLSequenceStartWithOption x) {
        print(SQLKeyWord.START);
        printSpaceAfterValue(SQLKeyWord.WITH);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLSequenceIncrementByOption x) {
        print(SQLKeyWord.INCREMENT);
        printSpaceAfterValue(SQLKeyWord.BY);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLSequenceMaxValueOption x) {
        print(SQLKeyWord.MAXVALUE);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLSequenceNoMaxValueOption x) {
        print(SQLKeyWord.NOMAXVALUE);
        return false;
    }

    @Override
    public boolean visit(SQLSequenceMinValueOption x) {
        print(SQLKeyWord.MINVALUE);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLSequenceNoMinValueOption x) {
        print(SQLKeyWord.NOMINVALUE);
        return false;
    }

    @Override
    public boolean visit(SQLSequenceCycleOption x) {
        print(SQLKeyWord.CYCLE);
        return false;
    }

    @Override
    public boolean visit(SQLSequenceNoCycleOption x) {
        print(SQLKeyWord.NOCYCLE);
        return false;
    }

    @Override
    public boolean visit(SQLSequenceCacheOption x) {
        print(SQLKeyWord.CACHE);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(SQLSequenceNoCacheOption x) {
        print(SQLKeyWord.NOCACHE);
        return false;
    }

    @Override
    public boolean visit(SQLSequenceOrderOption x) {
        print(SQLKeyWord.ORDER);
        return false;
    }

    @Override
    public boolean visit(SQLSequenceNoOrderOption x) {
        print(SQLKeyWord.NOORDER);
        return false;
    }

    @Override
    public boolean visit(SQLSequenceKeepOption x) {
        print(SQLKeyWord.KEEP);
        return false;
    }

    @Override
    public boolean visit(SQLSequenceNoKeepOption x) {
        print(SQLKeyWord.NOKEEP);
        return false;
    }

    @Override
    public boolean visit(SQLSequenceScaleOption x) {
        print(SQLKeyWord.SCALE);
        printSpaceAfterValue(x.getType());
        return false;
    }

    @Override
    public boolean visit(SQLSequenceNoScaleOption x) {
        print(SQLKeyWord.NOSCALE);
        return false;
    }

    @Override
    public boolean visit(SQLSequenceSessionOption x) {
        print(SQLKeyWord.SESSION);
        return false;
    }

    @Override
    public boolean visit(SQLSequenceGlobalOption x) {
        print(SQLKeyWord.GLOBAL);
        return false;
    }

    // ---------------------------- Sequence expr End ------------------------------------


    // ---------------------------- Synonym expr Start ------------------------------------

    @Override
    public boolean visit(ISQLAlterSynonymAction.SQLAlterSynonymEditionAbleAction x) {
        print(SQLKeyWord.EDITIONABLE);
        return false;
    }

    @Override
    public boolean visit(ISQLAlterSynonymAction.SQLAlterSynonymNonEditionAbleAction x) {
        print(SQLKeyWord.NONEDITIONABLE);
        return false;
    }

    @Override
    public boolean visit(ISQLAlterSynonymAction.SQLAlterSynonymCompileAction x) {
        print(SQLKeyWord.COMPILE);
        return false;
    }

    // ---------------------------- Synonym expr End ------------------------------------

    // ---------------------------- Table expr Start ------------------------------------

    @Override
    public boolean visit(SQLColumnDefinition x) {
        printAccept(x.getName());
        printSpaceAfterAccept(x.getDataType());
//        printSpaceAfterAccept(x.getReferenceScopeCheck());

        printSpaceAfterAccept(x.getDefaultClause());

        printColumnConstraints(x.getColumnConstraints());
//        printSpaceAfterAccept(x.getCollateClause());
//        printSpaceAfterAccept(x.getCommentClause());
        return false;
    }

    public void printColumnConstraints(List<ISQLColumnConstraint> columnConstraints) {
        printSpaceAfterAccept(columnConstraints, " ");
    }

    @Override
    public boolean visit(SQLVirtualColumnDefinition x) {
        printAccept(x.getColumn());
        printSpaceAfterAccept(x.getDataType());
//        printSpaceAfterAccept(x.getCollateClause());
//        printSpaceAfterValue(x.getVisible());

        if (x.isGeneratedAlways()) {
            printSpaceAfterValue(SQLKeyWord.GENERATED);
            printSpaceAfterValue(SQLKeyWord.ALWAYS);
        }
        printSpaceAfterValue(SQLKeyWord.AS);
        printSpaceAfterValue('(');
        printAccept(x.getExpr());
        print(')');

//        printSpaceAfterValue(x.getVirtual());

//        printSpaceAfterAccept(x.getEvaluationEditionClause());
//        printSpaceAfterAccept(x.getUnusableEditionsClause());

        printSpaceAfterAccept(x.getColumnConstraints(), " ");
        return false;
    }

    @Override
    public boolean visit(SQLPrimaryKeyTableConstraint x) {
        ISQLName name = x.getName();
        if (name != null) {
            print(SQLKeyWord.CONSTRAINT);
            printSpaceAfterAccept(name);
            printSpace();
        }

        print(isLowerCase() ? "primary key" : "PRIMARY KEY");
//        printSpaceAfterValue(x.getIndexType());
        printSpaceAfterAccept(x.getColumns(), ", ", true);

        printSpaceAfterAccept(x.getOptions(), " ");
        return false;
    }

    @Override
    public boolean visit(SQLUniqueTableConstraint x) {
        ISQLName name = x.getName();
        if (name != null) {
            print(SQLKeyWord.CONSTRAINT);
            printSpaceAfterAccept(name);
            printSpace();
        }

        print(SQLKeyWord.UNIQUE);
//        printSpaceAfterValue(x.getIndexType());
        printSpaceAfterAccept(x.getIndexName());
//        printSpaceAfterValue(x.getIndexType());

        printSpaceAfterAccept(x.getColumns(), ", ", true);

        printIndentLnAccept(x.getOptions());
        return false;
    }

    @Override
    public boolean visit(SQLForeignKeyTableConstraint x) {
        ISQLName name = x.getName();
        if (name != null) {
            print(SQLKeyWord.CONSTRAINT);
            printSpaceAfterAccept(name);
            printSpace();
        }

        print(SQLKeyWord.FOREIGN);
        printSpaceAfterValue(SQLKeyWord.KEY);
        printSpaceAfterAccept(x.getIndexName());
        printSpaceAfterAccept(x.getReferencingColumns(), ", ", true);

        printSpaceAfterValue(SQLKeyWord.REFERENCES);

        printSpaceAfterAccept(x.getReferencedTable());

        printAccept(x.getReferencedColumns(), ", ", true);

        printIndentLnAccept(x.getOptions());
        return false;
    }

    @Override
    public boolean visit(SQLCheckTableConstraint x) {
        ISQLName name = x.getName();
        if (name != null) {
            print(SQLKeyWord.CONSTRAINT);
            printSpaceAfterAccept(name);
            printSpace();
        }

        print(SQLKeyWord.CHECK);
        printSpaceAfterValue("(");
        printAccept(x.getCondition());
        print(')');

        printIndentLnAccept(x.getOptions());
        return false;
    }

    @Override
    public boolean visit(SQLScopeForTableConstraint x) {
        print(isLowerCase() ? "scope for" : "SCOPE FOR");
        printSpaceAfterValue('(');
        printAccept(x.getRef());
        print(')');

        printSpaceAfterValue(SQLKeyWord.IS);
        printSpaceAfterAccept(x.getScopeTale());
        return false;
    }

    @Override
    public boolean visit(SQLRefWithRowIdTableConstraint x) {
        print(SQLKeyWord.REF);
        printSpaceAfterValue('(');
        printAccept(x.getRef());
        print(')');

        printSpaceAfterValue(isLowerCase() ? "with rowid" : "WITH ROWID");
        return false;
    }

    @Override
    public boolean visit(SQLLikeClause x) {
        print(SQLKeyWord.LIKE);
        printSpaceAfterAccept(x.getName());
        printlnAfterValue(x.getOptions());
        return false;
    }

    @Override
    public boolean visit(SQLNotNullColumnConstraint x) {
        ISQLName name = x.getName();
        if (name != null) {
            print(SQLKeyWord.CONSTRAINT);
            printSpaceAfterAccept(name);
            printSpace();
        }

        print(isLowerCase() ? "not null" : "NOT NULL");

        printSpaceAfterAccept(x.getOptions(), " ");
        return false;
    }

    @Override
    public boolean visit(SQLNullColumnConstraint x) {
        ISQLName name = x.getName();
        if (name != null) {
            print(SQLKeyWord.CONSTRAINT);
            printSpaceAfterAccept(name);
            printSpace();
        }

        print(SQLKeyWord.NULL);

        printSpaceAfterAccept(x.getOptions(), " ");
        return false;
    }

    @Override
    public boolean visit(SQLUniqueColumnConstraint x) {
        ISQLName name = x.getName();
        if (name != null) {
            print(SQLKeyWord.CONSTRAINT);
            printSpaceAfterAccept(name);
            printSpace();
        }

        print(SQLKeyWord.UNIQUE);
        if (x.isKey()) {
            printSpaceAfterValue(SQLKeyWord.KEY);
        }

        printSpaceAfterAccept(x.getOptions(), " ");
        return false;
    }

    @Override
    public boolean visit(SQLPrimaryKeyColumnConstraint x) {
        ISQLName name = x.getName();
        if (name != null) {
            print(SQLKeyWord.CONSTRAINT);
            printSpaceAfterAccept(name);
            printSpace();
        }

        if (x.isPrimary()) {
            print(SQLKeyWord.PRIMARY);
            printSpace();
        }
        print(SQLKeyWord.KEY);

        printSpaceAfterAccept(x.getOptions(), " ");
        return false;
    }

    @Override
    public boolean visit(SQLReferencesColumnConstraint x) {
        ISQLName name = x.getName();
        if (name != null) {
            print(SQLKeyWord.CONSTRAINT);
            printSpaceAfterAccept(name);
            printSpace();
        }

        print(SQLKeyWord.REFERENCES);

        printSpaceAfterAccept(x.getReferencedTable());

        printAccept(x.getReferencedColumns(), ", ", true);

        printSpaceAfterAccept(x.getActions(), " ");

        printSpaceAfterAccept(x.getOptions(), " ");
        return false;
    }

    @Override
    public boolean visit(AbstractSQLReferencesConstraint.SQLOnUpdateAction x) {
        print(SQLKeyWord.ON);
        printSpaceAfterValue(SQLKeyWord.UPDATE);
        printSpaceAfterValue(x.getAction());
        return false;
    }

    @Override
    public boolean visit(AbstractSQLReferencesConstraint.SQLOnDeleteAction x) {
        print(SQLKeyWord.ON);
        printSpaceAfterValue(SQLKeyWord.DELETE);
        printSpaceAfterValue(x.getAction());
        return false;
    }

    @Override
    public boolean visit(SQLCheckColumnConstraint x) {
        ISQLName name = x.getName();
        if (name != null) {
            print(SQLKeyWord.CONSTRAINT);
            printSpaceAfterAccept(name);
            printSpace();
        }

        print(SQLKeyWord.CHECK);
        printSpaceAfterValue('(');
        printAccept(x.getCondition());
        print(')');

        printSpaceAfterAccept(x.getOptions(), " ");
        return false;
    }

    @Override
    public boolean visit(SQLScopeIsColumnConstraint x) {
        print(isLowerCase() ? "scope is" : "SCOPE IS");
        printSpaceAfterAccept(x.getScopeTable());
        return false;
    }

    @Override
    public boolean visit(SQLWithRowIdColumnConstraint x) {
        print(isLowerCase() ? "with rowid" : "WITH ROWID");
        return false;
    }

    @Override
    public boolean visit(ISQLConstraint.SQLColumn x) {
        printAccept(x.getName());
        ISQLExpr length = x.getLength();
        if (length != null) {
            print('(');
            printAccept(length);
            print(')');
        }
        printSpaceAfterValue(x.getOrdering());
        return false;
    }

    @Override
    public boolean visit(SQLDeferrableConstraintState x) {
        print(SQLKeyWord.DEFERRABLE);
        return false;
    }

    @Override
    public boolean visit(SQLNotDeferrableConstraintState x) {
        print(isLowerCase() ? "not deferrable" : "NOT DEFERRABLE");
        return false;
    }

    @Override
    public boolean visit(SQLInitiallyImmediateConstraintState x) {
        print(isLowerCase() ? "initially immediate" : "INITIALLY IMMEDIATE");
        return false;
    }

    @Override
    public boolean visit(SQLInitiallyDeferredConstraintState x) {
        print(isLowerCase() ? "initially deferred" : "INITIALLY DEFERRED");
        return false;
    }

    @Override
    public boolean visit(SQLRelyConstraintState x) {
        print(SQLKeyWord.RELY);
        return false;
    }

    @Override
    public boolean visit(SQLNoRelyConstraintState x) {
        print(SQLKeyWord.NORELY);
        return false;
    }

    @Override
    public boolean visit(SQLEnableConstraintState x) {
        print(SQLKeyWord.ENABLE);
        return false;
    }

    @Override
    public boolean visit(SQLDisableConstraintState x) {
        print(SQLKeyWord.DISABLE);
        return false;
    }

    @Override
    public boolean visit(SQLValidateConstraintState x) {
        print(SQLKeyWord.VALIDATE);
        return false;
    }

    @Override
    public boolean visit(SQLNoValidateConstraintState x) {
        print(SQLKeyWord.NOVALIDATE);
        return false;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityStartWithOption x) {
        return false;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLLimitValueExpr x) {
        return false;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityIncrementByOption x) {
        return false;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityMaxValueOption x) {
        return false;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityNoMaxValueOption x) {
        return false;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityMinValueOption x) {
        return false;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityNoMinValueOption x) {
        return false;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityCycleOption x) {
        return false;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityNoCycleOption x) {
        return false;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityCacheOption x) {
        return false;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityNoCacheOption x) {
        return false;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityOrderOption x) {
        return false;
    }

    @Override
    public boolean visit(ISQLIdentityOption.SQLIdentityNoOrderOption x) {
        return false;
    }

    @Override
    public boolean visit(SQLPartitionByRange x) {
        print(isLowerCase() ? "partition by" : "PARTITION BY");

        if (x.isLinear()) {
            printSpaceAfterValue(SQLKeyWord.LINEAR);
        }

        printSpaceAfterValue(SQLKeyWord.RANGE);
        printSpaceAfterAccept(x.getColumns(), ", ", true);

        ISQLExpr interval = x.getInterval();
        if (interval != null) {
            printSpaceAfterValue(SQLKeyWord.INTERVAL);
            printSpace();
            if (x.isIntervalParen()) {
                print('(');
            }
            printAccept(x.getInterval());
            if (x.isIntervalParen()) {
                print(')');
            }
        }


        ISQLExpr partitionsNum = x.getPartitionsNum();
        if (partitionsNum != null) {
            printSpace();
//            printPartitionsNum(partitionsNum);
        }

        printlnAndAccept(x.getSubPartitionBy());

        printSpaceAndLnAndAccept(x.getPartitions(), ",", true);

        return false;
    }

    @Override
    public boolean visit(SQLPartitionByList x) {
        print(isLowerCase() ? "partition by" : "PARTITION BY");

        if (x.isLinear()) {
            printSpaceAfterValue(SQLKeyWord.LINEAR);
        }

        printSpaceAfterValue(SQLKeyWord.LIST);
        printSpaceAfterAccept(x.getColumns(), ", ", true);

        ISQLExpr partitionsNum = x.getPartitionsNum();
        if (partitionsNum != null) {
            printSpace();
//            printPartitionsNum(partitionsNum);
        }

        printlnAndAccept(x.getSubPartitionBy());

        printSpaceAndLnAndAccept(x.getPartitions(), ",", true);

        return false;
    }

    @Override
    public boolean visit(SQLPartitionByHash x) {
        print(isLowerCase() ? "partition by" : "PARTITION BY");

        if (x.isLinear()) {
            printSpaceAfterValue(SQLKeyWord.LINEAR);
        }

        printSpaceAfterValue(SQLKeyWord.HASH);
        printSpaceAfterAccept(x.getColumns(), ", ", true);

        ISQLExpr partitionsNum = x.getPartitionsNum();
        if (partitionsNum != null) {
            printSpaceAfterValue(SQLKeyWord.PARTITIONS);
            printSpaceAfterAccept(partitionsNum);
        }

        printlnAndAccept(x.getStoreInClause());

        printlnAndAccept(x.getSubPartitionBy());

        printSpaceAndLnAndAccept(x.getPartitions(), ",", true);
        return false;
    }

    @Override
    public boolean visit(SQLPartitionByKey x) {
        print(isLowerCase() ? "partition by" : "PARTITION BY");

        if (x.isLinear()) {
            printSpaceAfterValue(SQLKeyWord.LINEAR);
        }

        printSpaceAfterValue(SQLKeyWord.KEY);
        printSpaceAfterAccept(x.getColumns(), ", ", true);

        ISQLExpr partitionsNum = x.getPartitionsNum();
        if (partitionsNum != null) {
            print(SQLKeyWord.PARTITIONS);
            printSpaceAfterAccept(partitionsNum);
        }

        printlnAndAccept(x.getSubPartitionBy());

        printSpaceAndLnAndAccept(x.getPartitions(), ",", true);

        return false;
    }

    @Override
    public boolean visit(SQLPartitionByRangeColumns x) {
        print(isLowerCase() ? "partition by" : "PARTITION BY");

        if (x.isLinear()) {
            printSpaceAfterValue(SQLKeyWord.LINEAR);
        }

        printSpaceAfterValue(isLowerCase() ? "range columns" : "RANGE COLUMNS");
        printSpaceAfterAccept(x.getColumns(), ", ", true);

        ISQLExpr partitionsNum = x.getPartitionsNum();
        if (partitionsNum != null) {
            print(SQLKeyWord.PARTITIONS);
            printSpaceAfterAccept(partitionsNum);
        }

        printlnAndAccept(x.getSubPartitionBy());

        printSpaceAndLnAndAccept(x.getPartitions(), ",", true);

        return false;
    }

    @Override
    public boolean visit(SQLPartitionByListColumns x) {
        print(isLowerCase() ? "partition by" : "PARTITION BY");

        if (x.isLinear()) {
            printSpaceAfterValue(SQLKeyWord.LINEAR);
        }

        printSpaceAfterValue(isLowerCase() ? "list columns" : "LIST COLUMNS");
        printSpaceAfterAccept(x.getColumns(), ", ", true);

        ISQLExpr partitionsNum = x.getPartitionsNum();
        if (partitionsNum != null) {
            print(SQLKeyWord.PARTITIONS);
            printSpaceAfterAccept(partitionsNum);
        }

        printlnAndAccept(x.getSubPartitionBy());

        printSpaceAndLnAndAccept(x.getPartitions(), ",", true);

        return false;
    }

    @Override
    public boolean visit(SQLPartitionByReference x) {
        print(isLowerCase() ? "partition by" : "PARTITION BY");

        if (x.isLinear()) {
            printSpaceAfterValue(SQLKeyWord.LINEAR);
        }

        printSpaceAfterValue(SQLKeyWord.REFERENCE);
        printSpaceAfterAccept(x.getColumns(), ", ", true);

        ISQLExpr partitionsNum = x.getPartitionsNum();
        if (partitionsNum != null) {
            print(SQLKeyWord.PARTITIONS);
            printSpaceAfterAccept(partitionsNum);
        }

        printlnAndAccept(x.getSubPartitionBy());

        printSpaceAndLnAndAccept(x.getPartitions(), ",", true);
        return false;
    }

    @Override
    public boolean visit(SQLPartitionBySystem x) {
        print(isLowerCase() ? "partition by" : "PARTITION BY");

        if (x.isLinear()) {
            printSpaceAfterValue(SQLKeyWord.LINEAR);
        }

        printSpaceAfterValue(SQLKeyWord.SYSTEM);
        printSpaceAfterAccept(x.getColumns(), ", ", true);

        ISQLExpr partitionsNum = x.getPartitionsNum();
        if (partitionsNum != null) {
            print(SQLKeyWord.PARTITIONS);
            printSpaceAfterAccept(partitionsNum);
        }

        printlnAndAccept(x.getSubPartitionBy());

        printSpaceAndLnAndAccept(x.getPartitions(), ",", true);
        return false;
    }

    @Override
    public boolean visit(SQLPartitionByConsistentHash x) {
        print(isLowerCase() ? "subpartition by" : "SUBPARTITION BY");

        if (x.isLinear()) {
            printSpaceAfterValue(SQLKeyWord.LINEAR);
        }

        printSpaceAfterValue(isLowerCase() ? "consistent hash" : "CONSISTENT HASH");
        printSpaceAfterAccept(x.getColumns(), ", ", true);

        ISQLExpr partitionsNum = x.getPartitionsNum();
        if (partitionsNum != null) {
            print(SQLKeyWord.PARTITIONS);
            printSpaceAfterAccept(partitionsNum);
        }

        printlnAndAccept(x.getSubPartitionBy());

        printSpaceAndLnAndAccept(x.getPartitions(), ",", true);
        return false;
    }

    @Override
    public boolean visit(SQLSubPartitionByRange x) {
        print(isLowerCase() ? "subpartition by" : "SUBPARTITION BY");

        if (x.isLinear()) {
            printSpaceAfterValue(SQLKeyWord.LINEAR);
        }

        printSpaceAfterValue(SQLKeyWord.RANGE);
        printSpaceAfterAccept(x.getColumns(), ", ", true);

        ISQLExpr subpartitionsQuantity = x.getSubpartitionsNum();
        if (subpartitionsQuantity != null) {
            printlnAfterValue(SQLKeyWord.SUBPARTITIONS);
            printSpaceAfterAccept(subpartitionsQuantity);
        }

        printlnAndAccept(x.getSubpartitionTemplate());
        return false;
    }

    @Override
    public boolean visit(SQLSubPartitionByList x) {
        print(isLowerCase() ? "subpartition by" : "SUBPARTITION BY");

        if (x.isLinear()) {
            printSpaceAfterValue(SQLKeyWord.LINEAR);
        }

        printSpaceAfterValue(SQLKeyWord.LIST);
        printSpaceAfterAccept(x.getColumns(), ", ", true);

        this.incrementIndent();
        ISQLExpr subpartitionsQuantity = x.getSubpartitionsNum();
        if (subpartitionsQuantity != null) {
            printlnAfterValue(SQLKeyWord.SUBPARTITIONS);
            printSpaceAfterAccept(subpartitionsQuantity);
        }

        printlnAndAccept(x.getSubpartitionTemplate());
        this.decrementIndent();
        return false;
    }

    @Override
    public boolean visit(SQLSubPartitionByHash x) {
        print(isLowerCase() ? "subpartition by" : "SUBPARTITION BY");

        if (x.isLinear()) {
            printSpaceAfterValue(SQLKeyWord.LINEAR);
        }

        printSpaceAfterValue(SQLKeyWord.HASH);

        printSpaceAfterAccept(x.getColumns(), ", ", true);

        this.incrementIndent();
        ISQLExpr subpartitionsQuantity = x.getSubpartitionsNum();
        if (subpartitionsQuantity != null) {
            printlnAfterValue(SQLKeyWord.SUBPARTITIONS);
            printSpaceAfterAccept(subpartitionsQuantity);
        }

        printlnAndAccept(x.getSubpartitionTemplate());
        this.decrementIndent();
        return false;
    }

    @Override
    public boolean visit(SQLSubPartitionByKey x) {
        print(isLowerCase() ? "subpartition by" : "SUBPARTITION BY");

        if (x.isLinear()) {
            printSpaceAfterValue(SQLKeyWord.LINEAR);
        }

        printSpaceAfterValue(SQLKeyWord.KEY);

        ISQLExpr algorithm = x.getAlgorithm();
        if (algorithm != null) {
            printSpaceAfterValue(SQLKeyWord.ALGORITHM);
            printSpaceAfterValue(SQLKeyWord.EQ);
            printSpaceAfterAccept(algorithm);
        }
        printSpaceAfterAccept(x.getColumns(), ", ", true);

        this.incrementIndent();
        ISQLExpr subpartitionsQuantity = x.getSubpartitionsNum();
        if (subpartitionsQuantity != null) {
            printlnAfterValue(SQLKeyWord.SUBPARTITIONS);
            printSpaceAfterAccept(subpartitionsQuantity);
        }

        printlnAndAccept(x.getSubpartitionTemplate());
        this.decrementIndent();
        return false;
    }

    @Override
    public boolean visit(SQLSubpartitionTemplate x) {
        print(isLowerCase() ? "subpartition template" : "SUBPARTITION TEMPLATE");
        printSpaceAndLnAndAccept(x.getSubPartitions(), ",", true);
        return false;
    }

    @Override
    public boolean visit(SQLPartitionDefinition x) {
        print(SQLKeyWord.PARTITION);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterAccept(x.getValues());


        this.incrementIndent();

        List<ISQLExpr> option = x.getOptions();
        if (option != null
                && option.size() > 0) {
            println();
            printlnAndAccept(x.getOptions());
        }

        ISQLExpr subpartitionsQuantity = x.getSubpartitionsNum();
        if (subpartitionsQuantity != null) {
            printlnAfterValue(SQLKeyWord.SUBPARTITIONS);
            printSpaceAfterAccept(subpartitionsQuantity);

        }
//        printlnAndAccept(x.getStoreInClause());
        this.decrementIndent();

        printSpaceAndLnAndAccept(x.getSubPartitions(), ",", true);
        return false;
    }

    @Override
    public boolean visit(SQLSubPartitionDefinition x) {
        print(SQLKeyWord.SUBPARTITION);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterAccept(x.getValues());

        printIndentLnAccept(x.getOptions());
        return false;
    }

    @Override
    public boolean visit(SQLPartitionValues x) {
        print(SQLKeyWord.VALUES);
        printSpaceAfterAccept(x.getValues(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLPartitionValuesIn x) {
        print(isLowerCase() ? "values in" : "VALUES IN");
        printSpaceAfterAccept(x.getValues(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLPartitionValuesLessThan x) {
        print(isLowerCase() ? "values less than" : "VALUES LESS THAN");
        printSpaceAfterAccept(x.getValues(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLPartitionValuesLessThanMaxValue x) {
        print(isLowerCase() ? "values less than" : "VALUES LESS THAN");
        printSpaceAfterValue(SQLKeyWord.MAXVALUE);
        return false;
    }

    @Override
    public boolean visit(SQLPartitionsetByList x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPartitionsetByRange x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPartitionsetDefinition x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(ISQLEnableDisableClause.SQLEnableConstraintClause x) {
        print(SQLKeyWord.ENABLE);
//        printSpaceAfterValue(x.getValidate());
        printSpaceAfterValue(SQLKeyWord.CONSTRAINT);
        printSpaceAfterAccept(x.getName());

        printlnAndAccept(x.getUsingIndexClause());
        printlnAndAccept(x.getExceptionsClause());
        if (x.isCascade()) {
            printlnAfterValue(SQLKeyWord.CASCADE);
        }
//        printlnAfterValue(x.getKeepIndex());
        return false;
    }

    @Override
    public boolean visit(ISQLEnableDisableClause.SQLEnableUniqueClause x) {
        print(SQLKeyWord.DISABLE);
//        printSpaceAfterValue(x.getValidate());
        printSpaceAfterValue(SQLKeyWord.UNIQUE);
        printSpaceAfterAccept(x.getColumns(), ", ", true);

        printlnAndAccept(x.getUsingIndexClause());
        printlnAndAccept(x.getExceptionsClause());
        if (x.isCascade()) {
            printlnAfterValue(SQLKeyWord.CASCADE);
        }
//        printlnAfterValue(x.getKeepIndex());
        return false;
    }

    @Override
    public boolean visit(ISQLEnableDisableClause.SQLEnablePrimaryKeyClause x) {
        print(SQLKeyWord.DISABLE);
//        printSpaceAfterValue(x.getValidate());
        printSpaceAfterValue(isLowerCase() ? "primary key" : "PRIMARY KEY");

        printlnAndAccept(x.getUsingIndexClause());
        printlnAndAccept(x.getExceptionsClause());
        if (x.isCascade()) {
            printlnAfterValue(SQLKeyWord.CASCADE);
        }
//        printlnAfterValue(x.getKeepIndex());
        return false;
    }

    @Override
    public boolean visit(ISQLEnableDisableClause.SQLDisableConstraintClause x) {
        print(SQLKeyWord.DISABLE);
//        printSpaceAfterValue(x.getValidate());
        printSpaceAfterValue(SQLKeyWord.CONSTRAINT);
        printSpaceAfterAccept(x.getName());

        printlnAndAccept(x.getUsingIndexClause());
        printlnAndAccept(x.getExceptionsClause());
        if (x.isCascade()) {
            printlnAfterValue(SQLKeyWord.CASCADE);
        }
//        printlnAfterValue(x.getKeepIndex());
        return false;
    }

    @Override
    public boolean visit(ISQLEnableDisableClause.SQLDisableUniqueClause x) {
        print(SQLKeyWord.DISABLE);
//        printSpaceAfterValue(x.getValidate());
        printSpaceAfterValue(SQLKeyWord.UNIQUE);
        printSpaceAfterAccept(x.getColumns(), ", ", true);

        printlnAndAccept(x.getUsingIndexClause());
        printlnAndAccept(x.getExceptionsClause());
        if (x.isCascade()) {
            printlnAfterValue(SQLKeyWord.CASCADE);
        }
//        printlnAfterValue(x.getKeepIndex());
        return false;
    }

    @Override
    public boolean visit(ISQLEnableDisableClause.SQLDisablePrimaryKeyClause x) {
        print(SQLKeyWord.DISABLE);
//        printSpaceAfterValue(x.getValidate());
        printSpaceAfterValue(isLowerCase() ? "primary key" : "PRIMARY KEY");

        printlnAndAccept(x.getUsingIndexClause());
        printlnAndAccept(x.getExceptionsClause());
        if (x.isCascade()) {
            printlnAfterValue(SQLKeyWord.CASCADE);
        }
//        printlnAfterValue(x.getKeepIndex());
        return false;
    }


    // ------------------------------------ Alter ------------------------------------

    @Override
    public boolean visit(SQLAlterTableRenameAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableMoveTableAction x) {
        return false;
    }


    // ------------- Column --------------

    @Override
    public boolean visit(SQLAlterTableAddColumnAction x) {
        print(SQLKeyWord.ADD);

        if (x.isColumn()) {
            printSpaceAfterValue(SQLKeyWord.COLUMN);
        }

        if (x.isIfNotExists()) {
            printSpaceAfterValue(isLowerCase() ? "if not exists" : "IF NOT_EXISTS");
        }

        printSpaceAfterAccept(x.getColumns(), ", ", x.isParen());

        List<ISQLExpr> properties = x.getProperties();
        if (properties != null
                && properties.size() > 0) {
            println();
            printlnAndAccept(x.getProperties());
        }

        printlnAndAccept(x.getPartitions(), ",", true);

        return false;
    }

    @Override
    public boolean visit(SQLAlterTableAlterColumnAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableChangeColumnAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableOrderByColumnAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableModifyColumnAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableModifyColumnsAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableRenameColumnAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropColumnAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropColumnsAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropColumnsContinueAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropUnusedColumnsAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableSetUnusedColumnAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableSetUnusedColumnsAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableModifyCollectionRetrievalAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableModifyLobStorageAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableAlterVarrayColPropertyAction x) {
        return false;
    }


    // ------------- Period --------------

    @Override
    public boolean visit(SQLAlterTableAddPeriodAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropPeriodAction x) {
        return false;
    }


    // ------------- Constraint --------------

    @Override
    public boolean visit(SQLAlterTableAddTableConstraintAction x) {

        print(SQLKeyWord.ADD);

        printSpace();
        if (x.isParen()) {
            print('(');
        }

        printAccept(x.getConstraints(), " ");

        if (x.isParen()) {
            print(')');
        }

        if (x.isNotValid()) {
            printSpaceAfterValue(isLowerCase() ? "not valid" : "NOT VALID");
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableAlterConstraintAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableAlterIndexConstraintAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableModifyConstraintAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableModifyUniqueConstraintAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableModifyPrimaryKeyConstraintAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableRenameConstraintAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableRenameIndexConstraintAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableRenameKeyConstraintAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropConstraintAction x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(SQLKeyWord.CONSTRAINT);
        if (x.isIfExists()) {
            printSpaceAfterValue(isLowerCase() ? "if exists" : "IF EXISTS");
        }
        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(x.getDropBehavior());
        printSpaceAfterValue(x.getIndex());
        if (x.isOnline()) {
            printSpaceAfterValue(SQLKeyWord.ONLINE);
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropUniqueConstraintAction x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(SQLKeyWord.UNIQUE);

        printSpaceAfterAccept(x.getColumns(), ", ", true);

        printSpaceAfterValue(x.getDropBehavior());
        printSpaceAfterValue(x.getIndex());
        if (x.isOnline()) {
            printSpaceAfterValue(SQLKeyWord.ONLINE);
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropPrimaryKeyConstraintAction x) {
        print(isLowerCase() ? "modify primary key" : "MODIFY PRIMARY KEY");
        printSpaceAfterValue(x.getDropBehavior());

//        printSpaceAfterAccept(x.getOptions(), " ");

        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropIndexConstraintAction x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(SQLKeyWord.INDEX);
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropKeyConstraintAction x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterValue(SQLKeyWord.KEY);
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableEnableKeyAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDisableKeyAction x) {
        return false;
    }

    // ------------- Partition --------------

    @Override
    public boolean visit(SQLAlterTableAddPartitionAction x) {
        print(SQLKeyWord.ADD);

        this.incrementIndent();
        println();
        printlnAndAccept(x.getPartitions(), ", ");

        ISQLExpr before = x.getBefore();
        if (before != null) {
            printlnAfterValue(SQLKeyWord.BEFORE);
            printSpaceAfterAccept(before);
        }

        printlnAndAccept(x.getDependentTablesClause());
        this.decrementIndent();
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableCoalescePartitionAction x) {
        print(isLowerCase() ? "coalesce partition" : "COALESCE PARTITION");
        printSpaceAfterAccept(x.getExpr());

        printSpaceAfterAccept(x.getUpdateIndex());
//        printSpaceAfterAccept(x.getParallel());
//        printSpaceAfterValue(x.getAllowDisallowClustering());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableReorganizePartitionAction x) {
        print(isLowerCase() ? "reorganize partition" : "REORGANIZE PARTITION");
        printSpaceAfterAccept(x.getNames(), " ");
        printSpaceAfterValue(SQLKeyWord.INTO);

        printSpaceAndLnAndAccept(x.getPartitions(), ",", true);
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableAnalyzePartitionAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableCheckPartitionAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableOptimizePartitionAction x) {
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableRebuildPartitionAction x) {
        print(isLowerCase() ? "rebuild partition" : "REBUILD PARTITION");
        printSpaceAfterAccept(x.getNames(), " ");
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableRepairPartitionAction x) {
        print(SQLKeyWord.REPAIR);
        printSpaceAfterValue(SQLKeyWord.PARTITION);
        printSpaceAfterAccept(x.getNames(), ", ");
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableMovePartitionAction x) {
        print(SQLKeyWord.MOVE);
        printSpaceAfterValue(SQLKeyWord.PARTITION);
        printSpaceAfterAccept(x.getName());

        if (x.isMappingTable()) {
            printSpaceAfterValue(isLowerCase() ? "mapping table" : "MAPPING TABLE");
        }

        List<ISQLExpr> properties = x.getProperties();
        if (properties != null
                && properties.size() > 0) {
            println();
            printlnAndAccept(x.getProperties());
        }

//        printlnAndAccept(x.getFilterCondition());
        printlnAndAccept(x.getUpdateIndexClause());
//        printlnAndAccept(x.getParallelClause());
//        printlnAfterValue(x.getAllowDisallowClustering());
        if (x.isOnline()) {
            printSpaceAfterValue(SQLKeyWord.ONLINE);
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableMovePartitionForAction x) {
        print(SQLKeyWord.MOVE);
        printSpaceAfterValue(SQLKeyWord.PARTITION);
        printSpaceAfterValue(SQLKeyWord.FOR);
        printSpaceAfterAccept(x.getNames(), ", ", true);

        if (x.isMappingTable()) {
            printSpaceAfterValue(isLowerCase() ? "mapping table" : "MAPPING TABLE");
        }

        List<ISQLExpr> properties = x.getProperties();
        if (properties != null
                && properties.size() > 0) {
            println();
            printlnAndAccept(x.getProperties());
        }

//        printlnAndAccept(x.getFilterCondition());
        printlnAndAccept(x.getUpdateIndexClause());
//        printlnAndAccept(x.getParallelClause());
//        printlnAfterValue(x.getAllowDisallowClustering());
        if (x.isOnline()) {
            printSpaceAfterValue(SQLKeyWord.ONLINE);
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableMoveSubPartitionAction x) {
        print(SQLKeyWord.MOVE);
        printSpaceAfterValue(SQLKeyWord.SUBPARTITION);
        printSpaceAfterAccept(x.getName());

//        printSpaceAfterValue(x.getIndexing());
//        printlnAndAccept(x.getPartitioningStorage());
//        printlnAndAccept(x.getUpdateIndex());
//        printlnAndAccept(x.getFilterCondition());
//        printlnAndAccept(x.getParallel());
//        printlnAfterValue(x.getAllowDisallowClustering());
        if (x.isOnline()) {
            printSpaceAfterValue(SQLKeyWord.ONLINE);
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableMoveSubPartitionForAction x) {
        print(SQLKeyWord.MOVE);
        printSpaceAfterValue(SQLKeyWord.SUBPARTITION);
        printSpaceAfterValue(SQLKeyWord.FOR);
        printSpaceAfterAccept(x.getNames(), ", ", true);

//        printSpaceAfterValue(x.getIndexing());
//        printlnAndAccept(x.getPartitioningStorage());
//        printlnAndAccept(x.getUpdateIndex());
//        printlnAndAccept(x.getFilterCondition());
//        printlnAndAccept(x.getParallel());
//        printlnAfterValue(x.getAllowDisallowClustering());
        if (x.isOnline()) {
            printSpaceAfterValue(SQLKeyWord.ONLINE);
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableModifyPartitionAction x) {
        print(SQLKeyWord.MODIFY);
        printSpaceAfterValue(SQLKeyWord.PARTITION);
        printSpaceAfterAccept(x.getName());
//        printSpaceAfterAccept(x.getItems(), " ");
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableModifyPartitionForAction x) {
        print(SQLKeyWord.MODIFY);
        printSpaceAfterValue(SQLKeyWord.PARTITION);
        printSpaceAfterValue(SQLKeyWord.FOR);
        printSpaceAfterAccept(x.getNames(), ", ", true);
//        printSpaceAfterAccept(x.getItems(), " ");
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableModifySubPartitionAction x) {
        print(SQLKeyWord.MODIFY);
        printlnAfterValue(SQLKeyWord.SUBPARTITION);
        printSpaceAfterAccept(x.getName());
//        printSpaceAfterAccept(x.getItems(), " ");
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableModifySubPartitionForAction x) {
        print(SQLKeyWord.MODIFY);
        printSpaceAfterValue(SQLKeyWord.SUBPARTITION);
        printSpaceAfterValue(SQLKeyWord.FOR);
        printSpaceAfterAccept(x.getNames(), ", ", true);
//        printSpaceAfterAccept(x.getItems(), " ");
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableRemovePartitioningAction x) {
        print(SQLKeyWord.REMOVE);
        printSpaceAfterValue(SQLKeyWord.PARTITIONING);
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropPartitionAction x) {
        print(isLowerCase() ? "drop partition" : "DROP PARTITION");
        printSpaceAfterAccept(x.getItems(), ", ");
//        printSpaceAfterAccept(x.getParallel());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropPartitionForAction x) {
        print(isLowerCase() ? "drop partition for" : "DROP PARTITION FOR");
        printSpaceAfterAccept(x.getItems(), ", ");
//        printSpaceAfterAccept(x.getParallel());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropPartitionsAction x) {
        print(isLowerCase() ? "drop partitions" : "DROP PARTITIONS");
        printSpaceAfterAccept(x.getItems(), ", ");
//        printSpaceAfterAccept(x.getParallel());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropPartitionsForAction x) {
        print(isLowerCase() ? "drop partitions for" : "DROP PARTITIONS FOR");
        printSpaceAfterAccept(x.getItems(), ", ");
//        printSpaceAfterAccept(x.getParallel());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropSubPartitionAction x) {
        print(isLowerCase() ? "drop subpartition" : "DROP SUBPARTITION");
        printSpaceAfterAccept(x.getItems(), ", ");
//        printSpaceAfterAccept(x.getParallel());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropSubPartitionForAction x) {
        print(isLowerCase() ? "drop subpartition for" : "DROP SUBPARTITION FOR");
        printSpaceAfterAccept(x.getItems(), ", ");
//        printSpaceAfterAccept(x.getParallel());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropSubPartitionsAction x) {
        print(isLowerCase() ? "drop subpartitions" : "DROP SUBPARTITIONS");
        printSpaceAfterAccept(x.getItems(), ", ");
//        printSpaceAfterAccept(x.getParallel());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDropSubPartitionsForAction x) {
        print(isLowerCase() ? "drop subpartitions for" : "DROP SUBPARTITIONS FOR");
        printSpaceAfterAccept(x.getItems(), ", ");
//        printSpaceAfterAccept(x.getParallel());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableRenamePartitionAction x) {
        print(SQLKeyWord.RENAME);
        printSpaceAfterValue(SQLKeyWord.PARTITION);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.TO);
        printSpaceAfterAccept(x.getNewName());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableRenamePartitionForAction x) {
        print(isLowerCase() ? "rename partition for" : "RENAME PARTITION FOR");
        printSpaceAfterAccept(x.getNames(), ", ", true);
        printSpaceAfterValue(SQLKeyWord.TO);
        printSpaceAfterAccept(x.getNewName());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableRenameSubPartitionAction x) {
        print(SQLKeyWord.RENAME);
        printSpaceAfterValue(SQLKeyWord.SUBPARTITION);
        printSpaceAfterAccept(x.getName());
        printSpaceAfterValue(SQLKeyWord.TO);
        printSpaceAfterAccept(x.getNewName());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableRenameSubPartitionForAction x) {
        print(SQLKeyWord.RENAME);
        printSpaceAfterValue(SQLKeyWord.SUBPARTITION);
        printSpaceAfterAccept(x.getNames(), ", ", true);
        printSpaceAfterValue(SQLKeyWord.TO);
        printSpaceAfterAccept(x.getNewName());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableTruncatePartitionAction x) {
        print(isLowerCase() ? "truncate partition" : "TRUNCATE PARTITION");
        printSpaceAfterAccept(x.getNames(), ", ");

        printSpaceAfterValue(x.getStorageType());
        if (x.isCascade()) {
            printSpaceAfterValue(SQLKeyWord.CASCADE);
        }

        printSpaceAfterAccept(x.getUpdateIndexClause());
//        printSpaceAfterAccept(x.getParallelClause());

        return false;
    }

    @Override
    public boolean visit(SQLAlterTableTruncatePartitionForAction x) {
        print(isLowerCase() ? "truncate partition for" : "TRUNCATE PARTITION FOR");
        printSpaceAfterAccept(x.getNames(), ", ", true);

        printSpaceAfterValue(x.getStorageType());
        if (x.isCascade()) {
            printSpaceAfterValue(SQLKeyWord.CASCADE);
        }

        printSpaceAfterAccept(x.getUpdateIndexClause());
//        printSpaceAfterAccept(x.getParallelClause());

        return false;
    }

    @Override
    public boolean visit(SQLAlterTableTruncatePartitionsAction x) {
        print(isLowerCase() ? "truncate partitions" : "TRUNCATE PARTITIONS");
        printSpaceAfterAccept(x.getNames(), ", ");

        printSpaceAfterValue(x.getStorageType());
        if (x.isCascade()) {
            printSpaceAfterValue(SQLKeyWord.CASCADE);
        }

        printSpaceAfterAccept(x.getUpdateIndexClause());
//        printSpaceAfterAccept(x.getParallelClause());

        return false;
    }

    @Override
    public boolean visit(SQLAlterTableTruncatePartitionsForAction x) {
        print(isLowerCase() ? "truncate partitions for" : "TRUNCATE PARTITIONS FOR");
        printSpaceAfterAccept(x.getNames(), ", ", true);

        printSpaceAfterValue(x.getStorageType());
        if (x.isCascade()) {
            printSpaceAfterValue(SQLKeyWord.CASCADE);
        }

        printSpaceAfterAccept(x.getUpdateIndexClause());
//        printSpaceAfterAccept(x.getParallelClause());

        return false;
    }

    @Override
    public boolean visit(SQLAlterTableTruncateSubPartitionAction x) {
        print(isLowerCase() ? "truncate subpartition" : "TRUNCATE SUBPARTITION");
        printSpaceAfterAccept(x.getNames(), ", ");

        printSpaceAfterValue(x.getStorageType());
        if (x.isCascade()) {
            printSpaceAfterValue(SQLKeyWord.CASCADE);
        }

        printSpaceAfterAccept(x.getUpdateIndexClause());
//        printSpaceAfterAccept(x.getParallelClause());

        return false;
    }

    @Override
    public boolean visit(SQLAlterTableTruncateSubPartitionForAction x) {
        print(isLowerCase() ? "truncate subpartition for" : "TRUNCATE SUBPARTITION FOR");
        printSpaceAfterAccept(x.getNames(), ", ", true);

        printSpaceAfterValue(x.getStorageType());
        if (x.isCascade()) {
            printSpaceAfterValue(SQLKeyWord.CASCADE);
        }

        printSpaceAfterAccept(x.getUpdateIndexClause());
//        printSpaceAfterAccept(x.getParallelClause());

        return false;
    }

    @Override
    public boolean visit(SQLAlterTableTruncateSubPartitionsAction x) {
        print(isLowerCase() ? "truncate subpartitions" : "TRUNCATE SUBPARTITIONS");
        printSpaceAfterAccept(x.getNames(), ", ");

        printSpaceAfterValue(x.getStorageType());
        if (x.isCascade()) {
            printSpaceAfterValue(SQLKeyWord.CASCADE);
        }

//        printSpaceAfterAccept(x.getUpdateIndexClause());
//        printSpaceAfterAccept(x.getParallelClause());

        return false;
    }

    @Override
    public boolean visit(SQLAlterTableTruncateSubPartitionsForAction x) {
        print(isLowerCase() ? "truncate subpartitions for" : "TRUNCATE SUBPARTITIONS FOR");
        printSpaceAfterAccept(x.getNames(), ", ", true);

        printSpaceAfterValue(x.getStorageType());
        if (x.isCascade()) {
            printSpaceAfterValue(SQLKeyWord.CASCADE);
        }

        printSpaceAfterAccept(x.getUpdateIndexClause());
//        printSpaceAfterAccept(x.getParallelClause());

        return false;
    }

    @Override
    public boolean visit(SQLAlterTableSplitPartitionAction x) {
        print(isLowerCase() ? "split partition" : "SPLIT PARTITION");
        printSpaceAfterAccept(x.getName());
        printSpaceAfterAccept(x.getItem());

        printlnAndAccept(x.getSplitNestedTablePart());
//        printlnAndAccept(x.getFilterCondition());
//        printlnAndAccept(x.getDependentTables());
//        printlnAndAccept(x.getUpdateIndex());
//        printlnAndAccept(x.getParallel());
//        printlnAfterValue(x.getAllowDisallowClustering());
        if (x.isOnline()) {
            printlnAfterValue(SQLKeyWord.ONLINE);
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableSplitPartitionForAction x) {
        print(isLowerCase() ? "split partition for" : "SPLIT PARTITION FOR");
        printSpaceAndLnAndAccept(x.getNames(), ",", true);
        printSpaceAfterAccept(x.getItem());

        printlnAndAccept(x.getSplitNestedTablePart());
//        printlnAndAccept(x.getFilterCondition());
//        printlnAndAccept(x.getDependentTables());
//        printlnAndAccept(x.getUpdateIndex());
//        printlnAndAccept(x.getParallel());
//        printlnAfterValue(x.getAllowDisallowClustering());
        if (x.isOnline()) {
            printlnAfterValue(SQLKeyWord.ONLINE);
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableSplitSubPartitionAction x) {
        print(isLowerCase() ? "split subpartition" : "SPLIT SUBPARTITION");
//        printSpaceAndLnAndAccept(x.getNames(), ",", true);
        printSpaceAfterAccept(x.getItem());

        printlnAndAccept(x.getSplitNestedTablePart());
//        printlnAndAccept(x.getFilterCondition());
//        printlnAndAccept(x.getDependentTables());
//        printlnAndAccept(x.getUpdateIndex());
//        printlnAndAccept(x.getParallel());
//        printlnAfterValue(x.getAllowDisallowClustering());
        if (x.isOnline()) {
            printlnAfterValue(SQLKeyWord.ONLINE);
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableSplitSubPartitionForAction x) {
        print(isLowerCase() ? "split subpartition for" : "SPLIT SUBPARTITION FOR");
        printSpaceAndLnAndAccept(x.getNames(), ",", true);
        printSpaceAfterAccept(x.getItem());

        printlnAndAccept(x.getSplitNestedTablePart());
//        printlnAndAccept(x.getFilterCondition());
//        printlnAndAccept(x.getDependentTables());
//        printlnAndAccept(x.getUpdateIndex());
//        printlnAndAccept(x.getParallel());
//        printlnAfterValue(x.getAllowDisallowClustering());
        if (x.isOnline()) {
            printlnAfterValue(SQLKeyWord.ONLINE);
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableMergePartitionsAction x) {
        print(SQLKeyWord.MERGE);
        printSpaceAfterValue(SQLKeyWord.PARTITIONS);
        printSpaceAfterAccept(x.getItems(), ", ");

        SQLPartitionDefinition partition = x.getPartition();
        if (partition != null) {
            printlnAfterValue(SQLKeyWord.INTO);
            printSpaceAfterAccept(partition);
        }

//        printlnAndAccept(x.getFilterCondition());
//        printlnAndAccept(x.getDependentTables());
//        printlnAndAccept(x.getUpdateIndex());
//        printlnAndAccept(x.getParallel());

        if (x.isOnline()) {
            printlnAfterValue(SQLKeyWord.ONLINE);
        }
//        printlnAfterValue(x.getAllowDisallowClustering());

        return false;
    }

    @Override
    public boolean visit(SQLAlterTableMergePartitionsToAction x) {
        print(SQLKeyWord.MERGE);
        printSpaceAfterValue(SQLKeyWord.PARTITIONS);
        printSpaceAfterAccept(x.getItems(), " TO ");

        SQLPartitionDefinition partition = x.getPartition();
        if (partition != null) {
            printlnAfterValue(SQLKeyWord.INTO);
            printSpaceAfterAccept(partition);
        }

//        printlnAndAccept(x.getFilterCondition());
//        printlnAndAccept(x.getDependentTables());
//        printlnAndAccept(x.getUpdateIndex());
//        printlnAndAccept(x.getParallel());

        if (x.isOnline()) {
            printlnAfterValue(SQLKeyWord.ONLINE);
        }
//        printlnAfterValue(x.getAllowDisallowClustering());

        return false;
    }

    @Override
    public boolean visit(SQLAlterTableMergeSubPartitionsAction x) {
        print(SQLKeyWord.MERGE);
        printSpaceAfterValue(SQLKeyWord.PARTITIONS);
        printSpaceAfterAccept(x.getItems(), ", ");

        SQLSubPartitionDefinition subPartition = x.getSubPartition();
        if (subPartition != null) {
            printlnAfterValue(SQLKeyWord.INTO);
            printSpaceAfterAccept(subPartition);
        }

//        printlnAndAccept(x.getFilterCondition)

//        if (x.isOnline()) {
//            printlnAfterValue(SQLKeyWord.ONLINE);
//        }
//        printlnAfterValue(x.getAllowDisallowClustering());

        return false;
    }

    @Override
    public boolean visit(SQLAlterTableMergeSubPartitionsToAction x) {
        print(SQLKeyWord.MERGE);
        printSpaceAfterValue(SQLKeyWord.SUBPARTITIONS);
        printSpaceAfterAccept(x.getItems(), " TO ");

        SQLSubPartitionDefinition subPartition = x.getSubPartition();
        if (subPartition != null) {
            printlnAfterValue(SQLKeyWord.INTO);
            printSpaceAfterAccept(subPartition);
        }

//        printlnAndAccept(x.getFilterCondition());
//        printlnAndAccept(x.getDependentTables());
//        printlnAndAccept(x.getUpdateIndex());
//        printlnAndAccept(x.getParallel());

//        if (x.isOnline()) {
//            printlnAfterValue(SQLKeyWord.ONLINE);
//        }
//        printlnAfterValue(x.getAllowDisallowClustering());

        return false;
    }

    @Override
    public boolean visit(SQLAlterTableExchangePartitionAction x) {
        print(isLowerCase() ? "exchange partition" : "EXCHANGE PARTITION");
        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.WITH);
        printSpaceAfterValue(SQLKeyWord.TABLE);
        printSpaceAfterAccept(x.getTable());

//        SQLIncludingType indexes = x.getIndexes();
//        if (indexes != null) {
//            printSpaceAfterValue(indexes);
//            printSpaceAfterValue(SQLKeyWord.INDEXES);
//        }

//        SQLWithType validation = x.getValidation();
//        if (validation != null) {
//            printSpaceAfterValue(validation);
//            printSpaceAfterValue(SQLKeyWord.VALIDATION);
//        }

//        printSpaceAfterAccept(x.getExceptionsClause());
//        printSpaceAfterAccept(x.getUpdateIndexClause());
//        printSpaceAfterAccept(x.getParallelClause());

        if (x.isCascade()) {
            printSpaceAfterValue(SQLKeyWord.CASCADE);
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableExchangePartitionForAction x) {
        print(isLowerCase() ? "exchange partition for" : "EXCHANGE PARTITION FOR");
        printSpaceAfterAccept(x.getNames(), ", ", true);

        printSpaceAfterValue(SQLKeyWord.WITH);
        printSpaceAfterValue(SQLKeyWord.TABLE);
        printSpaceAfterAccept(x.getTable());

//        SQLIncludingType indexes = x.getIndexes();
//        if (indexes != null) {
//            printSpaceAfterValue(indexes);
//            printSpaceAfterValue(SQLKeyWord.INDEXES);
//        }

//        SQLWithType validation = x.getValidation();
//        if (validation != null) {
//            printSpaceAfterValue(validation);
//            printSpaceAfterValue(SQLKeyWord.VALIDATION);
//        }

//        printSpaceAfterAccept(x.getExceptionsClause());
//        printSpaceAfterAccept(x.getUpdateIndexClause());
//        printSpaceAfterAccept(x.getParallelClause());

        if (x.isCascade()) {
            printSpaceAfterValue(SQLKeyWord.CASCADE);
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableExchangeSubPartitionAction x) {
        print(isLowerCase() ? "exchange partition" : "EXCHANGE PARTITION");
        printSpaceAfterAccept(x.getName());

        printSpaceAfterValue(SQLKeyWord.WITH);
        printSpaceAfterValue(SQLKeyWord.TABLE);
        printSpaceAfterAccept(x.getTable());

//        SQLIncludingType indexes = x.getIndexes();
//        if (indexes != null) {
//            printSpaceAfterValue(indexes);
//            printSpaceAfterValue(SQLKeyWord.INDEXES);
//        }

//        SQLWithType validation = x.getValidation();
//        if (validation != null) {
//            printSpaceAfterValue(validation);
//            printSpaceAfterValue(SQLKeyWord.VALIDATION);
//        }

//        printSpaceAfterAccept(x.getExceptionsClause());
//        printSpaceAfterAccept(x.getUpdateIndexClause());
//        printSpaceAfterAccept(x.getParallelClause());

        if (x.isCascade()) {
            printSpaceAfterValue(SQLKeyWord.CASCADE);
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableExchangeSubPartitionForAction x) {
        print(isLowerCase() ? "exchange subpartition for" : "EXCHANGE SUBPARTITION FOR");
        printSpaceAfterAccept(x.getNames(), ", ", true);

        printSpaceAfterValue(SQLKeyWord.WITH);
        printSpaceAfterValue(SQLKeyWord.TABLE);
        printSpaceAfterAccept(x.getTable());

//        SQLIncludingType indexes = x.getIndexes();
//        if (indexes != null) {
//            printSpaceAfterValue(indexes);
//            printSpaceAfterValue(SQLKeyWord.INDEXES);
//        }

//        SQLWithType validation = x.getValidation();
//        if (validation != null) {
////            printSpaceAfterValue(validation);
//            printSpaceAfterValue(SQLKeyWord.VALIDATION);
//        }

//        printSpaceAfterAccept(x.getExceptionsClause());
//        printSpaceAfterAccept(x.getUpdateIndexClause());
//        printSpaceAfterAccept(x.getParallelClause());

        if (x.isCascade()) {
            printSpaceAfterValue(SQLKeyWord.CASCADE);
        }
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableSetPartitioningAction x) {
        print(isLowerCase() ? "set partitioning" : "SET PARTITIONING");
//        printSpaceAfterValue(x.getSetPartition());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableSetIntervalAction x) {
        print(SQLKeyWord.SET);
        printSpaceAfterValue(SQLKeyWord.INTERVAL);
        printSpaceAfterValue('(');
        printAccept(x.getExpr());
        print(')');
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableSetStoreInAction x) {
        print(isLowerCase() ? "set store in" : "SET STORE IN");
        printSpaceAfterAccept(x.getItems(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableSetSubPartitionTemplateAction x) {
        print(isLowerCase(), "set subpartition template", "SET SUBPARTITION TEMPLATE");
        printSpaceAndLnAndAccept(x.getSubPartitions(), ",", true);
        return false;
    }


    // ------------- Trigger --------------

    @Override
    public boolean visit(SQLAlterTableEnableTriggerAction x) {
        print(SQLKeyWord.ENABLE);
        printSpaceAfterValue(SQLKeyWord.TRIGGER);
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableDisableTriggerAction x) {
        print(SQLKeyWord.DISABLE);
        printSpaceAfterValue(SQLKeyWord.TRIGGER);
        printSpaceAfterAccept(x.getName());
        return false;
    }

    // ---------------------------- Table expr End ------------------------------------


    // ---------------------------- Trigger expr End ------------------------------------
    @Override
    public boolean visit(SQLTriggerDatabaseEvent x) {
        print(x.getType());
        return false;
    }

    @Override
    public boolean visit(SQLTriggerDDLEvent x) {
        print(x.getType());
        return false;
    }

    @Override
    public boolean visit(SQLTriggerDMLEvent x) {
        print(x.getType());

        List<ISQLExpr> ofColumns = x.getOfColumns();
        if (ofColumns != null
                && ofColumns.size() > 0) {
            printSpaceAfterValue(SQLKeyWord.OF);
            printSpaceAfterAccept(x.getOfColumns(), ", ");
        }
        return false;
    }

    @Override
    public boolean visit(SQLTriggerReferencingClause x) {
        print(REFERENCING);
        printIndentLnAccept(x.getItems());
        return false;
    }

    @Override
    public boolean visit(SQLTriggerReferencingClause.SQLOldItem x) {
        print(SQLKeyWord.OLD);
        if (x.isAs()) {
            printSpaceAfterValue(SQLKeyWord.AS);
        }
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLTriggerReferencingClause.SQLNewItem x) {
        print(SQLKeyWord.NEW);
        if (x.isAs()) {
            printSpaceAfterValue(SQLKeyWord.AS);
        }
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLTriggerReferencingClause.SQLParentItem x) {
        print(SQLKeyWord.PARENT);
        if (x.isAs()) {
            printSpaceAfterValue(SQLKeyWord.AS);
        }
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(SQLTriggerCompoundTriggerBlock x) {
        print(isLowerCase() ? "compound trigger" : "COMPOUND TRIGGER");
        printIndentLnAccept(x.getDeclareSections());
        printIndentLnAccept(x.getTimingPointSections());
        printlnAfterValue(SQLKeyWord.END);
        printSpaceAfterAccept(x.getEndName());
        return false;
    }

    @Override
    public boolean visit(SQLTriggerCompoundTriggerBlock.SQLTimingPointSection x) {
        print(x.getTimingPoint());
        printSpaceAfterValue(SQLKeyWord.IS);
        printSpaceAfterValue(SQLKeyWord.BEGIN);

        printIndentLnAccept(x.getStatements());
        if (x.getExceptionHandlers() != null
                && x.getExceptionHandlers().size() > 0) {
            printlnAfterValue(SQLKeyWord.EXCEPTION);
            printIndentLnAccept(x.getExceptionHandlers());
        }

        printlnAfterValue(SQLKeyWord.END);
        printSpaceAfterValue(x.getEndTimingPoint());
        return false;
    }

    @Override
    public boolean visit(ISQLAlterTriggerAction.SQLAlterTriggerEnableAction x) {
        print(SQLKeyWord.ENABLE);
        return false;
    }

    @Override
    public boolean visit(ISQLAlterTriggerAction.SQLAlterTriggerDisableAction x) {
        print(SQLKeyWord.DISABLE);
        return false;
    }

    @Override
    public boolean visit(ISQLAlterTriggerAction.SQLAlterTriggerRenameToAction x) {
        print(SQLKeyWord.RENAME);
        printSpaceAfterValue(SQLKeyWord.TO);
        printSpaceAfterAccept(x.getName());
        return false;
    }

    @Override
    public boolean visit(ISQLAlterTriggerAction.SQLAlterTriggerEditionAbleAction x) {
        print(SQLKeyWord.EDITIONABLE);
        return false;
    }

    @Override
    public boolean visit(ISQLAlterTriggerAction.SQLAlterTriggerNonEditionAbleAction x) {
        print(SQLKeyWord.NONEDITIONABLE);
        return false;
    }


    // ---------------------------- Trigger expr End ------------------------------------


    // ---------------------------- Type expr End ------------------------------------

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAlterTypeEditionAbleAction x) {
        print(SQLKeyWord.EDITIONABLE);
        return false;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAlterTypeNoneEditionAbleAction x) {
        print(SQLKeyWord.NONEDITIONABLE);
        return false;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAlterTypeResetAction x) {
        print(SQLKeyWord.RESET);
        return false;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAlterTypeInstantiableAction x) {
        print(SQLKeyWord.INSTANTIABLE);
        return false;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAlterTypeFinalAction x) {
        print(SQLKeyWord.FINAL);
        return false;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAlterTypeNotInstantiableAction x) {
        print(SQLKeyWord.NOT);
        printSpaceAfterValue(SQLKeyWord.INSTANTIABLE);
        return false;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAlterTypeNotFinalAction x) {
        print(SQLKeyWord.NOT);
        printSpaceAfterValue(SQLKeyWord.FINAL);
        return false;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLAttributeClause x) {
        print(SQLKeyWord.ATTRIBUTE);
        printSpaceAfterAccept(x.getParameters(), ", ", x.isParen());
        return false;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLLimitClause x) {
        print(SQLKeyWord.LIMIT);
        printSpaceAfterAccept(x.getValue());
        return false;
    }

    @Override
    public boolean visit(ISQLAlterTypeAction.SQLElementTypeClause x) {
        print(SQLKeyWord.ELEMENT);
        printSpaceAfterValue(SQLKeyWord.TYPE);
        printSpaceAfterAccept(x.getDataType());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTypeReplaceAction x) {
        print(SQLKeyWord.REPLACE);
        printSpaceAfterAccept(x.getProperties(), " ");
        printSpaceAfterAccept(x.getParameters(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLAlterTypeAddAction x) {
        print(SQLKeyWord.ADD);
        printSpaceAfterAccept(x.getExpr());
        printSpaceAfterAccept(x.getDependentHandlingClause());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTypeDropAction x) {
        print(SQLKeyWord.DROP);
        printSpaceAfterAccept(x.getExpr());
        printSpaceAfterAccept(x.getDependentHandlingClause());
        return false;
    }

    @Override
    public boolean visit(SQLAlterTypeModifyAction x) {
        print(SQLKeyWord.MODIFY);
        printSpaceAfterAccept(x.getExpr());
        printSpaceAfterAccept(x.getDependentHandlingClause());
        return false;
    }

    @Override
    public boolean visit(AbstractSQLAlterTypeAction.SQLDependentHandlingInvalidateClause x) {
        print(SQLKeyWord.INVALIDATE);
        return false;
    }

    @Override
    public boolean visit(AbstractSQLAlterTypeAction.SQLDependentHandlingCascadeClause x) {
        print(SQLKeyWord.CASCADE);
        printSpaceAfterValue(x.getOptionType());
        if (x.isForce()) {
            printSpaceAfterValue(SQLKeyWord.FORCE);
        }
        printSpaceAfterAccept(x.getExceptionsClause());
        return false;
    }

    // ---------------------------- Type expr End ------------------------------------



    // ---------------------------- View expr Start ------------------------------------

    @Override
    public boolean visit(SQLAlterViewAddTableConstraintAction x) {
        print(SQLKeyWord.ADD);
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewModifyConstraintAction x) {
        print(SQLKeyWord.MODIFY);
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewDropConstraintAction x) {
        print(SQLKeyWord.DROP);
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewDropPrimaryKeyConstraintAction x) {
        print(SQLKeyWord.DROP);
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewDropUniqueConstraintAction x) {
        print(SQLKeyWord.DROP);
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewCompileAction x) {
        print(SQLKeyWord.COMPILE);
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewReadOnlyAction x) {
        print(isLowerCase()?"read only":"READ ONLY");
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewReadWriteAction x) {
        print(isLowerCase()?"read write":"READ WRITE");
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewEditionableAction x) {
        print(SQLKeyWord.EDITIONABLE);
        return false;
    }

    @Override
    public boolean visit(SQLAlterViewNonEditionableAction x) {
        print(SQLKeyWord.NONEDITIONABLE);
        return false;
    }

    // ---------------------------- View expr End ------------------------------------
    

    // ---------------------------- Insert expr Start ------------------------------------

    @Override
    public boolean visit(SQLMultiInsertStatement.SQLAllInsertClause x) {
        print(SQLKeyWord.ALL);
        printIndentLnAccept(x.getItems());
        return false;
    }

    @Override
    public boolean visit(SQLMultiInsertStatement.SQLConditionalInsertClause x) {
        print(x.getType());
        printIndentLnAccept(x.getWhenClauses());

        List<SQLMultiInsertStatement.SQLInsertIntoClauseItem> elseItems = x.getElseItems();
        if (elseItems != null
                && elseItems.size() > 0) {
            printlnAfterValue(ELSE);
            printIndentLnAccept(x.getElseItems());
        }

        return false;
    }

    @Override
    public boolean visit(SQLMultiInsertStatement.SQLConditionalInsertWhenClause x) {
        print(SQLKeyWord.WHEN);
        printSpaceAfterAccept(x.getCondition());

        printSpaceAfterValue(SQLKeyWord.THEN);

        printIndentLnAccept(x.getThenItems());

        return false;
    }

    @Override
    public boolean visit(SQLMultiInsertStatement.SQLInsertIntoClauseItem x) {
        print(SQLKeyWord.INTO);
        printSpaceAfterAccept(x.getTableReference());
        printSpaceAfterAccept(x.getColumns(), ",", true);

        printlnAndAccept(x.getValuesClause());
        printlnAndAccept(x.getErrorLoggingClause());
        return false;
    }


    // ---------------------------- Insert expr Start ------------------------------------


    // ---------------------------- Select expr Start ------------------------------------

    @Override
    public boolean visit(SQLSelectQuery x) {
        SQLWithClause withClause = x.getWithClause();
        if (withClause != null) {
            withClause.accept(this);
            println();
        }

        print(SQLKeyWord.SELECT);
        printSpaceAfterValue(x.getSetQuantifier());


        printSpace();
        printSelectItems(x.getSelectItems());

        if (x.isBulkCollect()) {

        }
        printIndentLnSelectTargetItems(x.getSelectTargetItems());

        printlnAndAccept(x.getFromClause());

        printlnAndAccept(x.getWhereClause());

        printlnAndAccept(x.getGroupByClause());

        printlnAndAccept(x.getWindowClause());

        printlnAndAccept(x.getOrderByClause());

        printlnAndAccept(x.getLimitClause());

        return false;
    }

    @Override
    public boolean visit(SQLParenSelectQuery x) {
        print('(');
        this.incrementIndent();
        println();

        x.getQuery().accept(this);

        this.decrementIndent();
        println();
        print(')');

        SQLOrderByClause orderByClause = x.getOrderByClause();
        if (orderByClause != null) {
            println();
            orderByClause.accept(this);
        }

        ISQLLimitClause limitClause = x.getLimitClause();
        if (limitClause != null) {
            println();
            limitClause.accept(this);
        }

        return false;
    }

    @Override
    public boolean visit(SQLSelectUnionQuery x) {

        printAccept(x.getLeft());

        printlnAfterValue(x.getOperator());

        printlnAndAccept(x.getRight());

        printlnAndAccept(x.getOrderByClause());

        printlnAndAccept(x.getLimitClause());

        return false;
    }

    public void printSelectItems(List<SQLSelectItem> selectList) {
        int lineLength = 0;

        incrementIndent();
        for (int i = 0; i < selectList.size(); i++) {
            SQLSelectItem selectItem = selectList.get(i);
            int selectItemLength = selectItem.toString().length();
            if (i != 0) {
                print(",");
            }
            if (isKeepComment() || lineLength > getLineMaxLength()) {
                println();
                lineLength = 0;
            } else if (i != 0) {
                printSpace();
            }

            lineLength += selectItemLength;
            selectItem.accept(this);
        }
        decrementIndent();
    }

    public void printIndentLnSelectTargetItems(List<SQLSelectTargetItem> selectTargetItems) {
        if (selectTargetItems == null
                || selectTargetItems.size() == 0) {
            return;
        }

        incrementIndent();
        printlnAfterValue(SQLKeyWord.INTO);
        printSpace();

        int lineLength = 0;

        for (int i = 0; i < selectTargetItems.size(); i++) {
            SQLSelectTargetItem selectItem = selectTargetItems.get(i);
            int selectItemLength = selectItem.toString().length();
            if (i != 0) {
                print(",");
            }
            if (isKeepComment() || lineLength > getLineMaxLength()) {
                println();
                lineLength = 0;
            } else if (i != 0) {
                printSpace();
            }

            lineLength += selectItemLength;
            selectItem.accept(this);
        }
        decrementIndent();
    }

    @Override
    public boolean visit(SQLSelectItem x) {
        printAccept(x.getExpr());
        ISQLExpr alias = x.getAlias();
        if (alias != null) {
            printSpace();

            if (x.isAs()) {
                print(SQLKeyWord.AS);
                printSpace();
            }

            alias.accept(this);
        }

        return false;
    }

    @Override
    public boolean visit(SQLSelectTargetItem x) {
        printAccept(x.getExpr());
        return false;
    }

    @Override
    public boolean visit(SQLObjectNameTableReference x) {
//        if (x.isOnly()) {
//            print(SQLKeyWord.ONLY);
//            print(SQLKeyWord.LEFT_PAREN);
//        }

        x.getName().accept(this);

//        if (x.isOnly()) {
//            print(SQLKeyWord.LEFT_PAREN);
//        }

        printSpaceAfterAccept(x.getPartitionExtensionClause());

        if (x.isAs()) {
            printSpace();
            print(SQLKeyWord.AS);
        }

        ISQLIdentifier alias = x.getAlias();
        if (alias != null) {
            printSpace();
            alias.accept(this);
        }


        printSpaceAfterAccept(x.getColumns(), ", ", true);


//        SQLSampleClause sampleClause = x.getSampleClause();
//        if (sampleClause != null) {
//            printSpace();
//            sampleClause.accept(this);
//        }

        return false;
    }

    @Override
    public boolean visit(SQLSubQueryTableReference x) {
        if (x.isOnly()) {
            print(SQLKeyWord.ONLY);
            printSpace();
        }

        if (x.isLateral()) {
            print(SQLKeyWord.LATERAL);
            printSpace();
        }

        print('(');
        this.incrementIndent();
        println();

        x.getSubQuery().accept(this);

        this.decrementIndent();
        println();
        print(')');

        if (x.isAs()) {
            printSpace();
            print(SQLKeyWord.AS);
        }

        ISQLIdentifier alias = x.getAlias();
        if (alias != null) {
            printSpace();
            alias.accept(this);
        }

        if (x.getColumns().size() > 0) {
            printSpace();
            printAccept(x.getColumns(), ", ", true);
        }

//        SQLSampleClause sampleClause = x.getSampleClause();
//        if (sampleClause != null) {
//            printSpace();
//            sampleClause.accept(this);
//        }

        return false;
    }

    @Override
    public boolean visit(SQLTableFunctionTableReference x) {
        if (x.isOnly()) {
            print(SQLKeyWord.ONLY);
            printSpaceAfterValue('(');
            printSpace();
        }

        print(SQLKeyWord.TABLE);
        print('(');

        x.getExpr().accept(this);
        print(')');

        if (x.isOuterJoin()) {
            printSpaceAfterValue("(+)");
        }

        if (x.isOnly()) {
            print(')');
        }


        if (x.isAs()) {
            printSpaceAfterValue(SQLKeyWord.AS);
        }

        printSpaceAfterAccept(x.getAlias());

        return false;
    }

    @Override
    public boolean visit(SQLUnNestFunctionTableReference x) {
        print(SQLKeyWord.UNNEST);
        print('(');
        x.getExpr().accept(this);
        print(')');

        if (x.isWithOrdinality()) {
//            printSpaceAfterValue(SQLKeyWord.WITH_ORDINALITY);
        }

        if (x.isAs()) {
            printSpaceAfterValue(SQLKeyWord.AS);
        }

        printSpaceAfterAccept(x.getAlias());

        return false;
    }

    @Override
    public boolean visit(SQLJoinTableReference x) {
        if (x.isParen()) {
            print('(');
        }

        x.getLeft().accept(this);

//        printSpaceAfterAccept(x.getLeftPartitionByClause());

        boolean isComma = x.getJoinType() == SQLJoinTableReference.SQLJoinType.COMMA;
        if (isComma) {
            print(',');
        } else {
            this.incrementIndent();
            println();
            print(x.getJoinType());
        }

        printSpaceAfterAccept(x.getRight());

//        printSpaceAfterAccept(x.getRightPartitionByClause());

        printSpaceAfterAccept(x.getConditions(), " ");

        if (!isComma) {
            this.decrementIndent();
        }

        if (x.isParen()) {
            print(')');
        }
        return false;
    }

    @Override
    public boolean visit(SQLJoinTableReference.SQLJoinOnCondition x) {
        if (x.getCondition() == null) {
            return false;
        }
        print(SQLKeyWord.ON);
        printSpaceAfterAccept(x.getCondition());
        return false;
    }

    @Override
    public boolean visit(SQLJoinTableReference.SQLJoinUsingCondition x) {
        print(SQLKeyWord.USING);
        printSpaceAfterAccept(x.getColumns(), ", ", true);
        return false;
    }

    @Override
    public boolean visit(SQLGroupByClause x) {
        boolean print = false;

        List<SQLGroupByClause.SQLGroupByItem> items = x.getItems();
        SQLHavingClause havingClause = x.getHavingClause();

        if (x.isOrder()) {

            if (items.size() > 0) {
                printGroupByList(items);
                if (x.isWithRollup()) {
                    printSpaceAfterValue(isLowerCase() ? "with rollup" : "WITH ROLLUP");

                }
                print = true;
            }

            if (havingClause != null) {
                if (print) {
                    println();
                }
                havingClause.accept(this);
            }

        } else {

            if (havingClause != null) {
                havingClause.accept(this);
                print = true;
            }

            if (items.size() > 0) {
                if (print) {
                    println();
                }
                printGroupByList(items);
            }
        }
        return false;
    }

    public void printGroupByList(List<SQLGroupByClause.SQLGroupByItem> items) {
        if (items == null
                || items.size() == 0) {
            return;
        }
        print(SQLKeyWord.GROUP);
        printSpaceAfterValue(SQLKeyWord.BY);
        printSpace();
        printAccept(items, ", ");
    }

    @Override
    public boolean visit(SQLGroupByClause.SQLGroupByItem x) {
        printAccept(x.getExpr());
        return false;
    }

    @Override
    public boolean visit(SQLHavingClause x) {
        print(SQLKeyWord.HAVING);

        printSpace();
        x.getCondition().accept(this);
        return false;
    }


    @Override
    public boolean visit(SQLOrderByClause x) {
        print(SQLKeyWord.ORDER);

        if (x.isSiblings()) {
//            printSpaceAfterValue(SQLKeyWord.SIBLINGS);
        }

        printSpaceAfterValue(SQLKeyWord.BY);

        printSpace();

        printAccept(x.getItems(), ", ");

        return false;
    }


    @Override
    public boolean visit(SQLOrderByItem x) {
        printAccept(x.getSortKey());

        printSpaceAfterAccept(x.getOrderingSpecification());

        printSpaceAfterValue(x.getNullOrdering());

        return false;
    }

    @Override
    public boolean visit(SQLOrderByItem.SQLASCOrderingSpecification x) {
        print(SQLKeyWord.ASC);
        return false;
    }

    @Override
    public boolean visit(SQLOrderByItem.SQLDESCOrderingSpecification x) {
        print(SQLKeyWord.DESC);
        return false;
    }

    @Override
    public boolean visit(SQLOrderByItem.SQLUsingCOrderingSpecification x) {
        print(SQLKeyWord.USING);
        printSpaceAfterAccept(x.getExpr());
        return false;
    }

    @Override
    public boolean visit(SQLLimitOffsetClause x) {
        print(SQLKeyWord.LIMIT);

        boolean isOffset = x.isOffset();
        ISQLExpr offset = x.getOffsetExpr();
        if (offset != null
                && !isOffset) {
            printSpaceAfterAccept(offset);
            print(",");
        }

        printSpaceAfterAccept(x.getRowCountExpr());

        if (offset != null
                && isOffset) {
            printSpaceAfterValue(SQLKeyWord.OFFSET);

            printSpaceAfterAccept(offset);

            printSpaceAfterValue(x.getOffSetRowType());

        }
        return false;
    }

    @Override
    public boolean visit(SQLOffsetFetchClause x) {
        boolean print = false;

        ISQLExpr offsetExpr = x.getOffsetExpr();
        if (offsetExpr != null) {
            print = true;

            print(SQLKeyWord.OFFSET);

            printSpaceAfterAccept(x.getOffsetExpr());

            printSpaceAfterValue(x.getOffSetRowType());
        }


        SQLOffsetFetchClause.SQLFetchType fetchType = x.getFetchType();
        if (fetchType != null) {
            if (print) {
                printSpace();
            }

            print(SQLKeyWord.FETCH);
            printSpaceAfterValue(fetchType);

            printSpaceAfterAccept(x.getRowCountExpr());

            if (x.isPercent()) {
                printSpaceAfterValue(SQLKeyWord.PERCENT);
            }

            printSpaceAfterValue(x.getFetchRowType());

            printSpaceAfterValue(x.getOnlyType());
        }

        return false;
    }

    @Override
    public boolean visit(SQLForUpdateClause x) {
        print(SQLKeyWord.FOR);

        printSpaceAfterValue(x.getForType());

        List<ISQLName> columns = x.getColumns();
        if (columns.size() > 0) {
            printSpaceAfterValue(SQLKeyWord.OF);
            printSpaceAfterAccept(columns, ", ");
        }

        printSpaceAfterAccept(x.getForOption());
        return false;
    }

    @Override
    public boolean visit(SQLForUpdateClause.SQLForNoWaitOption x) {
        print(SQLKeyWord.NOWAIT);
        return false;
    }

    @Override
    public boolean visit(SQLForUpdateClause.SQLForSkipLockedOption x) {
        print(isLowerCase() ? "skip locked" : "SKIP LOCKED");
        return false;
    }

    @Override
    public boolean visit(SQLForUpdateClause.SQLForWaitOption x) {
        print(SQLKeyWord.WAIT);
        printSpaceAfterAccept(x.getExpr());
        return false;
    }

    @Override
    public boolean visit(SQLLockInShareModeClause x) {
        print(isLowerCase() ? "lock in share mode" : "LOCK IN SHARE MODE");
        return false;
    }

    // ---------------------------- Select expr End ------------------------------------
}
