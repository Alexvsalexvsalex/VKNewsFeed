package ru.shishkin.rule;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

public class HostReachableRule implements BeforeAllCallback {
    private static final int TIMEOUT = 1_000;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        HostReachable hostReachable
                = extensionContext.getRequiredTestClass().getAnnotation(HostReachable.class);

        if (hostReachable != null) {
            String host = hostReachable.value();
            if (!checkHost(host)) {
                throw new Exception("Skipped, because following host " +
                        "is not available at the moment: " + host);
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD, ElementType.TYPE })
    public @interface HostReachable {
        String value();
    }

    private static boolean checkHost(String host) {
        return nativePing(host) || nativePing6(host);
    }

    private static boolean nativePing(String host) {
        return nativePingImpl("ping", host);
    }

    private static boolean nativePing6(String host) {
        return nativePingImpl("ping6", host);
    }

    private static boolean nativePingImpl(String cmd, String host) {
        try {
            Process pingProcess
                    = new ProcessBuilder(cmd, "-c", "1", host).start();
            if (!pingProcess.waitFor(TIMEOUT, TimeUnit.MILLISECONDS)) {
                return false;
            }
            return pingProcess.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

}
