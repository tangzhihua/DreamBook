package cn.retech.custom_control;

public interface ICustomControlDelegate {

  /**
   * @param contorl 控件对象本身
   * @param actionTypeEnum 动作类型枚举
   */
  public void customControlOnAction(final Object contorl, final Object actionTypeEnum);

}
