package io.nirahtech.gepeen.property;

import java.util.UUID;

public class Identifier {

	private String value;
	
	public final String getValue() {
		return this.value;
	}
	
	public final void setValue(final String value) {
		this.value = value;
	}
	
	public Identifier() {
		this.value = UUID.randomUUID().toString();
	}
	
	public Identifier(String value) {
		this.value = value;
	}
	
	public static final Identifier generate(final Class<?> type) {		
		final String prefix = createPrefix(type.getSimpleName()).toUpperCase();
		final String uuid = UUID.randomUUID().toString();
		return new Identifier(prefix+'-'+uuid);
	}
	
	
	private static final String getCapitales(final String type) {
		final String capitales = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String result = "";
		for (int index = 0; index < type.length(); index++) {
			if (capitales.contains(String.valueOf(type.charAt(index)))) {
				result += type.charAt(index);
			}
		}
		return result;
	}
	
	private static final String extractCharacters(final String type, final boolean multipleCapitales) {
		final String capitales = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String result = "";
		if (!multipleCapitales) {
			return type.substring(0, 3).toUpperCase();
		}
		boolean previousIsCapital = false;
		for (int index = 0; index < type.length(); index++) {
			if (capitales.contains(String.valueOf(type.charAt(index)))) {
				result += type.charAt(index);
				previousIsCapital = true;
			} else {
				if (previousIsCapital) {
					result += type.charAt(index);
					previousIsCapital = true;
				}
			}
		}
		return result;
	}
	
	private static final String createPrefix(final String type) {
		if (getCapitales(type).length() < 2) {
			return extractCharacters(type, false);
		} else {
			return extractCharacters(type, true);
		}
	}
}
