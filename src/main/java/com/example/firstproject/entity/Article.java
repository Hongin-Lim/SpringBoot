package com.example.firstproject.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity  //DB가 해당 객체를 인식 가능! (해당 클래스로 테이블을 만든다)
@AllArgsConstructor
@NoArgsConstructor   // 디폴트 생성자 추가
@ToString
@Getter // 롬복으로 부터 게터 추가

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // strategy = GenerationType.IDENTITY -> DB가 id를 자동으로 등록해 준다(이미 id 1값이 등록되어 있을때(수동) 추후 값을 넣으면 id 1번 부터 들어가서 충돌, 이를 해결 = 중복되지 않게 2번부터)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;

}