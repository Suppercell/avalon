package com.avalon.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import com.avalon.common.util.RedisUtil;
import com.avalon.common.util.StringUtil;

public class RedisSessionDao extends EnterpriseCacheSessionDAO {
    public final static String SESSION_KEY_PREFIX = "SESSION_KEY_PREFIX_";

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    /**
    * 删除session
    */
    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            return;
        }
        RedisUtil.del(SESSION_KEY_PREFIX + session.getId());
    }

    /**
    * 获取存活的sessions
    */
    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();
        Set<String> keys = RedisUtil.keys("SESSION_KEY_PREFIX" + "*");
        for (String key : keys) {
            String result = RedisUtil.getKey(SESSION_KEY_PREFIX + key);
            if (StringUtil.isNotBlank(result)) {
                sessions.add((Session) SerializationUtils.deserialize(result));
            }
        }
        return sessions;
    }

    /**
    * 创建session
    */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        assignSessionId(session, sessionId);
        saveSession(session);
        return sessionId;
    }

    /**
    * 获取session
    */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("read session");
        if (sessionId == null) {
            return null;
        }
        String result = RedisUtil.getKey(SESSION_KEY_PREFIX + sessionId);
        if (StringUtil.isBlank(result)) {
            return null;
        }
        Session s = (Session) SerializationUtils.deserialize(result);
        return s;
    }

    /**
    * 保存session并存储过期时间
    * @param session
    * @throws UnknownSessionException
    */
    public void saveSession(Session session) throws UnknownSessionException {
        if (session == null) {
            return;
        }
        //设置过期时间
        int expireTime = 60;
        RedisUtil.setnx(SESSION_KEY_PREFIX + session.getId(),
            SerializationUtils.serialize(session), expireTime);
    }
}
