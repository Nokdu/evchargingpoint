package evcharging.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import evcharging.api.core.Messages;
import evcharging.api.dao.ChargingPointDAO;
import evcharging.api.entity.ChargingPointEntity;
import evcharging.api.model.ChargingPoint;
import evcharging.api.validation.ChargingPointValidator;

@Service
public class ChargingPointServiceImpl implements ChargingPointService {

	@Autowired
	ChargingPointDAO dao;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Messages messages;

	@Autowired
	private ChargingPointValidator validator;

	@Override
	public List<ChargingPoint> getAllChargingPoints() {

		List<ChargingPointEntity> chargingPointEntities = dao.findAll();

		return chargingPointEntities.stream().map(m -> modelMapper.map(m, ChargingPoint.class))
				.collect(Collectors.toList());

	}

	@Override
	public ChargingPoint getChargingPoint(String chargeDeviceID) {
		validator.validateChargeDeviceId(chargeDeviceID);
		ChargingPointEntity cPointEntity = dao.findOne(chargeDeviceID);
		if (cPointEntity != null) {
			return modelMapper.map(cPointEntity, ChargingPoint.class);
		}
		return null;
	}

	@Override
	public boolean addChargingPoint(ChargingPoint chargingPoint) {
		validator.validateChargingPoint(chargingPoint);

		if (!isAlreadyExist(chargingPoint)) {

			ChargingPointEntity chargingPointEntity = modelMapper.map(chargingPoint, ChargingPointEntity.class);
			dao.saveAndFlush(chargingPointEntity);
			return true;
		}
		return false;
	}

	public boolean isAlreadyExist(ChargingPoint chargingPoint) {
		ChargingPoint cPoint = getChargingPoint(chargingPoint.getChargeDeviceID());

		if (cPoint != null) {
			throw new DataIntegrityViolationException(messages.get("charging.point.already.exist.with.this.id"));

		}

		if (getChargingPoint(chargingPoint.getLatitude(), chargingPoint.getLongitude()) != null) {
			throw new DataIntegrityViolationException(messages.get("charging.point.already.exist.with.this.latlong"));

		}

		return false;

	}

	private ChargingPoint getChargingPoint(Double latitude, Double longitude) {
		ChargingPointEntity cPointEntity = dao.findByLatitudeAndLongitude(latitude, longitude);

		if (cPointEntity != null) {
			return modelMapper.map(cPointEntity, ChargingPoint.class);
		}
		return null;
	}

	@Override
	public void updateChargingPoint(ChargingPoint chargingPoint) {

		validator.validateChargingPoint(chargingPoint);

		ChargingPoint cPoint = getChargingPoint(chargingPoint.getLatitude(), chargingPoint.getLongitude());

		if ((cPoint != null) && (!cPoint.getChargeDeviceID().equals(chargingPoint.getChargeDeviceID()))) {
			throw new DataIntegrityViolationException(messages.get("charging.point.already.exist.with.this.latlong"));

		}

		ChargingPointEntity chargingPointEntity = modelMapper.map(chargingPoint, ChargingPointEntity.class);
		dao.saveAndFlush(chargingPointEntity);

	}

	@Override
	public void deleteChargingPoint(String chargeDeviceID) {
		validator.validateChargeDeviceId(chargeDeviceID);
		dao.delete(chargeDeviceID);
	}

	@Override
	public List<ChargingPoint> getNearestChargingPoints(double latitude, double longitude, int pointNumber) {

		validator.validateLongitude(longitude);
		validator.validateLatitude(latitude);
		validator.validateIsNumber(pointNumber);

		List<ChargingPoint> allChargingPoints = getAllChargingPoints();
		Map<Double, List<ChargingPoint>> groupByDistanceMap = allChargingPoints.stream()
				.collect(Collectors.groupingBy(item -> Math.sqrt(
						Math.pow(item.getLatitude() - latitude, 2) + Math.pow(item.getLongitude() - longitude, 2))));

		SortedSet<Double> keys = new TreeSet<Double>(groupByDistanceMap.keySet());
		List<ChargingPoint> result = new ArrayList<ChargingPoint>();
		for (Double key : keys) {
			List<ChargingPoint> value = groupByDistanceMap.get(key);
			for (ChargingPoint chargingPoint : value) {
				if (result.size() == pointNumber) {
					break;
				} else {
					result.add(chargingPoint);
				}
			}
			if (result.size() == pointNumber) {
				break;
			}
		}

		return result;
	}

}
