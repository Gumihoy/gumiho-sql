package com.gumihoy.sql.basic.parser;

import com.gumihoy.sql.util.HashUtils;

/**
 * @author kent on 2019-06-20.
 */
public enum SQLKeyWord {

    // A
    ACCESS("access", "ACCESS"),
    ACCESSIBLE("accessible", "ACCESSIBLE"),
    ACTION("action", "ACTION"),
    ADD("add", "ADD"),
    ADD_MONTHS("add_months", "ADD_MONTHS"),
    ADVANCED("advanced", "ADVANCED"),
    AFTER("after", "AFTER"),
    AGENT("agent", "AGENT"),
    AGGREGATE("aggregate", "AGGREGATE"),
    ALGORITHM("algorithm", "ALGORITHM"),
    ALL("all", "ALL"),
    ALLOCATE("allocate", "ALLOCATE"),
    ALLOW("allow", "ALLOW"),
    ALTER("alter", "ALTER"),
    ALWAYS("always", "ALWAYS"),
    ANALYZE("analyze", "ANALYZE"),
    AND("and", "AND"),
    ANY("any", "ANY"),
    ANYDATA("anydata", "ANYDATA"),
    ANYDATASET("anydataset", "ANYDATASET"),
    ANYSCHEMA("anyschema", "ANYSCHEMA"),
    ANYTYPE("anytype", "ANYTYPE"),
    ARCHIVE("archive", "ARCHIVE"),
    ARCHIVELOG("archivelog", "ARCHIVELOG"),
    ARE(""),
    ARRAY("array", "ARRAY"),
    APPLY("apply", "APPLY"),
    AS("as", "AS"),
    ASC("asc", "ASC"),
    ASENSITIVE(""),
    ASSOCIATE("associate", "ASSOCIATE"),
    ASYMMETRIC(""),
    AT("at", "AT"),
    ATTRIBUTE("attribute", "ATTRIBUTE"),
    ATTRIBUTES("attributes", "ATTRIBUTES"),
    AUDIT("audit", "AUDIT"),
    ATOMIC(""),
    AUTHENTICATED("authenticated", "AUTHENTICATED"),
    AUTHID("authid", "AUTHID"),
    AUTHORIZATION(""),
    AUTO("auto", "AUTO"),
    AUTOALLOCATE("autoallocate", "AUTOALLOCATE"),
    AUTOEXTEND("autoextend", "AUTOEXTEND"),
    AUTONOMOUS_TRANSACTION("autonomous_transaction", "AUTONOMOUS_TRANSACTION"),
    AVAILABILITY("availability", "AVAILABILITY"),

    // B
    B("b", "B"),
    BASIC("basic", "BASIC"),
    BASICFILE("basicfile", "BASICFILE"),
    BEFORE("before", "BEFORE"),
    BEGIN("begin", "BEGIN"),
    BETWEEN("between", "BETWEEN"),
    BFILE("bfile", "BFILE"),
    BIGFILE("bigfile", "BIGFILE"),
    BIGINT("bigint", "BIGINT"),
    BINARY("binary", "BINARY"),
    BINARY_DOUBLE("binary_double", "BINARY_DOUBLE"),
    BINARY_FLOAT("binary_float", "BINARY_FLOAT"),
    BINARY_INTEGER("binary_integer", "BINARY_INTEGER"),
    BIT("bit", "BIT"),
    BITAND("bitand", "BITAND"),
    BITMAP("bitmap", "BITMAP"),
    BLOB("blob", "BLOB"),
    BLOCK("block", "BLOCK"),
    BLOCKSIZE("blocksize", "BLOCKSIZE"),
    BODY("body", "BODY"),
    BOOL("bool", "BOOL"),
    BOOLEAN("boolean", "BOOLEAN"),
    BOTH(""),
    BUFFER_POOL("buffer_pool", "BUFFER_POOL"),
    BULK("bulk", "BULK"),
    BY("by", "BY"),
    BYTE("byte", "BYTE"),

