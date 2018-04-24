package evcharging.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import evcharging.api.entity.ChargingPointEntity;

public interface ChargingPointDAO extends JpaRepository<ChargingPointEntity, String> {

	ChargingPointEntity findByLatitudeAndLongitude(Double latitude, Double longitude);

}
