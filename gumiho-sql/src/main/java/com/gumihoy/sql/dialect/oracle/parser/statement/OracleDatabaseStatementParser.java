package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLFileSpecification;
import com.gumihoy.sql.basic.ast.expr.common.SQLSizeClause;
import com.gumihoy.sql.basic.ast.expr.database.create.ISQLCreateDatabaseAction;
import com.gumihoy.sql.basic.ast.expr.database.create.ISQLDatabaseLoggingClause;
import com.gumihoy.sql.basic.ast.expr.database.create.ISQLDefaultTempTablespaceClause;
import com.gumihoy.sql.basic.ast.expr.database.create.ISQLTablespaceClause;
import com.gumihoy.sql.basic.ast.expr.database.create.ISQLUndoModeClause;
import com.gumihoy.sql.basic.ast.expr.database.create.SQLDefaultTablespaceClause;
import com.gumihoy.sql.basic.ast.expr.database.create.SQLEnablePluggableDatabaseClause;
import com.gumihoy.sql.basic.ast.expr.database.create.SQLExtentManagementClause;
import com.gumihoy.sql.basic.ast.expr.database.create.SQLFileNameConvertClause;
import com.gumihoy.sql.basic.ast.expr.database.create.SQLSetTimeZoneClause;
import com.gumihoy.sql.basic.ast.expr.database.create.SQLTablespaceDataFileClause;
import com.gumihoy.sql.basic.ast.expr.database.create.SQLUndoTablespaceClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLAlterDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLCreateDatabaseStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.database.SQLDropDatabaseStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLDatabaseStatementParser;
import com.gumihoy.sql.dialect.oracle.parser.OracleExprParser;

/**
 * @author kent on 2019-07-10.
 */
public class OracleDatabaseStatementParser extends SQLDatabaseStatementParser {

    public OracleDatabaseStatementParser(SQLLexer lexer) {
        this(new OracleExprParser(lexer));
    }

    public OracleDatabaseStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    @Override
    public SQLCreateDatabaseStatement parseCreate() {
        acceptAndNextToken(SQLToken.TokenKind.CREATE, true);
        acceptAndNextToken(SQLToken.TokenKind.DATABASE, true);

        ISQLName name = null;
        if (!this.accept(SQLToken.TokenKind.USER)
                && !this.accept(SQLToken.TokenKind.CONTROLFILE)
                && !this.accept(SQLToken.TokenKind.MAXDATAFILES)
                && !this.accept(SQLToken.TokenKind.MAXINSTANCES)
                && !this.accept(SQLToken.TokenKind.CHARACTER)
                && !this.accept(SQLToken.TokenKind.NATIONAL)
                && !this.accept(SQLToken.TokenKind.SET)

                // database_logging_clauses
                && !this.accept(SQLToken.TokenKind.LOGFILE)
                && !this.accept(SQLToken.TokenKind.MAXLOGFILES)
                && !this.accept(SQLToken.TokenKind.MAXLOGMEMBERS)
                && !this.accept(SQLToken.TokenKind.ARCHIVELOG)
                && !this.accept(SQLToken.TokenKind.NOARCHIVELOG)
                && !this.accept(SQLToken.TokenKind.FORCE)

                // tablespace_clauses
                && !this.accept(SQLToken.TokenKind.EXTENT)
                && !this.accept(SQLToken.TokenKind.DATAFILE)
                && !this.accept(SQLToken.TokenKind.SYSAUX)
                && !this.accept(SQLToken.TokenKind.DEFAULT)

                && !this.accept(SQLToken.TokenKind.BIGFILE)
                && !this.accept(SQLToken.TokenKind.SMALLFILE)

                && !this.accept(SQLToken.TokenKind.UNDO)

                && !this.accept(SQLToken.TokenKind.USER_DATA)
                && !this.accept(SQLToken.TokenKind.ENABLE)) {

            name = exprParser.parseName();
        }

        SQLCreateDatabaseStatement x = new SQLCreateDatabaseStatement(this.dbType);
        x.setName(name);

        for (; ; ) {
            ISQLExpr action = parseCreateAction();
            if (action == null) {
                break;
            }
            x.addAction(action);
        }

        return x;
    }

