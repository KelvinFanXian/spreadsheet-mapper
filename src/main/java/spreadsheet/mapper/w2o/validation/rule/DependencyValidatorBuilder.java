package spreadsheet.mapper.w2o.validation.rule;

import spreadsheet.mapper.w2o.validation.validator.cell.DependencyValidator;
import spreadsheet.mapper.w2o.validation.validator.cell.SingleCellValidator;

import java.util.List;

/**
 * dependency validator builder
 * <p>
 * Created by hanwen on 2017/1/23.
 */
public interface DependencyValidatorBuilder {

  /**
   * start build a single cell validate rule
   *
   * @param name rule name
   * @return {@link RuleBuilder}
   * @see DependencyValidatorFactoryRegisterer
   * @see spreadsheet.mapper.w2o.validation.validator.cell.SingleCellValidator
   */
  RuleBuilder single(String name);

  /**
   * start build a multi cell validate rule
   *
   * @param name rule name
   * @return {@link RuleBuilder}
   * @see DependencyValidatorFactoryRegisterer
   * @see spreadsheet.mapper.w2o.validation.validator.cell.MultiCellValidator
   */
  RuleBuilder multi(String name);

  /**
   * build validators from supplied rules
   *
   * @return {@link SingleCellValidator}
   */
  List<DependencyValidator> build();

  /**
   * validate rule builder
   */
  interface RuleBuilder {

    /**
     * @param matchFields {@link SingleCellValidator#getMatchField()}
     * @return {@link RuleBuilder}
     */
    RuleBuilder matchFields(String... matchFields);

    /**
     * if empty default is field
     *
     * @param group {@link SingleCellValidator#getGroup()}
     * @return {@link RuleBuilder}
     */
    RuleBuilder group(String group);

    /**
     * @param dependsOn {@link SingleCellValidator#getDependsOn()}
     * @return {@link RuleBuilder}
     */
    RuleBuilder dependsOn(String... dependsOn);

    /**
     * @param errorMessage {@link SingleCellValidator#getErrorMessage()}
     * @return {@link RuleBuilder}
     */
    RuleBuilder errorMessage(String errorMessage);

    /**
     * @param additionalParam the additional param validator need
     * @return {@link RuleBuilder}
     */
    RuleBuilder param(Object additionalParam);

    /**
     * finish a rule
     *
     * @return {@link DependencyValidatorBuilder}
     */
    DependencyValidatorBuilder end();
  }
}
