/**
 * 
 */
package com.epro.pms.project_basic_info.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epro.pms.exception.CommonException;
import com.epro.pms.project_basic_info.po.KeyRolesMatchBaselineInfo;
import com.epro.pms.project_basic_info.po.StatisticalFormInfo;
import com.epro.pms.project_basic_info.service.KeyRolesMatchBaseService;
import com.epro.pms.util.JSONUtils;
import com.epro.pms.util.po.Header;
import com.epro.pms.util.po.Response;
import com.epro.pms.util.po.RspConst;

/**
 * @author epro
 *
 */
@Controller
@RequestMapping("/keyroleBaseLine")
public class KeyRolesMatchBaseController {
	
	@Autowired
	private KeyRolesMatchBaseService keyroleBaseLineService;
	
	private Logger logger=LogManager.getLogger(KeyRolesMatchBaseController.class);
	
	@ResponseBody
	@RequestMapping(value="/findKeyRoleBaseLine.do",method = RequestMethod.POST)
	public Map<String,Object> findKeyRoleBaseLine(@RequestBody Map<String, Object> paramMap) throws CommonException{
		logger.info("--22---进入KeyRolesMatchBaseController类findKeyRoleBaseLine方法-------");
		Map<String, Object> headerMap = (Map<String, Object>) paramMap.get("header");
		Map<String, Object> requestMap = (Map<String, Object>) paramMap.get("request");
		
		KeyRolesMatchBaselineInfo kroleinfo = new KeyRolesMatchBaselineInfo();
		kroleinfo.setArea(requestMap.get("area")+"");
		kroleinfo.setProductLine(requestMap.get("productLine")+"");
		
		List<KeyRolesMatchBaselineInfo> listInfo = keyroleBaseLineService.findKeyRoleMatchBaseline(kroleinfo);
		
		Integer tseTotal = 0,pmTotal =0,plTotal=0,tcTotal=0,seTotal=0,mdeTotal=0,countTotal=0,isroleTotal=0,keyrole=0,count=0,succescount=0;
		for (KeyRolesMatchBaselineInfo kr : listInfo) {
			//1.TSE  2.PM  3.PL  4.TC  5.SE   6.MDE
			Integer tse = kr.getTseCount() == null ? 0 : kr.getTseCount();
			Integer pm = kr.getPmCount() == null ? 0 : kr.getPmCount();
			Integer pl = kr.getPlCount() == null ? 0 : kr.getPlCount();
			Integer tc = kr.getTcCount() == null ? 0 : kr.getTcCount();
			Integer se = kr.getSeCount() ==null ? 0 :kr.getSeCount();
			Integer mde = kr.getMdeCount() == null ? 0 :kr.getMdeCount();
			//关键角色的总人数
			 keyrole  = kr.getIsKeyRole() == null ? 0 :kr.getIsKeyRole();
			//总人数
			 count = kr.getCount() == null ? 0 :kr.getCount();
			//关键角色的占比
			if(keyrole!= 0 && count !=0){
				Integer keyRolecount = (keyrole* 100 / count) ;
				kr.setKeyroleAccount(keyRolecount);
			    //匹配度 matchAccount
				float p = (float)keyrole / count ;
				float pi = 0.0f;
				if (p >= KeyRolesMatchBaselineInfo.STANDARD){
					 pi = 100.0f;
					 kr.setMatchAccount(pi);
				}else{
					 pi = Math.round((p * 100) / KeyRolesMatchBaselineInfo.STANDARD);
					 kr.setMatchAccount(pi);
				}
			}
			//答辩通过的人数
			succescount  = kr.getIsAnswerPassed() == null ? 0 :kr.getIsAnswerPassed();
			//胜任
			if(succescount != 0 && !succescount.equals("")){
				float competentAccount =0.0f;
				if(keyrole != 0 && !keyrole.equals("")){
					competentAccount = (succescount*100 / keyrole);
				}
				kr.setCompetentAccount(competentAccount);
			}
			
			kr.setIsKeyRole(keyrole);
			kr.setCount(count);
			
			tseTotal += tse;
			pmTotal += pm;
			plTotal += pl;
			tcTotal += tc;
			seTotal += se;
			mdeTotal += mde;
			countTotal += count;
			isroleTotal += keyrole;
		}	
		
		
		if(!listInfo.isEmpty() && !"".equals(listInfo)){
			//关键角色的占比
			Integer keyRolecount =0;float pi = 0.0f;
			if(isroleTotal!= 0 && countTotal !=0){
				keyRolecount = (isroleTotal* 100 / countTotal) ;
			    //匹配度 matchAccount
				float p = (float)isroleTotal / countTotal ;
				if (p >= KeyRolesMatchBaselineInfo.STANDARD){
					 pi = 100.0f;
				}else{
					 pi = Math.round((p * 100) / KeyRolesMatchBaselineInfo.STANDARD);
				}
			}
			//胜任
			float competentAccount =0.0f;
			if(succescount != 0 && !succescount.equals("")){
				if(isroleTotal != 0 && !isroleTotal.equals("")){
					competentAccount = (succescount*100 / isroleTotal);
				}
			}
			
			KeyRolesMatchBaselineInfo kroleMatchInfo = new KeyRolesMatchBaselineInfo();
			kroleMatchInfo.setProductLine("总计");
			kroleMatchInfo.setTseCount(tseTotal);
			kroleMatchInfo.setPmCount(pmTotal);
			kroleMatchInfo.setPlCount(plTotal);
			kroleMatchInfo.setTcCount(tcTotal);
			kroleMatchInfo.setIsKeyRole(isroleTotal);//isKeyRole
			kroleMatchInfo.setSeCount(seTotal);
			kroleMatchInfo.setMdeCount(mdeTotal);
			kroleMatchInfo.setCount(countTotal);
			kroleMatchInfo.setKeyroleAccount(keyRolecount); //keyroleAccount
			kroleMatchInfo.setMatchAccount(pi);//matchAccount
			kroleMatchInfo.setCompetentAccount(competentAccount);//competentAccount
			listInfo.add(kroleMatchInfo);
		}
		//String  tolist= JSONUtils.toJSONString(listInfo);
		//System.out.println(tolist);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(RspConst.HEADER, new Header(RspConst.SUCCESS_CODE,RspConst.SUCCESS_MSG,1,1,1));
		resMap.put(RspConst.RESPONSE,new Response("result",listInfo));
		logger.info("------退出KeyRolesMatchBaseController类findKeyRoleBaseLine方法-------");
		return resMap;
	}
	
}
