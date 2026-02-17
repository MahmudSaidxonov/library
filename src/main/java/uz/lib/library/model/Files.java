package uz.lib.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_files")
@Getter
@Setter
public class Files {
    @Id
    @GeneratedValue(generator = "usr_file_id_seq")
    @SequenceGenerator(name = "usr_file_id_seq", sequenceName = "usr_file_id_seq", allocationSize = 1)
    private Integer id;
    private Integer userId;
    private String path;
    private String ext;
    @ManyToOne(fetch = FetchType.LAZY)
    private FileType fileType;

}