    // C
    C("c", "C"),
    CACHE("cache", "CACHE"),
    CALL("call", "CALL"),
    CALLED(""),
    CAPACITY("capacity", "CAPACITY"),
    CASCADE("cascade", "CASCADE"),
    CASCADED("cascaded", "CASCADED"),
    CASE("case", "CASE"),
    CAST("cast", "CAST"),
    CHANGE("change", "CHANGE"),
    CHAR("char", "CHAR"),
    CHAR_LENGTH("char_length", "CHAR_LENGTH"),
    CHARACTER("character", "CHARACTER"),
    CHARSETID("charsetid", "CHARSETID"),
    CHARSETFORM("charsetform", "CHARSETFORM"),
    CHECK("check", "CHECK"),
    CHECKPOINT("checkpoint", "CHECKPOINT"),
    CHUNK("chunk", "CHUNK"),
    CLEANUP("cleanup", "CLEANUP"),
    CHR("chr", "CHR"),
    CLOB("clob", "CLOB"),
    CLONE("clone", "CLONE"),
    CLOSE("close", "CLOSE"),
    CLUSTER("cluster", "CLUSTER"),
    CLUSTER_DETAILS("cluster_details", "CLUSTER_DETAILS"),
    CLUSTER_DISTANCE("cluster_distance", "CLUSTER_DISTANCE"),
    CLUSTER_ID("cluster_id", "CLUSTER_ID"),
    CLUSTER_PROBABILITY("cluster_probability", "CLUSTER_PROBABILITY"),
    CLUSTER_SET("cluster_set", "CLUSTER_SET"),
    COALESCE("coalesce", "COALESCE"),
    COLLATE("collate", "COLLATE"),
    COLLECT("collect", "COLLECT"),
    COL_NEW(":new",":NEW"),
    COL_OLD(":old",":OLD"),
    COL_PARENT(":parent",":PARENT"),
    COLUMN("column", "COLUMN"),
    COLUMNS("columns", "COLUMNS"),
    COMMENT("comment", "COMMENT"),
    COMMIT("commit", "COMMIT"),
    COMPILE("compile", "COMPILE"),
    COMPOUND("compound", "COMPOUND"),
    COMPRESS("compress", "COMPRESS"),
    CONCAT("concat", "CONCAT"),
    CONCURRENTLY("concurrently", "CONCURRENTLY"),
    CONNECT("connect", "CONNECT"),
    CONNECT_BY_ROOT("connect_by_root", "CONNECT_BY_ROOT"),
    CONSISTENT("consistent", "CONSISTENT"),
    CONSTANT("constant", "CONSTANT"),
    CONSTRAINT("constraint", "CONSTRAINT"),
    CONSTRAINTS("constraints", "CONSTRAINTS"),
    CONSTRUCTOR("constructor", "CONSTRUCTOR"),
    CONTAINER("container", "CONTAINER"),
    CONTAINER_MAP("container_map", "CONTAINER_MAP"),
    CONTAINERS_DEFAULT("containers_default", "CONTAINERS_DEFAULT"),
    CONTEXT("context", "CONTEXT"),
    CONTINUE("continue", "CONTINUE"),
    CONTROLFILE("controlfile", "CONTROLFILE"),
    CONVERT("convert", "CONVERT"),
    CORRESPONDING("corresponding", "CORRESPONDING"),
    COSH("cosh", "COSH"),
    COUNT("count", "COUNT"),
    COVERAGE("coverage", "COVERAGE"),
    CREATE("create", "CREATE"),
    CREATION("creation", "CREATION"),
    CROSS("cross", "CROSS"),
    CUBE("cube", "CUBE"),
    CURRENT("current", "CURRENT"),
    CURRENT_DATE("current_date", "CURRENT_DATE"),
    CURRENT_DEFAULT_TRANSFORM_GROUP(""),
    CURRENT_PATH("current_path", "CURRENT_PATH"),
    CURRENT_ROLE("current_role", "CURRENT_ROLE"),
    CURRENT_TIME("current_time", "CURRENT_TIME"),
    CURRENT_TIMESTAMP("current_timestamp", "CURRENT_TIMESTAMP"),
    CURRENT_USER("current_user", "CURRENT_USER"),
    CURRVAL("currval", "CURRVAL"),
    CURSOR("cursor", "CURSOR"),
    CYCLE("cycle", "CYCLE"),


    // D
    D("d", "D"),
    DATA("data", "DATA"),
    DATABASE("database", "DATABASE"),
    DATAFILE("datafile", "DATAFILE"),
    DATAFILES("datafiles", "DATAFILES"),
    DATE("date", "DATE"),
    DATETIME("datetime", "DATETIME"),
    DAY("day", "DAY"),
    DAY_MICROSECOND("day_microsecond", "DAY_MICROSECOND"),
    DAY_MINUTE("day_minute", "DAY_MINUTE"),
    DAY_SECOND("day_second", "DAY_SECOND"),
    DB_ROLE_CHANGE("db_role_change", "DB_ROLE_CHANGE"),
    DBTIMEZONE("dbtimezone", "DBTIMEZONE"),
    DDL("ddl", "DDL"),
    DEALLOCATE("deallocate", "DEALLOCATE"),
    DEBUG("debug", "DEBUG"),
    DEC("dec", "DEC"),
    DECIMAL("decimal", "DECIMAL"),
    DECLARE("declare", "DECLARE"),
    DECRYPT("decrypt", "DECRYPT"),
    DEDUPLICATE("deduplicate", "DEDUPLICATE"),
    DEFAULT("default", "DEFAULT"),
    DEFERRABLE("deferrable", "DEFERRABLE"),
    DEFERRED("deferred", "DEFERRED"),
    DEFINER("definer", "DEFINER"),
    DEFINITION("definition", "DEFINITION"),
    DELAYED("delayed", "DELAYED"),
    DELETE("delete", "DELETE"),
    DEPRECATE("deprecate", "DEPRECATE"),
    DEREF("deref", "DEREF"),
    DESC("desc", "DESC"),
    DESCRIBE("describe", "DESCRIBE"),
    DETERMINISTIC("deterministic", "DETERMINISTIC"),
    DIRECTORY("directory", "DIRECTORY"),
    DISABLE("disable", "DISABLE"),
    DISALLOW("disallow", "DISALLOW"),
    DISASSOCIATE("disassociate", "DISASSOCIATE"),
    DISCARD("discard", "DISCARD"),
    DISCONNECT("disconnect", "DISCONNECT"),
    DISTINCT("distinct", "DISTINCT"),
    DISTINCTROW("distinctrow", "DISTINCTROW"),
    DIV("div", "DIV"),
    DML("dml", "DML"),
    DO("do", "DO"),
    DOUBLE("double", "DOUBLE"),
    DROP("drop", "DROP"),
    DUAL("dual", "DUAL"),
    DUPLICATE("duplicate", "DUPLICATE"),
    DUPLICATED("duplicated", "DUPLICATED"),
    DURATION("duration", "DURATION"),
    DYNAMIC(""),

