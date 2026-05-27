/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.data;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import javax.swing.SwingUtilities;

/**
 *
 * @author jsanbaq
 */
public class BackgroundWork {

    private static final ExecutorService POOL = Executors.newFixedThreadPool(
            Math.max(2, Runtime.getRuntime().availableProcessors()),
            runnable -> {
                Thread t = new Thread(runnable, "BackgroundWork-worker");
                t.setDaemon(true);
                return t;
            }
    );

    public static <T> void run(Callable<T> task, Consumer<T> onSuccess, Consumer<Throwable> onError) {
        CompletableFuture.supplyAsync(() -> {
            try {
                return task.call();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }, POOL).whenComplete((result, error) -> SwingUtilities.invokeLater(() -> {
            if (error != null) {
                Throwable cause = error.getCause() != null ? error.getCause() : error;
                onError.accept(cause);
            } else {
                onSuccess.accept(result);
            }
        }));
    }

    public static void runVoid(Runnable task, Consumer<Throwable> onError) {
        CompletableFuture
                .runAsync(task, POOL)
                .whenComplete((v, error) -> {
                    if (error != null) {
                        Throwable cause = error.getCause() != null ? error.getCause() : error;
                        SwingUtilities.invokeLater(() -> onError.accept(cause));
                    }
                });
    }

    public static void shutdown() {
        POOL.shutdownNow();
    }

}