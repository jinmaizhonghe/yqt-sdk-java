package yqt;

import com.payplus.api.client.ApiClient;
import com.payplus.api.client.ApiRequest;

import com.alibaba.fastjson.JSON;
import com.payplus.api.client.ApiResponse;
import com.payplus.api.enumtype.EncryptTypeEnum;
import com.payplus.api.security.AESUtil;
import com.payplus.api.util.CloseUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

import org.apache.commons.io.IOUtils;

public class test {

    private static String agentNo = "********";//代理商商编
    private static String agentKey = "*********";//代理商秘钥

    public static void main(String[] args) throws Exception {

        //商户注册
        //testRegisterMerchant();
        //注册查询
        //testRegisteredMerchantEnquiries();
        //上传图片
//        testFileUpload();
        //支付参数新增接口
        //testNewPaymentParameters();
        //支付参数查询接口
        //testPaymentParameterQuery();
        //下单消费
        //testConsume();
        //消费查询
        //testConsumeQuery();
        //退款接口
        //testRefund();
        //退款查询
        //testRefundQuery();
        //对账单下载
        testDownloadStatement();
        //对账单下载返回文件流
//        testDownloadStatementStream();
        //代理商查询商户信息打款接口
        //testCheckMerchantPayment();

    }

    private static void testRegisterMerchant() throws Exception {
//商户注册
        String url = "https://api.jia007.com/api-center/rest/v1.0/yqt/agentSubMerchantRegist";

        ApiRequest apiRequest = new ApiRequest(agentNo, agentKey);
        apiRequest.setSupportSign(false);
        apiRequest.setEncryptType(EncryptTypeEnum.AES);

        apiRequest.addParam("requestNo", String.valueOf(System.currentTimeMillis()));
        apiRequest.addParam("signedName", "日用百货经销处");
        apiRequest.addParam("merchantShortName", "尚飞豪");
        apiRequest.addParam("customerStyle", "INDIVIDUAL");
        apiRequest.addParam("businessLicence", "9213********20");
        apiRequest.addParam("orgCode", "9213********20");
        apiRequest.addParam("taxCode", "9213********20");
        apiRequest.addParam("city", "130100");
        apiRequest.addParam("address", "河北省石家庄市");
        apiRequest.addParam("servicePhone", "13312345678");
        apiRequest.addParam("legalPerson", "张三");
        apiRequest.addParam("cardType", "IDCARD");
        apiRequest.addParam("idCard", "13****************");
        apiRequest.addParam("contactor", "张三");
        apiRequest.addParam("bindMobile", "133123456786");
        apiRequest.addParam("business", "100100008");
        apiRequest.addParam("accountName", "张三");
        apiRequest.addParam("bankAccountNo", "6214**********87");
        apiRequest.addParam("bankAccountType", "PRIVATE_CASH");
        apiRequest.addParam("bankBranchCode", "308100005168");
        apiRequest.addParam("serverCallbackUrl", "http://pay.weixin.cn/notify/JhNotify");

        List<PayProduct> payProductList = new ArrayList<PayProduct>();
        PayProduct payProduct = new PayProduct();
        payProduct.setFeeRate("0.50");
        payProduct.setPayTool("WECHAT_MICROPAY");

        PayProduct payProduct1 = new PayProduct();
        payProduct1.setFeeRate("0.50");
        payProduct1.setPayTool("WECHAT_PUBLIC");

        PayProduct payProduct2 = new PayProduct();
        payProduct2.setFeeRate("0.50");
        payProduct2.setPayTool("WECHAT_SCAN");

        payProductList.add(payProduct);
        payProductList.add(payProduct1);
        payProductList.add(payProduct2);

        System.out.println("payProduct数据格式:" + JSON.toJSONString(payProductList));
        apiRequest.addParam("payProduct", JSON.toJSONString(payProductList));

        System.out.println("apiRequest:" + apiRequest.toString());
        System.out.println("url:" + url);
        ApiResponse apiResponse = ApiClient.post(url, apiRequest);
        System.out.println(apiResponse.getState());
        System.out.println("apiResponse:" + JSON.toJSONString(apiResponse));
    }

