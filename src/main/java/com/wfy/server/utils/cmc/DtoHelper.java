package com.wfy.server.utils.cmc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class DtoHelper {
	private static Logger logger = LoggerFactory.getLogger(DtoHelper.class);

	private static final String DTO_PACKAGE_PREFIX = "com.fhic.marketing.web.dto.json";
	private static final String ENTITY_PACKAGE_PREFIX = "com.fhic.marketing.web.dao.entity";

	/** * Bean 属性拷贝 * * @param dest * @param orig */
	private static void copyProperties(Object dest, Object orig) {
		if (orig == null) {
			logger.error("\n error: copy property error: orig is null");
			return;
		}
		if (dest == null) {
			logger.error("\n error: copy property error: dest is null");
			return;
		}
		try {
			copy(dest, orig);
		} catch (Exception e) {
			logger.error("\n error: copy property error: " + e.toString(), e);
		}
	}

	/** jpa 到 dto 或者 dto 到 jpa 的属性值copy */
	private static void copy(Object dest, Object orig) throws Exception {
		// 得到两个Class 的所有成员变量
		Field[] destFields = dest.getClass().getDeclaredFields();
		Field[] origFields = orig.getClass().getDeclaredFields();
		// 设置访问权限
		AccessibleObject.setAccessible(destFields, true);
		AccessibleObject.setAccessible(origFields, true);
		Object value = null;
		String name = null;
		String returnType = null;
		Map<Field, Object> tempCache = new HashMap<Field, Object>();
		for (int i = 0; i < origFields.length; i++) {
			name = origFields[i].getName();
			returnType = origFields[i].getType().getName();
			for (int j = 0; j < destFields.length; j++) {
				if (!isFinal(destFields[j].getModifiers()) && name.equals(destFields[j].getName()) && returnType.equals(destFields[j].getType().getName())) {
					value = origFields[i].get(orig);
					if (value != null) {
						destFields[j].set(dest, value);
					}
					break;
				} else {
					value = origFields[i].get(orig);
					if (value != null && isDto(destFields[j].getType())) {
						Object dto = convertToDto(destFields[i].getType(), value);
						if (null == tempCache.get(origFields[i]) && null != dto) {
							tempCache.put(origFields[i], dto);
						}
					} else if (value != null && isEntity(destFields[j].getType())) {
						Object jpa = convertToEntity(destFields[i].getType(), value);
						tempCache.put(origFields[i], jpa);
					}
				}
			}
		}
		Set<Field> keys = tempCache.keySet();
		for (Field field : keys) {
			String name1 = field.getName();
			String returnType1 = tempCache.get(field).getClass().getName();
			for (int k = 0; k < destFields.length; k++) {
				if (!isFinal(destFields[k].getModifiers()) && name1.equals(destFields[k].getName()) && returnType1.equals(destFields[k].getType().getName())) {
					destFields[k].set(dest, tempCache.get(field));
					break;
				}
			}
		}
		tempCache.clear();
		tempCache = null;
	}

	private static boolean isDto(Class<?> clazz) {
		if (null == clazz || null == clazz.getPackage())
			return false;
		if (clazz.getPackage().getName().equals(DTO_PACKAGE_PREFIX))
			return true;
		return false;
	}

	private static boolean isEntity(Class<?> clazz) {
		if (null == clazz || null == clazz.getPackage())
			return false;
		if (clazz.getPackage().getName().equals(ENTITY_PACKAGE_PREFIX)) {
			return true;
		}
		return false;
	}

	private static boolean isFinal(int mod) {
		return (mod & Modifier.FINAL) != 0;
	}

	/**
	 * JPA 属性为lazy时需要调用此方法手动将属性值copy过来
	 * 
	 * @param setObj
	 * @param getObj
	 */
	private static void copyByReflect(Object setObj, Object getObj) {
		if (null == setObj || null == getObj)
			return;
		String GET = "get";
		String SET = "set";
		Method[] setobjDeclaredMethods = setObj.getClass().getDeclaredMethods();
		Method[] getobjDeclaredMethods = getObj.getClass().getDeclaredMethods();
		Map<String, Object> getObjMap = new HashMap<String, Object>();
		try {
			if (null != getobjDeclaredMethods) {
				for (Method method : getobjDeclaredMethods) {
					if (/*
						 * method.getModifiers() == 1 &&
						 */method.getName().startsWith(GET)) {
						Object o = method.invoke(getObj);
						if (null != o) {
							getObjMap.put(method.getName().replaceFirst(GET, ""), o);
						}
					}
				}
			}
			if (null != setobjDeclaredMethods) {
				for (Method method : setobjDeclaredMethods) {
					String temp = method.getName().replaceFirst(GET, "").replace(SET, "");
					String filedName = temp.substring(0, 1).toLowerCase() + temp.substring(1, temp.length());
					Field field = setObj.getClass().getDeclaredField(filedName);
					if (/*
						 * method.getModifiers() == 1 &&
						 */method.getName().startsWith(SET)) {
						Object vlaue = getObjMap.get(method.getName().replaceFirst(SET, ""));
						if (isDto(field.getType()) && null != vlaue && isEntity(vlaue.getClass())) {
							copyByReflect(vlaue.getClass().newInstance(), vlaue);
						} else if (isEntity(field.getType()) && null != vlaue && isDto(vlaue.getClass())) {
							copyByReflect(vlaue.getClass().newInstance(), vlaue);
						} else if (null != getObjMap.get(method.getName().replaceFirst(SET, ""))) {
							method.invoke(setObj, getObjMap.get(method.getName().replaceFirst(SET, "")));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Object convertToDto(Class<?> clazz, Object orig) {
		if (null != orig && clazz != null) {
			try {
				Object o = clazz.newInstance();
				copyProperties(o, orig);
				return o;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static Object convertToEntity(Class<?> clazz, Object orig) {
		if (null != orig && clazz != null) {
			try {
				Object o = clazz.newInstance();
				copy(o, orig);
				return o;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static List convertToDtoList(Class clazz, List orig) {
		if (null != orig && !orig.isEmpty() && clazz != null) {
			List list = new ArrayList();
			for (Object obj : orig) {
				try {
					Object o = clazz.newInstance();
					copy(o, obj);
					list.add(o);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return list;
		}
		return new ArrayList();
	}

	private static Object convertMapToDto(Class<?> clazz, Map<Object, Object> orig) {
		if (null != orig && clazz != null) {
			try {
				Object o = clazz.newInstance();
				copyMapToDto(o, orig);
				return o;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static void copyMapToDto(Object dest, Map<Object, Object> orig) throws IllegalArgumentException, IllegalAccessException {
		if (orig == null) {
			logger.error("\n error: copy property error: orig is null");
			return;
		}
		if (dest == null) {
			logger.error("\n error: copy property error: dest is null");
			return;
		}
		// 得到Class 的所有成员变量
		Field[] destFields = dest.getClass().getDeclaredFields();
		// 设置访问权限
		AccessibleObject.setAccessible(destFields, true);
		Object value = null;
		String name = null;
		Class returnType = null;
		for (int i = 0; i < destFields.length; i++) {
			name = destFields[i].getName();
			if (orig.containsKey(name)) {
				returnType = destFields[i].getType();
				Class origValueType = orig.get(name).getClass();
//				System.out.println(returnType.getName() +"    "+name+"  "+origValueType.getName());
				value = orig.get(name);
				if (value != null && (origValueType.getName().equals(returnType.getName()) || returnType.isAssignableFrom(origValueType))) {
					destFields[i].set(dest, value);
				} else {
					logger.error("\n error: copy property error: check the map of valus is {} and data types is {}", value, origValueType);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T, S> T convertObj(Class<T> DestinationClass, S sourceObj) {
		return (T) DtoHelper.convertToDto(DestinationClass, sourceObj);
	}

	@SuppressWarnings("unchecked")
	public static <T, S> List<T> convertToList(Class<T> DestinationClass, List<S> sourceObj) {
		if (sourceObj == null || sourceObj.size() == 0) {
			return new ArrayList<T>();
		}
		return (List<T>) DtoHelper.convertToDtoList(DestinationClass, sourceObj);
	}

	@SuppressWarnings("unchecked")
	public static <T, S> T convertFromMap(Class<T> DestinationClass, Map<Object, Object> sourceMap) {
		return (T) DtoHelper.convertMapToDto(DestinationClass, sourceMap);
	}
}
