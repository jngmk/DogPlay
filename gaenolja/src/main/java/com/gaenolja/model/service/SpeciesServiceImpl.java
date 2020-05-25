package com.gaenolja.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaenolja.model.dao.SpeciesDAO;
import com.gaenolja.model.dto.Species;

@Service
public class SpeciesServiceImpl implements SpeciesService{
	
	@Autowired
	private SpeciesDAO dao;
	
	@Override
	public Species search(int id) {
		try {
			Species species = dao.search(id);
			return species;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	};
	
	@Override
	public List<Species> searchall(){
		try {
			List<Species> species = dao.searchall();
			return species;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean insert(Species species) {
		try {
			dao.insert(species);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean update(Species species) {
		try {
			dao.update(species);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean delete(int id) {
		try {
			dao.delete(id);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