    // E
    E("e", "E"),
    EACH("each", "EACH"),
    EDITION("edition", "EDITION"),
    EDITIONABLE("editionable", "EDITIONABLE"),
    EDITIONING("editioning", "EDITIONING"),
    ELEMENT("element", "ELEMENT"),
    ELSE("else", "ELSE"),
    ELSEIF("elseif", "ELSEIF"),
    ENABLE("enable", "ENABLE"),
    ENCRYPT("encrypt", "ENCRYPT"),
    END("end", "END"),
    ENUM("enum", "ENUM"),
    ERRORS("errors", "ERRORS"),
    ESCAPE("escape", "ESCAPE"),
    EXCEPT("except", "EXCEPT"),
    EXCEPTION("exception", "EXCEPTION"),
    EXCEPTION_INIT("exception_init", "EXCEPTION_INIT"),
    EXCEPTIONS("exceptions", "EXCEPTIONS"),
    EXCHANGE("exchange", "EXCHANGE"),
    EXCLUDING("excluding", "EXCLUDING"),
    EXEC("exec", "EXEC"),
    EXECUTE("execute", "EXECUTE"),
    EXIT("exit", "EXIT"),
    EXISTS("exists", "EXISTS"),
    EXP("exp", "EXP"),
    EXPLAIN("explain", "EXPLAIN"),
    EXTEND("extend", "EXTEND"),
    EXTENDED("extended", "EXTENDED"),
    EXTENT("extent", "EXTENT"),
    EXTERNAL("external", "EXTERNAL"),
    EXTRACT("extract", "EXTRACT"),
    EXTERNALLY("externally", "EXTERNALLY"),

    // F
    F("f", "F"),
    FALSE("false", "FALSE"),
    FEATURE_COMPARE("feature_compare", "FEATURE_COMPARE"),
    FEATURE_DETAILS("feature_details", "FEATURE_DETAILS"),
    FEATURE_ID("feature_id", "FEATURE_ID"),
    FEATURE_SET("feature_set", "FEATURE_SET"),
    FEATURE_VALUE("feature_value", "FEATURE_VALUE"),
    FETCH("fetch", "FETCH"),
    FLASH_CACHE("flash_cache", "FLASH_CACHE"),
    FILE_NAME_CONVERT("file_name_convert", "FILE_NAME_CONVERT"),
    FILESYSTEM_LIKE_LOGGING("filesystem_like_logging", "FILESYSTEM_LIKE_LOGGING"),
    FILTER("filter", "FILTER"),
    FINAL("final", "FINAL"),
    FIRST("first", "FIRST"),
    FLASHBACK("flashback", "FLASHBACK"),
    FLOAT("float", "FLOAT"),
    FOLLOWING("following", "FOLLOWING"),
    FOLLOWS("follows", "FOLLOWS"),
    FOR("for", "FOR"),
    FORALL("forall", "FORALL"),
    FORCE("force", "FORCE"),
    FOREIGN("foreign", "FOREIGN"),
    FORMAT("format", "FORMAT"),
    FREE("free", "FREE"),
    FREELIST("freelist", "FREELIST"),
    FREELISTS("freelists", "FREELISTS"),
    FREEPOOLS("freepools", "FREEPOOLS"),
    FROM("from", "FROM"),
    FULL("full", "FULL"),
    FULLTEXT("fulltext", "FULLTEXT"),
    FUNCTION("function", "FUNCTION"),

    // G
    G("g", "G"),
    GENERATED("generated", "GENERATED"),
    GET("get", "GET"),
    GLOBAL("global", "GLOBAL"),
    GLOBALLY("globally", "GLOBALLY"),
    GOTO("goto", "GOTO"),
    GRANT("grant", "GRANT"),
    GROUP("group", "GROUP"),
    GROUPING("grouping", "GROUPING"),
    GROUPS("groups", "GROUPS"),


    // H
    HASH("hash", "HASH"),
    HAVING("having", "HAVING"),
    HEAP("heap", "HEAP"),
    HIGH("high", "HIGH"),
    HIGH_PRIORITY("high_priority", "HIGH_PRIORITY"),

    HOLD("hold", "HOLD"),
    HOUR("hour", "HOUR"),
    HOUR_MICROSECOND("hour_microsecond", "HOUR_MICROSECOND"),
    HOUR_MINUTE("hour_minute", "HOUR_MINUTE"),
    HOUR_SECOND("hour_second", "HOUR_SECOND"),

    // I
    ID("id", "ID"),
    IDENTIFIED("identified", "IDENTIFIED"),
    IDENTIFIER("identifier", "IDENTIFIER"),
    IDENTITY("identity", "IDENTITY"),
    IF("if", "IF"),
    IFNULL("ifnull", "IFNULL"),
    IGNORE("ignore", "IGNORE"),
    ILM("ilm", "ILM"),
    IMMEDIATE("immediate", "IMMEDIATE"),
    IMPORT("import", "IMPORT"),
    IN("in", "IN"),
    INCLUDING("including", "INCLUDING"),
    INCREMENT("increment", "INCREMENT"),
    INDEX("index", "INDEX"),
    INDEXING("indexing", "INDEXING"),
    INDEXTYPE("indextype", "INDEXTYPE"),
    INDICATOR("indicator", "INDICATOR"),
    INDICES("indices", "INDICES"),
    INITIAL("initial", "INITIAL"),
    INITIALLY("initially", "INITIALLY"),
    INITRANS("initrans", "INITRANS"),
    INLINE("inline", "INLINE"),
    INMEMORY("inmemory", "INMEMORY"),
    INNER("inner", "INNER"),
    INOUT("inout", "INOUT"),
    INPUT(""),
    INSENSITIVE(""),
    INSERT("insert", "INSERT"),
    INSTANCE("instance", "INSTANCE"),
    INSTANTIABLE("instantiable", "INSTANTIABLE"),
    INSTEAD("instead", "INSTEAD"),
    INSTR("instr", "INSTR"),
    INT("int", "INT"),
    INTEGER("integer", "INTEGER"),
    INTERSECT(""),
    INTERVAL("interval", "INTERVAL"),
    INTO("into", "INTO"),
    INVALIDATE("invalidate", "INVALIDATE"),
    INVALIDATION("invalidation", "INVALIDATION"),
    INVISIBLE("invisible", "INVISIBLE"),
    INVOKER("invoker", "INVOKER"),
    IS("is", "IS"),
    ISOLATION("isolation", "ISOLATION"),
    ITERATION_NUMBER("iteration_number", "ITERATION_NUMBER"),

