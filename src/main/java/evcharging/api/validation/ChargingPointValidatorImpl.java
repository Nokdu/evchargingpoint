package evcharging.api.validation;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import evcharging.api.core.Messages;
import evcharging.api.model.ChargingPoint;

@Component
public class ChargingPointValidatorImpl extends AbstractBaseValidator implements ChargingPointValidator {

	@Autowired
	Messages messages;

	private static final int MIN_LATITUDE = -90;
	private static final int MAX_LATITUDE = 90;
	private static final int MAX_LONGITUDE = 180;
	private static final int MIN_LONGITUDE = -180;

	@Override
	public boolean validateLatitude(Double latitude) {

		validateObjectIsNull(latitude, messages.get("latitude.is.null"));

		if (latitude.compareTo(Double.valueOf(MIN_LATITUDE)) < 0
				|| latitude.compareTo(Double.valueOf(MAX_LATITUDE)) > 0)

		{
			throw new ValidationException(messages.get("validation.latitude.must.between.90s"));
		}
		return true;
	}

	@Override
	public boolean validateLongitude(Double longitude) {

		validateObjectIsNull(longitude, messages.get("longitude.is.null"));

		if (longitude.compareTo(Double.valueOf(MIN_LONGITUDE)) < 0
				|| longitude.compareTo(Double.valueOf(MAX_LONGITUDE)) > 0) {
			throw new ValidationException(messages.get("validation.longitude.must.between.180s"));
		}
		return true;
	}

	@Override
	public boolean validateChargingPoint(ChargingPoint chargingPoint) {
		validateObjectIsNull(chargingPoint, messages.get("object.is.null", "ChargingPoint"));
		validateLatitude(chargingPoint.getLatitude());
		validateLongitude(chargingPoint.getLongitude());
		validateChargeDeviceId(chargingPoint.getChargeDeviceID());

		return true;
	}

	@Override
	public boolean validateChargeDeviceId(String chargeDeviceID) {
		if (isNullOrEmpty(chargeDeviceID)) {
			throw new ValidationException(messages.get("validation.device.id.is.null"));
		}
		return true;
	}

	@Override
	public boolean validateIsNumber(int pointNumber) {
		if (!isNumber(pointNumber)) {
			throw new ValidationException(messages.get("validation.number.must.be.positif"));
		}
		return true;
	}

}
