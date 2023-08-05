package com.example.demo.modules.excel;

import com.example.demo.modules.AbstractController;
import com.example.demo.modules.entity.sys.entity.SysMenu;
import com.example.demo.service.modules.excel.ExcelService;
import com.example.demo.utils.AjaxObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("${adminPath}/excel")
public class ExcelController extends AbstractController{

    @Autowired
    private ExcelService excelService;

    @GetMapping("/export")
    @ResponseBody
    public AjaxObject exportExcel(HttpServletResponse response, String fileName, Integer pageNum, Integer pageSize) {
        fileName = "test.xlsx";
        if (fileName == null || "".equals(fileName)) {
            return AjaxObject.error("文件名不能为空！");
        } else {
            if (fileName.endsWith("xls") || fileName.endsWith("xlsx")) {
                Boolean isOk = excelService.exportExcel(response, fileName, 1, 10);
                if (isOk) {
                    return AjaxObject.error("导出成功！");
                } else {
                    return AjaxObject.error("导出失败！");
                }
            }
            return AjaxObject.error("文件格式有误！");
        }
    }

    @GetMapping("/import")
    @ResponseBody
    public AjaxObject importExcel(String fileName) {
        fileName = "G:/test.xlsx";
        if (fileName == null && "".equals(fileName)) {
            return AjaxObject.ok("文件名不能为空！");
        } else {
            if (fileName.endsWith("xls") || fileName.endsWith("xlsx")) {
                Boolean isOk = excelService.importExcel(fileName);
                if (isOk) {
                    return AjaxObject.ok("导入成功！");
                } else {
                    return AjaxObject.ok("导入失败！");
                }
            }
            return AjaxObject.ok("文件格式错误！");
        }
    }

    //饼状图的数据查询
    //@ResponseBody
    @RequestMapping("/pojos_bing")
    @ResponseBody
    public List<SysMenu> gotoIndex() {
        List<SysMenu> pojos = excelService.find();
        return pojos;
    }

}
