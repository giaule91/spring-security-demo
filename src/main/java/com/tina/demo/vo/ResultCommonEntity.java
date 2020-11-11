/*
 * ModifiedEntity
 *
 *  Copyright (c) 2016 HEB
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information
 *  of HEB.
 */

package com.tina.demo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Holds a value and message. The idea is that you want to send a result and a message back to the front
 * end so you can store your messages for the user here.
 * @since 2.0.2
 * @param <T> The type of data to hold.
 */
public class ResultCommonEntity<T> {

	private String message;
	private boolean success;
	private T data;
	@JsonProperty(value = "status_code")
	private int statusCode;

	/**
	 * Constructs a new ModifiedEntity.
	 *
	 * @param data The data for this object to hold.
	 * @param message The message for the front end.
	 */
	public ResultCommonEntity(T data, boolean success, String message) {
		this.message = message;
		this.data = data;
		this.success = success;
	}
	public ResultCommonEntity(T data, boolean success, String message, int statusCode) {
		this.message = message;
		this.data = data;
		this.success = success;
		this.statusCode = statusCode;
	}

	/**
	 * Returns the message.
	 *
	 * @return The message for the front end.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the data for the front end.
	 *
	 * @return The data for the front end.
	 */
	public T getData() {
		return data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}