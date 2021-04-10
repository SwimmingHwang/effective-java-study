package com.swimminghwang.effectivejava;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Base64;

public class SerializationDemo {
  public static void main(String[] args) throws IOException {
    String base64Member = null;
    Member member = new Member("황수영", "sy1229@sooyoung.com", 25);
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
        oos.writeObject(member);
        // serializedMember -> 직렬화된 member 객체
        byte[] serializedMember = baos.toByteArray();
        System.out.println(Arrays.toString(serializedMember));
        // 바이트 배열로 생성된 직렬화 데이터를 base64로 변환
        base64Member = Base64.getEncoder().encodeToString(serializedMember);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    byte[] serializedMember = Base64.getDecoder().decode(base64Member);
    try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedMember)) {
      try (ObjectInputStream ois = new ObjectInputStream(bais)) {
        // 역직렬화된 Member 객체를 읽어온다.
        Object objectMember = ois.readObject();
        Member member2 = (Member) objectMember;
        System.out.println(member);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }
}
