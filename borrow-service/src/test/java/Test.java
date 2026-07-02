import java.util.Base64;

public class Test {


    @org.junit.jupiter.api.Test
    public void test() {
        String str = "你们可能不知道只用20万赢到578万是什么概念";
        //Base64不只是可以对字符串进行编码，任何byte[]数据都可以，编码结果可以是byte[]，也可以是字符串
        String encodeStr = Base64.getEncoder().encodeToString(str.getBytes());
        System.out.println("Base64编码后的字符串：" + encodeStr);

        System.out.println("解码后的字符串：" + new String(Base64.getDecoder().decode(encodeStr)));
    }

}
