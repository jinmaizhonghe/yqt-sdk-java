# yqt-sdk-java
# YQT-SDK 服务介绍

> YQT-SDK是基于[YQT](http://doc.jia007.com)接口封装的开发工具包。她屏蔽了大部分细节、简化了接入流程、同时提供了一些便捷的方法和标准化的参数枚举值。帮助开发者在接入过程中避开一些常见的问题，让开发者快速接入[YQT](http://doc.jia007.com)的服务。

> *注: 该开发工具包仅支持Java语言，其他语言开发者可以参照[YQT](http://doc.jia007.com)的官方文档。*

## 一、快速接入

> *ApiRequest 是一个核心类可以帮助我们接入YQT核心系统。  
> 从此，你不再需要关注接口协议、加密方法、测试数据等等...*

### 准备工作

1. 说的再好都不如一个栗子吃得饱，让我们一起来看下干货。

### DEMO 

下面我们使用Java作为开发语言，对接[YQT](http://doc.jia007.com)的用户消费接口。

```java
private static String merchantNo = "";//商编
private static String key = “”;//秘钥
String url = "https://api.jia007.com/api-center/rest/v1.0/yqt/consume";
ApiRequest apiRequest = new ApiRequest(merchantNo, key);
apiRequest.setSupportSign(false);
apiRequest.setEncryptType(EncryptTypeEnum.AES);
apiRequest.addParam("requestNo", "TEST00001");
apiRequest.addParam("merchantNo", "1051100110000070");
apiRequest.addParam("orderAmount", "0.01");
apiRequest.addParam("payTool", "WECHAT_SCAN");
apiRequest.addParam("openId", "testopenid");
apiRequest.addParam("orderDate", "2017-10-19 16:18:04");
apiRequest.addParam("productName", "测试");
apiRequest.addParam("serverCallbackUrl", "http://www.qq.com");
apiRequest.addParam("sceneType", "MIS");
apiRequest.addParam("clientIp", "192.168.1.1");
System.out.println("apiRequest:" + apiRequest.toString());
System.out.println("url:" + url);
ApiResponse apiResponse = ApiClient.post(url, apiRequest);
System.out.println(apiResponse.getState());
System.out.println("apiResponse:" + JSON.toJSONString(apiResponse));


Console打印日志为：
apiResponse:{"resultMap":
{"code":"1","message":"受理成功","orderAmount":"0.01",
"orderNo":"11171117185644557301","redirectUrl":"weixin://wxpay/bizpayurl?pr=oyb4sLN","status":"PROCESS"}
,"state":"SUCCESS"}

```

## 二、接入指南

### 请求参数

> 目前，我们采用自己封装的ApiRequest方式来进行参数传递。

```java

ApiRequest apiRequest = new ApiRequest(merchantNo, key);
apiRequest.addParam("key", "value");
```

### 响应参数

> 服务返回*ApiResponse*对象 包含的属性和对应的方法如下：

```java
public class ApiResponse {
//返回结果集
private Map<String, String> resultMap;
//标识本次请求状态
private ApiStateEnum state;
//返回状态码
private String code;
//返回信息
private String message;
}

```

```

