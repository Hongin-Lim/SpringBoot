package com.example.firstproject.controller;
import com.example.firstproject.Repository.ArticleRepository;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j // 로깅을 위한 어노테이션
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticles(ArticleForm form){
//        System.out.println(form.toString()); -> 로깅으로 대체
        log.info(form.toString());

        // 1. Dto를 변환! -> Entity!
        Article article = form.toEntity();
//        System.out.println(article.toString());
        log.info(article.toString());
        // 2. Repository에게 Entity를 DB안에 저장하게 함!
        Article saved = articleRepository.save(article);
//        System.out.println(saved.toString());
        log.info(saved.toString());
        // 리다이렉트 적용: 생성 후, 브라우저가 해당 URL로 재요청
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}") // 해당 URL요청을 처리 선언
    public String show(@PathVariable Long id, Model model) { // URL에서 id를 변수로 가져옴
        log.info("id = " + id);

        // 1: id로 데이터를 가져옴!
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2: 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articleEntity);

        // 3: 보여줄 페이지를 설정!
        return "articles/show";
        //Optional<Article> articleEntity = articleRepository.findById(id);
    }
    @GetMapping("/articles")
    public String index(Model model){

        // 1: 모든 Article을 가져온다
        List<Article> articleEntityList = articleRepository.findAll();

        // 2: 가져온 Article 묶음을 뷰로 전달한다.
        model.addAttribute("articleList", articleEntityList);

        // 3: 뷰 페이지를 설정
        return "articles/index"; //articles/index.mustache
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        // 수정할 데이터를 가져오기!
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터를 등록!
        model.addAttribute("article", articleEntity);

        // 뷰 페이지 설정
        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());

        // 1 : DTO를 엔티티로 변환한다!
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2 : 엔티티를 DB로 저장한다!

        // 2-1 : DB에서 기존 데이터를 가져온다!
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2 : 기존 데이터가 있다면! 값을 갱신한다!
        if(target != null){
            articleRepository.save(articleEntity); // 엔티티가 DB로 갱신!
        }

        // 3 : 수정 결과 페이지로 리다이렉트 한다!

        return "redirect:/articles/" + articleEntity.getId();
    }
}