package com.example.firstproject.controller;

import com.example.firstproject.Repository.ArticleRepository;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController // RestAPI용 컨트롤러! 데이터(JSON)를 반환!
public class ArticleApiController {

    @Autowired // DI
    private ArticleRepository articleRepository;
    // GET
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article index(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }
    // POST

    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto){
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){

        // 1. 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        // 2. 대상 엔티티를 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못 된 요청 처리(대상이 없거나, id가 다른경우)
        if (target == null || id != article.getId()) {
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 4. 업데이트 및 정상 응답(200)
        target.patch(article); // 추가된 부분 특정 칼럼에 값을 넣지 않고 보냈을때 null이 되지 않기 위한 즉, 일부만 수정할때(ex. title은 냅두고 content만 수정할때)
       Article updated = articleRepository.save(target); // 추가된 부분 article -> target
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if (target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 대상 삭제
        articleRepository.delete(target);
        // 데이터 반환
        return ResponseEntity.status(HttpStatus.OK).body(null);
       // 또는 return ResponseEntity.status(HttpStatus.OK).bulid();
    }



}
