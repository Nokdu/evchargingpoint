package evcharging.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import evcharging.api.model.ChargingPoint;
import evcharging.api.service.ChargingPointService;

@RestController
@RequestMapping("/charging-point")
public class ChargingPointController {

	@Autowired
	private ChargingPointService chargingPointService;

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<List<ChargingPoint>> getAllChargingPoints() {
		final List<ChargingPoint> chargingPoints = chargingPointService.getAllChargingPoints();
		return new ResponseEntity<List<ChargingPoint>>(chargingPoints, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity addChargingPoint(@RequestBody ChargingPoint chargingPoint) {

		chargingPointService.addChargingPoint(chargingPoint);

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{chargeDeviceID}", method = RequestMethod.DELETE)
	ResponseEntity deleteChargingPoint(@PathVariable("chargeDeviceID") String chargeDeviceID) {

		chargingPointService.deleteChargingPoint(chargeDeviceID);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.PUT)
	ResponseEntity updateChargingPoint(@RequestBody ChargingPoint chargingPoint) {

		chargingPointService.updateChargingPoint(chargingPoint);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{chargeDeviceID}", method = RequestMethod.GET)
	ResponseEntity<ChargingPoint> getChargingPoint(@PathVariable("chargeDeviceID") String chargeDeviceID) {

		ChargingPoint chargingPoint = chargingPointService.getChargingPoint(chargeDeviceID);
		return new ResponseEntity<>(chargingPoint, HttpStatus.OK);
	}

	@RequestMapping(value = "/nearest-point", method = RequestMethod.GET)
	ResponseEntity<List<ChargingPoint>> getNearestChargingPoints(@RequestParam(required = true) double latitude,
			@RequestParam(required = true) double longitude, @RequestParam(required = true) int pointNumber) {

		List<ChargingPoint> chargingPoints = chargingPointService.getNearestChargingPoints(latitude, longitude,
				pointNumber);
		return new ResponseEntity<>(chargingPoints, HttpStatus.OK);
	}

}
