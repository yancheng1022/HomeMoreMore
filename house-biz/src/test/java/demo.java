import java.util.Date;

public class demo {
    public static void main(String[] args) {
        System.out.println(new Date());
        System.out.println(System.currentTimeMillis());
        System.out.println(new Date(System.currentTimeMillis()));
    }
}
