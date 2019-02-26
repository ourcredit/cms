package com.monkey.common.wechatsdk;

public class PayConfig {

    public final static String Create_Ip="103.45.8.198";

    public final static String  OutKey="1518460341";

    public final static String WX_ORDER_NOTIFY_URL="https://wecon.chengquan-cn.com/pay/wx/order/notify";
    public final static String WX_CUSTOMER_NOTIFY_URL="https://wecon.chengquan-cn.com/pay/wx/customer/notify";
    public final static String WX_CHARGE_NOTIFY_URL="https://wecon.chengquan-cn.com/pay/wx/charge/notify";
    public final static String WX_BACK_NOTIFY_URL="https://wecon.chengquan-cn.com/pay/wx/back/notify";

    public final static String R_Back_Url="https://api.mch.weixin.qq.com/secapi/pay/refund";
    public static final String WX_PAYURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static final String WX_APPID = "wx28b9ad2af700ed55";
    public static final String WX_SECRIT = "7393dc4c73b33eb9f9c6b2c73c8ec3bf";
    public static final String WX_SHOPID = "1518460341";
    public static final String WX_PAYFOR = "1q2w3e4r5t6y7u8i9o0p1q2w3e4r5t6y";




    public final static String AL_ORDER_NOTIFY_URL="https://wecon.chengquan-cn.com/pay/ali/order/notify";
    public final static String AL_BACK_NOTIFY_URL="https://wecon.chengquan-cn.com/pay/ali/back/notify";



