/**
 * Document : 常量类 Created on : 2014-3-21, 14:26:31 Author : ocq
 */
package com.wangzhe.util;

import com.wangzhe.beans.ProxyBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 常用常量
 *
 * @author ocq
 */
public class Constants {

    //互动任务超时时间interactionTimeOutTime*2=时间（单位分钟）比如interactionTimeOutTime的值是15，那么超时时间是30分钟
    public static final int interactionTimeOutTime = 15;

    public static final String UTF8 = "UTF-8";
    //打开网站的默认编码
    public static final String DEFAULT_ENCODE = UTF8;
    public static final String GBK = "GBK";
    public static final String GBK2312 = "GBK2312";

    ;
    //邮件激活
    //注册地址
    public final static String ACTIVE_REG_URL_FORM_EMAIL = "regurlFromEmail";
    public final static String ACTIVE_USERNAME_FORM_EMAIL = "usernameFromEmail";
    public final static String PASSWORD_FORM_EMAIL = "passwordFromEmail";
    //注册码
    public final static String ACTIVE_REG_CODE_FORM_EMAIL = "regCodeFromEmail";
    //api的邮箱和代理容器 用完记得清
    public static List<?> ApiMailList = new ArrayList<>();
    public static List<ProxyBean> ApiProxyList = new ArrayList<>();

    //qq容器
    public static Map<String, String> QQMAP = new HashMap<>();
    //邮箱容器
    //public static Map<Integer, String> EMAILMAP = new HashMap<>();
    //可用邮箱类别key邮箱名称，value邮箱密码
//    public static List<String> useableEmailMap = new ArrayList<>();
    public static Map<String, String> useableEmailMap = new HashMap<>();
    //网站类型容器
    public static Map<String, Integer> TYPEMAP = new LinkedHashMap<>();
    //是否弹窗
    public static boolean ISSHOWMESSAGEBOX = true;
    //是否启动邮箱线程
    public static boolean ISSTARTEMAILTHREAD = true;
    //是否重写比较
    public static boolean isReCompare = false;
    public static final String STATUSTIP_TJBDCW = "提交表单错误";
    public static final String STATUSTIP_FSJHYJSB = "发送激活邮件失败";
    public static final String STATUSTIP_XZXQPDSB = "选择兴趣频道失败";
    public static int pageSize = 10000;
//    public static int pageSize = 740000;
    public static int logMax = 500;
    public static boolean isSelectAll = false;
    public static String newGroupName = "未命名分组";
    public static String oldGroupName = "";
    public static String titleKey = "%标题大全";
    public static String provinceKey = "%省";
    public static String cityKey = "%市";
    public static String keywordKey = "%关键字";
    public static String[] urlArray = new String[]{"邮箱挖掘公告示例", "邮箱挖掘发标示例", "Local"};
    public static String[] titleArray = new String[]{"邮箱挖掘公告示例", "邮箱挖掘发标示例", "邮箱挖掘文章"};

    //PropertiesVerify
    public static String PROPERTIES_MANUAL = "setting.manual";
    public static String PROPERTIES_AUTO = "setting.auto";
    public static String PROPERTIES_QUESTIONMODE = "setting.QuestionMode";
    public static String PROPERTIES_CAPTCHAMODE = "setting.CaptchaMode";
    public static String PROPERTIES_CAPTCHAPAYTYPE = "setting.CaptchaPayType";
    public static String PROPERTIES_GSAAPIURL = "setting.GsaAPIUrl";
    public static String PROPERTIES_CAPTCHAWUSERNAME = "setting.CaptchawUsername";
    public static String PROPERTIES_CAPTCHAWPASSWORD = "setting.CaptchawPassword";
    public static String PROPERTIES_DEATHUSERNAME = "setting.DeathUsername";
    public static String PROPERTIES_DEATHPASSWORD = "setting.DeathPassword";
    public static String PROPERTIES_IMAGEUSERNAME = "setting.ImageUsername";
    public static String PROPERTIES_IMAGEPASSWORD = "setting.ImagePassword";
    public static String PROPERTIES_DECAPUSERNAME = "setting.DecapUsername";
    public static String PROPERTIES_DECAPPASSWORD = "setting.DecapPassword";
    public static String PROPERTIES_DECAPAPIURL = "setting.DecapAPIUrl";
    public static String PROPERTIES_DECAPAPIPORT = "setting.DecapAPIPort";

