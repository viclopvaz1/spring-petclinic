package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.model.TipoOperacion;
import org.springframework.samples.petclinic.repository.CitaOperacionRepository;

public interface SpringDataCitaOperacionRepository extends CitaOperacionRepository, Repository<CitaOperacion, Integer> {

	@Override
	@Query("SELECT citaOperacion FROM CitaOperacion citaOperacion WHERE citaOperacion.tipoOperacion.name LIKE :tipoOperacion%")
	Collection<CitaOperacion> findCitaOperacionByTipoOperacion(@Param("tipoOperacion") String tipoOperacion);
	
	@Override
	@Query("SELECT citaOperacion FROM CitaOperacion citaOperacion WHERE citaOperacion.id = :citaOperacionId")
	CitaOperacion findCitaOperacionById(@Param("citaOperacionId") int citaOperacionId);
	
//	@Override
//	@Query("SELECT tipoOperacion FROM TipoOperacion tipoOperacion WHERE tipoOperacion.name LIKE :name%")
//	TipoOperacion findTipoOperacionByName(@Param("name") String name);
	
//	@Override
//	@Query("SELECT tipoOperacion FROM TipoOperacion tipoOperacion ORDER BY tipoOperacion.name")
//	Collection<TipoOperacion> findTiposOperaciones() throws DataAccessException;

}
