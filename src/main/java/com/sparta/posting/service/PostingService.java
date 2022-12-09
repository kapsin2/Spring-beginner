package com.sparta.posting.service;

import com.sparta.posting.dto.PostingRequestDto;
import com.sparta.posting.entity.Posting;
import com.sparta.posting.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service                   // 서비스 레이어, 내부에서 자바 로직을 처리함(해당 클래스를 루트 컨테이너에 빈(Bean) 객체로 생성해주는 어노테이션입니다.)
@RequiredArgsConstructor   //초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해 줍니다.
public class PostingService {
    private final PostingRepository postingRepository;   //@RequiredArgsConstructor 때문에 초기화 하지 않고도 사용가능

    @Transactional             //컨트롤러와 결합해주는 역할을 한다.
    public Posting createPosting(PostingRequestDto postingRequestDto) {
        Posting posting = new Posting(postingRequestDto);
        postingRepository.save(posting);
        return posting;
    }

    @Transactional(readOnly = true)      //읽기전용으로 하면 약간의 성능적인 이점을 얻을 수 있다.
    public List<Posting> getPostings() {return postingRepository.findAllByOrderByCreatedAtDesc();}

    @Transactional(readOnly = true)
    public Posting getPostingById(Long id) {
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        return posting;
    }

    @Transactional
    public Posting update(Long id,String password, PostingRequestDto postingRequestDto) {
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if (getPostingById(id).getPassword().equals(password)) {

            posting.update(postingRequestDto);
            return posting;
        } else {
            System.out.println("비밀번호가 일치하지 않습니다.");
        }
        return posting;
    }

    @Transactional
    public String deletePosting(Long id, String password) {
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if (getPostingById(id).getPassword().equals(password)) {
            postingRepository.deleteById(id);
            return "delete ok";
        } else {
            System.out.println("비밀번호가 일치하지 않습니다.");
            return "delete fail";
        }
    }
}
