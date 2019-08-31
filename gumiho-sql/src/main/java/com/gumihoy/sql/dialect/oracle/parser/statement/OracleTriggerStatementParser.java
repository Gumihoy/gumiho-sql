package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLCollateOptionExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLExceptionHandler;
import com.gumihoy.sql.basic.ast.expr.common.SQLLabelStatement;
import com.gumihoy.sql.basic.ast.expr.common.SQLSharingClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerCompoundTriggerBlock;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerDDLEvent;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerDMLEvent;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerDatabaseEvent;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerEvent;
import com.gumihoy.sql.basic.ast.expr.trigger.SQLTriggerReferencingClause;
import com.gumihoy.sql.basic.ast.expr.trigger.alter.ISQLAlterTriggerAction;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLAlterTriggerStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.trigger.SQLCreateTriggerStatement;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLTriggerStatementParser;
import com.gumihoy.sql.dialect.oracle.parser.OracleExprParser;

/**
 * @author kent on 2019-06-25.
 */
public class OracleTriggerStatementParser extends SQLTriggerStatementParser {

    public OracleTriggerStatementParser(OracleExprParser exprParser) {
        super(exprParser);
    }

    @Override
    public SQLCreateTriggerStatement parseCreate() {
        SQLCreateTriggerStatement x = new SQLCreateTriggerStatement(this.dbType);

        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);

        x.setOrReplace(exprParser.parseOrReplace());
        x.setEditionAbleType(exprParser.parseEditionAbleType());

        acceptAndNextToken(SQLToken.TokenKind.TRIGGER, true);

        x.setName(exprParser.parseName());

        SQLSharingClause sharingClause = exprParser.parseSharingClause();
        x.setSharingClause(sharingClause);

        SQLCollateOptionExpr collateOptionExpr = exprParser.parseCollateClause();
        x.setCollationExpr(collateOptionExpr);

