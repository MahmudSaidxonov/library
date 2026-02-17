package uz.lib.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.lib.library.dto.RatingDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.model.Rating;
import uz.lib.library.model.User;
import uz.lib.library.repository.RatingRepository;
import uz.lib.library.repository.UserRepository;
import uz.lib.library.service.RatingService;
import uz.lib.library.service.additional.AppStatusCodes;
import uz.lib.library.service.additional.AppStatusMessages;
import uz.lib.library.service.mapper.RatingMapper;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.lib.library.service.additional.AppStatusCodes.*;
import static uz.lib.library.service.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingMapper ratingMapper;
    private final RatingRepository repository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseDto<RatingDto> addRating(String email, String password) {

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseDto.<RatingDto>builder()
                    .success(false)
                    .message(AppStatusMessages.NOT_FOUND)
                    .code(AppStatusCodes.NOT_FOUND_ERROR_CODE)
                    .build();
        }
        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            return ResponseDto.<RatingDto>builder()
                    .message("Password is not correct")
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        Optional<Rating> r = repository.findByUserId(user.get());

        if (r.isPresent()) {
            return ResponseDto.<RatingDto>builder()
                    .success(false)
                    .code(DATABASE_ERROR_CODE)
                    .message(email + " already exists in the registry")
                    .build();
        }

//        user = Optional.ofNullable(loadUserWithRoles(user.get()));

        Rating rating = new Rating();
        rating.setUserId(user.get());
        rating.setDiploma_ball(0);
        rating.setCertificates_ball(0);
        rating.setText_ball(0);
        rating.setTotal(0);

        repository.save(rating);

        return ResponseDto.<RatingDto>builder()
                .code(0)
                .success(true)
                .message("OK")
                .data(ratingMapper.toDto(rating))
                .build();
    }

    @Override
    public ResponseDto<RatingDto> updateRating(RatingDto ratingDto) {
        try {
            Optional<Rating> rating = repository.findById(ratingDto.getId());
            if (rating.isEmpty()) {
                return ResponseDto.<RatingDto>builder()
                        .success(false)
                        .message(AppStatusMessages.NOT_FOUND)
                        .code(AppStatusCodes.NOT_FOUND_ERROR_CODE)
                        .build();
            }

            rating = ratingMapper.toEditEntity(ratingDto);
//        rating.get().setUserId(loadUserWithRoles(rating.get().getUserId()));
            repository.save(rating.get());

            return ResponseDto.<RatingDto>builder()
                    .code(OK_CODE)
                    .success(true)
                    .message(AppStatusMessages.OK)
                    .data(ratingMapper.toDto(rating.get()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<RatingDto>builder()
                    .success(false)
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR+ ": " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<RatingDto> getRatingById(Integer id) {
        try {
            Optional<Rating> rating = repository.findById(id);
            if (rating.isEmpty()) {
                return ResponseDto.<RatingDto>builder()
                        .success(false)
                        .code(-1)
                        .message("User with this ID not found")
                        .data(null)
                        .build();
            }

            return ResponseDto.<RatingDto>builder()
                    .success(true)
                    .code(0)
                    .message("OK")
                    .data(ratingMapper.toDto(rating.get()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<RatingDto>builder()
                    .success(false)
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR+ ": " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseEntity<ByteArrayResource> getRatingsExcel() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=RatingTemplate.xlsx");
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Rating");
        List<Rating> ratings = repository.getRating();
        File f = new File(FilesServiceImpl.filePath("report", ".xlsx"));

        Row r = sheet.createRow(0);
        sheet.autoSizeColumn(0);
        r.createCell(0).setCellValue("name");
        r.createCell(1).setCellValue("diploma_ball");
        r.createCell(2).setCellValue("certificates_ball");
        r.createCell(3).setCellValue("text_ball");
        r.createCell(4).setCellValue("total");
        r.createCell(5).setCellValue("data");

        if (!f.exists() && f.createNewFile()){
            for (int i = 0, j = 0; i < ratings.size(); i++) {
                Row row = sheet.createRow(i+1);
                sheet.autoSizeColumn(i+1);
                row.createCell(j++).setCellValue(ratings.get(i).getUserId().getFirstName());
                row.createCell(j++).setCellValue(ratings.get(i).getDiploma_ball());
                row.createCell(j++).setCellValue(ratings.get(i).getCertificates_ball());
                row.createCell(j++).setCellValue(ratings.get(i).getText_ball());
//                row.createCell(j++).setCellValue(ratings.get(i).getDiploma_ball() + ratings.get(i).getCertificates_ball() + ratings.get(i).getText_ball());
                row.createCell(j++).setCellValue(ratings.get(i).getTotal());
                row.createCell(j).setCellValue(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
                j = 0;
            }
            book.write(new FileOutputStream(f));
            book.write(stream);
            book.close();
        }
        return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                header, HttpStatus.CREATED);
    }

    @Override
    public ResponseDto<List<RatingDto>> getAllRatings() {
        try {
            return ResponseDto.<List<RatingDto>>builder()
                    .success(true)
                    .code(OK_CODE)
                    .message(OK)
                    .data(repository.getRating().stream()
                            .map(ratingMapper::toDto)
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<RatingDto>>builder()
                    .code(1)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }


//    public User loadUserWithRoles(User u) throws UsernameNotFoundException {
//        Optional<List<Authorities>> list = authoritiesRepository.getAuthoritiesByUserId(u.getId());
//        if (list.isEmpty()) throw new UsernameNotFoundException("user roles is not found");
//        u.setRoles(list.get());
//        return u;
//    }
}
