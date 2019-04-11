import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: JVMStudy
 * @description:
 * @author: hjc
 * @create: 2019-04-02 13:35
 */
public class SelectorServer {
    private String IP;

    public int PORT;

    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private boolean closed;

    public SelectorServer(String IP, int PORT) {
        this.IP = IP;
        this.PORT = PORT;
        try {
            serverSocketChannel.bind(new InetSocketAddress(IP, PORT));
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Runnable selectRunable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    int n = selector.select();
                    if (n == 0) {
                        continue;
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        //如果是accept操作，将接受的信道设置为非阻塞模式，并注册到选择器
                        if (key.isAcceptable()) {
                            SocketChannel channel=((ServerSocketChannel)key.channel()).accept();
                            channel.configureBlocking(false);
                            channel.register(key.selector(),SelectionKey.OP_READ,byteBuffer);
                        }
                        //读
                        if (key.isReadable()) {

                        }
                        //写
                        if (key.isWritable() && key.isValid()) {

                        }
                        //链接
                        if (key.isConnectable()) {

                        }
                        iterator.remove();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    };
}
