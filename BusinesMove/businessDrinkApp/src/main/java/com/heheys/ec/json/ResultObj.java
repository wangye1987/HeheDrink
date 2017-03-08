package com.heheys.ec.json;

import java.io.Serializable;

/**
 * @author WK
 */
public class ResultObj implements Serializable {

	private static final long serialVersionUID = -2561063952591303198L;
	private int status;
	private Object error;
	private Object result;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Object getError() {
		return error;
	}
	public void setError(Object error) {
		this.error = error;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}


}
