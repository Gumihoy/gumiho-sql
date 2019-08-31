package com.gumihoy.sql.bvt.translate.oracle.postgresql.version_9_6.datatype.media;

import com.gumihoy.sql.enums.DBVersion;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.translate.result.SQLTransformResult;
import com.gumihoy.sql.util.SQLTransformUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-30.
 */
public class Oracle2PostgreSQLMediaDataTypeTest_0 {


    @Test
    public void test_0() {
        String sql = "CREATE TABLE t\n" +
                "(\n" +
                "  c1 ORDAudio ,\n" +
                "  c2 SYS.ORDAudio,\n" +
                "  c3 ORDImage,\n" +
                "  c4 SYS.ORDImage,\n" +
                "  c5 ORDVideo,\n" +
                "  c6 SYS.ORDVideo ,\n" +
                "  c7 ORDDoc ,\n" +
                "  c8 SYS.ORDDoc,\n" +
                "  c9 ORDDicom,\n" +
                "  c10 SYS.ORDDicom,\n" +
                "  c11 SI_StillImage,\n" +
                "  c12 SYS.SI_StillImage ,\n" +
                "  c13 SI_AverageColor ,\n" +
                "  c14 SYS.SI_AverageColor,\n" +
                "  c15 SI_PositionalColor,\n" +
                "  c16 SYS.SI_PositionalColor,\n" +
                "  c17 SI_ColorHistogram,\n" +
                "  c18 SYS.SI_ColorHistogram ,\n" +
                "  c19 SI_Texture ,\n" +
                "  c20 SYS.SI_Texture,\n" +
                "  c21 SI_FeatureList,\n" +
                "  c22 SYS.SI_FeatureList,\n" +
                "  c23 SI_Color,\n" +
                "  c24 SYS.SI_Color\n" +
                ");";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.PostgreSQL.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToPostgreSQL(sql, config);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("CREATE TABLE t (\n" +
                "\tc1 ORDAudio,\n" +
                "\tc2 SYS.ORDAudio,\n" +
                "\tc3 ORDImage,\n" +
                "\tc4 SYS.ORDImage,\n" +
                "\tc5 ORDVideo,\n" +
                "\tc6 SYS.ORDVideo,\n" +
                "\tc7 ORDDoc,\n" +
                "\tc8 SYS.ORDDoc,\n" +
                "\tc9 ORDDicom,\n" +
                "\tc10 SYS.ORDDicom,\n" +
                "\tc11 SI_StillImage,\n" +
                "\tc12 SYS.SI_StillImage,\n" +
                "\tc13 SI_AverageColor,\n" +
                "\tc14 SYS.SI_AverageColor,\n" +
                "\tc15 SI_PositionalColor,\n" +
                "\tc16 SYS.SI_PositionalColor,\n" +
                "\tc17 SI_ColorHistogram,\n" +
                "\tc18 SYS.SI_ColorHistogram,\n" +
                "\tc19 SI_Texture,\n" +
                "\tc20 SYS.SI_Texture,\n" +
                "\tc21 SI_FeatureList,\n" +
                "\tc22 SYS.SI_FeatureList,\n" +
                "\tc23 SI_Color,\n" +
                "\tc24 SYS.SI_Color\n" +
                ");", result.targetSql);
    }
}
