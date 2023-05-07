package top.ashun.recruit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import top.ashun.recruit.config.BusinessException;
import top.ashun.recruit.entity.ScheduleCollectResults;
import top.ashun.recruit.entity.ScheduleCollectTaskTables;
import top.ashun.recruit.entity.ScheduleCollectTasks;
import top.ashun.recruit.entity.UserInfo;
import top.ashun.recruit.mapper.ScheduleCollectResultsMapper;
import top.ashun.recruit.pojo.vo.AddTaskVO;
import top.ashun.recruit.pojo.vo.Code;
import top.ashun.recruit.pojo.vo.GetOneCellFulledNamesVO;
import top.ashun.recruit.pojo.vo.OwnScheduleTaskGetVO;
import top.ashun.recruit.util.ObjectUtil;
import top.ashun.recruit.util.RequestUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qks
 * @since 2023-05-06
 */
@Service
public class ScheduleCollectResultsServiceImpl extends ServiceImpl<ScheduleCollectResultsMapper, ScheduleCollectResults> {

    @Autowired
    private ScheduleCollectTaskTablesServiceImpl scheduleCollectTaskTablesService;
    @Autowired
    private ScheduleCollectTasksServiceImpl scheduleCollectTasksService;
    @Value("${websiteHost}")
    private String websiteHost;

    public String getTaskTables(Long taskId, Long userId) throws JsonProcessingException {
        Assert.notNull(taskId, "taskId cannot be empty");
        Assert.notNull(userId, "userId cannot be empty");
        List<ScheduleCollectTaskTables> taskTables = scheduleCollectTaskTablesService.list(new QueryWrapper<ScheduleCollectTaskTables>().eq("task_id", taskId));
        ScheduleCollectTasks task = scheduleCollectTasksService.getById(taskId);
        if (task == null) {
            return null;
        }
        QueryWrapper<ScheduleCollectResults> scheduleCollectResultsQueryWrapper = new QueryWrapper<>();
        scheduleCollectResultsQueryWrapper.eq("task_id", taskId);
        scheduleCollectResultsQueryWrapper.eq("user_id", userId);
        List<ScheduleCollectResults> scheduleCollectResultList = list(scheduleCollectResultsQueryWrapper);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("taskId", String.valueOf(taskId));
        node.put("taskName", task.getTaskName());
        node.put("remark", task.getRemark());
        ArrayNode tableList = mapper.createArrayNode();

        for (ScheduleCollectTaskTables taskTable : taskTables) {

            ObjectNode tableItem = mapper.createObjectNode();
            tableItem.put("tableId", String.valueOf(taskTable.getTableId()));
            tableItem.put("tableName", taskTable.getTableName());

            ArrayNode dataList = mapper.createArrayNode();

            List<String> dayList = Arrays.asList("周一", "周二", "周三", "周四", "周五", "周六", "周日");
            if (scheduleCollectResultList.size() == 0) {
                for (String s : dayList) {
                    ObjectNode dayData = mapper.createObjectNode();
                    dayData.put("taskId", String.valueOf(taskId));
                    dayData.put("tableId", String.valueOf(taskTable.getTableId()));
                    dayData.put("date", s);
                    dayData.put("one", 0);
                    dayData.put("two", 0);
                    dayData.put("three", 0);
                    dayData.put("four", 0);
                    dayData.put("five", 0);
                    dataList.add(dayData);
                }
            } else {
                for (String s : dayList) {
                    QueryWrapper<ScheduleCollectResults> dataWrapper = new QueryWrapper<>();
                    dataWrapper.eq("task_id", taskId);
                    dataWrapper.eq("table_id", taskTable.getTableId());
                    dataWrapper.eq("date", s);
                    dataWrapper.eq("user_id", userId);
                    ScheduleCollectResults resultTableDataItem = getBaseMapper().selectOne(dataWrapper);
                    if (resultTableDataItem == null) {
                        resultTableDataItem = ScheduleCollectResults.zeroValueInstance(taskId, taskTable.getTableId(), s, userId);
                    }
                    ObjectNode dayData = mapper.createObjectNode();
                    dayData.put("taskId", String.valueOf(taskId));
                    dayData.put("tableId", String.valueOf(taskTable.getTableId()));
                    dayData.put("date", s);
                    dayData.put("one", resultTableDataItem.getOne());
                    dayData.put("two", resultTableDataItem.getTwo());
                    dayData.put("three", resultTableDataItem.getThree());
                    dayData.put("four", resultTableDataItem.getFour());
                    dayData.put("five", resultTableDataItem.getFive());
                    dataList.add(dayData);
                }
            }

            tableItem.set("dataList", dataList);
            tableList.add(tableItem);
        }
        node.set("tableList", tableList);
        return mapper.writeValueAsString(node);
    }

