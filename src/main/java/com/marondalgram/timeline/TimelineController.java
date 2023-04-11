package com.marondalgram.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marondalgram.timeline.bo.ContentBO;
import com.marondalgram.timeline.domain.Content;

@RequestMapping("/timeline")
@Controller
public class TimelineController {
	
	@Autowired
	private ContentBO contentBO;
	
	@RequestMapping("/timeline_list_view")
	public String timeLineListView(Model model) {
		
		List<Content> contentList = contentBO.getContentList();
		model.addAttribute("contentList", contentList);
		model.addAttribute("viewName", "timeline/timeline_list");
		
		return "template/layout";
	}
}
