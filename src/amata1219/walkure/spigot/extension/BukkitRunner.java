package amata1219.walkure.spigot.extension;

import amata1219.walkure.spigot.Walkure;
import org.bukkit.Bukkit;

public abstract class BukkitRunner implements Runnable {

    private BukkitRunner head;
    private BukkitRunner next;
    private long delay;

    public static BukkitRunner of(long delay, Runnable processing) {
        BukkitRunner runner = new BukkitRunner() {
            @Override
            public void process() {
                processing.run();
            }
        };
        BukkitRunner head = new BukkitRunner() {
            @Override
            public void process() {

            }
        };
        head.next = runner;
        head.delay = delay;
        runner.head = head;
        return runner;
    }

    @Override
    public void run() {
        process();
        if (next != null) Bukkit.getScheduler().runTaskLater(Walkure.instance(), next, delay);
    }

    public abstract void process();

    public BukkitRunner append(long delay, Runnable processing) {
        BukkitRunner next = new BukkitRunner() {
            @Override
            public void process() {
                processing.run();
            }
        };
        next.head = head;
        this.next = next;
        this.delay = delay;
        return next;
    }

    public void runTaskLater() {
        head.run();
    }

}
