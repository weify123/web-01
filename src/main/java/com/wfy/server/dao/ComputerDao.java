package com.wfy.server.dao;

import com.wfy.server.model.Computer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by weifeiyu on 2017/5/4.
 */
public interface ComputerDao {

    List<Computer> queryObjList();
}
