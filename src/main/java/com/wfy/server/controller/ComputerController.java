package com.wfy.server.controller;

import com.wfy.server.model.Computer;
import com.wfy.server.service.ComputerService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by weifeiyu on 2017/5/4.
 */
@Controller
@RequestMapping("/computer")
public class ComputerController {

    private Logger log = Logger.getLogger(ComputerController.class);

    @Resource
    private ComputerService computerService;

    @RequestMapping(value = "/list")
    public ModelAndView showUser(HttpServletRequest request,ModelAndView view){
        log.debug("查询所有用户信息");
        List<Computer> computers = computerService.queryObjList();
        view.addObject("computers",computers);
        return view;
    }
}
