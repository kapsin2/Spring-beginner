package com.sparta.posting.service;

import com.sparta.posting.dto.DeleteResponseDto;
import com.sparta.posting.dto.PostingRequestDto;
import com.sparta.posting.entity.Posting;
import com.sparta.posting.entity.User;
import com.sparta.posting.jwt.JwtUtil;
import com.sparta.posting.repository.PostingRepository;
import com.sparta.posting.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service                   // 서비스 레이어, 내부에서 자바 로직을 처리함(해당 클래스를 루트 컨테이너에 빈(Bean) 객체로 생성해주는 어노테이션입니다.)
@RequiredArgsConstructor   //초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해 줍니다.
public class PostingService {
    private final PostingRepository postingRepository;   //@RequiredArgsConstructor 때문에 초기화 하지 않고도 사용가능
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional             //컨트롤러와 결합해주는 역할을 한다.
    public Posting createPosting(PostingRequestDto postingRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims = null;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInformToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
        }
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        Posting posting = new Posting(postingRequestDto,user);
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
    public Posting update(Long id, PostingRequestDto postingRequestDto, HttpServletRequest request) {
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );
        String token = jwtUtil.resolveToken(request);
        Claims claims = null;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInformToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
        }
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        posting = postingRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new NullPointerException("해당 게시글은 수정할수 없습니다.")
        );
        posting.update(postingRequestDto);
        return posting;
    }

    @Transactional
    public DeleteResponseDto deletePosting(Long id, HttpServletRequest request) {
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );

        String token = jwtUtil.resolveToken(request);
        Claims claims = null;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInformToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
        }
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        posting = postingRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new NullPointerException("해당 게시글은 삭제할수 없습니다.")
        );
        postingRepository.deleteById(id);
        DeleteResponseDto deleteResponseDto = new DeleteResponseDto();
        return deleteResponseDto;
    }
}
