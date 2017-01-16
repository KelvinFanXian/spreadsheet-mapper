package spreadsheet.mapper.w2o.validation.validator.sheet;

import spreadsheet.mapper.model.meta.FieldMeta;
import spreadsheet.mapper.model.meta.SheetMeta;
import spreadsheet.mapper.w2o.validation.validator.cell.CellValidator;
import spreadsheet.mapper.model.core.Sheet;
import spreadsheet.mapper.w2o.process.setter.FieldSetter;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * required field validator
 *
 * all validators matches by field,
 * if field lost means all the ({@link CellValidator} and {@link FieldSetter}) of this field will skip.
 * this validator useful to detect if excel files contains all the fields you want after.
 *
 * eg: class A has fields [A, B...].
 * if you want modify A, B, supplied the {@link RequireFieldValidator#requireFields} as [A, B]. when the excel files lost A or B,
 * this validator will get false.
 * </pre>
 * Created by hanwen on 4/26/16.
 */
public class RequireFieldValidator implements SheetValidator {

  private List<String> requireFields = new ArrayList<>();

  private String errorMessage;

  public RequireFieldValidator(String errorMessage, String[] requireFields) {
    this.errorMessage = errorMessage;
    if (requireFields != null) {
      Collections.addAll(this.requireFields, requireFields);
    }
  }

  @Override
  public String getErrorMessage() {
    return errorMessage;
  }

  @Override
  public boolean valid(Sheet sheet, SheetMeta sheetMeta) {

    List<FieldMeta> fieldMetas = sheetMeta.getFieldMetas();

    List<String> fields = new ArrayList<>();
    for (FieldMeta fieldMeta : fieldMetas) {
      fields.add(fieldMeta.getName());
    }

    return CollectionUtils.subtract(requireFields, fields).isEmpty();

  }

}
