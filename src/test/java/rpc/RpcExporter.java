package rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author: 温海林
 * 2018年07月11日16:57:52
 */
public class RpcExporter {
    static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void exporter(final String hostName, final int port) throws IOException {
        final ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(hostName, port));
        try {
            while (true) {
                executor.execute(new ExporterTask(server.accept()));
            }
        } finally {
            server.close();
        }
    }

    private static class ExporterTask implements Runnable {
        Socket client;

        public ExporterTask(final Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            ObjectOutputStream output = null;
            ObjectInputStream input = null;
            try {
                input = new ObjectInputStream(client.getInputStream());
                final String interfaceName = input.readUTF();
                final Class<?> service = Class.forName(interfaceName);
                final String methodName = input.readUTF();
                final Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                final Object[] arguments = (Object[]) input.readObject();
                final Method method = service.getMethod(methodName, parameterTypes);
                final Object result = method.invoke(service.newInstance(), arguments);
                output = new ObjectOutputStream(client.getOutputStream());
                output.writeObject(result);
            } catch (final Exception e) {
                e.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
                if (output != null) {
                    try {
                        output.close();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
                if (client != null) {
                    try {
                        client.close();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