    //PropertiesGeneral
    public static String PROPERTIES_GENERALUSERNAME = "GeneralUsername";
    public static String PROPERTIES_GENERALPASSWORD = "GeneralPassword";
    public static String PROPERTIES_GENERALEMAIL = "GeneralEmail";
    public static String PROPERTIES_GENERALLASTNAME = "GeneralLastName";
    public static String PROPERTIES_GENERALFIRSTNAME = "GeneralFirstName";
    public static String PROPERTIES_GENERALHOMEPAGE = "GeneralHomepage";
    public static String PROPERTIES_GENERALCELLPHONE = "GeneralCellPhone";

    //copyright：ocq
    public static final int HTTP_STATUS_MOVE_PERMANENTLY = 301;//永久转移
    public static final int HTTP_STATUS_MOVE_TEMP = 302;//临时转移
    public static final int HTTP_STATUS_FORBIDDEN = 403;//请求禁止
    public static final int HTTP_STATUS_NOT_FOUND = 404;//找不到页面
    public static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500;//内部服务器错误

    /**
     * 发布是否审核
     */
    public static final int PUBLISH_NOT_AUDIT = 0;//发布不需要审核
    public static final int PUBLISH_NEED_AUDIT = 1;//发布需要审核

    //线程等待的对象
    public static final String WOBJ = "";
    /**
     * 验证码状态属性
     */
    public static final int VERIFY_OK = 0;//确定
    public static final int VERIFY_FAIL = 1;//失败
    public static final int VERIFY_CANCLE = 2;//取消
    public static final int VERIFY_REFRESH = 3;//刷新 
//    public static final int VERIFY_NO_LONGER_POP_UP_INT = 4;//不再弹出  
//      public static final int QUESTION_NO_LONGER_POP_UP_INT = 5;//不再弹出  
    public static boolean VERIFY_NO_LONGER_POP_UP = false;//不再弹出 true代表不弹
    public static boolean QUESTION_NO_LONGER_POP_UP = false;//不再弹出 true代表不弹
    public static boolean USE_VERIFY_API = false;//是否调用付费api，true就是调用
//     public static boolean VERIFY_HAVE_CANCLE = false;//true代表取消
    public static final int VERIFY_LOOP_COUNT = 3;
    public static final String CANCLE_QUESTION_MESSAGE = "取消了识别验证问答，如需开启请到配置中点击\"开启验证\"按钮！";
    public static final String CANCLE_CAPTCHA_MESSAGE = "取消了识别验证码，如需开启请到配置中点击\"开启验证\"按钮！";
    public static final String REFRESH_CAPTCHA_MESSAGE = "刷新验证码";
    public static final String REFRESH_QUESTION_MESSAGE = "刷新验证问答";
    public static final String STATUSTIP_RWQX = "任务取消";
    //点击图片
    public final static int CAPTCHA_TYPE_CLICK = 1;

    //锁对象
    public static final ReentrantLock CaptchaQuestionLock = new ReentrantLock();

    public static final String STATUS_DDZC = "等待注册";
    public static final String STATUS_ZCCG = "注册成功";
    public static final String STATUS_JHCG = "激活成功";
    public static final String STATUS_ZCSB = "注册失败";
    public static final String STATUS_DDDL = "等待登录";
    public static final String STATUS_DLCG = "登录成功";
    public static final String STATUS_DLSB = "登录失败";
    public static final String STATUS_DDFB = "等待发布";
    public static final String STATUS_FBCG = "发布成功";
    public static final String STATUS_FBSB = "发布失败";
    public static final String STATUS_DDHF = "等待回复";
    public static final String STATUS_HFCG = "回复成功";
    public static final String STATUS_HFSB = "回复失败";
    public static final String STATUS_WXHFWZ = "无效回复网址";
    public static final String STATUS_DDZT = "等待抓贴";
    public static final String STATUS_ZTCG = "抓贴成功";
    public static final String STATUS_ZTSB = "抓贴失败";
    public static final String STATUS_BJFBJLCG = "编辑成功";
    public static final String STATUS_SZCG = "设置成功";
    public static final String STATUS_FBDSH = "发布待审核";

