package com.gumihoy.sql.bvt.dialect.oracle.ddl.package_.create;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author kent on 2019-07-09.
 */
public class OracleCreatePackageTest_10 {

    @Test
    public void test_0() throws Exception {
//        InputStream inputStream = this.getClass().getResourceAsStream("/package/create/create_package_0.sql");
        String sql = "CREATE OR REPLACE PACKAGE WOS_COMMON_PKG IS\n" +
                "\n" +
                "  -- Author  : MAMI\n" +
                "  -- Created : 2006-1-23 12:09:32\n" +
                "  -- Amended by jinlo 2007/10/17\n" +
                "  -- Add 3 lock exceptions\n" +
                "  -- Add sp_lock, sp_unlock, send_mail procedures\n" +
                "  -- Purpose : public common functions and constants\n" +
                "  -- Amended by jinlo 2008/10/8\n" +
                "  -- Remove sp_lock, using LTS\n" +
                "\n" +
                "  -- Public type declarations\n" +
                "\n" +
                "  TYPE RULE_VALUE_T_REC IS RECORD(\n" +
                "    RULE_CODE    CM_NEW_RULE.RULE_CODE%TYPE,\n" +
                "    PARENT_CODE  CM_NEW_RULE.PARENT_CODE%TYPE,\n" +
                "    VALUE        CM_NEW_RULE_VALUE.VALUE%TYPE,\n" +
                "    WAREHOUSE_ID CM_NEW_RULE_VALUE.WAREHOUSE_ID%TYPE,\n" +
                "    OWNER_ID     CM_NEW_RULE_VALUE.OWNER_ID%TYPE);\n" +
                "\n" +
                "  TYPE RULE_VALUE_T_RECTAB IS TABLE OF RULE_VALUE_T_REC;\n" +
                "\n" +
                "  TYPE RULE_VALUE_REFCUR_T IS REF CURSOR;\n" +
                "\n" +
                "  -- Public constant declarations\n" +
                "  MODULE_COMMON             CONSTANT VARCHAR2(50) := 'COMMON';\n" +
                "  CONFIGURATION_CURRENT_ENV CONSTANT VARCHAR2(50) := 'CURRENT_ENV';\n" +
                "  CURRENT_ENV_DEV           CONSTANT VARCHAR2(50) := 'DEV';\n" +
                "  TZ_ASIA_HONG_KONG         CONSTANT VARCHAR2(50) := 'Asia/Hong_Kong';\n" +
                "  TZ_OFFSET_ASIA_HONG_KONG  CONSTANT VARCHAR2(50) := 'Asia/Hong_Kong';\n" +
                "\n" +
                "  -- rule value level\n" +
                "  RULE_SYSTEM_LEVEL          CONSTANT VARCHAR2(50) := 'RULE_SYSTEM_LEVEL';\n" +
                "  RULE_WAREHOUSE_LEVEL       CONSTANT VARCHAR2(50) := 'RULE_WAREHOUSE_LEVEL';\n" +
                "  RULE_WAREHOUSE_OWNER_LEVEL CONSTANT VARCHAR2(50) := 'RULE_WAREHOUSE_OWNER_LEVEL';\n" +
                "\n" +
                "  -- operation history operation type\n" +
                "  OH_OT_SHIPPING_ALLOCATION CONSTANT RP_OPERATION_HISTORY.OPERATION_TYPE%TYPE := 'SHIPPING_ALLOCATION';\n" +
                "\n" +
                "  -- add a prefix to a not null string\n" +
                "  FUNCTION ADD_PREFIX(I_STR         IN VARCHAR2,\n" +
                "                      I_PREFIX      IN VARCHAR2,\n" +
                "                      I_IGNORE_NULL IN BOOLEAN DEFAULT TRUE) RETURN VARCHAR2;\n" +
                "\n" +
                "  -- Filter the special characters in a string for the normalized sql.\n" +
                "  -- i_string: a string for sql concatenation\n" +
                "  FUNCTION STRING_FILTER(I_STRING IN VARCHAR2) RETURN VARCHAR2;\n" +
                "\n" +
                "  -- concat an array using a delimiter\n" +
                "  FUNCTION CONCAT_ARRAY(I_STRARRAY    IN STRARRAY,\n" +
                "                        I_DELIMITER   IN VARCHAR2,\n" +
                "                        I_NEED_QUOTE  IN BOOLEAN DEFAULT FALSE,\n" +
                "                        I_IGNORE_NULL IN BOOLEAN DEFAULT TRUE)\n" +
                "    RETURN VARCHAR2;\n" +
                "\n" +
                "  -- concat an array using a delimiter\n" +
                "  FUNCTION CONCAT_ARRAY(I_NUMARRAY    IN NUMARRAY,\n" +
                "                        I_DELIMITER   IN VARCHAR2,\n" +
                "                        I_NEED_QUOTE  IN BOOLEAN DEFAULT FALSE,\n" +
                "                        I_IGNORE_NULL IN BOOLEAN DEFAULT TRUE)\n" +
                "    RETURN VARCHAR2;\n" +
                "\n" +
                "  -- Add an offset to a field of a date.\n" +
                "  -- i_field: SS, DD, MM, YY\n" +
                "  FUNCTION ADD(I_DATE   IN DATE,\n" +
                "               I_FIELD  IN VARCHAR2 DEFAULT 'DD',\n" +
                "               I_OFFSET IN NUMBER DEFAULT 0) RETURN DATE;\n" +
                "\n" +
                "  -- Get the zone a warehouse resides.\n" +
                "  FUNCTION GET_WAREHOUSE_TZ(I_WAREHOUSE IN VARCHAR2) RETURN VARCHAR2;\n" +
                "\n" +
                "  -- Convert a datetime at a zone to a datetime at the zone a warehouse resides.\n" +
                "  -- i_zone default HongKong Time Zone 'Asia/Shanghai'.\n" +
                "  FUNCTION TO_WAREHOUSE_TZ(I_DATE      IN DATE,\n" +
                "                           I_WAREHOUSE IN VARCHAR2,\n" +
                "                           I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')\n" +
                "    RETURN DATE;\n" +
                "\n" +
                "  -- Convert a datetime at the zone a warehouse resides to a datetime at a zone\n" +
                "  -- i_zone default HongKong Time Zone '+08:00'.\n" +
                "  FUNCTION FROM_WAREHOUSE_TZ(I_DATE      IN DATE,\n" +
                "                             I_WAREHOUSE IN VARCHAR2,\n" +
                "                             I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')\n" +
                "    RETURN DATE;\n" +
                "\n" +
                "  -- Convert a datetime at a zone to a datetime at the zone a warehouse resides, then apply trunc(),\n" +
                "  -- and reconvert the result datetime at the zone a warehouse resides to a datetime at the original zone.\n" +
                "  -- i_fmt default 'DD'\n" +
                "  -- i_zone default HongKong Time Zone '+08:00'.\n" +
                "  FUNCTION TRUNC_WAREHOUSE_TZ(I_DATE      IN DATE,\n" +
                "                              I_WAREHOUSE IN VARCHAR2,\n" +
                "                              I_FMT       IN VARCHAR2 DEFAULT 'DD',\n" +
                "                              I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')\n" +
                "    RETURN DATE;\n" +
                "\n" +
                "  -- Convert a datetime at a zone to a datetime at the zone a warehouse resides, then apply round(),\n" +
                "  -- and reconvert the result datetime at the zone a warehouse resides to a datetime at the original zone.\n" +
                "  -- i_fmt default 'DD'\n" +
                "  -- i_zone default HongKong Time Zone '+08:00'.\n" +
                "  FUNCTION ROUND_WAREHOUSE_TZ(I_DATE      IN DATE,\n" +
                "                              I_WAREHOUSE IN VARCHAR2,\n" +
                "                              I_FMT       IN VARCHAR2 DEFAULT 'DD',\n" +
                "                              I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')\n" +
                "    RETURN DATE;\n" +
                "\n" +
                "  -- Convert a datetime at a zone to a datetime at the zone a warehouse resides, then apply trunc(),\n" +
                "  -- and reconvert the result datetime at the zone a warehouse resides to a datetime at the original zone.\n" +
                "  -- i_fmt default 'DD'\n" +
                "  -- i_zone default HongKong Time Zone '+08:00'.\n" +
                "  FUNCTION TRUNC_WAREHOUSE_TZ_OFFSET(I_DATE      IN DATE,\n" +
                "                                     I_WAREHOUSE IN VARCHAR2,\n" +
                "                                     I_FIELD_B   IN VARCHAR2 DEFAULT 'DD',\n" +
                "                                     I_OFFSET_B  IN NUMBER DEFAULT 0,\n" +
                "                                     I_FIELD_A   IN VARCHAR2 DEFAULT 'DD',\n" +
                "                                     I_OFFSET_A  IN NUMBER DEFAULT 0,\n" +
                "                                     I_FMT       IN VARCHAR2 DEFAULT 'DD',\n" +
                "                                     I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')\n" +
                "    RETURN DATE;\n" +
                "\n" +
                "  -- Get the first day of the week with the index.\n" +
                "  FUNCTION GET_FIRST_DAY_OF_WEEK(I_DATE  IN DATE,\n" +
                "                                 I_INDEX IN NUMBER DEFAULT 0) RETURN DATE;\n" +
                "\n" +
                "  -- Get the first day of the month with the index.\n" +
                "  FUNCTION GET_FIRST_DAY_OF_MONTH(I_DATE  IN DATE,\n" +
                "                                  I_INDEX IN NUMBER DEFAULT 0) RETURN DATE;\n" +
                "\n" +
                "  -- Get the first day of the year with the index.\n" +
                "  FUNCTION GET_FIRST_DAY_OF_YEAR(I_DATE  IN DATE,\n" +
                "                                 I_INDEX IN NUMBER DEFAULT 0) RETURN DATE;\n" +
                "\n" +
                "  -- Get the first day of the week with the index at the zone a warehouse resides.\n" +
                "  -- return the time at i_zone.\n" +
                "  FUNCTION GET_FIRST_DAY_OF_WEEK(I_DATE      IN DATE,\n" +
                "                                 I_WAREHOUSE IN VARCHAR2,\n" +
                "                                 I_INDEX     IN NUMBER DEFAULT 0,\n" +
                "                                 I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')\n" +
                "    RETURN DATE;\n" +
                "\n" +
                "  -- Get the first day of the month with the index at the zone a warehouse resides.\n" +
                "  -- return the time at i_zone.\n" +
                "  FUNCTION GET_FIRST_DAY_OF_MONTH(I_DATE      IN DATE,\n" +
                "                                  I_WAREHOUSE IN VARCHAR2,\n" +
                "                                  I_INDEX     IN NUMBER DEFAULT 0,\n" +
                "                                  I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')\n" +
                "    RETURN DATE;\n" +
                "\n" +
                "  -- Get the first day of the year with the index at the zone a warehouse resides.\n" +
                "  -- return the time at i_zone.\n" +
                "  FUNCTION GET_FIRST_DAY_OF_YEAR(I_DATE      IN DATE,\n" +
                "                                 I_WAREHOUSE IN VARCHAR2,\n" +
                "                                 I_INDEX     IN NUMBER DEFAULT 0,\n" +
                "                                 I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')\n" +
                "    RETURN DATE;\n" +
                "\n" +
                "  -- Put a piece of information in the buffer as multiple lines.\n" +
                "  -- i_line_length should range from 0 to 255 and default to 255.\n" +
                "  PROCEDURE PUT_LINES(I_STR VARCHAR2, I_LINE_LENGTH NUMBER DEFAULT 255);\n" +
                "  FUNCTION ISEMPTY(I_NUMARRAY IN NUMARRAY) RETURN BOOLEAN;\n" +
                "  FUNCTION ISEMPTY(I_STRARRAY IN STRARRAY) RETURN BOOLEAN;\n" +
                "  FUNCTION ISEMPTY(I_ALLOCATION_ZONE_TAB IN ALLOCATION_ZONE_TAB)\n" +
                "    RETURN BOOLEAN;\n" +
                "\n" +
                "  -- 1-TRUE, 0-FALSE\n" +
                "  FUNCTION EQUALSTRIMTO(I_1 IN VARCHAR2, I_2 IN VARCHAR2) RETURN NUMBER;\n" +
                "\n" +
                "  FUNCTION EQUALSTO(I_1 IN VARCHAR2, I_2 IN VARCHAR2) RETURN NUMBER;\n" +
                "\n" +
                "  FUNCTION EQUALSTO(I_1 IN NUMBER, I_2 IN NUMBER) RETURN NUMBER;\n" +
                "\n" +
                "  FUNCTION EQUALSTO(I_1 IN DATE, I_2 IN DATE) RETURN NUMBER;\n" +
                "\n" +
                "  -- get new sequence no from sequence table for ALL\n" +
                "  -- after parse\n" +
                "  -- if error, return null\n" +
                "  FUNCTION GET_NEW_SEQUENCE_NO(I_SEQENCE_NAME IN SEQUENCE_TABLE.SEQUENCE_NAME%TYPE,\n" +
                "                               I_CACHE_SIZE   IN INT DEFAULT 50)\n" +
                "    RETURN SEQUENCE_TABLE.SEQUENCE_NUMBER%TYPE;\n" +
                "\n" +
                "  FUNCTION GET_TEMPLATE_SP_CODE_OID(WAREHOUSE_ID     IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE,\n" +
                "                                    OWNER_ID         IN CM_COMPANY.COMPANY_ID%TYPE,\n" +
                "                                    SP_ACTUAL_CODE   IN SP_CODE.CODE%TYPE,\n" +
                "                                    SP_CATEGORY_NAME IN SP_CODE.CATEGORY_NAME%TYPE)\n" +
                "    RETURN SP_CODE.OID%TYPE;\n" +
                "\n" +
                "  FUNCTION CHECK_SUP_TEMPLATE(I_WAREHOUSE_ID   IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE,\n" +
                "                              I_OWNER_ID       IN CM_COMPANY.COMPANY_ID%TYPE,\n" +
                "                              SP_ACTUAL_CODE   IN SP_CODE.CODE%TYPE,\n" +
                "                              SP_CATEGORY_NAME IN SP_CODE.CATEGORY_NAME%TYPE,\n" +
                "                              SP_CODE_OID      OUT SP_CODE.OID%TYPE)\n" +
                "    RETURN BOOLEAN;\n" +
                "  /*\n" +
                "  parse new sequence no from sequence to  sequence+suffix\n" +
                "  suffix : db property\n" +
                "  */\n" +
                "  FUNCTION PARSE_NEW_SEQUENCE_NO(I_ORIGINAL_OID IN SEQUENCE_TABLE.SEQUENCE_NUMBER%TYPE)\n" +
                "    RETURN SEQUENCE_TABLE.SEQUENCE_NUMBER%TYPE;\n" +
                "  -- judge if a string can be pared to number\n" +
                "  FUNCTION IS_NUMBER(I_STR IN VARCHAR2) RETURN BOOLEAN;\n" +
                "  FUNCTION IS_NUMBER2(I_STR IN VARCHAR2) RETURN NUMBER;\n" +
                "\n" +
                "  -- log info about store procedure into WOS_STORE_PROCEDURE_LOG\n" +
                "  -- if current_env = DEV, thrown\n" +
                "  --   ORA-20101: Exception thrown for development\n" +
                "  PROCEDURE LOGSTOREDPROCEDURE(I_MODULE_NAME          IN VARCHAR2,\n" +
                "                               I_STORE_PROCEDURE_NAME IN VARCHAR2,\n" +
                "                               I_FUNCTION_NAME        IN VARCHAR2,\n" +
                "                               I_SUB_FUNCTION_NAME    IN VARCHAR2,\n" +
                "                               I_MAIN_REF_NO          IN VARCHAR2,\n" +
                "                               I_SUB_REF_NO           IN VARCHAR2,\n" +
                "                               I_SUB_CHILD_REF_NO     IN VARCHAR2,\n" +
                "                               I_TASK                 IN VARCHAR2,\n" +
                "                               I_TASK_DESCRIPTION     IN VARCHAR2,\n" +
                "                               I_STATUS               IN VARCHAR2,\n" +
                "                               I_ERRCODE              IN VARCHAR2 DEFAULT NULL,\n" +
                "                               I_ERRMSG               IN VARCHAR2 DEFAULT NULL);\n" +
                "\n" +
                "  FUNCTION GET_SP_OID(I_TABLE_NAME IN SP_CATEGORY.NAME%TYPE,\n" +
                "                      I_CODE       IN SP_CODE.CODE%TYPE)\n" +
                "    RETURN SP_CODE.OID%TYPE /*RESULT_CACHE*/\n" +
                "  ;\n" +
                "\n" +
                "  FUNCTION GET_SP_CODE(I_OID IN SP_CODE.OID%TYPE, I_CUT IN NUMBER)\n" +
                "    RETURN SP_CODE.CODE%TYPE /*RESULT_CACHE*/\n" +
                "  ;\n" +
                "\n" +
                "  FUNCTION GET_GEO_OID(I_CODE IN SP_GEO_PORT.LOC_CODE%TYPE)\n" +
                "    RETURN SP_GEO_PORT.OID%TYPE /*RESULT_CACHE*/\n" +
                "  ;\n" +
                "\n" +
                "  FUNCTION GET_GEO_CODE(I_OID IN SP_GEO_PORT.OID%TYPE)\n" +
                "    RETURN SP_GEO_PORT.LOC_CODE%TYPE /*RESULT_CACHE*/\n" +
                "  ;\n" +
                "\n" +
                "  FUNCTION GET_GEO_NAME(I_OID IN SP_GEO_PORT.OID%TYPE)\n" +
                "    RETURN SP_GEO_PORT.LOC_NAME%TYPE /*RESULT_CACHE*/\n" +
                "  ;\n" +
                "\n" +
                "  FUNCTION GET_PCS_UOM(I_ITEM_OID IN CM_ITEM.OID%TYPE)\n" +
                "    RETURN CM_UNIT_OF_MEASURE.UOM%TYPE;\n" +
                "\n" +
                "  -- added by yiwo for interface.\n" +
                "  FUNCTION GET_SUB_STRING(I_OLD_STRING VARCHAR2,\n" +
                "                          I_SEP_STRING VARCHAR2,\n" +
                "                          I_NUM        NUMBER) RETURN VARCHAR2;\n" +
                "\n" +
                "  FUNCTION CONNECT_SEAL_NO(I_SEAL_NO  IN IF_IN_SP_CONTAINER.SEAL_NO%TYPE,\n" +
                "                           I_SEAL_NO1 IN IF_IN_SP_CONTAINER.SEAL_NO1%TYPE,\n" +
                "                           I_SEAL_NO2 IN IF_IN_SP_CONTAINER.SEAL_NO2%TYPE,\n" +
                "                           I_SEAL_NO3 IN IF_IN_SP_CONTAINER.SEAL_NO3%TYPE,\n" +
                "                           I_SEAL_NO4 IN IF_IN_SP_CONTAINER.SEAL_NO4%TYPE)\n" +
                "    RETURN VARCHAR2;\n" +
                "  PROCEDURE SEND_MAIL(SENDER      IN VARCHAR2,\n" +
                "                      RECIPIENT   IN VARCHAR2,\n" +
                "                      CCRECIPIENT IN VARCHAR2,\n" +
                "                      SUBJECT     IN VARCHAR2,\n" +
                "                      MESSAGE     IN VARCHAR2,\n" +
                "                      SMTP        IN VARCHAR2,\n" +
                "                      PORT        IN VARCHAR2);\n" +
                "  FUNCTION GET_PARAM(I_MODULE             IN VARCHAR2,\n" +
                "                     I_CONFIGURATION_NAME IN VARCHAR2) RETURN VARCHAR2 /*RESULT_CACHE*/\n" +
                "  ;\n" +
                "\n" +
                "  FUNCTION IS_COMMON_DB RETURN BOOLEAN;\n" +
                "\n" +
                "  FUNCTION IS_BIZ_DB RETURN BOOLEAN;\n" +
                "\n" +
                "  /*Find rule value in warehouse or warehouse+company level by rule code\n" +
                "   i_rule_level\n" +
                "             not null, among\n" +
                "               RULE_SYSTEM_LEVEL, RULE_WAREHOUSE_LEVEL and RULE_WAREHOUSE_OWNER_LEVEL\n" +
                "   i_rule_code\n" +
                "             rule code to match\n" +
                "  return rule_value_t_tab*/\n" +
                "  FUNCTION GET_VALUE_BY_RULE_CODE(I_RULE_LEVEL   IN VARCHAR2,\n" +
                "                                  I_RULE_CODE    IN CM_NEW_RULE.RULE_CODE%TYPE DEFAULT NULL,\n" +
                "                                  I_WAREHOUSE_ID IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE DEFAULT NULL,\n" +
                "                                  I_OWNER_ID     IN CM_COMPANY.COMPANY_ID%TYPE DEFAULT NULL)\n" +
                "    RETURN RULE_VALUE_T_TAB;\n" +
                "\n" +
                "  /*Find rule value in warehouse or warehouse+company level by rule codes\n" +
                "   i_rule_level\n" +
                "             not null, among\n" +
                "               RULE_SYSTEM_LEVEL, RULE_WAREHOUSE_LEVEL and RULE_WAREHOUSE_OWNER_LEVEL\n" +
                "   i_rule_codes\n" +
                "             rule codes to match\n" +
                "  return rule_value_t_tab*/\n" +
                "  FUNCTION GET_RULE_VALUE_BY_RULE_CODE(I_RULE_LEVEL    IN VARCHAR2,\n" +
                "                                       I_RULE_CODES    IN STRARRAY DEFAULT NULL,\n" +
                "                                       I_WAREHOUSE_IDS IN STRARRAY DEFAULT NULL,\n" +
                "                                       I_OWNER_IDS     IN STRARRAY DEFAULT NULL)\n" +
                "    RETURN RULE_VALUE_T_TAB;\n" +
                "\n" +
                "  /*Find rule value in system, warehouse or warehouse+company level by parent code\n" +
                "   i_rule_level\n" +
                "             not null, among\n" +
                "               RULE_SYSTEM_LEVEL, RULE_WAREHOUSE_LEVEL and RULE_WAREHOUSE_OWNER_LEVEL\n" +
                "   i_parent_code\n" +
                "             parent code to match\n" +
                "  return rule_value_t_tab*/\n" +
                "  FUNCTION GET_VALUE_BY_PARENT_CODE(I_RULE_LEVEL   IN VARCHAR2,\n" +
                "                                    I_PARENT_CODE  IN CM_NEW_RULE.RULE_CODE%TYPE DEFAULT NULL,\n" +
                "                                    I_WAREHOUSE_ID IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE DEFAULT NULL,\n" +
                "                                    I_OWNER_ID     IN CM_COMPANY.COMPANY_ID%TYPE DEFAULT NULL)\n" +
                "    RETURN RULE_VALUE_T_TAB;\n" +
                "\n" +
                "  /*Find rule value in system, warehouse or warehouse+company level by parent codes\n" +
                "   i_rule_level\n" +
                "             not null, among\n" +
                "               RULE_SYSTEM_LEVEL, RULE_WAREHOUSE_LEVEL and RULE_WAREHOUSE_OWNER_LEVEL\n" +
                "   i_parent_codes\n" +
                "             parent codes to match\n" +
                "  return rule_value_t_tab*/\n" +
                "  FUNCTION GET_RULE_VALUE_BY_PARENT_CODE(I_RULE_LEVEL    IN VARCHAR2,\n" +
                "                                         I_PARENT_CODES  IN STRARRAY DEFAULT NULL,\n" +
                "                                         I_WAREHOUSE_IDS IN STRARRAY DEFAULT NULL,\n" +
                "                                         I_OWNER_IDS     IN STRARRAY DEFAULT NULL)\n" +
                "    RETURN RULE_VALUE_T_TAB;\n" +
                "\n" +
                "  -- make sure there is no same <warehouse,owner> in result\n" +
                "  FUNCTION GET_CONFIGRATION_4_WH_OWNER(I_WAREHOUSE_ID IN VARCHAR2,\n" +
                "                                       I_OWNER_ID     IN VARCHAR2,\n" +
                "                                       I_RULE_VALUES  IN RULE_VALUE_T_TAB)\n" +
                "    RETURN RULE_VALUE_T_TAB;\n" +
                "\n" +
                "  -- make sure there is no same <warehouse> in result\n" +
                "  FUNCTION GET_CONFIGRATION_4_WH(I_WAREHOUSE_ID IN VARCHAR2,\n" +
                "                                 I_RULE_VALUES  IN RULE_VALUE_T_TAB)\n" +
                "    RETURN RULE_VALUE_T_TAB;\n" +
                "\n" +
                "  /*\n" +
                "  This function assume there is only one value at most, so\n" +
                "  it is recommended that i_abc_rule_values is the result of get_configration_4_wh_owner\n" +
                "  */\n" +
                "  FUNCTION GET_VALUE_OF_RULE(I_RULE_CODE   IN VARCHAR2,\n" +
                "                             I_RULE_VALUES IN RULE_VALUE_T_TAB)\n" +
                "    RETURN VARCHAR2;\n" +
                "\n" +
                "  /*\n" +
                "  This function can handle multi values of the rule, generally,\n" +
                "  it is used to filter values of different <wh,owner>\n" +
                "  */\n" +
                "  FUNCTION GET_VALUES_OF_RULE(I_RULE_CODE   IN VARCHAR2,\n" +
                "                              I_RULE_VALUES IN RULE_VALUE_T_TAB)\n" +
                "    RETURN RULE_VALUE_T_TAB;\n" +
                "\n" +
                "  /*\n" +
                "  Like split in Java, no regular expression supported.\n" +
                "  */\n" +
                "  FUNCTION STRING_SPLIT(P_STR         IN VARCHAR2,\n" +
                "                        P_DELIMITER   IN VARCHAR2,\n" +
                "                        P_IGNORE_NULL IN BOOLEAN DEFAULT FALSE)\n" +
                "    RETURN STRARRAY;\n" +
                "\n" +
                "  /*dont consider operator is NONE*/\n" +
                "  FUNCTION GET_COMMON_FILTER_EXP(I_COL_EXP    IN VARCHAR2,\n" +
                "                                 I_OPERATOR   IN VARCHAR2,\n" +
                "                                 I_VALUE      IN VARCHAR2,\n" +
                "                                 I_VALUE_TYPE IN VARCHAR2) RETURN VARCHAR2;\n" +
                "\n" +
                "  -- get first element of a collection if it's not empty, otherwise return null\n" +
                "  FUNCTION GETSTRARRAYFIRST(I_STRARRAY IN STRARRAY) RETURN VARCHAR;\n" +
                "\n" +
                "  -- get first element of a collection if it's not empty, otherwise return null\n" +
                "  FUNCTION GETNUMARRAYFIRST(I_NUMARRAY IN NUMARRAY) RETURN NUMBER;\n" +
                "\n" +
                "  -- get first element of a collection if it's not empty, otherwise return null\n" +
                "  FUNCTION GETDATEARRAYFIRST(I_DATEARRAY IN DATEARRAY) RETURN DATE;\n" +
                "\n" +
                "  -- tick the start of an operation\n" +
                "  PROCEDURE TICK_OPERATION_HISTORY(I_OPERATION_TYPE IN RP_OPERATION_HISTORY.OPERATION_TYPE%TYPE,\n" +
                "                                   I_OPERATION_KEY  IN RP_OPERATION_HISTORY.OPERATION_KEY%TYPE,\n" +
                "                                   I_USER_ID        IN CM_USER.USER_ID%TYPE,\n" +
                "                                   I_REF_NO         IN RP_OPERATION_HISTORY.REF_NO%TYPE DEFAULT NULL);\n" +
                "\n" +
                "  -- tock the start of an operation\n" +
                "  PROCEDURE TOCK_OPERATION_HISTORY(I_OPERATION_TYPE IN RP_OPERATION_HISTORY.OPERATION_TYPE%TYPE,\n" +
                "                                   I_OPERATION_KEY  IN RP_OPERATION_HISTORY.OPERATION_KEY%TYPE,\n" +
                "                                   I_USER_ID        IN CM_USER.USER_ID%TYPE,\n" +
                "                                   I_REF_NO         IN RP_OPERATION_HISTORY.REF_NO%TYPE DEFAULT NULL);\n" +
                "\n" +
                "  -- tick and tock the start of an operation at the same time\n" +
                "  PROCEDURE TICK_TOCK_OPERATION_HISTORY(I_OPERATION_TYPE IN RP_OPERATION_HISTORY.OPERATION_TYPE%TYPE,\n" +
                "                                        I_OPERATION_KEY  IN RP_OPERATION_HISTORY.OPERATION_KEY%TYPE,\n" +
                "                                        I_USER_ID        IN CM_USER.USER_ID%TYPE,\n" +
                "                                        I_REF_NO         IN RP_OPERATION_HISTORY.REF_NO%TYPE DEFAULT NULL);\n" +
                "\n" +
                "  FUNCTION SUBARRAY(I_SRC_STRARRAY IN STRARRAY,\n" +
                "                    I_FIRST        IN NUMBER,\n" +
                "                    I_LAST         IN NUMBER) RETURN STRARRAY;\n" +
                "\n" +
                "  FUNCTION SUBARRAY(I_SRC_STRARRAY IN NUMARRAY,\n" +
                "                    I_FIRST        IN NUMBER,\n" +
                "                    I_LAST         IN NUMBER) RETURN NUMARRAY;\n" +
                "\n" +
                "  PROCEDURE LOG_ACTION_LOG(I_WAREHOUSE_ID IN VARCHAR2,\n" +
                "                           I_ORDER_OID    IN NUMBER,\n" +
                "                           I_TYPE         IN VARCHAR2,\n" +
                "                           I_OPERATION    IN VARCHAR2,\n" +
                "                           I_OPERATOR     IN VARCHAR2);\n" +
                "\n" +
                "END WOS_COMMON_PKG;";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE OR REPLACE PACKAGE WOS_COMMON_PKG\n" +
                "IS\n" +
                "\tTYPE RULE_VALUE_T_REC IS RECORD (\n" +
                "\t\tRULE_CODE CM_NEW_RULE.RULE_CODE%TYPE,\n" +
                "\t\tPARENT_CODE CM_NEW_RULE.PARENT_CODE%TYPE,\n" +
                "\t\tVALUE CM_NEW_RULE_VALUE.VALUE%TYPE,\n" +
                "\t\tWAREHOUSE_ID CM_NEW_RULE_VALUE.WAREHOUSE_ID%TYPE,\n" +
                "\t\tOWNER_ID CM_NEW_RULE_VALUE.OWNER_ID%TYPE\n" +
                "\t);\n" +
                "\tTYPE RULE_VALUE_T_RECTAB IS TABLE OF RULE_VALUE_T_REC;\n" +
                "\tTYPE RULE_VALUE_REFCUR_T IS REF CURSOR;\n" +
                "\tMODULE_COMMON CONSTANT VARCHAR2(50) := 'COMMON';\n" +
                "\tCONFIGURATION_CURRENT_ENV CONSTANT VARCHAR2(50) := 'CURRENT_ENV';\n" +
                "\tCURRENT_ENV_DEV CONSTANT VARCHAR2(50) := 'DEV';\n" +
                "\tTZ_ASIA_HONG_KONG CONSTANT VARCHAR2(50) := 'Asia/Hong_Kong';\n" +
                "\tTZ_OFFSET_ASIA_HONG_KONG CONSTANT VARCHAR2(50) := 'Asia/Hong_Kong';\n" +
                "\tRULE_SYSTEM_LEVEL CONSTANT VARCHAR2(50) := 'RULE_SYSTEM_LEVEL';\n" +
                "\tRULE_WAREHOUSE_LEVEL CONSTANT VARCHAR2(50) := 'RULE_WAREHOUSE_LEVEL';\n" +
                "\tRULE_WAREHOUSE_OWNER_LEVEL CONSTANT VARCHAR2(50) := 'RULE_WAREHOUSE_OWNER_LEVEL';\n" +
                "\tOH_OT_SHIPPING_ALLOCATION CONSTANT RP_OPERATION_HISTORY.OPERATION_TYPE%TYPE := 'SHIPPING_ALLOCATION';\n" +
                "\tFUNCTION ADD_PREFIX (\n" +
                "\t\tI_STR IN VARCHAR2,\n" +
                "\t\tI_PREFIX IN VARCHAR2,\n" +
                "\t\tI_IGNORE_NULL IN BOOLEAN DEFAULT TRUE\n" +
                "\t) RETURN VARCHAR2;\n" +
                "\tFUNCTION STRING_FILTER (\n" +
                "\t\tI_STRING IN VARCHAR2\n" +
                "\t) RETURN VARCHAR2;\n" +
                "\tFUNCTION CONCAT_ARRAY (\n" +
                "\t\tI_STRARRAY IN STRARRAY,\n" +
                "\t\tI_DELIMITER IN VARCHAR2,\n" +
                "\t\tI_NEED_QUOTE IN BOOLEAN DEFAULT FALSE,\n" +
                "\t\tI_IGNORE_NULL IN BOOLEAN DEFAULT TRUE\n" +
                "\t) RETURN VARCHAR2;\n" +
                "\tFUNCTION CONCAT_ARRAY (\n" +
                "\t\tI_NUMARRAY IN NUMARRAY,\n" +
                "\t\tI_DELIMITER IN VARCHAR2,\n" +
                "\t\tI_NEED_QUOTE IN BOOLEAN DEFAULT FALSE,\n" +
                "\t\tI_IGNORE_NULL IN BOOLEAN DEFAULT TRUE\n" +
                "\t) RETURN VARCHAR2;\n" +
                "\tFUNCTION ADD (\n" +
                "\t\tI_DATE IN DATE,\n" +
                "\t\tI_FIELD IN VARCHAR2 DEFAULT 'DD',\n" +
                "\t\tI_OFFSET IN NUMBER DEFAULT 0\n" +
                "\t) RETURN DATE;\n" +
                "\tFUNCTION GET_WAREHOUSE_TZ (\n" +
                "\t\tI_WAREHOUSE IN VARCHAR2\n" +
                "\t) RETURN VARCHAR2;\n" +
                "\tFUNCTION TO_WAREHOUSE_TZ (\n" +
                "\t\tI_DATE IN DATE,\n" +
                "\t\tI_WAREHOUSE IN VARCHAR2,\n" +
                "\t\tI_ZONE IN VARCHAR2 DEFAULT 'Asia/Shanghai'\n" +
                "\t) RETURN DATE;\n" +
                "\tFUNCTION FROM_WAREHOUSE_TZ (\n" +
                "\t\tI_DATE IN DATE,\n" +
                "\t\tI_WAREHOUSE IN VARCHAR2,\n" +
                "\t\tI_ZONE IN VARCHAR2 DEFAULT 'Asia/Shanghai'\n" +
                "\t) RETURN DATE;\n" +
                "\tFUNCTION TRUNC_WAREHOUSE_TZ (\n" +
                "\t\tI_DATE IN DATE,\n" +
                "\t\tI_WAREHOUSE IN VARCHAR2,\n" +
                "\t\tI_FMT IN VARCHAR2 DEFAULT 'DD',\n" +
                "\t\tI_ZONE IN VARCHAR2 DEFAULT 'Asia/Shanghai'\n" +
                "\t) RETURN DATE;\n" +
                "\tFUNCTION ROUND_WAREHOUSE_TZ (\n" +
                "\t\tI_DATE IN DATE,\n" +
                "\t\tI_WAREHOUSE IN VARCHAR2,\n" +
                "\t\tI_FMT IN VARCHAR2 DEFAULT 'DD',\n" +
                "\t\tI_ZONE IN VARCHAR2 DEFAULT 'Asia/Shanghai'\n" +
                "\t) RETURN DATE;\n" +
                "\tFUNCTION TRUNC_WAREHOUSE_TZ_OFFSET (\n" +
                "\t\tI_DATE IN DATE,\n" +
                "\t\tI_WAREHOUSE IN VARCHAR2,\n" +
                "\t\tI_FIELD_B IN VARCHAR2 DEFAULT 'DD',\n" +
                "\t\tI_OFFSET_B IN NUMBER DEFAULT 0,\n" +
                "\t\tI_FIELD_A IN VARCHAR2 DEFAULT 'DD',\n" +
                "\t\tI_OFFSET_A IN NUMBER DEFAULT 0,\n" +
                "\t\tI_FMT IN VARCHAR2 DEFAULT 'DD',\n" +
                "\t\tI_ZONE IN VARCHAR2 DEFAULT 'Asia/Shanghai'\n" +
                "\t) RETURN DATE;\n" +
                "\tFUNCTION GET_FIRST_DAY_OF_WEEK (\n" +
                "\t\tI_DATE IN DATE,\n" +
                "\t\tI_INDEX IN NUMBER DEFAULT 0\n" +
                "\t) RETURN DATE;\n" +
                "\tFUNCTION GET_FIRST_DAY_OF_MONTH (\n" +
                "\t\tI_DATE IN DATE,\n" +
                "\t\tI_INDEX IN NUMBER DEFAULT 0\n" +
                "\t) RETURN DATE;\n" +
                "\tFUNCTION GET_FIRST_DAY_OF_YEAR (\n" +
                "\t\tI_DATE IN DATE,\n" +
                "\t\tI_INDEX IN NUMBER DEFAULT 0\n" +
                "\t) RETURN DATE;\n" +
                "\tFUNCTION GET_FIRST_DAY_OF_WEEK (\n" +
                "\t\tI_DATE IN DATE,\n" +
                "\t\tI_WAREHOUSE IN VARCHAR2,\n" +
                "\t\tI_INDEX IN NUMBER DEFAULT 0,\n" +
                "\t\tI_ZONE IN VARCHAR2 DEFAULT 'Asia/Shanghai'\n" +
                "\t) RETURN DATE;\n" +
                "\tFUNCTION GET_FIRST_DAY_OF_MONTH (\n" +
                "\t\tI_DATE IN DATE,\n" +
                "\t\tI_WAREHOUSE IN VARCHAR2,\n" +
                "\t\tI_INDEX IN NUMBER DEFAULT 0,\n" +
                "\t\tI_ZONE IN VARCHAR2 DEFAULT 'Asia/Shanghai'\n" +
                "\t) RETURN DATE;\n" +
                "\tFUNCTION GET_FIRST_DAY_OF_YEAR (\n" +
                "\t\tI_DATE IN DATE,\n" +
                "\t\tI_WAREHOUSE IN VARCHAR2,\n" +
                "\t\tI_INDEX IN NUMBER DEFAULT 0,\n" +
                "\t\tI_ZONE IN VARCHAR2 DEFAULT 'Asia/Shanghai'\n" +
                "\t) RETURN DATE;\n" +
                "\tPROCEDURE PUT_LINES (\n" +
                "\t\tI_STR VARCHAR2,\n" +
                "\t\tI_LINE_LENGTH NUMBER DEFAULT 255\n" +
                "\t);\n" +
                "\tFUNCTION ISEMPTY (\n" +
                "\t\tI_NUMARRAY IN NUMARRAY\n" +
                "\t) RETURN BOOLEAN;\n" +
                "\tFUNCTION ISEMPTY (\n" +
                "\t\tI_STRARRAY IN STRARRAY\n" +
                "\t) RETURN BOOLEAN;\n" +
                "\tFUNCTION ISEMPTY (\n" +
                "\t\tI_ALLOCATION_ZONE_TAB IN ALLOCATION_ZONE_TAB\n" +
                "\t) RETURN BOOLEAN;\n" +
                "\tFUNCTION EQUALSTRIMTO (\n" +
                "\t\tI_1 IN VARCHAR2,\n" +
                "\t\tI_2 IN VARCHAR2\n" +
                "\t) RETURN NUMBER;\n" +
                "\tFUNCTION EQUALSTO (\n" +
                "\t\tI_1 IN VARCHAR2,\n" +
                "\t\tI_2 IN VARCHAR2\n" +
                "\t) RETURN NUMBER;\n" +
                "\tFUNCTION EQUALSTO (\n" +
                "\t\tI_1 IN NUMBER,\n" +
                "\t\tI_2 IN NUMBER\n" +
                "\t) RETURN NUMBER;\n" +
                "\tFUNCTION EQUALSTO (\n" +
                "\t\tI_1 IN DATE,\n" +
                "\t\tI_2 IN DATE\n" +
                "\t) RETURN NUMBER;\n" +
                "\tFUNCTION GET_NEW_SEQUENCE_NO (\n" +
                "\t\tI_SEQENCE_NAME IN SEQUENCE_TABLE.SEQUENCE_NAME%TYPE,\n" +
                "\t\tI_CACHE_SIZE IN INT DEFAULT 50\n" +
                "\t) RETURN SEQUENCE_TABLE.SEQUENCE_NUMBER%TYPE;\n" +
                "\tFUNCTION GET_TEMPLATE_SP_CODE_OID (\n" +
                "\t\tWAREHOUSE_ID IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE,\n" +
                "\t\tOWNER_ID IN CM_COMPANY.COMPANY_ID%TYPE,\n" +
                "\t\tSP_ACTUAL_CODE IN SP_CODE.CODE%TYPE,\n" +
                "\t\tSP_CATEGORY_NAME IN SP_CODE.CATEGORY_NAME%TYPE\n" +
                "\t) RETURN SP_CODE.OID%TYPE;\n" +
                "\tFUNCTION CHECK_SUP_TEMPLATE (\n" +
                "\t\tI_WAREHOUSE_ID IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE,\n" +
                "\t\tI_OWNER_ID IN CM_COMPANY.COMPANY_ID%TYPE,\n" +
                "\t\tSP_ACTUAL_CODE IN SP_CODE.CODE%TYPE,\n" +
                "\t\tSP_CATEGORY_NAME IN SP_CODE.CATEGORY_NAME%TYPE,\n" +
                "\t\tSP_CODE_OID OUT SP_CODE.OID%TYPE\n" +
                "\t) RETURN BOOLEAN;\n" +
                "\tFUNCTION PARSE_NEW_SEQUENCE_NO (\n" +
                "\t\tI_ORIGINAL_OID IN SEQUENCE_TABLE.SEQUENCE_NUMBER%TYPE\n" +
                "\t) RETURN SEQUENCE_TABLE.SEQUENCE_NUMBER%TYPE;\n" +
                "\tFUNCTION IS_NUMBER (\n" +
                "\t\tI_STR IN VARCHAR2\n" +
                "\t) RETURN BOOLEAN;\n" +
                "\tFUNCTION IS_NUMBER2 (\n" +
                "\t\tI_STR IN VARCHAR2\n" +
                "\t) RETURN NUMBER;\n" +
                "\tPROCEDURE LOGSTOREDPROCEDURE (\n" +
                "\t\tI_MODULE_NAME IN VARCHAR2,\n" +
                "\t\tI_STORE_PROCEDURE_NAME IN VARCHAR2,\n" +
                "\t\tI_FUNCTION_NAME IN VARCHAR2,\n" +
                "\t\tI_SUB_FUNCTION_NAME IN VARCHAR2,\n" +
                "\t\tI_MAIN_REF_NO IN VARCHAR2,\n" +
                "\t\tI_SUB_REF_NO IN VARCHAR2,\n" +
                "\t\tI_SUB_CHILD_REF_NO IN VARCHAR2,\n" +
                "\t\tI_TASK IN VARCHAR2,\n" +
                "\t\tI_TASK_DESCRIPTION IN VARCHAR2,\n" +
                "\t\tI_STATUS IN VARCHAR2,\n" +
                "\t\tI_ERRCODE IN VARCHAR2 DEFAULT NULL,\n" +
                "\t\tI_ERRMSG IN VARCHAR2 DEFAULT NULL\n" +
                "\t);\n" +
                "\tFUNCTION GET_SP_OID (\n" +
                "\t\tI_TABLE_NAME IN SP_CATEGORY.NAME%TYPE,\n" +
                "\t\tI_CODE IN SP_CODE.CODE%TYPE\n" +
                "\t) RETURN SP_CODE.OID%TYPE;\n" +
                "\tFUNCTION GET_SP_CODE (\n" +
                "\t\tI_OID IN SP_CODE.OID%TYPE,\n" +
                "\t\tI_CUT IN NUMBER\n" +
                "\t) RETURN SP_CODE.CODE%TYPE;\n" +
                "\tFUNCTION GET_GEO_OID (\n" +
                "\t\tI_CODE IN SP_GEO_PORT.LOC_CODE%TYPE\n" +
                "\t) RETURN SP_GEO_PORT.OID%TYPE;\n" +
                "\tFUNCTION GET_GEO_CODE (\n" +
                "\t\tI_OID IN SP_GEO_PORT.OID%TYPE\n" +
                "\t) RETURN SP_GEO_PORT.LOC_CODE%TYPE;\n" +
                "\tFUNCTION GET_GEO_NAME (\n" +
                "\t\tI_OID IN SP_GEO_PORT.OID%TYPE\n" +
                "\t) RETURN SP_GEO_PORT.LOC_NAME%TYPE;\n" +
                "\tFUNCTION GET_PCS_UOM (\n" +
                "\t\tI_ITEM_OID IN CM_ITEM.OID%TYPE\n" +
                "\t) RETURN CM_UNIT_OF_MEASURE.UOM%TYPE;\n" +
                "\tFUNCTION GET_SUB_STRING (\n" +
                "\t\tI_OLD_STRING VARCHAR2,\n" +
                "\t\tI_SEP_STRING VARCHAR2,\n" +
                "\t\tI_NUM NUMBER\n" +
                "\t) RETURN VARCHAR2;\n" +
                "\tFUNCTION CONNECT_SEAL_NO (\n" +
                "\t\tI_SEAL_NO IN IF_IN_SP_CONTAINER.SEAL_NO%TYPE,\n" +
                "\t\tI_SEAL_NO1 IN IF_IN_SP_CONTAINER.SEAL_NO1%TYPE,\n" +
                "\t\tI_SEAL_NO2 IN IF_IN_SP_CONTAINER.SEAL_NO2%TYPE,\n" +
                "\t\tI_SEAL_NO3 IN IF_IN_SP_CONTAINER.SEAL_NO3%TYPE,\n" +
                "\t\tI_SEAL_NO4 IN IF_IN_SP_CONTAINER.SEAL_NO4%TYPE\n" +
                "\t) RETURN VARCHAR2;\n" +
                "\tPROCEDURE SEND_MAIL (\n" +
                "\t\tSENDER IN VARCHAR2,\n" +
                "\t\tRECIPIENT IN VARCHAR2,\n" +
                "\t\tCCRECIPIENT IN VARCHAR2,\n" +
                "\t\tSUBJECT IN VARCHAR2,\n" +
                "\t\tMESSAGE IN VARCHAR2,\n" +
                "\t\tSMTP IN VARCHAR2,\n" +
                "\t\tPORT IN VARCHAR2\n" +
                "\t);\n" +
                "\tFUNCTION GET_PARAM (\n" +
                "\t\tI_MODULE IN VARCHAR2,\n" +
                "\t\tI_CONFIGURATION_NAME IN VARCHAR2\n" +
                "\t) RETURN VARCHAR2;\n" +
                "\tFUNCTION IS_COMMON_DB RETURN BOOLEAN;\n" +
                "\tFUNCTION IS_BIZ_DB RETURN BOOLEAN;\n" +
                "\tFUNCTION GET_VALUE_BY_RULE_CODE (\n" +
                "\t\tI_RULE_LEVEL IN VARCHAR2,\n" +
                "\t\tI_RULE_CODE IN CM_NEW_RULE.RULE_CODE%TYPE DEFAULT NULL,\n" +
                "\t\tI_WAREHOUSE_ID IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE DEFAULT NULL,\n" +
                "\t\tI_OWNER_ID IN CM_COMPANY.COMPANY_ID%TYPE DEFAULT NULL\n" +
                "\t) RETURN RULE_VALUE_T_TAB;\n" +
                "\tFUNCTION GET_RULE_VALUE_BY_RULE_CODE (\n" +
                "\t\tI_RULE_LEVEL IN VARCHAR2,\n" +
                "\t\tI_RULE_CODES IN STRARRAY DEFAULT NULL,\n" +
                "\t\tI_WAREHOUSE_IDS IN STRARRAY DEFAULT NULL,\n" +
                "\t\tI_OWNER_IDS IN STRARRAY DEFAULT NULL\n" +
                "\t) RETURN RULE_VALUE_T_TAB;\n" +
                "\tFUNCTION GET_VALUE_BY_PARENT_CODE (\n" +
                "\t\tI_RULE_LEVEL IN VARCHAR2,\n" +
                "\t\tI_PARENT_CODE IN CM_NEW_RULE.RULE_CODE%TYPE DEFAULT NULL,\n" +
                "\t\tI_WAREHOUSE_ID IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE DEFAULT NULL,\n" +
                "\t\tI_OWNER_ID IN CM_COMPANY.COMPANY_ID%TYPE DEFAULT NULL\n" +
                "\t) RETURN RULE_VALUE_T_TAB;\n" +
                "\tFUNCTION GET_RULE_VALUE_BY_PARENT_CODE (\n" +
                "\t\tI_RULE_LEVEL IN VARCHAR2,\n" +
                "\t\tI_PARENT_CODES IN STRARRAY DEFAULT NULL,\n" +
                "\t\tI_WAREHOUSE_IDS IN STRARRAY DEFAULT NULL,\n" +
                "\t\tI_OWNER_IDS IN STRARRAY DEFAULT NULL\n" +
                "\t) RETURN RULE_VALUE_T_TAB;\n" +
                "\tFUNCTION GET_CONFIGRATION_4_WH_OWNER (\n" +
                "\t\tI_WAREHOUSE_ID IN VARCHAR2,\n" +
                "\t\tI_OWNER_ID IN VARCHAR2,\n" +
                "\t\tI_RULE_VALUES IN RULE_VALUE_T_TAB\n" +
                "\t) RETURN RULE_VALUE_T_TAB;\n" +
                "\tFUNCTION GET_CONFIGRATION_4_WH (\n" +
                "\t\tI_WAREHOUSE_ID IN VARCHAR2,\n" +
                "\t\tI_RULE_VALUES IN RULE_VALUE_T_TAB\n" +
                "\t) RETURN RULE_VALUE_T_TAB;\n" +
                "\tFUNCTION GET_VALUE_OF_RULE (\n" +
                "\t\tI_RULE_CODE IN VARCHAR2,\n" +
                "\t\tI_RULE_VALUES IN RULE_VALUE_T_TAB\n" +
                "\t) RETURN VARCHAR2;\n" +
                "\tFUNCTION GET_VALUES_OF_RULE (\n" +
                "\t\tI_RULE_CODE IN VARCHAR2,\n" +
                "\t\tI_RULE_VALUES IN RULE_VALUE_T_TAB\n" +
                "\t) RETURN RULE_VALUE_T_TAB;\n" +
                "\tFUNCTION STRING_SPLIT (\n" +
                "\t\tP_STR IN VARCHAR2,\n" +
                "\t\tP_DELIMITER IN VARCHAR2,\n" +
                "\t\tP_IGNORE_NULL IN BOOLEAN DEFAULT FALSE\n" +
                "\t) RETURN STRARRAY;\n" +
                "\tFUNCTION GET_COMMON_FILTER_EXP (\n" +
                "\t\tI_COL_EXP IN VARCHAR2,\n" +
                "\t\tI_OPERATOR IN VARCHAR2,\n" +
                "\t\tI_VALUE IN VARCHAR2,\n" +
                "\t\tI_VALUE_TYPE IN VARCHAR2\n" +
                "\t) RETURN VARCHAR2;\n" +
                "\tFUNCTION GETSTRARRAYFIRST (\n" +
                "\t\tI_STRARRAY IN STRARRAY\n" +
                "\t) RETURN VARCHAR;\n" +
                "\tFUNCTION GETNUMARRAYFIRST (\n" +
                "\t\tI_NUMARRAY IN NUMARRAY\n" +
                "\t) RETURN NUMBER;\n" +
                "\tFUNCTION GETDATEARRAYFIRST (\n" +
                "\t\tI_DATEARRAY IN DATEARRAY\n" +
                "\t) RETURN DATE;\n" +
                "\tPROCEDURE TICK_OPERATION_HISTORY (\n" +
                "\t\tI_OPERATION_TYPE IN RP_OPERATION_HISTORY.OPERATION_TYPE%TYPE,\n" +
                "\t\tI_OPERATION_KEY IN RP_OPERATION_HISTORY.OPERATION_KEY%TYPE,\n" +
                "\t\tI_USER_ID IN CM_USER.USER_ID%TYPE,\n" +
                "\t\tI_REF_NO IN RP_OPERATION_HISTORY.REF_NO%TYPE DEFAULT NULL\n" +
                "\t);\n" +
                "\tPROCEDURE TOCK_OPERATION_HISTORY (\n" +
                "\t\tI_OPERATION_TYPE IN RP_OPERATION_HISTORY.OPERATION_TYPE%TYPE,\n" +
                "\t\tI_OPERATION_KEY IN RP_OPERATION_HISTORY.OPERATION_KEY%TYPE,\n" +
                "\t\tI_USER_ID IN CM_USER.USER_ID%TYPE,\n" +
                "\t\tI_REF_NO IN RP_OPERATION_HISTORY.REF_NO%TYPE DEFAULT NULL\n" +
                "\t);\n" +
                "\tPROCEDURE TICK_TOCK_OPERATION_HISTORY (\n" +
                "\t\tI_OPERATION_TYPE IN RP_OPERATION_HISTORY.OPERATION_TYPE%TYPE,\n" +
                "\t\tI_OPERATION_KEY IN RP_OPERATION_HISTORY.OPERATION_KEY%TYPE,\n" +
                "\t\tI_USER_ID IN CM_USER.USER_ID%TYPE,\n" +
                "\t\tI_REF_NO IN RP_OPERATION_HISTORY.REF_NO%TYPE DEFAULT NULL\n" +
                "\t);\n" +
                "\tFUNCTION SUBARRAY (\n" +
                "\t\tI_SRC_STRARRAY IN STRARRAY,\n" +
                "\t\tI_FIRST IN NUMBER,\n" +
                "\t\tI_LAST IN NUMBER\n" +
                "\t) RETURN STRARRAY;\n" +
                "\tFUNCTION SUBARRAY (\n" +
                "\t\tI_SRC_STRARRAY IN NUMARRAY,\n" +
                "\t\tI_FIRST IN NUMBER,\n" +
                "\t\tI_LAST IN NUMBER\n" +
                "\t) RETURN NUMARRAY;\n" +
                "\tPROCEDURE LOG_ACTION_LOG (\n" +
                "\t\tI_WAREHOUSE_ID IN VARCHAR2,\n" +
                "\t\tI_ORDER_OID IN NUMBER,\n" +
                "\t\tI_TYPE IN VARCHAR2,\n" +
                "\t\tI_OPERATION IN VARCHAR2,\n" +
                "\t\tI_OPERATOR IN VARCHAR2\n" +
                "\t);\n" +
                "END WOS_COMMON_PKG;", format);
    }


}
