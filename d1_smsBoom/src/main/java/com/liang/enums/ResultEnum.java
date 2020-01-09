package com.liang.enums;

public enum ResultEnum {
	
	ERROR(-1,"操作异常,请联系管理员"),
	USER_NULL(10,"用户不存在"),
	USER_PWD_ERROR(11,"密码错误"),
	USER_NOT_LOGIN(12,"请先登陆"),
	USER_NOT_ROLE(13,"权限不足"),
	USER_NOT_NULL(14,"用户已存在"),
	EMPL_NOT_NULL(15,"该员工已存在,请检查姓名或身份证号"),
	FILE_FORMAT_ERROR(16,"文件格式有误"),
	IS_OVERDUE_0(17,"当前工时正在统计,请勿重复操作"),
	IS_OVERDUE_1(18,"该员工暂无正在统计的工时"),
	WORK_TIME_MIN(19,"要操作的日期小于当前开工日期,操作失败"),
	NOT_MONEY(20,"余额不足"),
	COUNT_END(21,"已结算,不允许修改"),
	END_TIME_MIN(22,"结束时间不能小于开始时间"),
	NOT_MY_COUNT(23,"请选择自己的工资"),
	NOT_STR(24,"用户名不能携带特殊字符"),
	NOT_NULL(25,"必填项不能为空"),
	EMAIL_NOT_NULL(26,"邮箱已被使用,请更换邮箱或找回密码"),
	NOT_RANDOMKEY(27,"验证码不存在或过期,请获取验证码"),
	RANDOMKEY_ERROR(28,"验证码错误"),
	USER_SEAL(29,"用户冻结,请联系管理员"),
	;
	
	
	private Integer code;
	private String message;
	
	private ResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Integer getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	
	
}
