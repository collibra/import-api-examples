package com.collibra.importer.properties;

class BlankPropertyException extends RuntimeException {

	BlankPropertyException(String property) {
		super(String.format("Property %s value should not be blank or empty", property));
	}

}
