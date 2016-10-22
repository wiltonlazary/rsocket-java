/*
 * Copyright 2016 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.reactivesocket.aeron;


import io.aeron.driver.MediaDriver;
import io.aeron.driver.ThreadingMode;
import org.agrona.concurrent.SleepingIdleStrategy;

import java.util.concurrent.TimeUnit;

public class MediaDriverHolder {
    private static final MediaDriverHolder INSTANCE = new MediaDriverHolder();

    static {
        final io.aeron.driver.MediaDriver.Context ctx = new io.aeron.driver.MediaDriver.Context()
            .threadingMode(ThreadingMode.SHARED)
            .dirsDeleteOnStart(true)
            .conductorIdleStrategy(new SleepingIdleStrategy(TimeUnit.MILLISECONDS.toNanos(1)))
            .receiverIdleStrategy(new SleepingIdleStrategy(TimeUnit.MILLISECONDS.toNanos(1)))
            .senderIdleStrategy(new SleepingIdleStrategy(TimeUnit.MILLISECONDS.toNanos(1)));

        ctx.driverTimeoutMs(TimeUnit.MINUTES.toMillis(10));
        MediaDriver.launch(ctx);
    }

    private MediaDriverHolder() {}

    public static MediaDriverHolder getInstance() {
        return INSTANCE;
    }
}
