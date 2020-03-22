package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Adiestrador;

public interface SpringDataAdiestradorRepository extends AdiestradorRepository, Repository<Adiestrador, Integer> {
	
	@Override
	@Query("SELECT adiestrador FROM Adiestrador adiestrador WHERE adiestrador.estrellas = 5")
	Collection<Adiestrador> findAdiestradorByEstrellas(@Param("estrellas") Integer estrellas);

}