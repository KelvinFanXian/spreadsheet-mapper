package spreadsheet.mapper.w2o.validation.validator.cell;

import org.testng.annotations.Test;
import spreadsheet.mapper.TestFactory;
import spreadsheet.mapper.model.core.Cell;
import spreadsheet.mapper.model.meta.FieldMeta;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by hanwen on 2017/1/4.
 */
public class LocalDateTimeValidatorTest {

  @Test
  public void testCustomValidate() throws Exception {

    Map<String, FieldMeta> fieldMetaMap = TestFactory.createFieldMetaMap();
    CellValidator[] validator = new LocalDateTimeValidator()
        .matchField("test.localDateTime").pattern("yyyy-MM-dd HH:mm:ss").end();

    assertEquals(validator.length, 1);

    Map<String, Cell> cellMap1 = TestFactory.createCellMap1();
    assertTrue(validator[0].valid(cellMap1.get("test.localDateTime"), fieldMetaMap.get("test.localDateTime")));

    Map<String, Cell> cellMap2 = TestFactory.createErrorCellMap();
    assertFalse(validator[0].valid(cellMap2.get("test.localDateTime"), fieldMetaMap.get("test.localDateTime")));
  }

}