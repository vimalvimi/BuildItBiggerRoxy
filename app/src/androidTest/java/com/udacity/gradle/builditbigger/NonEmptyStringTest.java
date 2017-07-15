package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NonEmptyStringTest extends ApplicationTestCase<Application> implements OnJokeReceivedListener {

        CountDownLatch signal;
        String joke;

        public NonEmptyStringTest() {
            super(Application.class);
        }

        public void testJoke() {
            try {
                signal = new CountDownLatch(1);
                new EndPointAsync().execute(this);
                signal.await(10, TimeUnit.SECONDS);
                assertNotNull("Null Joke", joke);
                assertFalse("Empty Joke", joke.isEmpty());
            } catch (Exception ex) {
                fail();
            }
        }

        @Override
        public void onReceived(String joke) {
            this.joke = joke;
            signal.countDown();
        }
    }
