package uz.lib.library.service.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import uz.lib.library.dto.QuizDto;
import uz.lib.library.dto.RatingDto;
import uz.lib.library.model.Quiz;
import uz.lib.library.model.Rating;
import uz.lib.library.repository.QuizRepository;
import uz.lib.library.repository.RatingRepository;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class QuizMapper implements CommonMapper<QuizDto, Quiz> {
    @Autowired
    protected QuizRepository repository;

    public Optional<Quiz> toEditEntity(QuizDto dto){
        return repository.findById(dto.getId())
                .map(q -> {
                    if (dto.getTitle() != null) q.setTitle(dto.getTitle());
                    if (dto.getDescription() != null) q.setDescription(dto.getDescription());
                    if (dto.getNumberOfQuestions() != null) q.setNumberOfQuestions(dto.getNumberOfQuestions());
                    if (dto.getMaxMarks() != null) q.setMaxMarks(dto.getMaxMarks());
                  return q;
                });
    }
}
