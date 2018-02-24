package com.iuling.mods.base.service;
/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.SerialCode;
import com.iuling.mods.base.mapper.SerialCodeMapper;

/**
 * 流水号编码信息Service
 * @author 宋小雄
 * @version 2017-09-18
 */
@Service
@Transactional(readOnly = true)
public class SerialCodeService extends CrudService<SerialCodeMapper, SerialCode> {

	public SerialCode get(String id) {
		return super.get(id);
	}
	
	public List<SerialCode> findList(SerialCode serialCode) {
		return super.findList(serialCode);
	}
	
	public Page<SerialCode> findPage(Page<SerialCode> page, SerialCode serialCode) {
		return super.findPage(page, serialCode);
	}
	
	@Transactional(readOnly = false)
	public void save(SerialCode serialCode) {
		super.save(serialCode);
	}
	
	@Transactional(readOnly = false)
	public void delete(SerialCode serialCode) {
		super.delete(serialCode);
	}

	public synchronized String getSerialCode(){
		String pre = new SimpleDateFormat("yyMMddHHmm").format(new Date(System.currentTimeMillis()));
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date(ca.get(Calendar.YEAR),ca.get(Calendar.MONTH),ca.get(Calendar.DAY_OF_YEAR),0,0));
		Date startTime = ca.getTime();
		ca.set(Calendar.DATE, ca.get(Calendar.DATE) - 1);
		Date endTime = ca.getTime();
		int count = mapper.findCountBetween(startTime,endTime) + 1;
		if (count < 10)
			pre = pre + "00" + count;
		else if (count >= 10 && count < 100)
			pre = pre +"0" + count;
		else
			pre = pre +count;
		save(new SerialCode(null,pre));
		return pre;
	}
}