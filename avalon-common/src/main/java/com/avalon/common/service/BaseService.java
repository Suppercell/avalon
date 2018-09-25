package com.avalon.common.service;

import java.util.List;

/**
 * baseService
 * @author admin
 *
 * @param <ENTITY>
 * @param <ID>
 */
public interface BaseService<ENTITY, ID> {

    ENTITY getById(ID id);

    List<ENTITY> getByIds(ID[] ids);

    void save(ENTITY entity);

    void save(List<ENTITY> entities);

    int update(ENTITY entity);

    void update(List<ENTITY> entities);

    List<ENTITY> query(ENTITY entity);

}
