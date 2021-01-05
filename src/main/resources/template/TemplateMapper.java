package %%mapperPackage%%;

import org.apache.ibatis.annotations.Mapper;

/**
 * %%mapperClassName%%
 *
 * @author wangshuai
 * @date %%date%%
 */
@Mapper
public interface %%mapperClassName%% {

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insert(%%doClassName%% record);

    /**
     * 批量新增
     *
     * @param records
     * @return
     */
    int batchInsert(List<%%doClassName%%> records);

    /**
     * 根据条件更新
     *
     * @param record
     * @return
     */
    int update(%%doClassName%% record);

    /**
     * 批量更新
     *
     * @param records
     * @return
     */
    void batchUpdateById(List<%%doClassName%%> records);

    /**
     * 插入 id相同则更新
     *
     * @param record
     */
    void insertOrUpdate(%%doClassName%% record);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int deleteById(long id);

    /**
     * 根据ids批量删除
     *
     * @param ids
     * @return
     */
    int deleteByIds(List<Long> ids);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    %%doClassName%% findById(long id);

    /**
     * 查询总数
     *
     * @param query
     * @return
     */
    int countByQuery(%%queryClassName%%<%%doClassName%%> query);

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    List<%%doClassName%%> pageQuery(%%queryClassName%%<%%doClassName%%> query);

    /**
     * 根据条件获取所有符合记录的数据
     *
     * @param query
     * @return
     */
    List<%%doClassName%%> fullQuery(%%queryClassName%%<%%doClassName%%> query);

}
