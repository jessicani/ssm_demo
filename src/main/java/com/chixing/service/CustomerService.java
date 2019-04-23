package com.chixing.service;

import com.chixing.entity.Customer;

public interface CustomerService {
    /**
     * 根据手机用户查询用户是否存在
     * @param telno 手机号
     * @return  返回查询的用户对象
     */
    public Customer getByTelno(long telno);

    /**
     * 添加新用户
     * @param customer 新账户对象
     * @return 返回是否添加成功
     */
    public boolean save(Customer customer);

    /**
     * 根据id查询用户信息
     * @param custId 用户id
     * @return 返回用户对象
     */
    public Customer getById(int custId);
}
