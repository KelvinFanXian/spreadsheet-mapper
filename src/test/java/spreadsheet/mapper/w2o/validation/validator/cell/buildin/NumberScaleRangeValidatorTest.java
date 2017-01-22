package spreadsheet.mapper.w2o.validation.validator.cell.buildin;

import org.testng.annotations.Test;
import spreadsheet.mapper.TestFactory;
import spreadsheet.mapper.model.core.Cell;
import spreadsheet.mapper.model.meta.FieldMeta;

import java.util.Map;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by hanwen on 2017/1/4.
 */
public class NumberScaleRangeValidatorTest {

  @Test
  public void testCustomValidate() throws Exception {

    Map<String, FieldMeta> fieldMetaMap = TestFactory.createFieldMetaMap();

    NumberScaleRangeValidator validator0 = new NumberScaleRangeValidator();
    validator0.matchField("float1");
    validator0.gte(1);
    validator0.lte(10);
    NumberScaleRangeValidator validator1 = new NumberScaleRangeValidator();
    validator1.matchField("float2");
    validator1.gte(1);
    validator1.lte(10);
    NumberScaleRangeValidator validator2 = new NumberScaleRangeValidator();
    validator2.matchField("double1");
    validator2.gte(1);
    validator2.lte(10);
    NumberScaleRangeValidator validator3 = new NumberScaleRangeValidator();
    validator3.matchField("double2");
    validator3.gte(1);
    validator3.lte(10);

    Map<String, Cell> cellMap1 = TestFactory.createCellMap1();
    assertTrue(validator0.valid(cellMap1.get("float1"), fieldMetaMap.get("float1")));
    assertTrue(validator1.valid(cellMap1.get("float2"), fieldMetaMap.get("float2")));
    assertFalse(validator2.valid(cellMap1.get("double1"), fieldMetaMap.get("double1")));
    assertFalse(validator3.valid(cellMap1.get("double2"), fieldMetaMap.get("double2")));

    Map<String, Cell> cellMap2 = TestFactory.createErrorCellMap();
    assertFalse(validator0.valid(cellMap2.get("float1"), fieldMetaMap.get("float1")));
    assertTrue(validator1.valid(cellMap2.get("float2"), fieldMetaMap.get("float2")));
    assertFalse(validator2.valid(cellMap2.get("double1"), fieldMetaMap.get("double1")));
    assertTrue(validator3.valid(cellMap2.get("double2"), fieldMetaMap.get("double2")));
  }

}