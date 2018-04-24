package evcharging.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import evcharging.api.ChargingPointController;
import evcharging.api.model.ChargingPoint;
import evcharging.api.service.ChargingPointService;

@RunWith(MockitoJUnitRunner.class)
public class ChargingPointControllerTest {

	List<ChargingPoint> chargingPoints;

	@Mock
	ChargingPointService chargingPointService;

	@Mock
	ModelMapper modelMapper;

	@InjectMocks
	ChargingPointController chargingPointController;

	ChargingPoint chargingPoint;

	@Before
	public void setUp() {

		chargingPoint = new ChargingPoint();
		chargingPoint.setChargeDeviceID("cfeedcdd5e287bef4b583158a12363f1");
		chargingPoint.setLatitude(51.48092);
		chargingPoint.setLongitude(-0.419318);

		ChargingPoint chargingPoint2 = new ChargingPoint();
		chargingPoint2.setChargeDeviceID("e2f964e176efb40969652e3249023645");
		chargingPoint2.setLatitude(51.52834);
		chargingPoint2.setLongitude(-0.118619);

		chargingPoints = new ArrayList<ChargingPoint>();
		chargingPoints.add(chargingPoint);
		chargingPoints.add(chargingPoint2);

	}

	@Test
	public void getAllChargingPoints() {

		Mockito.when(chargingPointService.getAllChargingPoints()).thenReturn(chargingPoints);
		List<ChargingPoint> chargingpoints = chargingPointController.getAllChargingPoints().getBody();
		assertEquals(chargingpoints.get(0).getChargeDeviceID(), "cfeedcdd5e287bef4b583158a12363f1");
		assertTrue(chargingpoints.get(1).getLatitude() == 51.52834);
		assertEquals(chargingpoints.size(), 2);

	}

	@Test
	public void addChargingPoint() {

		String result = (String) chargingPointController.addChargingPoint(chargingPoint).getBody();
		assertEquals(result, null);
	}

	@Test
	public void deleteChargingPoint() {
		String chargeDeviceID = "565656546";
		String result = (String) chargingPointController.deleteChargingPoint(chargeDeviceID).getBody();
		assertEquals(result, null);
	}

	@Test
	public void updateChargingPoint() {
		String result = (String) chargingPointController.updateChargingPoint(new ChargingPoint()).getBody();
		assertEquals(result, null);
	}

	@Test
	public void getNearestChargingPoints() {
		Mockito.when(chargingPointService.getNearestChargingPoints(51.509939, -0.142737, 2)).thenReturn(chargingPoints);
		List<ChargingPoint> cPoints = chargingPointController.getNearestChargingPoints(51.509939, -0.142737, 2)
				.getBody();

		assertEquals(cPoints.size(), 2);
		assertEquals(cPoints.get(0).getChargeDeviceID(), "cfeedcdd5e287bef4b583158a12363f1");
		assertTrue(cPoints.get(1).getLatitude() == 51.52834);

	}

}
