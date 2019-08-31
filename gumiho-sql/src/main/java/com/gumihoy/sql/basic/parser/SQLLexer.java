package com.gumihoy.sql.basic.parser;

import static com.gumihoy.sql.basic.parser.SQLToken.TokenKind.*;
import static com.gumihoy.sql.util.SQLCharacterUtils.isSQLIdentifierPart;
import static com.gumihoy.sql.util.SQLCharacterUtils.isSQLIdentifierStart;
import static com.gumihoy.sql.util.SQLCharacterUtils.isWhitespace;

import com.gumihoy.sql.basic.ast.enums.SQLCommentType;
import com.gumihoy.sql.basic.ast.expr.comment.ISQLComment;
import com.gumihoy.sql.basic.ast.expr.comment.SQLMinusCommentExpr;
import com.gumihoy.sql.basic.ast.expr.comment.SQLMultiLineCommentExpr;
import com.gumihoy.sql.basic.ast.expr.comment.SQLSharpCommentExpr;
import com.gumihoy.sql.config.SQLLexerConfig;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.HashUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kent on 2019-06-13.
 */
public class SQLLexer {

    // 替换
    protected final static byte EOI = 0x1A;

    protected DBType dbType;
    protected SQLToken prevToken;
    protected SQLToken token;

    protected final String input;
    protected final int inputLen;
    protected SQLLexerConfig config;
    protected StringBuilder commentBuilder;
    protected List<ISQLComment> comments = new ArrayList<>();
    protected int pos;
    protected int startPos;
    protected char ch;
    protected StringBuilder sbuf = new StringBuilder();
    protected long lowerHash;
    protected long hash;
    protected int line;
    protected int charPositionInLine;

    protected SQLToken.TokenKind kind;
    public static SQLReservedWords RESERVED_WORDS = SQLReservedWords.DEFAULT_RESERVED_WORDS;


    public SQLLexer(String input) {
        this(input, DBType.SQL);
    }

    public SQLLexer(String input, SQLLexerConfig config) {
        this(input, DBType.SQL, config);
    }

    public SQLLexer(String input, DBType dbType) {
        this(input, DBType.SQL, null);
    }

    public SQLLexer(String input, DBType dbType, SQLLexerConfig config) {
        this.dbType = dbType;
        this.input = input;
        this.inputLen = input.length();
        if (config == null) {
            config = new SQLLexerConfig();
        }
        this.config = config;
        if (this.config.keepComment) {
            commentBuilder = new StringBuilder();
        }

        this.pos = 0;
        this.ch = charAt(pos);
    }

    private void clear() {
        this.sbuf.setLength(0);
        lowerHash = 0L;
        hash = 0L;
    }

