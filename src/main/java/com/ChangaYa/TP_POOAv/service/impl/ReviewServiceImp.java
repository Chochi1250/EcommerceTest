package com.ChangaYa.TP_POOAv.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.ChangaYa.TP_POOAv.dto.ReviewDto;
import com.ChangaYa.TP_POOAv.model.Review;

import com.ChangaYa.TP_POOAv.repository.ReviewRepository;
import com.ChangaYa.TP_POOAv.service.ReviewService;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImp implements ReviewService {
     private ReviewRepository reviewRepository;

    
    public ReviewServiceImp(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    

    @Override
    public void saveReview(ReviewDto reviewDto) {
        Review review = mapToReview(reviewDto);
        reviewRepository.save(review);
    }
    public List<ReviewDto> getReviewsAsociadas(String tituloServicio){
        List<Review> reviews = reviewRepository.findByTituloServicio(tituloServicio);
        return reviews.stream().map((review) -> mapToReviewDto(review)).collect(Collectors.toList());
    }

	@Override
	public List<ReviewDto> findAllReviews() {
		List<Review> reviews = reviewRepository.findAll();  
        return reviews.stream().map((review) -> mapToReviewDto(review)).collect(Collectors.toList());
	}
    public Review mapToReview(ReviewDto reviewDto){
        Review review = Review.builder()
        .id(reviewDto.getId())
        .nombreCliente(reviewDto.getNombreCliente())
        .tituloServicio(reviewDto.getTituloServicio())
        .comentario(reviewDto.getComentario())
        .calificacion(reviewDto.getCalificacion())
        .fechaCalificacion(reviewDto.getFechaCalificacion())
        .build();
        return review;
    }
  
    public ReviewDto mapToReviewDto(Review review){
        ReviewDto reviewDto = ReviewDto.builder()
        .id(review.getId())
        .nombreCliente(review.getNombreCliente())
        .tituloServicio(review.getTituloServicio())
        .comentario(review.getComentario())
        .calificacion(review.getCalificacion())
        .fechaCalificacion(review.getFechaCalificacion())
        .build();
        return reviewDto;
    }
}
