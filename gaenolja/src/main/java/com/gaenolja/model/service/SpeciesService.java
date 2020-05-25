package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Species;

public interface SpeciesService {
	public Species search(int id);
	public List<Species> searchall();
	public boolean insert(Species species);
	public boolean update(Species species);
	public boolean delete(int id);
}
