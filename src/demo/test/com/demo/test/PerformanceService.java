package com.demo.test;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;
import org.paramecium.transaction.annotation.Transactional;

@Service
@Transactional
public class PerformanceService {

	GenericOrmDao<Performance, Long> ormDao = new GenericOrmDao<Performance, Long>(Performance.class);
	
	public PerformanceService() {
		ormDao.setValidation(true);
	}
	
	public void save(Performance performance) throws Exception{
		ormDao.insert(performance);
	}
	
	public Page page(Page page){
		return ormDao.select(page);
	}
	
}
