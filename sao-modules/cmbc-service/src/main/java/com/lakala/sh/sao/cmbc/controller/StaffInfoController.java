package com.lakala.sh.sao.cmbc.controller;

import com.lakala.sh.sao.common.exception.BaseException;
import com.lakala.sh.sao.common.vo.StaffInfo;
import com.lakala.sh.sao.cmbc.service.StaffInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Demo用
 * 应用场景：测试DB入口等操作
 */

@Slf4j
@RestController
@RequestMapping("/staff")
public class StaffInfoController {

    @Autowired
    StaffInfoService staffInfoService;

    @Value("${server.port}")
    private String port;

    @ApiOperation(value="获取员工", notes="根据id获取员工列表")
    @ApiImplicitParam(name = "id", value = "员工ID", required = true)
    @RequestMapping(value = "getstaff", method = RequestMethod.GET)
    public StaffInfo getStaff(String id) {
        StaffInfo staffInfo = staffInfoService.findByStaffId(id);
        return staffInfo;
    }

    @ApiOperation(value="获取员工", notes="根据id获取员工列表")
    @ApiImplicitParam(name = "id", value = "员工ID", required = true)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StaffInfo getStaff2(@PathVariable String id) {
        StaffInfo staffInfo = staffInfoService.findByStaffId(id);
        return staffInfo;
    }

    @ApiOperation(value="更新员工详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新员工详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "员工ID", dataType = "String"),
            @ApiImplicitParam(name = "staffInfo", value = "员工详细实体staffInfo", required = true, dataType = "StaffInfo")
    })
    @RequestMapping(value="add/{id}", method=RequestMethod.PUT)
    public String addUser(@PathVariable String id, @RequestBody StaffInfo staffInfo) {

        staffInfo.setStaffCode(id);
        staffInfo.setStaffId(id);
        staffInfoService.insertSelective(staffInfo);
        return "success";
    }

    @ApiOperation(value="获取员工总数")
    @RequestMapping(value={"count"})
    public int getUserListSize(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
//       log.info(port + "=port;Authorization-------------------------->"+httpRequest.getHeaders("Authorization").nextElement());
//        throw new Exception("出现业务错误！");
        return staffInfoService.findAllCount();
    }

    @ApiOperation(value="获取员工总页数")
    @RequestMapping(value = "/pages", method=RequestMethod.GET)
    public List<StaffInfo> itemsPage(int currentPage,int pageSize){
        return staffInfoService.findItemByPage(currentPage, pageSize);
    }
}
