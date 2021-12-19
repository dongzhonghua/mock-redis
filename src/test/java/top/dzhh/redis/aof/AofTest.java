package top.dzhh.redis.aof;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

/**
 * @author dongzhonghua
 * Created on 2021-12-10
 */
class AofTest {
    @Test
    public void mmap() {

    }

    static class TestMmap {
        public static String path =
                "/Users/dongzhonghua03/Documents/github/mock-redis/src/test/java/top/dzhh/redis/aof/mmap";

        public static void main(String[] args) throws IOException {
            File file1 = new File(path, "1");

            RandomAccessFile randomAccessFile = new RandomAccessFile(file1, "rw");

            int len = 2048;
            // 映射为2kb，那么生成的文件也是2kb
            MappedByteBuffer mmap = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, len);

            System.out.println(mmap.isReadOnly());


            System.out.println(mmap.position());
            System.out.println(mmap.limit());

            // 写数据之后，JVM 退出之后会强制刷新的
            for (int i = 0; i < 2048; i++) {
                mmap.put("z".getBytes());
            }
            unmap(mmap);
        }

        // copy from  FileChannelImpl#unmap(私有方法)
        private static void unmap(MappedByteBuffer bb) {
            Cleaner cl = ((DirectBuffer) bb).cleaner();
            if (cl != null) {
                cl.clean();
            }
        }
    }


}