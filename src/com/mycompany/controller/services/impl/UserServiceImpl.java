package com.mycompany.controller.services.impl;

import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.model.bean.AbstractUser;
import com.mycompany.model.bean.Book;
import com.mycompany.model.bean.User;
import com.mycompany.model.dao.UserDao;
import com.mycompany.model.dao.impl.UserDaoImpl;
import com.mycompany.model.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements AbstractUserService<User> {
    private Connection conn = JdbcUtils.getConnection();
    private UserDao userDao = new UserDaoImpl(); // 用户数据访问对象

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    // 实例化UserServiceImpl的时候获取一个数据库连接对象
    public UserServiceImpl() throws SQLException{
        this.conn = JdbcUtils.getConnection();
    }

    @Override
    public User register(User user) throws SQLException {
        String account = user.getAccount();
        User u = findByAccount(user);
        if(u!=null){
            System.out.println("用户已经存在"+u.getAccount()+"，不允许注册");
            return null;
        }
        System.out.println("用户不存在，可以注册");
        return createRecord(user);
    }

    @Override
    public User findByAccount(User user) {
        StringBuilder sb = new StringBuilder();
        String account = user.getAccount();
        sb.append("SELECT * FROM `user` WHERE `account` = '")
                .append(account)
                .append("';");
        System.out.println(sb.toString());
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(sb.toString());
            if(!resultSet.next()){
                user = null;
            }else{
                user = new User();
                user.setAccount(resultSet.getString(2));
                user.setPassword(resultSet.getString(4));
                user.setType_id(resultSet.getInt(6));
                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    @Override
    public User login(User user) throws SQLException {
        User u = userDao.selectByAccount(conn,user.getAccount());
        return u;
    }

    @Override
    public User rename(User user) throws SQLException {
        return null;
    }

    @Override
    public User changePwd(User user) throws SQLException {
        return null;
    }

    @Override
    public List<Book> showAllBooks() throws SQLException {
        return null;
    }

    @Override
    public User findByAccountAndPassword(AbstractUser user) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String account = user.getAccount();
        String password = user.getPassword();
        sb.append("SELECT * FROM `user` WHERE `account` = '")
                .append(account)
                .append("' AND `password` = '").append(password).append("';");
        System.out.println(sb.toString());
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(sb.toString());
            if(!resultSet.next()){
                user = null;
            }else{
                user = new User();
                user.setAccount(resultSet.getString("account"));
                user.setPassword(resultSet.getString("password"));
                ((User)user).setType_id(resultSet.getInt("type_id"));
                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return (User) user;
    }


    /**
     * 登录：至少需要三个参数: 账号,密码,账户类型
     * */
    private User createRecord(User u) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `user` VALUEs(DEFAULT, '")
                .append(u.getAccount()).append("', DEFAULT, '")
                .append(u.getPassword()).append("', NULL, 1);");
        System.out.println(sb);
        conn.createStatement().executeUpdate(sb.toString());
        return u;
    }
}
