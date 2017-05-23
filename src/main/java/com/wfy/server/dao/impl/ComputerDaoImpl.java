package com.wfy.server.dao.impl;

import com.wfy.server.dao.ComputerDao;
import com.wfy.server.model.Computer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by weifeiyu on 2017/5/4.
 */
@Transactional
@Repository("computerDaoImpl")
public class ComputerDaoImpl implements ComputerDao {

    @Autowired
    ComputerDao computerDao;

    @Transactional(readOnly=true)
    public List<Computer> queryObjList(){
        return computerDao.queryObjList();
    }
}
