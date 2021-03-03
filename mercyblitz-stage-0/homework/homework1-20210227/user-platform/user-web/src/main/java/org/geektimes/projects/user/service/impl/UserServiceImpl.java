package org.geektimes.projects.user.service.impl;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.repository.UserRepository;
import org.geektimes.projects.user.service.UserService;

/**
 * <pre>
 * 。
 * </pre>
 *
 * @author Yishen 844147804@qq.com
 * @version 1.00.00
 */
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean register(User user) {
        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPassword();

        String phoneNumber = user.getPhoneNumber();

        // phoneNumber 特殊处理，防止SQL设置参数时报错
        // TODO 处理得不怎么优雅，后续再优化
        if (phoneNumber == null || "".equals(phoneNumber.trim())) {
            user.setPhoneNumber("");
        }

        if (name == null || "".equals(name.trim())) {
            throw new RuntimeException("注册用户名不能为空！");
        }

        if (email == null || "".equals(email.trim())) {
            throw new RuntimeException("注册邮箱不能为空！");
        }

        if (password == null || "".equals(password.trim())) {
            throw new RuntimeException("账号密码不能为空！");
        }

        User u = userRepository.getUserByName(name);
        if (u != null) {
            throw new RuntimeException("用户名已被注册!, name:"+name);
        }
        u = userRepository.getUserByEmail(email);
        if (u != null) {
            throw new RuntimeException("用户邮箱已被注册!, email:"+email);
        }

        return userRepository.save(user);
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return userRepository.getByNameAndPassword(name, password);
    }
}
