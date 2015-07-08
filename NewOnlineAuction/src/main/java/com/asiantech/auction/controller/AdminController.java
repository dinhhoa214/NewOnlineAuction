package com.asiantech.auction.controller;
  
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;  
import com.asiantech.auction.service.CategoryService;
import com.asiantech.auction.service.ItemService;
import com.asiantech.auction.service.AccountService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired 
	AccountService userSv;
	
	@Autowired 
	CategoryService categorySv;
	
	@Autowired 
	ItemService itemSv;
	
	
	//default method is get
	//you can implement pageable of spring
	
	
    //you can use defaultvalue for page param 
	@RequestMapping(value="/adminStPanels", method=RequestMethod.GET)
    public String getAdminStpanelsPage(){  
        return "admin/stpanels";
    }
	@RequestMapping(value="/adminStIcons", method=RequestMethod.GET)
    public String getAdminSticonsPage(){  
        return "admin/sticons";
    }
	
	@RequestMapping(value="/adminStTypography", method=RequestMethod.GET)
    public String getAdminSttypoPage(){  
        return "admin/sttypography";
    }

	@RequestMapping(value="/adminTablePage", method=RequestMethod.GET)
    public String getAdminTablePage(){  
        return "admin/tablepage";
    }
	@RequestMapping(value="/adminBlankPage", method=RequestMethod.GET)
    public String getAdminBlankPage(){  
        return "admin/blankpage";
    }
}
