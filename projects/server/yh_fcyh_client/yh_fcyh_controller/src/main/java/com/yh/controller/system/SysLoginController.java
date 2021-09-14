package com.yh.controller.system;

import com.yh.constant.Constants;
import com.yh.domain.AjaxResult;
import com.yh.domain.model.LoginBody;
import com.yh.service.system.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yaohuan
 * @version 1.0
 **/
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }
}