    private static class PayProduct {
        private String business;
        private String payTool;
        private String feeRate;

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public String getPayTool() {
            return payTool;
        }

        public void setPayTool(String payTool) {
            this.payTool = payTool;
        }

        public String getFeeRate() {
            return feeRate;
        }

        public void setFeeRate(String feeRate) {
            this.feeRate = feeRate;
        }
    }

    private static void testRegisteredMerchantEnquiries() throws Exception {
//注册查询
        String url = "https://api.jia007.com/api-center/rest/v1.0/yqt/queryAgentSubMerchantRegist";

        ApiRequest apiRequest = new ApiRequest(agentNo, agentKey);
        apiRequest.setSupportSign(false);
        apiRequest.setEncryptType(EncryptTypeEnum.AES);

        apiRequest.addParam("requestNo", String.valueOf(System.currentTimeMillis()));
        apiRequest.addParam("registRequestNo", "A1533811431862");

//        List<PayProduct> payProductList = new ArrayList<PayProduct>();
//        PayProduct payProduct = new PayProduct();
//        payProduct.setBusiness("109109012");
//        payProduct.setFeeRate("0.30");
//        payProduct.setPayTool("WECHAT_SCAN");
//        payProductList.add(payProduct);

//        System.out.println("payProduct:" + JSON.toJSONString(payProductList));
//        apiRequest.addParam("payProduct", JSON.toJSONString(payProductList));

        System.out.println("apiRequest:" + apiRequest.toString());
        System.out.println("url:" + url);
        ApiResponse apiResponse = ApiClient.post(url, apiRequest);
        System.out.println(apiResponse.getState());
        System.out.println("apiResponse:" + JSON.toJSONString(apiResponse));
//        System.out.println(new String(apiResponse.getMessage().getBytes("UTF-8"), "GBK"));
    }

    private static void testFileUpload() throws Exception {
        //图片上传
        String url = "https://api.jia007.com/api-center/rest/v1.0/yqt/fileUpload";

        ApiRequest apiRequest = new ApiRequest(agentNo, agentKey);
        apiRequest.setSupportSign(false);
        apiRequest.setEncryptType(EncryptTypeEnum.AES);

        String filePath = "D:\\UPLOAD\\BUSINESS_CERTIFICATE.jpg";
        File file = new File(filePath);
        String fileType = "BUSINESS_CERTIFICATE";
        apiRequest.addParam("requestNo", String.valueOf(System.currentTimeMillis()));
        apiRequest.addParam("filePath", filePath);
        apiRequest.addParam("fileType", fileType);
        System.out.println(JSON.toJSONString(apiRequest));
        ApiResponse apiResponse = ApiClient.uploadFile(url, apiRequest, filePath, "filePath");
        System.out.println(JSON.toJSONString(apiResponse));
        System.out.println(apiResponse.getState());
        System.out.println("apiResponse:" + JSON.toJSONString(apiResponse));
    }

    private static void testNewPaymentParameters() throws Exception {
//支付配置新增接口
        String url = "https://api.jia007.com/api-center/rest/v1.0/yqt/merchantAddSubconfigNew";

        ApiRequest apiRequest = new ApiRequest(agentNo, agentKey);
        apiRequest.setSupportSign(false);
        apiRequest.setEncryptType(EncryptTypeEnum.AES);

        apiRequest.addParam("requestNo", String.valueOf(System.currentTimeMillis()));
        apiRequest.addParam("merchantNo", "********");
        //apiRequest.addParam("alipayPid", "201**********34");
        apiRequest.addParam("wechatAppId", "wx********");
        apiRequest.addParam("appName", "医院");
        //apiRequest.addParam("wechatJsApiPath", "https://www.yeepay.com/");
        //apiRequest.addParam("subscribeAppId", "wx643**********746");
        //以上参数4选1

        System.out.println("apiRequest:" + apiRequest.toString());
        System.out.println("url:" + url);
        ApiResponse apiResponse = ApiClient.post(url, apiRequest);
        System.out.println(apiResponse.getState());
        System.out.println("apiResponse:" + JSON.toJSONString(apiResponse));

    }

