package com.supwisdom.spreadsheet.mapper.w2o.compose.setter;

import com.supwisdom.spreadsheet.mapper.TestBean;
import org.testng.annotations.Test;
import com.supwisdom.spreadsheet.mapper.TestFactory;
import com.supwisdom.spreadsheet.mapper.model.core.Cell;
import com.supwisdom.spreadsheet.mapper.model.meta.FieldMeta;
import com.supwisdom.spreadsheet.mapper.w2o.param.BooleanParam;

import java.util.Map;

import static org.testng.Assert.*;

/**
 * Created by hanwen on 2017/1/5.
 */
public class BooleanSetterTest {

  @Test
  public void testSet() throws Exception {

    Map<String, FieldMeta> fieldMetaMap = TestFactory.createFieldMetaMap();

    Map<String, Cell> cellMap1 = TestFactory.createCellMap1();
    Map<String, Cell> cellMap2 = TestFactory.createErrorCellMap();

    // touch register
    new BeanUtilsSetter();

    BooleanSetter<TestBean> setter1 = new BooleanSetter<TestBean>()
        .matchField("boolean1")
        .param(
            new BooleanParam()
                .supportedTrue("pass")
                .supportedFalse("failure")
        );
    BooleanSetter<TestBean> setter2 = new BooleanSetter<TestBean>()
        .param(
            new BooleanParam()
                .supportedFalse("failure")
                .supportedTrue("pass")
        )
        .matchField("boolean2");

    TestBean testBean1 = new TestBean();
    setter1.set(testBean1, cellMap1.get("boolean1"), fieldMetaMap.get("boolean1"));
    setter2.set(testBean1, cellMap1.get("boolean2"), fieldMetaMap.get("boolean2"));

    assertTrue(testBean1.isBoolean1());
    assertFalse(testBean1.getBoolean2());


    TestBean testBean2 = new TestBean();
    setter1.set(testBean2, cellMap2.get("boolean1"), fieldMetaMap.get("boolean1"));
    setter2.set(testBean2, cellMap2.get("boolean2"), fieldMetaMap.get("boolean2"));

    assertFalse(testBean2.isBoolean1());
    assertNull(testBean2.getBoolean2());
  }

}