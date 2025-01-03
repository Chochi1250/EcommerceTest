package com.ChangaYa.TP_POOAv.serviceTest;

import com.ChangaYa.TP_POOAv.dto.ReviewDto;
import com.ChangaYa.TP_POOAv.model.Review;
import com.ChangaYa.TP_POOAv.repository.ReviewRepository;
import com.ChangaYa.TP_POOAv.service.impl.ReviewServiceImp;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImp reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveReview() {
        ReviewDto reviewDto = ReviewDto.builder()
                .id(new ObjectId())
                .nombreCliente("Cliente Test")
                .tituloServicio("Servicio Test")
                .comentario("Muy buen servicio")
                .calificacion(5)
                .fechaCalificacion(LocalDate.now())
                .build();

        Review review = new Review();
        review.setTituloServicio("Servicio Test");

        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        reviewService.saveReview(reviewDto);

        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testGetReviewsAsociadas() {
        String tituloServicio = "Servicio Test";
        
        Review review1 = new Review();
        review1.setTituloServicio(tituloServicio);
        review1.setComentario("Comentario 1");

        Review review2 = new Review();
        review2.setTituloServicio(tituloServicio);
        review2.setComentario("Comentario 2");

        when(reviewRepository.findByTituloServicio(tituloServicio)).thenReturn(Arrays.asList(review1, review2));

        List<ReviewDto> result = reviewService.getReviewsAsociadas(tituloServicio);

        assertEquals(2, result.size());
        assertEquals("Comentario 1", result.get(0).getComentario());
        assertEquals("Comentario 2", result.get(1).getComentario());
        verify(reviewRepository, times(1)).findByTituloServicio(tituloServicio);
    }

    @Test
    void testFindAllReviews() {
        Review review1 = new Review();
        review1.setTituloServicio("Servicio 1");
        Review review2 = new Review();
        review2.setTituloServicio("Servicio 2");

        when(reviewRepository.findAll()).thenReturn(Arrays.asList(review1, review2));

        List<ReviewDto> result = reviewService.findAllReviews();

        assertEquals(2, result.size());
        assertEquals("Servicio 1", result.get(0).getTituloServicio());
        assertEquals("Servicio 2", result.get(1).getTituloServicio());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testMapToReview() {
        ReviewDto reviewDto = ReviewDto.builder()
                .id(new ObjectId())
                .nombreCliente("Cliente Test")
                .tituloServicio("Servicio Test")
                .comentario("Buen servicio")
                .calificacion(4)
                .fechaCalificacion(LocalDate.now())
                .build();

        Review result = reviewService.mapToReview(reviewDto);

        assertEquals(reviewDto.getId(), result.getId());
        assertEquals(reviewDto.getNombreCliente(), result.getNombreCliente());
        assertEquals(reviewDto.getTituloServicio(), result.getTituloServicio());
        assertEquals(reviewDto.getComentario(), result.getComentario());
        assertEquals(reviewDto.getCalificacion(), result.getCalificacion());
        assertEquals(reviewDto.getFechaCalificacion(), result.getFechaCalificacion());
    }

    @Test
    void testMapToReviewDto() {
        Review review = Review.builder()
                .id(new ObjectId())
                .nombreCliente("Cliente Test")
                .tituloServicio("Servicio Test")
                .comentario("Excelente")
                .calificacion(5)
                .fechaCalificacion(LocalDate.now())
                .build();

        ReviewDto result = reviewService.mapToReviewDto(review);

        assertEquals(review.getId(), result.getId());
        assertEquals(review.getNombreCliente(), result.getNombreCliente());
        assertEquals(review.getTituloServicio(), result.getTituloServicio());
        assertEquals(review.getComentario(), result.getComentario());
        assertEquals(review.getCalificacion(), result.getCalificacion());
        assertEquals(review.getFechaCalificacion(), result.getFechaCalificacion());
    }
}

