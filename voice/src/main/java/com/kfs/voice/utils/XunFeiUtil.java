package com.kfs.voice.utils;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kfs.voice.entity.Exam;
import com.kfs.voice.entity.ExamQuestion;
import com.kfs.voice.service.impl.LearnServiceImpl;
import com.kfs.voice.vo.ReadExamAttrs;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import okhttp3.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class XunFeiUtil {

    @Autowired
    LearnServiceImpl learnService;

    private static XunFeiUtil xunFeiUtil;

    @PostConstruct
    public void init() {
        xunFeiUtil = this;
        xunFeiUtil.learnService = this.learnService;
    }

    public static final int StatusFirstFrame = 0;
    public static final int StatusContinueFrame = 1;
    public static final int StatusLastFrame = 2;

    final Base64.Encoder encoder = Base64.getEncoder();
    final Base64.Decoder decoder = Base64.getDecoder();

    public static final Gson json = new Gson();

    private static String iseHostUrl;

    @Value("${xunfei.iseHostUrl}")
    private void setIseHostUrl(String iseHostUrl) {
        XunFeiUtil.iseHostUrl = iseHostUrl;
    }

    private static String tssHostUrl;

    @Value("${xunfei.tssHostUrl}")
    private void setTssHostUrl(String tssHostUrl) {
        XunFeiUtil.tssHostUrl = tssHostUrl;
    }

    private static String appid;

    @Value("${xunfei.appid}")
    private void setAppid(String appid) {
        XunFeiUtil.appid = appid;
    }

    private static String apiSecret;

    @Value("${xunfei.apiSecret}")
    private void setApiSecret(String apiSecret) {
        XunFeiUtil.apiSecret = apiSecret;
    }

    private static String apiKey;

    @Value("${xunfei.apiKey}")
    private void setApiKey(String apiKey) {
        XunFeiUtil.apiKey = apiKey;
    }

    private static String tte;

    @Value("${xunfei.tte}")
    private void setTte(String tte) {
        XunFeiUtil.tte = tte;
    }

    private static String vcn;

    @Value("${xunfei.vcn}")
    private void setVcn(String vcn) {
        XunFeiUtil.vcn = vcn;
    }

    private static String voiceMp3Str = "";
    private static volatile boolean returnLock = true;

    public String transferToVoice(String content) throws Exception {
        voiceMp3Str = Strings.EMPTY;

        String authUrl = getAuthUrl(tssHostUrl, apiKey, apiSecret);

        OkHttpClient client = new OkHttpClient.Builder().build();
        String url = authUrl.replace("http://", "ws://").replace("https://", "wss://");
        Request request = new Request.Builder().url(url).build();

        List<Byte> list = Lists.newArrayList();
//        List<String> returnList = Lists.newArrayList();

        WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                System.out.println("ws建立连接成功...");

                try {
                    String requestJson = "{\n" +
                            "  \"common\": {\n" +
                            "    \"app_id\": \"" + appid + "\"\n" +
                            "  },\n" +
                            "  \"business\": {\n" +
                            "    \"aue\": \"lame\",\n" +
                            "    \"sfl\": 1,\n" +
                            "    \"tte\": \"" + tte + "\",\n" +
//                            "    \"ent\": \"intp65\",\n" +
                            "    \"vcn\": \"" + vcn + "\",\n" +
                            "    \"pitch\": 50,\n" +
                            "    \"speed\": 50\n" +
                            "  },\n" +
                            "  \"data\": {\n" +
                            "    \"status\": 2,\n" +
                            "    \"text\": \"" + Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8)) + "\"\n" +
                            //"    \"text\": \"" + Base64.getEncoder().encodeToString(TEXT.getBytes("UTF-16LE")) + "\"\n" +
                            "  }\n" +
                            "}";

                    webSocket.send(requestJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Gson gson = new Gson();
                ParamBuilder.JsonParse myJsonParse = gson.fromJson(text, ParamBuilder.JsonParse.class);

                if (myJsonParse.code != 0) {
                    System.out.println("发生错误，错误码为：" + myJsonParse.code);
                    System.out.println("本次请求的sid为：" + myJsonParse.sid);
                }

                if (myJsonParse.data != null) {
                    try {
                        byte[] textBase64Decode = Base64.getDecoder().decode(myJsonParse.data.audio);
                        System.out.println("receive=>" + textBase64Decode);
                        for (int i = 0; i < textBase64Decode.length; i++) {
                            list.add(textBase64Decode[i]);
                        }
//                        list.add(textBase64Decode);
//                        returnList.add(Base64.getEncoder().encodeToString(textBase64Decode));

                        if (myJsonParse.data.status == 2) {
                            String str = transferToBase64Str(list);
                            voiceMp3Str = str;
                            System.out.println("本次请求的sid==>" + myJsonParse.sid);

                            returnLock = false;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        while (returnLock) {

        }

        returnLock = true;

        return voiceMp3Str;
//        return returnList;
    }

    private String transferToBase64Str(List<Byte> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Strings.EMPTY;
        }

//        int bytesLen = list.stream().map(e -> e.length).reduce(Integer::sum).orElse(0);
//        byte[]imtthy returnBytes = new byte[bytesLen];
//        for (int i = 0; i < list.size(); i++) {
//            if (i == 0) {
//                System.arraycopy(list.get(i), 0, returnBytes, 0, list.get(i).length);
//            } else {
//                System.arraycopy(list.get(i), 0, returnBytes, list.get(i - 1).length, list.get(i).length);
//            }
//        }
        Object[] objects = list.toArray();
        byte [] data = new byte[list.size()];
        for (int i = 0; i <objects.length ; i++) {
            data[i] = list.get(i);
        }
        return Base64.getEncoder().encodeToString(data);
    }

    public void evalVoice(String examGroupId, String examQuestionId, ExamQuestion examQuestion) throws Exception {
        String authUrl = getAuthUrl(iseHostUrl, apiKey, apiSecret);

        OkHttpClient client = new OkHttpClient.Builder().build();
        String url = authUrl.replace("http://", "ws://").replace("https://", "wss://");
        Request request = new Request.Builder().url(url).build();

        String file = "temp.pcm";

        WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);

                new Thread(() -> {
                    int frameSize = 1280;
                    int intervel = 40;
                    int status = 0;

                    ssb(webSocket, examQuestion.getContentRef(), examQuestion.getCategory(), examQuestion.getEnt());

                    try (FileInputStream fs = new FileInputStream(file)) {
                        byte[] buffer = new byte[frameSize];
                        end:
                        while (true) {
                            int len = fs.read(buffer);
                            if (len == -1) {
                                status = StatusLastFrame;
                            }

                            switch (status) {
                                case StatusFirstFrame:
                                    send(webSocket, 1, 1, Base64.getEncoder().encodeToString(Arrays.copyOf(buffer, len)));
                                    status = StatusContinueFrame;
                                    break;

                                case StatusContinueFrame:
                                    send(webSocket, 2, 1, Base64.getEncoder().encodeToString(Arrays.copyOf(buffer, len)));
                                    break;

                                case StatusLastFrame:
                                    send(webSocket, 4, 2, "");
                                    System.out.println("sendlast");
                                    break end;
                            }
                            Thread.sleep(intervel);
                        }
                        System.out.println("all data is send");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                //System.out.println(text);
                IseNewResponseData resp = json.fromJson(text, IseNewResponseData.class);
                if (resp != null) {
                    if (resp.getCode() != 0) {
                        System.out.println("code=>" + resp.getCode() + " error=>" + resp.getMessage() + " sid=" + resp.getSid());
                        System.out.println("错误码查询链接：https://www.xfyun.cn/document/error-code");
                        return;
                    }
                    if (resp.getData() != null) {
                        if (resp.getData().getData() != null) {
                            //中间结果处理
                        }
                        if (resp.getData().getStatus() == 2) {
                            try {
                                String resultXmlStr = new String(decoder.decode(resp.getData().getData()), "UTF-8");
                                System.out.println("sid:" + resp.getSid() + " 最终识别结果" + resultXmlStr);

                                ReadExamAttrs readExamAttrs = buildReadEnWordAttrs(examQuestion.getEnt(), examQuestion.getCategory(), resultXmlStr);
                                Exam exam = buildExam(examGroupId, examQuestionId, examQuestion, readExamAttrs);
                                xunFeiUtil.learnService.saveExam(exam);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            webSocket.close(1000, "");
                        } else {
                            // todo 根据返回的数据处理
                        }
                    }
                }
                //System.out.println("response==>"+text);
            }
        });
    }

    private Exam buildExam(String examGroupId, String examQuestionId, ExamQuestion examQuestion, ReadExamAttrs readExamAttrs) {
        Exam exam = new Exam();
        exam.setCategory(examQuestion.getCategory());
        exam.setEnt(examQuestion.getEnt());
        exam.setExamGroupId(examGroupId);
        exam.setExamQuestionId(examQuestionId);
        exam.setContent(readExamAttrs.getContent());
        exam.setAccuracyScore(BigDecimal.valueOf(readExamAttrs.getAccuracyScore()));
        exam.setBegPos(readExamAttrs.getBegPos());
        exam.setEndPos(readExamAttrs.getEndPos());
        exam.setExceptInfo(readExamAttrs.getExceptInfo());
        exam.setIsRejected(readExamAttrs.getIsRejected());
        exam.setRejectType(readExamAttrs.getRejectType());
        exam.setStandardScore(BigDecimal.valueOf(readExamAttrs.getAccuracyScore()));
        exam.setTotalScore(BigDecimal.valueOf(readExamAttrs.getTotalScore()));
        exam.setFluencyScore(BigDecimal.valueOf(readExamAttrs.getFluencyScore()));
        exam.setToneScore(BigDecimal.valueOf(readExamAttrs.getToneScore()));
        exam.setPhoneScore(BigDecimal.valueOf(readExamAttrs.getPhoneScore()));
        exam.setIntegrityScore(BigDecimal.valueOf(readExamAttrs.getIntegrityScore()));
        exam.setTimeLen(readExamAttrs.getTimeLen());
        exam.setEmotionScore(BigDecimal.valueOf(readExamAttrs.getEmotionScore()));
        exam.setScorePattern(readExamAttrs.getScorePattern());
        exam.setWordCount(readExamAttrs.getWordCount());

        return exam;
    }

    private ReadExamAttrs buildReadEnWordAttrs(String ent, String category, String resultXmlStr) throws IOException, ResourceException, ScriptException {
        GroovyScriptEngine engine = new GroovyScriptEngine("src/main/java/com/kfs/voice/script");
        Binding binding = new Binding();
        String[] args = new String[]{ent, category, resultXmlStr};
        binding.setVariable("args", args);
        String readEnWordAttrsJson = (String) engine.run("ParseReadWordResult.groovy", binding);

        Gson gson = new Gson();
        return gson.fromJson(readEnWordAttrsJson, ReadExamAttrs.class);
    }

    private String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());

        StringBuilder builder = new StringBuilder("host: ").append(url.getHost()).append("\n").
                append("date: ").append(date).append("\n").//
                append("GET ").append(url.getPath()).append(" HTTP/1.1");

        Charset charset = Charset.forName("UTF-8");
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
        mac.init(spec);
        byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));
        String sha = Base64.getEncoder().encodeToString(hexDigits);

        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);

        HttpUrl httpUrl = HttpUrl.parse("https://" + url.getHost() + url.getPath()).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(charset))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();
        return httpUrl.toString();
    }

    private void ssb(WebSocket webSocket, String text, String category, String ent) {
        ParamBuilder p = new ParamBuilder();
        p.add("common", new ParamBuilder().add("app_id", appid))
                .add("business",
                        new ParamBuilder().add("category", category)
                                .add("rstcd", "utf8")
                                .add("ise_unite", "1")
                                .add("rst", "entirety")
                                .add("extra_ability", "syll_phone_err_msg;pitch;multi_dimension")
//                                .add("sub", sub)
                                .add("sub", "ise")
                                .add("ent", ent)
                                .add("tte", "utf-8")
                                .add("cmd", "ssb")
                                .add("auf", "audio/L16;rate=16000")
                                .add("aue", "raw")
                                .add("text", '\uFEFF' + text)
                )
                .add("data",
                        new ParamBuilder().add("status", 0)
                                .add("data", "")
                );

        webSocket.send(p.toString());
    }

    public void send(WebSocket webSocket, int aus, int status, String data) {
        ParamBuilder p = new ParamBuilder();
        p.add("business",
                new ParamBuilder().add("cmd", "auw")
                        .add("aus", aus)
                        .add("aue", "raw")
        ).add("data",
                new ParamBuilder().add("status", status)
                        .add("data", data)
                        .add("data_type", 1)
                        .add("encoding", "raw")
        );

        webSocket.send(p.toString());
    }

    private static class IseNewResponseData {
        private int code;
        private String message;
        private String sid;
        private Data data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    private static class Data {
        private int status;
        private String data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public static class ParamBuilder {
        private JsonObject jsonObject = new JsonObject();

        public ParamBuilder add(String key, String val) {
            this.jsonObject.addProperty(key, val);
            return this;
        }

        public ParamBuilder add(String key, int val) {
            this.jsonObject.addProperty(key, val);
            return this;
        }

        public ParamBuilder add(String key, boolean val) {
            this.jsonObject.addProperty(key, val);
            return this;
        }

        public ParamBuilder add(String key, float val) {
            this.jsonObject.addProperty(key, val);
            return this;
        }

        public ParamBuilder add(String key, JsonObject val) {
            this.jsonObject.add(key, val);
            return this;
        }

        public ParamBuilder add(String key, ParamBuilder val) {
            this.jsonObject.add(key, val.jsonObject);
            return this;
        }

        class JsonParse {
            int code;
            String sid;
            Data data;
        }

        class Data {
            int status;
            String audio;
        }

        @Override
        public String toString() {
            return this.jsonObject.toString();
        }
    }
}
