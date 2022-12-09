package com.sparta.posting.controller;

import com.sparta.posting.dto.PostingRequestDto;
import com.sparta.posting.entity.Posting;
import com.sparta.posting.service.PostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
                    //view를 반환할때는 @Controller 사용해야함
@RestController   //@Controller에 @ResponseBody가 추가된것  주용도는 Json형태로(@ResponseBody를 감싼형태로) 객체 데이터를  반환하는것
@RequiredArgsConstructor
public class PostingController {
    private final PostingService postingService;

    @GetMapping("/")       //핸들러가 어떤 방식(Get,Post...)으로 컨트롤러에 요청을 하느냐에따라 구분해서 매서드를 실행시키기 위해 Mapping을 사용한다.
    public ModelAndView home() {
        return new ModelAndView();
    }

    @PostMapping("/api/postings")
    public Posting createPosting(@RequestBody PostingRequestDto postingRequestDto) {return postingService.createPosting(postingRequestDto);}

    @GetMapping("/api/postings")
    public List<Posting> getPostings() {
        return postingService.getPostings();
    }

    @GetMapping("/api/postings/{id}")
    public Posting getPostingsById(@PathVariable Long id) {
        return postingService.getPostingById(id);
    }

    @PutMapping("/api/postings/{id},{password}")
    public Posting updatePosting(@PathVariable Long id,@PathVariable String password, @RequestBody PostingRequestDto postingRequestDto) {
        return postingService.update(id,password,postingRequestDto);
    }

    @DeleteMapping("/api/postings/{id},{password}")
    public String deletePosting(@PathVariable Long id,@PathVariable String password) {
        return postingService.deletePosting(id,password);
    }
}
