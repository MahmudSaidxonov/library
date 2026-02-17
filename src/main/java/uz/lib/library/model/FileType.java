package uz.lib.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "file_type")
@Entity
@Getter
@Setter
public class FileType {
    @Id
    @GeneratedValue(generator = "resolutionIdSeq")
    @SequenceGenerator(name = "resolutionIdSeq", sequenceName = "resolution_id_seq", allocationSize = 1)
    private Integer id;
    private String type;
    @OneToMany(mappedBy = "fileType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Files> fileList = new ArrayList<>();
}

