package system.core.enums;

public enum DataStateTypeEnum {
	ADD_SUCCESS("0", "新增成功"), ADD_ERROR("1", "新增失败"),
	SAVE_SUCCESS("2", "保存成功"), SAVE_ERROR("3", "保存失败"),
	DELETE_SUCCESS("4", "删除成功"), DELETE_ERROR("5", "删除失败"),
	Start_SUCCESS("6", "启动成功"), Start_ERROR("7", "启动失败");
	private String code;
	private String message;

	private DataStateTypeEnum(String style, String indexPath) {
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
