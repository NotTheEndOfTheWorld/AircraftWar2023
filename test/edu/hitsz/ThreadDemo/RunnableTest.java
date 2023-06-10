package edu.hitsz.ThreadDemo;
/**
 * 使用Runnable接口实现多线程
 *
 * @author duskphantom
 */
public class RunnableTest {
    public static void main(String[] args) {

        Runnable r = () -> {
            try {
                for (int i = 0; i < 3; i++) {
                    System.out.println("[" + Thread.currentThread().getName() + "],线程执行，当前的循环次数：" + i);

                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        // 启动线程
        new Thread(r, "线程1").start();
        new Thread(r, "线程2").start();
    }
}
