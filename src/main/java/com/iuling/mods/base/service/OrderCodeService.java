
package com.iuling.mods.base.service;

import java.util.List;

import com.iuling.comm.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.OrderCode;
import com.iuling.mods.base.mapper.OrderCodeMapper;

/**
 * 生成各类单号Service
 * @author 潘俞再
 * @version 2017-09-21
 */
@Service
@Transactional(readOnly = true)
public class OrderCodeService extends CrudService<OrderCodeMapper, OrderCode> {

	public OrderCode get(String id) {
		return super.get(id);
	}
	
	public List<OrderCode> findList(OrderCode orderCode) {
		return super.findList(orderCode);
	}
	
	public Page<OrderCode> findPage(Page<OrderCode> page, OrderCode orderCode) {
		return super.findPage(page, orderCode);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderCode orderCode) {
		super.save(orderCode);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderCode orderCode) {
		super.delete(orderCode);
	}
	/**
	 * 生成单号
	 */
	@Transactional(readOnly = false)
	public synchronized String getOrderCode(String key) {
		String code = null;
		String date = DateUtils.getDate1();
		//获取这个key的值 如果有就返回 +1  没有就添加
		OrderCode orderCode = new OrderCode();
		orderCode.setOrderKey(key);
		orderCode = this.get(key);
		if(orderCode != null){
			Integer num = Integer.parseInt(orderCode.getOrderVal())+1;
			//前面补0
			String temp = "";
			for(int i = 0;i<4-(num.toString().length());i++){
				temp += "0";
			}
			String stringNum = temp+num;
			code = key+date+stringNum;
			//更新最新值
			orderCode.setOrderVal(num+"");
			mapper.update(orderCode);
		}else{
			code = key+date+"0001";
			orderCode = new OrderCode();
			orderCode.setOrderKey(key);
			orderCode.setOrderVal("1");
			this.save(orderCode);
		}

		return code;
	}
}