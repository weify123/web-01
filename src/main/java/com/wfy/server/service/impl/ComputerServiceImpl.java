package com.wfy.server.service.impl;

import com.wfy.server.dao.ComputerDao;
import com.wfy.server.model.Computer;
import com.wfy.server.service.ComputerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weifeiyu on 2017/5/4.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ComputerServiceImpl implements ComputerService{

    @Resource
    private ComputerDao computerDao;

    public List<Computer> queryObjList(){

        return computerDao.queryObjList();
    }
}
