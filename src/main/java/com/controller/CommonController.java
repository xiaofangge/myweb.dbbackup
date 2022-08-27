package com.controller;

import com.pojo.DbversionConfig;
import com.service.DbversionConfigService;
import com.utils.SessionValues;
import com.service.MenuService;
import com.wf.captcha.ArithmeticCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@SuppressWarnings("all")
@Controller
public class CommonController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private DbversionConfigService dbversionConfigService;

    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        captcha.setLen(2);
        captcha.getArithmeticString();
        // 验证码存入session
        request.getSession().setAttribute(SessionValues.CAPTCHA, captcha.text());

        // 输出图片流
        captcha.out(response.getOutputStream());
    }

    @GetMapping({"", "/"})
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @GetMapping("/modifypwd")
    public ModelAndView modifypwd() {
        return new ModelAndView("views/admin/modifypwd");
    }

    @GetMapping("/menu")
    @ResponseBody
    public Map<String, Object> menu() {
        return menuService.menu();
    }

    @GetMapping("/main")
    public ModelAndView main() {
        return new ModelAndView("views/main");
    }

    @GetMapping("/welcome")
    public ModelAndView welcome() {
        return new ModelAndView("views/welcome");
    }

    @GetMapping("/databases")
    public ModelAndView users(Model model) {
        List<DbversionConfig> dbversionList = dbversionConfigService.dbversionList();
        model.addAttribute("dbversionList", dbversionList);
        return new ModelAndView("views/database/databases");
    }

    @GetMapping("/backupRecord")
    public ModelAndView project() {
        return new ModelAndView("views/backup/backupRecord");
    }

    @GetMapping("/clearLog")
    public ModelAndView authorities() {
        return new ModelAndView("views/log/clearLog");
    }

    @GetMapping("/loginLog")
    public ModelAndView authvconfig() {
        return new ModelAndView("views/log/loginlog");
    }

    @GetMapping("/sysLog")
    public ModelAndView passwdconfig() {
        return new ModelAndView("views/log/sysLog");
    }
}