    // J
    JAVA("java", "JAVA"),
    JOIN("join", "JOIN"),
    JSON("json", "JSON"),

    // K
    K("k", "K"),
    KEEP("keep", "KEEP"),
    KEEP_DUPLICATES("keep_duplicates", "KEEP_DUPLICATES"),
    KEY("key", "KEY"),
    KEYS("keys", "KEYS"),

    // L
    LANGUAGE("language", "LANGUAGE"),
    LARGE("large", "LARGE"),
    LAST("last", "LAST"),
    LATERAL("lateral", "LATERAL"),
    LAX("lax", "LAX"),
    LEADING("leading", "LEADING"),
    LEAF("leaf", "LEAF"),
    LEFT("left", "LEFT"),
    LENGTH("length", "LENGTH"),
    LENGTHB("lengthb", "LENGTHB"),
    LESS("less", "LESS"),
    LEVEL("level", "LEVEL"),
    LEVELS("levels", "LEVELS"),
    LIBRARY("library", "LIBRARY"),
    LIKE("like", "LIKE"),
    LIKEC("likec", "LIKEC"),
    LIKE2("like2", "LIKE2"),
    LIKE4("like4", "LIKE4"),
    LIMIT("limit", "LIMIT"),
    LINEAR("linear", "LINEAR"),
    LINK("link", "LINK"),
    LIST("list", "LIST"),
    LOAD("load", "LOAD"),
    LOB("lob", "LOB"),
    LOBS("lobs", "LOBS"),
    LOCAL("local", "LOCAL"),
    LOCALTIME("localtime", "LOCALTIME"),
    LOCALTIMESTAMP("localtimestamp", "LOCALTIMESTAMP"),
    LOCATION("location", "LOCATION"),
    LOCATOR("locator", "LOCATOR"),
    LOCK("lock", "LOCK"),
    LOCKED("locked", "LOCKED"),
    LOCKING("locking", "LOCKING"),
    LOG("log", "LOG"),
    LOGFILE("logfile", "LOGFILE"),
    LOGGING("logging", "LOGGING"),
    LOGOFF("logoff", "LOGOFF"),
    LOGON("logon", "LOGON"),
    LONG("long", "LONG"),
    LONGBLOB("longblob", "LONGBLOB"),
    LONGTEXT("longtext", "LONGTEXT"),
    LOOP("loop", "LOOP"),
    LOW("low", "LOW"),
    LOW_PRIORITY("low_priority", "LOW_PRIORITY"),
    LPAD("lpad", "LPAD"),
    LTRIM("ltrim", "LTRIM"),

    // M
    M("m", "M"),
    MANAGEMENT("management", "MANAGEMENT"),
    MAP("map", "MAP"),
    MAPPING("mapping", "MAPPING"),
    MATCH("match", "MATCH"),
    MATERIALIZED("materialized", "MATERIALIZED"),
    MAX("max", "MAX"),
    MAXEXTENTS("maxextents", "MAXEXTENTS"),
    MAXDATAFILES("maxdatafiles", "MAXDATAFILES"),
    MAXINSTANCES("maxinstances", "MAXINSTANCES"),
    MAXLEN("maxlen", "MAXLEN"),
    MAXLOGFILES("maxlogfiles", "MAXLOGFILES"),
    MAXLOGHISTORY("maxloghistory", "MAXLOGHISTORY"),
    MAXLOGMEMBERS("maxlogmembers", "MAXLOGMEMBERS"),
    MAXSIZE("maxsize", "MAXSIZE"),
    MAXVALUE("maxvalue", "MAXVALUE"),
    MEDIUMBLOB("mediumblob", "MEDIUMBLOB"),
    MEDIUMINT("mediumint", "MEDIUMINT"),
    MEDIUMTEXT("mediumtext", "MEDIUMTEXT"),
    MEMBER("member", "MEMBER"),
    MEMCOMPRESS("memcompress", "MEMCOMPRESS"),
    MEMOPTIMIZE("memoptimize", "MEMOPTIMIZE"),
    MERGE("merge", "MERGE"),
    METADATA("metadata", "METADATA"),
    METHOD("method", "METHOD"),
    MICROSECOND("microsecond", "MICROSECOND"),
    MIN("min", "MIN"),
    MINEXTENTS("minextents", "MINEXTENTS"),
    MINING("mining", "MINING"),
    MINUS("minus", "MINUS"),
    MINUTE("minute", "MINUTE"),
    MINUTE_MICROSECOND("minute_microsecond", "MINUTE_MICROSECOND"),
    MINUTE_SECOND("minute_second", "MINUTE_SECOND"),
    MINVALUE("minvalue", "MINVALUE"),
    MOD("mod", "MOD"),
    MODE("mode", "MODE"),
    MODIFIES("modifies", "MODIFIES"),
    MODIFY("modify", "MODIFY"),
    MODULE("module", "MODULE"),
    MONTH("month", "MONTH"),
    MOVE("move", "MOVE"),
    MOVEMENT("movement", "MOVEMENT"),
    MULTISET("multiset", "MULTISET"),

