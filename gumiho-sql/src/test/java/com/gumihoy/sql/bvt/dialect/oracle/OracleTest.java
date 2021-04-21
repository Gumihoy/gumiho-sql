package com.gumihoy.sql.bvt.dialect.oracle;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleTest {

    @Test
    public void test_0() {
        String sql = "   SELECT /* tpostUnityboardDAOMap.xml 엄소희 2007.09.13 4 */                 RNUM                 , ONUM                 , BRD_INFO_NO                 , BRD_INFO_SBJCT                 , TO_CHAR(CREATE_DT,'YYYYMMDD') CREATE_DT                 , RDCNT                 , RPLY_CNT                 , RECM_CNT                 , DISP_YN                 , SVC_CLF                 , EDU_CORS_GR_CD                 , BRD_INFO_URL                 , BRD_INFO_CLF_NO                 , TO_CHAR(EDU_DY,'YYYYMMDD') EDU_DY                 , CHRG_YN                 , FNSH_YN                 , TOTAL_COUNT                 , MEM_NM                 , MEM_ID                 , ANSWER_YN         FROM (             SELECT                 rownum RNUM                 , (x.total_count + 1 - rownum) ONUM                 , x.BRD_INFO_NO                 , x.BRD_INFO_SBJCT                 , x.CREATE_DT                 , x.RDCNT                 , x.RPLY_CNT                 , x.RECM_CNT                 , x.DISP_YN                 , x.SVC_CLF                 , x.EDU_CORS_GR_CD                 , x.BRD_INFO_URL                 , x.BRD_INFO_CLF_NO                 , x.EDU_DY                 , x.CHRG_YN                 , x.FNSH_YN                 , x.TOTAL_COUNT                 , x.MEM_NM                 , x.MEM_ID                 , x.ANSWER_YN             FROM             (                 SELECT DISTINCT                     a.BRD_INFO_NO                     , a.BRD_INFO_SBJCT                     , a.CREATE_DT                     , a.RDCNT                     , a.RPLY_CNT                     , a.RECM_CNT                     , a.DISP_YN                     , a.SVC_CLF                     , a.EDU_CORS_GR_CD                     , a.BRD_INFO_URL                     , a.BRD_INFO_CLF_NO                     , a.EDU_DY                     , a.CHRG_YN                     , a.FNSH_YN                     , COUNT(DISTINCT a.BRD_INFO_NO) over() TOTAL_COUNT                     , b.EMP_NM MEM_NM                     , b.EMP_ID MEM_ID                     , NVL((SELECT 'Y' FROM cm_unity_brd_info d  WHERE d.HGRNK_BRD_INFO_NO = a.BRD_INFO_NO AND rownum = 1),'N') ANSWER_YN                 FROM                     cm_unity_brd_info a                     , sy_emp b                     , cm_week_poplt_prd c                 WHERE                     a.UNITY_BRD_NO = ?                     AND a.BRD_INFO_CLF = '03'                     AND a.DEL_YN = 'N'                     AND a.HGRNK_BRD_INFO_NO = 0                     AND a.CREATE_NO = b.EMP_NO                     AND a.BRD_INFO_NO = c.BRD_INFO_NO                                              AND                             TO_CHAR(a.CREATE_DT,'YYYYMMDD') BETWEEN TO_CHAR(TO_DATE(?),'YYYYMMDD') AND TO_CHAR(TO_DATE(?),'YYYYMMDD')                                                  AND                             a.DISP_YN = ?                                                                                                                                           ORDER BY '' ''    ) x   )   WHERE    1 = 1                                      AND ? <= RNUM AND RNUM < ?           \n";
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals(sql, format);
    }


}
