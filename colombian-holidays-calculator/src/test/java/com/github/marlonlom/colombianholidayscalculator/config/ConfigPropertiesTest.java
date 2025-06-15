package com.github.marlonlom.colombianholidayscalculator.config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConfigPropertiesTest {

	@Mock
	private Properties properties;
	private ConfigProperties configProperties;

	@Before
	public void setUp() throws Exception {
		properties = Mockito.mock(Properties.class);
	}

	@Test
	public void shouldBeReadyAndContainValidProperties() {
		when(properties.getProperty("holiday.date_format")).thenReturn("dd/MM/yyyy");
		when(properties.getProperty("holiday.details")).thenReturn("Año Nuevo;Navidad");
		configProperties = new ConfigProperties(properties);
		assertTrue(configProperties.isReady());
		assertNotNull(configProperties.getHolidayDateFormat());
		assertNotNull(configProperties.getHolidayDetails());
		assertFalse(configProperties.getHolidayDetails().isEmpty());
	}

	@Test
	public void shouldNotBeReadyByEmptyDateFormat() {
		when(properties.getProperty("holiday.date_format")).thenReturn("");
		configProperties = new ConfigProperties(properties);
		assertFalse(configProperties.isReady());
		assertNull(configProperties.getHolidayDateFormat());
	}

	@Test
	public void shouldNotBeReadyByEmptyHolidayNames() {
		when(properties.getProperty("holiday.date_format")).thenReturn("dd/MM/yyyy");
		when(properties.getProperty("holiday.details")).thenReturn("");
		configProperties = new ConfigProperties(properties);
		assertFalse(configProperties.isReady());
		assertNotNull(configProperties.getHolidayDateFormat());
		assertNull(configProperties.getHolidayDetails());
	}

	@Test
	public void shouldNotBeReadyByInvalidDateFormat() {
		when(properties.getProperty("holiday.date_format")).thenReturn("wtf");
		configProperties = new ConfigProperties(properties);
		assertFalse(configProperties.isReady());
		assertNull(configProperties.getHolidayDateFormat());
	}

}