    public void nextToken() {

        this.clear();

        loop:
        for (; ; ) {

            // 处理空格
            scanWhitespace();

            switch (this.ch) {
                case '"':
                    startPos = pos;
                    scanDQuotaString();
                    break loop;
                case '`':
                    startPos = pos;
                    scanBQuotaString();
                    break loop;
                case '\'':
                    startPos = pos;
                    scanSQuotaString();
                    break loop;

                case '0':
                    startPos = pos;
                    scanStartZero();
                    break loop;
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    startPos = pos;
                    scanNumeric();
                    break loop;

                case '!':
                    startPos = pos;
                    scanBang();
                    break loop;
                case '#':
                    startPos = pos;
                    kind = SQLToken.TokenKind.SHARP;
                    scanChar();
                    break loop;

                case '(':
                case '（':
                    startPos = pos;
                    kind = LPAREN;
                    scanChar();
                    break loop;
                case ')':
                case '）':
                    startPos = pos;
                    charPositionInLine = pos;
                    kind = RPAREN;
                    scanChar();
                    break loop;
                case '{':
                    startPos = pos;
                    charPositionInLine = pos;
                    kind = LBRACE;
                    scanChar();
                    break loop;
                case '}':
                    charPositionInLine = pos;
                    kind = RBRACE;
                    scanChar();
                    break loop;
                case '[':
                    startPos = pos;
                    charPositionInLine = pos;
                    kind = LBRACKET;
                    scanChar();
                    break loop;
                case ']':
                    startPos = pos;
                    charPositionInLine = pos;
                    kind = RBRACKET;
                    scanChar();
                    break loop;
                case ';':
                case '；':
                    startPos = pos;
                    charPositionInLine = pos;
                    kind = SEMI;
                    scanChar();
                    break loop;
                case ',':
                case '，':
                    startPos = pos;
                    charPositionInLine = pos;
                    kind = COMMA;
                    scanChar();
                    break loop;


                case '?':
                    startPos = pos;
                    charPositionInLine = pos;
                    scanQues();
                    break loop;

                case '%':
                    startPos = pos;
                    kind = SQLToken.TokenKind.PERCENT;
                    scanChar();
                    break loop;
                case '&':
                    startPos = pos;
                    kind = SQLToken.TokenKind.AMP;
                    scanChar();
                    break loop;
                case '*':
                    charPositionInLine = pos;
                    scanStar();
                    break loop;
                case '/':
                    charPositionInLine = pos;
                    scanSlash();
                    break loop;
                case '+':
                    startPos = pos;
                    charPositionInLine = pos;
                    scanPlus();
                    break loop;
                case '-':
                    startPos = pos;
                    charPositionInLine = pos;
                    scanSub();
                    break loop;
                case '.':
                    startPos = pos;
                    charPositionInLine = pos;
                    scanDot();
                    break loop;
                case ':':
                    startPos = pos;
                    charPositionInLine = pos;
                    scanColon();
                    break loop;
                case '=':
                    startPos = pos;
                    charPositionInLine = pos;
                    scanEQ();
                    break loop;
                case '>':
                    startPos = pos;
                    charPositionInLine = pos;
                    scanGT();
                    break loop;
                case '<':
                    startPos = pos;
                    charPositionInLine = pos;
                    scanLT();
                    break loop;
                case '^':
                    startPos = pos;
                    charPositionInLine = pos;
                    kind = SQLToken.TokenKind.CARET;
                    scanChar();
                    break loop;
                case '|':
                    startPos = pos;
                    charPositionInLine = pos;
                    scanBar();
                    break loop;
                case '@':
                    startPos = pos;
                    kind = SQLToken.TokenKind.MONKEYS_AT;
                    scanChar();
                    break loop;
                case 'B':
                    startPos = pos;
                    scanStartB();
                    break loop;
                case 'X':
                    startPos = pos;
                    scanStartX();
                    break loop;
                case 'b':
                    startPos = pos;
                    scanStartB();
                    break loop;
                case 'x':
                    startPos = pos;
                    scanStartX();
                    break loop;

                default:

                    if (isSQLIdentifierStart(ch)) {
                        startPos = pos;
                        charPositionInLine = pos;
                        scanIdent();
                        break loop;
                    }

                    if (isEOF()) {
                        kind = EOF;
                        break loop;
                    }

                    throw new SQLParserException("TODO:" + ch);
            }
        }

        prevToken = token;
        token = SQLToken.of(kind, line, charPositionInLine);

    }

    public boolean isEOF() {
        return pos >= inputLen;
    }

    /**
     * DOT(".")
     * ELLIPSIS("...")
     */
    public void scanDot() {
        // 默认值
        kind = DOT;
        scanChar();
        if (isDigit(this.ch)) {
            unscan();
            scanNumeric();
            return;
        }
        if (ch == '.') {
            kind = DOTDOT;
            scanChar();
            if (ch == '.') {
                kind = ELLIPSIS;
                scanChar();
            }
            return;
        }

    }

    public void scanColon() {
        kind = SQLToken.TokenKind.COLON;
        scanChar();
        if (ch == '=') {
            kind = SQLToken.TokenKind.ASSIGN;
            scanChar();
        }
    }


    public void scanQues() {
        // 默认值
        kind = SQLToken.TokenKind.QUES;
        scanChar();
    }


    // 处理空格
    protected void scanWhitespace() {
        for (; ; ) {
            if (isWhitespace(ch)) {
                if (ch == '\n') {
                    incrementLine();
                }
                scanChar();
                continue;
            }
            break;
        }
    }


    public void scanIdent() {
        putChar();
        lowerHash = HashUtils.fnv_1a_64_lower(this.ch);

        for (; ; ) {
            scanChar();
            if (ch == EOI) {
                break;
            }
            if (!isSQLIdentifierPart(ch)) {
                break;
            }
            putChar();
            lowerHash = HashUtils.fnv_1a_64_lower(lowerHash, this.ch);
        }

        this.kind = lookupKind(lowerHash);
    }

    public void scanDoubleQuotedIdent() {
        for (; ; ) {
            putAndScanChar();
            if (ch == '"') {
                putChar();
                break;
            }
        }

        kind = SQLToken.TokenKind.DOUBLE_QUOTED_IDENTIFIER;
    }

