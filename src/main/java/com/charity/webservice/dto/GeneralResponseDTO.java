package com.charity.webservice.dto;

public class GeneralResponseDTO {

	private boolean success;

	public GeneralResponseDTO() {

	}

	public GeneralResponseDTO(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
