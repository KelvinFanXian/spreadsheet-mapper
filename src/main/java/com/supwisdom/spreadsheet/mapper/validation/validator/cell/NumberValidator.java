package com.supwisdom.spreadsheet.mapper.validation.validator.cell;

import com.supwisdom.spreadsheet.mapper.model.core.Cell;
import org.apache.commons.lang3.math.NumberUtils;
import com.supwisdom.spreadsheet.mapper.model.meta.FieldMeta;

/**
 * number validator
 * <p>
 * Created by hanwen on 2017/1/11.
 */
public class NumberValidator extends CustomSingleCellValidatorAdapter<NumberValidator> {

  @Override
  protected boolean customValid(Cell cell, FieldMeta fieldMeta) {
    return NumberUtils.isNumber(cell.getValue());
  }
}
