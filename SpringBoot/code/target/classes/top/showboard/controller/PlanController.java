//package top.showboard.controller;
//
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.*;
//
//import top.showboard.entity.Plan;
//import top.showboard.mapper.PlanMapper;
//import top.showboard.mapper.UserMapper;
//import top.showboard.response.Result;
//import top.showboard.service.PlanService;
//
//import javax.annotation.Resource;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.List;
//
///**
// * <p>
// *  前端控制器
// * </p>
// *
// * @author Collapsar
// * @since 2022-06-24
// */
//@RestController
//@RequestMapping("/plan")
//@Api(value = "提供计划相关的接口",tags = "计划管理")
//public class PlanController {
//    @Resource
//    private PlanService planService;
//    @Resource
//    private UserMapper userMapper;
//    @Resource
//    private PlanMapper planMapper;
//
//    @ApiOperation("添加一个新计划")
//    @PostMapping("/addOnePlan")
//    public Result addOnePlan(String planName, String playPattern, Integer authorID,String startDate,String endDate,
//                             String startTime,String endTime,
//                             String planContent, @RequestParam(value ="equipmentID")List<Integer> equipmentID) throws ParseException {
//        if (planName.isEmpty()){
//            return Result.error().message("planName为空!");
//        }
//        if (playPattern.isEmpty()){
//            return Result.error().message("playPattern为空!");
//        }
//        if (planContent.isEmpty()){
//            return Result.error().message("planContent为空!");
//        }
//        if (equipmentID.size()==0){
//            return Result.error().message("equipmentID为空!");
//        }
//        if (authorID==null || userMapper.selectById(authorID)==null){
//            return Result.error().message("authorID不存在!");
//        }
//        Plan plan = new Plan();
//        if (playPattern.equals("时段播放")){
//            if (startDate.isEmpty() || endDate.isEmpty()){
//                return Result.error().message("起止日期出错!");
//            }
//            if (startTime.isEmpty() || endTime.isEmpty()){
//                return Result.error().message("起止时间出错!");
//            }
//            //设定起止日期
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            plan.setStartdate(simpleDateFormat.parse(startDate));
//            plan.setEnddate(simpleDateFormat.parse(endDate));
//            //设定起止时间
//            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//            plan.setStarttime(formatter.parse(startTime));
//            plan.setEndtime(formatter.parse(endTime));
//        }
//        plan.setAuthor(userMapper.selectById(authorID).getAccount());
//        plan.setPlanname(planName);
//        plan.setPlaypattern(playPattern);
//        plan.setPlancontent(planContent);
//        plan.setEquipmentlist(equipmentID.toString());
//        return Result.ok().data("plan",planService.addOnePlan(plan));
//    }
//
//    @ApiOperation("根据id删除计划")
//    @DeleteMapping("/deletePlanByID")
//    public Result deletePlanByID(Integer planID){
//        Boolean result = planService.deletePlanByID(planID);
//        if(result){
//            return Result.ok().message("删除成功！");
//        }else {
//            return Result.error().message("删除失败！");
//        }
//    }
//
//    @ApiOperation("返回计划列表分页")
//    @GetMapping("/getAllPlan")
//    public Result getAllPlan(Integer current, Integer size){
//        if(current==null||size==null){
//            return Result.error().message("current和size不能为空！");
//        }
//        return Result.ok().data("plan",planService.getAllPlan(current,size));
//    }
//
//    @ApiOperation("多条件联合查询计划")
//    @GetMapping("/getPlanBySearchUnion")
//    public Result getPlanBySearchUnion(Integer current, Integer size, String planName, String planState){
//        if(current==null||size==null){
//            return Result.error().message("current和size不能为空！");
//        }
//        return Result.ok().data("users",planService.getPlanBySearchUnion(current,size,planName,planState));
//    }
//
//    @ApiOperation("根据id查询计划")
//    @GetMapping("/getPlanByID")
//    public Result getPlanByID(Integer planID){
//        if (planMapper.selectById(planID) == null){
//            return Result.error().message("计划id不存在！");
//        }
//        return Result.ok().data("plan",planMapper.selectById(planID));
//    }
//
//    @ApiOperation("获取计划的设备列表")
//    @GetMapping("/getPlanEquipmentList")
//    public Result getNoticeEquipmentList(Integer planID){
//        if (planMapper.selectById(planID)==null){
//            return Result.error().message("planID不存在！");
//        }
//        if (planMapper.selectById(planID).getEquipmentlist().isEmpty()){
//            return Result.error().message("plan EquipmentIDList 不存在！");
//        }
//        return Result.ok().data("plan",planService.getPlanEquipmentList(planID));
//    }
//
//    @ApiOperation("编辑计划")
//    @PutMapping("/updateOnePlan")
//    public Result updateOnePlan(Integer planID, String planName, String playPattern, Integer authorID,
//                                String startDate,String endDate, String startTime,String endTime,
//                             String planContent, @RequestParam(value ="equipmentID")List<Integer> equipmentID) throws ParseException {
//        if (planID == null || planMapper.selectById(planID) == null){
//            return Result.error().message("planID为空!");
//        }
//        if (planName.isEmpty()){
//            return Result.error().message("planName为空!");
//        }
//        if (playPattern.isEmpty()){
//            return Result.error().message("playPattern为空!");
//        }
//        if (planContent.isEmpty()){
//            return Result.error().message("planContent为空!");
//        }
//        if (equipmentID.size()==0){
//            return Result.error().message("equipmentID为空!");
//        }
//        if (authorID==null || userMapper.selectById(authorID)==null){
//            return Result.error().message("authorID不存在!");
//        }
//        Plan plan = new Plan();
//        if (playPattern.equals("时段播放")){
//            if (startDate.isEmpty() || endDate.isEmpty()){
//                return Result.error().message("起止日期出错!");
//            }
//            if (startTime.isEmpty() || endTime.isEmpty()){
//                return Result.error().message("起止时间出错!");
//            }
//            //设定起止日期
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            plan.setStartdate(simpleDateFormat.parse(startDate));
//            plan.setEnddate(simpleDateFormat.parse(endDate));
//            //设定起止时间
//            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//            plan.setStarttime(formatter.parse(startTime));
//            plan.setEndtime(formatter.parse(endTime));
//        }
//        plan.setPlanid(planID);
//        plan.setAuthor(userMapper.selectById(authorID).getAccount());
//        plan.setPlanname(planName);
//        plan.setPlaypattern(playPattern);
//        plan.setPlancontent(planContent);
//        plan.setEquipmentlist(equipmentID.toString());
//        return Result.ok().data("plan",planService.updateOnePlan(plan));
//    }
//
//    /**
//     * 计划执行
//     */
//    public void startPlanNow(){
//        planService.startPlanNow();
//    }
//
//    @ApiOperation("测试接口:设定运行时间")
//    @GetMapping("/setRunTime")
//    public Result setRunTime(Integer planID, int hours, int minutes, int seconds, Boolean startIf){
//        planService.setRunTime(planID,hours,minutes,seconds,startIf);
//        return Result.ok().message("test");
//    }
//}
//