    private static void testPaymentParameterQuery() throws Exception {
//支付配置查询接口
        String url = "https://api.jia007.com/api-center/rest/v1.0/yqt/queryMerchantSubconfig";

        ApiRequest apiRequest = new ApiRequest(agentNo, agentKey);
        apiRequest.setSupportSign(false);
        apiRequest.setEncryptType(EncryptTypeEnum.AES);

        apiRequest.addParam("requestNo", String.valueOf(System.currentTimeMillis()));
        apiRequest.addParam("merchantNo", "*********");

        System.out.println("apiRequest:" + apiRequest.toString());
        System.out.println("url:" + url);
        ApiResponse apiResponse = ApiClient.post(url, apiRequest);
        System.out.println(apiResponse.getState());
        System.out.println("apiResponse:" + JSON.toJSONString(apiResponse));

    }

    private static void testConsume() throws Exception {
//支付接口
        String url = "https://api.jia007.com/api-center/rest/v1.0/yqt/consume";

        ApiRequest apiRequest = new ApiRequest(agentNo, agentKey);
        apiRequest.setSupportSign(false);
        apiRequest.setEncryptType(EncryptTypeEnum.AES);

        apiRequest.addParam("requestNo", String.valueOf(System.currentTimeMillis()));
        apiRequest.addParam("merchantNo", "105110011******");//子商户商编
        apiRequest.addParam("orderAmount", "0.02");
        apiRequest.addParam("payTool", "TRADE_SCAN");
        //apiRequest.addParam("payTool", "WECHAT_PUBLIC");
        //apiRequest.addParam("appId", "wx931386123456789e");
        //apiRequest.addParam("currency", "CNY");
        //apiRequest.addParam("expireTime", "5");
        apiRequest.addParam("orderDate", "2018-07-06 14:43:04");
        apiRequest.addParam("serverCallbackUrl", "http://www.qq.com");
        apiRequest.addParam("productName", "测试");
        //apiRequest.addParam("productDesc", "test20180522114248");
        apiRequest.addParam("openId", "testopenid");
        //apiRequest.addParam("authCode", "282441635956244114");
//        apiRequest.addParam("extraInfo", "自定义参数");
        apiRequest.addParam("clientIp", "192.168.1.1");
        apiRequest.addParam("sceneType", "MIS");
        //apiRequest.addParam("limitPay", "credit_group");
//        apiRequest.addParam("goodsTag", "0.01");
        //apiRequest.addParam("sceneInfo", "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"http://dz.pay.hylw258.com/corder_jianhang.php\",\"wap_name\": \"腾讯充值\"}}");

        System.out.println("apiRequest:" + apiRequest.toString());
        System.out.println("url:" + url);
        System.out.println("加密数据："+ AESUtil.encrypt(JSON.toJSONString((apiRequest.toString())),agentKey));
        ApiResponse apiResponse = ApiClient.post(url, apiRequest);
        System.out.println(apiResponse.getState());
        System.out.println("apiResponse:" + JSON.toJSONString(apiResponse));
    }

    private static void testConsumeQuery() throws Exception {
//支付查询接口
        String url = "https://api.jia007.com/api-center/rest/v1.0/yqt/queryOrder";

        ApiRequest apiRequest = new ApiRequest(agentNo, agentKey);
        apiRequest.setSupportSign(false);
        apiRequest.setEncryptType(EncryptTypeEnum.AES);

        apiRequest.addParam("merchantNo", "105110011********");//子商户商编
        apiRequest.addParam("trxRequestNo", "1530864143303");
        //apiRequest.addParam("orderNo", "11171031155251903380");
        //以上两个参数都传时以订单号为准

        System.out.println("apiRequest:" + apiRequest.toString());
        System.out.println("url:" + url);
        ApiResponse apiResponse = ApiClient.post(url, apiRequest);
        System.out.println(apiResponse.getState());
        System.out.println("apiResponse:" + JSON.toJSONString(apiResponse));
    }

