package spreadsheet.mapper.w2f.write;

import spreadsheet.mapper.model.core.Workbook;

import java.io.OutputStream;

/**
 * Created by hanwen on 2016/12/30.
 */
public interface WorkbookWriter {

  /**
   * write {@link Workbook} to supplied output stream
   *
   * @param workbook    {@link Workbook}
   * @param outputStream notice close the stream
   */
  void write(Workbook workbook, OutputStream outputStream);
}
