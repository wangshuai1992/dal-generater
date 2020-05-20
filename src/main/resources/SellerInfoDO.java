package com.clubfactory.center.seller.core.dataobject.seller;

/**
 * SellerInfoDO
 *
 * @author wangshuai
 * @date 2020-05-20 22:26
 */
@Data
public class SellerInfoDO extends BaseDO {

    private static final long serialVersionUID = -884429125847036858L;

    /**
     * 卖家id
     */
    private Long sellerId;

    /**
     * bankName
     */
    private String bankName;

    /**
     * bankAccount
     */
    private String bankAccount;

    /**
     * accountHolder
     */
    private String accountHolder;

    /**
     * 银行代码
     */
    private String ifscCode;

}
