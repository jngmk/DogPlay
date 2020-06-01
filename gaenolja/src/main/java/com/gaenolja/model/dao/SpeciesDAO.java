package com.gaenolja.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaenolja.model.dto.Species;

@Mapper
public interface SpeciesDAO {
	public Species search(int id);
	public List<Species> searchall();
	public void insert(Species species);
	public void update(Species species);
	public void delete(int id);
}
