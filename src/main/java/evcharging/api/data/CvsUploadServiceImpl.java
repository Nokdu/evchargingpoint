package evcharging.api.data;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBeanBuilder;

import evcharging.api.model.ChargingPoint;
import evcharging.api.model.CvsChargingPoint;
import evcharging.api.service.ChargingPointService;

@Service
public class CvsUploadServiceImpl implements DataUploadService {

	@Autowired
	ChargingPointService service;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Transactional
	public void bulkSave(byte[] file) {

		List<CvsChargingPoint> cvsChargingPoints = new CsvToBeanBuilder(new StringReader(new String(file)))
				.withType(CvsChargingPoint.class).withSkipLines(1).build().parse();

		List<ChargingPoint> chargingPoints = cvsChargingPoints.stream()
				.map(m -> modelMapper.map(m, ChargingPoint.class)).collect(Collectors.toList());

		for (ChargingPoint chargingPoint : chargingPoints) {
			service.addChargingPoint(chargingPoint);
		}

	}

}
