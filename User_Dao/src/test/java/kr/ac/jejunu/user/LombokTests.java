package kr.ac.jejunu.user;

import org.junit.jupiter.api.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LombokTests {
    @Test
    public void equals(){
        User user = User.builder().id(1).name("hyun").password("1234").build();
        User user2 = User.builder().id(1).name("hyun").password("1234").build();
        assertThat(user, is(user2));
    }
}