    /**
     * ' ('\\'. | '\'\'' | ~('\'' | '\\'))* ';
     */
    public void scanSQuotaString() {
        startPos = pos;
        kind = SQLToken.TokenKind.STRING_LITERAL;
        for (; ; ) {
            scanString();
            if (ch == '\'') {
                putChar('\\');
                putChar('\'');
            }
            for (; ; ) {
                if (isWhitespace(ch)) {
                    scanChar();
                    continue;
                }
                if (ch == '\'') {
                    scanSQuotaString();
                }
                return;
            }
        }
    }

    /**
     * " ( '\\'. | '""' | ~('"'| '\\') )* ";
     */
    public void scanDQuotaString() {
        kind = SQLToken.TokenKind.DOUBLE_QUOTED_IDENTIFIER;
        for (; ; ) {
            scanString();
            if (ch == '"') {
                putChar('\"');
            }
            for (; ; ) {
                if (isWhitespace(ch)) {
                    scanChar();
                    continue;
                }
                if (ch == '"') {
                    scanSQuotaString();
                }
                return;
            }
        }
    }

    /**
     * '`' ( '\\'. | '``' | ~('`'|'\\'))* '`';
     */
    public void scanBQuotaString() {
        throw new SQLParserException("Syn" + ch);
    }

    public void scanString() {
        char firstCh = ch;
        scanChar();
        for (; ; ) {
            if (ch == '\\') {
                putAndScanChar();
                putAndScanChar();
            }
            if (ch == firstCh) {
                scanChar();
                break;
            }
            if (ch == EOI) {
                throw new SQLParserException("Syn");
            }
            putChar();
            scanChar();
        }
    }

    /**
     * 0b''
     * 0x''
     * 01233
     */
    public void scanStartZero() {
        if (charAt(pos + 1) == 'b') {
            scanChar();
            scanBitValue();
        } else if (charAt(pos + 1) == 'x') {
            scanChar();
            scanHexaDecimal();
        } else {
            scanNumeric();
        }
    }

    /**
     * 1, .2, 3.4, -5, -6.78, +9.10
     * 1.2E3, 1.2E-3, -1.2E3, -1.2E-3
     */
    public void scanNumeric() {
        // 默认值
        kind = SQLToken.TokenKind.INTEGER_LITERAL;

        if (ch == '-' || ch == '+') {
            // 处理空格
            putAndScanChar();
            scanWhitespace();
        }

        scanDigit();

        if (ch == '.') {
            if (charAt(pos + 1) == '.') {
                return;
            }
            kind = SQLToken.TokenKind.DECIMAL_LITERAL;
            putAndScanChar();
            scanDigit();
        }

        if (ch == 'e' || ch == 'E') {
            kind = SQLToken.TokenKind.FLOATING_POINT_LITERAL;
            putAndScanChar();
            if (ch == '-' || ch == '+') {
                putAndScanChar();
            }
            scanDigit();
        }
    }

    public static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public void scanDigit() {
        for (; ; ) {
            if (isDigit(this.ch)) {
                putAndScanChar();
                continue;
            }
            break;
        }
    }

    /**
     * !
     */
    public void scanBang() {
        kind = SQLToken.TokenKind.BANG;
        scanChar();
        if (ch == '=') {
            kind = SQLToken.TokenKind.BANGEQ;
            scanChar();
        }
    }

    public void scanStar() {
        // 默认值
        kind = SQLToken.TokenKind.STAR;
        scanChar();
    }

    public void scanSlash() {
        // 默认值
        kind = SQLToken.TokenKind.SLASH;
        int subPos = pos;
        char next = charAt(++subPos);
        if (next == '*') {
            scanMultiLineComment();
        } else {
            scanChar();
        }
    }

    public void scanPlus() {
        // 默认值
        kind = SQLToken.TokenKind.PLUS;

        int subPos = pos;
        char next = charAt(++subPos);
        // 处理空格
        for (; ; ) {
            if (isWhitespace(next)) {
                if (next == '\n') {
                    incrementLine();
                }
                next = charAt(++subPos);
                continue;
            }
            break;
        }

        if ((token == null || token.kind == LPAREN || token.kind == COMMA) && next >= '0' && next <= '9') {
            scanNumeric();
        } else {
            scanChar();
        }
    }

