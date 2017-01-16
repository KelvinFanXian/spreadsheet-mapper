package spreadsheet.mapper.o2w.compose.converter;

import org.testng.annotations.Test;
import spreadsheet.mapper.TestBean;
import spreadsheet.mapper.TestFactory;
import spreadsheet.mapper.model.core.RowBean;
import spreadsheet.mapper.model.meta.FieldMeta;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Created by hanwen on 2017/1/4.
 */
public class BooleanExtractorTest {

  @Test
  public void testGetStringValue() throws Exception {

    Map<String, FieldMeta> fieldMetaMap = TestFactory.createFieldMetaMap();
    FieldMeta fieldMeta1 = fieldMetaMap.get("test.boolean1");
    FieldMeta fieldMeta2 = fieldMetaMap.get("test.boolean2");

    BooleanConverter<TestBean> extractor1 = new BooleanConverter<TestBean>().trueString("pass").falseString("failure").matchField("test.boolean1");
    BooleanConverter<TestBean> extractor2 = new BooleanConverter<TestBean>().trueString("pass").falseString("failure").matchField("test.boolean2");

    TestBean testBean1 = TestFactory.createBean1();
    String s11 = extractor1.getStringValue(testBean1, new RowBean(), fieldMeta1);
    assertEquals(s11, "pass");

    String s12 = extractor2.getStringValue(testBean1, new RowBean(), fieldMeta2);
    assertEquals(s12, "failure");

    TestBean testBean2 = TestFactory.createBean2();
    String s21 = extractor1.getStringValue(testBean2, new RowBean(), fieldMeta1);
    assertEquals(s21, "failure");

    String s22 = extractor2.getStringValue(testBean2, new RowBean(), fieldMeta2);
    assertNull(s22);
  }

}