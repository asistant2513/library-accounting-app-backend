package com.havrylenko.library.model.dto.restDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.config.json.filter.NullableFilter;
import com.havrylenko.library.model.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.Objects.isNull;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewDTO implements NullableFilter {
    private long id = -1;
    private String text;
    private short rating = -1;
    private String bookId;
    private String readerId;

    public ReviewDTO(final Review review) {
        this.id = review.getId();
        this.text = review.getText();
        this.rating = review.getRating();
        this.bookId = review.getBook().getId();
        this.readerId = review.getReader().getUserId();
    }

    @Override
    public boolean isEmpty() {
        return id < 0 && isNull(text) && isNull(bookId) && isNull(readerId) && rating < 0;
    }
}