    public static final String STATUSTIP_QTCW = "其它错误";
    public static final String STATUSTIP_WXQQ = "无效请求";//未定义操作
    public static final String STATUSTIP_UNKNOWN = "未知错误";
    public static final String STATUSTIP_FWQCW = "服务器错误";
    public static final String STATUSTIP_WYCW = "网页错误";
    public static final String STATUSTIP_TJHWYCW = "提交后网页错误";
    public static final String STATUSTIP_TJQQCW = "提交请求错误";
    public static final String STATUSTIP_BBTD = "版本太低";
    public static final String STATUSTIP_MYBD = "没有表单";
    public static final String STATUSTIP_DDSH = "等待审核";
    public static final String STATUSTIP_DDJH = "等待邮箱激活";
    public static final String STATUSTIP_YXDLSB = "邮箱登录失败";
    public static final String STATUSTIP_YXBS = "邮箱被锁";
    public static final String STATUSTIP_YJHQZCDZ = "邮件获取注册地址";
    public static final String STATUSTIP_YHDDYZ = "用户等待验证";
    public static final String STATUSTIP_XTTS = "系统推送";
    public static final String STATUSTIP_SDTJ = "手动添加";
    public static final String STATUSTIP_BZCQQZC = "不支持QQ注册";
    public static final String STATUSTIP_JZPFZC = "禁止频繁注册";
    public static final String STATUSTIP_QQDL = "QQ登录";
    public static final String STATUSTIP_MYQQZHHMM = "没有QQ帐号和密码";
    public static final String STATUSTIP_QQZHYSY = "qq账号已使用";
    public static final String STATUSTIP_ZBDQQDLDZ = "找不到QQ登录地址";
    public static final String STATUSTIP_QQGSBZQ = "QQ不正确或为空";
    public static final String STATUSTIP_QQDKSB = "QQ登录页打开失败";
    public static final String STATUSTIP_QQJCYC = "QQ号检测异常";
    public static final String STATUSTIP_QQDLSB = "QQ登录失败";
    public static final String STATUSTIP_YZMCW = "验证码错误";
    public static final String STATUSTIP_YZMWSR = "验证码未输入";
    public static final String STATUSTIP_YZMBZC = "验证码不支持";
//    public static final String STATUSTIP_YZMBZCHYMCW = "验证码不支持或域名错误";//暂时注释，改成验证码错误了
    public static final String STATUSTIP_YZWDCW = "验证问答错误";
    public final static String STATUSTIP_QUESTION_QUXIAO = "取消了验证问答识别";
    public final static String STATUSTIP_CAPTCHA_QUXIAO = "取消了验证码识别";
    public static final String STATUSTIP_YXYY = "邮箱已用";
    public static final String STATUSTIP_YXWX = "邮箱无效";
    public static final String STATUSTIP_YXJHSB = "邮箱激活失败";
    public static final String STATUSTIP_YXWKHYW = "邮箱为空或用完";
    public static final String STATUSTIP_WXZC = "未先注册";
    public static final String STATUSTIP_YXYMBKY = "邮箱域名不可用";
    public static final String STATUSTIP_JZZC = "禁止注册";
    public static final String STATUSTIP_NCYY = "昵称已用";
    public static final String STATUSTIP_XZWNC = "需中文昵称";
    public static final String STATUSTIP_SZNCSB = "设置昵称失败";

