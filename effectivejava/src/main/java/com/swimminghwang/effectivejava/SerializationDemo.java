package com.swimminghwang.effectivejava;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Base64;

/**
 *
 * 코드 출처 :https://woowabros.github.io/experience/2017/10/17/java-serialize.html
 *
 * [직렬화 예제 설명]
 *
 * 1. ByteArrayOutputStream을 이용하여 객체를 바이트 배열로 쓸 준비를 한다.
 * (ObjectOutputStream 은 스스로 객체를 ByteArray 로 변환할 수 없기 때문에)
 * 2. `oos.writeObject(member);`
 * ObjectOutputStream 을 이용해 member **객체**를 ByteArrayOutputStream에  **byteArray** 형태로 쓴다.
 * 3. `serializedMember = baos.toByteArray();`
 * ByteArrayOutputStream의 내부 저장공간에 저장된 바이트 배열을 반환.
 */
public class SerializationDemo {
  public static void main(String[] args) throws IOException {
    String base64Member = null;
    Member member1 = new Member("황수영", "sy1229@sooyoung.com", 25);

    // 직렬화
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
        oos.writeObject(member1);
        byte[] serializedMember = baos.toByteArray();
        System.out.println(Arrays.toString(serializedMember));
        // 바이트 배열로 생성된 직렬화 데이터를 base64로 변환
        base64Member = Base64.getEncoder().encodeToString(serializedMember);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    // 역직렬화
    byte[] serializedMember = Base64.getDecoder().decode(base64Member);
    try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedMember)) {
      try (ObjectInputStream ois = new ObjectInputStream(bais)) {
        // 역직렬화된 Member 객체를 읽어온다.
        Object objectMember = ois.readObject();
        Member member2 = (Member) objectMember;
        System.out.println(member2);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }
}
