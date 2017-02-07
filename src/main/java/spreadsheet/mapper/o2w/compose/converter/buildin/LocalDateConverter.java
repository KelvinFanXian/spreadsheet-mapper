package spreadsheet.mapper.o2w.compose.converter.buildin;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spreadsheet.mapper.model.core.Cell;
import spreadsheet.mapper.model.meta.FieldMeta;
import spreadsheet.mapper.o2w.compose.WorkbookComposeException;
import spreadsheet.mapper.o2w.compose.converter.FieldConverterAdapter;
import spreadsheet.mapper.utils.FieldUtils;

/**
 * local date text value with supplied pattern converter
 * <p>
 * Created by hanwen on 5/3/16.
 */
public class LocalDateConverter<T> extends FieldConverterAdapter<T, LocalDateConverter<T>> {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateConverter.class);

  private String pattern;

  public LocalDateConverter<T> pattern(String pattern) {
    this.pattern = pattern;
    return getThis();
  }

  @Override
  protected LocalDateConverter<T> getThis() {
    return this;
  }

  @Override
  public String getValue(T object, Cell cell, FieldMeta fieldMeta) {

    try {
      Object value = PropertyUtils.getProperty(object, fieldMeta.getName());

      if (!(value instanceof LocalDate)) {
        return null;
      }

      return ((LocalDate) value).toString(pattern);

    } catch (NestedNullException e) {
      LOGGER.debug("{} is null", fieldMeta.getName());
      return null;
    } catch (Exception e) {
      LOGGER.error(ExceptionUtils.getStackTrace(e));
      throw new WorkbookComposeException(e);
    }
  }
}
