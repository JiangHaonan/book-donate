package com.mutou.order.job;

/**
 * DeliveryConfirmJob，用于下订单后发送到物流配送系统的消息的可靠性确认
 */
public class DeliveryConfirmJob {

    // TODO
    // 遍历WAIT_DELIVER状态的ORDER，在超时以后重新sendMessage给物流配送系统，确保消息发送的可靠性
    // 用分布式Job框架 elastic-job或者xxl-job
}
