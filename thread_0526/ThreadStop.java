package exercise.thread_0526;


//方法一：设置标记位
/*class ThreadShutDown implements Runnable{
    private boolean flag = true;
    public void setFlag(boolean flag){
        this.flag = flag;
    }

    @Override
    public void run() {
        int i=0;
        while(flag){
            System.out.println(Thread.currentThread().getName()+","+i++);
        }
        System.out.println("线程停止");
    }
}
public class ThreadStop {
    public static void main(String[] args) throws InterruptedException {
        ThreadShutDown threadShutDown = new ThreadShutDown();
        Thread thread = new Thread(threadShutDown);
        thread.start();
        Thread.sleep(1000);
        thread.stop();////2.调用Thread类提供的stop方法强行关闭线程。
        threadShutDown.setFlag(false);
    }
}*/

//方法一无法处理线程阻塞时停止的问题
/*
class ThreadShutDown2 implements Runnable{
    private boolean flag = true;
    public void setFlag(boolean flag){
        this.flag = flag;
    }

    @Override
    public void run() {
        int i=0;
        while(flag){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("线程停止");
    }
}
public class ThreadStop {
    public static void main(String[] args) throws InterruptedException {
        ThreadShutDown2 threadShutDown = new ThreadShutDown2();
        Thread thread = new Thread(threadShutDown);
        thread.start();
        thread.sleep(200);
        threadShutDown.setFlag(true);
    }
}
*/

//3.调用Thread类提供的interrupt()：
//若线程中没有使用类似sleep/wait/join时，调用此线程对象的interrupt方法并不会真正中断线程，
// 只是简单地将线程的状态置为interrupt而已（实例1）
/*
class ThreadShutDown2 implements Runnable{
    private boolean flag = true;
    public void setFlag(boolean flag){
        this.flag = flag;
    }

    @Override
    public void run() {
        int i=0;
        while(flag){
            boolean bool = Thread.currentThread().isInterrupted();
            System.out.println("当前线程状态为："+bool);
            System.out.println(Thread.currentThread().getName()+","+i++);
        }
        System.out.println("线程停止");
    }
}
public class ThreadStop {
    public static void main(String[] args) throws InterruptedException {
        ThreadShutDown2 threadShutDown = new ThreadShutDown2();
        Thread thread = new Thread(threadShutDown);
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
*/

//我们可以根据此状态来进一步确定如何处理线程
/*class ThreadShutDown2 implements Runnable{
    private boolean flag = true;
    public void setFlag(boolean flag){
        this.flag = flag;
    }

    @Override
    public void run() {
        int i=0;
        while(flag){
            boolean bool = Thread.currentThread().isInterrupted();
            if(bool){
                System.out.println("线程已被中断");
                return;
            }
            System.out.println("当前线程状态为："+bool);
            System.out.println(Thread.currentThread().getName()+","+i++);
        }
        System.out.println("线程停止");
    }
}
public class ThreadStop {
    public static void main(String[] args) throws InterruptedException {
        ThreadShutDown2 threadShutDown = new ThreadShutDown2();
        Thread thread = new Thread(threadShutDown);
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}*/

/*
若线程中调用了阻塞线程的方法如:
sleep()、wait()、join()
此时再调用线程的interrupt方法时会抛出异常InterruptedException,
同时将线程状态还原(isInterrupted = false)
 */
class ThreadShutDown2 implements Runnable{
    private boolean flag = true;
    public void setFlag(boolean flag){
        this.flag = flag;
    }

    @Override
    public void run() {
        int i=0;
        while(flag){
            try {
                Thread.sleep(200);
                boolean bool = Thread.currentThread().isInterrupted();
                if(bool){
                    System.out.println("线程已被中断");
                    return;
                }
                System.out.println("当前线程状态为："+bool);
                System.out.println(Thread.currentThread().getName()+","+i++);
            } catch (InterruptedException e) {
                System.out.println("抛出中断异常");
                return ;
            }
        }
        System.out.println("线程停止");
    }
}
public class ThreadStop {
    public static void main(String[] args) throws InterruptedException {
        ThreadShutDown2 threadShutDown = new ThreadShutDown2();
        Thread thread = new Thread(threadShutDown);
        thread.start();
        thread.sleep(1000);
        thread.interrupt();
    }
}

