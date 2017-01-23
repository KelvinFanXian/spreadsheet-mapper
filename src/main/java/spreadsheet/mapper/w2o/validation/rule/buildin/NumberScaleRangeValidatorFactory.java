package spreadsheet.mapper.w2o.validation.rule.buildin;

import spreadsheet.mapper.w2o.param.NumberScaleRangeParam;
import spreadsheet.mapper.w2o.validation.rule.DependencyRuleParam;
import spreadsheet.mapper.w2o.validation.rule.SingleCellValidatorFactory;
import spreadsheet.mapper.w2o.validation.validator.cell.buildin.NumberScaleRangeValidator;

/**
 * Created by hanwen on 2017/1/22.
 */
public class NumberScaleRangeValidatorFactory implements SingleCellValidatorFactory<NumberScaleRangeValidator> {

  @Override
  public NumberScaleRangeValidator create(DependencyRuleParam param, String matchField) {
    Object additionalParam = param.getAdditionalParam();

    if (!(additionalParam instanceof NumberScaleRangeParam)) {
      throw new IllegalArgumentException("the number scale range validator additional param not satisfied, need [NumberScaleRangeParam]");
    }

    return new NumberScaleRangeValidator()
        .matchField(matchField)
        .errorMessage(param.getErrorMessage())
        .group(param.getGroup())
        .dependsOn(param.getDependsOn().toArray(new String[0]))
        .param((NumberScaleRangeParam) additionalParam);
  }
}