        SQLCreateTriggerStatement.SQLTriggerActionTime actionTime = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.BEFORE)) {
            actionTime = SQLCreateTriggerStatement.SQLTriggerActionTime.BEFORE;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.AFTER)) {
            actionTime = SQLCreateTriggerStatement.SQLTriggerActionTime.AFTER;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.INSTEAD)) {
            this.acceptAndNextToken(SQLToken.TokenKind.OF, true);
            actionTime = SQLCreateTriggerStatement.SQLTriggerActionTime.INSTEAD_OF;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.FOR)) {
            actionTime = SQLCreateTriggerStatement.SQLTriggerActionTime.FOR;

        } else {
            throw new SQLParserException("");
        }
        x.setActionTime(actionTime);

        for (; ; ) {
            SQLTriggerEvent event = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.DELETE)) {
                event = SQLTriggerDMLEvent.of(SQLTriggerDMLEvent.SQLTriggerDMLEventType.DELETE);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.INSERT)) {
                event = SQLTriggerDMLEvent.of(SQLTriggerDMLEvent.SQLTriggerDMLEventType.INSERT);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.UPDATE)) {
                SQLTriggerDMLEvent dmlEvent = SQLTriggerDMLEvent.of(SQLTriggerDMLEvent.SQLTriggerDMLEventType.UPDATE);

                if (this.acceptAndNextToken(SQLToken.TokenKind.OF)) {
                    for (; ; ) {
                        dmlEvent.addOfColumn(exprParser.parseName());
                        if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                            break;
                        }
                    }
                }
                event = dmlEvent;

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.ALTER)) {
                event = SQLTriggerDDLEvent.of(SQLTriggerDDLEvent.SQLTriggerDDLEventType.ALTER);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.ANALYZE)) {
                event = SQLTriggerDDLEvent.of(SQLTriggerDDLEvent.SQLTriggerDDLEventType.ANALYZE);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.ASSOCIATE)) {
                this.acceptAndNextToken(SQLToken.TokenKind.STATISTICS, true);
                event = SQLTriggerDDLEvent.of(SQLTriggerDDLEvent.SQLTriggerDDLEventType.ASSOCIATE_STATISTICS);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.AUDIT)) {
                event = SQLTriggerDDLEvent.of(SQLTriggerDDLEvent.SQLTriggerDDLEventType.AUDIT);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.COMMENT)) {
                event = SQLTriggerDDLEvent.of(SQLTriggerDDLEvent.SQLTriggerDDLEventType.COMMENT);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.CREATE)) {
                event = SQLTriggerDDLEvent.of(SQLTriggerDDLEvent.SQLTriggerDDLEventType.CREATE);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.DISASSOCIATE)) {
                this.acceptAndNextToken(SQLToken.TokenKind.STATISTICS, true);
                event = SQLTriggerDDLEvent.of(SQLTriggerDDLEvent.SQLTriggerDDLEventType.DISASSOCIATE_STATISTICS);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.DROP)) {
                event = SQLTriggerDDLEvent.of(SQLTriggerDDLEvent.SQLTriggerDDLEventType.DROP);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.GRANT)) {
                event = SQLTriggerDDLEvent.of(SQLTriggerDDLEvent.SQLTriggerDDLEventType.GRANT);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.NOAUDIT)) {
                event = SQLTriggerDDLEvent.of(SQLTriggerDDLEvent.SQLTriggerDDLEventType.NOAUDIT);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.RENAME)) {
                event = SQLTriggerDDLEvent.of(SQLTriggerDDLEvent.SQLTriggerDDLEventType.RENAME);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.REVOKE)) {
                event = SQLTriggerDDLEvent.of(SQLTriggerDDLEvent.SQLTriggerDDLEventType.REVOKE);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.TRUNCATE)) {
                event = SQLTriggerDDLEvent.of(SQLTriggerDDLEvent.SQLTriggerDDLEventType.TRUNCATE);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.DDL)) {
                event = SQLTriggerDDLEvent.of(SQLTriggerDDLEvent.SQLTriggerDDLEventType.DDL);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.STARTUP)) {
                event = SQLTriggerDatabaseEvent.of(SQLTriggerDatabaseEvent.SQLTriggerDatabaseEventType.STARTUP);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.SHUTDOWN)) {
                event = SQLTriggerDatabaseEvent.of(SQLTriggerDatabaseEvent.SQLTriggerDatabaseEventType.SHUTDOWN);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.DB_ROLE_CHANGE)) {
                event = SQLTriggerDatabaseEvent.of(SQLTriggerDatabaseEvent.SQLTriggerDatabaseEventType.DB_ROLE_CHANGE);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.SERVERERROR)) {
                event = SQLTriggerDatabaseEvent.of(SQLTriggerDatabaseEvent.SQLTriggerDatabaseEventType.SERVERERROR);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.LOGON)) {
                event = SQLTriggerDatabaseEvent.of(SQLTriggerDatabaseEvent.SQLTriggerDatabaseEventType.LOGON);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.LOGOFF)) {
                event = SQLTriggerDatabaseEvent.of(SQLTriggerDatabaseEvent.SQLTriggerDatabaseEventType.LOGOFF);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.SUSPEND)) {
                event = SQLTriggerDatabaseEvent.of(SQLTriggerDatabaseEvent.SQLTriggerDatabaseEventType.SUSPEND);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.CLONE)) {
                event = SQLTriggerDatabaseEvent.of(SQLTriggerDatabaseEvent.SQLTriggerDatabaseEventType.CLONE);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.UNPLUG)) {
                event = SQLTriggerDatabaseEvent.of(SQLTriggerDatabaseEvent.SQLTriggerDatabaseEventType.UNPLUG);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.SET)) {
                this.acceptAndNextToken(SQLToken.TokenKind.CONTAINER, true);
                event = SQLTriggerDatabaseEvent.of(SQLTriggerDatabaseEvent.SQLTriggerDatabaseEventType.SET_CONTAINER);

            }

            x.addEvent(event);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.OR)) {
                break;
            }
        }

        this.acceptAndNextToken(SQLToken.TokenKind.ON, true);
        if (this.acceptAndNextToken(SQLToken.TokenKind.NESTED)) {
            this.acceptAndNextToken(SQLToken.TokenKind.TABLE, true);
            x.setNestedTableColumn(exprParser.parseName());
            this.acceptAndNextToken(SQLToken.TokenKind.OF, true);
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.PLUGGABLE)) {
            x.setPluggable(true);
        }
        x.setOnExpr(exprParser.parseName());

        x.setReferencingClause(parseReferencingClause());

        if (this.acceptAndNextToken(SQLToken.TokenKind.FOR)) {
            this.acceptAndNextToken(SQLToken.TokenKind.EACH, true);
            this.acceptAndNextToken(SQLToken.TokenKind.ROW, true);
            x.setForEachType(SQLCreateTriggerStatement.SQLTriggerForEachType.FOR_EACH_ROW);
        }


        if (this.acceptAndNextToken(SQLToken.TokenKind.WHEN)) {
            this.acceptAndNextToken(SQLToken.TokenKind.LPAREN, true);
            x.setWhenCondition(exprParser.parseExpr());
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        ISQLObject triggerBody = null;
        if (this.accept(SQLToken.TokenKind.COMPOUND)) {
            triggerBody = parseCompoundTriggerBlock();

        } else if (this.accept(SQLToken.TokenKind.CALL)) {
            triggerBody = exprParser.parseCallStatement();

        } else {
            triggerBody = getExprParser().parseBlock();
        }
        x.setTriggerBody(triggerBody);

        return x;
    }

    public SQLTriggerReferencingClause parseReferencingClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.REFERENCING)) {
            return null;
        }
        SQLTriggerReferencingClause x = new SQLTriggerReferencingClause();
        for (; ; ) {
            SQLTriggerReferencingClause.ISQLItem item = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.NEW)) {
                item = new SQLTriggerReferencingClause.SQLNewItem();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.OLD)) {

                item = new SQLTriggerReferencingClause.SQLOldItem();

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.PARENT)) {

                item = new SQLTriggerReferencingClause.SQLParentItem();

            } else {
                break;
            }

            boolean as = this.acceptAndNextToken(SQLToken.TokenKind.AS);
            ISQLName name = exprParser.parseName();

            item.setAs(as);
            item.setName(name);

            x.addItem(item);
        }

        return x;
    }

    public SQLTriggerCompoundTriggerBlock parseCompoundTriggerBlock() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.COMPOUND)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.TRIGGER, true);

        SQLTriggerCompoundTriggerBlock x = new SQLTriggerCompoundTriggerBlock();

        for (; ; ) {
            if (this.accept(SQLToken.TokenKind.BEFORE)
                    || this.accept(SQLToken.TokenKind.AFTER)
                    || this.accept(SQLToken.TokenKind.INSTEAD)) {
                break;
            }
            ISQLExpr declareSection = getExprParser().parseDeclareSection();
            x.addDeclareSection(declareSection);
        }

        for (; ; ) {
            if (this.accept(SQLToken.TokenKind.END)) {
                break;
            }
            SQLTriggerCompoundTriggerBlock.SQLTimingPointSection timingPointSection = parseTimingPointSection();
            x.addTimingPointSection(timingPointSection);
        }

        this.acceptAndNextToken(SQLToken.TokenKind.END, true);
        ISQLName endName = exprParser.parseName();
        x.setEndName(endName);

        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
        x.setAfterSemi(true);

        return x;
    }

    public SQLTriggerCompoundTriggerBlock.SQLTimingPointSection parseTimingPointSection() {
        SQLTriggerCompoundTriggerBlock.SQLTimingPointSection x = new SQLTriggerCompoundTriggerBlock.SQLTimingPointSection();

        SQLTriggerCompoundTriggerBlock.SQLTimingPoint timingPoint = parseTimingPoint();
        x.setTimingPoint(timingPoint);

        this.acceptAndNextToken(SQLToken.TokenKind.IS, true);
        this.acceptAndNextToken(SQLToken.TokenKind.BEGIN, true);

        for (; ; ) {
            if (this.accept(SQLToken.TokenKind.EXCEPTION)
                    || this.accept(SQLToken.TokenKind.END)) {
                break;
            }
            SQLLabelStatement statement = exprParser.parseLabelStatement();
            if (statement == null) {
                throw new SQLParserException(errorInfo());
            }
            x.addStatement(statement);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.EXCEPTION)) {
            for (; ; ) {
                SQLExceptionHandler exceptionHandler = getExprParser().parseExceptionHandler();
                if (exceptionHandler == null) {
                    break;
                }
                x.addExceptionHandler(exceptionHandler);
            }
        }

        this.acceptAndNextToken(SQLToken.TokenKind.END, true);
        SQLTriggerCompoundTriggerBlock.SQLTimingPoint endTimingPoint = parseTimingPoint();
        x.setEndTimingPoint(endTimingPoint);
        this.acceptAndNextToken(SQLToken.TokenKind.SEMI, true);
        x.setAfterSemi(true);

        return x;
    }


    public SQLTriggerCompoundTriggerBlock.SQLTimingPoint parseTimingPoint() {
        if (this.acceptAndNextToken(SQLToken.TokenKind.BEFORE)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.STATEMENT)) {

                return SQLTriggerCompoundTriggerBlock.SQLTimingPoint.BEFORE_STATEMENT;

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.EACH)) {
                this.acceptAndNextToken(SQLToken.TokenKind.ROW, true);
                return SQLTriggerCompoundTriggerBlock.SQLTimingPoint.BEFORE_EACH_ROW;

            }

            throw new SQLParserException(errorInfo());

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.AFTER)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.STATEMENT)) {

                return SQLTriggerCompoundTriggerBlock.SQLTimingPoint.AFTER_STATEMENT;

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.EACH)) {
                this.acceptAndNextToken(SQLToken.TokenKind.ROW, true);
                return SQLTriggerCompoundTriggerBlock.SQLTimingPoint.AFTER_EACH_ROW;

            }

            throw new SQLParserException(errorInfo());

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.INSTEAD)) {
            this.acceptAndNextToken(SQLToken.TokenKind.OF, true);
            if (this.acceptAndNextToken(SQLToken.TokenKind.EACH)) {
                this.acceptAndNextToken(SQLToken.TokenKind.ROW, true);
                return SQLTriggerCompoundTriggerBlock.SQLTimingPoint.INSTEAD_OF_EACH_ROW;

            }
            throw new SQLParserException(errorInfo());
        }
        return null;
    }


    @Override
    public SQLAlterTriggerStatement parseAlter() {
        return super.parseAlter();
    }

    @Override
    public ISQLAlterTriggerAction parseAlterAction() {
        if (this.accept(SQLToken.TokenKind.COMPILE)) {
            return getExprParser().parseCompileClause();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.ENABLE)) {
            return new ISQLAlterTriggerAction.SQLAlterTriggerEnableAction();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.DISABLE)) {
            return new ISQLAlterTriggerAction.SQLAlterTriggerDisableAction();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.RENAME)) {
            this.acceptAndNextToken(SQLToken.TokenKind.TO, true);
            return new ISQLAlterTriggerAction.SQLAlterTriggerRenameToAction(exprParser.parseName());
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.EDITIONABLE)) {
            return new ISQLAlterTriggerAction.SQLAlterTriggerEditionAbleAction();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.NONEDITIONABLE)) {
            return new ISQLAlterTriggerAction.SQLAlterTriggerNonEditionAbleAction();
        }
        return null;
    }


    @Override
    public OracleExprParser getExprParser() {
        return (OracleExprParser) exprParser;
    }
}
