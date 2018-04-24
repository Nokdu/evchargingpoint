package evcharging.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import org.springframework.dao.DataIntegrityViolationException;

import evcharging.api.core.Messages;
import evcharging.api.dao.ChargingPointDAO;
import evcharging.api.entity.ChargingPointEntity;
import evcharging.api.model.ChargingPoint;
import evcharging.api.service.ChargingPointServiceImpl;
import evcharging.api.validation.ChargingPointValidator;

@RunWith(MockitoJUnitRunner.class)
public class ChargingPointServiceImplTest {

	@Mock
	ChargingPointDAO dao;

	@Mock
	ModelMapper mapper;

	@Mock
	Messages messages;

	@Mock
	private ChargingPointValidator validator;

	@InjectMocks
	ChargingPointServiceImpl cPointServiceImpl;

	List<ChargingPoint> chargingPoints;
	List<ChargingPointEntity> chargingPointEntities;
	ChargingPointEntity chargingPointEntity;
	ChargingPointEntity chargingPointEntity2;
	ChargingPoint chargingPoint2;
	ChargingPoint chargingPoint;

	@Before
	public void setUp() {

		prepareChargingPointList();

		prepareChargingPointEntityList();

	}

	private void prepareChargingPointEntityList() {

		chargingPointEntity = new ChargingPointEntity();
		chargingPointEntity.setChargeDeviceID("cfeedcdd5e287bef4b583158a12363f1");
		chargingPointEntity.setLatitude(51.48092);
		chargingPointEntity.setLongitude(-0.419318);

		chargingPointEntity2 = new ChargingPointEntity();
		chargingPointEntity2.setChargeDeviceID("e2f964e176efb40969652e3249023645");
		chargingPointEntity2.setLatitude(51.52834);
		chargingPointEntity2.setLongitude(-0.118619);

		chargingPointEntities = new ArrayList<ChargingPointEntity>();
		chargingPointEntities.add(chargingPointEntity);
		chargingPointEntities.add(chargingPointEntity2);

	}

	private void prepareChargingPointList() {
		chargingPoint = new ChargingPoint();
		chargingPoint.setChargeDeviceID("cfeedcdd5e287bef4b583158a12363f1");
		chargingPoint.setLatitude(51.48092);
		chargingPoint.setLongitude(-0.419318);

		chargingPoint2 = new ChargingPoint();
		chargingPoint2.setChargeDeviceID("e2f964e176efb40969652e3249023645");
		chargingPoint2.setLatitude(51.52834);
		chargingPoint2.setLongitude(-0.118619);

		chargingPoints = new ArrayList<ChargingPoint>();
		chargingPoints.add(chargingPoint);
		chargingPoints.add(chargingPoint2);

	}

	@Test
	public void getAllChargingPoints() {

		Mockito.when(dao.findAll()).thenReturn(chargingPointEntities);
		Mockito.when(mapper.map(chargingPointEntity, ChargingPoint.class)).thenReturn(chargingPoint);
		Mockito.when(mapper.map(chargingPointEntity2, ChargingPoint.class)).thenReturn(chargingPoint2);

		List<ChargingPoint> cPoints = cPointServiceImpl.getAllChargingPoints();
		assertEquals(cPoints.size(), 2);
		assertEquals(chargingPointEntity.getChargeDeviceID(), cPoints.get(0).getChargeDeviceID());
	}

	@Test
	public void getChargingPoint() {
		Mockito.when(dao.findOne("cfeedcdd5e287bef4b583158a12363f1")).thenReturn(chargingPointEntity);
		Mockito.when(mapper.map(chargingPointEntity, ChargingPoint.class)).thenReturn(chargingPoint);
		ChargingPoint cPoint = cPointServiceImpl.getChargingPoint("cfeedcdd5e287bef4b583158a12363f1");
		assertEquals(cPoint.getChargeDeviceID(), "cfeedcdd5e287bef4b583158a12363f1");
		assertTrue(cPoint.getLatitude() == 51.48092);
		assertTrue(cPoint.getLongitude() == -0.419318);
	}

	@Test
	public void getNearestChargingPoints() {

		Mockito.when(dao.findAll()).thenReturn(chargingPointEntities);
		Mockito.when(mapper.map(chargingPointEntity, ChargingPoint.class)).thenReturn(chargingPoint);
		Mockito.when(mapper.map(chargingPointEntity2, ChargingPoint.class)).thenReturn(chargingPoint2);

		List<ChargingPoint> cPoints = cPointServiceImpl.getNearestChargingPoints(51.52814, -0.118621, 1);

		assertTrue(cPoints.size() == 1);
		assertEquals(cPoints.get(0).getChargeDeviceID(), "e2f964e176efb40969652e3249023645");
		assertTrue(cPoints.get(0).getLatitude() == 51.52834);

	}

	@Test
	public void isAlreadyExistWhenNotExist() {
		ChargingPoint chargingPoint = new ChargingPoint();
		chargingPoint.setChargeDeviceID("cfeedcdd5e287bef4b583158a12363f1");
		chargingPoint.setLatitude(51.48092);
		chargingPoint.setLongitude(-0.419318);

		Mockito.when(dao.findOne("cfeedcdd5e287bef4b583158a12363f1")).thenReturn(null);
		Mockito.when(dao.findByLatitudeAndLongitude(51.48092, -0.419318)).thenReturn(null);
		assertFalse(cPointServiceImpl.isAlreadyExist(chargingPoint));
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void isAlreadyExistWhenExistWithSameDeviceID() {
		ChargingPoint chargingPoint3 = new ChargingPoint();
		chargingPoint3.setChargeDeviceID("cfeedcdd5e287bef4b583158a12363f1");
		chargingPoint3.setLatitude(51.48092);
		chargingPoint3.setLongitude(-0.419318);

		Mockito.when(dao.findOne("cfeedcdd5e287bef4b583158a12363f1")).thenReturn(chargingPointEntity);
		Mockito.when(mapper.map(chargingPointEntity, ChargingPoint.class)).thenReturn(chargingPoint);
		Mockito.when(messages.get("charging.point.already.exist.with.this.id")).thenReturn("Already exist");
		cPointServiceImpl.isAlreadyExist(chargingPoint3);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void isAlreadyExistWhenExistWithDifferentDeviceIDAndSameLatLong() {
		ChargingPoint chargingPoint3 = new ChargingPoint();
		chargingPoint3.setChargeDeviceID("cfeedcdd5e287bef4b583158a12363f1");
		chargingPoint3.setLatitude(51.48092);
		chargingPoint3.setLongitude(-0.419318);

		Mockito.when(dao.findOne("cfeedcdd5e287bef4b583158a12363f1")).thenReturn(chargingPointEntity);
		Mockito.when(dao.findByLatitudeAndLongitude(51.48092, -0.419318)).thenReturn(chargingPointEntity);
		Mockito.when(mapper.map(chargingPointEntity, ChargingPoint.class)).thenReturn(chargingPoint);
		Mockito.when(messages.get("charging.point.already.exist.with.this.id")).thenReturn("Already exist");
		cPointServiceImpl.isAlreadyExist(chargingPoint3);

	}
}
