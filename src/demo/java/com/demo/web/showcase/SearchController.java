package com.demo.web.showcase;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.security.annotation.Security;

@Security
@ShowLabel("搜索引擎")
@Controller("/showcase/search")
public class SearchController extends ShowCaseBaseController{
	
}