    //内置头像地址
    public static String avatarUrl = "res/avatar";
    public static final String STATUSTIP_JZDL = "禁止登录";
    public static final String STATUSTIP_ZCGB = "注册关闭";
//     public static final String STATUSTIP_YXYZZ= "邮箱验证中";
    public static final String STATUSTIP_SQMMZ = "收取密码中";
    public static final String STATUSTIP_BZCZC = "不支持注册";
    public static final String STATUSTIP_ZQCG = "抓取成功";
    public static final String STATUSTIP_ZQTJ = "抓取添加";
    public static final String STATUSTIP_DKSB = "打开失败";
    public static final String STATUSTIP_ZYDKSB = "主页打开失败";
    public static final String STATUSTIP_ZCYDKSB = "注册页打开失败";
    public static final String STATUSTIP_DLYDKSB = "登录页打开失败";
    public static final String STATUSTIP_FBYDKSB = "发布页打开失败";
    public static final String STATUSTIP_FBYMDS = "发布页面丢失";
    public static final String STATUSTIP_JCBDBD = "检测不到表单";
    public static final String STATUSTIP_HFYDKSB = "回复页打开失败";
    public static final String STATUSTIP_ZCYMDS = "注册页面丢失";
    public static final String STATUSTIP_DLYMDS = "登录页面丢失";
//    public static final String STATUSTIP_YZMBZC = "滑动验证";//改成验证码不支持
    public static final String STATUSTIP_HFYMDS = "回复页面丢失";
    public static final String STATUSTIP_HBNRCF = "发布内容重复";
    public static final String STATUSTIP_HDYZ = "滑动验证";
    public static final String STATUSTIP_ZBDZCDZ = "找不到注册地址";
    public static final String STATUSTIP_ZBDDLDZ = "找不到登录地址";
    public static final String STATUSTIP_ZBDFBDZ = "找不到发布地址";
    public static final String STATUSTIP_ZBDKFBBK = "找不到可发布版块";
    public static final String STATUSTIP_ZBDHFDZ = "找不到回复地址";
    public static final String STATUSTIP_ZBDHFBK = "找不到回复版块";
    public static final String STATUSTIP_ZBDZTDZ = "找不到抓贴地址";
    public static final String STATUSTIP_ZBDTJDZ = "找不到提交地址";
    public static final String STATUSTIP_GBKWZT = "该版块无主题";
    public static final String STATUSTIP_SXZCYM = "失效注册页面";
    public static final String STATUSTIP_SXDLYM = "失效登录页面";
    public static final String STATUSTIP_CWDZ = "错误地址";
    public static final String STATUSTIP_XYYQM = "需要邀请码";
    public static final String STATUSTIP_HAQM = "含安全码";
    public static final String STATUSTIP_DXYZ = "短信验证";
    public static final String STATUSTIP_YHMFF = "用户名非法";
    public static final String STATUSTIP_YHMZWZC = "用户名中文注册";
    public static final String STATUSTIP_YHMXXZC = "用户名小写注册";
    public static final String STATUSTIP_YHMYSY = "用户名已使用";
    public static final String STATUSTIP_ZHHMMCW = "帐号或密码错误";
    public static final String STATUSTIP_ZHWJH = "帐号未激活";
    public static final String STATUSTIP_BXSYQQZHDL = "必须使用QQ帐号登录";
    public static final String STATUSTIP_ZHBJ = "账号被禁";
    public static final String STATUSTIP_MMFF = "密码非法";
    public static final String STATUSTIP_MMTR = "密码太弱";
    public static final String STATUSTIP_MMJYSB = "密码校验失败";
    public static final String STATUSTIP_MMSZSB = "密码设置失败";
    public static final String STATUSTIP_TJWQ = "提交未全";
    public static final String STATUSTIP_QQLLBZQ = "请求来路不正确";
    public static final String STATUSTIP_TJSB = "提交失败";
    public static final String STATUSTIP_XXWTQ = "信息未填全";
    public static final String STATUSTIP_QXBZ = "权限不足";
    public static final String STATUSTIP_FBGB = "发布关闭";
    public static final String STATUSTIP_JZFB = "禁止发布";
    public static final String STATUSTIP_DHFB = "等会发布";
    public static final String STATUSTIP_WQFLJ = "无权发链接";
    public static final String STATUSTIP_XSSXZ = "新手实习中";
    public static final String STATUSTIP_BHWJC = "包含违禁词";
    public static final String STATUSTIP_XSZTX = "需设置头像";
    public static final String STATUSTIP_SZTXSB = "设置头像失败";
    public static final String STATUSTIP_ZHDDSH = "帐号等待审核";
    public static final String STATUSTIP_ZHDDJH = "帐号等待激活";
    public static final String STATUSTIP_ZHBCZHBSCHZZBSH = "主题不存在或被删除或正在被审核";
    public static final String STATUSTIP_ZHIPBJY = "帐号|IP被禁用";
    public static final String STATUSTIP_MXSXZF = "每小时限制发";
    public static final String STATUSTIP_FBJG = "发表间隔";
    public static final String STATUSTIP_FBJGTK = "发表间隔太快";
    public static final String STATUSTIP_WZNRBJS = "所发内容不接受";//以前是文章内容不接受，由于打招呼，发消息等用到次提示
    public static final String STATUSTIP_WZNRTD = "文章内容太短";
    public static final String STATUSTIP_WZNRTC = "文章内容太长";
    public static final String STATUSTIP_YMMYZT = "页面没有主题";
    public static final String STATUSTIP_BTTD = "标题太短";
    public static final String STATUSTIP_BTTC = "标题太长";
    public static final String STATUSTIP_BQTC = "标签太长";
    public static final String STATUSTIP_XSJFBWK = "悬赏积分不为空";
    public static final String STATUSTIP_SWDL = "尚未登录";
    public static final String STATUSTIP_XYBDSJ = "需要绑定手机";
    public static final String STATUSTIP_TZBCZ = "帖子不存在";
    public static final String STATUSTIP_ZBDBK = "找不到版块";
    public static final String STATUSTIP_BKWTKZ = "版块无帖可抓";
    public static final String STATUSTIP_WZC = "未支持";
    public static final String STATUSTIP_CJBOARDSB = "创建Board失败";
    public static final String STATUSTIP_ZHBZT = "帐号被暂停";
    //加好友相关 
    public static final String STATUS_DDDFTY = "等待对方同意";
    //收听相关
    public static final String STATUS_STCG = "收听成功";
    //打招呼相关
    public static final String STATUS_DZHCG = "打招呼成功";

