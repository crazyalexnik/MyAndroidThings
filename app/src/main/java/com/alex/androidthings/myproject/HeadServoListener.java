package com.alex.androidthings.myproject;


import org.apache.commons.logging.Log;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import std_msgs.Int32;

public class HeadServoListener extends AbstractNodeMain {


    public interface Callback {
        void onMessageReceived(int data);
    }


    private Callback mCallback;

    public HeadServoListener(Callback callback){
        mCallback = callback;
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("android/alarm_listener");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        final Log log = connectedNode.getLog();
        Subscriber<Int32> subscriber = connectedNode.newSubscriber("/servohead", Int32._TYPE);
        subscriber.addMessageListener(new MessageListener<Int32>() {
            @Override
            public void onNewMessage(Int32 message) {

                if(mCallback != null) {
                    message.getData();
                        mCallback.onMessageReceived(message.getData());


                }
            }
        });
    }
}