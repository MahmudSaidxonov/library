package uz.lib.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.model.FileType;
import uz.lib.library.model.Files;
import uz.lib.library.repository.FileResolutionRepository;
import uz.lib.library.repository.FilesRepository;
import uz.lib.library.service.FilesService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

import static uz.lib.library.service.additional.AppStatusCodes.*;
import static uz.lib.library.service.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class FilesServiceImpl implements FilesService {

    private final FilesRepository userFilesRepository;
    private final FileResolutionRepository fileResolutionRepository;
    public static synchronized String filePath(String folder, String ext) {
        File file = new File("userFiles" + "/" + folder);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = UUID.randomUUID().toString();

        return file.getPath() + "/" + fileName + ext;
    }


    @Override
    public ResponseDto<Integer> fileUpload(Integer userId, String type, MultipartFile file) {

        Optional<FileType> fileType = fileResolutionRepository.findByType(type);

        if (fileType.isEmpty()) {
            return ResponseDto.<Integer>builder()
                    .data(null)
                    .message(NOT_FOUND)
                    .success(false)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();
        }

        Files entity = new Files();

        entity.setUserId(userId);
        entity.setFileType(fileType.get());
        entity.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));

        String filePath = null;

        if (type.equalsIgnoreCase("PASSPORT")) {
            filePath = savePassport(file, userId, entity.getExt());
        }
        else if (type.equalsIgnoreCase("DIPLOMA")) {
            filePath = saveDiploma(file, userId, entity.getExt());
        }
        else if (type.equalsIgnoreCase("TEXT")) {
            filePath = saveText(file, userId, entity.getExt());
        }

        entity.setPath(filePath);

        try {
            Files savedFile = userFilesRepository.save(entity);

            return ResponseDto.<Integer>builder()
                    .data(savedFile.getId())
                    .message(OK)
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<Integer>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }

    }

    @Override
    public ResponseDto<List<byte[]>> getFileById(Integer userId, String type) throws IOException {
        if (userId == null || type == null) {
            return ResponseDto.<List<byte[]>>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        Optional<FileType> fileType = fileResolutionRepository.findByType(type);

        if (fileType.isEmpty()) {
            return ResponseDto.<List<byte[]>>builder()
                    .message(NOT_FOUND)
                    .success(false)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();
        }

        Optional<List<Files>> optional = userFilesRepository.findAllByUserId(userId);

        if (optional.isEmpty()) {
            return ResponseDto.<List<byte[]>>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();
        }

        List<String> imagePath = new ArrayList<>();

        optional.get().forEach(i -> imagePath.add(i.getPath()));

        List<byte[]> file = new ArrayList<>();
        imagePath.forEach(i -> {
            try {
                file.add(new FileInputStream(i).readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return ResponseDto.<List<byte[]>>builder()
                .message(OK)
                .code(OK_CODE)
                .data(file)
                .success(true)
                .build();
    }


    private String savePassport(MultipartFile file, Integer userId, String ext) {
        String filePath;
        try {
            java.nio.file.Files.copy(file.getInputStream(), Path.of(
                    filePath = filePath("passport/user" + userId, ext)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath;
    }

    private String saveDiploma(MultipartFile file, Integer userId, String ext) {
        String filePath;
        try {
            java.nio.file.Files.copy(file.getInputStream(), Path.of(
                    filePath = filePath("diploma/user" + userId, ext)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath;
    }

    private String saveText(MultipartFile file, Integer userId, String ext) {
        String filePath;
        try {
            java.nio.file.Files.copy(file.getInputStream(), Path.of(
                    filePath = filePath("text/user" + userId, ext)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath;
    }

}
