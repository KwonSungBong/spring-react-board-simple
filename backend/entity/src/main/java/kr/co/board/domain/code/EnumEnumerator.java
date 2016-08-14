package kr.co.board.domain.code;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class EnumEnumerator<T extends Enum<T>> {

	private final Class<T> enumClass;

	public EnumEnumerator(Class<T> enumClass) {
		this.enumClass = enumClass;
	}


	public List<Map<String, String>> getCodeList(){
		List<Map<String, String>> codeList = new LinkedList<>();

		EnumSet enumSet = EnumSet.allOf(enumClass);

		//noinspection unchecked
		enumSet.forEach((e) -> {
				try {
					Method method = e.getClass().getDeclaredMethod("getValue");
					String value = (String) method.invoke(e);

					log.debug("Enum Code : {}", e.toString());
					log.debug("Enum Value : {}", value);

					Map<String, String> code = new LinkedHashMap<>();
					code.put("code", e.toString());
					code.put("value", value);

					codeList.add(code);

				} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
		);

		return codeList;
	}
}
