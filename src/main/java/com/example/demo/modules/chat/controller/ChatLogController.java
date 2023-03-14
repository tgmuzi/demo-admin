package com.example.demo.modules.chat.controller;

import com.example.demo.modules.AbstractController;
import com.example.demo.utils.AjaxObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author muzi
 * @since 2022-08-30
 */
@Controller
@RequestMapping("${adminPath}/chat/chatLog")
public class ChatLogController extends AbstractController {

    @PostMapping("/getUUID")
    @ResponseBody
    public AjaxObject getUUID() {
        Integer uuid = UUID.randomUUID().toString().replaceAll("-", "").hashCode();
        uuid = uuid < 0 ? -uuid : uuid;// String.hashCode() 值会为空
        return AjaxObject.ok().data(uuid);
    }

    @GetMapping("/chat1")
    public String chat1(HttpServletRequest request, Model model) {
        Integer uuid = UUID.randomUUID().toString().replaceAll("-", "").hashCode();
        uuid = uuid < 0 ? -uuid : uuid;// String.hashCode() 值会为空
        model.addAttribute("ctx", getAdminPath() + "/");
        model.addAttribute("username", uuid);
        model.addAttribute("nickName", getRandomChar());
        return "modules/chat/chat";
    }
    @GetMapping("/chat")
    public String chat(HttpServletRequest request, Model model) {
        Integer uuid = UUID.randomUUID().toString().replaceAll("-", "").hashCode();
        uuid = uuid < 0 ? -uuid : uuid;// String.hashCode() 值会为空
        model.addAttribute("ctx", getAdminPath() + "/");
        model.addAttribute("username", uuid);
        model.addAttribute("nickName", getRandomChar());
        return "modules/chat/chat";
    }
    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request, Model model) {
        Integer uuid = UUID.randomUUID().toString().replaceAll("-", "").hashCode();
        uuid = uuid < 0 ? -uuid : uuid;// String.hashCode() 值会为空
        model.addAttribute("ctx", getAdminPath() + "/");
        model.addAttribute("username", uuid);
        model.addAttribute("nickName", getRandomChar());
        return "modules/sys/welcome";
    }

    // 随机生成汉字
    private static char getRandomChar() {
        String str = "";
        int hightPos; //
        int lowPos;

        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("错误");
        }

        return str.charAt(0);
    }

}
