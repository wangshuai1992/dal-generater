import lombok.Data;

import java.util.Date;

/**
 * SellerInfoDO
 *
 * @author wangshuai
 * @date 2020-05-13 18:53
 */
@Data
public class SellerInfoDO extends BaseDO {

    private static final long serialVersionUID = -7457287750116157705L;

    /**
     * sellerName
     */
    private String sellerName;

    /**
     * 通过jwt登录的id
     */
    private Integer loginId;

    /**
     * VERIFYING:审核中|VERIFY_FAILED:审核失败|VERIFIED:审核成功|REVERIFYING:重新审核中|RESTRICTED:被限制
     */
    private String status;

    /**
     * 最近一次申请时间UTC
     */
    private Date lastApplyTime;

    /**
     * 审核的次数, 审核成功或者失败后, 次数加1
     */
    private Integer verifyCount;

    /**
     * 审核决策时间
     */
    private Date verifyTime;

    /**
     * 审核用户id
     */
    private Integer verifyUserId;

    /**
     * 审核用户姓名
     */
    private String verifyUserName;

    /**
     * 审核拒绝原因
     */
    private String verifyRejectReason;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 卖家性质
     */
    private String companyAttribute;

    /**
     * 接收推送信息的手机号
     */
    private String messagePhone;

    /**
     * 国家
     */
    private String country;

    /**
     * 统一征信号
     */
    private String pan;

    /**
     * 联系人姓名
     */
    private String contactsName;

    /**
     * 联系人电话
     */
    private String contactsPhone;

    /**
     * 品牌相关正面图片
     */
    private String brandAuthFrontUrl;

    /**
     * 品牌相关反面图片
     */
    private String brandAuthBackUrl;

    private String gstNo;

    private String panFrontUrl;

    private String panBackUrl;

    private String otherStoreLinks;

    private String whatsAppAccount;

    private String tan;

    private String extInfo;

    private String bdName;

    /**
     * 是否是测试账号（0否 、1是）
     */
    private Integer isTest;

}
