package com.example.kakao.episode;

import com.example.kakao._entity.EpisodePhoto;
import com.example.kakao._entity.LikeEpisode;
import com.example.kakao._entity.RecentWebtoon;
import com.example.kakao.comment.Comment;
import com.example.kakao.webtoon.Webtoon;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "episode_tb", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"webtoon_id", "detailTitle"})
})
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Webtoon webtoon;

    @OneToMany(mappedBy = "episode", fetch = FetchType.LAZY)
    private List<EpisodePhoto> episodePhotoList = new ArrayList<>();

    @OneToMany(mappedBy = "episode", fetch = FetchType.LAZY)
    @OrderBy("createdAt DESC") // 코멘트 최근 순서대로 정렬
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "episode", fetch = FetchType.LAZY)
    private List<LikeEpisode> likeEpisodeList = new ArrayList<>();

    @OneToMany(mappedBy = "episode", fetch = FetchType.LAZY)
    private List<RecentWebtoon> recentWebtoonList = new ArrayList<>();

    @Column(length = 45, nullable = false)
    private String detailTitle;

    @Column(nullable = false)
    private String thumbnail;

    @ColumnDefault("'작가의말 없음'")
    private String authorText;

    @ColumnDefault("0") // 작동안하는듯
    private Integer cookieCost;

    @ColumnDefault("0") // 작동안하는듯
    private Double starScore; // 별점 계산 분자값

    @ColumnDefault("0") // 작동안하는듯
    private Double starCount; // 별점 계산 분모값

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;


    @Builder
    public Episode(Integer id, Webtoon webtoon, List<EpisodePhoto> episodePhotoList, List<Comment> commentList,
                   List<LikeEpisode> likeEpisodeList, String detailTitle, String thumbnail, String authorText,
                   Integer cookieCost, Double starScore, Double starCount, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.webtoon = webtoon;
        this.episodePhotoList = episodePhotoList;
        this.commentList = commentList;
        this.likeEpisodeList = likeEpisodeList;
        this.detailTitle = detailTitle;
        this.thumbnail = thumbnail;
        this.authorText = authorText;
        this.cookieCost = cookieCost;
        this.starScore = starScore;
        this.starCount = starCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}
