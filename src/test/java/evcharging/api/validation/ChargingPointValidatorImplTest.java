package evcharging.api.validation;

import static org.junit.Assert.assertTrue;

import javax.validation.ValidationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import evcharging.api.core.Messages;
import evcharging.api.model.ChargingPoint;
import evcharging.api.validation.ChargingPointValidatorImpl;

@RunWith(MockitoJUnitRunner.class)
public class ChargingPointValidatorImplTest {

	@Mock
	Messages messages;

	@InjectMocks
	ChargingPointValidatorImpl validator;

	ChargingPoint chargingPoint;

	@Before
	public void setUp() {
		chargingPoint = new ChargingPoint();
		chargingPoint.setChargeDeviceID("cfeedcdd5e287bef4b583158a12363f1");
		chargingPoint.setLatitude(51.48092);
		chargingPoint.setLongitude(-0.419318);
	}

	@Test
	public void validateLatitude() {
		assertTrue(validator.validateLatitude(45.5655666));
	}

	@Test(expected = ValidationException.class)
	public void validateLatitudeWhenLatitudeIsInvalid() {
		assertTrue(validator.validateLatitude(-95.98));
		assertTrue(validator.validateLatitude(95.98));
	}

	@Test
	public void validateLongitude() {
		assertTrue(validator.validateLongitude(45.5655666));
	}

	@Test(expected = ValidationException.class)
	public void validateLongitudeWhenLongitudeIsInvalid() {
		assertTrue(validator.validateLongitude(185.98));
	}

	@Test
	public void validateChargingPoint() {
		assertTrue(validator.validateChargingPoint(chargingPoint));
	}

	@Test(expected = ValidationException.class)
	public void validateChargingPointWhenObjectIsNull() {
		ChargingPoint cPoint = null;
		Mockito.when(messages.get("object.is.null", "ChargingPoint")).thenReturn("ChargingPoint is null");
		assertTrue(validator.validateChargingPoint(cPoint));
	}

	@Test(expected = ValidationException.class)
	public void validateChargingPointDeviceIdIsNull() {
		chargingPoint.setChargeDeviceID(null);
		assertTrue(validator.validateChargingPoint(chargingPoint));
	}

	@Test
	public void validateChargeDeviceId() {
		assertTrue(validator.validateChargeDeviceId("cf164e398bebd2384cbbbfe73fa72fcf"));
	}

	@Test(expected = ValidationException.class)
	public void validateChargeDeviceIdWhenIdIsNull() {
		assertTrue(validator.validateChargeDeviceId(""));
	}

	@Test
	public void validateIsNumber() {
		assertTrue(validator.validateIsNumber(5));
	}

	@Test(expected = ValidationException.class)
	public void validateWhenNumberIsInvalid() {
		assertTrue(validator.validateIsNumber(0));
	}

}
