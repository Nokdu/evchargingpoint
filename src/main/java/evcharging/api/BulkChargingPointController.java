package evcharging.api;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import evcharging.api.core.Messages;
import evcharging.api.data.DataUploadService;

@RestController
@RequestMapping("/bulk-charging-point")
public class BulkChargingPointController {

	public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";

	@Autowired
	private DataUploadService dataUploadService;

	@Autowired
	Messages messages;

	private static final Logger LOGGER = LoggerFactory.getLogger(BulkChargingPointController.class);

	@RequestMapping(value = "/save-csv", method = RequestMethod.POST)
	public ResponseEntity<?> uploadFile(@RequestParam(required = true) MultipartFile file) {

		try {

			dataUploadService.bulkSave(file.getBytes());
		} catch (IOException e) {
			LOGGER.error(messages.get("file.upload.problem"), e);
		}

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
