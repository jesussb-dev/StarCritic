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
 * Utilidad para ejecutar tareas en segundo plano fuera del Event Dispatch Thread
 * de Swing y volver al EDT para entregar el resultado o el error.
 * @author Jesús Santos Baquero
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

    /**
     * Ejecutar una tarea con resultado en segundo plano y entregar la respuesta
     * o el error en el EDT.
     * @param <T> el tipo del resultado de la tarea.
     * @param task la tarea a ejecutar fuera del EDT.
     * @param onSuccess callback invocado en el EDT si la tarea termina sin error.
     * @param onError callback invocado en el EDT si la tarea lanza una excepción.
     */
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

    /**
     * Ejecutar una tarea sin resultado en segundo plano. Solo se notifica al
     * EDT en caso de error.
     * @param task la tarea a ejecutar fuera del EDT.
     * @param onError callback invocado en el EDT si la tarea lanza una excepción.
     */
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

    /**
     * Detener el pool de hilos. Solo debe llamarse al cerrar la aplicación.
     */
    public static void shutdown() {
        POOL.shutdownNow();
    }

}