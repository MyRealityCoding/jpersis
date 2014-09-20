/*
 * Copyright 2014 Miguel Gonzalez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.bitbrain.jpersis.core;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This invoker takes care of objects and invokes their fields
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FieldInvoker {

	/**
	 * This method invokes a field of the object and inserts data
	 * 
	 * @param object target object
	 * @param field field to set
	 * @param value data for the field
	 */
	public void invoke(Object object, Field field, String value)
			throws InvokeException {
		
		if (value == null || value.isEmpty()) {
			throw new InvokeException(field.getName() + " is null");
		}
		
		try {
			field.set(object, convertToObject(field.getType(), value));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new InvokeException(e);
		}
	}
	
	private Object convertToObject(Class type, String value) throws InvokeException {
		Object obj = null;
		// Enum
		if (type.isEnum()) {
			obj = invokeEnum(type, value);
		// Date
		} else if (type.equals(Date.class) || type.equals(java.sql.Date.class)) {
			obj = invokeDate(value);
		// Integer
		} else if (type.equals(Integer.TYPE)) {
			obj = Integer.valueOf(value);
		// Boolean
		} else if (type.equals(Boolean.TYPE)) {
			obj = Boolean.valueOf(value);
		// Long
		} else if (type.equals(Long.TYPE)) {
			obj = Long.valueOf(value);
		// Float
		} else if (type.equals(Float.TYPE)) {
			obj = Float.valueOf(value);
		// Double
		} else if (type.equals(Double.TYPE)) {
			obj = Double.valueOf(value);
		// String
		} else if (type.equals(String.class)) {
			obj = value;
		// Char
		} else if (type.equals(Character.TYPE)) {
			obj = invokeChar(value);
		} else {
			throw new InvokeException("Type " + type.getName() + " is not supported by JPersis");
		}
		
		return obj;
	}

	private Object invokeEnum(Class type, String value) {
		return Enum.valueOf(type, value);
	}
	
	private Object invokeChar(String value) throws InvokeException {
		if (value.length() == 1) {
            return Character.valueOf(value.toCharArray()[0]);
        } else {
            throw new InvokeException(value + " is not a valid character");
        }
	}
	
	private Object invokeDate(String value)
			throws InvokeException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return (Date) format.parse(value);
		} catch (ParseException | IllegalArgumentException e) {
			throw new InvokeException(e);
		}
	}

	public class InvokeException extends Exception {

		private static final long serialVersionUID = 7413162412732936818L;
		
		public InvokeException(String s) {
			super(s);
		}

		public InvokeException(Throwable e) {
			super(e);
		}
	}
}