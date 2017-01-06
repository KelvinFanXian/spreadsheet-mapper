package spreadsheet.mapper.w2f;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spreadsheet.mapper.Constants;
import spreadsheet.mapper.model.core.Cell;
import spreadsheet.mapper.model.core.Row;
import spreadsheet.mapper.model.core.Sheet;
import spreadsheet.mapper.model.core.Workbook;

import java.io.IOException;
import java.io.OutputStream;

/**
 * workbook to excel writer decorator
 * <p>
 * Created by hanwen on 2016/12/30.
 */
public class Workbook2ExcelWriter implements WorkbookWriter {

  private static final Logger LOGGER = LoggerFactory.getLogger(Workbook2ExcelWriter.class);

  private org.apache.poi.ss.usermodel.Workbook poiWorkbook;

  /**
   * workbook to excel writer use {@link SXSSFWorkbook} or {@link HSSFWorkbook}
   *
   * @param xlsx true use {@link SXSSFWorkbook} else use {@link HSSFWorkbook}
   */
  public Workbook2ExcelWriter(boolean xlsx) {
    // sxssf keep 100 rows in memory, exceeding rows will be flushed to disk
    poiWorkbook = xlsx ? new SXSSFWorkbook(100) : new HSSFWorkbook();
  }

  public void write(Workbook workbook, OutputStream outputStream) {
    if (workbook == null) {
      return;
    }

    for (Sheet excelSheet : workbook.getSheets()) {

      org.apache.poi.ss.usermodel.Sheet sheet = createSheet(poiWorkbook, excelSheet);

      for (Row excelRow : excelSheet.getRows()) {

        org.apache.poi.ss.usermodel.Row row = createRow(sheet, excelRow);

        for (Cell excelCell : excelRow.getCells()) {
          createCell(row, excelCell);
        }
      }
    }

    try {
      poiWorkbook.write(outputStream);

      if (poiWorkbook instanceof SXSSFWorkbook) {
        ((SXSSFWorkbook) poiWorkbook).dispose();
      }
    } catch (IOException e) {
      LOGGER.error(ExceptionUtils.getStackTrace(e));
      throw new WorkbookWriteException(e);
    } finally {

      try {
        poiWorkbook.close();
      } catch (IOException e) {
        LOGGER.error(ExceptionUtils.getStackTrace(e));
      }
    }

  }

  private org.apache.poi.ss.usermodel.Sheet createSheet(org.apache.poi.ss.usermodel.Workbook workbook, Sheet sheet) {
    String sheetName = sheet.getName();

    org.apache.poi.ss.usermodel.Sheet poiSheet;
    if (StringUtils.isBlank(sheetName)) {
      poiSheet = workbook.createSheet();
    } else {
      poiSheet = workbook.createSheet(sheetName);
    }

    return poiSheet;
  }

  private org.apache.poi.ss.usermodel.Row createRow(org.apache.poi.ss.usermodel.Sheet sheet, Row excelRow) {
    return sheet.createRow(excelRow.getIndex() - 1);
  }

  private void createCell(org.apache.poi.ss.usermodel.Row row, Cell excelCell) {
    String value = excelCell.getValue();
    org.apache.poi.ss.usermodel.Cell cell = row.createCell(excelCell.getIndex() - 1, org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
    cell.setCellValue(value == null ? Constants.EMPTY_VALUE : value);
  }

}
