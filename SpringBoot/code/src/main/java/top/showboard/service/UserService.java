package top.showboard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.showboard.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-24
 */
public interface UserService extends IService<User> {
    //登录账号密码判断
    Boolean judgePassword(String account,String password);
    //返回用户列表分页
    Page<User> getAllUser(Integer current, Integer size);
    //根据条件模糊查询用户信息分页
    Page<User> getUserBySearch(Integer current, Integer size,String keywords,String fieldName);
    //多条件联合查询用户
    Page<User> getUserBySearchUnion(Integer current,Integer size,String account,String organization,String role,String accountState);
    //新增用户
    User addOneUser(User newUser);
    //编辑用户信息
    User updateOneUser(User user,Integer userid);
    //修改账号状态
    User updateAccountStates(Integer userid,String accountStates);
    //根据id删除用户
    Boolean deleteAccount(Integer userid);
    //获得选择框列表
    List<User> getDistinctList(String fieldName);
}
