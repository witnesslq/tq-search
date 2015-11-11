package cn.taqu.search.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.taqu.search.web.springmvc.JsonResult;

@Service
public class SearchService {

	private static Logger logger = LoggerFactory.getLogger(SearchService.class);
	
	@Autowired
	@Value("${solr.accountServerUrl}")
	private String accountServerUrl;
	
	@Autowired
	@Value("${solr.forumServerUrl}")
	private String forumServerUrl;
	
	/**
	 * @Title:queryOpra
	 * @Description:solr查询操作
	 * @param queryField 查询的字段
	 * @param fieldValue 查询字段对应的值
	 * @param start 查询的起始位置
	 * @param rows 一次查出来的数量
	 * @param sortField 排序字段
	 * @param sort 排序（升或降）
	 * @param hightlight 是否高亮
	 * @param hlField 高亮字段
	 * @param serverUrl solr服务url
	 * @return
	 * @author:huangyuehong
	 * @Date:2015年10月27日 下午5:20:56
	 */
	public JsonResult queryOpra(String queryField, String fieldValue, int start, int rows, String sortField, String sort, String hightlight, String hlField) {
		String serverUrl;
		if(queryField.equals("account")){
			serverUrl = accountServerUrl;
		}else{
			serverUrl = forumServerUrl;
		}
		HttpSolrServer server = createSolrServer(serverUrl);
		SolrQuery queryParams = new SolrQuery();
		try {
			// 查询关键词，*:*代表所有属性、所有值，即所有index
			// params.set("q", "*:*");
			queryParams.set("q", queryField + ":" + fieldValue);
			queryParams.set("df", queryField);//关键词中有空格也可以查询，相当于or
			// 分页，start=0就是从0开始，，rows=5当前返回5条记录，第二页就是变化start这个值为5就可以了。
			queryParams.set("start", start);
			queryParams.set("rows", rows);

			// 按queryField和sort排序，asc升序 desc降序
			if (sortField != null && sortField.trim().length() != 0) {
				if (sort != null && sort.trim().length() != 0) {
					queryParams.set("sort", sortField + " " + sort);
				} else {
					// 默认为升序asc
					queryParams.set("sort", sortField + " asc");
				}
			}

			// 设置高亮
			if (hightlight != null && hightlight.trim().length() != 0 && hightlight.equals("true") && null != hlField && hlField.trim().length() != 0) {
				queryParams.setHighlight(true); // 开启高亮组件
				queryParams.addHighlightField(hlField);// 高亮字段
				queryParams.setHighlightSimplePre("<font color=\"red\">");// 标记
				queryParams.setHighlightSimplePost("</font>");
				queryParams.setHighlightSnippets(1);// 结果分片数，默认为1
				queryParams.setHighlightFragsize(1000);// 每个分片的最大长度，默认为100
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		QueryResponse response = null;
		SolrDocumentList docs = null;
		//查询结果
		Map resultMap = new HashMap();
		
		try {
			response = server.query(queryParams);
			docs = response.getResults();
			resultMap.put("numFound", docs.getNumFound());
			resultMap.put("start", docs.getStart());
			resultMap.put("docs", docs);
			resultMap.put("hightlight", response.getHighlighting());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 返回查询结果
		return JsonResult.success(resultMap);
	}

	/**
	 * @Title:createSolrServer
	 * @Description:创建solr服务器
	 * @param serverUrl 服务url
	 * @return
	 * @author:huangyuehong
	 * @Date:2015年10月29日 下午1:52:53
	 */
	public HttpSolrServer createSolrServer(String serverUrl) {
		HttpSolrServer solrServer = null;
		try {
			solrServer = new HttpSolrServer(serverUrl);
			solrServer.setConnectionTimeout(100);
			solrServer.setDefaultMaxConnectionsPerHost(100);
			solrServer.setMaxTotalConnections(100);
		} catch (Exception e) {
			//System.out.println("请检查tomcat服务器或端口是否开启!");
			e.printStackTrace();
		}
		return solrServer;
	}

	/**
	 * @Title:buildIndex
	 * @Description:全量索引
	 * @param serverUrl 服务url
	 * @param entity 实体类
	 * @author:huangyuehong
	 * @Date:2015年11月2日 上午11:08:45
	 */
	public void buildIndex(String serverUrl, String entity) {
		HttpSolrServer solrServer = createSolrServer(serverUrl);
		SolrQuery query = new SolrQuery();
		// 指定RequestHandler，默认使用/select
		query.setRequestHandler("/dataimport");

		query.setParam("command", "full-import");
		query.setParam("clean", "true");
		query.setParam("commit", "true");
		query.setParam("entity", entity);
		query.setParam("optimize", "true");
		
		try {
			solrServer.query(query);
		} catch (Exception e) {
			logger.error("重建索引时遇到错误： " + e);
		}
	}
}
