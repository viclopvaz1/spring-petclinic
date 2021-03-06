package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataCitaOperacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaOperacionService {
	
	private SpringDataCitaOperacionRepository citaOperacionRepo;
	
	
	@Autowired
	public CitaOperacionService(final SpringDataCitaOperacionRepository stringCitaOperacionRepo) {
		this.citaOperacionRepo = stringCitaOperacionRepo;
	}
	
	@Transactional
	public Iterable<CitaOperacion> findAll() {
		Iterable<CitaOperacion> res = this.citaOperacionRepo.findAll();
		if (this.hayCitas(res)) {
			throw new NoSuchElementException();
		}
		return res;
	}

	@Transactional
	@Cacheable("citaOperacionByTipoOperacion")
	public Iterable<CitaOperacion> findCitaOperacionByTipoOperacion(final String tipoOperacion) throws NoSuchElementException {
		Iterable<CitaOperacion> res = this.citaOperacionRepo.findCitaOperacionByTipoOperacion(tipoOperacion);

		if (this.hayCitas(res)) {
			throw new NoSuchElementException();
		}
		return res;
	}
	
	@Transactional
	public Optional<CitaOperacion> findCitaOperacionById(final int CitaOperacionId) throws NoSuchElementException{
		return this.citaOperacionRepo.findCitaOperacionById(CitaOperacionId);
	}
	
	@Transactional
	public Collection<CitaOperacion> findCitaOperacionByPet(final int petId) throws NoSuchElementException{
		return this.citaOperacionRepo.findCitaOperacionByPet(petId);
	}
	
	@Transactional
	@CacheEvict(cacheNames = "citaOperacionByTipoOperacion", allEntries = true)
	public void saveCitaOperacion(final CitaOperacion citaOperacion) {
		this.citaOperacionRepo.save(citaOperacion);
	}
	
	@Transactional
	public void deleteCitaOperacion(final CitaOperacion citaOperacion) {
		this.citaOperacionRepo.delete(citaOperacion);
	}
	
	public Boolean hayCitas(Iterable<CitaOperacion> res) {
		return StreamSupport.stream(res.spliterator(), false).count() == 0;
	}
}
