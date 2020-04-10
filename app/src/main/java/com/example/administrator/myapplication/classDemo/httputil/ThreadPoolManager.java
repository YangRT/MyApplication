package com.example.administrator.myapplication.classDemo.httputil;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//线程池管理者
public class ThreadPoolManager {

    private static ThreadPoolManager threadPoolManager;
    //创建队列
    private LinkedBlockingQueue<Runnable> mQueue;
    //创建延时队列
    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<>();
    //创建线程池
    private ThreadPoolExecutor mExecutor;

    //创建核心线程，获取队列请求，交给线程池处理
    public Runnable coreThread = new Runnable() {
        Runnable runnable;
        @Override
        public void run() {
            while (true){
                try {
                    runnable = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mExecutor.execute(runnable);
            }
        }
    };

    //构造函数初始化
    private ThreadPoolManager(){
        mQueue = new LinkedBlockingQueue<>();
        mExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                addTask(r);
            }
        });
        mExecutor.execute(coreThread);
        mExecutor.execute(delayedThread);
    }

    //单例模式
    public static ThreadPoolManager getInstance(){
        if(threadPoolManager == null){
            synchronized (ThreadPoolManager.class){
                if (threadPoolManager == null){
                    threadPoolManager = new ThreadPoolManager();
                }
            }
        }
        return threadPoolManager;
    }

    //添加任务
    public void addTask(Runnable runnable){
        if(runnable != null){
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addDelayTask(HttpTask httpTask){
        if(httpTask != null){
            httpTask.setDelayTime(3000);
            mDelayQueue.offer(httpTask);
        }
    }

    private Runnable delayedThread = new Runnable() {
        @Override
        public void run() {
            HttpTask task = null;
            try {
                while (true) {
                    task = mDelayQueue.take();
                    if (task.getRetryCount() < 3) {
                        mExecutor.execute(task);
                        task.setRetryCount(task.getRetryCount()+1);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };
}