    // N
    N("n", "N"),
    NAME("name", "NAME"),
    NATIONAL("national", "NATIONAL"),
    NATURAL("natural", "NATURAL"),
    NCHAR("nchar", "NCHAR"),
    NCLOB("nclob", "NCLOB"),
    NESTED("nested", "NESTED"),
    NEW("new", "NEW"),
    NEXT("next", "NEXT"),
    NEXTVAL("nextval", "NEXTVAL"),
    NO("no", "NO"),
    NOARCHIVELOG("noarchivelog", "NOARCHIVELOG"),
    NOAUDIT("noaudit", "NOAUDIT"),
    NOCACHE("nocache", "NOCACHE"),
    NOCOMPRESS("nocompress", "NOCOMPRESS"),
    NOCOPY("nocopy", "NOCOPY"),
    NOCYCLE("nocycle", "NOCYCLE"),
    NOEXTEND("noextend", "NOEXTEND"),
    NOKEEP("nokeep", "NOKEEP"),
    NOLOGGING("nologging", "NOLOGGING"),
    NOMAPPING("nomapping", "NOMAPPING"),
    NOMAXVALUE("nomaxvalue", "NOMAXVALUE"),
    NOMINVALUE("nominvalue", "NOMINVALUE"),
    NONE("none", "NONE"),
    NONEDITIONABLE("noneditionable", "NONEDITIONABLE"),
    NONSCHEMA("nonschema", "NONSCHEMA"),
    NOORDER("noorder", "NOORDER"),
    NOPARALLEL("noparallel", "NOPARALLEL"),
    NORELY("norely", "NORELY"),
    NOSCALE("noscale", "NOSCALE"),
    NOT("not", "NOT"),
    NOVALIDATE("novalidate", "NOVALIDATE"),
    NOWAIT("nowait", "NOWAIT"),
    NULL("null", "NULL"),
    NULLIF("nullif", "NULLIF"),
    NUMBER("number", "NUMBER"),
    NUMERIC("numeric", "NUMERIC"),
    NVARCHAR2("nvarchar2", "NVARCHAR2"),
    NVL("nvl", "NVL"),
    NVL2("nvl2", "NVL2"),


    // O
    OBJECT("object", "OBJECT"),
    OF("of", "OF"),
    OFF("off", "OFF"),
    OFFSET("offset", "OFFSET"),
    OJ("oj", "OJ"),
    OID("oid", "OID"),
    OIDINDEX("oidindex", "OIDINDEX"),
    OLD("old", "OLD"),
    ON("on", "ON"),
    ONLINE("online", "ONLINE"),
    ONLY("only", "ONLY"),
    OPAQUE("opaque", "OPAQUE"),
    OPEN("open", "OPEN"),
    OPERATOR("operator", "OPERATOR"),
    OPTIMAL("optimal", "OPTIMAL"),
    OPTIMIZE("optimize", "OPTIMIZE"),
    OPTION("option", "OPTION"),
    OR("or", "OR"),
    ORA_DM_PARTITION_NAME("ora_dm_partition_name", "ORA_DM_PARTITION_NAME"),
    ORA_INVOKING_USER("ora_invoking_user", "ORA_INVOKING_USER"),
    ORA_INVOKING_USERID("ora_invoking_userid", "ORA_INVOKING_USERID"),
    ORDER("order", "ORDER"),
    ORGANIZATION("organization", "ORGANIZATION"),
    OUT("out", "OUT"),
    OUTER("outer", "OUTER"),
    OUTPUT("output", "OUTPUT"),
    OVER("over", "OVER"),
    OVERFLOW("overflow", "OVERFLOW"),
    OVERLAPS("overlaps", "OVERLAPS"),
    OVERRIDING("overriding", "OVERRIDING"),

    // P
    P("p", "P"),
    PACKAGE("package", "PACKAGE"),
    PARAMETER("parameter", "PARAMETER"),
    PARAMETERS("parameters", "PARAMETERS"),
    PARALLEL("parallel", "PARALLEL"),
    PARALLEL_ENABLE("parallel_enable", "PARALLEL_ENABLE"),
    PARENT("parent", "PARENT"),
    PARTIAL("partial", "PARTIAL"),
    PARTITION("partition", "PARTITION"),
    PARTITIONING("partitioning", "PARTITIONING"),
    PARTITIONS("partitions", "PARTITIONS"),
    PCTINCREASE("pctincrease", "PCTINCREASE"),
    PCTFREE("pctfree", "PCTFREE"),
    PCTTHRESHOLD("pctthreshold", "PCTTHRESHOLD"),
    PCTUSED("pctused", "PCTUSED"),
    PCTVERSION("pctversion", "PCTVERSION"),
    PERFORMANCE("performance", "PERFORMANCE"),
    PERIOD("period", "PERIOD"),
    PIPE("pipe", "PIPE"),
    PIPELINED("pipelined", "PIPELINED"),
    PLS_INTEGER("pls_integer", "PLS_INTEGER"),
    POLICY("policy", "POLICY"),
    PRAGMA("pragma", "PRAGMA"),
    PRECEDING("preceding", "PRECEDING"),
    PRECEDES("precedes", "PRECEDES"),
    PRECISION("precision", "PRECISION"),
    PREDICTION("prediction", "PREDICTION"),
    PREDICTION_BOUNDS("prediction_bounds", "PREDICTION_BOUNDS"),
    PREDICTION_COST("prediction_cost", "PREDICTION_COST"),
    PREDICTION_DETAILS("prediction_details", "PREDICTION_DETAILS"),
    PREDICTION_PROBABILITY("prediction_probability", "PREDICTION_PROBABILITY"),
    PREDICTION_SET("prediction_set", "PREDICTION_SET"),
    PREPARE("prepare", "PREPARE"),
    PRESERVE("preserve", "PRESERVE"),
    PRIMARY("primary", "PRIMARY"),
    PRIVATE("private", "PRIVATE"),

