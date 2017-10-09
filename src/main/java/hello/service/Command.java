package hello.service;

import java.util.HashMap;
import java.util.Map;

public class Command {

	private String name;

	private Map<String, String> parameters = new HashMap<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParameter(String key) {
		return parameters.get(key);
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public String putParameter(String key, String value) {
		return parameters.put(key, value);
	}

}
