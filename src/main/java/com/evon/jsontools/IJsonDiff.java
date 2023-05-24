package com.evon.jsontools;

public interface IJsonDiff {
    /**
     * 比对两个json，返回差值
     * 如
     * aJson={"user":{"name":"evon","phone":"13265479809"}}
     * bJson={"user":{"name":"evon","phone":"13265479810"}}
     * 返回值={"user":{"phone":"13265479810"}}
     * @param aJson
     * @param bJson
     * @param ignoreFields 对象嵌套时以'.'分割，如'user.name'
     * @return
     */
    String subtract(String aJson, String bJson, String... ignoreFields);


    /**
     * 合并两个json，返回并集
     * 如
     * aJson={"user":{"name":"evon","phone":"13265479809"}}
     * bJson={"user":{"name":"evon","phone":"13265479810","age":20}}
     * 返回值={"user":{"name":"evon","phone":"13265479809","age":20}}
     * @param aJson
     * @param bJson
     * @return
     */
    String addNotCover(String aJson, String bJson);

    /**
     * 合并两个json，返回并集
     * 如
     * aJson={"user":{"name":"evon","phone":"13265479809"}}
     * bJson={"user":{"name":"evon","phone":"13265479810","age":20}}
     * 返回值={"user":{"name":"evon","phone":"13265479810","age":20}}
     * @param aJson
     * @param bJson
     * @return
     */
    String addAndCover(String aJson, String bJson);

    /**
     * 比对两个json，key对应值相同标识true，key对应值不相同标识false
     * 如
     * aJson={"user":{"name":"evon","phone":"13265479809"}}
     * bJson={"user":{"name":"evon","phone":"13265479810"}}
     * 返回值={"user":{"name":true,"phone":false}}
     * @param aJson
     * @param bJson
     * @param ignoreFields 对象嵌套时以'.'分割，如'user.name'
     * @return
     */
    String compare(String aJson, String bJson, String... ignoreFields);

    /**
     * 比对两个json，key对应值相同标识true，key对应值不相同标识false
     * 如
     * aJson={"user":{"name":"evon","phone":"13265479809"}}
     * bJson={"user":{"name":"evon","phone":"13265479810"}}
     * 返回值={"user":{"phone":false}}
     * @param aJson
     * @param bJson
     * @param ignoreFields 对象嵌套时以'.'分割，如'user.name'
     * @return
     */
    String compareReturnFalse(String aJson, String bJson, String... ignoreFields);

    /**
     * 比对两个json，key对应值相同标识空，key对应值不相同标识{旧值}->{新值}
     * aJson={"user":{"name":"evon","phone":"13265479809"}}
     * bJson={"user":{"name":"evon","phone":"13265479810"}}
     * 返回值={"user":{"phone":"13265479809->13265479810"}}
     * @param aJson
     * @param bJson
     * @param ignoreFields
     * @return
     */
    String compareReturnValue(String aJson, String bJson, String... ignoreFields);

}