    public static final String AL_APPID = "2018120462445165";
  //  public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg/xCUv0a3aVTpQhpZSzfYLl6y6gWBQhkcZ7GxYKn76kryOOtxKSpcNEPUo77acsWfsaRirDfsBpznI2cZUxU/uG9pS4XH1NXnpu9FmYC484eXhKmcoBn7ZMVqkmORDXIl942CjZZoUnl6B/TdxhCtVET0vleGay1GiHy9j2oiA/IwkK58V7tqZOD6/gF1xt+2Em+/yr3sGrzks2yUsetBp2EOpa+J30LNGNuK3qXdqxYL9Uqp6Kmtx/LGK9TM4FiAUfLbIO5tCR4/yyEqcUWbpcdnHvEupC8WKxeHRUJQB1J/KH9IkgiRIAcBUAMmmbW2ByEvH+gqz2/njdbz+d79QIDAQAB";
    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhuftKUHEpGUS2klvPj5zB8zfElFfx7sG36IgffmsUi/yW/LAKew7iYIzsTCUjc0rT1djJeBMEdxFldk1PBelR4e7GIMbfw+J7YlthMTVE11gURS8qq6cwR653HYU8Zfn4rIbxlLrpe94nbgQfB5Ujvb8hDKVYHXxdaudilgMFy+S15/yszpVZyvDfY/nxuUMU79Fh1zHSVYB5pDXSTa3cpUnhdztKs6NzWC2XXIHEXvQgT121XHkcRo0uGw8wltY4Uoisx3BFJq4OvQU3eLHSI41QrlQGp8+kpKg4gCcJA3oxbYOaK1P93EQTBV0pbEzpzfga3NltpbDuVdxx1uAvQIDAQAB";
   public static final String PRIVATE_KEY="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCD/EJS/RrdpVOlCGllLN9guXrLqBYFCGRxnsbFgqfvqSvI463EpKlw0Q9SjvtpyxZ+xpGKsN+wGnOcjZxlTFT+4b2lLhcfU1eem70WZgLjzh5eEqZygGftkxWqSY5ENciX3jYKNlmhSeXoH9N3GEK1URPS+V4ZrLUaIfL2PaiID8jCQrnxXu2pk4Pr+AXXG37YSb7/KvewavOSzbJSx60GnYQ6lr4nfQs0Y24repd2rFgv1Sqnoqa3H8sYr1MzgWIBR8tsg7m0JHj/LISpxRZulx2ce8S6kLxYrF4dFQlAHUn8of0iSCJEgBwFQAyaZtbYHIS8f6CrPb+eN1vP53v1AgMBAAECggEAZkGPxSoxoV2Qg4ET3v44BoFNgPG2Nan94AWKrMWAhO7FbHvxSjPkzvI589WxNZLR1+DSK+jDNXYY8kbIz7am915ABvTBl1SDwu+YPP1F6ebsoifC611Op8LX+znHzlL5Gahgp+LZ6FsPk5rsxA14yW+Kc18eJ7gmC5wX1+2HX/nm8A3F9t2FWt4m0nZYWAIwmH6SPSpIc27NBk9zttf1A4WEDUgxbWC1k2p2AZ4IuVmzJEixchyOAeh9TIPibKiN+/NzjRhQmqthUOrT2OO0CRItmA5c1wW/3aysdXHWPoHnz4AlMUTkb06NBLRcjf0Rv0Qc1Jd5hrQCggWC+SYNAQKBgQD9pr1VEpk9ezqL2XGfYXsB6GhPMH0lQn7T2ROCkd27PBe35cQTEprSEE6m5paNmhv0z3wmCA2jMsvGaoKQAs/EMbSsEVKGm6lIVzsltQa0B4HVDAIWxc9zRmkr7Z3XYGPu8o2NZwKcQkKwqlUbE73UMbzqWx4seSExDZ8fLi/3NQKBgQCFNR64v45Afz3R+mOJbEWGYxYI4RggVmaeUh6w0DpEVsOdCOV9sSPabJFTOwIv4+xIlYPPQur7U5x/t3YFV3NciVbqSwN4tWOExdSJqr8n+pTYDy6q2S2cNEwb/uF+ZbxWu0k3UjquELR/BF9ZMd5EzWXo8GBJ44BYGkMn10BJwQKBgQCHM/qVvy0phWbmpogXEYnUsYHwPANo+fHKkFoC7ieHpvdpkbKbC/60W/bgpea4Id4m9vXQ+9fxFqFh6OIhSy5eALSyRyV7FLv+EGk5py2khp/g5N60LspuQqjKvoS1Ce8arfVf2vmcOwF7U7+7iCFWWxC8xsblo8dac01O/HqaxQKBgG7pk/LwTFmXmLnFait6Y0lKJ4atJZGHX1pjtfoy5tEQDUArsCm/3MJ5s2dBssh9Vrf5Bg8FAPLS43PUJgi8j+l00VGzs+hF398leZpG+XMoSda8jXbj7f4p7VVCHC9KNk9hEc1mQkW80w0PbvSteQyAmWIjA4mCS9MPyurZZ0YBAoGADJjJp0KRvbnKKQDakLH4nw2WxO9djEQOSEyZyj9p4KCnvFQlD83HRlL80DK54q1BGUL4xdV27Vac6Wd4ZNA/6GoMZFBMcHli6I1dBSltCkTlHJzT9b0DXGZxfSzmvC7tQiLUvQoYKoKM80Y0vhAmOJ99EAMSciDMK5KBsCOzvb4=" ;




    public static final  String QY_CORPID = "wwb942c25ab301adcf";
    public static final String QY_SECRET = "GXrb21jr8rdxO8Q9IxHiYPDv8NR1WU3v_EwnTfekK8M";

    //小程序唯一标识   (在微信小程序管理后台获取)
    public static final String MP_CUSTOMER_APPID = "wx28b9ad2af700ed55";
    //小程序的 app secret (在微信小程序管理后台获取)
    public static  String MP_CUSTOMER_SECRET = "7393dc4c73b33eb9f9c6b2c73c8ec3bf";
    
    
    //小程序唯一标识   (在微信小程序管理后台获取)
    public static final String MP_SERVICE_APPID = "wx5b1e81345b3e6b41";
    //小程序的 app secret (在微信小程序管理后台获取)
    public static  String MP_SERVICE_SECRET = "4c226bf6a37cbd6acf3815073b363c79";


    public static  final  String ACCESSTOKENKEY="ACCESSTOKENKEY" ;
    public static final String SOURCEURL="http://wechat.chengquan-cn.com/";
}
