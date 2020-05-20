/**
 * SellerInfoDAO
 *
 * @author wangshuai
 * @date 2020-05-20 19:08
 */
@Service
@Slf4j
public class SellerInfoDAO {

    @Resource
    private SellerInfoMapper mapper;

    public SellerInfoQuery<SellerInfoDO> pageQuery(SellerInfoQuery<SellerInfoDO> query) {
        int count = mapper.countByQuery(query);
        if (count != 0) {
            query.setTotalCount(count);
            List<SellerInfoDO> list = mapper.pageQuery(query);
            query.setDataList(list);
        }
        return query;
    }

    public List<SellerInfoDO> fullQuery(SellerInfoQuery<SellerInfoDO> query) {
        return mapper.fullQuery(query);
    }

    public void insert(SellerInfoDO record) {
        mapper.insert(record);
    }

    public void batchInsert(List<SellerInfoDO> records) {
        if (!records.isEmpty()) {
            mapper.batchInsert(records);
        }
    }

    public SellerInfoDO findById(Long id) {
        return mapper.findById(id);
    }

    public void update(SellerInfoDO record) {
        mapper.update(record);
    }

    public void batchUpdateById(List<SellerInfoDO> records) {
        if (!CollectionUtils.isEmpty(records)) {
            mapper.batchUpdateById(records);
        }
    }

    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    public void deleteByIds(List<Long> idList) {
        if(idList.isEmpty()) {
            return;
        }
        mapper.deleteByIds(idList);
    }

}
