/*
 * com.heb.jaf.security.JafSecurityConstants
 *
 * Copyright (c) 2016 HEB
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HEB.
 */

package com.tina.demo.security;

/**
 * Used to store constants common to the JAF security framework.
 *
 * @author d116773
 */
public final class JafSecurityConstants {
	private JafSecurityConstants() {};

	public static final String RESPONSE_CONTENT_TYPE = "application/json";
	public static final String UNKNOWN_VALUE = "<unknown>";

	public static final String USER_ID_KEY = "user_id";
	public static final String USER_EMAIL_KEY = "email";
	public static final String USER_ROLES_KEY = "roles";
	public static final String USER_TOKEN_KEY = "token";
	public static final String RESULT_MESSAGE_KEY = "message";
	public static final String RESULT_SUCCESS_KEY = "success";
	public static final String RESULT_DATA_KEY = "data";
	public static final String STATUS_CODE = "status_code";

}
