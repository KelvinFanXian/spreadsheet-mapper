package spreadsheet.mapper.w2o.process.setter.buildin;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spreadsheet.mapper.model.core.Cell;
import spreadsheet.mapper.model.meta.FieldMeta;
import spreadsheet.mapper.utils.FieldUtils;
import spreadsheet.mapper.w2o.process.WorkbookProcessException;
import spreadsheet.mapper.w2o.process.setter.FieldSetterAdapter;

/**
 * local date field value setter
 * <p>
 * Created by hanwen on 5/3/16.
 */
public class LocalDateSetter<T> extends FieldSetterAdapter<T, LocalDateSetter<T>> {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateSetter.class);

  private String pattern;

  public LocalDateSetter<T> pattern(String pattern) {
    this.pattern = pattern;
    return getThis();
  }

  @Override
  protected LocalDateSetter<T> getThis() {
    return this;
  }

  @Override
  public void customSet(T object, Cell cell, FieldMeta fieldMeta) {
    try {
      DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern);
      String value = cell.getValue();
      String fieldName = FieldUtils.detectRealFieldName(fieldMeta);

      LocalDate localDate = null;
      try {
        localDate = dateTimeFormatter.parseLocalDate(value);
      } catch (IllegalArgumentException e) {
        LOGGER.debug("{} format not valid", value);
      }

      PropertyUtils.setProperty(object, fieldName, localDate);
    } catch (Exception e) {
      LOGGER.error(ExceptionUtils.getStackTrace(e));
      throw new WorkbookProcessException(e);
    }
  }
}
