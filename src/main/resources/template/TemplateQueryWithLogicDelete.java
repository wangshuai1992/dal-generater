package %%queryPackage%%;

import java.util.Date;

/**
 * %%queryClassName%%
 *
 * @author wangshuai
 * @date %%date%%
 */
@Data
public class %%queryClassName%%<T> extends PageQuery<T> {

    private static final long serialVersionUID = -%%serialVersionUID%%L;

%%fields%%

    /**
     * 是否删除 0 否，1 是
     */
    private Integer isDeleted = 0;

}
