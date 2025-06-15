package com.github.marlonlom.colombianholidayscalculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.github.marlonlom.colombianholidayscalculator.config.ConfigProperties;
import com.github.marlonlom.colombianholidayscalculator.util.LocalDateUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HolidaysCalculatorTest {

	@Mock
	private ConfigProperties configProperties;
	private HolidaysCalculator holidaysCalculator;

	private void handleMockHolidayDateFormat() {
		when(configProperties.getHolidayDateFormat()).thenReturn(new SimpleDateFormat("dd/MM/yyyy"));
	}

	private void handleMockHolidayDetailsList() {
		when(configProperties.getHolidayDetails()).thenReturn(List.of("Año Nuevo", "Día de los Reyes Magos",
				"Día de San José", "Domingo de Ramos", "Jueves Santo", "Viernes Santo", "Domingo de Resurrección",
				"Día del Trabajo", "Día de la Ascensión", "Corpus Christi", "Sagrado Corazón", "San Pedro y San Pablo",
				"Día de la Independencia", "Batalla de Boyacá", "La asunción de la Virgen", "Día de la Raza",
				"Todos los Santos", "Independencia de Cartagena", "Día de la Inmaculada Concepción", "Día de Navidad"));
	}

	@Before
	public void setUp() throws Exception {
		configProperties = Mockito.mock(ConfigProperties.class);
	}

	@Test
	public void shouldNotReturnColombianHolidaysByEmptyConfigProperties() {
		holidaysCalculator = new HolidaysCalculator(new LocalDateUtil(), configProperties);
		JSONObject holidays = holidaysCalculator.getHolidays(2024);
		assertNotNull(holidays);
		assertTrue(holidays.isEmpty());
		assertEquals(0, holidays.length());
	}

	@Test
	public void shouldNotReturnColombianHolidaysByEmptyHolidayNames() {
		handleMockHolidayDateFormat();
		when(configProperties.getHolidayDetails()).thenReturn(List.of());
		holidaysCalculator = new HolidaysCalculator(new LocalDateUtil(), configProperties);
		JSONObject holidays = holidaysCalculator.getHolidays(2024);
		assertNotNull(holidays);
		assertTrue(holidays.isEmpty());
		assertEquals(0, holidays.length());
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotReturnColombianHolidaysByNoDateFormat() {
		handleMockHolidayDetailsList();
		holidaysCalculator = new HolidaysCalculator(new LocalDateUtil(), configProperties);
		JSONObject holidays = holidaysCalculator.getHolidays(2024);
		assertNotNull(holidays);
		assertTrue(holidays.isEmpty());
		assertEquals(0, holidays.length());
	}

	@Test
	public void shouldShowColombianHolidaysFor2024() {
		handleMockHolidayDateFormat();
		handleMockHolidayDetailsList();
		holidaysCalculator = new HolidaysCalculator(new LocalDateUtil(), configProperties);
		JSONObject holidays = holidaysCalculator.getHolidays(2024);
		System.out.println(String.format("holidays =%s", holidays.toString()));
		assertNotNull(holidays);
		assertFalse(holidays.isEmpty());
		assertEquals(20, holidays.length());
	}
}
