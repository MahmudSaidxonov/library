package uz.lib.library.service.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import uz.lib.library.dto.RatingDto;
import uz.lib.library.model.Rating;
import uz.lib.library.repository.RatingRepository;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class RatingMapper implements CommonMapper<RatingDto, Rating> {

    @Autowired
    protected RatingRepository repository;

    public Optional<Rating> toEditEntity(RatingDto dto){
        return repository.findById(dto.getId())
                .map(u -> {
                    if (dto.getDiploma_ball() != null) u.setDiploma_ball(dto.getDiploma_ball());
                    if (dto.getCertificates_ball() != null) u.setCertificates_ball(dto.getCertificates_ball());
                    if (dto.getText_ball() != null) u.setText_ball(dto.getText_ball());
                    u.setTotal(u.getDiploma_ball() + u.getCertificates_ball() + u.getText_ball());
                    return u;
                });
    }
}
