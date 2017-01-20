package spreadsheet.mapper.w2o.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import spreadsheet.mapper.AssertUtil;
import spreadsheet.mapper.TestBean;
import spreadsheet.mapper.TestFactory;
import spreadsheet.mapper.model.core.Workbook;
import spreadsheet.mapper.model.core.WorkbookBean;
import spreadsheet.mapper.model.meta.WorkbookMeta;
import spreadsheet.mapper.model.meta.WorkbookMetaBean;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static spreadsheet.mapper.w2o.process.DefaultSheetProcessHelperTest.addSetter;

/**
 * Created by hanwen on 2017/1/19.
 */
@Test(dependsOnGroups = "sheetProcessHelperTest")
public class DefaultWorkbookProcessHelperTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultWorkbookProcessHelperTest.class);

  @BeforeClass
  public void before() {
    LOGGER.debug("-------------------starting test workbook process helper-------------------");
  }

  @Test
  public void testProcess() throws Exception {

    WorkbookProcessHelper workbookProcessHelper = new DefaultWorkbookProcessHelper();

    SheetProcessHelper<TestBean> sheetProcessHelper =
        new DefaultSheetProcessHelper<TestBean>()
            .setObjectFactory(new DefaultSheetProcessHelperTest.TestBeanObjectFactory());

    addSetter(sheetProcessHelper);

    Workbook workbook = new WorkbookBean();
    workbook.addSheet(TestFactory.createSheet());
    workbook.addSheet(TestFactory.createSheet());

    WorkbookMeta workbookMeta = new WorkbookMetaBean();
    workbookMeta.addSheetMeta(TestFactory.createSheetMeta(true));
    workbookMeta.addSheetMeta(TestFactory.createSheetMeta(true));

    List<List> list = workbookProcessHelper
        .addSheetProcessHelper(sheetProcessHelper)
        .addSheetProcessHelper(sheetProcessHelper)
        .process(workbook, workbookMeta);

    assertEquals(list.size(), 2);

    for (List sub : list) {
      assertEquals(sub.size(), 2);

      AssertUtil.assertTestBean1Equals((TestBean) sub.get(0));
      AssertUtil.assertTestBean2Equals((TestBean) sub.get(1));
    }
  }

}