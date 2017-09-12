package com.epro.pms.common.service.impl;

import java.io.File;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import com.epro.pms.common.dao.ICommonMapper;
import com.epro.pms.common.po.OptionConst;
import com.epro.pms.common.po.OptionPO;
import com.epro.pms.common.service.ICommonService;

@Service
public class CommonServiceImpl implements ICommonService {
	@Autowired
	private ICommonMapper commonMapper;
	
	private static Log log = LogFactory.getLog(CommonServiceImpl.class);
	
	public static final Map<String, List<OptionPO>> optionCache = new HashMap<String, List<OptionPO>>();
	
	public static final String ROOT_PATH = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/config/common_config.xml");
	
	public List<OptionPO> queryOptionList(Map<String, Object> paramMap) {
		
		Integer type = (Integer) paramMap.get("type");  // 获得请求类型
		
		List<OptionPO> optionList = null;
		
		switch(type) {
			case OptionConst.REGION:
				optionList = getOptionList(OptionConst.REGION_VAR);
				break;
			case OptionConst.PRODUCT_LINE:
				optionList = getOptionList(OptionConst.PRODUCT_LINE_VAR);
				break;
			case OptionConst.PROJECT_TYPE:
				optionList = getOptionList(OptionConst.PROJECT_TYPE_VAR);
				break;
			case OptionConst.DEVELOP_TYPE:
				optionList = getOptionList(OptionConst.DEVELOP_TYPE_VAR);
				break;
			case OptionConst.BUSINESS_MODE:
				optionList = getOptionList(OptionConst.BUSINESS_MODE_VAR);
				break;
			case OptionConst.PROJECT_FLOW:
				optionList = getOptionList(OptionConst.PROJECT_FLOW_VAR);
				break;
			case OptionConst.PROJECT_CLASSIFY:
				optionList = getOptionList(OptionConst.PROJECT_CLASSIFY_VAR);
				break;
			case OptionConst.PROJECT_ROLE:
				optionList = getOptionList(OptionConst.PROJECT_ROLE_VAR);
				break;
			case OptionConst.COOPERATE_MODE:
				optionList = getOptionList(OptionConst.COOPERATE_MODE_VAR);
				break;
			case OptionConst.COOPERATE_TYPE:
				optionList = getOptionList(OptionConst.COOPERATE_TYPE_VAR);
				break;
			case OptionConst.PDU:
				optionList = getOptionList(OptionConst.PDU_VAR);
				break;
			case OptionConst.PROJ_STATE:
				optionList = getOptionList(OptionConst.PROJ_STATE_VAR);
				break;
			case OptionConst.CAPACITY_COEFFICIENT:
				optionList = getOptionList(OptionConst.CAPACITY_COEFFICIENT_VAR);
				break;
			case OptionConst.ROLE:
				optionList = getOptionList(OptionConst.ROLE_VAR);
				break;
			default:
				optionList = new ArrayList<OptionPO>();
		}
	
		return optionList;
	}
	
	
	
	private List<OptionPO> getOptionList(String var) {
		List<OptionPO> optionList = null;
		if (optionCache.containsKey(var)) {
			log.info(var + "有缓存，返回缓存数据。。。");
			optionList = optionCache.get(var);
		} else {
			log.info(var + "无缓存，读取xml文件。。。");
			// 增加至容器
			File file = new File(ROOT_PATH);
			optionList = parseXMl(file, var);
			
			if (!optionList.isEmpty()) {
				optionCache.put(var, optionList); 
			}
		}
		return optionList;
	}
	
