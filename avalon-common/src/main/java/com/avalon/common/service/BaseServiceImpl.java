package com.avalon.common.service;

import java.util.ArrayList;
import java.util.List;

import com.avalon.common.dao.BaseDao;
import com.github.pagehelper.PageHelper;

/**
 * BaseService实现
 * @author admin
 *
 * @param <T>
 * @param <ID>
 */
public abstract class BaseServiceImpl<T, ID> implements BaseService<T, ID> {
    protected abstract BaseDao<T, ID> getDao();

    @Override
    public T getById(ID id) {
        return getDao().getById(id);
    }

    @Override
    public List<T> getByIds(ID[] ids) {
        if (ids == null || ids.length == 0) {
            return new ArrayList<T>();
        }
        return getDao().getByIds(ids);
    }

    @Override
    public void save(T entity) {
        getDao().save(entity);
    }

    @Override
    public void save(List<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return;
        }
        getDao().saveBatch(entities);
    }

    @Override
    public int update(T entity) {
        return getDao().update(entity);
    }

    @Override
    public void update(List<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return;
        }
        getDao().updateBatch(entities);
    }

    @Override
    public List<T> query(T entity) {
        return getDao().query(entity);
    }

    protected void preparePage(Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }
        PageHelper.startPage(pageNo, pageSize);
    }
}
