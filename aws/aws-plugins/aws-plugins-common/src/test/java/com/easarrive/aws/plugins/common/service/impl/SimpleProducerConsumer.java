/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : SimpleProducerConsumer.java
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月1日
 * @Time : 上午10:47:16
 */
package com.easarrive.aws.plugins.common.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

//import com.amazonaws.services.sqs.model.BatchResultErrorEntry;
//import com.amazonaws.services.sqs.model.DeleteMessageBatchRequest;
//import com.amazonaws.services.sqs.model.DeleteMessageBatchRequestEntry;
//import com.amazonaws.services.sqs.model.DeleteMessageBatchResult;
//import com.amazonaws.services.sqs.model.DeleteMessageRequest;

/**
 * Start a specified number of producer and consumer threads, and
 * produce-consume for the least of the specified duration and 1h. Some messages
 * can be left in the queue because producers and consumers may not be in exact
 * balance.
 *
 * The program does not validate its arguments.
 *
 * An example command line (omitting class path for clarity) to produce-consume
 * with 1 producer and 2 consumers, batches of 10, for 20min is as follows:
 *
 * {@code java com.amazonaws.sqs.samples.SimpleProducerConsumer [accessKey]
 * [secretKey] https://sqs.us-east-1.amazonaws.com 1 2 10 1024 20}
 *
 * The command line parameters are, in order:
 *
 * @param accessKey       The AWS access key
 * @param secretKey       The AWS secret key
 * @param endpoint        The SQS region endpoint
 * @param queueName       The name of the queue. The program assumes that the queue already
 *                        exists.
 * @param producerCount   The number of producer threads to use
 * @param consumerCount   The number of consumer threads to use
 * @param batchSize       The size of batches to send, receive and delete messages. Must be
 *                        between 1 and 10. With a value of 1, the program uses the single
 *                        operation APIs ({@code SendMessage} and {@code DeleteMessage}).
 * @param messageSizeByte The size of messages to send in bytes. Note that the maximum batch
 *                        size is 64KB so that batchSize * messageSizeByte must be < 64KB.
 * @param runTimeMinutes  The time to run the program for. The program will run for the
 *                        least of this duration and 1h.
 */
public class SimpleProducerConsumer {
    private static Log log = LogFactory.getLog(SimpleProducerConsumer.class);

    // maximum runtime of the program
    private static int MAX_RUNTIME_MINUTES = 60;

    public static void main(String[] args) throws InterruptedException {
        final AWSCredentials credentials = new BasicAWSCredentials("AKIAIDPJMKK4UHLE3OVA", "A+cn+TT3tUs6xbto5k1IKkWwPLBq995aOkqKxZNY");

        final String endpoint = "sqs.us-west-2.amazonaws.com";
        final String queueName = "image";
        final int producerCount = 10;
        final int consumerCount = 3;
        final int batchSize = 3;
        final int messageSizeByte = 10000;
        final int runTimeMinutes = 100;

        // configure the SQS client with enough connections for all producer and
        // consumer threads
        AmazonSQS sqsClient = new AmazonSQSClient(credentials, new ClientConfiguration().withMaxConnections(producerCount + consumerCount));
        sqsClient.setEndpoint(endpoint);
        String queueUrl = sqsClient.getQueueUrl(new GetQueueUrlRequest(queueName)).getQueueUrl();

        // the flag to stop producer, consumer, and monitor threads
        AtomicBoolean stop = new AtomicBoolean(false);

        // start the producers
        final AtomicInteger producedCount = new AtomicInteger();
        Thread[] producers = new Thread[producerCount];
        for (int i = 0; i < producerCount; i++) {
            producers[i] = new BatchProducer(sqsClient, queueUrl, batchSize, messageSizeByte, producedCount, stop);
            producers[i].start();
        }

        // start the consumers
        final AtomicInteger consumedCount = new AtomicInteger();
        Thread[] consumers = new Thread[consumerCount];
        for (int i = 0; i < consumerCount; i++) {
            consumers[i] = new BatchConsumer(sqsClient, queueUrl, batchSize, consumedCount, stop);
            consumers[i].start();
        }

        // start the monitor (thread)
        Thread monitor = new Monitor(producedCount, consumedCount, stop);
        monitor.start();

        // wait for the specified amount of time then stop
        Thread.sleep(TimeUnit.MINUTES.toMillis(Math.min(runTimeMinutes, MAX_RUNTIME_MINUTES)));
        stop.set(true);

        // join all threads
        for (int i = 0; i < producerCount; i++)
            producers[i].join();

        for (int i = 0; i < consumerCount; i++)
            consumers[i].join();

        monitor.interrupt();
        monitor.join();
    }

    /**
     * Producer thread using {@code SendMessageBatch} to send messages until
     * stopped.
     */
    private static class BatchProducer extends Thread {
        final AmazonSQS sqsClient;
        final String queueUrl;
        final int batchSize;
        final AtomicInteger producedCount;
        final AtomicBoolean stop;
        final String theMessage;

        BatchProducer(AmazonSQS sqsQueueBuffer, String queueUrl, int batchSize, int messageSizeByte, AtomicInteger producedCount, AtomicBoolean stop) {
            this.sqsClient = sqsQueueBuffer;
            this.queueUrl = queueUrl;
            this.batchSize = batchSize;
            this.producedCount = producedCount;
            this.stop = stop;
            this.theMessage = makeRandomString(messageSizeByte);
        }

