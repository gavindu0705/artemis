package com.artemis.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;

import com.artemis.core.log.ALogger;

public class BeanUtils {

	public static final ALogger LOG = new ALogger(BeanUtils.class);

	/**
	 * 复制 source 属性 到 target
	 * 
	 * 数据类型 参数名 不同 忽略
	 * 
	 * @param model
	 * @return
	 */
	public static void copyProperties(Object source, Object target) {
		if (null == source || null == target) {
			return;
		}
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(source.getClass());
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			Method readMethod = descriptor.getReadMethod();
			Method writeMethod = descriptor.getWriteMethod();
			if (null == readMethod || null == writeMethod) {
				continue;
			}
			String writeMethodName = writeMethod.getName();
			Class<?> returnType = readMethod.getReturnType();
			writeMethod = org.springframework.beans.BeanUtils.findMethod(target.getClass(), writeMethodName, returnType);
			if (null == writeMethod) {
				continue;
			}
			try {
				Object value = readMethod.invoke(source);
				writeMethod.invoke(target, value);
			} catch (IllegalArgumentException e) {
				LOG.error("error copy property:" + descriptor.getName(), e);
			} catch (IllegalAccessException e) {
				LOG.error("error copy property:" + descriptor.getName(), e);
			} catch (InvocationTargetException e) {
				LOG.error("error copy property:" + descriptor.getName(), e);
			}
		}
	}

}
