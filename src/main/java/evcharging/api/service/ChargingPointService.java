package evcharging.api.service;

import java.util.List;

import evcharging.api.model.ChargingPoint;

public interface ChargingPointService {

	List<ChargingPoint> getAllChargingPoints();

	ChargingPoint getChargingPoint(String chargeDeviceID);

	boolean addChargingPoint(ChargingPoint chargingPoint);

	void deleteChargingPoint(String chargeDeviceID);

	void updateChargingPoint(ChargingPoint chargingPoint);

	List<ChargingPoint> getNearestChargingPoints(double lattitude, double longitude, int pointNumber);

}