        public void run() {
            try {
                while (!stop.get()) {
                    SendMessageBatchRequest batchRequest = new SendMessageBatchRequest().withQueueUrl(queueUrl);

                    List<SendMessageBatchRequestEntry> entries = new ArrayList<SendMessageBatchRequestEntry>();
                    for (int i = 0; i < batchSize; i++)
                        entries.add(new SendMessageBatchRequestEntry().withId(Integer.toString(i)).withMessageBody(theMessage));
                    batchRequest.setEntries(entries);

                    SendMessageBatchResult batchResult = sqsClient.sendMessageBatch(batchRequest);
                    producedCount.addAndGet(batchResult.getSuccessful().size());

                    // sendMessageBatch can return successfully, and yet
                    // individual batch
                    // items fail. So, make sure to retry the failed ones.
                    if (!batchResult.getFailed().isEmpty()) {
                        log.warn("Producer: retrying sending " + batchResult.getFailed().size() + " messages");
                        for (int i = 0, n = batchResult.getFailed().size(); i < n; i++) {
                            sqsClient.sendMessage(new SendMessageRequest(queueUrl, theMessage));
                            producedCount.incrementAndGet();
                        }
                    }
                }
            } catch (AmazonClientException e) {
                // by default AmazonSQSClient retries calls 3 times before
                // failing,
                // so, when this rare condition occurs, simply stop
                log.error("BatchProducer: " + e.getMessage());
                System.exit(1);
            }
        }
    }

    /**
     * Consumer thread using {@code ReceiveMessage} and
     * {@code DeleteMessageBatch} to consume messages until stopped.
     */
    private static class BatchConsumer extends Thread {
        final AmazonSQS sqsClient;
        final String queueUrl;
        final int batchSize;
        // final AtomicInteger consumedCount;
        final AtomicBoolean stop;

        BatchConsumer(AmazonSQS sqsClient, String queueUrl, int batchSize, AtomicInteger consumedCount, AtomicBoolean stop) {
            this.sqsClient = sqsClient;
            this.queueUrl = queueUrl;
            this.batchSize = batchSize;
            // this.consumedCount = consumedCount;
            this.stop = stop;
        }

        public void run() {
            try {
                ReceiveMessageResult result = null;
                List<Message> messages;
                while (!stop.get()) {
                    result = sqsClient.receiveMessage(new ReceiveMessageRequest(queueUrl).withMaxNumberOfMessages(batchSize));

                    if (!result.getMessages().isEmpty()) {
                        messages = result.getMessages();
                        System.out.println(messages.size() + "\t\t" + messages);
                        // DeleteMessageBatchRequest batchRequest = new
                        // DeleteMessageBatchRequest().withQueueUrl(queueUrl);
                        //
                        // List<DeleteMessageBatchRequestEntry> entries = new
                        // ArrayList<DeleteMessageBatchRequestEntry>();
                        // for (int i = 0, n = messages.size(); i < n; i++)
                        // entries.add(new
                        // DeleteMessageBatchRequestEntry().withId(Integer.toString(i)).withReceiptHandle(messages.get(i).getReceiptHandle()));
                        // batchRequest.setEntries(entries);
                        //
                        // DeleteMessageBatchResult batchResult =
                        // sqsClient.deleteMessageBatch(batchRequest);
                        // consumedCount.addAndGet(batchResult.getSuccessful().size());
                        //
                        // // deleteMessageBatch can return successfully, and
                        // yet
                        // // individual
                        // // batch items fail. So, make sure to retry the
                        // failed
                        // // ones.
                        // if (!batchResult.getFailed().isEmpty()) {
                        // int n = batchResult.getFailed().size();
                        // log.warn("Producer: retrying deleting " + n +
                        // " messages");
                        // for (BatchResultErrorEntry e :
                        // batchResult.getFailed()) {
                        // sqsClient.deleteMessage(new
                        // DeleteMessageRequest(queueUrl,
                        // messages.get(Integer.parseInt(e.getId())).getReceiptHandle()));
                        // consumedCount.incrementAndGet();
                        // }
                        // }
                    }
                }
            } catch (AmazonClientException e) {
                // by default AmazonSQSClient retries calls 3 times before
                // failing,
                // so, when this rare condition occurs, simply stop
                log.error("BatchConsumer: " + e.getMessage());
                System.exit(1);
            }
        }
    }

    /**
     * Thread that prints, every second, the number of messages produced and
     * consumed so far.
     */
    private static class Monitor extends Thread {
        private final AtomicInteger producedCount;
        private final AtomicInteger consumedCount;
        private final AtomicBoolean stop;

        Monitor(AtomicInteger producedCount, AtomicInteger consumedCount, AtomicBoolean stop) {
            this.producedCount = producedCount;
            this.consumedCount = consumedCount;
            this.stop = stop;
        }

        public void run() {
            try {
                while (!stop.get()) {
                    Thread.sleep(1000);
                    log.info("produced messages = " + producedCount.get() + ", consumed messages = " + consumedCount.get());
                }
            } catch (InterruptedException e) {
                // allow thread to exit
            }
        }
    }

    private static String makeRandomString(int sizeByte) {
        byte[] bs = new byte[(int) Math.ceil(sizeByte * 5 / 8)];
        new Random().nextBytes(bs);
        bs[0] = (byte) ((bs[0] | 64) & 127);
        return new BigInteger(bs).toString(32);
    }
}
