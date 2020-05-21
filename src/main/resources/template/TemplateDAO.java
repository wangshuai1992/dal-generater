package %%daoPackage%%;

/**
 * %%daoClassName%%
 *
 * @author wangshuai
 * @date %%date%%
 */
@Service
@Slf4j
public class %%daoClassName%% {

    @Resource
    private %%mapperClassName%% mapper;

    public %%queryClassName%%<%%doClassName%%> pageQuery(%%queryClassName%%<%%doClassName%%> query) {
        int count = mapper.countByQuery(query);
        if (count != 0) {
            query.setTotalCount(count);
            List<%%doClassName%%> list = mapper.pageQuery(query);
            query.setDataList(list);
        }
        return query;
    }

    public List<%%doClassName%%> fullQuery(%%queryClassName%%<%%doClassName%%> query) {
        return mapper.fullQuery(query);
    }

    public void insert(%%doClassName%% record) {
        mapper.insert(record);
    }

    public void batchInsert(List<%%doClassName%%> records) {
        if (!records.isEmpty()) {
            mapper.batchInsert(records);
        }
    }

    public %%doClassName%% findById(Long id) {
        return mapper.findById(id);
    }

    public void update(%%doClassName%% record) {
        mapper.update(record);
    }

    public void batchUpdateById(List<%%doClassName%%> records) {
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
