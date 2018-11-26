package bean;

import java.sql.Timestamp;


public class RequestOne {

	private String function_id;
	private Timestamp timestamp;
	private LoginUser data;

	public String getFunction_id() {
		return function_id;
	}

	public void setFunction_id(String function_id) {
		this.function_id = function_id;
	}

	@Override
	public String toString() {
		return "RequestOne{" +
				"function_id='" + function_id + '\'' +
				", timestamp=" + timestamp +
				", data=" + data +
				'}';
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public LoginUser getData() {
		return data;
	}

	public void setData(LoginUser data) {
		this.data = data;
	}
}
