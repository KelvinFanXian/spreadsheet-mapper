package spreadsheet.mapper.w2o.process.setter;


import spreadsheet.mapper.model.meta.FieldMeta;

/**
 * object field value setter
 * <p>
 * Created by hanwen on 15-12-16.
 */
public interface FieldSetter<T> extends Setter<T> {

  /**
   * @return which field this setter matched
   * @see FieldMeta#getName()
   */
  String getMatchField();

}
