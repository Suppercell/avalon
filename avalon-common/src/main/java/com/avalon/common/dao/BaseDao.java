package com.avalon.common.dao;

import java.util.List;

/**
 * 基础DAO类
 * @author admin
 *
 * @param <ENTITY>
 * @param <ID>
 */
public interface BaseDao<ENTITY, ID> {
    /**
     * 更据ID查找
     * @param id
     * @return
     */
    ENTITY getById(ID id);

    /**
     * 根据IDS数组查找
     * @param ids
     * @return
     */
    List<ENTITY> getByIds(ID[] ids);

    /**
     * 单个保存，ID返回与对象的id字段
     * @param entity
     */
    void save(ENTITY entity);

    /**
     * 批量保存，ID返回与对象的id字段
     * @param entities
     */
    void saveBatch(List<ENTITY> entities);

    /**
     * 单个更新
     * @param entity
     * @return
     */
    int update(ENTITY entity);

    /**
     * 批量更新
     * @param entity
     * @return
     */
    void updateBatch(List<ENTITY> entities);

    /**
     * 基础查询
     * @param entity
     * @return
     */
    List<ENTITY> query(ENTITY entity);

}
