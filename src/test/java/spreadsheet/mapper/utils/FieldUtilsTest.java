package spreadsheet.mapper.utils;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import spreadsheet.mapper.TestBean;
import spreadsheet.mapper.model.meta.FieldMeta;
import spreadsheet.mapper.model.meta.FieldMetaBean;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Created by hanwen on 2017/1/6.
 */
public class FieldUtilsTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(FieldUtilsTest.class);

  @BeforeClass
  public void before() {
    LOGGER.debug("-------------------starting test field utils-------------------");
  }

  @Test
  public void testDetectRealField() throws Exception {

    FieldMeta fieldMeta1 = new FieldMetaBean("int1", 1);
    FieldMeta fieldMeta2 = new FieldMetaBean("test.int1", 1);
    FieldMeta fieldMeta3 = new FieldMetaBean(FieldUtils.BUSINESS_KEY_PREFIX + "test.int1", 1);

    assertEquals(FieldUtils.detectRealFieldName(fieldMeta1), "int1");
    assertEquals(FieldUtils.detectRealFieldName(fieldMeta2), "test.int1");
    assertEquals(FieldUtils.detectRealFieldName(fieldMeta3), "test.int1");
  }

  @Test
  public void testGetFieldType() throws Exception {

    assertEquals(FieldUtils.getFieldType(TestBean.class, new String[]{"int1"}), int.class);
    assertEquals(FieldUtils.getFieldType(TestBean.class, new String[]{"int2"}), Integer.class);
    assertEquals(FieldUtils.getFieldType(TestBean.class, new String[]{"long1"}), long.class);
    assertEquals(FieldUtils.getFieldType(TestBean.class, new String[]{"long2"}), Long.class);
    assertEquals(FieldUtils.getFieldType(TestBean.class, new String[]{"float1"}), float.class);
    assertEquals(FieldUtils.getFieldType(TestBean.class, new String[]{"float2"}), Float.class);
    assertEquals(FieldUtils.getFieldType(TestBean.class, new String[]{"double1"}), double.class);
    assertEquals(FieldUtils.getFieldType(TestBean.class, new String[]{"double2"}), Double.class);
    assertEquals(FieldUtils.getFieldType(TestBean.class, new String[]{"boolean1"}), boolean.class);
    assertEquals(FieldUtils.getFieldType(TestBean.class, new String[]{"boolean2"}), Boolean.class);
    assertEquals(FieldUtils.getFieldType(TestBean.class, new String[]{"string"}), String.class);
    assertEquals(FieldUtils.getFieldType(TestBean.class, new String[]{"bigDecimal"}), BigDecimal.class);
    assertEquals(FieldUtils.getFieldType(TestBean.class, new String[]{"localDate"}), LocalDate.class);
    assertEquals(FieldUtils.getFieldType(TestBean.class, new String[]{"localDateTime"}), LocalDateTime.class);

    assertEquals(FieldUtils.getFieldType(TestBean.class, new String[]{"bigDecimal", "intVal"}), BigInteger.class);

    assertNull(FieldUtils.getFieldType(TestBean.class, new String[]{"test"}));
    assertNull(FieldUtils.getFieldType(TestBean.class, new String[]{"string", "test"}));
    assertNull(FieldUtils.getFieldType(TestBean.class, new String[]{"localDate", "test"}));
    assertNull(FieldUtils.getFieldType(TestBean.class, new String[]{"localDateTime", "test"}));

  }
}