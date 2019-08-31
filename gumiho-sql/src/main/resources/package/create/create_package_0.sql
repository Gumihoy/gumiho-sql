CREATE OR REPLACE PACKAGE WOS_COMMON_PKG IS

  -- Author  : MAMI
  -- Created : 2006-1-23 12:09:32
  -- Amended by jinlo 2007/10/17
  -- Add 3 lock exceptions
  -- Add sp_lock, sp_unlock, send_mail procedures
  -- Purpose : public common functions and constants
  -- Amended by jinlo 2008/10/8
  -- Remove sp_lock, using LTS

  -- Public type declarations

  TYPE RULE_VALUE_T_REC IS RECORD(
    RULE_CODE    CM_NEW_RULE.RULE_CODE%TYPE,
    PARENT_CODE  CM_NEW_RULE.PARENT_CODE%TYPE,
    VALUE        CM_NEW_RULE_VALUE.VALUE%TYPE,
    WAREHOUSE_ID CM_NEW_RULE_VALUE.WAREHOUSE_ID%TYPE,
    OWNER_ID     CM_NEW_RULE_VALUE.OWNER_ID%TYPE);

  TYPE RULE_VALUE_T_RECTAB IS TABLE OF RULE_VALUE_T_REC;

  TYPE RULE_VALUE_REFCUR_T IS REF CURSOR;

  -- Public constant declarations
  MODULE_COMMON             CONSTANT VARCHAR2(50) := 'COMMON';
  CONFIGURATION_CURRENT_ENV CONSTANT VARCHAR2(50) := 'CURRENT_ENV';
  CURRENT_ENV_DEV           CONSTANT VARCHAR2(50) := 'DEV';
  TZ_ASIA_HONG_KONG         CONSTANT VARCHAR2(50) := 'Asia/Hong_Kong';
  TZ_OFFSET_ASIA_HONG_KONG  CONSTANT VARCHAR2(50) := 'Asia/Hong_Kong';

  -- rule value level
  RULE_SYSTEM_LEVEL          CONSTANT VARCHAR2(50) := 'RULE_SYSTEM_LEVEL';
  RULE_WAREHOUSE_LEVEL       CONSTANT VARCHAR2(50) := 'RULE_WAREHOUSE_LEVEL';
  RULE_WAREHOUSE_OWNER_LEVEL CONSTANT VARCHAR2(50) := 'RULE_WAREHOUSE_OWNER_LEVEL';

  -- operation history operation type
  OH_OT_SHIPPING_ALLOCATION CONSTANT RP_OPERATION_HISTORY.OPERATION_TYPE%TYPE := 'SHIPPING_ALLOCATION';

  -- add a prefix to a not null string
  FUNCTION ADD_PREFIX(I_STR         IN VARCHAR2,
                      I_PREFIX      IN VARCHAR2,
                      I_IGNORE_NULL IN BOOLEAN DEFAULT TRUE) RETURN VARCHAR2;

  -- Filter the special characters in a string for the normalized sql.
  -- i_string: a string for sql concatenation
  FUNCTION STRING_FILTER(I_STRING IN VARCHAR2) RETURN VARCHAR2;

  -- concat an array using a delimiter
  FUNCTION CONCAT_ARRAY(I_STRARRAY    IN STRARRAY,
                        I_DELIMITER   IN VARCHAR2,
                        I_NEED_QUOTE  IN BOOLEAN DEFAULT FALSE,
                        I_IGNORE_NULL IN BOOLEAN DEFAULT TRUE)
    RETURN VARCHAR2;

  -- concat an array using a delimiter
  FUNCTION CONCAT_ARRAY(I_NUMARRAY    IN NUMARRAY,
                        I_DELIMITER   IN VARCHAR2,
                        I_NEED_QUOTE  IN BOOLEAN DEFAULT FALSE,
                        I_IGNORE_NULL IN BOOLEAN DEFAULT TRUE)
    RETURN VARCHAR2;

  -- Add an offset to a field of a date.
  -- i_field: SS, DD, MM, YY
  FUNCTION ADD(I_DATE   IN DATE,
               I_FIELD  IN VARCHAR2 DEFAULT 'DD',
               I_OFFSET IN NUMBER DEFAULT 0) RETURN DATE;

  -- Get the zone a warehouse resides.
  FUNCTION GET_WAREHOUSE_TZ(I_WAREHOUSE IN VARCHAR2) RETURN VARCHAR2;

  -- Convert a datetime at a zone to a datetime at the zone a warehouse resides.
  -- i_zone default HongKong Time Zone 'Asia/Shanghai'.
  FUNCTION TO_WAREHOUSE_TZ(I_DATE      IN DATE,
                           I_WAREHOUSE IN VARCHAR2,
                           I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE;

  -- Convert a datetime at the zone a warehouse resides to a datetime at a zone
  -- i_zone default HongKong Time Zone '+08:00'.
  FUNCTION FROM_WAREHOUSE_TZ(I_DATE      IN DATE,
                             I_WAREHOUSE IN VARCHAR2,
                             I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE;

  -- Convert a datetime at a zone to a datetime at the zone a warehouse resides, then apply trunc(),
  -- and reconvert the result datetime at the zone a warehouse resides to a datetime at the original zone.
  -- i_fmt default 'DD'
  -- i_zone default HongKong Time Zone '+08:00'.
  FUNCTION TRUNC_WAREHOUSE_TZ(I_DATE      IN DATE,
                              I_WAREHOUSE IN VARCHAR2,
                              I_FMT       IN VARCHAR2 DEFAULT 'DD',
                              I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE;

  -- Convert a datetime at a zone to a datetime at the zone a warehouse resides, then apply round(),
  -- and reconvert the result datetime at the zone a warehouse resides to a datetime at the original zone.
  -- i_fmt default 'DD'
  -- i_zone default HongKong Time Zone '+08:00'.
  FUNCTION ROUND_WAREHOUSE_TZ(I_DATE      IN DATE,
                              I_WAREHOUSE IN VARCHAR2,
                              I_FMT       IN VARCHAR2 DEFAULT 'DD',
                              I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE;

  -- Convert a datetime at a zone to a datetime at the zone a warehouse resides, then apply trunc(),
  -- and reconvert the result datetime at the zone a warehouse resides to a datetime at the original zone.
  -- i_fmt default 'DD'
  -- i_zone default HongKong Time Zone '+08:00'.
  FUNCTION TRUNC_WAREHOUSE_TZ_OFFSET(I_DATE      IN DATE,
                                     I_WAREHOUSE IN VARCHAR2,
                                     I_FIELD_B   IN VARCHAR2 DEFAULT 'DD',
                                     I_OFFSET_B  IN NUMBER DEFAULT 0,
                                     I_FIELD_A   IN VARCHAR2 DEFAULT 'DD',
                                     I_OFFSET_A  IN NUMBER DEFAULT 0,
                                     I_FMT       IN VARCHAR2 DEFAULT 'DD',
                                     I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE;

  -- Get the first day of the week with the index.
  FUNCTION GET_FIRST_DAY_OF_WEEK(I_DATE  IN DATE,
                                 I_INDEX IN NUMBER DEFAULT 0) RETURN DATE;

  -- Get the first day of the month with the index.
  FUNCTION GET_FIRST_DAY_OF_MONTH(I_DATE  IN DATE,
                                  I_INDEX IN NUMBER DEFAULT 0) RETURN DATE;

  -- Get the first day of the year with the index.
  FUNCTION GET_FIRST_DAY_OF_YEAR(I_DATE  IN DATE,
                                 I_INDEX IN NUMBER DEFAULT 0) RETURN DATE;

  -- Get the first day of the week with the index at the zone a warehouse resides.
  -- return the time at i_zone.
  FUNCTION GET_FIRST_DAY_OF_WEEK(I_DATE      IN DATE,
                                 I_WAREHOUSE IN VARCHAR2,
                                 I_INDEX     IN NUMBER DEFAULT 0,
                                 I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE;

  -- Get the first day of the month with the index at the zone a warehouse resides.
  -- return the time at i_zone.
  FUNCTION GET_FIRST_DAY_OF_MONTH(I_DATE      IN DATE,
                                  I_WAREHOUSE IN VARCHAR2,
                                  I_INDEX     IN NUMBER DEFAULT 0,
                                  I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE;

  -- Get the first day of the year with the index at the zone a warehouse resides.
  -- return the time at i_zone.
  FUNCTION GET_FIRST_DAY_OF_YEAR(I_DATE      IN DATE,
                                 I_WAREHOUSE IN VARCHAR2,
                                 I_INDEX     IN NUMBER DEFAULT 0,
                                 I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE;

  -- Put a piece of information in the buffer as multiple lines.
  -- i_line_length should range from 0 to 255 and default to 255.
  PROCEDURE PUT_LINES(I_STR VARCHAR2, I_LINE_LENGTH NUMBER DEFAULT 255);
  FUNCTION ISEMPTY(I_NUMARRAY IN NUMARRAY) RETURN BOOLEAN;
  FUNCTION ISEMPTY(I_STRARRAY IN STRARRAY) RETURN BOOLEAN;
  FUNCTION ISEMPTY(I_ALLOCATION_ZONE_TAB IN ALLOCATION_ZONE_TAB)
    RETURN BOOLEAN;

  -- 1-TRUE, 0-FALSE
  FUNCTION EQUALSTRIMTO(I_1 IN VARCHAR2, I_2 IN VARCHAR2) RETURN NUMBER;

  FUNCTION EQUALSTO(I_1 IN VARCHAR2, I_2 IN VARCHAR2) RETURN NUMBER;

  FUNCTION EQUALSTO(I_1 IN NUMBER, I_2 IN NUMBER) RETURN NUMBER;

  FUNCTION EQUALSTO(I_1 IN DATE, I_2 IN DATE) RETURN NUMBER;

  -- get new sequence no from sequence table for ALL
  -- after parse
  -- if error, return null
  FUNCTION GET_NEW_SEQUENCE_NO(I_SEQENCE_NAME IN SEQUENCE_TABLE.SEQUENCE_NAME%TYPE,
                               I_CACHE_SIZE   IN INT DEFAULT 50)
    RETURN SEQUENCE_TABLE.SEQUENCE_NUMBER%TYPE;

  FUNCTION GET_TEMPLATE_SP_CODE_OID(WAREHOUSE_ID     IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE,
                                    OWNER_ID         IN CM_COMPANY.COMPANY_ID%TYPE,
                                    SP_ACTUAL_CODE   IN SP_CODE.CODE%TYPE,
                                    SP_CATEGORY_NAME IN SP_CODE.CATEGORY_NAME%TYPE)
    RETURN SP_CODE.OID%TYPE;

  FUNCTION CHECK_SUP_TEMPLATE(I_WAREHOUSE_ID   IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE,
                              I_OWNER_ID       IN CM_COMPANY.COMPANY_ID%TYPE,
                              SP_ACTUAL_CODE   IN SP_CODE.CODE%TYPE,
                              SP_CATEGORY_NAME IN SP_CODE.CATEGORY_NAME%TYPE,
                              SP_CODE_OID      OUT SP_CODE.OID%TYPE)
    RETURN BOOLEAN;
  /*
  parse new sequence no from sequence to  sequence+suffix
  suffix : db property
  */
  FUNCTION PARSE_NEW_SEQUENCE_NO(I_ORIGINAL_OID IN SEQUENCE_TABLE.SEQUENCE_NUMBER%TYPE)
    RETURN SEQUENCE_TABLE.SEQUENCE_NUMBER%TYPE;
  -- judge if a string can be pared to number
  FUNCTION IS_NUMBER(I_STR IN VARCHAR2) RETURN BOOLEAN;
  FUNCTION IS_NUMBER2(I_STR IN VARCHAR2) RETURN NUMBER;

  -- log info about store procedure into WOS_STORE_PROCEDURE_LOG
  -- if current_env = DEV, thrown
  --   ORA-20101: Exception thrown for development
  PROCEDURE LOGSTOREDPROCEDURE(I_MODULE_NAME          IN VARCHAR2,
                               I_STORE_PROCEDURE_NAME IN VARCHAR2,
                               I_FUNCTION_NAME        IN VARCHAR2,
                               I_SUB_FUNCTION_NAME    IN VARCHAR2,
                               I_MAIN_REF_NO          IN VARCHAR2,
                               I_SUB_REF_NO           IN VARCHAR2,
                               I_SUB_CHILD_REF_NO     IN VARCHAR2,
                               I_TASK                 IN VARCHAR2,
                               I_TASK_DESCRIPTION     IN VARCHAR2,
                               I_STATUS               IN VARCHAR2,
                               I_ERRCODE              IN VARCHAR2 DEFAULT NULL,
                               I_ERRMSG               IN VARCHAR2 DEFAULT NULL);

  FUNCTION GET_SP_OID(I_TABLE_NAME IN SP_CATEGORY.NAME%TYPE,
                      I_CODE       IN SP_CODE.CODE%TYPE)
    RETURN SP_CODE.OID%TYPE /*RESULT_CACHE*/
  ;

  FUNCTION GET_SP_CODE(I_OID IN SP_CODE.OID%TYPE, I_CUT IN NUMBER)
    RETURN SP_CODE.CODE%TYPE /*RESULT_CACHE*/
  ;

  FUNCTION GET_GEO_OID(I_CODE IN SP_GEO_PORT.LOC_CODE%TYPE)
    RETURN SP_GEO_PORT.OID%TYPE /*RESULT_CACHE*/
  ;

  FUNCTION GET_GEO_CODE(I_OID IN SP_GEO_PORT.OID%TYPE)
    RETURN SP_GEO_PORT.LOC_CODE%TYPE /*RESULT_CACHE*/
  ;

  FUNCTION GET_GEO_NAME(I_OID IN SP_GEO_PORT.OID%TYPE)
    RETURN SP_GEO_PORT.LOC_NAME%TYPE /*RESULT_CACHE*/
  ;

  FUNCTION GET_PCS_UOM(I_ITEM_OID IN CM_ITEM.OID%TYPE)
    RETURN CM_UNIT_OF_MEASURE.UOM%TYPE;

  -- added by yiwo for interface.
  FUNCTION GET_SUB_STRING(I_OLD_STRING VARCHAR2,
                          I_SEP_STRING VARCHAR2,
                          I_NUM        NUMBER) RETURN VARCHAR2;

  FUNCTION CONNECT_SEAL_NO(I_SEAL_NO  IN IF_IN_SP_CONTAINER.SEAL_NO%TYPE,
                           I_SEAL_NO1 IN IF_IN_SP_CONTAINER.SEAL_NO1%TYPE,
                           I_SEAL_NO2 IN IF_IN_SP_CONTAINER.SEAL_NO2%TYPE,
                           I_SEAL_NO3 IN IF_IN_SP_CONTAINER.SEAL_NO3%TYPE,
                           I_SEAL_NO4 IN IF_IN_SP_CONTAINER.SEAL_NO4%TYPE)
    RETURN VARCHAR2;
  PROCEDURE SEND_MAIL(SENDER      IN VARCHAR2,
                      RECIPIENT   IN VARCHAR2,
                      CCRECIPIENT IN VARCHAR2,
                      SUBJECT     IN VARCHAR2,
                      MESSAGE     IN VARCHAR2,
                      SMTP        IN VARCHAR2,
                      PORT        IN VARCHAR2);
  FUNCTION GET_PARAM(I_MODULE             IN VARCHAR2,
                     I_CONFIGURATION_NAME IN VARCHAR2) RETURN VARCHAR2 /*RESULT_CACHE*/
  ;

  FUNCTION IS_COMMON_DB RETURN BOOLEAN;

  FUNCTION IS_BIZ_DB RETURN BOOLEAN;

  /*Find rule value in warehouse or warehouse+company level by rule code
   i_rule_level
             not null, among
               RULE_SYSTEM_LEVEL, RULE_WAREHOUSE_LEVEL and RULE_WAREHOUSE_OWNER_LEVEL
   i_rule_code
             rule code to match
  return rule_value_t_tab*/
  FUNCTION GET_VALUE_BY_RULE_CODE(I_RULE_LEVEL   IN VARCHAR2,
                                  I_RULE_CODE    IN CM_NEW_RULE.RULE_CODE%TYPE DEFAULT NULL,
                                  I_WAREHOUSE_ID IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE DEFAULT NULL,
                                  I_OWNER_ID     IN CM_COMPANY.COMPANY_ID%TYPE DEFAULT NULL)
    RETURN RULE_VALUE_T_TAB;

  /*Find rule value in warehouse or warehouse+company level by rule codes
   i_rule_level
             not null, among
               RULE_SYSTEM_LEVEL, RULE_WAREHOUSE_LEVEL and RULE_WAREHOUSE_OWNER_LEVEL
   i_rule_codes
             rule codes to match
  return rule_value_t_tab*/
  FUNCTION GET_RULE_VALUE_BY_RULE_CODE(I_RULE_LEVEL    IN VARCHAR2,
                                       I_RULE_CODES    IN STRARRAY DEFAULT NULL,
                                       I_WAREHOUSE_IDS IN STRARRAY DEFAULT NULL,
                                       I_OWNER_IDS     IN STRARRAY DEFAULT NULL)
    RETURN RULE_VALUE_T_TAB;

  /*Find rule value in system, warehouse or warehouse+company level by parent code
   i_rule_level
             not null, among
               RULE_SYSTEM_LEVEL, RULE_WAREHOUSE_LEVEL and RULE_WAREHOUSE_OWNER_LEVEL
   i_parent_code
             parent code to match
  return rule_value_t_tab*/
  FUNCTION GET_VALUE_BY_PARENT_CODE(I_RULE_LEVEL   IN VARCHAR2,
                                    I_PARENT_CODE  IN CM_NEW_RULE.RULE_CODE%TYPE DEFAULT NULL,
                                    I_WAREHOUSE_ID IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE DEFAULT NULL,
                                    I_OWNER_ID     IN CM_COMPANY.COMPANY_ID%TYPE DEFAULT NULL)
    RETURN RULE_VALUE_T_TAB;

  /*Find rule value in system, warehouse or warehouse+company level by parent codes
   i_rule_level
             not null, among
               RULE_SYSTEM_LEVEL, RULE_WAREHOUSE_LEVEL and RULE_WAREHOUSE_OWNER_LEVEL
   i_parent_codes
             parent codes to match
  return rule_value_t_tab*/
  FUNCTION GET_RULE_VALUE_BY_PARENT_CODE(I_RULE_LEVEL    IN VARCHAR2,
                                         I_PARENT_CODES  IN STRARRAY DEFAULT NULL,
                                         I_WAREHOUSE_IDS IN STRARRAY DEFAULT NULL,
                                         I_OWNER_IDS     IN STRARRAY DEFAULT NULL)
    RETURN RULE_VALUE_T_TAB;

  -- make sure there is no same <warehouse,owner> in result
  FUNCTION GET_CONFIGRATION_4_WH_OWNER(I_WAREHOUSE_ID IN VARCHAR2,
                                       I_OWNER_ID     IN VARCHAR2,
                                       I_RULE_VALUES  IN RULE_VALUE_T_TAB)
    RETURN RULE_VALUE_T_TAB;

  -- make sure there is no same <warehouse> in result
  FUNCTION GET_CONFIGRATION_4_WH(I_WAREHOUSE_ID IN VARCHAR2,
                                 I_RULE_VALUES  IN RULE_VALUE_T_TAB)
    RETURN RULE_VALUE_T_TAB;

  /*
  This function assume there is only one value at most, so
  it is recommended that i_abc_rule_values is the result of get_configration_4_wh_owner
  */
  FUNCTION GET_VALUE_OF_RULE(I_RULE_CODE   IN VARCHAR2,
                             I_RULE_VALUES IN RULE_VALUE_T_TAB)
    RETURN VARCHAR2;

  /*
  This function can handle multi values of the rule, generally,
  it is used to filter values of different <wh,owner>
  */
  FUNCTION GET_VALUES_OF_RULE(I_RULE_CODE   IN VARCHAR2,
                              I_RULE_VALUES IN RULE_VALUE_T_TAB)
    RETURN RULE_VALUE_T_TAB;

  /*
  Like split in Java, no regular expression supported.
  */
  FUNCTION STRING_SPLIT(P_STR         IN VARCHAR2,
                        P_DELIMITER   IN VARCHAR2,
                        P_IGNORE_NULL IN BOOLEAN DEFAULT FALSE)
    RETURN STRARRAY;

  /*dont consider operator is NONE*/
  FUNCTION GET_COMMON_FILTER_EXP(I_COL_EXP    IN VARCHAR2,
                                 I_OPERATOR   IN VARCHAR2,
                                 I_VALUE      IN VARCHAR2,
                                 I_VALUE_TYPE IN VARCHAR2) RETURN VARCHAR2;

  -- get first element of a collection if it's not empty, otherwise return null
  FUNCTION GETSTRARRAYFIRST(I_STRARRAY IN STRARRAY) RETURN VARCHAR;

  -- get first element of a collection if it's not empty, otherwise return null
  FUNCTION GETNUMARRAYFIRST(I_NUMARRAY IN NUMARRAY) RETURN NUMBER;

  -- get first element of a collection if it's not empty, otherwise return null
  FUNCTION GETDATEARRAYFIRST(I_DATEARRAY IN DATEARRAY) RETURN DATE;

  -- tick the start of an operation
  PROCEDURE TICK_OPERATION_HISTORY(I_OPERATION_TYPE IN RP_OPERATION_HISTORY.OPERATION_TYPE%TYPE,
                                   I_OPERATION_KEY  IN RP_OPERATION_HISTORY.OPERATION_KEY%TYPE,
                                   I_USER_ID        IN CM_USER.USER_ID%TYPE,
                                   I_REF_NO         IN RP_OPERATION_HISTORY.REF_NO%TYPE DEFAULT NULL);

  -- tock the start of an operation
  PROCEDURE TOCK_OPERATION_HISTORY(I_OPERATION_TYPE IN RP_OPERATION_HISTORY.OPERATION_TYPE%TYPE,
                                   I_OPERATION_KEY  IN RP_OPERATION_HISTORY.OPERATION_KEY%TYPE,
                                   I_USER_ID        IN CM_USER.USER_ID%TYPE,
                                   I_REF_NO         IN RP_OPERATION_HISTORY.REF_NO%TYPE DEFAULT NULL);

  -- tick and tock the start of an operation at the same time
  PROCEDURE TICK_TOCK_OPERATION_HISTORY(I_OPERATION_TYPE IN RP_OPERATION_HISTORY.OPERATION_TYPE%TYPE,
                                        I_OPERATION_KEY  IN RP_OPERATION_HISTORY.OPERATION_KEY%TYPE,
                                        I_USER_ID        IN CM_USER.USER_ID%TYPE,
                                        I_REF_NO         IN RP_OPERATION_HISTORY.REF_NO%TYPE DEFAULT NULL);

  FUNCTION SUBARRAY(I_SRC_STRARRAY IN STRARRAY,
                    I_FIRST        IN NUMBER,
                    I_LAST         IN NUMBER) RETURN STRARRAY;

  FUNCTION SUBARRAY(I_SRC_STRARRAY IN NUMARRAY,
                    I_FIRST        IN NUMBER,
                    I_LAST         IN NUMBER) RETURN NUMARRAY;

  PROCEDURE LOG_ACTION_LOG(I_WAREHOUSE_ID IN VARCHAR2,
                           I_ORDER_OID    IN NUMBER,
                           I_TYPE         IN VARCHAR2,
                           I_OPERATION    IN VARCHAR2,
                           I_OPERATOR     IN VARCHAR2);

END WOS_COMMON_PKG;
/
CREATE OR REPLACE PACKAGE BODY WOS_COMMON_PKG IS
  -- Private type declarations
  TYPE SEQUENCE_CACHE_TAB IS TABLE OF SEQUENCE_TYP INDEX BY VARCHAR2(300);
  -- sequence cahce for this session
  SEQUENCE_CACHE SEQUENCE_CACHE_TAB;
  G_CURRENT_ENV  WOS_CONFIGURATION.CONFIGURATION_VALUE%TYPE;

  -- add a prefix to a not null string
  FUNCTION ADD_PREFIX(I_STR         IN VARCHAR2,
                      I_PREFIX      IN VARCHAR2,
                      I_IGNORE_NULL IN BOOLEAN DEFAULT TRUE) RETURN VARCHAR2 IS
    L_STR VARCHAR2(4000);
  BEGIN
    IF I_IGNORE_NULL THEN
      IF I_STR IS NOT NULL THEN
        L_STR := I_PREFIX || I_STR;
      END IF;
    ELSE
      L_STR := I_PREFIX || I_STR;
    END IF;
    RETURN L_STR;
  END ADD_PREFIX;

  -- Filter the special characters in a string for the normalized sql.
  -- i_string: a string for sql concatenation
  FUNCTION STRING_FILTER(I_STRING IN VARCHAR2) RETURN VARCHAR2 IS
  BEGIN
    RETURN REPLACE(I_STRING, '''', '''''');
  END STRING_FILTER;

  -- concat an array using a delimiter
  FUNCTION CONCAT_ARRAY(I_STRARRAY    IN STRARRAY,
                        I_DELIMITER   IN VARCHAR2,
                        I_NEED_QUOTE  IN BOOLEAN DEFAULT FALSE,
                        I_IGNORE_NULL IN BOOLEAN DEFAULT TRUE)
    RETURN VARCHAR2 IS
    L_STR     VARCHAR2(4000);
    L_ELEMENT VARCHAR2(4000);
  BEGIN
    IF I_STRARRAY IS NULL OR I_STRARRAY.COUNT = 0 THEN
      RETURN '';
    END IF;
    FOR I IN I_STRARRAY.FIRST .. I_STRARRAY.LAST LOOP
      L_ELEMENT := I_STRARRAY(I);
      IF I_IGNORE_NULL THEN
        IF L_ELEMENT IS NOT NULL THEN
          IF I_NEED_QUOTE THEN
            L_ELEMENT := '''' || REPLACE(L_ELEMENT, '''', '''''') || '''';
          END IF;
          L_STR := L_STR || I_DELIMITER || L_ELEMENT;
        END IF;
      ELSE
        IF I_NEED_QUOTE THEN
          L_ELEMENT := '''' || REPLACE(L_ELEMENT, '''', '''''') || '''';
        END IF;
        L_STR := L_STR || I_DELIMITER || L_ELEMENT;
      END IF;
    END LOOP;
    IF L_STR IS NOT NULL AND I_DELIMITER IS NOT NULL THEN
      L_STR := SUBSTR(L_STR, LENGTHC(I_DELIMITER) + 1);
    END IF;
    RETURN L_STR;
  END CONCAT_ARRAY;

  -- concat an array using a delimiter
  FUNCTION CONCAT_ARRAY(I_NUMARRAY    IN NUMARRAY,
                        I_DELIMITER   IN VARCHAR2,
                        I_NEED_QUOTE  IN BOOLEAN DEFAULT FALSE,
                        I_IGNORE_NULL IN BOOLEAN DEFAULT TRUE)
    RETURN VARCHAR2 IS
    L_STR     VARCHAR2(4000);
    L_ELEMENT VARCHAR2(4000);
  BEGIN
    IF I_NUMARRAY IS NULL OR I_NUMARRAY.COUNT = 0 THEN
      RETURN '';
    END IF;
    FOR I IN I_NUMARRAY.FIRST .. I_NUMARRAY.LAST LOOP
      L_ELEMENT := I_NUMARRAY(I);
      IF I_IGNORE_NULL THEN
        IF L_ELEMENT IS NOT NULL THEN
          IF I_NEED_QUOTE THEN
            L_ELEMENT := '''' || L_ELEMENT || '''';
          END IF;
          L_STR := L_STR || I_DELIMITER || L_ELEMENT;
        END IF;
      ELSE
        IF I_NEED_QUOTE THEN
          L_ELEMENT := '''' || L_ELEMENT || '''';
        END IF;
        L_STR := L_STR || I_DELIMITER || L_ELEMENT;
      END IF;
    END LOOP;
    IF L_STR IS NOT NULL AND I_DELIMITER IS NOT NULL THEN
      L_STR := SUBSTR(L_STR, LENGTHC(I_DELIMITER) + 1);
    END IF;
    RETURN L_STR;
  END CONCAT_ARRAY;

  -- Add an offset to a field of a date.
  -- i_field: SS, DD, MM, YY
  FUNCTION ADD(I_DATE   IN DATE,
               I_FIELD  IN VARCHAR2 DEFAULT 'DD',
               I_OFFSET IN NUMBER DEFAULT 0) RETURN DATE IS
    O_DATE DATE;
  BEGIN
    O_DATE := I_DATE;
    IF (I_OFFSET IS NULL OR I_OFFSET = 0) THEN
      RETURN(O_DATE);
    END IF;
    CASE
      WHEN I_FIELD = 'SS' THEN
        O_DATE := I_DATE + I_OFFSET / 86400;
      WHEN I_FIELD = 'DD' THEN
        O_DATE := I_DATE + I_OFFSET;
      WHEN I_FIELD = 'MM' THEN
        O_DATE := ADD_MONTHS(I_DATE, I_OFFSET);
      WHEN I_FIELD = 'YY' THEN
        O_DATE := ADD_MONTHS(I_DATE, I_OFFSET * 12);
    END CASE;
    RETURN(O_DATE);
  END ADD;

  -- Get the zone a warehouse resides.
  FUNCTION GET_WAREHOUSE_TZ(I_WAREHOUSE IN VARCHAR2) RETURN VARCHAR2 IS
    L_WAREHOUSE_TZ CM_WAREHOUSE.DB_TZNAME%TYPE;
  BEGIN
    SELECT DECODE(WH.TZDAYLIGHT, 1, WH.DB_TZNAME, WH.DB_TZOFFSET)
      INTO L_WAREHOUSE_TZ
      FROM CM_WAREHOUSE WH
     WHERE WH.WAREHOUSE_ID = I_WAREHOUSE;
    RETURN L_WAREHOUSE_TZ;
  END GET_WAREHOUSE_TZ;

  -- Convert a datetime at a zone to a datetime at the zone a warehouse resides.
  -- i_zone default HongKong Time Zone '+08:00'.
  FUNCTION TO_WAREHOUSE_TZ(I_DATE      IN DATE,
                           I_WAREHOUSE IN VARCHAR2,
                           I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE IS
    L_WAREHOUSE_TZ CM_WAREHOUSE.DB_TZNAME%TYPE;
  BEGIN
    L_WAREHOUSE_TZ := GET_WAREHOUSE_TZ(I_WAREHOUSE);
    RETURN NEW_DATE(I_DATE, I_ZONE, L_WAREHOUSE_TZ);
  END TO_WAREHOUSE_TZ;

  -- Convert a datetime at the zone a warehouse resides to a datetime at a zone
  -- i_zone default HongKong Time Zone '+08:00'.
  FUNCTION FROM_WAREHOUSE_TZ(I_DATE      IN DATE,
                             I_WAREHOUSE IN VARCHAR2,
                             I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE IS
    L_WAREHOUSE_TZ CM_WAREHOUSE.DB_TZNAME%TYPE;
  BEGIN
    L_WAREHOUSE_TZ := GET_WAREHOUSE_TZ(I_WAREHOUSE);
    RETURN NEW_DATE(I_DATE, L_WAREHOUSE_TZ, I_ZONE);
  END FROM_WAREHOUSE_TZ;

  -- Convert a datetime at a zone to a datetime at the zone a warehouse resides, then apply trunc(),
  -- and reconvert the result datetime at the zone a warehouse resides to a datetime at the original zone.
  -- i_fmt default 'DD'
  -- i_zone default HongKong Time Zone '+08:00'.
  FUNCTION TRUNC_WAREHOUSE_TZ(I_DATE      IN DATE,
                              I_WAREHOUSE IN VARCHAR2,
                              I_FMT       IN VARCHAR2 DEFAULT 'DD',
                              I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE IS
    L_WAREHOUSE_TZ CM_WAREHOUSE.DB_TZNAME%TYPE;
  BEGIN
    L_WAREHOUSE_TZ := GET_WAREHOUSE_TZ(I_WAREHOUSE);
    RETURN NEW_DATE(TRUNC(NEW_DATE(I_DATE, I_ZONE, L_WAREHOUSE_TZ), I_FMT),
                    L_WAREHOUSE_TZ,
                    I_ZONE);
  END TRUNC_WAREHOUSE_TZ;

  -- Convert a datetime at a zone to a datetime at the zone a warehouse resides, then apply round(),
  -- and reconvert the result datetime at the zone a warehouse resides to a datetime at the original zone.
  -- i_fmt default 'DD'
  -- i_zone default HongKong Time Zone '+08:00'.
  FUNCTION ROUND_WAREHOUSE_TZ(I_DATE      IN DATE,
                              I_WAREHOUSE IN VARCHAR2,
                              I_FMT       IN VARCHAR2 DEFAULT 'DD',
                              I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE IS
    L_WAREHOUSE_TZ CM_WAREHOUSE.DB_TZNAME%TYPE;
  BEGIN
    L_WAREHOUSE_TZ := GET_WAREHOUSE_TZ(I_WAREHOUSE);
    RETURN NEW_DATE(ROUND(NEW_DATE(I_DATE, I_ZONE, L_WAREHOUSE_TZ), I_FMT),
                    L_WAREHOUSE_TZ,
                    I_ZONE);
  END ROUND_WAREHOUSE_TZ;

  -- Convert a datetime at a zone to a datetime at the zone a warehouse resides, then apply trunc(),
  -- and reconvert the result datetime at the zone a warehouse resides to a datetime at the original zone.
  -- i_fmt default 'DD'
  -- i_zone default HongKong Time Zone '+08:00'.
  FUNCTION TRUNC_WAREHOUSE_TZ_OFFSET(I_DATE      IN DATE,
                                     I_WAREHOUSE IN VARCHAR2,
                                     I_FIELD_B   IN VARCHAR2 DEFAULT 'DD',
                                     I_OFFSET_B  IN NUMBER DEFAULT 0,
                                     I_FIELD_A   IN VARCHAR2 DEFAULT 'DD',
                                     I_OFFSET_A  IN NUMBER DEFAULT 0,
                                     I_FMT       IN VARCHAR2 DEFAULT 'DD',
                                     I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE IS
    L_WAREHOUSE_TZ CM_WAREHOUSE.DB_TZNAME%TYPE;
  BEGIN
    L_WAREHOUSE_TZ := GET_WAREHOUSE_TZ(I_WAREHOUSE);
    RETURN NEW_DATE(ADD(TRUNC(ADD(NEW_DATE(I_DATE, I_ZONE, L_WAREHOUSE_TZ),
                                  I_FIELD_B,
                                  I_OFFSET_B),
                              I_FMT),
                        I_FIELD_A,
                        I_OFFSET_A),
                    L_WAREHOUSE_TZ,
                    I_ZONE);
  END TRUNC_WAREHOUSE_TZ_OFFSET;

  -- Get the first day of the week with the index.
  FUNCTION GET_FIRST_DAY_OF_WEEK(I_DATE  IN DATE,
                                 I_INDEX IN NUMBER DEFAULT 0) RETURN DATE IS
  BEGIN
    RETURN ADD(TRUNC(ADD(I_DATE, 'DD', -1), 'D'), 'DD', 1 + 7 * I_INDEX);
  END GET_FIRST_DAY_OF_WEEK;

  -- Get the first day of the month with the index.
  FUNCTION GET_FIRST_DAY_OF_MONTH(I_DATE  IN DATE,
                                  I_INDEX IN NUMBER DEFAULT 0) RETURN DATE IS
  BEGIN
    RETURN ADD(TRUNC(ADD(I_DATE), 'MM'), 'MM', I_INDEX);
  END GET_FIRST_DAY_OF_MONTH;

  -- Get the first day of the year with the index.
  FUNCTION GET_FIRST_DAY_OF_YEAR(I_DATE  IN DATE,
                                 I_INDEX IN NUMBER DEFAULT 0) RETURN DATE IS
  BEGIN
    RETURN ADD(TRUNC(ADD(I_DATE), 'YY'), 'MM', 12 * I_INDEX);
  END GET_FIRST_DAY_OF_YEAR;

  -- Get the first day of the week with the index at the zone a warehouse resides.
  -- return the time at i_zone.
  FUNCTION GET_FIRST_DAY_OF_WEEK(I_DATE      IN DATE,
                                 I_WAREHOUSE IN VARCHAR2,
                                 I_INDEX     IN NUMBER DEFAULT 0,
                                 I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE IS
  BEGIN
    RETURN TRUNC_WAREHOUSE_TZ_OFFSET(I_DATE,
                                     I_WAREHOUSE,
                                     'DD',
                                     -1,
                                     'DD',
                                     1 + 7 * I_INDEX,
                                     'D',
                                     I_ZONE);
  END GET_FIRST_DAY_OF_WEEK;

  -- Get the first day of the month with the index at the zone a warehouse resides.
  -- return the time at i_zone.
  FUNCTION GET_FIRST_DAY_OF_MONTH(I_DATE      IN DATE,
                                  I_WAREHOUSE IN VARCHAR2,
                                  I_INDEX     IN NUMBER DEFAULT 0,
                                  I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE IS
  BEGIN
    RETURN TRUNC_WAREHOUSE_TZ_OFFSET(I_DATE,
                                     I_WAREHOUSE,
                                     NULL,
                                     0,
                                     'MM',
                                     I_INDEX,
                                     'MM',
                                     I_ZONE);
  END GET_FIRST_DAY_OF_MONTH;

  -- Get the first day of the year with the index at the zone a warehouse resides.
  -- return the time at i_zone.
  FUNCTION GET_FIRST_DAY_OF_YEAR(I_DATE      IN DATE,
                                 I_WAREHOUSE IN VARCHAR2,
                                 I_INDEX     IN NUMBER DEFAULT 0,
                                 I_ZONE      IN VARCHAR2 DEFAULT 'Asia/Shanghai')
    RETURN DATE IS
  BEGIN
    RETURN TRUNC_WAREHOUSE_TZ_OFFSET(I_DATE,
                                     I_WAREHOUSE,
                                     NULL,
                                     0,
                                     'MM',
                                     12 * I_INDEX,
                                     'YY',
                                     I_ZONE);
  END GET_FIRST_DAY_OF_YEAR;

  -- Put a piece of information in the buffer as multiple lines.
  -- i_line_length should range from 0 to 255 and default to 255.
  PROCEDURE PUT_LINES(I_STR VARCHAR2, I_LINE_LENGTH NUMBER DEFAULT 255) IS
    L_POS NUMBER := 1;
  BEGIN
    DBMS_OUTPUT.NEW_LINE;
    WHILE L_POS <= LENGTH(I_STR) LOOP
      DBMS_OUTPUT.PUT_LINE(SUBSTR(I_STR, L_POS, I_LINE_LENGTH));
      L_POS := L_POS + I_LINE_LENGTH;
    END LOOP;
  END PUT_LINES;

  FUNCTION ISEMPTY(I_NUMARRAY IN NUMARRAY) RETURN BOOLEAN IS
  BEGIN
    IF I_NUMARRAY IS NULL OR I_NUMARRAY.COUNT = 0 THEN
      RETURN TRUE;
    ELSE
      if I_NUMARRAY.Count = 1 and (length(trim(I_NUMARRAY(1))) = 0 or
         length(trim(I_NUMARRAY(1))) is null) then
        return true;
      end if;
      RETURN FALSE;
    END IF;
  END ISEMPTY;
  FUNCTION ISEMPTY(I_STRARRAY IN STRARRAY) RETURN BOOLEAN IS
  BEGIN
    IF I_STRARRAY IS NULL OR I_STRARRAY.COUNT = 0 THEN
      RETURN TRUE;
    ELSE
      if I_STRARRAY.Count = 1 and (length(trim(I_STRARRAY(1))) = 0 or
         length(trim(I_STRARRAY(1))) is null) then
        return true;
      end if;
      RETURN FALSE;
    END IF;
  END ISEMPTY;
  --l_allocation_zone_tab        allocation_zone_tab
  FUNCTION ISEMPTY(I_ALLOCATION_ZONE_TAB IN ALLOCATION_ZONE_TAB)
    RETURN BOOLEAN IS
  BEGIN
    IF I_ALLOCATION_ZONE_TAB IS NULL OR I_ALLOCATION_ZONE_TAB.COUNT = 0 THEN
      RETURN TRUE;
    ELSE
      RETURN FALSE;
    END IF;
  END ISEMPTY;

  -- 1-TRUE, 0-FALSE
  FUNCTION EQUALSTRIMTO(I_1 IN VARCHAR2, I_2 IN VARCHAR2) RETURN NUMBER IS
  BEGIN
    IF (((TRIM(I_1) IS NULL) AND (TRIM(I_2) IS NULL)) OR
       (TRIM(I_1) = TRIM(I_2))) THEN
      RETURN 1;
    ELSE
      RETURN 0;
    END IF;
  END EQUALSTRIMTO;

  FUNCTION EQUALSTO(I_1 IN VARCHAR2, I_2 IN VARCHAR2) RETURN NUMBER IS
  BEGIN
    IF ((I_1 IS NULL AND I_2 IS NULL) OR I_1 = I_2) THEN
      RETURN 1;
    ELSE
      RETURN 0;
    END IF;
  END EQUALSTO;

  FUNCTION EQUALSTO(I_1 IN NUMBER, I_2 IN NUMBER) RETURN NUMBER IS
  BEGIN
    IF ((I_1 IS NULL AND I_2 IS NULL) OR I_1 = I_2) THEN
      RETURN 1;
    ELSE
      RETURN 0;
    END IF;
  END EQUALSTO;

  FUNCTION EQUALSTO(I_1 IN DATE, I_2 IN DATE) RETURN NUMBER IS
  BEGIN
    IF ((I_1 IS NULL AND I_2 IS NULL) OR I_1 = I_2) THEN
      RETURN 1;
    ELSE
      RETURN 0;
    END IF;
  END EQUALSTO;

  FUNCTION PARSE_NEW_SEQUENCE_NO(I_ORIGINAL_OID IN SEQUENCE_TABLE.SEQUENCE_NUMBER%TYPE)
    RETURN SEQUENCE_TABLE.SEQUENCE_NUMBER%TYPE IS
    L_DB_PROPERTY WOS_CONFIGURATION.CONFIGURATION_VALUE%TYPE;
    L_FINAL_OID   SEQUENCE_TABLE.SEQUENCE_NUMBER%TYPE;
  BEGIN
    L_DB_PROPERTY := (GET_PARAM(I_MODULE             => 'SYSTEM',
                                I_CONFIGURATION_NAME => 'SHARDING_DB_PROPERTY'));

    IF NOT pkg_db_compatiblity_utils.isEmpty(L_DB_PROPERTY) THEN
      -- l_final_oid := to_number(to_char(i_original_oid) || l_db_property);
      L_FINAL_OID := I_ORIGINAL_OID * 100 + TO_NUMBER(L_DB_PROPERTY);
    ELSE
      L_FINAL_OID := I_ORIGINAL_OID;
    END IF;

    RETURN L_FINAL_OID;

  END PARSE_NEW_SEQUENCE_NO;

  -- get new sequence no from sequence table for ALL
  -- if error, return null
  FUNCTION GET_NEW_SEQUENCE_NO(I_SEQENCE_NAME IN SEQUENCE_TABLE.SEQUENCE_NAME%TYPE,
                               I_CACHE_SIZE   IN INT DEFAULT 50)
    RETURN SEQUENCE_TABLE.SEQUENCE_NUMBER%TYPE IS
    PRAGMA AUTONOMOUS_TRANSACTION;
    L_SEQUENCE_NO  SEQUENCE_TABLE.SEQUENCE_NUMBER%TYPE;
    L_SEQUENCE_TYP SEQUENCE_TYP;
  BEGIN
    IF I_CACHE_SIZE IS NULL OR I_CACHE_SIZE <= 0 THEN
      RETURN NULL;
    END IF;

    IF NOT SEQUENCE_CACHE.EXISTS(I_SEQENCE_NAME) THEN
      if PKG_DB_COMPATIBLITY_UTILS.is_edb_database() = 0 then
        SEQUENCE_CACHE(I_SEQENCE_NAME) := SEQUENCE_TYP();
      else
        SEQUENCE_CACHE(I_SEQENCE_NAME) := L_SEQUENCE_TYP;
      end if;
    END IF;
    L_SEQUENCE_TYP := SEQUENCE_CACHE(I_SEQENCE_NAME);
    IF L_SEQUENCE_TYP IS NULL OR
       L_SEQUENCE_TYP.LAST_NUMBER >= L_SEQUENCE_TYP.MAX_NUMBER THEN
      if PKG_DB_COMPATIBLITY_UTILS.is_edb_database() = 0 then
        L_SEQUENCE_TYP := SEQUENCE_TYP();
      end if;
      SEQUENCE_CACHE(I_SEQENCE_NAME) := L_SEQUENCE_TYP;
      UPDATE SEQUENCE_TABLE T
         SET T.SEQUENCE_NUMBER = T.SEQUENCE_NUMBER + I_CACHE_SIZE
       WHERE T.SEQUENCE_NAME = UPPER(I_SEQENCE_NAME)
      RETURNING T.SEQUENCE_NUMBER - I_CACHE_SIZE, T.SEQUENCE_NUMBER INTO L_SEQUENCE_TYP.LAST_NUMBER, L_SEQUENCE_TYP.MAX_NUMBER;
      COMMIT;
    END IF;
    L_SEQUENCE_TYP.LAST_NUMBER := L_SEQUENCE_TYP.LAST_NUMBER + 1;
    L_SEQUENCE_NO := L_SEQUENCE_TYP.LAST_NUMBER;
    SEQUENCE_CACHE(I_SEQENCE_NAME) := L_SEQUENCE_TYP;
    --
    --L_SEQUENCE_NO := PARSE_NEW_SEQUENCE_NO(L_SEQUENCE_NO);
    RETURN L_SEQUENCE_NO;
  END GET_NEW_SEQUENCE_NO;

  FUNCTION IS_NUMBER(I_STR IN VARCHAR2) RETURN BOOLEAN IS
    NUM NUMBER;
  BEGIN
    SELECT TO_NUMBER(I_STR) INTO NUM FROM DUAL;
    RETURN TRUE;
  EXCEPTION
    WHEN INVALID_NUMBER THEN
      RETURN FALSE;
  END IS_NUMBER;

  FUNCTION IS_NUMBER2(I_STR IN VARCHAR2) RETURN NUMBER IS
    NUM NUMBER;
  BEGIN
    SELECT TO_NUMBER(I_STR) INTO NUM FROM DUAL;
    RETURN 1;
  EXCEPTION
    WHEN INVALID_NUMBER THEN
      RETURN 0;
  END IS_NUMBER2;

  PROCEDURE LOGSTOREDPROCEDURE(I_MODULE_NAME          IN VARCHAR2,
                               I_STORE_PROCEDURE_NAME IN VARCHAR2,
                               I_FUNCTION_NAME        IN VARCHAR2,
                               I_SUB_FUNCTION_NAME    IN VARCHAR2,
                               I_MAIN_REF_NO          IN VARCHAR2,
                               I_SUB_REF_NO           IN VARCHAR2,
                               I_SUB_CHILD_REF_NO     IN VARCHAR2,
                               I_TASK                 IN VARCHAR2,
                               I_TASK_DESCRIPTION     IN VARCHAR2,
                               I_STATUS               IN VARCHAR2,
                               I_ERRCODE              IN VARCHAR2 DEFAULT NULL,
                               I_ERRMSG               IN VARCHAR2 DEFAULT NULL) IS
    PRAGMA AUTONOMOUS_TRANSACTION;
    ERRCODE VARCHAR2(20);
    ERRMSG  VARCHAR2(150);
  BEGIN
    ERRCODE := SUBSTRB(NVL(I_ERRCODE, TO_CHAR(SQLCODE)), 1, 20);
    ERRMSG  := SUBSTRB(NVL(I_ERRMSG, SQLERRM), 1, 150);

    INSERT INTO WOS_STORE_PROCEDURE_LOG
      (MODULE_NAME,
       STORE_PROCEDURE_NAME,
       FUNCTION_NAME,
       SUB_FUNCTION_NAME,
       MAIN_REF_NO,
       SUB_REF_NO,
       SUB_CHILED_REF_NO,
       TASK,
       TASK_DESCRIPTION,
       STATUS,
       ERR_CODE,
       ERR_MSG,
       CRTDATE,
       ERROR_STACK)
    VALUES
      (SUBSTRB(I_MODULE_NAME, 1, 50),
       SUBSTRB(I_STORE_PROCEDURE_NAME, 1, 50),
       SUBSTRB(I_FUNCTION_NAME, 1, 50),
       SUBSTRB(I_SUB_FUNCTION_NAME, 1, 50),
       SUBSTRB(I_MAIN_REF_NO, 1, 50),
       SUBSTRB(I_SUB_REF_NO, 1, 50),
       SUBSTRB(I_SUB_CHILD_REF_NO, 1, 50),
       SUBSTRB(I_TASK, 1, 30),
       SUBSTRB(I_TASK_DESCRIPTION, 1, 2500),
       SUBSTRB(I_STATUS, 1, 10),
       ERRCODE, --substrb(i_err_code, 1, 20),
       ERRMSG, --substrb(i_err_msg, 1, 150),
       SYSDATE,
       SUBSTRB(PKG_DB_COMPATIBLITY_UTILS.db_error_msg(), 1, 4000));
    COMMIT;

    IF SQLCODE <> 0 THEN
      IF G_CURRENT_ENV IS NULL THEN
        BEGIN
          SELECT T.CONFIGURATION_VALUE
            INTO G_CURRENT_ENV
            FROM WOS_CONFIGURATION T
           WHERE T.MODULE = MODULE_COMMON
             AND T.CONFIGURATION_NAME = CONFIGURATION_CURRENT_ENV
             AND ROWNUM = 1;
        EXCEPTION
          WHEN OTHERS THEN
            G_CURRENT_ENV := NULL;
        END;
      END IF;
      IF G_CURRENT_ENV = CURRENT_ENV_DEV THEN
        RAISE_APPLICATION_ERROR(-20101,
                                'Exception thrown for development',
                                TRUE);
      END IF;
    END IF;
  END LOGSTOREDPROCEDURE;

  PROCEDURE LOG_ACTION_LOG(I_WAREHOUSE_ID IN VARCHAR2,
                           I_ORDER_OID    IN NUMBER,
                           I_TYPE         IN VARCHAR2,
                           I_OPERATION    IN VARCHAR2,
                           I_OPERATOR     IN VARCHAR2) IS
    PRAGMA AUTONOMOUS_TRANSACTION;
    L_CNT     NUMBER(15) DEFAULT 0;
    L_OID     NUMBER(15);
    L_SUM_OID NUMBER(15);
  BEGIN

    SELECT COUNT(1)
      INTO L_CNT
      FROM CM_FUNCTION_WAREHOUSE CFW
     WHERE CFW.WAREHOUSE_ID = I_WAREHOUSE_ID;
    IF L_CNT = 0 THEN
      RETURN;
    END IF;

    SELECT WOS_COMMON_PKG.GET_NEW_SEQUENCE_NO('AL_ACTIONLOG_SUM_OID')
      INTO L_SUM_OID
      FROM DUAL;
    INSERT INTO AL_ACTIONLOG_SUM
      (OID, REASON, UPDATE_TIME, UPDATE_BY)
    VALUES
      (L_SUM_OID, NULL, SYSDATE, I_OPERATOR);

    SELECT WOS_COMMON_PKG.GET_NEW_SEQUENCE_NO('AL_ACTIONLOG_OID')
      INTO L_OID
      FROM DUAL;
    INSERT INTO AL_ACTIONLOG
      (OID, BIZ_KEY, TYPE, ACTION, SUMMARY_OID)
    VALUES
      (L_OID, '' || I_ORDER_OID, I_TYPE, 'Add', L_SUM_OID);

    INSERT INTO AL_ACTIONLOG_DETAIL
      (OID, ACTIONLOG_OID, VALUE_TO, FIELD_NAME, DISP_SQUENCE_NUM)
      SELECT WOS_COMMON_PKG.GET_NEW_SEQUENCE_NO('AL_ACTIONLOG_DETAIL_OID'),
             L_OID,
             I_OPERATION,
             'SP_LOG_REMARK',
             0
        FROM DUAL;
    COMMIT;

  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      WOS_COMMON_PKG.LOGSTOREDPROCEDURE('Record action log',
                                        'wos_common_pkg',
                                        'LOG_ACTION_LOG',
                                        NULL,
                                        NULL,
                                        NULL,
                                        I_ORDER_OID,
                                        'LOG_ACTION_LOG Exception',
                                        I_OPERATION,
                                        'FAIL');

  END LOG_ACTION_LOG;

  -- %Author xiaovi
  -- %usage get oid for matched support table item, use like get_sp_oid('UOM','UOM_CTN')
  -- %param i_table_name NAME in SP_CATEGORY
  -- %param i_code CODE in SP_CODE
  -- %return SP_CODE.OID
  FUNCTION GET_SP_OID(I_TABLE_NAME IN SP_CATEGORY.NAME%TYPE,
                      I_CODE       IN SP_CODE.CODE%TYPE)
    RETURN SP_CODE.OID%TYPE /*RESULT_CACHE*/
   IS
    L_OID SP_CODE.OID%TYPE;
  BEGIN
    IF I_CODE IS NULL THEN
      RETURN NULL;
    END IF;
    SELECT SSD.OID
      INTO L_OID
      FROM SP_CATEGORY SS, SP_CODE SSD
     WHERE SS.NAME = I_TABLE_NAME
       AND SS.NAME = SSD.CATEGORY_NAME
       AND SSD.CODE = I_CODE;
    RETURN L_OID;
  EXCEPTION
    WHEN OTHERS THEN
      RETURN NULL;
  END GET_SP_OID;

  -- %Author xiaovi
  -- %usage get the actual_code for input oid from support table
  -- %param i_oid the oid in SP_CODE
  -- %param i_cut trim how many letters from begining
  -- %return actual_code
  FUNCTION GET_SP_CODE(I_OID IN SP_CODE.OID%TYPE, I_CUT IN NUMBER)
    RETURN SP_CODE.CODE%TYPE /*RESULT_CACHE*/
   IS
    L_CODE SP_CODE.CODE%TYPE;
  BEGIN
    IF I_OID IS NULL THEN
      RETURN NULL;
    END IF;
    SELECT SSD.CODE
      INTO L_CODE
      FROM SP_CATEGORY SS, SP_CODE SSD
     WHERE SS.NAME = SSD.CATEGORY_NAME
       AND SSD.OID = I_OID;
    RETURN SUBSTR(L_CODE, I_CUT + 1);
  EXCEPTION
    WHEN OTHERS THEN
      RETURN NULL;
  END GET_SP_CODE;

  -- %Author xiaovi
  -- %usage get the oid for input Location, search in Port first, if not found search in Location
  -- %param i_code three letters loc_code, such like 'HKG'
  -- %return the oid in sp_geo_port or sp_geo_location
  FUNCTION GET_GEO_OID(I_CODE IN SP_GEO_PORT.LOC_CODE%TYPE)
    RETURN SP_GEO_PORT.OID%TYPE /*RESULT_CACHE*/
   IS
    L_OID SP_GEO_PORT.OID%TYPE;
  BEGIN
    IF I_CODE IS NULL THEN
      RETURN NULL;
    END IF;
    BEGIN
      SELECT SGP.OID
        INTO L_OID
        FROM SP_GEO_PORT SGP
       WHERE SGP.LOC_CODE = I_CODE;
      RETURN L_OID;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        NULL;
    END;
    BEGIN
      SELECT SGL.OID
        INTO L_OID
        FROM SP_GEO_LOCATION SGL
       WHERE SGL.LOC_CODE = I_CODE;
      RETURN L_OID;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        NULL;
    END;
    RETURN NULL;
  END GET_GEO_OID;

  FUNCTION GET_GEO_NAME(I_OID IN SP_GEO_PORT.OID%TYPE)
    RETURN SP_GEO_PORT.LOC_NAME%TYPE /*RESULT_CACHE*/
   IS
    L_CODE_NAME SP_GEO_PORT.LOC_NAME%TYPE;
  BEGIN
    IF I_OID IS NULL THEN
      RETURN NULL;
    END IF;

    SELECT MAX(SGP.LOC_NAME)
      INTO L_CODE_NAME
      FROM SP_GEO_PORT SGP
     WHERE SGP.OID = I_OID;

    IF L_CODE_NAME IS NULL THEN
      SELECT MAX(SGL.LOC_NAME)
        INTO L_CODE_NAME
        FROM SP_GEO_LOCATION SGL
       WHERE SGL.OID = I_OID;
    END IF;

    RETURN L_CODE_NAME;

  END GET_GEO_NAME;

  -- %Author xiaovi
  -- %usage get loc_code for input oid
  -- %param i_oid oid in sp_geo_port or sp_geo_location
  -- %return loc_code
  FUNCTION GET_GEO_CODE(I_OID IN SP_GEO_PORT.OID%TYPE)
    RETURN SP_GEO_PORT.LOC_CODE%TYPE /*RESULT_CACHE*/
   IS
    L_CODE SP_GEO_PORT.LOC_CODE%TYPE;
  BEGIN
    IF I_OID IS NULL THEN
      RETURN NULL;
    END IF;
    BEGIN
      SELECT SGP.LOC_CODE
        INTO L_CODE
        FROM SP_GEO_PORT SGP
       WHERE SGP.OID = I_OID;
      RETURN L_CODE;
    EXCEPTION
      WHEN OTHERS THEN
        NULL;
    END;
    BEGIN
      SELECT SGL.LOC_CODE
        INTO L_CODE
        FROM SP_GEO_LOCATION SGL
       WHERE SGL.OID = I_OID;
      RETURN L_CODE;
    EXCEPTION
      WHEN OTHERS THEN
        NULL;
    END;
    RETURN NULL;
  END GET_GEO_CODE;

  FUNCTION GET_PCS_UOM(I_ITEM_OID IN CM_ITEM.OID%TYPE)
    RETURN CM_UNIT_OF_MEASURE.UOM%TYPE IS
    L_UOM CM_UNIT_OF_MEASURE.UOM%TYPE;
  BEGIN
    SELECT UOM.UOM
      INTO L_UOM
      FROM CM_UNIT_OF_MEASURE UOM
     WHERE UOM.ITEM_OID = I_ITEM_OID
       AND UOM.UPM = 1
       AND UOM.IS_STANDARD_UOM = 1
       AND UOM.IS_BASEUOM = 1;
    RETURN L_UOM;
  END;

  FUNCTION GET_SUB_STRING(I_OLD_STRING VARCHAR2,
                          I_SEP_STRING VARCHAR2,
                          I_NUM        NUMBER) RETURN VARCHAR2 IS
    L_N              NUMBER;
    L_COUNT          NUMBER;
    L_SEP_LOC_FROM   NUMBER;
    L_SEP_LOC_TO     NUMBER;
    L_OLD_STRING     VARCHAR2(450);
    L_OLD_STRING_TMP VARCHAR2(450);
    L_RESULT         VARCHAR2(450);
  BEGIN
    IF I_NUM >= 1 THEN
      L_N              := 0;
      L_COUNT          := 0;
      L_SEP_LOC_FROM   := 1;
      L_SEP_LOC_TO     := 1;
      L_OLD_STRING     := I_OLD_STRING || I_SEP_STRING;
      L_OLD_STRING_TMP := L_OLD_STRING;
      LOOP
        L_N := L_N + INSTR(L_OLD_STRING_TMP, I_SEP_STRING);
        EXIT WHEN(NVL(L_N, 0) = 0 OR L_COUNT = I_NUM);
        IF L_COUNT > 0 THEN
          L_SEP_LOC_FROM := L_SEP_LOC_TO + 2;
        END IF;
        L_SEP_LOC_TO     := L_N - 1;
        L_OLD_STRING_TMP := SUBSTR(L_OLD_STRING, L_N + 1);
        L_COUNT          := L_COUNT + 1;
      END LOOP;
      IF (L_SEP_LOC_TO >= L_SEP_LOC_FROM) AND (L_COUNT = I_NUM) THEN
        L_RESULT := SUBSTR(I_OLD_STRING,
                           L_SEP_LOC_FROM,
                           L_SEP_LOC_TO - L_SEP_LOC_FROM + 1);
      END IF;
    ELSE
      L_RESULT := NULL;
    END IF;
    RETURN L_RESULT;
  END GET_SUB_STRING;

  FUNCTION CONNECT_SEAL_NO(I_SEAL_NO  IN IF_IN_SP_CONTAINER.SEAL_NO%TYPE,
                           I_SEAL_NO1 IN IF_IN_SP_CONTAINER.SEAL_NO1%TYPE,
                           I_SEAL_NO2 IN IF_IN_SP_CONTAINER.SEAL_NO2%TYPE,
                           I_SEAL_NO3 IN IF_IN_SP_CONTAINER.SEAL_NO3%TYPE,
                           I_SEAL_NO4 IN IF_IN_SP_CONTAINER.SEAL_NO4%TYPE)
    RETURN VARCHAR2 IS
    L_RESULT GO_SHIPMENT_ORDER_HEADER.SEAL_NO%TYPE;
  BEGIN
    L_RESULT := I_SEAL_NO;
    IF (I_SEAL_NO1 IS NOT NULL) OR (I_SEAL_NO2 IS NOT NULL) OR
       (I_SEAL_NO3 IS NOT NULL) OR (I_SEAL_NO4 IS NOT NULL) THEN
      L_RESULT := L_RESULT || ';' || I_SEAL_NO1;
    END IF;
    IF (I_SEAL_NO2 IS NOT NULL) OR (I_SEAL_NO3 IS NOT NULL) OR
       (I_SEAL_NO4 IS NOT NULL) THEN
      L_RESULT := L_RESULT || ';' || I_SEAL_NO2;
    END IF;
    IF (I_SEAL_NO3 IS NOT NULL) OR (I_SEAL_NO4 IS NOT NULL) THEN
      L_RESULT := L_RESULT || ';' || I_SEAL_NO3;
    END IF;
    IF (I_SEAL_NO4 IS NOT NULL) THEN
      L_RESULT := L_RESULT || ';' || I_SEAL_NO4;
    END IF;
    RETURN L_RESULT;
  END CONNECT_SEAL_NO;
  -- Function and procedure implementations

  PROCEDURE MULTI_RECIPIENTS(CONNECTION IN UTL_SMTP.CONNECTION,
                             RECIPIENT  IN VARCHAR2) IS
    L_CONNECTION        UTL_SMTP.CONNECTION;
    ORGINIAL_RECIPIENTS VARCHAR2(2000);
    ONE_RECIPIENT       VARCHAR2(100);
    L_INDEX             NUMBER(2);
  BEGIN
    IF TRIM(RECIPIENT) IS NOT NULL THEN
      ORGINIAL_RECIPIENTS := RECIPIENT;
      L_CONNECTION        := CONNECTION;
      LOOP
        EXIT WHEN ORGINIAL_RECIPIENTS IS NULL;
        L_INDEX := INSTR(ORGINIAL_RECIPIENTS, ';');
        IF L_INDEX > 1 THEN
          ONE_RECIPIENT       := SUBSTR(ORGINIAL_RECIPIENTS, 1, L_INDEX - 1);
          ORGINIAL_RECIPIENTS := SUBSTR(ORGINIAL_RECIPIENTS, L_INDEX + 1);
        ELSE
          ONE_RECIPIENT       := ORGINIAL_RECIPIENTS;
          ORGINIAL_RECIPIENTS := NULL;
        END IF;
        UTL_SMTP.RCPT(L_CONNECTION, ONE_RECIPIENT);
      END LOOP;
    END IF;
  END MULTI_RECIPIENTS;
  -- Start the connection.

  -- %Author jinlo
  --2007/10/16
  -- %Send email in Oracle server
  -- %param sender From field
  -- %param recipient To field,sepearted by comma
  -- %param subject Subject field
  -- %param message Messge field
  -- %param smtp SMTP field
  -- %param port Port field
  PROCEDURE SEND_MAIL(SENDER      IN VARCHAR2,
                      RECIPIENT   IN VARCHAR2,
                      CCRECIPIENT IN VARCHAR2,
                      SUBJECT     IN VARCHAR2,
                      MESSAGE     IN VARCHAR2,
                      SMTP        IN VARCHAR2,
                      PORT        IN VARCHAR2) IS

    CRLF       VARCHAR2(2) := UTL_TCP.CRLF;
    CONNECTION UTL_SMTP.CONNECTION;
    HEADER     VARCHAR2(2000);
    --    one_recipient VARCHAR2(100);
    --    l_index       NUMBER(2);
  BEGIN
    -- Start the connection.
    CONNECTION := UTL_SMTP.OPEN_CONNECTION(SMTP, PORT);

    HEADER := 'Date: ' || TO_CHAR(SYSDATE, 'dd Mon yy hh24:mi:ss') || CRLF ||
              'From: ' || SENDER || '' || CRLF || 'Subject: ' || SUBJECT || CRLF ||
              'To: ' || RECIPIENT || CRLF;
    IF NOT TRIM(CCRECIPIENT) IS NULL THEN
      HEADER := HEADER || 'CC: ' || CCRECIPIENT;
    END IF;

    -- Handshake with the SMTP server
    UTL_SMTP.HELO(CONNECTION, SMTP);
    UTL_SMTP.MAIL(CONNECTION, SENDER);
    MULTI_RECIPIENTS(CONNECTION, RECIPIENT);
    MULTI_RECIPIENTS(CONNECTION, CCRECIPIENT);
    UTL_SMTP.OPEN_DATA(CONNECTION);

    -- Write the header
    UTL_SMTP.WRITE_DATA(CONNECTION, HEADER);

    -- The crlf is required to distinguish that what comes next is not simply part of the header..
    UTL_SMTP.WRITE_DATA(CONNECTION, CRLF || MESSAGE);
    UTL_SMTP.CLOSE_DATA(CONNECTION);
    UTL_SMTP.QUIT(CONNECTION);

  END SEND_MAIL;

  -- %Author jinlo
  --%Date 2007/10/16
  -- %Helper function for send_warning_mail function, reading configuration
  -- %param i_module
  -- %param i_configuration_name
  -- %param o_param
  FUNCTION GET_PARAM(I_MODULE             IN VARCHAR2,
                     I_CONFIGURATION_NAME IN VARCHAR2) RETURN VARCHAR2 /*RESULT_CACHE*/
   IS
    V_TEMP VARCHAR2(2000);
  BEGIN
    SELECT NVL((SELECT CONFIGURATION_VALUE
                 FROM WOS_CONFIGURATION
                WHERE MODULE = I_MODULE
                  AND CONFIGURATION_NAME = I_CONFIGURATION_NAME),
               ' ')
      INTO V_TEMP
      FROM DUAL;
    RETURN V_TEMP;
  END GET_PARAM;

  /*
  * return if the db is common or not
  */
  FUNCTION IS_COMMON_DB RETURN BOOLEAN IS
  BEGIN
    RETURN(GET_PARAM('SYSTEM', 'IS_COMMON_DB') = 1);
  END IS_COMMON_DB;

  /*
  * return if the db is biz or not
  */
  FUNCTION IS_BIZ_DB RETURN BOOLEAN IS
  BEGIN
    RETURN(GET_PARAM('SYSTEM', 'IS_BIZ_DB') = 1);
  END IS_BIZ_DB;

  /*Find rule value in system, warehouse or warehouse+company level by parent codes
   i_rule_value_level
             not null, among
               RULE_SYSTEM_LEVEL, RULE_WAREHOUSE_LEVEL and RULE_WAREHOUSE_OWNER_LEVEL
   i_parent_codes
             parent codes to match
   i_rule_codes
             rule codes to match
  return rule_value_t_tab*/
  FUNCTION GET_RULE_VALUE_BY_CODE(I_RULE_LEVEL    IN VARCHAR2,
                                  I_PARENT_CODES  IN STRARRAY DEFAULT NULL,
                                  I_RULE_CODES    IN STRARRAY DEFAULT NULL,
                                  I_WAREHOUSE_IDS IN STRARRAY DEFAULT NULL,
                                  I_OWNER_IDS     IN STRARRAY DEFAULT NULL)
    RETURN RULE_VALUE_T_TAB IS
    VC_RULE_VALUE    RULE_VALUE_REFCUR_T;
    V_SQL            VARCHAR2(32767);
    V_PARENT_CODES   VARCHAR2(32767);
    V_RULE_CODES     VARCHAR2(32767);
    V_WAREHOUSE_IDS  VARCHAR2(32767);
    V_OWNER_IDS      VARCHAR2(32767);
    V_RULE_VALUE_TAB RULE_VALUE_T_TAB := RULE_VALUE_T_TAB();
    V_RULE_VALUE_REC RULE_VALUE_T_REC;
    J                NUMBER := 0;
  BEGIN
    CASE
      WHEN I_RULE_LEVEL = RULE_SYSTEM_LEVEL THEN
        V_SQL := 'SELECT r.rule_code, r.parent_code, r.DEFAULT_VALUE AS VALUE, NULL AS WAREHOUSE_ID, NULL AS OWNER_ID FROM CM_NEW_RULE r WHERE r.default_value IS NOT NULL';
        IF I_PARENT_CODES IS NOT NULL AND I_PARENT_CODES.COUNT > 0 THEN
          SELECT SUBSTR(STRAGG(',''' || REPLACE(COLUMN_VALUE, '''', '''''') || ''''),
                        2)
            INTO V_PARENT_CODES
            FROM TABLE(I_PARENT_CODES);
          V_SQL := V_SQL || ' AND r.parent_code IN (' || V_PARENT_CODES || ')';
        END IF;
        IF I_RULE_CODES IS NOT NULL AND I_RULE_CODES.COUNT > 0 THEN
          SELECT SUBSTR(STRAGG(',''' || REPLACE(COLUMN_VALUE, '''', '''''') || ''''),
                        2)
            INTO V_RULE_CODES
            FROM TABLE(I_RULE_CODES);
          V_SQL := V_SQL || ' AND r.rule_code IN (' || V_RULE_CODES || ')';
        END IF;
        V_SQL := V_SQL || ' ORDER BY r.parent_code, r.rule_code';
        OPEN VC_RULE_VALUE FOR V_SQL;
        LOOP
          FETCH VC_RULE_VALUE
            INTO V_RULE_VALUE_REC;
          EXIT WHEN VC_RULE_VALUE%NOTFOUND; -- exit when last row is fetched
          -- append to collection
          J := J + 1;
          V_RULE_VALUE_TAB.EXTEND;
          V_RULE_VALUE_TAB(J) := RULE_VALUE_T(V_RULE_VALUE_REC.RULE_CODE,
                                              V_RULE_VALUE_REC.PARENT_CODE,
                                              V_RULE_VALUE_REC.VALUE,
                                              V_RULE_VALUE_REC.WAREHOUSE_ID,
                                              V_RULE_VALUE_REC.OWNER_ID);
        END LOOP;
        CLOSE VC_RULE_VALUE;
        /*EXECUTE IMMEDIATE v_sql BULK COLLECT
          INTO v_rule_value_t_rectab;
        FOR i IN v_rule_value_t_rectab.FIRST .. v_rule_value_t_rectab.LAST LOOP
          j := j + 1;
          v_rule_value_t_tab.EXTEND;
          v_rule_value_t_tab(j) := rule_value_t(v_rule_value_t_rectab(i)
                                                .RULE_CODE,
                                                v_rule_value_t_rectab(i)
                                                .PARENT_CODE,
                                                v_rule_value_t_rectab(i).VALUE,
                                                v_rule_value_t_rectab(i)
                                                .WAREHOUSE_ID,
                                                v_rule_value_t_rectab(i)
                                                .OWNER_ID);
        END LOOP;*/
      WHEN I_RULE_LEVEL = RULE_WAREHOUSE_LEVEL THEN
        V_SQL := 'SELECT r.rule_code, r.parent_code, nvl(rv.VALUE, r.default_value) AS VALUE, w.warehouse_id, NULL AS owner_id FROM cm_new_rule r JOIN cm_warehouse w ON 1 = 1 LEFT OUTER JOIN cm_new_rule_value rv ON rv.rule_code = r.rule_code AND rv.warehouse_id = w.warehouse_id AND rv.owner_id IS NULL WHERE nvl(rv.VALUE, r.default_value) IS NOT NULL AND NOT EXISTS (SELECT 0 FROM cm_new_rule_ignore_dft_val ridv WHERE ridv.rule_code = r.rule_code AND ridv.warehouse_id = w.warehouse_id AND ridv.owner_id IS NULL)';
        IF I_PARENT_CODES IS NOT NULL AND I_PARENT_CODES.COUNT > 0 THEN
          SELECT SUBSTR(STRAGG(',''' || REPLACE(COLUMN_VALUE, '''', '''''') || ''''),
                        2)
            INTO V_PARENT_CODES
            FROM TABLE(I_PARENT_CODES);
          V_SQL := V_SQL || ' AND r.parent_code IN (' || V_PARENT_CODES || ')';
        END IF;
        IF I_RULE_CODES IS NOT NULL AND I_RULE_CODES.COUNT > 0 THEN
          SELECT SUBSTR(STRAGG(',''' || REPLACE(COLUMN_VALUE, '''', '''''') || ''''),
                        2)
            INTO V_RULE_CODES
            FROM TABLE(I_RULE_CODES);
          V_SQL := V_SQL || ' AND r.rule_code IN (' || V_RULE_CODES || ')';
        END IF;
        IF I_WAREHOUSE_IDS IS NOT NULL AND I_WAREHOUSE_IDS.COUNT > 0 THEN
          SELECT SUBSTR(STRAGG(',''' || REPLACE(COLUMN_VALUE, '''', '''''') || ''''),
                        2)
            INTO V_WAREHOUSE_IDS
            FROM TABLE(I_WAREHOUSE_IDS);
          V_SQL := V_SQL || ' AND w.warehouse_id IN (' || V_WAREHOUSE_IDS || ')';
        END IF;
        V_SQL := V_SQL ||
                 ' ORDER BY w.warehouse_id, r.parent_code, r.rule_code';
        OPEN VC_RULE_VALUE FOR V_SQL;
        LOOP
          FETCH VC_RULE_VALUE
            INTO V_RULE_VALUE_REC;
          EXIT WHEN VC_RULE_VALUE%NOTFOUND; -- exit when last row is fetched
          -- append to collection
          J := J + 1;
          V_RULE_VALUE_TAB.EXTEND;
          V_RULE_VALUE_TAB(J) := RULE_VALUE_T(V_RULE_VALUE_REC.RULE_CODE,
                                              V_RULE_VALUE_REC.PARENT_CODE,
                                              V_RULE_VALUE_REC.VALUE,
                                              V_RULE_VALUE_REC.WAREHOUSE_ID,
                                              V_RULE_VALUE_REC.OWNER_ID);
        END LOOP;
        CLOSE VC_RULE_VALUE;
        /*EXECUTE IMMEDIATE v_sql BULK COLLECT
          INTO v_rule_value_tab;
        FOR i IN v_rule_value_rectab.FIRST .. v_rule_value_rectab.LAST LOOP
          j := j + 1;
          v_rule_value_tab.EXTEND;
          v_rule_value_tab(j) := rule_value_t(v_rule_value_rectab(i).RULE_CODE,
                                              v_rule_value_rectab(i).PARENT_CODE,
                                              v_rule_value_rectab(i).VALUE,
                                              v_rule_value_rectab(i)
                                              .WAREHOUSE_ID,
                                              v_rule_value_rectab(i).OWNER_ID);
        END LOOP;*/
      WHEN I_RULE_LEVEL = RULE_WAREHOUSE_OWNER_LEVEL THEN
        V_SQL := 'SELECT r.rule_code, r.parent_code, nvl(rv.VALUE, r.default_value) AS VALUE, wcr.warehouse_id, wcr.company_id AS owner_id FROM cm_new_rule r JOIN cm_warehouse_company_relation wcr ON wcr.relation_type = ''WAREHOUSE_OWNER'' LEFT OUTER JOIN cm_new_rule_value rv ON rv.rule_code = r.rule_code AND rv.warehouse_id = wcr.warehouse_id AND rv.owner_id = wcr.company_id WHERE nvl(rv.VALUE, r.default_value) IS NOT NULL AND NOT EXISTS (SELECT 0 FROM cm_new_rule_ignore_dft_val ridv WHERE ridv.rule_code = r.rule_code AND ridv.warehouse_id = wcr.warehouse_id AND ridv.owner_id = wcr.company_id)';
        IF I_PARENT_CODES IS NOT NULL AND I_PARENT_CODES.COUNT > 0 THEN
          SELECT SUBSTR(STRAGG(',''' || REPLACE(COLUMN_VALUE, '''', '''''') || ''''),
                        2)
            INTO V_PARENT_CODES
            FROM TABLE(I_PARENT_CODES);
          V_SQL := V_SQL || ' AND r.parent_code IN (' || V_PARENT_CODES || ')';
        END IF;
        IF I_RULE_CODES IS NOT NULL AND I_RULE_CODES.COUNT > 0 THEN
          SELECT SUBSTR(STRAGG(',''' || REPLACE(COLUMN_VALUE, '''', '''''') || ''''),
                        2)
            INTO V_RULE_CODES
            FROM TABLE(I_RULE_CODES);
          V_SQL := V_SQL || ' AND r.rule_code IN (' || V_RULE_CODES || ')';
        END IF;
        IF I_WAREHOUSE_IDS IS NOT NULL AND I_WAREHOUSE_IDS.COUNT > 0 THEN
          SELECT SUBSTR(STRAGG(',''' || REPLACE(COLUMN_VALUE, '''', '''''') || ''''),
                        2)
            INTO V_WAREHOUSE_IDS
            FROM TABLE(I_WAREHOUSE_IDS);
          V_SQL := V_SQL || ' AND wcr.warehouse_id IN (' || V_WAREHOUSE_IDS || ')';
        END IF;
        IF I_OWNER_IDS IS NOT NULL AND I_OWNER_IDS.COUNT > 0 THEN
          SELECT SUBSTR(STRAGG(',''' || REPLACE(COLUMN_VALUE, '''', '''''') || ''''),
                        2)
            INTO V_OWNER_IDS
            FROM TABLE(I_OWNER_IDS);
          V_SQL := V_SQL || ' AND wcr.company_id IN (' || V_OWNER_IDS || ')';
        END IF;
        V_SQL := V_SQL ||
                 ' ORDER BY wcr.warehouse_id, wcr.company_id, r.parent_code, r.rule_code';
        OPEN VC_RULE_VALUE FOR V_SQL;
        LOOP
          FETCH VC_RULE_VALUE
            INTO V_RULE_VALUE_REC;
          EXIT WHEN VC_RULE_VALUE%NOTFOUND; -- exit when last row is fetched
          -- append to collection
          J := J + 1;
          V_RULE_VALUE_TAB.EXTEND;
          V_RULE_VALUE_TAB(J) := RULE_VALUE_T(V_RULE_VALUE_REC.RULE_CODE,
                                              V_RULE_VALUE_REC.PARENT_CODE,
                                              V_RULE_VALUE_REC.VALUE,
                                              V_RULE_VALUE_REC.WAREHOUSE_ID,
                                              V_RULE_VALUE_REC.OWNER_ID);
        END LOOP;
        CLOSE VC_RULE_VALUE;
        /*EXECUTE IMMEDIATE v_sql BULK COLLECT
          INTO v_rule_value_rectab;
        IF v_rule_value_rectab IS NOT NULL AND v_rule_value_rectab.COUNT > 0 THEN
          FOR i IN v_rule_value_rectab.FIRST .. v_rule_value_rectab.LAST LOOP
            j := j + 1;
            v_rule_value_tab.EXTEND;
            v_rule_value_tab(j) := rule_value_t(v_rule_value_rectab(i)
                                                .RULE_CODE,
                                                v_rule_value_rectab(i)
                                                .PARENT_CODE,
                                                v_rule_value_rectab(i).VALUE,
                                                v_rule_value_rectab(i)
                                                .WAREHOUSE_ID,
                                                v_rule_value_rectab(i).OWNER_ID);
          END LOOP;
        END IF;*/
      ELSE
        RAISE_APPLICATION_ERROR(-20000,
                                'i_rule_level can not be null and should be among RULE_SYSTEM_LEVEL, RULE_WAREHOUSE_LEVEL and RULE_WAREHOUSE_OWNER_LEVEL',
                                TRUE);
    END CASE;
    RETURN V_RULE_VALUE_TAB;
  END GET_RULE_VALUE_BY_CODE;

  /*Find rule value in warehouse or warehouse+company level by rule code
   i_rule_level
             not null, among
               RULE_SYSTEM_LEVEL, RULE_WAREHOUSE_LEVEL and RULE_WAREHOUSE_OWNER_LEVEL
   i_rule_code
             rule code to match
  return rule_value_t_tab*/
  FUNCTION GET_VALUE_BY_RULE_CODE(I_RULE_LEVEL   IN VARCHAR2,
                                  I_RULE_CODE    IN CM_NEW_RULE.RULE_CODE%TYPE DEFAULT NULL,
                                  I_WAREHOUSE_ID IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE DEFAULT NULL,
                                  I_OWNER_ID     IN CM_COMPANY.COMPANY_ID%TYPE DEFAULT NULL)
    RETURN RULE_VALUE_T_TAB IS
    L_RULE_CODES    STRARRAY := NULL;
    L_WAREHOUSE_IDS STRARRAY := NULL;
    L_OWNER_IDS     STRARRAY := NULL;
  BEGIN
    IF I_RULE_CODE IS NOT NULL THEN
      L_RULE_CODES := NEW STRARRAY();
      L_RULE_CODES.EXTEND;
      L_RULE_CODES(1) := I_RULE_CODE;
    END IF;
    IF I_WAREHOUSE_ID IS NOT NULL THEN
      L_WAREHOUSE_IDS := NEW STRARRAY();
      L_WAREHOUSE_IDS.EXTEND;
      L_WAREHOUSE_IDS(1) := I_WAREHOUSE_ID;
    END IF;
    IF I_OWNER_ID IS NOT NULL THEN
      L_OWNER_IDS := NEW STRARRAY();
      L_OWNER_IDS.EXTEND;
      L_OWNER_IDS(1) := I_OWNER_ID;
    END IF;
    RETURN GET_RULE_VALUE_BY_CODE(I_RULE_LEVEL    => I_RULE_LEVEL,
                                  I_RULE_CODES    => L_RULE_CODES,
                                  I_WAREHOUSE_IDS => L_WAREHOUSE_IDS,
                                  I_OWNER_IDS     => L_OWNER_IDS);
  END GET_VALUE_BY_RULE_CODE;

  /*Find rule value in warehouse or warehouse+company level by rule codes
   i_rule_value_level
             not null, among
               RULE_SYSTEM_LEVEL, RULE_WAREHOUSE_LEVEL and RULE_WAREHOUSE_OWNER_LEVEL
   i_rule_codes
             rule codes to match
  return rule_value_t_tab*/
  FUNCTION GET_RULE_VALUE_BY_RULE_CODE(I_RULE_LEVEL    IN VARCHAR2,
                                       I_RULE_CODES    IN STRARRAY DEFAULT NULL,
                                       I_WAREHOUSE_IDS IN STRARRAY DEFAULT NULL,
                                       I_OWNER_IDS     IN STRARRAY DEFAULT NULL)
    RETURN RULE_VALUE_T_TAB IS
  BEGIN
    RETURN GET_RULE_VALUE_BY_CODE(I_RULE_LEVEL    => I_RULE_LEVEL,
                                  I_RULE_CODES    => I_RULE_CODES,
                                  I_WAREHOUSE_IDS => I_WAREHOUSE_IDS,
                                  I_OWNER_IDS     => I_OWNER_IDS);
  END GET_RULE_VALUE_BY_RULE_CODE;

  /*Find rule value in system, warehouse or warehouse+company level by parent code
   i_rule_level
             not null, among
               RULE_SYSTEM_LEVEL, RULE_WAREHOUSE_LEVEL and RULE_WAREHOUSE_OWNER_LEVEL
   i_parent_code
             parent code to match
  return tb_rule_value*/
  FUNCTION GET_VALUE_BY_PARENT_CODE(I_RULE_LEVEL   IN VARCHAR2,
                                    I_PARENT_CODE  IN CM_NEW_RULE.RULE_CODE%TYPE DEFAULT NULL,
                                    I_WAREHOUSE_ID IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE DEFAULT NULL,
                                    I_OWNER_ID     IN CM_COMPANY.COMPANY_ID%TYPE DEFAULT NULL)
    RETURN RULE_VALUE_T_TAB IS
    L_PARENT_CODES  STRARRAY := NULL;
    L_WAREHOUSE_IDS STRARRAY := NULL;
    L_OWNER_IDS     STRARRAY := NULL;
  BEGIN
    IF I_PARENT_CODE IS NOT NULL THEN
      L_PARENT_CODES := NEW STRARRAY();
      L_PARENT_CODES.EXTEND;
      L_PARENT_CODES(1) := I_PARENT_CODE;
    END IF;
    IF I_WAREHOUSE_ID IS NOT NULL THEN
      L_WAREHOUSE_IDS := NEW STRARRAY();
      L_WAREHOUSE_IDS.EXTEND;
      L_WAREHOUSE_IDS(1) := I_WAREHOUSE_ID;
    END IF;
    IF I_OWNER_ID IS NOT NULL THEN
      L_OWNER_IDS := NEW STRARRAY();
      L_OWNER_IDS.EXTEND;
      L_OWNER_IDS(1) := I_OWNER_ID;
    END IF;
    RETURN GET_RULE_VALUE_BY_CODE(I_RULE_LEVEL    => I_RULE_LEVEL,
                                  I_PARENT_CODES  => L_PARENT_CODES,
                                  I_WAREHOUSE_IDS => L_WAREHOUSE_IDS,
                                  I_OWNER_IDS     => L_OWNER_IDS);
  END GET_VALUE_BY_PARENT_CODE;

  /*Find rule value in system, warehouse or warehouse+company level by parent codes
   i_rule_value_level
             not null, among
               RULE_SYSTEM_LEVEL, RULE_WAREHOUSE_LEVEL and RULE_WAREHOUSE_OWNER_LEVEL
   i_parent_codes
             parent codes to match
  return rule_value_t_tab*/
  FUNCTION GET_RULE_VALUE_BY_PARENT_CODE(I_RULE_LEVEL    IN VARCHAR2,
                                         I_PARENT_CODES  IN STRARRAY DEFAULT NULL,
                                         I_WAREHOUSE_IDS IN STRARRAY DEFAULT NULL,
                                         I_OWNER_IDS     IN STRARRAY DEFAULT NULL)
    RETURN RULE_VALUE_T_TAB IS
  BEGIN
    RETURN GET_RULE_VALUE_BY_CODE(I_RULE_LEVEL    => I_RULE_LEVEL,
                                  I_PARENT_CODES  => I_PARENT_CODES,
                                  I_WAREHOUSE_IDS => I_WAREHOUSE_IDS,
                                  I_OWNER_IDS     => I_OWNER_IDS);
  END GET_RULE_VALUE_BY_PARENT_CODE;

  -- make sure there is no same <warehouse,owner> in result
  FUNCTION GET_CONFIGRATION_4_WH_OWNER(I_WAREHOUSE_ID IN VARCHAR2,
                                       I_OWNER_ID     IN VARCHAR2,
                                       I_RULE_VALUES  IN RULE_VALUE_T_TAB)
    RETURN RULE_VALUE_T_TAB IS
    O_RULE_VALUES RULE_VALUE_T_TAB := RULE_VALUE_T_TAB();
    CNT           INTEGER := 1;
  BEGIN
    FOR I IN 1 .. I_RULE_VALUES.COUNT LOOP
      IF I_RULE_VALUES(I).WAREHOUSE_ID = I_WAREHOUSE_ID AND I_RULE_VALUES(I)
         .OWNER_ID = I_OWNER_ID THEN
        O_RULE_VALUES.EXTEND;
        O_RULE_VALUES(CNT) := I_RULE_VALUES(I);
        CNT := CNT + 1;
      END IF;
    END LOOP;
    RETURN O_RULE_VALUES;
  END GET_CONFIGRATION_4_WH_OWNER;

  -- make sure there is no same <warehouse> in result
  FUNCTION GET_CONFIGRATION_4_WH(I_WAREHOUSE_ID IN VARCHAR2,
                                 I_RULE_VALUES  IN RULE_VALUE_T_TAB)
    RETURN RULE_VALUE_T_TAB IS
    O_RULE_VALUES RULE_VALUE_T_TAB := RULE_VALUE_T_TAB();
    CNT           INTEGER := 1;
  BEGIN
    FOR I IN 1 .. I_RULE_VALUES.COUNT LOOP
      IF I_RULE_VALUES(I).WAREHOUSE_ID = I_WAREHOUSE_ID THEN
        O_RULE_VALUES.EXTEND;
        O_RULE_VALUES(CNT) := I_RULE_VALUES(I);
        CNT := CNT + 1;
      END IF;
    END LOOP;
    RETURN O_RULE_VALUES;
  END GET_CONFIGRATION_4_WH;

  /*
  this function assume there is only one value at most, so
  it is recommended that i_abc_rule_values is the result of get_configration_4_wh_owner
  */
  FUNCTION GET_VALUE_OF_RULE(I_RULE_CODE   IN VARCHAR2,
                             I_RULE_VALUES IN RULE_VALUE_T_TAB)
    RETURN VARCHAR2 IS
  BEGIN
    FOR I IN 1 .. I_RULE_VALUES.COUNT LOOP
      IF I_RULE_VALUES(I).RULE_CODE = I_RULE_CODE THEN
        RETURN I_RULE_VALUES(I).VALUE;
      END IF;
    END LOOP;
    RETURN NULL;
  END GET_VALUE_OF_RULE;

  /*
  This function can handle multi values of the rule, generally,
  it is used to filter values of different <wh,owner>
  */
  FUNCTION GET_VALUES_OF_RULE(I_RULE_CODE   IN VARCHAR2,
                              I_RULE_VALUES IN RULE_VALUE_T_TAB)
    RETURN RULE_VALUE_T_TAB IS
    O_RULE_VALUES RULE_VALUE_T_TAB := RULE_VALUE_T_TAB();
    CNT           INTEGER := 1;
  BEGIN
    FOR I IN 1 .. I_RULE_VALUES.COUNT LOOP
      IF I_RULE_VALUES(I).RULE_CODE = I_RULE_CODE THEN
        O_RULE_VALUES.EXTEND;
        O_RULE_VALUES(CNT) := I_RULE_VALUES(I);
        CNT := CNT + 1;
      END IF;
    END LOOP;
    RETURN O_RULE_VALUES;
  END GET_VALUES_OF_RULE;

  /*
  Like split in Java, no regular expression supported.
  */
  FUNCTION STRING_SPLIT(P_STR         IN VARCHAR2,
                        P_DELIMITER   IN VARCHAR2,
                        P_IGNORE_NULL IN BOOLEAN DEFAULT FALSE)
    RETURN STRARRAY IS
    J         INT := 0;
    I         INT := 1;
    LEN       INT := 0;
    LEN1      INT := 0;
    STR       VARCHAR2(4000);
    STR_SPLIT STRARRAY := STRARRAY();
  BEGIN
    LEN  := LENGTH(P_STR);
    LEN1 := LENGTH(P_DELIMITER);

    <<LOOP1>>
    WHILE J <= LEN LOOP
      J := INSTR(P_STR, P_DELIMITER, I);

      IF J = 0 THEN
        J   := LEN + 1;
        STR := SUBSTR(P_STR, I);
        IF STR IS NULL AND P_IGNORE_NULL THEN
          GOTO LOOP1;
        END IF;
        STR_SPLIT.EXTEND;
        STR_SPLIT(STR_SPLIT.COUNT) := STR;
        IF I >= LEN THEN
          EXIT;
        END IF;
      ELSE
        STR := SUBSTR(P_STR, I, J - I);
        I   := J + LEN1;
        IF STR IS NULL AND P_IGNORE_NULL THEN
          GOTO LOOP1;
        END IF;
        STR_SPLIT.EXTEND;
        STR_SPLIT(STR_SPLIT.COUNT) := STR;
      END IF;
    END LOOP;
    RETURN STR_SPLIT;
  END STRING_SPLIT;

  -- get first element of a collection if it's not empty, otherwise return null
  FUNCTION GETSTRARRAYFIRST(I_STRARRAY IN STRARRAY) RETURN VARCHAR IS
  BEGIN
    IF I_STRARRAY IS NOT NULL AND I_STRARRAY.COUNT > 0 THEN
      RETURN I_STRARRAY(I_STRARRAY.FIRST);
    END IF;
    RETURN NULL;
  END GETSTRARRAYFIRST;

  -- get first element of a collection if it's not empty, otherwise return null
  FUNCTION GETNUMARRAYFIRST(I_NUMARRAY IN NUMARRAY) RETURN NUMBER IS
  BEGIN
    IF I_NUMARRAY IS NOT NULL AND I_NUMARRAY.COUNT > 0 THEN
      RETURN I_NUMARRAY(I_NUMARRAY.FIRST);
    END IF;
    RETURN NULL;
  END GETNUMARRAYFIRST;

  -- get first element of a collection if it's not empty, otherwise return null
  FUNCTION GETDATEARRAYFIRST(I_DATEARRAY IN DATEARRAY) RETURN DATE IS
  BEGIN
    IF I_DATEARRAY IS NOT NULL AND I_DATEARRAY.COUNT > 0 THEN
      RETURN I_DATEARRAY(I_DATEARRAY.FIRST);
    END IF;
    RETURN NULL;
  END GETDATEARRAYFIRST;

  -- tick the start of an operation
  PROCEDURE TICK_OPERATION_HISTORY(I_OPERATION_TYPE IN RP_OPERATION_HISTORY.OPERATION_TYPE%TYPE,
                                   I_OPERATION_KEY  IN RP_OPERATION_HISTORY.OPERATION_KEY%TYPE,
                                   I_USER_ID        IN CM_USER.USER_ID%TYPE,
                                   I_REF_NO         IN RP_OPERATION_HISTORY.REF_NO%TYPE DEFAULT NULL) IS
    l_cnt number(15);
  BEGIN
    -- check input params
    IF I_OPERATION_TYPE IS NULL THEN
      RAISE_APPLICATION_ERROR(-20000,
                              'i_operation_type can not be null',
                              TRUE);
    END IF;
    IF I_OPERATION_KEY IS NULL THEN
      RAISE_APPLICATION_ERROR(-20000,
                              'i_operation_key can not be null',
                              TRUE);
    END IF;

    select count(1)
      into l_cnt
      from RP_OPERATION_HISTORY OH
     where oh.operation_type = I_OPERATION_TYPE
       and oh.operation_key = I_OPERATION_KEY
       and decode(oh.ref_no, I_REF_NO, 1, 0) = 1;

    if l_cnt = 0 then
      INSERT into RP_OPERATION_HISTORY
        (OID,
         OPERATION_TYPE,
         OPERATION_KEY,
         START_TIME,
         CREATOR,
         UPDATER,
         CREATE_TIME,
         UPDATE_TIME)
      VALUES
        (GET_NEW_SEQUENCE_NO('RP_OPERATION_HISTORY_OID'),
         I_OPERATION_TYPE,
         I_OPERATION_KEY,
         SYSDATE,
         I_USER_ID,
         I_USER_ID,
         SYSDATE,
         SYSDATE);
    end if;
  END TICK_OPERATION_HISTORY;

  -- tock the start of an operation
  PROCEDURE TOCK_OPERATION_HISTORY(I_OPERATION_TYPE IN RP_OPERATION_HISTORY.OPERATION_TYPE%TYPE,
                                   I_OPERATION_KEY  IN RP_OPERATION_HISTORY.OPERATION_KEY%TYPE,
                                   I_USER_ID        IN CM_USER.USER_ID%TYPE,
                                   I_REF_NO         IN RP_OPERATION_HISTORY.REF_NO%TYPE DEFAULT NULL) IS
  BEGIN
    -- check input params
    IF I_OPERATION_TYPE IS NULL THEN
      RAISE_APPLICATION_ERROR(-20000,
                              'i_operation_type can not be null',
                              TRUE);
    END IF;
    IF I_OPERATION_KEY IS NULL THEN
      RAISE_APPLICATION_ERROR(-20000,
                              'i_operation_key can not be null',
                              TRUE);
    END IF;
    IF I_REF_NO IS NOT NULL THEN
      UPDATE RP_OPERATION_HISTORY OH
         SET OH.FINISH_TIME = SYSDATE,
             OH.UPDATER     = I_USER_ID,
             OH.UPDATE_TIME = SYSDATE
       WHERE OH.OPERATION_TYPE = I_OPERATION_TYPE
         AND OH.OPERATION_KEY = I_OPERATION_KEY
         AND OH.REF_NO = I_REF_NO;
    ELSE
      UPDATE RP_OPERATION_HISTORY OH
         SET OH.FINISH_TIME = SYSDATE,
             OH.UPDATER     = I_USER_ID,
             OH.UPDATE_TIME = SYSDATE
       WHERE OH.OPERATION_TYPE = I_OPERATION_TYPE
         AND OH.OPERATION_KEY = I_OPERATION_KEY
         AND OH.REF_NO IS NULL;
    END IF;
  END TOCK_OPERATION_HISTORY;

  -- tick and tock the start of an operation at the same time
  PROCEDURE TICK_TOCK_OPERATION_HISTORY(I_OPERATION_TYPE IN RP_OPERATION_HISTORY.OPERATION_TYPE%TYPE,
                                        I_OPERATION_KEY  IN RP_OPERATION_HISTORY.OPERATION_KEY%TYPE,
                                        I_USER_ID        IN CM_USER.USER_ID%TYPE,
                                        I_REF_NO         IN RP_OPERATION_HISTORY.REF_NO%TYPE DEFAULT NULL) IS
    l_cnt number(15);
  BEGIN
    -- check input params
    IF I_OPERATION_TYPE IS NULL THEN
      RAISE_APPLICATION_ERROR(-20000,
                              'i_operation_type can not be null',
                              TRUE);
    END IF;
    IF I_OPERATION_KEY IS NULL THEN
      RAISE_APPLICATION_ERROR(-20000,
                              'i_operation_key can not be null',
                              TRUE);
    END IF;

    select count(1)
      into l_cnt
      from RP_OPERATION_HISTORY OH
     where OH.OPERATION_TYPE = I_OPERATION_TYPE
       AND OH.OPERATION_KEY = I_OPERATION_KEY
       AND decode(OH.REF_NO, I_REF_NO, 1, 0) = 1;

    if l_cnt > 0 then
      UPDATE RP_OPERATION_HISTORY oh
         SET OH.FINISH_TIME = SYSDATE,
             OH.UPDATER     = I_USER_ID,
             OH.UPDATE_TIME = SYSDATE
       where OH.OPERATION_TYPE = I_OPERATION_TYPE
         AND OH.OPERATION_KEY = I_OPERATION_KEY
         AND decode(OH.REF_NO, I_REF_NO, 1, 0) = 1;
    else
      INSERT into RP_OPERATION_HISTORY
        (OID,
         OPERATION_TYPE,
         OPERATION_KEY,
         REF_NO,
         START_TIME,
         FINISH_TIME,
         CREATOR,
         UPDATER,
         CREATE_TIME,
         UPDATE_TIME)
      VALUES
        (GET_NEW_SEQUENCE_NO('RP_OPERATION_HISTORY_OID'),
         I_OPERATION_TYPE,
         I_OPERATION_KEY,
         I_REF_NO,
         SYSDATE,
         SYSDATE,
         I_USER_ID,
         I_USER_ID,
         SYSDATE,
         SYSDATE);
    end if;

  END TICK_TOCK_OPERATION_HISTORY;

  -- check the template logic
  FUNCTION CHECK_SUP_TEMPLATE(I_WAREHOUSE_ID   IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE,
                              I_OWNER_ID       IN CM_COMPANY.COMPANY_ID%TYPE,
                              SP_ACTUAL_CODE   IN SP_CODE.CODE%TYPE,
                              SP_CATEGORY_NAME IN SP_CODE.CATEGORY_NAME%TYPE,
                              SP_CODE_OID      OUT SP_CODE.OID%TYPE)
    RETURN BOOLEAN IS
    L_OWNER_CNT              NUMBER(15) := 0;
    L_WH_OWNER_CNT           NUMBER(15) := 0;
    L_OWNER_TEMPLATE_NAME    VARCHAR2(300);
    L_WH_OWNER_TEMPLATE_NAME VARCHAR2(300);
    L_BOOL                   BOOLEAN := FALSE;
  BEGIN

    IF SP_ACTUAL_CODE IS NULL OR SP_CATEGORY_NAME IS NULL THEN
      RETURN FALSE;
    END IF;

    SELECT MAX(SC.OID)
      INTO SP_CODE_OID
      FROM SP_CODE SC
     WHERE SC.CATEGORY_NAME = SP_CATEGORY_NAME
       AND SC.CODE = SP_ACTUAL_CODE;

    IF SP_CODE_OID IS NULL THEN
      RETURN FALSE;
    END IF;

    SELECT COUNT(TT.OID)
      INTO L_OWNER_CNT
      FROM SP_REL_TEMPLATE_WH_OWNER TT
     WHERE TT.WAREHOUSE_ID = I_WAREHOUSE_ID
       AND TT.OWNER_ID = I_OWNER_ID;

    IF L_OWNER_CNT > 0 THEN
      SELECT TT.TEMPLATE_NAME,
             (SELECT COUNT(1)
                FROM SP_REL_TEMPLATE_CODE REFCODE
               WHERE REFCODE.TEMPLATE_NAME = TT.TEMPLATE_NAME
                 AND REFCODE.CODE_OID = SP_CODE_OID)
        INTO L_WH_OWNER_TEMPLATE_NAME, L_WH_OWNER_CNT
        FROM SP_REL_TEMPLATE_WH_OWNER TT
       WHERE TT.WAREHOUSE_ID = I_WAREHOUSE_ID
         AND TT.OWNER_ID = I_OWNER_ID;
    END IF;

    SELECT COUNT(TT.OID)
      INTO L_OWNER_CNT
      FROM SP_REL_TEMPLATE_WH_OWNER TT
     WHERE TT.OWNER_ID = I_OWNER_ID
       AND TT.WAREHOUSE_ID IS NULL;

    IF L_OWNER_CNT > 0 THEN
      SELECT TT.TEMPLATE_NAME,
             (SELECT COUNT(1)
                FROM SP_REL_TEMPLATE_CODE REFCODE
               WHERE REFCODE.TEMPLATE_NAME = TT.TEMPLATE_NAME
                 AND REFCODE.CODE_OID = SP_CODE_OID)
        INTO L_OWNER_TEMPLATE_NAME, L_OWNER_CNT
        FROM SP_REL_TEMPLATE_WH_OWNER TT
       WHERE TT.OWNER_ID = I_OWNER_ID
         AND TT.WAREHOUSE_ID IS NULL;

    END IF;

    IF L_WH_OWNER_TEMPLATE_NAME IS NULL AND L_OWNER_TEMPLATE_NAME IS NULL THEN
      SP_CODE_OID := NULL;
      RETURN FALSE;
    END IF;

    IF L_WH_OWNER_CNT > 0 AND L_OWNER_CNT > 0 THEN
      RETURN FALSE;
    END IF;

    IF L_WH_OWNER_TEMPLATE_NAME IS NOT NULL AND L_WH_OWNER_CNT = 0 THEN
      INSERT INTO SP_REL_TEMPLATE_CODE
        (TEMPLATE_NAME, CODE_OID)
      VALUES
        (L_WH_OWNER_TEMPLATE_NAME, SP_CODE_OID);
      L_BOOL := TRUE;
    END IF;

    IF L_OWNER_TEMPLATE_NAME IS NOT NULL AND L_OWNER_CNT = 0 AND
       (L_OWNER_TEMPLATE_NAME != L_WH_OWNER_TEMPLATE_NAME) THEN
      INSERT INTO SP_REL_TEMPLATE_CODE
        (TEMPLATE_NAME, CODE_OID)
      VALUES
        (L_OWNER_TEMPLATE_NAME, SP_CODE_OID);
      L_BOOL := TRUE;
    END IF;

    RETURN L_BOOL;

  END CHECK_SUP_TEMPLATE;

  -- check tempalte code oid, and then add them if necessary
  FUNCTION GET_TEMPLATE_SP_CODE_OID(WAREHOUSE_ID     IN CM_WAREHOUSE.WAREHOUSE_ID%TYPE,
                                    OWNER_ID         IN CM_COMPANY.COMPANY_ID%TYPE,
                                    SP_ACTUAL_CODE   IN SP_CODE.CODE%TYPE,
                                    SP_CATEGORY_NAME IN SP_CODE.CATEGORY_NAME%TYPE)
    RETURN SP_CODE.OID%TYPE IS
    SP_CODE_OID   SP_CODE.OID%TYPE;
    L_NEED_UPDATE BOOLEAN := FALSE;
  BEGIN
    L_NEED_UPDATE := CHECK_SUP_TEMPLATE(WAREHOUSE_ID,
                                        OWNER_ID,
                                        SP_ACTUAL_CODE,
                                        SP_CATEGORY_NAME,
                                        SP_CODE_OID);

    IF L_NEED_UPDATE THEN
      -- update owner level verstion time
      UPDATE CM_VERSION_TIME T
         SET T.VERSION_TIME = SYSDATE
       WHERE T.SCENARIO = 'VT_SUPPORT_CATEGORY_UPDATE_TIME'
         AND T.KEY = 'OWNER';
    END IF;

    RETURN SP_CODE_OID;
  END GET_TEMPLATE_SP_CODE_OID;

  /*dont consider operator is NONE*/
  FUNCTION GET_COMMON_FILTER_EXP(I_COL_EXP    IN VARCHAR2,
                                 I_OPERATOR   IN VARCHAR2,
                                 I_VALUE      IN VARCHAR2,
                                 I_VALUE_TYPE IN VARCHAR2) RETURN VARCHAR2 IS
    L_SQL        VARCHAR2(10000);
    L_TEMP_VALUE VARCHAR2(4000);
    --filter _value_type
    FILTER_VAL_TYPE_STR          CONSTANT VARCHAR2(60) := 'STRING';
    FILTER_VAL_TYPE_BOOL         CONSTANT VARCHAR2(60) := 'BOOL';
    FILTER_VAL_TYPE_TIME_TO_ZERO CONSTANT VARCHAR2(60) := 'TIME_OFFSET_MILLIS_BASE_ON_ZERO';
    FILTER_VAL_TYPE_TIME_TO_NOW  CONSTANT VARCHAR2(60) := 'TIME_OFFSET_MILLIS_BASE_ON_NOW';

  BEGIN
    L_SQL := I_COL_EXP;
    /*specal logic of _customized_fields  */

    CASE I_VALUE_TYPE
      WHEN FILTER_VAL_TYPE_STR THEN
        IF I_OPERATOR IN ('NOT_IN', 'IN') THEN
          L_TEMP_VALUE := '''' || REPLACE(REPLACE(I_VALUE, '''', ''''''),
                                          ',',
                                          ''',''') || '''';
        ELSE
          L_TEMP_VALUE := '''' || REPLACE(I_VALUE, '''', '''''') || '''';
        END IF;
      WHEN FILTER_VAL_TYPE_BOOL THEN
        L_TEMP_VALUE := REPLACE(REPLACE(I_VALUE, 'True', 1), 'False', 0);
      WHEN FILTER_VAL_TYPE_TIME_TO_ZERO THEN
        -- base on sysdate's zero clock
        L_TEMP_VALUE := 'TRUNC(SYSDATE) +' || '( ' || I_VALUE ||
                        '/1000/60/60/24)';
      WHEN FILTER_VAL_TYPE_TIME_TO_NOW THEN
        --base on execute time
        L_TEMP_VALUE := 'SYSDATE +' || '( ' || I_VALUE || '/1000/60/60/24)';
      ELSE
        L_TEMP_VALUE := I_VALUE;
    END CASE;

    CASE I_OPERATOR
      WHEN 'LESS_THAN_OR_EQUAL' THEN
        L_SQL := L_SQL || ' <= ' || L_TEMP_VALUE;
      WHEN 'EQUAL' THEN
        L_SQL := L_SQL || '  = ' || L_TEMP_VALUE;
      WHEN 'NOT_IN' THEN
        L_SQL := '(' || L_SQL || ' NOT IN (' || L_TEMP_VALUE || ') OR ' ||
                 L_SQL || ' IS NULL )';
      WHEN 'GREAT_THAN' THEN
        L_SQL := L_SQL || ' > ' || L_TEMP_VALUE;
      WHEN 'NOT_EQUAL' THEN
        L_SQL := '(' || L_SQL || ' <> ' || L_TEMP_VALUE || ' OR ' || L_SQL ||
                 ' IS NULL )';
      WHEN 'IN' THEN
        L_SQL := L_SQL || ' IN (' || L_TEMP_VALUE || ')';
      WHEN 'CONTAIN' THEN
        L_SQL := L_SQL || ' LIKE  ' || L_TEMP_VALUE || '||''%''';
      WHEN 'NOT_CONTAIN' THEN
        L_SQL := '(' || L_SQL || ' NOT LIKE  ' || L_TEMP_VALUE || '||''%''' ||
                 ' OR ' || L_SQL || ' IS NULL )';
      WHEN 'GREAT_THAN_OR_EQUAL' THEN
        L_SQL := L_SQL || ' >= ' || L_TEMP_VALUE;
      WHEN 'LESS_THAN' THEN
        L_SQL := L_SQL || ' < ' || L_TEMP_VALUE;
      ELSE
        NULL;
    END CASE;
    RETURN L_SQL;
  END GET_COMMON_FILTER_EXP;

  FUNCTION SUBARRAY(I_SRC_STRARRAY IN STRARRAY,
                    I_FIRST        IN NUMBER,
                    I_LAST         IN NUMBER) RETURN STRARRAY IS
    L_RTN  STRARRAY;
    L_SIZE NUMBER;
  BEGIN
    L_SIZE := I_LAST - I_FIRST + 1;
    IF I_FIRST < 1 OR I_LAST > I_SRC_STRARRAY.COUNT OR L_SIZE < 1 THEN
      RAISE_APPLICATION_ERROR(-20000, 'Array index out of bound', TRUE);
    END IF;

    SELECT VAL
      BULK COLLECT
      INTO L_RTN
      FROM (SELECT VAL
              FROM (SELECT COLUMN_VALUE VAL, ROWNUM RN
                      FROM TABLE(I_SRC_STRARRAY) SRC
                     WHERE ROWNUM < I_LAST + 1)
             WHERE RN > I_FIRST - 1);

    RETURN L_RTN;
  END SUBARRAY;

  FUNCTION SUBARRAY(I_SRC_STRARRAY IN NUMARRAY,
                    I_FIRST        IN NUMBER,
                    I_LAST         IN NUMBER) RETURN NUMARRAY IS
    L_RTN  NUMARRAY;
    L_SIZE NUMBER;
  BEGIN
    L_SIZE := I_LAST - I_FIRST + 1;
    IF I_FIRST < 1 OR I_LAST > I_SRC_STRARRAY.COUNT OR L_SIZE < 1 THEN
      RAISE_APPLICATION_ERROR(-20000, 'Array index out of bound', TRUE);
    END IF;

    SELECT VAL
      BULK COLLECT
      INTO L_RTN
      FROM (SELECT VAL
              FROM (SELECT COLUMN_VALUE VAL, ROWNUM RN
                      FROM TABLE(I_SRC_STRARRAY) SRC
                     WHERE ROWNUM < I_LAST + 1)
             WHERE RN > I_FIRST - 1);

    RETURN L_RTN;
  END SUBARRAY;

END WOS_COMMON_PKG;
/