    PRIOR("prior", "PRIOR"),
    PROCEDURE("procedure", "PROCEDURE"),
    PUBLIC("public", "PUBLIC"),
    PURGE("purge", "PURGE"),
    PLUGGABLE("pluggable", "PLUGGABLE"),

    // Q
    Q("q", "Q"),
    QUARTER("quarter", "QUARTER"),
    QUERY("query", "QUERY"),
    QUICK("quick", "QUICK"),

    // R
    RAISE("raise", "RAISE"),
    RANGE("range", "RANGE"),
    RAW("raw", "RAW"),
    READ("read", "READ"),
    READS("reads", "READS"),
    REAL("real", "REAL"),
    RECORD("record", "RECORD"),
    REBUILD("rebuild", "REBUILD"),
    RECURSIVE("recursive", "RECURSIVE"),
    RECYCLE("recycle", "RECYCLE"),
    REF("ref", "REF"),
    REFERENCE("reference", "REFERENCE"),
    REFERENCES("references", "REFERENCES"),
    REFERENCING("referencing", "REFERENCING"),
    REGEXP("regexp", "REGEXP"),
    REJECT("reject", "REJECT"),
    RELATIONAL("relational", "RELATIONAL"),
    RELY("rely", "RELY"),
    REMAINDER("remainder", "REMAINDER"),
    REMOVE("remove", "REMOVE"),
    REPAIR("repair", "REPAIR"),
    REPLACE("replace", "REPLACE"),
    REGR_AVGX(""),
    REGR_AVGY(""),
    REGR_COUNT(""),
    REGR_INTERCEPT(""),
    REGR_R2(""),
    REGR_SLOPE(""),
    REGR_SXX(""),
    REGR_SXY(""),
    REGR_SYY(""),
    RELEASE("release", "RELEASE"),
    RELIES_ON("relies_on", "RELIES_ON"),
    RENAME("rename", "RENAME"),
    REORGANIZE("reorganize", "REORGANIZE"),
    REPEAT("repeat", "REPEAT"),
    RESET("reset", "RESET"),
    RESTRICT("restrict", "RESTRICT"),
    RESTRICT_REFERENCES("restrict_references", "RESTRICT_REFERENCES"),
    RESULT("result", "RESULT"),
    RESULT_CACHE("result_cache", "RESULT_CACHE"),
    RETENTION("retention", "RETENTION"),
    RETURN("return", "RETURN"),
    RETURNING("returning", "RETURNING"),
    RETURNS("returns", "RETURNS"),
    REUSE("reuse", "REUSE"),
    REVERSE("reverse", "REVERSE"),
    REVOKE("revoke", "REVOKE"),
    RIGHT("right", "RIGHT"),
    RLIKE("rlike", "RLIKE"),
    ROLE("role", "ROLE"),
    ROLLBACK("rollback", "ROLLBACK"),
    ROLLUP("rollup", "ROLLUP"),
    ROUND("round", "ROUND"),
    ROW("row", "ROW"),
    ROWID("rowid", "ROWID"),
    ROWNUM("rownum", "ROWNUM"),
    ROWS("rows", "ROWS"),
    ROWTYPE("rowtype", "ROWTYPE"),
    RPAD("rpad", "RPAD"),
    RTRIM("rtrim", "RTRIM"),

