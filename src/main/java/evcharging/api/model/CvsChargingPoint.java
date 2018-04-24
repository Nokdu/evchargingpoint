package evcharging.api.model;

import com.opencsv.bean.CsvBindByPosition;

public class CvsChargingPoint {

	@CsvBindByPosition(position = 0, required = false)
	private String chargeDeviceID;
	@CsvBindByPosition(position = 1, required = false)
	private String reference;
	@CsvBindByPosition(position = 2, required = false)
	private String name;
	@CsvBindByPosition(position = 3, required = false)
	private Double latitude;
	@CsvBindByPosition(position = 4, required = false)
	private Double longitude;

	public String getChargeDeviceID() {
		return chargeDeviceID;
	}

	public void setChargeDeviceID(String chargeDeviceID) {
		this.chargeDeviceID = chargeDeviceID;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
