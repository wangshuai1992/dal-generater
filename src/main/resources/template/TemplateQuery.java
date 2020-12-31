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

}
