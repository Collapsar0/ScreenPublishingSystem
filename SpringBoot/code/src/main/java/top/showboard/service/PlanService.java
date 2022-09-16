//package top.showboard.service;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import top.showboard.entity.Equipment;
//import com.baomidou.mybatisplus.extension.service.IService;
//import top.showboard.entity.Plan;
//
//import java.util.List;
//
///**
// * <p>
// *  服务类
// * </p>
// *
// * @author Collapsar
// * @since 2022-06-24
// */
//public interface PlanService extends IService<Plan> {
//    //添加一个新计划
//    Plan addOnePlan(Plan plan);
//    //根据id删除计划
//    Boolean deletePlanByID(Integer planID);
//    //返回计划列表分页
//    Page<Plan> getAllPlan(Integer current, Integer size);
//    //多条件联合查询计划
//    Page<Plan> getPlanBySearchUnion(Integer current, Integer size, String planName, String planState);
//    //获取计划的设备列表
//    List<Equipment> getPlanEquipmentList(Integer planID);
//    //编辑计划
//    Plan updateOnePlan(Plan plan);
//    //计划执行
//    void startPlanNow();
//    //设定运行时间
//    void setRunTime(Integer planID, int hours,int minutes,int seconds,Boolean startIf);
//}
