package com.ruoyi.web.controller.customer;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.customer.domain.Customer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.customer.service.ICustomerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户管理Controller
 * 
 * @author azim
 * @date 2023-02-22
 */
@RestController
@RequestMapping("/customer/customer")
public class CustomerController extends BaseController
{
    @Autowired
    private ICustomerService customerService;

    /**
     * 查询客户管理列表
     */
    @PreAuthorize("@ss.hasPermi('customer:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(Customer customer)
    {
        startPage();
        List<Customer> list = customerService.selectCustomerList(customer);
        return getDataTable(list);
    }

    /**
     * 导出客户管理列表
     */
    @PreAuthorize("@ss.hasPermi('customer:customer:export')")
    @Log(title = "客户管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Customer customer)
    {
        List<Customer> list = customerService.selectCustomerList(customer);
        ExcelUtil<Customer> util = new ExcelUtil<Customer>(Customer.class);
        util.exportExcel(response, list, "客户管理数据");
    }

    /**
     * 获取客户管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('customer:customer:query')")
    @GetMapping(value = "/{customerId}")
    public AjaxResult getInfo(@PathVariable("customerId") Long customerId)
    {
        return success(customerService.selectCustomerByCustomerId(customerId));
    }

    /**
     * 新增客户管理
     */
    @PreAuthorize("@ss.hasPermi('customer:customer:add')")
    @Log(title = "客户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Customer customer)
    {
        return toAjax(customerService.insertCustomer(customer));
    }

    /**
     * 修改客户管理
     */
    @PreAuthorize("@ss.hasPermi('customer:customer:edit')")
    @Log(title = "客户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Customer customer)
    {
        return toAjax(customerService.updateCustomer(customer));
    }

    /**
     * 删除客户管理
     */
    @PreAuthorize("@ss.hasPermi('customer:customer:remove')")
    @Log(title = "客户管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{customerIds}")
    public AjaxResult remove(@PathVariable Long[] customerIds)
    {
        return toAjax(customerService.deleteCustomerByCustomerIds(customerIds));
    }
}
