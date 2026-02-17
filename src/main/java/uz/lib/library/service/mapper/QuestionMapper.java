package uz.lib.library.service.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import uz.lib.library.dto.QuestionDto;
import uz.lib.library.dto.QuizDto;
import uz.lib.library.model.Question;
import uz.lib.library.model.Quiz;
import uz.lib.library.repository.QuestionRepository;
import uz.lib.library.repository.QuizRepository;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class QuestionMapper implements CommonMapper<QuestionDto, Question>{
    @Autowired
    protected QuestionRepository repository;
    @Autowired
    protected QuizMapper mapper;

    public Optional<Question> toEditEntity(QuestionDto dto){
        return repository.findById(dto.getId())
                .map(q -> {
                    if (dto.getContent() != null) q.setContent(dto.getContent());
                    if (dto.getOption1() != null) q.setOption1(dto.getOption1());
                    if (dto.getOption2() != null) q.setOption2(dto.getOption2());
                    if (dto.getOption3() != null) q.setOption3(dto.getOption3());
                    if (dto.getOption4() != null) q.setOption4(dto.getOption4());
                    if (dto.getAnswer() != null) q.setAnswer(dto.getAnswer());
                    if (dto.getQuiz() != null) q.setQuiz(mapper.toEntity(dto.getQuiz()));

                    return q;
                });
    }
}
