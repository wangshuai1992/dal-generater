package com.clubfactory.center.seller.core.query.seller;

/**
 * SellerInfoQuery
 *
 * @author wangshuai
 * @date 2020-05-20 22:26
 */
@Data
public class SellerInfoQuery<T> extends PageQuery<T> {

    private static final long serialVersionUID = -004475904324205113L;

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
