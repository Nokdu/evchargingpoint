package evcharging.api.model;

public class ChargingPoint {

	private String chargeDeviceID;
	private String reference;
	private String name;
	private Double latitude;
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
