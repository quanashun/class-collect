package top.ashun.recruit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.ashun.recruit.entity.ScheduleCollectResults;
import top.ashun.recruit.pojo.vo.AddTaskVO;
import top.ashun.recruit.pojo.vo.GetOneCellFulledNamesVO;
import top.ashun.recruit.pojo.vo.R;
import top.ashun.recruit.service.ScheduleCollectResultsServiceImpl;
import top.ashun.recruit.service.ScheduleCollectTaskTablesServiceImpl;
import top.ashun.recruit.service.ScheduleCollectTasksServiceImpl;

import java.util.List;

/**
 * @author 18483
 */
@RestController
@RequestMapping("/schedule-collect-result")
@Api(tags = "课程表收集结果表接口")
public class ScheduleCollectResultController {
    @Autowired
    private ScheduleCollectResultsServiceImpl scheduleCollectResultsService;
    @Autowired
    private ScheduleCollectTaskTablesServiceImpl scheduleCollectTaskTablesService;
    @Autowired
    private ScheduleCollectTasksServiceImpl scheduleCollectTasksService;

    @ApiOperation("任务填写者获取任务对应的表")
    @GetMapping("/getTaskTables")
    @PreAuthorize("permitAll()")
    public String getTaskTables(Long taskId, Long userId) throws JsonProcessingException {
        return scheduleCollectResultsService.getTaskTables(taskId, userId);
    }

    @ApiOperation("任务填写者插入填写的数据")
    @PostMapping("/setTaskTableData")
    @PreAuthorize("permitAll()")
    public R setTaskTableData(@RequestBody ScheduleCollectResults data) {
        return R.success(scheduleCollectResultsService.setTaskTableData(data));
    }

    @ApiOperation("删除一个收集任务")
    @GetMapping("/deleteTask")
    @PreAuthorize("hasRole('ROLE_COMMON_USER')")
    public R deleteTask(String taskId) {
        return R.success(scheduleCollectResultsService.deleteTask(taskId));
    }

    @ApiOperation("添加一个收集任务")
    @PreAuthorize("hasRole('ROLE_COMMON_USER')")
    @PostMapping("/addTask")
    public R addTask(@RequestBody AddTaskVO addTaskVO) {
        return R.success(scheduleCollectResultsService.addTask(addTaskVO));
    }

    @ApiOperation("获取自己创建的收集任务的列表")
    @PreAuthorize("hasRole('ROLE_COMMON_USER')")
    @GetMapping("/getOwnScheduleCollectTaskList")
    public R getOwnScheduleCollectTaskList() {
        return R.success(scheduleCollectResultsService.getOwnScheduleCollectTaskList());
    }

    @ApiOperation("查看任务的具体填写情况")
    @PreAuthorize("hasRole('ROLE_COMMON_USER')")
    @GetMapping("/getTaskResult")
    public String getTaskResult(Long taskId) throws JsonProcessingException {
        return scheduleCollectResultsService.getTaskResult(taskId);
    }

    @ApiOperation("查看选择某一节课的同学名单")
    @PreAuthorize("hasRole('ROLE_COMMON_USER')")
    @PostMapping("/statisticsOneCellFulledNames")
    public List<String> statisticsOneCellFulledNames(@RequestBody GetOneCellFulledNamesVO vo) {
        return scheduleCollectResultsService.statisticsOneCellFulledNames(vo);
    }
}