    public void scanSub() {
        // 默认值
        kind = SQLToken.TokenKind.SUB;

        int subPos = pos;
        char next = charAt(++subPos);
        if (next == '-') {
            scanMinusComment();
        } else {
            // 处理空格
            for (; ; ) {
                if (isWhitespace(next)) {
                    if (next == '\n') {
                        incrementLine();
                    }
                    next = charAt(++subPos);
                    continue;
                }
                break;
            }

            if ((token == null || token.kind == LPAREN || token.kind == COMMA) && next >= '0' && next <= '9') {
                scanNumeric();
            } else {
                scanChar();
            }
        }
    }

    public void scanEQ() {
        kind = SQLToken.TokenKind.EQ;
        scanChar();
    }

    /**
     * >
     * >=
     * >>
     * >>>
     */
    public void scanGT() {
        kind = SQLToken.TokenKind.GT;
        scanChar();

        if (ch == '=') {
            kind = SQLToken.TokenKind.GTEQ;
            scanChar();

        } else if (ch == '>') {
            kind = SQLToken.TokenKind.GTGT;
            scanChar();
            if (ch == '>') {
                kind = SQLToken.TokenKind.GTGTGT;
                scanChar();
            }
        }
    }

    /**
     * <
     * <<
     * <>
     * <=
     * <=>
     */
    public void scanLT() {
        kind = SQLToken.TokenKind.LT;
        scanChar();
        if (ch == '<') {
            kind = SQLToken.TokenKind.LTLT;
            scanChar();
        } else if (ch == '=') {
            kind = SQLToken.TokenKind.LTEQ;
            scanChar();

            if (ch == '>') {
                kind = SQLToken.TokenKind.LTEQ;
                scanChar();
            }
        } else if (ch == '>') {
            kind = SQLToken.TokenKind.LTGT;
            scanChar();
        }
    }

    public void scanBar() {
        kind = SQLToken.TokenKind.BAR;

        scanChar();
        scanWhitespace();
        if (this.ch == '|') {
            kind = SQLToken.TokenKind.BARBAR;
            scanChar();
        }
    }

    /**
     * /*   * /
     */
    public void scanMultiLineComment() {
        if (config.keepComment) {
            putAndScanCommentChar();
            putAndScanCommentChar();
        } else {
            scanChar();
            scanChar();
        }

        for (; ; ) {
            if (ch == '*' && charAt(pos + 1) == '/') {
                if (config.keepComment) {
                    putAndScanCommentChar();
                    putAndScanCommentChar();
                } else {
                    scanChar();
                    scanChar();
                }

                break;
            }

            if (config.keepComment) {
                putAndScanCommentChar();
            } else {
                scanChar();
            }

        }
        addComment(SQLCommentType.MULTI_LINE);
        nextToken();
    }

    public void scanMinusComment() {
        for (; ; ) {
            if (config.keepComment) {
                putCommentChar();
            }
            scanChar();
            if (ch == '\r'
                    || ch == '\n') {
                break;
            }
        }

        addComment(SQLCommentType.MINUS);
        nextToken();
    }

    public void scanSharpComment() {

        for (; ; ) {
            if (config.keepComment) {
                putCommentChar();
            }
            scanChar();
            if (ch == '\r'
                    || ch == '\n') {
                break;
            }
        }
        addComment(SQLCommentType.SHARP);
        nextToken();
    }


    public void scanMultiLineHint() {

        for (; ; ) {
            putAndScanCommentChar();
            if (ch == '*' && charAt(pos + 1) == '/') {
                putAndScanCommentChar();
                putAndScanCommentChar();
                break;
            }
        }

        addComment(SQLCommentType.MULTI_LINE);
        nextToken();
    }

    public void scanMinusHint() {
        for (; ; ) {
            putAndScanCommentChar();
            if (ch == '\r'
                    || ch == '\n') {
                break;
            }
        }

        addComment(SQLCommentType.MINUS);
        nextToken();
    }


    public void addComment(SQLCommentType type) {
        if (commentBuilder == null
                || commentBuilder.length() == 0
                || type == null) {
            return;
        }

        ISQLComment comment = null;
        switch (type) {
            case MULTI_LINE:
                comment = new SQLMultiLineCommentExpr(commentBuilder.toString());
                break;
            case MINUS:
                comment = new SQLMinusCommentExpr(commentBuilder.toString());
                break;
            case SHARP:
                comment = new SQLSharpCommentExpr(commentBuilder.toString());
                break;
            default:
                throw new SQLParserException();
        }
        comments.add(comment);
        commentBuilder.setLength(0);
    }


