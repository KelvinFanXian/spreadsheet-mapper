package spreadsheet.mapper.w2o.process.setter;

import spreadsheet.mapper.model.meta.FieldMeta;
import spreadsheet.mapper.w2o.process.WorkbookProcessException;
import spreadsheet.mapper.model.core.Cell;
import spreadsheet.mapper.utils.FieldUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * boolean field value setter
 * <p>
 * Created by hanwen on 5/3/16.
 */
public class BooleanValueSetter<T> extends FieldValueSetterAdapter<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(BooleanValueSetter.class);

  private Set<String> trueStrings = new HashSet<>();

  private Set<String> falseStrings = new HashSet<>();

  public BooleanValueSetter(String[] trueStrings, String[] falseStrings, String matchField) {
    super(matchField);
    if (trueStrings != null) {
      Collections.addAll(this.trueStrings, trueStrings);
    }
    if (falseStrings != null) {
      Collections.addAll(this.falseStrings, falseStrings);
    }
  }

  @Override
  public void set(T object, Cell cell, FieldMeta fieldMeta) {
    try {
      String stringValue = cell.getValue();
      Boolean booleanValue = null;

      if (trueStrings.contains(stringValue)) {
        booleanValue = Boolean.TRUE;
      } else if (falseStrings.contains(stringValue)) {
        booleanValue = Boolean.FALSE;
      }

      BeanUtils.setProperty(object, FieldUtils.detectRealFieldName(fieldMeta), booleanValue);
    } catch (Exception e) {
      LOGGER.error(ExceptionUtils.getStackTrace(e));
      throw new WorkbookProcessException(e);
    }
  }
}
