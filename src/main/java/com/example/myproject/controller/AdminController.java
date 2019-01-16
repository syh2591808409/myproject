package com.example.myproject.controller;

import com.example.myproject.domain.Admin;
import com.example.myproject.service.AdminService;
import com.example.myproject.service.impl.AdminServiceImpl;
import com.example.myproject.service.impl.SendQQMailUtil;
import com.example.myproject.service.impl.WeatherUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import static org.apache.tomcat.jni.Time.now;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    private Admin admin_now;
    @GetMapping("/login")
    public String loginGet(Model model){
        model.addAttribute("projectName", "spb");
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(Admin admin, Model model, HttpSession httpSession, @ModelAttribute("password") String password,@ModelAttribute("password2") String password2) {
        if(!password.equals(password2)){
            model.addAttribute("error", "两次输入密码不一致！");
            return "login";
        }
        Admin adminRes = adminService.findByNameAndPassword(admin);
        if (adminRes != null) {
            httpSession.setAttribute("admin", adminRes);
            return "redirect:index";
        } else {
            model.addAttribute("error", "用户名或密码错误，请重新登录！");
            return "login";
        }
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("projectName","spb");
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(Admin admin, Model model, @ModelAttribute("password") String password, @ModelAttribute("password2") String password2, @ModelAttribute("birth") String birth) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date birthdate = df.parse(birth);
        admin.setAge((date.getYear()-birthdate.getYear()));
        if(!password.equals(password2)){
            model.addAttribute("error","两次输入的密码不一致");
            return "register";
        }
        Admin adminRes = adminService.findByName(admin);
        if (adminRes == null) {
            if(admin.getSex().equals("男")){
                admin.setHeadPicture("/img/system_man.jpg");
            }{
                admin.setHeadPicture("/img/system_woman.jpg");
            }
            adminService.insert(admin);
            return "redirect:login";
        } else {
            model.addAttribute("error", "用户名已经存在，请修改用户名！");
            return "register";
        }
    }

    @GetMapping("/sendEmail")
    public String sendEmailGet(Model model){
        model.addAttribute("projectName", "spb");
        return "sendEmail";
    }

    @PostMapping("/sendEmail")
    public String sendEmailPost(Admin admin, Model model) throws AddressException, MessagingException, UnsupportedEncodingException {
        Admin adminRes = adminService.findByNameAndRealAndEmail(admin);
        if (adminRes != null) {
            String url = "http://127.0.0.1:9112/admin/changePassword?"+"userName="+URLEncoder.encode(adminRes.getUserName(),"utf-8")+"&realName="+ URLEncoder.encode(adminRes.getRealName(),"utf-8");
            SendQQMailUtil.sendQQEmail(adminRes.getEmailAddress(),url);
            return "redirect:sendEmailSuccess";
        } else {
            model.addAttribute("error", "您输入的账户姓名邮箱不匹配，请好好想想！");
            return "sendEmail";
        }
    }

    @GetMapping("/sendEmailSuccess")
    public String sendEmailSuccessGet(Model model){
        model.addAttribute("projectName", "spb");
        return "sendEmailSuccess";
    }

    @RequestMapping(value = "/changePassword",method = RequestMethod.GET)
    public String changePasswordGet(@RequestParam("userName") String userName,@RequestParam("realName") String realName,Model model)throws UnsupportedEncodingException {
        model.addAttribute("userName", URLDecoder.decode(userName, "utf-8"));
        model.addAttribute("realName", URLDecoder.decode(realName, "utf-8"));
        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePasswordPost(Admin admin,Model model){
        adminService.updatePasswordByUnameAndRname(admin);
        return "redirect:changePasswordSuccess";
    }

    @GetMapping("/changePasswordSuccess")
    public String changePasswordSuccessGet(Model model){
        model.addAttribute("projectName", "spb");
        return "changePasswordSuccess";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "dashboard";
    }

//    @GetMapping("/index")
//    public String dashboard(Model model) {
//        return "index";
//    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String indexGet(Model model,HttpSession session){
        Admin admin =(Admin) session.getAttribute("admin");
//        System.out.println(admin);
        admin_now=adminService.findByNameSingle(admin.getUserName());
        if(admin_now!=null){
            model.addAttribute("headPicture",admin_now.getHeadPicture());
            model.addAttribute("userName",admin_now.getUserName());
            String cityCode = WeatherUtil.getCityCode(admin_now.getCity());
            String weatherJson=WeatherUtil.sendGet("http://www.weather.com.cn/data/cityinfo/"+cityCode+".html");
            System.out.println(weatherJson);
            JSONObject jsonData = JSONObject.fromObject(weatherJson);
            JSONObject result = (JSONObject) jsonData.get("weatherinfo");
            model.addAttribute("city",result.get("city").toString());
            model.addAttribute("cityid",result.get("cityid").toString());
            model.addAttribute("temp1",result.get("temp1").toString());
            model.addAttribute("temp2",result.get("temp2").toString());
            model.addAttribute("weather",result.get("weather").toString());
        }else {

        }
        return "index";
    }

//    @RequestMapping(value = "/index",method = RequestMethod.POST)
//    public String info_changePost(Admin admin,Model model){
//        if(admin!=null){
//            System.out.println("good");
//        }
//        return "index";
//    }

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public String info_changeGet(Model model,@RequestParam("userName") String userName){
        Admin adminRes = adminService.findByNameSingle(userName);
        model.addAttribute("headPicture",adminRes.getHeadPicture());
        model.addAttribute("userName",adminRes.getUserName());
        model.addAttribute("realName_old",adminRes.getRealName());
        model.addAttribute("provinceAndCity_old",adminRes.getProvince()+"——"+adminRes.getCity());
        if(adminRes !=null){
        }
        return "info_change";
    }

    @RequestMapping(value = "/info",method = RequestMethod.POST)
    public String info_changePost(
                                  Admin admin, Model model,
                                  @RequestParam("userName") String userName,
                                  @ModelAttribute("password_old") String password_old,
                                  @ModelAttribute("password") String password,
                                  @ModelAttribute("password2")String password2,
                                  @ModelAttribute("realName")String realName,
                                  @ModelAttribute("province")String province,
                                  @ModelAttribute("city")String city,
                                  @ModelAttribute("headPicture_new")String newheadPicture) throws IOException {
        Admin adminRes = adminService.findByNameSingle(userName);
        model.addAttribute("headPicture",adminRes.getHeadPicture());
        model.addAttribute("userName",adminRes.getUserName());
        model.addAttribute("realName_old",adminRes.getRealName());
        model.addAttribute("provinceAndCity_old",adminRes.getProvince()+"——"+adminRes.getCity());
        if(!password_old.equals(""))
        {
            if(!adminRes.getPassword().equals(password_old)){
                model.addAttribute("error","您输入的旧密码不正确");
            }else{
                if(!password.equals(password2)){
                    model.addAttribute("error","两次输入的新密码不一致");
                }else {
                    String regex_pw = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$";
                    if(!password.matches(regex_pw)){
                        model.addAttribute("error","密码必须为数字字母混合");
                    }else{
                    adminService.updatePasswordByUname(userName, password);
                    model.addAttribute("success", "密码修改成功");
                    }
                }
            }
        }else if(!realName.equals("")){
                String regex_rn = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d]{1,11}$";
                if(!realName.matches(regex_rn)){
                    model.addAttribute("error","请输入中文");
                }else{
                    adminService.updateRealNameByUname(userName,realName);
                    Admin admin_new = adminService.findByNameSingle(userName);
                    model.addAttribute("realName_old",admin_new.getRealName());
                    model.addAttribute("success", "昵称修改成功");
                }
        }else if(!newheadPicture.equals("")){
//            String path = request.getSession().getServletContext().getRealPath("static/img");
//            System.out.println(path);
            adminService.updateheadPictureByUname(userName,newheadPicture);
            Admin admin_new = adminService.findByNameSingle(userName);
            model.addAttribute("headPicture",admin_new.getHeadPicture());
            model.addAttribute("success", "头像修改成功");
        }else{
            if(province.equals("请选择省份")){
                model.addAttribute("error","请选择省份和城市");
            }else{
                adminService.updateProvinceAndCityByUname(userName,province,city);
                Admin admin_new = adminService.findByNameSingle(userName);
                model.addAttribute("provinceAndCity_old",admin_new.getProvince()+"——"+admin_new.getCity());
                model.addAttribute("success", "地区修改成功");
            }

        }

        return "info_change";
    }
//
//    @RequestMapping(value="/upload_hp")
//    public void upload(@RequestParam(value="file",required=false)MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String path = request.getSession().getServletContext().getRealPath("static/img");
//        path += "/head.jpg";
//        file.transferTo(new File(path));
//    }

}
