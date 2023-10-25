package com.example.kakao.episode;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.example.kakao.comment.Comment;
import com.example.kakao.entity.EpisodePhoto;
import com.example.kakao.entity.ReComment;
import com.example.kakao.user.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class EpisodeResponse {

    // 웹툰 에피소드 1편 보기 DTO
    @Getter
    @Setter
    @ToString
    public static class FindByIdDTO {
        private Integer episodeId;
        private String detailTitle;
        private Double starScore;
        private Double starCount;
        private Timestamp createdAt;
        private String authorText;
        private String authorName;
        private Integer webtoonId;
        private String webtoonName;
        private Integer likeEpisodeCount;
        private List<CommentDTO> commentList;
        private List<PhotoDTO> PhotoList;

        public FindByIdDTO(Episode episode) {
            this.episodeId = episode.getId();
            this.detailTitle = episode.getDetailTitle();
            this.starScore = episode.getStarScore();

            this.starCount = episode.getStarCount();
            this.createdAt = episode.getCreatedAt();
            this.authorText = episode.getAuthorText();


            this.webtoonId = episode.getWebtoon().getId();
            this.webtoonName = episode.getWebtoon().getTitle();

            this.authorName = episode.getWebtoon().getWebtoonAuthorList().stream()
                    .map(webtoonAuthor -> webtoonAuthor.getAuthor().getAuthorNickname())
                    .collect(Collectors.joining(" / "));

            // this.likeEpisodeCount = episode.getLikeEpisodeList().size(); // 싫어요일수도 있음
            this.likeEpisodeCount = episode.getLikeEpisodeList().stream()
                    .map( t -> (t.getIsLike() == true) ? 1 : -1 )
                    .reduce(0, (a, b) -> a + b);

            List<Integer> authorUserIdList = episode.getWebtoon().getWebtoonAuthorList().stream()
                    .map(webtoonAuthor -> webtoonAuthor.getAuthor().getUser().getId())
                    .collect(Collectors.toList());
            this.commentList = episode.getCommentList().stream()
                    .map(t -> new CommentDTO(t, authorUserIdList)).collect(Collectors.toList());

            this.PhotoList = episode.getEpisodePhotoList().stream()
                    .map(t -> new PhotoDTO(t)).collect(Collectors.toList());
        }

        @Getter
        @Setter
        @ToString
        class CommentDTO {
            private Integer id;
            // private UserDTO user;
            private Integer episodeId;
            private Integer likeCommentCount;
            private Boolean isDelete;
            private String text;
            private Boolean isAuthor = false;
            private Timestamp createdAt;
            private Integer userId;
            private String userEmail;
            private String userUsername;
            private List<ReCommentDTO> reCommentList;

            CommentDTO(Comment comment, List<Integer> authorUserIdList) {
                this.id = comment.getId();
                this.isDelete = comment.getIsDelete();
                this.text = comment.getText();
                this.createdAt = comment.getCreatedAt();
                this.episodeId = comment.getEpisode().getId();

                // this.user = new UserDTO(comment.getUser());
                this.userId = comment.getUser().getId();
                this.userEmail = comment.getUser().getEmail();
                this.userUsername = comment.getUser().getUsername();

                this.reCommentList = comment.getReCommentList().stream()
                        .map(t -> new ReCommentDTO(t)).collect(Collectors.toList());

                this.isAuthor = authorUserIdList.stream()
                        .anyMatch(t -> t == comment.getUser().getId());
                // for (Integer authorUserId : authorUserIdList ) {
                //     if(comment.getUser().getId()==authorUserId){
                //         this.isAuthor = true;
                //     }
                // }

                this.likeCommentCount = comment.getLikeCommentList().stream()
                        .map( t -> (t.getIsLike() == true) ? 1 : -1 )
                        .reduce(0, (a, b) -> a + b);

            }

            @Getter
            @Setter
            @ToString
            class ReCommentDTO {
                private Integer id;
                private Integer commentId;
                private Boolean isDelete;
                private String text;
                private Timestamp createdAt;
                private Integer likeReCommentCount;
                private Integer userId;
                private String userEmail;
                private String userUsername;

                public ReCommentDTO(ReComment reComment) {
                    this.id = reComment.getId();
                    this.isDelete = reComment.getIsDelete();
                    this.text = reComment.getText();
                    this.createdAt = reComment.getCreatedAt();
                    this.commentId = reComment.getComment().getId();

                    this.userId = reComment.getUser().getId();
                    this.userEmail = reComment.getUser().getEmail();
                    this.userUsername = reComment.getUser().getUsername();
                    
                    this.likeReCommentCount = reComment.getLikeReCommentList().stream()
                            .map( t -> (t.getIsLike() == true) ? 1 : -1 )
                            .reduce(0, (a, b) -> a + b);
                }
            }

            // @Getter
            // @Setter
            // @ToString
            // class UserDTO {
            //     private Integer id;
            //     private String email;
            //     private String username;

            //     UserDTO(User user) {
            //         this.id = user.getId();
            //         this.email = user.getEmail();
            //         this.username = user.getUsername();
            //     }
            // }
        }

        @Getter
        @Setter
        @ToString
        class PhotoDTO {
            private Integer id;
            private String photoURL;

            PhotoDTO(EpisodePhoto episodePhoto) {
                this.id = episodePhoto.getId();
                this.photoURL = episodePhoto.getPhotoURL();
            }
        }

    }




    // @ToString
    // @Getter
    // @Setter
    // public static class FindAllDTO {
    //     private Integer id;
    //     private String title;
    //     private String author;
    //     private Double starCount;
    //     private String image;

    //     public FindAllDTO(Episode episode) {
    //         this.id = webtoon.getId();
    //         this.title = webtoon.getTitle();
    //         this.author = webtoon.getAuthor();
    //         this.starCount = webtoon.getStarCount();
    //         this.image = webtoon.getImage();
    //     }
    // }


    // // 상품조회 + 옵션조회
    // @Getter
    // @Setter
    // public static class FindByIdV1DTO {
    //     private Integer productId;
    //     private String productName;
    //     private String productImage;
    //     private Integer productPrice;
    //     private Integer productStartCount;
    //     private List<OptionDTO> options;

    //     public FindByIdV1DTO(Product product, List<Option> options) {
    //         this.productId = product.getId();
    //         this.productName = product.getProductName();
    //         this.productImage = product.getImage();
    //         this.productPrice = product.getPrice();
    //         this.productStartCount = 5;
    //         this.options = options.stream()
    //                 .map(o -> new OptionDTO(o))
    //                 .collect(Collectors.toList());
    //     }

    //     @Getter
    //     @Setter
    //     class OptionDTO {
    //         private Integer optionId;
    //         private String optionName;
    //         private Integer optionPrice;

    //         OptionDTO(Option option) {
    //             this.optionId = option.getId();
    //             this.optionName = option.getOptionName();
    //             this.optionPrice = option.getPrice();
    //         }
    //     }
    // }

    // // 양방향 매핑
    // @Getter
    // @Setter
    // public static class FindByIdV2DTO {
    //     private Integer productId;
    //     private String productName;
    //     private String productImage;
    //     private Integer productPrice;
    //     private Integer productStartCount;
    //     private List<OptionDTO> options;

    //     public FindByIdV2DTO(Product product) {
    //         this.productId = product.getId();
    //         this.productName = product.getProductName();
    //         this.productImage = product.getImage();
    //         this.productPrice = product.getPrice();
    //         this.productStartCount = 5;
    //         System.out.println("이제 Lazy Loading 한다 =================");
    //         this.options = product.getOptions().stream()
    //                 .map(o -> new OptionDTO(o))
    //                 .collect(Collectors.toList());
    //     }

    //     @Getter
    //     @Setter
    //     class OptionDTO {
    //         private Integer optionId;
    //         private String optionName;
    //         private Integer optionPrice;

    //         OptionDTO(Option option) {
    //             System.out.println("이제 Lazy Loading 시작됨 =========");
    //             this.optionId = option.getId();
    //             this.optionName = option.getOptionName();
    //             this.optionPrice = option.getPrice();
    //         }
    //     }
    // }

    // // 옵션만 조회
    // @Getter
    // @Setter
    // public static class FindByIdV3DTO {
    //     private Integer productId;
    //     private String productName;
    //     private String productImage;
    //     private Integer productPrice;
    //     private Integer productStartCount;
    //     private List<OptionDTO> options;

    //     public FindByIdV3DTO(List<Option> options) {
    //         System.out.println("이제 Lazy 시작한다???????????????????????");
    //         this.productId = options.get(0).getProduct().getId();
    //         this.productName = options.get(0).getProduct().getProductName();
    //         this.productImage = options.get(0).getProduct().getImage();
    //         this.productPrice = options.get(0).getProduct().getPrice();
    //         this.productStartCount = 5;
    //         this.options = options.stream()
    //                 .map(o -> new OptionDTO(o))
    //                 .collect(Collectors.toList());
    //     }

    //     @Getter
    //     @Setter
    //     class OptionDTO {
    //         private Integer optionId;
    //         private String optionName;
    //         private Integer optionPrice;

    //         OptionDTO(Option option) {
    //             this.optionId = option.getId();
    //             this.optionName = option.getOptionName();
    //             this.optionPrice = option.getPrice();
    //         }
    //     }
    // }
}