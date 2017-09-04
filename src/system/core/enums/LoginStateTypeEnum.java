package system.core.enums;

public enum LoginStateTypeEnum {
	NOT_USER("0", "用户不存在"), PASSWORD_ERROR("1", "密码错误"),LOGIN_SUCCESS("200", "登录成功");

	private String code;
	private String message;

	private LoginStateTypeEnum(String style, String indexPath) {
		this.code = style;
		this.message = indexPath;

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
