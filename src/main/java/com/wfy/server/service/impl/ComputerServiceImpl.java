package com.wfy.server.service.impl;

import com.wfy.server.dao.ComputerDao;
import com.wfy.server.model.Computer;
import com.wfy.server.service.ComputerService;
import org.springframework.aop.framework.AopContext;
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

        /*try { // 本类中的事务
            if (AopContext.currentProxy() != null) {
                ComputerServiceImpl proxy = (ComputerServiceImpl) AopContext.currentProxy();
                proxy.transactionalHandleUserRegistryResult(user);
            } else {
                transactionalHandleUserRegistryResult(user);
            }
        }catch (Exception e){
            //RedisShardPool.sadd(BusinessServerParameter.REDIS_OPENING_EBANK_NUMBER_KEY, user.getBankAccountSerialNumber());
            logger.error("用户:{},开户成功，但修改数据库异常",user.getPhoneNumber());
        }*/

        return computerDao.queryObjList();
    }
}
