package com.ssafy.gogosing.controller;

import com.ssafy.gogosing.dto.music.request.MusicLikeRequestDto;
import com.ssafy.gogosing.dto.music.response.MusicDetailResponseDto;
import com.ssafy.gogosing.dto.music.response.MusicResponseDto;
import com.ssafy.gogosing.service.MusicService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Getter
@RequestMapping("/api/music")
public class MusicController {

    private final MusicService musicService;

    @ApiOperation(value = "유저 노래 좋아요")
    @PostMapping("/like")
    public ResponseEntity<?> likeMusic(@Valid @RequestBody MusicLikeRequestDto musicLikeRequestDto,
                                       @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        musicService.likeMusic(musicLikeRequestDto, userDetails);
        return ResponseEntity.ok().body("");
    }

    @ApiOperation(value = "유저 노래 좋아요 취소")
    @DeleteMapping("/like")
    public ResponseEntity<?> unlikeMusic(@Valid @RequestBody MusicLikeRequestDto musicLikeRequestDto,
                                       @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        musicService.unlikeMusic(musicLikeRequestDto, userDetails);
        return ResponseEntity.ok().body("");
    }

    @ApiOperation(value = "유저 노래 좋아요 리스트")
    @GetMapping("/like")
    public ResponseEntity<?> likeMusicList(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        musicService.likeMusicList(userDetails);
        return ResponseEntity.ok().body(musicService.likeMusicList(userDetails));
    }

    @ApiOperation(value = "유저 노래 좋아요 기반 추천 리스트")
    @GetMapping("/like/list")
    public ResponseEntity<?> recommendListMusicOnLike(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        return ResponseEntity.ok().body(musicService.recommendListMusicOnLike(userDetails));
    }

    @ApiOperation(value = "노래 상세정보")
    @GetMapping("/detail/{musicId}")
    public ResponseEntity<?> detail(@PathVariable("musicId") Long musicId) throws Exception {
        return ResponseEntity.ok().body(musicService.detail(musicId));
    }

    @ApiOperation(value = "노래 인기 차트")
    @GetMapping("/chart")
    public ResponseEntity<?> popularChart() throws Exception {
        return ResponseEntity.ok().body(musicService.popularChart());
    }

}
