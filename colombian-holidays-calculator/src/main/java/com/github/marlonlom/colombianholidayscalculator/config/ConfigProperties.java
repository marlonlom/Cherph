package com.github.marlonlom.colombianholidayscalculator.config;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Manages the loading and access of configuration properties from a file. This
 * class handles reading holiday date formats and holiday names, and provides
 * methods to check if the configuration was loaded successfully.
 * 
 * @author marlonlom
 * @version 2.0.0
 */
public class ConfigProperties {

	/**
	 * The date format used for parsing and formatting holiday dates.
	 */
	private SimpleDateFormat holidayDateFormat;

	/** A list of strings containing details or names for various holidays. */
	private List<String> holidayDetails;

	/**
	 * Indicates whether the configuration properties have been successfully loaded
	 * and initialized. {@code true} if ready, {@code false} otherwise.
	 */
	private boolean ready;

	/**
	 * Constructs a new {@code ConfigProperties} instance, loading properties from
	 * the default file named "config.properties".
	 */
	public ConfigProperties() {
		super();
		this.readConfig(initializeProperties("config.properties"));
	}

	/**
	 * Constructs a new {@code ConfigProperties} instance using the provided
	 * {@link Properties} object. This is useful for testing or when properties are
	 * sourced from a non-default location.
	 *
	 * @param properties The {@link Properties} object to read configuration from.
	 */
	protected ConfigProperties(Properties properties) {
		super();
		this.readConfig(properties);
	}

	/**
	 * Retrieves the configured {@link SimpleDateFormat} for holiday dates.
	 *
	 * @return A {@link SimpleDateFormat} object, or {@code null} if not
	 *         initialized.
	 */
	public SimpleDateFormat getHolidayDateFormat() {
		return holidayDateFormat;
	}

	/**
	 * Retrieves the list of configured holiday names or details.
	 *
	 * @return A {@link List} of strings representing holiday details, or
	 *         {@code null} if not initialized.
	 */
	public List<String> getHolidayDetails() {
		return holidayDetails;
	}

	/**
	 * Initializes the {@link SimpleDateFormat} from the "holiday.date_format"
	 * property.
	 *
	 * @param properties The {@link Properties} object containing configuration.
	 * @throws RuntimeException if the date format property is missing, empty, or
	 *                          invalid.
	 */
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

	/**
	 * Initializes the list of holiday details from the "holiday.details" property.
	 * Details are expected to be semicolon-separated.
	 *
	 * @param properties The {@link Properties} object containing configuration.
	 * @throws RuntimeException if the holiday details property is missing or empty.
	 */
	private void initializeHolidayNames(Properties properties) throws RuntimeException {
		String holidayDetailsString = properties.getProperty("holiday.details");
		if (holidayDetailsString == null || holidayDetailsString.trim().isEmpty()) {
			throw new IllegalArgumentException("Holiday names text property cannot be empty.");
		}
		this.setHolidayDetails(Arrays.stream(holidayDetailsString.split(";")).map(String::trim)
				.filter(s -> !s.isEmpty()).collect(Collectors.toList()));
	}

	/**
	 * Loads properties from the specified file name. The file is expected to be
	 * found in the classpath.
	 *
	 * @param fileName The name of the properties file (e.g., "config.properties").
	 * @return A {@link Properties} object containing the loaded properties.
	 * @throws IllegalArgumentException if the file cannot be read or found.
	 */
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

	/**
	 * Checks if the configuration has been loaded and initialized successfully.
	 *
	 * @return {@code true} if the configuration is ready for use, {@code false}
	 *         otherwise.
	 */
	public boolean isReady() {
		return ready;
	}

	/**
	 * Reads and validates configuration properties. Initializes the date format and
	 * holiday names based on the provided properties. Sets the ready status based
	 * on the success of initialization.
	 *
	 * @param properties The {@link Properties} object to read configuration from.
	 */
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

	/**
	 * Sets the list of holiday details.
	 *
	 * @param holidayDetails The list of holiday detail strings.
	 */
	private void setHolidayDetails(List<String> holidayDetails) {
		this.holidayDetails = holidayDetails;
	}

	/**
	 * Sets the readiness status of the configuration.
	 *
	 * @param ready {@code true} if the configuration is ready, {@code false}
	 *              otherwise.
	 */
	private void setReady(boolean ready) {
		this.ready = ready;
	}

}
