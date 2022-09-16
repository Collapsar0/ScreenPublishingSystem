package top.showboard.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import top.showboard.entity.User;
import top.showboard.mapper.UserMapper;
import top.showboard.response.Result;
import top.showboard.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-24
 */
@RestController
@RequestMapping("/user")
@Api(value = "提供用户登陆与注册的接口",tags = "用户管理")
public class UserController {
    //定义用户业务对象
    @Resource
    private UserService usersService;
    @Resource
    private UserMapper userMapper;

    @ApiOperation("登录账号密码判断")
    @GetMapping("/judgePassword")
    public Result judgePassword(String account,String password){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().eq("account",account);
        //判断用户名是否存在
        if(userMapper.selectList(queryWrapper).size()==0){
            return Result.error().message("用户名不存在！");
        }else if(!usersService.judgePassword(account, password)){
            return Result.error().message("密码错误！");
        }else {
            return Result.ok().message("登录成功！");
        }
    }

    @ApiOperation("返回用户列表分页")
    @GetMapping("/getAllUser")
    public Result getAllUsers(Integer current, Integer size){
        if(current==null||size==null){
            return Result.error().message("current和size不能为空！");
        }
        Page<User> usersPage = usersService.getAllUser(current,size);
        return Result.ok().data("users",usersPage);
    }

    @ApiOperation("根据条件模糊查询用户信息分页")
    @GetMapping("/getUserBySearch")
    public Result getUserBySearch(Integer current,Integer size,String keywords,String fieldName){
        if(current==null||size==null){
            return Result.error().message("current和size不能为空！");
        }
        Page<User> usersPage = usersService.getUserBySearch(current,size,keywords,fieldName);
        return Result.ok().data("users",usersPage);
    };

    @ApiOperation("多条件联合查询用户")
    @GetMapping("/getUserBySearchUnion")
    public Result getUserBySearchUnion(Integer current,Integer size,String account,String organization,String role,String accountState){
        if(current==null||size==null){
            return Result.error().message("current和size不能为空！");
        }
        Page<User> usersPage = usersService.getUserBySearchUnion(current,size,account,organization,role,accountState);
        return Result.ok().data("users",usersPage);
    };

    @ApiOperation("新增用户")
    @PostMapping("/addOneUser")
    public Result addOneUser(String account,String password,String organization,String role,String accountState,String realName,String telephone,String email){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().eq("account",account);
        //判断用户名是否重复
        if(userMapper.selectList(queryWrapper).size()!=0){
            return Result.error().message("用户名已存在！");
        }
        User user = createNewUser(account, password, organization, role, accountState, realName, telephone, email);
        User userRes = usersService.addOneUser(user);
        return Result.ok().data("users",userRes);
    }

    @ApiOperation("编辑用户信息")
    @PutMapping("/updateOneUser")
    public Result updateOneUser(Integer userid,String account,String password,String organization,String role,String accountState,String realName,String telephone,String email){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().eq("account",account);
        //判断用户id是否存在
        if(userMapper.selectById(userid)==null){
            return Result.error().message("用户id不存在！");
        }
        //判断用户名是否重复
        List<User> userList = userMapper.selectList(queryWrapper);
        if(userList.size()!=0 && userList.get(0).getUserid()!=userid){
            return Result.error().message("用户名已存在！");
        }
        User user = createNewUser(account, password, organization, role, accountState, realName, telephone, email);
        User userRes = usersService.updateOneUser(user,userid);
        return Result.ok().data("users",userRes);
    }

    @ApiOperation("更改用户状态")
    @PutMapping("/updateAccountStates")
    public Result updateAccountStates(Integer userid,String accountStates){
        if(accountStates.equals("已启用")){
            accountStates = "已停用";
        }else if (accountStates.equals("已停用")){
            accountStates = "已启用";
        }else {
            return Result.error().message("用户状态出现问题！");
        }
        User userRes = usersService.updateAccountStates(userid,accountStates);
        return Result.ok().data("users",userRes);
    }

    @ApiOperation("根据id删除用户")
    @DeleteMapping("/deleteAccount")
    public Result deleteAccount(Integer userid){
        Boolean result = usersService.deleteAccount(userid);
        if(result){
            return Result.ok().message("删除成功！");
        }else {
            return Result.error().message("删除失败！");
        }
    }

    /**
     * 创建一个用户
     * @param account
     * @param password
     * @param organization
     * @param role
     * @param accountState
     * @param realName
     * @param telephone
     * @param email
     * @return
     */
    private User createNewUser(String account, String password, String organization, String role, String accountState, String realName, String telephone, String email) {
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setOrganization(organization);
        user.setRole(role);
        user.setAccountstate(accountState);
        user.setRealname(realName);
        user.setTelephone(telephone);
        user.setEmail(email);
        return user;
    }

    @ApiOperation("获得选择框列表")
    @GetMapping("/getDistinctList")
    public Result getDistinctList(String fieldName){
        return Result.ok().data("List",usersService.getDistinctList(fieldName));
    }
}