    //通用 
    public static final String STATUSTIP_FFQQ = "非法请求";
    public static final String STATUSTIP_WZFM = "网站繁忙";
    public static final String STATUSTIP_WZWHZ = "网站维护中";
    public static String STATUSTIP_QQTPF = "请求太频繁";
    public static final String STATUSTIP_WLCS = "未支持";
    public static final String STATUSTIP_BZC = "不支持";
    public static final String STATUSTIP_CXYC = "程序异常";

    public static final String STATUSTIP_ = "";//请不要用该状态，该状态只是为了辅助输入的。
    //激活线程等待的锁对象
    public static final String EMAIL_ACTIVATE_THREAD_WAIT_OBJ = "";
    //默认10毫秒启动一个线程
    public static int THREAD_START_INTERVAL = 10;

    static {
//        String captchaMode = PropertiesUtil.getCaptchaMode();
//        if (captchaMode.equalsIgnoreCase(Constants.PROPERTIES_MANUAL)) {
//            VERIFY_NO_LONGER_POP_UP = false;
//        } else if (captchaMode.equalsIgnoreCase(Constants.PROPERTIES_AUTO)) {
//            VERIFY_NO_LONGER_POP_UP = true;
//        } else if (captchaMode.equalsIgnoreCase("")) {
//            VERIFY_NO_LONGER_POP_UP = true;
//        }
//        String question = PropertiesUtil.getQuestionMode();
//        if (question.equalsIgnoreCase("manual")) {
//            QUESTION_NO_LONGER_POP_UP = false;
//        } else if (question.equalsIgnoreCase("auto")) {
//            QUESTION_NO_LONGER_POP_UP = true;
//        }
//        USE_VERIFY_API = captchaMode.equals(Constants.PROPERTIES_AUTO);
    }

}
