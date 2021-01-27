package %%doPackage%%;

import java.util.Date;

/**
 * %%doClassName%%
 *
 * @author wangshuai
 * @date %%date%%
 */
@Data
public class %%doClassName%% extends BaseDO {

    private static final long serialVersionUID = -%%serialVersionUID%%L;

%%fields%%
    /**
     * 是否删除 0 否，1 是
     */
    private Integer isDeleted = 0;

}
