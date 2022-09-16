package top.showboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.showboard.entity.User;
import top.showboard.mapper.UserMapper;
import top.showboard.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 对密码进行MD5加密
     * @param str
     * @return
     */
    public static String getMD5Str(String str) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(str.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //16是表示转换为16进制数
        String md5Str = new BigInteger(1, digest).toString(16);
        return md5Str;
    }

    /**
     * 登录账号密码判断
     * @param account
     * @param password
     * @return
     */
    @Override
    public  Boolean judgePassword(String account,String password){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().eq("account",account);
        User user = userMapper.selectOne(queryWrapper);
        if(user.getPassword().equals(getMD5Str(password))){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 返回所有用户列表分页
     * @param current
     * @param size
     * @return
     */
    @Override
    public Page<User> getAllUser(Integer current, Integer size){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().orderByAsc("userid");
        Page<User> usersPage = new Page<>(current,size);
        return userMapper.selectPage(usersPage,queryWrapper);
    }

    /**
     * 根据条件模糊查询用户信息分页
     * @param current
     * @param size
     * @param keywords
     * @param fieldName
     * @return
     */
    @Override
    public Page<User> getUserBySearch(Integer current,Integer size,String keywords,String fieldName){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().like(fieldName,keywords);
        Page<User> usersPage = new Page<>(current,size);
        return userMapper.selectPage(usersPage,queryWrapper);
    }

    /**
     * 多条件联合查询用户
     * @param current
     * @param size
     * @param account
     * @param organization
     * @param role
     * @param accountState
     * @return
     */
    @Override
    public Page<User> getUserBySearchUnion(Integer current,Integer size,String account,String organization,String role,String accountState){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!account.isEmpty()){
            queryWrapper.like("account",account);
        }
        if(!organization.isEmpty()){
            queryWrapper.eq("organization",organization);
        }
        if(!role.isEmpty()){
            queryWrapper.eq("role",role);
        }
        if(!accountState.isEmpty()){
            queryWrapper.eq("accountState",accountState);
        }
        Page<User> userPage = new Page<>(current,size);
        Page page = userMapper.selectPage(userPage,queryWrapper);
        return page;
    }
    /**
     * 新增用户
     * @param user
     * @return
     */
    @Override
    public User addOneUser(User user){
        user.setUpdatedate(new Date());
        user.setPassword(getMD5Str(user.getPassword()));
        userMapper.insert(user);
        return user;
    }

    /**
     * 编辑用户信息
     * @param user
     * @return
     */
    @Override
    public User updateOneUser(User user,Integer userid){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userID",userid).set("account",user.getAccount());
        if(!user.getPassword().equals(userMapper.selectById(userid).getPassword())){
            updateWrapper.eq("userID",userid).set("password",getMD5Str(user.getPassword()));
        }
        updateWrapper.eq("userID",userid).set("organization",user.getOrganization());
        updateWrapper.eq("userID",userid).set("role",user.getRole());
        updateWrapper.eq("userID",userid).set("accountState",user.getAccountstate());
        updateWrapper.eq("userID",userid).set("realName",user.getRealname());
        updateWrapper.eq("userID",userid).set("email",user.getEmail());
        updateWrapper.eq("userID",userid).set("telephone",user.getTelephone());
        updateWrapper.eq("userID",userid).set("updateDate",new Date());
        userMapper.update(null,updateWrapper);
        return userMapper.selectById(userid);
    }

    /**
     * 修改账号状态
     * @param userid
     * @param accountStates
     * @return
     */
    @Override
    public User updateAccountStates(Integer userid,String accountStates){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userID",userid).set("accountState",accountStates);
        userMapper.update(null,updateWrapper);
        return userMapper.selectById(userid);
    }

    /**
     * 根据id删除用户
     * @param userid
     * @return
     */
    @Override
    public Boolean deleteAccount(Integer userid){
        return userMapper.deleteById(userid) > 0;
    }

    /**
     * 获得选择框列表
     * @param fieldName
     * @return
     */
    @Override
    public List<User> getDistinctList(String fieldName){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT "+fieldName);
        return userMapper.selectList(queryWrapper);
    }
}
