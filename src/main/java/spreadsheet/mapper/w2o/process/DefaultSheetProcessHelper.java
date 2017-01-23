package spreadsheet.mapper.w2o.process;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spreadsheet.mapper.model.core.Cell;
import spreadsheet.mapper.model.core.Row;
import spreadsheet.mapper.model.core.Sheet;
import spreadsheet.mapper.model.meta.FieldMeta;
import spreadsheet.mapper.model.meta.SheetMeta;
import spreadsheet.mapper.w2o.process.listener.*;
import spreadsheet.mapper.w2o.process.setter.buildin.BeanUtilsSetter;
import spreadsheet.mapper.w2o.process.setter.FieldSetter;
import spreadsheet.mapper.w2o.process.setter.Setter;

import java.util.*;

/**
 * sheet to objects processor
 * <p>
 * Created by hanwen on 15-12-16.
 */
public class DefaultSheetProcessHelper<T> implements SheetProcessHelper<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSheetProcessHelper.class);

  private ObjectFactory<T> objectFactory;

  private SheetProcessListener<T> sheetProcessListener = new NoopSheetProcessListener<>();

  private RowProcessListener<T> rowProcessListener = new NoopRowProcessListener<>();

  private CellProcessListener<T> cellProcessListener = new NoopCellProcessListener<>();

  private LinkedHashMap<String, FieldSetter<T>> field2setter = new LinkedHashMap<>();

  private Setter<T> defaultSetter = new BeanUtilsSetter<>();

  @Override
  public SheetProcessHelper<T> addFieldSetter(FieldSetter<T> fieldSetter) {
    if (fieldSetter == null) {
      throw new IllegalArgumentException("field setter can not be null");
    }

    String matchField = fieldSetter.getMatchField();
    if (StringUtils.isBlank(matchField)) {
      throw new IllegalArgumentException("field value setter match field can not be null");
    }
    if (field2setter.containsKey(matchField)) {
      throw new IllegalArgumentException("sheet process helper contains multi field setter at field[" + matchField + "]");
    }

    field2setter.put(matchField, fieldSetter);
    return this;
  }

  @Override
  public SheetProcessHelper<T> setObjectFactory(ObjectFactory<T> objectFactory) {
    if (objectFactory == null) {
      throw new IllegalArgumentException("object factory can not be null");
    }

    this.objectFactory = objectFactory;
    return this;
  }

  @Override
  public SheetProcessHelper<T> setSheetProcessorListener(SheetProcessListener<T> sheetProcessListener) {
    if (sheetProcessListener == null) {
      throw new IllegalArgumentException("sheet process listener can not be null");
    }

    this.sheetProcessListener = sheetProcessListener;
    return this;
  }

  @Override
  public SheetProcessHelper<T> setRowProcessorListener(RowProcessListener<T> rowProcessListener) {
    if (rowProcessListener == null) {
      throw new IllegalArgumentException("row process listener can not be null");
    }

    this.rowProcessListener = rowProcessListener;
    return this;
  }

  @Override
  public SheetProcessHelper<T> setCellProcessorListener(CellProcessListener<T> cellProcessListener) {
    if (cellProcessListener == null) {
      throw new IllegalArgumentException("cell process listener can not be null");
    }

    this.cellProcessListener = cellProcessListener;
    return this;
  }

  @Override
  public List<T> process(Sheet sheet, SheetMeta sheetMeta) {
    if (objectFactory == null) {
      throw new WorkbookProcessException("set object factory first");
    }

    List<FieldMeta> fieldMetas = sheetMeta.getFieldMetas();
    Map<Integer, FieldMeta> columnIndex2fieldMeta = buildFieldMetaMap(fieldMetas);

    List<T> dataOfSheet = new ArrayList<>();
    sheetProcessListener.before(sheet, sheetMeta);

    for (int i = sheetMeta.getDataStartRowIndex(); i <= sheet.sizeOfRows(); i++) {
      Row row = sheet.getRow(i);

      T object = objectFactory.create(row, sheetMeta);

      rowProcessListener.before(object, row, sheetMeta);

      for (Cell cell : row.getCells()) {

        FieldMeta fieldMeta = columnIndex2fieldMeta.get(cell.getIndex());

        if (fieldMeta == null) {
          // if missing field meta skip the cell(same column index with field meta)
          LOGGER.debug("no field meta at row index:[" + cell.getIndex() + "], cell value:[" + cell.getValue() + "] ignored");
          continue;
        }

        cellProcessListener.before(object, cell, fieldMeta);

        // use default setter first
        defaultSetter.set(object, cell, fieldMeta);

        FieldSetter<T> fieldSetter = field2setter.get(fieldMeta.getName());

        if (fieldSetter != null) {
          fieldSetter.set(object, cell, fieldMeta);
        }

        cellProcessListener.after(object, cell, fieldMeta);
      }

      rowProcessListener.after(object, row, sheetMeta);

      dataOfSheet.add(object);
    }

    sheetProcessListener.after(dataOfSheet, sheet, sheetMeta);

    return dataOfSheet;
  }

  private Map<Integer, FieldMeta> buildFieldMetaMap(List<FieldMeta> fieldMetas) {
    Map<Integer, FieldMeta> columnIndex2fieldMeta = new HashMap<>();
    for (FieldMeta fieldMeta : fieldMetas) {
      columnIndex2fieldMeta.put(fieldMeta.getColumnIndex(), fieldMeta);
    }
    return columnIndex2fieldMeta;
  }
}