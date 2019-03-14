package com.monkey.application.Controls;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkey.application.Controls.dtos.CreateUserInput;
import com.monkey.core.dtos.UserDto;
import com.monkey.core.entity.Tree;
import com.monkey.core.entity.User;
import com.monkey.core.entity.UserRole;
import com.monkey.core.mapper.TreeRepository;
import com.monkey.core.mapper.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liugh123
 * @since 2018-05-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserRepository, User> implements IUserService {

    @Autowired
    private IUserRoleService _userRoleRepository;
    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private TreeRepository _treeRepository;

    @Override
    public UserDto selectUserRole(Integer id) {
        UserDto r = _userRepository.selectUserRole(id);
        if (!r.getRoles().isEmpty()) {
            List<Integer> ids = new ArrayList<>();
            r.getRoles().forEach(w -> ids.add(w.getId()));
            r.setPermissions(_userRepository.selectRolePermision(ids));
        }
        return r;
    }

    /**
     * <p>
     * 创建用户角色信息
     * </p>
     *
     * @param input dto
     * @since 2018-05-03
     */
    public void ModifyUserAndRoles(CreateUserInput input) {
        User u;
        QueryWrapper ew = new QueryWrapper<>();
        ew.eq("account", input.account);
        Integer teamId = 0;
        String teamName = "";
        Integer orgId = 0;
        String orgName = "";
        if (input.orgId != null) {
            Tree tree = _treeRepository.selectById(input.orgId);
            if (tree != null) {
                String[] strs = tree.getLevelCode().split("\\.");
                if (strs.length == 5) {
                    String[] aaa = new String[strs.length - 1];
                    String[] bbb = new String[strs.length - 2];
                    for (int i = 0; i < aaa.length; i++) {
                        aaa[i] = strs[i];
                    }
                    for (int i = 0; i < bbb.length; i++) {
                        bbb[i]=strs[i];
                    }
                    String filter = String.join(".", aaa);
                    QueryWrapper qw = new QueryWrapper();
                    qw.eq("levelCode", filter);
                    List r = _treeRepository.selectList(qw);
                    if (!r.isEmpty()) {
                        Tree tr = (Tree) r.get(0);
                        teamId = tr.getId();
                        teamName = tr.getName();
                    }
                     filter = String.join(".", bbb);
                     qw = new QueryWrapper();
                    qw.eq("levelCode", filter);
                     r = _treeRepository.selectList(qw);
                    if (!r.isEmpty()) {
                        Tree tr = (Tree) r.get(0);
                        orgId = tr.getId();
                        orgName = tr.getName();
                    }
                }
            }
        }
        if (input.id == null) {
            u = new User(input.account, input.password, input.userName, input.isActive);
            u.setTeamId(teamId);
            u.setTeamName(teamName);
            u.setOrgName(orgName);
            u.setOrgId(orgId);
            u.setCurrentOrg(input.orgId);
            this.save(u);
        } else {
            u = this.getOne(ew);
            if (u != null) {
                u.setMobile(input.mobile);
                u.setIsActive(input.isActive);
                u.setUserName(input.userName);
                u.setOrgName(orgName);
                u.setOrgId(orgId);
                u.setTeamId(teamId);
                u.setCurrentOrg(input.orgId);
                u.setTeamName(teamName);
                if (input.password != null && !input.password.isEmpty()) {
                    u.setPassword(BCrypt.hashpw(input.password, BCrypt.gensalt()));
                }
                this.updateById(u);
            }
        }
        if (!input.roles.isEmpty()) {
            ew = new QueryWrapper<>();
            ew.eq("userId", u.getId());
            _userRoleRepository.remove(ew);
            List<UserRole> urs = new ArrayList<>();
            ew = new QueryWrapper();
            ew.eq("isStatic", 1);
            List<Integer> temp = new ArrayList<>();
            for (Integer r : input.roles) {
                if (!temp.contains(r)) {
                    urs.add(new UserRole(u.getId(), r));
                }
            }
            for (Integer r : temp) {
                urs.add(new UserRole(u.getId(), r));
            }
            _userRoleRepository.saveBatch(urs);
        }
    }

    /**
     * <p>
     * 注册
     * </p>
     *
     * @param user  用户
     * @param roles 角色ids
     * @since 2018-05-03
     */
    @Override
    public boolean register(User user, List<Integer> roles) {
        boolean result = this.save(user);
        if (result) {
            List<UserRole> ur = new ArrayList<>();
            for (Integer roleId : roles) {
                ur.add(new UserRole(user.getId(), roleId));
            }
            _userRoleRepository.saveBatch(ur);
        }
        return result;
    }
}
