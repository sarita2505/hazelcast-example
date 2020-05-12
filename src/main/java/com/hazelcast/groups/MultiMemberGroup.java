package com.hazelcast.groups;

import com.hazelcast.builder.HazelCastInstanceBuilder;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;

import java.util.UUID;

public class MultiMemberGroup {
    private static final String GROUP_1 = "GROUP_1";
    private static final String GROUP_2 = "GROUP_2";

    public static void main(String[] args) {

        HazelcastInstance instance1 = HazelCastInstanceBuilder.build(GROUP_1);
        HazelcastInstance instance2 = HazelCastInstanceBuilder.build(GROUP_1);
        HazelcastInstance instance3 = HazelCastInstanceBuilder.build(GROUP_2);

        IQueue<String> queue1 = instance1.getQueue("q1");
        IQueue<String> queue2 = instance2.getQueue("q1");
        IQueue<String> queue3 = instance3.getQueue("q1");

        QueueService queueService1 = new QueueAddService(queue1, GROUP_1);
        QueueService queueService2 = new QueuePollService(queue2, GROUP_1);
        QueueService queueService3 = new QueuePollService(queue3, GROUP_2);

//        queueService1.process();
//        queueService3.process();
//        queueService2.process();

        QueueProcessor[] processors = new QueueProcessor[]{
                new QueueProcessor(queueService1),
                new QueueProcessor(queueService2),
                new QueueProcessor(queueService3)
        };
        for(QueueProcessor queueProcessor: processors)
        {
            Thread t = new Thread(queueProcessor);
            t.start();
        }
    }


}

interface QueueService {
    public void process();
}

abstract class AbstractQueueService implements QueueService {
    private IQueue queue;
    private String name;

    public AbstractQueueService(IQueue queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    public IQueue getQueue() {
        return queue;
    }

    public String getName() {
        return name;
    }
}

class QueueAddService extends AbstractQueueService {
    public QueueAddService(IQueue queue, String name) {
        super(queue, name);
    }

    @Override
    public void process() {
        String data = UUID.randomUUID().toString();
        getQueue().add(data);
        System.out.println("data added: "+getName());
    }
}


class QueuePollService extends AbstractQueueService {
    public QueuePollService(IQueue queue, String name) {
        super(queue, name);
    }

    @Override
    public void process() {
        System.out.println(this.getClass());
        String data = (String) getQueue().poll();
        System.out.println(getName()+ "data polled: " + data);
    }
}

class QueueProcessor implements Runnable {

    private QueueService queueService;

    public QueueProcessor(QueueService queueService) {
        this.queueService = queueService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queueService.process();
        }
    }
}
