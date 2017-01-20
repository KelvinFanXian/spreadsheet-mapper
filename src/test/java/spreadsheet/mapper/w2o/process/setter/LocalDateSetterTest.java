package spreadsheet.mapper.w2o.process.setter;

import org.joda.time.LocalDate;
import org.testng.annotations.Test;
import spreadsheet.mapper.TestBean;
import spreadsheet.mapper.TestFactory;
import spreadsheet.mapper.model.core.Cell;
import spreadsheet.mapper.model.meta.FieldMeta;

import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * Created by hanwen on 2017/1/5.
 */
public class LocalDateSetterTest {

  @Test
  public void testSet() throws Exception {

    Map<String, FieldMeta> fieldMetaMap = TestFactory.createFieldMetaMap();

    Map<String, Cell> cellMap1 = TestFactory.createCellMap1();
    Map<String, Cell> cellMap2 = TestFactory.createErrorCellMap();

    LocalDateSetter<TestBean> setter1 = new LocalDateSetter<TestBean>().matchField("localDate").pattern("yyyy-MM-dd");
    LocalDateSetter<TestBean> setter2 = new LocalDateSetter<TestBean>().pattern("yyyy/MM/dd").matchField("localDate");

    TestBean testBean1 = new TestBean();
    setter1.set(testBean1, cellMap1.get("localDate"), fieldMetaMap.get("localDate"));

    TestBean testBean2 = new TestBean();
    setter2.set(testBean2, cellMap2.get("localDate"), fieldMetaMap.get("localDate"));

    assertEquals(testBean1.getLocalDate(), new LocalDate(1984, 11, 22));
    assertEquals(testBean2.getLocalDate(), new LocalDate(1984, 11, 22));

  }

}