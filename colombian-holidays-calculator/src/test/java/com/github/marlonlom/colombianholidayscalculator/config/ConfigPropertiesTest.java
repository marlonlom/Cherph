/*
 * Copyright 2025 marlonlom
 * SPDX-License-Identifier: Apache-2.0
 */
package com.github.marlonlom.colombianholidayscalculator.config;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Properties;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

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
  public void shouldNotBeReadyByEmptyProperties() {
    when(properties.isEmpty()).thenReturn(true);
    configProperties = new ConfigProperties(properties);
    assertFalse(configProperties.isReady());
    assertNull(configProperties.getHolidayDateFormat());
  }

  @Test
  public void shouldNotBeReadyByInvalidDateFormat() {
    when(properties.getProperty("holiday.date_format")).thenReturn("wtf");
    configProperties = new ConfigProperties(properties);
    assertFalse(configProperties.isReady());
    assertNull(configProperties.getHolidayDateFormat());
  }

  @Test
  public void shouldNotBeReadyByNullDateFormat() {
    when(properties.getProperty("holiday.date_format")).thenReturn(null);
    configProperties = new ConfigProperties(properties);
    assertFalse(configProperties.isReady());
    assertNull(configProperties.getHolidayDateFormat());
  }

  @Test
  public void shouldNotBeReadyByNullHolidayNames() {
    when(properties.getProperty("holiday.date_format")).thenReturn("dd/MM/yyyy");
    when(properties.getProperty("holiday.details")).thenReturn(null);
    configProperties = new ConfigProperties(properties);
    assertFalse(configProperties.isReady());
    assertNotNull(configProperties.getHolidayDateFormat());
    assertNull(configProperties.getHolidayDetails());
  }

}