    public Integer setTaskTableData(ScheduleCollectResults data) {
        ObjectUtil.checkNotNull(data, "taskId", "tableId", "date", "userId", "userName", "one", "two", "three", "four", "five");
        List<String> dateList = Arrays.asList("周一", "周二", "周三", "周四", "周五", "周六", "周日");
        ScheduleCollectTasks task = scheduleCollectTasksService.getById(data.getTaskId());
        if (task == null) {
            return null;
        }
        if (!dateList.contains(data.getDate())) {
            throw new IllegalArgumentException("date属性必须在周一-周日之间");
        }
        QueryWrapper<ScheduleCollectResults> scheduleCollectResultsQueryWrapper = new QueryWrapper<>();
        scheduleCollectResultsQueryWrapper.eq("task_id", data.getTaskId());
        scheduleCollectResultsQueryWrapper.eq("table_id", data.getTableId());
        scheduleCollectResultsQueryWrapper.eq("date", data.getDate());
        scheduleCollectResultsQueryWrapper.eq("user_id", data.getUserId());
        ScheduleCollectResults selectedData = baseMapper.selectOne(scheduleCollectResultsQueryWrapper);
        if (selectedData != null) {
            baseMapper.deleteById(selectedData.getId());
        }
        return baseMapper.insert(data);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteTask(String taskId) {
        Assert.notNull(taskId, "taskId cannot be null");
        UserInfo loginUser = RequestUtil.getLoginUser();
        ScheduleCollectTasks record = scheduleCollectTasksService.getById(taskId);
        if (record != null && String.valueOf(record.getUserId()).equals(loginUser.getId())) {
            scheduleCollectTasksService.getBaseMapper().deleteById(taskId);
            QueryWrapper<ScheduleCollectTaskTables> scheduleCollectTaskTablesQueryWrapper = new QueryWrapper<>();
            scheduleCollectTaskTablesQueryWrapper.eq("task_Id", taskId);
            scheduleCollectTaskTablesService.getBaseMapper().delete(scheduleCollectTaskTablesQueryWrapper);
            QueryWrapper<ScheduleCollectResults> scheduleCollectResultsQueryWrapper = new QueryWrapper<>();
            scheduleCollectResultsQueryWrapper.eq("task_Id", taskId);
            baseMapper.delete(scheduleCollectResultsQueryWrapper);
            return 1;
        }
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer addTask(AddTaskVO addTaskVO) {
        ScheduleCollectTasks task = new ScheduleCollectTasks();
        task.setTaskName(addTaskVO.getTaskName());
        task.setRemark(addTaskVO.getRemark());
        task.setUserId(Long.valueOf(RequestUtil.getLoginUser().getId()));
        task.setUserName(RequestUtil.getLoginUser().getUsername());
        long taskId = IdWorker.getId(new Object());
        task.setTaskId(taskId);
        scheduleCollectTasksService.getBaseMapper().insert(task);
        for (int i = 0; i < addTaskVO.getTableNames().size(); i++) {
            ScheduleCollectTaskTables table = new ScheduleCollectTaskTables();
            table.setTaskId(taskId);
            table.setTableName(addTaskVO.getTableNames().get(i));
            table.setTableId(i + 1);
            scheduleCollectTaskTablesService.getBaseMapper().insert(table);
        }
        return 1;
    }


    public List<OwnScheduleTaskGetVO> getOwnScheduleCollectTaskList() {
        List<OwnScheduleTaskGetVO> result = new ArrayList<>();
        Long userId = Long.valueOf(RequestUtil.getLoginUser().getId());
        QueryWrapper<ScheduleCollectTasks> scheduleCollectTaskQueryWrapper = new QueryWrapper<>();
        scheduleCollectTaskQueryWrapper.eq("user_id", userId);
        List<ScheduleCollectTasks> taskList = scheduleCollectTasksService.list(scheduleCollectTaskQueryWrapper);
        for (ScheduleCollectTasks task : taskList) {
            OwnScheduleTaskGetVO item = new OwnScheduleTaskGetVO();
            item.setTaskId(String.valueOf(task.getTaskId()));
            item.setTitle(task.getTaskName());
            item.setNumberOfFilled(computeNumberOfFilled(task.getTaskId()));
            item.setRemark(task.getRemark());
            item.setAddress(websiteHost + "/#/task/" + task.getTaskId());
            result.add(item);
        }
        return result;
    }

    public List<String> statisticsOneCellFulledNames(GetOneCellFulledNamesVO vo){
        ArrayList<String> result = new ArrayList<>();
        QueryWrapper<ScheduleCollectResults> scheduleCollectResultsQueryWrapper = new QueryWrapper<>();
        scheduleCollectResultsQueryWrapper.eq("task_id", vo.getTaskId());
        scheduleCollectResultsQueryWrapper.eq("table_id", vo.getTableId());
        scheduleCollectResultsQueryWrapper.eq("date", vo.getDate());
        scheduleCollectResultsQueryWrapper.eq(vo.getNumber(),"1");
        List<ScheduleCollectResults> list = list(scheduleCollectResultsQueryWrapper);
        for (ScheduleCollectResults item : list) {
            result.add(item.getUserName());
        }

        return result;
    }
    private Integer computeNumberOfFilled(Long taskId) {
        return baseMapper.computeNumberOfFilled(taskId);
    }

    public String getTaskResult(Long taskId) throws JsonProcessingException {
        Assert.notNull(taskId, "taskId cannot be null");
        ScheduleCollectTasks currentTask = scheduleCollectTasksService.getBaseMapper().selectById(taskId);
        if (!String.valueOf(currentTask.getUserId()).equals(RequestUtil.getLoginUser().getId()) && !RequestUtil.getRoleList().contains("ROLE_root")) {
            throw new BusinessException(Code.PERMISSION_NO_ACCESS);
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("taskId", String.valueOf(taskId));
        node.put("taskName", currentTask.getTaskName());
        node.put("remark", currentTask.getRemark());
        ArrayNode tableList = mapper.createArrayNode();

        List<ScheduleCollectTaskTables> taskTables = scheduleCollectTaskTablesService.list(new QueryWrapper<ScheduleCollectTaskTables>().eq("task_id", taskId));
        for (ScheduleCollectTaskTables taskTable : taskTables) {
            ObjectNode tableItem = mapper.createObjectNode();
            tableItem.put("tableId", String.valueOf(taskTable.getTableId()));
            tableItem.put("tableName", taskTable.getTableName());

            ArrayNode dataList = mapper.createArrayNode();

            List<String> dayList = Arrays.asList("周一", "周二", "周三", "周四", "周五", "周六", "周日");
            for (String s : dayList) {
                QueryWrapper<ScheduleCollectResults> dataWrapper = new QueryWrapper<>();
                dataWrapper.eq("task_id", taskId);
                dataWrapper.eq("table_id", taskTable.getTableId());
                dataWrapper.eq("date", s);
                List<ScheduleCollectResults> list = list(dataWrapper);
                ScheduleCollectResults resultTableDataItem = computeDayResult(list);
                ObjectNode dayData = mapper.createObjectNode();
                dayData.put("taskId", String.valueOf(taskId));
                dayData.put("tableId", String.valueOf(taskTable.getTableId()));
                dayData.put("date", s);
                dayData.put("one", resultTableDataItem.getOne());
                dayData.put("two", resultTableDataItem.getTwo());
                dayData.put("three", resultTableDataItem.getThree());
                dayData.put("four", resultTableDataItem.getFour());
                dayData.put("five", resultTableDataItem.getFive());
                dataList.add(dayData);
            }


            tableItem.set("dataList", dataList);
            tableList.add(tableItem);
        }
        node.set("tableList", tableList);
        return mapper.writeValueAsString(node);
    }

    private ScheduleCollectResults computeDayResult(List<ScheduleCollectResults> data) {
        ScheduleCollectResults result = new ScheduleCollectResults();
        // 初始化标记数组
        int[] marks = new int[]{0, 0, 0, 0, 0};
        for (ScheduleCollectResults row : data) {
            // 将后五列进行 OR 操作
            marks[0] |= Integer.parseInt(row.getOne());
            marks[1] |= Integer.parseInt(row.getTwo());
            marks[2] |= Integer.parseInt(row.getThree());
            marks[3] |= Integer.parseInt(row.getFour());
            marks[4] |= Integer.parseInt(row.getFive());
        }
        result.setOne(String.valueOf(marks[0]));
        result.setTwo(String.valueOf(marks[1]));
        result.setThree(String.valueOf(marks[2]));
        result.setFour(String.valueOf(marks[3]));
        result.setFive(String.valueOf(marks[4]));
        return result;
    }

}
