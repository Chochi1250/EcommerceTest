package com.ChangaYa.TP_POOAv.service;

import java.util.List;

import com.ChangaYa.TP_POOAv.dto.ReviewDto;


public interface ReviewService {
    List<ReviewDto> findAllReviews();
    void saveReview(ReviewDto reviewDto);
    List<ReviewDto> getReviewsAsociadas(String tituloServicio);
    

}
