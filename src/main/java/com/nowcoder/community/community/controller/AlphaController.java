package com.nowcoder.community.community.controller;

import com.nowcoder.community.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")


public class AlphaController {

    @Autowired
    private AlphaService alphaService;


    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "Hello Spring Boot.";
    }


    @RequestMapping("/data")
    @ResponseBody
    public String getData(){
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        // 获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());

        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }
        System.out.println(request.getParameter( "code"));

        // 返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try (PrintWriter writer = response.getWriter();){
            writer.write("<h1>牛客网<h1>"); //一级标题
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //GET请求，有两种方法，一种是current = 拼进来，一种是直接写值
    // /students?current=1&limit=20  目前第一页，最多显示20条数据
    @RequestMapping(path = "/students",method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(// 参数名保证一致就可以
        @RequestParam(name = "current",required = false, defaultValue = "1") int current,
        // 不是必须的参数，默认值为1，名字为current
        @RequestParam(name = "limit",required = false, defaultValue = "10") int limit){
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /student/123
    @RequestMapping(path = "/student/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        // 获取变量名为id的值
        System.out.println(id);
        return "a student";
    }

    // POST请求
    @RequestMapping(path = "/student",method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    // 响应HTML数据
    @RequestMapping(path = "/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age",30);
        mav.setViewName("/demo/view");//templates 下的目录
        return mav;
    }

    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","北京大学");
        model.addAttribute("age",80);
        return "/demo/view";
    }

//    响应JSON数据(异步请求：网页不刷新，但是访问了服务器得到返回的结果，结果不是HTML，因为网页不刷新)
//    Java -> JSON -> JS对象
    @RequestMapping(path = "/emp",method =  RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp(){
        Map<String, Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age", 23);
        emp.put("sakary",8000.00);
        return emp;
    }

    @RequestMapping(path = "/emps",method =  RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getEmps(){
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age", 23);
        emp.put("salary",8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","李四");
        emp.put("age", 24);
        emp.put("salary",9000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","王五");
        emp.put("age", 25);
        emp.put("salary",10000.00);
        list.add(emp);

        return list;
    }
}
