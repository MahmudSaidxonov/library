package uz.lib.library.service;

import org.springframework.web.multipart.MultipartFile;
import uz.lib.library.dto.ResponseDto;

import java.io.IOException;
import java.util.List;

public interface FilesService {
    ResponseDto<Integer> fileUpload(Integer userId, String type, MultipartFile file);

    ResponseDto<List<byte[]>> getFileById(Integer fileId, String type) throws IOException;
}
