/**
 * SellerInfoQuery
 *
 * @author wangshuai
 * @date 2020-05-20 19:08
 */
@Data
public class SellerInfoQuery<T> extends PageQuery<T> {

    private static final long serialVersionUID = -741257029564203242L;

    private String sellerName;

    private Integer loginId;

    private String status;

    private Date lastApplyTime;

    private Integer verifyCount;

    private Date verifyTime;

    private Integer verifyUserId;

    private String verifyUserName;

    private String verifyRejectReason;

    private String email;

    private String companyAttribute;

    private String messagePhone;

    private String country;

    private String pan;

    private String contactsName;

    private String contactsPhone;

    private String brandAuthFrontUrl;

    private String brandAuthBackUrl;

    private String gstNo;

    private String panFrontUrl;

    private String panBackUrl;

    private String otherStoreLinks;

    private String whatsAppAccount;

    private String tan;

    private String extInfo;

    private String bdName;

    private Integer isTest;

}
