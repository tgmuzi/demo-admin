package com.example.demo.modules.chat.controller;

import com.example.demo.modules.chat.entity.AesConfig;
import com.example.demo.modules.chat.entity.ChatLog;
import com.example.demo.modules.chat.entity.SocketConfig;
import com.example.demo.modules.chat.service.IChatLogService;
import com.example.demo.utils.AesUtil;
import com.example.demo.utils.AjaxObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @author Alan Chen
 * @description 订单通知
 * @date 2020-04-08
 */
@Component
@RequestMapping("${adminPath}/webSocket")
@ServerEndpoint("/web_socket/order_notification/{merchantId}/{roomName}")
public class WebSocketServer {
    private Logger log = Logger.getLogger(WebSocketServer.class);

    /**
     * 在线人数
     */
    private static int onlineCount = 0;

    /**
     * 在线用户的Map集合，key：用户名，value：Session对象
     */
    private static Map<String, Session> sessionMap = new HashMap<String, Session>();
    // 使用map来收集session，key为roomName，value为同一个房间的用户集合
    // concurrentMap的key不存在时报错，不是返回null
    private static final Map<String, Set<Session>> rooms = new HashMap<>();

    /**
     * 注入其他类（换成自己想注入的对象）
     */
    public static IChatLogService chatLogService;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("merchantId") String merchantId,
            @PathParam("roomName") String roomName) {
        // 在webSocketMap新增上线用户
        sessionMap.put(merchantId, session);
        // 将session按照房间名来存储，将各个房间的用户隔离
        if (!rooms.containsKey(roomName)) {
            // 创建房间不存在时，创建房间
            Set<Session> room = new HashSet<>();
            // 添加用户
            room.add(session);
            rooms.put(roomName, room);
        } else {
            // 房间已存在，直接添加用户到相应的房间
            rooms.get(roomName).add(session);
        }
        // 在线人数加加
        WebSocketServer.onlineCount++;

        System.out.println("在线人数：" + WebSocketServer.onlineCount);
        // 通知除了自己之外的所有人
        sendOnlineCount(session,
                "{'type':'onlineCount','onlineCount':" + WebSocketServer.onlineCount + ",merchantId:'" + merchantId
                        + "'}");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        // 下线用户名
        String logoutmerchantId = "";

        // 从webSocketMap删除下线用户
        for (Entry<String, Session> entry : sessionMap.entrySet()) {
            if (entry.getValue() == session) {
                sessionMap.remove(entry.getKey());
                logoutmerchantId = entry.getKey();
                break;
            }
        }
        // 在线人数减减
        WebSocketServer.onlineCount--;

        // 通知除了自己之外的所有人
        sendOnlineCount(session, "{'type':'onlineCount','onlineCount':" + WebSocketServer.onlineCount + ",merchantId:'"
                + logoutmerchantId + "'}");
    }

    /**
     * 服务器接收到客户端消息时调用的方法
     * 
     * @throws Exception
     */
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        try {
            String decrypt = "";
            SocketConfig socketConfig;
            ObjectMapper objectMapper = new ObjectMapper();
            // jackson 序列化和反序列化 date处理
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false);
            // JSON字符串转 HashMap
            AesConfig aesConfig = objectMapper.readValue(message, AesConfig.class);

            // 先解密
            String data = aesConfig.getData();
            String aesKey = aesConfig.getAesKey();
            log.info("加密后：" + data);
            log.info("密钥：" + aesKey);

            // AES解密得到明文data数据
            decrypt = new String(AesUtil.decrypt(data, aesKey).getBytes(),
                    "UTF-8");
            System.out.println(decrypt);
            socketConfig = objectMapper.readValue(decrypt, SocketConfig.class);

            ChatLog chatLog = new ChatLog();
            chatLog.setFromUser(Long.parseLong(socketConfig.getFromUser()));
            chatLog.setToUser(Long.parseLong(socketConfig.getToUser()));
            chatLog.setMsg(message);
            chatLog.setType(socketConfig.getType());
            chatLog.setCode(socketConfig.getCode());
            chatLog.setCreateTime(new Date());
            chatLog.setCreateTimeTs(new Date().getTime());
            save(chatLog);
            // 来源用户
            // String fromUser = socketConfig.getFromUser();
            // 目标用户
            String toUser = socketConfig.getToUser();
            // 如果点击的是自己，那就是群聊
            // if (socketConfig.getFromUser().equals(socketConfig.getToUser())) {
            // // 群聊
            // groupChat(session, socketConfig);
            // } else {
            // // 私聊
            // privateChat(session, toUser, socketConfig);
            // }
            switch (socketConfig.getType()) {
                case 0:
                    // 群聊
                    broadcast(socketConfig.getToUser(), socketConfig);
                    break;
                case 1:
                    // 私聊
                    privateChat(session, toUser, socketConfig);
                    break;
                case 2:
                    // 公告
                    groupChat(session, socketConfig);
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 通知除了自己之外的所有人
     */
    private void sendOnlineCount(Session session, String message) {
        for (Entry<String, Session> entry : sessionMap.entrySet()) {
            try {
                if (entry.getValue() != session) {
                    entry.getValue().getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 私聊
     */
    private void privateChat(Session session, String tarUser, SocketConfig socketConfig) throws IOException {
        // 获取目标用户的session
        Session tarUserSession = sessionMap.get(tarUser);

        // 如果不在线则发送“对方不在线”回来源用户
        if (tarUserSession == null) {
            session.getBasicRemote().sendText("{\"type\":\"0\",\"message\":\"对方不在线\"}");
        } else {
            socketConfig.setType(1);
            tarUserSession.getBasicRemote().sendText(new ObjectMapper().writeValueAsString(socketConfig));
        }
    }

    /**
     * 公告
     */
    private void groupChat(Session session, SocketConfig socketConfig) throws IOException {
        for (Entry<String, Session> entry : sessionMap.entrySet()) {
            // 自己就不用再发送消息了
            if (entry.getValue() != session) {
                socketConfig.setType(2);
                log.info(entry.getValue());
                entry.getValue().getBasicRemote().sendText(new ObjectMapper().writeValueAsString(socketConfig));
            }
        }
    }

    // 按照房间名进行广播
    public void broadcast(String roomName, SocketConfig socketConfig) throws IOException {
        for (Session session : rooms.get(roomName)) {
            session.getBasicRemote().sendText(new ObjectMapper().writeValueAsString(socketConfig));
        }
    }

    /**
     * 登录
     */
    @RequestMapping("/login/{merchantId}")
    public ModelAndView login(HttpServletRequest request, @PathVariable String merchantId) {
        return new ModelAndView("socketChart.html", "merchantId", merchantId);
    }

    /**
     * 登出
     */
    @RequestMapping("/logout/{merchantId}")
    public String loginOut(HttpServletRequest request, @PathVariable String merchantId) {
        return "退出成功！";
    }

    /**
     * 获取在线用户
     */
    @RequestMapping("/getOnlineList")
    @ResponseBody
    private AjaxObject getOnlineList(String merchantId) {
        List<String> list = new ArrayList<String>();
        // 遍历webSocketMap
        for (Entry<String, Session> entry : WebSocketServer.sessionMap.entrySet()) {
            if (!entry.getKey().equals(merchantId)) {
                list.add(entry.getKey());
            }
        }
        return AjaxObject.ok().data(list);
    }

    // static final ConcurrentHashMap<String, List<WebSocketClient>>
    // webSocketClientMap = new ConcurrentHashMap<>();
    // // 静态变量，用来记录当前在线连接数。
    // private static int onlineCount = 0;
    // // 与客户端的连接会话。
    // private Session session;

    // // 与客户端的连接的用户id。
    // private String userId;

    // // 接收用户id。
    // private String chantId;
    // /**
    // * 登录用户的前端公钥Map集合（其实应该放在Redis）
    // */
    // private static CopyOnWriteArraySet<OrderNotificationWebSocket> clients = new
    // CopyOnWriteArraySet<>();
    // private static Map<String, OrderNotificationWebSocket> map = new
    // ConcurrentHashMap<String, OrderNotificationWebSocket>();

    // public static IChatLogService chatLogService;

    // /**
    // * 连接建立成功调用的方法
    // */
    // /**
    // * 连接建立成功时触发，绑定参数
    // *
    // * @param session 与某个客户端的连接会话，需要通过它来给客户端发送数据
    // * @param merchantId 商户ID
    // */
    // // 连接打开时执行
    // @OnOpen
    // public void onOpen(Session session, @PathParam("merchantId") String
    // merchantId) throws IOException {
    // // System.out.println("新客户端接入，用户ID：" + merchantId);
    // // System.out.println("在线人数：" + OrderNotificationWebSocket.onlineCount);
    // // if (StringUtils.isNotBlank(merchantId)) {
    // // this.userId = merchantId;
    // // this.session = session;
    // // userMap.put(merchantId, this); // 加入set中
    // // addOnlineCount(); // 在线数加1
    // // }
    // this.session = session;
    // this.userId = merchantId;
    // this.chantId = chantId;

    // // map.put(session.getId(), session);
    // map.put(chantId, this);
    // clients.add(this);
    // log.info("有新用户加入,当前人数为：" + clients.size());
    // this.session.getAsyncRemote()
    // .sendText(session.getId());
    // System.out.println("在线人数：" + OrderNotificationWebSocket.onlineCount);
    // }

    // // 连接关闭调用的方法
    // @OnClose
    // public void onClose() {
    // // System.out.println("客户端关闭连接：" + this.userId);
    // // userMap.remove(this.userId); // 从map中删除
    // // clients.remove(this);
    // // subOnlineCount(); // 在线数减1
    // clients.remove(this);
    // log.info("有用户断开连接,当前人数为：" + clients.size());
    // System.out.println("在线人数：" + OrderNotificationWebSocket.onlineCount);
    // }

    // // 收到客户端消息后调用的方法
    // @OnMessage
    // public void onMessage(String message, Session session,
    // @PathParam("merchantId") String merchantId) {
    // if (StringUtils.isNotBlank(this.userId)) {
    // if (!"ping".equals(message)) {// 不是心跳检测
    // try {
    // String decrypt = "";
    // SocketConfig socketConfig;
    // ObjectMapper objectMapper = new ObjectMapper();
    // // jackson 序列化和反序列化 date处理
    // objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
    // false);
    // // JSON字符串转 HashMap
    // HashMap map1 = objectMapper.readValue(message, HashMap.class);

    // // 先解密
    // String data = (String) map1.get("data");
    // String aesKey = (String) map1.get("aesKey");
    // log.info("加密后：" + data);
    // log.info("密钥：" + aesKey);

    // // AES解密得到明文data数据
    // decrypt = new String(AesUtil.decrypt(data, aesKey).getBytes(),
    // "UTF-8");
    // socketConfig = objectMapper.readValue(decrypt, SocketConfig.class);

    // ChatLog chatLog = new ChatLog();
    // chatLog.setFromUser(Long.parseLong(socketConfig.getFromUser()));
    // chatLog.setToUser(Long.parseLong(socketConfig.getToUser()));
    // chatLog.setMsg(message);
    // chatLog.setType(socketConfig.getType());
    // chatLog.setCode(socketConfig.getCode());
    // chatLog.setCreateTime(new Date());
    // chatLog.setCreateTimeTs(new Date().getTime());
    // save(chatLog);
    // switch (socketConfig.getType()) {
    // case 0:// 公告
    // sendAll(message);
    // break;
    // case 1:// 聊天室
    // sendMessageTo(message, socketConfig.getFromUser(), socketConfig.getToUser());
    // break;
    // case 2:// 通知
    // sendAll(message);
    // break;
    // case 3:// 内部邮件
    // sendAll(message);
    // break;
    // case 4:// 特殊推送
    // sendAll(message);
    // break;
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // log.error("发送消息出错");
    // }

    // }
    // } else {
    // System.out.println("当前客户未登陆：" + this.userId);
    // }

    // System.out.println("用户【" + this.userId + "】访问");
    // }

    // // 发生错误时调用
    // @OnError
    // public void onError(Session session, Throwable error) {
    // log.error("出现错误");
    // error.printStackTrace();
    // }

    // public void sendMessage(String userId, String message) {
    // try {
    // if (!StringUtils.isNotBlank(userId)) {
    // System.out.println("客户ID不能为空");
    // return;
    // }
    // for (Map.Entry<String, OrderNotificationWebSocket> entry : map.entrySet()) {
    // if (entry.getKey().equals(userId)) {
    // entry.getValue().getSession().getBasicRemote().sendText(message);
    // System.out.println("推送给用户【" + entry.getKey() + "】消息成功，消息为：" + message);
    // }
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    // public static void sendMessage(List<String> userIds, String message) {
    // try {
    // if (userIds == null || userIds.size() == 0) {
    // System.out.println("客户ID不能为空");
    // return;
    // }
    // for (Map.Entry<String, OrderNotificationWebSocket> entry : map.entrySet()) {
    // if (userIds.contains(entry.getKey())) {
    // entry.getValue().getSession().getBasicRemote().sendText(message);
    // System.out.println("推送给用户【" + entry.getKey() + "】消息成功，消息为：" + message);
    // }
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    // /**
    // * 消息发送
    // *
    // * @param message
    // * @param getUid
    // * @throws IOException
    // */
    // public void sendMessageTo(String message, String sendUid, String getUid) {
    // // OrderNotificationWebSocket fromSession = map.get(sendUid);
    // OrderNotificationWebSocket item = map.get(getUid);
    // if (item != null) {
    // item.session.getAsyncRemote().sendText(message);
    // } else {// 离线消息发送
    // // fromSession.session.getAsyncRemote().sendText(message);
    // }
    // }

    // public void sendAll(String message) {
    // try {
    // if (!StringUtils.isNotBlank(userId)) {
    // System.out.println("客户ID不能为空");
    // return;
    // }
    // for (Map.Entry<String, OrderNotificationWebSocket> entry : map.entrySet()) {
    // entry.getValue().getSession().getBasicRemote().sendText(message);
    // System.out.println("推送给用户【" + entry.getKey() + "】消息成功，消息为：" + message);
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    public int save(ChatLog chatLog) {
        return chatLogService.save(chatLog);
    }

    // // 获取连接人数
    // public static synchronized int getOnlineCount() {
    // return onlineCount;
    // }

    // // 连接人数加一
    // public static synchronized void addOnlineCount() {
    // onlineCount += 1;
    // }

    // // 连接人数减一
    // public static synchronized void subOnlineCount() {
    // if (onlineCount > 0) {
    // onlineCount -= 1;
    // }
    // }

    // public Session getSession() {
    // return session;
    // }

    // // 解析json数据
    // public Map<String, Object> jsonToMap(String jsonStr) {
    // Map<String, Object> map = JSON.parseObject(jsonStr);
    // return map;
    // }

    // public List<?> jsonToList(String jsonStr) {
    // List<?> list = JSON.parseArray(jsonStr);
    // return list;
    // }

    // /**
    // * 自定义群发消息
    // *
    // * @param message
    // */
    // public void broadcast(String message) {
    // for (OrderNotificationWebSocket websocket : clients) {
    // // 异步发送消息
    // websocket.session.getAsyncRemote().sendText(message);
    // }
    // }
}