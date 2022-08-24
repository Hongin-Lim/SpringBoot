package com.example.firstproject.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity  //DB가 해당 객체를 인식 가능!
@AllArgsConstructor
@NoArgsConstructor   // 디폴트 생성자 추가
@ToString
@Getter // 롬복으로 부터 게터 추가

public class Article {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String title;
    @Column
    private String content;

}