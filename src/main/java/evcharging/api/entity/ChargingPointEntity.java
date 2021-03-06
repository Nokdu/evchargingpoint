package evcharging.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Charging_Point")
public class ChargingPointEntity {

	@Id
	@Column(name = "Charge_Device_ID", insertable = true, updatable = false)
	private String chargeDeviceID;

	@Column(name = "Reference")
	private String reference;

	@Column(name = "Name")
	private String name;

	@Column(name = "Latitude")
	private Double latitude;

	@Column(name = "Longitude")
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
