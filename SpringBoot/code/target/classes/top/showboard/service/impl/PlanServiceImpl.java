//package top.showboard.service.impl;
//
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import lombok.SneakyThrows;
//import top.showboard.component.WebSocketServer;
//import top.showboard.entity.Equipment;
//import top.showboard.entity.Plan;
//import top.showboard.entity.Program;
//import top.showboard.mapper.EquipmentMapper;
//import top.showboard.mapper.PlanMapper;
//import top.showboard.mapper.ProgramMapper;
//import top.showboard.service.PlanService;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import javax.websocket.EncodeException;
//import javax.websocket.OnMessage;
//import javax.websocket.Session;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//
///**
// * <p>
// *  服务实现类
// * </p>
// *
// * @author Collapsar
// * @since 2022-06-24
// */
//@Service
//public abstract class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements PlanService {
//    @Resource
//    private PlanMapper planMapper;
//    @Resource
//    private ProgramMapper programMapper;
//    @Resource
//    private EquipmentMapper equipmentMapper;
//    @Resource
//    private WebSocketServer webSocketServer;
//
//    /**
//     * 添加一个新计划
//     * @param plan
//     * @return
//     */
//    @Override
//    public Plan addOnePlan(Plan plan){
//        plan.setPlanpic(getPlanPic(plan.getPlancontent()));
//        plan.setUpdatedate(new Date());
//        plan.setPlanstate("发布中");
//        plan.setChecker("SuperAdmin");
//        planMapper.insert(plan);
//        setPlanTime(null,plan.getPlanid(),plan.getStartdate(),plan.getEnddate(),plan.getStarttime(),plan.getEnddate());
//        return plan;
//    }
//
//    /**
//     * Json包数据类
//     */
//    public static class PlanJson{
//        Integer programID;
//    }
//
//    /**
//     * Json包数据类
//     */
//    public static class ProgramJson{
//        Integer materialID;
//        Integer playbackTime;
//    }
//
//    /**
//     * 解析JSON函数
//     * @param json
//     * @return
//     */
//    public List<PlanJson> getGsonList(String json){
//        Gson gson = new Gson();
//        String content = null;
//        try {
//            content = java.net.URLDecoder.decode(json,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return gson.fromJson(content, new TypeToken<List<PlanJson>>(){}.getType());
//    }
//
//    /**
//     * 解析JSON函数
//     * @param json json数据内容
//     * @return 解析完成的List<ProgramJson>
//     */
//    public List<ProgramJson> getProgramGsonList(String json){
//        //创建一个Gson
//        Gson gson = new Gson();
//        String content = null;
//        try {
//            //进行UTF-8的字符转义
//            content = java.net.URLDecoder.decode(json,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        //返回解析完成的List<ProgramJson>
//        return gson.fromJson(content, new TypeToken<List<ProgramJson>>(){}.getType());
//    }
//
//    /**
//     * 获取plan头图
//     * @param planJson
//     * @return
//     */
//    public String getPlanPic(String planJson){
//        Integer programID = getGsonList(planJson).get(0).programID;
//        return programMapper.selectById(programID).getProgrampic();
//    }
//
//    /**
//     * 根据id删除计划
//     * @param planID
//     * @return
//     */
//    @Override
//    public Boolean deletePlanByID(Integer planID){
//        return planMapper.deleteById(planID) > 0;
//    }
//
//    /**
//     * 返回计划列表分页
//     * @param current
//     * @param size
//     * @return
//     */
//    @Override
//    public Page<Plan> getAllPlan(Integer current, Integer size){
//        QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select().orderByAsc("planID");
//        return planMapper.selectPage(new Page<>(current,size),queryWrapper);
//    }
//
//    /**
//     * 多条件联合查询计划
//     * @param current
//     * @param size
//     * @param planName
//     * @param planState
//     * @return
//     */
//    @Override
//    public Page<Plan> getPlanBySearchUnion(Integer current, Integer size, String planName, String planState){
//        QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();
//        if(!planName.isEmpty()){
//            queryWrapper.like("planName",planName);
//        }
//        if(!planState.isEmpty()){
//            queryWrapper.eq("planState",planState);
//        }
//        return planMapper.selectPage(new Page<>(current,size),queryWrapper);
//    }
//
//    /**
//     * 获取计划的设备列表
//     * @param planID
//     * @return
//     */
//    @Override
//    public List<Equipment> getPlanEquipmentList(Integer planID){
//        List<Integer> equipmentIDList = new ArrayList<>();
//        List<Equipment> equipmentList = new ArrayList<>();
//        String equipmentIDString = planMapper.selectById(planID).getEquipmentlist();
//        //获取List中的值
//        equipmentIDString = equipmentIDString.replaceAll(" ","");
//        equipmentIDString = equipmentIDString.substring(1,equipmentIDString.length()-1);
//        String[] result = equipmentIDString.split(",");
//        for (String str: result) {
//            if (str!=null){
//                equipmentIDList.add(Integer.valueOf(str));
//            }
//        }
//        //将List中的值导入并回传
//        for (Integer integer:equipmentIDList) {
//            equipmentList.add(equipmentMapper.selectById(integer));
//        }
//        return equipmentList;
//    }
//
//    /**
//     * 编辑计划
//     * @param plan
//     * @return
//     */
//    @Override
//    public Plan updateOnePlan(Plan plan){
//        plan.setPlanpic(getPlanPic(plan.getPlancontent()));
//        plan.setUpdatedate(new Date());
//        plan.setPlanstate("发布中");
//        plan.setChecker("SuperAdmin");
//        planMapper.updateById(plan);
//        return planMapper.selectById(plan.getPlanid());
//    }
//
//    /**
//     * 计划执行
//     */
//
//    public void startPlanNow(String message) {
//        List<Plan> planList = planMapper.selectList(new QueryWrapper<>());
//        for (Plan plan:planList) {
//            if (plan.getPlaypattern().equals("时段播放")){
//                setPlanTime(message,plan.getPlanid(), plan.getStartdate(),plan.getEnddate(),plan.getStarttime(),plan.getEndtime());
//            }
//        }
//    }
//
//    /**
//     * 设定计划时间
//     */
//    public void setPlanTime(String message, Integer planID, Date startDate, Date endDate, Date startTime, Date endTime){
//        DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        // 当前时分秒字符串切成数组
//        String[] startTimeList = sdf.format(startTime).split(":");
//        String[] endTimeList = sdf.format(endTime).split(":");
//        // 获取startTime
//        int startHours = Integer.parseInt(startTimeList[0]);
//        int startMinutes = Integer.parseInt(startTimeList[1]);
//        int startSeconds = Integer.parseInt(startTimeList[2]);
//        // 获取endTime
//        int endHours = Integer.parseInt(endTimeList[0]);
//        int endMinutes = Integer.parseInt(endTimeList[1]);
//        int endSeconds = Integer.parseInt(endTimeList[2]);
//        // 添加至运行时间
//        setRunTime(message,planID,startHours,startMinutes,startSeconds,true);
//        setRunTime(message,planID,endHours,endMinutes,endSeconds,false);
//    }
//
//    /**
//     * 设定运行时间
//     * @param hours
//     * @param minutes
//     * @param seconds
//     */
//
//    public void setRunTime(String message,Integer planID, int hours,int minutes,int seconds,Boolean startIf){
//
//        System.out.println("planID:"+planID+
//                " hours:"+hours+" minutes:"+minutes+" seconds:"+seconds+
//                " startIf:"+startIf);
//        Calendar cal = Calendar.getInstance();
//        // 每天定点执行
//        cal.set(Calendar.HOUR_OF_DAY, hours);
//        cal.set(Calendar.MINUTE, minutes);
//        cal.set(Calendar.SECOND, seconds);
//        // 设定开始时间
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @SneakyThrows
//            public void run() {
//                String planProgramJson = getPlanProgramJson(planID);
//                SendMessage sendMessage = new SendMessage();
//                sendMessage.setMessage(planID,startIf,planProgramJson,message);
//                sendPlanProgramJson(sendMessage);
//                System.out.println("startIf:true"+planProgramJson);
//            }
//        }, cal.getTime(), 24 * 60 * 60 * 1000);
//    }
//
//    /**
//     * 发送信息类
//     */
//    public class SendMessage{
//        Integer planID;
//        Boolean startIf;
//        String planProgramJson;
//        String message;
//        public void setMessage(Integer planID,Boolean startIf,String planProgramJson,String message){
//            this.planID = planID;
//            this.startIf = startIf;
//            this.planProgramJson = planProgramJson;
//            this.message = message;
//        }
//    }
//
//    /**
//     * 发送消息函数
//     * @param sendMessage
//     * @throws IOException
//     * @throws EncodeException
//     */
//    public void sendPlanProgramJson(SendMessage sendMessage) throws IOException, EncodeException {
//            Session toSession = WebSocketServer.sessionMap.get("Android001");
//            if (toSession == null){
//                System.out.println("未获取到设备信息！");
//            } else {
//                toSession.getBasicRemote().sendObject(sendMessage);
//            }
//    }
//
//
//    /**
//     * 将plan中的program整合发送
//     * @param planID
//     * @return
//     */
//    public String getPlanProgramJson (Integer planID){
//        List<PlanJson> planJsonList = getGsonList(planMapper.selectById(planID).getPlancontent());
//        List<ProgramJson> resultList = new ArrayList<>();
//        for (PlanJson planJson:planJsonList) {
//            Program program = programMapper.selectById(planJson.programID);
//            List<ProgramJson> programJsonList = getProgramGsonList(program.getProgramcontent());
//            resultList.addAll(programJsonList);
//        }
//        return new Gson().toJson(resultList);
//    }
//
//    /**
//     * 获取应该在多少秒后
//     * @param shi
//     * @param fen
//     * @return
//     */
//    public static long getTaskTime(int shi,int fen) {
//        DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//
//        // 当前时分秒字符串切成数组
//        String[] sArr = sdf.format(new Date()).split(":");
//        // 从数组取值换算成 秒计数值
//        long currentMiao = (Integer.parseInt(sArr[0]) *60*60) + (Integer.parseInt(sArr[1]) *60)
//                + Integer.parseInt(sArr[2]);
//        // 设定的执行时间换算成 秒计数值
//        long runTime = (shi*60*60 + fen*60);
//
//        if (currentMiao <= runTime) {
//            return runTime - currentMiao;
//        } else {
//            return currentMiao + (24*60*60) - (currentMiao - runTime);
//        }
//    }
//}
