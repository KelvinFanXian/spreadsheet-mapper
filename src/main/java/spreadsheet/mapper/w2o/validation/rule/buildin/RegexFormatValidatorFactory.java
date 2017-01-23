package spreadsheet.mapper.w2o.validation.rule.buildin;

import spreadsheet.mapper.w2o.validation.rule.DependencyRuleParam;
import spreadsheet.mapper.w2o.validation.rule.SingleCellValidatorFactory;
import spreadsheet.mapper.w2o.validation.validator.cell.buildin.RegexFormatValidator;

/**
 * Created by hanwen on 2017/1/22.
 */
public class RegexFormatValidatorFactory implements SingleCellValidatorFactory<RegexFormatValidator> {

  @Override
  public RegexFormatValidator create(DependencyRuleParam param, String matchField) {
    Object additionalParam = param.getAdditionalParam();

    if (!(additionalParam instanceof String)) {
      throw new IllegalArgumentException("the regex format validator additional param not satisfied, need [String]");
    }

    return new RegexFormatValidator()
        .matchField(matchField)
        .errorMessage(param.getErrorMessage())
        .group(param.getGroup())
        .dependsOn(param.getDependsOn().toArray(new String[0]))
        .regex((String) additionalParam);
  }
}