    @Override
    public ISQLExpr parseCreateAction() {
        SQLLexer.SQLMake make = this.make();

        if (this.acceptAndNextToken(SQLToken.TokenKind.USER)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.SYS)) {
                this.acceptAndNextToken(SQLToken.TokenKind.IDENTIFIED, true);
                this.acceptAndNextToken(SQLToken.TokenKind.BY, true);

                ISQLExpr password = exprParser.parseExpr();

                return new ISQLCreateDatabaseAction.SQLUserSysIdentifiedByAction(password);

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.SYSTEM)) {
                this.acceptAndNextToken(SQLToken.TokenKind.IDENTIFIED, true);
                this.acceptAndNextToken(SQLToken.TokenKind.BY, true);

                ISQLExpr password = exprParser.parseExpr();
                return new ISQLCreateDatabaseAction.SQLUserSystemIdentifiedByAction(password);

            }

            throw new SQLParserException();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.CONTROLFILE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.REUSE, true);
            return new ISQLCreateDatabaseAction.SQLControlfileReuseAction();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.MAXDATAFILES)) {
            ISQLExpr value = exprParser.parseExpr();
            return new ISQLCreateDatabaseAction.SQLMaxDataFilesAction(value);

        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.MAXINSTANCES)) {
            ISQLExpr value = exprParser.parseExpr();
            return new ISQLCreateDatabaseAction.SQLMaxInstancesAction(value);
        }

        if (this.accept(SQLToken.TokenKind.CHARACTER)) {
            return exprParser.parseCharacterSetClause();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.NATIONAL)) {
            this.acceptAndNextToken(SQLToken.TokenKind.CHARACTER, true);
            this.acceptAndNextToken(SQLToken.TokenKind.SET, true);
            ISQLExpr charset = exprParser.parseExpr();
            return new ISQLCreateDatabaseAction.SQLNationalCharacterSetAction(charset);
        }

        // set_default_tablespace_clause 、 set_time_zone_clause
        if (this.acceptAndNextToken(SQLToken.TokenKind.SET)) {
            ISQLCreateDatabaseAction action = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.DEFAULT)) {

                ISQLCreateDatabaseAction.SQLFileType fileType = null;
                if (this.acceptAndNextToken(SQLToken.TokenKind.BIGFILE)) {
                    fileType =  ISQLCreateDatabaseAction.SQLFileType.BIGFILE;

                } else if (this.acceptAndNextToken(SQLToken.TokenKind.SMALLFILE)) {
                    fileType =  ISQLCreateDatabaseAction.SQLFileType.SMALLFILE;

                } else {
                    throw new SQLParserException();
                }

                this.acceptAndNextToken(SQLToken.TokenKind.TABLESPACE, true);
                return new ISQLCreateDatabaseAction.SQLSetDefaultTableSpaceAction(fileType);
            }

            // set_time_zone_clause
            if (this.acceptAndNextToken(SQLToken.TokenKind.TIME_ZONE)) {
                this.reset(make);
                return parseSetTimeZoneClause();
            }
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.USER_DATA)) {
            this.acceptAndNextToken(SQLToken.TokenKind.TABLESPACE, true);
            ISQLName name = exprParser.parseName();

            ISQLCreateDatabaseAction.SQLUserDataTableSpaceAction action = new ISQLCreateDatabaseAction.SQLUserDataTableSpaceAction(name);

            this.acceptAndNextToken(SQLToken.TokenKind.DATAFILE, true);
            for (;;) {
                SQLFileSpecification dataFile = exprParser.parseFileSpecification();
                action.addDataFile(dataFile);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }

            return action;
        }


        // database_logging_clauses
        if (this.acceptAndNextToken(SQLToken.TokenKind.LOGFILE)) {
            ISQLDatabaseLoggingClause.SQLLogFileAction action = new ISQLDatabaseLoggingClause.SQLLogFileAction();
            for (; ; ) {
                ISQLDatabaseLoggingClause.SQLLogFileAction.SQLLogFileActionItem item = null;
                ISQLExpr groupValue = null;
                if (this.acceptAndNextToken(SQLToken.TokenKind.GROUP)) {
                    groupValue = exprParser.parseExpr();
                }
                SQLFileSpecification fileSpecification = exprParser.parseFileSpecification();
                if (fileSpecification != null) {
                    item = new ISQLDatabaseLoggingClause.SQLLogFileAction.SQLLogFileActionItem(groupValue, fileSpecification);
                }
                action.addItem(item);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            return action;
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.MAXLOGFILES)) {
            ISQLExpr value = exprParser.parseExpr();
            return new ISQLDatabaseLoggingClause.SQLMaxLogFilesAction(value);
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.MAXLOGMEMBERS)) {
            ISQLExpr value = exprParser.parseExpr();
            return new ISQLDatabaseLoggingClause.SQLMaxLogMembersAction(value);
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.MAXLOGHISTORY)) {
            ISQLExpr value = exprParser.parseExpr();
            return new ISQLDatabaseLoggingClause.SQLMaxLogHistoryAction(value);
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.ARCHIVELOG)) {
            return new ISQLDatabaseLoggingClause.SQLArchiveLogAction();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.NOARCHIVELOG)) {
            return new ISQLDatabaseLoggingClause.SQLNoArchiveLogAction();
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.FORCE)) {
            this.acceptAndNextToken(SQLToken.TokenKind.LOGGING, true);
            return new ISQLDatabaseLoggingClause.SQLForceLoggingAction();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.SET)) {
            this.acceptAndNextToken(SQLToken.TokenKind.STANDBY, true);
            this.acceptAndNextToken(SQLToken.TokenKind.NOLOGGING, true);
            this.acceptAndNextToken(SQLToken.TokenKind.FOR, true);

            ISQLDatabaseLoggingClause.SQLSetStandbyNoLoggingType type = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.DATA)) {
                this.acceptAndNextToken(SQLToken.TokenKind.AVAILABILITY, true);
                type = ISQLDatabaseLoggingClause.SQLSetStandbyNoLoggingType.DATA_AVAILABILITY;

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.LOAD)) {
                this.acceptAndNextToken(SQLToken.TokenKind.PERFORMANCE, true);
                type = ISQLDatabaseLoggingClause.SQLSetStandbyNoLoggingType.LOAD_PERFORMANCE;

            } else {
                throw new SQLParserException();
            }

            return new ISQLDatabaseLoggingClause.SQLSetStandbyNoLoggingAction(type);
        }


        // tablespace_clauses
        if (this.acceptAndNextToken(SQLToken.TokenKind.EXTENT)) {
            this.acceptAndNextToken(SQLToken.TokenKind.MANAGEMENT, true);
            this.acceptAndNextToken(SQLToken.TokenKind.LOCAL, true);
            return new ISQLTablespaceClause.SQLExtentManagementLocalClause();
        }
        if (this.acceptAndNextToken(SQLToken.TokenKind.DATAFILE)) {
            ISQLTablespaceClause.SQLDataFileClause action = new ISQLTablespaceClause.SQLDataFileClause();
            for (; ; ) {
                SQLFileSpecification item = exprParser.parseFileSpecification();
                action.addItem(item);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            return action;
        }

        if (this.accept(SQLToken.TokenKind.SYSAUX)) {
            return parseSysauxDataFileClause();
        }

        // default_tablespace/default_temp_tablespace
        if (this.acceptAndNextToken(SQLToken.TokenKind.DEFAULT)) {
            if (this.acceptAndNextToken(SQLToken.TokenKind.TABLESPACE)) {
                this.reset(make);
                return parseDefaultTablespaceClause();
            }

            if ((this.acceptAndNextToken(SQLToken.TokenKind.TEMPORARY) && this.acceptAndNextToken(SQLToken.TokenKind.TABLESPACE))
                    || (this.acceptAndNextToken(SQLToken.TokenKind.LOCAL) && this.acceptAndNextToken(SQLToken.TokenKind.TEMPORARY) && this.acceptAndNextToken(SQLToken.TokenKind.TABLESPACE))) {
                this.reset(make);
                return parseDefaultTempTablespaceClause();
            }

            throw new SQLParserException();
        }

        // undo_tablespace
        if (this.acceptAndNextToken(SQLToken.TokenKind.UNDO)) {
            this.reset(make);
            return parseUndoTablespaceClause();
        }

        // default_temp_tablespace / undo_tablespace
        if (this.acceptAndNextToken(SQLToken.TokenKind.BIGFILE)
                || this.acceptAndNextToken(SQLToken.TokenKind.SMALLFILE)) {

            if (this.acceptAndNextToken(SQLToken.TokenKind.DEFAULT)) {
                if (this.acceptAndNextToken(SQLToken.TokenKind.TABLESPACE)) {
                    this.reset(make);

                    return parseDefaultTablespaceClause();
                }

                if ((this.acceptAndNextToken(SQLToken.TokenKind.TEMPORARY) && this.acceptAndNextToken(SQLToken.TokenKind.TABLESPACE))
                        || (this.acceptAndNextToken(SQLToken.TokenKind.LOCAL) && this.acceptAndNextToken(SQLToken.TokenKind.TEMPORARY) && this.acceptAndNextToken(SQLToken.TokenKind.TABLESPACE))) {
                    this.reset(make);
                    return parseDefaultTempTablespaceClause();
                }

                throw new SQLParserException();
            }


            if (this.acceptAndNextToken(SQLToken.TokenKind.UNDO)) {
                this.reset(make);
                return parseUndoTablespaceClause();
            }

            if (this.acceptAndNextToken(SQLToken.TokenKind.USER_DATA)) {
                this.acceptAndNextToken(SQLToken.TokenKind.TABLESPACE, true);
                ISQLName name = exprParser.parseName();

                ISQLCreateDatabaseAction.SQLUserDataTableSpaceAction action = new ISQLCreateDatabaseAction.SQLUserDataTableSpaceAction(name);

                this.acceptAndNextToken(SQLToken.TokenKind.DATAFILE, true);
                for (;;) {
                    SQLFileSpecification dataFile = exprParser.parseFileSpecification();
                    action.addDataFile(dataFile);
                    if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                        break;
                    }
                }

                return action;
            }

            throw new SQLParserException();
        }


        // enable_pluggable_database
        if (this.accept(SQLToken.TokenKind.ENABLE)) {
            return parseEnablePluggableDatabase();
        }

        return null;
    }


    @Override
    public SQLAlterDatabaseStatement parseAlter() {
        return super.parseAlter();
    }

    @Override
    public SQLDropDatabaseStatement parseDrop() {
        return super.parseDrop();
    }

    public SQLDefaultTablespaceClause parseDefaultTablespaceClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.DEFAULT)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.TABLESPACE, true);
        ISQLExpr name = exprParser.parseExpr();

        SQLFileSpecification dataFile = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.DATAFILE)) {
            dataFile = exprParser.parseFileSpecification();
        }

        SQLExtentManagementClause extentManagementClause = parseExtentManagementClause();


        SQLDefaultTablespaceClause x = new SQLDefaultTablespaceClause();
        x.setName(name);
        x.setDataFile(dataFile);
        x.setExtentManagementClause(extentManagementClause);

        return x;
    }

    public ISQLDefaultTempTablespaceClause parseDefaultTempTablespaceClause() {
        ISQLDefaultTempTablespaceClause x = null;
        ISQLDefaultTempTablespaceClause.SQLFileType fileType = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.BIGFILE)) {
            fileType = ISQLDefaultTempTablespaceClause.SQLFileType.BIGFILE;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.SMALLFILE)) {
            fileType = ISQLDefaultTempTablespaceClause.SQLFileType.SMALLFILE;

        }

        this.acceptAndNextToken(SQLToken.TokenKind.DEFAULT, true);

        if (this.acceptAndNextToken(SQLToken.TokenKind.TEMPORARY)
                && this.acceptAndNextToken(SQLToken.TokenKind.TABLESPACE)) {
            x = new ISQLDefaultTempTablespaceClause.SQLDefaultTemporaryTablespaceClause();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.LOCAL)
                && this.acceptAndNextToken(SQLToken.TokenKind.TEMPORARY)
                && this.acceptAndNextToken(SQLToken.TokenKind.TABLESPACE)
                && this.acceptAndNextToken(SQLToken.TokenKind.FOR)) {

            ISQLDefaultTempTablespaceClause.SQLForType forType = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.ALL)) {
                forType = ISQLDefaultTempTablespaceClause.SQLForType.ALL;

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.LEAF)) {
                forType = ISQLDefaultTempTablespaceClause.SQLForType.LEAF;

            } else {
                throw new SQLParserException();
            }

            x = new ISQLDefaultTempTablespaceClause.SQLDefaultLocalTemporaryTablespaceClause();
            ((ISQLDefaultTempTablespaceClause.SQLDefaultLocalTemporaryTablespaceClause) x).setForType(forType);
        } else {
            throw new SQLParserException();
        }

        x.setFileType(fileType);

        ISQLExpr name = exprParser.parseExpr();
        x.setName(name);

        if (this.acceptAndNextToken(SQLToken.TokenKind.TEMPFILE)) {
            for (; ; ) {
                SQLFileSpecification tempFile = exprParser.parseFileSpecification();
                x.addTempFile(tempFile);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
        }

        SQLExtentManagementClause extentManagementClause = parseExtentManagementClause();
        x.setExtentManagementClause(extentManagementClause);

        return x;
    }



    public SQLExtentManagementClause parseExtentManagementClause() {
        SQLLexer.SQLMake make = this.make();
        if (!this.acceptAndNextToken(SQLToken.TokenKind.EXTENT)
                && !this.acceptAndNextToken(SQLToken.TokenKind.MANAGEMENT)
                && !this.acceptAndNextToken(SQLToken.TokenKind.LOCAL)) {
            this.reset(make);
            return null;
        }

        ISQLExpr expr = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.AUTOALLOCATE)) {
            expr = new SQLExtentManagementClause.SQLAutoAllocateExpr();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.UNIFORM)) {
            expr = new SQLExtentManagementClause.SQLUniformExpr();
        }

        return new SQLExtentManagementClause(expr);
    }

    public ISQLTablespaceClause.SQLSysauxDataFileClause parseSysauxDataFileClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.SYSAUX)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.DATAFILE, true);

        ISQLTablespaceClause.SQLSysauxDataFileClause x = new ISQLTablespaceClause.SQLSysauxDataFileClause();
        for (;;) {
            SQLFileSpecification dataFile = exprParser.parseFileSpecification();
            x.addDataFile(dataFile);
            if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                break;
            }
        }

        return x;
    }


    public SQLUndoTablespaceClause parseUndoTablespaceClause() {
        SQLUndoTablespaceClause.SQLFileType fileType = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.BIGFILE)) {
            fileType = SQLUndoTablespaceClause.SQLFileType.BIGFILE;

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.SMALLFILE)) {
            fileType = SQLUndoTablespaceClause.SQLFileType.SMALLFILE;

        }

        if (!this.acceptAndNextToken(SQLToken.TokenKind.UNDO)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.TABLESPACE, true);

        ISQLExpr name = exprParser.parseExpr();
        SQLUndoTablespaceClause x = new SQLUndoTablespaceClause(name);

        if (this.acceptAndNextToken(SQLToken.TokenKind.DATAFILE)) {
            for (; ; ) {
                SQLFileSpecification dataFile = exprParser.parseFileSpecification();
                x.addDataFile(dataFile);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
        }

        return x;
    }


    public SQLSetTimeZoneClause parseSetTimeZoneClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.SET)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.TIME_ZONE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.EQ, true);
        ISQLExpr value = exprParser.parseExpr();
        return new SQLSetTimeZoneClause(value);
    }


    public SQLEnablePluggableDatabaseClause parseEnablePluggableDatabase() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.ENABLE)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.PLUGGABLE, true);
        this.acceptAndNextToken(SQLToken.TokenKind.DATABASE, true);

        SQLEnablePluggableDatabaseClause x = new SQLEnablePluggableDatabaseClause();

        if (this.acceptAndNextToken(SQLToken.TokenKind.SEED)) {
            x.setSeed(true);
            SQLFileNameConvertClause fileNameConvertClause = parseFileNameConvertClause();
            x.setFileNameConvert(fileNameConvertClause);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.SYSTEM)) {
            SQLTablespaceDataFileClause system = parseTablespaceDataFileClause();
            x.setSystem(system);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.SYSAUX)) {
            SQLTablespaceDataFileClause sysaux = parseTablespaceDataFileClause();
            x.setSysaux(sysaux);
        }

        return x;
    }

    public SQLFileNameConvertClause parseFileNameConvertClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.FILE_NAME_CONVERT)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.EQ, true);


        SQLFileNameConvertClause.ISQLValue value = null;
        if (this.acceptAndNextToken(SQLToken.TokenKind.NONE)) {

            value = new SQLFileNameConvertClause.SQLNoneValue();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            SQLFileNameConvertClause.SQLValues items = new SQLFileNameConvertClause.SQLValues();
            value = items;

            for (; ; ) {

                ISQLExpr name = exprParser.parseExpr();
                this.acceptAndNextToken(SQLToken.TokenKind.COMMA, true);
                ISQLExpr replace = exprParser.parseExpr();
                SQLFileNameConvertClause.SQLItem item = new SQLFileNameConvertClause.SQLItem(name, replace);
                items.addItem(item);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        return new SQLFileNameConvertClause(value);
    }


    public SQLTablespaceDataFileClause parseTablespaceDataFileClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.DATAFILES)) {
            return null;
        }

        SQLTablespaceDataFileClause x = new SQLTablespaceDataFileClause();
        for (; ; ) {
            ISQLExpr item = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.SIZE)) {
                SQLSizeClause sizeClause = exprParser.parseSizeClause();
                item = new SQLTablespaceDataFileClause.SQLSizeSizeClauseItem(sizeClause);

            } else {
                item = exprParser.parseAutoExtendClause();
            }
            if (item == null) {
                break;
            }
            x.addItem(item);
        }

        return x;
    }

    public ISQLUndoModeClause parseUndoModeClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.LOCAL)) {
            return null;
        }
        this.acceptAndNextToken(SQLToken.TokenKind.UNDO, true);
        if (this.acceptAndNextToken(SQLToken.TokenKind.ON)) {
            return new ISQLUndoModeClause.SQLUndoModeOnClause();

        } else if (this.acceptAndNextToken(SQLToken.TokenKind.OFF)) {
            return new ISQLUndoModeClause.SQLUndoModeOffClause();

        }

        throw new SQLParserException();
    }


}