    /**
     * ident
     * B'XX'
     */
    public void scanStartB() {
        char nextCh = charAt(pos + 1);
        if (nextCh == '\'') {
            scanChar();
            scanBitValue();
            return;
        }

        scanIdent();
    }

    /**
     * ident
     * X'XX'
     */
    public void scanStartX() {
        char nextCh = charAt(pos + 1);
        if (nextCh == '\'') {
            scanChar();
            scanHexaDecimal();
            return;
        }

        scanIdent();
    }

    public static boolean isBitValue(char ch) {
        return ch == '0' || ch == '1';
    }

    /**
     * '0101'
     * '1111'
     */
    public void scanBitValue() {
        kind = SQLToken.TokenKind.BIT_VALUE_LITERAL;
        scanChar();
        for (; ; ) {
            if (ch == '\'') {
                scanChar();
                break;
            }
            if (!isBitValue(ch)) {
                throw new SQLParserException("");
            }
            putAndScanChar();
        }
    }

    /**
     * 'xxx'
     * 'xxx'
     */
    public void scanHexaDecimal() {
        kind = SQLToken.TokenKind.HEXA_DECIMAL_LITERAL;
        scanChar();
        for (; ; ) {
            if (ch == '\'') {
                scanChar();
                break;
            }
            if (ch == EOI) {
                throw new SQLParserException("");
            }
            putAndScanChar();
        }
    }

    public final char charAt(int index) {
        if (index >= input.length()) {
            return EOI;
        }

        return input.charAt(index);
    }

    public SQLToken.TokenKind lookupKind(long hash) {
        SQLToken.TokenKind kind = RESERVED_WORDS.getReservedWords(hash);
        return kind == null ? UNQUOTED_IDENTIFIER : kind;
    }


    protected final void scanChar() {
        ch = charAt(++pos);
    }

    protected void unscan() {
        ch = charAt(--pos);
    }

    protected final void incrementLine() {
        line++;
        charPositionInLine = 0;
    }


    protected final void putCommentChar() {
        putCommentChar(this.ch);
    }

    protected final void putAndScanCommentChar() {
        putCommentChar(this.ch);
        this.scanChar();
    }

    protected final void putCommentChar(char ch) {
        this.commentBuilder.append(ch);
    }


    protected final void putChar() {
        putChar(this.ch);
    }

    protected final void putAndScanChar() {
        putChar(this.ch);
        this.scanChar();
    }

    protected final void putChar(char ch) {
        this.sbuf.append(ch);
    }


    public final SQLToken prevToken() {
        return prevToken;
    }

    public final SQLToken token() {
        return token;
    }

    public final long lowerHash() {
        return lowerHash;
    }

    public final String getStringValue() {
        return this.sbuf.toString();
    }

    public SQLMake make() {
        return SQLMake.make(this);
    }

    public void reset(SQLMake make) {
        this.prevToken = make.prevToken;
        this.token = make.token;
        this.pos = make.pos;
        this.ch = make.ch;

        this.sbuf.setLength(0);
        this.sbuf.append(make.sbuf);

        this.lowerHash = make.lowerHash;
        this.hash = make.hash;

        this.line = make.line;
        this.charPositionInLine = make.charPositionInLine;
        this.kind = make.kind;
    }

    public static class SQLMake {
        protected SQLToken prevToken;
        protected SQLToken token;
        protected int pos;
        protected char ch;

        protected StringBuilder sbuf;
        protected long lowerHash;
        protected long hash;

        protected int line;
        protected int charPositionInLine;
        protected SQLToken.TokenKind kind;

        public static SQLMake make(SQLLexer lexer) {
            SQLMake make = new SQLMake();
            make.prevToken = lexer.prevToken;
            make.token = lexer.token;
            make.pos = lexer.pos;
            make.ch = lexer.ch;

            make.sbuf = new StringBuilder(lexer.sbuf);

            make.lowerHash = lexer.lowerHash;
            make.hash = lexer.hash;

            make.line = lexer.line;
            make.charPositionInLine = lexer.charPositionInLine;
            make.kind = lexer.kind;

            return make;
        }

    }

    public static void put(Map<Long, SQLToken.TokenKind> map, SQLToken.TokenKind kind) {
        map.put(kind.keyWord.lowerHash, kind);
    }


    public String errorInfo() {
        String message = input.substring(startPos);
        return message;
    }

}
