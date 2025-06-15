package com.github.marlonlom.colombianholidayscalculator.config;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Clase para cargar y acceder a las propiedades de configuración.
 * 
 * @author marlonlom
 */
public class ConfigProperties {

	private SimpleDateFormat holidayDateFormat;
	private List<String> holidayDetails;
	private boolean ready;

	/**
	 * Crea una nueva instancia de propiedades de configuración.
	 */
	public ConfigProperties() {
		super();
		this.readConfig(initializeProperties("config.properties"));
	}

	/**
	 * Crea una nueva instancia de propiedades de configuración
	 *
	 * @param properties the properties
	 */
	protected ConfigProperties(Properties properties) {
		super();
		this.readConfig(properties);
	}

	/**
	 * Obtiene el formato de fecha para los feriados.
	 *
	 * @return Un objeto SimpleDateFormat configurado con el formato de fecha.
	 */
	public SimpleDateFormat getHolidayDateFormat() {
		return holidayDateFormat;
	}

	/**
	 * Obtiene la lista de nombres de feriados.
	 *
	 * @return Una lista de cadenas con los nombres de los feriados.
	 */
	public List<String> getHolidayDetails() {
		return holidayDetails;
	}

	private void initializeDateFormat(Properties properties) throws RuntimeException {
		String dateFormatString = properties.getProperty("holiday.date_format");
		try {
			if (dateFormatString == null || dateFormatString.trim().isEmpty()) {
				throw new IllegalArgumentException("Date format text property cannot be empty.");
			}
			this.holidayDateFormat = new SimpleDateFormat(dateFormatString);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid date format: " + dateFormatString, e);
		}
	}

	private void initializeHolidayNames(Properties properties) throws RuntimeException {
		String holidayDetailsString = properties.getProperty("holiday.details");
		if (holidayDetailsString == null || holidayDetailsString.trim().isEmpty()) {
			throw new IllegalArgumentException("Holiday names text property cannot be empty.");
		}
		this.setHolidayDetails(Arrays.stream(holidayDetailsString.split(";")).map(String::trim)
				.filter(s -> !s.isEmpty()).collect(Collectors.toList()));
	}

	private Properties initializeProperties(String fileName) {
		System.out.println("initializeProperties / starting");
		Properties properties = new Properties();
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
			properties.load(inputStream);
		} catch (Throwable throwable) {
			final String failureMessage = throwable != null ? String.format("(%s)", throwable.getMessage())
					: "Failed to read config properties.";
			System.err.println("initializeProperties / failed ".concat(failureMessage));
			throw new IllegalArgumentException(failureMessage, throwable);
		} finally {
			System.out.println("initializeProperties / finished");
		}
		return properties;
	}

	public boolean isReady() {
		return ready;
	}

	private void readConfig(Properties properties) {
		try {
			if (properties.isEmpty()) {
				this.setReady(false);
			}
			this.initializeDateFormat(properties);
			this.initializeHolidayNames(properties);
			final boolean isConfigReady = this.getHolidayDateFormat() != null
					&& (this.getHolidayDetails() != null && !this.getHolidayDetails().isEmpty());
			this.setReady(isConfigReady);
		} catch (RuntimeException runtimeException) {
			this.setReady(false);
			System.err.println("readConfig / failed ".concat(runtimeException.getMessage()));
		}
	}

	private void setHolidayDetails(List<String> holidayDetails) {
		this.holidayDetails = holidayDetails;
	}

	private void setReady(boolean ready) {
		this.ready = ready;
	}

}
