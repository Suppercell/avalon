package com.avalon.common.listener.log;

import org.springframework.stereotype.Component;

@Component
public class AspectLogger {

    //    protected final Logger logger = Logger.getLogger(getClass());
    //
    //    private Random         random = new Random();
    //
    //    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    //        long time = System.currentTimeMillis();
    //        int num = random.nextInt();
    //        logger.debug("[" + num + "]调用方法名为: " + pjp.getTarget().getClass().getName() + "."
    //                     + pjp.getSignature().getName());
    //        Object[] params = pjp.getArgs();
    //        if (params != null) {
    //            int seq = 1;
    //            for (Object obj : params) {
    //                try {
    //                    if (obj != null) {
    //                        logger
    //                            .debug("[" + num + "]调用第" + (seq++) + "个参数，类型为"
    //                                   + obj.getClass().getName() + "，数值为：" + BeanUtils.describe(obj));
    //                    } else {
    //                        logger.debug("[" + num + "]调用第" + (seq++) + "个参数，数值为：null");
    //                    }
    //
    //                } catch (Exception e) {
    //                    logger.debug("[" + num + "]调用第" + (--seq) + "个参数，类型为"
    //                                 + obj.getClass().getName() + "，参数不可描述");
    //                }
    //            }
    //        }
    //        Object retVal = pjp.proceed();
    //        time = System.currentTimeMillis() - time;
    //        logger.debug("[" + num + "]调用耗时：" + time + " 毫秒");
    //        return retVal;
    //
    //    }
    //
    //    public void doBefore(JoinPoint jp) {
    //        int num = random.nextInt();
    //        logger.debug("[" + num + "]调用方法名为: " + jp.getTarget().getClass().getName() + "."
    //                     + jp.getSignature().getName());
    //        Object[] params = jp.getArgs();
    //        try {
    //            int seq = 1;
    //            for (Object obj : params) {
    //                logger.debug("[" + num + "]调用第" + (seq++) + "个参数：" + BeanUtils.describe(obj));
    //            }
    //        } catch (Exception e) {
    //            logger.error(e);
    //        }
    //    }
    //
    //    public void doAfter(JoinPoint jp) {
    //        logger.info("log Ending method: " + jp.getTarget().getClass().getName() + "."
    //                    + jp.getSignature().getName());
    //    }
    //
    //    public void doThrowing(JoinPoint jp, Throwable ex) {
    //        logger.info("method " + jp.getTarget().getClass().getName() + "."
    //                    + jp.getSignature().getName() + " throw exception");
    //        logger.info(ex.getMessage());
    //    }

}
