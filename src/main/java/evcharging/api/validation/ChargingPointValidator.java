package evcharging.api.validation;

import evcharging.api.model.ChargingPoint;

public interface ChargingPointValidator {

	boolean validateChargingPoint(ChargingPoint chargingPoint);

	boolean validateChargeDeviceId(String chargeDeviceID);

	boolean validateLongitude(Double longitude);

	boolean validateLatitude(Double latitude);

	boolean validateIsNumber(int pointNumber);

}
