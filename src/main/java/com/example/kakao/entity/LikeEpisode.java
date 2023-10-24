package com.example.kakao.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.example.kakao.episode.Episode;
import com.example.kakao.user.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="like_episode_tb")
public class LikeEpisode{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Episode episode;

    private Boolean isStar; // 별점 주기 취소안됨

    private Boolean isLike; // true좋아요 / false 싫어요

    private Timestamp createdAt;

    //두개 공통 중복이면 안되게 유니크 제약조건 필요
    
}