	private List<OptionPO> parseXMl(File file, String attr)  {
	    //1.读取XML文件,获得document对象              
        SAXReader reader = new SAXReader();            
        Document document = null;
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
        //2.获得dropdown节点
        Element dnNode = document.getRootElement().element("dropdown");  
        List<OptionPO> optionList = new ArrayList<OptionPO>();
        @SuppressWarnings("unchecked")
        
		Iterator<Element> selIter = dnNode.elementIterator("select"); 
        while(selIter.hasNext()) {
        	// get selNode
        	Element selNode = selIter.next();
        	// if attr of this selNode equals arg.
        	if(attr.equals(selNode.attributeValue("name"))) {
        		// loop option node
        		Iterator<Element> optIter = selNode.elementIterator("option");
        		while (optIter.hasNext()) {
        			Element optNode = optIter.next();
        			String value = optNode.attributeValue("value");
                	String text = optNode.getText();
                	optionList.add(new OptionPO(value, text));
        		}
        		break;
        	}
        }
        
        return optionList;
	}


	@Override
	public List<OptionPO> queryProLineOptionList(Map<String, Object> paramMap) {
		return commonMapper.queryProLineOptionList(paramMap);
	}



	@Override
	public List<OptionPO> queryPDUOptionList(Map<String, Object> paramMap) {
		return commonMapper.queryPDUOptionList(paramMap);
	}



	@Override
	public void saveNewProLineAndPdu(Map<String, Object> paramMap) {
		log.info("------method:saveNewProLineAndPdu-----， param=" + paramMap);
		// step1.查询是否存在产品线和产品记录
		boolean isExists = commonMapper.isProLineAndPduExists(paramMap);
		log.info("------数据是否该产品和产品线-----，result=" + isExists);
		
		// step2. 插入新的记录
		if (!isExists) {
			log.info("------新增产品和产品线-----，param=" + paramMap);
			commonMapper.insertProLineAndPdu(paramMap);
		}
	}



	@Override
	public String generateProjTeamNo(Map<String, Object> paramMap) {
		File file = new File(ROOT_PATH);
		//1.读取XML文件,获得document对象              
        SAXReader reader = new SAXReader();            
        Document document = null;
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//2.获得dropdown节点
        Element dicNode = document.getRootElement().element("dictionary"); 
        String area = "";
        String proline = "";
        Iterator<Element> eleIter = dicNode.elementIterator("element");
       
        while(eleIter.hasNext()) {
        	// get selNode
        	Element eleNode = eleIter.next();
        	// if attr of this selNode equals arg.
        	String attr = eleNode.attributeValue("type");
        	if("area".equals(attr)) {
        		// loop option node
        		Iterator<Element> nodeIter = eleNode.elementIterator("node");
        		while (nodeIter.hasNext()) {
        			Element node = nodeIter.next();
        			String value = node.attributeValue("value");
                	String text = node.getText();
                	if (text.equals((String)paramMap.get("area"))) {
                		area = "HW" + value;
                		break;
                	}
        		}
        	} else if ("proline".equals(attr)) {
        		// loop option node
        		Iterator<Element> nodeIter = eleNode.elementIterator("node");
        		while (nodeIter.hasNext()) {
        			Element node = nodeIter.next();
        			String value = node.attributeValue("value");
                	String text = node.getText();
                	if (text.equals((String)paramMap.get("proline"))) {
                		proline = value;
                		break;
                	}
        		}
        	}
        }
        
        String maxNo = commonMapper.queryProjTeamMaxNo(paramMap);
        int no = Integer.parseInt(maxNo);
        //String[] arr = maxNo.split("-");
        //int no = Integer.parseInt(arr[2]);
        //no++; // 自增1
        String result = "";
        if(((no/100) + (no%100/10) + (no%10)) == 0){
        	result = area + "-" + proline + "-" + (no/100) + (no%100/10) + ((no%10) + 1);
        }else{
        	result = area + "-" + proline + "-" + (no/100) + (no%100/10) + (no%10);
        } 
               
        String ProjTeamNo = commonMapper.queryProjTeamNo(result);
        if(ProjTeamNo != null && !"".equals(ProjTeamNo)){
        	no = no + 1;
        	result = area + "-" + proline + "-" + (no/100) + (no%100/10) + (no%10);
        	return result;
        }
		return result;
	}
	
}