    private static void testRefund() throws Exception {
//退款接口
        String url = "https://api.jia007.com/api-center/rest/v1.0/yqt/refund";

        ApiRequest apiRequest = new ApiRequest(agentNo, agentKey);
        apiRequest.setSupportSign(false);
        apiRequest.setEncryptType(EncryptTypeEnum.AES);

        apiRequest.addParam("requestNo", String.valueOf(System.currentTimeMillis()));
        apiRequest.addParam("merchantNo", "105110011********");//子商户商编
        apiRequest.addParam("trxRequestNo", "1530864143303");
        //apiRequest.addParam("orderNo", "11171031155251903380");
        apiRequest.addParam("refundAmount", "0.01");

        System.out.println("apiRequest:" + apiRequest.toString());
        System.out.println("url:" + url);
        ApiResponse apiResponse = ApiClient.post(url, apiRequest);
        System.out.println(apiResponse.getState());
        System.out.println("apiResponse:" + JSON.toJSONString(apiResponse));
    }

    private static void testRefundQuery() throws Exception {
//退款查询接口
        String url = "https://api.jia007.com/api-center/rest/v1.0/yqt/queryRefund";

        ApiRequest apiRequest = new ApiRequest(agentNo, agentKey);
        apiRequest.setSupportSign(false);
        apiRequest.setEncryptType(EncryptTypeEnum.AES);

        apiRequest.addParam("trxRequestNo", "1530864143303");
        apiRequest.addParam("merchantNo", "105110011********");//子商户商编
        //apiRequest.addParam("refundRequestNo", "1509437562877");


        System.out.println("apiRequest:" + apiRequest.toString());
        System.out.println("url:" + url);
        ApiResponse apiResponse = ApiClient.post(url, apiRequest);
        System.out.println(apiResponse.getState());
        System.out.println("apiResponse:" + JSON.toJSONString(apiResponse));
    }


    private static void testDownloadStatement() {
        //对账单下载
        String url = "https://api.jia007.com/api-center/rest/v2.0/yqt/downloadFile";
        ApiRequest apiRequest = new ApiRequest(agentNo, agentKey);
        apiRequest.setSupportSign(false);
        apiRequest.setEncryptType(EncryptTypeEnum.AES);
        apiRequest.addParam("checkDate", "2018-09-11");//交易日期
        apiRequest.addParam("bizType", "TRADE");//账单业务类型:微信支付宝WA
        try {
            // 文件下载路径
            String filePath = "/Users/xxx/Desktop/test.csv";
            ApiClient.downloadFile(url, apiRequest,filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件返回文件流
     */
    private static void testDownloadStatementStream() {
        //对账单下载
        String url = "https://api.jia007.com/api-center/rest/v2.0/yqt/downloadFile";
        ApiRequest apiRequest = new ApiRequest(agentNo, agentKey);
        apiRequest.setSupportSign(false);
        apiRequest.setEncryptType(EncryptTypeEnum.AES);
        apiRequest.addParam("checkDate", "2018-09-11");//交易日期
        apiRequest.addParam("bizType", "TRADE");//账单业务类型:微信支付宝WA
        InputStream inputStream = null;

        try {
            // 文件下载路径
            inputStream= ApiClient.downloadFile(url, apiRequest);
            // 文件流处理


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            CloseUtils.close(inputStream);
        }
    }

    private static void testCheckMerchantPayment() throws Exception {
//代理商查询商户信息打款接口
        String url = "https://api.jia007.com/api-center/rest/v1.0/yqt/queryRemitRecord";

        ApiRequest apiRequest = new ApiRequest(agentNo, agentKey);
        apiRequest.setSupportSign(false);
        apiRequest.setEncryptType(EncryptTypeEnum.AES);

        apiRequest.addParam("agentNo", "********");
        apiRequest.addParam("merchantNo", "105110011********");//子商户商编
        apiRequest.addParam("settleDate", "2018-03-08");

        System.out.println("apiRequest:" + apiRequest.toString());
        System.out.println("url:" + url);
        ApiResponse apiResponse = ApiClient.post(url, apiRequest);
        System.out.println(apiResponse.getState());
        System.out.println("apiResponse:" + JSON.toJSONString(apiResponse));
    }

}
