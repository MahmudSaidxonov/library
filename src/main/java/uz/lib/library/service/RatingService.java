package uz.lib.library.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import uz.lib.library.dto.RatingDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.projections.BookProjection;

import java.io.IOException;
import java.util.List;

public interface RatingService {
    ResponseDto<RatingDto> addRating(String email, String password);
    ResponseDto<RatingDto> updateRating(RatingDto ratingDto);
    ResponseDto<RatingDto> getRatingById(Integer id);
    ResponseEntity<ByteArrayResource> getRatingsExcel() throws IOException;
    ResponseDto<List<RatingDto>> getAllRatings();

}
