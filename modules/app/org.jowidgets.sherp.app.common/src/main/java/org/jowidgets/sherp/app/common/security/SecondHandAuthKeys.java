/*
 * Copyright (c) 2011, grossmann
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 *   names of its contributors may be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL jo-widgets.org BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package org.jowidgets.sherp.app.common.security;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public final class SecondHandAuthKeys {

	public static final String SECOND_HAND_ADMIN_GROUP = "SECOND_HAND_ADMIN";

	//CRUD services
	public static final String CREATE_CUSTOMER = "CREATE_CUSTOMER";
	public static final String READ_CUSTOMER = "READ_CUSTOMER";
	public static final String UPDATE_CUSTOMER = "UPDATE_CUSTOMER";
	public static final String DELETE_CUSTOMER = "DELETE_CUSTOMER";

	public static final String CREATE_COMMODITY = "CREATE_COMMODITY";
	public static final String READ_COMMODITY = "READ_COMMODITY";
	public static final String UPDATE_COMMODITY = "UPDATE_COMMODITY";
	public static final String DELETE_COMMODITY = "DELETE_COMMODITY";

	//Authorizations collection
	public static final Collection<String> ALL_AUTHORIZATIONS = createAuthorizations();

	private SecondHandAuthKeys() {}

	private static List<String> createAuthorizations() {
		final List<String> result = new LinkedList<String>();
		for (final Field field : SecondHandAuthKeys.class.getDeclaredFields()) {
			if (field.getType().equals(String.class)) {
				try {
					final String fieldValue = (String) field.get(SecondHandAuthKeys.class);
					if (!SECOND_HAND_ADMIN_GROUP.equals(fieldValue)) {
						result.add(fieldValue);
					}
				}
				catch (final Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		return result;
	}

}
