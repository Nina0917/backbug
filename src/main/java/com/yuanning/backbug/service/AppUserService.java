package com.yuanning.backbug.service;

import com.yuanning.backbug.entity.Role;
import com.yuanning.backbug.entity.User;
import com.yuanning.backbug.entity.UserRole;
import com.yuanning.backbug.entity.messageEnum.RoleType;
import com.yuanning.backbug.entity.request.AppUserResult;
import com.yuanning.backbug.entity.request.RegistrationRequest;

import com.yuanning.backbug.entity.request.UserUpdateRequest;
import com.yuanning.backbug.exceptionHandler.BaseException;
import com.yuanning.backbug.exceptionHandler.MessageEnum;
import com.yuanning.backbug.exceptionHandler.MessageUtil;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.repository.AppUserRepository;
import com.yuanning.backbug.repository.RoleRepository;
import com.yuanning.backbug.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class AppUserService {

    private final static String USER_NOT_FOUND_MSG = "user not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;


    @Transactional(rollbackFor = Exception.class)
    // 注册用户
    public Result signUpUser(RegistrationRequest request) {
        // 检查邮箱是否被注册过了
        ensureUserNameNotExist(request.getEmail());
        // 将userrequest转化成user
        User user = request.toUser();
        // 加密密码
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        // 保存user
        appUserRepository.save(user);
        //给用户绑定两个角色：用户和管理者
        // Role studentRole = roleRepository.findByName(RoleType.USER.getName()).orElseThrow(() -> new RoleNotFoundException((Map<String, Object>) new HashMap<String,Object>().put("roleName:", RoleType.USER.getName())));
        // Role managerRole = roleRepository.findByName(RoleType.MANAGER.getName()).orElseThrow(() -> new RoleNotFoundException((Map<String, Object>) new HashMap<String,Object>().put("roleName:", RoleType.MANAGER.getName())));

        Role studentRole = roleRepository.findByName(RoleType.USER.getName()).orElseThrow(() -> new BaseException(MessageEnum.NOT_FOUND));
        Role managerRole = roleRepository.findByName(RoleType.MANAGER.getName()).orElseThrow(() -> new BaseException(MessageEnum.NOT_FOUND));

        userRoleRepository.save(new UserRole(user, studentRole));
        userRoleRepository.save(new UserRole(user, managerRole));
        // return Result {code:"0", message:"SUCCESS", data:null}
        return MessageUtil.success();
    }

    // 通过邮箱找到用户并返回
    public User find(String email) {
        Map<String, Object> map = new HashMap<>();
        map.put("email:", email);
        return appUserRepository.findByEmail(email).orElseThrow(() -> new BaseException(MessageEnum.User_Not_Exist));
    }

    // 更新用户
    public Result update(UserUpdateRequest userUpdateRequest) {
        User user = find(userUpdateRequest.getEmail());
        if (Objects.nonNull(userUpdateRequest.getLastName())) {
            user.setLastName(userUpdateRequest.getLastName());
        }
        if (Objects.nonNull(userUpdateRequest.getFirstName())) {
            user.setFirstName(userUpdateRequest.getFirstName());
        }
        if (Objects.nonNull(userUpdateRequest.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(userUpdateRequest.getPassword()));
        }
        if (Objects.nonNull(userUpdateRequest.getEnabled())) {
            user.setEnabled(userUpdateRequest.getEnabled());
        }
        appUserRepository.save(user);
        return MessageUtil.success();
    }

    // 删除用户
    public Result delete(String email) {
        if (!appUserRepository.existsByEmail(email)) {
            throw new BaseException(MessageEnum.User_Not_Exist);
        }
        appUserRepository.deleteByEmail(email);
        return MessageUtil.success();
    }

    // 获取全部用户
    public Result<List<AppUserResult>> getAll(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum,5);
        // 这一点要返回AppUserResult
        List<AppUserResult> all = appUserRepository.findAllAppUserInfo(pageable);

        return MessageUtil.success(all);
    }

    // 比较两个密码是否相等
    public boolean check(String currentPassword, String password) {
        return this.bCryptPasswordEncoder.matches(currentPassword, password);
    }

    // 查看邮箱是否已经存在
    private void ensureUserNameNotExist(String email) {
        boolean exist = appUserRepository.existsByEmail(email);
        if (exist) {
            throw new BaseException(MessageEnum.Email_occupied);
        }
    }




    // 以下为 自带的， UserService里没有的方法
    // 确认用户（通过邮箱）
    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
    // 用户登录放在了AuthController里
   /* public Result<String> signIn(User appUser) {
        // 判断数据是否为empty
        if (appUser.getEmail().isEmpty() || appUser.getPassword().isEmpty()) {
            return MessageUtil.error(MessageEnum.EMPTY_FIELD.getCode(), MessageEnum.EMPTY_FIELD.getMessage());
        }
        //repository里通过邮箱查找用户
        Optional<User> user = appUserRepository.findByEmail(appUser.getEmail());

        //如果用户不存在,返回错误信息
        if (!user.isPresent()) {
            //throw new IllegalStateException("email already taken");
            return MessageUtil.error(MessageEnum.User_Not_Exist.getCode(), MessageEnum.User_Not_Exist.getMessage());
        }

        // 用户存在，继续
        // 验证密码
        String encodedPass = user.get().getPassword();
        boolean matches = bCryptPasswordEncoder.matches(appUser.getPassword(), encodedPass);
        // 密码不匹配，返回错误信息
        if (!matches) {
            return MessageUtil.error(MessageEnum.Password_Not_Correct.getCode(), MessageEnum.Password_Not_Correct.getMessage());
        }

        // 密码匹配，查看当前用户是否已经邮箱激活账号
        // 没有激活邮箱
        if (!user.get().getEnabled()) {
            return MessageUtil.error(MessageEnum.User_Not_Active.getCode(), MessageEnum.User_Not_Active.getMessage());
        }

        // 密码匹配，且用户已经被激活
        return MessageUtil.success("success!");

    }*/
}
