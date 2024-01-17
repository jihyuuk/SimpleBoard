package com.simple.board.service;

import com.simple.board.domain.dto.board.BoardDTO;
import com.simple.board.domain.dto.post.PostDTO;
import com.simple.board.domain.dto.post.PostFormDTO;
import com.simple.board.domain.entity.Category;
import com.simple.board.domain.entity.Content;
import com.simple.board.domain.entity.Post;
import com.simple.board.domain.entity.User;
import com.simple.board.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final ContentService contentService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final PostImageService postImageService;

    public Post findById(Long id){
        //enabled=true인 게시글만
        return postRepository.findByIdAndEnabledTrue(id);
    }

    public PostFormDTO findPostFormDTO(Long id,String userName){
        return postRepository.findByIdAndUser_name(id,userName);
    }

    public Post getReferenceById(Long id){
        return postRepository.getReferenceById(id);
    }

    public boolean existById(Long id){
        return postRepository.existsById(id);
    }

    @Transactional
    public void update(PostFormDTO dto,String accessUser){
        //enabled=true인 게시글만
        Post post = findById(dto.getId());

        beforeUpdateCheck(accessUser, post);

        post.update(dto.getTitle(),dto.getContent());
    }

    @Transactional
    public Long save(PostFormDTO dto, String userName){
        Category category = categoryService.findById(dto.getCategoryId());
        User user = userService.findByName(userName);
        //이미 isAuthenticated()로 확인했지만
        //USER가 없을시 예외처리해야함, cateogry도 마찬가지
        
        //콘텐츠 생성
        Content content = new Content(dto.getContent());
        //이미지 추출
        List<String> images = extractImages(content);
        //썸네일 추출
        String thumbnailUrl = images.isEmpty()? null : images.get(0);
        
        //저장
        contentService.save(content);
        postImageService.saveList(content,images);

        Post post = new Post(category, user, dto.getTitle(),thumbnailUrl, content);
        return postRepository.save(post).getId();
    }

    @Transactional
    public void delete(Long id, String accessUser) {
        //논리적삭제 enabled = false로 논리적삭제
        Post post = findById(id);

        beforeUpdateCheck(accessUser, post);
        
        post.setEnabled(false);
    }

    public List<BoardDTO> getBoardDTOs(Category category){
        return postRepository.findAllBoardDTO(category);
    }

    @Transactional
    public PostDTO getPostDTO(Long id){
        Post post = postRepository.findById(id).get();
        post.viewUp();
        return new PostDTO(post);
        //return postRepository.findPostDTO(id);
    }


    private List<String> extractImages(Content content){
        Document doc = Jsoup.parse(content.getContents());
        Elements imgTags = doc.select("img");

        List<String> images = new ArrayList<>();
        
        //이미지 없으면
        if(imgTags.isEmpty()){
            return images;
        }
        
        //이미지 존재시
        for (Element imgTag : imgTags) {
            //임시 이미지 url
            String tempUrl = imgTag.attr("src");
            //파일명
            String fileName = tempUrl.substring("/image/temp/".length());

            //기존 임시폴더 경로를 진짜 저장소 경로로 변경
            String newUrl = tempUrl.replace("/temp/", "/images/");
            imgTag.attr("src",newUrl);
            
            //이미지 리스트에 추가
            images.add(newUrl);

            //파일 진짜 저장소로 move하기
            Path sourcePath = Paths.get("storage/upload/temp/"+fileName).toAbsolutePath();
            Path destinationPath = Paths.get("storage/upload/images/"+fileName).toAbsolutePath();
            try {
                Files.move(sourcePath,destinationPath, StandardCopyOption.REPLACE_EXISTING);
                log.info("파일 이동 성공");
            }catch (Exception e){
                log.info("이미지 move 오류 발생");
            }
        }

        content.setContents(doc.html());

        return images;
    }


    //작성자인지 확인
    private boolean isAuthor(String accessUser, Post post) {
        return post.getUser().getName().equals(accessUser);
    }

    private void beforeUpdateCheck(String accessUser, Post post) {
        //이미 삭제되었거나 존재x -> 404에러
        if(post == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        //작성자만이 업데이트 가능 -> 403에러
        if(!isAuthor(accessUser, post)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
