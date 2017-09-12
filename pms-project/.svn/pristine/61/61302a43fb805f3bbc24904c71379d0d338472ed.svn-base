/**
 * 
 */
package com.epro.pms.project_basic_info.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epro.pms.exception.CommonException;
import com.epro.pms.project_basic_info.dao.StatisticalFormMapper;
import com.epro.pms.project_basic_info.po.ProjectBasicInfo;
import com.epro.pms.project_basic_info.po.StatisticalFormInfo;
import com.epro.pms.project_basic_info.service.StatisticalFormService;

/**
 * @author epro lvyalin
 *
 */
@Service
public class StatisticalFormServiceImpl implements StatisticalFormService{
	
	@Autowired
	private StatisticalFormMapper statisticalForm;
	

	public List<StatisticalFormInfo> listStatisticalForm(StatisticalFormInfo statisticalFormInfo)throws CommonException {
		// TODO Auto-generated method stub
		List<StatisticalFormInfo>  listStatisticalForm = statisticalForm.listStatisticalForm(statisticalFormInfo);
		Integer resoureTotal = 0;
		Integer taskTotal = 0;
		Integer opeTotal = 0;
		Integer e2eTotal = 0;
		Integer mixedTypeTotal = 0;
		Integer procount = 0 ,procountToal = 0,mixedTypeTeam = 0,projPersonCountTeam =0;
		Integer resoureT = 0,taskT =0,opeT=0,e2eT=0;
		for (StatisticalFormInfo sf : listStatisticalForm) {
			procountToal = sf.getProjectPersonCount() == null ? 0 : sf.getProjectPersonCount();
			mixedTypeTeam = sf.getMixedTypeTeam() == null ? 0:sf.getMixedTypeTeam();
			resoureT = sf.getResourceTeam() == null ? 0 : sf.getResourceTeam();
			taskT = sf.getTaskTeam() == null ? 0 : sf.getTaskTeam();
			opeT = sf.getOpeningTeam() == null ? 0 : sf.getOpeningTeam();
			e2eT = sf.getE2eTeam() == null ? 0 : sf.getE2eTeam();
			
			mixedTypeTotal += mixedTypeTeam;
			resoureTotal += resoureT;
			taskTotal += taskT;
			opeTotal += opeT;
			e2eTotal += e2eT;
			procountToal += projPersonCountTeam;
			//总和
			procount = e2eT+opeT+taskT+resoureT+mixedTypeTeam;
			procountToal =resoureTotal +taskTotal +opeTotal +e2eTotal +mixedTypeTotal;
			sf.setProjectPersonCount(procount);
			
			//资源型占比
			if(procount !=0){
				//混编型占比
				Integer mixedTypeASum = ((e2eT+opeT+taskT+resoureT+mixedTypeTeam)*100 / procount);
				sf.setMixedTypeAccount(mixedTypeASum);
				
				Integer resoutASum = ((e2eT+opeT+taskT+resoureT)*100 / procount);
				sf.setResourceAccount(resoutASum);
				//任务型占比
				Integer taskASum = ((e2eT+opeT+taskT)*100 / procount);
				sf.setTaskAccount(taskASum);
				//开发型占比
				Integer opeASum =  ((e2eT+opeT)*100 / procount);
				sf.setOpeningAccount(opeASum);
				//E2E型占比
				Integer e2eASum = (e2eT*100 / procount); 
				sf.setProjectAccount(e2eASum);
				//list.add(sf);
			}
		}	
		if(!listStatisticalForm.isEmpty() && !"".equals(listStatisticalForm)){
			Integer allTotal = (e2eTotal+opeTotal+taskTotal+resoureTotal);
			Integer mixedTypeASumTotal=0,resoutASumTotal=0,taskASumTotal=0,opeASumTotal=0,e2eASumTotal=0;
			if(allTotal !=0 ){
				//混编型占比
				mixedTypeASumTotal = ((allTotal + mixedTypeTotal)*100 /procountToal);
				//资源型占比
				resoutASumTotal = (allTotal*100 / procountToal) ;
				//任务型占比
				taskASumTotal = ((e2eTotal+opeTotal+taskTotal)*100 / procountToal);
				//开发型占比
				opeASumTotal = ((e2eTotal+opeTotal)*100 / procountToal);
				//E2E型占比
				e2eASumTotal = (e2eTotal*100 / procountToal);
			}
			StatisticalFormInfo info1 = new StatisticalFormInfo();
			info1.setProductLine("总计");
			info1.setMixedTypeTeam(mixedTypeTotal);
			info1.setResourceTeam(resoureTotal);
			info1.setTaskTeam(taskTotal);
			info1.setOpeningTeam(opeTotal);
			info1.setE2eTeam(e2eTotal);
			info1.setProjectPersonCount(procountToal);
			
			info1.setMixedTypeAccount(mixedTypeASumTotal);
			info1.setResourceAccount(resoutASumTotal);
			info1.setTaskAccount(taskASumTotal);
			info1.setOpeningAccount(opeASumTotal);
			info1.setProjectAccount(e2eASumTotal);
			listStatisticalForm.add(info1);
		}
		return listStatisticalForm;
	}

	public List<Map<String, Object>> getAreaData() throws CommonException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> listMap= statisticalForm.getAreaData();
		return listMap;
	}

	

}
