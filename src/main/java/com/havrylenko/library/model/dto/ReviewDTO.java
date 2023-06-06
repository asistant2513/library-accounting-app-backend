package com.havrylenko.library.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.model.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewDTO {
    private long id;
    private String text;
    private short rating;
    private String bookId;
    private String readerId;

    public ReviewDTO(final Review review) {
        this.id = review.getId();
        this.text = review.getText();
        this.rating = review.getRating();
        this.bookId = review.getBook().getId();
        this.readerId = review.getReader().getUserId();
    }
}