    // S
    SAVE("save", "SAVE"),
    SAVEPOINT("savepoint", "SAVEPOINT"),
    SCALE("scale", "SCALE"),
    SCHEMA("schema", "SCHEMA"),
    SCOPE("scope", "SCOPE"),
    SCROLL("scroll", "SCROLL"),
    SEARCH("search", "SEARCH"),
    SECOND("second", "SECOND"),
    SECOND_MICROSECOND("second_microsecond", "SECOND_MICROSECOND"),
    SECUREFILE("securefile", "SECUREFILE"),
    SECURITY("security", "SECURITY"),
    SEED("seed", "SEED"),
    SEGMENT("segment", "SEGMENT"),
    SELECT("select", "SELECT"),
    SELF("self", "SELF"),
    SENSITIVE("sensitive", "SENSITIVE"),
    SEQUENCE("sequence", "SEQUENCE"),
    SERIALLY_REUSABLE("serially_reusable", "SERIALLY_REUSABLE"),
    SERVERERROR("servererror", "SERVERERROR"),
    SESSION("session", "SESSION"),
    SESSION_USER("session_user", "SESSION_USER"),
    SESSIONTIMEZONE("sessiontimezone", "SESSIONTIMEZONE"),
    SET("set", "SET"),
    SETTINGS("settings", "SETTINGS"),
    SHARE("share", "SHARE"),
    SHARED("shared", "SHARED"),
    SHARING("sharing", "SHARING"),
    SHUTDOWN("shutdown", "SHUTDOWN"),
    SIMILAR("similar", "SIMILAR"),
    SIMPLE("simple", "SIMPLE"),
    SINH("sinh", "SINH"),
    SIZE("size", "SIZE"),
    SKIP("skip", "SKIP"),
    SMALLFILE("smallfile", "SMALLFILE"),
    SMALLINT("smallint", "SMALLINT"),
    SOME("some", "SOME"),
    SORT("sort", "SORT"),
    SOUNDS("sounds", "SOUNDS"),
    SPATIAL("spatial", "SPATIAL"),
    SPECIFIC(""),
    SPECIFICATION("specification", "SPECIFICATION"),
    SPECIFICTYPE(""),
    SPLIT("split", "SPLIT"),
    SQL("sql", "SQL"),
    SQLCODE("sqlcode", "SQLCODE"),
    SQLERRM("sqlerrm", "SQLERRM"),
    SQL_BIG_RESULT("sql_big_result", "SQL_BIG_RESULT"),
    SQL_CACHE("sql_cache", "SQL_CACHE"),
    SQL_CALC_FOUND_ROWS("sql_calc_found_rows", "SQL_CALC_FOUND_ROWS"),
    SQL_BUFFER_RESULT("sql_buffer_result", "SQL_BUFFER_RESULT"),
    SQL_NO_CACHE("sql_no_cache", "SQL_NO_CACHE"),
    SQL_SMALL_RESULT("sql_small_result", "SQL_SMALL_RESULT"),
    SQLEXCEPTION(""),
    SQLSTATE("sqlstate", "SQLSTATE"),
    SQLWARNING(""),
    STANDBY("standby", "STANDBY"),
    START("start", "START"),
    STARTUP("startup", "STARTUP"),
    STATEMENT("statement", "STATEMENT"),
    STATIC("static", "STATIC"),
    STATISTICS("statistics", "STATISTICS"),
    STORE("store", "STORE"),
    STORAGE("storage", "STORAGE"),
    STRAIGHT_JOIN("straight_join", "STRAIGHT_JOIN"),
    STRICT("strict", "STRICT"),
    STRING("string", "STRING"),
    STRUCT("struct", "STRUCT"),
    SYSAUX("sysaux", "SYSAUX"),
    SUBMULTISET("submultiset", "SUBMULTISET"),
    SUBPARTITION("subpartition", "SUBPARTITION"),
    SUBPARTITIONS("subpartitions", "SUBPARTITIONS"),
    SUBSTITUTABLE("substitutable", "SUBSTITUTABLE"),
    SUBTYPE("subtype", "SUBTYPE"),
    SUPPLEMENT("supplement", "SUPPLEMENT"),
    SUSPEND("suspend", "SUSPEND"),
    SYMMETRIC("symmetric", "SYMMETRIC"),
    SYNONYM("synonym", "SYNONYM"),
    SYS("sys", "SYS"),
    SYS_GUID("sys_guid", "SYS_GUID"),
    SYSDATE("sysdate", "SYSDATE"),
    SYSTEM("system", "SYSTEM"),
    SYSTIMESTAMP("systimestamp", "SYSTIMESTAMP"),
    SYSTEM_USER("system_user", "SYSTEM_USER"),

    // T
    T("t", "T"),
    TABLE("table", "TABLE"),
    TABLES("tables", "TABLES"),
    TABLESPACE("tablespace", "TABLESPACE"),
    TANH("tanh", "TANH"),
    TDO("tdo", "TDO"),
    TEMPFILE("tempfile", "TEMPFILE"),
    TEMPORARY("temporary", "TEMPORARY"),
    TEMPLATE("template", "TEMPLATE"),
    TEXT("text", "TEXT"),
    THAN("than", "THAN"),
    THEN("then", "THEN"),
    TIME("time", "TIME"),
    TIME_ZONE("time_zone", "TIME_ZONE"),
    TIMESTAMP("timestamp", "TIMESTAMP"),
    TIMESTAMPADD("timestampadd", "TIMESTAMPADD"),
    TIMEZONE_ABBR("timezone_abbr", "TIMEZONE_ABBR"),
    TIMEZONE_HOUR("timezone_hour", "TIMEZONE_HOUR"),
    TIMEZONE_MINUTE("timezone_minute", "TIMEZONE_MINUTE"),
    TIMEZONE_REGION("timezone_region", "TIMEZONE_REGION"),
    TINYBLOB("tinyblob", "TINYBLOB"),
    TINYINT("tinyint", "TINYINT"),
    TINYTEXT("tinytext", "TINYTEXT"),
    TO("to", "TO"),
    TO_CHAR("to_char", "TO_CHAR"),
    TRAILING(""),
    TRANSLATE("translate", "TRANSLATE"),
    TRANSLATION(""),
    TREAT("treat", "TREAT"),
    TRIGGER("trigger", "TRIGGER"),
    TRIGGERS("triggers", "TRIGGERS"),
    TRIM("trim", "TRIM"),
    TRUE("true", "TRUE"),
    TRUNCATE("truncate", "TRUNCATE"),
    TYPE("type", "TYPE"),

