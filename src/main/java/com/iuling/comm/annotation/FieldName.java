/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Iuling</a> All rights reserved.
 */
package com.iuling.comm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * bean中文名注解
 */
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)  
public @interface FieldName {

	String value();
	
}
