package com.example.hou;

import com.example.hou.config.RabbitMQConfig;
import com.example.hou.entity.Exam;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author: 作者
 * @create: 2024-04-17 20:18
 */
@SpringBootTest
public class MQTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //首先在service层          // 发布考试开始消息到RabbitMQ
    public void startExam(Exam exam) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXAM_EXCHANGE, RabbitMQConfig.EXAM_ROUTING_KEY, exam);
    }

    public void submitAnswer(String examId, Answer answer) {
        // 提交答案逻辑，这里可以保存到redis作为缓存  注意检测时间
    }

    // 其他考试相关的逻辑...

    /*


监听器用来监听考试开始或结束的消息
    @RabbitListener(queues = RabbitMQConfig.EXAM_QUEUE)
    public class ExamListener {

    @Autowired
    private ExamService examService;

    @RabbitHandler
    public void onMessageReceived(Exam exam) {
        // 收到考试消息后处理逻辑，比如开启一个定时任务检查答题时间等
        // ...
    }

    @RabbitHandler
    public void onAnswerReceived(Answer answer) {
        // 收到答案提交消息后处理逻辑
        examService.submitAnswer(answer.getExamId(), answer);
    }
}



定时任务 用来自动交卷
@Component
public class ExamAutoSubmitTask {

    @Autowired
    private ExamService examService;

    @Scheduled(fixedRate = 10000) // 每10秒执行一次
    public void checkAndSubmitAnswers() {
        // 查询所有未提交答案且已超过截止时间的考试
        // ...
        // 调用examService的自动提交答案方法
    }
}


总之目录结构应该是
        ├── service
        │   ├── ExamService.java
        │   ├── listener
        │   │   └── ExamListener.java
        │   └── task
                └── ExamAutoSubmitTask.java



再比如商品秒杀的MQ设计
创建一个名为seckill_queue的队列，放发起交易请求
设置队列持久化，重启不丢失

交换器与绑定：
创建一个直接交换器seckill_exchange。
将seckill_queue绑定到seckill_exchange上，使用特定的路由键（如seckill.request）。

生产者（秒杀发起）：
从接口收到交易申请  封装成消息，并发送到seckill_exchange。
发送消息时，使用路由键seckill.request，确保消息被路由到正确的队列seckill_queue。

消费者（处理交易）：
设置多个消费者监听seckill_queue，用于处理秒杀请求。
每个消费者从队列中取出一条消息，处理，如验证用户身份、检查库存、生成订单等。
消费者处理完秒杀请求后，将处理结果返回给前端或存储到数据库中。

流量控制：
为了避免消息队列过载，可以设置队列的最大长度和消费者的处理速度限制。
当队列长度达到阈值时，可以拒绝接收新的秒杀请求，或者采用降级策略，如返回秒杀结束提示。

异常处理：
网络等问题 采用RabbitMQ的确认，确保消息被消费后再移除出队。
处理失败的秒杀请求，放入死信队列（DLX），以便后续重试或人工干预。

    */

    @Test
    void test() {








    }

}