    // U
    UDF("udf", "UDF"),
    UESCAPE("uescape", "UESCAPE"),
    UNDER("under", "UNDER"),
    UNDO("undo", "UNDO"),
    UNIFORM("uniform", "UNIFORM"),
    UID("uid", "UID"),
    UNION("union", "UNION"),
    UNIQUE("unique", "UNIQUE"),
    UNKNOWN("unknown", "UNKNOWN"),
    UNLIMITED("unlimited", "UNLIMITED"),
    UNNEST("unnest", "UNNEST"),
    UNPACKAGE("unpackage", "UNPACKAGE"),
    UNPLUG("unplug", "UNPLUG"),
    UNUSABLE("unusable", "UNUSABLE"),
    UNUSED("unused", "UNUSED"),
    UNSIGNED("unsigned", "UNSIGNED"),
    UPDATE("update", "UPDATE"),
    UPGRADE("upgrade", "UPGRADE"),
    UPPER("upper", "UPPER"),
    URITYPE("uritype", "URITYPE"),
    UROWID("urowid", "UROWID"),
    USER("user", "USER"),
    USER_DATA("user_data", "USER_DATA"),
    USING("using", "USING"),
    UTC_DATE("utc_date", "UTC_DATE"),
    UTC_TIME("utc_time", "UTC_TIME"),
    UTC_TIMESTAMP("utc_timestamp", "UTC_TIMESTAMP"),
    UUID("uuid", "UUID"),

    // V
    VALIDATE("validate", "VALIDATE"),
    VALIDATE_CONVERSION("validate_conversion", "VALIDATE_CONVERSION"),
    VALUE("value", "VALUE"),
    VALUES("values", "VALUES"),
    VAR_POP(""),
    VAR_SAMP(""),
    VARBINARY("varbinary", "VARBINARY"),
    VARCHAR("varchar", "VARCHAR"),
    VARCHAR2("varchar2", "VARCHAR2"),
    VARRAY("varray", "VARRAY"),
    VARRAYS("varrays", "VARRAYS"),
    VARYING("varying", "VARYING"),
    VIEW("view", "VIEW"),
    VIRTUAL("virtual", "VIRTUAL"),
    VISIBLE("visible", "VISIBLE"),

    // W
    WAIT("wait", "WAIT"),
    WEEK("week", "WEEK"),
    WHEN("when", "WHEN"),
    WHENEVER("whenever", "WHENEVER"),
    WHERE("where", "WHERE"),
    WHILE("while", "WHILE"),
    WIDTH_BUCKET("width_bucket", "WIDTH_BUCKET"),
    WINDOW("window", "WINDOW"),
    WITH("with", "WITH"),
    WITHIN("within", "WITHIN"),
    WITHOUT("without", "WITHOUT"),
    WRITE("write", "WRITE"),

    // X
    X("x", "X"),
    XML("xml", "XML"),
    XMLCAST("xmlcast", "XMLCAST"),
    XMLCOLATTVAL("xmlcolattval", "XMLCOLATTVAL"),
    XMLELEMENT("xmlelement", "XMLELEMENT"),
    XMLEXISTS("xmlexists", "XMLEXISTS"),
    XMLFOREST("xmlforest", "XMLFOREST"),
    XMLPARSE("xmlparse", "XMLPARSE"),
    XMLPI("xmlpi", "XMLPI"),
    XMLSCHEMA("xmlschema", "XMLSCHEMA"),
    XMLTYPE("xmltype", "XMLTYPE"),
    XOR("xor", "XOR"),

    // Y
    YEAR("year", "YEAR"),
    YEAR_MONTH("year_month", "YEAR_MONTH"),

    // Z
    ZEROFILL("zerofill", "ZEROFILL"),
    ZONE("zone", "ZONE"),
    ZONEMAP("zonemap", "ZONEMAP"),

    ARROW("->"),
    ASSIGN(":="),
    COLCOL("::"),
    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),
    LBRACKET("["),
    RBRACKET("]"),
    SEMI(";"),
    COMMA(","),
    DOT("."),
    DOTDOT(".."),
    ELLIPSIS("..."),
    EQ("="),
    GT(">"),
    LT("<"),
    BANG("!"),
    TILDE("~"),
    QUES("?"),
    COLON(":"),
    EQEQ("=="),
    LTEQ("<="),
    GTEQ(">="),
    BANGEQ("!="),
    LTGT("<>"),
    EQGT("=>"),
    LTEQGT("<=>"),
    AMPAMP("&&"),
    BARBAR("||"),
    PLUSPLUS("++"),
    SUBSUB("--"),
    PLUS("+"),
    SUB("-"),
    STAR("*"),
    SLASH("/"),
    AMP("&"),
    BAR("|"),
    CARET("^"),
    PERCENT("%"),
    LTLT("<<"),
    GTGT(">>"),
    GTGTGT(">>>"),
    PLUSEQ("+="),
    SUBEQ("-="),
    STAREQ("*="),
    SLASHEQ("/="),
    AMPEQ("&="),
    BAREQ("|="),
    CARETEQ("^="),
    PERCENTEQ("%="),

    LTLTEQ("<<="),
    GTGTEQ(">>="),
    GTGTGTEQ(">>>="),
    MONKEYS_AT("@"),
    SHARP("#"),

    ;
    public final String lower;
    public final String upper;
    public final long lowerHash;

    SQLKeyWord() {
        this(null, null);
    }

    SQLKeyWord(String name) {
        this(name, name);
    }

    SQLKeyWord(String lower, String upper) {
        this.lower = lower;
        this.upper = upper;
        this.lowerHash = HashUtils.fnv_1a_64_lower(lower);
    }

    @Override
    public String toString() {
        return upper;
    }
}
