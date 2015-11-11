package cn.taqu.search.web.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.taqu.search.service.SearchService;
import cn.taqu.search.web.api.common.RequestParams;
import cn.taqu.search.web.api.constant.CodeStatus;
import cn.taqu.search.web.springmvc.JsonResult;

@RestController
@RequestMapping(value = "/api", params = "service=search")
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	/**
	 * @Title:select
	 * @Description:查询操作
	 * @param params
	 * @return
	 * @author:huangyuehong
	 * @Date:2015年10月27日 下午5:54:09
	 */
	@RequestMapping(params = "method=select")
	public JsonResult select(RequestParams params) {
		// 查询的字段、查询的字段对应的值、查询的起始位置、一次查出来的数量、排序字段、排序、是否高亮、高亮字段
		// 查询字段和查询字段对应值必传
		String queryField = params.getFormString(0);
		String fieldValue = params.getFormString(1);
		// 其余字段非必传
		String startString = params.getFormStringOption(2);
		String rowsString = params.getFormStringOption(3);
		String sortField = params.getFormStringOption(4);
		String sort = params.getFormStringOption(5);
		String hightlight = params.getFormStringOption(6);
		String hlField = params.getFormStringOption(7);

		// 当起始位置和查询数量为空时，设为默认值0和10
		int start = 0;
		int rows = 10;
		if (startString != null && startString.trim().length() != 0) {
			start = Integer.parseInt(startString);
		}
		if (rowsString != null && rowsString.trim().length() != 0) {
			rows = Integer.parseInt(rowsString);
		}

		// 检测输入是否合法
		if (null == queryField || null == fieldValue || queryField.trim().length() == 0 || fieldValue.trim().length() == 0) {
			return JsonResult.failedCode(CodeStatus.REQUEST_PARA_ERROR);
		}
		
		return searchService.queryOpra(queryField, fieldValue, start, rows, sortField, sort, hightlight, hlField);

	}

}
