package com.wangzhe.service;

import com.wangzhe.util.Constants;
//import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

/**
 * 线程池，根据用户配置数量创建线程池。本类是对java5以后的自有类ExecutorService的封装。
 * @since 1.0
 * @author ocq
 */
public class ThreadPool {

    private static final Logger log = Logger.getLogger(ThreadPool.class.getName());

    private static ExecutorService pool;//单例
    private static int pool_size;
    private static int tempcount;

    public static ExecutorService initThreadPool() {

        if (pool == null || pool.isShutdown()) {
//            if (pool == null) {·
//                log.info("第一次初始化线程池");
//            } else {
//                log.info("线程池shutdown后再次启动线程池");
//            }
//            pool_size = Integer.parseInt(PropertiesUtil.getSiteSearchThreadCount());

            //格式化线程池名字
            //"sitePool"+Thread.currentThread().getId()+"-"+Thread.currentThread().getName()+"-%s"
//            final ThreadFactory threadFactory = new ThreadFactoryBuilder()//-%d-#%d-%1$d-
//                    .setNameFormat("sitePool #%d")
//                    .setDaemon(false)
//                    .build();
//            pool = Executors.newFixedThreadPool(Constants.siteTaskThreadCount, threadFactory);
            //创建固定线程池
            pool = Executors.newFixedThreadPool(Constants.siteTaskThreadCount);
            //callable可以有返回值，可以处理异常，用call()方法运行
//            final Future<Integer> division = pool.submit(() -> 1 / 0);
//              //below will throw ExecutionException caused by ArithmeticException
//            division.get();
            tempcount = 0;
        }
        //如果线程池还没有==null或者没有shutdown那么直接返回
        return pool;
    }

    public static List<Runnable> shutdownNow() {
        if (pool == null) {
            return null;
        }
        List<Runnable> runableList = pool.shutdownNow();
//          pool=null;
        return runableList;
    }

    public static void shutdown() {
        if (pool != null) {
            pool.shutdown();
        }
    }

    public static Future<?> submit(Runnable task) {
//         log.info("线程池接收到任务"+(++tempcount));
        initThreadPool();
//        log.info("线程池中正在运行的任务数量为："+((ThreadPoolExecutor) ThreadPool.getPool()).getActiveCount());
        return pool.submit(task);
    }

    public static void execute(Runnable task) {
        initThreadPool();
        pool.execute(task);
    }

    public static void submit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static boolean isShutdown() {
        if (pool == null) {
            return true;
        }
        return pool.isShutdown();
    }

    public static boolean isTerminated() {
        if (pool == null) {
            return true;
        }
        return pool.isTerminated();
    }

    public static ExecutorService getPool() {
        return pool;
    }

    public static void setPool(ExecutorService pool) {
        ThreadPool.pool = pool;
    }

    public static int getPool_size() {
        return pool_size;
    }

    public static void setPool_size(int pool_size) {
        ThreadPool.pool_size = pool_size;
    }

    public static boolean awaitTermination(long timeout, TimeUnit unit) {
        try {
            return pool.awaitTermination(timeout, unit);
        } catch (InterruptedException ex) {
            log.error(ex);
        }
        return false;
    }

    public static void main(String[] args) {
//          ExecutorService pool = ThreadPool.getThreadPool();
//          pool.submit(
//                  new Runnable() {
//                      @Override
//                      public void run() {
//                          try {
//                              log.info("(执行任务1");
//                              Thread.sleep(10000);
//                          } catch (InterruptedException ex) {
//                              final Logger.getLogger(ThreadPool.class.getName()).log(Level.SEVERE, null, ex);
//                          }
//                      }
//                  }
//          );
//                
//          log.info(pool);
//          pool.shutdown();
//          
//           pool.execute(
//                  new Runnable() {
//                      @Override
//                      public void run() {
//                          try {
//                              log.info("(执行任务2");
//                              Thread.sleep(10000);
//                          } catch (InterruptedException ex) {
//                              final Logger.getLogger(ThreadPool.class.getName()).log(Level.SEVERE, null, ex);
//                          }
//                      }
//                  }
//          );
//          log.info(pool);
//            pool=ThreadPool.getThreadPool();
//            log.info(pool);
//          pool.shutdownNow();
//          log.info(pool);
//            pool=ThreadPool.getThreadPool();
//            log.info(pool);
    }

}
