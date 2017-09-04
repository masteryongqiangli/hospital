package system.core.enums;

public enum ValidTypeEnum {
	USER_EXIST("0","用户已存在"),IDCARD_ERROR("1","身份证号错误");

	private String code;
	private String message;

	private ValidTypeEnum(String style, String indexPath) {
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
