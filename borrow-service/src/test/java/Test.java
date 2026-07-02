import java.util.Base64;

public class Test {


    @org.junit.jupiter.api.Test
    public void test() {
        String header = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
        String payload = "eyJleHAiOjE3ODMwMTc1ODYsInVzZXJfbmFtZSI6InRlc3QiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiNTFhNTRmMTAtMTg0Zi00OTgzLWJlMjEtNWY2NzU3NDk5NDQxIiwiY2xpZW50X2lkIjoid2ViIiwic2NvcGUiOlsiYm9vayIsInVzZXIiLCJib3Jyb3ciXX0";


        System.out.println("header :  " + new String(Base64.getUrlDecoder().decode(header)));
        System.out.println("payload:  " + new String(Base64.getUrlDecoder().decode(payload)));
    }

}
