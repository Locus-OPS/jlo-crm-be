package th.co.locus.jlo.common;

import java.sql.SQLException;

public class ServiceResult<T> {

	private T result;
	private boolean success = false;
	private String responseCode;
	private String responseDescription;
	private Throwable throwable;
	private String etc1;
	private String etc2;
	private String etc3;

	public ServiceResult() {

	}

	public ServiceResult(String errorCode, Throwable throwable) {
		if (throwable instanceof SQLException) {
			SQLException sqlException = (SQLException) throwable;
			this.responseCode = "" + sqlException.getErrorCode();
		} else {
			this.responseCode = errorCode;
		}
		this.success = false;
		this.throwable = throwable;
		this.responseDescription = throwable.getLocalizedMessage();
	}

	public ServiceResult(boolean b) {
		this.success = b;
	}

	public ServiceResult(T result) {
		this.result = result;
		this.success = true;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
		this.success = false;
	}

	@Override
	public String toString() {
		return "ServiceResult [result=" + result + ", success=" + success + ", responseCode=" + responseCode + ", responseDescription=" + responseDescription + ", throwable="
				+ throwable + "]";
	}

	public String getEtc1() {
		return etc1;
	}

	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}

	public String getEtc2() {
		return etc2;
	}

	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}

	public String getEtc3() {
		return etc3;
	}

	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}

}