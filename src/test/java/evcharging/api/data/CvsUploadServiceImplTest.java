package evcharging.api.data;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import evcharging.api.data.CvsUploadServiceImpl;
import evcharging.api.model.ChargingPoint;
import evcharging.api.service.ChargingPointService;

@RunWith(MockitoJUnitRunner.class)
public class CvsUploadServiceImplTest {

	@Mock
	ChargingPointService service;

	@Mock
	ModelMapper mapper;

	@InjectMocks
	CvsUploadServiceImpl cvsService;

	StringBuilder writer;

	ChargingPoint cPoint;

	byte[] file;

	@Before
	public void setUp() throws IOException {

		MockitoAnnotations.initMocks(this);

		writer = new StringBuilder("charging_point.csv");
		writer.append("chargeDeviceID");
		writer.append(',');
		writer.append("reference");
		writer.append(',');
		writer.append("name");
		writer.append(',');
		writer.append("latitude");
		writer.append(',');
		writer.append("longitude");
		writer.append('\n');
		writer.append("cfeedcdd5e287bef4b583158a12363f1");
		writer.append(',');
		writer.append("SRC_LDN60188");
		writer.append(',');
		writer.append("2 Riddons Road");
		writer.append(',');
		writer.append("51.431454");
		writer.append(',');
		writer.append("0.031175");
		writer.append('\n');

		cPoint = new ChargingPoint();
		cPoint.setChargeDeviceID("cfeedcdd5e287bef4b583158a12363f1");
		cPoint.setLatitude(51.431454);
		cPoint.setLongitude(0.031175);

		file = writer.toString().getBytes();

	}

	@Test
	public void bulkSave() {

		Mockito.when(service.addChargingPoint(cPoint)).thenReturn(true);
		cvsService.bulkSave(file);

	}

}
