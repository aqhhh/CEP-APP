package com.example.demo.common.enums;

public enum ResultCodeBase implements BaseEnum {

    //成功返回码
    SUCCESS("000000", "成功"),

    //失败返回码
    FAIL("999999", "失败"),

    //用户鉴权
    NOT_LOGIN("100002", "未登录"),
    UNAUTHORIZED("100003","未授权"),
    INVALID_TOKEN("100004","令牌非法"),

    //系统异常
    SYSTEM_EXCEPTION("950001", "系统异常"),
    RUNTIME_EXCEPTION("950002", "运行异常"),
    DATA_EXCEPTION("950003", "数据异常"),
    REMOTE_SERVICE_UNAVAILABLE("950003", "远程服务器不可到达"),

    //业务校验
    REQUEST_ARGUMENTS_ILLEGAL("950100", "请求参数异常"),
    DATA_NOT_EXIST("950101", "数据不存在"),
    PARAM_VALIDATE_ERROR("950102", "参数格式校验错误"),

    //用户模块
    LOGIN_FAIL("150001", "用户名或者密码错误"),
    REGISTER_FAIL("150002", "手机号已被注册"),
    GETTOKEN_FAIL("150003","token不存在"),
    //IS_LOGIN("150004","用户已在其他设备登录");
    ADD_EQUIPMENT_FAIL("150005","添加或更新设备信息失败"),
    USER_NOT_EXIT("150006", "用户不存在"),
    PSW_NOT_MATCH("150007", "原密码错误"),
    RREQUENCY("150008", "操作过于频繁，请稍后再试"),
    SENDSMS_FAIL("150009", "获取验证码失败"),
    CODE_NOT_MATCH("150010", "验证码不正确或已失效"),


    ACC_ADD_FAIL("150101", "添加失败"),
    ACC_EDIT_FAIL("150102", "编辑失败"),
    ACC_DEL_FAIL("150103", "删除失败"),
    PRODUCT_NOT_EXIST("150104", "产品不存在"),
    IS_APPLY("150105", "已参与过该抽奖活动，请勿重复点击"),
    OVER_ENDTIME("150106", "该抽奖活动已结束"),
    NO_MONEY("150107", "虚拟币不足");



    private String code;
    private String desc;

    ResultCodeBase(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
