/**
 * SellerInfoMapper
 *
 * @author wangshuai
 * @date 2020-05-20 19:08
 */
@Mapper
public interface SellerInfoMapper {

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insert(SellerInfoDO record);

    /**
     * 批量新增
     *
     * @param records
     * @return
     */
    int batchInsert(List<SellerInfoDO> records);

    /**
     * 根据条件更新
     *
     * @param record
     * @return
     */
    int update(SellerInfoDO record);

    /**
     * 批量更新
     *
     * @param records
     * @return
     */
    void batchUpdateById(List<SellerInfoDO> records);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

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
    SellerInfoDO findById(Long id);

    /**
     * 查询总数
     *
     * @param query
     * @return
     */
    int countByQuery(SellerInfoQuery<SellerInfoDO> query);

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    List<SellerInfoDO> pageQuery(SellerInfoQuery<SellerInfoDO> query);

    /**
     * 根据条件获取所有符合记录的数据
     *
     * @param query
     * @return
     */
    List<SellerInfoDO> fullQuery(SellerInfoQuery<SellerInfoDO> query);

}
