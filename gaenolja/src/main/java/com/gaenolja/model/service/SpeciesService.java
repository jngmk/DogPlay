package com.gaenolja.model.service;

import java.util.List;

import com.gaenolja.model.dto.Species;

public interface SpeciesService {
	public Species search(int id);
	public List<Species> searchall();
	public void insert(Species species);
	public void update(Species species);
	public void delete(int id);
}
