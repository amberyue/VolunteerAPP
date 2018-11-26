package bean;

import java.sql.Timestamp;


public class Request<T> {

	private String function_id;
	private Timestamp timestamp;
	private String session_id;
	private T data;

	public String getFunction_id() {
		return function_id;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
