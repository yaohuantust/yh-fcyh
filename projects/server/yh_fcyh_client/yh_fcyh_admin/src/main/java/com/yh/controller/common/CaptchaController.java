package com.yh.controller.common;

import com.google.code.kaptcha.Producer;
import com.yh.constant.Constants;
import com.yh.controller.business.FcyhController;
import com.yh.domain.AjaxResult;
import com.yh.utils.sign.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

/**
 * @author yaohuan
 * @version 1.0
 **/
@RestController
public class CaptchaController {
    public final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    // 验证码类型
    @Value("${fcyh.captchaType}")
    private String captchaType;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        // TODO 将验证码保存在Redis中


        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        Assert.notNull(image, "image must not be null");
        ImageIO.write(image, "jpg", os);

        AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));

        logger.info("image:base64---->"+Base64.encode(os.toByteArray()));
        return ajax;
    }
}
