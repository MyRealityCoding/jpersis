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
package de.bitbrain.jpersis.drivers;

/**
 * Driver for database communication
 * 
 * @author Miguel Gonzalez
 * @since 1.0
 * @version 1.0
 */
public interface Driver {
	
	Query query(Class<?> model);
	
	public interface Query {
		
		Query condition(String condition, Object[] params);
		
		Query select();
		
		Query update(Object object);
		
		Query delete(Object object);
		
		Query insert(Object object);
		
		Query count();
		
		Query limit(int limit);
		
		Query order(Order order);
		
		Object commit();	
	}
	
	public enum Order {
		ASC,
		DESC;
	}
}
